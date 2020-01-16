package com.rex.my.web.integration;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertEquals;

public class LoginFormVerifyTest {

    private static WebDriver driver;

    @BeforeClass
    public static void setDriver() {
        // 設置 chrome driver .exe 所在的環境變數
        System.setProperty("webdriver.chrome.driver", "D:/IDE/Selenium/Browers/Chrome/version 79/chromedriver.exe");
        driver = new ChromeDriver();
        // driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
        // 開啟網頁
        driver.get("http://localhost:8080/my-web/login");
    }

    @AfterClass
    public static void closeDriver() {
        // 關閉網頁
        driver.close();
    }

    @Test
    public void loginFailure() throws InterruptedException {
        // 輸入 email
        typeEmail("test11111@email.com");
        // 輸入 password
        typePassword("11111111");
        // 停頓 0.5 秒，等待 EasyUI 反應
        Thread.sleep(500);
        // 找到 #loginBtn button 並執行 click event
        driver.findElement(By.id("loginBtn")).click();
        // 驗證 server 回傳的錯誤訊息
        assertEquals("帳號或密碼錯誤", driver.findElement(By.xpath("//*[@id='loginWindow']/div[1]")).getText());
    }

    @Test
    public void errorEmailFormat() throws InterruptedException {
        // 輸入 email
        typeEmail("111@11");
        // 停頓 0.5 秒，等待 EasyUI 反應
        Thread.sleep(500);
        // 驗證 EasyUI 錯誤訊息
        assertEquals("請輸入有效的電子郵件地址", driver.findElement(By.className("tooltip-content")).getText());
    }

    @Test
    public void errorPasswordFormat() throws InterruptedException {
        // 輸入 password
        typePassword("1111");
        // 停頓 0.5 秒，等待 EasyUI 反應
        Thread.sleep(500);
        // 驗證 EasyUI 錯誤訊息
        assertEquals("密碼必須是8~12碼", driver.findElement(By.className("tooltip-content")).getText());
    }

    @Test
    public void emptyEmail() throws InterruptedException {
        // 輸入 email
        typeEmail("");
        // 停頓 0.5 秒，等待 EasyUI 反應
        Thread.sleep(500);
        // 驗證 EasyUI 錯誤訊息
        assertEquals("請輸入Email", driver.findElement(By.className("tooltip-content")).getText());
    }

    @Test
    public void emptyPassword() throws InterruptedException {
        // 輸入 password
        typePassword("");
        // 停頓 0.5 秒，等待 EasyUI 反應
        Thread.sleep(500);
        // 驗證 EasyUI 錯誤訊息
        assertEquals("請輸入密碼", driver.findElement(By.className("tooltip-content")).getText());
    }

    private void typeEmail(String email) {
        typeEasyUiBoxValue("email", email);
    }

    private void typePassword(String password) {
        typeEasyUiBoxValue("password", password);
    }

    private void typeEasyUiBoxValue(String id, String value) {
        WebElement input = driver.findElement(By.xpath("//*[@id='" + id + "']/following-sibling::span/input[1]"));
        // 先清空再重新輸入
        input.sendKeys("");
        input.sendKeys(value);
    }

}
