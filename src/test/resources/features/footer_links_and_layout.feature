@Footer @Public
Feature: Footer links and layout
  As a visitor of the Victory Center website
  I want the footer to work correctly on every page
  So that I can rely on its links and always find it at the bottom of the page

  @Issue-86
  Scenario: Footer has no broken links
    Given the Victory Center home page is open
    When the visitor scrolls the page to the bottom
    Then none of the links in the footer is broken

  @Issue-86
  Scenario: Footer stays correct after scrolling and page reload on the "Хто ми" page
    Given the visitor opens the "Who We Are" page at "/about-us"
    When the visitor scrolls the page to the bottom and back to the top 3 times
    And the visitor scrolls the page to the bottom
    Then the footer is displayed once, at the bottom of the page and below the page content
    When the visitor reloads the page
    And the visitor scrolls the page to the bottom
    Then the footer is displayed once, at the bottom of the page and below the page content