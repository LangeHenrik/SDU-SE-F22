DROP FUNCTION IF EXISTS get_type_of_filter;
DROP function if exists get_long_filter;
DROP function if exists get_time_filter;
DROP function if exists get_double_filter;

DROP TRIGGER IF EXISTS trg_double_view_insert_trigger ON SortingRangeDoubleView;
DROP TRIGGER IF EXISTS trg_time_view_insert_trigger ON SortingRangeTimeView;
DROP TRIGGER IF EXISTS trg_long_view_insert_trigger ON sortingrangelongview;
DROP FUNCTION IF EXISTS double_filter_view_insert;
DROP FUNCTION IF EXISTS time_filter_view_insert;
DROP FUNCTION IF EXISTS long_filter_view_insert;


DROP VIEW IF EXISTS SortingRangeDoubleView;
DROP VIEW IF EXISTS SortingRangeTimeView;
DROP VIEW IF EXISTS SortingRangeLongView;

DROP TABLE IF EXISTS SortingRangeTimeFilters;
DROP TABLE IF EXISTS SortingRangeDoubleFilters;
DROP TABLE IF EXISTS SortingRangeLongFilters;
DROP TABLE IF EXISTS SortingRangeFilters;
DROP TABLE IF EXISTS SortingRangeFilterTypes;


DROP DOMAIN IF EXISTS SortingRangeAttributeDomain;


create table SortingRangeFilterTypes
(
    filterTypeId   serial primary key,
    filterTypeName varchar unique not null
);

-- Inserts the possible filter types we have
INSERT INTO SortingRangeFilterTypes (filterTypeName)
Values ('Time'),
       ('Long'),
       ('Double');

create domain SortingRangeAttributeDomain AS varchar;

