package com.example.bdd.web;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import static org.springframework.test.util.AssertionErrors.assertTrue;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

@SpringBootTest
@ContextConfiguration
public class LoginWebSteps {

    private String currentPage;
    private String username;
    private String password;
    private boolean loginSuccessful;

    private WebDriver driver;

    @Before
    public void setup() {
        ChromeOptions options = new ChromeOptions();
//        options.addArguments("--headless"); // Run in headless mode
        driver = new ChromeDriver(options);
    }

    @After
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Given("I am on the login page")
    public void i_am_on_the_login_page() {
        currentPage = "login";
        System.out.println("User is on the login page");

        driver.get("https://demo-login-workshop.vercel.app/");
        assertTrue("Should be on login page",
                driver.getTitle().contains("Login") ||
                        driver.getCurrentUrl().contains("login"));
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

        WebElement usernameField = driver.findElement(By.id("username_field"));
        WebElement passwordField = driver.findElement(By.id("password_field"));
        WebElement loginButton = driver.findElement(By.id("login_button"));

        usernameField.sendKeys(username);
        passwordField.sendKeys(password);
        loginButton.click();
    }

    @Then("I should see the welcome page")
    public void i_should_see_the_welcome_page() {
        assertTrue("Login should be successful", loginSuccessful);
        assertTrue("Should be on welcome page", "welcome".equals(currentPage));
        System.out.println("User successfully reached the welcome page");

        assertTrue("Should be redirected to welcome page",
                driver.getCurrentUrl().contains("welcome") &
                        driver.getPageSource().contains("Welcome"));

        WebElement resultElement = driver.findElement(By.xpath("//*[@data-test='result']"));
        assertTrue("Login success message should be displayed",
                resultElement.getText().contains("Login succeeded. Now you can logout."));
    }
}
