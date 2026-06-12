/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : LeakEscalationTargetType
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Enum
 * @Layer       : Domain
 * @Module      : leakdetection
 * @Package     : dz.sh.hidra.modules.leakdetection.domain.value
 *
 * @Description : Defines LeakEscalationTargetType values.
 *
 */
package dz.sh.hidra.modules.leakdetection.domain.value;

/**
 * Defines LeakEscalationTargetType values.
 */
public enum LeakEscalationTargetType {
    ALARM, INCIDENT, WORKFLOW, NOTIFICATION, AUDIT, MANUAL_REFERENCE
}
