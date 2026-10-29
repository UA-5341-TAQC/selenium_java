@footer
Feature: Footer navigation, contact information and social media
  Visitors can use the footer to navigate the website,
  view contact information and open Victory Center social media profiles.

  Background:
    Given the Victory Center home page is open

  @TC838
  Scenario Outline: Footer navigation links open the corresponding pages
    When the visitor scrolls to the footer
    And the visitor clicks the footer navigation link for "<path>"
    Then the page at "<path>" opens on the Victory Center website
    And the page displays non-empty content
    And the page does not display a 404 or page-not-found message

    Examples:
      | path             |
      | /reports         |
      | /support-us      |
      | /stories         |
      | /about-us        |
      | /history         |
      | /team            |
      | /partners-page   |
      | /events-and-news |
      | /hippotherapy    |
      | /programs        |

  @TC839
  Scenario: Footer displays contact information
    When the visitor scrolls to the footer
    Then the footer displays the email "victorycenter@gmail.com"
    And the footer displays the phone number "+380 50 334 4448"

  @TC840
  Scenario Outline: Footer opens a social media profile in a new tab
    When the visitor scrolls to the footer
    And the visitor clicks "<network>" in the footer
    Then a new tab opens with the profile "<url>"
    And the original page remains open at the same URL

    Examples:
      | network   | url                                      |
      | Facebook  | https://www.facebook.com/victorycenterua   |
      | Telegram  | https://t.me/victorycenterua               |
      | Instagram | https://www.instagram.com/victorycenterua |
