/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : RiskAssessmentStatus
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Enum
 * @Layer       : Domain
 * @Module      : risk
 * @Package     : dz.sh.hidra.modules.risk.domain.value
 *
 * @Description : Defines RiskAssessmentStatus values.
 *
 */
package dz.sh.hidra.modules.risk.domain.value;

/**
 * Defines RiskAssessmentStatus values.
 */
public enum RiskAssessmentStatus {
    DRAFT, UNDER_REVIEW, APPROVED, ACTIVE, SUPERSEDED, RETIRED, REJECTED, CANCELLED, EXPIRED
}
