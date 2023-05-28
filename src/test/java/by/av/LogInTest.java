package by.av;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import java.time.Duration;

public class LogInTest {

    private WebDriver driver;
    private WebDriverWait wait;

    @BeforeMethod(alwaysRun = true)
    private void setUp(){
        //Open Chrome browser
        System.setProperty("webdriver.chrome.driver", "src/main/resources/chromedriver.exe");
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        driver.manage().window().maximize();
    }

    @AfterMethod(alwaysRun = true)
    private void tearDown(){
        //Close Chrome browser
        driver.quit();
    }


    @Test
    @Parameters({"email", "password"})
    public void logInSuccess(String email, String password){
        //Open homepage and access the login form
        String avUrl = "https://av.by/";
        driver.get(avUrl);
        WebElement loginLink = driver.findElement(By.cssSelector("a.nav__link[href='/login']"));
        loginLink.click();
        WebElement emailTab = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[contains(text(),'почте или логину')]")));
        emailTab.click();
        WebElement emailInput = driver.findElement(By.id("authLogin"));
        WebElement pwdInput = driver.findElement(By.id("loginPassword"));
        WebElement submitBtn = driver.findElement(By.cssSelector("button.button--action[type='submit']"));

        //Submit valid data and log in
       //String myEmail = "marjorine13@gmail.com";
        //String myPassword = "Asee28.g5SD";
        emailInput.sendKeys(email);
        pwdInput.sendKeys(password);
        submitBtn.click();

        WebElement userBtn = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("li.nav__item--user")));

        //Verify the successful login and go to the user profile
        Assert.assertTrue(userBtn.isDisplayed(), "User profile dropdown is not displayed");
        userBtn.click();

        wait.until(ExpectedConditions.urlContains("/offers"));
        String loggedInExpectedUrl = "https://av.by/profile/offers";
        String loggedInActualUrl = driver.getCurrentUrl();
        Assert.assertEquals(loggedInActualUrl, loggedInExpectedUrl, "The actual url differs from the expected");
        WebElement settingsLink = driver.findElement(By.cssSelector("a.sidenav__link[href='/profile/settings']"));
        settingsLink.click();
        WebElement username = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("name")));
        String myName = "Анастасия";
        Assert.assertEquals(username.getAttribute("value"), myName, "The user name differs from the expected");



    }

}
