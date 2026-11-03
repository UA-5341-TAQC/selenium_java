@AdminFaq
Feature: Admin FAQ
  Administrators can select FAQ publication pages, validate form fields,
  and discard entered values without saving or publishing a question.

  Background:
    Given the administrator is logged in
    And the administrator opens the FAQ page
    And the administrator opens the Add Question modal

  @Issue-954
  Scenario: Verify multiple FAQ pages can be selected and one selection removed
    Then the FAQ page options should be:
      | Програми       |
      | Про іпотерапію |
      | Донати         |
    When the administrator selects the following FAQ pages:
      | Програми |
    Then the selected FAQ pages should be:
      | Програми |
    When the administrator selects the following FAQ pages:
      | Програми       |
      | Про іпотерапію |
    Then the selected FAQ pages should be:
      | Програми       |
      | Про іпотерапію |
    When the administrator selects the following FAQ pages:
      | Про іпотерапію |
    Then the selected FAQ pages should be:
      | Про іпотерапію |

  @Issue-971
  Scenario: Verify valid question and answer lengths are accepted and counters updated
    When the administrator enters a FAQ question of 10 characters
    Then the FAQ question should contain 10 characters and its counter should be correct
    And the FAQ form should have no validation errors
    When the administrator enters a FAQ question of 50 characters
    Then the FAQ question should contain 50 characters and its counter should be correct
    And the FAQ form should have no validation errors
    When the administrator enters a FAQ answer of 50 characters
    Then the FAQ answer should contain 50 characters and its counter should be correct
    And the FAQ form should have no validation errors
    When the administrator enters a FAQ answer of 150 characters
    Then the FAQ answer should contain 150 characters and its counter should be correct
    And the FAQ form should have no validation errors

  @Issue-974
  Scenario: Verify saving a draft is enabled with a page and valid question only
    Then the FAQ draft and publication buttons should be disabled
    When the administrator selects the following FAQ pages:
      | Програми |
    And the administrator enters a FAQ question of 10 characters
    Then the FAQ answer should be empty
    And the FAQ draft button should be enabled
    And the FAQ publication button should be disabled

  @Issue-975
  Scenario: Verify publication is enabled when all required FAQ fields are valid
    Then the FAQ draft and publication buttons should be disabled
    When the administrator selects the following FAQ pages:
      | Програми |
    And the administrator enters a FAQ question of 10 characters
    And the administrator enters a FAQ answer of 50 characters
    Then the FAQ form should have no validation errors
    And the FAQ publication button should be enabled

  @Issue-978
  Scenario: Verify entered values are preserved when discarding changes is declined
    When the administrator selects the following FAQ pages:
      | Програми |
    And the administrator enters a FAQ question of 10 characters
    And the administrator enters a FAQ answer of 50 characters
    And the administrator requests to close the FAQ form
    Then the FAQ discard confirmation should be displayed
    When the administrator declines discarding FAQ changes
    Then the FAQ form should remain open with the entered values and pages

  @Issue-979
  Scenario: Verify entered values are discarded when modal closure is confirmed
    When the administrator selects the following FAQ pages:
      | Програми |
    And the administrator enters a FAQ question of 10 characters
    And the administrator enters a FAQ answer of 50 characters
    And the administrator requests to close the FAQ form
    Then the FAQ discard confirmation should be displayed
    When the administrator confirms discarding FAQ changes
    Then the FAQ form should be closed and the FAQ page should be visible
    When the administrator reopens the Add Question modal
    Then the FAQ form should be empty
