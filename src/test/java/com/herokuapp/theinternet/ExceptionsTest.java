package com.herokuapp.theinternet;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.*;

import java.rmi.server.ExportException;
import java.time.Duration;

public class ExceptionsTest {
    private WebDriver driver;
    private WebDriverWait wait;

    @BeforeMethod(alwaysRun = true)
    private void setUp(){
        //create driver
        System.setProperty("webdriver.chrome.driver", "src/main/resources/chromedriver.exe");
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
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
        WebElement row2 = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div#row2 input")));

        Assert.assertTrue(row2.isDisplayed(), "The Row 2 is not displayed");
    }

    @Test
    public void elementNotInteractable(){
        String testUrl = "https://practicetestautomation.com/practice-test-exceptions/";
        driver.get(testUrl);
        WebElement addBtn = driver.findElement(By.id("add_btn"));
        addBtn.click();
        WebElement row2 = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div#row2 input")));
        row2.sendKeys("Pie");
        //WebElement saveBtn = driver.findElement(By.id("save_btn"));
        //the button is not interactable(2 elements with such an id on the page)
        WebElement saveBtn = driver.findElement(By.xpath("//div[@id='row2']//button[@id='save_btn']"));
        saveBtn.click();
        WebElement confMsg = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("confirmation")));
        String expectedConfMsg = "Row 2 was saved";
        Assert.assertEquals(confMsg.getText(), expectedConfMsg, "The confirmation message differs from the expected");
    }

    @Test
    public void invalidElementState(){
        String testUrl = "https://practicetestautomation.com/practice-test-exceptions/";
        driver.get(testUrl);
        WebElement row1 = driver.findElement(By.xpath("//div[@id='row1']//input"));
        //won't work as the input is disabled by default
        //row1.clear();
        WebElement editBtn = driver.findElement(By.xpath("//div[@id='row1']//button[@id='edit_btn']"));
        editBtn.click();
        wait.until(ExpectedConditions.elementToBeClickable(row1));
        row1.clear();
        String myNewValue = "Dumplings";
        row1.sendKeys(myNewValue);
        String newValue = row1.getAttribute("value");
        Assert.assertEquals(newValue, myNewValue, "The values differ");
        WebElement saveBtn = driver.findElement(By.xpath("//div[@id='row1']//button[@id='save_btn']"));
        saveBtn.click();
        WebElement confMsg = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("confirmation")));
        String expectedConfMsg = "Row 1 was saved";
        Assert.assertEquals(confMsg.getText(), expectedConfMsg, "The confirmation message differs from the expected");

    }

    @Test
    public void staleElementReference(){
        String testUrl = "https://practicetestautomation.com/practice-test-exceptions/";
        driver.get(testUrl);
        WebElement instructions = driver.findElement(By.id("instructions"));
        Assert.assertTrue(instructions.isDisplayed(), "The instructions paragraph is not displayed");
        WebElement addBtn = driver.findElement(By.id("add_btn"));
        addBtn.click();
        //stale element reference as the instructions paragraph does not exist anymore
        //Assert.assertFalse(instructions.isDisplayed(), "The instructions paragraph is still displayed");
        Assert.assertTrue(wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("instructions"))), "The instructions paragraph is still displayed");
    }

    @Test
    public void timeOutException(){
        String testUrl = "https://practicetestautomation.com/practice-test-exceptions/";
        driver.get(testUrl);
        WebElement addBtn = driver.findElement(By.id("add_btn"));
        addBtn.click();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(6));
        WebElement row2 = driver.findElement(By.xpath("//div[@id='row2']"));
        Assert.assertTrue(row2.isDisplayed(), "The 2nd row is not displayed");

    }
}
