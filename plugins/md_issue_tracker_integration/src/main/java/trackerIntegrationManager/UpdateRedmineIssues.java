package trackerIntegrationManager;

import com.nomagic.magicdraw.actions.MDAction;
import com.nomagic.magicdraw.core.Application;
import com.nomagic.magicdraw.core.Project;
import com.nomagic.magicdraw.ui.dialogs.selection.ElementSelectionDlg;
import com.nomagic.magicdraw.uml.BaseElement;
import com.nomagic.magicdraw.uml.ClassTypes;
import com.nomagic.uml2.ext.jmi.helpers.StereotypesHelper;
import com.nomagic.uml2.ext.magicdraw.classes.mdkernel.Element;
import com.nomagic.uml2.ext.magicdraw.classes.mdkernel.Package;
import com.nomagic.uml2.ext.magicdraw.mdprofiles.Profile;
import com.nomagic.uml2.ext.magicdraw.mdprofiles.Stereotype;
import trackerManager.RedmineIssueManager;

import java.awt.event.ActionEvent;
import java.util.List;

import static elementSelectionManager.ElementSelectionManager.createElementSelectionDialog;

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
