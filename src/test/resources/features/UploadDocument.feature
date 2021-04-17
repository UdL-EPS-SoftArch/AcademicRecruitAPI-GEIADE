Feature: Upload Document

  In order to upload a document
  As a secretary
  I want to upload a document

  Background:
    Given There is a registered administrator with username "admin" and password "password" and email "admin@sample.app"
    And I login as "admin" with password "password"

  Scenario: Admin uploads a document
    Given I create a document with title "Document de prova"
    When I upload a empty text file to document with id 1
    Then The response code is successful
    And It has been uploaded a document id 1

  Scenario: Admin deletes a document
    Given I create a document with title "Document de prova"
    When I upload a empty text file to document with id 1
    And I delete the text file of document with with id 1
    Then The response code is successful
    And The file of document with with id 1 doesn't exist anymore