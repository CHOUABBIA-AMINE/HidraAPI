/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : AlarmEscalationType
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Enum
 * @Layer       : Domain
 * @Module      : alarm
 * @Package     : dz.sh.hidra.modules.alarm.domain.value
 *
 * @Description : Defines AlarmEscalationType values.
 *
 */
package dz.sh.hidra.modules.alarm.domain.value;

/**
 * Defines AlarmEscalationType values.
 */
public enum AlarmEscalationType {
    ORGANIZATION, WORKFLOW, INCIDENT, SUPERVISOR, ON_CALL, MANUAL
}
