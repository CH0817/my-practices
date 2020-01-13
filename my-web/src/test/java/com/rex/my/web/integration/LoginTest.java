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

import java.util.concurrent.TimeUnit;

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
        WebDriver driver = new ChromeDriver();

        try {
            driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);

            driver.get("http://localhost:8080/my-web/login");

            // 輸入 email
            WebElement email = driver.findElement(By.xpath("//*[@id='email']/following-sibling::span/input[1]"));
            email.sendKeys("test@email.com");

            // 輸入 password
            WebElement password = driver.findElement(By.xpath("//*[@id='password']/following-sibling::span/input[1]"));
            password.sendKeys("11111111");

            // 停頓一秒，等待 EasyUI 反應
            Thread.sleep(1000);

            // 模擬滑鼠移動到 Email input 的 span

            // 找到 #loginBtn button 並執行 click event
            driver.findElement(By.id("loginBtn")).click();

            assertEquals("主頁", driver.getTitle());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            driver.close();
        }
    }

    @Test
    public void loginFailure() {
        setTextBoxValue("email", "test11111@email.com");
        setTextBoxValue("password", "11111111");

        // 找到 #loginBtn button 並執行 click event
        driver.findElement(By.id("loginBtn")).click();

        assertEquals("帳號或密碼錯誤", driver.findElement(By.xpath("//*[@id='loginWindow']/div[1]")).getText());
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

    private void setTextBoxValue(String elementId, String value) {
        // JS 設置 EasyUI box value
        jsExecutor.executeScript("$('#" + elementId + "').textbox('setValue', '" + value + "')");
    }

}
