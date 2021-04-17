Feature: Update Document

  In order to update the documents
  As a secretary
  I want to update documents


  Background:
    Given There is a registered administrator with username "admin" and password "password" and email "admin@sample.app"
    And There is a registered user with username "user" and password "password" and email "user@sample.app"
    And I login as "admin" with password "password"
    And I create a document with title "Resolucio"

  Scenario: Admin updates existent document
    Given I login as "admin" with password "password"
    When I change the title of the document with id "1" to "Resolucio2"
    Then The response code is 200
    And The previously updated document has now title "Resolucio2"

  Scenario: User updates existent document created by admin
    Given I login as "user" with password "password"
    When I change the title of the document with id "1" to "Resolucio2"
    Then The response code is 403
