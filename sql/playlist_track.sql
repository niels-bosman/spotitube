USE spotitube;

DROP TABLE IF EXISTS playlist_track;
CREATE TABLE playlist_track
(
    id INT NOT NULL auto_increment PRIMARY KEY,
    track_id INT NOT NULL,
    playlist_id INT NOT NULL
);

INSERT INTO playlist_track (track_id, playlist_id) VALUES (0, 0);
INSERT INTO playlist_track (track_id, playlist_id) VALUES (1, 1);
INSERT INTO playlist_track (track_id, playlist_id) VALUES (2, 0);
INSERT INTO playlist_track (track_id, playlist_id) VALUES (3, 0);