//package mobile.tests;
//
//import io.appium.java_client.AppiumDriver;
//import org.junit.jupiter.api.*;
//import org.openqa.selenium.By;
//import org.openqa.selenium.WebElement;
//import org.openqa.selenium.remote.DesiredCapabilities;
//import io.appium.java_client.android.AndroidDriver;
//
//import java.net.MalformedURLException;
//import java.net.URL;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//
//@TestInstance(TestInstance.Lifecycle.PER_CLASS)
//public class CalculatorMobileTest {
//
//    private AppiumDriver driver;
//
//    private WebElement digit2;
//    private WebElement digit3;
//    private WebElement plusButton;
//    private WebElement equalsButton;
//    private WebElement resultField;
//
//    @BeforeAll
//    void setUp() throws MalformedURLException {
//        DesiredCapabilities caps = new DesiredCapabilities();
//        caps.setCapability("platformName", "Android");
//        caps.setCapability("deviceName", "Android Emulator");
//        caps.setCapability("appPackage", "com.android.calculator2");
//        caps.setCapability("appActivity", "com.android.calculator2.Calculator");
//        caps.setCapability("noReset", true);
//
//        driver = new AndroidDriver(new URL("http://localhost:4723/wd/hub"), caps);
//
//        digit2 = driver.findElement(By.id("com.android.calculator2:id/digit_2"));
//        digit3 = driver.findElement(By.id("com.android.calculator2:id/digit_3"));
//        plusButton = driver.findElementByAccessibilityId("plus");
//        equalsButton = driver.findElementByAccessibilityId("equals");
//        resultField = driver.findElementById("com.android.calculator2:id/result");
//    }
//
//    @AfterAll
//    void tearDown() {
//        if (driver != null) {
//            driver.quit();
//        }
//    }
//
//    @Test
//    void testAddition() {
//        digit2.click();
//        plusButton.click();
//        digit3.click();
//        equalsButton.click();
//
//        String result = resultField.getText();
//        assertEquals("5", result);
//    }
//}
