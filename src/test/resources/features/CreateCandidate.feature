Feature: Create Participant
  In order to create the candidates
  As a admin
  I want to create a candidate

  Background:
    Given There is a registered administrator with username "admin" and password "password" and email "admin@sample.app"

  Scenario: Admin Created candidate
    Given I login as "admin" with password "password"
    When I create a candidate with name "name"
    Then The response code is 201
    And It has been created a candidate with name "name"
