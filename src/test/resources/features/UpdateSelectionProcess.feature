Feature: Update SelectionProcess
  In order to update a selection process
  As a admin
  I want to update a selection process

  Background:
    Given There is a registered administrator with username "admin" and password "password" and email "admin@sample.app"
    And I login as "admin" with password "password"
    And I create a selection process with vacancy "sample-vacany"

  Scenario: Admin updates vacancy
    When I change vacancy of selection process with id "1" to "sample-vacancy-updated"
    Then The response code is 200
    And The previously updated selection process has now vacancy "sample-vacancy-updated"