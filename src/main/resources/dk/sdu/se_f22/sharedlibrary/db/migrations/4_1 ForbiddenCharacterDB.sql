CREATE TABLE illegalchars (
    id SERIAL PRIMARY KEY,
    characters varchar(50) UNIQUE NOT NULL
);