CREATE DATABASE IF NOT EXISTS football;

USE football;

CREATE TABLE IF NOT EXISTS players (
    player_name     VARCHAR(16)     NOT NULL,
    player_age      INT             NOT NULL,
    player_club     VARCHAR(16)     NOT NULL,
    player_country  VARCHAR(16)     NOT NULL
);

INSERT INTO players VALUES ("Messi",34,"PSG","Argentina");
INSERT INTO players VALUES ("Ronaldo",36,"MANU","Portugal");
INSERT INTO players VALUES ("Neymar",29,"PSG","Brazil");
INSERT INTO players VALUES ("Kane",28,"SPURS","England");
INSERT INTO players VALUES ("E Hazard",30,"MADRID","Belgium");