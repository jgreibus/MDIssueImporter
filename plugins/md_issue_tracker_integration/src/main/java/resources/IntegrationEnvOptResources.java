package resources;

import com.nomagic.magicdraw.resources.ResourceManager;

public final class IntegrationEnvOptResources
{
    public static final String BUNDLE_NAME = "resources.IntegrationEnvOptResources";

    private IntegrationEnvOptResources()
    {
        // do nothing.
    }

    public static String getString(String key)
    {
        return ResourceManager.getStringFor(key, BUNDLE_NAME, IntegrationEnvOptResources.class.getClassLoader());
    }
}
