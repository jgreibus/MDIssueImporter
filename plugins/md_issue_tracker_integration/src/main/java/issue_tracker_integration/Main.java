package issue_tracker_integration;

import com.nomagic.magicdraw.core.Application;
import com.nomagic.magicdraw.core.options.EnvironmentOptions;
import com.nomagic.magicdraw.plugins.Plugin;

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
	}

	@Override
	public boolean close() {
		return true;
	}

	@Override
	public boolean isSupported() {
		return true;
	}

	/**
	 * Configures example environment options.
	 */
	private void configureEnvironmentOptions() {
		Application application = Application.getInstance();
		EnvironmentOptions options = application.getEnvironmentOptions();
		options.addGroup(new IntegrationOptions());

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
