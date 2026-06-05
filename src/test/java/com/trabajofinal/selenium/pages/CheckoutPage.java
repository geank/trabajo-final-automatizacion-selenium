package com.trabajofinal.selenium.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class CheckoutPage {
    private final WebDriver driver;
    private final WebDriverWait wait;

    // LOCALIZADORES del formulario (usando los IDs del HTML)
    private final By txtName = By.id("name");
    private final By txtCountry = By.id("country");
    private final By txtCity = By.id("city");
    private final By txtCard = By.id("card");
    private final By txtMonth = By.id("month");
    private final By txtYear = By.id("year");

    // Botón para procesar la compra
    private final By btnPurchase = By.xpath("//button[text()='Purchase']");

    //modal final
    private final By lblSuccessTitle = By.xpath("//h2[text()='Thank you for your purchase!']");
    private final By btnOkSuccess = By.xpath("//button[contains(@class,'confirm') and text()='OK']");

    public CheckoutPage(WebDriver driver, WebDriverWait wait) {
        this.driver = driver;
        this.wait = wait;
    }

    public void completarFormularioOrden(String nombre, String pais, String ciudad, String tarjeta, String mes, String anio) {


        wait.until(ExpectedConditions.visibilityOfElementLocated(txtName)).sendKeys(nombre);


        driver.findElement(txtCountry).sendKeys(pais);
        driver.findElement(txtCity).sendKeys(ciudad);
        driver.findElement(txtCard).sendKeys(tarjeta);
        driver.findElement(txtMonth).sendKeys(mes);
        driver.findElement(txtYear).sendKeys(anio);

        // Hacemos clic en el botón de compra
        wait.until(ExpectedConditions.elementToBeClickable(btnPurchase)).click();
    }

    public String obtenerMensajeConfirmacion() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(lblSuccessTitle)).getText();
    }

    public void hacerClicEnOkExito() {
        wait.until(ExpectedConditions.elementToBeClickable(btnOkSuccess)).click();
    }
}
