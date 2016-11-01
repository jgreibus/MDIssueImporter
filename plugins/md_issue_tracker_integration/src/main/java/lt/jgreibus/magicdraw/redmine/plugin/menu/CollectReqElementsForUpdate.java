package lt.jgreibus.magicdraw.redmine.plugin.menu;

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
import lt.jgreibus.magicdraw.redmine.element.manager.ElementParserManeger;

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

    public void actionPerformed(ActionEvent e) {

        List<java.lang.Class> types = ClassTypes.getSubtypes(Class.class);

        Project project = Application.getInstance().getProject();
        Profile profile = StereotypesHelper.getProfile(project, "SysML Profile");
        Stereotype stereotype = StereotypesHelper.getStereotype(project, "Requirement", profile);

        //RedmineIssueManager.AddIssueDescription();
        ElementSelectionDlg elementSelectionDlg = createStereotypedElementsSelectionDialog(types, stereotype);
        elementSelectionDlg.setVisible(true);

        if (elementSelectionDlg.isOkClicked()) {

            String s = (String) JOptionPane.showInputDialog(MDDialogParentProvider.getProvider().getDialogParent(), "What is issue ID?");

            Collection<BaseElement> selectedElements = new ArrayList<>();
            List<BaseElement> candidates = elementSelectionDlg.getSelectedElements();
            if (!candidates.isEmpty()) {
                for (BaseElement bEl : candidates) {
                    if (StereotypesHelper.hasStereotype((Element) bEl, stereotype)) {
                        selectedElements.add(bEl);
                    }
                }
            } else {
                NotificationManager.getInstance().showNotification(new Notification("NO_SELECTION",
                        "Requirements were not selected",
                        "Requirement element were not selected and issue update is stopped.", NotificationSeverity.WARNING));
            }
            System.out.println(ElementParserManeger.constructHTML(selectedElements));
        }
//        private static Collection<String> getLinkedIssues(Collection e){
//
//        Collection<String> issueID = new ArrayList<>();
//        for(BaseElement element : e){
//            if(StereotypesHelper.hasStereotype((Element)element, ""))
//        }
    }
}
