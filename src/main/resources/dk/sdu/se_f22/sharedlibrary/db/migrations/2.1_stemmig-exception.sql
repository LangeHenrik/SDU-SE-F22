CREATE TABLE StemmingException (
    id SERIAL PRIMARY KEY,
    exceptionName varchar(50) UNIQUE NOT NULL
);