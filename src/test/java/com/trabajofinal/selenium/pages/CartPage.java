package com.trabajofinal.selenium.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class CartPage {
    private final WebDriver driver;
    private final WebDriverWait wait;

    private final By lblTitulo = By.xpath("//h2[contains(text(),'Products')]");
    private final By btnCart = By.xpath("//button[text()='Cart']");
    public CartPage(WebDriver driver, WebDriverWait wait) {
        this.driver = driver;
        this.wait = wait;
    }

    public void navegarACarrito(){
        WebElement cartLink = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[@onclick='showcart()' and text()='Cart']")));
        cartLink.click();
        wait.until(ExpectedConditions.urlContains("cart.html"));
    }
}
