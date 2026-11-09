package org.academy.ui;

import io.qameta.allure.Issue;
import io.qameta.allure.Story;
import org.academy.ui.pages.BasePublicPage;
import org.academy.utils.testrunners.BaseUiTestRunner;
import org.academy.utils.LinkChecker;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.net.URI;
import java.util.List;

public class TestFooterBrokenLinks extends BaseUiTestRunner {

    @Test(description = "Verify there are no broken links in the footer")
    @Issue("86")
    @Story("Footer")
    public void testFooterHasNoBrokenLinks() {
        BasePublicPage page = new BasePublicPage(driver);
        page.getFooter();
        page.scrollToBottom();

        List<String> links = page.getFooter().getAllLinks();
        Assert.assertFalse(links.isEmpty(), "No links were found in the footer");

        String baseHost = URI.create(testEnvProvider.getBaseUrl()).getHost();
        SoftAssert softAssert = new SoftAssert();

        for (String link : links) {
            String problem = baseHost.equals(URI.create(link).getHost())
                    ? LinkChecker.checkInternalLink(driver, link)
                    : LinkChecker.checkExternalLink(link);
            softAssert.assertNull(problem, "Broken footer link: " + problem);
        }

        softAssert.assertAll();
    }
}