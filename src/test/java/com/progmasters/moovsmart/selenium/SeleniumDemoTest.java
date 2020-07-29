package com.progmasters.moovsmart.selenium;

import com.progmasters.moovsmart.domain.UserProperty;
import com.progmasters.moovsmart.dto.CreateUserCommand;
import com.progmasters.moovsmart.repository.UserRepository;
import com.progmasters.moovsmart.service.UserService;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@Transactional
@Rollback
public class SeleniumDemoTest {

    private WebDriver driver;
    private SeleniumIniter initer = new SeleniumIniter();
    CreateUserCommand user = new CreateUserCommand();

    @Autowired
    private UserService userService;


    @BeforeEach
    public void initTest() {
        driver = initer.getDriver();
        driver.get("http://localhost:4200/");


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

    @Test
    public void testRegistrationOfProperty() {
        user.setMail("demo@demo.hu");
        user.setUserName("John Tester");
        user.setPassword("Demo123");

        UserRepository userRepository = userService.getUserRepository();
        UserProperty user = new UserProperty(this.user);

        user.setIsActive(true);
        userRepository.save(user);

        driver.findElement(By.cssSelector("#myNavbar > a:nth-child(3)")).click();

        WebElement elementMailLabel = driver.findElement(By.cssSelector("#mail"));
        elementMailLabel.sendKeys("demo@demo.hu");
        WebElement elementPassword = driver.findElement(By.cssSelector("#password"));
        elementPassword.sendKeys("Demo123");

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        driver.findElement(By.cssSelector("body > app-root > div > app-signin > div > form > button")).click();

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
       driver.findElement(By.cssSelector("#myNavbar > ul > li:nth-child(2) > a")).click();

    }

    @AfterEach
    public void closeTabs() {
        driver.close();
    }

}
