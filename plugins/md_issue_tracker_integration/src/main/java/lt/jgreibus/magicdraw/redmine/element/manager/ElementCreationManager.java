package lt.jgreibus.magicdraw.redmine.element.manager;

import com.nomagic.magicdraw.core.Application;
import com.nomagic.magicdraw.core.Project;
import com.nomagic.magicdraw.openapi.uml.ModelElementsManager;
import com.nomagic.magicdraw.openapi.uml.ReadOnlyElementException;
import com.nomagic.magicdraw.openapi.uml.SessionManager;
import com.nomagic.uml2.ext.jmi.helpers.StereotypesHelper;
import com.nomagic.uml2.ext.magicdraw.classes.mdkernel.Class;
import com.nomagic.uml2.ext.magicdraw.classes.mdkernel.Element;
import com.nomagic.uml2.ext.magicdraw.mdprofiles.Profile;
import com.nomagic.uml2.ext.magicdraw.mdprofiles.Stereotype;
import com.nomagic.uml2.impl.ElementsFactory;

import static com.nomagic.uml2.ext.jmi.helpers.StereotypesHelper.addStereotype;


public class ElementCreationManager {

    public static void createStereotypedClassElement(Element owner, String subject, String issue) {

        final Project project = Application.getInstance().getProject();
        Profile profile = StereotypesHelper.getProfile(project, "Softneta Medical Profile");
        Stereotype stereotype = StereotypesHelper.getStereotype(project, "Problem", profile);
        if (project != null) {
            if (!SessionManager.getInstance().isSessionCreated(project)) {
                ElementsFactory factory = project.getElementsFactory();
                SessionManager.getInstance().createSession(project, "Create element");
                try {
                    Class c = factory.createClassInstance();
                    ModelElementsManager.getInstance().addElement(c, owner);
                    try {
                        c.setName(subject);
                        addStereotype(c, stereotype);
                        StereotypesHelper.setStereotypePropertyValue(c, stereotype, "issue", issue);
                    } catch (java.lang.IllegalArgumentException e) {
                        System.out.println(e);
                    }
                } catch (ReadOnlyElementException e) {

                }
                SessionManager.getInstance().closeSession(project);
            }
        }
    }
}
