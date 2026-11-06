package org.academy.utils.testrunners;

import org.academy.utils.TestEnvProvider;
import org.testng.annotations.BeforeSuite;

public class BaseTestRunner {
    protected static TestEnvProvider testEnvProvider = new TestEnvProvider();

    @BeforeSuite
    public void beforeSuite() {
        if (testEnvProvider == null) {
            testEnvProvider = new TestEnvProvider();
        }
    }
}
