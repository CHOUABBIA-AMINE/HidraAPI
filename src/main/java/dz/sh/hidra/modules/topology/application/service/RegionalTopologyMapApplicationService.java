/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : RegionalTopologyMapApplicationService
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-11-20
 *
 * @Type        : Class
 * @Layer       : Application
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.application.service
 *
 * @Description : Projects a live Identity-authorized, regional Topology graph.
 *
 */
package dz.sh.hidra.modules.topology.application.service;

import dz.sh.hidra.modules.identity.application.contract.topology.TopologyRegionalAccessContract;
import dz.sh.hidra.modules.identity.application.contract.topology.TopologyRegionalAccessContract.Request;
import dz.sh.hidra.modules.identity.application.contract.topology.TopologyRegionalAccessContract.ResourceKind;
import dz.sh.hidra.modules.topology.application.port.in.RegionalTopologyMapUseCase;
import dz.sh.hidra.modules.topology.application.port.in.TopologyMapVisualizationUseCase.*;
import dz.sh.hidra.modules.topology.application.port.out.RegionalTopologyMapReadPort;
import dz.sh.hidra.modules.topology.application.port.out.RegionalTopologyMapReadPort.Candidate;
import dz.sh.hidra.modules.topology.application.port.out.RegionalTopologyMapReadPort.Detail;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Set;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * One-request graph projection. T33 is the only resource decision authority.
 * A route permission is insufficient, and the administrator map is never called.
 */
@Service
public class RegionalTopologyMapApplicationService implements RegionalTopologyMapUseCase {
    private static final int BATCH = 250;
    private static final int DEFAULT_SIZE = 100;
    private static final List<String> LAYERS = List.of("pipeline-systems", "pipelines",
            "facilities", "topology-nodes", "pipeline-segments", "topology-connections");

    private final RegionalTopologyMapReadPort read;
    private final TopologyRegionalAccessContract access;

    public RegionalTopologyMapApplicationService(RegionalTopologyMapReadPort read,
            TopologyRegionalAccessContract access) {
        this.read = Objects.requireNonNull(read);
        this.access = Objects.requireNonNull(access);
    }

    @Override
    public List<LayerDescriptor> layers(String companyPartyId) {
        company(companyPartyId);
        return LAYERS.stream().map(RegionalTopologyMapApplicationService::descriptor).toList();
    }

    @Override
    public LayerDescriptor layer(String companyPartyId, String layerId) {
        company(companyPartyId);
        return descriptor(canonicalLayer(layerId));
    }

    @Override
    @Transactional(readOnly = true)
    public FeatureCollection features(String companyPartyId, String layerId, Integer page,
            Integer size, String query) {
        String company = company(companyPartyId);
        String layer = canonicalLayer(layerId);
        int p = page(page), s = size(size);
        String q = optionalQuery(query);
        List<Feature> visible = graph(company).features().get(layer);
        return collection(layer, List.of(layer), filtered(visible, q), p, s);
    }

    @Override
    @Transactional(readOnly = true)
    public Feature feature(String companyPartyId, String layerId, String entityId) {
        String company = company(companyPartyId);
        String layer = canonicalLayer(layerId);
        if (entityId == null || !entityId.matches("[A-Za-z0-9][A-Za-z0-9_-]{0,79}")) {
            throw new NoSuchElementException("Feature unavailable");
        }
        return graph(company).features().get(layer).stream()
                .filter(f -> entityId.equals(f.properties().entityId()))
                .findFirst().orElseThrow(() -> new NoSuchElementException("Feature unavailable"));
    }

