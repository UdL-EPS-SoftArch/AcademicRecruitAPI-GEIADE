Feature: Update Document

  In order to update an existent documents
  As a secretary
  I want to update documents

  Background:


  Scenario: Change the name of an existent Document
    Given There is a registred document with id "id"
    When I change the name from name "old" to name "new"
    Then The response code is 200
    And The document with id "id" new name is "new"