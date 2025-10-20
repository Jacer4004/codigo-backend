# codigo-backend

Microservicio desarrollado con Quarkus 3.28.2, Java 17, Maven 3.9.11, postgres 17 .
Implementa operaciones CRUD.

## Requisitos

Tener instalados:

Java 17
Maven 3.9.11
Postgres 17
Postman v9.13.2
bacuku base de datos basedatos.sql


## Ejecutar

Ejecutar por terminal

./mvnw quarkus:dev

Endpoints Persona: GET /persona GET /persona/{id} POST /persona PUT /persona/{id} DELETE /persona/{id}
Endpoints Cliente: GET /cliente GET /cliente/{id} POST /cliente PUT /cliente/a/{id} DELETE /cliente/{id}
Endpoints Cuenta: GET /cuenta GET /cuenta/{id} POST /cuenta PUT /cuenta/a/{id} DELETE /cuenta/{id}
Endpoints Movimineto: GET /movimiento GET /movimiento/retiro/{id}/{valor} GET /movimiento/deposito/{id}/{valor} GET /movimiento/reporte/{id}/{desde}/{hasta} POST /movimiento PUT /movimiento/a/{id} DELETE /movimiento/{id} 


> **_NOTE:_**  Quarkus  <http://localhost:8080/q/dev/>.


