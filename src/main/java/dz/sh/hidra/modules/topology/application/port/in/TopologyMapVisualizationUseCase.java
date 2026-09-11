/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : TopologyMapVisualizationUseCase
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-11
 *
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.application.port.in
 *
 * @Description : Defines strongly typed, read-only topology map visualization queries for GIS clients.
 *
 */
package dz.sh.hidra.modules.topology.application.port.in;

import java.math.BigDecimal;
import java.util.List;

public interface TopologyMapVisualizationUseCase {

    List<LayerDescriptor> listLayers();

    LayerDescriptor layer(String layerId);

    FeatureCollection features(String layerId, Integer page, Integer size, String query);

    FeatureCollection geoJson(String layerIds, Integer page, Integer size, String query);

    SearchResult search(String query, Integer page, Integer size);

    record LayerDescriptor(
            String id,
            String label,
            String geometryType,
            String description,
            String featuresEndpoint
    ) { }

    sealed interface Geometry permits PointGeometry, LineStringGeometry, MultiLineStringGeometry {
        String type();
    }

    record PointGeometry(String type, List<BigDecimal> coordinates) implements Geometry {
        public PointGeometry(List<BigDecimal> coordinates) { this("Point", coordinates); }
    }

    record LineStringGeometry(String type, List<List<BigDecimal>> coordinates) implements Geometry {
        public LineStringGeometry(List<List<BigDecimal>> coordinates) { this("LineString", coordinates); }
    }

    record MultiLineStringGeometry(String type, List<List<List<BigDecimal>>> coordinates) implements Geometry {
        public MultiLineStringGeometry(List<List<List<BigDecimal>>> coordinates) { this("MultiLineString", coordinates); }
    }

    record FeatureProperties(
            String layer,
            String entityType,
            String entityId,
            String code,
            String nameAr,
            String nameFr,
            String nameEn,
            String status,
            String facilityKind,
            String nodeType,
            String pipelineSystemId,
            String pipelineType,
            String fromNodeId,
            String toNodeId,
            String segmentType,
            String flowDirection,
            String connectionType
    ) { }

    record Feature(
            String type,
            String id,
            Geometry geometry,
            FeatureProperties properties
    ) {
        public Feature(String id, Geometry geometry, FeatureProperties properties) {
            this("Feature", id, geometry, properties);
        }
    }

    record FeatureCollection(
            String type,
            String name,
            String layer,
            int page,
            int size,
            long totalFeatures,
            int totalPages,
            boolean hasNext,
            List<String> layers,
            List<Feature> features
    ) {
        public FeatureCollection(
                String name,
                String layer,
                int page,
                int size,
                long totalFeatures,
                int totalPages,
                boolean hasNext,
                List<String> layers,
                List<Feature> features
        ) {
            this("FeatureCollection", name, layer, page, size, totalFeatures, totalPages, hasNext, layers, features);
        }
    }

    record SearchResult(
            String query,
            String type,
            int page,
            int size,
            long totalFeatures,
            int totalPages,
            boolean hasNext,
            List<Feature> features
    ) {
        public SearchResult(String query, int page, int size, long totalFeatures, int totalPages, boolean hasNext, List<Feature> features) {
            this(query, "FeatureCollection", page, size, totalFeatures, totalPages, hasNext, features);
        }
    }
}
