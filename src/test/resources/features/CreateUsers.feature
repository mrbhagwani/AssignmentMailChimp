Feature: Creating new user using different scenarios.


  Scenario: Create new user correctly - everything goes well.
    Given I have written the email address "hemant@mail.com"
    Given I have entered the username "Soooooooooooooooooonnnnnnnnnnnno"
    Given I have added the password "Test123!"
    When I click the Sign Up button
    Then I create an account "Check your email"


    Scenario: Create new user - username is too long.
      Given I have written the email address "hemant@mail.com"
      Given I have entered a very long username
      Given I have added the password "Test123!"
      When I click the Sign Up button
      Then I get an error message concerning the long username


      Scenario: Create new user - username is already taken.
        Given I have written the email address "hemant@mail.com"
        Given I have entered an already taken username "Sonno"
        Given I have added the password "Test123!"
        When I click the Sign Up button
        Then I get an error message about the username already being taken


        Scenario: Create new user - email is missing.
          Given I have not written an email ""
          Given I have entered the username "Soonnnnnonnnnnnnnno"
          Given I have added the password "Test123!"
          When I click the Sign Up button
          Then I get an error message about the email not being complete
