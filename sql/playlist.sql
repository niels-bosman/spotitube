USE spotitube;

DROP TABLE IF EXISTS playlist;
CREATE TABLE playlist
(
    id INT NOT NULL auto_increment PRIMARY KEY,
    user_id INT NOT NULL,
    name VARCHAR(50) NOT NULL
);

INSERT INTO playlist (name, user_id) VALUES ('afspeellijst', 0);