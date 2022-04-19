CREATE TABLE illegalChars (
    id SERIAL PRIMARY KEY,
    chars varchar(50) UNIQUE NOT NULL
);