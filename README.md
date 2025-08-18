# Medilabo Solutions

# Project Overview
```properties
TODO: à quoi sert l'application
```

# Technologies
```properties
TODO: à finaliser
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
- **Git** (Versionning)
- **Jacoco** (Code Coverage)
- **JUnit 5**
- **Mockito**
- **JaCoCo** (Code Coverage)

## Architecture
```properties
TODO: schéma de l'architecture microservices\
  pour chaque microservice : description, techno utilisée, port
```

# Getting Started
The project can be run locally or in a Docker container.

## Prerequisites for Local installation:
- Java (21)
- Maven (3.9.11)
- MySQL (8.0)
- MongoDB (8.0)
- Node (22.18)
- NPM (10.9.3)

## Prerequisites for Docker installation:
- Docker
- Docker Compose (2.39.1)

## Project architecture
```properties
TODO: à finaliser
```
```properties
cros-paul-Medilabo/
├── 📂client-ui/
│   └── 📂frontend/                           # Microservice Frontend
├── 📂ms-config/                              # Service de configuration
├── 📂ms-eureka/                              # Discovery service
├── 📂ms-gateway/                             # Gateway between frontend & backend
├── 📂ms-note/                                # Microservice Note
├── 📂ms-patient/                             # Microservice Patient
├── 📂ms-riskassessment/                      # Microservice Risk Assessment
├── 📝secrets_ms_config_git_credentials.json  # Git credentials for config service
└── 📝.env                                    # Environnement variables for Local & Docker installation
```
## Clone the project

```bash
  git clone https://github.com/paulc/cros-paul-Medilabo.git
```
## Deployment in 'Local' mode

Create a MySQL database
```SQL 
CREATE DATABASE medilabo_patient;
```
_Note: data aren't persistes between restarts_

Create a MongoDB database with sample data.  
Before running the script, open it and modify the username and password, you will need them for the .env file.
```js
let username = "";
let password = "";
...
```
Run the script with mongo shell.
```BASH
  mongosh < medilabo_note/src/main/resources/mongodb-init-local.js
```
_Note: data are persisted between restarts, you can re-run the script to re-create the data._

Create an.env file at the root of the project (see 'Project architecture')
```properties
# MySQL
MYSQL_USER=
MYSQL_PASSWORD=

# MongoDB
MONGO_INITDB_DATABASE_USER=
MONGO_INITDB_DATABASE_PASSWORD=

# MS-Gateway: authentification
MS_GATEWAY_SECURITY_USER=
MS_GATEWAY_SECURITY_PASSWORD=

### Do Not modify the code below this ###
SPRING_PROFILES_ACTIVE=local
```

Start each microservice from his folder, example: 
```bash
  cd ms-eureka
  mvn spring-boot:run
```
Please respect this order :
- ms-eureka
- ms-note, ms-patient, ms-riskassessment
- ms-gateway

_Note: in local mode, the ms-config isn't needed because configurations are stored locally (See application-local.properties in corresponding microservice)._ 

Start the Frontend : 
```bash
  cd client-ui/frontend/
  npm install
  npm run dev
```

Access the frontend at http://localhost:9080/


## Deployment in 'Docker' mode

Create an.env file at the root of the project (see 'Project architecture')
```properties
# MySQL
MYSQL_USER=
MYSQL_PASSWORD=

# MongoDB
MONGO_INITDB_DATABASE_USER=
MONGO_INITDB_DATABASE_PASSWORD=

# MS-Gateway: authentification
MS_GATEWAY_SECURITY_USER=
MS_GATEWAY_SECURITY_PASSWORD=

### Do Not modify the code below this ###
SPRING_PROFILES_ACTIVE=docker
```

Copy the file secrets_ms_config_git_credentials.json at the root of the project (see architecture).  
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

Start the project, from the root of the project:
```bash
  # Build the images
  docker-compose build
  # Start the project in detached mode
  docker-compose up -d
  # Stop the project
  docker-compose down
```

_Note, containers are started in this order:
- ms-eureka, dbMySQL, dbMongoDB
- ms-config
- ms-note, ms-patient, ms-riskassessment
- ms-gateway
- Frontend
Databases are persisted between restarts._

Access the frontend at http://localhost:9080/


# Unitary tests, code coverage and documentation :

Use the .env file from the 'Local' mode deployment.  
Run the commande from the root of each microservice (except Frontend), example : 
```bash
  cd ms-patient
  mvn clean test site
```

Surefire, JaCoCo and Javadoc are available at target/site/index.html of each microservice.

# Green code
```properties
TODO: à finaliser
```