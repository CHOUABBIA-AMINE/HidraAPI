/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : TopologyAssetReferenceDto
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : Application
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.application.dto
 *
 * @Description : Topology asset reference DTO.
 *
 */
package dz.sh.hidra.modules.topology.application.dto;

public record TopologyAssetReferenceDto(String topologyAssetTypeCode, String topologyAssetId, String topologyAssetCodeSnapshot, String topologyAssetNameSnapshot) { }
