package lt.jgreibus.magicdraw.redmine.tracker.manager.redmine;

import com.nomagic.magicdraw.core.Application;
import com.nomagic.magicdraw.core.Project;
import com.nomagic.magicdraw.ui.notification.Notification;
import com.nomagic.magicdraw.ui.notification.NotificationManager;
import com.nomagic.magicdraw.ui.notification.NotificationSeverity;
import com.nomagic.magicdraw.uml.BaseElement;
import com.nomagic.uml2.ext.magicdraw.classes.mdkernel.Element;
import com.taskadapter.redmineapi.RedmineException;
import com.taskadapter.redmineapi.RedmineManager;
import com.taskadapter.redmineapi.RedmineManagerFactory;
import com.taskadapter.redmineapi.bean.CustomField;
import com.taskadapter.redmineapi.bean.Issue;
import lt.jgreibus.magicdraw.redmine.element.manager.HTMLbuilder;
import lt.jgreibus.magicdraw.redmine.element.manager.StereotypedClassElementCreator;
import lt.jgreibus.magicdraw.redmine.element.manager.StereotypedClassElementCreator.StereotypeNotDefinedException;
import lt.jgreibus.magicdraw.redmine.exception.NotifiedException;
import lt.jgreibus.magicdraw.redmine.plugin.options.IntegrationEnvironmentOptions;

import javax.annotation.Nullable;
import java.util.Collection;
import java.util.HashMap;
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

    private static final String getTestCaseCustomField(){
        final com.nomagic.magicdraw.properties.Property property = PROJECT.getOptions().getProperty(PROJECT_GENERAL_PROPERTIES, "TC_CUSTOM_FIELD");
        return (String) property.getValue();
    }

    private static final String getRequirementCustomField() {
        final com.nomagic.magicdraw.properties.Property property = PROJECT.getOptions().getProperty(PROJECT_GENERAL_PROPERTIES, "REQ_CUSTOM_FIELD");
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

    public static void getRedmineIssues(Element owner) {

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
                    "New elements were created: " + StereotypedClassElementCreator.getCreatedElementCount()
                            + " , updated: " + StereotypedClassElementCreator.getUpdatedElementCount()));
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
    public static void addIssueDescription(String issueID, String description) {
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

    private static boolean containsRequirements(String issueID){
        com.taskadapter.redmineapi.RedmineManager mgr = RedmineManagerFactory.createWithApiKey(getURI(), getApiAccessKey());
        Issue issue = null;
        try {
            issue = mgr.getIssueManager().getIssueById(Integer.parseInt(issueID));
        } catch (RedmineException e) {
            e.printStackTrace();
        }
        String description = issue.getDescription();

        return false;
    }

    public static void updateIssueTestReport (HashMap issueAndTestCaseMap){
        HashMap<String, Collection<BaseElement>> map = new HashMap<>(issueAndTestCaseMap);
        String uri = getURI();
        String api = getApiAccessKey();
        if (uri != null && uri != "" && api != null && api != "") {
            RedmineManager mgr = RedmineManagerFactory.createWithApiKey(uri, api);
            for (String key : map.keySet()) {
                Issue issue = null;
                try {
                    issue = mgr.getIssueManager().getIssueById(Integer.valueOf(key));
                } catch (RedmineException e) {
                    NotificationManager.getInstance().showNotification(new Notification("NOT_FOUND_ISSUE", "Issue has not be found by specified ID", e.getMessage(), NotificationSeverity.ERROR));
                    return;
                }
                CustomField cf = issue.getCustomFieldByName(getTestCaseCustomField());
                cf.setValue(HTMLbuilder.constructTestCaseReport(map.get(key)));
                try {
                    mgr.getIssueManager().update(issue);
                } catch (RedmineException e) {
                    NotificationManager.getInstance().showNotification(new Notification("ISSUE_NULL", "Issue has not be found by specified ID", e.getMessage(), NotificationSeverity.ERROR));
                    return;
                }
                NotificationManager.getInstance().showNotification(new Notification("ISSUE_UPDATE",
                        "Issue(s) updated successfully",
                        composeUpdateReport(map),
                        NotificationSeverity.INFO));
            }
        } else NotificationManager.getInstance().showNotification(new Notification("PROPERTIES_NULL",
                "Redmine configuration properties are missing",
                "Redmine URI or user API Key is missing in the Environment  options",
                NotificationSeverity.ERROR));
    }

    private static String composeUpdateReport(HashMap issueMap) {

        HashMap map = issueMap;
        int mapSize = map.keySet().size();
        StringBuilder sb = new StringBuilder();
        if (mapSize > 1) sb.append(mapSize + " issues have been updated by adding test case information");
        else sb.append(mapSize + " issue has been updated by adding test case information. \n");
        sb.append("Updated issues: ");
        map.keySet().forEach(o -> {
            sb.append(o.toString());
        });
        return sb.toString();
    }

    public static void addLinkToSpec(String url, String issueID) {
        String uri = getURI();
        String api = getApiAccessKey();
        if (uri != null && uri != "" && api != null && api != "") {
            RedmineManager mgr = RedmineManagerFactory.createWithApiKey(uri, api);
            Issue issue = null;
            try {
                issue = mgr.getIssueManager().getIssueById(Integer.valueOf(issueID));
            } catch (RedmineException e) {
                NotificationManager.getInstance().showNotification(new Notification("NOT_FOUND_ISSUE", "Issue has not be found by specified ID", e.getMessage(), NotificationSeverity.ERROR));
                return;
            }
            CustomField cf = issue.getCustomFieldByName(getRequirementCustomField());
            if (cf != null) {
                cf.setValue(url);
                try {
                    mgr.getIssueManager().update(issue);
                } catch (RedmineException e) {
                    NotificationManager.getInstance().showNotification(new Notification("ISSUE_NULL", "Issue has not be found by specified ID", e.getMessage(), NotificationSeverity.ERROR));
                    return;
                }
                NotificationManager.getInstance().showNotification(new Notification("ISSUE_UPDATE",
                        "Issue(s) updated successfully",
                        "Updated issue: " + issueID,
                        NotificationSeverity.INFO));
            }
            else {
                NotificationManager.getInstance().showNotification(new Notification("CF_NULL",
                        "Custom field for Requirement Specification is missing or specified incorrectly",
                        "",
                        NotificationSeverity.ERROR));
            }
        } else NotificationManager.getInstance().showNotification(new Notification("PROPERTIES_NULL",
                "Redmine configuration properties are missing",
                "Redmine URI or user API Key is missing in the Environment  options",
                NotificationSeverity.ERROR));
    }

    public static final class ConfigurationPropertyMissingExcpetion extends NotifiedException {
        private ConfigurationPropertyMissingExcpetion(String id, String title, String message) {
            super(id, title, message);
        }
    }
}
