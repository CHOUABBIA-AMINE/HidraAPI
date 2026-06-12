/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : MonitoringPersistence
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : monitoring
 * @Package     : dz.sh.hidra.modules.monitoring.infrastructure.persistence
 *
 * @Description : Monitoring database table constants.
 *
 */
package dz.sh.hidra.modules.monitoring.infrastructure.persistence;

/**
 * Monitoring database table constants.
 */
public final class MonitoringPersistence {

    public static final String MONITORING_RULE_TABLE = "hidra_monitoring_rule";
    public static final String MONITORING_THRESHOLD_TABLE = "hidra_monitoring_threshold";
    public static final String MONITORING_EVALUATION_TABLE = "hidra_monitoring_evaluation";
    public static final String OPERATIONAL_STATE_TABLE = "hidra_monitoring_operational_state";
    public static final String OPERATIONAL_STATE_SNAPSHOT_TABLE = "hidra_monitoring_operational_state_snapshot";
    public static final String PLAN_ACTUAL_DEVIATION_TABLE = "hidra_monitoring_plan_actual_deviation";
    public static final String MONITORING_ALERT_CANDIDATE_TABLE = "hidra_monitoring_alert_candidate";
    public static final String MONITORING_ACKNOWLEDGEMENT_TABLE = "hidra_monitoring_acknowledgement";
    public static final String RISK_SIGNAL_TABLE = "hidra_monitoring_risk_signal";
    public static final String MONITORING_CATALOG_ENTRY_TABLE = "hidra_monitoring_catalog_entry";
    public static final String MONITORING_CATALOG_TRANSLATION_TABLE = "hidra_monitoring_catalog_translation";

    private MonitoringPersistence() {
        throw new UnsupportedOperationException("Utility class must not be instantiated.");
    }
}
