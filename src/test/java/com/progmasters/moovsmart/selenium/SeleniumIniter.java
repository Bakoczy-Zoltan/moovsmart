package com.progmasters.moovsmart.selenium;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class SeleniumIniter {

    static {
        ClassLoader classLoader = SeleniumIniter.class.getClassLoader();
        System.setProperty("webdriver.chrome.driver", classLoader.getResource("selenium-driver/chromedriver_linux64/chromedriver").getFile());
    }

    public  WebDriver getDriver(){
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--start-maximized");
        return new ChromeDriver(options);
    }

}
