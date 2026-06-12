/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : SimulationTargetReference
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : simulation
 * @Package     : dz.sh.hidra.modules.simulation.domain.value
 *
 * @Description : Neutral simulated target reference.
 *
 */
package dz.sh.hidra.modules.simulation.domain.value;

/**
 * Neutral simulated target reference.
 *
 * @param targetType target type
 * @param targetId target identifier
 * @param targetCodeSnapshot target code snapshot
 * @param targetLabelSnapshot target label snapshot
 */
public record SimulationTargetReference(
        String targetType,
        String targetId,
        String targetCodeSnapshot,
        String targetLabelSnapshot
) {
}
