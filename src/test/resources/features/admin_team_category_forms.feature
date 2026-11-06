@AdminTeamCategoryForms
Feature: Team category form validation and discarding changes
  Administrators can validate category fields and discard form changes.
  No scenario saves, publishes or deletes a category.

  Background:
    Given the admin is logged in
    And the admin opens the Team page

  @Issue-337
  Scenario: Category name accepts the minimum and maximum valid lengths
    When the admin opens the Add category modal
    And the admin enters 5 characters into the "Назва" field
    And the admin clicks outside the field
    Then the Team category "Назва" field should contain 5 characters without errors
    When the admin enters 20 characters into the "Назва" field
    And the admin clicks outside the field
    Then the Team category "Назва" field should contain 20 characters without errors

  @Issue-338
  Scenario: Category name rejects a short value and blocks characters beyond its limit
    When the admin opens the Add category modal
    And the admin enters 4 characters into the "Назва" field
    And the admin clicks outside the field
    Then the error "Не менше 5 символів" is displayed for the "Назва" field
    When the admin enters 20 characters into the "Назва" field
    And the admin clicks outside the field
    And the admin appends a character to the Team category "Назва" field
    Then the Team category "Назва" field should contain 20 characters without errors

  @Issue-339
  Scenario: Category description accepts the minimum and maximum valid lengths
    When the admin opens the Add category modal
    And the admin enters 5 characters into the "Опис" field
    And the admin clicks outside the field
    Then the Team category "Опис" field should contain 5 characters without errors
    When the admin enters 200 characters into the "Опис" field
    And the admin clicks outside the field
    Then the Team category "Опис" field should contain 200 characters without errors

  @Issue-340
  Scenario: Category description rejects a short value and blocks characters beyond its limit
    When the admin opens the Add category modal
    And the admin enters 4 characters into the "Опис" field
    And the admin clicks outside the field
    Then the error "Не менше 5 символів" is displayed for the "Опис" field
    When the admin enters 200 characters into the "Опис" field
    And the admin clicks outside the field
    And the admin appends a character to the Team category "Опис" field
    Then the Team category "Опис" field should contain 200 characters without errors

  @Issue-341
  Scenario: Both category fields are required before saving can be enabled
    When the admin opens the Add category modal
    And the admin enters 0 characters into the "Назва" field
    And the admin clicks outside the field
    And the admin enters 0 characters into the "Опис" field
    And the admin clicks outside the field
    Then the error "Поле обов'язкове" is displayed for the "Назва" field
    And the error "Поле обов'язкове" is displayed for the "Опис" field
    And saving the Team category should be disabled
    When the admin enters 10 characters into the "Опис" field
    And the admin clicks outside the field
    Then the error "Поле обов'язкове" is displayed for the "Назва" field
    And saving the Team category should be disabled
    When the admin enters 0 characters into the "Опис" field
    And the admin clicks outside the field
    And the admin enters 10 characters into the "Назва" field
    And the admin clicks outside the field
    Then the error "Поле обов'язкове" is displayed for the "Опис" field
    And saving the Team category should be disabled
    When the admin enters 10 characters into the "Опис" field
    And the admin clicks outside the field
    Then the Team category form should have no validation errors
    And saving the Team category should be enabled

  @Issue-342
  Scenario: Category fields trim leading and trailing spaces on blur
    When the admin opens the Add category modal
    And the admin enters " Менеджерка " into the "Назва" field
    And the admin clicks outside the field
    Then the Team category "Назва" value should be "Менеджерка" with the correct counter
    When the admin enters " Керує командою фахівців " into the "Опис" field
    And the admin clicks outside the field
    Then the Team category "Опис" value should be "Керує командою фахівців" with the correct counter

  @Issue-543
  Scenario: Declining closure preserves values in the new category form
    When the admin opens the Add category modal
    And the admin enters 10 characters into the "Назва" field
    And the admin clicks outside the field
    And the admin enters 30 characters into the "Опис" field
    And the admin clicks outside the field
    And the admin closes the Add category modal
    Then the Add category "Зміни будуть втрачені. Бажаєте продовжити?" confirmation pop-up is displayed
    And the pop-up has the "Так" and "Ні" buttons
    When the admin declines discarding Team category changes
    Then the Team category form should retain the entered values

  @Issue-317
  Scenario: The edit form lists existing categories without duplicates and selects one at a time
    When the admin opens the Edit category modal
    Then the editable Team categories should match the category bar without duplicates
    When the admin selects another existing Team category
    Then only that Team category should be selected

  @Issue-321
  Scenario Outline: Edited category fields validate lengths and block extra characters
    When the admin opens the Edit category modal
    And the admin enters 4 characters into the "<field>" field
    And the admin clicks outside the field
    Then the error "Не менше 5 символів" is displayed for the "<field>" field
    When the admin enters 5 characters into the "<field>" field
    And the admin clicks outside the field
    Then the Team category "<field>" field should contain 5 characters without errors
    When the admin enters <limit> characters into the "<field>" field
    And the admin clicks outside the field
    And the admin appends a character to the Team category "<field>" field
    Then the Team category "<field>" field should contain <limit> characters without errors

    Examples:
      | field | limit |
      | Назва | 20    |
      | Опис  | 200   |

  @Issue-324
  Scenario: Closing an unchanged edit form requires no confirmation
    When the admin opens the Edit category modal
    And the admin closes the unchanged Edit category modal
    Then the Edit category modal is closed
    And no confirmation pop-up is displayed

  @Issue-326
  Scenario: Confirming closure discards category edits without persisting them
    When the admin opens the Edit category modal
    And the admin enters 10 characters into the "Назва" field
    And the admin clicks outside the field
    And the admin enters 30 characters into the "Опис" field
    And the admin clicks outside the field
    And the admin closes the Edit category modal
    Then the Edit category "Зміни будуть втрачені. Бажаєте продовжити?" confirmation pop-up is displayed
    And the pop-up has the "Так" and "Ні" buttons
    When the admin confirms closing in the pop-up
    Then the Edit category modal is closed
    When the admin reloads the Team category page and reopens the original category
    Then the original Team category values should be unchanged

  @Issue-327
  Scenario: Declining closure preserves the category edits in the open form
    When the admin opens the Edit category modal
    And the admin enters 10 characters into the "Назва" field
    And the admin clicks outside the field
    And the admin enters 30 characters into the "Опис" field
    And the admin clicks outside the field
    And the admin closes the Edit category modal
    Then the Edit category "Зміни будуть втрачені. Бажаєте продовжити?" confirmation pop-up is displayed
    And the pop-up has the "Так" and "Ні" buttons
    When the admin declines discarding Team category changes
    Then the Team category form should retain the entered values

  @Issue-556
  Scenario: Category fields normalize consecutive spaces and update their counters on blur
    When the admin opens the Add category modal
    And the admin enters "  Нова  роль  " into the "Назва" field
    And the admin clicks inside the "Опис" field
    Then the Team category "Назва" value should be "Нова роль" with the correct counter
    When the admin enters "  Опис  нової  ролі  " into the "Опис" field
    And the admin clicks outside the field
    Then the Team category "Опис" value should be "Опис нової ролі" with the correct counter

  @Issue-323
  Scenario: Valid edits enable saving only after both category fields pass validation
    When the admin opens the Edit category modal
    Then saving the Team category should be disabled
    When the admin enters 4 characters into the "Назва" field
    And the admin clicks outside the field
    And the admin enters 4 characters into the "Опис" field
    And the admin clicks outside the field
    Then saving the Team category should be disabled
    When the admin enters 10 characters into the "Назва" field
    And the admin clicks outside the field
    Then saving the Team category should be disabled
    When the admin enters 30 characters into the "Опис" field
    And the admin clicks outside the field
    Then the Team category form should have no validation errors
    And saving the Team category should be enabled
    When the admin closes the Edit category modal
    Then the Edit category "Зміни будуть втрачені. Бажаєте продовжити?" confirmation pop-up is displayed
    When the admin confirms closing in the pop-up
    Then the Edit category modal is closed
    When the admin reloads the Team category page and reopens the original category
    Then the original Team category values should be unchanged

  @Issue-322
  Scenario Outline: Edited category counters update after every character typed or removed without blur
    When the admin opens the Edit category modal
    And the admin enters 0 characters into the "<field>" field
    Then the Team category "<field>" value should be "" with the correct counter
    When the admin appends "a" to the "<field>" field
    Then the Team category "<field>" value should be "a" with the correct counter
    When the admin appends "b" to the "<field>" field
    Then the Team category "<field>" value should be "ab" with the correct counter
    When the admin appends "c" to the "<field>" field
    Then the Team category "<field>" value should be "abc" with the correct counter
    When the admin removes the last character from the "<field>" field
    Then the Team category "<field>" value should be "ab" with the correct counter
    When the admin removes the last character from the "<field>" field
    Then the Team category "<field>" value should be "a" with the correct counter

    Examples:
      | field |
      | Назва |
      | Опис  |
