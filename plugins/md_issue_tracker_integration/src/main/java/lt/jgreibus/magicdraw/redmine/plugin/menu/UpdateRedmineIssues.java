package lt.jgreibus.magicdraw.redmine.plugin.menu;

import com.nomagic.magicdraw.actions.MDAction;
import com.nomagic.uml2.ext.jmi.reflect.ClassTypes;
import com.nomagic.uml2.ext.magicdraw.classes.mdkernel.Class;
import com.nomagic.uml2.ext.magicdraw.classes.mdkernel.Package;
import lt.jgreibus.magicdraw.redmine.trackerManager.RedmineIssueManager;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.*;
import java.util.List;

public class UpdateRedmineIssues extends MDAction{
    public UpdateRedmineIssues(String id, String name)
    {
        super(id, name, null, null);
    }

    public void actionPerformed(ActionEvent e)
    {
        List<java.lang.Class> types = ClassTypes.getSubtypes(Class.class);

        RedmineIssueManager.AddIssueDescription();

    }
}
