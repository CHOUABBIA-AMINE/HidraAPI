/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : AlarmClosureType
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Enum
 * @Layer       : Domain
 * @Module      : alarm
 * @Package     : dz.sh.hidra.modules.alarm.domain.value
 *
 * @Description : Defines AlarmClosureType values.
 *
 */
package dz.sh.hidra.modules.alarm.domain.value;

/**
 * Defines AlarmClosureType values.
 */
public enum AlarmClosureType {
    NORMALIZED, FALSE_ALARM, DUPLICATE, MAINTENANCE, CANCELLED, ESCALATED_TO_INCIDENT
}
