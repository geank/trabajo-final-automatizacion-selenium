package com.trabajofinal.selenium.tests;

import com.trabajofinal.selenium.base.BaseTest;
import com.trabajofinal.selenium.pages.*;
import com.trabajofinal.selenium.utils.ScreenshotUtil;
import org.junit.jupiter.api.*;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class CartAndCheckoutTest extends BaseTest {
    private WebDriverWait wait;
    private LoginPage loginPage;
    private ProductsPage productsPage;
    private SelectedProductPage selectedProductPage;
    private CartPage cartPage;
    private CheckoutPage checkoutPage;
    private int precioTotalEsperado = 0;

    @BeforeEach
    void init() {
        super.setUp();
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        driver.get(props.getProperty("url-1"));
        loginPage = new LoginPage(driver, wait);
        productsPage = new ProductsPage(driver, wait);
        selectedProductPage = new SelectedProductPage(driver, wait);
        cartPage = new CartPage(driver, wait);
        precioTotalEsperado = 0;
        checkoutPage = new CheckoutPage(driver, wait);
    }

    private void prepararCarritoConItems() {
        // 1. Login
        loginPage.loginCompleto("testcompany", "Pruebastest");

        loginPage.obtenerTextoLogout();

        //cartPage.limpiarCarritoSiTieneElementos();

        productsPage.regresarAHome();

        // 2. Agregar ítem fijo (Monitor ASUS)
        productsPage.seleccionarCategoriaMonitors();
        productsPage.seleccionarMonitorAsus();

        //guardamos el precio
        precioTotalEsperado += selectedProductPage.obtenerPrecioProducto();

        selectedProductPage.hacerClicEnAgregarAlCarrito();
        selectedProductPage.obtenerTextoAlertAgregarProducto(); // Acepta la alerta

        // 3. Regresar y agregar ítem aleatorio
        productsPage.regresarAHome();
        productsPage.seleccionarProductoAleatorio();

        //guardamos el precio
        precioTotalEsperado += selectedProductPage.obtenerPrecioProducto();

        selectedProductPage.hacerClicEnAgregarAlCarrito();
        selectedProductPage.obtenerTextoAlertAgregarProducto(); // Acepta la alerta

        // 4. Ir a la pantalla del carrito
        productsPage.navegarACart();
    }

    @Test
    void testAgregarItems() {
        prepararCarritoConItems();

        // RÚBRICA: Validación de este caso específico
        Assertions.assertTrue(driver.getCurrentUrl().contains("cart.html"),
                "PASO FALLÓ: No se llegó a la pantalla del carrito.");
        System.out.println("Caso 4 - Agregar Ítems: ¡PASÓ CORRECTAMENTE!");

    }

    @Test
    void testCheckoutCompraConValidacionDeMontos() {

        prepararCarritoConItems();

        int sumaPreciosTabla = cartPage.calcularSumaPreciosDeTabla();

        int precioTotal = cartPage.obtenerPrecioTotalCarrito();

        Assertions.assertEquals(precioTotal, sumaPreciosTabla,"¡La validación falló! El monto del carrito no coincide con los productos agregados.");

        cartPage.hacerClicEnPlaceOrder();

        checkoutPage.completarFormularioOrden(
                "Gonzalo Mora",
                "Perú",
                "Arequipa",
                "4557-8823-1122-3344",
                "06",
                "2026"
        );
        String textoEsperadoExito = "Thank you for your purchase!";
        String textoActualExito = checkoutPage.obtenerMensajeConfirmacion();

        Assertions.assertEquals(textoEsperadoExito, textoActualExito,
                "PASO FALLÓ: El mensaje de confirmación de compra no es el correcto o no apareció.");

        System.out.println("🎉 ¡Compra validada exitosamente en el sistema!");

        // 5. Cerrar el modal con el botón OK
        checkoutPage.hacerClicEnOkExito();

        System.out.println("Caso 5 - Checkout Completo: ¡PASÓ CORRECTAMENTE CON ÉXITO ABSOLUTO!");
    }

    @Test
    void testCheckoutCompraSinValidacion() {

        prepararCarritoConItems();


        cartPage.hacerClicEnPlaceOrder();

        checkoutPage.completarFormularioOrden(
                "Gonzalo Mora",
                "Perú",
                "Arequipa",
                "4557-8823-1122-3344",
                "06",
                "2026"
        );
        String textoEsperadoExito = "Thank you for your purchase!";
        String textoActualExito = checkoutPage.obtenerMensajeConfirmacion();

        Assertions.assertEquals(textoEsperadoExito, textoActualExito,
                "PASO FALLÓ: El mensaje de confirmación de compra no es el correcto o no apareció.");

        System.out.println("🎉 ¡Compra validada exitosamente en el sistema!");

        // 5. Cerrar el modal con el botón OK
        checkoutPage.hacerClicEnOkExito();

        System.out.println("Caso 6 - Checkout Completo: ¡PASÓ CORRECTAMENTE CON ÉXITO ABSOLUTO!");
        ScreenshotUtil.takeScreenshot(driver, "testCheckoutCompraSinValidacion");
    }

    @Test
    void testCheckoutCompraSinCamposObligatorios() {
        prepararCarritoConItems();
        cartPage.hacerClicEnPlaceOrder();
        checkoutPage.completarFormularioOrden(
                "",
                "Perú",
                "Lima",
                "",
                "06",
                "2026"
        );
        String mensajeAlerta = checkoutPage.obtenerTextoAlertNativo();
        Assertions.assertEquals("Please fill out Name and Creditcard.", mensajeAlerta, "El mensaje de la ventana emergente no es el correcto.");
        System.out.println("Validación exitosa: Se detectó el mensaje'" + mensajeAlerta + "'.");

        System.out.println("Caso 7 - Checkout sin campos obligatorios: ¡PASÓ CORRECTAMENTE CON ÉXITO ABSOLUTO!");
        ScreenshotUtil.takeScreenshot(driver, "testCheckoutSinCamposObligatorios");
    }

    @AfterEach
    void cleanup(){
        super.tearDown();
    }
}
