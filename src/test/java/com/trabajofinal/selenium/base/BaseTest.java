package com.trabajofinal.selenium.base;


import com.trabajofinal.selenium.factory.DriverFactory;
import org.openqa.selenium.WebDriver;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class BaseTest {
    protected WebDriver driver;
    protected Properties props;

    public void setUp() {
        props = new Properties();
        try {
            props.load(new FileInputStream("src/test/resources/config.properties"));
            String browser = System.getProperty("browser",
                    props.getProperty("browser"));
            boolean headless = Boolean.parseBoolean(System.getProperty("headless",
                    props.getProperty("headless")));
            driver = DriverFactory.createDriver(browser, headless);
            driver.get(props.getProperty("url"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

}