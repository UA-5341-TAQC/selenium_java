package org.academy.ui.testrunners;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.academy.utils.TestEnvProvider;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;

import java.time.Duration;

public class BaseUiTestRunner {
    protected static TestEnvProvider testEnvProvider;
    protected WebDriver driver;

    @BeforeSuite
    public void beforeSuite() {
        WebDriverManager.chromedriver().setup();
        testEnvProvider = new TestEnvProvider();
    }

    public WebDriver initChromeDriver() {

        ChromeOptions options = new ChromeOptions();
        if (testEnvProvider.isHeadless()) {
            options.addArguments("--headless=new");
        }
        options.addArguments("--disable-gpu");
        options.addArguments("--no-sandbox");
        options.addArguments("--remote-allow-origins=*");
        options.addArguments("--disable-extensions");

        WebDriver driver = new ChromeDriver(options);
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(testEnvProvider.getImplicitWait()));
        return driver;
    }

    @BeforeMethod
    public void beforeMethod() {
        driver = initChromeDriver();
        driver.get(testEnvProvider.getBaseUrl());
    }

    @AfterMethod(alwaysRun = true)
    public void afterMethod() {
        if (driver != null) {
            driver.quit();
        }
    }

    @AfterSuite(alwaysRun = true)
    public void afterSuite() {
        if (driver != null) {
            driver.quit();
        }
    }
}
