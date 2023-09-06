INSERT INTO carpooling.status (status_id, status) VALUES (1, 'pending');
INSERT INTO carpooling.status (status_id, status) VALUES (2, 'confirmed');
INSERT INTO carpooling.status (status_id, status) VALUES (3, 'deleted');

insert into users(username, password, email, first_name, last_name, profile_picture, registration_date, phone_number, is_driver, is_blocked, is_admin,conf_code, status)
values
    ('ledka', 'ledak123', 'ledka@gmail.com', 'Leda', 'Yovkova', '0e647a64-3cf3-44f6-901e-b33a4546db99_my_pic.jpg', '2023-08-10 22:24:24', '031312312', 1, 0, 1, 'dbbe4e58-8e2e-4423-888f-91ab94a3aa66',2),
    ('milen', 'milen1991', 'milen@gmail.com', 'Milen', 'Vaklinov', '1.jpg', '2023-08-09 10:11:22', '0888123456', 1, 0, 0, 'dbbe4e58-8e2e-4423-888f-91ab94a3aa66',2),
    ('kalata', 'kaloyan91', 'kaloyan@gmail.com', 'Kaloyan', 'Stanev', 'kaloyan.jpeg', '2023-08-08 15:30:45', '0777123456', 1, 0, 0, 'dbbe4e58-8e2e-4423-888f-91ab94a3aa66',2),
    ('emilyjohnson', 'emily101', 'emily.johnson@example.com', 'Emily', 'Johnson', 'profile.jpg', '2023-08-07 18:12:59', '0899112233', 1, 0, 0, 'dbbe4e58-8e2e-4423-888f-91ab94a3aa66', 2),
    ('davidwilson', 'david2023', 'david.wilson@example.com', 'David', 'Wilson', 'profile.jpg', '2023-08-06 09:30:15', '0555123456', 1, 0, 0, 'dbbe4e58-8e2e-4423-888f-91ab94a3aa66', 2),
    ('susanmiller', 'susan456', 'susan.miller@example.com', 'Susan', 'Miller', 'profile.jpg', '2023-08-11 14:55:30', '0876123456', 1, 0, 0, 'dbbe4e58-8e2e-4423-888f-91ab94a3aa66', 2),
    ('williamjones', 'william789', 'william.jones@example.com', 'William', 'Jones', 'profile.jpg', '2023-08-12 20:18:42', '0899123456', 1, 0, 0, 'dbbe4e58-8e2e-4423-888f-91ab94a3aa66', 2),
    ('natalieadams', 'natalie101', 'natalie.adams@example.com', 'Natalie', 'Adams', 'profile.jpg', '2023-08-13 12:45:11', '0886123456', 1, 0, 0, 'dbbe4e58-8e2e-4423-888f-91ab94a3aa66', 2),
    ('robertrobinson', 'robert2023', 'robert.robinson@example.com', 'Robert', 'Robinson', 'profile.jpg', '2023-08-14 17:30:27', '0776123456', 1, 0, 0, 'dbbe4e58-8e2e-4423-888f-91ab94a3aa66', 2),
    ('lauramiller', 'laura456', 'laura.miller@example.com', 'Laura', 'Miller', 'profile.jpg', '2023-08-15 08:20:10', '0556123456', 1, 0, 0, 'dbbe4e58-8e2e-4423-888f-91ab94a3aa66', 2),
    ('matthewwilson', 'matthew789', 'matthew.wilson@example.com', 'Matthew', 'Wilson', 'profile.jpg', '2023-08-16 19:55:30', '0887123456', 1, 0, 0, 'dbbe4e58-8e2e-4423-888f-91ab94a3aa66', 2),
    ('oliviajohnson', 'olivia101', 'olivia.johnson@example.com', 'Olivia', 'Johnson', 'profile.jpg', '2023-08-17 15:18:42', '0776123456', 1, 0, 0, 'dbbe4e58-8e2e-4423-888f-91ab94a3aa66', 2),
    ('jamesdavis', 'james2023', 'james.davis@example.com', 'James', 'Davis', 'profile.jpg', '2023-08-18 11:45:11', '0898123456', 1, 0, 0, 'dbbe4e58-8e2e-4423-888f-91ab94a3aa66', 2),
    ('elizabethharris', 'elizabeth456', 'elizabeth.harris@example.com', 'Elizabeth', 'Harris', 'profile.jpg', '2023-08-19 14:30:27', '0557123456', 0, 0, 0, 'dbbe4e58-8e2e-4423-888f-91ab94a3aa66', 2),
    ('charlesmartin', 'charles789', 'charles.martin@example.com', 'Charles', 'Martin', 'profile.jpg', '2023-08-20 08:20:10', '0776123456', 1, 0, 0, 'dbbe4e58-8e2e-4423-888f-91ab94a3aa66', 2),
    ('sarahthompson', 'sarah101', 'sarah.thompson@example.com', 'Sarah', 'Thompson', 'profile.jpg', '2023-08-21 19:55:30', '0887123456', 0, 0, 0, 'dbbe4e58-8e2e-4423-888f-91ab94a3aa66', 2),
    ('danielwhite', 'daniel2023', 'daniel.white@example.com', 'Daniel', 'White', 'profile.jpg', '2023-08-22 15:18:42', '0777123456', 0, 0, 0, 'dbbe4e58-8e2e-4423-888f-91ab94a3aa66', 2),
    ('jessicawilson', 'jessica456', 'jessica.wilson@example.com', 'Jessica', 'Wilson', 'profile.jpg', '2023-08-23 11:45:11', '0898123456', 0, 0, 0, 'dbbe4e58-8e2e-4423-888f-91ab94a3aa66', 2),
    ('thomasdavis', 'thomas789', 'thomas.davis@example.com', 'Thomas', 'Davis', 'profile.jpg', '2023-08-24 14:30:27', '0557123456', 0, 0, 0, 'dbbe4e58-8e2e-4423-888f-91ab94a3aa66', 2),
    ('elizabethwilson', 'elizabeth101', 'elizabeth.wilson@example.com', 'Elizabeth', 'Wilson', 'profile.jpg', '2023-08-25 08:20:10', '0776123456', 0, 0, 0, 'dbbe4e58-8e2e-4423-888f-91ab94a3aa66', 2),
    ('ryanmartin', 'ryan2023', 'ryan.martin@example.com', 'Ryan', 'Martin', 'profile.jpg', '2023-08-26 19:55:30', '0887123456', 0, 0, 0, 'dbbe4e58-8e2e-4423-888f-91ab94a3aa66', 2),
    ('sophiasmith', 'sophia456', 'sophia.smith@example.com', 'Sophia', 'Smith', 'profile.jpg', '2023-08-27 15:18:42', '0777123456', 0, 0, 0, 'dbbe4e58-8e2e-4423-888f-91ab94a3aa66', 2),
    ('jameswilson', 'james789', 'james.wilson@example.com', 'James', 'Wilson', 'profile.jpg', '2023-08-28 11:45:11', '0898123456', 0, 0, 0, 'dbbe4e58-8e2e-4423-888f-91ab94a3aa66', 2),
    ('emilydavis', 'emily1010', 'emily.davis@example.com', 'Emily', 'Davis', 'profile.jpg', '2023-08-29 14:30:27', '0557123456', 0, 0, 0, 'dbbe4e58-8e2e-4423-888f-91ab94a3aa66', 2),
    ('michaeljones', 'michael2023', 'michael.jones@example.com', 'Michael', 'Jones', 'profile.jpg', '2023-08-30 08:20:10', '0776123456', 0, 0, 0, 'dbbe4e58-8e2e-4423-888f-91ab94a3aa66', 2),
    ('sarahthomas', 'sarah456', 'sarah.thomas@example.com', 'Sarah', 'Thomas', 'profile.jpg', '2023-08-31 19:55:30', '0887123456', 0, 0, 0, 'dbbe4e58-8e2e-4423-888f-91ab94a3aa66', 2),
    ('davidharris', 'david789', 'david.harris@example.com', 'David', 'Harris', 'profile.jpg', '2023-09-01 15:18:42', '0777123456', 0, 0, 0, 'dbbe4e58-8e2e-4423-888f-91ab94a3aa66', 2),
    ('oliviamartin', 'olivia1010', 'olivia.martin@example.com', 'Olivia', 'Martin', 'profile.jpg', '2023-09-02 11:45:11', '0898123456', 0, 0, 0, 'dbbe4e58-8e2e-4423-888f-91ab94a3aa66', 2),
    ('andrewdavis', 'andrew2023', 'andrew.davis@example.com', 'Andrew', 'Davis', 'profile.jpg', '2023-09-03 14:30:27', '0557123456', 0, 0, 0, 'dbbe4e58-8e2e-4423-888f-91ab94a3aa66', 2),
    ('elizabethbrown', 'elizabeth456', 'elizabeth.brown@example.com', 'Elizabeth', 'Brown', 'profile.jpg', '2023-09-04 08:20:10', '0776123456', 0, 0, 0, 'dbbe4e58-8e2e-4423-888f-91ab94a3aa66', 2),
    ('williamwilson', 'william789', 'william.wilson@example.com', 'William', 'Wilson', 'profile.jpg', '2023-09-05 19:55:30', '0887123456', 0, 0, 0, 'dbbe4e58-8e2e-4423-888f-91ab94a3aa66', 2),
    ('jessicamartin', 'jessica1010', 'jessica.martin@example.com', 'Jessica', 'Martin', 'profile.jpg', '2023-09-06 15:18:42', '0777123456', 0, 0, 0, 'dbbe4e58-8e2e-4423-888f-91ab94a3aa66', 2);




