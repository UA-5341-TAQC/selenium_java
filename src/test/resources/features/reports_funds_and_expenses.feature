@Reports @Public
Feature: Reports page: total collected funds and expenses chart
  As a visitor of the Victory Center website
  I want to see how much money was collected and how it was spent
  So that I can trust the foundation

  Background:
    Given the visitor opens the "Reports" page at "/reports"
    And the website language is set to Ukrainian

  @Issue-882
  Scenario: Total collected funds use spaces as thousands separators and have no kopiykas
    Then the total collected funds are shown with spaces as thousands separators and without kopiykas
    And the total collected funds equal the sum of all funding sources without kopiykas

  @Issue-882
  Scenario: Total collected funds with kopiykas above 50 are truncated, not rounded up
    Given the funding report is replaced with the sources "1000000.00" and "249854.99"
    Then the total collected funds are "1 249 854 грн"

  @Issue-882
  Scenario: Expenses chart shows a percentage and an absolute amount for every segment
    Then every segment of the expenses chart shows a percentage and an absolute amount
    And the percentages of the expenses chart sum up to 100 percent
    And the expenses chart matches the report data