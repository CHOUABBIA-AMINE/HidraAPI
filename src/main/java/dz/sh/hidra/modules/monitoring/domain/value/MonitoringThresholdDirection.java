/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : MonitoringThresholdDirection
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Enum
 * @Layer       : Domain
 * @Module      : monitoring
 * @Package     : dz.sh.hidra.modules.monitoring.domain.value
 *
 * @Description : Defines MonitoringThresholdDirection values.
 *
 */
package dz.sh.hidra.modules.monitoring.domain.value;

/**
 * Defines MonitoringThresholdDirection values.
 */
public enum MonitoringThresholdDirection {
    LOW, HIGH, BAND, CHANGE_RATE, EQUALS, NOT_EQUALS
}
