package com.trabajofinal.selenium.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

public class CartPage {
    private final WebDriver driver;
    private final WebDriverWait wait;
    private final By linksDelete = By.xpath("//a[text()='Delete']");
    private final By lblTotal = By.id("totalp");
    private final By btnMenuCart = By.xpath("//a[contains(text(),'Cart')]");
    private final By btnPlaceOrder = By.xpath("//button[text()='Place Order']");
    private final By celdasPrecios = By.xpath("//tbody[@id='tbodyid']/tr/td[3]");
    public CartPage(WebDriver driver, WebDriverWait wait) {
        this.driver = driver;
        this.wait = wait;
    }

    public void limpiarCarritoSiTieneElementos() {
        navegarACarrito();

        // Buscamos si existen botones de "Delete" en la tabla
        List<WebElement> botonesDelete = driver.findElements(linksDelete);

        while (!botonesDelete.isEmpty()) {
            System.out.println("🧹 Limpiando residuo de prueba anterior...");

            // Hacemos clic en el primer botón "Delete" disponible
            wait.until(ExpectedConditions.elementToBeClickable(linksDelete)).click();

            // Esperamos un momento corto a que la fila desaparezca del DOM (asíncrono)
            try {
                Thread.sleep(1500);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }

            // Volvemos a buscar la lista para ver si quedan más filas
            botonesDelete = driver.findElements(linksDelete);
        }
        System.out.println("✨ El carrito está completamente limpio y listo para el test.");
    }
    public void navegarACarrito(){
        wait.until(ExpectedConditions.elementToBeClickable(btnMenuCart)).click();
        // Esperamos a que la URL limpie los parámetros y vuelva al index
        wait.until(ExpectedConditions.urlContains("cart.html"));
    }

    public int  obtenerPrecioTotalCarrito(){

        wait.until(ExpectedConditions.visibilityOfElementLocated(lblTotal));
        wait.until(driver -> !driver.findElement(lblTotal).getText().trim().isEmpty());

        String textoPrecioTotal = driver.findElement(lblTotal).getText();
        String soloNumeros = textoPrecioTotal.replaceAll("[^0-9]", "");

        if (soloNumeros.isEmpty()) return 0;
        // 3. Convertimos el String  a un entero
        return Integer.parseInt(soloNumeros);

    }
    public void hacerClicEnPlaceOrder() {
        wait.until(ExpectedConditions.elementToBeClickable(btnPlaceOrder)).click();
    }
    public int calcularSumaPreciosDeTabla() {
        // 1. Esperamos a que la tabla tenga al menos un elemento o que el contenedor total esté visible
        wait.until(ExpectedConditions.visibilityOfElementLocated(lblTotal));

        // 2. Capturamos todas las celdas de precios que existan en este instante
        List<WebElement> listaPrecios = driver.findElements(celdasPrecios);
        int sumaCalculada = 0;


        // 3. Iteramos por cada celda, limpiamos el texto y lo sumamos
        for (WebElement celda : listaPrecios) {
            String textoPrecio = celda.getText().trim();
            if (!textoPrecio.isEmpty()) {
                // Filtramos por seguridad para dejar solo dígitos
                int precioItem = Integer.parseInt(textoPrecio.replaceAll("[^0-9]", ""));
                sumaCalculada += precioItem;
                System.out.println("   -> Artículo en carrito: $" + precioItem);
            }
        }

        System.out.println("Suma total: $" + sumaCalculada);
        return sumaCalculada;
    }

}
