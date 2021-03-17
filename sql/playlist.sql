USE spotitube;

DROP TABLE IF EXISTS playlist;
CREATE TABLE playlist
(
    id INT NOT NULL auto_increment PRIMARY KEY,
    name VARCHAR(50) NOT NULL,
);

INSERT INTO playlist (name) VALUES ('afspeellijst');