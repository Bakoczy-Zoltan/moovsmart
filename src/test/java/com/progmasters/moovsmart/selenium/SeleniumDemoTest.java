package com.progmasters.moovsmart.selenium;

import com.progmasters.moovsmart.dto.CreateUserCommand;
import com.progmasters.moovsmart.service.UserService;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@DataJpaTest
public class SeleniumDemoTest {

    private WebDriver driver;
    private SeleniumIniter initer = new SeleniumIniter();

    @Autowired(required = false)
    private UserService userService;


    @BeforeEach
    public void initTest() {
        driver = initer.getDriver();
        driver.get("http://localhost:4200/");
        userService.makeUser(new CreateUserCommand());
    }

    @Test
    public void findPropertyListButton() {
        String listTitle = driver.findElement(By.cssSelector("#myNavbar > ul > li > a")).getAttribute("innerHTML");
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        assertEquals(listTitle, "Ingatlanok listája");
    }

    @Test
    public void testClickOn() {
        WebElement elementListButton = driver.findElement(By.linkText("Ingatlanok listája"));
        elementListButton.click();

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        WebElement elementSignIn = driver.findElement(By.cssSelector("#myNavbar > a:nth-child(3)"));
        Assertions.assertEquals("Bejelentkezés ", elementSignIn.getAttribute("innerText"));

    }

    @Test
    public void TestListProperty() {
        WebElement elementListButton = driver.findElement(By.linkText("Ingatlanok listája"));
        elementListButton.click();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        WebElement elementSignIn = driver.findElement(By.cssSelector("#myNavbar > a:nth-child(2)"));
        elementSignIn.click();

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        List<WebElement> elements = new ArrayList<>();
        WebElement elementMailLabel = driver.findElement(By.cssSelector("body > app-root > div > app-registration > div.container.jumbotron > form > div:nth-child(2) > label"));
        elements.add(elementMailLabel);
        WebElement elementNameLabel = driver.findElement(By.cssSelector("body > app-root > div > app-registration > div.container.jumbotron > form > div:nth-child(1) > label"));
        elements.add(elementNameLabel);
        WebElement elementPassword = driver.findElement(By.cssSelector("body > app-root > div > app-registration > div.container.jumbotron > form > div:nth-child(3) > label"));
        elements.add(elementPassword);

        Assertions.assertEquals("Regisztráció ", elementSignIn.getAttribute("innerText"));
        Assertions.assertTrue(elementNameLabel.getAttribute("innerText").equals("Név"));
    }

    @AfterEach
    public void closeTabs() {
        driver.close();
    }

}
