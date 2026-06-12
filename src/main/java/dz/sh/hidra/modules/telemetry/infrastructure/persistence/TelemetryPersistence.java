/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : TelemetryPersistence
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Utility
 * @Layer       : Infrastructure
 * @Module      : telemetry
 * @Package     : dz.sh.hidra.modules.telemetry.infrastructure.persistence
 *
 * @Description : Telemetry database table constants.
 *
 */
package dz.sh.hidra.modules.telemetry.infrastructure.persistence;

/**
 * Telemetry database table constants.
 */
public final class TelemetryPersistence {

    public static final String TELEMETRY_SOURCE_TABLE = "hidra_telemetry_source";
    public static final String TELEMETRY_SOURCE_ENDPOINT_TABLE = "hidra_telemetry_source_endpoint";
    public static final String TELEMETRY_DEVICE_TABLE = "hidra_telemetry_device";
    public static final String TELEMETRY_POINT_TABLE = "hidra_telemetry_point";
    public static final String TELEMETRY_EXTERNAL_TAG_MAPPING_TABLE = "hidra_telemetry_external_tag_mapping";
    public static final String TELEMETRY_POINT_BINDING_TABLE = "hidra_telemetry_point_binding";
    public static final String TELEMETRY_INGESTION_BATCH_TABLE = "hidra_telemetry_ingestion_batch";
    public static final String TELEMETRY_READING_TABLE = "hidra_telemetry_reading";
    public static final String TELEMETRY_QUALITY_ASSESSMENT_TABLE = "hidra_telemetry_quality_assessment";
    public static final String TRUSTED_TELEMETRY_READING_TABLE = "hidra_telemetry_trusted_reading";
    public static final String TELEMETRY_QUARANTINE_RECORD_TABLE = "hidra_telemetry_quarantine_record";
    public static final String TELEMETRY_CATALOG_ENTRY_TABLE = "hidra_telemetry_type_catalog";
    public static final String TELEMETRY_CATALOG_TRANSLATION_TABLE = "hidra_telemetry_type_translation";
    public static final String TELEMETRY_UNIT_TABLE = "hidra_telemetry_unit";
    public static final String TELEMETRY_VALIDATION_RULE_TABLE = "hidra_telemetry_validation_rule";
    public static final String TELEMETRY_POINT_STATE_SNAPSHOT_TABLE = "hidra_telemetry_point_state_snapshot";

    private TelemetryPersistence() {
        throw new UnsupportedOperationException("Utility class must not be instantiated.");
    }
}
