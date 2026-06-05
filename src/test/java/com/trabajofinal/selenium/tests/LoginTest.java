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
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static java.sql.DriverManager.getDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class LoginTest  extends BaseTest {
    private WebDriverWait wait;
    private LoginPage loginPage;
    private ProductsPage productsPage;
    private SelectedProductPage selectedProductPage;
    private CartPage cartPage;

    // Logger
    private static final Logger logger =  LoggerFactory.getLogger(LoginTest.class);

    @BeforeEach
    void init() {
        // Inicializa el navegador (Chrome o Edge) configurado en BaseTest
        super.setUp();
        // Inicializa el wait usando el driver seguro del hilo actual
        wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        driver.get(props.getProperty("url-1"));
        loginPage = new LoginPage(driver, wait);
        productsPage = new ProductsPage(driver,wait);
        selectedProductPage = new SelectedProductPage(driver, wait);
        cartPage = new CartPage(driver,wait);
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
    @Test
    void testAgregarItems(){
        //login
        loginPage.loginCompleto("standard_user", "secret_sauce");
        String textoActual = loginPage.obtenerTextoLogout();
        String textoEsperado = "Log out";
        Assertions.assertEquals(textoEsperado, textoActual,
                "¡El login falló! El botón de cierre de sesión no muestra el texto correcto.");
        productsPage.seleccionarCategoriaMonitors();
        productsPage.seleccionarMonitorAsus();

        String urlEsperada = "https://www.demoblaze.com/prod.html?idp_=14";
        String urlActual = driver.getCurrentUrl();

        Assertions.assertEquals(urlEsperada, urlActual,
                "PASO FALLÓ: El clic en el monitor ASUS no redirigió a la URL de detalles correcta.");

        selectedProductPage.hacerClicEnAgregarAlCarrito();

        String textoEsperadoItem = "Product added.";
        String textoTtem = selectedProductPage.obtenerTextoAlertAgregarProducto();

        Assertions.assertEquals(textoEsperadoItem, textoTtem,
                "PASO FALLÓ: El clic de agregar carrito no funciono.");
        cartPage.navegarACarrito();
        System.out.println("Prueba 1 - Login Exitoso: ¡PASÓ CORRECTAMENTE!");

    }
    @AfterEach
    void cleanup(){
        super.tearDown();
    }
}
