# Library Management System

A simple full-stack library management system that allows users to **register**, **log in**, **view books**, **borrow books**, **return books**, and **view borrowing history**.  

## Features
| Feature | Description |
|----------|-------------|
| Registration | Create a new account |
| Login | Simple login handled on frontend |
| View Books | Display all books or search by title/author |
| Borrow Book | Borrow a book if available |
| Return Book | Return a previously borrowed book |
| Borrow History | View user's borrowing records |

## Setup Instructions

### Prerequisites
Install these before running:
| Software | Version |
|-----------|---------|
| Java JDK | 17 or 21 |
| Maven | Latest |
| MySQL | 8.x |
| VS Code | Latest |
| Git | Latest |
### 1.Clone the Project
```bash
git clone https://github.com/xtfwork/library.git
cd smart-library/backend
```
### 2.Configure Database
Before running the backend, make sure MySQL is running locally.
Create a database named `library` manually:
```sql
CREATE DATABASE library;
```
Open the file backend/src/main/resources/application-dev.yml and update your own MySQL username and password

### 3.Run the Backend
```bash
cd backend
mvn clean install
mvn spring-boot:run
```

### 4.Run the Frontend
Open the file index.html inside:
```bash
backend/src/main/resources/static/index.html
```

### feel free to leave a comment!!
