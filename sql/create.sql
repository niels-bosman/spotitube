DROP DATABASE IF EXISTS spotitube;
CREATE DATABASE spotitube;

USE spotitube;

DROP TABLE IF EXISTS user;
CREATE TABLE user
(
    id       INT         NOT NULL AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(50) NOT NULL,
    token    VARCHAR(50) NULL,
    name     VARCHAR(50) NULL
);

INSERT INTO user (username, password, name)
VALUES ('niels', 'niels', 'Niels Bosman');

INSERT INTO user (username, password, name)
VALUES ('niels2', 'niels2', 'Niels Bosman2');

DROP TABLE IF EXISTS playlist_user;
CREATE TABLE playlist_user
(
    id          INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    user_id     INT NOT NULL,
    playlist_id INT NOT NULL
);

INSERT INTO playlist_user (user_id, playlist_id)
VALUES (1, 1);

INSERT INTO playlist_user (user_id, playlist_id)
VALUES (1, 1);

INSERT INTO playlist_user (user_id, playlist_id)
VALUES (2, 1);

INSERT INTO playlist_user (user_id, playlist_id)
VALUES (3, 1);

DROP TABLE IF EXISTS playlist;
CREATE TABLE playlist
(
    id       INT         NOT NULL AUTO_INCREMENT PRIMARY KEY,
    owner_id INT         NOT NULL,
    name     VARCHAR(50) NOT NULL
);

INSERT INTO playlist (name, owner_id)
VALUES ('Heavy metal', 1);

INSERT INTO playlist (name, owner_id)
VALUES ('Pop', 1);

INSERT INTO playlist (name, owner_id)
VALUES ('Hip hop', 1);

INSERT INTO playlist (name, owner_id)
VALUES ('Techno', 1);

INSERT INTO playlist (name, owner_id)
VALUES ('Progressive Rock', 1);

DROP TABLE IF EXISTS playlist_track;
CREATE TABLE playlist_track
(
    id               INT  NOT NULL AUTO_INCREMENT PRIMARY KEY,
    track_id         INT  NOT NULL,
    playlist_id      INT  NOT NULL,
    offlineAvailable BOOL NOT NULL
);

INSERT INTO playlist_track (track_id, playlist_id, offlineAvailable)
VALUES (1, 2, true);

INSERT INTO playlist_track (track_id, playlist_id, offlineAvailable)
VALUES (2, 2, true);

INSERT INTO playlist_track (track_id, playlist_id, offlineAvailable)
VALUES (3, 3, false);

INSERT INTO playlist_track (track_id, playlist_id, offlineAvailable)
VALUES (3, 2, false);

DROP TABLE IF EXISTS track;
CREATE TABLE track
(
    id              INT          NOT NULL AUTO_INCREMENT PRIMARY KEY,
    title           VARCHAR(50)  NOT NULL,
    performer       VARCHAR(50)  NOT NULL,
    duration        INT          NOT NULL,
    album           VARCHAR(50)  NULL,
    playCount       INT          NULL,
    publicationDate VARCHAR(50)  NULL,
    description     VARCHAR(100) NULL
);

INSERT INTO track
(title, performer, duration, album, playCount, publicationDate, description)
VALUES ('Ocean and a rock', 'Lisa Hannigan', 337, 'Sea sew', null, null, null);

INSERT INTO track
(title, performer, duration, album, playCount, publicationDate, description)
VALUES ('So Long, Marianne', 'Leonard Cohen', 546, 'Songs of Leonard Cohen', null, null, null);

INSERT INTO track
(title, performer, duration, album, playCount, publicationDate, description)
VALUES ('One', 'Metallica', 423, null, 37, '18-03-2001', 'Long version');