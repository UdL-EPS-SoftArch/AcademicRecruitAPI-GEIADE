Feature: Update Document

  In order to update the documents
  As a secretary
  I want to update documents


  Background:
    Given There is a registered administrator with username "admin" and password "password" and email "admin@sample.app"
    Given I create a document with title "Resolucio"

  Scenario: Admin update existent document
    When I change the title of the document with id "2" to "Resolucio2"
    Then The response code is 200
    And The previously updated document has now title "Resolucio2"
