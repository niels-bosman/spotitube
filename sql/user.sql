USE spotitube;

DROP TABLE IF EXISTS user;
CREATE TABLE user
(
    id INT NOT NULL auto_increment PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(50) NOT NULL,
    token VARCHAR(50) NULL,
    name VARCHAR(50) NULL
);

INSERT INTO user (username, password, name) VALUES ('niels', 'niels', 'Niels Bosman');
INSERT INTO user (username, password, name) VALUES ('niels2', 'niels2', 'Niels Bosman2');