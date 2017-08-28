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
                Property requirement_cf_property = projectOptions.getProperty(ProjectOptions.PROJECT_GENERAL_PROPERTIES, "REQ_CUSTOM_FIELD");
                Property testCase_cf_property = projectOptions.getProperty(ProjectOptions.PROJECT_GENERAL_PROPERTIES, "TC_CUSTOM_FIELD");
                Property diagram_template_node_ID = projectOptions.getProperty(ProjectOptions.PROJECT_GENERAL_PROPERTIES, "DIAGRAM_TEMPLATE_NODE_ID");
                Property element_template_node_ID = projectOptions.getProperty(ProjectOptions.PROJECT_GENERAL_PROPERTIES, "ELEMENT_TEMPLATE_NODE_ID");
                ElementProperty stereotype_property = new ElementProperty("STEREOTYPE_ID", null);

                if (diagram_template_node_ID == null) {
                    diagram_template_node_ID = new StringProperty("DIAGRAM_TEMPLATE_NODE_ID", null);
                    diagram_template_node_ID.setGroup("INTEGRATION_GROUP");
                    diagram_template_node_ID.setResourceProvider(new PropertyResourceProvider() {
                        public String getString(String string, Property property) {
                            if ("DIAGRAM_TEMPLATE_NODE_ID".equals(string)) {
                                return "Diagram Template Node ID";
                            }
                            if ("DIAGRAM_TEMPLATE_NODE_ID_DESCRIPTION".equals(string)) {
                                return "Property is used to specify diagram viewpoint element ID from the used Collaborator template (should start with TREE_VIEW keyword)";
                            }
                            if ("INTEGRATION_GROUP".equals(string)) {
                                return "Integration";
                            }
                            return string;
                        }
                    });
                    projectOptions.addProperty(ProjectOptions.PROJECT_GENERAL_PROPERTIES, diagram_template_node_ID);
                }
                if (element_template_node_ID == null) {
                    element_template_node_ID = new StringProperty("ELEMENT_TEMPLATE_NODE_ID", null);
                    element_template_node_ID.setGroup("INTEGRATION_GROUP");
                    element_template_node_ID.setResourceProvider(new PropertyResourceProvider() {
                        public String getString(String string, Property property) {
                            if ("ELEMENT_TEMPLATE_NODE_ID".equals(string)) {
                                return "Element Template Node ID";
                            }
                            if ("ELEMENT_TEMPLATE_NODE_ID_DESCRIPTION".equals(string)) {
                                return "Property is used to specify element viewpoint element ID from the used Collaborator template (should start with TREE_VIEW keyword)";
                            }
                            if ("INTEGRATION_GROUP".equals(string)) {
                                return "Integration";
                            }
                            return string;
                        }
                    });
                    projectOptions.addProperty(ProjectOptions.PROJECT_GENERAL_PROPERTIES, element_template_node_ID);
                }
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
                if (requirement_cf_property == null){
                    requirement_cf_property = new StringProperty("REQ_CUSTOM_FIELD", null);
                    requirement_cf_property.setGroup("INTEGRATION_GROUP");
                    requirement_cf_property.setResourceProvider(new PropertyResourceProvider() {
                        @Override
                        public String getString(String string, Property property) {

                            if ("REQ_CUSTOM_FIELD".equals(string)) {
                                return "Requirement Link Custom Field";
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