INSERT INTO cars (user_id, driver_license_picture, car_brand, car_model, car_color, car_year, description, extra_storage, smoke, air_conditioner, pet_available, consume_food, consume_drink, car_capacity)
VALUES
    (1, 'url:license1', 'Toyota', 'Camry', 'Blue', 2019, 'Comfortable car', 1, 0, 1, 1, 0, 1, 4),
    (2, 'url:license1', 'Honda', 'Civic', 'Silver', 2020, 'Compact car', 0, 0, 1, 1, 0, 1, 4),
    (3, 'url:license1', 'Nissan', 'Altima', 'Black', 2021, 'Sedan', 1, 0, 1, 0, 1, 0, 4),
    (4, 'url:license1', 'Ford', 'Escape', 'Gray', 2022, 'Crossover', 0, 1, 0, 0, 1, 1, 4),
    (5, 'url:license1', 'Chevrolet', 'Cruze', 'Red', 2018, 'Economical car', 1, 0, 1, 1, 0, 0, 4),
    (6, 'url:license1', 'Hyundai', 'Elantra', 'White', 2020, 'Family car', 1, 0, 1, 0, 1, 0, 4),
    (7, 'url:license1', 'Kia', 'Sportage', 'Blue', 2019, 'SUV', 0, 0, 1, 1, 0, 1, 4),
    (8, 'url:license1', 'Subaru', 'Outback', 'Green', 2021, 'Off-road vehicle', 1, 0, 1, 0, 1, 0, 4),
    (9, 'url:license1', 'Mazda', 'CX-5', 'Red', 2022, 'Compact SUV', 0, 1, 0, 0, 1, 1, 4),
    (10, 'url:license1', 'Volkswagen', 'Passat', 'Black', 2020, 'Business car', 0, 0, 1, 1, 0, 0, 4),
    (11, 'url:license1', 'Audi', 'A4', 'Silver', 2021, 'Luxury car', 1, 0, 1, 0, 1, 0, 4),
    (12, 'url:license1', 'Tesla', 'Model 3', 'Blue', 2022, 'Electric car', 0, 0, 1, 1, 0, 1, 4),
    (13, 'url:license1', 'Mercedes-Benz', 'C-Class', 'White', 2020, 'Premium car', 1, 0, 1, 0, 1, 0, 4),
    (14, 'url:license1', 'Lexus', 'RX', 'Black', 2019, 'Luxury SUV', 1, 0, 1, 0, 1, 0, 4),
    (15, 'url:license1', 'Volvo', 'XC60', 'Gray', 2021, 'Compact luxury SUV', 0, 0, 1, 0, 1, 1, 4);



