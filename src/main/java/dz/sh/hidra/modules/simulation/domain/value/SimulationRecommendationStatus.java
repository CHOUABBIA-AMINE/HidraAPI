/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : SimulationRecommendationStatus
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Enum
 * @Layer       : Domain
 * @Module      : simulation
 * @Package     : dz.sh.hidra.modules.simulation.domain.value
 *
 * @Description : Defines SimulationRecommendationStatus values.
 *
 */
package dz.sh.hidra.modules.simulation.domain.value;

/**
 * Defines SimulationRecommendationStatus values.
 */
public enum SimulationRecommendationStatus {
    DRAFT, PUBLISHED, SENT_TO_WORKFLOW, ACCEPTED, REJECTED, SUPERSEDED
}
