package com.trabajofinal.selenium.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class ProductsPage {
    private final WebDriver driver;
    private final WebDriverWait wait;

    private final By btnCategoriaMonitor = By.xpath("(//div[@class='list-group']/a)[4]");
    private final By btnMonitorAsus = By.xpath("//div[@id='tbodyid']//a[@href='prod.html?idp_=14']");
    public ProductsPage(WebDriver driver, WebDriverWait wait) {
        this.driver = driver;
        this.wait = wait;
    }
    public void seleccionarCategoriaMonitors(){
        wait.until(ExpectedConditions.elementToBeClickable(btnCategoriaMonitor)).click();
        try {
            Thread.sleep(5500); // Pausa de seguridad de 1.5 segundos para la carga asíncrona de JavaScript
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        wait.until(ExpectedConditions.visibilityOfElementLocated(btnMonitorAsus));
    }
    public void seleccionarMonitorAsus(){
        wait.until(ExpectedConditions.elementToBeClickable(btnMonitorAsus)).click();
        wait.until(ExpectedConditions.urlContains("prod.html?idp_=14"));
    }
}
