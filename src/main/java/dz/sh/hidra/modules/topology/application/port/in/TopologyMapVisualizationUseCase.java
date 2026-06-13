/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : TopologyMapVisualizationUseCase
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-13
 *
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.application.port.in
 *
 * @Description : Defines read-only topology map visualization queries for GIS clients.
 *
 */
package dz.sh.hidra.modules.topology.application.port.in;

import java.util.List;
import java.util.Map;

/**
 * Defines read-only topology map visualization queries for GIS clients.
 */
public interface TopologyMapVisualizationUseCase {

    List<Map<String, Object>> listLayers();

    Map<String, Object> layer(String layerId);

    Map<String, Object> features(String layerId, Integer page, Integer size, String query);

    Map<String, Object> geoJson(String layerIds, Integer page, Integer size, String query);

    Map<String, Object> search(String query, Integer page, Integer size);
}
