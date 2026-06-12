/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : AlarmEvidenceType
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Enum
 * @Layer       : Domain
 * @Module      : alarm
 * @Package     : dz.sh.hidra.modules.alarm.domain.value
 *
 * @Description : Defines AlarmEvidenceType values.
 *
 */
package dz.sh.hidra.modules.alarm.domain.value;

/**
 * Defines AlarmEvidenceType values.
 */
public enum AlarmEvidenceType {
    MONITORING_CANDIDATE, MONITORING_EVALUATION, TELEMETRY_READING, PLANNING_TARGET, TOPOLOGY_ASSET, DOCUMENT, WORKFLOW, INCIDENT, EXTERNAL_REFERENCE
}
