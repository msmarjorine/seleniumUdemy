package com.herokuapp.theinternet;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.Test;

public class NegativeTests {

    @Test
    public void invalidUsernameLoginTest(){
        //Open Chrome browser
        System.setProperty("webdriver.chrome.driver", "src/main/resources/chromedriver.exe");
        WebDriver driver = new ChromeDriver();
        driver.manage().window().maximize();

        //open test page
        String url = "http://the-internet.herokuapp.com/login";
        driver.get(url);

        //enter username
        WebElement username = driver.findElement(By.id("username"));
        String invalidUsername = "userarbuser";
        username.sendKeys(invalidUsername);
        //enter password
        WebElement password = driver.findElement(By.name("password"));
        String validPassword = "SuperSecretPassword!";
        password.sendKeys(validPassword);
        //click Login button
        WebElement loginBtn = driver.findElement((By.className("radius")));
        loginBtn.click();

        //verifications
        Assert.assertEquals(driver.getCurrentUrl(), url, "The page url differs from the expected one");
        WebElement errorNtf = driver.findElement(By.id("flash"));
        Assert.assertTrue(errorNtf.isDisplayed(), "The notification about the error is displayed");
        String expectedUsernameMessage = "Your username is invalid!";
        Assert.assertTrue(errorNtf.getText().contains(expectedUsernameMessage), "The notification message differs from expected");
        String redcolor = "rgba(198, 15, 19, 1)";
        Assert.assertEquals(errorNtf.getCssValue("background-color"), redcolor, "The background color of the notification differs from expected");

        //close Chrome
        driver.quit();
    }

    @Test
    public void invalidPasswordLoginTest(){
        //Open Chrome browser
        System.setProperty("webdriver.chrome.driver", "src/main/resources/chromedriver.exe");
        WebDriver driver = new ChromeDriver();
        driver.manage().window().maximize();

        //open test page
        String url = "http://the-internet.herokuapp.com/login";
        driver.get(url);

        //enter username
        WebElement username = driver.findElement(By.id("username"));
        String validUsername = "tomsmith";
        username.sendKeys(validUsername);
        //enter password
        WebElement password = driver.findElement(By.name("password"));
        String invalidPassword = "justsmth";
        password.sendKeys(invalidPassword);
        //click Login button
        WebElement loginBtn = driver.findElement((By.className("radius")));
        loginBtn.click();

        //verifications
        Assert.assertEquals(driver.getCurrentUrl(), url, "The page url differs from the expected one");
        WebElement errorNtf = driver.findElement(By.id("flash"));
        Assert.assertTrue(errorNtf.isDisplayed(), "The notification about the error is displayed");
        String expectedUsernameMessage = "Your password is invalid!";
        Assert.assertTrue(errorNtf.getText().contains(expectedUsernameMessage), "The notification message differs from expected");
        String redcolor = "rgba(198, 15, 19, 1)";
        Assert.assertEquals(errorNtf.getCssValue("background-color"), redcolor, "The background color of the notification differs from expected");

        //close Chrome
        driver.quit();
    }
}
