DROP TABLE IF EXISTS person;


CREATE TABLE person (
    id SERIAL,
    name VARCHAR(240),
    street_name VARCHAR(240),
    number VARCHAR(240),
    city VARCHAR(240),
    country VARCHAR(240),
    email VARCHAR(240),
    phone_number VARCHAR(240),
    created_datetime TIMESTAMP
);