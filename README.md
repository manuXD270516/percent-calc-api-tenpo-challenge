# 🧮 percentage-calc-api

API REST para realizar cálculos con porcentaje dinámico, con historial de llamadas, manejo de errores, documentación Swagger y despliegue con Docker y PostgreSQL.

---

## 📌 Características principales

- ✅ **Cálculo con porcentaje dinámico** (suma + % desde un mock externo o caché)
- ✅ **Caché del porcentaje** en memoria (30 min con Caffeine)
- ✅ **Historial de llamadas** persistente (timestamp, endpoint, parámetros, respuesta/error)
- ✅ **Manejo global de errores**
- ✅ **Documentación interactiva Swagger**
- ✅ **Pruebas unitarias con JUnit y Mockito**
- ✅ **Dockerización y `docker-compose` para entorno local y remoto**
- ✅ **[Documentación Postman](https://documenter.getpostman.com/view/2654210/2sB2qgddg1)**
- ✅ **[Documentacion Swagger (Local)](http://localhost:8080/swagger-ui/index.html)**

---

## 🧪 Endpoints principales

- `POST /api/v1/calculate` – Realiza cálculo con porcentaje dinámico
- `GET /mock/percentage` – Mock de porcentaje (devuelve entre 5% y 10%)
- `GET /api/v1/history` – Historial paginado de llamadas

---

## 🚀 Ejecución rápida con Docker

### Requisitos

- Docker y Docker Compose
- Maven (solo si vas a compilar manualmente)

### Opción 1: Despliegue local (build desde código)

```bash
docker-compose up --build
```

Esto usará:
- `Dockerfile`
- `docker-compose.yml` + `docker-compose.override.yml`
- Imagen: `percentage-api:local`

### Opción 2: Despliegue con imagen desde Docker Hub

```bash
docker-compose -f docker-compose.yml -f docker-compose.prod.yml up
```

Esto descargará la imagen publicada:

```
manudevops94/percentage-calc-api-percentage-api:latest
```

---

## 🐳 Archivos de Docker relevantes

- `Dockerfile`: Build multistage (Maven + JDK 21)
- `docker-compose.yml`: Base (PostgreSQL)
- `docker-compose.override.yml`: Desarrollo local
- `docker-compose.prod.yml`: Uso de imagen Docker Hub

---

## ⚙️ Configuración (application.yml)

```yaml
spring:
  datasource:
    url: ${SPRING_DATASOURCE_URL:jdbc:postgresql://localhost:5432/challenge}
    username: ${SPRING_DATASOURCE_USERNAME:postgres}
    password: ${SPRING_DATASOURCE_PASSWORD:postgres}
    driver-class-name: org.postgresql.Driver
  jpa:
    hibernate:
      ddl-auto: ${SPRING_JPA_HIBERNATE_DDL_AUTO:update}
```

Variables externas inyectadas automáticamente desde Docker Compose.

---

## 🧪 Pruebas

```bash
./mvnw test
```

Incluye cobertura de:
- Servicios (`PercentageServiceImpl`, `CalculationServiceImpl`)
- Controladores (`CalculationController`, `HistoryController`, `MockPercentageController`)
- Cache (`PercentageCache`)

---

## 📎 Recursos

- 🧪 [Documentación de Postman](https://documenter.getpostman.com/view/2654210/2sB2qgddg1)
- 📄 Swagger UI: [http://localhost:8080/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html)
- 🐳 Docker Hub: `https://hub.docker.com/repository/docker/manudevops94/percentage-api`

---

## 👨‍💻 Autor

Desarrollado por **@manudevops94** como parte de un challenge técnico.
