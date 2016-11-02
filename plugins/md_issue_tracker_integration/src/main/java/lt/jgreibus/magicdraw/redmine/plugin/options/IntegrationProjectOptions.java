package lt.jgreibus.magicdraw.redmine.plugin.options;

import com.nomagic.magicdraw.core.options.ProjectOptions;
import com.nomagic.magicdraw.core.options.ProjectOptionsConfigurator;
import com.nomagic.magicdraw.properties.ElementProperty;
import com.nomagic.magicdraw.properties.Property;
import com.nomagic.magicdraw.properties.PropertyResourceProvider;
import com.nomagic.magicdraw.properties.StringProperty;
import com.nomagic.uml2.ext.magicdraw.mdprofiles.Stereotype;

import java.util.Collections;

public class IntegrationProjectOptions {

    public static void addProjectOptionsConfigurator() {
        ProjectOptions.addConfigurator(new ProjectOptionsConfigurator() {
            public void configure(ProjectOptions projectOptions) {
                com.nomagic.magicdraw.properties.Property project_property = projectOptions.getProperty(ProjectOptions.PROJECT_GENERAL_PROPERTIES, "PROJECT_ID");
                com.nomagic.magicdraw.properties.Property query_property = projectOptions.getProperty(ProjectOptions.PROJECT_GENERAL_PROPERTIES, "QUERY_ID");
                com.nomagic.magicdraw.properties.ElementProperty stereotype_property = new ElementProperty("STEREOTYPE_ID", null);
                if (project_property == null) {
                    project_property = new StringProperty("PROJECT_ID", "description");
                    project_property.setGroup("INTEGRATION_GROUP");
                    project_property.setResourceProvider(new PropertyResourceProvider() {
                        public String getString(String string, Property property) {
                            if ("PROJECT_ID".equals(string)) {
                                return "Project ID";
                            }
                            if ("PROJECT_ID_DESCRIPTION".equals(string)) {
                                return "Property used to define an ID of the project that should be integrated to the MagicDraw project";
                            }
                            if ("INTEGRATION_GROUP".equals(string)) {
                                return "Integration";
                            }
                            return string;
                        }
                    });
                    projectOptions.addProperty(ProjectOptions.PROJECT_GENERAL_PROPERTIES, project_property);
                }
                if (query_property == null) {
                    query_property = new StringProperty("QUERY_ID", null);
                    query_property.setGroup("INTEGRATION_GROUP");
                    query_property.setResourceProvider(new PropertyResourceProvider() {
                        public String getString(String string, Property property) {

                            if ("QUERY_ID".equals(string)) {
                                return "Query ID";
                            }
                            if ("QUERY_ID_DESCRIPTION".equals(string)) {
                                return "Property used to define an ID of the project query that collects elements for import into MagicDraw project";
                            }
                            if ("INTEGRATION_GROUP".equals(string)) {
                                return "Integration";
                            }
                            return string;
                        }
                    });
                    projectOptions.addProperty(ProjectOptions.PROJECT_GENERAL_PROPERTIES, query_property);
                }
                stereotype_property.setSelectableTypes(Collections.singleton(Stereotype.class));
                stereotype_property.setGroup("INTEGRATION_GROUP");
                stereotype_property.setResourceProvider(new PropertyResourceProvider() {
                    public String getString(String string, Property property) {

                        if ("STEREOTYPE_ID".equals(string)) {
                            return "Applicable stereotype";
                        }
                        if ("STEREOTYPE_ID_DESCRIPTION".equals(string)) {
                            return "Select a stereotype that will be applied on the created during import Class elements";
                        }
                        if ("INTEGRATION_GROUP".equals(string)) {
                            return "Integration";
                        }
                        return string;
                    }
                });
                projectOptions.addProperty(ProjectOptions.PROJECT_GENERAL_PROPERTIES, stereotype_property);
            }

            @Override
            public void afterLoad(ProjectOptions projectOptions) {

            }
        });
    }
}
