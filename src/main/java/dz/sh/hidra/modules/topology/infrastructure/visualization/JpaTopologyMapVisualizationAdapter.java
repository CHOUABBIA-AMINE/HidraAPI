/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : JpaTopologyMapVisualizationAdapter
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-11
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.infrastructure.visualization
 *
 * @Description : Builds typed and count-aware GeoJSON map projections from topology persistence.
 *
 */
package dz.sh.hidra.modules.topology.infrastructure.visualization;

import dz.sh.hidra.modules.topology.application.port.in.TopologyMapVisualizationUseCase;
import dz.sh.hidra.modules.topology.infrastructure.persistence.entity.FacilityJpaEntity;
import dz.sh.hidra.modules.topology.infrastructure.persistence.entity.PipelineJpaEntity;
import dz.sh.hidra.modules.topology.infrastructure.persistence.entity.PipelineSegmentJpaEntity;
import dz.sh.hidra.modules.topology.infrastructure.persistence.entity.PipelineSystemJpaEntity;
import dz.sh.hidra.modules.topology.infrastructure.persistence.entity.TopologyConnectionJpaEntity;
import dz.sh.hidra.modules.topology.infrastructure.persistence.entity.TopologyNodeJpaEntity;
import jakarta.persistence.EntityManager;
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

@Component
public class JpaTopologyMapVisualizationAdapter implements TopologyMapVisualizationUseCase {

    private static final int DEFAULT_PAGE = 0;
    private static final int DEFAULT_SIZE = 500;
    private static final int MAX_SIZE = 2_000;
    private static final List<String> DEFAULT_LAYERS = List.of(
            "pipeline-systems", "pipelines", "facilities", "topology-nodes", "pipeline-segments", "topology-connections"
    );

    private final EntityManager entityManager;

    public JpaTopologyMapVisualizationAdapter(EntityManager entityManager) {
        this.entityManager = Objects.requireNonNull(entityManager, "EntityManager must not be null.");
    }

    @Override
    public List<LayerDescriptor> listLayers() {
        return List.of(
                descriptor("pipeline-systems", "Pipeline systems", "MultiLineString", "Pipeline-system geometries derived from their pipelines and segments."),
                descriptor("pipelines", "Pipelines", "MultiLineString", "Pipeline geometries derived from connected pipeline segments."),
                descriptor("facilities", "Facility points", "Point", "Facilities, stations, terminals, depots, and operational sites."),
                descriptor("topology-nodes", "Topology nodes", "Point", "Hydraulic/network nodes used to connect facilities and pipelines."),
                descriptor("pipeline-segments", "Pipeline segments", "LineString", "Pipeline segment links rendered from connected topology-node coordinates."),
                descriptor("topology-connections", "Topology connections", "LineString", "Logical topology connections rendered from node coordinates.")
        );
    }

    @Override
    public LayerDescriptor layer(String layerId) {
        String normalized = normalizeLayer(layerId);
        return listLayers().stream().filter(item -> item.id().equals(normalized)).findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Unsupported topology map layer: " + layerId + "."));
    }

    @Override
    @Transactional(readOnly = true)
    public FeatureCollection features(String layerId, Integer page, Integer size, String query) {
        String normalized = normalizeLayer(layerId);
        layer(normalized);
        return collection(normalized, List.of(normalized), featuresFor(normalized, query), page(page), size(size));
    }

    @Override
    @Transactional(readOnly = true)
    public FeatureCollection geoJson(String layerIds, Integer page, Integer size, String query) {
        List<String> layers = layerIds == null || layerIds.isBlank()
                ? DEFAULT_LAYERS
                : Arrays.stream(layerIds.split(",")).map(JpaTopologyMapVisualizationAdapter::normalizeLayer)
                        .filter(item -> !item.isBlank()).toList();
        layers.forEach(this::layer);
        List<Feature> all = new ArrayList<>();
        layers.forEach(layer -> all.addAll(featuresFor(layer, query)));
        return collection(null, layers, all, page(page), size(size));
    }

