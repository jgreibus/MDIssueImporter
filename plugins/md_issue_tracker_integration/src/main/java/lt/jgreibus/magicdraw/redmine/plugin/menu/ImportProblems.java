package lt.jgreibus.magicdraw.redmine.plugin.menu;

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
import lt.jgreibus.magicdraw.redmine.trackerManager.RedmineIssueManager;

import java.awt.event.ActionEvent;
import java.util.List;

import static lt.jgreibus.magicdraw.redmine.element.manager.ElementSelectionManager.createElementSelectionDialog;

public class ImportProblems extends MDAction
{
    public ImportProblems(String id, String name)
    {
        super(id, name, null, null);
    }
    /**
     * Shows message.
     */
    public void actionPerformed(ActionEvent e)
    {
        List<Class> types = ClassTypes.getSubtypes(Package.class);
        ElementSelectionDlg elementSelectionDlg = createElementSelectionDialog(types);

        elementSelectionDlg.setVisible(true);

        if (elementSelectionDlg.isOkClicked()) {
            BaseElement selected = elementSelectionDlg.getSelectedElement();
            RedmineIssueManager.GetIssues((Element) selected);
        }
    }
}