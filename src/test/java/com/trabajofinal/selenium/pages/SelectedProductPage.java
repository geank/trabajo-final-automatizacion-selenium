package com.trabajofinal.selenium.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class SelectedProductPage {
    private final WebDriver driver;
    private final WebDriverWait wait;

    private final By btnAgregarAlCarrito = By.xpath("//a[text()='Add to cart']");

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



}
