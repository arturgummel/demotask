package com.homework.demo;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class SeleniumTests {
    protected WebDriver driver;
    @LocalServerPort
    private int port;
    private String base;

    @Before
    public void setUp() throws Exception {
        File file = new File("adapter/chromedriver.exe");
        System.setProperty("webdriver.chrome.driver", file.getAbsolutePath());
        driver = new ChromeDriver();
        this.base = "http://localhost:" + port;
    }

    @Test
    public void testSignIn() throws Exception {
        driver.get(base);

        //login as user1 and check first and last names
        WebElement username = driver.findElement(By.id("username"));
        WebElement password = driver.findElement(By.id("password"));
        WebElement signin = driver.findElement(By.id("SubmitSignIn"));
        username.sendKeys("user1");
        password.sendKeys("12345");
        signin.submit();
        Thread.sleep(2000);
        WebElement firstName = driver.findElement(By.id("firstname"));
        WebElement lastName = driver.findElement(By.id("lastname"));
        assertEquals("Bot", firstName.getAttribute("value"));
        assertEquals("Tot", lastName.getAttribute("value"));
    }

    @Test
    public void updateData() throws Exception {
        driver.get(base);

        //login as user2
        WebElement username = driver.findElement(By.id("username"));
        username.sendKeys("user2");
        WebElement password = driver.findElement(By.id("password"));
        password.sendKeys("12345");
        WebElement signin = driver.findElement(By.id("SubmitSignIn"));
        signin.submit();
        Thread.sleep(2000);

        //clear values and insert new one
        WebElement firstName = driver.findElement(By.id("firstname"));
        firstName.clear();
        firstName.sendKeys("Stew");
        WebElement lastName = driver.findElement(By.id("lastname"));
        lastName.clear();
        lastName.sendKeys("Drill");
        WebElement address = driver.findElement(By.id("address"));
        address.clear();

        //add some wrong format values
        address.sendKeys("MynewAddressLengthIsMoreThan256CharactersMynewAddressLengthIsMoreThan256CharactersMynewAddressLengthIsMoreThan256CharactersMynewAddressLengthIsMoreThan256CharactersMynewAddressLengthIsMoreThan256CharactersMynewAddressLengthIsMoreThan256CharactersMynewAddressLengthIsMoreThan256Characters");
        WebElement birthDate = driver.findElement(By.id("birthDate"));
        birthDate.clear();
        birthDate.sendKeys("10-10-1990"); //wrong format
        WebElement updateBtn = driver.findElement(By.id("updateSubmit"));
        updateBtn.submit();

        //check updated values
        assertEquals("Stew", driver.findElement(By.id("firstname")).getAttribute("value"));
        assertEquals("Drill", driver.findElement(By.id("lastname")).getAttribute("value"));
        assertEquals("The length can not be more than 256 chars!", driver.findElement(By.id("addressError")).getText());
        assertEquals("Invalid birthdate format!", driver.findElement(By.id("birthDateError")).getText());
    }

    @Test
    public void testAdminTable() throws Exception {
        driver.get(base);

        //login as admin
        WebElement username = driver.findElement(By.id("username"));
        username.sendKeys("admin");
        WebElement password = driver.findElement(By.id("password"));
        password.sendKeys("admin");
        WebElement signin = driver.findElement(By.id("SubmitSignIn"));
        signin.submit();
        Thread.sleep(2000);

        //find table caption
        WebElement captionTable = driver.findElement(By.tagName("caption"));
        assertEquals("List of users", captionTable.getText());

        //test logout
        WebElement logout = driver.findElement(By.id("logout"));
        logout.click();
        assertEquals("Coming Soon!", driver.findElement(By.tagName("h1")).getText());
    }

    @After
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
