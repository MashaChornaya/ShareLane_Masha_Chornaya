import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class SignUpTests {
  public WebDriver driver;
    @BeforeClass
    public void setUp() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
    }

    @BeforeMethod
    public void navigate() {
        driver.get("https://www.sharelane.com/cgi-bin/register.py");
    }

    @Test
    public void zipCodePositiveTest() throws InterruptedException {
        driver.findElement(By.name("zip_code")).sendKeys("12345");
        WebElement continueButton = driver.findElement(By.cssSelector("[value='Continue']"));
        continueButton.click();
        Assert.assertEquals(driver.findElement(By.name("zip_code")).isDisplayed(), true, "Zip code should not be displayed");
        Assert.assertTrue(driver.findElement(By.name("zip_code")).isDisplayed(), "Zip code should not be displayed");
    }
    @Test
    public void zipCodeNegativeTestEmpty() throws InterruptedException {
        driver.findElement(By.name("zip_code")).sendKeys("");
        WebElement continueButton = driver.findElement(By.cssSelector("[value='Continue']"));
        continueButton.click();
        String messageOfError = driver.findElement(By.cssSelector(".error_message")).getText();
        String expectedText = "Oops, error on page. ZIP code should have 5 digits";
        Assert.assertEquals(messageOfError, expectedText, "Zip code should not be empty");
        Assert.assertTrue(Boolean.parseBoolean(driver.findElement(By.cssSelector(".error_message")).getText()),"Zip code should not be emty!");
    }
    @Test
    public void zipCodeNegativeTestShort() throws InterruptedException {
        driver.findElement(By.name("zip_code")).sendKeys("123");
        WebElement continueButton = driver.findElement(By.cssSelector("[value='Continue']"));
        continueButton.click();
        String messageOfError = driver.findElement(By.cssSelector(".error_message")).getText();
        String expectedText = "Oops, error on page. ZIP code should have 5 digits";
        Assert.assertEquals(messageOfError, expectedText, "Zip code should not be too short");
        Assert.assertEquals(driver.findElement(By.cssSelector(".error_message")).getText(),"Oops, error on page. ZIP code should have 5 digits","gshfgnfgnn");
        Assert.assertTrue(Boolean.parseBoolean(messageOfError),"Zip code should not be too short");
    }
    @Test
    public void SingUpPositiveTest() throws InterruptedException {
        driver.findElement(By.name("zip_code")).sendKeys("12345");
        WebElement continueButton = driver.findElement(By.cssSelector("[value='Continue']"));
        continueButton.click();
        driver.findElement(By.name("first_name")).sendKeys("Mary");
        driver.findElement(By.name("last_name")).sendKeys("Black");
        driver.findElement(By.name("email")).sendKeys("qwertyuiop@mail.com");
        driver.findElement(By.name("password1")).sendKeys("123qwerty");
        driver.findElement(By.name("password2")).sendKeys("123qwerty");
        WebElement registerButton = driver.findElement(By.cssSelector("[value='Register']"));
        registerButton.click();
        String massageOfSuccessfullySignUp = driver.findElement(By.cssSelector(".confirmation_message")).getText();
        String expectedMassage = "Account is created!";
        Assert.assertEquals(massageOfSuccessfullySignUp, expectedMassage);
    }
    @Test
    public void SingUpNegativeTestLittlePassword() throws InterruptedException {
        driver.findElement(By.name("zip_code")).sendKeys("12345");
        WebElement continueButton = driver.findElement(By.cssSelector("[value='Continue']"));
        continueButton.click();
        driver.findElement(By.name("first_name")).sendKeys("Mary");
        driver.findElement(By.name("last_name")).sendKeys("Black");
        driver.findElement(By.name("email")).sendKeys("qwertyuiop@mail.com");
        driver.findElement(By.name("password1")).sendKeys("123");
        driver.findElement(By.name("password2")).sendKeys("123");
        WebElement registerButton = driver.findElement(By.cssSelector("[value='Register']"));
        registerButton.click();
        String massageOfError = driver.findElement(By.cssSelector(".error_message")).getText();
        String expectedResult = "Oops, error on page. Some of your fields have invalid data or email was previously used";
        Assert.assertEquals(massageOfError, expectedResult, "Password should be more than 3 symbols");
        Assert.assertTrue(Boolean.parseBoolean(driver.findElement(By.cssSelector(".error_massage")).getText()),  "Pass should be more than 3 symbols");
    }
    @Test
    public void SingUpNegativeTestNoFirstName() throws InterruptedException {
        driver.findElement(By.name("zip_code")).sendKeys("12345");
        WebElement continueButton = driver.findElement(By.cssSelector("[value='Continue']"));
        continueButton.click();
        driver.findElement(By.name("first_name")).sendKeys("");
        driver.findElement(By.name("last_name")).sendKeys("Black");
        driver.findElement(By.name("email")).sendKeys("qwertyuiop@mail.com");
        driver.findElement(By.name("password1")).sendKeys("1234");
        driver.findElement(By.name("password2")).sendKeys("1234");
        WebElement registerButton = driver.findElement(By.cssSelector("[value='Register']"));
        registerButton.click();
        boolean massageOfError= Boolean.parseBoolean(driver.findElement(By.cssSelector(".error_message")).getText());
        String expectedResult="Oops, error on page. Some of your fields have invalid data or email was previously used";
        Assert.assertEquals(massageOfError,expectedResult, "First name should be entered");
        Assert.assertTrue(massageOfError,"First name should be entered");
    }

    @AfterClass
    public void tearDown() {
        driver.quit();
    }
}