/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : SimulationTopologyReference
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : simulation
 * @Package     : dz.sh.hidra.modules.simulation.domain.value
 *
 * @Description : Neutral topology snapshot reference.
 *
 */
package dz.sh.hidra.modules.simulation.domain.value;

/**
 * Neutral topology snapshot reference.
 *
 * @param topologySnapshotId topology snapshot identifier
 * @param topologyScopeType topology scope type
 * @param topologyScopeId topology scope identifier
 * @param topologyVersionSnapshot topology version snapshot
 */
public record SimulationTopologyReference(
        String topologySnapshotId,
        String topologyScopeType,
        String topologyScopeId,
        String topologyVersionSnapshot
) {
}
