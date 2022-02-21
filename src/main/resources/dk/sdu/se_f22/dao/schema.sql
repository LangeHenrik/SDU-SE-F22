CREATE TABLE IF NOT EXISTS logs
(
    log_id  varchar(100) not null constraint logs_pk primary key,
    entry_date timestamp,
    logger varchar(100),
    log_level varchar(100),
    message text,
    exception text
);