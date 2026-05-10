# 🚀 Smart Appointment System

<div align="center">

![Java](https://img.shields.io/badge/Java-17-orange?style=for-the-badge\&logo=java)
![Spring Boot](https://img.shields.io/badge/Spring_Boot-Framework-green?style=for-the-badge\&logo=springboot)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-Database-blue?style=for-the-badge\&logo=postgresql)
![HTML](https://img.shields.io/badge/HTML5-Frontend-orange?style=for-the-badge\&logo=html5)
![CSS](https://img.shields.io/badge/CSS3-Styling-blue?style=for-the-badge\&logo=css3)
![JavaScript](https://img.shields.io/badge/JavaScript-Dynamic-yellow?style=for-the-badge\&logo=javascript)
![GitHub](https://img.shields.io/badge/GitHub-Repository-black?style=for-the-badge\&logo=github)

### ✨ Multi-Domain Appointment & Queue Management System

Book appointments seamlessly with Doctors, Salons, Tutors, and Service Providers.

</div>

---

A full-stack multi-domain appointment and queue management system built using **Spring Boot**, **Java**, **PostgreSQL**, **HTML**, **CSS**, and **JavaScript**.

This project allows users to book appointments with different service providers such as:

* Doctors
* Salons
* Tutors
* Other service providers

The system supports provider registration, slot management, booking, appointment history, and appointment status handling.

---

# ✨ Features

## User Features

* User Registration & Login
* Browse Service Providers
* Book Appointment Slots
* View Booking History
* Appointment Confirmation
* Appointment Status Tracking

## Provider Features

* Provider Registration & Login
* Provider Dashboard
* Manage Appointment Slots
* View User Bookings
* Appointment Management

## Appointment Features

* Slot-based Appointment Scheduling
* Prevent Double Booking
* Appointment Cancellation Handling
* Rescheduling Validation
* Booking History Management

## Exception Handling

* SlotAlreadyBookedException
* AppointmentAlreadyCancelledException
* RescheduleNotAllowedException
* Global Exception Handling

---

# 🛠️ Tech Stack

## Backend

* Java
* Spring Boot
* Spring MVC
* Spring Data JPA
* Hibernate

## Frontend

* HTML5
* CSS3
* JavaScript

## Database

* PostgreSQL

## Tools & Platform

* IntelliJ IDEA
* Git
* GitHub
* Maven

---

# 📂 Project Structure

```bash
src/
 ├── main/
 │    ├── java/com/ved/appointment/
 │    │      ├── controller/
 │    │      ├── dto/
 │    │      ├── entity/
 │    │      ├── exception/
 │    │      ├── repository/
 │    │      ├── service/
 │    │      └── SmartAppointmentSystemApplication.java
 │    │
 │    └── resources/
 │           ├── static/
 │           │      ├── provider/
 │           │      ├── user/
 │           │      ├── index.html
 │           │      ├── history.html
 │           │      └── success.html
 │           │
 │           └── application.properties
```

---

# 🗄️ Database Configuration

Update your `application.properties` file:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/appointment_db
spring.datasource.username=your_username
spring.datasource.password=your_password

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
```

---

# ⚙️ How to Run the Project

## Clone Repository

```bash
git clone https://github.com/itz-ved/smart-appointment-system.git
```

## Open Project

Open the project in IntelliJ IDEA.

## Install Dependencies

Maven dependencies will install automatically.

## Run PostgreSQL

Make sure PostgreSQL server is running.

## Start Application

Run:

```bash
SmartAppointmentSystemApplication.java
```

Or use:

```bash
mvn spring-boot:run
```

---

# 🎨 Frontend Pages

## User Pages

* User Registration
* User Login
* Appointment Booking
* Booking History

## Provider Pages

* Provider Registration
* Provider Login
* Provider Dashboard
* Provider Bookings
* Appointment Management

---

# 🔗 API Highlights

## User APIs

* Register User
* Login User
* Fetch User Details

## Provider APIs

* Register Provider
* Login Provider
* Get Provider Slots

## Appointment APIs

* Book Appointment
* Cancel Appointment
* View Appointment History

---

# 🚀 Future Improvements

* JWT Authentication
* Email Notifications
* Payment Gateway Integration
* Admin Dashboard
* Calendar Integration
* Real-time Notifications
* Docker Deployment
* Cloud Hosting

---

# 📸 Screenshots

You can add screenshots of:

* Homepage
* Provider Dashboard
* Appointment Booking Page
* Booking History
* Login & Registration Pages

---

# 👨‍💻 Author

**Vedprakash Mishra**

GitHub: [https://github.com/itz-ved](https://github.com/itz-ved)

---

# 📜 License

This project is open-source and available for learning and development purposes.
