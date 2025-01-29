CREATE TABLE route_point(
                                    train_id VARCHAR(255) NOT NULL,
                                    route_point_type VARCHAR(50) NOT NULL,
                                    position INT NOT NULL,
                                    tiploc VARCHAR(7) NOT NULL,
                                    activity_type VARCHAR(12),
                                    planned_activity_type VARCHAR(12),
                                    is_cancelled BOOLEAN NOT NULL DEFAULT FALSE,
                                    platform VARCHAR(3),
                                    planned_time_of_arrival TIME,
                                    planned_time_of_departure TIME,
                                    working_time_of_arrival TIME,
                                    working_time_of_departure TIME,
                                    false_destination VARCHAR(7),
                                    delay_minutes INT,
                                    working_time_of_passing TIME,
                                    PRIMARY KEY (train_id, route_point_type, position)
);

CREATE TABLE journey
(
    train_id             varchar(255) NOT NULL PRIMARY KEY ,
    scheduled_start_date date         NOT NULL
);
