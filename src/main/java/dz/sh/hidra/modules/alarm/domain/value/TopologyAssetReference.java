/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : TopologyAssetReference
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : alarm
 * @Package     : dz.sh.hidra.modules.alarm.domain.value
 *
 * @Description : Neutral topology asset reference for alarms.
 *
 */
package dz.sh.hidra.modules.alarm.domain.value;

/**
 * Neutral topology asset reference for alarms.
 *
 * @param topologyAssetTypeCode asset type code
 * @param topologyAssetId stable asset ID owned by Topology
 * @param topologyAssetCode asset code snapshot
 * @param topologyAssetNameSnapshot optional asset name snapshot
 */
public record TopologyAssetReference(
        String topologyAssetTypeCode,
        String topologyAssetId,
        String topologyAssetCode,
        String topologyAssetNameSnapshot
) {
}
