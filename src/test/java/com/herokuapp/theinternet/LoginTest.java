package com.herokuapp.theinternet;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.*;

public class LoginTest {

    private WebDriver driver;

    @Parameters({"browser"})
    @BeforeMethod(alwaysRun = true)
    private void setUp(@Optional String browser){
        //create driver
        switch(browser){
            case "chrome":
                System.setProperty("webdriver.chrome.driver", "src/main/resources/chromedriver.exe");
                driver = new ChromeDriver();
                break;
            case "firefox": //doesn't work im my env
                System.setProperty("webdriver.gecko.driver", "src/main/resources/geckodriver.exe");
                driver = new FirefoxDriver();
                break;
            default:
                System.out.println("Unsupported browser " + browser + ", will run in Chrome");
                System.setProperty("webdriver.chrome.driver", "src/main/resources/chromedriver.exe");
                driver = new ChromeDriver();
                break;
        }

        //maximize browser window
        driver.manage().window().maximize();
    }

    @AfterMethod(alwaysRun = true)
    private void tearDown(){
        //close browser
        driver.quit();
    }

    @Test
    @Parameters({"username", "password", "expectedMessage"})
    public void validLoginTest(String username, String password, String expectedMessage){
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
        String actualMessage = successNtf.getText();
        Assert.assertTrue(actualMessage.contains(expectedMessage), "The message differs from expected");

    }

    @Test
    @Parameters({"username", "password", "expectedMessage"})
    public void invalidLoginTest(String username, String password, String expectedMessage){
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

    }
}
