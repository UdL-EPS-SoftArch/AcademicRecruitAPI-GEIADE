Feature: Create Document

  In order to create the documents generated
  As a secretary
  I want to create documents

  Background:


  Scenario: Create a Document with id "id" already existent
    Given There is a registred document with id "id"
    When I create the document with id "id"
    Then The response code is 403
    And The document with id "id" is not created


  Scenario: Create a Document with id "id" not existent
    Given There is not a registred document with id "id"
    When I create the document with id "id"
    Then The response code is 200
    And The Document is created



