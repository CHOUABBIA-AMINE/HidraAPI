/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : IncidentEvidenceType
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Enum
 * @Layer       : Domain
 * @Module      : incident
 * @Package     : dz.sh.hidra.modules.incident.domain.value
 *
 * @Description : Defines IncidentEvidenceType values.
 *
 */
package dz.sh.hidra.modules.incident.domain.value;

/**
 * Defines IncidentEvidenceType values.
 */
public enum IncidentEvidenceType {
    TELEMETRY_READING, MONITORING_EVALUATION, ALARM, LEAK_CASE, TOPOLOGY_ASSET, DOCUMENT, PHOTO, EXTERNAL_SYSTEM, OTHER
}
