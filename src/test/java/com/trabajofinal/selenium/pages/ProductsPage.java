package com.trabajofinal.selenium.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;
import java.util.Random;

public class ProductsPage {
    private final WebDriver driver;
    private final WebDriverWait wait;

    private final By btnCategoriaMonitor = By.xpath("(//div[@class='list-group']/a)[4]");
    private final By btnMonitorAsus = By.xpath("//div[@id='tbodyid']//a[@href='prod.html?idp_=14']");
    private final By btnMenuHome = By.xpath("//a[contains(text(),'Home')]");
    private final By btnMenuCart = By.xpath("//a[contains(text(),'Cart')]");
    private final By linksProductos = By.xpath("//div[@id='tbodyid']//h4[@class='card-title']/a");

    public ProductsPage(WebDriver driver, WebDriverWait wait) {
        this.driver = driver;
        this.wait = wait;
    }
    public void seleccionarCategoriaMonitors(){
        wait.until(ExpectedConditions.elementToBeClickable(btnCategoriaMonitor)).click();
        try {
            Thread.sleep(3500); // Pausa de seguridad de 03 segundos para la carga asíncrona de JavaScript
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        wait.until(ExpectedConditions.visibilityOfElementLocated(btnMonitorAsus));
    }
    public void seleccionarMonitorAsus(){
        wait.until(ExpectedConditions.elementToBeClickable(btnMonitorAsus)).click();
        wait.until(ExpectedConditions.urlContains("prod.html?idp_=14"));
    }

    public void regresarAHome() {
        wait.until(ExpectedConditions.elementToBeClickable(btnMenuHome)).click();
        // Esperamos a que la URL limpie los parámetros y vuelva al index
        wait.until(ExpectedConditions.urlContains("index.html"));
    }

    public void navegarACart() {
        wait.until(ExpectedConditions.elementToBeClickable(btnMenuCart)).click();
        // Esperamos a que la URL limpie los parámetros y vuelva al index
        wait.until(ExpectedConditions.urlContains("cart.html"));
    }

    public String seleccionarProductoAleatorio() {
        // 1. Esperar a que por lo menos un producto sea visible en el catálogo
        wait.until(ExpectedConditions.presenceOfElementLocated(linksProductos));

        // 2. Recuperar la lista completa de elementos coincidentes en el DOM
        List<WebElement> listaDeProductos = driver.findElements(linksProductos);
        int cantidadProductos = listaDeProductos.size();

        System.out.println("📊 Productos disponibles para elegir al azar: " + cantidadProductos);

        // 3. Generar un índice aleatorio entre 0 y el total de productos encontrados
        Random random = new Random();
        int indiceAleatorio = random.nextInt(cantidadProductos);

        // 4. Extraer el elemento escogido y su texto para el reporte
        WebElement productoEscogido = listaDeProductos.get(indiceAleatorio);
        String nombreProducto = productoEscogido.getText();

        System.out.println("🎲 Índice seleccionado al azar: " + indiceAleatorio + " -> Producto: " + nombreProducto);

        // 5. Hacer clic en el producto seleccionado aleatoriamente
        wait.until(ExpectedConditions.elementToBeClickable(productoEscogido)).click();

        // Esperamos a que la URL cambie a la página de detalles (prod.html)
        wait.until(ExpectedConditions.urlContains("prod.html"));

        return nombreProducto;
    }
}
