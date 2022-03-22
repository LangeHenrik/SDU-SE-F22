
/*
 Drop alle tabellerne i starten af dokumentet.
    -   Drop tabeller som refererer til andre tabeller
    -   Drop resten af tabellerne
 */
 -- Drop dependent tables
DROP TABLE IF EXISTS BrandProductTypeJunction;

-- Drop all other tables
DROP TABLE IF EXISTS Brand;
DROP TABLE IF EXISTS ProductType;
DROP TABLE IF EXISTS Config;

/*
 Her oprettes tabellerne, der skal ikke INSERT INTO tabellerne endnu, da vi vil lave en .java fil som seeder hele databasen på én gang,
 og kalder hver gruppes seedDatabase()-metode
 */
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

CREATE TABLE BrandProductTypeJunction(
    brandId   INTEGER REFERENCES Brand (id),
    productId INTEGER REFERENCES ProductType (id)
);

CREATE TABLE Config(
    brandIndexInterval INTEGER NOT NULL
);

DROP TABLE IF EXISTS SearchTokenDelimiterSettings;

CREATE TABLE SearchTokenDelimiterSettings (
    id serial PRIMARY KEY,
    delimiter VARCHAR NOT NULL UNIQUE
);