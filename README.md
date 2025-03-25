The Library Management System is a web application built using Spring Boot that allows users to manage books, users, and the borrowing process efficiently. This project now includes security enhancements using Spring Security & OAuth 2.0 and performance optimization using Spring Cache & Redis.

Tech Stack

Backend: Spring Boot, Java, JPA, Hibernate, Spring Security, OAuth 2.0

Database: MySQL

Caching: Spring Cache, Redis

Testing: JUnit

Build Tool: Maven

Features

Core Features:

✅ Add, update, and delete books
📖✅ Manage users and their borrowing process
👥✅ Track overdue books and fines 
⏳✅ RESTful APIs for seamless integration 
🌐✅ JUnit testing for robust and reliable code ✅

Security Enhancements:

🔒 Spring Security & OAuth 2.0: Secure authentication & role-based access control🔑 JWT Token Authentication: Users authenticate securely using JWT🔄 OAuth 2.0 Authorization: Supports third-party authentication providers

Performance Optimization:

⚡ Spring Cache: Speeds up data retrieval and reduces database queries🚀 Redis Integration: Caching frequently accessed data for faster response times

Installation & Setup

Prerequisites:

Java 17+

MySQL database

Redis Server (for caching)





Configure Database:

Update application.properties with your MySQL database credentials.

Run Redis (If not already running):

redis-server

Build & Run the Application:

mvn clean install
mvn spring-boot:run



Future Improvements

Implementing a recommendation system for books 📌

Enhancing role-based access with more granular permissions 🔍

Improving UI/UX with a React or Angular frontend 🎨

Contributing

Contributions are welcome! Feel free to submit a pull request or open an issue.
