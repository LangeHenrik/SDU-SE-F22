CREATE TABLE content_Log
(
    id         SERIAL PRIMARY KEY,
    html       VARCHAR   NOT NULL,
    article_nr INT       NOT NULL,
    modified   TIMESTAMP NOT NULL
);

CREATE TABLE employee_Table
(
    id            SERIAL PRIMARY KEY,
    employee_name VARCHAR NOT NULL
);

CREATE TABLE change_Log
(
    content_id  serial NOT NULL REFERENCES content_Log (id),
    employee_id integer NOT NULL REFERENCES employee_Table (id)
);

INSERT INTO employee_Table (ID, EMPLOYEE_NAME) VALUES (0, 'Holly')