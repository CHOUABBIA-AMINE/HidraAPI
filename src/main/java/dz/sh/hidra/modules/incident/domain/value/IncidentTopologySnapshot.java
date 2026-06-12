/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : IncidentTopologySnapshot
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : incident
 * @Package     : dz.sh.hidra.modules.incident.domain.value
 *
 * @Description : Neutral topology snapshot stored by incident.
 *
 */
package dz.sh.hidra.modules.incident.domain.value;

/**
 * Neutral topology snapshot stored by incident without importing topology domain models.
 *
 * @param topologyAssetTypeCode topology asset type code
 * @param topologyAssetId topology asset identifier
 * @param topologyAssetCode topology asset code snapshot
 * @param topologyAssetNameSnapshot topology asset name snapshot
 */
public record IncidentTopologySnapshot(
        String topologyAssetTypeCode,
        String topologyAssetId,
        String topologyAssetCode,
        String topologyAssetNameSnapshot
) {
}
