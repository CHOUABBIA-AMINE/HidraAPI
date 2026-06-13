/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : JpaTopologyMapVisualizationAdapter
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-13
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.infrastructure.visualization
 *
 * @Description : Builds GeoJSON map layers from topology JPA projections.
 *
 */
package dz.sh.hidra.modules.topology.infrastructure.visualization;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import dz.sh.hidra.modules.topology.application.port.in.TopologyMapVisualizationUseCase;
import dz.sh.hidra.modules.topology.infrastructure.persistence.entity.FacilityJpaEntity;
import dz.sh.hidra.modules.topology.infrastructure.persistence.entity.PipelineSegmentJpaEntity;
import dz.sh.hidra.modules.topology.infrastructure.persistence.entity.TopologyConnectionJpaEntity;
import dz.sh.hidra.modules.topology.infrastructure.persistence.entity.TopologyNodeJpaEntity;
import jakarta.persistence.EntityManager;

/**
 * Builds GeoJSON map layers from topology JPA projections.
 */
@Component
public class JpaTopologyMapVisualizationAdapter implements TopologyMapVisualizationUseCase {

    private static final int DEFAULT_PAGE = 0;
    private static final int DEFAULT_SIZE = 500;
    private static final int MAX_SIZE = 2_000;

    private final EntityManager entityManager;

    public JpaTopologyMapVisualizationAdapter(EntityManager entityManager) {
        this.entityManager = Objects.requireNonNull(entityManager, "EntityManager must not be null.");
    }

    @Override
    public List<Map<String, Object>> listLayers() {
        return List.of(
                layerDescriptor("facilities", "Facility points", "Point", "Facilities, stations, terminals, depots, and operational sites."),
                layerDescriptor("topology-nodes", "Topology nodes", "Point", "Hydraulic/network nodes used to connect facilities, pipelines, and measurement locations."),
                layerDescriptor("pipeline-segments", "Pipeline segments", "LineString", "Pipeline segment links rendered from from-node and to-node coordinates when available."),
                layerDescriptor("topology-connections", "Topology connections", "LineString", "Logical topology connections rendered from node coordinates when available.")
        );
    }

    @Override
    public Map<String, Object> layer(String layerId) {
        return listLayers().stream()
                .filter(layer -> Objects.equals(layer.get("id"), normalizeLayer(layerId)))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Unsupported topology map layer: " + layerId + "."));
    }

    @Override
    @Transactional(readOnly = true)
    public Map<String, Object> features(String layerId, Integer page, Integer size, String query) {
        String normalizedLayer = normalizeLayer(layerId);
        int normalizedPage = page(page);
        int normalizedSize = size(size);
        List<Map<String, Object>> features = switch (normalizedLayer) {
            case "facilities" -> facilityFeatures(normalizedPage, normalizedSize, query);
            case "topology-nodes" -> nodeFeatures(normalizedPage, normalizedSize, query);
            case "pipeline-segments" -> pipelineSegmentFeatures(normalizedPage, normalizedSize, query);
            case "topology-connections" -> topologyConnectionFeatures(normalizedPage, normalizedSize, query);
            default -> throw new IllegalArgumentException("Unsupported topology map layer: " + layerId + ".");
        };
        return featureCollection(normalizedLayer, normalizedPage, normalizedSize, features);
    }

    @Override
    @Transactional(readOnly = true)
    public Map<String, Object> geoJson(String layerIds, Integer page, Integer size, String query) {
        List<String> layers = layerIds == null || layerIds.isBlank()
                ? List.of("facilities", "topology-nodes", "pipeline-segments", "topology-connections")
                : Arrays.stream(layerIds.split(",")).map(JpaTopologyMapVisualizationAdapter::normalizeLayer).filter(item -> !item.isBlank()).toList();
        List<Map<String, Object>> features = new ArrayList<>();
        for (String layer : layers) {
            Map<String, Object> collection = features(layer, page, size, query);
            Object layerFeatures = collection.get("features");
            if (layerFeatures instanceof List<?> featureList) {
                for (Object feature : featureList) {
                    if (feature instanceof Map<?, ?> mapFeature) {
                        features.add(stringObjectMap(mapFeature));
                    }
                }
            }
        }
        return Map.of(
                "type", "FeatureCollection",
                "name", "hidra-topology",
                "layers", layers,
                "features", features
        );
    }