    @Override
    @Transactional(readOnly = true)
    public SearchResult search(String query, Integer page, Integer size) {
        String normalized = query == null ? "" : query.trim();
        if (normalized.isBlank()) {
            return new SearchResult("", page(page), size(size), 0, 0, false, List.of());
        }
        List<Feature> all = new ArrayList<>();
        DEFAULT_LAYERS.forEach(layer -> all.addAll(featuresFor(layer, normalized)));
        int p = page(page);
        int s = size(size);
        List<Feature> result = slice(all, p, s);
        return new SearchResult(query, p, s, all.size(), totalPages(all.size(), s), hasNext(all.size(), p, s), result);
    }

    private List<Feature> featuresFor(String layer, String query) {
        Map<String, TopologyNodeJpaEntity> nodes = nodesById();
        return switch (layer) {
            case "pipeline-systems" -> pipelineSystemFeatures(nodes, query);
            case "pipelines" -> pipelineFeatures(nodes, query);
            case "facilities" -> facilityFeatures(query);
            case "topology-nodes" -> nodeFeatures(query);
            case "pipeline-segments" -> segmentFeatures(nodes, query);
            case "topology-connections" -> connectionFeatures(nodes, query);
            default -> throw new IllegalArgumentException("Unsupported topology map layer: " + layer + ".");
        };
    }

    private List<Feature> facilityFeatures(String query) {
        return entityManager.createQuery("select e from FacilityJpaEntity e", FacilityJpaEntity.class).getResultList().stream()
                .filter(e -> matches(query, e.code(), e.nameAr(), e.nameFr(), e.nameEn(), e.status(), e.facilityKind()))
                .filter(e -> hasPoint(e.latitude(), e.longitude()))
                .map(e -> new Feature("facilities:" + e.id(), new PointGeometry(coordinate(e.longitude(), e.latitude(), e.elevationMeters())),
                        properties("facilities", "facility", e.id(), e.code(), e.nameAr(), e.nameFr(), e.nameEn(), name(e.status()), name(e.facilityKind()), null, null, null, null, null, null, null, null)))
                .toList();
    }

    private List<Feature> nodeFeatures(String query) {
        return entityManager.createQuery("select e from TopologyNodeJpaEntity e", TopologyNodeJpaEntity.class).getResultList().stream()
                .filter(e -> matches(query, e.code(), e.name(), e.nodeType(), e.status()))
                .filter(e -> hasPoint(e.latitude(), e.longitude()))
                .map(e -> new Feature("topology-nodes:" + e.id(), new PointGeometry(coordinate(e.longitude(), e.latitude(), e.elevationMeters())),
                        properties("topology-nodes", "topology-node", e.id(), e.code(), null, null, e.name(), name(e.status()), null, name(e.nodeType()), null, null, null, null, null, null, null)))
                .toList();
    }

    private List<Feature> segmentFeatures(Map<String, TopologyNodeJpaEntity> nodes, String query) {
        return entityManager.createQuery("select e from PipelineSegmentJpaEntity e", PipelineSegmentJpaEntity.class).getResultList().stream()
                .filter(e -> matches(query, e.code(), e.segmentType(), e.flowDirection(), e.status(), e.pipelineId()))
                .map(e -> segmentFeature(e, nodes)).flatMap(Optional::stream).toList();
    }

    private List<Feature> connectionFeatures(Map<String, TopologyNodeJpaEntity> nodes, String query) {
        return entityManager.createQuery("select e from TopologyConnectionJpaEntity e", TopologyConnectionJpaEntity.class).getResultList().stream()
                .filter(e -> matches(query, e.code(), e.connectionType(), e.status()))
                .map(e -> connectionFeature(e, nodes)).flatMap(Optional::stream).toList();
    }

    private List<Feature> pipelineFeatures(Map<String, TopologyNodeJpaEntity> nodes, String query) {
        List<PipelineSegmentJpaEntity> segments = entityManager.createQuery("select e from PipelineSegmentJpaEntity e", PipelineSegmentJpaEntity.class).getResultList();
        return entityManager.createQuery("select e from PipelineJpaEntity e", PipelineJpaEntity.class).getResultList().stream()
                .filter(e -> matches(query, e.code(), e.nameAr(), e.nameFr(), e.nameEn(), e.pipelineType(), e.status()))
                .map(e -> aggregatePipeline(e, segments, nodes)).flatMap(Optional::stream).toList();
    }

