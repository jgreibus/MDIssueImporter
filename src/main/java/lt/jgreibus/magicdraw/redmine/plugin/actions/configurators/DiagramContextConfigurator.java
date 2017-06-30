package lt.jgreibus.magicdraw.redmine.plugin.actions.configurators;

import com.nomagic.actions.ActionsCategory;
import com.nomagic.actions.ActionsManager;
import com.nomagic.magicdraw.actions.ActionsGroups;
import com.nomagic.magicdraw.actions.DiagramContextAMConfigurator;
import com.nomagic.magicdraw.actions.MDActionsCategory;
import com.nomagic.magicdraw.uml.symbols.DiagramPresentationElement;
import com.nomagic.magicdraw.uml.symbols.PresentationElement;
import lt.jgreibus.magicdraw.redmine.plugin.actions.CreateLinkToSpecificationAction;
import org.apache.commons.lang.ArrayUtils;

public class DiagramContextConfigurator implements DiagramContextAMConfigurator {
    @Override
    public void configure(ActionsManager manager, DiagramPresentationElement diagram, PresentationElement[] selected, PresentationElement requestor) {
        configure(manager, (PresentationElement[]) ArrayUtils.add(selected, requestor));
    }

    public void configure(ActionsManager manager, PresentationElement... elements) {
        ActionsCategory category = manager.getCategory("Redmine");
        if (category == null) {
            category = new MDActionsCategory("Redmine", "Redmine", null, ActionsGroups.APPLICATION_RELATED);
            category.setNested(true);
        }
        manager.removeCategory(category);
        manager.addCategory(category);
        category.addAction(new CreateLinkToSpecificationAction());
    }

    @Override
    public int getPriority() {
        return 0;
    }


}
