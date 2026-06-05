package com.trabajofinal.selenium.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class SelectedProductPage {
    private final WebDriver driver;
    private final WebDriverWait wait;

    private final By btnAgregarAlCarrito = By.xpath("//a[text()='Add to cart']");
    private final By lblPrecio = By.className("price-container");
    public SelectedProductPage(WebDriver driver, WebDriverWait wait) {
        this.driver = driver;
        this.wait = wait;
    }

    public void hacerClicEnAgregarAlCarrito() {
        // Aseguramos que el botón sea completamente visible y clickeable en pantalla
        wait.until(ExpectedConditions.elementToBeClickable(btnAgregarAlCarrito)).click();
    }
    public String obtenerTextoAlertAgregarProducto() {
        org.openqa.selenium.Alert alerta = wait.until(ExpectedConditions.alertIsPresent());
        String textAlert = alerta.getText();
        alerta.accept();
        return textAlert;
    }
    public int obtenerPrecioProducto() {
        // 1. Esperar a que el contenedor del precio sea visible
        String textoCompleto = wait.until(ExpectedConditions.visibilityOfElementLocated(lblPrecio)).getText();

        // 2. Usamos una expresión regular para eliminar todo lo que NO sea un número
        String soloNumeros = textoCompleto.replaceAll("[^0-9]", "");
        // 3. Convertimos el String limpio a un entero
        int precio = Integer.parseInt(soloNumeros);
        System.out.println("💰 Precio capturado y convertido: $" + precio);
        return precio;
    }



}
