Proyecto: API de Core Bancario (Simulación)

Construye una API RESTful de "Core Bancario" utilizando Java 21 y Spring Boot 3. El proyecto debe simular operaciones bancarias básicas (Cuentas, Tarjetas y Préstamos) y debe ser un ejemplo de código de nivel Senior, adhiriéndose estrictamente a los principios SOLID, Clean Code y demostrando el uso efectivo de Patrones de Diseño.

## 1. Stack Tecnológico y Configuración

Lenguaje: Java 21.

Framework: Spring Boot 3.x (usando Web, Data JPA, Validation).

Base de Datos: MySQL 8.

Contenerización: La base de datos MySQL debe levantarse exclusivamente a través de un archivo docker-compose.yml preconfigurado con un usuario, contraseña y base de datos inicial.

Build: Maven o Gradle.

## 2. Requerimientos de Arquitectura y Calidad (Nivel Senior)

Principios SOLID: El diseño debe reflejar los 5 principios SOLID.

Patrones de Diseño (Implementación Obligatoria):

Patrón Strategy: Usar para lógicas de negocio intercambiables.

Ejemplo 1: Múltiples estrategias para calcular intereses de préstamos (ej. InteresSimpleStrategy, InteresCompuestoStrategy).

Ejemplo 2: Múltiples estrategias de descuento para tarjetas (ej. DescuentoSupermercadoStrategy, DescuentoClienteGoldStrategy).

Patrón Factory (o Factory Method): Usar para la creación de objetos complejos.

Ejemplo: Una LoanFactory que crea diferentes tipos de objetos Prestamo según la solicitud del cliente.

Patrón Builder: Usar para la construcción de las entidades principales (ej. Cliente, Prestamo) para asegurar su creación en un estado válido.

Patrón Facade: Crear un BankingFacadeService que simplifique operaciones complejas (ej. solicitarPrestamo puede implicar verificar al cliente, consultar su score crediticio y crear la solicitud, todo en un solo método de fachada).

Transacciones ACID: Todas las operaciones que modifiquen la base de datos (transferencias, pagos, solicitud de préstamos) deben ser transaccionales (@Transactional) para garantizar la atomicidad, consistencia, aislamiento y durabilidad (ACID). Si una parte de la operación falla (ej. no hay fondos), toda la transacción debe hacer rollback.

Clean Code: El código debe ser legible, con nombres de variables y métodos claros, y DTOs (Data Transfer Objects) para las capas de API.

## 3. Lógica de Dominio y Funcionalidades (Core Bancario)

Se deben modelar las siguientes entidades JPA:



Cliente (id, nombre, email, score_crediticio)

Cuenta (id, numeroCuenta, balance, tipo [AHORRO, CORRIENTE], cliente_id)

Tarjeta (id, numero, tipo [DEBITO, CREDITO], limite, cuenta_id)

Prestamo (id, monto, tasaInteres, estado [PENDIENTE, APROBADO, RECHAZADO], cliente_id)

Transaccion (id, monto, fecha, tipo [PAGO_TARJETA, PAGO_PRESTAMO, DEPOSITO])

Endpoints/Casos de Uso:



Módulo de Cuentas:

POST /clientes: Crear un nuevo cliente.

GET /cuentas/{id}/balance: Obtener balance de una cuenta.

Módulo de Tarjetas (con Lógica de Negocio):

POST /tarjetas/{id}/compra: Realiza una compra con tarjeta.

Lógica: Debe verificar el límite (crédito) o el balance (débito).

Lógica (Patrón Strategy): Aplicar un descuento si la compra cumple una condición. El servicio debe inyectar una List<IDescuentoStrategy> y probar cuál aplica. (Ej. DescuentoPorMontoStrategy: si la compra > 5000, 5% de descuento).

Módulo de Préstamos (con Lógica de Negocio):

POST /prestamos/solicitar: Un cliente solicita un préstamo.

Lógica (Patrón Factory): El DTO de solicitud especificará un tipo_prestamo ("PERSONAL", "ADELANTO"). La LoanFactory creará la entidad Prestamo correcta.

Lógica (Patrón Strategy): El servicio debe usar una IAprobacionStrategy para decidir si el préstamo se aprueba (ej. AprobacionScoreCrediticioStrategy que chequea el score_crediticio del cliente).

POST /prestamos/{id}/pagar_cuota: Paga una cuota del préstamo.

Lógica: Debe calcular el interés correspondiente a esa cuota y debitarlo de la cuenta del cliente (Debe ser ACID).

## 4. Uso de Features Modernas (Java 21)

Records: Usar Records de Java para todos los DTOs (ej. SolicitudPrestamoDTO, RespuestaBalanceDTO).

Pattern Matching for switch: Usar en cualquier Factory o Strategy que dependa de un tipo (ej. switch(tipoDePrestamo)).

Virtual Threads (Project Loom): Configurar el servidor (Tomcat) para usar Virtual Threads y manejar las peticiones de la API, explicando su beneficio en un entorno de alta concurrencia I/O (como llamadas a base de datos).

## 5. Logging (Administración de Logs)

Integrar SLF4J con Logback (el estándar de Spring Boot, más moderno que JAX-RS para este fin).

Configurar un logback-spring.xml.

Requerimiento: Registrar logs (INFO) en cada entrada de controlador, (DEBUG) para lógica de negocio clave (ej. "Calculando interés para préstamo X"), y (WARN o ERROR) para transacciones fallidas o excepciones (ej. "Intento de compra sin fondos en cuenta Y").
