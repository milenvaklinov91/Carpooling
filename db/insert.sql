INSERT INTO carpooling.status (status_id, status) VALUES (1, 'pending');
INSERT INTO carpooling.status (status_id, status) VALUES (2, 'confirmed');
INSERT INTO carpooling.status (status_id, status) VALUES (3, 'deleted');

insert into users(username, password, email, first_name, last_name, profile_picture, registration_date, phone_number, is_driver, is_blocked, is_admin,conf_code, status)
values
    ('ledka', 'ledak123', 'ledka@gmail.com', 'Leda', 'Yovkova', '0e647a64-3cf3-44f6-901e-b33a4546db99_my_pic.jpg', '2023-08-10 22:24:24', '031312312', 1, 0, 1, 'dbbe4e58-8e2e-4423-888f-91ab94a3aa66',2),
    ('milen', 'milen1991', 'milen@gmail.com', 'Milen', 'Vaklinov', '1.jpg', '2023-08-09 10:11:22', '0888123456', 1, 0, 0, 'dbbe4e58-8e2e-4423-888f-91ab94a3aa66',2),
    ('kalata', 'kaloyan91', 'kaloyan@gmail.com', 'Kaloyan', 'Stanev', 'kaloyan.jpeg', '2023-08-08 15:30:45', '0777123456', 1, 0, 0, 'dbbe4e58-8e2e-4423-888f-91ab94a3aa66',2),
    ('petra', 'petra92', 'petra@gmail.com', 'Petra', 'Kovacheva', 'profile.jpg', '2023-08-07 18:12:59', '0899112233', 1, 0, 0, 'dbbe4e58-8e2e-4423-888f-91ab94a3aa66',2),
    ('gosho', 'gosho93', 'gosho@gmail.com', 'Georgi', 'Marinov', 'profile.jpg', '2023-08-06 09:30:15', '0555123456', 0, 1, 0, 'dbbe4e58-8e2e-4423-888f-91ab94a3aa66',2),
    ('anastasia', 'ana2000', 'anastasia@gmail.com', 'Anastasia', 'Ivanova', 'profile.jpg', '2023-08-11 14:55:30', '0876123456', 1, 0, 1, 'dbbe4e58-8e2e-4423-888f-91ab94a3aa66', 2),
    ('alex', 'alex85', 'alex@gmail.com', 'Alex', 'Stoyanov', 'profile.jpg', '2023-08-12 20:18:42', '0899123456', 0, 1, 0, 'dbbe4e58-8e2e-4423-888f-91ab94a3aa66', 2),
    ('emily', 'emily1990', 'emily@gmail.com', 'Emily', 'Georgieva', 'profile.jpg', '2023-08-13 12:45:11', '0886123456', 1, 0, 0, 'dbbe4e58-8e2e-4423-888f-91ab94a3aa66', 2),
    ('john', 'john88', 'john@gmail.com', 'John', 'Smith', 'profile.jpg', '2023-08-14 17:30:27', '0776123456', 0, 0, 1, 'dbbe4e58-8e2e-4423-888f-91ab94a3aa66', 2),
    ('susan', 'susan87', 'susan@gmail.com', 'Susan', 'Johnson', 'profile.jpg', '2023-08-15 08:20:10', '0556123456', 1, 0, 0, 'dbbe4e58-8e2e-4423-888f-91ab94a3aa66', 2),
    ('david', 'david94', 'david@gmail.com', 'David', 'Anderson', 'profile.jpg', '2023-08-16 19:55:30', '0887123456', 1, 0, 1, 'dbbe4e58-8e2e-4423-888f-91ab94a3aa66', 2),
    ('laura', 'laura89', 'laura@gmail.com', 'Laura', 'Miller', 'profile.jpg', '2023-08-17 15:18:42', '0777123456', 0, 1, 0, 'dbbe4e58-8e2e-4423-888f-91ab94a3aa66', 2),
    ('matthew', 'matthew86', 'matthew@gmail.com', 'Matthew', 'Williams', 'profile.jpg', '2023-08-18 11:45:11', '0898123456', 1, 0, 0, 'dbbe4e58-8e2e-4423-888f-91ab94a3aa66', 2),
    ('olivia', 'olivia95', 'olivia@gmail.com', 'Olivia', 'Johnson', 'profile.jpg', '2023-08-19 14:30:27', '0557123456', 0, 0, 1, 'dbbe4e58-8e2e-4423-888f-91ab94a3aa66', 2),
    ('james', 'james90', 'james@gmail.com', 'James', 'Davis', 'profile.jpg', '2023-08-20 08:20:10', '0776123456', 1, 0, 0, 'dbbe4e58-8e2e-4423-888f-91ab94a3aa66', 2);



