@admin @partners
Feature: Admin Partners Management
  As an administrator of the Victory Center admin panel,
  I want to view, validate, and manage content in the "Partners" section,
  So that information across all blocks is accurate, validated, and safely published.

  Background:
    Given the administrator is logged in
    And the admin navigates to the "Партнери" page

  @Issue-1
  Scenario: Verify that Banner title and description accept up to 30 characters
    Given the admin clears the Banner title field
    And the admin clears the Banner description field

    When the admin enters "1234567890" into the Banner title field
    And the admin enters "1234567890" into the Banner description field
    And the admin triggers validation by blurring the Banner description field

    Then the Banner title field contains "1234567890"
    And the Banner description field contains "1234567890"
    And the Banner title counter reads "10/30"
    And the Banner description counter reads "10/30"
    And no Banner validation errors are displayed
    And the Banner "Опублікувати" button becomes enabled

    When the admin appends "12345678901234567890" to the Banner title field
    And the admin appends "12345678901234567890" to the Banner description field
    And the admin triggers validation by blurring the Banner description field

    Then the Banner title field contains "123456789012345678901234567890"
    And the Banner description field contains "123456789012345678901234567890"
    And the Banner title counter reads "30/30"
    And the Banner description counter reads "30/30"
    And no Banner validation errors are displayed
    And the Banner "Опублікувати" button becomes enabled