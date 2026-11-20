/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : RegionalTopologyMapUseCase
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-11-20
 *
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.application.port.in
 *
 * @Description : Defines authorized regional map discovery requests.
 *
 */
package dz.sh.hidra.modules.topology.application.port.in;

import java.util.List;
import dz.sh.hidra.modules.topology.application.port.in.TopologyMapVisualizationUseCase.Feature;
import dz.sh.hidra.modules.topology.application.port.in.TopologyMapVisualizationUseCase.FeatureCollection;
import dz.sh.hidra.modules.topology.application.port.in.TopologyMapVisualizationUseCase.LayerDescriptor;
import dz.sh.hidra.modules.topology.application.port.in.TopologyMapVisualizationUseCase.SearchResult;

/** Separate from the unscoped, administrator-only map contract. */
public interface RegionalTopologyMapUseCase {
    List<LayerDescriptor> layers(String companyPartyId);
    LayerDescriptor layer(String companyPartyId, String layerId);
    FeatureCollection features(String companyPartyId, String layerId, Integer page, Integer size, String query);
    Feature feature(String companyPartyId, String layerId, String entityId);
    FeatureCollection geoJson(String companyPartyId, String layers, Integer page, Integer size, String query);
    SearchResult search(String companyPartyId, String query, Integer page, Integer size);
}
