CREATE TABLE Requirements_fieldnames(
    id SERIAL PRIMARY KEY,
    fieldname varchar(100) UNIQUE NOT NULL
);

CREATE TABLE Requirements_values(
    id SERIAL PRIMARY KEY,
    value varchar(100) NOT NULL,
    fieldname_id int references Requirements_fieldnames(id)
);

CREATE TABLE Categories(
    id SERIAL PRIMARY KEY,
    parent_id int references Categories(id),
    name varchar(100) NOT NULL UNIQUE,
    description text NOT NULL,
    requirements_id int references Requirements_values(id) NOT NULL
);

INSERT INTO Requirements_fieldnames (fieldname) VALUES ('name'), ('category'), ('inStock');