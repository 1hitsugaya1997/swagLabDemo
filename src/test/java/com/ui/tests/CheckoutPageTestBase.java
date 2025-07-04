package com.ui.tests;

import com.base.BaseTest;
import com.codeborne.selenide.SelenideElement;
import com.ui.pages.CardPage;
import com.ui.pages.CheckoutPage;
import com.ui.pages.HomePage;
import com.ui.pages.LoginPage;
import com.utils.TestConfig;
import io.qameta.allure.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.util.List;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$x;
import static com.codeborne.selenide.WebDriverRunner.url;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Epic("Оформление заказа")
@Feature("Страница Checkout: Your Information")
public class CheckoutPageTestBase extends BaseTest {

    private static final Logger logger = LoggerFactory.getLogger(CheckoutPageTestBase.class);

    private static final String STANDARD_USER = "standard_user";
    private static final String STANDARD_PASSWORD = "secret_sauce";

    private final LoginPage loginPage = new LoginPage();
    private final HomePage homePage = new HomePage();
    private final CardPage cardPage = new CardPage();
    private final CheckoutPage checkoutPage = new CheckoutPage();

    // ✅ Авторизация стандартного пользователя
    private void loginStandardUser() {
        Allure.step("Авторизоваться стандартным пользователем", () -> {
            loginPage.enterUsername(TestConfig.getLogin(STANDARD_USER));
            loginPage.enterPassword(TestConfig.getPassword(STANDARD_USER));
            loginPage.clickLogin();
            logger.info("Авторизация стандартного пользователя прошла успешно");
        });
    }

    @Test
    @Severity(SeverityLevel.NORMAL)
    @Story("Переход к форме оформления заказа")
    @DisplayName("Проверка, что отображается форма Checkout")
    public void shouldDisplayCheckoutForm() {
        loginPage.openLoginPage();
        loginStandardUser();

        Allure.step("Переход в корзину и на форму Checkout", () -> {
            homePage.clickCart();
            cardPage.clickCheckout();
        });

        Allure.step("Проверить, что форма Checkout отображается", () -> {
            assertTrue(checkoutPage.isOnCheckoutPage(), "Форма Checkout: Your Information не отображается");
        });
    }

    @Test
    @Severity(SeverityLevel.CRITICAL)
    @Story("Успешное заполнение формы")
    @DisplayName("Заполнение всех полей формы и переход к следующему шагу")
    public void shouldFillFormCorrectly() {
        loginPage.openLoginPage();
        loginStandardUser();

        Allure.step("Переход в корзину и на форму Checkout", () -> {
            homePage.clickCart();
            cardPage.clickCheckout();
        });

        Allure.step("Заполнить форму и нажать Continue", () -> {
            checkoutPage.enterFirstName("Иван")
                    .enterLastName("Иванов")
                    .enterCode("123456")
                    .clickContinue();
        });

        Allure.step("Проверить переход на страницу Overview", () -> {
            assertTrue(url().contains("checkout-step-two"), "Не произошел переход на следующий шаг оформления");
        });
    }

    @Test
    @Severity(SeverityLevel.NORMAL)
    @Story("Отмена оформления")
    @DisplayName("Кнопка Cancel возвращает на корзину")
    public void shouldReturnToCartOnCancel() {
        loginPage.openLoginPage();
        loginStandardUser();

        Allure.step("Переход в корзину и на форму Checkout", () -> {
            homePage.clickCart();
            cardPage.clickCheckout();
        });

        Allure.step("Нажать Cancel и проверить возврат в корзину", () -> {
            checkoutPage.clickCancel();
            assertTrue(url().contains("cart"), "Не произошел возврат на страницу корзины");
        });
    }

    @Test
    @Severity(SeverityLevel.CRITICAL)
    @Story("Проверка отображения элементов в меню")
    @DisplayName("Проверка отображения элементов в меню")
    public void shouldSeeMenuItemsOnCheckoutPage() {
        loginPage.openLoginPage();
        loginStandardUser();

        Allure.step("Переход в корзину и на форму Checkout", () -> {
            homePage.clickCart();
            cardPage.clickCheckout();
        });

        Allure.step("Открыть меню и проверить наличие пунктов", () -> {
            List<String> expectedItems = List.of("All Items", "About", "Logout", "Reset App State");
            cardPage.clickOpenMenu();
            expectedItems.forEach(cardPage::shouldSeeMenuItem);
            logger.info("Пункты меню отображаются корректно: {}", expectedItems);
        });
    }

    @Test
    @Severity(SeverityLevel.NORMAL)
    @Story("Проверка работы кнопки About")
    @DisplayName("Переход по кнопке About")
    public void shouldRedirectToSauceLabsPageViaAboutMenu() {
        loginPage.openLoginPage();
        loginStandardUser();

        Allure.step("Переход в корзину и на форму Checkout", () -> {
            homePage.clickCart();
            cardPage.clickCheckout();
        });

        Allure.step("Проверить, что кнопка About ведет на saucelabs.com", () -> {
            cardPage.clickOpenMenu();

            SelenideElement aboutLink = $x("//a[text()='About']");
            aboutLink.shouldBe(visible, Duration.ofSeconds(5));

            String href = aboutLink.getAttribute("href");
            logger.info("Ссылка About ведет на: {}", href);
            assertTrue(href.contains("saucelabs.com"), "Ссылка About не ведет на saucelabs.com");
        });
    }
}
