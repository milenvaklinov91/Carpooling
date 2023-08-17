insert into users(username, password, email, first_name, last_name, profile_picture, registration_date, phone_number, is_driver, is_blocked, is_admin)
values
    ('ledka', 'ledak123', 'ledka@gmail.com', 'Leda', 'Yovkova', 'url:pic', '2023-08-10 22:24:24', '031312312', 0, 0, 1),
    ('milen', 'milen1991', 'milen@gmail.com', 'Milen', 'Vaklinov', 'url:pic', '2023-08-09 10:11:22', '0888123456', 1, 0, 0),
    ('kalata', 'kaloyan91', 'kaloyan@gmail.com', 'Kaloyan', 'Stanev', 'url:pic', '2023-08-08 15:30:45', '0777123456', 0, 0, 0),
    ('petra', 'petra92', 'petra@gmail.com', 'Petra', 'Kovacheva', 'url:pic', '2023-08-07 18:12:59', '0899112233', 1, 0, 0),
    ('gosho', 'gosho93', 'gosho@gmail.com', 'Georgi', 'Marinov', 'url:pic', '2023-08-06 09:30:15', '0555123456', 0, 1, 0);


insert into cars(user_id, driver_license_picture, car_brand, car_model, car_color, car_year, description, extra_storage, smoke, air_conditioner, pet_available, consume_food, consume_drink, car_capacity)
values
    (1, 'url:license1', 'Toyota', 'Camry', 'Blue', 2019, 'Comfortable car', 1, 0, 1, 1, 0, 1, 4),
    (2, 'url:license2', 'Ford', 'Mustang', 'Red', 2022, 'Sporty car', 0, 1, 0, 0, 1, 0, 2),
    (1, 'url:license1', 'Honda', 'Civic', 'Silver', 2020, 'Compact car', 0, 0, 1, 1, 0, 1, 5),
    (2, 'url:license3', 'Chevrolet', 'Malibu', 'Black', 2021, 'Family car', 1, 0, 1, 0, 1, 0, 7),
    (3, 'url:license2', 'BMW', 'X5', 'White', 2018, 'SUV', 1, 0, 1, 1, 0, 0, 5);


insert into trips(user_id, start_location, end_location, departure_datetime, cost_per_person, available_seats, description,status)
values
    (1, 'Sofia', 'Varna', '2023-08-15 08:00:00', '20', 3, 'Morning commute', 1),
    (2, 'Plovdiv', 'Burgas', '2023-08-16 15:30:00', '15', 2, 'Afternoon ride', 2),
    (3, 'Pleven', 'Sofia', '2023-08-17 11:00:00', '30', 4, 'Weekend getaway', 0),
    (1, 'Blagoevgrad', 'Dupnica', '2023-08-18 09:15:00', '25', 1, 'Quick business trip', 1),
    (2, 'Plovdiv', 'Haskovo', '2023-08-19 17:45:00', '10', 3, 'Evening hangout', 2);


insert into feedbacks(rated_user_id, rated_by_user_id, trip_id, rating_value)
values
    (2, 1, 1, 4),
    (1, 2, 2, 5),
    (3, 1, 3, 4),
    (1, 3, 4, 3),
    (2, 3, 5, 5);


insert into feedback_comments(comment, feedback_id)
values
    ('Great experience!', 1),
    ('Friendly driver', 2),
    ('Smooth ride', 3),
    ('Could be better', 4),
    ('Excellent trip', 5);


insert into trip_requests(trip_id, user_id, status)
values
    (1, 2, 0),
    (2, 3, 1),
    (3, 1, 2),
    (4, 4, 2),
    (5, 2, 1);
