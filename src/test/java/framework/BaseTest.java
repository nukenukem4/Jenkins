package framework;

import framework.browser.Browser;
import framework.testRail.ConfigurateTestRailRequest;
import org.testng.ITestResult;
import org.testng.annotations.*;

public abstract class BaseTest {
    public TestInfo testInfo;
    protected ConfigurateTestRailRequest commonTestRail = new ConfigurateTestRailRequest();
    protected static String runId;

    public abstract void run();

    @BeforeTest
    public void setUp() {
        Browser.getInstance().manage().window().maximize();
    }
    @BeforeSuite
    @Parameters({"SuiteId"})
    public void beforeSuite(String suiteId){
        commonTestRail.createTestRun(suiteId);
        this.runId = commonTestRail.getRunId();
    }
    @AfterTest
    public void end() {
        Browser.getInstance().quit();
    }

    @Test
    public void test() {
        this.testInfo = getClass().getAnnotation(TestInfo.class);
        Browser.getStartPage();
        run();
    }
    @AfterMethod
    public void report(ITestResult result){
        commonTestRail.enterResults(testInfo.id(), result.getStatus(), runId);
    }
    protected void log(String message) {
        Logger.getLogger().info(message);
    }

}
