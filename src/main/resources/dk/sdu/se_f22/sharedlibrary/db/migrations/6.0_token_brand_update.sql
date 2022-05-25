DROP TABLE IF EXISTS BrandProductTypeJunction;
DROP TABLE IF EXISTS ProductType;

DROP TABLE IF EXISTS TokenBrandMap;

CREATE TABLE TokenBrandMap (
    tokenId INTEGER NOT NULL REFERENCES tokens (id),
    brandId INTEGER NOT NULL REFERENCES brand (id),
    PRIMARY KEY(tokenId, brandId)
);