    @Override
    @Transactional(readOnly = true)
    public FeatureCollection geoJson(String companyPartyId, String layerIds,
            Integer page, Integer size, String query) {
        String company = company(companyPartyId);
        int p = page(page), s = size(size);
        String q = optionalQuery(query);
        Set<String> chosen = new LinkedHashSet<>();
        if (layerIds == null || layerIds.isBlank()) {
            chosen.addAll(LAYERS);
        } else {
            for (String raw : layerIds.split(",", -1)) {
                chosen.add(canonicalLayer(raw));
            }
        }
        List<String> canonical = LAYERS.stream().filter(chosen::contains).toList();
        Projection result = graph(company);
        List<Feature> visible = new ArrayList<>();
        for (String layer : canonical) visible.addAll(result.features().get(layer));
        return collection(null, canonical, filtered(visible, q), p, s);
    }

    @Override
    @Transactional(readOnly = true)
    public SearchResult search(String companyPartyId, String query, Integer page, Integer size) {
        String company = company(companyPartyId);
        String q = requiredQuery(query);
        int p = page(page), s = size(size);
        Projection result = graph(company);
        List<Feature> all = new ArrayList<>();
        for (String layer : LAYERS) all.addAll(filtered(result.features().get(layer), q));
        return new SearchResult(q, p, s, all.size(), pages(all.size(), s),
                hasNext(all.size(), p, s), slice(all, p, s));
    }

