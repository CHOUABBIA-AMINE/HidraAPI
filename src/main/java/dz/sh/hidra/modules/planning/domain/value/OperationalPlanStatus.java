/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : OperationalPlanStatus
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Enum
 * @Layer       : Domain
 * @Module      : planning
 * @Package     : dz.sh.hidra.modules.planning.domain.value
 *
 * @Description : Defines OperationalPlanStatus values.
 *
 */
package dz.sh.hidra.modules.planning.domain.value;

/**
 * Defines OperationalPlanStatus values.
 */
public enum OperationalPlanStatus {
    DRAFT, SUBMITTED, APPROVED, ACTIVE, SUPERSEDED, CANCELLED, CLOSED
}
