package com.ui.tests;

import com.base.BaseTest;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import com.ui.pages.*;
import io.qameta.allure.*;
import org.junit.jupiter.api.*;

import java.time.Duration;
import java.util.List;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$x;
import static com.ui.tests.CardPageTestBase.logger;
import static org.junit.jupiter.api.Assertions.*;

@Epic("Оформление заказа")
@Feature("Страница Overview")
@DisplayName("CheckoutOverviewPage Tests")
public class CheckoutOverviewPageTest extends BaseTest {

    private final LoginPage loginPage = new LoginPage();
    private final HomePage homePage = new HomePage();
    private final CardPage cardPage = new CardPage();
    private final CheckoutPage checkoutPage = new CheckoutPage();
    private final CheckoutOverviewPage overviewPage = new CheckoutOverviewPage();
    private final CheckoutCompletePage completePage = new CheckoutCompletePage();

    @Test
    @Story("Проверка перехода на страницу обзора заказа")
    @Severity(SeverityLevel.CRITICAL)
    @DisplayName("Должны попасть на страницу 'Checkout: Overview'")
    public void shouldOpenCheckoutOverviewPage() {
        Allure.step("Открыть главную страницу", loginPage::openLoginPage);
        logger.info("Открыта страница логина");
        Allure.step("Авторизоваться стандартным пользователем", () -> {
            loginPage.enterUsername("standard_user")
                    .enterPassword("secret_sauce")
                    .clickLogin();
            logger.info("Авторизация прошла успешно");
        });

        String itemName = "Sauce Labs Backpack";

        Allure.step("Добавить товар в корзину: " + itemName, () -> {
            homePage.addItemToCart(itemName);
            logger.info("Добавлен товар: {}", itemName);
        });

        Allure.step("Перейти в корзину и к оформлению", () -> {
            homePage.clickCart();
            cardPage.clickCheckout();
            checkoutPage.enterFirstName("Иван")
                    .enterLastName("Иванов")
                    .enterCode("123456")
                    .clickContinue();
        });
        Selenide.sleep(2000); // пауза 2 секунды
        Allure.step("Проверить, что находимся на странице Overview", () -> {
            assertTrue(overviewPage.isOnOverviewPage(), "Не на странице обзора заказа");
        });
    }

    @Test
    @Story("Проверка финализации заказа")
    @Severity(SeverityLevel.BLOCKER)
    @DisplayName("Завершение заказа кнопкой Finish")
    public void shouldFinishOrderSuccessfully() {
        Allure.step("Открыть главную страницу", loginPage::openLoginPage);
        logger.info("Открыта страница логина");
        Allure.step("Авторизоваться стандартным пользователем", () -> {
            loginPage.enterUsername("standard_user")
                    .enterPassword("secret_sauce")
                    .clickLogin();
            logger.info("Авторизация прошла успешно");
        });

        String itemName = "Sauce Labs Backpack";

        Allure.step("Добавить товар в корзину: " + itemName, () -> {
            homePage.addItemToCart(itemName);
            logger.info("Добавлен товар: {}", itemName);
        });

        Allure.step("Перейти в корзину и к оформлению", () -> {
            homePage.clickCart();
            cardPage.clickCheckout();
            checkoutPage.enterFirstName("Иван")
                    .enterLastName("Иванов")
                    .enterCode("123456")
                    .clickContinue();
        });
        Selenide.sleep(2000); // пауза 2 секунды
        Allure.step("Нажать кнопку Finish", overviewPage::clickFinish);
        Selenide.sleep(2000); // пауза 2 секунды
        Allure.step("Проверить, что заказ завершён", () -> {
            assertTrue(completePage.isOnCompletePage(), "Заказ не завершён успешно");
        });
    }

    @Test
    @Story("Проверка возврата на главную")
    @Severity(SeverityLevel.NORMAL)
    @DisplayName("Нажать Cancel и вернуться к продуктам")
    public void shouldCancelAndReturnToCart() {
        Allure.step("Открыть главную страницу", loginPage::openLoginPage);
        logger.info("Открыта страница логина");
        Allure.step("Авторизоваться стандартным пользователем", () -> {
            loginPage.enterUsername("standard_user")
                    .enterPassword("secret_sauce")
                    .clickLogin();
            logger.info("Авторизация прошла успешно");
        });

        String itemName = "Sauce Labs Backpack";

        Allure.step("Добавить товар в корзину: " + itemName, () -> {
            homePage.addItemToCart(itemName);
            logger.info("Добавлен товар: {}", itemName);
        });

        Allure.step("Перейти в корзину и к оформлению", () -> {
            homePage.clickCart();
            cardPage.clickCheckout();
            checkoutPage.enterFirstName("Иван")
                    .enterLastName("Иванов")
                    .enterCode("123456")
                    .clickContinue();
        });

        Allure.step("Нажать Cancel", overviewPage::clickCancel);

        Allure.step("Проверить, что вернулись в корзину", () -> {
            assertTrue(homePage.isOnInventoryPage(), "Не вернулись на страницу корзины");
        });
    }

