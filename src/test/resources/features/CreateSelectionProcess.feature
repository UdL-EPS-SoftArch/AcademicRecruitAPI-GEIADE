Feature: Create Selection Process

  In order to track the recruitment method
  As a user with role secretary
  I want to create the selection process

  Background:
    Given There is a registered "Participant" with role "Secretary" and password "password" and email "admin@sample.app"

  Scenario: Create new Selection Process
    Given There is no created Selection Process
    And I login as "user" with password "password"
    When I create a new Selection Process with vacancy "vacancy-sample"
    Then The response code is 201
    And It has been created a Selection Process with vacancy "vacancy-sample"
    And I can display the Selection Process with id "id-sample"

  Scenario: Create Selection Process when not authenticated
    Given I'm not logged in
    When I create a new Selection Process with vacancy "vacancy-sample"
    Then The response code is 401
    And It has not been created a Selection Process with id "id-sample"

  Scenario: Create Selection Process when not authorized
    Given I'm logged in as "user" with password "password" and role "president"
    When I create a new Selection Process with vacancy "vacancy-sample"
    Then The response code is 403
    And It has not been created a Selection Process with id "id-sample"

  Scenario: Create Selection Process when not authorized
    Given I'm logged in as "user" with password "password" and role "vocal"
    When I create a new Selection Process with vacancy "vacancy-sample"
    Then The response code is 403
    And It has not been created a Selection Process with id "id-sample"