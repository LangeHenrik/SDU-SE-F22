DROP TABLE IF EXISTS Config;

CREATE TABLE Config(
    id Integer not null,
    brandIndexInterval INTEGER NOT NULL
);

insert into config(id,brandindexinterval) values (60,100);