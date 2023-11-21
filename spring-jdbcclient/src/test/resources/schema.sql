create table person
(
    id         bigint primary key AUTO_INCREMENT,
    first_name varchar not null,
    last_name  varchar not null,
    created_at timestamp
);