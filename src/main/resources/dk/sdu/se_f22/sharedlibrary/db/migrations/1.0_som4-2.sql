CREATE TABLE scores(
    id serial PRIMARY KEY,
    type varchar(6),
    bracket DOUBLE PRECISION,
    weight int
    );

INSERT INTO scores (type, bracket, weight) VALUES
    ('price',1000,1),
    ('price',2000,2),
    ('price',3000,4),
    ('price',4000,5),
    ('price',5000,6),
    ('review',2.5,1),
    ('review',3.3,2),
    ('review',3.9,3),
    ('review',4.3,4),
    ('review',4.7,5),
    ('stock',0,1),
    ('stock',3,2),
    ('stock',10,3),
    ('stock',20,4),
    ('stock',50,5),
    ('date',1,1),
    ('date',2,2),
    ('date',3,3),
    ('date',4,4),
    ('date',5,5);