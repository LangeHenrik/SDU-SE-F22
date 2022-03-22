
/*
 -Drop alle tabellerne i starten af dokumentet.
    -  Drop tabeller som refererer til andre tabeller
    -   Drop resten af tabellerne
*/
-- Drop dependent tables
DROP TABLE IF EXISTS BrandProductTypeJunction;
DROP TABLE IF EXISTS TokenBrandTypeJunction;

-- Drop all other tables
DROP TABLE IF EXISTS Tokens;
DROP TABLE IF EXISTS Brand;
DROP TABLE IF EXISTS ProductType;
DROP TABLE IF EXISTS Config;
DROP TABLE IF EXISTS StemmingException;

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
    token VARCHAR(255) NOT NULL
);

CREATE TABLE TokenBrandTypeJunction(
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
