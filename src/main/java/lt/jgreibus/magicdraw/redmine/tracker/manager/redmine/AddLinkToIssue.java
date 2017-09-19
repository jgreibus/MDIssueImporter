package lt.jgreibus.magicdraw.redmine.tracker.manager.redmine;

import com.nomagic.magicdraw.core.Application;
import com.nomagic.magicdraw.ui.notification.Notification;
import com.nomagic.magicdraw.ui.notification.NotificationManager;
import com.nomagic.magicdraw.ui.notification.NotificationSeverity;
import com.taskadapter.redmineapi.RedmineException;
import com.taskadapter.redmineapi.RedmineManager;
import com.taskadapter.redmineapi.RedmineManagerFactory;
import com.taskadapter.redmineapi.bean.CustomField;
import com.taskadapter.redmineapi.bean.Issue;

public class AddLinkToIssue {

    RedmineIssueManager mng = new RedmineIssueManager(Application.getInstance());
    mng.

    public static void updateIssue(Integer id){
        //mng.
    }

    public static void addLinkToSpec(String url, String issueID) {
        //String uri = RedmineIssueManager.uri;
        //String api = apiAccessKey;
        if (uri != null && uri != "" && api != null && api != "") {
            RedmineManager mgr = RedmineManagerFactory.createWithApiKey(uri, api);
            Issue issue = null;
            try {
                issue = mgr.getIssueManager().getIssueById(Integer.valueOf(issueID));
            } catch (RedmineException e) {
                NotificationManager.getInstance().showNotification(new Notification("NOT_FOUND_ISSUE", "Issue has not be found by specified ID", e.getMessage(), NotificationSeverity.ERROR));
                return;
            }
            CustomField cf = issue.getCustomFieldByName(requirementCustomField);
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
            } else {
                NotificationManager.getInstance().showNotification(new Notification("CF_NULL",
                        "Custom field for Requirement Specification is missing or specified incorrectly",
                        "",
                        NotificationSeverity.ERROR));
            }
        } else NotificationManager.getInstance().showNotification(new Notification("PROPERTIES_NULL",
                "Redmine configuration properties are missing",
                "Redmine uri or user API Key is missing in the Environment  options",
                NotificationSeverity.ERROR));
    }

}
