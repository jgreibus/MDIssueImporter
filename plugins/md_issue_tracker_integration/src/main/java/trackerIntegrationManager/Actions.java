package trackerIntegrationManager;

import com.nomagic.magicdraw.actions.MDAction;
import com.nomagic.magicdraw.ui.dialogs.MDDialogParentProvider;

import com.nomagic.magicdraw.ui.dialogs.selection.ElementSelectionDlg;
import com.nomagic.magicdraw.uml.BaseElement;
import com.nomagic.magicdraw.uml.ClassTypes;
import com.nomagic.uml2.ext.magicdraw.actions.mdbasicactions.CallBehaviorAction;
import com.nomagic.uml2.ext.magicdraw.classes.mdkernel.Property;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.util.List;

import static elementSelectionManager.elementSelectionManager.createElementSelectionDialog;

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
        JOptionPane.showMessageDialog(MDDialogParentProvider.getProvider().getDialogParent(), "This is:" + getName());
        //RedmineIssueManager.GetIssues();
        //elementSelectionManager.createElementSelectionDialog();
        //List<Class> types = ClassTypes.getSubtypes(Property.class);
        List<Class> types = ClassTypes.getSubtypes(CallBehaviorAction.class);
        ElementSelectionDlg elementSelectionDlg = createElementSelectionDialog(types);

        elementSelectionDlg.setVisible(true);

        if (elementSelectionDlg.isOkClicked()) {
            List<BaseElement> selectedElements = elementSelectionDlg.getSelectedElements();

            for (BaseElement selectedElement : selectedElements) {
                System.out.println(selectedElement.getHumanName());
            }
        }
    }
}