create table SortingRangeFilters
(
    filterId         serial primary key,
    description      varchar                     not null,
    name             varchar(40) unique          not null,
    productAttribute SortingRangeAttributeDomain not null,
    filterTypeId     int                         not null references SortingRangeFilterTypes (filterTypeId)
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
SELECT filterId, description, name, productAttribute, min, max, filterTypeName as filterType
from SortingRangeFilters
         inner join SortingRangeTimeFilters using (FilterId)
         inner join SortingRangeFilterTypes using (filterTypeId);

create VIEW SortingRangeDoubleView as
SELECT filterId, description, name, productAttribute, min, max, filterTypeName as filterType
from SortingRangeFilters
         inner join SortingRangeDoubleFilters using (FilterId)
         inner join SortingRangeFilterTypes using (filterTypeId);

create VIEW SortingRangeLongView as
SELECT filterId, description, name, productAttribute, min, max, filterTypeName as filterType
from SortingRangeFilters
         inner join SortingRangeLongFilters using (FilterId)
         inner join SortingRangeFilterTypes using (filterTypeId);


select *
from SortingRangeDoubleView;

select *
from SortingRangeLongView;

-- allowing view inserts
-- For time filters view
create or replace function time_filter_view_insert()
    RETURNS trigger
AS
$$
DECLARE
    filter_id_var integer;
BEGIN
    INSERT INTO SortingRangeFilters (description, name, productAttribute, filterTypeId)
    VALUES (NEW.description, NEW.name, NEW.productAttribute, 1)
    RETURNING filterId into filter_id_var;

    INSERT INTO SortingRangeTimeFilters (filterId, min, max) VALUES (filter_id_var, NEW.min, NEW.max);

    return new;
END;
$$
    language plpgsql;


create trigger trg_time_view_insert_trigger
    INSTEAD OF INSERT
    ON SortingRangeTimeView
    FOR EACH ROW
EXECUTE procedure time_filter_view_insert();

-- For double filter view
create or replace function double_filter_view_insert()
    RETURNS trigger
AS
$$
DECLARE
    filter_id_var integer;
BEGIN
    INSERT INTO SortingRangeFilters (description, name, productAttribute, filterTypeId)
    VALUES (NEW.description, NEW.name, NEW.productAttribute, 3)
    RETURNING filterId into filter_id_var;

    INSERT INTO SortingRangeDoubleFilters (filterId, min, max) VALUES (filter_id_var, NEW.min, NEW.max);

    return new;
END;
$$
    language plpgsql;

create trigger trg_double_view_insert_trigger
    INSTEAD OF INSERT
    ON SortingRangeDoubleView
    FOR EACH ROW
EXECUTE procedure double_filter_view_insert();


-- For double filter view
create or replace function long_filter_view_insert()
    RETURNS trigger
AS
$$
DECLARE
    filter_id_var integer;
BEGIN
    INSERT INTO SortingRangeFilters (description, name, productAttribute, filterTypeId)
        -- The 2 in this line could be handled differently
        -- We could create a trigger on inserts into each table containing different filter types instead
        -- I prefer this, since it does not need an extra trigger, and also allows fo not null constraint on filterTypeId
    VALUES (NEW.description, NEW.name, NEW.productAttribute, 2)
    RETURNING filterId into filter_id_var;

    INSERT INTO SortingRangeLongFilters (filterId, min, max) VALUES (filter_id_var, NEW.min, NEW.max);

    return new;
END;
$$
    language plpgsql;

create trigger trg_long_view_insert_trigger
    INSTEAD OF INSERT
    ON SortingRangeLongView
    FOR EACH ROW
EXECUTE procedure long_filter_view_insert();



-- the below insert is necessary to have as the first inserts in order to pass our unit-tests
/*
INSERT INTO SortingRangeFilters (description, name, productAttribute)
VALUES ('test description', 'test name double', 'price');
INSERT INTO SortingRangeDoubleFilters (filterId, min, max)
VALUES (1, 0, 10);


INSERT INTO SortingRangeFilters (description, name, productAttribute)
VALUES ('test description', 'test name ean', 'ean');
INSERT INTO SortingRangeLongFilters (filterId, min, max)
VALUES (2, 2, 100);

INSERT INTO SortingRangeFilters (description, name, productAttribute)
VALUES ('test description', 'test name time', 'expirationDate');
-- INSERT INTO SortingRangeTimeFilters (filterId, min, max) VALUES (3, 0, 10);
*/

select *
from SortingRangeDoubleView;

select *
from SortingRangeLongView;


create or replace function get_type_of_filter(filter_id_input integer)
    returns varchar
as
$$
DECLARE
    filter_type_var         varchar;
    DECLARE filter_type_int integer;
BEGIN
    SELECT (filterTypeId) from SortingRangeFilters where filter_id_input = filterId into filter_type_int;
    SELECT (filterTypeName) from SortingRangeFilterTypes where filterTypeId = filter_type_int into filter_type_var;
    return filter_type_var;

end;
$$
    language plpgsql;



create or replace function get_double_filter(filter_id_input integer)
    returns SortingRangeDoubleView
as
$$
    DECLARE result record;
BEGIN
    SELECT * from SortingRangeDoubleView where filter_id_input = SortingRangeDoubleView.filterId into result;
    RETURN result;

end;
$$
    language plpgsql;



create or replace function get_long_filter(filter_id_input integer)
    returns SortingRangeLongView
as
$$
DECLARE result record;
BEGIN
    SELECT * from SortingRangeLongView where filter_id_input = SortingRangeLongView.filterId into result;
    RETURN result;

end;
$$
    language plpgsql;


create or replace function get_time_filter(filter_id_input integer)
    returns SortingRangeTimeView
as
$$
DECLARE result record;
BEGIN
    SELECT * from SortingRangeTimeView where filter_id_input = SortingRangeTimeView.filterId into result;
    RETURN result;

end;
$$
    language plpgsql;

-- Inserting directly to the views
INSERT INTO SortingRangeDoubleView (name, description, productAttribute, min, max)
VALUES ('test name double', 'test description', 'price', 0, 10);

INSERT INTO SortingRangeLongView (name, description, productAttribute, min, max)
VALUES ('test name ean', 'test description', 'ean', 2, 100);

INSERT INTO SortingRangeTimeView (name, description, productAttribute, min, max)
VALUES ('test name time', 'test description', 'expirationDate', '2018-10-18 05:30:57', '2019-10-18 05:30:57');

SELECT get_type_of_filter(2);
SELECT get_type_of_filter(1);
SELECT * FROM get_double_filter(1);


