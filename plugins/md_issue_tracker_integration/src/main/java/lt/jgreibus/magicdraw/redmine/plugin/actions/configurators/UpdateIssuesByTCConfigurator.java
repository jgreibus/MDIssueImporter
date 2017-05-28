package lt.jgreibus.magicdraw.redmine.plugin.actions.configurators;

import com.nomagic.actions.AMConfigurator;
import com.nomagic.actions.ActionsCategory;
import com.nomagic.actions.ActionsManager;
import com.nomagic.actions.NMAction;
import com.nomagic.magicdraw.actions.MDActionsCategory;

/**
 * Created by justa on 5/27/2017.
 */
public class UpdateIssuesByTCConfigurator implements AMConfigurator{
    String PARENT_MENU ="TOOLS";
    String MENU = "Export Test Case Info";

    private NMAction action;

    public UpdateIssuesByTCConfigurator(NMAction action)
    {
        this.action = action;
    }

    @Override
    public void configure(ActionsManager manager) {


        ActionsCategory category = (ActionsCategory) manager.getCategory(PARENT_MENU);

        if (category == null) {
            category = new MDActionsCategory(MENU, MENU);
            category.setNested(true);
            manager.addCategory(category);
        }
        category.addAction(action);
    }

    @Override
    public int getPriority()
    {
        return AMConfigurator.MEDIUM_PRIORITY;
    }
}
