package resources;

import com.nomagic.magicdraw.resources.ResourceManager;

/**
 * Resource handler class.
 * This class is an interface to MagicDraw ResourceManager.
 */
public final class IntegrationEnvOptResources
{
    /**
     * Resource bundle name.
     */
    public static final String BUNDLE_NAME = "resources.IntegrationEnvOptResources";

    /**
     * Constructs this resource handler.
     */
    private IntegrationEnvOptResources()
    {
        // do nothing.
    }

    /**
     * Gets resource by key.
     *
     * @param key key by which to get the resource.
     * @return translated resource.
     */
    public static String getString(String key)
    {
        return ResourceManager.getStringFor(key, BUNDLE_NAME, IntegrationEnvOptResources.class.getClassLoader());
    }
}
