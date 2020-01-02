package com.rex.my.web.integration;

import com.rex.my.web.controller.base.BaseControllerTest;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class LoginTest extends BaseControllerTest {

    @BeforeClass
    public static void setDriver() {
        System.setProperty("webdriver.chrome.driver", "D:/IDE/Selenium/Browers/Chrome/version 79/chromedriver.exe");
    }

    @Test
    public void loginSuccess() {
        WebDriver driver = new ChromeDriver();
        driver.get("http://localhost:8080/my-web/login");
        System.out.println(driver.getTitle());
        System.out.println(driver.findElement(By.name("email")));
        driver.close();
    }

}
