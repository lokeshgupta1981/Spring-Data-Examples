truncate table person;
insert into person(first_name, last_name, created_at) values
    ('Lokesh','Gupta', CURRENT_TIMESTAMP),
    ('Alex','Kolen', CURRENT_TIMESTAMP),
    ('Brian','Schultz', CURRENT_TIMESTAMP);