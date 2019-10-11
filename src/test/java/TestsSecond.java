import framework.BaseTest;
import framework.Logger;
import framework.TestInfo;
import framework.browser.Browser;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;

@TestInfo(id = 14995574)
public class TestsSecond extends BaseTest {


    @BeforeTest
    public void setUp() {
        Browser.getInstance().manage().window().maximize();
    }

    @AfterTest
    public void end() {
        Browser.getInstance().quit();
    }

    @Override
    public void run() {
        Browser.getStartPage();
        WebElement input = Browser.getInstance().findElement(By.xpath("//input[@name='q']"));
        input.sendKeys("jenkins");
        input.submit();
        String firstLinkText = Browser.getInstance().findElement(By.xpath("//div[@id='rso']//div[@class='rc']//a")).getAttribute("href");
        Logger.getLogger().info(String.format("First link text %s", firstLinkText));
        Assert.assertEquals(firstLinkText, "https://jenkins.io/");

    }

}
