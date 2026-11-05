@admin @who_we_are
Feature: Admin Who We Are Management
  As an administrator of the Victory Center admin panel,
  I want to view, validate, and manage content in the "Хто ми" section,
  So that information across all blocks is accurate, validated, and safely published.

  Background:
    Given the administrator is logged in
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
    Then the publish confirmation pop-up "Опублікувати зміни?" is displayed
    And the publish confirmation pop-up has "Так" and "Ні" buttons

  @Issue-950
  Scenario: Verify confirmation popup is displayed after clicking the enabled "Опублікувати" button in "Кого підтримуємо" block
    When the admin selects the "Кого підтримуємо" tab
    When the admin modifies card 1 with valid text "Новий опис підтримки"
    And the admin clicks the "Опублікувати" button
    Then the publish confirmation pop-up "Опублікувати зміни?" is displayed
    And the publish confirmation pop-up has "Так" and "Ні" buttons

  @Issue-951 @Issue-928
  Scenario Outline: Verify cancelling publication via "Ні" button closes pop-up and keeps "Опублікувати" enabled
    When the admin selects the "<tab>" tab
    And the admin modifies card 1 with valid text "<valid_text>"
    And the admin clicks the "Опублікувати" button
    And the admin clicks "Ні" in the confirmation pop-up
    Then the publish confirmation pop-up is closed
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
    Then the publish confirmation pop-up is closed
    And the "Опублікувати" button is enabled
    And the public "Хто ми" page does not display "<unpublished_text>"

    Examples:
      | tab          | unpublished_text                        |
      | Люди         | Неопубліковані зміни блоку Люди         |
      | Що ми робимо | Неопублікований опис місії Що ми робимо |

  @Issue834
  Scenario: Verify character counters of title and description
    Given the admin navigates to the "Хто ми" page
    When the admin selects the "Основне" tab
    Then the title counter should be "14/50"
    And the description counter should be "278/300"
    When the admin saves the original title
    And the admin saves the original description
    And the admin enters 2 characters in the title
    Then the title counter should be "16/50"
    When the admin clears the title
    Then the title counter should be "0/50"
    When the admin enters 51 characters in the title
    Then the title counter should be "50/50"
    And the title should contain 50 characters
    When the admin reloads the page without publishing
    Then the original title should be restored
    And the original description should be restored
    And the title counter should be "14/50"
    And the description counter should be "278/300"

  @Issue835
  Scenario: Verify editable fields remain recoverable without publishing
    Given the admin navigates to the "Хто ми" page
    When the admin selects the "Основне" tab
    And the admin saves the original title
    And the admin clears the title
    Then the title counter should be "0/50"
    And the title validation message should be "Заголовок обов'язковий"
    And the "Опублікувати" button is disabled
    When the admin enters 51 characters in the title
    Then the title counter should be "50/50"
    And the title should contain 50 characters
    When the admin reloads the page without publishing
    Then the original title should be restored

  @Issue874
  Scenario: Verify min/max boundary values for title and description
    Given the admin navigates to the "Хто ми" page
    When the admin selects the "Основне" tab
    And the admin clears the title
    And the admin clears the main description
    And the admin enters 10 characters in the title
    And the admin enters 10 characters in the description
    Then the title counter should be "10/50"
    And the description counter should be "10/300"
    And no title validation error should be displayed
    And no description validation error should be displayed
    And the "Опублікувати" button is enabled
    When the admin enters 40 characters in the title
    And the admin enters 290 characters in the description
    Then the title counter should be "50/50"
    And the description counter should be "300/300"
    And no title validation error should be displayed
    And no description validation error should be displayed
    And the "Опублікувати" button is enabled

  @Issue875
  Scenario: Verify minimum length validation for title and description
    Given the admin navigates to the "Хто ми" page
    When the admin selects the "Основне" tab
    And the admin clears the title
    And the admin clears the main description
    And the admin enters 9 characters in the title
    Then the title counter should be "9/50"
    And the title validation message should be "Не менше 10 символів"
    When the admin applies bold formatting to the title
    Then the title counter should be "9/50"
    And the title validation message should be "Не менше 10 символів"
    When the admin applies italic formatting to the title
    Then the title counter should be "9/50"
    And the title validation message should be "Не менше 10 символів"
    When the admin enters 9 characters in the description
    Then the description counter should be "9/300"
    And the description validation message should be "Не менше 10 символів"
    When the admin applies bold formatting to the description
    Then the description counter should be "9/300"
    And the description validation message should be "Не менше 10 символів"
    When the admin applies italic formatting to the description
    Then the description counter should be "9/300"
    And the description validation message should be "Не менше 10 символів"
    And the "Опублікувати" button is disabled

  @Issue876
  Scenario: Verify maximum character limits for title and description
    Given the admin navigates to the "Хто ми" page
    When the admin selects the "Основне" tab
    And the admin clears the title
    And the admin clears the main description
    When the admin enters 50 characters in the title
    Then the title counter should be "50/50"
    And the title should contain 50 characters
    When the admin enters 1 characters in the title
    Then the title counter should be "50/50"
    And the title should contain 50 characters
    When the admin enters 300 characters in the description
    Then the description counter should be "300/300"
    And the description should contain 300 characters
    When the admin enters 1 characters in the description
    Then the description counter should be "300/300"
    And the description should contain 300 characters
    When the admin clears the title
    And the admin clears the main description
    Then the title counter should be "0/50"
    And the description counter should be "0/300"
    When the admin pastes 51 characters into the title
    Then the title counter should be "50/50"
    And the title should contain the first 50 pasted characters
    When the admin pastes 301 characters into the description
    Then the description counter should be "300/300"
    And the description should contain the first 300 pasted characters

  @Issue-1000
  Scenario Outline: Verify 'Опублікувати' button state for the description of every card in the "Люди" block (card <first_card> is the starting one)
    When the admin selects the "Люди" tab
    And the admin waits for 4 people cards to be loaded
    Then exactly 4 cards are displayed
    And the "Опублікувати" button becomes disabled
    When the admin remembers the original descriptions and counters of all cards
    # 9 символів -> помилка, кнопка вимкнена
    And the admin clears the "Опис" field of card <first_card> and enters "Тест коня"
    And the admin triggers validation by blurring the "Опис" field of card <first_card>
    Then the validation message "Не менше 10 символів" is displayed under the "Опис" field of card <first_card>
    And the "Опублікувати" button becomes disabled
    # 10 символів -> помилки немає, кнопка активна
    When the admin clears the "Опис" field of card <first_card> and enters "Тест коней"
    And the admin triggers validation by blurring the "Опис" field of card <first_card>
    Then no validation message is displayed under the "Опис" field of card <first_card>
    And the "Опублікувати" button becomes enabled
    # інша картка порожня -> помилка, кнопка знову вимкнена
    When the admin clears the "Опис" field of card <other_card>
    And the admin triggers validation by blurring the "Опис" field of card <other_card>
    Then the validation message "Поле обов'язкове" is displayed under the "Опис" field of card <other_card>
    And the "Опублікувати" button becomes disabled
    # інша картка валідна -> кнопка активна
    When the admin clears the "Опис" field of card <other_card> and enters "Тест коней"
    And the admin triggers validation by blurring the "Опис" field of card <other_card>
    Then no validation message is displayed under the "Опис" field of card <other_card>
    And the "Опублікувати" button becomes enabled
    # перезавантаження скидає зміни
    When the admin refreshes the page and reopens the "Люди" tab with 4 cards
    Then all cards display their original descriptions and counters
    And the "Опублікувати" button becomes disabled
    # жирний пробіл вважається порожнім описом
    When the admin enters a bold space into the "Опис" field of card <first_card>
    Then the validation message "Поле обов'язкове" is displayed under the "Опис" field of card <first_card>
    And the "Опублікувати" button becomes disabled
    When the admin refreshes the page and reopens the "Люди" tab with 4 cards
    Then all cards display their original descriptions and counters
    And the "Опублікувати" button becomes disabled
    # додали і видалили символ -> повернення до початкового стану
    When the admin adds and deletes the character "X" in the "Опис" field of card <first_card>
    Then the "Опис" field and counter of card <first_card> match the original ones
    And the "Опублікувати" button becomes disabled
    When the admin refreshes the page and reopens the "Люди" tab with 4 cards
    Then all cards display their original descriptions and counters
    And the "Опублікувати" button becomes disabled

    Examples:
      | first_card | other_card |
      | 1          | 2          |
      | 2          | 3          |
      | 3          | 4          |
      | 4          | 1          |

  @Issue-999
  Scenario Outline: Verify validation messages for the description of card <card> in the "Люди" block
    When the admin selects the "Люди" tab
    And the admin waits for 4 people cards to be loaded
    Then exactly 4 cards are displayed
    When the admin clears the "Опис" field of card <card> and enters "Тест коня"
    Then no validation message is displayed under the "Опис" field of card <card> while it is focused
    When the admin triggers validation by blurring the "Опис" field of card <card>
    Then the validation message "Не менше 10 символів" is displayed under the "Опис" field of card <card>
    And exactly 1 validation message is displayed under the "Опис" field of card <card>
    And no validation messages are displayed under the cards other than card <card>
    When the admin clears the "Опис" field of card <card> and enters "Тест коней"
    And the admin triggers validation by blurring the "Опис" field of card <card>
    Then no validation message is displayed under the "Опис" field of card <card>
    When the admin clears the "Опис" field of card <card>
    Then no validation message is displayed under the "Опис" field of card <card> while it is focused
    When the admin triggers validation by blurring the "Опис" field of card <card>
    Then the validation message "Поле обов'язкове" is displayed under the "Опис" field of card <card>
    And exactly 1 validation message is displayed under the "Опис" field of card <card>
    And no validation messages are displayed under the cards other than card <card>
    # лише пробіли вважаються порожнім полем
    When the admin types 12 spaces into the "Опис" field of card <card>
    And the admin triggers validation by blurring the "Опис" field of card <card>
    Then the validation message "Поле обов'язкове" is displayed under the "Опис" field of card <card>
    And exactly 1 validation message is displayed under the "Опис" field of card <card>
    And no validation messages are displayed under the cards other than card <card>

    Examples:
      | card |
      | 1    |
      | 2    |
      | 3    |
      | 4    |

  @Issue-998
  Scenario Outline: Verify that the description of card <card> in the "Люди" block does not exceed 60 characters
    When the admin selects the "Люди" tab
    And the admin waits for 4 people cards to be loaded
    Then exactly 4 cards are displayed
    And the "Опублікувати" button becomes disabled
    When the admin remembers the original descriptions and counters of all cards
    And the admin clears the "Опис" field of card <card> and types 60 characters
    Then the "Опис" field of card <card> consists of 60 repeated characters "А"
    And the character counter for card <card> reads "60/60"
    When the admin types an additional character "B" into the "Опис" field of card <card>
    Then the "Опис" field of card <card> consists of 60 repeated characters "А"
    And the character counter for card <card> reads "60/60"
    When the admin triggers validation by blurring the "Опис" field of card <card>
    Then no validation message is displayed under the "Опис" field of card <card>
    When the admin clears the "Опис" field of card <card>
    And the admin pastes text "BBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBA" into the "Опис" field of card <card>
    Then the "Опис" field of card <card> consists of 60 repeated characters "B"
    And the character counter for card <card> reads "60/60"
    When the admin triggers validation by blurring the "Опис" field of card <card>
    Then no validation message is displayed under the "Опис" field of card <card>
    And the "Опублікувати" button becomes enabled
    When the admin refreshes the page and reopens the "Люди" tab with 4 cards
    Then all cards display their original descriptions and counters
    And the "Опублікувати" button becomes disabled

    Examples:
      | card |
      | 1    |
      | 2    |
      | 3    |
      | 4    |