package framework.testRail;

public class Reporter {

    private static PropertiesResourceManager manager = new PropertiesResourceManager("testrail");

    private APIClient apiClient;
    private String projectId;
    private String suite_id;
    private String enableTr;
    private static Reporter reporter;

    private Reporter() {
        this.apiClient = new APIClient(manager.getProperty("link"));
        this.projectId = manager.getProperty("projectId");
        this.apiClient.setUser(manager.getProperty("email"));
        this.apiClient.setPassword(manager.getProperty("password"));
        this.suite_id = manager.getProperty("suite_id");
        this.enableTr = System.getProperty("testRail");
    }

    public static Reporter getReporter() {
        if (reporter == null) {
            reporter = new Reporter();
        }
        return reporter;
    }

    public APIClient getApiClient() {
        return apiClient;
    }

    public String getSuiteId() {
        return suite_id;
    }

    public String getProjectId() {
        return projectId;
    }
    public String getEnableTr() {
        return enableTr;
    }
}
