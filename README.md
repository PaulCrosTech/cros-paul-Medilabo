# 🩺 Medilabo Solutions

# 🔍 Project Overview
```properties
TODO: à quoi sert l'application
```

# 🛠️ Technologies
```properties
TODO: à finaliser, trop long, à synthétiser avec le schéma de l'architecture
```

## Backend
- **Java 21**
- **Spring Boot 3.5.4**
- 
- **Netflix Eureka** (Discovery service)
- **Spring Cloud OpenFeign** (Communication between microservices)
- **Spring Cloud Config** (Central management for configuration via Git, SVN, or HashiCorp Vault.)
- **Spring Cloud Gateway** (API Gateway)
- 
- **Spring Data JPA** (Patient)
- **Spring Data MongoDB** (Note)
- **Spring Boot Actuator** (Monitoring)
- 
- **Lombok** (Annotations)
- **Spring Security** (Authentication)
- **MapStruct** (Mapping DTO/Entity)

## Databases
- **MySQL 8.0**
- **MongoDB 8.0**

## Frontend
- **Vite**
- **TypeScript**
- **ESLint**
- **React**
- **Axios** (HTTP client)
- **React Boostrap**
- **React Rooter**
- **HTML5/CSS3**

## Build and test tools
- **Maven 3.9.11** (Build)
- **Docker** (Containerisation)
- **Git** (Versioning)
- **Jacoco** (Code Coverage)
- **JUnit 5**
- **Mockito**
- **JaCoCo** (Code Coverage)

# 🏗️ Architecture
```properties
TODO: schéma de l'architecture microservices\
  pour chaque microservice : description, techno utilisée, port
```

# 🚀 Getting Started
The project can be run locally or with Docker.  
Docker is recommended for a quick start, as it simplifies the setup process.

## 🗂️ Project architecture

```properties
cros-paul-Medilabo/
├── 📂client-ui/
│   └── 📂frontend/
├── 📂ms-config/
├── 📂ms-eureka/
├── 📂ms-gateway/
│   └── 📝.env                                # Env (Local mode)
├── 📂ms-note/
│   └── 📝.env                                # Env (Local mode)
├── 📂ms-patient/
│   └── 📝.env                                # Env (Local mode)
├── 📂ms-riskassessment/
├── 📝secrets_ms_config_git_credentials.json  # Git credentials for ms-config (Docker mode)
└── 📝.env                                    # Env (Docker mode)
```
## 📦 Clone the project

```bash
  git clone https://github.com/paulc/cros-paul-Medilabo.git
```

## 🐳 Deployment with Docker

### Prerequisites
- Docker
- Docker Compose (2.39.1)

### Installation steps

Create an ```.env``` file in the root folder.

```properties
# Activate the Docker profile
SPRING_PROFILES_ACTIVE=docker

# MS-Gateway & Frontend : authentification
MS_GATEWAY_USER=
MS_GATEWAY_PASSWORD=

# MS-Patient : MySQL
MYSQL_USER=
MYSQL_PASSWORD=

# MS-Note : MongoDB
MONGODB_USER=
MONGODB_PASSWORD=
```

Copy the file ```secrets_ms_config_git_credentials.json``` file in the root folder.  
This file has been sent to you by the project manager.

```json
{
  "MS_CONFIG_GIT_URI": "",
  "MS_CONFIG_GIT_USERNAME": "",
  "MS_CONFIG_GIT_PAT": ""
}
```
_Note: This file contains the credentials required to access the Git repository where the microservices configuration files are stored.  
In Docker mode, the ms-config service is essential as the configurations are fetched from the Git repository._

Launch the project from the root directory:
```bash
  # Build the images
  docker-compose build
  # Start the project in detached mode
  docker-compose up -d
  # Stop the project
  docker-compose down
```

Containers are started in this order:
- ms-eureka, dbMySQL, dbMongoDB
- ms-config
- ms-note, ms-patient, ms-riskassessment
- ms-gateway
- Frontend 

Access the frontend at http://localhost:9080/

_Note: Databases are persisted between restarts._

## 🖥️ Deployment in local

### Prerequisites
- Java (21)
- Maven (3.9.11)
- MySQL (8.0)
- MongoDB (8.0)
- Node (22.18)
- NPM (10.9.3)

### Installation steps

Create a **MySQL** database
```SQL 
CREATE DATABASE medilabo_patient;
```
_Note: data aren't persistes between restarts._

Create a **MongoDB** database with sample data.  
Use the script `medilabo_note/src/main/resources/mongodb-init-local.js` to create the database and insert sample data.
Before running it, set up the username and password, you will need them for microservice ms-note too.
```js
let username = "";
let password = "";
```

Run the script with mongo shell.
```BASH
  mongosh < medilabo_note/src/main/resources/mongodb-init-local.js
```
_Note: data are persisted between restarts, you can re-run the script to re-create the data._

Create an ```.env``` file in **ms-note** folder  
_Note: use the same credentials as in the MongoDB database._
```properties
# MS-Note : MongoDB
MONGODB_USER=
MONGODB_PASSWORD=
```

Create an ```.env``` file in **frontend** folder
```properties
# MS-Gateway & Frontend : authentification
MS_GATEWAY_USER=
MS_GATEWAY_PASSWORD=
```

Create an ```.env``` file in **ms-gateway** folder  
_Note : use the same credentials as in the frontend .env file_
```properties
# MS-Gateway & Frontend : authentification
MS_GATEWAY_USER=
MS_GATEWAY_PASSWORD=
```

**Start each microservice** from his folder, example: 
```bash
  cd ms-eureka
  mvn spring-boot:run
```
Please respect this order:
1. ms-eureka
2. ms-note, ms-patient, ms-riskassessment
3. ms-gateway

_Note: in local mode, the ms-config isn't needed because configurations are stored locally (See application-local.properties in corresponding microservice)._ 

Install and start the Frontend: 
```bash
  cd client-ui/frontend/
  # Install dependencies
  npm install
  # Start the frontend
  npm run dev
```

Access the frontend at http://localhost:9080/

# ✅ Unitary tests, code coverage and documentation
```properties
TODO: à finaliser
```
First you have to follow steps for local deployment.  
Then run the commande from the folder of each microservice (except Frontend), example : 
```bash
  cd ms-patient
  mvn clean test site
```

Surefire, JaCoCo and Javadoc are available at target/site/index.html of each microservice.

# 🌱 Green code
```properties
TODO: à finaliser
```