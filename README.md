# Proyecto Final: Framework de Automatización con Selenium WebDriver

Este proyecto comprende el diseño e implementación de un marco de trabajo de automatización de pruebas (Test Automation Framework) robusto y profesional para la plataforma e-commerce **DemoBlaze**. Aplica el patrón de diseño **Page Object Model (POM)**, aserciones avanzadas de negocio y pruebas guiadas por datos (*Data-Driven Testing*).

---

## 🚀 Arquitectura y Características Clave

- **Page Object Model (POM):** Separación absoluta entre la lógica de interfaz de las páginas web (`Pages`) y la definición de las validaciones (`Tests`), garantizando un mantenimiento limpio y escalable.
- **Data-Driven Testing (DDT):** Implementación de pruebas parametrizadas utilizando las bondades nativas de JUnit 5 (`@ParameterizedTest` y `@MethodSource`) para evaluar múltiples combinaciones de credenciales en un solo bloque de código.
- **Auditoría Dinámica de Montos (Anti-Desfase de Servidor):** El script recolecta dinámicamente las filas de productos existentes en la tabla del carrito, calcula su suma en tiempo de ejecución y la contrasta contra el indicador general de cobranza de la página web. Esto blinda el test contra falsos negativos por persistencia de datos previos en el servidor.
- **Gestión Asíncrona de Evidencias:** Captura automática de imágenes del estado de la aplicación mediante un componente utilitario (`ScreenshotUtil`) optimizado de forma secuencial para ejecutarse libre de interrupciones ante alertas nativas de JavaScript.

---

## 🛠️ Tecnologías Utilizadas

- **Lenguaje:** Java 18
- **Framework de Pruebas:** JUnit 5 (Jupiter)
- **Herramienta de Automatización:** Selenium WebDriver 4.23.0
- **Gestor de Dependencias y Construcción:** Gradle
- **Patrón de Logging:** Logback Classic (para auditoría interna del motor en consola)

---

## 👥 Integrantes del equipo

- **Gaitan Aldave, Luis Orlando**
- **Morales Alarcon, Ronald**
- **Morales Castro, Gean Carlos**
- **Pantoja Cruz, Mayra Deisy**
- **Roque Bolivar, Katherin**
---
## ⚙️ Configuración del Entorno y Cambio de Navegador

El marco de trabajo permite alternar de forma transparente el navegador web de ejecución y el modo de renderizado (visual o en segundo plano / Headless) sin modificar el código fuente de Java.

Para configurar estas propiedades, edita el archivo de configuración localizado en el directorio de recursos (`src/test/resources/config.properties` o equivalente):

```properties
# =========================================================================
# CONFIGURACIÓN DINÁMICA DE EJECUCIÓN
# =========================================================================

# Define el navegador de ejecución (Opciones soportadas: chrome / firefox)
browser=chrome
# browser=firefox

# URL base del entorno de pruebas
url-1=[https://www.demoblaze.com](https://www.demoblaze.com)

# Modo de ejecución de la interfaz gráfica (true = Segundo plano sin ventana / false = Modo visible)
headless=false

```

## 🧪 Cobertura y Detalle de la Suite de Pruebas

La cobertura de automatización se encuentra distribuida estratégicamente en los siguientes módulos y casos de prueba:

### 1. Módulo: `LoginTest`
* **`testLoginExitoso` (Caso 1):** Flujo básico positivo de autenticación utilizando credenciales válidas. Valida la correcta transición de la sesión verificando la presencia del componente `Log out` y genera su respectiva captura de pantalla.
* **`testLoginFallido` (Caso 2):** Flujo negativo que controla el rechazo de accesos con datos inválidos. Intercepta de forma asíncrona la alerta nativa del navegador para comprobar el mensaje de error `Wrong password.`.
* **`testLoginMultiplesEscenarios` (Caso 3):** Implementación de *Data-Driven Testing*. Utiliza un flujo parametrizado que consume un `Stream` de argumentos en memoria para evaluar secuencialmente escenarios de éxito, contraseñas erróneas, usuarios inexistentes y validaciones de campos vacíos.

### 2. Módulo: `CartAndCheckoutTest`
* **`testAgregarItems` (Caso 4):** Flujo modular de adición de productos que combina la selección de un artículo fijo (Monitor ASUS) con un producto aleatorio del catálogo del sistema, validando que el usuario finalice dentro de la URL del carrito (`cart.html`).
* **`testCheckoutCompraConValidacionDeMontos` (Caso 5):** El caso de prueba más robusto de la suite. Ejecuta una auditoría física en el DOM sumando los precios de los artículos de la tabla y realizando un `assertEquals` contra el elemento `#totalp` del banner. Llena el formulario de pago, valida el mensaje de éxito final `Thank you for your purchase!` y toma evidencias fotográficas secuenciales.
* **`testCheckoutCompraSinValidacion` (Caso 6):** Proceso directo de conversión que realiza el circuito de checkout y compra de productos utilizando datos válidos sin ejecutar la auditoría matemática previa en la tabla del carrito.
* **`testCheckoutCompraSinCamposObligatorios` (Caso 7):** Prueba de robustez del checkout. Envía el formulario omitiendo los datos requeridos de Nombre y Tarjeta de Crédito para validar que el sistema dispare correctamente el bloqueo y la alerta flotante nativa: `Please fill out Name and Creditcard.`.

---

## 🏃 Instrucciones de Ejecución

Abre la terminal de comandos (CMD, PowerShell o Git Bash) en la raíz del proyecto y ejecuta las tareas de Gradle correspondientes según el escenario que desees evaluar.

### 1. Ejecutar un caso de prueba específico

#### 🔐 Pruebas de Login (LoginTest)

| Escenario | Comando |
|-----------|---------|
| **Login exitoso** | `gradle clean test --tests "com.trabajofinal.selenium.tests.LoginTest.testLoginExitoso"` |
| **Login fallido** (usuario inválido) | `gradle clean test --tests "com.trabajofinal.selenium.tests.LoginTest.testLoginFallido"` |
| **Múltiples escenarios de login** (parametrizado) | `gradle clean test --tests "com.trabajofinal.selenium.tests.LoginTest.testLoginMultiplesEscenarios"` |

#### 🛒 Pruebas de Carrito y Checkout (CartAndCheckoutTest)

| Escenario | Comando |
|-----------|---------|
| **Agregar ítems al carrito** (Caso 4) | `gradle clean test --tests "com.trabajofinal.selenium.tests.CartAndCheckoutTest.testAgregarItems"` |
| **Checkout con validación de montos** (Caso 5) | `gradle clean test --tests "com.trabajofinal.selenium.tests.CartAndCheckoutTest.testCheckoutCompraConValidacionDeMontos"` |
| **Checkout sin validación de montos** (Caso 6) | `gradle clean test --tests "com.trabajofinal.selenium.tests.CartAndCheckoutTest.testCheckoutCompraSinValidacion"` |
| **Checkout con campos obligatorios vacíos** (Caso 7 – Ejemplo que mostraste) | `gradle clean test --tests "com.trabajofinal.selenium.tests.CartAndCheckoutTest.testCheckoutCompraSinCamposObligatorios"` |

### 2. Ejecutar todos los tests de una clase

```bash
# Todos los tests de LoginTest
gradle clean test --tests "com.trabajofinal.selenium.tests.LoginTest"

# Todos los tests de CartAndCheckoutTest
gradle clean test --tests "com.trabajofinal.selenium.tests.CartAndCheckoutTest"
