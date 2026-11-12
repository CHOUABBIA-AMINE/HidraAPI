/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : JdbcTelemetryCommissioningPersistenceAdapter
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-11-12
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : telemetry
 * @Package     : dz.sh.hidra.modules.telemetry.infrastructure.persistence.adapter
 *
 * @Description : Atomic and versioned Telemetry commissioning on existing owned tables.
 *
 */
package dz.sh.hidra.modules.telemetry.infrastructure.persistence.adapter;

import dz.sh.hidra.modules.telemetry.application.port.in.TelemetryCommissioningUseCase.*;
import dz.sh.hidra.modules.telemetry.application.port.out.TelemetryCommissioningPersistencePort;
import dz.sh.hidra.modules.telemetry.domain.exception.InvalidTelemetryValueException;
import dz.sh.hidra.modules.telemetry.domain.exception.TelemetryCommissioningConflictException;
import dz.sh.hidra.modules.telemetry.domain.policy.TelemetryCommissioningPolicy;
import java.nio.charset.StandardCharsets;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class JdbcTelemetryCommissioningPersistenceAdapter implements TelemetryCommissioningPersistencePort {
    private final JdbcTemplate jdbc;
    private static final String VIEW = """
        SELECT s.id AS source_id,s.code AS source_code,
               d.id AS device_id,d.code AS device_code,
               p.id AS point_id,p.code AS point_code,
               p.point_type_id,p.signal_type_id,p.unit_id,
               b.topology_asset_type_code AS asset_type,
               b.topology_asset_id AS asset_id,
               b.topology_asset_code AS asset_code,
               b.id AS binding_id,b.binding_role_id,
               m.id AS mapping_id,m.external_tag_name,
               b.valid_from,b.valid_to,b.active AS binding_active,
               s.status AS source_status,d.status AS device_status,p.status AS point_status
          FROM public.hidra_telemetry_point p
          JOIN public.hidra_telemetry_device d ON d.id=p.device_id
          JOIN public.hidra_telemetry_source s ON s.id=d.source_id
          JOIN public.hidra_telemetry_point_binding b ON b.point_id=p.id
          LEFT JOIN public.hidra_telemetry_external_tag_mapping m
            ON m.point_id=p.id AND m.source_id=s.id AND m.device_id=d.id
               AND m.valid_from=b.valid_from
        """;
    private static final RowMapper<Snapshot> MAPPER = (rs,row)->snapshot(rs);

    public JdbcTelemetryCommissioningPersistenceAdapter(JdbcTemplate jdbc) {
        this.jdbc=Objects.requireNonNull(jdbc);
    }

    private static Snapshot snapshot(ResultSet r) throws SQLException {
        Instant from=r.getTimestamp("valid_from").toInstant();
        Timestamp until=r.getTimestamp("valid_to");
        Instant end=until==null?null:until.toInstant();
        // Historical records remain reconstructible after later source/point retirement.
        // Runtime ingestion separately requires current ACTIVE statuses and one effective binding.
        if(r.getString("mapping_id")==null)
            throw denied();
        boolean effective=r.getBoolean("binding_active") && !from.isAfter(Instant.now())
                && (end==null || end.isAfter(Instant.now()));
        return new Snapshot(r.getString("source_id"),r.getString("source_code"),
                r.getString("device_id"),r.getString("device_code"),
                r.getString("point_id"),r.getString("point_code"),
                r.getString("point_type_id"),r.getString("signal_type_id"),r.getString("unit_id"),
                r.getString("asset_type"),r.getString("asset_id"),r.getString("asset_code"),
                r.getString("binding_id"),r.getString("binding_role_id"),
                r.getString("mapping_id"),r.getString("external_tag_name"),
                from,end,effective);
    }

    private static AccessDeniedException denied() {
        return new AccessDeniedException("Protected telemetry resource unavailable.");
    }
    private static TelemetryCommissioningConflictException conflict() {
        return new TelemetryCommissioningConflictException();
    }
    private static String derive(UUID request,String object) {
        return UUID.nameUUIDFromBytes(("HIDRA_T08|"+request+"|"+object)
                .getBytes(StandardCharsets.UTF_8)).toString();
    }
    private boolean activeCatalog(String id,String family) {
        return id!=null && !id.isBlank() && Boolean.TRUE.equals(jdbc.queryForObject(
                "SELECT EXISTS (SELECT 1 FROM public.hidra_telemetry_type_catalog "
                +"WHERE id=? AND catalog_name=? AND active=true)",Boolean.class,id,family));
    }
    private void requiredCatalog(String id,String family) {
        TelemetryCommissioningPolicy.required(family,id,80);
        if(!activeCatalog(id,family))throw denied();
    }
    private boolean activeUnit(String id) {
        return id!=null && Boolean.TRUE.equals(jdbc.queryForObject(
                "SELECT EXISTS (SELECT 1 FROM public.hidra_telemetry_unit WHERE id=? AND active=true)",
                Boolean.class,id));
    }
    private void eligiblePoint(String pointType,String signalType,String unit) {
        requiredCatalog(pointType,"POINT_TYPE");
        requiredCatalog(signalType,"SIGNAL_TYPE");
        var shape=jdbc.queryForObject("SELECT value_shape FROM public.hidra_telemetry_type_catalog "
                +"WHERE id=?",String.class,signalType);
        Boolean exempt=jdbc.queryForObject("SELECT numeric_unit_exempt "
                +"FROM public.hidra_telemetry_type_catalog WHERE id=?",Boolean.class,pointType);
        if(!("NUMERIC".equals(shape)||"TEXT".equals(shape)||"BOOLEAN".equals(shape)) ||
                (unit!=null&&!activeUnit(unit)) ||
                ("NUMERIC".equals(shape)&&unit==null&&!Boolean.TRUE.equals(exempt)))
            throw denied();
    }
    private static void requiredNames(Enroll c) {
        TelemetryCommissioningPolicy.required("sourceNameFr",c.sourceNameFr(),160);
        TelemetryCommissioningPolicy.required("deviceNameFr",c.deviceNameFr(),160);
        TelemetryCommissioningPolicy.required("pointNameFr",c.pointNameFr(),160);
        TelemetryCommissioningPolicy.required("sourceTypeId",c.sourceTypeId(),80);
        TelemetryCommissioningPolicy.required("protocolId",c.protocolId(),80);
        TelemetryCommissioningPolicy.required("deviceTypeId",c.deviceTypeId(),80);
        TelemetryCommissioningPolicy.required("pointTypeId",c.pointTypeId(),80);
        TelemetryCommissioningPolicy.required("signalTypeId",c.signalTypeId(),80);
        TelemetryCommissioningPolicy.required("bindingRoleId",c.bindingRoleId(),80);
        if(c.unitId()!=null)TelemetryCommissioningPolicy.required("unitId",c.unitId(),80);
    }
    private List<Snapshot> selectAt(String pointId,Instant at) {
        return jdbc.query(VIEW+" WHERE p.id=? AND b.valid_from<=? "
                +"AND (b.valid_to IS NULL OR b.valid_to>?) "
                +"ORDER BY b.valid_from DESC,b.id FETCH FIRST 2 ROWS ONLY",
                MAPPER,pointId,Timestamp.from(at),Timestamp.from(at));
    }
    @Override
    @Transactional(propagation=Propagation.MANDATORY)
    public Optional<Snapshot> effective(String pointId,Instant at) {
        if(pointId==null||at==null)throw denied();
        var rows=selectAt(pointId,at);
        if(rows.size()>1)throw denied(); // fail closed on ambiguous historic intervals
        return rows.stream().findFirst();
    }
    @Override
    @Transactional(propagation=Propagation.MANDATORY)
    public List<Snapshot> history(String pointId,int limit) {
        if(pointId==null||limit<1||limit>51)throw denied();
        var result=jdbc.query(VIEW+" WHERE p.id=? ORDER BY b.valid_from DESC,b.id DESC "
                +"FETCH FIRST ? ROWS ONLY",MAPPER,pointId,limit);
        // A missing or duplicate tag mapping for a historic binding cannot be silently disclosed.
        return List.copyOf(result);
    }

    @Override
    @Transactional(propagation=Propagation.MANDATORY)
    public Snapshot enroll(Enroll c) {
        requiredNames(c);
        requiredCatalog(c.sourceTypeId(),"SOURCE_TYPE");
        requiredCatalog(c.protocolId(),"PROTOCOL");
        requiredCatalog(c.deviceTypeId(),"DEVICE_TYPE");
        requiredCatalog(c.bindingRoleId(),"BINDING_ROLE");
        eligiblePoint(c.pointTypeId(),c.signalTypeId(),c.unitId());
        String source=derive(c.requestId(),"source"),device=derive(c.requestId(),"device"),
               point=derive(c.requestId(),"point"),binding=derive(c.requestId(),"binding"),
               mapping=derive(c.requestId(),"mapping");
        if(Boolean.TRUE.equals(jdbc.queryForObject("SELECT EXISTS (SELECT 1 "
                +"FROM public.hidra_telemetry_source WHERE id=?)",Boolean.class,source))) {
            var prior=effective(point,c.validFrom()).orElseThrow(
                    JdbcTelemetryCommissioningPersistenceAdapter::conflict);
            if(!source.equals(prior.sourceId()) || !device.equals(prior.deviceId())
                    || !binding.equals(prior.bindingId()) || !mapping.equals(prior.mappingId())
                    || !Objects.equals(c.assetId(),prior.assetId())
                    || !Objects.equals(c.assetType(),prior.assetType())
                    || !Objects.equals(c.sourceCode(),prior.sourceCode())
                    || !Objects.equals(c.deviceCode(),prior.deviceCode())
                    || !Objects.equals(c.pointCode(),prior.pointCode())
                    || !Objects.equals(c.unitId(),prior.unitId())
                    || !Objects.equals(c.externalTagName(),prior.externalTagName())
                    || !Objects.equals(c.validFrom(),prior.validFrom())
                    || !Objects.equals(c.validUntil(),prior.validUntil()))
                throw conflict();
            return prior;
        }
        Instant now=Instant.now().truncatedTo(java.time.temporal.ChronoUnit.MICROS);
        jdbc.update("INSERT INTO public.hidra_telemetry_source "
                +"(id,code,name_fr,source_type_id,protocol_id,status,created_at,updated_at) "
                +"VALUES (?,?,?,?,?,'ACTIVE',?,?)",
                source,c.sourceCode(),c.sourceNameFr(),c.sourceTypeId(),c.protocolId(),
                Timestamp.from(now),Timestamp.from(now));
        jdbc.update("INSERT INTO public.hidra_telemetry_device "
                +"(id,source_id,code,name_fr,device_type_id,status,created_at,updated_at) "
                +"VALUES (?,?,?,?,?,'ACTIVE',?,?)",
                device,source,c.deviceCode(),c.deviceNameFr(),c.deviceTypeId(),
                Timestamp.from(now),Timestamp.from(now));
        jdbc.update("INSERT INTO public.hidra_telemetry_point "
                +"(id,device_id,code,name_fr,point_type_id,signal_type_id,unit_id,status,created_at,updated_at) "
                +"VALUES (?,?,?,?,?,?,?,'ACTIVE',?,?)",
                point,device,c.pointCode(),c.pointNameFr(),c.pointTypeId(),c.signalTypeId(),
                c.unitId(),Timestamp.from(now),Timestamp.from(now));
        insertBinding(binding,point,c.assetType(),c.assetId(),c.bindingRoleId(),
                c.validFrom(),c.validUntil(),now);
        insertMapping(mapping,source,device,point,c.externalTagName(),
                c.validFrom(),c.validUntil(),now);
        return effective(point,c.validFrom()).orElseThrow(JdbcTelemetryCommissioningPersistenceAdapter::denied);
    }

    @Override
    @Transactional(propagation=Propagation.MANDATORY)
    public Snapshot rebind(Rebind c) {
        // Pessimistic point lock serializes concurrent replacements of the same canonical point.
        boolean found=jdbc.query("SELECT id FROM public.hidra_telemetry_point WHERE id=? FOR UPDATE",
                rs -> { return rs.next(); },c.pointId());
        if(!found)throw denied();
        requiredCatalog(c.bindingRoleId(),"BINDING_ROLE");
        String binding=derive(c.requestId(),"binding"),mapping=derive(c.requestId(),"mapping");
        boolean replay=Boolean.TRUE.equals(jdbc.queryForObject(
                "SELECT EXISTS (SELECT 1 FROM public.hidra_telemetry_point_binding WHERE id=?)",
                Boolean.class,binding));
        if(replay) {
            var prior=effective(c.pointId(),c.validFrom())
                    .orElseThrow(JdbcTelemetryCommissioningPersistenceAdapter::conflict);
            if(!binding.equals(prior.bindingId()) || !mapping.equals(prior.mappingId())
                    || !Objects.equals(c.assetId(),prior.assetId())
                    || !Objects.equals(c.assetType(),prior.assetType())
                    || !Objects.equals(c.externalTagName(),prior.externalTagName())
                    || !Objects.equals(c.bindingRoleId(),prior.bindingRoleId())
                    || !Objects.equals(c.validFrom(),prior.validFrom())
                    || !Objects.equals(c.validUntil(),prior.validUntil()))
                throw conflict();
            return prior;
        }
        Instant now=Instant.now().truncatedTo(java.time.temporal.ChronoUnit.MICROS);
        if(c.validFrom().isBefore(now))throw conflict(); // no backdated asset reassignment
        var current=effective(c.pointId(),now).orElseThrow(
                JdbcTelemetryCommissioningPersistenceAdapter::denied);
        if(!current.effective() || current.validUntil()!=null
                || !c.validFrom().isAfter(current.validFrom()))
            throw conflict();
        // Any already-scheduled effective binding or mapping is a conflict.
        Integer otherBindings=jdbc.queryForObject("""
                SELECT count(*) FROM public.hidra_telemetry_point_binding
                WHERE point_id=? AND id<>? AND id<>? AND valid_from>=?
                """,Integer.class,c.pointId(),current.bindingId(),binding,Timestamp.from(now));
        if(otherBindings!=null&&otherBindings>0)throw conflict();
        Integer otherMappings=jdbc.queryForObject("""
                SELECT count(*) FROM public.hidra_telemetry_external_tag_mapping
                WHERE point_id=? AND id<>? AND id<>? AND valid_from>=?
                """,Integer.class,c.pointId(),current.mappingId(),mapping,Timestamp.from(now));
        if(otherMappings!=null&&otherMappings>0)throw conflict();
        jdbc.update("UPDATE public.hidra_telemetry_point_binding "
                +"SET valid_to=?, updated_at=? WHERE id=? AND valid_to IS NULL",
                Timestamp.from(c.validFrom()),Timestamp.from(now),current.bindingId());
        jdbc.update("UPDATE public.hidra_telemetry_external_tag_mapping "
                +"SET valid_to=?, updated_at=? WHERE id=? AND valid_to IS NULL",
                Timestamp.from(c.validFrom()),Timestamp.from(now),current.mappingId());
        insertBinding(binding,c.pointId(),c.assetType(),c.assetId(),c.bindingRoleId(),
                c.validFrom(),c.validUntil(),now);
        insertMapping(mapping,current.sourceId(),current.deviceId(),c.pointId(),
                c.externalTagName(),c.validFrom(),c.validUntil(),now);
        return effective(c.pointId(),c.validFrom())
                .orElseThrow(JdbcTelemetryCommissioningPersistenceAdapter::denied);
    }

    private void insertBinding(String id,String point,String type,String asset,String role,
                               Instant from,Instant until,Instant now) {
        jdbc.update("INSERT INTO public.hidra_telemetry_point_binding "
                +"(id,point_id,topology_asset_type_code,topology_asset_id,topology_asset_code,"
                +"binding_role_id,active,valid_from,valid_to,created_at,updated_at) "
                +"VALUES (?,?,?,?,?,?,true,?,?,?,?)",
                id,point,type,asset,asset,role,Timestamp.from(from),time(until),
                Timestamp.from(now),Timestamp.from(now));
    }
    private void insertMapping(String id,String source,String device,String point,String tag,
                               Instant from,Instant until,Instant now) {
        jdbc.update("INSERT INTO public.hidra_telemetry_external_tag_mapping "
                +"(id,source_id,device_id,point_id,external_tag_name,mapping_mode,active,"
                +"valid_from,valid_to,created_at,updated_at) VALUES (?,?,?,?,?,'DIRECT',true,?,?,?,?)",
                id,source,device,point,tag,Timestamp.from(from),time(until),
                Timestamp.from(now),Timestamp.from(now));
    }
    private static Timestamp time(Instant value) { return value==null?null:Timestamp.from(value); }
}
