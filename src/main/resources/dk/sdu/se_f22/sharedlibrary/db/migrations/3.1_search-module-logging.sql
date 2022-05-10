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