package com.ui.pages;

import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.ElementsCollection;

import java.util.List;
import java.util.stream.Collectors;

import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.Condition.*;

public class CheckoutOverviewPage {

    // 🔍 Элементы страницы
    private final SelenideElement overviewHeader = $x("//span[text()='Checkout: Overview']");
    private final SelenideElement finishButton = $x("//button[@data-test='finish']");
    private final SelenideElement cancelButton = $x("//button[@data-test='cancel']");
    private final ElementsCollection itemNames = $$x("//div[@class='inventory_item_name']");
    private final ElementsCollection itemPrices = $$x("//div[@class='inventory_item_price']");
    private final SelenideElement summaryTotal = $x("//div[@class='summary_total_label']");

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

    // ✅ Получить сумму заказа (например: "Total: $58.29")
    public String getTotalAmountText() {
        return summaryTotal.shouldBe(visible).getText();
    }

    // ✅ Клик по кнопке Finish
    public void clickFinish() {
        finishButton.shouldBe(visible).click();
    }

    // ✅ Клик по кнопке Cancel
    public void clickCancel() {
        cancelButton.shouldBe(visible).click();
    }
}
