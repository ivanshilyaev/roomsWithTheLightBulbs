USE room_db;

CREATE TABLE room
(
    id      INTEGER  NOT NULL AUTO_INCREMENT,
    name    CHAR(32) NOT NULL,
    country CHAR(8)  NOT NULL,
    lamp    BIT      NOT NULL DEFAULT 1,
    CONSTRAINT pk_room PRIMARY KEY (id)
);