insert into cars(user_id, driver_license_picture, car_brand, car_model, car_color, car_year, description, extra_storage, smoke, air_conditioner, pet_available, consume_food, consume_drink, car_capacity)
values
    (1, 'url:license1', 'Toyota', 'Camry', 'Blue', 2019, 'Comfortable car', 1, 0, 1, 1, 0, 1, 4),
    (2, 'url:license2', 'Ford', 'Mustang', 'Red', 2022, 'Sporty car', 0, 1, 0, 0, 1, 0, 2),
    (1, 'url:license1', 'Honda', 'Civic', 'Silver', 2020, 'Compact car', 0, 0, 1, 1, 0, 1, 5),
    (2, 'url:license3', 'Chevrolet', 'Malibu', 'Black', 2021, 'Family car', 1, 0, 1, 0, 1, 0, 7),
    (3, 'url:license2', 'BMW', 'X5', 'White', 2018, 'SUV', 1, 0, 1, 1, 0, 0, 5);


insert into trips(user_id, start_location, end_location, departure_datetime, cost_per_person, available_seats, description,status,duration,distance)
values
    (1, 'Sofia', 'Varna', '2023-08-15 08:00:00', '20', 3, 'Morning commute', 1,'10000','123km'),
    (2, 'Plovdiv', 'Burgas', '2023-08-16 15:30:00', '15', 2, 'Afternoon ride', 2,'10000','150km'),
    (3, 'Pleven', 'Sofia', '2023-08-17 11:00:00', '30', 4, 'Weekend getaway', 0,'10000','140km'),
    (1, 'Blagoevgrad', 'Dupnica', '2023-08-18 09:15:00', '25', 1, 'Quick business trip', 1,'10000','145km'),
    (2, 'Plovdiv', 'Haskovo', '2023-08-19 17:45:00', '10', 3, 'Evening hangout', 2,'10000','300km'),
    (1, 'Sofia', 'Plovdiv', '2023-08-20 12:30:00', 25, 3, 'Midday commute', 1, 150, 'km'),
    (2, 'Varna', 'Burgas', '2023-08-21 10:00:00', 18, 2, 'Seaside getaway', 2, 90, 'km'),
    (3, 'Pleven', 'Ruse', '2023-08-22 09:15:00', 12, 1, 'Quick visit', 0, 60, 'km'),
    (4, 'Pazardzhik', 'Stara Zagora', '2023-08-23 16:45:00', 15, 2, 'Afternoon drive', 1, 50, 'km'),
    (5, 'Bansko', 'Pamporovo', '2023-08-24 08:00:00', 30, 4, 'Mountain retreat', 2, 180, 'km'),
    (1, 'Sofia', 'Varna', '2023-08-25 14:30:00', 22, 3, 'Afternoon trip', 1, 400, 'km'),
    (2, 'Plovdiv', 'Burgas', '2023-08-26 17:00:00', 28, 2, 'Beach adventure', 2, 250, 'km'),
    (3, 'Pleven', 'Sofia', '2023-08-27 11:45:00', 10, 4, 'Weekend visit', 0, 160, 'km'),
    (4, 'Pazardzhik', 'Stara Zagora', '2023-08-28 16:30:00', 20, 1, 'Quick errand', 1, 50, 'km'),
    (5, 'Bansko', 'Pamporovo', '2023-08-29 10:15:00', 12, 4, 'Mountain hike', 2, 180, 'km');


insert into feedbacks(rated_user_id, rated_by_user_id, trip_id, rating_value)
values
    (1, 1, 1, 4),
    (2, 2, 2, 5),
    (3, 3, 3, 4),
    (4, 4, 4, 3),
    (5, 5, 5, 5),
    (1, 6, 6, 4),
    (2, 7, 7, 5),
    (3, 8, 8, 4),
    (4, 9, 9, 3),
    (5, 10, 10, 5),
    (1, 11, 11, 4),
    (2, 12, 12, 5),
    (3, 13, 13, 4),
    (4, 14, 14, 3),
    (5, 15, 15, 5),
    (1, 16, 1, 4),
    (2, 2, 2, 5),
    (3, 3, 3, 4),
    (4, 4, 4, 3),
    (5, 5, 5, 5),
    (1, 6, 6, 4),
    (2, 7, 7, 5),
    (3, 8, 8, 4),
    (4, 9, 9, 3),
    (5, 10, 10, 5),
    (1, 11, 11, 4),
    (2, 12, 12, 5),
    (3, 13, 13, 4),
    (4, 14, 14, 3),
    (5, 15, 15, 5);


insert into feedback_comments(comment, feedback_id,user_id)
values
    ('Great experience!', 1, 1),
    ('Friendly driver', 2, 2),
    ('Smooth ride', 3, 3),
    ('Could be better', 4, 4),
    ('Excellent trip', 5, 5);


insert into trip_requests(trip_id, user_id, status)
values
    (1, 2, 0),
    (2, 3, 1),
    (3, 1, 2),
    (4, 4, 2),
    (5, 2, 1);