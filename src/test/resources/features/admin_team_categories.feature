@admin @team
Feature: Team categories in the admin panel
  Admins can create team member categories
  and get validation feedback while filling in the category form.

  Background:
    Given the admin is logged in
    And the admin opens the Team page
    And the admin opens the Add category modal

  @TC545
  Scenario Outline: Min length error and clean-up icon in the "<field>" field
    When the admin enters "abc" into the "<field>" field
    And the admin clicks outside the field
    Then the error "Не менше 5 символів" is displayed for the "<field>" field
    And the clean-up icon is not displayed in the "<field>" field
    When the admin clicks inside the "<field>" field
    Then the clean-up icon is displayed in red in the "<field>" field

    Examples:
      | field |
      | Назва |
      | Опис  |

  @TC544
  Scenario Outline: Clean-up icon follows focus and clears the "<field>" field
    When the admin clicks inside the "<field>" field
    Then the clean-up icon is not displayed in the "<field>" field
    When the admin enters "Volunteers" into the "<field>" field
    Then the clean-up icon is displayed in the "<field>" field
    When the admin clicks outside the field
    Then the clean-up icon is not displayed in the "<field>" field
    When the admin clicks inside the "<field>" field
    Then the clean-up icon is displayed in the "<field>" field
    When the admin clicks the clean-up icon in the "<field>" field
    Then the "<field>" field is empty
    And the "<field>" character counter shows "<emptyCounter>"

    Examples:
      | field | emptyCounter |
      | Назва | 0/20         |
      | Опис  | 0/200        |

  @TC542
  Scenario: Confirming the close discards the entered category values
    When the admin enters "Autotest category" into the "Назва" field
    And the admin enters "Autotest category description" into the "Опис" field
    And the admin closes the Add category modal
    Then the "Зміни будуть втрачені. Бажаєте продовжити?" confirmation pop-up is displayed
    When the admin confirms closing in the pop-up
    Then the confirmation pop-up is closed
    And the Add category modal is closed
    And the category "Autotest category" is not displayed on the Team page
    When the admin opens the Add category modal
    Then the "Назва" field is empty
    And the "Опис" field is empty

  @TC538
  Scenario Outline: Closing the modal with a non-empty "<field>" field asks for confirmation
    When the admin enters "Autotest" into the "<field>" field
    And the admin closes the Add category modal
    Then the "Зміни будуть втрачені. Бажаєте продовжити?" confirmation pop-up is displayed
    And the pop-up has the "Так" and "Ні" buttons
    And the Add category modal is still open

    Examples:
      | field |
      | Назва |
      | Опис  |

  @TC527
  Scenario: Closing the unchanged Add category modal does not ask for confirmation
    Given the "Назва" field is empty
    And the "Опис" field is empty
    When the admin closes the unchanged Add category modal
    Then the Add category modal is closed
    And no confirmation pop-up is displayed