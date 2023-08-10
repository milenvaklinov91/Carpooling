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

CREATE TABLE Cars (
    car_id INT PRIMARY KEY ,
    driver_id INT NOT NULL ,
    car_brand VARCHAR (45) NOT NULL ,
    car_model VARCHAR (45) NOT NULL ,
    color VARCHAR (45) NOT NULL ,
    car_year INT NOT NULL ,
    description VARCHAR (1222) NOT NULL ,
    FOREIGN KEY (driver_id) REFERENCES Drivers(driver_id)
);