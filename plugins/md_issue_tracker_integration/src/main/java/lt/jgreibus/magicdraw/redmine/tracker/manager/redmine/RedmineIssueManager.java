package lt.jgreibus.magicdraw.redmine.tracker.manager.redmine;

import com.nomagic.magicdraw.ui.notification.Notification;
import com.nomagic.magicdraw.ui.notification.NotificationManager;
import com.nomagic.magicdraw.ui.notification.NotificationSeverity;
import com.nomagic.uml2.ext.magicdraw.classes.mdkernel.Element;
import com.taskadapter.redmineapi.RedmineException;
import com.taskadapter.redmineapi.RedmineManagerFactory;
import com.taskadapter.redmineapi.bean.Issue;
import lt.jgreibus.magicdraw.redmine.element.manager.StereotypedClassElementCreator;
import lt.jgreibus.magicdraw.redmine.element.manager.StereotypedClassElementCreator.StereotypeNotDefinedException;

import java.util.List;

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
        try {
            for (Issue issue : issues) {
                System.out.println(issue.toString());
                final String issueID = issue.getId().toString();
                final String subject = issue.getSubject().toString();
                new StereotypedClassElementCreator().create(owner, subject, issueID);
            }
        } catch (StereotypeNotDefinedException e) {
            NotificationManager.getInstance().showNotification(new Notification(
                    e.getId(), e.getTitle(), e.getMessage(), NotificationSeverity.ERROR));
        }
    }

    public static void AddIssueDescription(String issueID, String description) {
        com.taskadapter.redmineapi.RedmineManager mgr = RedmineManagerFactory.createWithApiKey(uri, apiAccessKey);
        Issue issue = null;
        try {
            issue = mgr.getIssueManager().getIssueById(Integer.parseInt(issueID));
        } catch (RedmineException e) {
            e.printStackTrace();
        }

        StringBuilder sb = new StringBuilder();
        String origin = issue.getDescription();
        if (origin.length() > 0) {
            sb.append(origin);
            sb.append("=============Requirements=============");
            sb.append(description);
            issue.setDescription(sb.toString());
        } else {
            issue.setDescription(description);
        }
        issue.setNotes("Added requirements for: " + issueID);
        try {
            mgr.getIssueManager().update(issue);
            //mgr.
        } catch (RedmineException e) {
            System.out.println(e);
        }
    }
}
