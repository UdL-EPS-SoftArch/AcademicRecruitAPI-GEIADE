Feature: Read SelectionProcess
  In order to read a selection process instance
  As a user with role secretary
  I want to read the a selection process

  Background:
    Given There is a registered administrator with username "admin" and password "password" and email "admin@sample.app"

  Scenario: User with secretary role read a selection process
    Given I login as "admin" with password "password"
    And I create a selection process with vacancy "sample"
    When I read the selection process I have just created with vacancy "sample"
    Then The response code is successful
