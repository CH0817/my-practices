package com.rex.practice.web.integration;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertEquals;

public class MenuTest {

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
    public void menuTrigger() {
        // 要測試的 menu
        List<String> testTitleNames = Arrays.asList("收支表", "帳戶", "項目");
        // menu title xpath
        By menuTitleBy = By.xpath("//*[@id='functionTree']//span[@class='tree-title']");
        // wait menu elements
        List<WebElement> menuTitles = wait.until(ExpectedConditions.visibilityOfAllElements(driver.findElements(menuTitleBy)));
        // verify menu click trigger
        menuTitles.stream().filter(m -> testTitleNames.contains(m.getText())).forEach(this::verifyMenuTrigger);
    }

    private void verifyMenuTrigger(WebElement menu) {
        menu.click();
        By panelTitleXpathBy = By.xpath("//*[@id='content']//div[@class='panel-title']");
        WebElement panelTitle = wait.until(ExpectedConditions.visibilityOf(driver.findElement(panelTitleXpathBy)));
        assertEquals(menu.getText(), panelTitle.getText());
    }

}
