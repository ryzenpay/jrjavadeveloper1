CREATE TABLE IF NOT EXISTS users  (
    id INT PRIMARY KEY AUTO_INCREMENT,
    first_name VARCHAR(255) NOT NULL,
    last_name VARCHAR(255) NOT NULL,
    species VARCHAR(50),
    created TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

INSERT INTO users (first_name, last_name, species) VALUES
('Pim', 'Pimling', 'Critter'),
('Charlie', 'Dompler', 'Critter'),
('Alan', 'Red', 'Critter'),
('Glep', '', 'Critter'),
('Mr.', 'Boss', 'Human');