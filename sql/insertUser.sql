USE spotitube;

DROP TABLE IF EXISTS user;
CREATE TABLE user
(
    id INT NOT NULL auto_increment PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(50) NOT NULL
);

INSERT INTO user (username, password) VALUES ('niels', 'niels');