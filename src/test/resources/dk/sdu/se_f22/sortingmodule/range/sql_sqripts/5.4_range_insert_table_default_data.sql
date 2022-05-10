-- Inserting directly to the views
-- DO not under any circumstance change the order or the content of these inserts below.
-- Doing so will break a lot of unit tests
INSERT INTO SortingRangeDoubleView (name, description, productAttribute, min, max)
VALUES ('test name double', 'test description', 'price', 0, 10);

INSERT INTO SortingRangeLongView (name, description, productAttribute, min, max)
VALUES ('test name ean', 'test description for long filter', 'ean', 2, 100);

INSERT INTO SortingRangeTimeView (name, description, productAttribute, min, max)
VALUES ('test name time', 'test description for time filter', 'expirationDate', '2018-11-30 16:35:24.00',
        '2022-11-30 16:35:24.00');

INSERT INTO SortingRangeDoubleView (name, description, productAttribute, min, max)
VALUES ('double numero 4', 'test description for double quattro', 'clockspeed', 1, 14);

INSERT INTO SortingRangeLongView (name, description, productAttribute, min, max)
VALUES ('test name ean funf', 'five test description for long filter', 'longnumber', 1, 10);

INSERT INTO SortingRangeTimeView (name, description, productAttribute, min, max)
VALUES ('test name time six', 'sechs test description for time filter', 'publishedDate', '2011-11-30 19:35:24.00',
        '2019-11-30 19:35:24.00');

INSERT INTO SortingRangeTimeView (name, description, productAttribute, min, max)
VALUES ('test name time sieben', 'seven test description for time filter', 'someDate', '2020-11-30 19:35:24.00',
        '2030-11-30 19:35:24.00');

INSERT INTO SortingRangeLongView (name, description, productAttribute, min, max)
VALUES ('test name ean acht', 'eight test description for long filter', 'longattribute', 200, 100000);

INSERT INTO SortingRangeDoubleView (name, description, productAttribute, min, max)
VALUES ('test double ix', 'test description nine', 'weight', 2, 100);

-- From here on out feel free to fuck it up chief
INSERT INTO SortingRangeDoubleView (name, description,productAttribute,min,max)
VALUES ('test delete double','test description','price',3,100);
