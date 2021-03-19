USE
spotitube;

DROP TABLE IF EXISTS playlist_user;
CREATE TABLE playlist_user
(
    id          INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    user_id     INT NOT NULL,
    playlist_id INT NOT NULL
);

INSERT INTO playlist_user (user_id, playlist_id)
VALUES (0, 0);

INSERT INTO playlist_user (user_id, playlist_id)
VALUES (1, 1);

INSERT INTO playlist_user (user_id, playlist_id)
VALUES (2, 0);

INSERT INTO playlist_user (user_id, playlist_id)
VALUES (3, 0);