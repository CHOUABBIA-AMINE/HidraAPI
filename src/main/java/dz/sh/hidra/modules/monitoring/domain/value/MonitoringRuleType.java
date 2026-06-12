/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : MonitoringRuleType
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Enum
 * @Layer       : Domain
 * @Module      : monitoring
 * @Package     : dz.sh.hidra.modules.monitoring.domain.value
 *
 * @Description : Defines MonitoringRuleType values.
 *
 */
package dz.sh.hidra.modules.monitoring.domain.value;

/**
 * Defines MonitoringRuleType values.
 */
public enum MonitoringRuleType {
    THRESHOLD, PLAN_COMPARISON, STATE_CHANGE, STALE_DATA, QUALITY, CUSTOM
}
