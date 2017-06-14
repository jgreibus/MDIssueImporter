package lt.jgreibus.magicdraw.redmine.element.manager;

import com.nomagic.magicdraw.uml.*;
import com.nomagic.uml2.ext.jmi.helpers.StereotypesHelper;
import com.nomagic.uml2.ext.magicdraw.classes.mdkernel.Element;
import com.nomagic.uml2.ext.magicdraw.commonbehaviors.mdbasicbehaviors.Behavior;
import lt.jgreibus.magicdraw.redmine.plugin.actions.AddTestCasesToIssues;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;


public class HTMLbuilder {

    public static String constructHTML(Collection selectedElements){

        Collection<BaseElement> elements = new ArrayList<>(selectedElements);

        StringBuilder sb = new StringBuilder();
        sb.append("<html>");

        for(BaseElement el : elements){
            sb.append("<h2>"+el.getHumanName()+"</h2>");
            for(String text : StereotypesHelper.getStereotypePropertyValueAsString((Element) el, "Requirement", "Text")) {
                sb.append(text);
            }
        }
        sb.append("</html>");
        return sb.toString();
    }

    public static String constructTestCaseReport (Collection selectedElements){

        Collection<BaseElement> list = new ArrayList<>(selectedElements);
        StringBuilder sb = new StringBuilder();

        sb.append("<table border=1 cellpadding=1 cellspacing=1");
        for(BaseElement e : list){
            HashMap map = AddTestCasesToIssues.getTestCaseResults((Behavior) e);
            System.out.println(map);
            map.keySet().forEach(key -> {
                sb.append("<tr>");
                sb.append("<td>" + e.getHumanName() + "</td>");
                if (StereotypesHelper.hasStereotype((Element) key, "Pass")) sb.append("<td>" + " Pass " + "</td>");
                else sb.append("<td>" + " Fail " + "</td>");
                sb.append("<td>" + map.get(key).toString() + "</td>");
                sb.append("</tr>");
            });
        }
        sb.append("</table>");
        return sb.toString();
    }
}
