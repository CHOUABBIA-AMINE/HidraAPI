/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : TopologyBindingSnapshot
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : telemetry
 * @Package     : dz.sh.hidra.modules.telemetry.domain.value
 *
 * @Description : Snapshot of a topology binding stored in telemetry.
 *
 */
package dz.sh.hidra.modules.telemetry.domain.value;

/**
 * Snapshot of topology binding stored in telemetry without importing topology domain models.
 *
 * @param topologyAssetTypeCode topology asset type code
 * @param topologyAssetId topology asset identifier
 * @param topologyAssetCode topology asset code snapshot
 * @param topologyAssetNameSnapshot topology asset name snapshot
 * @param topologySnapshotId optional topology snapshot identifier
 */
public record TopologyBindingSnapshot(
        String topologyAssetTypeCode,
        String topologyAssetId,
        String topologyAssetCode,
        String topologyAssetNameSnapshot,
        String topologySnapshotId
) {
}
