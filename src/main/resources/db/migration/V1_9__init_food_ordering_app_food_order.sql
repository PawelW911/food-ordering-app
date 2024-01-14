CREATE TABLE food_order
(
    food_order_id            SERIAL                      NOT NULL,
    food_order_number        VARCHAR(64)                 NOT NULL,
    received_date_time  TIMESTAMP WITH TIME ZONE    NOT NULL,
    completed_date_time TIMESTAMP WITH TIME ZONE,
    sum_cost            NUMERIC(7,2)                NOT NULL,
    restaurant_id       INT                         NOT NULL,
    customer_id         INT                         NOT NULL,
    PRIMARY KEY (food_order_id),
    UNIQUE (food_order_number),
    CONSTRAINT fk_food_order_restaurant
        FOREIGN KEY (restaurant_id)
            REFERENCES restaurant (restaurant_id),
    CONSTRAINT fk_food_order_customer
        FOREIGN KEY (customer_id)
            REFERENCES customer (customer_id)
);