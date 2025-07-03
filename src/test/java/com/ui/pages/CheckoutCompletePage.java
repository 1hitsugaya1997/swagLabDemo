package com.ui.pages;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.byXpath;
import static com.codeborne.selenide.Selenide.*;

public class CheckoutCompletePage {

    // 🔍 Заголовок страницы завершения заказа
    private static final String COMPLETE_HEADER_XPATH = "//h2[text()='Thank you for your order!']";
    private final SelenideElement completeHeader = $x(COMPLETE_HEADER_XPATH);

    // 🔍 Сообщение об успешном заказе
    private static final String COMPLETE_MESSAGE_XPATH = "//div[@class='complete-text']";
    private final SelenideElement completeMessage = $x(COMPLETE_MESSAGE_XPATH);

    // 🔍 Кнопка возврата на главную
    private static final String BACK_HOME_BUTTON_XPATH = "//button[@id='back-to-products']";
    private final SelenideElement backHomeButton = $x(BACK_HOME_BUTTON_XPATH);

    // ✅ Проверка, что мы на странице завершения заказа
    public boolean isOnCompletePage() {
        return completeHeader.shouldBe(visible).exists();
    }

    // ✅ Получить текст благодарности
    public String getCompleteMessage() {
        return completeMessage.shouldBe(visible).getText();
    }

    // ✅ Нажать кнопку "Back Home"
    public void clickBackHome() {
        backHomeButton.shouldBe(visible).click();
    }
}
