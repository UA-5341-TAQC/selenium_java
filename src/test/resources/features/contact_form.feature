@ContactUsForm @Public

Feature: Contact Us Form Functionality
  As a public visitor of the Victory Center website
  I want  use the contact form in three available ways
  So that I can reach out to the organization

  Background:
    Given user is on the Main page

  @Issue-753 @UI @Functional
  Scenario Outline: Verify threshold warning message for Subject field
    Given the user opens the contact form via "<entry_point>"
    When the visitor enters a string of 79 "A" characters into the subject field into form
    Then the system displays the counter message empty
    When the visitor enters a string of 80 "A" characters into the subject field into form
    Then the system displays the counter message "<expected_message_1>"
    When the visitor enters a string of 81 "A" characters into the subject field into form
    Then the system displays the counter message "<expected_message_2>"
    When the visitor enters a string of 99 "A" characters into the subject field into form
    Then the system displays the counter message "<expected_message_3>"
    When the visitor enters a string of 100 "A" characters into the subject field into form
    Then the system displays the counter message "<limit_message>"

    Examples: Entry Point A
      | entry_point | expected_message_1     | expected_message_2     | expected_message_3 | limit_message            |
      | EntryPointA | Залишилось 20 символів | Залишилось 19 символів | Залишився 1 символ | Ліміт символів вичерпано |

    Examples: Entry Point B
      | entry_point | expected_message_1     | expected_message_2     | expected_message_3 | limit_message            |
      | EntryPointB | Залишилось 20 символів | Залишилось 19 символів | Залишився 1 символ | Ліміт символів вичерпано |

    Examples: Entry Point C
      | entry_point | expected_message_1     | expected_message_2     | expected_message_3 | limit_message            |
      | EntryPointC | Залишилось 20 символів | Залишилось 19 символів | Залишився 1 символ | Ліміт символів вичерпано |

  @Issue-755 @UI @Functional
  Scenario Outline: Verify threshold warning message for Message field
    Given the user opens the contact form via "<entry_point>"
    When the visitor enters a string of 1799 "A" characters into the message field into form
    Then the system displays the counter message empty
    When the visitor enters a string of 1800 "A" characters into the message field into form
    Then the system displays the counter message "<expected_message>"

    Examples: Entry Point
      | entry_point | expected_message        |
      | EntryPointA | Залишилось 200 символів |
      | EntryPointB | Залишилось 200 символів |
      | EntryPointC | Залишилось 200 символів |