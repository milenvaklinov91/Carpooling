create table status
(
    status_id int auto_increment
        primary key,
    status    varchar(45) not null
);

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
    is_admin          tinyint(1)   not null,
    conf_code         varchar(45)  null,
    status            int          not null,
    constraint users_status_status_id_fk
        foreign key (status) references status (status_id)
);

create table cars
(
    car_id                 int auto_increment
        primary key,
    user_id                int           not null,
    driver_license_picture varchar(255)  null,
    car_brand              varchar(45)   not null,
    car_model              varchar(45)   not null,
    car_color              varchar(45)   not null,
    car_year               int           not null,
    description            varchar(1222) not null,
    extra_storage          tinyint(1)    not null,
    smoke                  tinyint(1)    not null,
    air_conditioner        tinyint(1)    not null,
    pet_available          tinyint(1)    not null,
    consume_food           tinyint(1)    not null,
    consume_drink          tinyint(1)    not null,
    car_capacity           int           not null,
    constraint cars_users_user_id_pk
        foreign key (user_id) references users (user_id)
);

create table trips
(
    trip_id            int auto_increment
        primary key,
    user_id            int          null,
    start_location     varchar(255) not null,
    end_location       varchar(255) not null,
    departure_datetime datetime     not null,
    cost_per_person    varchar(45)  not null,
    available_seats    int          not null,
    status             smallint     not null,
    description        varchar(45)  not null,
    duration        varchar(45)  not null,
    distance        varchar(45)  not null,
    constraint trips_ibfk_1
        foreign key (user_id) references users (user_id)
);

create table feedbacks
(
    feedback_id      int auto_increment
        primary key,
    rated_user_id    int null,
    rated_by_user_id int null,
    trip_id          int null,
    rating_value     int not null,
    constraint feedbacks_ibfk_1
        foreign key (rated_user_id) references users (user_id),
    constraint feedbacks_ibfk_3
        foreign key (trip_id) references trips (trip_id)
);

create table feedback_comments
(
    feedback_comment_id int auto_increment
        primary key,
    comment             varchar(2000) not null,
    feedback_id         int           not null,
    user_id             int           not null,
    constraint feedback_comments_feedbacks_feedback_id_fk
        foreign key (feedback_id) references feedbacks (feedback_id),
    constraint feedback_comments_users_user_id_fk
        foreign key (user_id) references users (user_id)
);

create index rated_by_user_id
    on feedbacks (rated_by_user_id);

create index rated_user_id
    on feedbacks (rated_user_id);

create table trip_requests
(
    request_id int auto_increment
        primary key,
    trip_id    int      null,
    user_id    int      null,
    status     smallint not null,
    constraint trip_requests_ibfk_1
        foreign key (trip_id) references trips (trip_id),
    constraint trip_requests_ibfk_2
        foreign key (user_id) references users (user_id)
);

create index trip_id
    on trip_requests (trip_id);

create index user_id
    on trip_requests (user_id);


