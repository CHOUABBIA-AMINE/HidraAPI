/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : ReportingPersistence
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : reporting
 * @Package     : dz.sh.hidra.modules.reporting.infrastructure.persistence
 *
 * @Description : Reporting database table constants.
 *
 */
package dz.sh.hidra.modules.reporting.infrastructure.persistence;

/**
 * Reporting database table constants.
 */
public final class ReportingPersistence {

    public static final String REPORT_DEFINITION_TABLE = "hidra_reporting_report_definition";
    public static final String REPORT_TEMPLATE_TABLE = "hidra_reporting_report_template";
    public static final String REPORT_TEMPLATE_VERSION_TABLE = "hidra_reporting_report_template_version";
    public static final String REPORT_PARAMETER_DEFINITION_TABLE = "hidra_reporting_parameter_definition";
    public static final String REPORT_REQUEST_TABLE = "hidra_reporting_request";
    public static final String REPORT_PARAMETER_VALUE_TABLE = "hidra_reporting_parameter_value";
    public static final String REPORT_DATA_SOURCE_BINDING_TABLE = "hidra_reporting_data_source_binding";
    public static final String REPORT_INPUT_SNAPSHOT_TABLE = "hidra_reporting_input_snapshot";
    public static final String REPORT_RUN_TABLE = "hidra_reporting_run";
    public static final String REPORT_SECTION_DEFINITION_TABLE = "hidra_reporting_section_definition";
    public static final String REPORT_SECTION_RESULT_TABLE = "hidra_reporting_section_result";
    public static final String REPORT_TABLE_RESULT_TABLE = "hidra_reporting_table_result";
    public static final String REPORT_CHART_RESULT_TABLE = "hidra_reporting_chart_result";
    public static final String REPORT_OUTPUT_ARTIFACT_TABLE = "hidra_reporting_output_artifact";
    public static final String REPORT_SCHEDULE_TABLE = "hidra_reporting_schedule";
    public static final String REPORT_SCHEDULE_PARAMETER_TABLE = "hidra_reporting_schedule_parameter";
    public static final String REPORT_PUBLICATION_TABLE = "hidra_reporting_publication";
    public static final String REPORT_DISTRIBUTION_TARGET_TABLE = "hidra_reporting_distribution_target";
    public static final String REPORT_DISTRIBUTION_RECORD_TABLE = "hidra_reporting_distribution_record";
    public static final String REPORT_ACCESS_POLICY_TABLE = "hidra_reporting_access_policy";
    public static final String REPORT_CATALOG_ENTRY_TABLE = "hidra_reporting_catalog_entry";
    public static final String REPORT_CATALOG_TRANSLATION_TABLE = "hidra_reporting_catalog_translation";

    private ReportingPersistence() {
        throw new UnsupportedOperationException("Utility class must not be instantiated.");
    }
}
