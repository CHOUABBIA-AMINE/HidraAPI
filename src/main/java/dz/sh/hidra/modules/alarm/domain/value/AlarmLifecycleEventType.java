/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : AlarmLifecycleEventType
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Enum
 * @Layer       : Domain
 * @Module      : alarm
 * @Package     : dz.sh.hidra.modules.alarm.domain.value
 *
 * @Description : Defines AlarmLifecycleEventType values.
 *
 */
package dz.sh.hidra.modules.alarm.domain.value;

/**
 * Defines AlarmLifecycleEventType values.
 */
public enum AlarmLifecycleEventType {
    RAISED, ACKNOWLEDGED, SHELVED, UNSHELVED, SUPPRESSED, UNSUPPRESSED, CLEARED, CLOSED, ESCALATED, COMMENTED, ASSIGNED, CANCELLED
}
