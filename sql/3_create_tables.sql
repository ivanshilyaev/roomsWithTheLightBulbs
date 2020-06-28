USE room_db;

CREATE TABLE room
(
    id        INTEGER  NOT NULL AUTO_INCREMENT,
    name      CHAR(32) NOT NULL,
    country   CHAR(32) NOT NULL,
    lampState CHAR(32) NOT NULL,
    CONSTRAINT pk_room PRIMARY KEY (id)
);