Feature: Candidate Update
  In order to control candidates
  As an administrator
  I want to update existing candidates

  Background:
    Given There is a registered administrator with username "admin" and password "password" and email "admin@sample.app"

  Scenario: Update existing candidate
    Given I login as "<string>" with password "<string>"
    When I change the name of the candidate with name "lluc"
    Then The response code is 201
    And The previously updated candidate has now name "lluc"