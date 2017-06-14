package lt.jgreibus.magicdraw.redmine.plugin.actions.configurators;

import com.nomagic.actions.AMConfigurator;
import com.nomagic.actions.ActionsCategory;
import com.nomagic.actions.ActionsManager;
import com.nomagic.actions.NMAction;
import com.nomagic.magicdraw.actions.MDActionsCategory;

public class TrackerIntegrationMainMenuConfigurator implements AMConfigurator
    {
        String PARENT_MENU ="TOOLS";
        String MENU = "Tracker Integration";

        private NMAction action;

        public TrackerIntegrationMainMenuConfigurator(NMAction action)
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
