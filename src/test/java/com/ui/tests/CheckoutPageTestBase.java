package com.ui.tests;

import com.base.BaseTest;
import com.codeborne.selenide.SelenideElement;
import com.ui.pages.CardPage;
import com.ui.pages.CheckoutPage;
import com.ui.pages.HomePage;
import com.ui.pages.LoginPage;
import io.qameta.allure.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.util.List;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$x;
import static com.codeborne.selenide.WebDriverRunner.url;
import static com.codeborne.selenide.Selenide.switchTo;
import static com.ui.tests.CardPageTestBase.logger;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Epic("Оформление заказа")
@Feature("Страница Checkout: Your Information")
public class CheckoutPageTestBase extends BaseTest {

    private final LoginPage loginPage = new LoginPage();
    private final HomePage homePage = new HomePage();
    private final CardPage cardPage = new CardPage();
    private final CheckoutPage checkoutPage = new CheckoutPage();

    @Test
    @Severity(SeverityLevel.NORMAL)
    @Story("Переход к форме оформления заказа")
    @DisplayName("Проверка, что отображается форма Checkout")
    public void shouldDisplayCheckoutForm() {
        Allure.step("Авторизация и переход в корзину", () -> {
            loginPage.openLoginPage()
                    .enterUsername("standard_user")
                    .enterPassword("secret_sauce")
                    .clickLogin();
            homePage.clickCart();
            cardPage.clickCheckout();
        });

        Allure.step("Проверить, что форма оформления заказа отображается", () -> {
            assertTrue(checkoutPage.isOnCheckoutPage(), "Форма Checkout: Your Information не отображается");
        });
    }

    @Test
    @Severity(SeverityLevel.CRITICAL)
    @Story("Успешное заполнение формы")
    @DisplayName("Заполнение всех полей формы и переход к следующему шагу")
    public void shouldFillFormCorrectly() {
        loginPage.openLoginPage()
                .enterUsername("standard_user")
                .enterPassword("secret_sauce")
                .clickLogin();
        homePage.clickCart();
        cardPage.clickCheckout();

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
        loginPage.openLoginPage()
                .enterUsername("standard_user")
                .enterPassword("secret_sauce")
                .clickLogin();
        homePage.clickCart();
        cardPage.clickCheckout();

        Allure.step("Нажать кнопку Cancel", checkoutPage::clickCancel);

        Allure.step("Проверить возврат на страницу корзины", () -> {
            assertTrue(url().contains("cart"), "Не произошел возврат на страницу корзины");
        });
    }

    @Test
    @Severity(SeverityLevel.CRITICAL)
    @Story("Проверка отображения элементов в меню")
    @DisplayName("Проверка отображения элементов в меню")
    public void shouldSeeMenuItemsOnCheckoutPage() {
        loginPage.openLoginPage()
                .enterUsername("standard_user")
                .enterPassword("secret_sauce")
                .clickLogin();
        homePage.clickCart();
        cardPage.clickCheckout();

        Allure.step("Открыть меню и проверить наличие всех пунктов", () -> {
            List<String> expectedItems = List.of("All Items", "About", "Logout", "Reset App State");
            cardPage.clickOpenMenu();
            expectedItems.forEach(cardPage::shouldSeeMenuItem);
        });
    }

    @Test
    @Severity(SeverityLevel.NORMAL)
    @Story("Проверка работы кнопки About")
    @DisplayName("Переход по кнопке About")
    public void shouldRedirectToSauceLabsPageViaAboutMenu() {

        loginPage.openLoginPage()
                .enterUsername("standard_user")
                .enterPassword("secret_sauce")
                .clickLogin();
        homePage.clickCart();
        cardPage.clickCheckout();

        Allure.step("Открыть меню и проверить наличие всех пунктов", () -> {
            List<String> expectedItems = List.of("All Items", "About", "Logout", "Reset App State");
            cardPage.clickOpenMenu();
            expectedItems.forEach(cardPage::shouldSeeMenuItem);

            SelenideElement aboutLink = $x("//a[text()='About']");
            aboutLink.shouldBe(visible, Duration.ofSeconds(5));

            String href = aboutLink.getAttribute("href");
            logger.info("Ссылка About ведет на: {}", href);
            assertTrue(href.contains("saucelabs.com"), "Ссылка About не ведет на saucelabs.com");
        });
    }







}
