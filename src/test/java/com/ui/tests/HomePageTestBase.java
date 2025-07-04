package com.ui.tests;

import com.base.BaseTest;
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
import java.util.stream.Collectors;

import static com.codeborne.selenide.Condition.visible;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Epic("Главная страница")
@Feature("Отображение и взаимодействие с элементами")
public class HomePageTestBase extends BaseTest {

    private static final Logger logger = LoggerFactory.getLogger(HomePageTestBase.class);

    private final HomePage homePage = new HomePage();
    private final LoginPage loginPage = new LoginPage();

    private static final String STANDARD_USER = "standard_user";
    private static final String STANDARD_PASSWORD = "secret_sauce";

    // Метод для авторизации стандартного пользователя
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
    @Story("Корзина и добавление товара")
    @DisplayName("Добавление товара в корзину и переход к корзине")
    public void addItemToCartTest() {
        Allure.step("Открыть страницу логина", loginPage::openLoginPage);

        loginStandardUser();

        String itemName = "Sauce Labs Backpack";

        Allure.step("Добавить товар в корзину: " + itemName, () -> {
            homePage.addItemToCart(itemName);
            logger.info("Добавлен товар: {}", itemName);
        });

        Allure.step("Открыть корзину", () -> {
            homePage.clickCart();
            logger.info("Переход в корзину выполнен");
        });

        Allure.step("Проверить, что товар отображается в корзине", () -> {
            assertTrue(homePage.isItemInCart(itemName), "Товар не найден в корзине");
            logger.info("Товар '{}' отображается в корзине", itemName);
        });
    }

    @Test
    @Severity(SeverityLevel.NORMAL)
    @Story("Корзина и удаление товара")
    @DisplayName("Удаление товара из корзины")
    public void removeItemFromCartTest() {
        Allure.step("Открыть страницу логина", loginPage::openLoginPage);

        loginStandardUser();

        String itemName = "Sauce Labs Backpack";

        Allure.step("Добавить товар в корзину: " + itemName, () -> {
            homePage.addItemToCart(itemName);
            logger.info("Добавлен товар: {}", itemName);
        });

        Allure.step("Открыть корзину", () -> {
            homePage.clickCart();
            logger.info("Переход в корзину выполнен");
        });

        Allure.step("Проверить, что товар отображается в корзине", () -> {
            assertTrue(homePage.isItemInCart(itemName), "Товар не найден в корзине");
            logger.info("Товар '{}' отображается в корзине", itemName);
        });

        Allure.step("Перейти на вкладку All Items через меню", () -> {
            homePage.clickOpenMenu();
            homePage.clickMenuItem("All Items");
            logger.info("Переход на вкладку All Items выполнен успешно");
        });

        Allure.step("Удалить товар из корзины: " + itemName, () -> {
            homePage.removeItemFromCart(itemName);
            logger.info("Удалён товар: {}", itemName);
        });

        Allure.step("Открыть корзину", () -> {
            homePage.clickCart();
            logger.info("Переход в корзину выполнен");
        });

        Allure.step("Проверить, что товар не отображается в корзине", () -> {
            assertTrue(homePage.isItemNotInCart(itemName), "Товар всё ещё отображается в корзине");
            logger.info("Товар '{}' не отображается в корзине, как и ожидалось", itemName);
        });
    }

