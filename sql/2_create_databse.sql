CREATE DATABASE room_db;
create user application@localhost identified by 'application_password';

GRANT SELECT, INSERT, UPDATE, DELETE
    ON room_db.*
    TO application@localhost;

create user room_user@localhost identified by 'user_password';

GRANT SELECT, INSERT, UPDATE, DELETE
    ON room_db.*
    TO account_user@localhost;