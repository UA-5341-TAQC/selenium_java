package org.academy.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class TestEnvProvider {
    private Properties properties;

    public TestEnvProvider() {

        try (FileInputStream input = new FileInputStream("src/test/resources/env.properties")) {
            properties = new Properties();
            properties.load(input);
        } catch (IOException e) {
            System.err.println("Could not load properties file: " + e.getMessage());
            System.err.println("Use system properties.");
        }
    }

    public String getBaseUrl() {
        if (properties != null) {
            return properties.getProperty("BASE_URL");
        }
        return System.getProperty("BASE_URL");
    }

    public Boolean isHeadless() {
        if (properties != null) {
            return Boolean.parseBoolean(properties.getProperty("HEADLESS"));
        }
        return Boolean.parseBoolean(System.getProperty("HEADLESS"));
    }

    public int getImplicitWait() {
        if (properties != null) {
            return Integer.parseInt(properties.getProperty("IMPLICITLY_WAIT"));
        }
        return Integer.parseInt(System.getProperty("IMPLICITLY_WAIT", "10"));
    }
}