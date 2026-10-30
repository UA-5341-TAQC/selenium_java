@ContactUs @Public
Feature: Contact Us Page Functionality and Form Validation
  As a public visitor of the Victory Center website
  I want to view contact details and use the contact form on the "Зв'язатися" page
  So that I can reach out to the organization or connect via social media

  Background:
    Given the visitor opens the "Contact Us" page at "/contact-us"

  @Issue-752 @UI @Smoke
  Scenario: Verify contacts form and details UI elements on the public Contact Us page
    Then the contact details section title should be "МИ ЗАВЖДИ ВІДКРИТІ ДЛЯ ВАС"
    And the contact details section description should be "Якщо у вас є запитання, пропозиції чи ви шукаєте підтримку — наша команда завжди готова допомогти."
    And the contact email should contain "@" and have a visible copy button
    And the contact phone number should contain "+" and have a visible copy button
    And the contact address should not be blank
    And the social media section should display 3 links:
      | Facebook  |
      | Telegram  |
      | Instagram |
    And all contact form fields and the submit button should be displayed
    And the submit button text should be "Надіслати"

  @Issue-799 @Smoke
  Scenario: Verify that the contact form is available and accepts valid input
    Then all contact form fields and the submit button should be displayed
    When the visitor fills the contact form with the following data:
      | name    | Олександр Тестовий                                   |
      | email   | test.user@example.com                                |
      | subject | Загальне запитання                                   |
      | message | Доброго дня! Це перевірка форми зворотного зв'язку. |
    Then the contact form fields should contain the entered values
    And the submit button with text "Надіслати" should be displayed

  @Issue-749 @Validation @Boundary
  Scenario: Verify "Тема звернення" field minimum length validation boundaries
    When the visitor enters "Тест" into the subject field and triggers blur
    Then the validation error "Мінімум 5 символів" should be displayed
    When the visitor enters "Тести" into the subject field and triggers blur
    Then the validation error "Мінімум 5 символів" should disappear

  @Issue-754 @Validation @Boundary
  Scenario: Verify "Повідомлення" field minimum length validation boundaries
    When the visitor enters "123456789" into the message field and triggers blur
    Then the validation error "Мінімум 10 символів" should be displayed
    When the visitor enters "1234567890" into the message field and triggers blur
    Then the validation error "Мінімум 10 символів" should disappear

  @Issue-756 @Issue-749 @Issue-754 @Validation @Boundary
  Scenario: Verify maximum character limit blocks further input on Subject and Message fields
    When the visitor enters a string of 100 "A" characters into the subject field
    Then the subject field value length should be 100
    When the visitor enters a string of 100 "A" characters followed by "B" into the subject field
    Then the subject field value length should remain 100
    When the visitor enters a string of 2000 "M" characters into the message field
    Then the message field value length should be 2000
    When the visitor enters a string of 2000 "M" characters followed by "X" into the message field
    Then the message field value length should remain 2000

  @Issue-757 @Validation
  Scenario: Verify validation errors for simultaneous under-minimum input in Subject and Message fields
    When the visitor enters "Hi" into the subject field and triggers blur
    And the visitor enters "Short" into the message field and triggers blur
    Then the following validation errors should be displayed:
      | Мінімум 5 символів  |
      | Мінімум 10 символів |

  @Issue-761 @Navigation
  Scenario: Verify social media links navigation URLs and target attributes
    Then the contact details section should contain 3 social media links
    And each social media link URL should start with "https://"
    And each social media link should have target attribute "_blank"

  @Issue-760 @Functionality
  Scenario: Verify copy email and phone number buttons functionality
    When the visitor clicks the copy email button
    Then the copy email button should remain displayed and enabled
    When the visitor clicks the copy phone number button
    Then the copy phone number button should remain displayed and enabled

  @Issue-795 @Validation
  Scenario Outline: Verify that the contact form rejects invalid email addresses
    When the visitor enters "<invalid_email>" into the email field and triggers blur
    Then the validation error "Некоректний E-mail" should be displayed

    Examples:
      | invalid_email   |
      | test@           |
      | @test.com       |
      | user@domain     |
      | user.domain.com |

  @Issue-796 @Validation
  Scenario Outline: Verify that the contact form accepts valid email addresses
    When the visitor enters "<valid_email>" into the email field and triggers blur
    Then the validation error "Некоректний E-mail" should not be displayed

    Examples:
      | valid_email              |
      | test.user@example.com    |
      | info@victorycenter.ua    |
      | user_123+tag@sub.org.ua  |
