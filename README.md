# 💇‍♀️ Salon Appointment Booking System (Java + MySQL)




## 🧠 Project Overview

The **Salon Appointment Booking System** is a desktop-based application built using **Java Swing** and **MySQL**.  
It allows customers to **book**, **cancel**, and **view** salon appointments through a simple GUI.  
All appointments are stored in a **MySQL database**, and administrators can access the complete appointment list using a secure password.

This project demonstrates the integration of Java GUI with a backend database using **JDBC (Java Database Connectivity)**.




## 🛠️ Techniques & Technologies Used

| Category | Description |
|-----------|-------------|
| **Programming Language** | Java |
| **GUI Framework** | Java Swing |
| **Database** | MySQL |
| **Database Connectivity** | JDBC (Java Database Connectivity) |
| **SQL Concepts Used** | Database creation, table creation, insert, select, delete |
| **Core Concepts** | OOP, Event-driven programming, Exception handling |
| **IDE (Recommended)** | IntelliJ IDEA / Eclipse / NetBeans |
| **Version Control** | Git & GitHub |



## ⚙️ How the System Works

Here’s how the Salon Appointment Booking System operates step by step:

### 🧍‍♀️ **1. Customer Booking**
- The customer enters:
  - Their **name**
  - The desired **service** (Hair Cut, Facial, etc.)
  - The **date** and **time**
- When the **“Book Appointment”** button is clicked:
  - The entered details are validated.
  - If valid, the data is inserted into the `appointments` table in the MySQL database.
  - A success message is shown: *“Appointment booked successfully!”*



### 🧹 **2. Clear Form**
- The **“Clear”** button resets all text fields and selections in the form.



### ❌ **3. Cancel Appointment**
- The customer clicks on **“Cancel Appointment”**.
- They enter the **appointment ID** they wish to cancel.
- The system deletes that record from the `appointments` table using a SQL `DELETE` statement.
- Displays: *“Appointment cancelled successfully!”*



### 🔐 **4. Admin View**
- Only admins can view all booked appointments.
- The admin clicks **“Admin View”** and enters the password:
- If the password is correct:
- The program fetches all appointment records using a SQL `SELECT * FROM appointments;`
- Displays all appointments in a popup message box.



### 🗄️ **5. Database Storage**
- All customer bookings are stored in the MySQL database.

