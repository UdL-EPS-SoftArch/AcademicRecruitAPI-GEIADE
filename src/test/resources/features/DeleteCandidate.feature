Feature: Candidate Delte
  In order to control candidates
  As an administrator
  I want to delete existing candidates

  Background:
    Given There is a registered administrator with username "admin" and password "password" and email "admin@sample.app"

  Scenario: Delete existing candidate
    Given There is a registered candidate with dni "dni"
    And I login as "admin" with password "password"
    When I delete an existing candidate with name "name" and dni "dni"
    Then The response code is 201
    And It has been deleted a candidate with name "name" and dni "dni"