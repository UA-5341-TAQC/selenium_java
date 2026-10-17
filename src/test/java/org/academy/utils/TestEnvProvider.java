package org.academy.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Properties;

public class TestEnvProvider {
    private final Properties properties;

    public TestEnvProvider() {
        properties = new Properties();
        InputStream stream = getClass().getClassLoader().getResourceAsStream("env.properties");
        if (stream == null) {
            try {
                stream = new FileInputStream("src/test/resources/env.properties");
            } catch (IOException ignored) {
            }
        }
        if (stream != null) {
            try (InputStreamReader reader = new InputStreamReader(stream, StandardCharsets.UTF_8)) {
                properties.load(reader);
            } catch (IOException e) {
                System.err.println("Could not load properties file: " + e.getMessage());
                System.err.println("Use system properties.");
            }
        }
    }

    private String getProperty(String key) {
        if (properties != null && properties.getProperty(key) != null) {
            return properties.getProperty(key);
        }
        return System.getProperty(key);
    }

    public String getBaseUrl() {
        return getProperty("BASE_URL");
    }

    public String getAdminLoginUrl() {
        return getProperty("ADMIN_LOGIN_URL");
    }

    public Boolean isHeadless() {
        return Boolean.parseBoolean(getProperty("HEADLESS"));
    }

    public int getImplicitWait() {
        String waitVal = getProperty("IMPLICITLY_WAIT");
        return Integer.parseInt(waitVal != null ? waitVal : "10");
    }

    public String getAdminEmail() {
        return getProperty("ADMIN_EMAIL");
    }

    public String getAdminPassword() {
        return getProperty("ADMIN_PASSWORD");
    }
}