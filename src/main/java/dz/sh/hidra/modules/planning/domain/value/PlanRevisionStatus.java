/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : PlanRevisionStatus
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Enum
 * @Layer       : Domain
 * @Module      : planning
 * @Package     : dz.sh.hidra.modules.planning.domain.value
 *
 * @Description : Defines PlanRevisionStatus values.
 *
 */
package dz.sh.hidra.modules.planning.domain.value;

/**
 * Defines PlanRevisionStatus values.
 */
public enum PlanRevisionStatus {
    DRAFT, SUBMITTED, APPROVED, REJECTED, SUPERSEDED, WITHDRAWN
}
