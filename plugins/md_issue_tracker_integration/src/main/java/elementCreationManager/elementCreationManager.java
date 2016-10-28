package elementCreationManager;

import com.nomagic.magicdraw.core.Application;
import com.nomagic.magicdraw.core.Project;
import com.nomagic.magicdraw.openapi.uml.ModelElementsManager;
import com.nomagic.magicdraw.openapi.uml.ReadOnlyElementException;
import com.nomagic.magicdraw.openapi.uml.SessionManager;
import com.nomagic.magicdraw.uml.BaseElement;
import com.nomagic.uml2.ext.magicdraw.classes.mdkernel.Class;
import com.nomagic.uml2.ext.magicdraw.classes.mdkernel.Element;
import com.nomagic.uml2.ext.magicdraw.mdprofiles.Stereotype;
import com.nomagic.uml2.impl.ElementsFactory;
import com.sun.xml.internal.messaging.saaj.soap.impl.ElementFactory;

import java.util.Collection;

import static com.nomagic.uml2.ext.jmi.helpers.StereotypesHelper.addStereotype;


public class elementCreationManager {

    public static void createStereotypedElement(Element owner, Stereotype stereotype) {

        final Project project = Application.getInstance().getProject();
        if (project != null) {
            if (!SessionManager.getInstance().isSessionCreated(project)) {
                ElementsFactory factory = project.getElementsFactory();
                SessionManager.getInstance().createSession(project, "Create element");
                try {
                    Class c = factory.createClassInstance();
                    ModelElementsManager.getInstance().addElement(c, owner);
                    try {
                        addStereotype(c, stereotype);
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
