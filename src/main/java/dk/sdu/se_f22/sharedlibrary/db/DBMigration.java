package dk.sdu.se_f22.sharedlibrary.db;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import dk.sdu.se_f22.sharedlibrary.utils.Color;
import dk.sdu.se_f22.sharedlibrary.utils.NaturalOrderComparator;

/**
 * Migrate database to the newest version
 * 
 * @author Mikkel Albrechtsen (The0mikkel)
 */
public class DBMigration {
    private int batch;
    private boolean printText;

    public static void main(String[] args) {
        DBMigration migrator = new DBMigration();
        migrator.migrate();
    }

    /**
     * Migrate database to the newest version
     */
    public DBMigration () {
        this(true);
    }

    /**
     * Migrate database to the newest version
     * @param printText Enable or disable printing of text to console
     */
    public DBMigration (boolean printText) {
        this.batch = 0;
        this.printText = printText;
    }

    /**
     * Migrate the current databse to the newest version
     */
    public void migrate() {
        String migrationsPath = "src/main/resources/dk/sdu/se_f22/sharedlibrary/db/migrations/";

        // Run default SeedDatabase
        

        try (
            Connection connection = DBConnection.getPooledConnection();
        ) {
            // creates a file object
            File file = new File(migrationsPath);

            // returns an array of all files, sorted alphabetically
            String[] fileList = file.list();
            Arrays.sort(fileList, new NaturalOrderComparator());

            // Get known migrations
            List<String> migrations = this.getMigrations();
            this.batch++;

            boolean migrationStatus;

            for (String fileName : fileList) {
                fileName = fileName.toLowerCase();

                // Ensure the file is a sql file
                if (!this.validateFile(fileName)) {
                    continue;
                }

                // Ensure the file has not yet been runned in any migrations
                if (migrations.contains(fileName)) {
                    continue;
                }

                // Run all files
                this.println("Migrating: " + fileName, Color.YELLOW);
                migrationStatus = runSQLFromFile(connection, migrationsPath + fileName);

                // Add migration to migration list
                if (migrationStatus) {
                    if (!this.addMigration(fileName)) {
                        this.println("Migration completed but could not add migration to migrations table", Color.RED_UNDERLINED);
                    } else {
                        this.println("Migration completed", Color.GREEN);
                    }
                } else {
                    this.println("Migration failed!", Color.RED_BOLD_BRIGHT);
                    return;
                }
            }

            this.println("Database is up to date", Color.GREEN_UNDERLINED);
        } catch (Exception error) {
            this.println(error, error.getStackTrace());

            this.println("Migration failed!", Color.RED_BOLD_BRIGHT);
        }
    }

    /**
     * migrate the database to a fresh (new) state - THIS WILL DELETE ALL DATA
     */
    public void migrateFresh() {
        this.println("Flushing database", Color.YELLOW);

        try (
            Connection connection = DBConnection.getPooledConnection();
        ) {
            PreparedStatement stmt;

            // Drop schema to delete all tables
            stmt = connection.prepareStatement("DROP SCHEMA public CASCADE;");
            stmt.execute();

            // Recreate schema
            stmt = connection.prepareStatement("CREATE SCHEMA public;");
            stmt.execute();
            stmt.close();
        } catch (SQLException error) {
            this.println(error, error.getStackTrace());
        }

        this.println("Database flushed", Color.GREEN);
        this.migrate();
    }