insert into trips(user_id, start_location, end_location, departure_datetime, cost_per_person, available_seats, description,status,duration,distance)
values
    (1, 'Sofia', 'Varna', '2023-08-15 08:00:00', '20', 3, 'Morning commute', 2,'100','123'),
    (2, 'Plovdiv', 'Burgas', '2023-08-16 15:30:00', '15', 2, 'Afternoon ride', 2,'120','150'),
    (3, 'Pleven', 'Sofia', '2023-08-17 11:00:00', '30', 4, 'Weekend getaway', 2,'134','140'),
    (4, 'Blagoevgrad', 'Dupnica', '2023-08-18 09:15:00', '25', 1, 'Quick business trip', 2,'128','145'),
    (5, 'Plovdiv', 'Haskovo', '2023-08-19 17:45:00', '10', 3, 'Evening hangout', 2,'115','300'),
    (6, 'Sofia', 'Plovdiv', '2023-08-20 12:30:00', 25, 3, 'Midday commute', 2, '145', '145'),
    (7, 'Varna', 'Burgas', '2023-08-21 10:00:00', 18, 2, 'Seaside getaway', 2, '135', '178'),
    (8, 'Pleven', 'Ruse', '2023-08-22 09:15:00', 12, 1, 'Quick visit', 2, '127', '191'),
    (9, 'Pazardzhik', 'Stara Zagora', '2023-08-23 16:45:00', 15, 2, 'Afternoon drive', 2, '145', '222'),
    (10, 'Bansko', 'Pamporovo', '2023-08-24 08:00:00', 30, 4, 'Mountain retreat', 2, '145', '201'),
    (11, 'Sofia', 'Varna', '2023-08-25 14:30:00', 22, 3, 'Afternoon trip', 1, '159', '167'),
    (12, 'Plovdiv', 'Burgas', '2023-08-26 17:00:00', 28, 2, 'Beach adventure', 1, '145', '191'),
    (13, 'Pleven', 'Sofia', '2023-08-27 11:45:00', 10, 4, 'Weekend visit', 1, '160', '154'),
    (14, 'Pazardzhik', 'Stara Zagora', '2023-08-28 16:30:00', 20, 1, 'Quick errand', 1,'187', '138'),
    (15, 'Bansko', 'Pamporovo', '2023-08-29 10:15:00', 12, 4, 'Mountain hike', 1,'193', '129'),
    (1, 'Ruse', 'Burgas', '2023-08-15 08:00:00', '20', 3, 'Seaside Trip', 1, '100', '250'),
    (2, 'Burgas', 'Plovdiv', '2023-08-16 15:30:00', '15', 2, 'City Hopping', 1, '120', '300'),
    (3, 'Varna', 'Sofia', '2023-08-17 11:00:00', '30', 4, 'Capital Visit', 1, '134', '350'),
    (4, 'Veliko Tarnovo', 'Blagoevgrad', '2023-08-18 09:15:00', '25', 1, 'University Tour', 1, '128', '200'),
    (5, 'Bansko', 'Pamporovo', '2023-08-19 17:45:00', '10', 3, 'Mountain Retreat', 1, '115', '100'),
    (6, 'Stara Zagora', 'Haskovo', '2023-08-20 12:30:00', '25', 3, 'Southern Tour', 0, '145', '150'),
    (7, 'Pazardzhik', 'Dupnica', '2023-08-21 10:00:00', '18', 2, 'Small Town Trip', 0, '135', '80'),
    (8, 'Velingrad', 'Kyustendil', '2023-08-22 09:15:00', '12', 1, 'Spa Day', 0, '127', '110'),
    (9, 'Razgrad', 'Vratsa', '2023-08-23 16:45:00', '15', 2, 'Northwestern Journey', 0, '145', '180'),
    (10, 'Gabrovo', 'Kardzhali', '2023-08-24 08:00:00', '30', 4, 'Historical Exploration', 0, '145', '220'),
    (11, 'Silistra', 'Montana', '2023-08-25 14:30:00', '22', 3, 'Danube Valley Tour', 0, '159', '240'),
    (12, 'Lovech', 'Targovishte', '2023-08-26 17:00:00', '28', 2, 'Rural Experience', 0, '145', '170'),
    (13, 'Dobrich', 'Yambol', '2023-08-27 11:45:00', '10', 4, 'Eastern Adventure', 0, '160', '200'),
    (14, 'Pernik', 'Pleven', '2023-08-28 16:30:00', '20', 1, 'City Sightseeing', 0, '187', '90'),
    (15, 'Shumen', 'Troyan', '2023-08-29 10:15:00', '12', 4, 'Mountain Hiking', 0, '193', '130');


