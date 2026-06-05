package com.trabajofinal.selenium.tests;

import com.trabajofinal.selenium.base.BaseTest;
import com.trabajofinal.selenium.pages.CartPage;
import com.trabajofinal.selenium.pages.LoginPage;
import com.trabajofinal.selenium.pages.ProductsPage;
import com.trabajofinal.selenium.pages.SelectedProductPage;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class AddToCartTest extends BaseTest {
    private WebDriverWait wait;
    private LoginPage loginPage;
    private ProductsPage productsPage;
    private SelectedProductPage selectedProductPage;
    private CartPage cartPage;

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
        productsPage.regresarAHome();

        Assertions.assertTrue(driver.getCurrentUrl().contains("index.html"),
                "PASO FALLÓ: No se pudo regresar a la lista de ítems principal.");

        String productoAleatorio = productsPage.seleccionarProductoAleatorio();

        Assertions.assertTrue(driver.getCurrentUrl().contains("prod.html?idp_="),
                "PASO FALLÓ: No se redirigió a la ficha de detalles tras el clic aleatorio.");

        selectedProductPage.hacerClicEnAgregarAlCarrito();

        Assertions.assertEquals(textoEsperadoItem, selectedProductPage.obtenerTextoAlertAgregarProducto(),
                "PASO FALLÓ: No se pudo agregar al carrito el producto aleatorio: " + productoAleatorio);

        System.out.println("Prueba Finalizada: ¡Se combinó un producto fijo y uno aleatorio con éxito!");

        productsPage.navegarACart();

        Assertions.assertTrue(driver.getCurrentUrl().contains("cart.html"),
                "PASO FALLÓ: No se pudo regresar al carrito.");
    }

    @AfterEach
    void cleanup(){
        super.tearDown();
    }
}