    /**
     * Run SQL from a .sql file
     * 
     * @param connection Database connection
     * @param SQLFileName The name of the file to execute sql from
     * @return True on success, false on error - See console for more details about the error.
     * 
     * @author v-nemeth
     * @author Mikkel Albrechtsen (The0mikkel)
     */
    public boolean runSQLFromFile(Connection connection, String SQLFileName) {
        // Begin transaction
        try {
            connection.setAutoCommit(false);
        } catch (SQLException error) {
            this.println(error, error.getStackTrace());
        }

        try (
            FileReader fr = new FileReader(SQLFileName);
            BufferedReader br = new BufferedReader(fr);
            Statement stmt = connection.createStatement();
        ) {
            String currentLine;
            String sqlCode = "";

            // Read the file one line at a time
            while ((currentLine = br.readLine()) != null) {
                sqlCode += currentLine + "\n ";
            }

            // Execute the sql
            stmt.execute(sqlCode);

            // Commit the current file, if no errors was encountered
            connection.commit();
        } catch (SQLException|IOException error) {
            // Printing stack trace for better debugging
            this.println(error, error.getStackTrace());

            // Dev friendly error messages
            if (error instanceof FileNotFoundException) {
                this.println("File could not be found", Color.RED_BOLD);
            } else if (error instanceof SQLException) {
                this.println("There was an error with the sql in the file: "+SQLFileName, Color.RED_BOLD);
            }
            
            // Rollback any sql actions that may have been
            try {
                connection.rollback();
            } catch (SQLException rollbackError) {
                this.println("Could not rollback update", Color.RED_BOLD_BRIGHT);
                this.println(rollbackError, rollbackError.getStackTrace());
            }

            return false;
        }
        
        try {
            connection.setAutoCommit(true);
        } catch (SQLException error) {
            this.println(error, error.getStackTrace());
        }
        return true;
    }

    private List<String> getMigrations() {
        try (
            Connection connection = DBConnection.getPooledConnection();
        ) {
            PreparedStatement stmt = connection.prepareStatement("SELECT file_name, batch FROM migrations ORDER BY batch");
            ResultSet sqlReturnValues = stmt.executeQuery();
            List<String> returnValue = new ArrayList<String>();
            int topBatch = 0;
            while (sqlReturnValues.next()) {
                returnValue.add(sqlReturnValues.getString(1));
                topBatch = sqlReturnValues.getInt(2);
            }
            this.batch = topBatch;
            stmt.close();
            sqlReturnValues.close();

            return returnValue;
        } catch (SQLException error) {
            // this.println(error, error.getStackTrace()); -- No stacktrace should be printed. If an error occurs, it may be due to no migration has been run, and the system will then do all migrations
            return new ArrayList<String>();
        }
    }

    private boolean addMigration(String fileName) {
        try (
            Connection connection = DBConnection.getPooledConnection();
        ) {
            PreparedStatement stmt = connection.prepareStatement("INSERT INTO migrations (file_name, batch) VALUES (?, ?)");
            stmt.setString(1, fileName);
            stmt.setInt(2, this.batch);
            stmt.execute();
            stmt.close();
        } catch (SQLException error) {
            this.println(error, error.getStackTrace());
            return false;
        }
        return true;
    }

    /**
     * Check if file is valid, to be used as a migration
     * @param filename
     * @return boolean - True if file is valid
     */
    private boolean validateFile(String filename) {
        String lowercaseFileName = filename.toLowerCase(); 
        if (!lowercaseFileName.endsWith(".sql")) {
            return false;
        }
        return true;
    }

    /**
     * Print text to console
     * 
     * @param text
     */
    private void println(String text) {
        this.println(text, Color.RESET);
    }

    /**
     * Print stacktrace to console.
     * This will always print to console.
     * 
     * @param stackTrace as StackTraceElement array
     */
    private void println(Throwable error, StackTraceElement[] stackTrace) {
        this.printText = true;
        this.println("Error occurred!", Color.RED_BOLD);
        this.println(error.getClass().getName() + " " + error.getMessage(), Color.RED_BOLD);
        this.println("Stack trace:", Color.RED);
        for (StackTraceElement stackTraceElement : stackTrace) {
            this.println(stackTraceElement.toString(), Color.RED);
        }
    }

    /**
     * Print text to command-line with color. This will only print, if printing is set for object
     * 
     * @param text String to print
     * @param color as enum from Color
     */
    private void println(String text, Color color) {
        if (this.printText) {
            System.out.println(color + text + Color.RESET);
        }
    }
}
