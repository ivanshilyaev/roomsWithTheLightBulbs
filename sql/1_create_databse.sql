CREATE DATABASE room_db;
CREATE USER application@localhost IDENTIFIED BY 'application_password';

GRANT SELECT, INSERT, UPDATE, DELETE
    ON room_db.*
    TO application@localhost;

CREATE USER room_user@localhost IDENTIFIED BY 'user_password';

GRANT SELECT, INSERT, UPDATE, DELETE
    ON room_db.*
    TO account_user@localhost;