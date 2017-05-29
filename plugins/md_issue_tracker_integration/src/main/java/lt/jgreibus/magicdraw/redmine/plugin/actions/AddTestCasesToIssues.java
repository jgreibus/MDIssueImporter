package lt.jgreibus.magicdraw.redmine.plugin.actions;

import com.nomagic.magicdraw.actions.MDAction;
import com.nomagic.magicdraw.core.Application;
import com.nomagic.magicdraw.core.Project;
import com.nomagic.magicdraw.ui.dialogs.selection.ElementSelectionDlg;
import com.nomagic.magicdraw.uml.BaseElement;
import com.nomagic.magicdraw.uml.ClassTypes;
import com.nomagic.uml2.ext.jmi.helpers.ClassifierHelper;
import com.nomagic.uml2.ext.jmi.helpers.ModelHelper;
import com.nomagic.uml2.ext.jmi.helpers.StereotypesHelper;
import com.nomagic.uml2.ext.magicdraw.classes.mdkernel.Class;
import com.nomagic.uml2.ext.magicdraw.classes.mdkernel.Element;
import com.nomagic.uml2.ext.magicdraw.classes.mdkernel.Package;
import com.nomagic.uml2.ext.magicdraw.mdprofiles.Profile;
import com.nomagic.uml2.ext.magicdraw.mdprofiles.Stereotype;

import java.awt.event.ActionEvent;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import static com.nomagic.magicdraw.persistence.xmi.i.el;
import static lt.jgreibus.magicdraw.redmine.element.manager.ElementSelectionManager.createElementSelectionDialog;
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
        List<java.lang.Class> types = ClassTypes.getSubtypes(Class.class);
        Project project = Application.getInstance().getProject();
        Profile profile = StereotypesHelper.getProfile(project, "UML Testing Profile");
        Stereotype stereotype = StereotypesHelper.getStereotype(project, "TestCase", profile);

        ElementSelectionDlg elementSelectionDlg = createStereotypedElementsSelectionDialog(types, stereotype);

        elementSelectionDlg.setVisible(true);

        if (elementSelectionDlg.isOkClicked()) {

            List<BaseElement> selectedElements = elementSelectionDlg.getSelectedElements();

            for(BaseElement bel : selectedElements){

                Element el = (Element) bel;

                System.out.println(el.refGetValue("Verifies"));
               // ModelHelper.
              if(StereotypesHelper.hasStereotype((Element) el, stereotype)){

              }
            }
        }
    }
    //TBD
}
