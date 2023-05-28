package com.herokuapp.theinternet;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.*;

import java.time.Duration;

public class ExceptionsTest {
    private WebDriver driver;

    @BeforeMethod(alwaysRun = true)
    private void setUp(){
        //create driver
        System.setProperty("webdriver.chrome.driver", "src/main/resources/chromedriver.exe");
        driver = new ChromeDriver();

        //maximize browser window
        driver.manage().window().maximize();
    }

    @AfterMethod(alwaysRun = true)
    private void tearDown(){
        //close browser
        driver.quit();
    }

    @Test
    public void noSuchElement(){
        String testUrl = "https://practicetestautomation.com/practice-test-exceptions/";
        driver.get(testUrl);
        WebElement pageTitle = driver.findElement(By.cssSelector("#food_list h2"));
        String expectedTitle = "Test Exceptions";
        Assert.assertTrue(pageTitle.isDisplayed(), "The page title is not displayed");
        Assert.assertEquals(pageTitle.getText(), expectedTitle, "The title text differs from the expected");
        WebElement addBtn = driver.findElement(By.id("add_btn"));
        addBtn.click();

        //Challenge: the 2nd row does not appear instantly after the click

        //The worst way to overcome noSuchElement exception is Thread.sleep:
        /*try {
            Thread.sleep(9000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }*/

        //Implicit wait:
        /*Duration myWait = Duration.ofSeconds(10);
        driver.manage().timeouts().implicitlyWait(myWait);*/

        //Explicit wait:
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement row2 = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div#row2 input")));

        Assert.assertTrue(row2.isDisplayed(), "The Row 2 is not displayed");
    }
}
