package org.academy.utils.testrunners;

import org.academy.utils.AdminSession;

public class BaseAPITestRunner extends BaseTestRunner {

    protected String getAccessToken() {
        return AdminSession.getSharedAccessToken(testEnvProvider);
    }
}
