@Reports @Public
Feature: Reports Page Functionality
  As a public visitor of the Victory Center website
  I want to be able to review published reports on the "Звітність" page

  Background:
    Given Reports page is open


  @Issue-615 @UI
  Scenario: Verify that the download icon is displayed for each PDF file
    When The reports page has at least one PDF file published
    Then The PDF file-s should be displayed
    And The PDF file-s should contain a download icon for each PDF file

