CREATE TABLE token_parameters(
    id serial PRIMARY KEY,
    delimiter varchar(5) NOT NULL,
    ignored_chars varchar(64) NOT NULL,
    type varchar(50) NOT NULL
);