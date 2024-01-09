CREATE TABLE desert
(
    desert_id   SERIAL          NOT NULL,
    name            VARCHAR(32)     NOT NULL,
    composition     TEXT            NOT NULL,
    price           NUMERIC(6,2)    NOT NULL,
    quantity        INT             NOT NULL,
    menu_id         INT             NOT NULL,
    PRIMARY KEY (desert_id),
    CONSTRAINT fk_desert_menu
            FOREIGN KEY (menu_id)
                REFERENCES menu (menu_id)
);