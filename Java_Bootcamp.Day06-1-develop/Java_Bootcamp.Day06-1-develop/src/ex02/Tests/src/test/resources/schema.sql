DROP TABLE IF EXISTS product

CREATE TABLE product (
        identifier INT PRIMARY KEY,
        name VARCHAR(255),
        price DECIMAL(10, 2)
);
