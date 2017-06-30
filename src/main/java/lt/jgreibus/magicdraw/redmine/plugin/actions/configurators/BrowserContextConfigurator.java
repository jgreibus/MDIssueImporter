package lt.jgreibus.magicdraw.redmine.plugin.actions.configurators;

import com.nomagic.actions.AMConfigurator;
import com.nomagic.actions.ActionsManager;
import com.nomagic.magicdraw.actions.BrowserContextAMConfigurator;
import com.nomagic.magicdraw.actions.MDActionsCategory;
import com.nomagic.magicdraw.ui.browser.Tree;
import com.nomagic.magicdraw.ui.browser.actions.DefaultBrowserAction;

public class BrowserContextConfigurator implements BrowserContextAMConfigurator, AMConfigurator {
    private final DefaultBrowserAction browserAction;

    public BrowserContextConfigurator(DefaultBrowserAction action) {
        this.browserAction = action;
    }

    public void configure(ActionsManager manager, Tree tree) {
        configure(manager);
    }

    @Override
    public void configure(ActionsManager manager) {
        final MDActionsCategory category = new MDActionsCategory("", "");
        category.addAction(browserAction);
        manager.addCategory(category);
    }

    @Override
    public int getPriority() {
        return AMConfigurator.HIGH_PRIORITY;
    }
}
