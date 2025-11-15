# Changelog

Todos los cambios notables en este proyecto serán documentados en este archivo.
## [1.0.1] - 2024-10-01

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
