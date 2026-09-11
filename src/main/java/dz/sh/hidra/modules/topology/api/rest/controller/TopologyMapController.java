/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : TopologyMapController
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-11
 *
 * @Type        : Class
 * @Layer       : API
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.api.rest.controller
 *
 * @Description : Exposes strongly typed topology geometry, layer, and GeoJSON APIs.
 *
 */
package dz.sh.hidra.modules.topology.api.rest.controller;

import dz.sh.hidra.modules.topology.application.port.in.TopologyMapVisualizationUseCase;
import dz.sh.hidra.modules.topology.application.port.in.TopologyMapVisualizationUseCase.FeatureCollection;
import dz.sh.hidra.modules.topology.application.port.in.TopologyMapVisualizationUseCase.LayerDescriptor;
import dz.sh.hidra.modules.topology.application.port.in.TopologyMapVisualizationUseCase.SearchResult;
import java.util.List;
import java.util.Objects;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Validated
@RequestMapping("/api/v1/topology/map")
public class TopologyMapController {

    private final TopologyMapVisualizationUseCase visualizationUseCase;

    public TopologyMapController(TopologyMapVisualizationUseCase visualizationUseCase) {
        this.visualizationUseCase = Objects.requireNonNull(visualizationUseCase, "TopologyMapVisualizationUseCase must not be null.");
    }

    @GetMapping("/layers")
    public List<LayerDescriptor> layers() { return visualizationUseCase.listLayers(); }

    @GetMapping("/layers/{layerId}")
    public LayerDescriptor layer(@PathVariable String layerId) { return visualizationUseCase.layer(layerId); }

    @GetMapping("/layers/{layerId}/features")
    public FeatureCollection features(@PathVariable String layerId,
            @RequestParam(name = "page", required = false) Integer page,
            @RequestParam(name = "size", required = false) Integer size,
            @RequestParam(name = "q", required = false) String query) {
        return visualizationUseCase.features(layerId, page, size, query);
    }

    @GetMapping("/geojson")
    public FeatureCollection geoJson(@RequestParam(name = "layers", required = false) String layerIds,
            @RequestParam(name = "page", required = false) Integer page,
            @RequestParam(name = "size", required = false) Integer size,
            @RequestParam(name = "q", required = false) String query) {
        return visualizationUseCase.geoJson(layerIds, page, size, query);
    }

    @GetMapping("/search")
    public SearchResult search(@RequestParam(name = "q") String query,
            @RequestParam(name = "page", required = false) Integer page,
            @RequestParam(name = "size", required = false) Integer size) {
        return visualizationUseCase.search(query, page, size);
    }
}