    private Projection graph(String company) {
        Instant at = Instant.now();
        Map<Request, Boolean> memo = new HashMap<>();
        LinkedHashSet<Request> authority = new LinkedHashSet<>();
        List<Used> used = new ArrayList<>();

        Map<String, Candidate> systems = keyed(readCandidates("pipeline-systems", company, at));
        Map<String, Candidate> pipelines = keyed(readCandidates("pipelines", company, at));
        Map<String, Candidate> segments = keyed(readCandidates("pipeline-segments", company, at));
        Map<String, Candidate> facilities = keyed(readCandidates("facilities", company, at));

        Set<String> approvedSystems = approved(systems.values(), ResourceKind.PIPELINE_SYSTEM,
                company, memo, authority);
        Set<String> approvedPipelines = approved(pipelines.values(), ResourceKind.PIPELINE,
                company, memo, authority);
        Set<String> approvedSegments = approved(segments.values(), ResourceKind.PIPELINE_SEGMENT,
                company, memo, authority);
        Set<String> approvedFacilities = approved(facilities.values(), ResourceKind.FACILITY,
                company, memo, authority);

        List<String> endpoints = new ArrayList<>();
        for (String segmentId : approvedSegments) {
            Candidate segment = segments.get(segmentId);
            if (segment != null && pipelines.containsKey(segment.parentId())) {
                endpoints.add(segment.fromNodeId());
                endpoints.add(segment.toNodeId());
            }
        }
        Map<String, Candidate> nodes = keyed(read.dependents("topology-nodes",
                endpoints.stream().filter(Objects::nonNull).distinct().toList()));
        Set<String> allowedNodes = new LinkedHashSet<>();
        for (Candidate node : nodes.values()) {
            if (node.facilityId() == null || permit(ResourceKind.FACILITY, node.facilityId(),
                    company, memo, authority)) {
                allowedNodes.add(node.id());
            }
        }
        Map<String, Detail> nodeDetail = read.hydrate("topology-nodes", List.copyOf(allowedNodes));
        Set<String> drawable = new LinkedHashSet<>();
        Set<String> incident = new LinkedHashSet<>();
        for (String segmentId : approvedSegments) {
            Candidate segment = segments.get(segmentId);
            if (segment == null || !pipelines.containsKey(segment.parentId())) continue;
            if (!allowedNodes.contains(segment.fromNodeId())
                    || !allowedNodes.contains(segment.toNodeId())) continue;
            if (!point(nodeDetail.get(segment.fromNodeId()))
                    || !point(nodeDetail.get(segment.toNodeId()))) continue;
            drawable.add(segmentId);
            incident.add(segment.fromNodeId());
            incident.add(segment.toNodeId());
            used.add(new Used("pipeline-segments", segment));
        }
        Map<String, Candidate> connections = keyed(read.dependents("topology-connections",
                List.copyOf(drawable)));
        Set<String> includedConnections = new LinkedHashSet<>();
        for (Candidate connection : connections.values()) {
            Candidate segment = segments.get(connection.parentId());
            if (segment == null || !drawable.contains(connection.parentId())) continue;
            boolean same = Objects.equals(connection.fromNodeId(), segment.fromNodeId())
                    && Objects.equals(connection.toNodeId(), segment.toNodeId());
            boolean reverse = Objects.equals(connection.fromNodeId(), segment.toNodeId())
                    && Objects.equals(connection.toNodeId(), segment.fromNodeId());
            if (same || reverse) {
                includedConnections.add(connection.id());
                used.add(new Used("topology-connections", connection));
            }
        }
        for (String id : incident) used.add(new Used("topology-nodes", nodes.get(id)));
        for (String id : approvedFacilities) used.add(new Used("facilities", facilities.get(id)));

        Map<String, Detail> segmentDetail = read.hydrate("pipeline-segments", List.copyOf(drawable));
        Map<String, Detail> facilityDetail = read.hydrate("facilities", List.copyOf(approvedFacilities));
        Map<String, Detail> connectionDetail = read.hydrate("topology-connections",
                List.copyOf(includedConnections));
        Map<String, Detail> pipelineDetail = read.hydrate("pipelines", List.copyOf(approvedPipelines));
        Map<String, Detail> systemDetail = read.hydrate("pipeline-systems", List.copyOf(approvedSystems));

        Map<String, List<Feature>> features = new LinkedHashMap<>();
        for (String layer : LAYERS) features.put(layer, new ArrayList<>());

        Map<String, List<List<List<BigDecimal>>>> pipelineLines = new HashMap<>();
        Map<String, List<List<List<BigDecimal>>>> systemLines = new HashMap<>();
        for (String id : drawable) {
            Candidate candidate = segments.get(id);
            Detail d = segmentDetail.get(id);
            Detail from = nodeDetail.get(candidate.fromNodeId());
            Detail to = nodeDetail.get(candidate.toNodeId());
            if (d == null || !point(from) || !point(to)) continue;
            List<List<BigDecimal>> line = List.of(coordinate(from), coordinate(to));
            features.get("pipeline-segments").add(new Feature("pipeline-segments:" + id,
                    new LineStringGeometry(line),
                    properties("pipeline-segments", "pipeline-segment", d, null, null,
                            candidate.fromNodeId(), candidate.toNodeId())));
            pipelineLines.computeIfAbsent(candidate.parentId(), ignored -> new ArrayList<>()).add(line);
            Candidate parent = pipelines.get(candidate.parentId());
            if (parent != null) {
                systemLines.computeIfAbsent(parent.parentId(), ignored -> new ArrayList<>()).add(line);
            }
        }
        for (String id : approvedPipelines) {
            List<List<List<BigDecimal>>> lines = pipelineLines.get(id);
            Detail d = pipelineDetail.get(id);
            if (d == null || lines == null || lines.isEmpty()) continue;
            Candidate candidate = pipelines.get(id);
            String exposedSystem = approvedSystems.contains(candidate.parentId())
                    ? candidate.parentId() : null;
            features.get("pipelines").add(new Feature("pipelines:" + id,
                    new MultiLineStringGeometry(List.copyOf(lines)),
                    properties("pipelines", "pipeline", d, exposedSystem, null, null, null)));
            used.add(new Used("pipelines", candidate));
        }
        for (String id : approvedSystems) {
            List<List<List<BigDecimal>>> lines = systemLines.get(id);
            Detail d = systemDetail.get(id);
            if (d == null || lines == null || lines.isEmpty()) continue;
            features.get("pipeline-systems").add(new Feature("pipeline-systems:" + id,
                    new MultiLineStringGeometry(List.copyOf(lines)),
                    properties("pipeline-systems", "pipeline-system", d, id, null, null, null)));
            used.add(new Used("pipeline-systems", systems.get(id)));
        }
        for (String id : approvedFacilities) {
            Detail d = facilityDetail.get(id);
            if (!point(d)) continue;
            features.get("facilities").add(new Feature("facilities:" + id,
                    new PointGeometry(coordinate(d)),
                    properties("facilities", "facility", d, null, null, null, null)));
        }
        for (String id : incident) {
            Detail d = nodeDetail.get(id);
            if (!point(d)) continue;
            features.get("topology-nodes").add(new Feature("topology-nodes:" + id,
                    new PointGeometry(coordinate(d)),
                    properties("topology-nodes", "topology-node", d, null, null, null, null)));
        }
        for (String id : includedConnections) {
            Candidate c = connections.get(id);
            Detail d = connectionDetail.get(id);
            Detail from = nodeDetail.get(c.fromNodeId()), to = nodeDetail.get(c.toNodeId());
            if (d == null || !point(from) || !point(to)) continue;
            features.get("topology-connections").add(new Feature("topology-connections:" + id,
                    new LineStringGeometry(List.of(coordinate(from), coordinate(to))),
                    properties("topology-connections", "topology-connection", d, null, null,
                            c.fromNodeId(), c.toNodeId())));
        }
        // Recheck current actor/owner decisions and graph relationships after all
        // hydrations. Never emit a stale partially authorized projection.
        for (Request request : authority) {
            if (!access.permitted(request)) throw new NoSuchElementException("Regional map unavailable");
        }
        for (Used item : used) {
            if (item.candidate() == null || !read.unchanged(item.layer(), item.candidate(), company, at))
                throw new NoSuchElementException("Regional map unavailable");
        }
        for (List<Feature> list : features.values()) {
            list.sort(Comparator.comparing(f -> f.properties().entityId()));
        }
        return new Projection(features);
    }

