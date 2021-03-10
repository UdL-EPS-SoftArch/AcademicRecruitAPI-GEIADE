Feature: Candidate Creation
  In order to control candidates
  As an administrator
  I want to create new candidates

  Background:
    Given There is a registered administrator with username "admin" and password "password" and email "admin@sample.app"

  Scenario: Add new candidate
    Given There is no registered candidate with dni "dni"
    And I login as "admin" with password "password"
    When I register a new candidate with name "newname" and dni "dni"
    Then The response code is 201
    And It has been created a candidate with name "newname" and dni "dni"