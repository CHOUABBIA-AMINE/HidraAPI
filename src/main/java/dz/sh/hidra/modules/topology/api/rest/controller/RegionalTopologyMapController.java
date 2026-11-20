/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : RegionalTopologyMapController
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-11-20
 *
 * @Type        : Class
 * @Layer       : API
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.api.rest.controller
 *
 * @Description : Provides resource-filtered regional topology discovery without administrator map fallback.
 *
 */
package dz.sh.hidra.modules.topology.api.rest.controller;

import dz.sh.hidra.modules.topology.application.port.in.RegionalTopologyMapUseCase;
import dz.sh.hidra.modules.topology.application.port.in.TopologyMapVisualizationUseCase.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Validated
@Tag(name = "Regional Topology Map", description = "Current-authority regional Topology maps")
@RequestMapping("/api/v1/topology/regional-map")
public class RegionalTopologyMapController {
    private final RegionalTopologyMapUseCase queries;

    public RegionalTopologyMapController(RegionalTopologyMapUseCase queries) {
        this.queries = Objects.requireNonNull(queries);
    }

    @GetMapping("/layers")
    @Operation(summary = "List fixed regional topology layer descriptors")
    @ApiResponse(responseCode = "200", description = "Static descriptors, without resource inventory")
    public ResponseEntity<List<LayerDescriptor>> layers(
            @RequestParam("companyPartyId") String companyPartyId) {
        return protectedResponse(queries.layers(companyPartyId));
    }

    @GetMapping("/layers/{layerId}")
    @Operation(summary = "Read a fixed regional topology layer descriptor")
    @ApiResponse(responseCode = "404", description = "No such layer")
    public ResponseEntity<LayerDescriptor> layer(
            @RequestParam("companyPartyId") String companyPartyId,
            @PathVariable String layerId) {
        return protectedResponse(queries.layer(companyPartyId, layerId));
    }

    @GetMapping("/layers/{layerId}/features")
    @Operation(summary = "List resource-authorized regional features with post-scope counts")
    @ApiResponse(responseCode = "200", description = "Authorized features and exact bounded totals")
    public ResponseEntity<FeatureCollection> features(
            @RequestParam("companyPartyId") String companyPartyId,
            @PathVariable String layerId,
            @RequestParam(value = "page", required = false) Integer page,
            @RequestParam(value = "size", required = false) Integer size,
            @RequestParam(value = "q", required = false) String query) {
        return protectedResponse(queries.features(companyPartyId, layerId, page, size, query));
    }

    @GetMapping("/layers/{layerId}/features/{entityId}")
    @Operation(summary = "Read an exact, currently permitted regional feature")
    @ApiResponse(responseCode = "404", description = "Absent or unauthorized feature")
    public ResponseEntity<Feature> feature(
            @RequestParam("companyPartyId") String companyPartyId,
            @PathVariable String layerId,
            @PathVariable String entityId) {
        return protectedResponse(queries.feature(companyPartyId, layerId, entityId));
    }

    @GetMapping("/geojson")
    @Operation(summary = "Read deduplicated authorized regional GeoJSON layers")
    @ApiResponse(responseCode = "200", description = "Authorized cross-layer geometry")
    public ResponseEntity<FeatureCollection> geoJson(
            @RequestParam("companyPartyId") String companyPartyId,
            @RequestParam(value = "layers", required = false) String layerIds,
            @RequestParam(value = "page", required = false) Integer page,
            @RequestParam(value = "size", required = false) Integer size,
            @RequestParam(value = "q", required = false) String query) {
        return protectedResponse(queries.geoJson(companyPartyId, layerIds, page, size, query));
    }

    @GetMapping("/search")
    @Operation(summary = "Search only currently visible regional map resources")
    @ApiResponse(responseCode = "200", description = "Scoped, paged matching features")
    public ResponseEntity<SearchResult> search(
            @RequestParam("companyPartyId") String companyPartyId,
            @RequestParam("q") String query,
            @RequestParam(value = "page", required = false) Integer page,
            @RequestParam(value = "size", required = false) Integer size) {
        return protectedResponse(queries.search(companyPartyId, query, page, size));
    }

    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<ProblemDetail> missing(NoSuchElementException unavailable) {
        ProblemDetail body = ProblemDetail.forStatus(HttpStatus.NOT_FOUND);
        body.setDetail("Resource unavailable");
        return protectedResponse(HttpStatus.NOT_FOUND, body);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ProblemDetail> invalid(IllegalArgumentException invalid) {
        ProblemDetail body = ProblemDetail.forStatus(HttpStatus.BAD_REQUEST);
        body.setDetail("Invalid regional map request");
        return protectedResponse(HttpStatus.BAD_REQUEST, body);
    }

    private static <T> ResponseEntity<T> protectedResponse(T result) {
        return ResponseEntity.ok().cacheControl(CacheControl.noStore()).body(result);
    }

    private static <T> ResponseEntity<T> protectedResponse(HttpStatus code, T result) {
        return ResponseEntity.status(code).cacheControl(CacheControl.noStore()).body(result);
    }
}
