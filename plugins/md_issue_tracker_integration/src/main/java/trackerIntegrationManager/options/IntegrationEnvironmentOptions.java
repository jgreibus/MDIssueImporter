package trackerIntegrationManager.options;

import com.nomagic.magicdraw.core.options.AbstractPropertyOptionsGroup;
import com.nomagic.magicdraw.core.options.EnvironmentOptions;
import com.nomagic.magicdraw.icons.IconsFactory;
import com.nomagic.magicdraw.properties.ChoiceProperty;
import com.nomagic.magicdraw.properties.Property;
import com.nomagic.magicdraw.properties.PropertyResourceProvider;
import com.nomagic.magicdraw.properties.StringProperty;
import com.nomagic.uml2.ext.magicdraw.mdprofiles.Stereotype;
import resources.IntegrationEnvOptResources;

import javax.swing.*;
import java.util.Arrays;
import java.util.List;


public class IntegrationEnvironmentOptions extends AbstractPropertyOptionsGroup
{

    public static final String ID = "options.integration";
    public static final String SELECTION_GROUP = "INTEGRATION_SELECTION_GROUP";
    public static final String INTEGRATION_OPTIONS_GROUP = "INTEGRATION_OPTIONS_GROUP";
    public static final String INTEGRATION_ID = "INTEGRATION_ID";
    private static final Icon ICON = IconsFactory.getIcon("icon.png");

    private static String TRACKER_1 = "Redmine";
    private static String TRACKER_2 = "JIRA";

    public static final List TRACKER_VALUES = Arrays.asList(TRACKER_1, TRACKER_2);

    private static final String OPTION_GROUP_NAME = "OPTION_GROUP_NAME";

    public static final String USER_API_KEY_ID = "USER_API_KEY_ID";
    public static final String TRACKER_URL_ID = "TRACKER_URL_ID";
    // public static final Stereotype PROBLEM_STEREOTYPE = "STEREOTYPE_FOR_PROBLEM_ELEMENT";

    public final String userAPIKey = getUserAPIKeyValue();

    public static final PropertyResourceProvider PROPERTY_RESOURCE_PROVIDER = new PropertyResourceProvider()
    {
        @Override
        public String getString(String key, Property property)
        {
            return IntegrationEnvOptResources.getString(key);
        }
    };


    public IntegrationEnvironmentOptions()
    {
        super(ID);
    }

    @Override
    public void setDefaultValues()
    {
        setTrackerSelectionProperty(TRACKER_1);
        setUserApiKey("");
        setTrackerURL("");
    }

    public void setTrackerSelectionProperty(String value)
    {
        ChoiceProperty property = new ChoiceProperty(INTEGRATION_ID, value, TRACKER_VALUES);
        property.setValuesTranslatable(false);
        property.setResourceProvider(PROPERTY_RESOURCE_PROVIDER);
        property.setGroup(SELECTION_GROUP);

        addProperty(property, true);
    }

    public String getTrackerSelectionPropertyValue()
    {
        Property p = getProperty(INTEGRATION_ID);
        return (String) p.getValue();
    }

    public void setUserApiKey(String value){
        StringProperty user_api_key_property = new StringProperty(USER_API_KEY_ID, value);
        user_api_key_property.setResourceProvider(PROPERTY_RESOURCE_PROVIDER);
        user_api_key_property.setGroup(INTEGRATION_OPTIONS_GROUP);

        addProperty(user_api_key_property);
    }

    public String getUserAPIKeyValue() {
        Property p = getProperty(USER_API_KEY_ID);
        return (String) p.getValue();
    }

    public void setTrackerURL(String value){
        StringProperty tracker_URL_property = new StringProperty(TRACKER_URL_ID, value);
        tracker_URL_property.setResourceProvider(PROPERTY_RESOURCE_PROVIDER);
        tracker_URL_property.setGroup(INTEGRATION_OPTIONS_GROUP);

        addProperty(tracker_URL_property);
    }

    public String getTrackerUrlId() {
        Property property = getProperty(TRACKER_URL_ID);
        return (String) property.getValue();
    }

    public void setProblemStereotype(Stereotype e) {

    }

    @Override
    public String getName()
    {

        return IntegrationEnvOptResources.getString(OPTION_GROUP_NAME);
    }

    @Override
    public javax.swing.Icon getGroupIcon()
    {

        return ICON;
    }
}