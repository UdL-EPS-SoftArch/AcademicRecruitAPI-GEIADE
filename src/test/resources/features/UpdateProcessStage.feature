Feature: Update ProcessStage
  In order to modify the process stage
  As a admin
  I want to update a process stage

  Background:
    Given There is a registered administrator with username "admin" and password "password" and email "admin@sample.app"
    And I login as "admin" with password "password"
    And I create a processStage with name "Ronda 1" and step 2

  Scenario: Admin updates process stage step
    When I change the step of the process stage with id "1" to 1
    Then The response code is 200
    And The previously updated process stage has now step 1

  Scenario: Admin Created Process Stage associated to Selection Process and put it as active process stage
    When I create a selection process with vacancy "Profesor"
    And I create a processStage with name "Ronda 2" and step 2 associated to selection process with vacancy "Profesor"
    And I associate the processStage with id 3 as active into to the selection process with vacancy "Profesor"
    Then The previously updated selection process has now an active process stage with id 3