    @Test
    @Severity(SeverityLevel.NORMAL)
    @Story("Проверка отображения элементов в меню")
    @DisplayName("Проверка отображения элементов в меню")
    public void checkMenuItemsVisibility() {
        Allure.step("Открыть страницу логина", loginPage::openLoginPage);

        loginStandardUser();

        List<String> expectedMenuItems = List.of("All Items", "About", "Logout", "Reset App State");

        Allure.step("Проверка отображения элементов в меню", () -> {
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

        loginStandardUser();

        Allure.step("Проверить, что кнопка About ведёт на saucelabs.com", () -> {
            homePage.clickOpenMenu();

            String href = homePage.getAboutLinkHref();
            logger.info("Ссылка About ведёт на: {}", href);
            assertTrue(href.contains("saucelabs.com"), "Ссылка About не ведет на saucelabs.com");
        });
    }

    @Test
    @Severity(SeverityLevel.NORMAL)
    @Story("Проверка работы кнопки Logout")
    @DisplayName("Проверка выхода из системы через меню Logout")
    public void shouldLogoutSuccessfully() {
        Allure.step("Открыть страницу логина", loginPage::openLoginPage);

        loginStandardUser();

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

    // --- Сортировки ---

    @Test
    @Severity(SeverityLevel.TRIVIAL)
    @Story("Сортировка товаров")
    @DisplayName("Проверка сортировки по названию (A to Z)")
    public void sortItemsByNameAscTest() {
        Allure.step("Открыть страницу логина", loginPage::openLoginPage);

        loginStandardUser();

        Allure.step("Выбрать сортировку Name (A to Z)", () -> {
            homePage.selectSortOption("Name (A to Z)");
            logger.info("Выбрана сортировка по имени (A to Z)");
        });

        Allure.step("Проверить, что товары отсортированы по алфавиту", () -> {
            List<String> productNames = homePage.getAllProductNames();

            List<String> sortedNames = productNames.stream()
                    .sorted(String::compareToIgnoreCase)
                    .collect(Collectors.toList());

            logger.info("Текущий порядок: {}", productNames);
            logger.info("Ожидаемый порядок: {}", sortedNames);

            assertEquals(sortedNames, productNames, "Товары не отсортированы по имени (A to Z)");
        });
    }

    @Test
    @Severity(SeverityLevel.TRIVIAL)
    @Story("Сортировка товаров")
    @DisplayName("Проверка сортировки по названию (Z to A)")
    public void sortItemsByNameDescTest() {
        Allure.step("Открыть страницу логина", loginPage::openLoginPage);

        loginStandardUser();

        Allure.step("Выбрать сортировку Name (Z to A)", () -> {
            homePage.selectSortOption("Name (Z to A)");
            logger.info("Выбрана сортировка по имени (Z to A)");
        });

        Allure.step("Проверить, что товары отсортированы по алфавиту в обратном порядке", () -> {
            List<String> productNames = homePage.getAllProductNames();

            List<String> sortedNames = productNames.stream()
                    .sorted((a, b) -> b.compareToIgnoreCase(a))
                    .collect(Collectors.toList());

            logger.info("Текущий порядок: {}", productNames);
            logger.info("Ожидаемый порядок: {}", sortedNames);

            assertEquals(sortedNames, productNames, "Товары не отсортированы по имени (Z to A)");
        });
    }

    @Test
    @Severity(SeverityLevel.TRIVIAL)
    @Story("Сортировка товаров")
    @DisplayName("Проверка сортировки по цене (low to high)")
    public void sortItemsByPriceLowToHighTest() {
        Allure.step("Открыть страницу логина", loginPage::openLoginPage);

        loginStandardUser();

        Allure.step("Выбрать сортировку Price (low to high)", () -> {
            homePage.selectSortOption("Price (low to high)");
            logger.info("Выбрана сортировка по цене (low to high)");
        });

        Allure.step("Проверить, что товары отсортированы по возрастанию цены", () -> {
            List<Double> prices = homePage.getAllProductPrices();

            List<Double> sortedPrices = prices.stream()
                    .sorted()
                    .collect(Collectors.toList());

            logger.info("Текущий порядок цен: {}", prices);
            logger.info("Ожидаемый порядок цен: {}", sortedPrices);

            assertEquals(sortedPrices, prices, "Товары не отсортированы по цене (low to high)");
        });
    }

    @Test
    @Severity(SeverityLevel.TRIVIAL)
    @Story("Сортировка товаров")
    @DisplayName("Проверка сортировки по цене (high to low)")
    public void sortItemsByPriceHighToLowTest() {
        Allure.step("Открыть страницу логина", loginPage::openLoginPage);

        loginStandardUser();

        Allure.step("Выбрать сортировку Price (high to low)", () -> {
            homePage.selectSortOption("Price (high to low)");
            logger.info("Выбрана сортировка по цене (high to low)");
        });

        Allure.step("Проверить, что товары отсортированы по убыванию цены", () -> {
            List<Double> prices = homePage.getAllProductPrices();

            List<Double> sortedPrices = prices.stream()
                    .sorted((a, b) -> b.compareTo(a))
                    .collect(Collectors.toList());

            logger.info("Текущий порядок цен: {}", prices);
            logger.info("Ожидаемый порядок цен: {}", sortedPrices);

            assertEquals(sortedPrices, prices, "Товары не отсортированы по цене (high to low)");
        });
    }
}
