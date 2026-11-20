/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : JpaRegionalTopologyMapReadAdapter
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-11-20
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.infrastructure.visualization
 *
 * @Description : Reads company-bounded scalar Topology graph identities before authorized hydration.
 *
 */
package dz.sh.hidra.modules.topology.infrastructure.visualization;

import dz.sh.hidra.modules.topology.application.port.out.RegionalTopologyMapReadPort;
import dz.sh.hidra.modules.topology.application.port.out.RegionalTopologyMapReadPort.Candidate;
import dz.sh.hidra.modules.topology.application.port.out.RegionalTopologyMapReadPort.Detail;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

/** Read only; no cross-module persistence classes or unrestricted map scans. */
@Repository
public class JpaRegionalTopologyMapReadAdapter implements RegionalTopologyMapReadPort {
    private static final String SYSTEMS = "pipeline-systems";
    private static final String PIPELINES = "pipelines";
    private static final String SEGMENTS = "pipeline-segments";
    private static final String FACILITIES = "facilities";
    private static final String NODES = "topology-nodes";
    private static final String CONNECTIONS = "topology-connections";
    private final NamedParameterJdbcTemplate sql;

    public JpaRegionalTopologyMapReadAdapter(NamedParameterJdbcTemplate sql) {
        this.sql = Objects.requireNonNull(sql);
    }

    @Override
    public List<Candidate> candidates(String layer, String company, String after,
            int batchSize, Instant at) {
        if (batchSize < 1 || batchSize > 250 || company == null || at == null) {
            throw new IllegalArgumentException("Invalid regional candidate batch");
        }
        String query = switch (layer) {
            case SYSTEMS -> """
                SELECT s.id, NULL::text AS parent_id, NULL::text AS from_node_id,
                       NULL::text AS to_node_id, NULL::text AS facility_id
                FROM public.hidra_topology_pipeline_system s
                JOIN public.hidra_topology_foundation_system_owner o
                  ON o.pipeline_system_id=s.id
                WHERE s.status='ACTIVE' AND o.company_party_id=:company AND s.id > :after
                ORDER BY s.id LIMIT :limit
                """;
            case PIPELINES -> """
                SELECT p.id, p.pipeline_system_id AS parent_id,
                       NULL::text AS from_node_id, NULL::text AS to_node_id,
                       NULL::text AS facility_id
                FROM public.hidra_topology_pipeline p
                JOIN public.hidra_topology_pipeline_system s
                  ON s.id=p.pipeline_system_id AND s.status='ACTIVE'
                JOIN public.hidra_topology_foundation_system_owner o
                  ON o.pipeline_system_id=s.id AND o.company_party_id=:company
                JOIN public.hidra_topology_pipeline_authoring_revision r
                  ON r.pipeline_id=p.id AND r.pipeline_system_id=p.pipeline_system_id
                 AND r.company_party_id=:company
                 AND r.revision=(SELECT max(v.revision)
                       FROM public.hidra_topology_pipeline_authoring_revision v
                       WHERE v.pipeline_id=p.id)
                WHERE p.status='ACTIVE' AND p.id > :after
                ORDER BY p.id LIMIT :limit
                """;
            case SEGMENTS -> """
                SELECT seg.id, seg.pipeline_id AS parent_id,
                       seg.from_node_id, seg.to_node_id, NULL::text AS facility_id
                FROM public.hidra_topology_pipeline_segment seg
                JOIN public.hidra_topology_pipeline p
                  ON p.id=seg.pipeline_id AND p.status='ACTIVE'
                JOIN public.hidra_topology_pipeline_system s
                  ON s.id=p.pipeline_system_id AND s.status='ACTIVE'
                JOIN public.hidra_topology_foundation_system_owner o
                  ON o.pipeline_system_id=s.id AND o.company_party_id=:company
                JOIN public.hidra_topology_pipeline_authoring_revision r
                  ON r.pipeline_id=p.id AND r.pipeline_system_id=p.pipeline_system_id
                 AND r.company_party_id=:company
                 AND r.revision=(SELECT max(v.revision)
                       FROM public.hidra_topology_pipeline_authoring_revision v
                       WHERE v.pipeline_id=p.id)
                WHERE seg.status='ACTIVE' AND seg.id > :after
                ORDER BY seg.id LIMIT :limit
                """;
            case FACILITIES -> """
                SELECT DISTINCT f.id, link.pipeline_system_id AS parent_id,
                       NULL::text AS from_node_id, NULL::text AS to_node_id,
                       NULL::text AS facility_id
                FROM public.hidra_topology_facility f
                JOIN public.hidra_topology_pipeline_system_facility link
                  ON link.facility_id=f.id AND link.status='ACTIVE'
                 AND (link.valid_from IS NULL OR link.valid_from <= :at)
                 AND (link.valid_to IS NULL OR link.valid_to > :at)
                JOIN public.hidra_topology_pipeline_system s
                  ON s.id=link.pipeline_system_id AND s.status='ACTIVE'
                JOIN public.hidra_topology_foundation_system_owner o
                  ON o.pipeline_system_id=s.id AND o.company_party_id=:company
                WHERE f.status='ACTIVE' AND f.owner_party_id=:company
                  AND (f.commissioned_at IS NULL OR f.commissioned_at <= :at)
                  AND (f.retired_at IS NULL OR f.retired_at > :at)
                  AND f.id > :after
                ORDER BY f.id LIMIT :limit
                """;
            default -> throw new IllegalArgumentException("Unsupported regional candidate layer");
        };
        var params = new MapSqlParameterSource("company", company)
                .addValue("after", after).addValue("limit", batchSize)
                .addValue("at", Timestamp.from(at));
        return sql.query(query, params, (rs, n) ->
                new Candidate(rs.getString("id"), rs.getString("parent_id"),
                        rs.getString("from_node_id"), rs.getString("to_node_id"),
                        rs.getString("facility_id")));
    }

