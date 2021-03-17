Feature: Update Candidate
  In order to modify the candidate
  As an admin
  I want to update a candidate

  Background:
    Given There is a registered administrator with username "admin" and password "password" and email "admin@sample.app"
    And I login as "admin" with password "password"
    And I create a candidate with name "name"

  Scenario: Admin updates name
    When I change the name of the candidate to "lluc"
    Then The response code is 200
    And The previously updated candidate has now name "lluc"
