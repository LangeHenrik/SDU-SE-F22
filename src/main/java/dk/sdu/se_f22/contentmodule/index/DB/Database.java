package dk.sdu.se_f22.contentmodule.index.DB;

import java.sql.*;
import java.util.ArrayList;

public class Database {
    public Connection connection;
    private boolean conStatus;


    public Database() {
        // default database
        // No-args constructor
        connect("localhost", "5432", "postgres", "postgres", "test1234");
    }


    public Database(String username, String password) {
        // different-user constructor
        connect("localhost", "5432", "postgres", username, password);
    }


    public Database(String ip, String port, String dbname, String username, String password) {
        // different-DB constructor
        connect(ip, port, dbname, username, password);
    }


    private void connect(String ip, String port, String database, String username, String password) {
        try {
            connection = DriverManager.getConnection("jdbc:postgresql://" + ip + ":" + port + "/" + database, username, password);
            System.out.println("success DB");
            conStatus = true;
        } catch (Exception e) {
            System.out.println("error DB");
            conStatus = false;
        }
    }

    public boolean closeCon() {
        try {
            connection.close();
            return true;
        } catch (SQLException e) {
            return false;
        }
    }

    public boolean isConStatus() {
        // Metode til at se om der er forbindelse til DB
        return conStatus;
    }

    public int executeQuery(String SQL) {
        // Hvis du vil have et resultat tilbage
        int id = -1;
        int temp = -10;
        try {
            PreparedStatement statement = this.connection.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS);
            statement.executeQuery();
            try (ResultSet rs = statement.getResultSet()) {
                if (rs.next()) {
                    try {
                        id = rs.getInt(1);
                    } catch (Exception k) {
                        System.out.print(rs.getString(1));
                        temp = 0;
                    }
                    rs.close();
                }

            }
            if (temp != 0) {
                //System.out.println("Success "+SQL + " " + id);
            }
            return id;
        } catch (SQLException e) {
            //System.out.println("error "+SQL + " " + id);
            return id;
        }
    }

    public int execute(String SQL) {
        // Update i databasen og returner int
        int id = -1;
        try {
            PreparedStatement statement = this.connection.prepareStatement(SQL, 1);
            statement.executeUpdate();
            ResultSet rs = statement.getGeneratedKeys();

            try {
                if (rs.next()) {
                    id = rs.getInt(1);
                    rs.close();
                }
            } catch (Throwable var8) {
                if (rs != null) {
                    try {
                        rs.close();
                    } catch (Throwable var7) {
                        var8.addSuppressed(var7);
                    }
                }

                throw var8;
            }

            if (rs != null) {
                rs.close();
            }

            //System.out.println("Success " + SQL + " " + id);
            return id;
        } catch (SQLException var9) {
            //System.out.println("error " + SQL + " " + id);
            return id;
        }
    }

    public ArrayList<Integer> executeQueryArray(String SQL) {
        int id = -1;
        int temp = -10;
        ArrayList<Integer> ids = new ArrayList<>();
        try {
            PreparedStatement statement = this.connection.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS);
            statement.executeQuery();
            try (ResultSet rs = statement.getResultSet()) {
                while (rs.next()) {
                    ids.add(Integer.parseInt(rs.getArray(1).toString()));
                }
            }
            if (temp != 0) {
                //System.out.println("Success "+SQL + " " + id);
            }
            return ids;
        } catch (SQLException e) {
            System.out.println("error " + SQL + " " + id);
            return ids;
        }
    }

    public ArrayList<String> executeQueryResultset(String SQL) {
        int id = -1;
        int temp = -10;
        ArrayList<String> tempArrayList = new ArrayList<>();
        ArrayList<Integer> ids = new ArrayList<>();
        try {
            PreparedStatement statement = this.connection.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS);
            statement.executeQuery();
            try (ResultSet rs = statement.getResultSet()) {
                while (rs.next()) {
                    tempArrayList.add(rs.getArray(1).toString());
                    tempArrayList.add(rs.getArray(2).toString());
                    tempArrayList.add(rs.getArray(3).toString());
                }

            }
            if (temp != 0) {
                //System.out.println("Success "+SQL + " " + id);
            }
        } catch (SQLException e) {
            //System.out.println("error " + SQL + " " + id);
        }
        return tempArrayList;
    }
}
