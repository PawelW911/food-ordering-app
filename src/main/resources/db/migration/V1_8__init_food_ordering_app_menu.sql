CREATE TABLE menu
(
    menu_id         SERIAL          NOT NULL,
    name            VARCHAR(64)     NOT NULL,
    description     TEXT            NOT NULL,
    restaurant_id   INT             NOT NULL,
    PRIMARY KEY (menu_id),
    CONSTRAINT fk_menu_restaurant
            FOREIGN KEY (restaurant_id)
                REFERENCES restaurant (restaurant_id)
);