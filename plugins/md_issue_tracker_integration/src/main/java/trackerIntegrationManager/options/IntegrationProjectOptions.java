package trackerIntegrationManager.options;

import com.nomagic.magicdraw.core.options.ProjectOptions;
import com.nomagic.magicdraw.properties.Property;
import com.nomagic.magicdraw.properties.PropertyResourceProvider;
import com.nomagic.magicdraw.properties.StringProperty;

public class IntegrationProjectOptions {

//    ProjectOptions.addConfigurator(new ProjectOptionsConfigurator()
//    {
//        public void configure(ProjectOptions projectOptions)
//        {
//            com.nomagic.magicdraw.properties.Property property = projectOptions.getProperty(ProjectOptions.PROJECT_GENERAL_PROPERTIES, "TEST_PROPERTY_ID");
//            if (property == null)
//            {
//                // Create a property, if it does not exist
//                property = new StringProperty("TEST_PROPERTY_ID", "description");
//                // Group
//                property.setGroup("MY_GROUP");
//                // The custom resource provider
//                property.setResourceProvider(new PropertyResourceProvider()
//                {
//                    public String getString(String string, Property property)
//                    {
//                        if ("TEST_PROPERTY_ID".equals(string))
//                        {
//                            // Translate ID
//                            return "Test Property";
//                        }
//                        if ("TEST_PROPERTY_ID_DESCRIPTION".equals(string))
//                        {
//                            // Translate a description
//                            return "Test Property in My Group";
//                        }
//                        if ("MY_GROUP".equals(string))
//                        {
//                            // Translate a group
//                            return "My Group";
//                        }
//                        return string;
//                    }
//                });
//
//                // Add a property
//                projectOptions.addProperty(ProjectOptions.PROJECT_GENERAL_PROPERTIES, property);
//            }
//        }
//    }
}
