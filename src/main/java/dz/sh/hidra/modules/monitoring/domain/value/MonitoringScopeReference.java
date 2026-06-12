/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : MonitoringScopeReference
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : monitoring
 * @Package     : dz.sh.hidra.modules.monitoring.domain.value
 *
 * @Description : Neutral monitoring scope reference.
 *
 */
package dz.sh.hidra.modules.monitoring.domain.value;

/**
 * Neutral monitoring scope reference.
 *
 * @param topologyAssetType topology asset type
 * @param topologyAssetId topology asset identifier
 * @param topologyAssetCode topology asset code snapshot
 * @param topologyAssetNameSnapshot topology asset name snapshot
 * @param telemetryPointId optional telemetry point identifier
 * @param telemetryPointCodeSnapshot optional telemetry point code snapshot
 */
public record MonitoringScopeReference(
        String topologyAssetType,
        String topologyAssetId,
        String topologyAssetCode,
        String topologyAssetNameSnapshot,
        String telemetryPointId,
        String telemetryPointCodeSnapshot
) {
}
