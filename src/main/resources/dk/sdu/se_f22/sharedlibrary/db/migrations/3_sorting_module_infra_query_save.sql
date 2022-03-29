CREATE TABLE sorting_queries (
	id SERIAL PRIMARY KEY,
	text VARCHAR(512) NOT NULL,
	date_stamp TIMESTAMP NOT NULL
);

CREATE TABLE sorting_query_settings (
	queries_id INT REFERENCES sorting_queries(id),
	key VARCHAR(255) NOT NULL,
	value VARCHAR(255) NOT NULL,
	PRIMARY KEY (queries_id, key)
);

CREATE TABLE sorting_query_category(
	queries_id INT REFERENCES sorting_queries(id),
	catergory_id INT NOT NULL,
	PRIMARY KEY (queries_id, catergory_id)
);