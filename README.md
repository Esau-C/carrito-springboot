# Carrito de Compras - Spring Boot

Aplicacion monolitica de carrito de compras desarrollada con Spring Boot y MySQL.

## Tecnologias utilizadas

- Java 21
- Spring Boot
- Spring Data JPA
- Thymeleaf
- MySQL
- Bootstrap
- SweetAlert2

---

## Funcionalidades

- Mostrar productos
- Agregar productos al carrito
- Eliminar productos
- Aumentar y disminuir cantidades
- Control de stock
- Productos agotados
- Confirmacion visual de compra
- Calculo total de compra
- Persistencia en base de datos MySQL

---

## Base de datos

Crear la base de datos:

```sql
CREATE DATABASE carrito_compras;
```

---

## Configuracion

Editar el archivo:

```properties
src/main/resources/application.properties
```

Configurar:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/carrito_compras
spring.datasource.username=root
spring.datasource.password=TU_PASSWORD
```

---

## Ejecutar proyecto

Desde Visual Studio Code ejecutar:

```text
CarritoApplication.java
```

Luego abrir:

```text
http://localhost:8080
```

---

## Arquitectura utilizada

Patron MVC:

- Model
- View
- Controller

---

## Autor

Esau Contreras