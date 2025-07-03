package com.ui.tests;

import com.base.BaseTest;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.WebDriverRunner;
import com.ui.pages.HomePage;
import com.ui.pages.LoginPage;
import io.qameta.allure.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$$x;
import static com.codeborne.selenide.Selenide.$x;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Epic("Главная страница")
@Feature("Отображение и взаимодействие с элементами")
public class HomePageTestBase extends BaseTest {

    private static final Logger logger = LoggerFactory.getLogger(HomePageTestBase.class);

    private final HomePage homePage = new HomePage();
    private final LoginPage loginPage = new LoginPage();


    @Test
    @Severity(SeverityLevel.NORMAL)
    @Story("Корзина и добавление товара")
    @DisplayName("Добавление товара в корзину и переход к корзине")
    public void addItemToCartTest() {
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

        Allure.step("Открыть корзину", () -> {
            homePage.clickCart();
            logger.info("Переход в корзину выполнен");
        });

        Allure.step("Проверить, что товар отображается в корзине", () -> {
            boolean itemInCart = $x("//div[@class='inventory_item_name' and text()='" + itemName + "']").isDisplayed();
            assertTrue(itemInCart, "Товар не найден в корзине");
            logger.info("Товар '{}' отображается в корзине", itemName);
        });
    }

    @Test
    @Severity(SeverityLevel.NORMAL)
    @Story("Корзина и добавление товара")
    @DisplayName("Добавление товара в корзину и переход к корзине")
    public void removeItemToCartTest() {
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

        Allure.step("Открыть корзину", () -> {
            homePage.clickCart();
            logger.info("Переход в корзину выполнен");
        });

        Allure.step("Проверить, что товар отображается в корзине", () -> {
            boolean itemInCart = $x("//div[@class='inventory_item_name' and text()='" + itemName + "']").isDisplayed();
            assertTrue(itemInCart, "Товар не найден в корзине");
            logger.info("Товар '{}' отображается в корзине", itemName);
        });

        Allure.step("Перейти на вкладку All Items через меню", () -> {
            String menu_item = "All Items";
            homePage.clickOpenMenu();
            homePage.clickMenuItem(menu_item);
            logger.info("Переход на вкладку All Items выполнен успешно");
        });

        Allure.step("Убрать товар из корзины: " + itemName, () -> {
            homePage.addItemToCart(itemName);
            logger.info("Убран товар: {}", itemName);
        });

        Allure.step("Открыть корзину", () -> {
            homePage.clickCart();
            logger.info("Переход в корзину выполнен");
        });

        Allure.step("Проверить, что товар не отображается в корзине", () -> {
            $x("//div[@class='inventory_item_name' and text()='" + itemName + "']").shouldNotBe(visible);
            logger.info("Товар '{}' не отображается в корзине, как и ожидалось", itemName);
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

        Allure.step("Открыть страницу логина", loginPage::openLoginPage);
        logger.info("Открыта страница логина");

        Allure.step("Авторизоваться", () -> {
            loginPage.enterUsername("standard_user")
                    .enterPassword("secret_sauce")
                    .clickLogin();
            logger.info("Вход выполнен");
        });

        Allure.step("Переход по кнопке About и проверка URL", () -> {
            homePage.clickOpenMenu();

            // Временно отключим ожидание загрузки полной страницы (важно!)
            Selenide.executeJavaScript("window.stop();");

            // Кликаем по элементу без ожидания рендеринга
            SelenideElement aboutLink = $x("//a[text()='About']");
            aboutLink.shouldBe(visible, Duration.ofSeconds(10)).click();

            // Ждём открытия новой вкладки
            var windows = WebDriverRunner.getWebDriver().getWindowHandles();
            if (windows.size() > 1) {
                Selenide.switchTo().window(1);
            }

            String currentUrl = WebDriverRunner.url();
            logger.info("Текущий URL: {}", currentUrl);
            assertTrue(currentUrl.contains("saucelabs.com"), "Не произошел переход на saucelabs.com");
        });
    }

    @Test
    @Severity(SeverityLevel.NORMAL)
    @Story("Проверка работы кнопки Logout")
    @DisplayName("Проверка выхода из системы через меню Logout")
    public void shouldLogoutSuccessfully() {

        Allure.step("Открыть страницу логина", loginPage::openLoginPage);
        logger.info("Открыта страница логина");

        Allure.step("Авторизоваться", () -> {
            loginPage.enterUsername("standard_user")
                    .enterPassword("secret_sauce")
                    .clickLogin();
            logger.info("Вход выполнен");
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



    @Test
    @Severity(SeverityLevel.TRIVIAL)
    @Story("Сортировка товаров")
    @DisplayName("Проверка сортировки по названию (A to Z)")
    public void sortItemsByNameTest() {
        Allure.step("Открыть страницу логина", loginPage::openLoginPage);
        logger.info("Открыта страница логина");

        Allure.step("Авторизоваться", () -> {
            loginPage.enterUsername("standard_user")
                     .enterPassword("secret_sauce")
                     .clickLogin();
            logger.info("Вход выполнен");
        });

        Allure.step("Выбрать сортировку A to Z", () -> {
            homePage.selectSortOption("Name (A to Z)");
            logger.info("Выбрана сортировка по имени (A to Z)");
        });

        Allure.step("Проверить, что товары отсортированы по алфавиту и первый товар — Sauce Labs Backpack", () -> {
            // Получаем список названий товаров
            List<String> productNames = Collections.singletonList(homePage.getFirstProductName());

            // Проверяем первый товар
            String actualFirstProduct = productNames.get(0);
            logger.info("Первый товар: {}", actualFirstProduct);
            assertEquals("Sauce Labs Backpack", actualFirstProduct, "Первый товар отображается неверно");

            // Сравниваем отсортированный список
            List<String> sortedNames = new ArrayList<>(productNames);
            sortedNames.sort(String::compareToIgnoreCase);

            logger.info("Названия товаров на странице: {}", productNames);
            logger.info("Ожидаемый порядок: {}", sortedNames);

            assertEquals(sortedNames, productNames, "Товары не отсортированы по алфавиту (A–Z)");
        });
    }

    @Test
    @Severity(SeverityLevel.TRIVIAL)
    @Story("Сортировка товаров")
    @DisplayName("Проверка сортировки по названию (Z to A)")
    public void sortItemsByNameTest2() {
        Allure.step("Открыть страницу логина", loginPage::openLoginPage);
        logger.info("Открыта страница логина");

        Allure.step("Авторизоваться", () -> {
            loginPage.enterUsername("standard_user")
                    .enterPassword("secret_sauce")
                    .clickLogin();
            logger.info("Вход выполнен");
        });

        Allure.step("Выбрать сортировку Z to A", () -> {
            homePage.selectSortOption("Name (Z to A)");
            logger.info("Выбрана сортировка по имени (Z to A)");
        });

        Allure.step("Проверить, что товары отсортированы по алфавиту и первый товар — Test.allTheThings() T-Shirt (Red)", () -> {
            // Получаем список названий товаров
            List<String> productNames = Collections.singletonList(homePage.getFirstProductName());

            // Проверяем первый товар
            String actualFirstProduct = productNames.get(0);
            logger.info("Первый товар: {}", actualFirstProduct);
            assertEquals("Test.allTheThings() T-Shirt (Red)", actualFirstProduct, "Первый товар отображается неверно");

            // Сравниваем отсортированный список
            List<String> sortedNames = new ArrayList<>(productNames);
            sortedNames.sort(String::compareToIgnoreCase);

            logger.info("Названия товаров на странице: {}", productNames);
            logger.info("Ожидаемый порядок: {}", sortedNames);

            assertEquals(sortedNames, productNames, "Товары не отсортированы по алфавиту (Z–A)");
        });


    }

    @Test
    @Severity(SeverityLevel.TRIVIAL)
    @Story("Сортировка товаров")
    @DisplayName("Проверка сортировки по (Price (low to high))")
    public void shouldSortItemsByPriceLowToHigh() {
        Allure.step("Открыть страницу логина", loginPage::openLoginPage);
        logger.info("Открыта страница логина");

        Allure.step("Авторизоваться", () -> {
            loginPage.enterUsername("standard_user")
                    .enterPassword("secret_sauce")
                    .clickLogin();
            logger.info("Вход выполнен");
        });

        Allure.step("Выбрать сортировку Price (low to high)", () -> {
            homePage.selectSortOption("Price (low to high)");
            logger.info("Выбрана сортировка Price (low to high)");
        });

        Allure.step("Проверить, что товары отсортированы по алфавиту и первый товар — Sauce Labs Onesie", () -> {
            // Получаем список названий товаров
            List<String> productNames = Collections.singletonList(homePage.getFirstProductName());

            // Проверяем первый товар
            String actualFirstProduct = productNames.get(0);
            logger.info("Первый товар: {}", actualFirstProduct);
            assertEquals("Sauce Labs Onesie", actualFirstProduct, "Первый товар отображается неверно");

            // Сравниваем отсортированный список
            List<String> sortedNames = new ArrayList<>(productNames);
            sortedNames.sort(String::compareToIgnoreCase);

            logger.info("Названия товаров на странице: {}", productNames);
            logger.info("Ожидаемый порядок: {}", sortedNames);

            assertEquals(sortedNames, productNames, "Товары не отсортированы по Price (low to high)");
        });

    }

    @Test
    @Severity(SeverityLevel.TRIVIAL)
    @Story("Сортировка товаров")
    @DisplayName("Проверка сортировки по Price (high to low)")
    public void shouldSortItemsByPriceHighToLow() {
        Allure.step("Открыть страницу логина", loginPage::openLoginPage);
        logger.info("Открыта страница логина");

        Allure.step("Авторизоваться", () -> {
            loginPage.enterUsername("standard_user")
                    .enterPassword("secret_sauce")
                    .clickLogin();
            logger.info("Вход выполнен");
        });

        Allure.step("Выбрать сортировку Price (high to low)", () -> {
            homePage.selectSortOption("Price (high to low)");
            logger.info("Выбрана сортировка Price (high to low)");
        });

        Allure.step("Проверить, что товары отсортированы по алфавиту и первый товар — Sauce Labs Fleece Jacket", () -> {
            // Получаем список названий товаров
            List<String> productNames = Collections.singletonList(homePage.getFirstProductName());

            // Проверяем первый товар
            String actualFirstProduct = productNames.get(0);
            logger.info("Первый товар: {}", actualFirstProduct);
            assertEquals("Sauce Labs Fleece Jacket", actualFirstProduct, "Первый товар отображается неверно");

            // Сравниваем отсортированный список
            List<String> sortedNames = new ArrayList<>(productNames);
            sortedNames.sort(String::compareToIgnoreCase);

            logger.info("Названия товаров на странице: {}", productNames);
            logger.info("Ожидаемый порядок: {}", sortedNames);

            assertEquals(sortedNames, productNames, "Товары не отсортированы по Price (high to low)");
        });

    }


}