insert into feedbacks(rated_user_id, rated_by_user_id, trip_id, rating_value)
values
    (1, 30, 1, 4),
    (2, 29, 2, 5),
    (3, 28, 3, 4),
    (4, 27, 4, 3),
    (5, 26, 5, 5),
    (6, 25, 6, 4),
    (7, 24, 7, 5),
    (8, 23, 8, 4),
    (9, 22, 9, 3),
    (10, 21, 10, 5),
    (11, 20, 11, 4),
    (12, 19, 12, 5),
    (13, 18, 13, 4),
    (14, 17, 14, 3),
    (15, 16, 15, 5),
    (16, 15, 16, 4),
    (17, 14, 17, 5),
    (18, 13, 18, 4),
    (19, 12, 19, 3),
    (20, 11, 20, 5),
    (21, 10, 21, 4),
    (22, 9, 22, 5),
    (23, 8, 23, 4),
    (24, 7, 24, 3),
    (25, 6, 25, 5),
    (26, 5, 26, 4),
    (27, 4, 27, 5),
    (28, 3, 28, 4),
    (29, 2, 29, 3),
    (30, 1, 30, 5);


insert into feedback_comments(comment, feedback_id,user_id)
values
    ('Great experience!', 1, 1),
    ('Friendly driver', 2, 2),
    ('Smooth ride', 3, 3),
    ('Could be better', 4, 4),
    ('Excellent trip', 5, 5);


insert into trip_requests(trip_id, user_id, status)
values
    (1, 15, 0),
    (2, 14, 1),
    (3, 13, 2),
    (4, 12, 2),
    (5, 11, 1),
    (6, 10, 0),
    (7, 9, 1),
    (8, 8, 2),
    (9, 7, 2),
    (10, 6, 1),
    (11, 5, 0),
    (12, 4, 1),
    (13, 3, 2),
    (14, 2, 2),
    (15, 1, 1),
    (16, 16, 0),
    (17, 17, 1),
    (18, 18, 2),
    (19, 19, 2),
    (20, 20, 1),
    (21, 21, 0),
    (22, 22, 1),
    (23, 23, 2),
    (24, 24, 2),
    (25, 25, 1),
    (26, 26, 0),
    (27, 27, 1),
    (28, 28, 2),
    (29, 29, 2),
    (30, 30, 1);