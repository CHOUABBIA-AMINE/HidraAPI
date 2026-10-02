/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : IntegrityAssessmentStatus
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Enum
 * @Layer       : Domain
 * @Module      : integrity
 * @Package     : dz.sh.hidra.modules.integrity.domain.value
 *
 * @Description : Defines IntegrityAssessmentStatus values.
 *
 */
package dz.sh.hidra.modules.integrity.domain.value;

/**
 * Defines IntegrityAssessmentStatus values.
 */
public enum IntegrityAssessmentStatus {
    DRAFT, IN_PROGRESS, UNDER_REVIEW, APPROVED, SUPERSEDED, CANCELLED, CLOSED
}
