Feature: Create Document

  In order to create the documents generated
  As a secretary
  I want to create documents


  Background:
    Given There is a registered administrator with username "admin" and password "password" and email "admin@sample.app"

  Scenario: Admin Created document
    Given I login as "admin" with password "password"
    When I create a document with title "title"
    Then The response code is 201
    And It has been created a document with title "title"

  Scenario: Admin Created document associated to Selection Process
    Given I login as "admin" with password "password"
    And I create a selection process with vacancy "vacancy"
    When I create a document with title "title" associated to selection process with vacancy "vacancy"
    Then The response code is 201
    And It has been created a document with title "title"
    And It has been created a document associated to selection process with vacancy "vacancy"

  Scenario: Admin assigns Candidate to a Document
    Given I login as "admin" with password "password"
    When I assign the candidate named "Miquel" to the document titled "document36"
    Then The response code is 201
    And It has been assigned the Candidate named "Miquel" to the Document titled "document36"