    private List<Candidate> readCandidates(String layer, String company, Instant at) {
        List<Candidate> result = new ArrayList<>();
        String after = "";
        while (true) {
            List<Candidate> batch = read.candidates(layer, company, after, BATCH, at);
            if (batch.isEmpty()) break;
            String cursor = after;
            if (batch.size() > BATCH || batch.stream().anyMatch(c ->
                    c.id() == null || c.id().compareTo(cursor) <= 0)) {
                throw new IllegalStateException("Invalid regional candidate order");
            }
            result.addAll(batch);
            String last = batch.getLast().id();
            if (last.equals(after)) throw new IllegalStateException("Invalid regional cursor");
            after = last;
            if (batch.size() < BATCH) break;
        }
        return result;
    }

    private Set<String> approved(Collection<Candidate> candidates, ResourceKind kind,
            String company, Map<Request, Boolean> memo, Set<Request> authority) {
        Set<String> result = new LinkedHashSet<>();
        for (Candidate candidate : candidates) {
            if (permit(kind, candidate.id(), company, memo, authority)) result.add(candidate.id());
        }
        return result;
    }

    private boolean permit(ResourceKind kind, String id, String company,
            Map<Request, Boolean> memo, Set<Request> authority) {
        if (id == null) return false;
        Request request = new Request(kind, id, company);
        boolean allowed = memo.computeIfAbsent(request, access::permitted);
        if (allowed) authority.add(request);
        return allowed;
    }

    private static Map<String, Candidate> keyed(List<Candidate> values) {
        Map<String, Candidate> result = new LinkedHashMap<>();
        for (Candidate candidate : values) {
            if (candidate != null && candidate.id() != null) result.putIfAbsent(candidate.id(), candidate);
        }
        return result;
    }

    private static FeatureProperties properties(String layer, String kind, Detail d,
            String system, String facility, String from, String to) {
        return new FeatureProperties(layer, kind, d.id(), d.code(), d.nameAr(),
                d.nameFr(), d.nameEn(), d.status(), d.facilityKind(), d.nodeType(),
                system, d.pipelineType(), from, to, d.segmentType(), d.flowDirection(),
                d.connectionType());
    }

