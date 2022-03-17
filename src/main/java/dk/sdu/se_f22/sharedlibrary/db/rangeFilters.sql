DROP VIEW IF EXISTS SortingRangeDoubleView;
DROP VIEW IF EXISTS SortingRangeTimeView;
DROP VIEW IF EXISTS SortingRangeLongView;

DROP TABLE IF EXISTS SortingRangeTimeFilters;
DROP TABLE IF EXISTS SortingRangeDoubleFilters;
DROP TABLE IF EXISTS SortingRangeLongFilters;
DROP TABLE IF EXISTS SortingRangeFilters;

DROP DOMAIN IF EXISTS SortingRangeAttributeDomain;

create domain SortingRangeAttributeDomain AS varchar;

create table SortingRangeFilters
(
    filterId         serial primary key,
    description      varchar                     not null,
    name             varchar(40) unique          not null,
    productAttribute SortingRangeAttributeDomain not null
);

-- timestamp contains both date and time, but without timezone
-- if timezone is necessary use timestampz
create table SortingRangeTimeFilters
(
    filterId int references SortingRangeFilters (filterId) primary key,
    min      timestamp,
    max      timestamp
);

-- float(8) is 8 bytes of precision which match a Java double
create table SortingRangeDoubleFilters
(
    filterId int references SortingRangeFilters (filterId) primary key,
    min      float(8),
    max      float(8)
);

-- bigint is 8 bytes, which match a Java long
create table SortingRangeLongFilters
(
    filterId int references SortingRangeFilters (filterId) primary key,
    min      bigint,
    max      bigint
);

create VIEW SortingRangeTimeView as
SELECT *
from SortingRangeFilters
         inner join SortingRangeTimeFilters using (FilterId);

create VIEW SortingRangeDoubleView as
SELECT *
from SortingRangeFilters
         inner join SortingRangeDoubleFilters using (FilterId);

create VIEW SortingRangeLongView as
SELECT *
from SortingRangeFilters
         inner join SortingRangeLongFilters using (FilterId);


select * from SortingRangeDoubleView;

select * from SortingRangeLongView;


-- the below insert is necessary to have first to pass our unit-tests
INSERT INTO SortingRangeFilters (description, name, productAttribute) VALUES ('test description', 'test name double', 'price');
INSERT INTO SortingRangeDoubleFilters (filterId, min, max) VALUES (1, 0, 10);


INSERT INTO SortingRangeFilters (description, name, productAttribute) VALUES ('test description', 'test name ean', 'ean');
INSERT INTO SortingRangeLongFilters (filterId, min, max) VALUES (2, 2, 100);

INSERT INTO SortingRangeFilters (description, name, productAttribute) VALUES ('test description', 'test name time', 'expirationDate');
-- INSERT INTO SortingRangeTimeFilters (filterId, min, max) VALUES (3, 0, 10);

select * from SortingRangeDoubleView;

select * from SortingRangeLongView;


