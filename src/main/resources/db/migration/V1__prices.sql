CREATE TABLE prices
(
    BRAND_ID   int            not null,
    START_DATE timestamp      not null,
    END_DATE   timestamp      not null,
    PRICE_LIST int            not null,
    PRODUCT_ID int            not null,
    PRIORITY   int            not null,
    PRICE      decimal(19, 2) not null,
    CURR       varchar(3)     not null
);

INSERT INTO prices
VALUES (1, '2020-06-14 00.00.00', '2020-12-31 23.59.59', 1, 35455, 0, 35.50, 'EUR'),
       (1, '2020-06-14 15.00.00', '2020-06-14 18.30.00', 2, 35455, 1, 25.45, 'EUR'),
       (1, '2020-06-15 00.00.00', '2020-06-15 11.00.00', 3, 35455, 1, 30.50, 'EUR'),
       (1, '2020-06-15 16.00.00', '2020-12-31 23.59.59', 4, 35455, 1, 38.95, 'EUR');
