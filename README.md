# API de Core Bancario

Esta es una API RESTful para una simulación de core bancario, construida con Java 17 y Spring Boot 3. El proyecto simula operaciones bancarias básicas (Cuentas, Tarjetas y Préstamos) y es un ejemplo de código de nivel Senior, adhiriéndose estrictamente a los principios SOLID, Código Limpio (Clean Code), y demostrando el uso eficaz de Patrones de Diseño.

## 1. Stack Tecnológico y Configuración

* **Lenguaje**: Java 21
* **Framework**: Spring Boot 3.x (usando Web, Data JPA, Validation)
* **Base de Datos**: MySQL 8
* **Contenerización**: La base de datos MySQL debe ejecutarse exclusivamente a través de un archivo `docker-compose.yml` preconfigurado con un usuario, contraseña y base de datos inicial.
* **Build**: Maven

## 2. Arquitectura y Requisitos de Calidad (Nivel Senior)

* **Principios SOLID**: El diseño refleja los 5 principios SOLID.
* **Patrones de Diseño (Implementación Obligatoria)**:
    * **Patrón Strategy (Estrategia)**: Usado para lógicas de negocio intercambiables.
        * **Ejemplo 1**: Múltiples estrategias para calcular intereses de préstamos (ej. `InteresSimpleStrategy`, `InteresCompuestoStrategy`).
        * **Ejemplo 2**: Múltiples estrategias de descuento para tarjetas (ej. `DescuentoSupermercadoStrategy`, `DescuentoClienteGoldStrategy`).
    * **Patrón Factory (Fábrica) (o Factory Method)**: Usado para crear objetos complejos.
        * **Ejemplo**: Un `LoanFactory` que crea diferentes tipos de objetos `Prestamo` según la solicitud del cliente.
    * **Patrón Builder (Constructor)**: Usado para la construcción de las entidades principales (ej. `Cliente`, `Prestamo`) para asegurar su creación en un estado válido.
    * **Patrón Facade (Fachada)**: Se crea un `BankingFacadeService` para simplificar operaciones complejas (ej. `solicitarPrestamo` puede involucrar verificar al cliente, consultar su score crediticio y crear la solicitud, todo en un único método de fachada).
* **Transacciones ACID**: Todas las operaciones que modifican la base de datos (transferencias, pagos, solicitudes de préstamos) deben ser transaccionales (`@Transactional`) para asegurar atomicidad, consistencia, aislamiento y durabilidad (ACID). Si una parte de la operación falla (ej. fondos insuficientes), la transacción completa debe revertirse (rollback).
* **Código Limpio (Clean Code)**: El código debe ser legible, con nombres claros de variables y métodos, y DTOs (Data Transfer Objects) para las capas de la API.

## 3. Lógica de Dominio y Funcionalidades (Core Bancario)

Se deben modelar las siguientes entidades JPA:

* `Cliente` (id, nombre, email, score_crediticio)
* `Cuenta` (id, numeroCuenta, balance, tipo [AHORRO, CORRIENTE], cliente_id)
* `Tarjeta` (id, numero, tipo [DEBITO, CREDITO], limite, cuenta_id)
* `Prestamo` (id, monto, tasaInteres, estado [PENDIENTE, APROBADO, RECHAZADO], cliente_id)
* `Transaccion` (id, monto, fecha, tipo [PAGO_TARJETA, PAGO_PRESTAMO, DEPOSITO])

### Endpoints/Casos de Uso:

#### Módulo de Cuentas:

* `POST /clientes`: Crear un nuevo cliente.
* `GET /cuentas/{id}/balance`: Obtener el saldo de una cuenta.

#### Módulo de Tarjetas (Con Lógica de Negocio):

* `POST /tarjetas/{id}/compra`: Realizar una compra con una tarjeta.
    * **Lógica**: Debe chequear el límite (crédito) o saldo (débito).
    * **Lógica (Patrón Strategy)**: Aplicar un descuento si la compra cumple una condición. El servicio debe inyectar una `List<IDescuentoStrategy>` y probar cuál aplica. (ej. `DescuentoPorMontoStrategy`: si la compra > 5000, 5% de descuento).

#### Módulo de Préstamos (Con Lógica de Negocio):

* `POST /prestamos/solicitar`: Un cliente solicita un préstamo.
    * **Lógica (Patrón Factory)**: El DTO de solicitud especificará un `tipo_prestamo` ("PERSONAL", "ADELANTO"). El `LoanFactory` creará la entidad `Prestamo` correcta.
    * **Lógica (Patrón Strategy)**: El servicio debe usar una `IAprobacionStrategy` para decidir si el préstamo se aprueba (ej. `AprobacionScoreCrediticioStrategy` que chequea el `score_crediticio` del cliente).
* `POST /prestamos/{id}/pagar_cuota`: Pagar una cuota del préstamo.
    * **Lógica**: Debe calcular el interés correspondiente a esa cuota y debitarlo de la cuenta del cliente (Debe ser ACID).

## 4. Uso de Características Modernas (Java 17)

* **Records**: Se utilizan Java Records para todos los DTOs (ej. `SolicitudPrestamoDTO`, `RespuestaBalanceDTO`).
* **Pattern Matching para switch**: Usado en cualquier Factory o Strategy que dependa de un tipo (ej. `switch(tipoDePrestamo)`).
* **Virtual Threads (Project Loom)**: El servidor (Tomcat) está configurado para usar Virtual Threads para manejar las peticiones API, explicando su beneficio en un entorno de alta concurrencia de E/S (I/O) (como las llamadas a base de datos).

## 5. Logging (Gestión de Logs)

* Integra SLF4J con Logback (el estándar de Spring Boot, más moderno que JAX-RS para este propósito).
* Se configura un `logback-spring.xml`.
* **Requisito**: Registrar logs (INFO) en cada entrada de controlador, (DEBUG) para lógica de negocio clave (ej. "Calculando interés para préstamo X"), y (WARN o ERROR) para transacciones fallidas o excepciones (ej. "Intento de compra sin fondos en cuenta Y").

## 6. Swagger

El proyecto usa Swagger para la documentación del API. Una vez que la aplicación está corriendo, se puede acceder a la documentación en `http://localhost:8080/swagger-ui.html`.

## Cómo ejecutar la aplicación

1.  Iniciar la base de datos usando `docker-compose up -d`.
2.  Ejecutar la aplicación usando `mvn spring-boot:run`.