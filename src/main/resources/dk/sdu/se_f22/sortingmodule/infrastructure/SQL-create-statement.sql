
CREATE TABLE querys (
	id SERIAL PRIMARY KEY,
	tekst VARCHAR(512) NOT NULL,
	date_stamp TIMESTAMP NOT NULL

);


CREATE TABLE querys_settings (
	querys_id INT REFERENCES querys(id),
	key VARCHAR(255) NOT NULL,
	value VARCHAR(255) NOT NULL,
	PRIMARY KEY (querys_id, key)

);

CREATE TABLE querys_category(
	querys_id INT REFERENCES querys(id),
	catergory_id INT NOT NULL,
	PRIMARY KEY (querys_id, catergory_id)
);
