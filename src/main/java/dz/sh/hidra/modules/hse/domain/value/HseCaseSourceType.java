/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : HseCaseSourceType
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Enum
 * @Layer       : Domain
 * @Module      : hse
 * @Package     : dz.sh.hidra.modules.hse.domain.value
 *
 * @Description : Defines HseCaseSourceType values.
 *
 */
package dz.sh.hidra.modules.hse.domain.value;

/**
 * Defines HseCaseSourceType values.
 */
public enum HseCaseSourceType {
    MANUAL, INCIDENT_REFERENCE, ALARM_REFERENCE, LEAK_CASE_REFERENCE, INSPECTION, OBSERVATION, PERMIT, ENVIRONMENTAL_EVENT, INTEGRATION
}
