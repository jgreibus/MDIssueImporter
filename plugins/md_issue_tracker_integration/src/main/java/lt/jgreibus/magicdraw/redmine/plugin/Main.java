package lt.jgreibus.magicdraw.redmine.plugin;

import com.nomagic.actions.ActionsCategory;
import com.nomagic.actions.NMAction;
import com.nomagic.magicdraw.actions.ActionsConfiguratorsManager;
import com.nomagic.magicdraw.core.Application;
import com.nomagic.magicdraw.core.options.EnvironmentOptions;
import com.nomagic.magicdraw.plugins.Plugin;
import lt.jgreibus.magicdraw.redmine.plugin.actions.CollectReqElementsForUpdate;
import lt.jgreibus.magicdraw.redmine.plugin.actions.ImportProblems;
import lt.jgreibus.magicdraw.redmine.plugin.options.IntegrationEnvironmentOptions;
import lt.jgreibus.magicdraw.redmine.plugin.options.IntegrationProjectOptions;

import java.util.List;

public class Main extends Plugin {
	/**
	 * Make sure environment listener is a field, because it is registered as a weak reference in MagicDraw.
	 */
	@SuppressWarnings({"FieldCanBeLocal"})
	private EnvironmentOptions.EnvironmentChangeListener mEnvironmentOptionsListener;

	@Override
	public void init() {

		configureEnvironmentOptions();
		ActionsConfiguratorsManager manager = ActionsConfiguratorsManager.getInstance();
		manager.addMainMenuConfigurator(new MainMenuConfigurator(getMenuActions()));
		IntegrationProjectOptions.addProjectOptionsConfigurator();
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
		ActionsCategory category = new ActionsCategory(null, "subMenu");
		category.addAction(new ImportProblems(null, "Import Problems"));
		category.addAction(new CollectReqElementsForUpdate(null, "Update Redmine Issue"));
		return category;
	}

	/**
	 * Configures environment options.
	 */
	private void configureEnvironmentOptions() {
		Application application = Application.getInstance();
		EnvironmentOptions options = application.getEnvironmentOptions();
		options.addGroup(new IntegrationEnvironmentOptions());

		mEnvironmentOptionsListener = new EnvironmentOptions.EnvironmentChangeListener() {
			@Override
			public void updateByEnvironmentProperties(List props) {
				System.out.println("Environment options changed:");

				for (Object o : props) {
					System.out.println(o);
				}
			}
		};

		options.addEnvironmentChangeListener(mEnvironmentOptionsListener);
	}
}
