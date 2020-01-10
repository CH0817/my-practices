package com.rex.my.web.integration;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;

import static org.junit.Assert.assertEquals;

public class LoginTest {

    private static WebDriver driver;
    private static JavascriptExecutor jsExecutor;

    @BeforeClass
    public static void setDriver() {
        // 設置 chrome driver .exe 所在的環境變數
        System.setProperty("webdriver.chrome.driver", "D:/IDE/Selenium/Browers/Chrome/version 79/chromedriver.exe");
        driver = new ChromeDriver();
        // driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
        // 開啟網頁
        driver.get("http://localhost:8080/my-web/login");

        jsExecutor = (JavascriptExecutor) driver;
    }

    @AfterClass
    public static void closeDriver() {
        // 關閉網頁
        driver.close();
    }

    @Test
    public void loginSuccess() {
        login();
        assertEquals("主頁", driver.getTitle());
    }

    @Test
    public void errorEmailFormat() {
        setTextBoxValue("email", "test@email");

        // 找到顯示的 Email input 的 span
        WebElement element = driver.findElement(By.cssSelector("#loginForm > div:nth-child(2) > span"));

        // 模擬滑鼠移動到 Email input 的 span
        Actions action = new Actions(driver);
        action.moveToElement(element).perform();

        // 驗證 EasyUI 錯誤訊息
        assertEquals("請輸入有效的電子郵件地址", driver.findElement(By.className("tooltip-content")).getText());
    }

    @Test
    public void errorPasswordFormat() {
        setTextBoxValue("password", "1111");

        // JS 執行 passwordbox 隱藏密碼
        jsExecutor.executeScript("$('#password').passwordbox('hidePassword')");

        // 找到顯示的 Password input 的 span
        WebElement element = driver.findElement(By.cssSelector("#loginForm > div:nth-child(3) > span"));

        // 模擬滑鼠移動到 Password input 的 span
        Actions action = new Actions(driver);
        action.moveToElement(element).perform();

        // 驗證 EasyUI 錯誤訊息
        assertEquals("密碼必須是8~12碼", driver.findElement(By.className("tooltip-content")).getText());
    }

    @Test
    public void emptyEmail() {
        // 找到顯示的 Email input 的 span
        WebElement element = driver.findElement(By.cssSelector("#loginForm > div:nth-child(2) > span"));

        // 模擬滑鼠移動到 Email input 的 span
        Actions action = new Actions(driver);
        action.moveToElement(element).perform();

        // 驗證 EasyUI 錯誤訊息
        assertEquals("請輸入Email", driver.findElement(By.className("tooltip-content")).getText());
    }

    @Test
    public void emptyPassword() {
        // JS 執行 passwordbox 隱藏密碼
        jsExecutor.executeScript("$('#password').passwordbox('hidePassword')");

        // 找到顯示的 Password input 的 span
        WebElement element = driver.findElement(By.cssSelector("#loginForm > div:nth-child(3) > span"));

        // 模擬滑鼠移動到 Password input 的 span
        Actions action = new Actions(driver);
        action.moveToElement(element).perform();

        // 驗證 EasyUI 錯誤訊息
        assertEquals("請輸入密碼", driver.findElement(By.className("tooltip-content")).getText());
    }

    private void login() {
        setTextBoxValue("email", "test@email.com");
        setTextBoxValue("password", "11111111");

        // 找到 #loginBtn button 並執行 click event
        driver.findElement(By.id("loginBtn")).click();
    }

    private void setTextBoxValue(String elementId, String value) {
        // JS 設置 EasyUI box value
        jsExecutor.executeScript("$('#" + elementId + "').textbox('setValue', '" + value + "')");
    }

}