    @Override
    public List<Candidate> dependents(String layer, List<String> ids) {
        if (ids.isEmpty()) return List.of();
        List<Candidate> result = new ArrayList<>();
        for (int start = 0; start < ids.size(); start += 250) {
            List<String> chunk = ids.subList(start, Math.min(start + 250, ids.size()));
            String query = switch (layer) {
                case NODES -> """
                    SELECT n.id,NULL::text AS parent_id,NULL::text AS from_node_id,
                           NULL::text AS to_node_id,n.facility_id
                    FROM public.hidra_topology_node n
                    WHERE n.status='ACTIVE' AND n.id IN (:ids)
                    ORDER BY n.id
                    """;
                case CONNECTIONS -> """
                    SELECT c.id,c.pipeline_segment_id AS parent_id,
                           c.from_node_id,c.to_node_id,NULL::text AS facility_id
                    FROM public.hidra_topology_connection c
                    WHERE c.status='ACTIVE' AND c.pipeline_segment_id IN (:ids)
                    ORDER BY c.id
                    """;
                default -> throw new IllegalArgumentException("Unsupported regional dependency");
            };
            result.addAll(sql.query(query, new MapSqlParameterSource("ids", chunk),
                    (rs, n) -> new Candidate(rs.getString("id"), rs.getString("parent_id"),
                            rs.getString("from_node_id"), rs.getString("to_node_id"),
                            rs.getString("facility_id"))));
        }
        result.sort((a, b) -> a.id().compareTo(b.id()));
        return result;
    }

    @Override
    public Map<String, Detail> hydrate(String layer, List<String> ids) {
        if (ids.isEmpty()) return Map.of();
        String query = switch (layer) {
            case SYSTEMS -> """
                SELECT s.id,s.code,s.name_ar,s.name_fr,s.name_en,s.status,
                       t.code AS pipeline_type
                FROM public.hidra_topology_pipeline_system s
                JOIN public.hidra_topology_pipeline_system_type t ON t.id=s.system_type_id
                WHERE s.id IN (:ids) AND s.status='ACTIVE'
                """;
            case PIPELINES -> """
                SELECT p.id,p.code,p.name_ar,p.name_fr,p.name_en,p.status,
                       p.pipeline_system_id,t.code AS pipeline_type
                FROM public.hidra_topology_pipeline p
                JOIN public.hidra_topology_pipeline_type t ON t.id=p.pipeline_type_id
                WHERE p.id IN (:ids) AND p.status='ACTIVE'
                """;
            case SEGMENTS -> """
                SELECT id,code,status,segment_type,flow_direction
                FROM public.hidra_topology_pipeline_segment
                WHERE id IN (:ids) AND status='ACTIVE'
                """;
            case FACILITIES -> """
                SELECT id,code,name_ar,name_fr,name_en,status,facility_kind,
                       latitude,longitude,elevation_meters
                FROM public.hidra_topology_facility
                WHERE id IN (:ids) AND status='ACTIVE'
                """;
            case NODES -> """
                SELECT id,code,name AS name_en,status,node_type,
                       latitude,longitude,elevation_meters
                FROM public.hidra_topology_node
                WHERE id IN (:ids) AND status='ACTIVE'
                """;
            case CONNECTIONS -> """
                SELECT c.id,c.code,c.status,c.flow_direction,t.code AS connection_type
                FROM public.hidra_topology_connection c
                JOIN public.hidra_topology_connection_type t ON t.id=c.connection_type_id
                WHERE c.id IN (:ids) AND c.status='ACTIVE'
                """;
            default -> throw new IllegalArgumentException("Unsupported hydration layer");
        };
        Map<String, Detail> result = new LinkedHashMap<>();
        for (int start = 0; start < ids.size(); start += 250) {
            List<String> chunk = ids.subList(start, Math.min(ids.size(), start + 250));
            for (Map<String, Object> row : sql.queryForList(query,
                    new MapSqlParameterSource("ids", chunk))) {
                Detail detail = new Detail(value(row, "id"), value(row, "code"),
                        value(row, "name_ar"), value(row, "name_fr"), value(row, "name_en"),
                        value(row, "status"), value(row, "facility_kind"), value(row, "node_type"),
                        value(row, "pipeline_system_id"), value(row, "pipeline_type"),
                        value(row, "segment_type"), value(row, "flow_direction"),
                        value(row, "connection_type"), decimal(row, "longitude"),
                        decimal(row, "latitude"), decimal(row, "elevation_meters"));
                result.put(detail.id(), detail);
            }
        }
        return result;
    }