    private List<Feature> pipelineSystemFeatures(Map<String, TopologyNodeJpaEntity> nodes, String query) {
        List<PipelineJpaEntity> pipelines = entityManager.createQuery("select e from PipelineJpaEntity e", PipelineJpaEntity.class).getResultList();
        List<PipelineSegmentJpaEntity> segments = entityManager.createQuery("select e from PipelineSegmentJpaEntity e", PipelineSegmentJpaEntity.class).getResultList();
        return entityManager.createQuery("select e from PipelineSystemJpaEntity e", PipelineSystemJpaEntity.class).getResultList().stream()
                .filter(e -> matches(query, e.code(), e.nameAr(), e.nameFr(), e.nameEn(), e.systemType(), e.status()))
                .map(e -> aggregateSystem(e, pipelines, segments, nodes)).flatMap(Optional::stream).toList();
    }

    private Optional<Feature> aggregatePipeline(PipelineJpaEntity pipeline, List<PipelineSegmentJpaEntity> segments, Map<String, TopologyNodeJpaEntity> nodes) {
        List<List<List<BigDecimal>>> lines = segments.stream().filter(s -> pipeline.id().equals(s.pipelineId()))
                .map(s -> segmentCoordinates(s, nodes)).flatMap(Optional::stream).toList();
        if (lines.isEmpty()) return Optional.empty();
        return Optional.of(new Feature("pipelines:" + pipeline.id(), new MultiLineStringGeometry(lines),
                properties("pipelines", "pipeline", pipeline.id(), pipeline.code(), pipeline.nameAr(), pipeline.nameFr(), pipeline.nameEn(), name(pipeline.status()), null, null,
                        pipeline.pipelineSystemId(), name(pipeline.pipelineType()), null, null, null, null, null)));
    }

    private Optional<Feature> aggregateSystem(PipelineSystemJpaEntity system, List<PipelineJpaEntity> pipelines,
            List<PipelineSegmentJpaEntity> segments, Map<String, TopologyNodeJpaEntity> nodes) {
        List<String> pipelineIds = pipelines.stream().filter(p -> system.id().equals(p.pipelineSystemId())).map(PipelineJpaEntity::id).toList();
        List<List<List<BigDecimal>>> lines = segments.stream().filter(s -> pipelineIds.contains(s.pipelineId()))
                .map(s -> segmentCoordinates(s, nodes)).flatMap(Optional::stream).toList();
        if (lines.isEmpty()) return Optional.empty();
        return Optional.of(new Feature("pipeline-systems:" + system.id(), new MultiLineStringGeometry(lines),
                properties("pipeline-systems", "pipeline-system", system.id(), system.code(), system.nameAr(), system.nameFr(), system.nameEn(), name(system.status()), null, null,
                        system.id(), name(system.systemType()), null, null, null, null, null)));
    }

    private Optional<Feature> segmentFeature(PipelineSegmentJpaEntity segment, Map<String, TopologyNodeJpaEntity> nodes) {
        return segmentCoordinates(segment, nodes).map(coords -> new Feature("pipeline-segments:" + segment.id(), new LineStringGeometry(coords),
                properties("pipeline-segments", "pipeline-segment", segment.id(), segment.code(), null, null, null, name(segment.status()), null, null,
                        null, null, segment.fromNodeId(), segment.toNodeId(), name(segment.segmentType()), name(segment.flowDirection()), null)));
    }

    private Optional<List<List<BigDecimal>>> segmentCoordinates(PipelineSegmentJpaEntity segment, Map<String, TopologyNodeJpaEntity> nodes) {
        TopologyNodeJpaEntity from = nodes.get(segment.fromNodeId());
        TopologyNodeJpaEntity to = nodes.get(segment.toNodeId());
        if (from == null || to == null || !hasPoint(from.latitude(), from.longitude()) || !hasPoint(to.latitude(), to.longitude())) return Optional.empty();
        return Optional.of(List.of(coordinate(from.longitude(), from.latitude(), from.elevationMeters()), coordinate(to.longitude(), to.latitude(), to.elevationMeters())));
    }

