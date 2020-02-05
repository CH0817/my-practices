package com.rex.practice.web.integration;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@Ignore
public class LoginFormVerifyTest {

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
    public void loginFailure() {
        // 輸入 email
        typeEmail("test11111@email.com");
        // 輸入 password
        typePassword("11111111");
        // 等待 easyui box 的錯誤消失
        if (wait.until(ExpectedConditions.invisibilityOf(driver.findElement(By.className("tooltip-content"))))) {
            // 找到 #loginBtn button 並執行 click event
            driver.findElement(By.id("loginBtn")).click();
            // 驗證 server 回傳的錯誤訊息
            WebElement errorMessageDiv = wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath("//*[@id='loginWindow']/div[1]"))));
            assertEquals("帳號或密碼錯誤", errorMessageDiv.getText());
        }
    }

    @Test
    public void errorEmailFormat() {
        // 輸入 email
        typeEmail("111@11");
        // 驗證 EasyUI 錯誤訊息
        assertTrue(verifyInputErrorMessage("請輸入有效的電子郵件地址"));
    }

    @Test
    public void errorPasswordFormat() {
        // 輸入 password
        typePassword("1111");
        // 驗證 EasyUI 錯誤訊息
        assertTrue(verifyInputErrorMessage("密碼必須是8~12碼"));
    }

    @Test
    public void emptyEmail() {
        // 輸入 email
        typeEmail("");
        // 驗證 EasyUI 錯誤訊息
        assertTrue(verifyInputErrorMessage("請輸入Email"));
    }

    @Test
    public void emptyPassword() {
        // 輸入 password
        typePassword("");
        // 驗證 EasyUI 錯誤訊息
        assertTrue(verifyInputErrorMessage("請輸入密碼"));
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

    private boolean verifyInputErrorMessage(String message) {
        return wait.until(ExpectedConditions.textToBe(By.className("tooltip-content"), message));
    }

}
