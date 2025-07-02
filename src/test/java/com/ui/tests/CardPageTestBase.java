//package com.ui.tests;
//
//import com.base.BaseTest;
//import com.ui.pages.CardPage;
//import io.qameta.allure.*;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.params.ParameterizedTest;
//import org.junit.jupiter.params.provider.CsvSource;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//
//@Epic("Карточка товара")
//@Feature("Проверка информации на странице карточки товара")
//public class CardPageTestBase extends BaseTest {
//
//    private static final Logger logger = LoggerFactory.getLogger(CardPageTestBase.class);
//
//    private final CardPage cardPage = new CardPage();
//
//    @ParameterizedTest(name = "{index} => productId={0}, expectedTitle={1}, expectedPrice={2}")
//    @CsvSource({
//            "1, Sauce Labs Backpack, $29.99",
//            "2, Sauce Labs Bike Light, $9.99",
//            "3, Sauce Labs Bolt T-Shirt, $15.99"
//    })
//    @Severity(SeverityLevel.CRITICAL)
//    @Story("Проверка заголовка и цены товара по ID")
//    @DisplayName("Проверка заголовка и цены товара")
//    public void productInfoTest(int productId, String expectedTitle, String expectedPrice) {
//        Allure.step("Открыть страницу товара с ID " + productId, () -> cardPage.openProductPage(productId));
//
//        logger.info("Открыта страница товара с ID {}", productId);
//
//        Allure.step("Проверить заголовок товара", () -> {
//            String actualTitle = cardPage.getProductTitle();
//            logger.info("Ожидаемый заголовок: '{}', полученный: '{}'", expectedTitle, actualTitle);
//            assertEquals(expectedTitle, actualTitle, "Заголовок товара не совпадает");
//        });
//
//        Allure.step("Проверить цену товара", () -> {
//            String actualPrice = cardPage.getProductPrice();
//            logger.info("Ожидаемая цена: '{}', полученная: '{}'", expectedPrice, actualPrice);
//            assertEquals(expectedPrice, actualPrice, "Цена товара не совпадает");
//        });
//    }
//}
