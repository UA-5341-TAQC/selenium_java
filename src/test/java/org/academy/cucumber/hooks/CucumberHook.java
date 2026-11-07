package org.academy.cucumber.hooks;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import io.github.bonigarcia.wdm.WebDriverManager;
import io.qameta.allure.Attachment;
import lombok.Getter;

import org.academy.utils.DriverManager;
import org.academy.utils.TestEnvProvider;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.asserts.SoftAssert;

import java.nio.charset.StandardCharsets;
import java.time.Duration;

public class CucumberHook {

    private static final TestEnvProvider testValueProvider = new TestEnvProvider();
    private SoftAssert softAssert;

    static{
        WebDriverManager.chromedriver().setup();
    }

    @Before
    public void setUp(Scenario scenario) {
        WebDriver driver = initChromeDriver();
        DriverManager.setDriver(driver);
        driver.get(testValueProvider.getBaseUrl());
        scenario.log("Scenario started: " + scenario.getName());
    }

    @After
    public void tearDown(Scenario scenario) {
        WebDriver driver = DriverManager.getDriver();
        if (driver != null) {
            try {
                if (scenario.isFailed()) {
                    try {
                        attachScreenshot();
                        attachPageSource();
                    } catch (Exception e) {
                        scenario.log("Failed to capture failure artifacts: " + e.getMessage());
                    }
                }
                scenario.log("Scenario " + (scenario.isFailed() ? "failed" : "passed") + ": " + scenario.getName());
            } finally {
                try {
                    driver.quit();
                } finally {
                    DriverManager.removeDriver();
                }
            }
        }
    }

    @Attachment(value = "Screenshot on failure", type = "image/png")
    private byte[] attachScreenshot() {
        return ((TakesScreenshot) DriverManager.getDriver()).getScreenshotAs(OutputType.BYTES);
    }

    @Attachment(value = "Page source on failure", type = "text/html")
    private byte[] attachPageSource() {
        return DriverManager.getDriver().getPageSource().getBytes(StandardCharsets.UTF_8);
    }

    private WebDriver initChromeDriver() {
        ChromeOptions options = new ChromeOptions();
        if (testValueProvider.isHeadless()) {
            options.addArguments("--headless=new");
        }
        options.addArguments("--disable-gpu");
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--remote-allow-origins=*");
        options.addArguments("--disable-extensions");

        WebDriver driver = new ChromeDriver(options);
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(testValueProvider.getImplicitWait()));
        return driver;
    }
    public WebDriver getDriver(){
        return DriverManager.getDriver();
    }
    public SoftAssert getSoftAssert(){
        return softAssert;
    }
    public TestEnvProvider getTestValueProvider(){
        return testValueProvider;
    }
}