    private Optional<Feature> connectionFeature(TopologyConnectionJpaEntity connection, Map<String, TopologyNodeJpaEntity> nodes) {
        TopologyNodeJpaEntity from = nodes.get(connection.fromNodeId());
        TopologyNodeJpaEntity to = nodes.get(connection.toNodeId());
        if (from == null || to == null || !hasPoint(from.latitude(), from.longitude()) || !hasPoint(to.latitude(), to.longitude())) return Optional.empty();
        return Optional.of(new Feature("topology-connections:" + connection.id(), new LineStringGeometry(List.of(
                coordinate(from.longitude(), from.latitude(), from.elevationMeters()), coordinate(to.longitude(), to.latitude(), to.elevationMeters()))),
                properties("topology-connections", "topology-connection", connection.id(), connection.code(), null, null, null, name(connection.status()), null, null,
                        null, null, connection.fromNodeId(), connection.toNodeId(), null, null, name(connection.connectionType()))));
    }

    private FeatureCollection collection(String layer, List<String> layers, List<Feature> all, int page, int size) {
        return new FeatureCollection("hidra-topology", layer, page, size, all.size(), totalPages(all.size(), size), hasNext(all.size(), page, size), layers, slice(all, page, size));
    }

    private Map<String, TopologyNodeJpaEntity> nodesById() {
        Map<String, TopologyNodeJpaEntity> nodes = new LinkedHashMap<>();
        entityManager.createQuery("select e from TopologyNodeJpaEntity e", TopologyNodeJpaEntity.class).getResultList().forEach(e -> nodes.put(e.id(), e));
        return nodes;
    }

    private static FeatureProperties properties(String layer, String entityType, String entityId, String code, String nameAr, String nameFr, String nameEn,
            String status, String facilityKind, String nodeType, String pipelineSystemId, String pipelineType, String fromNodeId, String toNodeId,
            String segmentType, String flowDirection, String connectionType) {
        return new FeatureProperties(layer, entityType, entityId, code, nameAr, nameFr, nameEn, status, facilityKind, nodeType,
                pipelineSystemId, pipelineType, fromNodeId, toNodeId, segmentType, flowDirection, connectionType);
    }

    private static LayerDescriptor descriptor(String id, String label, String geometryType, String description) {
        return new LayerDescriptor(id, label, geometryType, description, "/api/v1/topology/map/layers/" + id + "/features");
    }

    private static List<BigDecimal> coordinate(BigDecimal longitude, BigDecimal latitude, BigDecimal elevation) {
        return elevation == null ? List.of(longitude, latitude) : List.of(longitude, latitude, elevation);
    }

    private static boolean hasPoint(BigDecimal latitude, BigDecimal longitude) { return latitude != null && longitude != null; }

    private static boolean matches(String query, Object... values) {
        if (query == null || query.isBlank()) return true;
        String normalized = query.trim().toLowerCase(Locale.ROOT);
        for (Object value : values) if (value != null && value.toString().toLowerCase(Locale.ROOT).contains(normalized)) return true;
        return false;
    }

    private static int page(Integer page) { return page == null || page < 0 ? DEFAULT_PAGE : page; }
    private static int size(Integer size) { return size == null || size <= 0 ? DEFAULT_SIZE : Math.min(size, MAX_SIZE); }
    private static int totalPages(long total, int size) { return total == 0 ? 0 : (int) ((total + size - 1) / size); }
    private static boolean hasNext(long total, int page, int size) { return ((long) page + 1) * size < total; }
    private static String normalizeLayer(String layerId) { return layerId == null ? "" : layerId.trim().toLowerCase(Locale.ROOT); }
    private static String name(Object value) { return value == null ? null : value.toString(); }

    private static <T> List<T> slice(List<T> values, int page, int size) {
        long startLong = (long) page * size;
        if (startLong >= values.size()) return List.of();
        int start = (int) startLong;
        int end = Math.min(values.size(), start + size);
        return List.copyOf(values.subList(start, end));
    }
}
