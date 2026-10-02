/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : RecommendationStatus
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Enum
 * @Layer       : Domain
 * @Module      : integrity
 * @Package     : dz.sh.hidra.modules.integrity.domain.value
 *
 * @Description : Defines RecommendationStatus values.
 *
 */
package dz.sh.hidra.modules.integrity.domain.value;

/**
 * Defines RecommendationStatus values.
 */
public enum RecommendationStatus {
    PROPOSED, APPROVED, REJECTED, IN_PROGRESS, COMPLETED, SUPERSEDED, CANCELLED
}
