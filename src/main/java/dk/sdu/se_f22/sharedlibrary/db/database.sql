DROP DATABASE IF EXISTS;

CREATE TABLE brands(
    id           serial PRIMARY KEY,
    name         VARCHAR(255) UNIQUE NOT NULL,
    description  VARCHAR(10000),
    founded      VARCHAR(255),
    headquarters VARCHAR(255)
);

CREATE TABLE config(
    brandindexinterval INTEGER NOT NULL
);