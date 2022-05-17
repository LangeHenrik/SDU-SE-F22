CREATE TABLE TokenParameters(
    id serial PRIMARY KEY,
    delimiter varchar(5) NOT NULL,
    ignoredChars varchar(64) NOT NULL,
    type varchar(50) NOT NULL
);