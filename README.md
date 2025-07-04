# swagLabDemo

## 📌 Описание

Проект автоматизации тестирования веб-приложения Swag Labs (https://www.saucedemo.com) с использованием Java, Selenium WebDriver и Allure для отчётов. Тесты охватывают основные сценарии пользовательского взаимодействия, включая авторизацию, добавление товаров в корзину, оформление заказа и возврат на главную страницу.

## 🧪 Структура проекта

- **src/test/java/ui/pages** — исходный код страниц (Page Object Model)
- **src/test/java/ui/tests** — тесты
- **src/test/resources** — конфигурационные файлы и тестовые данные
- **pom.xml** — Maven-конфигурация
- **allure-results/** — результаты тестов Allure

## ⚙️ Используемые технологии

- **Java** — основной язык программирования
- **Selenium WebDriver** — автоматизация браузера
- **JUnit 5** — фреймворк для тестирования
- **Allure** — отчёты о тестировании
- **Maven** — управление зависимостями и сборкой
- **Page Object Model** — паттерн проектирования для улучшения читаемости и поддержки кода

## 🚀 Установка и запуск

1. Клонируйте репозиторий:

   ```bash
   git clone https://github.com/1hitsugaya1997/swagLabDemo.git
   cd swagLabDemo

2. Установите зависимости:
    ```bash
   mvn clean install

3. Запустите тесты:
    ```bash
   mvn test
4. Для просмотра отчётов Allure:
    ```bash
   allure serve allure-results
