package issue_tracker_integration;

import com.nomagic.actions.AMConfigurator;
import com.nomagic.actions.ActionsCategory;
import com.nomagic.actions.ActionsManager;
import com.nomagic.actions.NMAction;
import com.nomagic.magicdraw.actions.MDActionsCategory;

/**
 * Created by justinasg on 2016-07-17.
 */
public class Actions implements AMConfigurator{

   String IMPORT = "Import";

   private NMAction action;

   public Actions(NMAction action){
       this.action = action;
   }

   public void addAction(ActionsManager actionsManager) {
       ActionsCategory category = (ActionsCategory) actionsManager.getActionFor(IMPORT);
       if(category == null){
           category = new MDActionsCategory(IMPORT, IMPORT);
           category.setNested(true);
           actionsManager.addCategory(category);
       }
       category.addAction(action);
    }

    public int getPriority() {
        return AMConfigurator.MEDIUM_PRIORITY;
    }
}
