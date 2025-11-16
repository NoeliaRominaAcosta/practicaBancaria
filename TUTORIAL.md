# Tutorial: API Core Bancario

Este tutorial te guiará a través del proceso de probar la API Core Bancario, conectarte a la base de datos y entender los patrones de diseño y principios utilizados en el proyecto.

## 1. Cómo Probar Cada Endpoint

Para probar los endpoints, puedes usar una herramienta como `curl` o Postman. Asegúrate de que la aplicación esté en ejecución antes de ejecutar los comandos.

### 1.1. Crear un Nuevo Cliente

* **Endpoint**: `POST /clientes`
* **Descripción**: Crea un nuevo cliente.
* **Comando `curl`**:
    ```bash
    curl -X POST -H "Content-Type: application/json" -d '{
        "nombre": "John Doe",
        "email": "john.doe@example.com",
        "scoreCrediticio": 600
    }' http://localhost:8080/clientes
    ```
* **Respuesta esperada**:
    ```json
    {
        "id": 1,
        "nombre": "John Doe",
        "email": "john.doe@example.com",
        "scoreCrediticio": 600,
        "cuentas": null
    }
    ```

### 1.2. Obtener Saldo de Cuenta

* **Endpoint**: `GET /cuentas/{id}/balance`
* **Descripción**: Obtiene el saldo de una cuenta específica.
* **Comando `curl`**:
    ```bash
    curl http://localhost:8080/cuentas/1/balance
    ```
* **Respuesta esperada**:
    ```json
    {
        "balance": 1000.0
    }
    ```
  **Nota**: Necesitas tener una cuenta con id 1 en la base de datos para que esto funcione.

### 1.3. Realizar una Compra con Tarjeta

* **Endpoint**: `POST /tarjetas/{id}/compra`
* **Descripción**: Realiza una compra con una tarjeta específica.
* **Comando `curl`**:
    ```bash
    curl -X POST -H "Content-Type: application/json" -d '{
        "monto": 100.0
    }' http://localhost:8080/tarjetas/1/compra
    ```
* **Respuesta esperada**: Un estado HTTP 200 OK.
  **Nota**: Necesitas tener una tarjeta con id 1 en la base de datos para que esto funcione.

### 1.4. Solicitar un Préstamo

* **Endpoint**: `POST /prestamos/solicitar`
* **Descripción**: Un cliente solicita un préstamo.
* **Comando `curl`**:
    ```bash
    curl -X POST -H "Content-Type: application/json" -d '{
        "clienteId": 1,
        "monto": 10000,
        "tipoPrestamo": "PERSONAL"
    }' http://localhost:8080/prestamos/solicitar
    ```
* **Respuesta esperada**:
    ```json
    {
        "id": 1,
        "monto": 10000,
        "tasaInteres": 0.1,
        "estado": "APROBADO",
        "cliente": {
            "id": 1,
            "nombre": "John Doe",
            "email": "john.doe@example.com",
            "scoreCrediticio": 600,
            "cuentas": []
        }
    }
    ```

### 1.5. Pagar una Cuota de Préstamo

* **Endpoint**: `POST /prestamos/{id}/pagar_cuota`
* **Descripción**: Paga una cuota de un préstamo.
* **Comando `curl`**:
    ```bash
    curl -X POST -H "Content-Type: application/json" -d '{
        "monto": 500
    }' http://localhost:8080/prestamos/1/pagar_cuota
    ```
* **Respuesta esperada**: Un estado HTTP 200 OK.
  **Nota**: Necesitas tener un préstamo con id 1 en la base de datos para que esto funcione.

## 2. Cómo Conectarse a la Base de Datos con Docker

El proyecto utiliza una base de datos MySQL que se ejecuta en un contenedor de Docker. Para iniciar la base de datos, puedes usar el archivo `docker-compose.yml`.

1.  **Iniciar la base de datos**:
    ```bash
    docker-compose up -d
    ```
2.  **Conectarse a la base de datos**:
    Puedes usar cualquier cliente de base de datos para conectarte. Aquí están los detalles de conexión:
    * **Host**: `localhost`
    * **Puerto**: `3307`
    * **Base de datos**: `core_bancario`
    * **Usuario**: `user`
    * **Contraseña**: `password`

## 3. Cómo Acceder a los Datos de la Base de Datos

Una vez que estés conectado a la base de datos, puedes consultar las tablas para ver los datos. Aquí están las tablas en la base de datos:

* `cliente`
* `cuenta`
* `tarjeta`
* `prestamo`
* `transaccion`

Puedes usar consultas SQL estándar para acceder a los datos. Por ejemplo, para ver todos los clientes, puedes ejecutar la siguiente consulta:

## 4. Estrategias y Patrones

Este proyecto utiliza varios patrones de diseño para asegurar un código base limpio y mantenible.

### 4.1. Patrón Strategy (Estrategia)

El Patrón Strategy se utiliza para lógicas de negocio intercambiables.

*   **Cálculo de Intereses de Préstamos**: La interfaz `IInteresStrategy` define el contrato para calcular los intereses de los préstamos. Las clases `InteresSimpleStrategy` e `InteresCompuestoStrategy` proporcionan diferentes implementaciones de esta interfaz. El `PrestamoServiceImpl` utiliza un mapa de estas estrategias para seleccionar la correcta en función del monto del préstamo.
*   **Descuentos de Tarjetas**: La interfaz `IDescuentoStrategy` define el contrato para aplicar descuentos a las compras con tarjeta. Las clases `DescuentoPorMontoStrategy`, `DescuentoSupermercadoStrategy` y `DescuentoClienteGoldStrategy` proporcionan diferentes implementaciones de esta interfaz. El `TarjetaServiceImpl` inyecta una lista de estas estrategias y aplica las que son aplicables.

