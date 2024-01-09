CREATE TABLE restaurant
(
    restaurant_id                   SERIAL          NOT NULL,
    unique_code                     VARCHAR(32)     NOT NULL,
    name                            VARCHAR(32)     NOT NULL,
    type_food                       VARCHAR(32)     NOT NULL,
    email                           VARCHAR(32)     NOT NULL,
    phone                           VARCHAR(32)     NOT NULL,
    opening_hours                   VARCHAR(32)     NOT NULL,
    menu_id                         INT             NOT NULL,
    restaurant_street_delivery_id   INT             NOT NULL,
    address_id                      INT             NOT NULL,
    opinion_id                      INT,
    owner_id                        INT             NOT NULL,
    PRIMARY KEY (restaurant_id),
    UNIQUE (unique_code),
    CONSTRAINT fk_restaurant_menu
        FOREIGN KEY (menu_id)
            REFERENCES menu (menu_id),
    CONSTRAINT fk_restaurant_restaurant_street_delivery
        FOREIGN KEY (restaurant_street_delivery_id)
            REFERENCES restaurant_street_delivery (restaurant_street_delivery_id),
    CONSTRAINT fk_restaurant_address
        FOREIGN KEY (address_id)
            REFERENCES address (address_id),
    CONSTRAINT fk_restaurant_opinion
        FOREIGN KEY (opinion_id)
            REFERENCES opinion (opinion_id),
    CONSTRAINT fk_restaurant_owner
        FOREIGN KEY (owner_id)
            REFERENCES owner (owner_id)

);