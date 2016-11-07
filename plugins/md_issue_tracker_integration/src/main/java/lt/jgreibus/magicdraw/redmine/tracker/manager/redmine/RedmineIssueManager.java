package lt.jgreibus.magicdraw.redmine.tracker.manager.redmine;

import com.nomagic.magicdraw.core.Application;
import com.nomagic.magicdraw.ui.notification.Notification;
import com.nomagic.magicdraw.ui.notification.NotificationManager;
import com.nomagic.magicdraw.ui.notification.NotificationSeverity;
import com.nomagic.uml2.ext.magicdraw.classes.mdkernel.Element;
import com.taskadapter.redmineapi.RedmineException;
import com.taskadapter.redmineapi.RedmineManagerFactory;
import com.taskadapter.redmineapi.bean.Issue;
import lt.jgreibus.magicdraw.redmine.element.manager.StereotypedClassElementCreator;
import lt.jgreibus.magicdraw.redmine.element.manager.StereotypedClassElementCreator.StereotypeNotDefinedException;
import lt.jgreibus.magicdraw.redmine.plugin.options.IntegrationEnvironmentOptions;

import java.util.List;

public class RedmineIssueManager {

    private static final String getURI() {
        String uri = ((IntegrationEnvironmentOptions) Application.getInstance().getEnvironmentOptions().getGroup(IntegrationEnvironmentOptions.ID)).getTrackerUrlId();
        if (!uri.isEmpty() || uri.length() != 0)
            return uri;
//        else{
//            NotificationManager.getInstance().showNotification( new Notification("URI", "Tracker URL is not specified",
//                    "Tracker URL must be specified in the Environment Options", NotificationSeverity.ERROR));
//        return;}
    }

    private static final String getApiAccessKey() {
        return ((IntegrationEnvironmentOptions) Application.getInstance().getEnvironmentOptions().getGroup(IntegrationEnvironmentOptions.ID)).getUserAPIKeyValue();
    }

    final static String projectKey = "151";
    final static Integer queryId = 162; // any

    public static void GetIssues(Element owner) {


        com.taskadapter.redmineapi.RedmineManager mgr = RedmineManagerFactory.createWithApiKey(getURI(), getApiAccessKey());
        List<Issue> issues = null;
        try {
            issues = mgr.getIssueManager().getIssues(null, queryId);
        } catch (RedmineException e) {
            e.printStackTrace();
        }
        StereotypedClassElementCreator elementCreator = new StereotypedClassElementCreator();
        try {
            for (Issue issue : issues) {
                System.out.println(issue.toString());
                final String issueID = issue.getId().toString();
                final String subject = issue.getSubject().toString();
                elementCreator.create(owner, subject, issueID);
            }
            NotificationManager.getInstance().showNotification(new Notification("STATISTIC", "Import statistic:", String.format("Updated: " + elementCreator.getUpdatedElementCount() + "%nCreated: " + elementCreator.getCreatedElementCount())));
        } catch (StereotypeNotDefinedException e) {
            NotificationManager.getInstance().showNotification(new Notification(
                    e.getId(), e.getTitle(), e.getMessage(), NotificationSeverity.ERROR));
        }
    }
    public static void AddIssueDescription(String issueID, String description) {
        com.taskadapter.redmineapi.RedmineManager mgr = RedmineManagerFactory.createWithApiKey(getURI(), getApiAccessKey());
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
