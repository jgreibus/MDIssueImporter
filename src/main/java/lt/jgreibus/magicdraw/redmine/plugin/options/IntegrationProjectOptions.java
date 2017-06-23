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

                Property project_property = projectOptions.getProperty(ProjectOptions.PROJECT_GENERAL_PROPERTIES, "PROJECT_ID");
                Property query_property = projectOptions.getProperty(ProjectOptions.PROJECT_GENERAL_PROPERTIES, "QUERY_ID");
                Property report_property = projectOptions.getProperty(ProjectOptions.PROJECT_GENERAL_PROPERTIES, "REPORT_URL");
                Property requirement_cf_property = projectOptions.getProperty(ProjectOptions.PROJECT_GENERAL_PROPERTIES, "REQ_CUSTOM_FIELD");
                Property testCase_cf_property = projectOptions.getProperty(ProjectOptions.PROJECT_GENERAL_PROPERTIES, "TC_CUSTOM_FIELD");
                Property report_tree_ID = projectOptions.getProperty(ProjectOptions.PROJECT_GENERAL_PROPERTIES, "REPORT_TREE_ID");
                ElementProperty stereotype_property = new ElementProperty("STEREOTYPE_ID", null);

                if (project_property == null) {
                    project_property = new StringProperty("PROJECT_ID", null);
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
                if (report_property == null) {
                    report_property = new StringProperty("REPORT_URL", null);
                    report_property.setGroup("INTEGRATION_GROUP");
                    report_property.setResourceProvider(new PropertyResourceProvider() {
                        public String getString(String string, Property property) {

                            if ("REPORT_URL".equals(string)) {
                                return "Report URL";
                            }
                            if ("REPORT_URL_DESCRIPTION".equals(string)) {
                                return "Property used to specify Requirement Web Report URL address";
                            }
                            if ("INTEGRATION_GROUP".equals(string)) {
                                return "Integration";
                            }
                            return string;
                        }
                    });
                    projectOptions.addProperty(ProjectOptions.PROJECT_GENERAL_PROPERTIES, report_property);
                }
                if (requirement_cf_property == null){
                    requirement_cf_property = new StringProperty("REQ_CUSTOM_FIELD", null);
                    requirement_cf_property.setGroup("INTEGRATION_GROUP");
                    requirement_cf_property.setResourceProvider(new PropertyResourceProvider() {
                        @Override
                        public String getString(String string, Property property) {

                            if ("REQ_CUSTOM_FIELD".equals(string)) {
                                return "Requirement Custom Field";
                            }
                            if ("REQ_CUSTOM_FIELD_DESCRIPTION".equals(string)) {
                                return "Property used to specify name of Redmine custom field which is used for storing link to requirement specification";
                            }
                            if ("INTEGRATION_GROUP".equals(string)) {
                                return "Integration";
                            }
                            return string;
                        }
                    });
                    projectOptions.addProperty(ProjectOptions.PROJECT_GENERAL_PROPERTIES, requirement_cf_property);
                }
                if (testCase_cf_property == null){
                    testCase_cf_property = new StringProperty("TC_CUSTOM_FIELD", null);
                    testCase_cf_property.setGroup("INTEGRATION_GROUP");
                    testCase_cf_property.setResourceProvider(new PropertyResourceProvider() {
                        @Override
                        public String getString(String string, Property property) {

                            if ("TC_CUSTOM_FIELD".equals(string)) {
                                return "Test Case Custom Field";
                            }
                            if ("TC_CUSTOM_FIELD_DESCRIPTION".equals(string)) {
                                return "Property used to specify name of Redmine custom field which is used for storing information about related test cases";
                            }
                            if ("INTEGRATION_GROUP".equals(string)) {
                                return "Integration";
                            }
                            return string;
                        }
                    });
                    projectOptions.addProperty(ProjectOptions.PROJECT_GENERAL_PROPERTIES, testCase_cf_property);
                }
                if (report_tree_ID == null) {
                    report_tree_ID = new StringProperty("REPORT_TREE_ID", null);
                    report_tree_ID.setGroup("INTEGRATION_GROUP");
                    report_tree_ID.setResourceProvider(new PropertyResourceProvider() {
                        @Override
                        public String getString(String string, Property property) {

                            if ("REPORT_TREE_ID".equals(string)) {
                                return "Report Tree Node";
                            }
                            if ("REPORT_TREE_ID_DESCRIPTION".equals(string)) {
                                return "ID of the diagram viewpoint from the template project";
                            }
                            if ("INTEGRATION_GROUP".equals(string)) {
                                return "Integration";
                            }
                            return string;
                        }
                    });
                    projectOptions.addProperty(ProjectOptions.PROJECT_GENERAL_PROPERTIES, report_tree_ID);
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
