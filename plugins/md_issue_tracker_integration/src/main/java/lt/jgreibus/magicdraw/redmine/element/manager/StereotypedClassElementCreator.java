package lt.jgreibus.magicdraw.redmine.element.manager;

import com.nomagic.magicdraw.core.Application;
import com.nomagic.magicdraw.core.Project;
import com.nomagic.magicdraw.openapi.uml.ModelElementsManager;
import com.nomagic.magicdraw.openapi.uml.ReadOnlyElementException;
import com.nomagic.magicdraw.openapi.uml.SessionManager;
import com.nomagic.magicdraw.properties.Property;
import com.nomagic.magicdraw.uml.Finder;
import com.nomagic.uml2.ext.jmi.helpers.StereotypesHelper;
import com.nomagic.uml2.ext.magicdraw.classes.mdkernel.Class;
import com.nomagic.uml2.ext.magicdraw.classes.mdkernel.Element;
import com.nomagic.uml2.ext.magicdraw.mdprofiles.Stereotype;
import com.nomagic.uml2.impl.ElementsFactory;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import static com.nomagic.magicdraw.core.options.ProjectOptions.PROJECT_GENERAL_PROPERTIES;
import static com.nomagic.uml2.ext.jmi.helpers.StereotypesHelper.addStereotype;
import static com.nomagic.uml2.ext.jmi.helpers.StereotypesHelper.canAssignStereotype;

public class StereotypedClassElementCreator {

    private static final Project PROJECT = Application.getInstance().getProject();
    private static Integer updatedCount = 0;
    private static Integer createdCount = 0;
    private final Stereotype stereotype;

    public StereotypedClassElementCreator() {
        if ((stereotype = getStereotype()) == null)
            throw new StereotypeNotDefinedException(
                    "NOT_EXISTING_STEREOTYPE",
                    "Stereotype is not specified",
                    "Can't find specified stereotype which should be applied on the elements that created for imported issues");
    }

    private static Stereotype getStereotype() {
        final Property property = PROJECT.getOptions().getProperty(PROJECT_GENERAL_PROPERTIES, "STEREOTYPE_ID");
        return (Stereotype) property.getValue();
    }

    public static final Integer getUpdatedElementCount() {
        return updatedCount;
    }

    public static final Integer getCreatedElementCount() {
        return createdCount;
    }

    public void create(Element owner, String subject, String issue) {

        final Collection<Class> existingElements = Finder.byTypeRecursively().find(PROJECT, new java.lang.Class[]{Class.class});
        final Optional<Class> element = existingElements.stream()
                .filter((c) -> StereotypesHelper.hasStereotype(c, stereotype))
                .filter((c) -> issue.equals(getIssueID(c)))
                .findAny();

        if (element.isPresent()) {
            element.get().setName(subject);
            updatedCount = updatedCount + 1;
        }
        else if (!SessionManager.getInstance().isSessionCreated(PROJECT)) {
            ElementsFactory factory = PROJECT.getElementsFactory();
            SessionManager.getInstance().createSession(PROJECT, "Create element");
            try {
                createElement(owner, subject, issue, factory);
            } catch (IllegalArgumentException | ReadOnlyElementException e) {
                throw new StereotypeNotDefinedException("STEREOTYPE", "Stereotype can't be applied", e.getMessage());
            } finally {
                SessionManager.getInstance().closeSession(PROJECT);
            }
        }
    }

    private void createElement(Element owner, String subject, String issue, ElementsFactory factory) throws ReadOnlyElementException {
        Class c = factory.createClassInstance();
        ModelElementsManager.getInstance().addElement(c, owner);
        c.setName(subject);
        createdCount = createdCount + 1;
        if (canAssignStereotype(c, stereotype)) {
            addStereotype(c, stereotype);
            StereotypesHelper.setStereotypePropertyValue(c, stereotype, "issue", issue);
        } else
            throw new StereotypeNotDefinedException(
                    "STEREOTYPE",
                    "Stereotype can't be applied",
                    "Specified stereotype can't be applied on the Class elements");
    }

    private String getIssueID(Class e) {
        final List list = StereotypesHelper.getStereotypePropertyValue(e, stereotype, "issue");
        return list.isEmpty() ? null : (String) list.get(0);
    }

    public static final class StereotypeNotDefinedException extends RuntimeException {

        private final String id;
        private final String title;

        private StereotypeNotDefinedException(String id, String title, String message) {
            super(message);
            this.id = id;
            this.title = title;
        }

        public String getId() {
            return id;
        }

        public String getTitle() {
            return title;
        }
    }
}
