drop schema if exists auto_ads;
create schema auto_ads;
use auto_ads;

-- DDL

create table `user`
(
    id         BIGINT unsigned auto_increment primary key,
    name       VARCHAR(100) null,
    email      VARCHAR(250) not null UNIQUE,
    first_name VARCHAR(50)  not null,
    last_name  VARCHAR(50)  not null,
    password   VARCHAR(60)  not null,
    phone      VARCHAR(20)  not null
);

create table advert
(
    id                BIGINT unsigned auto_increment primary key,
    price             int             not null,
    date_added        DATETIME        not null,
    manufacture_year  SMALLINT        not null,
    transmission_type VARCHAR(50)     null,
    chassis_type      VARCHAR(50)     not null,
    power             INT             not null,
    fuel_type         VARCHAR(50)     not null,
    kilometers        INT             not null,
    drive_type        VARCHAR(50)     not null,
    volume            DECIMAL(5, 3)   not null,
    color             VARCHAR(50)     not null,
    manufacturer      varchar(50)     not null,
    model             VARCHAR(50)     not null,
    seller_id         BIGINT UNSIGNED not null,
    foreign key (seller_id) references `user` (id)
);

create table image
(
    id        BIGINT unsigned auto_increment primary key,
    url       varchar(2048)   not null,
    advert_id bigint unsigned not null,
    foreign key (advert_id) references advert (id)
);

-- DML

INSERT INTO `user`(name, email, first_name, last_name, phone, password)
VALUES ('JonasJonaitis',
        'jonas@jonas.com',
        'Jonas',
        'Jonaitis',
        '+37062020200',
        '$2a$10$E3n4WLY4l0AQRosIyT3j2.iAFOb2CIlVAr9AN9ShFZQmjgHusNRRC');
