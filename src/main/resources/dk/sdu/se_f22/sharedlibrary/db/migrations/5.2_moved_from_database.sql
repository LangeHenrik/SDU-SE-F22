-- Drop dependent tables
DROP TABLE IF EXISTS BrandProductTypeJunction;
DROP TABLE IF EXISTS TokenBrandMap;
-- Drop all other tables
DROP TABLE IF EXISTS Tokens;
-- Drop all other tables
DROP TABLE IF EXISTS Brand;
DROP TABLE IF EXISTS ProductType;
DROP TABLE IF EXISTS Config;
DROP TABLE IF EXISTS StemmingException;
DROP TABLE IF EXISTS irregularwords;

 --Her oprettes tabellerne, der skal ikke INSERT INTO tabellerne endnu, da vi vil lave en .java fil som seeder hele databasen på én gang,
 --og kalder hver gruppes seedDatabase()-metode

CREATE TABLE Brand(
    id           serial PRIMARY KEY,
    name         VARCHAR(255) UNIQUE NOT NULL,
    description  TEXT,
    founded      VARCHAR(255),
    headquarters VARCHAR(255)
);

CREATE TABLE ProductType(
    id   serial PRIMARY KEY,
    type VARCHAR(255) UNIQUE NOT NULL
);

CREATE TABLE Tokens(
    id serial PRIMARY KEY,
    token VARCHAR(255) NOT NULL UNIQUE
);

CREATE TABLE TokenBrandMap(
    id serial PRIMARY KEY,
    tokenId INTEGER NOT NULL REFERENCES Tokens (id),
    brandId INTEGER NOT NULL REFERENCES Brand (id)

);

CREATE TABLE BrandProductTypeJunction(
    brandId   INTEGER REFERENCES Brand (id),
    productId INTEGER REFERENCES ProductType (id)
);

CREATE TABLE Config(
    brandIndexInterval INTEGER NOT NULL
);

CREATE TABLE StemmingException (
    id SERIAL PRIMARY KEY,
    exceptionName varchar(50) UNIQUE NOT NULL
);


CREATE TABLE irregularwords(
  word VARCHAR PRIMARY KEY,
  index INT NOT NULL
);

CREATE TABLE StemmingException (
    id SERIAL PRIMARY KEY,
    exceptionName varchar(50) UNIQUE NOT NULL
);

CREATE TABLE Misspellings(
    wrong varchar UNIQUE NOT NULL,
    correct varchar NOT NULL
);
