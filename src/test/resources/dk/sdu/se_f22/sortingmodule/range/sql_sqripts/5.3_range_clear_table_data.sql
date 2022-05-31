-- noinspection SqlWithoutWhereForFile

DELETE from SortingRangeTimeFilters;
DELETE from SortingRangeDoubleFilters;
DELETE from SortingRangeLongFilters;
DELETE from SortingRangeFilters;

ALTER SEQUENCE SortingRangeFilters_filterId_seq RESTART