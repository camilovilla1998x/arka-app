# Proyecto Arka

**Arka** es una empresa colombiana distribuidora de accesorios para PC, que está en proceso de expansión por Latinoamérica. Este proyecto tiene como objetivo construir un sistema backend profesional en Java con Spring Boot que automatice la gestión de productos, clientes, pedidos, stock, reportes y notificaciones.

---

## 📆 Estado del Proyecto

**Fase actual:** Desarrollo Backend – Módulos de Clientes, Productos y Categorías completos con pruebas unitarias e integración.

---

## 🌐 Tecnologías Usadas

* Java 17
* Spring Boot 3.x
* Maven
* Lombok
* JPA / Hibernate
* MySQL (base de datos relacional)
* MongoDB (para notificaciones futuras)
* Mockito + JUnit 5 (pruebas unitarias)
* MockMvc (pruebas de integración)
* MapStruct (mapeo limpio entre entidades y DTOs)

---

## 📂 Estructura del Proyecto

```
com.arka.arka_app
|
|-- controller         # Controladores REST
|-- service
|   |-- impl           # Implementaciones de servicios
|   |-- interfaces     # Interfaces de servicios
|-- repository
|   |-- mysql          # Repositorios JPA (MySQL)
|-- model
|   |-- mysql          # Entidades JPA (MySQL)
|-- dto
|   |-- customer       # DTOs para Cliente
|   |-- product        # DTOs para Producto
|   |-- category       # DTOs para Categoría
|-- mapper            # MapStruct (mapeo entre entidad y DTO)
|-- exception         # Manejo centralizado de errores
|-- test              # Pruebas unitarias y de integración
```

---

## 🔧 Funcionalidades Implementadas

### ✅ Módulo de Clientes (Customer)

* CRUD completo con rutas REST:
  * `GET /api/customers`
  * `GET /api/customers/<built-in function id>`
  * `POST /api/customers`
  * `PUT /api/customers/<built-in function id>`
  * `DELETE /api/customers/<built-in function id>`
  * `GET /api/customers/search?name=`
  * `GET /api/customers/sorted`

* Pruebas:
  * Unitarias (`CustomerServiceTest`) con Mockito
  * Integración (`CustomerControllerTest`) con MockMvc
  * Validaciones con `@Valid`
  * Excepciones centralizadas y estructuradas

### ✅ Módulo de Productos (Product)

* CRUD completo con rutas REST:
  * `GET /api/products`
  * `GET /api/products/<built-in function id>`
  * `POST /api/products`
  * `PUT /api/products/<built-in function id>`
  * `DELETE /api/products/<built-in function id>`
  * `GET /api/products/search?term=`
  * `GET /api/products/sorted`
  * `GET /api/products/price-range?min=&max=`

* Relaciones con:
  * `Category` (N:1)
  * `Supplier` (N:1)


### ✅ Módulo de Categorías (Category)

* CRUD completo:
  * Validación de nombre duplicado
  * Relación con productos
  * Métodos `existsByNameIgnoreCase()` y `findByNameIgnoreCase()`

---

## 🔄 Relaciones establecidas

| Entidad        | Relación                          |
|----------------|-----------------------------------|
| Cliente        | 1:N con `Order` y `Cart`          |
| Pedido         | N:M con `Product` (con cantidad)  |
| Producto       | N:1 con `Category` y `Supplier`   |
| Producto       | N:M con `Order`                   |
| Cart           | 1:N con `CartItem`                |
| CartItem       | N:1 con `Product` y `Cart`        |

---

## ❗ Manejo de Excepciones

Se maneja mediante `@RestControllerAdvice` con `GlobalExceptionHandler`. Se devuelve un DTO uniforme llamado `ErrorResponse`, útil para validar errores y mantener consistencia.

---

## 🧪 Testing

* Pruebas unitarias completas con Mockito
* Pruebas de integración con MockMvc
* Test de validaciones con `@Valid`
* Cobertura de errores y flujos alternativos

---

## 🧠 MapStruct

* Se implementó `@InheritInverseConfiguration` para evitar duplicación de lógica.
* Mapeo limpio y automático de DTOs.

---

## 🚧 Tareas en Proceso / Pendientes

* Finalizar relaciones y funcionalidades de:
  * Carrito (`Cart`, `CartItem`)
  * Proveedores (`Supplier`)
  * Pedidos (`Order`, `OrderItem`)
* Implementar lógica de:
  * Productos por categoría
  * Pedidos por producto
  * Carritos abandonados
  * Historial de pedidos por cliente
* Seguridad y autenticación
* Reportes automáticos y envío de notificaciones

---

## 💡 Buenas Prácticas Aplicadas

* Arquitectura por capas
* DTOs separados para input/output
* Tests organizados por servicio y controlador
* Manejo global de errores con estructura clara
* Código limpio, coherente y profesional
* Uso de Kanban + Notion para la gestión ágil del proyecto

---

## 🛠️ Requisitos para correr el proyecto

* Java 17
* MySQL 8 o superior
* MongoDB local (opcional)
* Maven 3.8+
* IDE como IntelliJ o VS Code

---

## 📖 Notas finales

> Proyecto 100% profesional, orientado a prácticas empresariales, testing completo, arquitectura limpia y documentación bien estructurada. Ideal para ambientes reales de desarrollo backend.

**Última actualización:** 2025-05-17
