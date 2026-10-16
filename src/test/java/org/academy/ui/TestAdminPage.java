package org.academy.ui;

import org.academy.ui.testrunners.UITestRunnerWithAdmin;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

public class TestAdminPage extends UITestRunnerWithAdmin {

    @Test
    public void testAdminPanel() {
        SoftAssert softAssert = new SoftAssert();

        softAssert.assertEquals(adminPanelPage.getMainText(), "ВІТАЄМО В ПАНЕЛІ АДМІНІСТРАТОРА", "Main Text does not match");
        softAssert.assertEquals(adminPanelPage.getSubText(), "Тут ви можете керувати контентом сайту", "Sub Text does not match");
        softAssert.assertTrue(adminPanelPage.getActionHint().contains("Оберіть категорію, щоб почати"), "Action Hint does not match");

        softAssert.assertAll();

    }

}