    private static boolean point(Detail detail) {
        return detail != null && detail.longitude() != null && detail.latitude() != null;
    }
    private static List<BigDecimal> coordinate(Detail detail) {
        return detail.elevationMeters() == null
                ? List.of(detail.longitude(), detail.latitude())
                : List.of(detail.longitude(), detail.latitude(), detail.elevationMeters());
    }
    private static List<Feature> filtered(List<Feature> items, String query) {
        if (query == null) return List.copyOf(items);
        String q = query.toLowerCase(Locale.ROOT);
        return items.stream().filter(feature -> {
            FeatureProperties p = feature.properties();
            for (String value : List.of(p.entityId(), p.code() == null ? "" : p.code(),
                    p.nameAr() == null ? "" : p.nameAr(),
                    p.nameFr() == null ? "" : p.nameFr(),
                    p.nameEn() == null ? "" : p.nameEn())) {
                if (value.toLowerCase(Locale.ROOT).contains(q)) return true;
            }
            return false;
        }).toList();
    }

    private static FeatureCollection collection(String layer, List<String> layers,
            List<Feature> all, int page, int size) {
        return new FeatureCollection("hidra-regional-topology", layer, page, size, all.size(),
                pages(all.size(), size), hasNext(all.size(), page, size), layers,
                slice(all, page, size));
    }
    private static <T> List<T> slice(List<T> values, int page, int size) {
        long start = (long) page * size;
        if (start >= values.size()) return List.of();
        return List.copyOf(values.subList((int) start, (int) Math.min(values.size(), start + size)));
    }
    private static int pages(long total, int size) {
        return total == 0 ? 0 : (int) ((total + size - 1L) / size);
    }
    private static boolean hasNext(long total, int page, int size) {
        return ((long) page + 1) * size < total;
    }
    private static int page(Integer value) {
        if (value == null) return 0;
        if (value < 0) throw new IllegalArgumentException("Invalid page");
        return value;
    }
    private static int size(Integer value) {
        if (value == null) return DEFAULT_SIZE;
        if (value < 1 || value > 500) throw new IllegalArgumentException("Invalid size");
        return value;
    }
    private static String company(String value) {
        if (value == null || !value.matches("[A-Za-z0-9][A-Za-z0-9_-]{0,79}")) {
            throw new IllegalArgumentException("Invalid companyPartyId");
        }
        return value;
    }
    private static String optionalQuery(String query) {
        if (query == null) return null;
        String result = query.trim();
        if (result.length() > 120) throw new IllegalArgumentException("Invalid query");
        return result.isEmpty() ? null : result;
    }
    private static String requiredQuery(String query) {
        String result = optionalQuery(query);
        if (result == null) throw new IllegalArgumentException("Query required");
        return result;
    }
    private static String canonicalLayer(String value) {
        if (value == null || !LAYERS.contains(value)) throw new NoSuchElementException("Layer unavailable");
        return value;
    }
    private static LayerDescriptor descriptor(String id) {
        String title = switch (id) {
            case "pipeline-systems" -> "Pipeline systems";
            case "pipelines" -> "Pipelines";
            case "facilities" -> "Facilities";
            case "topology-nodes" -> "Topology nodes";
            case "pipeline-segments" -> "Pipeline segments";
            case "topology-connections" -> "Topology connections";
            default -> throw new NoSuchElementException("Layer unavailable");
        };
        String shape = switch (id) {
            case "pipeline-systems", "pipelines" -> "MultiLineString";
            case "pipeline-segments", "topology-connections" -> "LineString";
            default -> "Point";
        };
        return new LayerDescriptor(id, title, shape, "Authorized regional topology",
                "/api/v1/topology/regional-map/layers/" + id + "/features");
    }

    private record Projection(Map<String, List<Feature>> features) { }
    private record Used(String layer, Candidate candidate) { }
}
