/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : RiskTreatmentStatus
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Enum
 * @Layer       : Domain
 * @Module      : risk
 * @Package     : dz.sh.hidra.modules.risk.domain.value
 *
 * @Description : Defines RiskTreatmentStatus values.
 *
 */
package dz.sh.hidra.modules.risk.domain.value;

/**
 * Defines RiskTreatmentStatus values.
 */
public enum RiskTreatmentStatus {
    PROPOSED, APPROVED, IN_PROGRESS, COMPLETED, VERIFIED, CLOSED, REJECTED, CANCELLED, OVERDUE, INEFFECTIVE
}
