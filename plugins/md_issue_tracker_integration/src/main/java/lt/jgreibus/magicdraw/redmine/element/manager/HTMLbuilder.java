package lt.jgreibus.magicdraw.redmine.element.manager;

import com.nomagic.magicdraw.uml.BaseElement;
import com.nomagic.uml2.ext.jmi.helpers.StereotypesHelper;
import com.nomagic.uml2.ext.magicdraw.classes.mdkernel.Element;

import java.util.ArrayList;
import java.util.Collection;

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
}
