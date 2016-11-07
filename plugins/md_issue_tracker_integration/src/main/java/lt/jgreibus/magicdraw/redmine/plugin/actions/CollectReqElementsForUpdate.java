package lt.jgreibus.magicdraw.redmine.plugin.actions;

import com.nomagic.magicdraw.actions.MDAction;
import com.nomagic.magicdraw.core.Application;
import com.nomagic.magicdraw.core.Project;
import com.nomagic.magicdraw.ui.dialogs.MDDialogParentProvider;
import com.nomagic.magicdraw.ui.dialogs.selection.ElementSelectionDlg;
import com.nomagic.magicdraw.ui.notification.Notification;
import com.nomagic.magicdraw.ui.notification.NotificationManager;
import com.nomagic.magicdraw.ui.notification.NotificationSeverity;
import com.nomagic.magicdraw.uml.BaseElement;
import com.nomagic.magicdraw.uml.ClassTypes;
import com.nomagic.uml2.ext.jmi.helpers.StereotypesHelper;
import com.nomagic.uml2.ext.magicdraw.classes.mdkernel.Class;
import com.nomagic.uml2.ext.magicdraw.classes.mdkernel.Element;
import com.nomagic.uml2.ext.magicdraw.mdprofiles.Profile;
import com.nomagic.uml2.ext.magicdraw.mdprofiles.Stereotype;
import lt.jgreibus.magicdraw.redmine.element.manager.HTMLbuilder;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static lt.jgreibus.magicdraw.redmine.element.manager.ElementSelectionManager.createStereotypedElementsSelectionDialog;

public class CollectReqElementsForUpdate extends MDAction{

    public CollectReqElementsForUpdate(String id, String name)
    {
        super(id, name, null, null);
    }

    private static Collection<String> getLinkedIssues(Collection e) {

        Collection<BaseElement> inputCollection = new ArrayList<>(e);
        Collection<String> issueID = new ArrayList<>();
        for (BaseElement element : inputCollection) {
            if (StereotypesHelper.hasStereotype((Element) element, "Related Issue")) {
                List<String> issue = StereotypesHelper.getStereotypePropertyValueAsString((Element) element, "Related Issue", "issueID");
                if (issue.size() == 1 && !issueID.contains(issue.get(0))) {
                    issueID.add(issue.get(0));
                }
            }
        }
        return issueID;
    }

    public void actionPerformed(ActionEvent e) {
        List<java.lang.Class> types = ClassTypes.getSubtypes(Class.class);

        Project project = Application.getInstance().getProject();
        Profile profile = StereotypesHelper.getProfile(project, "SysML Profile");
        Stereotype stereotype = StereotypesHelper.getStereotype(project, "Requirement", profile);

        ElementSelectionDlg elementSelectionDlg = createStereotypedElementsSelectionDialog(types, stereotype);
        elementSelectionDlg.setVisible(true);

        if (elementSelectionDlg.isOkClicked()) {

            Collection<BaseElement> selectedElements = new ArrayList<>();
            List<BaseElement> candidates = elementSelectionDlg.getSelectedElements();
            if (!candidates.isEmpty()) {
                for (BaseElement bEl : candidates) {
                    if (StereotypesHelper.hasStereotype((Element) bEl, stereotype)) {
                        selectedElements.add(bEl);
                    }
                }
                if (getLinkedIssues(selectedElements).size() > 1) {
                    Object[] i = getLinkedIssues(selectedElements).toArray(new Object[getLinkedIssues(selectedElements).size()]);
                    String selectedIssue = (String) JOptionPane.showInputDialog(MDDialogParentProvider.getProvider().getDialogParent(),
                            "Which one from listed issues should be updated?",
                            "Select Issue", 3, null, i, null);
                    //TODO
                    //RedmineIssueManager.AddIssueDescription(selectedIssue, HTMLbuilder.constructHTML(selectedElements));
                } else if (getLinkedIssues(selectedElements).size() == 1) {
                    //TODO
                    // RedmineIssueManager.AddIssueDescription(getLinkedIssues(selectedElements).iterator().next(),
                    //        HTMLbuilder.constructHTML(selectedElements));
                } else {
                    String selectedIssue = (String) JOptionPane.showInputDialog(MDDialogParentProvider.getProvider().getDialogParent(),
                            "Enter the issue ID that should be updated", "Enter issue ID", 1);
                    //TODO
                    //RedmineIssueManager.AddIssueDescription(selectedIssue, HTMLbuilder.constructHTML(selectedElements));
                }

            } else {
                NotificationManager.getInstance().showNotification(new Notification("NO_SELECTION",
                        "Requirements were not selected",
                        "Requirement element were not selected and issue update is stopped.", NotificationSeverity.WARNING));
            }
            System.out.println(HTMLbuilder.constructHTML(selectedElements));
        }
    }
}
