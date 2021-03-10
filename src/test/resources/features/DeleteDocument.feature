Feature: Delete Document

  In order to delete the documents generated
  As an secretary
  I want to delete Document entities

  Background:


  Scenario: Delete an existing Document
    Given There is a registred document with id "id"
    When I delete the document with id "id"
    Then The response code is 200
    And The document with id "id" is deleted


  Scenario: Trying to delete an not-existing Document
    Given There is not a registred document with id "id"
    When I delete the document with id "id"
    Then The response code is 403
