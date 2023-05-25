package com.herokuapp.theinternet;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

public class NegativeParameterizedTests {

    @Test
    @Parameters({"username", "password", "expectedMessage"})
    public void invalidLogin(String username, String password, String expectedMessage){
        //Open Chrome browser
        System.setProperty("webdriver.chrome.driver", "src/main/resources/chromedriver.exe");
        WebDriver driver = new ChromeDriver();
        driver.manage().window().maximize();

        //open test page
        String url = "http://the-internet.herokuapp.com/login";
        driver.get(url);

        //enter username
        WebElement usernameInput = driver.findElement(By.id("username"));
        usernameInput.sendKeys(username);
        //enter password
        WebElement passwordInput = driver.findElement(By.name("password"));
        passwordInput.sendKeys(password);
        //click Login button
        WebElement loginBtn = driver.findElement((By.className("radius")));
        loginBtn.click();

        //verifications
        Assert.assertEquals(driver.getCurrentUrl(), url, "The page url differs from the expected one");
        WebElement errorNtf = driver.findElement(By.id("flash"));
        Assert.assertTrue(errorNtf.isDisplayed(), "The notification about the error is displayed");
        Assert.assertTrue(errorNtf.getText().contains(expectedMessage), "The notification message differs from expected");
        String redcolor = "rgba(198, 15, 19, 1)";
        Assert.assertEquals(errorNtf.getCssValue("background-color"), redcolor, "The background color of the notification differs from expected");

        //close Chrome
        driver.quit();

    }
}
