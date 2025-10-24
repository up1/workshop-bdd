*** Settings ***
Library    SeleniumLibrary
Test Teardown    Close All Browsers

*** Variables ***
${URL}    https://demo-login-workshop.vercel.app/

*** Test Cases ***
Login With Valid User
    Given I am on the login page
    When I login with username "demo" and password "mode"
    Then I should see the welcome page

Login With Invalid User
    Given I am on the login page
    When I login with username "invalid" and password "invalid"
    Then I should see the error message
    And I should be able to login again

*** Keywords ***
Given I am on the login page
    Open Browser    ${URL}    chrome
    Maximize Browser Window
    Wait Until Page Contains Element    id=username_field    timeout=10s
    Wait Until Page Contains Element    id=password_field    timeout=10s

When I login with username "${username}" and password "${password}"
    Input Text    id=username_field    ${username}
    Input Text    id=password_field    ${password}
    Click Button    id=login_button

Then I should see the welcome page
    Wait Until Page Contains Element    xpath=//*[@data-test="result"]    timeout=10s
    Element Should Contain    xpath=//*[@data-test="result"]    Login succeeded. Now you can logout.

Then I should see the error message
    Wait Until Page Contains Element    xpath=//*[@data-test="result"]    timeout=10s
    Element Should Contain    xpath=//*[@data-test="result"]    Login failed. Invalid user name and/or password.

And I should be able to login again
    Go to    ${URL}
    Wait Until Page Contains Element    id=username_field    timeout=10s
    Wait Until Page Contains Element    id=password_field    timeout=10s