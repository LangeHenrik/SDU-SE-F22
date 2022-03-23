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

import dk.sdu.se_f22.sharedlibrary.Color;

/**
 * Migrate database to the newest version
 * 
 * @author Mikkel Albrechtsen (The0mikkel)
 */
public class DBMigration {

    private Connection connection;
    private int batch;
    private boolean printText;

    public static void main(String[] args) {
        DBMigration databaseSeeder = new DBMigration();
        databaseSeeder.migrate();
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
        this.connection = DBConnection.getConnection();
        this.batch = 0;
        this.printText = printText;
    }

    /**
     * Migrate the current databse to the newest version
     */
    public void migrate() {
        String migrationsPath = "src/main/resources/dk/sdu/se_f22/sharedlibrary/db/migrations";

        // creates a file object
        File file = new File(migrationsPath);

        // returns an array of all files, sorted alphabetically
        String[] fileList = file.list();
        Arrays.sort(fileList);

        // Get known migrations
        List migrations = this.getMigrations();
        this.batch++;

        boolean migrationStatus;

        for (String fileName : fileList) {
            // Ensure the file is a sql file
            String lowercaseFileName = fileName.toLowerCase(); 
            if (!lowercaseFileName.endsWith(".sql")) {
                continue;
            }

            // Ensure the file has not yet been runned in any migrations
            if (migrations.contains(fileName)) {
                continue;
            }

            // Run all files
            this.println("Migrating: " + fileName, Color.YELLOW);
            migrationStatus = runSQLFromFile(this.connection, migrationsPath + fileName);

            // Add migration to migration list
            if (migrationStatus) {
                if (!this.addMigration(fileName)) {
                    this.println("Migration completed but could not add migration to migrations table", Color.RED_UNDERLINED);
                } else {
                    this.println("Migration completed", Color.GREEN);
                }
            } else {
                this.println("Migration failed!", Color.RED);
                return;
            }
        }

        this.println("Database up to date", Color.GREEN_UNDERLINED);
    }

    /**
     * migrate the database to a fresh (new) state - THIS WILL DELETE ALL DATA
     */
    public void migrateFresh() {
        this.println("Flushing database", Color.YELLOW);

        try {
            PreparedStatement stmt;
            // Drop schema to delete all tables
            stmt = connection.prepareStatement("DROP SCHEMA public CASCADE;");
            stmt.execute();

            // Recreate schema
            stmt = connection.prepareStatement("CREATE SCHEMA public;");
            stmt.execute();
        } catch (SQLException ex) {
            ex.printStackTrace();
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
     * @author v-nementh
     * @author Mikkel Albrechtsen (The0mikkel)
     */
    private boolean runSQLFromFile(Connection connection, String SQLFileName) {
        try (
            FileReader fr = new FileReader(SQLFileName);
            BufferedReader br = new BufferedReader(fr);
            Statement stmt = connection.createStatement();
        ) {
            // Begin a transaction
            connection.setAutoCommit(false);

            String currentLine;
            String sqlCode = "";

            // Read the file one line at a time
            while ((currentLine = br.readLine()) != null) {
                // Safeguards commented code in -- format
                if (currentLine.contains("--"))
                    continue;

                // Safeguards commented code in /* comment /n */ format
                if (currentLine.contains("/*")) {
                    // Safeguards /* */ in same line
                    if (currentLine.contains("*/"))
                        break;

                    String commentedLine;
                    while ((commentedLine = br.readLine()) != null) {
                        if (commentedLine.contains("*/"))
                            break;
                    }
                    continue;
                }

                // Execute the sql-code
                if (currentLine.contains(";")) {
                    sqlCode += currentLine + " ";
                    // System.out.println("Printing sql code...");
                    // System.out.println(sqlCode);
                    stmt.execute(sqlCode);
                    sqlCode = "";
                    continue;
                }
                sqlCode += currentLine + " ";
            }

            // Commit the current file, if no errors was encountered
            connection.commit();
            connection.setAutoCommit(true);
        } catch (SQLException|IOException error) {
            // Printing stack trace for better debugging
            error.printStackTrace();

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
                this.println("Could not rollback update");
                rollbackError.printStackTrace();
            }

            return false;
        }
        return true;
    }

    private List<String> getMigrations() {
        try {
            PreparedStatement stmt = connection.prepareStatement("SELECT file_name, batch FROM migrations ORDER BY batch");
            ResultSet sqlReturnValues = stmt.executeQuery();
            List<String> returnValue = new ArrayList<String>();
            int topBatch = 0;
            while (sqlReturnValues.next()) {
                returnValue.add(sqlReturnValues.getString(1));
                topBatch = sqlReturnValues.getInt(2);
            }
            this.batch = topBatch;
            return returnValue;
        } catch (SQLException ex) {
            // ex.printStackTrace();
            return new ArrayList<String>();
        }
    }

    private boolean addMigration(String fileName) {
        try {
            PreparedStatement stmt = connection.prepareStatement("INSERT INTO migrations (file_name, batch) VALUES (?, ?)");
            stmt.setString(1, fileName);
            stmt.setInt(2, this.batch);
            stmt.execute();
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
        return true;
    }

    private void println(String text) {
        this.println(text, Color.RESET);
    }

    private void println(String text, Color color) {
        if (this.printText) {
            System.out.println(color + text + Color.RESET);
        }
    }
}
