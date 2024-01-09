ALTER TABLE restaurant_street_delivery
ADD COLUMN restaurant_id INT NOT NULL;


ALTER TABLE restaurant_street_delivery
ADD CONSTRAINT fk_restaurant_street_delivery_restaurant
        FOREIGN KEY (restaurant_id)
            REFERENCES restaurant (restaurant_id);
