package lt.jgreibus.magicdraw.redmine.plugin.actions;

import com.nomagic.magicdraw.actions.MDAction;
import com.nomagic.magicdraw.ui.dialogs.selection.ElementSelectionDlg;
import com.nomagic.magicdraw.uml.BaseElement;
import com.nomagic.magicdraw.uml.ClassTypes;
import com.nomagic.uml2.ext.jmi.helpers.StereotypesHelper;
import com.nomagic.uml2.ext.magicdraw.classes.mdkernel.Class;
import com.nomagic.uml2.ext.magicdraw.classes.mdkernel.DirectedRelationship;
import com.nomagic.uml2.ext.magicdraw.classes.mdkernel.Element;
import com.nomagic.uml2.ext.magicdraw.commonbehaviors.mdbasicbehaviors.Behavior;
import lt.jgreibus.magicdraw.redmine.utils.StereotypeUtils;

import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
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
        List<java.lang.Class> types = ClassTypes.getSubtypes(Class.class);

        ElementSelectionDlg elementSelectionDlg = createStereotypedElementsSelectionDialog(types, StereotypeUtils.getStereotypeObj("Softneta Medical Profile", "TC"));
        elementSelectionDlg.setVisible(true);

        if (elementSelectionDlg.isOkClicked()) {
            List<BaseElement> selectedElements = elementSelectionDlg.getSelectedElements();
            if(selectedElements.size()>0) {
                HashMap<Behavior, List<String>> map = createTestCaseAndIssueIDMap(selectedElements);
                List<String> IDs = new ArrayList<>();
                if(!map.isEmpty()) {
                    Collection<List<String>> ListOfIDs = map.values();

                    for(List list : ListOfIDs){
                        for(Object s : list){
                            IDs.add(s.toString());
                        }
                    }
                }
                for(String s : IDs){
                    System.out.println("Value: "+s);
                }
            }
        }
    }

    private static HashMap<Behavior, List<String>> createTestCaseAndIssueIDMap(List<BaseElement> cadidates) {

        String profileSysML = "SysML Profile";
        String profileMed = "Softneta Medical Profile";

        HashMap<Behavior, List<String>> tcMap = new HashMap<>();
        HashMap<String, List<Behavior>> map = new HashMap<>();
        List<Behavior> selectedTestCases = new ArrayList<>();
        List<String> reqIDs = new ArrayList<>();

        for(BaseElement bel : cadidates){
            Behavior el = (Behavior) bel;
            Collection<DirectedRelationship> targets = el.get_directedRelationshipOfSource();
            for (DirectedRelationship relationship : targets)
            {
                if(StereotypesHelper.hasStereotype(relationship, StereotypeUtils.getStereotypeObj(profileSysML, "Verify"))){
                    Collection<Element> target = relationship.getTarget();
                    if(target != null
                            && !target.isEmpty()
                            && StereotypesHelper.hasStereotypeOrDerived(target.iterator().next(),StereotypeUtils.getStereotypeObj(profileSysML, "Requirement"))){
                        List<String> issueIDs = StereotypesHelper.getStereotypePropertyValueAsString(target.iterator().next(), StereotypeUtils.getStereotypeObj(profileMed, "Related Issue"), "issueID");
                        if (issueIDs.size()>0 && !reqIDs.contains(issueIDs.iterator().next())) reqIDs.add(issueIDs.iterator().next());
                    }
                }
            }
            tcMap.put(el, reqIDs);
        }
        return tcMap;
    }




}
