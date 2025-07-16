# 💸 Transaction Payment System

Este proyecto es una API REST que gestiona transacciones y pagos, desarrollada con **programación reactiva** en Java usando **Spring WebFlux**, siguiendo los principios de **Clean Architecture** y desplegable en contenedores Docker.

---

## 📚 Tabla de Contenidos

- [🚀 Características](#-características)
- [🧪 Tecnologías Utilizadas](#-tecnologías-utilizadas)
- [🏗️ Arquitectura](#️-arquitectura)
- [⚙️ Configuración del Entorno](#-configuración-del-entorno)
- [🐳 Ejecutar con Docker](#-ejecutar-con-docker)
- [▶️ Ejecutar sin Docker](#️-ejecutar-sin-docker)
- [🧪 Pruebas](#-pruebas)
- [📂 Endpoints Principales](#-endpoints-principales)
- [📈 Monitorización](#-monitorización)
- [📄 License](#-license)

---

## 🚀 Características

- Procesamiento de pagos en orden (FIFO) con validación del monto
- Rechazo automático si el pago no cubre completamente una transacción
- Asignación automática del pago a transacciones pendientes
- MongoDB como base de datos reactiva
- Redis para cache de páginas de transacciones
- Swagger/OpenAPI para documentación
- Spring Boot Actuator para monitoreo
- Limpieza automática de caché tras cada pago

---

## 🧪 Tecnologías Utilizadas

| Tecnología       | Uso Principal                         |
|------------------|----------------------------------------|
| Java 17          | Lenguaje base                         |
| Spring WebFlux   | API reactiva                          |
| MongoDB          | Base de datos NoSQL                   |
| Redis            | Cache de resultados                   |
| Docker           | Contenedores                          |
| Docker Compose   | Orquestación de servicios             |
| Lombok           | Reducción de boilerplate              |
| MapStruct        | Mapeo entre entidades y DTOs          |
| Swagger/OpenAPI  | Documentación de API                  |
| Spring Actuator  | Métricas y healthchecks               |
| JUnit, Mockito   | Pruebas unitarias                     |

---

## 🏗️ Arquitectura

El proyecto está dividido en capas según Clean Architecture:

┌────────────────────────┐
│ entrypoints │ ← Controllers, DTOs, Swagger
├────────────────────────┤
│ usecases │ ← Casos de uso (business logic)
├────────────────────────┤
│ domain/models │ ← Entidades y lógica de dominio
├────────────────────────┤
│ infra/db │ ← MongoRepository + RedisCache
└────────────────────────┘
---

## ⚙️ Configuración del Entorno

Antes de ejecutar, asegúrate de definir un archivo `.env` con las siguientes variables:

```env
TRANSACTION_PAGE_CACHE_KEY=transactions::page:%d_size:%d
PORT=8080
MONGO_URI=mongodb://sa:sa@mongo-db-ts:27017/transactions_system_db?authSource=admin
SPRING_PROFILES_ACTIVE=prod
CACHE_TTL=300

🐳 Ejecutar con Docker
🧱 1. Construir el .jar
```bash
mvn clean package -DskipTests

🐳 2. Construir la imagen Docker
```bash
docker build -t transaction-app .

📦 3. Levantar todo con Docker Compose
```bash
docker-compose up

También puedes usar:
```bash
docker-compose up --build

Esto levantará:

La API (localhost:8080)

MongoDB (localhost:27017)

Redis (localhost:6379)

▶️ Ejecutar sin Docker
Asegúrate de tener MongoDB y Redis corriendo localmente

Crea el .env con las variables necesarias

Ejecuta la aplicación con:
```bash
mvn spring-boot:run

🧪 Pruebas
El proyecto incluye pruebas unitarias básicas en algunos casos de uso.
```bash
mvn test

🧪 Algunas pruebas unitarias aún están pendientes por cobertura completa.

📂 Endpoints Principales
Una vez corriendo, puedes acceder a la documentación Swagger:

http://localhost:8080/api/docs

🔄 Pagos
POST /api/payments: Procesar un nuevo pago

Body: { "amount": 10000.00 }

Devuelve transacciones pagadas y monto sobrante (si aplica)

📃 Transacciones
GET /api/transactions: Lista todas las transacciones (con paginación y caché)

POST /api/transactions: Crear nueva transacción

📈 Monitorización
El servicio expone métricas y estado con Spring Boot Actuator:

GET /actuator/health: Estado de salud

GET /actuator/metrics: Métricas del sistema

Docker está configurado para validar el healthcheck al iniciar correctamente el contenedor.

