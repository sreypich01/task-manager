# Task Manager Application

A full-stack task management system with Vue.js frontend and Spring Boot backend.

## Features
- Create, read, update, and delete tasks
- Form validation
- RESTful API
## API Endpoints
- GET	/api/tasks	Get all tasks
- POST	/api/tasks	Create new task
- PUT	/api/tasks/{id}	Update a task
- DELETE	/api/tasks/{id}	Delete a task

## Tools / Libraries Used
1. **Frontend:**
- Vue.js 3
- Tailwind CSS 3
- Axios
2. **Backend:**
- Spring Boot
- MyBatis
- PostgreSQL
- Lombok
  
## Setup

### Backend (Spring Boot)

1. **Clone repo:**
   ```bash
   git clone https://github.com/sreypich01/task-manager.git
   cd task-manager/backend
2. **Create PostgreSQL database:**
   ```bash
   CREATE DATABASE task_manager;
3. **Update src/main/resources/application.properties:**
   ```bash
    spring.datasource.url=jdbc:postgresql://localhost:5432/task_manager
    spring.datasource.username=postgres
    spring.datasource.password=pich0608
4. **Run Backend:**
 - Navigate to:
   ```bash
    src\main\java\sreypich\rom\taskmanager\TaskmanagerApplication.java
- Click Run in your IDE to start the Spring Boot server.
Default port: 8080
### Frontend (Vue.js)
   ```bash
    cd ../frontend
    npm install
    npm run dev
- Access: http://localhost:5173


