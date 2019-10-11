package framework.testRail;

import com.beust.jcommander.Parameter;

import org.json.simple.JSONObject;
import framework.browser.PropertiesResourceManager;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class ConfigurateTestRailRequest {

    public String runId;
    public String useTr;
    private static PropertiesResourceManager manager = new PropertiesResourceManager();
    public void createTestRun(String suiteId){
        manager.getSystemProperty("testRail");
        Reporter reporter = Reporter.getReporter();
        Map<String, Object> data = new HashMap();
        data.put("name", String.format("Run%s", new Random().nextInt(100)));
        data.put("include_all", true);
        data.put("suite_id", suiteId);
        this.useTr = System.getProperty("testRail");
        if (useTr.equals("true")) {
            try {
                JSONObject response = (JSONObject) reporter.getApiClient().sendPost(String.format("add_run/%s", reporter.getProjectId()), data);
                runId = response.get("id").toString();
            } catch (IOException | APIException e) {
                e.printStackTrace();
            }
        }
    }

    public void enterResults(int id, int statusResponse, String runId){

            int status = 0;
            switch (statusResponse) {
                case 2:
                    status = 5;
                    break;
                case 1:
                    status = 1;
                    break;
            }
            Reporter reporter = Reporter.getReporter();
            Map<String, Object> data = new HashMap();
            data.put("status_id", status);
            if (useTr.equals("true")){
            try {
                reporter.getApiClient().sendPost(String.format("add_result_for_case/" + runId + "/%s", id), data);
            } catch (IOException | APIException e) {
                e.printStackTrace();
            }
        }
    }
    public String getRunId() {
        return runId;
    }
}
