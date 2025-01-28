CREATE TABLE Station
(
    id                       uuid PRIMARY KEY,
    tiploc                   varchar(20) NOT NULL,
    scheduled_departure_time time,
    scheduled_arrival_time   time
);


CREATE TABLE JOURNEY
(
    train_id             varchar(255) NOT NULL,
    scheduled_start_date date         NOT NULL,
    origin               uuid,
    FOREIGN KEY (origin) REFERENCES Station (id),
    destination          uuid,
    FOREIGN KEY (destination) REFERENCES Station (id)
);
