CREATE TABLE menu
(
    menu_id         SERIAL          NOT NULL,
    name            VARCHAR(64),
    description     TEXT,
    restaurant_id   INT             NOT NULL,
    PRIMARY KEY (menu_id),
    CONSTRAINT fk_menu_restaurant
            FOREIGN KEY (restaurant_id)
                REFERENCES restaurant (restaurant_id)
);