package lt.jgreibus.magicdraw.redmine.plugin;

import com.nomagic.actions.ActionsCategory;
import com.nomagic.actions.NMAction;
import com.nomagic.magicdraw.actions.ActionsConfiguratorsManager;
import com.nomagic.magicdraw.core.Application;
import com.nomagic.magicdraw.core.options.EnvironmentOptions;
import com.nomagic.magicdraw.plugins.Plugin;
import com.nomagic.magicdraw.ui.browser.actions.DefaultBrowserAction;
import lt.jgreibus.magicdraw.redmine.plugin.actions.CollectReqElementsForUpdate;
import lt.jgreibus.magicdraw.redmine.plugin.actions.ImportProblems;
import lt.jgreibus.magicdraw.redmine.plugin.actions.ImportProblemsInBrowser;
import lt.jgreibus.magicdraw.redmine.plugin.actions.configurators.BrowserContextMenuConfigurator;
import lt.jgreibus.magicdraw.redmine.plugin.actions.configurators.MainMenuConfigurator;
import lt.jgreibus.magicdraw.redmine.plugin.options.IntegrationEnvironmentOptions;
import lt.jgreibus.magicdraw.redmine.plugin.options.IntegrationProjectOptions;

public class Main extends Plugin {

	private EnvironmentOptions.EnvironmentChangeListener mEnvironmentOptionsListener;

	@Override
	public void init() {

		configureEnvironmentOptions();
		ActionsConfiguratorsManager manager = ActionsConfiguratorsManager.getInstance();
		manager.addMainMenuConfigurator(new MainMenuConfigurator(getMenuActions()));
		IntegrationProjectOptions.addProjectOptionsConfigurator();
		final DefaultBrowserAction browserAction = new ImportProblemsInBrowser();
		BrowserContextMenuConfigurator configurator = new BrowserContextMenuConfigurator(browserAction);
		manager.addContainmentBrowserContextConfigurator(configurator);
	}

	@Override
	public boolean close() {
		return true;
	}

	@Override
	public boolean isSupported() {
		return true;
	}

	private NMAction getMenuActions(){
        ActionsCategory category = new ActionsCategory(null, "");
        category.addAction(new ImportProblems(null, "Import Problems from Redmine"));
		category.addAction(new CollectReqElementsForUpdate(null, "Update Redmine Issue"));
		return category;
	}

	private void configureEnvironmentOptions() {
		Application application = Application.getInstance();
		EnvironmentOptions options = application.getEnvironmentOptions();
		options.addGroup(new IntegrationEnvironmentOptions());

//		mEnvironmentOptionsListener = new EnvironmentOptions.EnvironmentChangeListener() {
//			@Override
//			public void updateByEnvironmentProperties(List props) {
//				System.out.println("Environment options changed:");
//
//				for (Object o : props) {
//					System.out.println(o);
//				}
//			}
//		};
//
//		options.addEnvironmentChangeListener(mEnvironmentOptionsListener);
    }
}
