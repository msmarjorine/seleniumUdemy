package com.herokuapp.theinternet;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.Test;

//Now Selenium can handle browser drivers by itself, no need to download (Beta)

public class SeleniumManagerTest {

    @Test
    public void openWebsite(){
        //System.setProperty("webdriver.chrome.driver", "src/main/resources/chromedriver.exe");
        WebDriver driver = new ChromeDriver();
        //WebDriver driver = new FirefoxDriver();
        driver.manage().window().maximize();
        String url = "https://practicetestautomation.com/";
        driver.get(url);

        WebElement logo = driver.findElement(By.cssSelector("img.custom-logo"));
        Assert.assertTrue(logo.isDisplayed(), "The logo is not visible");

        driver.quit();

    }
}
