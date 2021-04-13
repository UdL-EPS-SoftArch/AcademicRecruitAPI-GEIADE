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


  Scenario: Document created by a User
    Given I login as "admin" with password "password"
    And A Document is created by User "Secretari"
    When A Document is created by User "Secretari"
    Then The response code is 201
    And It has been created a processStage with name "Ronda 1" and step 2
    And It has been created a processStage associated to selection process with vacancy "Profesor"