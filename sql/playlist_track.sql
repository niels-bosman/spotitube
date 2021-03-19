USE spotitube;

DROP TABLE IF EXISTS playlist_track;
CREATE TABLE playlist_track
(
    id               INT  NOT NULL auto_increment PRIMARY KEY,
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