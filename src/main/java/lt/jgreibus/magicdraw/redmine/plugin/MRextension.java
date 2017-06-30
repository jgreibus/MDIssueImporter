package lt.jgreibus.magicdraw.redmine.plugin;

import com.nomagic.actions.ActionsCategory;
import com.nomagic.actions.NMAction;
import com.nomagic.magicdraw.actions.ActionsConfiguratorsManager;
import com.nomagic.magicdraw.core.Application;
import com.nomagic.magicdraw.core.options.EnvironmentOptions;
import com.nomagic.magicdraw.plugins.Plugin;
import com.nomagic.magicdraw.ui.browser.actions.DefaultBrowserAction;
import com.nomagic.magicdraw.uml.DiagramTypeConstants;
import lt.jgreibus.magicdraw.redmine.plugin.actions.AddTestCasesToIssuesAction;
import lt.jgreibus.magicdraw.redmine.plugin.actions.CollectReqElementsForUpdate;
import lt.jgreibus.magicdraw.redmine.plugin.actions.ImportProblems;
import lt.jgreibus.magicdraw.redmine.plugin.actions.ImportProblemsInBrowser;
import lt.jgreibus.magicdraw.redmine.plugin.actions.configurators.BrowserContextConfigurator;
import lt.jgreibus.magicdraw.redmine.plugin.actions.configurators.DiagramContextConfigurator;
import lt.jgreibus.magicdraw.redmine.plugin.actions.configurators.TrackerIntegrationMainMenuConfigurator;
import lt.jgreibus.magicdraw.redmine.plugin.options.IntegrationEnvironmentOptions;
import lt.jgreibus.magicdraw.redmine.plugin.options.IntegrationProjectOptions;

public class MRextension extends Plugin {

	private EnvironmentOptions.EnvironmentChangeListener mEnvironmentOptionsListener;

	@Override
	public void init() {

		configureEnvironmentOptions();
		ActionsConfiguratorsManager manager = ActionsConfiguratorsManager.getInstance();
		manager.addMainMenuConfigurator(new TrackerIntegrationMainMenuConfigurator(getMenuActions()));
		IntegrationProjectOptions.addProjectOptionsConfigurator();

		final DefaultBrowserAction browserAction = new ImportProblemsInBrowser();
		BrowserContextConfigurator configurator = new BrowserContextConfigurator(browserAction);
		manager.addContainmentBrowserContextConfigurator(configurator);

		DiagramContextConfigurator diagramConfigurator = new DiagramContextConfigurator();
		manager.addBaseDiagramContextConfigurator(DiagramTypeConstants.UML_ANY_DIAGRAM, diagramConfigurator);
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
		category.addAction(new AddTestCasesToIssuesAction(null, "Export Test Case Info"));
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
