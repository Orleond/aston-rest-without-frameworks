CREATE TABLE brand
(
    brand_id SERIAL PRIMARY KEY,
    name VARCHAR(128) NOT NULL UNIQUE
);

CREATE TABLE model
(
    model_id SERIAL PRIMARY KEY,
    name     VARCHAR(128) NOT NULL UNIQUE
);

CREATE TABLE brand_model
(
    brand_id INT REFERENCES brand (brand_id),
    model_id INT REFERENCES model (model_id),
    PRIMARY KEY (brand_id, model_id)
);

CREATE TABLE type
(
    type_id SERIAL PRIMARY KEY,
    type    VARCHAR(128) NOT NULL UNIQUE
);

CREATE TABLE product
(
    product_id SERIAL PRIMARY KEY,
    type       INT NOT NULL REFERENCES type (type_id),
    brand_id   INT NOT NULL,
    model_id   INT NOT NULL,
    price      DECIMAL(8, 2),
    FOREIGN KEY (brand_id, model_id) REFERENCES brand_model (brand_id, model_id)
);

INSERT INTO type(type)
VALUES ('Миксер'),
       ('Блендер'),
       ('Кофеварка');

INSERT INTO brand(name)
VALUES ('Braun'),
       ('Kitfort'),
       ('Brayer'),
       ('Holt');

INSERT INTO model(name)
VALUES ('MultiQuick 5 Vario'),
       ('MultiQuick 7'),
       ('MultiQuick 3'),
       ('KT-1381'),
       ('KT-3021'),
       ('KT-3044-1'),
       ('KT-1343-3'),
       ('KT-3071-1'),
       ('BR1302'),
       ('BR1309'),
       ('BR1307'),
       ('BR1303'),
       ('KT-743'),
       ('KT-753'),
       ('HT-CM-007'),
       ('HT-CM-008');

INSERT INTO brand_model(brand_id, model_id)
VALUES (1, 1),
       (1, 2),
       (1, 3),
       (2, 4),
       (2, 5),
       (2, 6),
       (2, 7),
       (2, 8),
       (3, 9),
       (3, 10),
       (3, 11),
       (3, 12),
       (2, 13),
       (2, 14),
       (4, 15),
       (4, 16);

INSERT INTO product(type, brand_id, model_id, price)
VALUES (2, 1, 1, 249.00),
       (2, 1, 2, 354.55),
       (2, 1, 3, 259.00),
       (2, 2, 4, 139.00),
       (2, 2, 5, 123.00),
       (1, 2, 6, 283.00),
       (1, 2, 7, 325.89),
       (1, 2, 8, 56.00),
       (1, 3, 9, 89.00),
       (1, 3, 10, 98.00),
       (1, 3, 11, 69.00),
       (1, 3, 12, 84.83),
       (3, 2, 13, 528.00),
       (3, 2, 14, 249.00),
       (3, 4, 15, 49.00),
       (3, 4, 16, 299.00);