package dk.sdu.se_f22.sharedlibrary.db;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.LoggerContext;
import org.apache.logging.log4j.core.appender.AsyncAppender;
import org.apache.logging.log4j.core.appender.ConsoleAppender;
import org.apache.logging.log4j.core.appender.db.jdbc.AbstractConnectionSource;
import org.apache.logging.log4j.core.appender.db.jdbc.ColumnConfig;
import org.apache.logging.log4j.core.appender.db.jdbc.JdbcAppender;
import org.apache.logging.log4j.core.config.AppenderRef;
import org.apache.logging.log4j.core.config.LoggerConfig;
import org.apache.logging.log4j.core.layout.PatternLayout;

import java.sql.Connection;
import java.sql.SQLException;

public class LoggingProvider {
    private static final Level logLevel = Level.ALL;
    private static final LoggerContext loggerContext;
    private static final LoggerConfig loggerConfig;

    static {
        loggerContext = (LoggerContext) LogManager.getContext();
        loggerConfig = loggerContext.getConfiguration().getRootLogger();

        clearAppenders();

        JdbcAppender jdbcAppender = createJdbcAppender();
        AsyncAppender asyncAppender = createAsyncAppender(jdbcAppender);
        ConsoleAppender consoleAppender = createConsoleAppender();

        loggerContext.getRootLogger().addAppender(loggerContext.getConfiguration().getAppender(asyncAppender.getName()));
        loggerContext.getRootLogger().addAppender(loggerContext.getConfiguration().getAppender(consoleAppender.getName()));

        loggerContext.getConfiguration().getRootLogger().setLevel(logLevel);

        loggerContext.updateLoggers();
    }

    private static void clearAppenders() {
        loggerConfig.getAppenders().forEach((key, value) -> loggerConfig.removeAppender(value.getName()));
    }

    private static ConsoleAppender createConsoleAppender() {
        var patternLayout = PatternLayout.newBuilder()
                .withPattern("%d{HH:mm:ss.SSS} [%thread] %-5level %logger{36} - %msg%n")
                .build();
        var consoleAppender = ConsoleAppender.newBuilder()
                .setName("ConsoleAppender")
                .setLayout(patternLayout)
                .build();
        consoleAppender.start();
        loggerContext.getConfiguration().addAppender(consoleAppender);
        return consoleAppender;
    }

    private static AsyncAppender createAsyncAppender(JdbcAppender jdbcAppender) {
        var asyncAppender = AsyncAppender.newBuilder()
                .setName("AsyncAppender")
                // This is the amount of logs to keep in the async buffer before blocking
                // There is no special reason i picked this number in particular
                .setBufferSize(1000)
                // Block when queue is full to prevent dropping logs
                .setBlocking(true)
                .setConfiguration(loggerContext.getConfiguration())
                .setAppenderRefs(new AppenderRef[] {
                        AppenderRef.createAppenderRef(jdbcAppender.getName(), logLevel, null)
                })
                .build();
        asyncAppender.start();
        loggerContext.getConfiguration().addAppender(asyncAppender);
        return asyncAppender;
    }

    private static JdbcAppender createJdbcAppender() {
        var columnConfigs = getColumnConfigs();
        var jdbcAppender = JdbcAppender.newBuilder()
                .setName("DatabaseAppender")
                .setConnectionSource(new AbstractConnectionSource() {
                    @Override
                    public Connection getConnection() throws SQLException {
                        return DBConnection.getPooledConnection();
                    }
                })
                .setTableName("logs")
                .setColumnConfigs(columnConfigs).build();
        jdbcAppender.start();
        loggerContext.getConfiguration().addAppender(jdbcAppender);
        return jdbcAppender;
    }

    public static Logger getLogger(final Class<?> moduleName) {
        return loggerContext.getLogger(moduleName);
    }

    private static ColumnConfig[] getColumnConfigs() {
        return new ColumnConfig[]{
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
    }
}
