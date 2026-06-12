/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : DeviationStatus
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Enum
 * @Layer       : Domain
 * @Module      : monitoring
 * @Package     : dz.sh.hidra.modules.monitoring.domain.value
 *
 * @Description : Defines DeviationStatus values.
 *
 */
package dz.sh.hidra.modules.monitoring.domain.value;

/**
 * Defines DeviationStatus values.
 */
public enum DeviationStatus {
    OPEN, ACKNOWLEDGED, RESOLVED, IGNORED, SUPERSEDED
}
