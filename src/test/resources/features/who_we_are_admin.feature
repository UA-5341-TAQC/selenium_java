@admin @who_we_are
Feature: Admin Who We Are Management
  As an administrator of the Victory Center admin panel,
  I want to view, validate, and manage content in the "Хто ми" section,
  So that information across all blocks is accurate, validated, and safely published.

  Background:
    Given an admin is logged in to the admin panel
    And the admin navigates to the "Хто ми" page

  @Issue-949
  Scenario: Verify 'Опублікувати' button is disabled when Admin enters the "Люди" tab
    When the admin selects the "Люди" tab
    Then the "Опублікувати" button is disabled

  @Issue-941
  Scenario: Verify "Опублікувати" button is disabled in the "Команда" block when no changes are made
    When the admin selects the "Команда" tab
    Then the "Опублікувати" button is disabled

  @Issue-901
  Scenario: Verify the content display in the "Кого підтримуємо" tab
    When the admin selects the "Кого підтримуємо" tab
    Then the title "КОГО МИ МОЖЕМО ПІДТРИМАТИ" is displayed above the cards
    And exactly 3 cards are displayed
    And each card displays an image
    And each card displays an "Опис" field with a character counter up to 300
    And the "Опублікувати" button is disabled

  @Issue-942
  Scenario: Verify that the system stores the defaulted pre-uploaded image in the "Команда" block
    When the admin selects the "Команда" tab
    Then a pre-uploaded image is displayed in the "Команда" block
    When the admin refreshes the page
    And the admin selects the "Команда" tab
    Then a pre-uploaded image is displayed in the "Команда" block

  @Issue-939
  Scenario: Verify "Опис" field is editable on the "Команда" tab
    When the admin selects the "Команда" tab
    Then the "Опис" field is editable
    When the admin clears the "Опис" field and enters "Тестовий опис для перевірки редагування"
    Then the "Опис" field contains "Тестовий опис для перевірки редагування"

  @Issue-946
  Scenario: Verify the 'Опис' field is editable on the "Люди" tab and displays an accurate character counter
    When the admin selects the "Люди" tab
    Then each card has an editable "Опис" field and a character counter
    When the admin clears the "Опис" field of card 1
    And the admin types "Новий опис" into the "Опис" field of card 1
    Then the character counter for card 1 reads "10/60"
    When the admin deletes 2 characters from the "Опис" field of card 2
    Then the character counter for card 2 matches its current text length out of 60

  @Issue-948
  Scenario: Verify the character counter updates correctly on 'Люди' tab when entering special characters and pasting text
    When the admin selects the "Люди" tab
    When the admin clears the "Опис" field of card 1
    And the admin enters special characters "✔✘$ € ♫" into the "Опис" field of card 1
    Then the character counter for card 1 reads "7/60"
    When the admin pastes text "Тестовий текст" into the "Опис" field of card 1
    Then the character counter for card 1 reads "21/60"

  @Issue-930
  Scenario: Verify validation messages for 'Опис' field in 'Що ми робимо' block
    When the admin selects the "Що ми робимо" tab
    When the admin clears the "Опис" field and enters "123456789"
    Then no validation message is displayed under the field
    When the admin triggers validation by blurring the "Опис" field
    Then the validation message "Не менше 10 символів" is displayed under the field
    When the admin clears the "Опис" field and enters "1234567890"
    And the admin triggers validation by blurring the "Опис" field
    Then no validation message is displayed under the field
    When the admin clears the "Опис" field
    And the admin triggers validation by blurring the "Опис" field
    Then the validation message "Поле обов'язкове" is displayed under the field
    When the admin clears the "Опис" field and enters "1234567890"
    And the admin triggers validation by blurring the "Опис" field
    Then no validation message is displayed under the field

  @Issue-931
  Scenario: Verify 'Опублікувати' button enables only after 'Опис' passes validation in 'Що ми робимо' block
    When the admin selects the "Що ми робимо" tab
    Then the "Опублікувати" button is disabled
    When the admin clears the "Опис" field
    And the admin triggers validation by blurring the "Опис" field
    Then the "Опублікувати" button is disabled
    When the admin clears the "Опис" field and enters "123456789"
    And the admin triggers validation by blurring the "Опис" field
    Then the "Опублікувати" button is disabled
    When the admin clears the "Опис" field and enters "1234567890"
    And the admin triggers validation by blurring the "Опис" field
    Then the "Опублікувати" button is enabled

  @Issue-929
  Scenario: Verify 'Опис' field of 'Що ми робимо' block stops at 300 characters by typing and pasting
    When the admin selects the "Що ми робимо" tab
    When the admin clears the "Опис" field and types 299 characters
    Then the character counter reads "299/300"
    And no validation message is displayed under the field
    When the admin attempts to type an additional character into the "Опис" field
    Then the character counter reads "300/300"
    And no validation message is displayed under the field
    When the admin attempts to type an additional character into the "Опис" field
    Then the character counter remains "300/300"
    And the "Опис" field length does not exceed 300 characters
    When the admin clears the "Опис" field and pastes 301 characters
    Then the character counter remains "300/300"
    And the "Опис" field length does not exceed 300 characters

  @Issue-934
  Scenario: Verify space management of the "Опис" field in the "Команда" block
    When the admin selects the "Команда" tab
    When the admin clears the "Опис" field and enters " 12345678 "
    And the admin triggers validation by blurring the "Опис" field
    Then the "Опис" field contains "12345678"
    And the character counter reads "8/400"
    And the validation message "Не менше 10 символів" is displayed under the field
    And the "Опублікувати" button is disabled
    When the admin clears the "Опис" field and enters "12345 "
    And the admin triggers validation by blurring the "Опис" field
    Then the "Опис" field contains "12345"
    And the character counter reads "5/400"
    And the validation message "Не менше 10 символів" is displayed under the field
    And the "Опублікувати" button is disabled

  @Issue-968
  Scenario: Verify confirmation pop-up is displayed when publishing changes in the "Люди" block
    When the admin selects the "Люди" tab
    When the admin modifies card 1 with valid text "Оновлений текст картки"
    And the admin clicks the "Опублікувати" button
    Then the "Опублікувати зміни?" confirmation pop-up is displayed
    And the confirmation pop-up has "Так" and "Ні" buttons

  @Issue-950
  Scenario: Verify confirmation popup is displayed after clicking the enabled "Опублікувати" button in "Кого підтримуємо" block
    When the admin selects the "Кого підтримуємо" tab
    When the admin modifies card 1 with valid text "Новий опис підтримки"
    And the admin clicks the "Опублікувати" button
    Then the "Опублікувати зміни?" confirmation pop-up is displayed
    And the confirmation pop-up has "Так" and "Ні" buttons

  @Issue-951 @Issue-928
  Scenario Outline: Verify cancelling publication via "Ні" button closes pop-up and keeps "Опублікувати" enabled
    When the admin selects the "<tab>" tab
    And the admin modifies card 1 with valid text "<valid_text>"
    And the admin clicks the "Опублікувати" button
    And the admin clicks "Ні" in the confirmation pop-up
    Then the confirmation pop-up is closed
    And the "Опублікувати" button is enabled

    Examples:
      | tab              | valid_text                   |
      | Кого підтримуємо | Зміни підтримки              |
      | Основне          | Новий валідний опис основного|

  @Issue-970 @Issue-932
  Scenario Outline: Verify cancelling publication via "Ні" button does not update public "Хто ми" page
    When the admin selects the "<tab>" tab
    And the admin modifies card 1 with valid text "<unpublished_text>"
    And the admin clicks the "Опублікувати" button
    And the admin clicks "Ні" in the confirmation pop-up
    Then the confirmation pop-up is closed
    And the "Опублікувати" button is enabled
    And the public "Хто ми" page does not display "<unpublished_text>"

    Examples:
      | tab          | unpublished_text                        |
      | Люди         | Неопубліковані зміни блоку Люди         |
      | Що ми робимо | Неопублікований опис місії Що ми робимо |
