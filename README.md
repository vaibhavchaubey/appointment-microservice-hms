# Appointment Microservice HMS

[![Java](https://img.shields.io/badge/Java-21-orange)](https://openjdk.org/projects/jdk/21/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.5.4-brightgreen)](https://spring.io/projects/spring-boot)
[![Spring Cloud](https://img.shields.io/badge/Spring%20Cloud-2025.0.0-blue)](https://spring.io/projects/spring-cloud)
[![MySQL](https://img.shields.io/badge/MySQL-8-blue)](https://www.mysql.com/)
[![Docker](https://img.shields.io/badge/Docker-Ready-blue)](https://www.docker.com/)
[![Eureka](https://img.shields.io/badge/Netflix%20Eureka-Client-lightgrey)](https://cloud.spring.io/spring-cloud-netflix/)

## 🚀 What the Project Does

`appointment-microservice-hms` is a Spring Boot-based microservice for hospital appointment management in a hospital management ecosystem. It handles scheduling, canceling, and querying medical appointments and integrates with doctor/patient profile services through OpenFeign. The service provides aggregated analytics (visit counts, reasons, monthly trends) and appointment record/prescription endpoints for doctor workflows. Designed for scale and service discovery, it works with Eureka and MySQL backend in a containerized infrastructure.

## 💡 Why the Project is Useful

- Provides a dedicated appointment operations API decoupled from other HMS domains (profiles, billing, etc.).
- Supports patient and doctor endpoints for retrieving history, pending visits, and treatment records.
- Enables downstream dashboard and analytics usage with built-in summary endpoints (visit counts, reason counts).
- Supports fault handling and standardized error payloads for resilient distributed systems.

## ✨ Key Features

- Designed to schedule and cancel appointments (`/appointment/schedule`, `/appointment/cancel/{id}`).
- Built to retrieve appointment details and cross-service patient/doctor names (`/appointment/get/details/{id}`).
- Implemented patient/doctor-specific lookup endpoints (`/appointment/getAllByPatient/{id}`, `/appointment/getAllByDoctor/{id}`).
- Optimized analytics endpoints: visits by patient/doctor, total counts, reason counts.
- Integrated appointment reports, prescription and medicine retrieval via `/appointment/report/**` endpoints.
- Configured with Spring Security and JWT support scaffolding for protected APIs.

## 🛠️ Tech Stack

- Frontend: (Not included in this module) expects a separate client app.
- Backend:
  - Java 21
  - Spring Boot 3.5.4
  - Spring Data JPA
  - Spring Web / WebFlux
  - Spring Security
  - Spring Cloud Eureka Client
  - Spring Cloud OpenFeign
  - JWT (`io.jsonwebtoken`)
- Database:
  - MySQL (via `mysql-connector-j`)
- DevOps / Tools:
  - Maven
  - Docker (Dockerfile present)
  - Spring Boot Maven Plugin

## ⚙️ Getting Started (Installation & Setup)

```bash
git clone https://github.com/vaibhavchaubey/appointment-microservice-hms.git
cd appointment-microservice-hms
```

### 1. Configure Database

Set environment variables, or edit `src/main/resources/application.properties`:

- `DB_URL` (e.g., `jdbc:mysql://localhost:3306/appointmentdb`)
- `DB_USERNAME`
- `DB_PASSWORD`

### 2. Configure Eureka

- `EUREKA_SERVER_URL` default: `http://localhost:8761/eureka/`
- `PROFILE_MICROSERVICE_URL` default: `http://localhost:9100`

### 3. Run Locally

```bash
./mvnw clean package
./mvnw spring-boot:run
```

Service starts on port `9200` by default (set `PORT` env var to override).

### 4. API Endpoints (Key)

#### Appointment

- POST `/appointment/schedule`
- PUT `/appointment/cancel/{appointmentId}`
- GET `/appointment/get/{appointmentId}`
- GET `/appointment/get/details/{appointmentId}`
- GET `/appointment/getAllByPatient/{patientId}`
- GET `/appointment/getAllByDoctor/{doctorId}`
- GET `/appointment/countByPatient/{patientId}`
- GET `/appointment/countByDoctor/{doctorId}`
- GET `/appointment/visitCount`
- GET `/appointment/countReasonsByPatient/{patientId}`
- GET `/appointment/countReasonsByDoctor/{doctorId}`
- GET `/appointment/countReasons`
- GET `/appointment/getMedicinesByPatient/{patientId}`
- GET `/appointment/today`

#### Appointment Report & Prescription

- POST `/appointment/report/create`
- PUT `/appointment/report/update`
- GET `/appointment/report/getByAppointmentId/{appointmentId}`
- GET `/appointment/report/getDetailsByAppointmentId/{appointmentId}`
- GET `/appointment/report/getById/{recordId}`
- GET `/appointment/report/getRecordsByPatientId/{patientId}`
- GET `/appointment/report/isRecordExists/{appointmentId}`
- GET `/appointment/report/getPrescriptionsByPatientId/{patientId}`
- GET `/appointment/report/getAllPrescriptions`
- GET `/appointment/report/getMedicinesByPrescriptionId/{prescriptionId}`

## 🧪 Tests

```bash
./mvnw test
```

## 🚢 Docker (Optional)

Build image:

```bash
docker build -t appointment-microservice-hms:latest .
```

Run container:

```bash
docker run -e DB_URL=jdbc:mysql://host.docker.internal:3306/appointmentdb \
  -e DB_USERNAME=root -e DB_PASSWORD=yourpass \
  -e EUREKA_SERVER_URL=http://host.docker.internal:8761/eureka/ \
  -p 9200:9200 appointment-microservice-hms:latest
```

## 📦 Project Metadata

- Name: appointment-microservice-hms
- Description: Appointment Microservice for Hospital Management System
- Artifact: `com.hms:appointment-microservice-hms:0.0.1-SNAPSHOT`

## 🤝 Contributing

1. Fork the repo
2. Create a feature branch
3. Add tests and run `./mvnw test`
4. Open PR with description and linked issue

## 📝 License

Add your open source / corporate license file at `/LICENSE` (e.g., Apache-2.0, MIT, etc.).
