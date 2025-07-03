package com.ui.pages;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;

import java.util.List;
import java.util.stream.Collectors;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.*;

public class CheckoutOverviewPage {

    // 🔍 Заголовок страницы "Checkout: Overview"
    private static final String OVERVIEW_HEADER_XPATH = "//span[text()='Checkout: Overview']";
    private final SelenideElement overviewHeader = $x(OVERVIEW_HEADER_XPATH);

    // 🔍 Кнопка завершения заказа
    private static final String FINISH_BUTTON_XPATH = "//button[@data-test='finish']";
    private final SelenideElement finishButton = $x(FINISH_BUTTON_XPATH);

    // 🔍 Кнопка отмены
    private static final String CANCEL_BUTTON_XPATH = "//button[@data-test='cancel']";
    private final SelenideElement cancelButton = $x(CANCEL_BUTTON_XPATH);

    // 🔍 Названия товаров
    private static final String ITEM_NAME_XPATH = "//div[@class='inventory_item_name']";
    private final ElementsCollection itemNames = $$x(ITEM_NAME_XPATH);

    // 🔍 Цены товаров
    private static final String ITEM_PRICE_XPATH = "//div[@class='inventory_item_price']";
    private final ElementsCollection itemPrices = $$x(ITEM_PRICE_XPATH);

    // 🔍 Общая сумма заказа
    private static final String SUMMARY_TOTAL_XPATH = "//div[@class='summary_total_label']";
    private final SelenideElement summaryTotal = $x(SUMMARY_TOTAL_XPATH);

    // ✅ Проверка, что мы на странице "Checkout: Overview"
    public boolean isOnOverviewPage() {
        return overviewHeader.shouldBe(visible).exists();
    }

    // ✅ Получить список названий товаров
    public List<String> getItemNames() {
        return itemNames.stream()
                .map(SelenideElement::getText)
                .collect(Collectors.toList());
    }

    // ✅ Получить список цен товаров
    public List<String> getItemPrices() {
        return itemPrices.stream()
                .map(SelenideElement::getText)
                .collect(Collectors.toList());
    }

    // ✅ Получить текст итоговой суммы заказа
    public String getTotalAmountText() {
        return summaryTotal.shouldBe(visible).getText();
    }

    // ✅ Нажать кнопку "Finish"
    public void clickFinish() {
        finishButton.shouldBe(visible).click();
    }

    // ✅ Нажать кнопку "Cancel"
    public void clickCancel() {
        cancelButton.shouldBe(visible).click();
    }
}
