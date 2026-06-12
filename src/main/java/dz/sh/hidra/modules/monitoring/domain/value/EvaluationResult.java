/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : EvaluationResult
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Enum
 * @Layer       : Domain
 * @Module      : monitoring
 * @Package     : dz.sh.hidra.modules.monitoring.domain.value
 *
 * @Description : Defines EvaluationResult values.
 *
 */
package dz.sh.hidra.modules.monitoring.domain.value;

/**
 * Defines EvaluationResult values.
 */
public enum EvaluationResult {
    NORMAL, WARNING, DEVIATION, CRITICAL, NO_DATA, BAD_QUALITY
}
