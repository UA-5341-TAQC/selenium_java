package org.academy.utils.testrunners;

import org.academy.utils.TestEnvProvider;
import org.testng.annotations.BeforeSuite;

public class BaseTestRunner {
    protected static TestEnvProvider testEnvProvider;

    @BeforeSuite
    public void beforeSuite() {
        testEnvProvider = new TestEnvProvider();
    }
}
