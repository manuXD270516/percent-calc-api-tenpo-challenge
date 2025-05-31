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
- ✅ **[Documentación Postman aquí](https://documenter.getpostman.com/view/2654210/2sB2qgddg1)**

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

## 🔧 Tecnologías y Librerías Utilizadas

### 🧪 Testing

Se utilizó **JUnit 5** y **Mockito** para implementar pruebas unitarias con cobertura de servicios, controladores y lógica de caché.

```xml
<!-- JUnit 5 -->
<dependency>
  <groupId>org.junit.jupiter</groupId>
  <artifactId>junit-jupiter</artifactId>
  <version>5.10.0</version>
  <scope>test</scope>
</dependency>

<!-- Mockito -->
<dependency>
  <groupId>org.mockito</groupId>
  <artifactId>mockito-core</artifactId>
  <version>5.11.0</version>
  <scope>test</scope>
</dependency>
```

---

### ☕ Manejo de caché

Se utilizó la librería **Caffeine** para implementar un mecanismo de caché en memoria, configurado para almacenar el porcentaje durante 30 minutos y ser utilizado como respaldo si el servicio externo falla.

```xml
<dependency>
  <groupId>com.github.ben-manes.caffeine</groupId>
  <artifactId>caffeine</artifactId>
  <version>3.1.8</version>
</dependency>
```

Configuración del `CacheManager` y uso en `PercentageCache` con recuperación segura y validación de existencia.

---

### 🛑 Manejo de errores

La estrategia de manejo de errores utiliza `@ControllerAdvice` y `@ExceptionHandler` para interceptar excepciones y retornar respuestas controladas con códigos HTTP apropiados. Se manejan errores de:

- Servicio externo no disponible
- Cache vacía
- Parámetros inválidos
- Errores internos no controlados

Ejemplo:

```java
@ExceptionHandler(IllegalStateException.class)
public ResponseEntity<ApiError> handleIllegalState(IllegalStateException ex) {
    return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
                         .body(new ApiError("Dependencia no disponible", ex.getMessage()));
}
```

Esto asegura una comunicación clara hacia el cliente y separación de responsabilidades en la lógica de negocio.

## 📎 Recursos

- 🧪 [Documentación de Postman](https://documenter.getpostman.com/view/2654210/2sB2qgddg1)
- 📄 Swagger UI: [http://localhost:8080/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html)
- 🐳 Docker Hub: `https://hub.docker.com/repository/docker/manudevops94/percentage-api`

---

# Bonus adicionales
## 🚀 Despliegue en Render con Docker Hub

### 📦 Publicar la imagen en Docker Hub

Antes de desplegar, asegúrate de que tu imagen esté publicada en Docker Hub. Puedes hacerlo con los siguientes comandos:

```bash
# 1. Taguear tu imagen local con tu nombre de usuario y nombre de repo
docker tag percentage-calc-api:latest docker.io/<tu-usuario>/percentage-calc-api:latest

# 2. Iniciar sesión en Docker Hub
docker login

# 3. Subir la imagen
docker push docker.io/<tu-usuario>/percentage-calc-api:latest
```

> Reemplaza `<tu-usuario>` por tu nombre de usuario real en Docker Hub.

---

### ⚙️ Configuración del render.yaml

Crea un archivo llamado `render.yaml` en la raíz del proyecto con el siguiente contenido:

```yaml
services:
  - type: web
    name: percentage-api
    env: docker
    plan: free
    image: docker.io/<tu-usuario>/percentage-calc-api:latest
    autoDeploy: false
    envVars:
      - key: SPRING_PROFILES_ACTIVE
        value: prod
      - key: DB_HOST
        fromService:
          name: postgres-db
          type: database
          property: host
      - key: DB_PORT
        value: 5432
      - key: DB_NAME
        value: challenge
      - key: DB_USER
        value: postgres
      - key: DB_PASSWORD
        fromService:
          name: postgres-db
          type: database
          property: password

  - type: database
    name: postgres-db
    plan: free
    properties:
      engine: postgres
      version: 15
```

---

### 🚀 Instrucciones para desplegar

1. Crea un repositorio en GitHub si aún no lo tienes.
2. Sube el archivo `render.yaml` a la raíz del proyecto.
3. Entra a [https://dashboard.render.com](https://dashboard.render.com).
4. Haz clic en "New +" → "Blueprint".
5. Conecta tu repo y Render detectará el archivo `render.yaml`.
6. Selecciona "Apply" para lanzar el despliegue.
7. Render descargará la imagen directamente desde Docker Hub y configurará la base de datos.

---

### ✅ Notas adicionales

- El parámetro `autoDeploy: false` evita que se reconstruya el servicio si haces cambios en el repositorio. Solo cambiará si actualizas la imagen en Docker Hub.
- Si haces `docker push` con una nueva imagen y quieres que Render la use, puedes reiniciar el servicio manualmente desde el dashboard.





## 👨‍💻 Autor

Desarrollado por **Manuel Saavedra** como parte de un challenge técnico.
---

