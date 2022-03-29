
/*
 -Drop alle tabellerne i starten af dokumentet.
    -  Drop tabeller som refererer til andre tabeller
    -   Drop resten af tabellerne
*/
-- Drop dependent tables
DROP TABLE IF EXISTS BrandProductTypeJunction;

-- Drop all other tables
DROP TABLE IF EXISTS BrandSearches;
DROP TABLE IF EXISTS ProductSearches;
DROP TABLE IF EXISTS ContentSearches;
DROP TABLE IF EXISTS Searches;
DROP TABLE IF EXISTS Brand;
DROP TABLE IF EXISTS ProductType;
DROP TABLE IF EXISTS Config;
DROP TABLE IF EXISTS StemmingException;

DROP TRIGGER IF EXISTS UpdateAllSearchesCountersTrigger ON Searches;
DROP TRIGGER IF EXISTS UpdateAllSearchesCountersProductTrigger ON Searches;
DROP TRIGGER IF EXISTS UpdateAllSearchesCountersBrandTrigger ON Searches;
DROP TRIGGER IF EXISTS UpdateAllSearchesCountersContentTrigger ON Searches;

--Her oprettes tabellerne, der skal ikke INSERT INTO tabellerne endnu, da vi vil lave en .java fil som seeder hele databasen på én gang,
--og kalder hver gruppes seedDatabase()-metode

CREATE TABLE Searches
(
    id             SERIAL PRIMARY KEY,
    searchString   varchar NOT NULL,
    timeSearched   TIMESTAMP DEFAULT NOW(),
    brandsCounter  INTEGER,
    productCounter INTEGER,
    contentCounter INTEGER
);

CREATE TABLE BrandSearches
(
    id       SERIAL PRIMARY KEY,
    brandID  TEXT NOT NULL,
    searchID INTEGER REFERENCES Searches (id)
);

CREATE TABLE ProductSearches
(
    id        SERIAL PRIMARY KEY,
    productID TEXT NOT NULL,
    searchID  INTEGER REFERENCES Searches (id)
);

CREATE TABLE ContentSearches
(
    id       SERIAL PRIMARY KEY,
    content  TEXT NOT NULL,
    searchID INTEGER REFERENCES Searches (id)
);

CREATE OR REPLACE PROCEDURE UpdateSearchesCounters(_SearchID INTEGER)
AS
$$
DECLARE
    _BrandsCounter  INTEGER := 0;
    _ProductCounter INTEGER := 0;
    _ContentCounter INTEGER := 0;
BEGIN
    SELECT COUNT(*)
    INTO _BrandsCounter
    FROM BrandSearches
    WHERE SearchID = _SearchID;
    SELECT COUNT(*)
    INTO _ProductCounter
    FROM ProductSearches
    WHERE SearchID = _SearchID;
    SELECT COUNT(*)
    INTO _ContentCounter
    FROM ContentSearches
    WHERE SearchID = _SearchID;

    UPDATE Searches
    SET BrandsCounter  = _BrandsCounter,
        ProductCounter = _ProductCounter,
        ContentCounter = _ContentCounter
    WHERE id = _SEARCHID;
END;
$$
    LANGUAGE plpgsql;


CREATE OR REPLACE PROCEDURE UpdateAllSearchesCounters()
AS
$$
DECLARE
    SearchIterator CURSOR FOR SELECT DISTINCT(id) AS id
                              FROM Searches;
BEGIN
    FOR SearchEntity IN SearchIterator
        LOOP
            CALL UpdateSearchesCounters(SearchEntity.id);
        END LOOP;
END;
$$
    LANGUAGE plpgsql;


CREATE OR REPLACE FUNCTION UpdateAllSearchesCountersTrigger()
    RETURNS TRIGGER
as
$$
BEGIN
    CALL UpdateAllSearchesCounters();
    RETURN NEW;
END;
$$
    LANGUAGE plpgsql;

CREATE TRIGGER UpdateAllSearchesCountersTrigger
    AFTER INSERT OR DELETE
    ON Searches
EXECUTE FUNCTION UpdateAllSearchesCountersTrigger();

CREATE TRIGGER UpdateAllSearchesCountersBrandTrigger
    AFTER INSERT OR DELETE
    ON BrandSearches
EXECUTE FUNCTION UpdateAllSearchesCountersTrigger();

CREATE TRIGGER UpdateAllSearchesCountersProductTrigger
    AFTER INSERT OR DELETE
    ON ProductSearches
EXECUTE FUNCTION UpdateAllSearchesCountersTrigger();

CREATE TRIGGER UpdateAllSearchesCountersContentTrigger
    AFTER INSERT OR DELETE
    ON ContentSearches
EXECUTE FUNCTION UpdateAllSearchesCountersTrigger();

CREATE TABLE Brand(
    id           SERIAL PRIMARY KEY,
    name         VARCHAR(255) UNIQUE NOT NULL,
    description  TEXT,
    founded      VARCHAR(255),
    headquarters VARCHAR(255)
);

CREATE TABLE ProductType(
    id   SERIAL PRIMARY KEY,
    type VARCHAR(255) UNIQUE NOT NULL
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
CREATE TABLE StemmingException (
    id SERIAL PRIMARY KEY,
    exceptionName varchar(50) UNIQUE NOT NULL
);
