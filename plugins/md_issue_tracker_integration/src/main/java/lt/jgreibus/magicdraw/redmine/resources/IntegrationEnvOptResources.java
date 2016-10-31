package lt.jgreibus.magicdraw.redmine.resources;

import com.nomagic.magicdraw.resources.ResourceManager;

public final class IntegrationEnvOptResources {

    public static final String BUNDLE_NAME = "lt.jgreibus.magicdraw.redmine.IntegrationEnvOptResources";

    private IntegrationEnvOptResources() {
        // do nothing.
    }

    public static String getString(String key) {
        return ResourceManager.getStringFor(key, BUNDLE_NAME, IntegrationEnvOptResources.class.getClassLoader());
    }
}
