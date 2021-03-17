USE spotitube;

DROP TABLE IF EXISTS playlist_user;
CREATE TABLE playlist_user
(
    id INT NOT NULL auto_increment PRIMARY KEY,
    user_id INT NOT NULL,
    playlist_id INT NOT NULL
);

INSERT INTO playlist_user (user_id, playlist_id) VALUES (0, 0);