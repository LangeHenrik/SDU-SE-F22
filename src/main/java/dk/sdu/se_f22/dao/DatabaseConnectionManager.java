package dk.sdu.se_f22.dao;

import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.LoggerContext;
import org.apache.logging.log4j.core.appender.db.jdbc.*;
import java.sql.*;

public class DatabaseConnectionManager extends AbstractConnectionSource {
    // Singleton
    private static final DatabaseConnectionManager instance = new DatabaseConnectionManager();

    // Logging attributes
    private Level logLevel = Level.ALL;

    // Database attributes
    private String database;
    private String host;
    private String port;
    private String username;
    private String password;
    private boolean hasSSL;
    private BasicDataSource connectionPool;

    /**
     * Setup database connection info
     * @param database The name of the target database
     * @param host The hostname of the database e.g. localhost
     * @param port The portname of the database e.g. 5432
     * @param username The DBMS username
     * @param password The DBMS password
     * @param hasSSL Whether or not the connection is encrypted
     */
    private DatabaseConnectionManager(String database, String host, String port, String username, String password, boolean hasSSL) {
        this.database = database;
        this.host = host;
        this.port = port;
        this.username = username;
        this.password = password;
        this.hasSSL = hasSSL;

        connectionPool = new BasicDataSource();
        connectionPool.setDriverClassName("org.postgresql.Driver");

        // Connections settings
        connectionPool.setUrl(getUrl());
        connectionPool.setUsername(username);
        connectionPool.setPassword(password);
        if(hasSSL)
            connectionPool.addConnectionProperty("useSSL", "true");

        // Pooling settings
        connectionPool.setMinIdle(5);
        connectionPool.setMaxIdle(10);
        connectionPool.setMaxOpenPreparedStatements(100);
    }

    /**
     * TODO: Make this set the database connection settings from environment variables
     */
    private DatabaseConnectionManager() {
        this("postgres", "localhost", "5432", "postgres", "postgres", false);
    }

    /**
     * Singleton accessor
     * @return A reference to the current DatabaseConnectionManager singleton
     */
    public static DatabaseConnectionManager getInstance(){
        return instance;
    }

    /**
     * Used to check whether the database connection is valid
     */
    public boolean isConnected() {
        if(connectionPool == null)
            return false;

        try {
            var connection = connectionPool.getConnection();
            return connection.isValid(5);
        } catch (SQLException e) {
           return false; // If we get an error, then we can assume that we aren't connected
        }
    }

    /**
     * Complete database connection url
     */
    public String getUrl() {
        return String.format("jdbc:postgresql://%s:%s/%s", host, port, database);
    }

    /**
     * This gives a connection from the connection pool, to make database queries.
     * When close() is called, the connection is returned to the connection
     * pool for reuse. Idle connections are killed automatically after some time I think.
     * @return A database connection
     */
    @Override
    public Connection getConnection() throws SQLException {
        return getInstance().connectionPool.getConnection();
    }

    /**
     * A database backed logger
     * @param moduleName The class from which the logger is used e.g. MyClass.class
     */
    public Logger getLogger(final Class moduleName) {
        /*
          The database scheme for the logs:
            CREATE TABLE IF NOT EXISTS logs
            (
                log_id  varchar(100) not null constraint logs_pk primary key,
                entry_date timestamp,
                logger varchar(100),
                log_level varchar(100),
                message text,
                exception text
            );
         */
        ColumnConfig[] columnConfigs = new ColumnConfig[] {
            ColumnConfig.newBuilder()
                .setName("log_id")
                 // This is needed because setNString is not implemented in postgres jcbc:
                 // https://github.com/pgjdbc/pgjdbc/blob/a966396c5e2ac3398ba5196d5e9baed705cf593e/pgjdbc/src/main/java/org/postgresql/jdbc/PgPreparedStatement.java#L1475
                .setUnicode(false)
                .setPattern("%u")
                .build(),
                ColumnConfig.newBuilder()
                        .setName("entry_date")
                        .setUnicode(false)
                        .setEventTimestamp(true)
                        .build(),
                ColumnConfig.newBuilder()
                        .setName("logger")
                        .setUnicode(false)
                        .setPattern("%logger")
                        .build(),
                ColumnConfig.newBuilder()
                        .setName("log_level")
                        .setUnicode(false)
                        .setPattern("%level")
                        .build(),
            ColumnConfig.newBuilder()
                .setName("message")
                .setUnicode(false)
                .setPattern("%m")
                .build(),
                ColumnConfig.newBuilder()
                        .setName("exception")
                        .setUnicode(false)
                        .setPattern("%throwable")
                        .build()
        };

        // Sets up the appenders for the logging
        var jdbcAppender = JdbcAppender.newBuilder()
                .setName("DatabaseAppender")
                .setConnectionSource(getInstance())
                .setTableName("logs")
                .setColumnConfigs(columnConfigs).build();
        jdbcAppender.start();

//        var consoleAppender = ConsoleAppender.newBuilder()
//                .setName("ConsoleAppender")
//                .setTarget(ConsoleAppender.Target.SYSTEM_OUT)
//                .build();
//        consoleAppender.start();

        // Apply the appenders to the current logging context
        LoggerContext lc = (LoggerContext) LogManager.getContext(true);

        lc.getConfiguration().addAppender(jdbcAppender);
//        lc.getConfiguration().addAppender(consoleAppender);

        lc.getRootLogger().addAppender(lc.getConfiguration().getAppender(jdbcAppender.getName()));
//        lc.getRootLogger().addAppender(lc.getConfiguration().getAppender(consoleAppender.getName()));

        // Sets the logging level at which logs are saved
        lc.getConfiguration().getRootLogger().setLevel(this.logLevel);

        // Apply configuration
        lc.updateLoggers();

        return lc.getLogger(moduleName);
    }
}
