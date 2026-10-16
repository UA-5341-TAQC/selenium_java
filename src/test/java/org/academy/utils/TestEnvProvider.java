package org.academy.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Properties;

public class TestEnvProvider {
    private final Properties properties;

    public TestEnvProvider() {

        properties = new Properties();
        try (InputStreamReader reader = new InputStreamReader(new FileInputStream("src/test/resources/env.properties"), StandardCharsets.UTF_8)) {
            properties.load(reader);
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

    public String getAdminLoginUrl() {
        if (properties != null) {
            return properties.getProperty("ADMIN_LOGIN_URL");
        }
        return System.getProperty("ADMIN_LOGIN_URL");
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

    public String getAdminEmail() {
        if (properties != null) {
            return properties.getProperty("ADMIN_EMAIL");
        }
        return System.getProperty("ADMIN_EMAIL");
    }

    public String getAdminPassword() {
        if (properties != null) {
            return properties.getProperty("ADMIN_PASSWORD");
        }
        return System.getProperty("ADMIN_PASSWORD");
    }
}