Feature: Create SelectionProcess
  In order to create a selection process instance
  As a user with role secretary
  I want to create the selection process

  Background:
    Given There is a registered administrator with username "admin" and password "password" and email "admin@sample.app"

  Scenario: User with secretary role create selection process
    Given I login as "admin" with password "password"
    When I create a selection process with vacancy "sample-vacancy"
    Then The response code is 201
    And It has been created a selection process with vacancy "sample-vacancy"