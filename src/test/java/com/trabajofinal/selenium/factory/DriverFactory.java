package com.trabajofinal.selenium.factory;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

import java.util.HashMap;
import java.util.Map;

public class DriverFactory {
    public static WebDriver createDriver(String browser, boolean headless) {

        switch (browser.toLowerCase()) {
            case "chrome":
                WebDriverManager.chromedriver().setup();
                ChromeOptions chromeOptions = new ChromeOptions();

                // Desactivar completamente el gestor de contraseñas y detección de fugas
                Map<String, Object> prefs = new HashMap<>();
                prefs.put("credentials_enable_service", false);
                prefs.put("profile.password_manager_enabled", false);
                prefs.put("profile.password_manager_leak_detection", false);
                prefs.put("profile.default_content_setting_values.notifications", 2);
                chromeOptions.setExperimentalOption("prefs", prefs);

                // Eliminar la barra de "controlado por software de pruebas"
                chromeOptions.setExperimentalOption("excludeSwitches", new String[]{"enable-automation"});

                // Argumentos CLAVE para eliminar el alert de cambio de contraseña
                chromeOptions.addArguments("--disable-features=PasswordLeakDetection,PasswordCheck,PasswordImport,PasswordGeneration,PasswordManagerOnboarding,SavePasswordBubble");
                chromeOptions.addArguments("--disable-save-password-bubble");
                chromeOptions.addArguments("--disable-autofill-keyword-accessory-view");
                chromeOptions.addArguments("--disable-autofill-credit-card-ablation");

                // Usar un perfil temporal y limpio (opcional pero muy efectivo)
                chromeOptions.addArguments("--user-data-dir=" + System.getProperty("java.io.tmpdir") + "/chrome_selenium_" + System.currentTimeMillis());

                if(headless){
                    chromeOptions.addArguments("--headless=new");
                }
                chromeOptions.addArguments("--start-maximized");
                return new ChromeDriver(chromeOptions);

            case "edge":
                // se debe colocar del driver para la descarga
                System.setProperty("webdriver.edge.driver",
                        "/opt/edgedriver/msedgedriver");
                EdgeOptions edgeOptions = new EdgeOptions();

                // Desactivar completamente el gestor de contraseñas y detección de fugas
                java.util.Map<String, Object> edgePrefs = new java.util.HashMap<>();
                edgePrefs.put("credentials_enable_service", false);
                edgePrefs.put("profile.password_manager_enabled", false);
                edgeOptions.setExperimentalOption("prefs", edgePrefs);

                if(headless){
                    edgeOptions.addArguments("--headless=new");
                }
                edgeOptions.addArguments("--start-maximized");
                return new EdgeDriver(edgeOptions);

            case "firefox":
                WebDriverManager.firefoxdriver().setup();
                FirefoxOptions firefoxOptions = new FirefoxOptions();

                if(headless) {
                    firefoxOptions.addArguments("--headless");
                }
                firefoxOptions.addArguments("--width=1920");
                firefoxOptions.addArguments("--height=1080");
                return new FirefoxDriver(firefoxOptions);

            default:
                throw new RuntimeException("Navegador no soportado: " + browser);


        }

    }
}
