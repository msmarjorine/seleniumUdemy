package com.herokuapp.theinternet;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.Test;

public class PositiveTests {

    @Test
    public void loginTest(){
        System.out.println("Starting loginTest");
        //create driver
        System.setProperty("webdriver.chrome.driver", "src/main/resources/chromedriver.exe");
        WebDriver driver = new ChromeDriver();

        //sleep for 3 seconds for demo purpose
        sleep(3000);

        //maximize browser window
        driver.manage().window().maximize();

        //open test page
        String url = "http://the-internet.herokuapp.com/login";
        driver.get(url);
        System.out.println("The page is opened");

        //sleep for 2 seconds for demo purpose
        sleep(2000);

        //enter username
        //enter password
        //click Login button

        //verifications:
            //check url
            //check Logout button
            //check notification

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
