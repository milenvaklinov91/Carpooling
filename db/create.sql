CREATE TABLE Users (
    user_id INT PRIMARY KEY,
    username VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL,
    first_name VARCHAR(45) NOT NULL ,
    last_name VARCHAR(45) NOT NULL,
    profile_picture VARCHAR(255),
    registration_date DATETIME
);


CREATE TABLE Drivers (
    driver_id INT PRIMARY KEY,
    user_id INT UNIQUE,
    driver_license_picture_url VARCHAR(255),
    car_description VARCHAR (2555),
    car_model VARCHAR(100),
    car_plate_number VARCHAR(20),
    FOREIGN KEY (user_id) REFERENCES Users(user_id)
);


CREATE TABLE Passengers (
    passenger_id INT PRIMARY KEY,
    user_id INT UNIQUE,
    FOREIGN KEY (user_id) REFERENCES Users(user_id)
);


CREATE TABLE Travels (
    travel_id INT PRIMARY KEY,
    driver_id INT,
    start_location VARCHAR(255) NOT NULL,
    end_location VARCHAR(255) NOT NULL,
    departure_datetime DATETIME NOT NULL,
    cost_per_person VARCHAR(45) NOT NULL ,
    available_seats INT NOT NULL,
    description VARCHAR(2222),
    FOREIGN KEY (driver_id) REFERENCES Drivers(driver_id)
);


CREATE TABLE RideRequests (
    request_id INT PRIMARY KEY,
    travel_id INT,
    passenger_id INT,
    request_status VARCHAR(20) NOT NULL,
    FOREIGN KEY (travel_id) REFERENCES Travels(travel_id) ,
    FOREIGN KEY (passenger_id) REFERENCES Passengers(passenger_id)
);


CREATE TABLE Ratings (
                           rating_id INT PRIMARY KEY,
                           rated_user_id INT,
                           rated_by_user_id INT,
                           ride_id INT,
                           rating_value INT NOT NULL,
                           comment VARCHAR (2555) NOT NULL ,
                           FOREIGN KEY (rated_user_id) REFERENCES Users(user_id),
                           FOREIGN KEY (rated_by_user_id) REFERENCES Users(user_id),
                           FOREIGN KEY (ride_id) REFERENCES Travels(travel_id)
);

create table Cars
(
    car_id          int           not null
        primary key,
    user_id         int           not null,
    car_brand       varchar(45)   not null,
    car_model       varchar(45)   not null,
    car_color       varchar(45)   not null,
    car_year        int           not null,
    description     varchar(1222) not null,
    extra_storage   tinyint(1)    not null,
    smoke           tinyint(1)    not null,
    air_conditioner tinyint(1)    not null,
    pet_available   tinyint(1)    not null,
    consume_food    tinyint(1)    not null,
    consume_drink   tinyint(1)    not null,
    car_capacity    int           not null,
    constraint cars_users_user_id_pk
        foreign key (user_id) references Users (user_id)
);