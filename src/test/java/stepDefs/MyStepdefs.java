package stepDefs;

import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.After;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static org.junit.Assert.assertEquals;

public class MyStepdefs {

    private WebDriver driver;
    private WebDriverWait wait;


    @Before

    public void setup() {
        System.setProperty("webdriver.chrome.driver", "/usr/local/chromedriver_mac_arm64/chromedriver");
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.addArguments("--remote-allow-origins=*", "ignore-certificate-errors");
        driver = new ChromeDriver(chromeOptions);
        driver.manage().window().maximize();
        JavascriptExecutor js = (JavascriptExecutor) driver;
        driver.get("https://login.mailchimp.com/signup/");
        js.executeScript("window.scrollBy(0,500)", "");
        wait = new WebDriverWait(driver, Duration.ofSeconds(20));


    }

    @Given("I have written the email address {string}")
    public void iHaveWrittenAn(String email) {
        driver.findElement(By.id("email")).sendKeys(email);

    }

    @Given("I have entered the username {string}")
    public void iHaveEnteredA(String username) {
        WebElement userId = driver.findElement(By.id("new_username"));
        //driver.findElement(By.id("new_username")).sendKeys("");
        userId.click();
        userId.clear();
        driver.findElement(By.id("new_username")).sendKeys(username);
    }

    @Given("I have added the password {string}")
    public void iHaveAddedThePassword(String password) {
        driver.findElement(By.id("new_password")).sendKeys(password);
    }

    @When("I click the Sign Up button")
    public void iClickTheSignUpButton() {
        Actions action = new Actions(driver);
        WebElement signUp = driver.findElement(By.id("create-account-enabled"));
        wait.until(ExpectedConditions.elementToBeClickable(signUp));
        action.moveToElement(signUp).perform();
        signUp.click();


    }

    @Then("I create an account {string}")
    public void iCreateAnAccount(String expected) {
        String actual = "";
        String successText = "";
        String helpInfo = "";


        try {
            wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(".\\!margin-bottom--lv3")));
            WebElement success = driver.findElement(By.cssSelector(".\\!margin-bottom--lv3"));
            successText = success.getText();
        } catch (Exception ignore) {}

        try {
            wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(3));
            WebElement helpButton = driver.findElement(By.id("recaptcha-help-button"));
            helpButton.click();
            ;
            WebElement info = driver.findElement(By.cssSelector(".rc-challenge-help"));
            helpInfo = info.getText();

        } catch (Exception ignore) {}


        if ((helpInfo.contains("Select each image that contains the object")) || (successText.equals("Check your email"))) {
            actual = "Check your email";
        }

        assertEquals(expected, actual);

    }


    @Given("I have entered a very long username")
    public void iHaveEnteredAVeryLongUsername() {

        WebElement userId = driver.findElement(By.id("new_username"));
        userId.click();
        userId.clear();

        String longUsername = "abc123";
        for (int i = 0; i < 100; i++) {
            longUsername += "1";
        }
        driver.findElement(By.id("new_username")).sendKeys(longUsername);

    }

    @Then("I get an error message concerning the long username")
    public void iGetAnErrorMessageConcerningTheLongUsername() {

        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id=\"signup-form\"]/fieldset/div[2]/div/span")));
        String actual = driver.findElement(By.xpath("//*[@id=\"signup-form\"]/fieldset/div[2]/div/span")).getText();
        String expected = "Enter a value less than 100 characters long";

        assertEquals(expected, actual);
    }

    @Given("I have entered an already taken username {string}")
    public void iHaveEnteredAnAlreadyTakenUsername(String username) {

        WebElement userId = driver.findElement(By.id("new_username"));
        userId.click();
        userId.clear();
        driver.findElement(By.id("new_username")).sendKeys(username);
    }

    @Then("I get an error message about the username already being taken")
    public void iGetAnErrorMessageAboutTheUsernameAlreadyBeingTaken() {
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id=\"signup-form\"]/fieldset/div[2]/div/span")));
        String actual = driver.findElement(By.xpath("//*[@id=\"signup-form\"]/fieldset/div[2]/div/span")).getText();
        String expected = "Great minds think alike - someone already has this username. If it's you, log in.";

        assertEquals(expected, actual);
    }

    @Given("I have not written an email {string}")
    public void iHaveNotWrittenAnEmail(String noEmail) {
        driver.findElement(By.id("email")).sendKeys(noEmail);
    }

    @Then("I get an error message about the email not being complete")
    public void iGetAnErrorMessageAboutTheEmailNotBeingComplete() {
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id=\"signup-form\"]/fieldset/div[1]/div/span")));
        String actual = driver.findElement(By.xpath("//*[@id=\"signup-form\"]/fieldset/div[1]/div/span")).getText();
        String expected = "An email address must contain a single @.";

        assertEquals(expected, actual);
    }
    @After
    public void tearDown(){
        driver.close();
        driver.quit();
    }


}
