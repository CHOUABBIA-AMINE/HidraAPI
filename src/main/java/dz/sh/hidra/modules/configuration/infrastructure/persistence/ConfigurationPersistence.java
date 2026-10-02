/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : ConfigurationPersistence
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : configuration
 * @Package     : dz.sh.hidra.modules.configuration.infrastructure.persistence
 *
 * @Description : Configuration database table constants.
 *
 */
package dz.sh.hidra.modules.configuration.infrastructure.persistence;

/**
 * Configuration database table constants.
 */
public final class ConfigurationPersistence {

    public static final String CONFIGURATION_NAMESPACE_TABLE = "hidra_configuration_namespace";
    public static final String CONFIGURATION_DEFINITION_TABLE = "hidra_configuration_definition";
    public static final String CONFIGURATION_DEFINITION_VERSION_TABLE = "hidra_configuration_definition_version";
    public static final String CONFIGURATION_VALUE_TABLE = "hidra_configuration_value";
    public static final String SCOPED_CONFIGURATION_OVERRIDE_TABLE = "hidra_configuration_scoped_override";
    public static final String CONFIGURATION_PROFILE_TABLE = "hidra_configuration_profile";
    public static final String CONFIGURATION_PROFILE_ENTRY_TABLE = "hidra_configuration_profile_entry";
    public static final String FEATURE_FLAG_TABLE = "hidra_configuration_feature_flag";
    public static final String FEATURE_FLAG_RULE_TABLE = "hidra_configuration_feature_flag_rule";
    public static final String CONFIGURATION_VALIDATION_RULE_TABLE = "hidra_configuration_validation_rule";
    public static final String CONFIGURATION_CHANGE_REQUEST_TABLE = "hidra_configuration_change_request";
    public static final String CONFIGURATION_DEPLOYMENT_TABLE = "hidra_configuration_deployment";
    public static final String RESOLVED_CONFIGURATION_SNAPSHOT_TABLE = "hidra_configuration_resolved_snapshot";
    public static final String CONFIGURATION_EXTERNAL_REFERENCE_TABLE = "hidra_configuration_external_reference";
    public static final String CONFIGURATION_CATALOG_ENTRY_TABLE = "hidra_configuration_catalog_entry";
    public static final String CONFIGURATION_CATALOG_TRANSLATION_TABLE = "hidra_configuration_catalog_translation";

    private ConfigurationPersistence() {
        throw new UnsupportedOperationException("Utility class must not be instantiated.");
    }
}