**¿Cómo sabe Spring qué estrategia inyectar?**

En el `TarjetaServiceImpl`, se inyecta una `List<IDescuentoStrategy>`. Cuando se inicia la aplicación, el contenedor de inyección de dependencias de Spring escanea el classpath en busca de componentes (clases anotadas con `@Component`, `@Service`, `@Repository`, etc.). Encuentra todos los beans que implementan la interfaz `IDescuentoStrategy` y los recopila en una lista. Esta lista luego se inyecta en el `TarjetaServiceImpl`.

En el método `realizarCompra`, el código itera a través de esta lista de estrategias y comprueba si cada una es aplicable. De esta manera, el compilador no sabe qué estrategia llamar. Es el contenedor de inyección de dependencias de Spring en tiempo de ejecución el que proporciona todas las estrategias disponibles, y el código en `TarjetaServiceImpl` itera a través de ellas para decidir cuál usar en función del método `esAplicable`.

**Ejemplo práctico:**

Supongamos que tenemos dos estrategias de descuento:

1.  `DescuentoPorMontoStrategy`: Esta estrategia ofrece un descuento si el monto de la compra supera un cierto umbral (p. ej., $5000).
2.  `DescuentoClienteGoldStrategy`: Esta estrategia ofrece un descuento si el cliente es un cliente "Gold" (p. ej., su puntaje de crédito es superior a 700).

Cuando se realiza una compra, el código itera a través de la lista de estrategias:

1.  **Primera iteración**: La variable `strategy` es una instancia de `DescuentoPorMontoStrategy`. El código llama a `strategy.esAplicable(cliente, monto)`. El método `esAplicable` de `DescuentoPorMontoStrategy` verificará si el `monto` es mayor que $5000.
    *   Si es así, el método devuelve `true` y se aplica el descuento.
    *   Si no es así, el método devuelve `false` y no sucede nada.

2.  **Segunda iteración**: La variable `strategy` es una instancia de `DescuentoClienteGoldStrategy`. El código llama a `strategy.esAplicable(cliente, monto)`. El método `esAplicable` de `DescuentoClienteGoldStrategy` verificará si el puntaje de crédito del `cliente` es mayor que 700.
    *   Si es así, el método devuelve `true` y se aplica el descuento.
    *   Si no es así, el método devuelve `false` y no sucede nada.

La declaración `if` es la que decide qué estrategia aplicar. El método `esAplicable` de cada estrategia contiene la lógica para determinar si esa estrategia debe aplicarse a la compra actual.

Esta es una característica poderosa del Patrón de Estrategia combinado con la inyección de dependencias. Permite agregar nuevas estrategias de descuento simplemente creando una nueva clase que implemente la interfaz `IDescuentoStrategy` y anotándola con `@Component`. No es necesario modificar el `TarjetaServiceImpl` en absoluto, lo que hace que el código sea más mantenible y extensible, siguiendo el Principio Abierto/Cerrado.

### 4.2. Patrón Factory (Fábrica)

El Patrón Factory se utiliza para crear objetos complejos.

*   **Creación de Préstamos**: La clase `LoanFactory` se utiliza para crear diferentes tipos de objetos `Prestamo` según la solicitud del cliente. Utiliza una declaración `switch` con coincidencia de patrones para crear el tipo de préstamo correcto.

### 4.3. Patrón Builder (Constructor)

El Patrón Builder se utiliza para la construcción de las entidades principales para asegurar su creación en un estado válido.

*   **Entidades `Cliente` y `Prestamo`**: Las entidades `Cliente` y `Prestamo` tienen una clase anidada `Builder` que se utiliza para construir los objetos paso a paso.

### 4.4. Patrón Facade (Fachada)

El Patrón Facade se utiliza para simplificar operaciones complejas.

*   **`BankingFacadeService`**: El `BankingFacadeService` simplifica el proceso de solicitud de un préstamo. Orquesta las llamadas al `ClienteRepository`, `LoanFactory` e `IAprobacionStrategy` para realizar la operación en un solo método.

## 5. Principios SOLID y Código Limpio

El proyecto se desarrolla con los siguientes principios en mente:

*   **Principio de Responsabilidad Única (SRP)**: Cada clase tiene una única responsabilidad. Por ejemplo, el `ClienteController` solo es responsable de manejar las solicitudes HTTP relacionadas con los clientes.
*   **Principio de Abierto/Cerrado (OCP)**: El código está abierto para la extensión pero cerrado para la modificación. Por ejemplo, se pueden agregar nuevas estrategias de descuento sin modificar el `TarjetaServiceImpl`.
*   **Principio de Sustitución de Liskov (LSP)**: Los subtipos son sustituibles por sus tipos base. Por ejemplo, cualquier implementación de `IDescuentoStrategy` se puede utilizar en el `TarjetaServiceImpl`.
*   **Principio de Segregación de Interfaces (ISP)**: Las interfaces son pequeñas y enfocadas. Por ejemplo, la interfaz `IInteresStrategy` solo tiene un método.
*   **Principio de Inversión de Dependencias (DIP)**: Los módulos de alto nivel no dependen de los módulos de bajo nivel. Ambos dependen de abstracciones. Por ejemplo, el `PrestamoServiceImpl` depende de la interfaz `IInteresStrategy`, no de las implementaciones concretas.

También se siguen las prácticas de **Código Limpio**, como el uso de nombres claros y descriptivos para variables y métodos, mantener los métodos pequeños y enfocados, y usar DTO para transferir datos entre capas.