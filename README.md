# 🩺 Medilabo Solutions

# 🔍 Project Overview
This project is a medical application designed to manage patient data, medical notes, and calculate type 2 diabetes risk assessments. 

# 🛠️ Technologies
## Backend
- **Java 21**
- **Spring Boot 3.5.4**
- **Spring Cloud 2025.0.0**
## Databases
- **MySQL 8.0**
- **MongoDB 8.0**
## Build and test tools
- **Maven 3.9.11**
- **JUnit 5**
- **Mockito**
- **JaCoCo**
## Frontend
- **React 19.1**
- **Vite 7.0**
- **TypeScript 5.8.3**
- **HTML5/CSS3**
- **Node.js 22.18**
- **NPM 10.9.3**

# 🏗️ Architecture
This project is built using a **microservices architecture**:

![Architecture Diagram](.github/readme_assets/architecture_diagram.jpg)

- **Frontend** (client-ui/frontend:9080) : User interface.


- **Api Gateway** (ms-gateway:9001) : Handles all API requests and routes them to the appropriate microservice.
  - Spring Cloud Gateway
  - Spring Boot Security (Secure API endpoints with Basic Authentication)


- **Patient** (ms-patient:9005) : Manages patient data.
  - Spring Data JPA (MySQL)
  - Spring Boot Actuator (Monitoring)


- **Note** (ms-note:9006) : Manages medical notes.
  - Spring Data MongoDB (MongoDB)
  - Spring Boot Actuator (Monitoring)
  - Spring Cloud OpenFeign (Communication with ms-patient)


- **Risk Assessment** (ms-riskassessment:9007) : Calculates the risk of type 2 diabetes based on patient datas and notes.
  - Spring Boot Actuator (Monitoring)
  - Spring Cloud OpenFeign (Communication with ms-patient & ms-note)


- **Eureka** (ms-eureka:9002) : Service discovery for microservices.
    - Spring Cloud Netflix Eureka


- **Config** (ms-config:9003) : Centralized configuration, stored on GitHub, for microservices.
  - Spring Cloud Config Server

# 🚀 Getting Started
This project can be run in two ways; Docker is the easiest and fastest method.

## 📦 Clone the project

```bash
  git clone https://github.com/paulc/cros-paul-Medilabo.git
```
---
## 🐳 Deployment with Docker

### Prerequisites
- Docker
- Docker Compose (2.39.1)

### Installation steps
From the main project directory:
```bash
  docker-compose build
```

### Run
From the main project directory:
```bash
  docker-compose up -d
```

#### **Frontend access : [http://localhost:9080/](http://localhost:9080/)**

### Stop
From the main project directory:
```bash
  docker-compose down
```

---

## 🖥️ Deployment without Docker

### Prerequisites
- Java (21)
- Maven (3.9.11)
- MySQL (8.0)
- MongoDB (8.0)
- Mongosh (2.5.6)
- Node (22.18)
- NPM (10.9.3)

### Installation steps

Create **MySQL** database:
```SQL 
CREATE DATABASE medilabo_patient;
CREATE USER 'myuser'@'localhost' IDENTIFIED BY 'mypassword';
GRANT ALL PRIVILEGES ON medilabo_patient.* TO 'myuser'@'localhost';
FLUSH PRIVILEGES;
```
Create **MongoDB** database:
```BASH
  mongosh < medilabo_note/src/main/resources/mongodb-init.js
```

Install the Frontend dependencies:
```bash
  cd client-ui/frontend/
  npm install
```

### Run

**Start each microservice** from his folder, example: 
```bash
  cd ms-eureka
  mvn spring-boot:run
```
Please follow this order:
1. ms-eureka
2. ms-note, ms-patient, ms-riskassessment
3. ms-gateway

_Note: The ms-config isn't needed because configurations are stored locally (See application-dev.properties in corresponding microservice)._ 

**Start the Frontend:** 
```bash
  cd client-ui/frontend/
  npm run dev
```

#### **Frontend access : [http://localhost:9080/](http://localhost:9080/)**

---

# 🌱 Green code
Green code is an approach aimed at reducing the environmental impact of software operation.
It focuses on optimizing hardware, software, and network usage to minimize energy consumption and carbon footprint.

Here are some points for improvement to make the application greener:
- **Network**: 
  - Integrate Caffeine as a cache manager in Spring Cloud Gateway.
  - Minimize dependencies, images, script and use CDN for the frontend.
  - Use small docker images.
- **Software**:
  - Optimize algorithms, like risk assessment calculation.
  - Choose a language and framework optimized for performance and energy efficiency. (Java and Spring Boot are well-suited for this purpose)
  - Use tools to analyze and optimize your code.
- **Hardware**:
  - Choose a green hosting provider to reduce the carbon footprint of the application.
  - Favor virtual machines, containers, and cloud services to pool resources and lower energy consumption.