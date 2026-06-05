package com.trabajofinal.selenium.tests;

import com.trabajofinal.selenium.base.BaseTest;
import com.trabajofinal.selenium.pages.CartPage;
import com.trabajofinal.selenium.pages.LoginPage;
import com.trabajofinal.selenium.pages.ProductsPage;
import com.trabajofinal.selenium.pages.SelectedProductPage;
import com.trabajofinal.selenium.utils.ScreenshotUtil;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.stream.Stream;

import static java.sql.DriverManager.getDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class LoginTest  extends BaseTest {
    private WebDriverWait wait;
    private LoginPage loginPage;

    // Logger
    private static final Logger logger =  LoggerFactory.getLogger(LoginTest.class);

    private static Stream<Arguments> proveerDatosDeLogin() {
        return Stream.of(
                // 1. Caso de Éxito
                Arguments.of("standard_user", "secret_sauce", "SUCCESS", "Log out"),

                // 2. Caso de Falla: Contraseña incorrecta
                Arguments.of("standard_user", "password_invalido", "FAILURE", "Wrong password."),

                // 3. Caso de Falla: Usuario inexistente
                Arguments.of("usuario_fantasma", "secret_sauce", "FAILURE", "User does not exist."),

                // 4. Caso de Falla: Campos vacíos
                Arguments.of("", "", "FAILURE", "Please fill out Username and Password.")
        );
    }


    @BeforeEach
    void init() {
        // Inicializa el navegador (Chrome o Edge) configurado en BaseTest
        super.setUp();
        // Inicializa el wait usando el driver seguro del hilo actual
        wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        driver.get(props.getProperty("url-1"));
        loginPage = new LoginPage(driver, wait);
    }

    @Test
    void testLoginExitoso() {
        loginPage.loginCompleto("standard_user", "secret_sauce");
        String textoActual = loginPage.obtenerTextoLogout();
        String textoEsperado = "Log out";
        Assertions.assertEquals(textoEsperado, textoActual,
                "¡El login falló! El botón de cierre de sesión no muestra el texto correcto.");
        System.out.println("Validación exitosa: Se detectó el botón '" + textoActual + "'.");
        System.out.println("Prueba 1 - Login Exitoso: ¡PASÓ CORRECTAMENTE!");
        ScreenshotUtil.takeScreenshot(driver, "testLoginExitoso");

    }
    @Test
    void testLoginFallido() {
        loginPage.loginCompleto("usuario_invalido", "clave_falsa");
        String errorActual = loginPage.obtenerTextoAlertNativo();
        Assertions.assertEquals("Wrong password.", errorActual, "El mensaje de la ventana emergente no es el correcto.");
        System.out.println("Validación exitosa: Se detectó error '" + errorActual + "'.");
        System.out.println("Prueba 2 - Login Fallido: ¡PASÓ CORRECTAMENTE!");
        ScreenshotUtil.takeScreenshot(driver, "testLoginFallido");

        //Escribe en el log el resultado
        logger.info("INFO: Se detecto error en el login: " + errorActual);

    }

    // =========================================================================
    // 🧪 EL TEST PARAMETRIZADO (Ejecuta un bucle por cada fila de datos)
    // =========================================================================
    @ParameterizedTest(name = "Ejecución {index} => Usuario: {0}, Escenario: {2}")
    @MethodSource("proveerDatosDeLogin") // Vincula este test con la fuente de datos anterior
    void testLoginMultiplesEscenarios(String usuario, String contrasena, String resultadoEsperado, String textoEsperado) {

        // El script interactúa usando las variables dinámicas de la fila actual
        loginPage.loginCompleto(usuario, contrasena);

        if (resultadoEsperado.equals("SUCCESS")) {
            // Validación para Login Exitoso: Verificar el botón de Logout
            String textoActual = loginPage.obtenerTextoLogout();
            Assertions.assertEquals(textoEsperado, textoActual,
                    "PASO FALLÓ: El login exitoso no muestra el botón correcto.");
            System.out.println("✅ Login Exitoso validado para: " + usuario);

        } else {
            // Validación para Login Fallido: Capturar y verificar el texto de la Alerta nativa
            String mensajeAlertaActual = loginPage.obtenerTextoAlertNativo(); // O tu método de capturar alertas

            Assertions.assertEquals(textoEsperado, mensajeAlertaActual,
                    "PASO FALLÓ: El mensaje de error de la alerta no coincide para el escenario de falla.");
            System.out.println("✅ Bloqueo de seguridad validado correctamente para: " + usuario);
        }
    }


    @AfterEach
    void cleanup(){
        super.tearDown();
    }
}
