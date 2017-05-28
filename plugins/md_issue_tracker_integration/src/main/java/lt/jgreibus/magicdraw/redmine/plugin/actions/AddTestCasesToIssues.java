package lt.jgreibus.magicdraw.redmine.plugin.actions;

import com.nomagic.magicdraw.actions.MDAction;
import com.nomagic.magicdraw.core.Application;
import com.nomagic.magicdraw.core.Project;
import com.nomagic.magicdraw.ui.dialogs.selection.ElementSelectionDlg;
import com.nomagic.magicdraw.uml.BaseElement;
import com.nomagic.magicdraw.uml.ClassTypes;
import com.nomagic.uml2.ext.jmi.helpers.StereotypesHelper;
import com.nomagic.uml2.ext.magicdraw.mdprofiles.Profile;
import com.nomagic.uml2.ext.magicdraw.mdprofiles.Stereotype;

import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static lt.jgreibus.magicdraw.redmine.element.manager.ElementSelectionManager.createStereotypedElementsSelectionDialog;

/**
 * Created by Justinas on 2017-05-26.
 */
public class AddTestCasesToIssues extends MDAction{
    public AddTestCasesToIssues(String id, String name) {
        super(id, name, null, null);
    }

    public void actionPerformed(ActionEvent e){
        //List<Class> types = ClassTypes.getSubtypes(Class.class);

        Class type = ClassTypes.getClassType("Class");
        Project project = Application.getInstance().getProject();
        Profile profile = StereotypesHelper.getProfile(project, "UML Testing Profile");
        Stereotype stereotype = StereotypesHelper.getStereotype(project, "TestCase", profile);

        ElementSelectionDlg elementSelectionDlg = createStereotypedElementsSelectionDialog(type, stereotype);


        elementSelectionDlg.setVisible(true);

        if (elementSelectionDlg.isOkClicked()) {

            Collection<BaseElement> selectedElements = new ArrayList<>();
            List<BaseElement> candidates = elementSelectionDlg.getSelectedElements();
        }
    }
    //TBD
}
