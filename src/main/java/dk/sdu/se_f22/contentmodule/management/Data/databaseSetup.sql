CREATE TABLE Content_Log {
    id SERIAL PRIMARY KEY,
    html VARCHAR NOT NULL,
    modified TIMESTAMP NOT NULL
    };

CREATE TABLE Change_Log {
    content_id integer NOT NULL REFERENCES Content_Log (id),
    employee_id integer NOT NULL REFERENCES Employee_Table (id)
    };

CREATE TABLE Employee_Table {
    id SERIAL PRIMARY KEY,
    employee_name VARCHAR NOT NULL
    };