package dk.sdu.se_f22.sharedlibrary.db;

import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.logging.log4j.Logger;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

/**
 * This is the main class for handling database connections.
 * <p>
 * It is implemented as a wrapper around the Postgres JBDC
 * driver and Apache DBCP2 connection pool. A connection
 * pool is simply a way to recycle database connection,
 * in order to prevent the overhead of making a new data
 * -base connection on every request.
 * Read more: https://stackoverflow.com/a/20712571
 * <p>
 * A connection pool also gives us the opportunity to use
 * prepared statement caching, which means that the old
 * prepared statements can also be reused instead of creating
 * new ones. This especially works well for applications
 * running the same queries many times.
 * Read more: https://stackoverflow.com/a/12141276
 * <p>
 * While using a single connection would work, there are little
 * to no downsides to using a connection pool.
 */
public class DBConnection {
    private static final Logger logger = LoggingProvider.getLogger(DBConnection.class);

    private static DBConnection instance = null;
    private final BasicDataSource connectionPool;
    private String url;
    private String user;
    private String password;
    private Connection dbConnection;

    private DBConnection() {
        loadConfig();

        // We use a connection pool, to reuse database connections
        connectionPool = new BasicDataSource();
        connectionPool.setDriverClassName("org.postgresql.Driver");

        // Connections settings
        connectionPool.setUrl("jdbc:postgresql://abul.db.elephantsql.com/hzajyqbo");
        connectionPool.setUsername("hzajyqbo");
        connectionPool.setPassword("K8664qtGojuBvQczzv66EhaqkUNbXLj0");

        // Pooling settings
        // There is not any particular reason behind these values,
        // and they can be tweaked if necessary.
        connectionPool.setMinIdle(5);
        connectionPool.setMaxIdle(20);
        connectionPool.setMaxTotal(30);
        connectionPool.setMaxOpenPreparedStatements(100);
    }

    public static DBConnection getInstance() {
        if (instance == null)
            instance = new DBConnection();
        return instance;
    }

    /**
     * WARN: This method is only kept for backwards compatability and will
     * eventually be removed! Use {@link #getPooledConnection()} instead!
     * <br/>
     * <p>
     * This method gets a reserved connection from the pool to the database.
     * This type of connection does not have to be closed, and doing this
     * many times using this method reduces performance severely, since opening
     * database connections has a large overhead. Since a connection isn't thread-
     * safe, database queries cannot be made outside the main thread with this,
     * and when trying to call this method on another thread null will be returned.
     *
     * @deprecated use {@link #getPooledConnection()} instead.
     */
    @Deprecated
    public static Connection getConnection() {

        // NOTE: This isn't foolproof way to ensure getConnection isn't used outside the main thread.
        if (Thread.currentThread().getId() != 1) {
            logger.warn("A database connection cannot be retrieved from outside the main thread, use getPooledConnection() instead!");
            return null;
        }

        try {
            if (getInstance().dbConnection == null || getInstance().dbConnection.isClosed()) {
                getInstance().dbConnection = getInstance().connectionPool.getConnection();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return getInstance().dbConnection;
    }

    /**
     * This method returns a reference to a connection from the connection pool.
     * When using this method, you have to close the connection after you are
     * done using it, such that the connection can be returned to the pool.
     * <p>
     * <br/>
     * Example using try-with-resources:
     * <pre> <code>     try (var connection = DBConnection.getPooledConnection();
     *     var statement = connection.prepareStatement("SELECT * FROM logs");
     *     var resultset = statement.executeQuery();) {
     *          while (resultset.next()) {
     *              System.out.println("Log: " + resultset.getString("message"));
     *          }
     * } catch(SQLException e) {
     *     e.printStackTrace();
     * } </code> </pre>
     */
    public static Connection getPooledConnection() throws SQLException {
        return getInstance().connectionPool.getConnection();
    }

    private void loadConfig() {
       /*try (InputStream inputStream = new FileInputStream("config.properties")) {
            Properties props = new Properties();
            props.load(inputStream);

            url = props.getProperty("db_url");
            user = props.getProperty("db_user");
            password = props.getProperty("db_password");
        } catch (IOException e) {
            e.printStackTrace();
        }*/
    }

    public boolean isConnected() {
        if (connectionPool == null)
            return false;

        boolean connectionStatus;

        try (Connection connection = connectionPool.getConnection()) {
            connectionStatus = connection.isValid(5);
        } catch (SQLException e) {
            return false;
        }

        return connectionStatus;
    }
}
