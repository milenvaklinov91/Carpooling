CREATE TABLE Users (
    user_id INT PRIMARY KEY,
    username VARCHAR(255) NOT NULL,
    password_hash VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL,
    profile_picture_url VARCHAR(255),
    registration_date DATETIME
);


CREATE TABLE Drivers (
    driver_id INT PRIMARY KEY,
    user_id INT UNIQUE,
    driver_license_picture_url VARCHAR(255),
    car_description TEXT,
    car_model VARCHAR(100),
    car_plate_number VARCHAR(20),
    FOREIGN KEY (user_id) REFERENCES Users(user_id)
);


CREATE TABLE Passengers (
    passenger_id INT PRIMARY KEY,
    user_id INT UNIQUE,
    FOREIGN KEY (user_id) REFERENCES Users(user_id)
);


CREATE TABLE Rides (
    ride_id INT PRIMARY KEY,
    driver_id INT,
    departure_location VARCHAR(255) NOT NULL,
    destination_location VARCHAR(255) NOT NULL,
    departure_datetime DATETIME NOT NULL,
    available_seats INT NOT NULL,
    description TEXT,
    FOREIGN KEY (driver_id) REFERENCES Drivers(driver_id)
);


CREATE TABLE RideRequests (
    request_id INT PRIMARY KEY,
    ride_id INT,
    passenger_id INT,
    request_status VARCHAR(20) NOT NULL,
    FOREIGN KEY (ride_id) REFERENCES Rides(ride_id),
    FOREIGN KEY (passenger_id) REFERENCES Passengers(passenger_id)
);


CREATE TABLE Ratings (
    rating_id INT PRIMARY KEY,
    rated_user_id INT,
    rated_by_user_id INT,
    ride_id INT,
    rating_value INT NOT NULL,
    comment TEXT,
    FOREIGN KEY (rated_user_id) REFERENCES Users(user_id),
    FOREIGN KEY (rated_by_user_id) REFERENCES Users(user_id),
    FOREIGN KEY (ride_id) REFERENCES Rides(ride_id)
);