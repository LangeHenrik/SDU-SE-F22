CREATE TABLE Categories(
    categoryID serial PRIMARY KEY,
    category VARCHAR);

CREATE TABLE Stock(
    stockID serial PRIMARY KEY,
    city VARCHAR
);

CREATE TABLE Product(
    productID varchar PRIMARY KEY,
    name VARCHAR not null,
    averageuserreview FLOAT not null,
    publisheddate TIMESTAMP not null,
    expirationdate TIMESTAMP not null,
    price FLOAT not null,
    description VARCHAR not null,
    categoryID integer references Categories(categoryID),
    ean bigint not null,
    weight float
);

CREATE TABLE ProductStock(
    productID VARCHAR references Product(productID),
    stockID integer references Stock(stockID)
);

CREATE TABLE Specs(
    specID serial PRIMARY KEY,
    clockspeed float
);

CREATE TABLE Storage(
    storageID serial PRIMARY KEY,
    size varchar
);

CREATE TABLE ProductStorage(
    uniqueID serial PRIMARY KEY,
    productID VARCHAR references Product(productID),
    storageID integer references Storage(storageID)
);

CREATE TABLE ProductSpecs(
    uniqueID serial PRIMARY KEY,
    specID integer references Specs(specID),
    productID VARCHAR references Product(productID)
);

CREATE OR REPLACE FUNCTION insertproductstorage(id VARCHAR,newsize VARCHAR) returns void AS $$
BEGIN
	INSERT INTO storage
    (size)
	select newsize
		WHERE
    		NOT EXISTS (
       		SELECT size FROM storage WHERE size = newsize
    )
AND newsize <> '0';
IF newsize <>'0'
	THEN INSERT INTO productstorage(productid,storageid) 
	VALUES (id,(SELECT storageid FROM storage WHERE storage.size = newsize));

END IF;
END;
$$
LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION insertproductstock(city text[],productid VARCHAR)
  RETURNS void AS
$$
declare
i varchar;

-- function that creates cities in stock that do not exist, and makes relation between productid and the cities in table productstock
BEGIN
	FOREACH i in ARRAY city
	loop
		INSERT INTO stock(city) 
		select i
		WHERE
			NOT EXISTS(
				select stock.city from stock where stock.city=i);
		INSERT INTO productstock(productid,stockid)
		values(productid,(select stockid from stock where stock.city = i));
	end loop;
END;
$$
LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION insertproductspecs(id VARCHAR,newspecs float) returns void AS $$
BEGIN
	INSERT INTO specs (clockspeed)
	select newspecs
	WHERE
    	NOT EXISTS (
       		SELECT clockspeed FROM specs WHERE specs.clockspeed = newspecs
    )
	AND newspecs <> 0; 
	
	IF newspecs <>0
		THEN INSERT INTO productspecs(specid,productid) 
		VALUES ((SELECT specid FROM specs WHERE specs.clockspeed = newspecs),id);
	END IF;	
END; 
$$
LANGUAGE plpgsql;

-- insertion into product, PK(id) controls no duplicates
CREATE OR REPLACE PROCEDURE insertproduct (id VARCHAR,review float, instock VARCHAR ARRAY,ean 
								bigint,price float,published timestamp,expiration timestamp,
								newcategory VARCHAR,name VARCHAR,description VARCHAR,
								newsize VARCHAR,newclockspeed float, weight float)
LANGUAGE SQL
AS $$

-- no insertion if category already exists
INSERT INTO categories (category)
select newcategory
WHERE
    NOT EXISTS (
       SELECT category FROM categories WHERE category = newcategory
    ); 

INSERT INTO product(productid,name,averageuserreview,publisheddate,expirationdate,price,description,ean,weight,categoryid) 
VALUES(id,name,review,published,expiration,price,description,ean,weight,( SELECT categoryid FROM categories WHERE category=newcategory));

-- check if specs exists, if it doesn't we should not make this reference

-- check if storage exists, if it doesn't we should not make this reference

select insertproductspecs(id,newclockspeed);
select insertproductstock(instock,id);	
select insertproductstorage(id,newsize);

$$;




