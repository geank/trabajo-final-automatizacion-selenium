package com.trabajofinal.selenium.tests;

import com.trabajofinal.selenium.base.BaseTest;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class AddToCartTest extends BaseTest {
    private WebDriverWait wait;
    @BeforeEach
    void init() {
        // Inicializa el navegador (Chrome o Edge) configurado en BaseTest
        super.setUp();
        // Inicializa el wait usando el driver seguro del hilo actual
        wait = new WebDriverWait(driver, Duration.ofSeconds(5));
    }
}
