CREATE TABLE sorting_queries (
	id SERIAL PRIMARY KEY,
	text VARCHAR(512) NOT NULL,
	page INT NOT NULL DEFAULT 1,
	page_size INT NOT NULL DEFAULT 25,
	scoring INT NOT NULL DEFAULT 0,
	date_stamp TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE sorting_query_ranges (
	query_id INT REFERENCES sorting_queries(id),
	range_id VARCHAR(255) NOT NULL,
	start_value DECIMAL NOT NULL,
	end_value DECIMAL NOT NULL,
	PRIMARY KEY (query_id, range_id)
);

CREATE TABLE sorting_query_categories (
	query_id INT REFERENCES sorting_queries(id),
	catergory_id INT NOT NULL,
	PRIMARY KEY (query_id, catergory_id)
);