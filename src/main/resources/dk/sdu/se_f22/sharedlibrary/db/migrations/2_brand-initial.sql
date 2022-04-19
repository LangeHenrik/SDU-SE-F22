CREATE TABLE Brand(
    id           serial PRIMARY KEY,
    name         VARCHAR(255) UNIQUE NOT NULL,
    description  TEXT,
    founded      VARCHAR(255),
    headquarters VARCHAR(255)
);

CREATE TABLE ProductType(
    id   serial PRIMARY KEY,
    type VARCHAR(255) UNIQUE NOT NULL
);

CREATE TABLE Tokens(
    id serial PRIMARY KEY,
    token VARCHAR(255) NOT NULL
);

CREATE TABLE TokenBrandMap(
    id serial PRIMARY KEY,
    tokenId INTEGER NOT NULL REFERENCES Tokens (id),
    brandId INTEGER NOT NULL REFERENCES Brand (id)
);

CREATE TABLE BrandProductTypeJunction(
    brandId   INTEGER REFERENCES Brand (id),
    productId INTEGER REFERENCES ProductType (id)
);

CREATE TABLE Config(
    brandIndexInterval INTEGER NOT NULL
);