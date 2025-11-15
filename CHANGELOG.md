# Changelog

Todos los cambios notables en este proyecto serán documentados en este archivo.

## [1.0.3] - 2025-15-11

### Agregado (Added)
- Se agregó el archivo `TROUBLESHOOTING.md` con una guía para solucionar problemas comunes.

### Corregido (Fixed)
- Se configuró explícitamente el `maven-compiler-plugin` en el `pom.xml` para asegurar que el proyecto se compile con la versión de Java correcta.
- Se actualizó la propiedad `java.version` a 17 en el `pom.xml`.

## [1.0.2] - 2025-15-11

### Agregado (Added)
- Se implementaron estrategias de cálculo de intereses de préstamos: `IInteresStrategy`, `InteresSimpleStrategy` e `InteresCompuestoStrategy`.
- Se implementaron estrategias de descuento de tarjetas: `DescuentoSupermercadoStrategy` y `DescuentoClienteGoldStrategy`.
- Se implementó el `TransaccionService` para registrar transacciones.

### Corregido (Fixed)
- Se actualizó la versión de Java a 17 en `pom.xml`.
- Se actualizaron las estrategias de descuento para aceptar un objeto `Cliente`.
- Se actualizó el `TarjetaServiceImpl` para pasar el `Cliente` a las estrategias de descuento.
- Se actualizó el `PrestamoServiceImpl` para usar las estrategias de interés.
- Se actualizaron los archivos `README.md` y `CHANGELOG.md`.

## [1.0.1] - 2025-15-11

### Agregado (Added)
- Se crearon tests unitarios para los controladores.
- Se agregó la base de datos H2 para testing.
- Se agregó Swagger para la documentación de la API.

### Corregido (Fixed)
- Se corrigieron errores de compilación y problemas de dependencias.
- Se corrigieron problemas con la configuración de Docker.
- Se corrigió un error tipográfico (typo) en la clase principal de la aplicación.
## [1.0.0] - 2024-10-01

### Agregado (Added)
- Se inicializó un proyecto Spring Boot usando `curl`.
- Se creó un archivo `docker-compose.yml` para la base de datos MySQL.
- Se configuró el archivo `application.properties`.
- Se creó la estructura de paquetes.
- Se crearon las entidades JPA: `Cliente`, `Cuenta`, `Tarjeta`, `Prestamo` y `Transaccion`.
- Se crearon los enums: `TipoCuenta`, `TipoTarjeta`, `EstadoPrestamo` y `TipoTransaccion`.
- Se crearon los repositorios: `ClienteRepository`, `CuentaRepository`, `TarjetaRepository`, `PrestamoRepository` y `TransaccionRepository`.
- Se crearon los DTOs: `ClienteDTO`, `RespuestaBalanceDTO`, `SolicitudCompraDTO`, `SolicitudPrestamoDTO` y `PagoCuotaDTO`.
- Se crearon las interfaces e implementaciones del patrón Strategy: `IDescuentoStrategy`, `DescuentoPorMontoStrategy`, `IAprobacionStrategy` y `AprobacionScoreCrediticioStrategy`.
- Se creó el `LoanFactory`.
- Se creó el `BankingFacadeService` y su implementación.
- Se crearon los servicios: `ClienteService`, `CuentaService`, `TarjetaService` y `PrestamoService` y sus implementaciones.
- Se crearon los controladores: `ClienteController`, `CuentaController`, `TarjetaController` y `PrestamoController`.
- Se creó el archivo `logback-spring.xml`.
