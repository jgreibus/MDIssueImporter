package lt.jgreibus.magicdraw.redmine.plugin;

import com.nomagic.magicdraw.actions.MDAction;
import lt.jgreibus.magicdraw.redmine.trackerManager.RedmineIssueManager;

import java.awt.event.ActionEvent;

public class UpdateRedmineIssues extends MDAction{
    public UpdateRedmineIssues(String id, String name)
    {
        super(id, name, null, null);
    }

    public void actionPerformed(ActionEvent e)
    {
        RedmineIssueManager.AddIssueDescription();
    }
}
