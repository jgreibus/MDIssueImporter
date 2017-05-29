package lt.jgreibus.magicdraw.redmine.plugin.actions;

import com.nomagic.magicdraw.actions.MDAction;
import com.nomagic.magicdraw.core.Application;
import com.nomagic.magicdraw.core.Project;
import com.nomagic.magicdraw.ui.dialogs.selection.ElementSelectionDlg;
import com.nomagic.magicdraw.uml.BaseElement;
import com.nomagic.magicdraw.uml.ClassTypes;
import com.nomagic.uml2.ext.jmi.helpers.StereotypesHelper;
import com.nomagic.uml2.ext.magicdraw.classes.mdkernel.Class;
import com.nomagic.uml2.ext.magicdraw.classes.mdkernel.DirectedRelationship;
import com.nomagic.uml2.ext.magicdraw.classes.mdkernel.Element;
import com.nomagic.uml2.ext.magicdraw.commonbehaviors.mdbasicbehaviors.Behavior;
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
        List<java.lang.Class> types = ClassTypes.getSubtypes(Class.class);
        Project project = Application.getInstance().getProject();
        Profile profile = StereotypesHelper.getProfile(project, "Softneta Medical Profile");
        Stereotype stereotype = StereotypesHelper.getStereotype(project, "TC", profile);

        ElementSelectionDlg elementSelectionDlg = createStereotypedElementsSelectionDialog(types, stereotype);
        elementSelectionDlg.setVisible(true);

        if (elementSelectionDlg.isOkClicked()) {
            List<BaseElement> selectedElements = elementSelectionDlg.getSelectedElements();
            extractVerifiedRequirementsFromTC(selectedElements);
        }
    }

    private static List<Element> extractVerifiedRequirementsFromTC(List<BaseElement> selectedElements) {
        List<Element> req = new ArrayList<>();
        for(BaseElement bel : selectedElements){
            Behavior el = (Behavior) bel;
            Collection<DirectedRelationship> targets = el.get_directedRelationshipOfSource();
            for (DirectedRelationship relationship : targets)
            {
                if(StereotypesHelper.hasStereotype(relationship, "Verify")){
                    Collection<Element> target = relationship.getTarget();
                    if(target != null && !target.isEmpty()){
                        for(Element t : target)
                            if(StereotypesHelper.hasStereotype(t, "Requirement")) req.add(t);
                    }
                }
            }
        }
        return req;
    }
}
