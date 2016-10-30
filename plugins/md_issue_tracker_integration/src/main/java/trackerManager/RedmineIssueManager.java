package trackerManager;

import com.nomagic.uml2.ext.magicdraw.classes.mdkernel.Element;
import com.taskadapter.redmineapi.IssueManager;
import com.taskadapter.redmineapi.RedmineException;
import com.taskadapter.redmineapi.RedmineManagerFactory;
import com.taskadapter.redmineapi.bean.Issue;

import java.util.List;

import static elementCreationManager.ElementCreationManager.createStereotypedClassElement;

public class RedmineIssueManager {
    final static String uri = "https://redmine.softneta.com";
    final static String apiAccessKey = "15f2ae24367ff01363f473221a747e763c559192";
    //        final String uri = ((IntegrationEnvironmentOptions) Application.getInstance().getEnvironmentOptions().getGroup(IntegrationEnvironmentOptions.ID)).getTrackerUrlId();
//        final String apiAccessKey = ((IntegrationEnvironmentOptions) Application.getInstance().getEnvironmentOptions().getGroup(IntegrationEnvironmentOptions.ID)).getUserAPIKeyValue();
    final static String projectKey = "151";
    final static Integer queryId = 162; // any

    public static void GetIssues(Element owner) {


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

    public static void AddIssueDescription(){
        com.taskadapter.redmineapi.RedmineManager mgr = RedmineManagerFactory.createWithApiKey(uri, apiAccessKey);
        Issue issue = null;
        try {
            issue = mgr.getIssueManager().getIssueById(10889);
        } catch (RedmineException e) {
            e.printStackTrace();
        }
        issue.setDescription("New description");
        issue.setSubject("New subject");
        issue.setEstimatedHours((float) 15.0);
        try {
            mgr.getIssueManager().update(issue);
            //mgr.
        } catch (RedmineException e) {
            System.out.println(e);
        }
        Issue i = null;
        try {
            i = mgr.getIssueManager().getIssueById(10889);
        } catch (RedmineException e) {
            e.printStackTrace();
        }
        System.out.println(i.getDescription().toString());

    }
}
