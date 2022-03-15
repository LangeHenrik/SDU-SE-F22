DROP VIEW IF EXISTS SortingRangeDoubleView;
DROP VIEW IF EXISTS SortingRangeTimeView;
DROP VIEW IF EXISTS SortingRangeDoubleView;

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

