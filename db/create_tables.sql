CREATE DATABASE shop;

\c shop

CREATE TABLE products (
     name           varchar(128) PRIMARY KEY,
     description    varchar(512),
     image          varchar(1024)
);

CREATE TABLE cart (
     name   varchar(128),
     price  integer
);