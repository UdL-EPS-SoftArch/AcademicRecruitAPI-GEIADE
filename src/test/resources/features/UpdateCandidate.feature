Feature: Candidate Update
  In order to control candidates
  As an administrator
  I want to update existing candidates

  Background:
    Given There is a registered administrator with username "admin" and password "password" and email "admin@sample.app"

  Scenario: Update existing candidate
    Given There is a registered candidate with dni "dni"
    And I login as "admin" with password "password"
    When I update an existing candidate with name "newname" and dni "dni"
    Then The response code is 201
    And It has been updated a candidate with name "newname" and dni "dni"