    @Override
    public boolean unchanged(String layer, Candidate original, String company, Instant at) {
        if (original == null || original.id() == null) return false;
        String query = switch (layer) {
            case SYSTEMS -> """
                SELECT s.id,NULL::text AS parent_id,NULL::text AS from_node_id,
                       NULL::text AS to_node_id,NULL::text AS facility_id
                FROM public.hidra_topology_pipeline_system s
                JOIN public.hidra_topology_foundation_system_owner o
                  ON o.pipeline_system_id=s.id
                WHERE s.id=:id AND s.status='ACTIVE' AND o.company_party_id=:company
                """;
            case PIPELINES -> """
                SELECT p.id,p.pipeline_system_id AS parent_id,NULL::text AS from_node_id,
                       NULL::text AS to_node_id,NULL::text AS facility_id
                FROM public.hidra_topology_pipeline p
                WHERE p.id=:id AND p.status='ACTIVE'
                """;
            case SEGMENTS -> """
                SELECT id,pipeline_id AS parent_id,from_node_id,to_node_id,
                       NULL::text AS facility_id
                FROM public.hidra_topology_pipeline_segment
                WHERE id=:id AND status='ACTIVE'
                """;
            case FACILITIES -> """
                SELECT f.id,link.pipeline_system_id AS parent_id,
                       NULL::text AS from_node_id,NULL::text AS to_node_id,
                       NULL::text AS facility_id
                FROM public.hidra_topology_facility f
                JOIN public.hidra_topology_pipeline_system_facility link
                  ON link.facility_id=f.id AND link.status='ACTIVE'
                 AND (link.valid_from IS NULL OR link.valid_from <= :at)
                 AND (link.valid_to IS NULL OR link.valid_to > :at)
                WHERE f.id=:id AND f.status='ACTIVE' AND f.owner_party_id=:company
                  AND (f.commissioned_at IS NULL OR f.commissioned_at <= :at)
                  AND (f.retired_at IS NULL OR f.retired_at > :at)
                """;
            case NODES -> """
                SELECT id,NULL::text AS parent_id,NULL::text AS from_node_id,
                       NULL::text AS to_node_id,facility_id
                FROM public.hidra_topology_node WHERE id=:id AND status='ACTIVE'
                """;
            case CONNECTIONS -> """
                SELECT id,pipeline_segment_id AS parent_id,from_node_id,to_node_id,
                       NULL::text AS facility_id
                FROM public.hidra_topology_connection WHERE id=:id AND status='ACTIVE'
                """;
            default -> throw new IllegalArgumentException("Unsupported recheck layer");
        };
        var params = new MapSqlParameterSource("id", original.id()).addValue("company", company)
                .addValue("at", Timestamp.from(at));
        List<Candidate> fresh = sql.query(query, params, (rs, n) ->
                new Candidate(rs.getString("id"), rs.getString("parent_id"),
                        rs.getString("from_node_id"), rs.getString("to_node_id"),
                        rs.getString("facility_id")));
        return fresh.size() == 1 && original.equals(fresh.getFirst());
    }

    private static String value(Map<String, Object> row, String key) {
        Object item = row.get(key);
        return item == null ? null : item.toString();
    }
    private static BigDecimal decimal(Map<String, Object> row, String key) {
        Object item = row.get(key);
        return item == null ? null : (BigDecimal) item;
    }
}
