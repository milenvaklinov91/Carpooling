create table users
(
    user_id           int auto_increment
        primary key,
    username          varchar(255) not null,
    password          varchar(255) not null,
    email             varchar(255) not null,
    first_name        varchar(45)  not null,
    last_name         varchar(45)  not null,
    profile_picture   varchar(255) null,
    registration_date datetime     null,
    phone_number      varchar(10)  not null,
    is_driver         tinyint(1)   not null,
    is_blocked        tinyint(1)   not null,
    is_admin          tinyint(1)   not null
);


create table cars
(
    car_id          int auto_increment
        primary key,
    user_id         int           not null,
    driver_license_picture varchar(255) null,
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



create table trips
(
    trip_id          int auto_increment
        primary key,
    user_id            int           null,
    start_location     varchar(255)  not null,
    end_location       varchar(255)  not null,
    departure_datetime datetime      not null,
    cost_per_person    varchar(45)   not null,
    available_seats    int           not null,
    description        varchar(2222) null,
    constraint trips_ibfk_1
        foreign key (user_id) references users (user_id)
);

create table ratings
(
    rating_id        int auto_increment
        primary key,
    rated_user_id    int           null,
    rated_by_user_id int           null,
    trip_id          int           null,
    rating_value     int           not null,
    comment          varchar(2555) not null,
    constraint ratings_ibfk_1
        foreign key (rated_user_id) references users (user_id),
    constraint ratings_ibfk_2
        foreign key (rated_by_user_id) references users (user_id),
    constraint ratings_ibfk_3
        foreign key (trip_id) references trips (trip_id)
);

create index rated_by_user_id
    on ratings (rated_by_user_id);

create index rated_user_id
    on ratings (rated_user_id);

create index ride_id
    on ratings (ride_id);

create table trip_requests
(
    request_id     int auto_increment
        primary key,
    trip_id      int         null,
    user_id        int         null,
    request_status varchar(20) not null,
    constraint trip_requests_ibfk_1
        foreign key (trip_id) references trips (trip_id),
    constraint trip_requests_ibfk_2
        foreign key (user_id) references users (user_id)
);

create index travel_id
    on trip_requests (trip_id);

create index user_id
    on trip_requests (user_id);


