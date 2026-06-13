/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : TopologyMapController
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-13
 *
 * @Type        : Class
 * @Layer       : API
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.api.rest.controller
 *
 * @Description : Exposes topology geometry, layer, and GeoJSON APIs for enterprise map visualization.
 *
 */
package dz.sh.hidra.modules.topology.api.rest.controller;

import dz.sh.hidra.modules.topology.application.port.in.TopologyMapVisualizationUseCase;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Exposes topology geometry, layer, and GeoJSON APIs for enterprise map visualization.
 */
@RestController
@Validated
@RequestMapping("/api/v1/topology/map")
public class TopologyMapController {

    private final TopologyMapVisualizationUseCase visualizationUseCase;

    public TopologyMapController(TopologyMapVisualizationUseCase visualizationUseCase) {
        this.visualizationUseCase = Objects.requireNonNull(visualizationUseCase, "TopologyMapVisualizationUseCase must not be null.");
    }

    @GetMapping("/layers")
    public List<Map<String, Object>> layers() {
        return visualizationUseCase.listLayers();
    }

    @GetMapping("/layers/{layerId}")
    public Map<String, Object> layer(@PathVariable String layerId) {
        return visualizationUseCase.layer(layerId);
    }

    @GetMapping("/layers/{layerId}/features")
    public Map<String, Object> features(
            @PathVariable String layerId,
            @RequestParam(name = "page", required = false) Integer page,
            @RequestParam(name = "size", required = false) Integer size,
            @RequestParam(name = "q", required = false) String query
    ) {
        return visualizationUseCase.features(layerId, page, size, query);
    }

    @GetMapping("/geojson")
    public Map<String, Object> geoJson(
            @RequestParam(name = "layers", required = false) String layerIds,
            @RequestParam(name = "page", required = false) Integer page,
            @RequestParam(name = "size", required = false) Integer size,
            @RequestParam(name = "q", required = false) String query
    ) {
        return visualizationUseCase.geoJson(layerIds, page, size, query);
    }

    @GetMapping("/search")
    public Map<String, Object> search(
            @RequestParam(name = "q") String query,
            @RequestParam(name = "page", required = false) Integer page,
            @RequestParam(name = "size", required = false) Integer size
    ) {
        return visualizationUseCase.search(query, page, size);
    }
}
