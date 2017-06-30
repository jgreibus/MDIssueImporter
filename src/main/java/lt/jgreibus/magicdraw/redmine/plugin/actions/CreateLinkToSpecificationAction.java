package lt.jgreibus.magicdraw.redmine.plugin.actions;

import com.nomagic.magicdraw.core.Application;
import com.nomagic.magicdraw.core.Project;
import com.nomagic.magicdraw.ui.actions.DefaultDiagramAction;
import com.nomagic.magicdraw.uml.symbols.DiagramPresentationElement;
import com.nomagic.magicdraw.uml.symbols.PresentationElement;
import com.nomagic.uml2.ext.jmi.helpers.StereotypesHelper;
import lt.jgreibus.magicdraw.redmine.plugin.options.IntegrationEnvironmentOptions;
import lt.jgreibus.magicdraw.redmine.tracker.manager.redmine.RedmineIssueManager;

import javax.annotation.Nullable;
import java.awt.event.ActionEvent;
import java.util.List;

import static com.nomagic.magicdraw.core.options.ProjectOptions.PROJECT_GENERAL_PROPERTIES;


public class CreateLinkToSpecificationAction extends DefaultDiagramAction {

    public static final String DEFAULT_ID = "linkToSpec";
    public static final String actionText = "Create Link to Spec";
    private final static Project PROJECT = Application.getInstance().getProject();

    public CreateLinkToSpecificationAction() {
        super(DEFAULT_ID, actionText, null, null);
    }

    private static final String getCollaboratorURL() {
        return ((IntegrationEnvironmentOptions) Application.getInstance().getEnvironmentOptions().getGroup(IntegrationEnvironmentOptions.ID)).getCollaboratorUrlId();
    }

    @Nullable
    private static final String getTemplateNodeID() {
        final com.nomagic.magicdraw.properties.Property property = PROJECT.getOptions().getProperty(PROJECT_GENERAL_PROPERTIES, "TEMPLATE_NODE_ID");
        return (String) property.getValue();
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        String specURL;
        StringBuilder sb = new StringBuilder();
        DiagramPresentationElement diagram = PROJECT.getActiveDiagram();

        sb.append(getCollaboratorURL());
        sb.append(getTemplateNodeID());
        sb.append("NodeView__");
        sb.append(diagram.getDiagram().getID());
        sb.append("_cc_");
        sb.append(getTemplateNodeID());

        List<PresentationElement> selected = diagram.getSelected();
        for (PresentationElement pe : selected) {
            if (StereotypesHelper.hasStereotype(pe.getElement(), "Related Issue")) {
                String id = (StereotypesHelper.getStereotypePropertyValueAsString(pe.getElement(),
                        "Related Issue",
                        "issueID",
                        true).iterator().next());
                specURL = sb.toString();
                RedmineIssueManager.addLinkToSpec(specURL, id);
            }
        }
    }
}
