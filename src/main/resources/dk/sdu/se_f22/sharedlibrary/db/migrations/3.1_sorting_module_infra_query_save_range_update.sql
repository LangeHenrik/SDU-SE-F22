DROP TABLE IF EXISTS sorting_query_ranges;

CREATE TABLE sorting_query_ranges (
	query_id INT REFERENCES sorting_queries(id),
	range_id INTEGER NOT NULL,
	start_value VARCHAR(255) NOT NULL,
	end_value VARCHAR(255) NOT NULL,
	PRIMARY KEY (query_id, range_id)
);