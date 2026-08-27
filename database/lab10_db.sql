CREATE DATABASE IF NOT EXISTS lab10_db CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE lab10_db;

CREATE TABLE IF NOT EXISTS users (
 id INT AUTO_INCREMENT PRIMARY KEY,
 email VARCHAR(100) NOT NULL UNIQUE,
 password VARCHAR(255) NOT NULL,
 full_name VARCHAR(100) NOT NULL,
 role VARCHAR(20) NOT NULL,
 active BOOLEAN NOT NULL DEFAULT TRUE
);

CREATE TABLE IF NOT EXISTS students (
 id INT AUTO_INCREMENT PRIMARY KEY,
 code VARCHAR(30) NOT NULL UNIQUE,
 full_name VARCHAR(100) NOT NULL,
 email VARCHAR(100) NOT NULL,
 major VARCHAR(100)
);

CREATE TABLE IF NOT EXISTS books (
 id INT AUTO_INCREMENT PRIMARY KEY,
 isbn VARCHAR(30) NOT NULL,
 title VARCHAR(150) NOT NULL,
 author VARCHAR(100) NOT NULL,
 quantity INT NOT NULL DEFAULT 0
);

CREATE TABLE IF NOT EXISTS products (
 id INT AUTO_INCREMENT PRIMARY KEY,
 code VARCHAR(30) NOT NULL,
 name VARCHAR(150) NOT NULL,
 price DECIMAL(15,2) NOT NULL DEFAULT 0,
 quantity INT NOT NULL DEFAULT 0
);

INSERT INTO users(email,password,full_name,role,active) VALUES
('admin@eaut.edu.vn','123456','Quản trị viên','ADMIN',TRUE),
('staff@eaut.edu.vn','123456','Nhân viên','STAFF',TRUE),
('user@eaut.edu.vn','123456','Người dùng','USER',TRUE)
ON DUPLICATE KEY UPDATE email=email;

INSERT INTO students(code,full_name,email,major) VALUES
('SV001','Nguyễn Văn An','an@example.com','Công nghệ phần mềm'),
('SV002','Trần Thị Bình','binh@example.com','Khoa học máy tính')
ON DUPLICATE KEY UPDATE code=code;

INSERT INTO books(isbn,title,author,quantity) VALUES
('978001','Lập trình Java','Nguyễn A',10),
('978002','Jakarta EE','Trần B',7);

INSERT INTO products(code,name,price,quantity) VALUES
('SP001','Laptop',15000000,5),
('SP002','Chuột',350000,20);
