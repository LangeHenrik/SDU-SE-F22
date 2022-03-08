
CREATE TABLE queries (
	id SERIAL PRIMARY KEY,
	text VARCHAR(512) NOT NULL,
	date_stamp TIMESTAMP NOT NULL

);


CREATE TABLE queries_settings (
	queries_id INT REFERENCES queries(id),
	key VARCHAR(255) NOT NULL,
	value VARCHAR(255) NOT NULL,
	PRIMARY KEY (queries_id, key)

);

CREATE TABLE queries_category(
	queries_id INT REFERENCES queries(id),
	catergory_id INT NOT NULL,
	PRIMARY KEY (queries_id, catergory_id)
);
