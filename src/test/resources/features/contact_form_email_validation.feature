@ContactUs @Validation @Public
Feature: Contact form e-mail validation
  As a visitor of the Victory Center website
  I want to be told when the e-mail I entered is not valid
  So that the form is sent only with an address that can be used for the reply

  Background:
    Given the visitor opens the "Contact Us" page at "/contact-us"

  @Issue-2420 @Issue-4167
  Scenario Outline: Contact form rejects an invalid e-mail
    When the visitor fills the contact form with the following data:
      | name    | Test QA      |
      | email   | <email>      |
      | subject | Test subject |
      | message | Test message |
    Then the e-mail field should contain "<email>"
    When the visitor submits the contact form
    Then the e-mail error "Некоректний E-mail" should be displayed
    And the contact form should not be sent

    Examples:
      | email                 |
      | userexample.com       |
      | user@example          |
      | user@localhost        |
      | @mail.com             |
      | user@                 |
      | user@.com             |
      | user@mail.c           |
      | test@test@mail.com    |
      | test..test@mail.com   |
      | test!#$%test@mail.com |
      | .test@mail.com        |
      | test.@mail.com        |
      | test@mail.com.        |
      | test@.mail.com        |
      | testmail.com@         |

  @Issue-2420
  Scenario Outline: Spaces cannot be entered into the e-mail field
    When the visitor types "<typed>" into the e-mail field
    Then the e-mail field should contain "<expected>"

    Examples:
      | typed              | expected           |
      | us er@mail.com     | user@mail.com      |
      | user name@mail.com | username@mail.com  |
      | test@ mail.com     | test@mail.com      |