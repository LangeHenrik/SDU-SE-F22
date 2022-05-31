CREATE TABLE searches (
    productId VARCHAR references product(productID),
    creationDate TIMESTAMP not null
);