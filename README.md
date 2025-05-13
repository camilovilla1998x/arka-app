# Proyecto Arka

**Arka** es una empresa colombiana distribuidora de accesorios para PC, que está en proceso de expansión por Latinoamérica. Este proyecto tiene como objetivo construir un sistema backend profesional en Java con Spring Boot que automatice la gestión de productos, clientes, pedidos, stock, reportes y notificaciones.

---

## 📆 Estado del Proyecto

**Fase actual:** Desarrollo Backend - Módulo de Clientes terminado con pruebas unitarias completas.

---

## 🌐 Tecnologías Usadas

* Java 17
* Spring Boot 3.x
* Maven
* Lombok
* JPA / Hibernate
* MySQL (base de datos relacional)
* MongoDB (para notificaciones)
* Mockito + JUnit 5 (pruebas unitarias)
* MockMvc (para pruebas de controladores - pendiente)
* MapStruct (para mapeo limpio entre entidades y DTOs)

---

## 📂 Estructura del Proyecto

```
com.arka.arka_app
|
|-- controller         # Controladores REST
|-- service
|   |-- impl           # Implementaciones de servicios
|-- repository
|   |-- mysql          # Repositorios JPA (MySQL)
|-- model
|   |-- mysql          # Entidades JPA (MySQL)
|-- dto
|   |-- customer       # DTOs para Cliente
|-- mapper            # MapStruct (mapeo entre entidad y DTO)
|-- exception         # Manejo centralizado de errores
|-- config            # Configuración adicional (ya sin ModelMapper)
|-- test              # Pruebas unitarias y de integración
```

---

## 🔧 Funcionalidades Implementadas

### ✅ Módulo de Clientes (Customer)

* CRUD completo con rutas REST:

  * `GET /customers` - Obtener todos
  * `GET /customers/{id}` - Obtener por ID
  * `POST /customers` - Crear nuevo cliente
  * `PUT /customers/{id}` - Actualizar cliente
  * `DELETE /customers/{id}` - Eliminar cliente
  * `GET /customers/search?name=` - Buscar por nombre parcial
  * `GET /customers/ordered` - Listar ordenado alfabéticamente

* Uso de DTOs para entrada (`CustomerRequestDTO`) y salida (`CustomerResponseDTO`)

* Mapeo limpio con MapStruct:

  * Configurado con `@Mapper(componentModel = "spring")`
  * Métodos bidireccionales: entity <-> DTO
  * Soporte para listas

* Pruebas unitarias 100% cubiertas en `CustomerServiceTest`

* Cobertura de escenarios positivos y negativos

### ⏳ Pendiente:

* MockMvc tests para `CustomerController`
* Módulo de Productos (Product)
* Manejo de pedidos y stock
* Autenticación y roles
* Reportes y notificaciones (MongoDB)

---

## 💡 Buenas Prácticas Aplicadas

* Arquitectura por capas
* Separación de responsabilidades clara
* Uso de DTOs y mappers para desacoplar datos
* Pruebas con Mockito organizadas usando `Arrange / Act / Assert`
* Documentación clara del código y comentarios profesionales
* Nombres limpios, coherentes y legibles

---

## 📅 Progreso del Proyecto

* [x] Modelado de base de datos en MySQL Workbench
* [x] Creación del esquema desde Spring Boot
* [x] Integración con MongoDB para notificaciones
* [x] Módulo de Clientes implementado y probado
* [ ] Controladores probados con MockMvc
* [ ] Módulo de Productos y Pedidos
* [ ] Seguridad y autenticación
* [ ] Reportes automáticos y notificaciones

---

## 🎓 Autor

> Este proyecto es desarrollado por mi autoría como parte de mi ruta de aprendizaje profesional backend con Spring Boot y metodología ágil Kanban.

---

## 🛠️ Requisitos para correr el proyecto

* Java 17
* MySQL 8 o superior
* MongoDB local (opcional por ahora)
* Maven 3.8+
* Editor (VS Code, IntelliJ, etc.)

---

## 📖 Notas finales

> Este proyecto tiene como objetivo practicar un enfoque empresarial completo, aplicando principios de **código limpio**, **testing**, **arquitectura escalable** y **documentación profesional**. Ideal para entornos reales de desarrollo backend.