    @Override
    @Transactional(readOnly = true)
    public Map<String, Object> search(String query, Integer page, Integer size) {
        String q = query == null ? "" : query.trim().toLowerCase(Locale.ROOT);
        if (q.isBlank()) {
            return Map.of("query", "", "features", List.of());
        }
        Map<String, Object> collection = geoJson("facilities,topology-nodes,pipeline-segments,topology-connections", page, size, q);
        return Map.of(
                "query", query,
                "type", "FeatureCollection",
                "features", collection.get("features")
        );
    }

    private List<Map<String, Object>> facilityFeatures(int page, int size, String query) {
        return entityManager.createQuery("select e from FacilityJpaEntity e", FacilityJpaEntity.class)
                .setFirstResult(page * size)
                .setMaxResults(size)
                .getResultList().stream()
                .filter(facility -> matches(query, facility.code(), facility.nameAr(), facility.nameFr(), facility.nameEn(), facility.status()))
                .filter(facility -> hasPoint(facility.latitude(), facility.longitude()))
                .map(facility -> pointFeature(
                        "facilities",
                        facility.id(),
                        facility.longitude(),
                        facility.latitude(),
                        facility.elevationMeters(),
                        Map.of(
                                "id", facility.id(),
                                "code", facility.code(),
                                "nameAr", nullable(facility.nameAr()),
                                "nameFr", nullable(facility.nameFr()),
                                "nameEn", nullable(facility.nameEn()),
                                "facilityKind", name(facility.facilityKind()),
                                "status", name(facility.status())
                        )
                ))
                .toList();
    }

    private List<Map<String, Object>> nodeFeatures(int page, int size, String query) {
        return entityManager.createQuery("select e from TopologyNodeJpaEntity e", TopologyNodeJpaEntity.class)
                .setFirstResult(page * size)
                .setMaxResults(size)
                .getResultList().stream()
                .filter(node -> matches(query, node.code(), node.name(), node.nodeType(), node.status()))
                .filter(node -> hasPoint(node.latitude(), node.longitude()))
                .map(node -> pointFeature(
                        "topology-nodes",
                        node.id(),
                        node.longitude(),
                        node.latitude(),
                        node.elevationMeters(),
                        Map.of(
                                "id", node.id(),
                                "code", node.code(),
                                "name", nullable(node.name()),
                                "nodeType", name(node.nodeType()),
                                "facilityId", nullable(node.facilityId()),
                                "status", name(node.status())
                        )
                ))
                .toList();
    }

    private List<Map<String, Object>> pipelineSegmentFeatures(int page, int size, String query) {
        Map<String, TopologyNodeJpaEntity> nodes = nodesById();
        return entityManager.createQuery("select e from PipelineSegmentJpaEntity e", PipelineSegmentJpaEntity.class)
                .setFirstResult(page * size)
                .setMaxResults(size)
                .getResultList().stream()
                .filter(segment -> matches(query, segment.code(), segment.segmentType(), segment.flowDirection(), segment.status()))
                .map(segment -> segmentFeature(segment, nodes))
                .flatMap(Optional::stream)
                .toList();
    }

    private List<Map<String, Object>> topologyConnectionFeatures(int page, int size, String query) {
        Map<String, TopologyNodeJpaEntity> nodes = nodesById();
        return entityManager.createQuery("select e from TopologyConnectionJpaEntity e", TopologyConnectionJpaEntity.class)
                .setFirstResult(page * size)
                .setMaxResults(size)
                .getResultList().stream()
                .filter(connection -> matches(query, connection.code(), connection.connectionType(), connection.status()))
                .map(connection -> connectionFeature(connection, nodes))
                .flatMap(Optional::stream)
                .toList();
    }

    private Optional<Map<String, Object>> segmentFeature(PipelineSegmentJpaEntity segment, Map<String, TopologyNodeJpaEntity> nodes) {
        TopologyNodeJpaEntity from = nodes.get(segment.fromNodeId());
        TopologyNodeJpaEntity to = nodes.get(segment.toNodeId());
        if (from == null || to == null || !hasPoint(from.latitude(), from.longitude()) || !hasPoint(to.latitude(), to.longitude())) {
            return Optional.empty();
        }
        return Optional.of(lineFeature(
                "pipeline-segments",
                segment.id(),
                coordinates(from),
                coordinates(to),
                Map.of(
                        "id", segment.id(),
                        "code", segment.code(),
                        "pipelineId", segment.pipelineId(),
                        "fromNodeId", segment.fromNodeId(),
                        "toNodeId", segment.toNodeId(),
                        "segmentType", name(segment.segmentType()),
                        "flowDirection", name(segment.flowDirection()),
                        "status", name(segment.status())
                )
        ));
    }

