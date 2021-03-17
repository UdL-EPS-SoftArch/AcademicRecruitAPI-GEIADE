Feature: Delete SelectionProcess
  In order to delete a selection process instance
  As a user with role secretary
  I want to delete the a selection process

  Background:
    Given There is a registered administrator with username "admin" and password "password" and email "admin@sample.app"

  Scenario: User with secretary role delete a selection process
    Given I login as "admin" with password "password"
    And A created selection process with vacancy "sample"
    When I delete the selection process I have just created with vacancy "sample"
    Then The response code is 201
    And It has been deleted a selection process with vacancy "sample"
    Then The response code is 404
