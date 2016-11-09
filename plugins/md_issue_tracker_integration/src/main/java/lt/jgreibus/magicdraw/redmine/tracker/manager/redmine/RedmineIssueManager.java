package lt.jgreibus.magicdraw.redmine.tracker.manager.redmine;

import com.nomagic.magicdraw.core.Application;
import com.nomagic.magicdraw.core.Project;
import com.nomagic.magicdraw.ui.notification.Notification;
import com.nomagic.magicdraw.ui.notification.NotificationManager;
import com.nomagic.magicdraw.ui.notification.NotificationSeverity;
import com.nomagic.uml2.ext.magicdraw.classes.mdkernel.Element;
import com.taskadapter.redmineapi.RedmineException;
import com.taskadapter.redmineapi.RedmineManagerFactory;
import com.taskadapter.redmineapi.bean.Issue;
import lt.jgreibus.magicdraw.redmine.element.manager.StereotypedClassElementCreator;
import lt.jgreibus.magicdraw.redmine.element.manager.StereotypedClassElementCreator.StereotypeNotDefinedException;
import lt.jgreibus.magicdraw.redmine.exception.NotifiedException;
import lt.jgreibus.magicdraw.redmine.plugin.options.IntegrationEnvironmentOptions;

import javax.annotation.Nullable;
import java.util.List;

import static com.nomagic.magicdraw.core.options.ProjectOptions.PROJECT_GENERAL_PROPERTIES;

public class RedmineIssueManager {

    private final static Project PROJECT = Application.getInstance().getProject();

    @Nullable
    private static final String getURI() {
        return ((IntegrationEnvironmentOptions) Application.getInstance().getEnvironmentOptions().getGroup(IntegrationEnvironmentOptions.ID)).getTrackerUrlId();
    }

    @Nullable
    private static final String getApiAccessKey() {
        return ((IntegrationEnvironmentOptions) Application.getInstance().getEnvironmentOptions().getGroup(IntegrationEnvironmentOptions.ID)).getUserAPIKeyValue();
    }

    @Nullable
    private static final String getProjectID() {
        final com.nomagic.magicdraw.properties.Property property = PROJECT.getOptions().getProperty(PROJECT_GENERAL_PROPERTIES, "PROJECT_ID");
        return (String) property.getValue();
    }

    @Nullable
    private static final Integer getQueryID() {

        com.nomagic.magicdraw.properties.Property property;
        Integer queryID = 0;
        try {
            property = PROJECT.getOptions().getProperty(PROJECT_GENERAL_PROPERTIES, "PROJECT_ID");
            queryID = Integer.parseInt((String) property.getValue());
        } catch (NumberFormatException e) {
            throw new NotifiedException(e);
        }
        return queryID;
    }

    public static void GetRedmineIssues(Element owner) {

        if (!getURI().isEmpty() && !getApiAccessKey().isEmpty()) {
            com.taskadapter.redmineapi.RedmineManager mgr = RedmineManagerFactory.createWithApiKey(getURI(), getApiAccessKey());

        List<Issue> issues = null;
        try {
            if (!getProjectID().isEmpty() || getQueryID() != 0)
                issues = mgr.getIssueManager().getIssues(getProjectID(), getQueryID());
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

            NotificationManager.getInstance().showNotification(new Notification("STATISTIC",
                    "Redmine import statistic:",
                    "New elements were created: " + elementCreator.getCreatedElementCount()
                            + " , updated: " + elementCreator.getUpdatedElementCount()));
        } catch (StereotypeNotDefinedException e) {
            NotificationManager.getInstance().showNotification(new Notification(
                    e.getId(), e.getTitle(), e.getMessage(), NotificationSeverity.ERROR));
        }
    } else
            NotificationManager.getInstance().showNotification(new Notification("NOT_DEFINED",
                    "Configuration properties are missing",
                    "Tracker URL or/and user access API is missing. Please specify missing properties in Environment Options",
                    NotificationSeverity.ERROR));
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

    public static final class ConfigurationPropertyMissingExcpetion extends NotifiedException {
        private ConfigurationPropertyMissingExcpetion(String id, String title, String message) {
            super(id, title, message);
        }
    }
}
