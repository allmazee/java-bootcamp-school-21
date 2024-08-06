CREATE TABLE products (
    id BIGINT GENERATED BY DEFAULT AS IDENTITY (START WITH 1),
    name  VARCHAR(128) NOT NULL,
    price INTEGER,

    PRIMARY KEY (id)
);