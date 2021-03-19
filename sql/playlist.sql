USE spotitube;

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