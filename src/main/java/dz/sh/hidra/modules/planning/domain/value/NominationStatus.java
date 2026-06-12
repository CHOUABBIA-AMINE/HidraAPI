/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : NominationStatus
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Enum
 * @Layer       : Domain
 * @Module      : planning
 * @Package     : dz.sh.hidra.modules.planning.domain.value
 *
 * @Description : Defines NominationStatus values.
 *
 */
package dz.sh.hidra.modules.planning.domain.value;

/**
 * Defines NominationStatus values.
 */
public enum NominationStatus {
    DRAFT, CONFIRMED, ALLOCATED, REJECTED, CANCELLED, SUPERSEDED
}
