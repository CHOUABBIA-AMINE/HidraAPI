/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : TopologyAssetReferenceProjection
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : Infrastructure
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.infrastructure.projection
 *
 * @Description : Topology asset reference projection.
 *
 */
package dz.sh.hidra.modules.topology.infrastructure.projection;

public record TopologyAssetReferenceProjection(String topologyAssetTypeCode, String topologyAssetId, String topologyAssetCodeSnapshot, String topologyAssetNameSnapshot) { }
