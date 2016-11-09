package lt.jgreibus.magicdraw.redmine.plugin.actions;

import com.nomagic.magicdraw.actions.MDAction;
import com.nomagic.magicdraw.core.Application;
import com.nomagic.magicdraw.ui.dialogs.selection.ElementSelectionDlg;
import com.nomagic.magicdraw.uml.BaseElement;
import com.nomagic.magicdraw.uml.ClassTypes;
import com.nomagic.uml2.ext.magicdraw.classes.mdkernel.Element;
import com.nomagic.uml2.ext.magicdraw.classes.mdkernel.Package;
import lt.jgreibus.magicdraw.redmine.exception.NotifiedException;
import lt.jgreibus.magicdraw.redmine.tracker.manager.redmine.RedmineIssueManager;
import lt.jgreibus.magicdraw.redmine.utils.NotificationUtils;

import java.awt.event.ActionEvent;
import java.util.List;

import static lt.jgreibus.magicdraw.redmine.element.manager.ElementSelectionManager.createElementSelectionDialog;

public class ImportProblems extends MDAction
{
    public ImportProblems(String id, String name)
    {
        super(id, name, null, null);
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
        try {
        List<Class> types = ClassTypes.getSubtypes(Package.class);
        ElementSelectionDlg elementSelectionDlg = createElementSelectionDialog(types);

        elementSelectionDlg.setVisible(true);

        if (elementSelectionDlg.isOkClicked()) {
            BaseElement selected = elementSelectionDlg.getSelectedElement();
            RedmineIssueManager.GetRedmineIssues((Element) selected);
        }
        } catch (NotifiedException ex) {
            NotificationUtils.showNotification(ex);
        }
    }

    @Override
    public void updateState() {
        setEnabled(Application.getInstance().getProject() != null);
    }
}