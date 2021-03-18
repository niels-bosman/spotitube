USE spotitube;

DROP TABLE IF EXISTS track;
CREATE TABLE track
(
    id INT NOT NULL auto_increment PRIMARY KEY,
    title VARCHAR(50) NOT NULL,
    performer VARCHAR(50) NOT NULL,
    duration INT NOT NULL,
    album VARCHAR(50) NULL,
    playCount INT NULL,
    publicationDate VARCHAR(50) NULL,
    description VARCHAR(100) NULL,
    offlineAvailable BOOL NOT NULL
);

INSERT INTO track
(title, performer, duration, album, playCount, publicationDate, description, offlineAvailable)
VALUES ('Ocean and a rock', 'Lisa Hannigan', 337, 'Sea sew', null, null, null, false);

INSERT INTO track
(title, performer, duration, album, playCount, publicationDate, description, offlineAvailable)
VALUES ('So Long, Marianne', 'Leonard Cohen', 546, 'Songs of Leonard Cohen', null, null, null, false);

INSERT INTO track
(title, performer, duration, album, playCount, publicationDate, description, offlineAvailable)
VALUES ('One', 'Metallica', 423, null, 37, '18-03-2001', 'Long version', true);