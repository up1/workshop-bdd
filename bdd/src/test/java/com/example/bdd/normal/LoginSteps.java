package com.example.bdd.normal;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

import static org.springframework.test.util.AssertionErrors.assertTrue;

@SpringBootTest
@ContextConfiguration
public class LoginSteps {

    private String currentPage;
    private String username;
    private String password;
    private boolean loginSuccessful;

    @Given("I am on the login page")
    public void i_am_on_the_login_page() {
        currentPage = "login";
        System.out.println("User is on the login page");
    }

    @When("I login with username {string} and password {string}")
    public void i_login_with_username_and_password(String username, String password) {
        this.username = username;
        this.password = password;

        // Simulate login logic
        if ("demo".equals(username) && "mode".equals(password)) {
            loginSuccessful = true;
            currentPage = "welcome";
            System.out.println("Login successful for user: " + username);
        } else {
            loginSuccessful = false;
            System.out.println("Login failed for user: " + username);
        }
    }

    @Then("I should see the welcome page")
    public void i_should_see_the_welcome_page() {
        assertTrue("Login should be successful", loginSuccessful);
        assertTrue("Should be on welcome page", "welcome".equals(currentPage));
        System.out.println("User successfully reached the welcome page");
    }
}
