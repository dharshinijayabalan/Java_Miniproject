CREATE DATABASE salon_booking_db4;
USE salon_booking_db4;

CREATE TABLE appointments (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    service VARCHAR(50) NOT NULL,
    date DATE NOT NULL,
    time VARCHAR(10) NOT NULL
);
SELECT * FROM appointments;
USE salon_booking_db4;
SELECT * FROM appointments;