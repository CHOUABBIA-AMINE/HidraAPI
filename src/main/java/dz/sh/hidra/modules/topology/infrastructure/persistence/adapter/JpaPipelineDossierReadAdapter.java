/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : JpaPipelineDossierReadAdapter
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-11-20
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.infrastructure.persistence.adapter
 *
 * @Description : Resolves exact current Topology associations before hydrating protected dossier facts.
 *
 */
package dz.sh.hidra.modules.topology.infrastructure.persistence.adapter;

import dz.sh.hidra.modules.topology.application.port.out.PipelineDossierReadPort;
import dz.sh.hidra.modules.topology.application.port.out.PipelineDossierReadPort.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

/** Fixed SQL, bounded keysets and parameterized exact owner identity lookups. */
@Repository
public class JpaPipelineDossierReadAdapter implements PipelineDossierReadPort {
    private final JdbcTemplate sql;
    public JpaPipelineDossierReadAdapter(JdbcTemplate sql) {
        this.sql = Objects.requireNonNull(sql);
    }

    @Override
    public Optional<Association> association(String company, String pipelineId) {
        return single(sql.query("""
                SELECT r.company_party_id, p.id AS pipeline_id, p.pipeline_system_id,
                       r.revision, r.recorded_at
                FROM public.hidra_topology_pipeline p
                JOIN public.hidra_topology_pipeline_authoring_revision r
                  ON r.pipeline_id=p.id AND r.pipeline_system_id=p.pipeline_system_id
                JOIN public.hidra_topology_pipeline_system s
                  ON s.id=p.pipeline_system_id AND s.status='ACTIVE'
                JOIN public.hidra_topology_foundation_system_owner o
                  ON o.pipeline_system_id=s.id AND o.company_party_id=r.company_party_id
                WHERE p.id=? AND p.status='ACTIVE' AND r.company_party_id=?
                  AND r.revision=(SELECT max(v.revision)
                         FROM public.hidra_topology_pipeline_authoring_revision v
                         WHERE v.pipeline_id=p.id)
                """, (rs, i) -> new Association(rs.getString("company_party_id"),
                        rs.getString("pipeline_id"), rs.getString("pipeline_system_id"),
                        rs.getLong("revision"), at(rs, "recorded_at")), pipelineId, company));
    }

    @Override
    public Optional<PipelineRow> pipeline(String id) {
        return single(sql.query("""
                SELECT p.id,p.code,p.name_ar,p.name_fr,p.name_en,t.code AS type_code,
                       p.status,p.nominal_diameter,p.diameter_unit_code,
                       p.design_pressure,p.pressure_unit_code,p.total_length_km,p.updated_at
                FROM public.hidra_topology_pipeline p
                JOIN public.hidra_topology_pipeline_type t ON t.id=p.pipeline_type_id
                WHERE p.id=? AND p.status='ACTIVE'
                """, (rs, i) -> new PipelineRow(rs.getString("id"),
                        rs.getString("code"),rs.getString("name_ar"),rs.getString("name_fr"),
                        rs.getString("name_en"),rs.getString("type_code"),rs.getString("status"),
                        rs.getBigDecimal("nominal_diameter"),rs.getString("diameter_unit_code"),
                        rs.getBigDecimal("design_pressure"),rs.getString("pressure_unit_code"),
                        rs.getBigDecimal("total_length_km"),at(rs,"updated_at")), id));
    }

    @Override
    public List<SectionCandidate> sections(String pipelineId, String afterId, int size) {
        bounded(size);
        return sql.query("""
                SELECT id,pipeline_id,from_node_id,to_node_id
                FROM public.hidra_topology_pipeline_segment
                WHERE pipeline_id=? AND status='ACTIVE' AND id>?
                ORDER BY id LIMIT ?
                """, (rs,i) -> new SectionCandidate(rs.getString("id"),
                        rs.getString("pipeline_id"),rs.getString("from_node_id"),
                        rs.getString("to_node_id")),pipelineId,afterId,size);
    }

    @Override
    public Optional<SectionRow> section(String id) {
        return single(sql.query("""
                SELECT id,code,segment_type,flow_direction,from_node_id,to_node_id,
                       start_kilometer_point,end_kilometer_point,length_km,updated_at
                FROM public.hidra_topology_pipeline_segment
                WHERE id=? AND status='ACTIVE'
                """, (rs,i) -> new SectionRow(rs.getString("id"),
                        rs.getString("code"),rs.getString("segment_type"),
                        rs.getString("flow_direction"),rs.getString("from_node_id"),
                        rs.getString("to_node_id"),rs.getBigDecimal("start_kilometer_point"),
                        rs.getBigDecimal("end_kilometer_point"),rs.getBigDecimal("length_km"),
                        at(rs,"updated_at")),id));
    }

