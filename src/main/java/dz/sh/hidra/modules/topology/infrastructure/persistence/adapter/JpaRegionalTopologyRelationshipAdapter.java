/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : JpaRegionalTopologyRelationshipAdapter
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-11-20
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.infrastructure.persistence.adapter
 *
 * @Description : Provides T33 scoped regional resource access evidence.
 *
 */
package dz.sh.hidra.modules.topology.infrastructure.persistence.adapter;

import dz.sh.hidra.modules.topology.application.contract.identity.IdentityRegionalTopologyContract;
import dz.sh.hidra.modules.topology.application.contract.identity.IdentityRegionalTopologyContract.Kind;
import dz.sh.hidra.modules.topology.application.contract.identity.IdentityRegionalTopologyContract.Scope;
import dz.sh.hidra.modules.topology.application.port.out.RegionalTopologyRelationshipPort;
import java.time.Instant;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Optional;
import java.util.Objects;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

/**
 * Topology owner-only reads. Inherited scope is proven by live canonical joins,
 * not region names, stored snapshots, arbitrary routes or the caller's company claim.
 */
@Repository
public class JpaRegionalTopologyRelationshipAdapter implements RegionalTopologyRelationshipPort {
    private final JdbcTemplate sql;
    public JpaRegionalTopologyRelationshipAdapter(JdbcTemplate sql) {
        this.sql = Objects.requireNonNull(sql);
    }

    @Override
    public Optional<IdentityRegionalTopologyContract.Resolution> resolve(
            Kind kind, String id, String company, Instant at) {
        if (kind == null || id == null || id.isBlank() || company == null || company.isBlank()
                || at == null) return Optional.empty();
        try {
            var scopes = switch (kind) {
                case PIPELINE_SYSTEM -> system(id, company)
                        ? List.of(new Scope("PIPELINE_SYSTEM", id)) : List.<Scope>of();
                case PIPELINE -> pipeline(id, company);
                case PIPELINE_SEGMENT -> segment(id, company);
                case FACILITY -> facility(id, company, at);
                case EQUIPMENT -> equipment(id, company, at);
            };
            if (scopes.isEmpty()) return Optional.empty();
            return Optional.of(new IdentityRegionalTopologyContract.Resolution(
                    kind, id, company, scopes));
        } catch (RuntimeException ownerUnavailable) {
            return Optional.empty();
        }
    }

    private boolean system(String id, String company) {
        return Boolean.TRUE.equals(sql.queryForObject("""
                SELECT EXISTS (
                    SELECT 1 FROM public.hidra_topology_pipeline_system s
                    JOIN public.hidra_topology_foundation_system_owner o
                      ON o.pipeline_system_id = s.id
                    WHERE s.id = ? AND s.status = 'ACTIVE' AND o.company_party_id = ?
                )
                """, Boolean.class, id, company));
    }

    private List<Scope> pipeline(String id, String company) {
        var systems = sql.queryForList("""
                SELECT p.pipeline_system_id FROM public.hidra_topology_pipeline p
                JOIN public.hidra_topology_pipeline_authoring_revision r
                  ON r.pipeline_id = p.id AND r.pipeline_system_id = p.pipeline_system_id
                WHERE p.id = ? AND p.status = 'ACTIVE' AND r.company_party_id = ?
                  AND r.revision = (SELECT max(v.revision)
                                   FROM public.hidra_topology_pipeline_authoring_revision v
                                   WHERE v.pipeline_id = p.id)
                """, String.class, id, company);
        if (systems.size() != 1 || !system(systems.getFirst(), company)) return List.of();
        return List.of(new Scope("PIPELINE_SYSTEM", systems.getFirst()), new Scope("PIPELINE", id));
    }

    private List<Scope> segment(String id, String company) {
        var pipelines = sql.queryForList("""
                SELECT pipeline_id FROM public.hidra_topology_pipeline_segment
                WHERE id = ? AND status = 'ACTIVE'
                """, String.class, id);
        return pipelines.size() == 1 ? pipeline(pipelines.getFirst(), company) : List.of();
    }

    private List<Scope> facility(String id, String company, Instant at) {
        var owners = sql.queryForList("""
                SELECT owner_party_id FROM public.hidra_topology_facility
                WHERE id = ? AND status = 'ACTIVE'
                  AND (commissioned_at IS NULL OR commissioned_at <= ?)
                  AND (retired_at IS NULL OR retired_at > ?)
                """, String.class, id, Timestamp.from(at), Timestamp.from(at));
        if (owners.size() != 1 || !company.equals(owners.getFirst())) return List.of();
        var systems = sql.queryForList("""
                SELECT pipeline_system_id FROM public.hidra_topology_pipeline_system_facility
                WHERE facility_id = ? AND status = 'ACTIVE'
                  AND (valid_from IS NULL OR valid_from <= ?)
                  AND (valid_to IS NULL OR valid_to > ?)
                """, String.class, id, Timestamp.from(at), Timestamp.from(at));
        if (systems.size() != 1 || !system(systems.getFirst(), company)) return List.of();
        return List.of(new Scope("PIPELINE_SYSTEM", systems.getFirst()), new Scope("FACILITY", id));
    }

    private List<Scope> equipment(String id, String company, Instant at) {
        var link = sql.queryForList("""
                SELECT facility_id, pipeline_segment_id FROM public.hidra_topology_equipment
                WHERE id = ? AND status = 'ACTIVE'
                  AND (installed_at IS NULL OR installed_at <= ?)
                  AND (retired_at IS NULL OR retired_at > ?)
                """, id, Timestamp.from(at), Timestamp.from(at));
        if (link.size() != 1) return List.of();
        var row = link.getFirst();
        String facilityId = (String) row.get("facility_id");
        String segmentId = (String) row.get("pipeline_segment_id");
        if (facilityId == null && segmentId == null) return List.of();
        var scopes = new LinkedHashSet<Scope>();
        if (facilityId != null) {
            var facility = facility(facilityId, company, at);
            if (facility.isEmpty()) return List.of();
            scopes.addAll(facility);
        }
        if (segmentId != null) {
            var segment = segment(segmentId, company);
            if (segment.isEmpty()) return List.of();
            // An equipment item may not mix incompatible facility and segment systems.
            if (facilityId != null && !scopes.contains(segment.getFirst())) return List.of();
            scopes.addAll(segment);
        }
        scopes.add(new Scope("EQUIPMENT", id));
        return new ArrayList<>(scopes);
    }
}
