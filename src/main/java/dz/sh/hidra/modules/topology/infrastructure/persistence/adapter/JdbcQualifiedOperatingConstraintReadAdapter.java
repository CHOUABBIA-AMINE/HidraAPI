/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : JdbcQualifiedOperatingConstraintReadAdapter
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-11-21
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.infrastructure.persistence.adapter
 *
 * @Description : Reads bounded immutable Topology-owned pressure restriction projection only.
 *
 */
package dz.sh.hidra.modules.topology.infrastructure.persistence.adapter;

import dz.sh.hidra.modules.topology.application.port.out.QualifiedOperatingConstraintReadPort;
import dz.sh.hidra.modules.topology.domain.model.QualifiedPressureRestriction.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class JdbcQualifiedOperatingConstraintReadAdapter
        implements QualifiedOperatingConstraintReadPort {
    private static final int MAX_FETCH = 4097;
    private final JdbcTemplate jdbc;
    public JdbcQualifiedOperatingConstraintReadAdapter(JdbcTemplate jdbc) {
        this.jdbc = Objects.requireNonNull(jdbc);
    }

    @Override
    public boolean targetMatches(Scope s, String system) {
        Boolean value = jdbc.queryForObject("""
                SELECT EXISTS (
                 SELECT 1 FROM public.hidra_topology_pipeline p
                 JOIN public.hidra_topology_pipeline_system sys
                   ON sys.id=p.pipeline_system_id AND sys.status='ACTIVE'
                 JOIN public.hidra_topology_foundation_system_owner own
                   ON own.pipeline_system_id=sys.id
                 JOIN public.hidra_topology_pipeline_authoring_revision rev
                   ON rev.pipeline_id=p.id AND rev.pipeline_system_id=sys.id
                 WHERE p.id=? AND p.pipeline_system_id=? AND p.status='ACTIVE'
                   AND own.company_party_id=? AND rev.company_party_id=?
                   AND rev.revision=(SELECT MAX(r.revision)
                        FROM public.hidra_topology_pipeline_authoring_revision r
                        WHERE r.pipeline_id=p.id)
                   AND (?='' OR EXISTS(
                     SELECT 1 FROM public.hidra_topology_pipeline_segment seg
                     WHERE seg.id=? AND seg.pipeline_id=p.id AND seg.status='ACTIVE'))
                )
                """, Boolean.class, s.pipelineId(), system, s.companyPartyId(),
                s.companyPartyId(), section(s), section(s));
        return Boolean.TRUE.equals(value);
    }

    @Override
    public boolean conditionAllows(Scope s, String unit) {
        Boolean exists = jdbc.queryForObject("""
                SELECT EXISTS(
                 SELECT 1 FROM public.hidra_topology_t36_condition_policy
                 WHERE company_party_id=? AND condition_code=? AND pressure_unit=?
                )
                """, Boolean.class, s.companyPartyId(), s.conditionCode(), unit);
        return Boolean.TRUE.equals(exists);
    }

    @Override
    public Snapshot snapshot(Scope s, Instant recordedBy) {
        Timestamp cutoff = Timestamp.from(recordedBy);
        List<Revision> revisions = jdbc.query("""
                SELECT revision_id,company_party_id,pipeline_id,section_id,condition_code,
                       upper_pressure,pressure_unit,pressure_basis,effective_from,
                       effective_until,recorded_at,baseline_receipt_id,
                       baseline_manifest_digest,engineering_source_revision,
                       decision_action_id,audit_event_id,submitted_by,reviewed_by,
                       approved_at,supersedes_revision_id
                FROM public.hidra_topology_t36_pressure_revision
                WHERE company_party_id=? AND pipeline_id=? AND section_id=?
                  AND condition_code=? AND recorded_at<=?
                ORDER BY revision_id LIMIT ?
                """, (rs,i) -> revision(rs), s.companyPartyId(), s.pipelineId(),
                section(s), s.conditionCode(), cutoff, MAX_FETCH);
        List<Termination> events = jdbc.query("""
                SELECT event_id,company_party_id,pipeline_id,section_id,condition_code,
                       retired_revision_id,event_kind,successor_revision_id,
                       effective_at,recorded_at,baseline_receipt_id,
                       baseline_manifest_digest,engineering_source_revision,
                       decision_action_id,audit_event_id,submitted_by,reviewed_by,approved_at
                FROM public.hidra_topology_t36_pressure_termination
                WHERE company_party_id=? AND pipeline_id=? AND section_id=?
                  AND condition_code=? AND recorded_at<=?
                ORDER BY event_id LIMIT ?
                """, (rs,i) -> termination(rs), s.companyPartyId(), s.pipelineId(),
                section(s), s.conditionCode(), cutoff, MAX_FETCH);
        return new Snapshot(revisions,events);
    }

    private static Revision revision(ResultSet rs) throws SQLException {
        return new Revision(uuid(rs,"revision_id"),
                scope(rs),rs.getBigDecimal("upper_pressure"),rs.getString("pressure_unit"),
                PressureBasis.valueOf(rs.getString("pressure_basis")),
                instant(rs,"effective_from"),instant(rs,"effective_until"),
                instant(rs,"recorded_at"),evidence(rs),uuid(rs,"supersedes_revision_id"));
    }
    private static Termination termination(ResultSet rs) throws SQLException {
        return new Termination(uuid(rs,"event_id"),scope(rs),uuid(rs,"retired_revision_id"),
                EventKind.valueOf(rs.getString("event_kind")),
                uuid(rs,"successor_revision_id"),instant(rs,"effective_at"),
                instant(rs,"recorded_at"),evidence(rs));
    }
    private static Scope scope(ResultSet rs) throws SQLException {
        String section=rs.getString("section_id");
        return new Scope(rs.getString("company_party_id"),rs.getString("pipeline_id"),
                section==null||section.isEmpty()?null:section,rs.getString("condition_code"));
    }
    private static Evidence evidence(ResultSet rs) throws SQLException {
        return new Evidence(uuid(rs,"baseline_receipt_id"),
                rs.getString("baseline_manifest_digest").trim(),
                rs.getString("engineering_source_revision"),rs.getString("decision_action_id"),
                rs.getString("audit_event_id"),rs.getString("submitted_by"),
                rs.getString("reviewed_by"),instant(rs,"approved_at"));
    }
    private static UUID uuid(ResultSet rs,String name) throws SQLException {
        Object value=rs.getObject(name);
        return value==null?null:(UUID)value;
    }
    private static Instant instant(ResultSet rs,String name) throws SQLException {
        Timestamp time=rs.getTimestamp(name);
        return time==null?null:time.toInstant();
    }
    private static String section(Scope scope) {
        return scope.pipelineLevel()?"":scope.sectionId();
    }
}