    @Override
    public Optional<NodeCandidate> nodeCandidate(String id) {
        return single(sql.query("""
                SELECT id,facility_id FROM public.hidra_topology_node
                WHERE id=? AND status='ACTIVE'
                """, (rs,i) -> new NodeCandidate(rs.getString("id"),
                        rs.getString("facility_id")),id));
    }

    @Override
    public Optional<NodeRow> node(String id) {
        return single(sql.query("""
                SELECT id,facility_id,longitude,latitude,elevation_meters,updated_at
                FROM public.hidra_topology_node WHERE id=? AND status='ACTIVE'
                """, (rs,i) -> new NodeRow(rs.getString("id"),
                        rs.getString("facility_id"),rs.getBigDecimal("longitude"),
                        rs.getBigDecimal("latitude"),rs.getBigDecimal("elevation_meters"),
                        at(rs,"updated_at")),id));
    }

    @Override
    public List<ConnectionCandidate> connections(String pipelineId, String afterId, int size) {
        bounded(size);
        return sql.query("""
                SELECT c.id,c.pipeline_segment_id AS section_id,c.from_node_id,c.to_node_id
                FROM public.hidra_topology_connection c
                JOIN public.hidra_topology_pipeline_segment seg
                  ON seg.id=c.pipeline_segment_id AND seg.status='ACTIVE'
                WHERE seg.pipeline_id=? AND c.status='ACTIVE' AND c.id>?
                ORDER BY c.id LIMIT ?
                """, (rs,i) -> new ConnectionCandidate(rs.getString("id"),
                        rs.getString("section_id"),rs.getString("from_node_id"),
                        rs.getString("to_node_id")),pipelineId,afterId,size);
    }

    @Override
    public Optional<ConnectionRow> connection(String id) {
        return single(sql.query("""
                SELECT c.id,c.code,t.code AS type_code,c.pipeline_segment_id AS section_id,
                       c.from_node_id,c.to_node_id,c.updated_at
                FROM public.hidra_topology_connection c
                JOIN public.hidra_topology_connection_type t ON t.id=c.connection_type_id
                WHERE c.id=? AND c.status='ACTIVE'
                """, (rs,i) -> new ConnectionRow(rs.getString("id"),
                        rs.getString("code"),rs.getString("type_code"),
                        rs.getString("section_id"),rs.getString("from_node_id"),
                        rs.getString("to_node_id"),at(rs,"updated_at")),id));
    }

    @Override
    public List<EquipmentCandidate> equipment(String pipelineId, String afterId, int size, Instant at) {
        bounded(size);
        Timestamp now = Timestamp.from(at);
        return sql.query("""
                SELECT e.id,e.pipeline_segment_id AS section_id,e.facility_id,e.node_id
                FROM public.hidra_topology_equipment e
                JOIN public.hidra_topology_pipeline_segment seg
                  ON seg.id=e.pipeline_segment_id AND seg.status='ACTIVE'
                WHERE seg.pipeline_id=? AND e.status='ACTIVE' AND e.id>?
                  AND (e.installed_at IS NULL OR e.installed_at<=?)
                  AND (e.retired_at IS NULL OR e.retired_at>?)
                ORDER BY e.id LIMIT ?
                """, (rs,i) -> new EquipmentCandidate(rs.getString("id"),
                        rs.getString("section_id"),rs.getString("facility_id"),
                        rs.getString("node_id")),pipelineId,afterId,now,now,size);
    }

    @Override
    public Optional<EquipmentRow> equipment(String id, Instant at) {
        Timestamp now=Timestamp.from(at);
        return single(sql.query("""
                SELECT e.id,e.code,e.name,t.code AS type_code,
                       e.pipeline_segment_id AS section_id,e.facility_id,e.node_id,
                       e.installed_at,e.updated_at
                FROM public.hidra_topology_equipment e
                JOIN public.hidra_topology_equipment_type t ON t.id=e.equipment_type_id
                WHERE e.id=? AND e.status='ACTIVE'
                  AND (e.installed_at IS NULL OR e.installed_at<=?)
                  AND (e.retired_at IS NULL OR e.retired_at>?)
                """, (rs,i) -> new EquipmentRow(rs.getString("id"),
                        rs.getString("code"),rs.getString("name"),rs.getString("type_code"),
                        rs.getString("section_id"),rs.getString("facility_id"),
                        rs.getString("node_id"),atNullable(rs,"installed_at"),
                        at(rs,"updated_at")),id,now,now));
    }

    private static <T> Optional<T> single(List<T> rows) {
        return rows.size()==1 ? Optional.of(rows.getFirst()) : Optional.empty();
    }

    private static void bounded(int size) {
        if(size<1 || size>250) throw new IllegalArgumentException("Invalid candidate size");
    }
    private static Instant at(ResultSet rs,String key) throws SQLException {
        Timestamp value=rs.getTimestamp(key);
        return value==null ? null : value.toInstant();
    }
    private static Instant atNullable(ResultSet rs,String key) throws SQLException {
        return at(rs,key);
    }
}
