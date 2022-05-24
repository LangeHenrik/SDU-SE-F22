CREATE TABLE cms_tokenparameters (
    id SERIAL PRIMARY KEY,
    limitedchar VARCHAR(250) UNIQUE NOT NULL
);

CREATE TABLE cms_parameterslist (
	parameter_id SERIAL PRIMARY KEY,
    parameter VARCHAR(250) NOT NULL UNIQUE
);

CREATE TABLE cms_htmlpages (
    page_id SERIAL PRIMARY KEY,
    html_id INT NOT NULL UNIQUE,
    logged TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE cms_usedparameters (
    page_id INTEGER REFERENCES cms_htmlpages(page_id),
    parameter_id INTEGER REFERENCES cms_parameterslist(parameter_id),
    UNIQUE (page_id, parameter_id)
);

ALTER TABLE cms_usedparameters ADD UNIQUE index(page_id, parameter_id);

INSERT INTO cms_tokenparameters (id, limitedchar)
VALUES (1, '-'),
       (2, ','),
       (3, '_'),
       (4, ':'),
       (5, '.'),
       (6, ';'),
       (7, ' ');  


INSERT INTO cms_parameterslist (parameter_id, parameter)
VALUES (1, '-'),
       (2, ','),
       (3, '_'),
       (4, ':'),
       (5, '.'),
       (6, ';'),
       (7, ' ');  
