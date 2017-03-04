CREATE TABLE users(
name VARCHAR(100) PRIMARY KEY NOT NULL,
password VARCHAR(100) NOT NULL);

INSERT INTO users VALUES 
('stachu', 'stachu'),
('kuba', 'kuba'); 

CREATE TABLE history(
user1 VARCHAR(100) DEFAULT 'User 1',
user2 VARCHAR(100) DEFAULT 'User 2',
data TIMESTAMP DEFAULT current_timestamp,
winner VARCHAR(100));

INSERT INTO history(user1, user2, winner) VALUES
('stachu', 'kuba', 'stachu'),
('kuba', 'stachu', 'stachu');