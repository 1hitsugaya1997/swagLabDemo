package com.ui.tests;

import com.base.BaseTest;
import com.codeborne.selenide.SelenideElement;
import com.ui.pages.*;
import com.utils.TestConfig;
import io.qameta.allure.*;
import org.junit.jupiter.api.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.util.List;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$x;
import static org.junit.jupiter.api.Assertions.*;

@Epic("Оформление заказа")
@Feature("Страница Overview")
@DisplayName("CheckoutOverviewPage Tests")
public class CheckoutOverviewPageTest extends BaseTest {

    private static final Logger logger = LoggerFactory.getLogger(CheckoutOverviewPageTest.class);

    private final LoginPage loginPage = new LoginPage();
    private final HomePage homePage = new HomePage();
    private final CardPage cardPage = new CardPage();
    private final CheckoutPage checkoutPage = new CheckoutPage();
    private final CheckoutOverviewPage overviewPage = new CheckoutOverviewPage();
    private final CheckoutCompletePage completePage = new CheckoutCompletePage();

    private static final String STANDARD_USER = "standard_user";

    private void loginStandardUser() {
        Allure.step("Авторизоваться стандартным пользователем", () -> {
            loginPage.enterUsername(TestConfig.getLogin(STANDARD_USER));
            loginPage.enterPassword(TestConfig.getPassword(STANDARD_USER));
            loginPage.clickLogin();
            logger.info("Пользователь '{}' успешно авторизован", STANDARD_USER);
        });
    }

    private void prepareOrderWithItem(String itemName) {
        Allure.step("Добавить товар в корзину: " + itemName, () -> {
            homePage.addItemToCart(itemName);
            logger.info("Товар '{}' добавлен в корзину", itemName);
        });

        Allure.step("Перейти в корзину и к оформлению", () -> {
            homePage.clickCart();
            cardPage.clickCheckout();
            checkoutPage.enterFirstName("Иван")
                    .enterLastName("Иванов")
                    .enterCode("123456")
                    .clickContinue();
            logger.info("Данные для оформления заказа введены");
        });
    }

    @Test
    @Story("Проверка перехода на страницу обзора заказа")
    @Severity(SeverityLevel.CRITICAL)
    @DisplayName("Должны попасть на страницу 'Checkout: Overview'")
    public void shouldOpenCheckoutOverviewPage() {
        Allure.step("Открыть страницу логина", loginPage::openLoginPage);
        logger.info("Страница логина открыта");

        loginStandardUser();

        String itemName = "Sauce Labs Backpack";
        prepareOrderWithItem(itemName);

        Allure.step("Проверить, что находимся на странице Overview", () -> {
            assertTrue(overviewPage.isOnOverviewPage(), "Ожидалась страница обзора заказа, но она не отображается");
            logger.info("Находимся на странице обзора заказа");
        });
    }

    @Test
    @Story("Проверка финализации заказа")
    @Severity(SeverityLevel.BLOCKER)
    @DisplayName("Завершение заказа кнопкой Finish")
    public void shouldFinishOrderSuccessfully() {
        Allure.step("Открыть страницу логина", loginPage::openLoginPage);
        logger.info("Страница логина открыта");

        loginStandardUser();

        String itemName = "Sauce Labs Backpack";
        prepareOrderWithItem(itemName);

        Allure.step("Нажать кнопку Finish", () -> {
            overviewPage.clickFinish();
            logger.info("Нажата кнопка Finish");
        });

        Allure.step("Проверить, что заказ завершён успешно", () -> {
            assertTrue(completePage.isOnCompletePage(), "Заказ не завершён успешно, страница завершения не отображается");
            logger.info("Заказ успешно завершён");
        });
    }

    @Test
    @Story("Проверка возврата на главную")
    @Severity(SeverityLevel.NORMAL)
    @DisplayName("Нажать Cancel и вернуться к продуктам")
    public void shouldCancelAndReturnToCart() {
        Allure.step("Открыть страницу логина", loginPage::openLoginPage);
        logger.info("Страница логина открыта");

        loginStandardUser();

        String itemName = "Sauce Labs Backpack";
        prepareOrderWithItem(itemName);

        Allure.step("Нажать кнопку Cancel", () -> {
            overviewPage.clickCancel();
            logger.info("Нажата кнопка Cancel");
        });

        Allure.step("Проверить, что вернулись на страницу продуктов", () -> {
            assertTrue(homePage.isOnInventoryPage(), "Ожидалось возвращение на страницу продуктов, но этого не произошло");
            logger.info("Успешный возврат на страницу продуктов");
        });
    }

    @Test
    @Severity(SeverityLevel.NORMAL)
    @Story("Проверка отображения элементов в меню")
    @DisplayName("Проверка отображения элементов в меню")
    public void checkMenuItemsVisibility() {
        Allure.step("Открыть страницу логина", loginPage::openLoginPage);
        logger.info("Страница логина открыта");

        loginStandardUser();

        String itemName = "Sauce Labs Backpack";
        prepareOrderWithItem(itemName);

        Allure.step("Проверить отображение элементов в меню", () -> {
            List<String> expectedMenuItems = List.of("All Items", "About", "Logout", "Reset App State");
            homePage.clickOpenMenu();
            expectedMenuItems.forEach(homePage::shouldSeeMenuItem);
            logger.info("Отображаются все ожидаемые элементы меню: {}", expectedMenuItems);
        });
    }

    @Test
    @Severity(SeverityLevel.NORMAL)
    @Story("Проверка работы кнопки About")
    @DisplayName("Переход по кнопке About")
    public void shouldRedirectToSauceLabsPageViaAboutMenu() {
        Allure.step("Открыть страницу логина", loginPage::openLoginPage);
        logger.info("Страница логина открыта");

        loginStandardUser();

        String itemName = "Sauce Labs Backpack";
        prepareOrderWithItem(itemName);

        Allure.step("Проверить, что кнопка About ведёт на saucelabs.com", () -> {
            homePage.clickOpenMenu();

            SelenideElement aboutLink = $x("//a[text()='About']");
            aboutLink.shouldBe(visible, Duration.ofSeconds(5));

            String href = aboutLink.getAttribute("href");
            logger.info("Ссылка About ведёт на: {}", href);
            assertTrue(href.contains("saucelabs.com"), "Ссылка About не ведет на saucelabs.com");
        });
    }

    @Test
    @Severity(SeverityLevel.NORMAL)
    @Story("Проверка выхода из системы через меню Logout")
    @DisplayName("Проверка выхода из системы через меню Logout")
    public void shouldLogoutSuccessfully() {
        Allure.step("Открыть страницу логина", loginPage::openLoginPage);
        logger.info("Страница логина открыта");

        loginStandardUser();

        String itemName = "Sauce Labs Backpack";
        prepareOrderWithItem(itemName);

        Allure.step("Выйти из системы через меню Logout", () -> {
            homePage.clickOpenMenu();
            homePage.clickMenuItem("Logout");
            logger.info("Выполнен выход из системы через меню Logout");
        });

        Allure.step("Проверить, что пользователь на странице логина", () -> {
            assertTrue(loginPage.isOnInventoryPage(), "Пользователь не вернулся на страницу логина после Logout");
            logger.info("Пользователь успешно вышел и увидел страницу логина");
        });
    }
}
