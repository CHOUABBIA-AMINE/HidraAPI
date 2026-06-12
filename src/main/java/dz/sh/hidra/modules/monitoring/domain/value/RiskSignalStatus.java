/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : RiskSignalStatus
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Enum
 * @Layer       : Domain
 * @Module      : monitoring
 * @Package     : dz.sh.hidra.modules.monitoring.domain.value
 *
 * @Description : Defines RiskSignalStatus values.
 *
 */
package dz.sh.hidra.modules.monitoring.domain.value;

/**
 * Defines RiskSignalStatus values.
 */
public enum RiskSignalStatus {
    OPEN, ACCEPTED, MITIGATED, DISMISSED, EXPIRED
}
