package com.ui.pages;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.byXpath;
import static com.codeborne.selenide.Selenide.*;

public class CheckoutCompletePage {

    // 🔍 Элементы
    private final SelenideElement completeHeader = $(byXpath("//h2[text()='Thank you for your order!']"));
    private final SelenideElement completeMessage = $(byXpath("//div[@class='complete-text']"));
    private final SelenideElement backHomeButton = $(byXpath("//button[@id='back-to-products']"));

    // ✅ Проверка, что мы на странице завершения оформления
    public boolean isOnCompletePage() {
        return completeHeader.shouldBe(visible).exists();
    }

    // ✅ Получить текст сообщения "Thank you"
    public String getCompleteMessage() {
        return completeMessage.shouldBe(visible).getText();
    }

    // ✅ Клик по кнопке "Back Home"
    public void clickBackHome() {
        backHomeButton.shouldBe(visible).click();
    }
}
