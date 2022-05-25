CREATE TABLE StemmingExceptions (
    id SERIAL PRIMARY KEY,
    exception varchar(50) UNIQUE NOT NULL
);