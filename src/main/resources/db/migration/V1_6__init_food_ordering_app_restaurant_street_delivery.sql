CREATE TABLE restaurant_street_delivery
(
    restaurant_street_delivery_id   SERIAL  NOT NULL,
    street_delivery_id              INT     NOT NULL,
    PRIMARY KEY (restaurant_street_delivery_id),
    CONSTRAINT fk_restaurant_street_delivery_street_delivery
        FOREIGN KEY (street_delivery_id)
            REFERENCES street_delivery (street_delivery_id)
);