    private Optional<Map<String, Object>> connectionFeature(TopologyConnectionJpaEntity connection, Map<String, TopologyNodeJpaEntity> nodes) {
        TopologyNodeJpaEntity from = nodes.get(connection.fromNodeId());
        TopologyNodeJpaEntity to = nodes.get(connection.toNodeId());
        if (from == null || to == null || !hasPoint(from.latitude(), from.longitude()) || !hasPoint(to.latitude(), to.longitude())) {
            return Optional.empty();
        }
        return Optional.of(lineFeature(
                "topology-connections",
                connection.id(),
                coordinates(from),
                coordinates(to),
                Map.of(
                        "id", connection.id(),
                        "code", connection.code(),
                        "fromNodeId", connection.fromNodeId(),
                        "toNodeId", connection.toNodeId(),
                        "connectionType", name(connection.connectionType()),
                        "status", name(connection.status())
                )
        ));
    }

    private Map<String, TopologyNodeJpaEntity> nodesById() {
        Map<String, TopologyNodeJpaEntity> nodes = new LinkedHashMap<>();
        entityManager.createQuery("select e from TopologyNodeJpaEntity e", TopologyNodeJpaEntity.class)
                .getResultList()
                .forEach(node -> nodes.put(node.id(), node));
        return nodes;
    }

    private static Map<String, Object> layerDescriptor(String id, String label, String geometryType, String description) {
        return Map.of(
                "id", id,
                "label", label,
                "geometryType", geometryType,
                "description", description,
                "featuresEndpoint", "/api/v1/topology/map/layers/" + id + "/features"
        );
    }

    private static Map<String, Object> featureCollection(String layer, int page, int size, List<Map<String, Object>> features) {
        return Map.of(
                "type", "FeatureCollection",
                "layer", layer,
                "page", page,
                "size", size,
                "returnedFeatures", features.size(),
                "features", features
        );
    }

    private static Map<String, Object> stringObjectMap(Map<?, ?> source) {
        Map<String, Object> copy = new LinkedHashMap<>();
        for (Map.Entry<?, ?> entry : source.entrySet()) {
            if (entry.getKey() instanceof String key) {
                copy.put(key, entry.getValue());
            }
        }
        return copy;
    }

    private static Map<String, Object> pointFeature(
            String layer,
            String id,
            BigDecimal longitude,
            BigDecimal latitude,
            BigDecimal elevation,
            Map<String, Object> properties
    ) {
        return Map.of(
                "type", "Feature",
                "id", layer + ":" + id,
                "geometry", Map.of(
                        "type", "Point",
                        "coordinates", coordinate(longitude, latitude, elevation)
                ),
                "properties", properties
        );
    }

    private static Map<String, Object> lineFeature(
            String layer,
            String id,
            List<Object> from,
            List<Object> to,
            Map<String, Object> properties
    ) {
        return Map.of(
                "type", "Feature",
                "id", layer + ":" + id,
                "geometry", Map.of(
                        "type", "LineString",
                        "coordinates", List.of(from, to)
                ),
                "properties", properties
        );
    }

    private static List<Object> coordinates(TopologyNodeJpaEntity node) {
        return coordinate(node.longitude(), node.latitude(), node.elevationMeters());
    }

    private static List<Object> coordinate(BigDecimal longitude, BigDecimal latitude, BigDecimal elevation) {
        if (elevation == null) {
            return List.of(longitude, latitude);
        }
        return List.of(longitude, latitude, elevation);
    }

    private static boolean hasPoint(BigDecimal latitude, BigDecimal longitude) {
        return latitude != null && longitude != null;
    }

    private static boolean matches(String query, Object... values) {
        if (query == null || query.isBlank()) {
            return true;
        }
        String normalizedQuery = query.toLowerCase(Locale.ROOT);
        for (Object value : values) {
            if (value != null && value.toString().toLowerCase(Locale.ROOT).contains(normalizedQuery)) {
                return true;
            }
        }
        return false;
    }

    private static int page(Integer page) {
        return page == null || page < 0 ? DEFAULT_PAGE : page;
    }

    private static int size(Integer size) {
        if (size == null || size <= 0) {
            return DEFAULT_SIZE;
        }
        return Math.min(size, MAX_SIZE);
    }

    private static String normalizeLayer(String layerId) {
        return layerId == null ? "" : layerId.trim().toLowerCase(Locale.ROOT);
    }

    private static String name(Enum<?> value) {
        return value == null ? null : value.name();
    }

    private static Object nullable(Object value) {
        return value == null ? "" : value;
    }
}
