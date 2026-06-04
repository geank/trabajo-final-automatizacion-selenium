package com.trabajofinal.selenium.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
public class LoginPage {
    private final WebDriver driver;
    private final WebDriverWait wait;

    private final By txtUsername = By.id("loginusername");
    private final By txtPassword = By.id("loginpassword");
    private final By btnLogin = By.xpath("//button[text()='Log in']");
    private final By btnLogout = By.id("logout2");



    public LoginPage(WebDriver driver, WebDriverWait wait) {
        this.driver = driver;
        this.wait = wait;
    }

    public void ingresarUsuario(String usuario) {
        wait.until(ExpectedConditions.elementToBeClickable(By.id("login2"))).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(txtUsername)).clear();
        driver.findElement(txtUsername).sendKeys(usuario);
    }

    public void ingresarContrasena(String password) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(txtPassword)).clear();
        driver.findElement(txtPassword).sendKeys(password);
    }

    public void hacerClicEnLogin() {
        wait.until(ExpectedConditions.elementToBeClickable(btnLogin)).click();
    }

    public void loginCompleto(String usuario, String password) {
        ingresarUsuario(usuario);
        ingresarContrasena(password);
        hacerClicEnLogin();
    }

    public String obtenerTextoLogout() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(btnLogout)).getText();
    }

    public String obtenerTextoAlertNativo() {
        org.openqa.selenium.Alert alerta = wait.until(ExpectedConditions.alertIsPresent());
        String textoError = alerta.getText();
        alerta.accept();
        return textoError;
    }

}
