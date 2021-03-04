CREATE DATABASE shop;

\c shop

CREATE TABLE cart (
     id     integer,
     name   varchar(128),
     price  integer
);