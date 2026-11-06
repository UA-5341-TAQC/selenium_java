@Partners @Public
Feature: Partners page content and layout
  As a visitor of the Victory Center website
  I want to view partner sections and partnership options
  So that I can learn about the partners and support the organization

  Background:
    Given the Partners page is open

  @Issue-23
  Scenario: Page sections follow the expected order
    Then the intro, partner sections, quote and footer appear in that order

  @Issue-24
  Scenario: Every partner profile has an image and description
    Then every partner section contains profiles with loaded images and non-empty descriptions

  @Issue-25
  Scenario: Partners are grouped into separate sections
    Given at least two partner sections are displayed
    Then every partner section has a non-empty heading and description
    And partner profiles are contained in their section below its description

  @Issue-26
  Scenario: Partner profiles are arranged from left to right
    Then partner profiles run left to right within rows without overlapping

  @Issue-30
  Scenario: Quote section displays partnership and support buttons
    When the visitor scrolls to the Partners quote section
    Then the quote section displays the Become a Partner button
    And the quote section displays the Donate button

  @Issue-31
  Scenario: Intro section displays an image, title and description
    Then the Partners intro displays a loaded image
    And the Partners intro contains a non-empty title and description below the title
