@Programs @Public
Feature: Programs Page Functionality
  As a public visitor of the Victory Center website
  I want to be able to review available programs on the "Програми" page

  Background:
    Given Programs page is open

  @Issue-771 @Functional
  Scenario:[ContactUs] [Visitor] Open and close the contact form on the Programs page
    When The CTA Зв'язатися button clicked
    Then The contact form is displayed on the right side of the page
    Then Click the X button in the contact form
    And The contact form is no longer displayed on the page
    Then The Зв'язатися button remains available on the Програми page