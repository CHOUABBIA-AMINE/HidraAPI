/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : AnalyticsPersistence
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Utility
 * @Layer       : Infrastructure
 * @Module      : analytics
 * @Package     : dz.sh.hidra.modules.analytics.infrastructure.persistence
 *
 * @Description : Analytics database table constants.
 *
 */
package dz.sh.hidra.modules.analytics.infrastructure.persistence;

/**
 * Analytics database table constants.
 */
public final class AnalyticsPersistence {

    public static final String ANALYTICS_SUBJECT_AREA_TABLE = "hidra_analytics_subject_area";
    public static final String ANALYTICS_DATA_SOURCE_REFERENCE_TABLE = "hidra_analytics_data_source_reference";
    public static final String ANALYTICS_DATASET_TABLE = "hidra_analytics_dataset";
    public static final String ANALYTICS_DATASET_VERSION_TABLE = "hidra_analytics_dataset_version";
    public static final String ANALYTICS_DATASET_LINEAGE_TABLE = "hidra_analytics_dataset_lineage";
    public static final String ANALYTICS_PROJECTION_DEFINITION_TABLE = "hidra_analytics_projection_definition";
    public static final String ANALYTICS_PROJECTION_RUN_TABLE = "hidra_analytics_projection_run";
    public static final String ANALYTICS_PROJECTION_SNAPSHOT_TABLE = "hidra_analytics_projection_snapshot";
    public static final String METRIC_DEFINITION_TABLE = "hidra_analytics_metric_definition";
    public static final String METRIC_DEFINITION_VERSION_TABLE = "hidra_analytics_metric_definition_version";
    public static final String METRIC_EVALUATION_RUN_TABLE = "hidra_analytics_metric_evaluation_run";
    public static final String METRIC_VALUE_TABLE = "hidra_analytics_metric_value";
    public static final String KPI_DEFINITION_TABLE = "hidra_analytics_kpi_definition";
    public static final String KPI_BAND_TABLE = "hidra_analytics_kpi_band";
    public static final String KPI_EVALUATION_TABLE = "hidra_analytics_kpi_evaluation";
    public static final String TREND_ANALYSIS_TABLE = "hidra_analytics_trend_analysis";
    public static final String TREND_POINT_TABLE = "hidra_analytics_trend_point";
    public static final String ANALYTICS_MODEL_TABLE = "hidra_analytics_model";
    public static final String ANALYTICS_MODEL_VERSION_TABLE = "hidra_analytics_model_version";
    public static final String ANALYTICS_MODEL_RUN_TABLE = "hidra_analytics_model_run";
    public static final String ANALYTICS_FEATURE_SET_TABLE = "hidra_analytics_feature_set";
    public static final String ANALYTICS_FEATURE_VALUE_TABLE = "hidra_analytics_feature_value";
    public static final String ANALYTICS_INSIGHT_TABLE = "hidra_analytics_insight";
    public static final String ANALYTICS_INSIGHT_EVIDENCE_TABLE = "hidra_analytics_insight_evidence";
    public static final String DIGITAL_TWIN_READINESS_ASSESSMENT_TABLE = "hidra_analytics_digital_twin_readiness_assessment";
    public static final String ANALYTICS_ACCESS_POLICY_TABLE = "hidra_analytics_access_policy";
    public static final String ANALYTICS_CATALOG_ENTRY_TABLE = "hidra_analytics_catalog_entry";
    public static final String ANALYTICS_CATALOG_TRANSLATION_TABLE = "hidra_analytics_catalog_translation";

    private AnalyticsPersistence() {
        throw new UnsupportedOperationException("Utility class must not be instantiated.");
    }
}
