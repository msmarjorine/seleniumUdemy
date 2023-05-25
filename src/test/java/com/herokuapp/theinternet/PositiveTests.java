package com.herokuapp.theinternet;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.Test;

public class PositiveTests {

    @Test
    public void loginTest(){
        System.out.println("Starting loginTest");
        //create driver
        System.setProperty("webdriver.chrome.driver", "src/main/resources/chromedriver.exe");
        WebDriver driver = new ChromeDriver();

        //sleep for 2 seconds for demo purpose
        sleep(2000);

        //maximize browser window
        driver.manage().window().maximize();

        //open test page
        String url = "http://the-internet.herokuapp.com/login";
        driver.get(url);
        System.out.println("The page is opened");

        //sleep for 2 seconds for demo purpose
        sleep(2000);

        //enter username
        WebElement username = driver.findElement(By.id("username"));
        String validUsername = "tomsmith";
        username.sendKeys(validUsername);
        //enter password
        WebElement password = driver.findElement(By.name("password"));
        String validPassword = "SuperSecretPassword!";
        password.sendKeys(validPassword);
        //click Login button
        WebElement loginBtn = driver.findElement((By.className("radius")));
        loginBtn.click();

        //verifications:
            //check url
        String expectedUrl = "http://the-internet.herokuapp.com/secure";
        String actualUrl = driver.getCurrentUrl();
        Assert.assertEquals(actualUrl, expectedUrl, "Actual page url is not the same as expected");
            //check Logout button
        WebElement logoutBtn = driver.findElement(By.cssSelector("a.button.radius"));
        Assert.assertTrue(logoutBtn.isDisplayed(), "The log out button is not present");
            //check notification
        WebElement successNtf = driver.findElement(By.id("flash"));
        Assert.assertTrue(successNtf.isDisplayed(), "The success notification is not present");
        String expectedMessage = "You logged into a secure area!";
        String actualMessage = successNtf.getText();
        //Assert.assertEquals(expectedMessage, actualMessage, "The message differs from expected");
        Assert.assertTrue(actualMessage.contains(expectedMessage), "The message differs from expected");

        //close browser
        driver.quit();

    }

    private static void sleep(long m) {
        try{
            Thread.sleep(m);
        } catch(InterruptedException e) {
            e.printStackTrace();
        }
    }
}
