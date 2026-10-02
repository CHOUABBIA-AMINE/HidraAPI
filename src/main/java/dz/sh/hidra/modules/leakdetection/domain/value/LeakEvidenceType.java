/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : LeakEvidenceType
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Enum
 * @Layer       : Domain
 * @Module      : leakdetection
 * @Package     : dz.sh.hidra.modules.leakdetection.domain.value
 *
 * @Description : Defines LeakEvidenceType values.
 *
 */
package dz.sh.hidra.modules.leakdetection.domain.value;

/**
 * Defines LeakEvidenceType values.
 */
public enum LeakEvidenceType {
    TELEMETRY_READING, TRUSTED_TELEMETRY_READING, MONITORING_DEVIATION, ALARM, TOPOLOGY_ASSET, PLANNING_TARGET, DOCUMENT, MANUAL_OBSERVATION, EXTERNAL_REFERENCE
}
