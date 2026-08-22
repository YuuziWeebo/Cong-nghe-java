CREATE DATABASE IF NOT EXISTS lab08_jsf
  CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE lab08_jsf;

CREATE TABLE IF NOT EXISTS sinhvien (
    id INT PRIMARY KEY AUTO_INCREMENT,
    ma_sinh_vien VARCHAR(30) NOT NULL UNIQUE,
    ho_ten VARCHAR(100) NOT NULL,
    email VARCHAR(150),
    lop VARCHAR(80) NOT NULL
);

CREATE TABLE IF NOT EXISTS sach (
    id INT PRIMARY KEY AUTO_INCREMENT,
    ten_sach VARCHAR(150) NOT NULL,
    tac_gia VARCHAR(120) NOT NULL,
    nam_xuat_ban INT NOT NULL
);

CREATE TABLE IF NOT EXISTS sanpham (
    id INT PRIMARY KEY AUTO_INCREMENT,
    ten_san_pham VARCHAR(150) NOT NULL,
    gia DECIMAL(15,2) NOT NULL,
    so_luong INT NOT NULL DEFAULT 0
);

CREATE TABLE IF NOT EXISTS tai_khoan (
    id INT PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(100) NOT NULL
);

INSERT IGNORE INTO sinhvien(ma_sinh_vien, ho_ten, email, lop) VALUES
('20240001','Nguyễn Văn An','an@gmail.com','DCCNTT15.10.1'),
('20240002','Trần Thị Bình','binh@gmail.com','DCCNTT15.10.2');

INSERT IGNORE INTO sach(ten_sach,tac_gia,nam_xuat_ban) VALUES
('Lập trình Java','Nguyễn Văn A',2024),
('Jakarta EE cơ bản','Trần Văn B',2025);

INSERT IGNORE INTO sanpham(ten_san_pham,gia,so_luong) VALUES
('Bàn phím cơ',850000,10),
('Chuột không dây',350000,25);

INSERT IGNORE INTO tai_khoan(username,password) VALUES
('admin','123456');
