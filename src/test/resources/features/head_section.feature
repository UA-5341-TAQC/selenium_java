Feature: Main Page Head Section Functionality

  Description:
  Verify that the Head section is displayed correctly on the Main page.
  Verify that clicking the "Переглянути програми" button opens
  the Programs page in a new browser tab.
  Verify that the Main page remains open after navigation.

  Background:
    Given user is on the Main page

  @issue935
  Scenario: Verify Head section is displayed correctly
    Then Head section should be displayed correctly
    And Head section text should not contain HTML tags

  @issue936
  Scenario: Verify View Programs opens Programs page in a new tab
    When user clicks View Programs button
    And user switches to the new tab
    Then Programs page should be opened in a new tab

  @issue937
  Scenario: Verify Main page remains open after opening Programs
    When user clicks View Programs button
    And user switches to the new tab
    Then Programs page should be opened
    When user switches back to the Main page tab
    Then Main page should remain open
