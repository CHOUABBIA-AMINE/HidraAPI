/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : ReportSnapshotType
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Enum
 * @Layer       : Domain
 * @Module      : reporting
 * @Package     : dz.sh.hidra.modules.reporting.domain.value
 *
 * @Description : Defines ReportSnapshotType values.
 *
 */
package dz.sh.hidra.modules.reporting.domain.value;

/**
 * Defines ReportSnapshotType values.
 */
public enum ReportSnapshotType {
    TOPOLOGY_SNAPSHOT, PLAN_VERSION, ANALYTICS_DATASET_VERSION, RISK_ASSESSMENT_VERSION, CUSTODY_PERIOD, TELEMETRY_TIME_RANGE, INCIDENT_FILTER, HSE_CASE_FILTER
}
