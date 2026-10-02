/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : AssessmentStatus
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Enum
 * @Layer       : Domain
 * @Module      : telemetry
 * @Package     : dz.sh.hidra.modules.telemetry.domain.value
 *
 * @Description : Defines AssessmentStatus values.
 *
 */
package dz.sh.hidra.modules.telemetry.domain.value;

/**
 * Defines AssessmentStatus values.
 */
public enum AssessmentStatus {
    PASSED, FAILED, WARNING, DUPLICATE, QUARANTINED, MANUAL_REVIEW
}
