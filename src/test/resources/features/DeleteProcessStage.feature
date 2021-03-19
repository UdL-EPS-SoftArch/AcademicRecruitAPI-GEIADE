Feature: Delete ProcessStage
  In order to delete the process stage
  As a admin
  I want to delete a process stage

  Background:
    Given There is a registered administrator with username "admin" and password "password" and email "admin@sample.app"
    And I login as "admin" with password "password"
    And I create a processStage with name "Ronda 1" and step 2

  Scenario: Admin deletes process stage step
    When I delete the process stage with id "1"
    Then The response code is 204
    And The previously deleted process stage doesn't exist
