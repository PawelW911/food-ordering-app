CREATE TABLE soup
(
    soup_id   SERIAL          NOT NULL,
    name            VARCHAR(32)     NOT NULL,
    composition     TEXT            NOT NULL,
    price           NUMERIC(6,2)    NOT NULL,
    quantity        INT             NOT NULL,
    menu_id         INT,
    food_order_id   INT,
    PRIMARY KEY (soup_id),
    CONSTRAINT fk_soup_menu
            FOREIGN KEY (menu_id)
                REFERENCES menu (menu_id),
    CONSTRAINT fk_soup_food_order
            FOREIGN KEY (food_order_id)
                REFERENCES food_order (food_order_id)

);