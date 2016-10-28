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

import java.awt.event.ActionEvent;
import java.util.List;

import static elementSelectionManager.elementSelectionManager.createElementSelectionDialog;
import static elementCreationManager.elementCreationManager.createStereotypedElement;

class SimpleAction extends MDAction
{
    public SimpleAction(String id, String name)
    {
        super(id, name, null, null);
    }
    /**
     * Shows message.
     */
    public void actionPerformed(ActionEvent e)
    {
        //JOptionPane.showMessageDialog(MDDialogParentProvider.getProvider().getDialogParent(), "This is:" + getName());
        //RedmineIssueManager.GetIssues();
        List<Class> types = ClassTypes.getSubtypes(Package.class);
        ElementSelectionDlg elementSelectionDlg = createElementSelectionDialog(types);

        elementSelectionDlg.setVisible(true);

        Project project = Application.getInstance().getProject();
        Profile profile = StereotypesHelper.getProfile(project, "UML_Standard_Profile");
        Stereotype stereotype = StereotypesHelper.getStereotype(project, "DiagramLegend", profile);

        if (elementSelectionDlg.isOkClicked()) {
            BaseElement selected = elementSelectionDlg.getSelectedElement();
            createStereotypedElement((Element) selected, stereotype);
        }
    }
}