# Spring Boot Microservices Test

A simple microservices project built with Java and Spring Boot.

The project contains two services:

- **auth-api** — user registration, login, JWT authentication, and processing requests.
- **data-api** — receives internal requests from auth-api and transforms text.

PostgreSQL is used to store users and processing history.

## Tech Stack

- Java 21
- Spring Boot 3.3.4
- Spring Security
- Spring Data JPA
- PostgreSQL
- JWT
- Maven
- Docker
- Docker Compose

## Architecture

```text
Client
  |
  v
auth-api :8080
  |
  +------> PostgreSQL
  |
  | X-Internal-Token
  v
data-api :8081
```

The client sends requests to `auth-api`.

`auth-api` uses JWT authentication and communicates with `data-api`.
`data-api` checks the internal token and transforms the received text.

## Run the Project

### 1. Build auth-api

```bash
mvn -f auth-api/pom.xml clean package -DskipTests
```

### 2. Build data-api

```bash
mvn -f data-api/pom.xml clean package -DskipTests
```

### 3. Start the containers

```bash
docker compose up -d --build
```

### 4. Check the containers

```bash
docker compose ps
```

The services run on:

- `auth-api` — port `8080`
- `data-api` — port `8081`

To stop the project:

```bash
docker compose down
```

## Test the API

The examples below use PowerShell.

### 1. Register a User

```powershell
$body = @{
    email = "test@example.com"
    password = "password123"
} | ConvertTo-Json

Invoke-WebRequest `
    -Uri "http://localhost:8080/api/auth/register" `
    -Method POST `
    -ContentType "application/json" `
    -Body $body `
    -UseBasicParsing
```

Expected status:

```text
201 Created
```

### 2. Login

```powershell
$body = @{
    email = "test@example.com"
    password = "password123"
} | ConvertTo-Json

$response = Invoke-WebRequest `
    -Uri "http://localhost:8080/api/auth/login" `
    -Method POST `
    -ContentType "application/json" `
    -Body $body `
    -UseBasicParsing

$response.Content
```

Expected status:

```text
200 OK
```

The response contains a JWT token:

```json
{
  "token": "<JWT_TOKEN>"
}
```

### 3. Process Text

Copy the JWT token from the login response:

```powershell
$token = "<JWT_TOKEN>"

$body = @{
    text = "hello"
} | ConvertTo-Json

Invoke-WebRequest `
    -Uri "http://localhost:8080/api/process" `
    -Method POST `
    -ContentType "application/json" `
    -Headers @{
        "Authorization" = "Bearer $token"
    } `
    -Body $body `
    -UseBasicParsing
```

Expected status:

```text
200 OK
```

Expected response:

```json
{
  "result": "HELLO"
}
```

## Request Flow

```text
Register
   ↓
Login
   ↓
Get JWT
   ↓
POST /api/process
   ↓
auth-api validates JWT
   ↓
auth-api calls data-api
   ↓
data-api validates X-Internal-Token
   ↓
hello → HELLO
   ↓
ProcessingLog is saved in PostgreSQL
```