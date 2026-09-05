-- Step 1: Database banao
CREATE DATABASE IF NOT EXISTS student_db;
USE student_db;

-- Step 2: Table banao
CREATE TABLE IF NOT EXISTS students (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    course VARCHAR(100) NOT NULL,
    email VARCHAR(100),
    phone VARCHAR(15)
);

-- Sample data (optional)
INSERT INTO students (name, course, email, phone) VALUES
('Rohan Sharma', 'B.Tech CSE', 'rohan@example.com', '9876543210'),
('Priya Verma', 'BCA', 'priya@example.com', '9876500000');
