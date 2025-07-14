package mobile.driver;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.remote.AndroidMobileCapabilityType;
import io.appium.java_client.remote.MobileCapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.net.MalformedURLException;
import java.net.URL;

public class AppiumDriverFactory {

    private static final String APPIUM_SERVER_URL = "http://127.0.0.1:4723";

    public static AndroidDriver createAndroidDriver() {
        DesiredCapabilities capabilities = new DesiredCapabilities();

        // 🧭 Основные настройки
        capabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, "Android");
        capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, "Android Emulator");
        capabilities.setCapability(MobileCapabilityType.PLATFORM_VERSION, "13.0"); // или твоя версия
        capabilities.setCapability(MobileCapabilityType.AUTOMATION_NAME, "UiAutomator2");

        // 📱 Настройки приложения — Calculator
        capabilities.setCapability(AndroidMobileCapabilityType.APP_PACKAGE, "com.google.android.calculator");
        capabilities.setCapability(AndroidMobileCapabilityType.APP_ACTIVITY, "com.android.calculator2.Calculator");

        // ⚙️ Поведение запуска
        capabilities.setCapability(MobileCapabilityType.NO_RESET, true);
        capabilities.setCapability(MobileCapabilityType.NEW_COMMAND_TIMEOUT, 60);

        try {
            return new AndroidDriver(new URL(APPIUM_SERVER_URL), capabilities);
        } catch (MalformedURLException e) {
            throw new RuntimeException("❌ Ошибка в URL Appium сервера: " + APPIUM_SERVER_URL, e);
        }
    }
}
