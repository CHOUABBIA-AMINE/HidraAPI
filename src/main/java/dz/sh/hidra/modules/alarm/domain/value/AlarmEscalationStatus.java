/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : AlarmEscalationStatus
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Enum
 * @Layer       : Domain
 * @Module      : alarm
 * @Package     : dz.sh.hidra.modules.alarm.domain.value
 *
 * @Description : Defines AlarmEscalationStatus values.
 *
 */
package dz.sh.hidra.modules.alarm.domain.value;

/**
 * Defines AlarmEscalationStatus values.
 */
public enum AlarmEscalationStatus {
    OPEN, ACCEPTED, REJECTED, CANCELLED, COMPLETED
}
