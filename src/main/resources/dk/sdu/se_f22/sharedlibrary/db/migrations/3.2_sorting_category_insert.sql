BEGIN;
INSERT INTO Requirements_fieldnames (fieldname) VALUES ('name'), ('category'), ('inStock');


INSERT INTO requirements_values (value, fieldname_id) VALUES ('pc', 2);
INSERT INTO categories (requirements_id, name, description)
VALUES ((SELECT id FROM requirements_values WHERE value = 'pc'),
        'PC', 'This category contains PCs');

INSERT INTO requirements_values (value, fieldname_id) VALUES ('monitors', 2);
INSERT INTO categories (requirements_id, name, description)
VALUES ((SELECT id FROM requirements_values WHERE value = 'monitors'),
        'Monitors', 'This category contains monitors');

INSERT INTO requirements_values (value, fieldname_id) VALUES ('components', 2);
INSERT INTO categories (requirements_id, name, description)
VALUES ((SELECT id FROM requirements_values WHERE value = 'components'),
        'Components', 'This category contains components');

INSERT INTO requirements_values (value, fieldname_id) VALUES ('laptops', 2);
INSERT INTO categories (requirements_id, name, description, parent_id)
VALUES ((SELECT id FROM requirements_values WHERE value = 'laptops'),
        'Laptops', 'This category contains laptops',
        (SELECT id FROM categories WHERE name = 'PC'));

INSERT INTO requirements_values (value, fieldname_id) VALUES ('desktops', 2);
INSERT INTO categories (requirements_id, name, description, parent_id)
VALUES ((SELECT id FROM requirements_values WHERE value = 'desktops'),
        'Desktops', 'This category contains desktops',
        (SELECT id FROM categories WHERE name = 'PC'));

INSERT INTO requirements_values (value, fieldname_id) VALUES ('storage', 2);
INSERT INTO categories (requirements_id, name, description, parent_id)
VALUES ((SELECT id FROM requirements_values WHERE value = 'storage'),
        'Storage', 'This category contains storages',
        (SELECT id FROM categories WHERE name = 'Components'));

INSERT INTO requirements_values (value, fieldname_id) VALUES ('processors', 2);
INSERT INTO categories (requirements_id, name, description, parent_id)
VALUES ((SELECT id FROM requirements_values WHERE value = 'processors'),
        'Processors', 'This category contains processors',
        (SELECT id FROM categories WHERE name = 'Components'));

INSERT INTO requirements_values (value, fieldname_id) VALUES ('harddrives', 2);
INSERT INTO categories (requirements_id, name, description, parent_id)
VALUES ((SELECT id FROM requirements_values WHERE value = 'harddrives'),
        'Harddrives', 'This category contains harddrives',
        (SELECT id FROM categories WHERE name = 'Storage'));

INSERT INTO requirements_values (value, fieldname_id) VALUES ('ssds', 2);
INSERT INTO categories (requirements_id, name, description, parent_id)
VALUES ((SELECT id FROM requirements_values WHERE value = 'ssds'),
        'SSDs', 'This category contains SSDs',
        (SELECT id FROM categories WHERE name = 'Storage'));
COMMIT;