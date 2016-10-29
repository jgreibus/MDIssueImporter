package trackerManager;

import com.nomagic.uml2.ext.magicdraw.classes.mdkernel.Element;
import com.taskadapter.redmineapi.RedmineException;
import com.taskadapter.redmineapi.RedmineManagerFactory;
import com.taskadapter.redmineapi.bean.Issue;

import java.util.List;

import static elementCreationManager.ElementCreationManager.createStereotypedClassElement;

public class RedmineIssueManager {


    public static void GetIssues(Element owner) {
        final String uri = "https://redmine.softneta.com";
        final String apiAccessKey = "15f2ae24367ff01363f473221a747e763c559192";
//        final String uri = ((IntegrationEnvironmentOptions) Application.getInstance().getEnvironmentOptions().getGroup(IntegrationEnvironmentOptions.ID)).getTrackerUrlId();
//        final String apiAccessKey = ((IntegrationEnvironmentOptions) Application.getInstance().getEnvironmentOptions().getGroup(IntegrationEnvironmentOptions.ID)).getUserAPIKeyValue();
        final String projectKey = "151";
        Integer queryId = 162; // any

        com.taskadapter.redmineapi.RedmineManager mgr = RedmineManagerFactory.createWithApiKey(uri, apiAccessKey);
        List<Issue> issues = null;
        try {
            issues = mgr.getIssueManager().getIssues(null, queryId);
        } catch (RedmineException e) {
            e.printStackTrace();
        }
        for (Issue issue : issues) {
            System.out.println(issue.toString());
            final String issueID = issue.getId().toString();
            final String subject = issue.getSubject().toString();
            createStereotypedClassElement(owner, subject, issueID);
        }
    }
}
