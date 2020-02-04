package com.rex.practice.web.integration;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.concurrent.TimeUnit;

public class AccountCRUDTest {

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
        // 登入
        login();
    }

    @AfterClass
    public static void closeDriver() {
        // 關閉網頁
        driver.close();
    }

    private static void login() {
        // 輸入 email
        driver.findElement(By.xpath("//*[@id='email']/following-sibling::span/input[1]")).sendKeys("test@email.com");
        // 輸入 password
        driver.findElement(By.xpath("//*[@id='password']/following-sibling::span/input[1]")).sendKeys("11111111");
        // 等待 easyui box 的錯誤消失
        if (wait.until(ExpectedConditions.invisibilityOf(driver.findElement(By.className("tooltip-content"))))) {
            // 找到 #loginBtn button 並執行 click event
            driver.findElement(By.id("loginBtn")).click();
        }
    }

    @Test
    public void addAccountNoAnyInput() {
        // 點擊功能表 -> 帳戶
        driver.findElement(By.xpath("//*[@id='functionTree']//span[@class='tree-title'][text()='帳戶']")).click();
        // 等待新增按鈕可點擊後點擊
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id='accountGridToolBar']/a[1]"))).click();
        // 等待保存按鈕可點擊後點擊
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id='accountGridToolBar']/a[3]"))).click();
        // 等待 easyui 指定錯誤內容警告視窗出現
        By messageWindowBy = By.xpath("//div[contains(@class,'messager-window')]//div[contains(@class,'messager-body')]//div[text()='名稱不能為空、帳戶類型不能為空']");
        wait.until(ExpectedConditions.visibilityOf(driver.findElement(messageWindowBy)));
    }

}
