package org.academy.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Properties;

public class TestEnvProvider {
    private static final String ENV_FILE_NAME = "env.properties";
    private static final String FALLBACK_PATH = "src/test/resources/" + ENV_FILE_NAME;
    private static final Properties PROPERTIES = new Properties();

    static {
        loadProperties();
    }

    public TestEnvProvider() {
        // Properties are loaded once in the static initializer block
    }

    private static void loadProperties() {
        InputStream stream = TestEnvProvider.class.getClassLoader().getResourceAsStream(ENV_FILE_NAME);
        if (stream == null) {
            try {
                stream = new FileInputStream(FALLBACK_PATH);
            } catch (IOException ignored) {
                // Fallback to system properties / environment variables
            }
        }

        if (stream != null) {
            try (InputStreamReader reader = new InputStreamReader(stream, StandardCharsets.UTF_8)) {
                PROPERTIES.load(reader);
            } catch (IOException e) {
                System.err.println("Could not load properties file: " + e.getMessage());
            }
        }
    }

    private String getProperty(String key) {
        // 1. Check JVM System Properties first (-DKEY=value) for easy CLI & CI/CD overrides
        String value = System.getProperty(key);
        if (value != null && !value.isBlank()) {
            return value.trim();
        }

        // 2. Check OS Environment Variables
        value = System.getenv(key);
        if (value != null && !value.isBlank()) {
            return value.trim();
        }

        // 3. Fallback to local env.properties file
        value = PROPERTIES.getProperty(key);
        return value != null ? value.trim() : null;
    }

    public String getBaseUrl() {
        return getProperty("BASE_URL");
    }

    public String getAdminLoginUrl() {
        return getProperty("ADMIN_LOGIN_URL");
    }

    public boolean isHeadless() {
        return Boolean.parseBoolean(getProperty("HEADLESS"));
    }

    public int getImplicitWait() {
        String waitVal = getProperty("IMPLICITLY_WAIT");
        if (waitVal == null || waitVal.isBlank()) {
            return 10;
        }
        try {
            return Integer.parseInt(waitVal);
        } catch (NumberFormatException e) {
            return 10;
        }
    }

    public String getAdminEmail() {
        return getProperty("ADMIN_EMAIL");
    }

    public String getAdminPassword() {
        return getProperty("ADMIN_PASSWORD");
    }
}