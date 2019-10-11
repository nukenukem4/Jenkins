package framework.browser;

import com.google.common.base.Strings;
import framework.Logger;
import io.github.bonigarcia.wdm.WebDriverManager;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class Browser {
    private static WebDriver driver;
    private static PropertiesResourceManager manager = new PropertiesResourceManager();
    private static String browserName = initBrowserName();
    private static String startPage = manager.getSystemProperty("startPage");
    private static String timeoutTime = manager.getSystemProperty("timeout");

    private Browser() {
    }

    public static WebDriver getInstance() {
        if (driver == null) {
            driver = initDriver();
            driver.manage().window().maximize();
            driver.manage().timeouts().pageLoadTimeout(5, TimeUnit.SECONDS);
            driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        }
        return driver;
    }

    public static void getStartPage() {
        driver.get(startPage);
    }

    private static WebDriver initDriver() {
        switch (browserName.toUpperCase()) {
            case "CHROME":
                return initChrome();
            case "FIREFOX":
                return initFF();
            default:
                Logger.getLogger().info(String.format("This [%s] browser not implemented. Available browsers are: FireFox, Chrome", browserName));
                return null;
        }

    }

    private static String initBrowserName() {
        return  System.getProperty("browser");
    }


    private static WebDriver initChrome() {
        WebDriverManager.chromedriver().setup();
        return new ChromeDriver(getChromeOptions());
    }

    private static WebDriver initFF() {
        WebDriverManager.firefoxdriver().setup();
        return new FirefoxDriver(getFFOptions());
    }


    private static ChromeOptions getChromeOptions() {
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.addArguments("--no-sandbox");
        chromeOptions.addArguments("--whitelisted-ips");
        chromeOptions.addArguments("--disable-extension");
        chromeOptions.addArguments("window-size=1920,1080");
        Map<String, Object> chromePrefs = new HashMap<>();
        chromePrefs.put("profile.default_content_settings.popups", 0);
        chromePrefs.put("safebrowsing.enabled", "true");
        chromeOptions.addArguments();
        chromeOptions.setExperimentalOption("prefs", chromePrefs);
        return chromeOptions;
    }

    private static FirefoxOptions getFFOptions() {
        FirefoxOptions firefoxOptions = new FirefoxOptions();
        System.setProperty(FirefoxDriver.SystemProperty.BROWSER_LOGFILE, "/dev/null");
        firefoxOptions.addArguments("--headless");
        return firefoxOptions;
    }

    static int getTimeoutTime() {
        return Integer.parseInt(timeoutTime);
    }
}
