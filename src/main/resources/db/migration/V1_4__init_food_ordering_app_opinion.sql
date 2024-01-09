CREATE TABLE opinion
(
    opinion_id      SERIAL                              NOT NULL,
    text            TEXT                                NOT NULL,
    stars           INT CHECK (stars BETWEEN 1 AND 5)   NOT NULL,
    date_time       TIMESTAMP WITH TIME ZONE            NOT NULL,
    customer_id     INT                                 NOT NULL,
    PRIMARY KEY (opinion_id),
    CONSTRAINT fk_opinion_customer
        FOREIGN KEY (customer_id)
            REFERENCES customer (customer_id)
);