    @Test
    @Severity(SeverityLevel.NORMAL)
    @Story("Проверка отображения элементов в меню")
    @DisplayName("Проверка отображения элементов в меню")
    public void checkMenuItemsVisibility() {
        Allure.step("Открыть главную страницу", loginPage::openLoginPage);
        logger.info("Открыта страница логина");
        Allure.step("Авторизоваться стандартным пользователем", () -> {
            loginPage.enterUsername("standard_user")
                    .enterPassword("secret_sauce")
                    .clickLogin();
            logger.info("Авторизация прошла успешно");
        });

        String itemName = "Sauce Labs Backpack";

        Allure.step("Добавить товар в корзину: " + itemName, () -> {
            homePage.addItemToCart(itemName);
            logger.info("Добавлен товар: {}", itemName);
        });

        Allure.step("Перейти в корзину и к оформлению", () -> {
            homePage.clickCart();
            cardPage.clickCheckout();
            checkoutPage.enterFirstName("Иван")
                    .enterLastName("Иванов")
                    .enterCode("123456")
                    .clickContinue();
        });

        Allure.step("Проверка отображения элементов в OpenMenu", () -> {
            List<String> menuItems = List.of("All Items", "About", "Logout", "Reset App State");

            homePage.clickOpenMenu();
            menuItems.forEach(homePage::shouldSeeMenuItem);

            logger.info("Элементы отображаются: {}", menuItems);
        });

    }

    @Test
    @Severity(SeverityLevel.NORMAL)
    @Story("Проверка работы кнопки About")
    @DisplayName("Переход по кнопке About")
    public void shouldRedirectToSauceLabsPageViaAboutMenu() {
        Allure.step("Открыть главную страницу", loginPage::openLoginPage);
        logger.info("Открыта страница логина");
        Allure.step("Авторизоваться стандартным пользователем", () -> {
            loginPage.enterUsername("standard_user")
                    .enterPassword("secret_sauce")
                    .clickLogin();
            logger.info("Авторизация прошла успешно");
        });

        String itemName = "Sauce Labs Backpack";

        Allure.step("Добавить товар в корзину: " + itemName, () -> {
            homePage.addItemToCart(itemName);
            logger.info("Добавлен товар: {}", itemName);
        });

        Allure.step("Перейти в корзину и к оформлению", () -> {
            homePage.clickCart();
            cardPage.clickCheckout();
            checkoutPage.enterFirstName("Иван")
                    .enterLastName("Иванов")
                    .enterCode("123456")
                    .clickContinue();
        });

        Allure.step("Проверить, что кнопка About ведёт на saucelabs.com", () -> {
            homePage.clickOpenMenu();

            SelenideElement aboutLink = $x("//a[text()='About']");
            aboutLink.shouldBe(visible, Duration.ofSeconds(5));

            String href = aboutLink.getAttribute("href");
            logger.info("Ссылка About ведет на: {}", href);
            assertTrue(href.contains("saucelabs.com"), "Ссылка About не ведет на saucelabs.com");
        });
    }

    @Test
    @Severity(SeverityLevel.NORMAL)
    @Story("Проверка работы кнопки Logout")
    @DisplayName("Проверка выхода из системы через меню Logout")
    public void shouldLogoutSuccessfully() {
        Allure.step("Открыть главную страницу", loginPage::openLoginPage);
        logger.info("Открыта страница логина");
        Allure.step("Авторизоваться стандартным пользователем", () -> {
            loginPage.enterUsername("standard_user")
                    .enterPassword("secret_sauce")
                    .clickLogin();
            logger.info("Авторизация прошла успешно");
        });

        String itemName = "Sauce Labs Backpack";

        Allure.step("Добавить товар в корзину: " + itemName, () -> {
            homePage.addItemToCart(itemName);
            logger.info("Добавлен товар: {}", itemName);
        });

        Allure.step("Перейти в корзину и к оформлению", () -> {
            homePage.clickCart();
            cardPage.clickCheckout();
            checkoutPage.enterFirstName("Иван")
                    .enterLastName("Иванов")
                    .enterCode("123456")
                    .clickContinue();
        });

        Allure.step("Выйти из системы через меню Logout", () -> {
            homePage.clickOpenMenu();
            homePage.clickMenuItem("Logout");
        });

        Allure.step("Проверить, что пользователь на странице логина", () -> {
            assertTrue(loginPage.isOnInventoryPage(), "Пользователь не вернулся на страницу логина после Logout");
            logger.info("Пользователь успешно вышел и увидел страницу логина");
        });
    }


}
