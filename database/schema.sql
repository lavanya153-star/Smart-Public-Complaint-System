-- Create Database
CREATE DATABASE IF NOT EXISTS complaint_system;
USE complaint_system;

------------------------------------------------------
-- USERS TABLE
------------------------------------------------------
CREATE TABLE IF NOT EXISTS users (
    user_id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100),
    email VARCHAR(100) UNIQUE,
    password VARCHAR(100)
);

------------------------------------------------------
-- ADMIN TABLE
------------------------------------------------------
CREATE TABLE IF NOT EXISTS admin (
    admin_id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50),
    password VARCHAR(50)
);

------------------------------------------------------
-- DEPARTMENTS TABLE
------------------------------------------------------
CREATE TABLE IF NOT EXISTS departments (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL
);

------------------------------------------------------
-- COMPLAINTS TABLE
-- (FINAL VERSION WITH ALL REQUIRED FIELDS)
------------------------------------------------------

CREATE TABLE complaints (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100),
    email VARCHAR(100),
    address VARCHAR(255),
    category VARCHAR(100),
    description TEXT,
    status VARCHAR(50) DEFAULT 'Pending',

    -- Required for Admin Responses
    adminResponse TEXT DEFAULT NULL,
    responseTime TIMESTAMP NULL
);
CREATE TABLE notifications (
    id INT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(255),
    message TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

ALTER TABLE complaints ADD COLUMN department VARCHAR(100) DEFAULT NULL;
