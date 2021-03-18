USE spotitube;

DROP TABLE IF EXISTS playlist;
CREATE TABLE playlist
(
    id INT NOT NULL auto_increment PRIMARY KEY,
    user_id INT NOT NULL,
    name VARCHAR(50) NOT NULL
);

INSERT INTO playlist (name, user_id) VALUES ('afspeellijst1', 0);
INSERT INTO playlist (name, user_id) VALUES ('afspeellijst2', 0);
INSERT INTO playlist (name, user_id) VALUES ('afspeellijst3', 0);
INSERT INTO playlist (name, user_id) VALUES ('afspeellijst4', 0);