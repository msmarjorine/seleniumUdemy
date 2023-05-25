package by.av;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.Test;

public class LogInTest {
    @Test(enabled=false)
    public static void logInSuccess(){
        //Open Chrome browser
        System.setProperty("webdriver.chrome.driver", "src/main/resources/chromedriver.exe");
        WebDriver driver = new ChromeDriver();

        driver.manage().window().maximize();

        //Open homepage and access the login form
        String avUrl = "https://av.by/";
        driver.get(avUrl);
        WebElement loginLink = driver.findElement(By.cssSelector("a.nav__link[href='/login']"));
        loginLink.click();
        sleep();
        WebElement emailTab = driver.findElement(By.xpath("//button[contains(text(),'почте или логину')]"));
        emailTab.click();
        WebElement emailInput = driver.findElement(By.id("authLogin"));
        WebElement pwdInput = driver.findElement(By.id("loginPassword"));
        WebElement submitBtn = driver.findElement(By.cssSelector("button.button--action[type='submit']"));

        //Submit valid data and log in
        String myEmail = "marjorine13@gmail.com";
        String myPassword = "Asee28.g5SD";
        emailInput.sendKeys(myEmail);
        pwdInput.sendKeys(myPassword);
        submitBtn.click();
        sleep();

        //Verify the successful login and go to the user profile
        WebElement userBtn = driver.findElement(By.cssSelector("li.nav__item--user"));
        Assert.assertTrue(userBtn.isDisplayed(), "User profile dropdown is not displayed");
        userBtn.click();
        sleep();
        String loggedInExpectedUrl = "https://av.by/profile/offers";
        String loggedInActualUrl = driver.getCurrentUrl();
        Assert.assertEquals(loggedInActualUrl, loggedInExpectedUrl, "The actual url differs from the expected");
        WebElement settingsLink = driver.findElement(By.cssSelector("a.sidenav__link[href='/profile/settings']"));
        settingsLink.click();
        sleep();
        WebElement username = driver.findElement(By.id("name"));
        String myName = "Анастасия";
        Assert.assertEquals(username.getAttribute("value"), myName, "The user name differs from the expected");

        //Close Chrome browser
        driver.quit();

    }

    private static void sleep() {
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
