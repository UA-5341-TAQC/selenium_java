package org.academy.api;

import org.academy.models.CompanyProfile.CompanyProfileResponse;
import org.academy.models.CompanyProfile.Contacts;
import org.academy.models.CompanyProfile.Requisites;
import org.academy.models.CompanyProfile.SocialLink;
import org.academy.utils.testrunners.BaseAPITestRunner;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.util.List;

public class CompanyProfileTests extends BaseAPITestRunner{
    protected CompanyProfileClient companyProfileClient;

    @BeforeClass
    public void beforeClass() {
        companyProfileClient = new CompanyProfileClient(testEnvProvider.getBaseApiUrl());
        companyProfileClient.setToken(getAccessToken());
    }

    @Test
    public void testGetCompanyProfile() {
        CompanyProfileResponse response = companyProfileClient.getCompanyProfile();
        SoftAssert softAssert = new SoftAssert();

        softAssert.assertNotNull(response, "Response should not be null");

        // Перевірка блоку contacts[cite: 2]
        Contacts contacts = response.getContacts();
        softAssert.assertNotNull(contacts, "Contacts should not be null");
        softAssert.assertEquals(contacts.getPhone(), "+380 50 334 4448", "Phone mismatch");
        softAssert.assertEquals(contacts.getAddress(), "м. Київ, вул. Хрещатик, 1", "Address mismatch");
        softAssert.assertEquals(contacts.getEmail(), "victorycenter@gmail.com", "Email mismatch");
        softAssert.assertEquals(contacts.getCorrespondenceEmail(), "victorycenter@gmail.com", "Correspondence email mismatch");
        softAssert.assertEquals(contacts.getMotto(), "ПЕРЕМОГА ПОЧИНАЄТЬСЯ З ТЕБЕ", "Motto mismatch");

        // Перевірка локалізацій контактів[cite: 2, 3]
        softAssert.assertNotNull(contacts.getLocalizations(), "Contact localizations should not be null");
        softAssert.assertEquals(contacts.getLocalizations().size(), 2, "Contact localizations size mismatch");

        // Перевірка блоку requisites[cite: 4]
        Requisites requisites = response.getRequisites();
        softAssert.assertNotNull(requisites, "Requisites should not be null");
        softAssert.assertEquals(requisites.getRecipient(), "ГО «ЦЕНТР ПЕРЕМОГИ»", "Recipient mismatch");
        softAssert.assertEquals(requisites.getEdrpou(), "43747389", "EDRPOU mismatch");
        softAssert.assertEquals(requisites.getAddress(), "м. Київ, вул. Хрещатик, 2", "Requisites address mismatch");

        // Перевірка локалізацій реквізитів[cite: 3, 4]
        softAssert.assertNotNull(requisites.getLocalizations(), "Requisite localizations should not be null");
        softAssert.assertEquals(requisites.getLocalizations().size(), 2, "Requisite localizations size mismatch");

        // Перевірка соціальних мереж[cite: 1, 5]
        List<SocialLink> socialLinks = response.getSocialLinks();
        softAssert.assertNotNull(socialLinks, "Social links should not be null");
        softAssert.assertEquals(socialLinks.size(), 3, "Social links size mismatch");

        softAssert.assertAll();
    }

}