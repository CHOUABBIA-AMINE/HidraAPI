/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : IntegrationPersistence
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : integration
 * @Package     : dz.sh.hidra.modules.integration.infrastructure.persistence
 *
 * @Description : Integration database table constants.
 *
 */
package dz.sh.hidra.modules.integration.infrastructure.persistence;

/**
 * Integration database table constants.
 */
public final class IntegrationPersistence {

    public static final String EXTERNAL_SYSTEM_TABLE = "hidra_integration_external_system";
    public static final String EXTERNAL_ENDPOINT_TABLE = "hidra_integration_external_endpoint";
    public static final String CONNECTOR_INSTANCE_TABLE = "hidra_integration_connector_instance";
    public static final String INTEGRATION_DATA_CONTRACT_TABLE = "hidra_integration_data_contract";
    public static final String INTEGRATION_SCHEMA_VERSION_TABLE = "hidra_integration_schema_version";
    public static final String INTEGRATION_MAPPING_PROFILE_TABLE = "hidra_integration_mapping_profile";
    public static final String INTEGRATION_FIELD_MAPPING_TABLE = "hidra_integration_field_mapping";
    public static final String INTEGRATION_TRANSFORMATION_RULE_TABLE = "hidra_integration_transformation_rule";
    public static final String EXTERNAL_OBJECT_REFERENCE_TABLE = "hidra_integration_external_object_reference";
    public static final String INTEGRATION_JOB_DEFINITION_TABLE = "hidra_integration_job_definition";
    public static final String INTEGRATION_JOB_RUN_TABLE = "hidra_integration_job_run";
    public static final String INTEGRATION_JOB_RUN_STEP_TABLE = "hidra_integration_job_run_step";
    public static final String INTEGRATION_EXCHANGE_MESSAGE_TABLE = "hidra_integration_exchange_message";
    public static final String INTEGRATION_INBOUND_RECORD_TABLE = "hidra_integration_inbound_record";
    public static final String INTEGRATION_OUTBOUND_RECORD_TABLE = "hidra_integration_outbound_record";
    public static final String INTEGRATION_RETRY_POLICY_TABLE = "hidra_integration_retry_policy";
    public static final String INTEGRATION_RETRY_ATTEMPT_TABLE = "hidra_integration_retry_attempt";
    public static final String INTEGRATION_DEAD_LETTER_RECORD_TABLE = "hidra_integration_dead_letter_record";
    public static final String INTEGRATION_SYNC_CURSOR_TABLE = "hidra_integration_sync_cursor";
    public static final String INTEGRATION_RECONCILIATION_RUN_TABLE = "hidra_integration_reconciliation_run";
    public static final String INTEGRATION_RECONCILIATION_ISSUE_TABLE = "hidra_integration_reconciliation_issue";
    public static final String INTEGRATION_HEALTH_SNAPSHOT_TABLE = "hidra_integration_health_snapshot";
    public static final String INTEGRATION_CATALOG_ENTRY_TABLE = "hidra_integration_catalog_entry";
    public static final String INTEGRATION_CATALOG_TRANSLATION_TABLE = "hidra_integration_catalog_translation";

    private IntegrationPersistence() {
        throw new UnsupportedOperationException("Utility class must not be instantiated.");
    }
}
