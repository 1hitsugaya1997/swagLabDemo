package com.utils;

import com.codeborne.selenide.WebDriverRunner;
import org.openqa.selenium.Alert;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class AlertUtils {

    public static Alert waitForAlert(Duration timeout) {
        WebDriverWait wait = new WebDriverWait(WebDriverRunner.getWebDriver(), timeout);
        return wait.until(ExpectedConditions.alertIsPresent());
    }

    public static String acceptAlert(Duration timeout) {
        Alert alert = waitForAlert(timeout);
        String text = alert.getText();
        alert.accept();
        return text;
    }

    public static String dismissAlert(Duration timeout) {
        Alert alert = waitForAlert(timeout);
        String text = alert.getText();
        alert.dismiss();
        return text;
    }

    public static void sendTextToPrompt(String input, Duration timeout) {
        Alert alert = waitForAlert(timeout);
        alert.sendKeys(input);
        alert.accept();
    }
}
