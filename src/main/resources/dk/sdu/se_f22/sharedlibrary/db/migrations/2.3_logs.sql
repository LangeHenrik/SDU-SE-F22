CREATE TABLE IF NOT EXISTS logs
(
    log_id TEXT PRIMARY KEY,
    entry_date TIMESTAMP,
    logger TEXT,
    log_level TEXT,
    message TEXT,
    exception TEXT
);