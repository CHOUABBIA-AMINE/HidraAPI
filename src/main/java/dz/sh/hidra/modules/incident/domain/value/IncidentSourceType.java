/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : IncidentSourceType
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Enum
 * @Layer       : Domain
 * @Module      : incident
 * @Package     : dz.sh.hidra.modules.incident.domain.value
 *
 * @Description : Defines IncidentSourceType values.
 *
 */
package dz.sh.hidra.modules.incident.domain.value;

/**
 * Defines IncidentSourceType values.
 */
public enum IncidentSourceType {
    MANUAL, MONITORING_ALERT, ALARM, LEAK_CASE, HSE_EVENT, INTEGRATION, OTHER
}
