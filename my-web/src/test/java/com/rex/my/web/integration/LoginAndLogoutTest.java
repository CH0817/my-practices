package com.rex.my.web.integration;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertEquals;

public class LoginAndLogoutTest {

    private static WebDriver driver;
    private static WebDriverWait wait;

    @BeforeClass
    public static void setDriver() {
        // 設置 chrome driver .exe 所在的環境變數
        System.setProperty("webdriver.chrome.driver", "D:/IDE/Selenium/Browers/Chrome/version 79/chromedriver.exe");
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, 3L);
        // 隱式 wait
        driver.manage().timeouts().implicitlyWait(3L, TimeUnit.SECONDS);
        // 開啟網頁
        driver.get("http://localhost:8080/my-web/login");
    }

    @AfterClass
    public static void closeDriver() {
        // 關閉網頁
        driver.close();
    }

    @Test
    public void loginAndLogout() {
        login();
        logout();
    }

    private void login() {
        // 輸入 email
        driver.findElement(By.xpath("//*[@id='email']/following-sibling::span/input[1]")).sendKeys("test@email.com");
        // 輸入 password
        driver.findElement(By.xpath("//*[@id='password']/following-sibling::span/input[1]")).sendKeys("11111111");
        // 等待 easyui box 的錯誤消失
        if (wait.until(ExpectedConditions.invisibilityOf(driver.findElement(By.className("tooltip-content"))))) {
            // 找到 #loginBtn button 並執行 click event
            driver.findElement(By.id("loginBtn")).click();
            // 驗證
            assertEquals("/main", driver.getCurrentUrl().substring(driver.getCurrentUrl().lastIndexOf("/")));
        }
    }

    public void logout() {
        // 找到【登出】並點擊
        driver.findElement(By.xpath("//*[@id='functionTree']//span[@class='tree-title'][text()='登出']")).click();
        // 驗證
        assertEquals("/login", driver.getCurrentUrl().substring(driver.getCurrentUrl().lastIndexOf("/")));
    }

}
