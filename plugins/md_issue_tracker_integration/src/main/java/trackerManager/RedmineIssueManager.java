package trackerManager;

import com.nomagic.magicdraw.core.Application;
import com.nomagic.magicdraw.core.options.EnvironmentOptions;
import com.taskadapter.redmineapi.RedmineException;
import com.taskadapter.redmineapi.RedmineManager;
import com.taskadapter.redmineapi.RedmineManagerFactory;
import com.taskadapter.redmineapi.bean.Issue;
import trackerIntegrationManager.IntegrationOptions;

import java.util.List;

public class RedmineIssueManager {


    public static void GetIssues() {
        //String uri = "https://redmine.softneta.com";
        //String apiAccessKey = "15f2ae24367ff01363f473221a747e763c559192";
        IntegrationOptions intOpt = new IntegrationOptions();
        final String uri = "https://redmine.softneta.com";
        final String apiAccessKey = ((IntegrationOptions) Application.getInstance().getEnvironmentOptions().getGroup(IntegrationOptions.ID)).getUserAPIKeyValue();
        String projectKey = "151";
        Integer queryId = null; // any

        com.taskadapter.redmineapi.RedmineManager mgr = RedmineManagerFactory.createWithApiKey(uri, apiAccessKey);
        List<Issue> issues = null;
        try {
            issues = mgr.getIssueManager().getIssues(projectKey, queryId);
        } catch (RedmineException e) {
            e.printStackTrace();
        }
        for (Issue issue : issues) {
            System.out.println(issue.toString());
        }
    }
}
