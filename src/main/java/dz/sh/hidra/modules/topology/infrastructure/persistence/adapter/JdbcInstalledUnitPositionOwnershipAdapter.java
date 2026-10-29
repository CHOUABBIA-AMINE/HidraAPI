/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : JdbcInstalledUnitPositionOwnershipAdapter
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-29
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.infrastructure.persistence.adapter
 *
 * @Description : Reads guarded current equipment placement and direct facility ownership.
 *
 */
package dz.sh.hidra.modules.topology.infrastructure.persistence.adapter;

import dz.sh.hidra.modules.topology.application.port.out.InstalledUnitPositionOwnershipPort;
import dz.sh.hidra.modules.topology.domain.value.EquipmentStatus;
import dz.sh.hidra.modules.topology.domain.value.FacilityStatus;
import dz.sh.hidra.modules.topology.domain.value.FacilityKind;
import dz.sh.hidra.platform.persistence.ProtectedRelationFence;
import java.sql.Connection;
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;
import javax.sql.DataSource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.ConnectionHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.transaction.support.TransactionSynchronization;
import org.springframework.transaction.support.TransactionSynchronizationManager;

/** Observations only: no Identity grant, equipment-kind approval, writer or public disclosure. */
@Repository
public class JdbcInstalledUnitPositionOwnershipAdapter implements InstalledUnitPositionOwnershipPort {
    private static final String EQUIPMENT="public.hidra_topology_equipment";
    private static final String FACILITY="public.hidra_topology_facility";
    private static final String GENERATION="public.hidra_topology_facility_owner_generation";
    private static final String OWNER_LOCK="public.hidra_topology_lock_installed_unit_position_ownership()";
    private static final String GUARD="""
        SELECT pg_catalog.current_setting('transaction_isolation')='read committed'
          AND pg_catalog.current_setting('transaction_read_only')='off'
          AND pg_catalog.current_setting('session_replication_role')='origin'
          AND NOT pg_catalog.has_schema_privilege(current_user,'public','CREATE')
          AND NOT pg_catalog.has_schema_privilege(current_user,'pg_catalog','CREATE')
          AND NOT EXISTS (SELECT 1 FROM pg_catalog.pg_roles r
              WHERE (r.rolname=current_user OR pg_catalog.pg_has_role(current_user,r.oid,'MEMBER'))
                AND (r.rolsuper OR r.rolbypassrls))
          AND NOT EXISTS (SELECT 1 FROM pg_catalog.pg_class c
              WHERE c.oid IN (pg_catalog.to_regclass('public.hidra_topology_facility'),pg_catalog.to_regclass('public.hidra_topology_facility_owner_generation'))
                AND (c.relkind<>'r' OR c.relpersistence<>'p' OR c.relrowsecurity OR c.relforcerowsecurity
                  OR c.relispartition OR pg_catalog.pg_has_role(current_user,c.relowner,'MEMBER')))
          AND NOT EXISTS (SELECT 1 FROM pg_catalog.pg_inherits
              WHERE inhrelid IN (pg_catalog.to_regclass('public.hidra_topology_facility'),pg_catalog.to_regclass('public.hidra_topology_facility_owner_generation'))
                 OR inhparent IN (pg_catalog.to_regclass('public.hidra_topology_facility'),pg_catalog.to_regclass('public.hidra_topology_facility_owner_generation')))
          AND NOT pg_catalog.has_table_privilege(current_user,'public.hidra_topology_facility','TRIGGER')
          AND NOT pg_catalog.has_table_privilege(current_user,'public.hidra_topology_facility_owner_generation','INSERT,UPDATE,DELETE,TRUNCATE,REFERENCES,TRIGGER')
          AND NOT pg_catalog.has_any_column_privilege(current_user,'public.hidra_topology_facility_owner_generation','INSERT,UPDATE,REFERENCES')
          AND (SELECT pg_catalog.count(*)=3 AND pg_catalog.bool_and(attnotnull) AND
              pg_catalog.array_agg(attname::text ORDER BY attnum)=ARRAY['facility_id','generation','present'] AND
              pg_catalog.array_agg(atttypid::integer ORDER BY attnum)=ARRAY[1043,20,16] AND
              pg_catalog.array_agg(atttypmod ORDER BY attnum)=ARRAY[84,-1,-1]
              FROM pg_catalog.pg_attribute WHERE attrelid=pg_catalog.to_regclass('public.hidra_topology_facility_owner_generation') AND attnum>0 AND NOT attisdropped)
          AND (SELECT pg_catalog.count(*)=2 AND pg_catalog.count(*) FILTER (WHERE contype='p' AND convalidated AND NOT condeferrable AND conkey=ARRAY[1]::smallint[])=1
              AND pg_catalog.count(*) FILTER (WHERE contype='c' AND convalidated AND NOT condeferrable
                AND pg_catalog.pg_get_constraintdef(oid)='CHECK ((generation > 0))')=1
              FROM pg_catalog.pg_constraint WHERE conrelid=pg_catalog.to_regclass('public.hidra_topology_facility_owner_generation'))
          AND (SELECT pg_catalog.count(*)=2 AND pg_catalog.bool_and(prosecdef AND prokind='f' AND pronargs=0
              AND prorettype='trigger'::regtype AND prolang=(SELECT oid FROM pg_catalog.pg_language WHERE lanname='plpgsql')
              AND NOT proleakproof AND NOT proisstrict AND provolatile='v' AND proparallel='u'
              AND proconfig=ARRAY['search_path=pg_catalog, pg_temp']
              AND NOT pg_catalog.pg_has_role(current_user,proowner,'MEMBER') AND NOT pg_catalog.has_function_privilege(current_user,oid,'EXECUTE')
              AND ((oid=pg_catalog.to_regprocedure('public.hidra_p25_facility_owner_write()') AND pg_catalog.md5(prosrc)='69058c03426f10cba64e50b8bc322070')
                OR (oid=pg_catalog.to_regprocedure('public.hidra_p25_facility_owner_truncate()') AND pg_catalog.md5(prosrc)='b14fa2b5bab283c56945a701eff70251')))
              FROM pg_catalog.pg_proc WHERE oid IN (pg_catalog.to_regprocedure('public.hidra_p25_facility_owner_write()'),pg_catalog.to_regprocedure('public.hidra_p25_facility_owner_truncate()')))
          AND (SELECT pg_catalog.count(*)=3 AND pg_catalog.bool_and(tgenabled='O' AND tgqual IS NULL AND tgnargs=0 AND tgconstraint=0 AND tgparentid=0
              AND ((tgrelid=pg_catalog.to_regclass('public.hidra_topology_facility') AND tgname='trg_p25_facility_owner_write' AND tgtype=29
                    AND tgfoid=pg_catalog.to_regprocedure('public.hidra_p25_facility_owner_write()')
                    AND ARRAY(SELECT a.attname::text FROM pg_catalog.pg_attribute a WHERE a.attrelid=tgrelid
                              AND a.attnum=ANY(tgattr::smallint[]) ORDER BY a.attnum)=ARRAY['id','owner_party_id'])
                OR (tgrelid=pg_catalog.to_regclass('public.hidra_topology_facility') AND tgname='trg_p25_facility_owner_root_truncate' AND tgtype=34
                    AND tgfoid=pg_catalog.to_regprocedure('public.hidra_p25_facility_owner_truncate()') AND pg_catalog.cardinality(tgattr::smallint[])=0)
                OR (tgrelid=pg_catalog.to_regclass('public.hidra_topology_facility_owner_generation') AND tgname='trg_p25_facility_owner_registry_truncate' AND tgtype=34
                    AND tgfoid=pg_catalog.to_regprocedure('public.hidra_p25_facility_owner_truncate()') AND pg_catalog.cardinality(tgattr::smallint[])=0)))
              FROM pg_catalog.pg_trigger WHERE tgrelid IN (pg_catalog.to_regclass('public.hidra_topology_facility'),pg_catalog.to_regclass('public.hidra_topology_facility_owner_generation')) AND NOT tgisinternal)
          AND (SELECT pg_catalog.count(*)=3 AND pg_catalog.bool_and(
                c.relkind='r' AND c.relpersistence='p' AND NOT c.relrowsecurity
                AND NOT c.relforcerowsecurity AND NOT c.relispartition
                AND NOT pg_catalog.pg_has_role(current_user,c.relowner,'MEMBER')
                AND pg_catalog.has_table_privilege(current_user,c.oid,'SELECT')
                AND NOT pg_catalog.has_table_privilege(current_user,c.oid,'INSERT,UPDATE,DELETE,TRUNCATE,REFERENCES,TRIGGER')
                AND NOT pg_catalog.has_any_column_privilege(current_user,c.oid,'INSERT,UPDATE,REFERENCES'))
              FROM pg_catalog.pg_class c WHERE c.oid IN (
                pg_catalog.to_regclass('public.hidra_topology_equipment'),pg_catalog.to_regclass('public.hidra_topology_facility'),
                pg_catalog.to_regclass('public.hidra_topology_facility_owner_generation')))
          AND NOT EXISTS (SELECT 1 FROM pg_catalog.pg_inherits
              WHERE inhrelid=pg_catalog.to_regclass('public.hidra_topology_equipment') OR inhparent=pg_catalog.to_regclass('public.hidra_topology_equipment'))
        """;
    private static final String OWNER_LOCK_GUARD="""
        SELECT EXISTS (
          SELECT 1 FROM pg_catalog.pg_proc p
          JOIN pg_catalog.pg_namespace n ON n.oid=p.pronamespace
          JOIN pg_catalog.pg_language l ON l.oid=p.prolang
          JOIN pg_catalog.pg_roles o ON o.oid=p.proowner
          WHERE p.oid=pg_catalog.to_regprocedure('public.hidra_topology_lock_installed_unit_position_ownership()')
            AND n.nspname='public' AND p.proname='hidra_topology_lock_installed_unit_position_ownership'
            AND p.prokind='f' AND p.prosecdef AND NOT p.proretset
            AND NOT p.proisstrict AND NOT p.proleakproof
            AND p.pronargs=0 AND p.pronargdefaults=0 AND p.provariadic=0
            AND p.proargtypes=''::pg_catalog.oidvector
            AND p.proallargtypes IS NULL AND p.proargmodes IS NULL AND p.proargnames IS NULL
            AND p.prorettype='pg_catalog.void'::pg_catalog.regtype
            AND p.provolatile='v' AND p.proparallel='u'
            AND l.lanname='plpgsql' AND l.lanpltrusted
            AND p.proconfig=ARRAY['search_path=pg_catalog, pg_temp']::pg_catalog.text[]
            AND pg_catalog.encode(pg_catalog.sha256(pg_catalog.convert_to(p.prosrc,'UTF8')),'hex')='60466e68d8b31baff428ba3d3bfeb318b4f2217e74fcd926007c47da444fe603'
            AND NOT o.rolsuper AND NOT o.rolbypassrls AND NOT o.rolcanlogin
            AND NOT o.rolcreaterole AND NOT o.rolcreatedb AND NOT o.rolreplication
            AND NOT o.rolinherit
            AND NOT EXISTS (SELECT 1 FROM pg_catalog.pg_auth_members m WHERE m.member=p.proowner OR m.roleid=p.proowner)
            AND NOT pg_catalog.pg_has_role(current_user,p.proowner,'MEMBER')
            AND NOT pg_catalog.has_schema_privilege(o.oid,'public','CREATE')
            AND NOT pg_catalog.has_schema_privilege(o.oid,'pg_catalog','CREATE')
            AND (SELECT pg_catalog.count(*)=2 AND pg_catalog.bool_and(
                    c.relowner<>p.proowner
                    AND pg_catalog.has_table_privilege(o.oid,c.oid,'UPDATE')
                    AND NOT pg_catalog.has_table_privilege(o.oid,c.oid,'UPDATE WITH GRANT OPTION')
                    AND NOT pg_catalog.has_any_column_privilege(o.oid,c.oid,'UPDATE WITH GRANT OPTION')
                    AND NOT pg_catalog.has_table_privilege(o.oid,c.oid,'SELECT,INSERT,DELETE,TRUNCATE,REFERENCES,TRIGGER')
                    AND NOT pg_catalog.has_any_column_privilege(o.oid,c.oid,'SELECT,INSERT,REFERENCES'))
                 FROM pg_catalog.pg_class c WHERE c.oid IN (
                    pg_catalog.to_regclass('public.hidra_topology_equipment'),
                    pg_catalog.to_regclass('public.hidra_topology_facility')))
            AND pg_catalog.has_function_privilege(current_user,p.oid,'EXECUTE')
            AND EXISTS (
                SELECT 1 FROM pg_catalog.aclexplode(COALESCE(p.proacl,pg_catalog.acldefault('f',p.proowner))) a
                WHERE a.grantee=(SELECT oid FROM pg_catalog.pg_roles WHERE rolname=current_user)
                  AND a.privilege_type='EXECUTE' AND NOT a.is_grantable)
            AND NOT EXISTS (
                SELECT 1 FROM pg_catalog.aclexplode(COALESCE(p.proacl,pg_catalog.acldefault('f',p.proowner))) a
                WHERE a.grantee NOT IN (p.proowner,(SELECT oid FROM pg_catalog.pg_roles WHERE rolname=current_user))
                   OR a.privilege_type<>'EXECUTE' OR (a.grantee<>p.proowner AND a.is_grantable))
        )
        """;
    private static final String HELD="""
        SELECT EXISTS(SELECT 1 FROM pg_catalog.pg_locks WHERE pid=pg_catalog.pg_backend_pid() AND granted
          AND locktype='relation' AND relation=pg_catalog.to_regclass('public.hidra_topology_equipment') AND mode='ShareRowExclusiveLock')
          AND EXISTS(SELECT 1 FROM pg_catalog.pg_locks WHERE pid=pg_catalog.pg_backend_pid() AND granted
          AND locktype='relation' AND relation=pg_catalog.to_regclass('public.hidra_topology_facility') AND mode='ShareRowExclusiveLock')
          AND EXISTS(SELECT 1 FROM pg_catalog.pg_locks WHERE pid=pg_catalog.pg_backend_pid() AND granted
          AND locktype='relation' AND relation=pg_catalog.to_regclass('public.hidra_topology_facility_owner_generation') AND mode='AccessShareLock')
        """;
    private static final String EVIDENCE="""
        SELECT e.id AS position_id,e.equipment_type_id,e.node_id,e.pipeline_segment_id,e.status AS equipment_status,
          f.id AS facility_id,f.owner_party_id,f.status AS facility_status,f.facility_kind,g.generation,g.present,
          pg_catalog.encode(pg_catalog.sha256(pg_catalog.convert_to(pg_catalog.row_to_json(e)::text,'UTF8')),'hex') AS position_image,
          pg_catalog.encode(pg_catalog.sha256(pg_catalog.convert_to(pg_catalog.row_to_json(f)::text,'UTF8')),'hex') AS facility_image
        FROM public.hidra_topology_equipment e
        JOIN public.hidra_topology_facility f ON f.id=e.facility_id
        JOIN public.hidra_topology_facility_owner_generation g ON g.facility_id=f.id
        WHERE e.id=? AND e.tableoid=pg_catalog.to_regclass('public.hidra_topology_equipment')
          AND f.tableoid=pg_catalog.to_regclass('public.hidra_topology_facility')
          AND g.tableoid=pg_catalog.to_regclass('public.hidra_topology_facility_owner_generation')
        """;
    private final JdbcTemplate jdbc;
    private final DataSource source;
    public JdbcInstalledUnitPositionOwnershipAdapter(JdbcTemplate jdbc) {
        this.jdbc=Objects.requireNonNull(jdbc);this.source=Objects.requireNonNull(jdbc.getDataSource());
    }
    @Override @Transactional(propagation=Propagation.MANDATORY)
    public Admission acquire(String exactPositionId) {
        try {
            String id=exact(exactPositionId);var holder=context();checkCatalog();
            // Admission SELECTs must use executeQuery; consume the helper's single void row.
            jdbc.queryForObject("SELECT " + OWNER_LOCK, (row,n) -> Boolean.TRUE);
            jdbc.execute("LOCK TABLE "+GENERATION+" IN ACCESS SHARE MODE NOWAIT");
            checkCatalog();check(HELD);
            var ticket=jdbc.queryForObject("SELECT pg_catalog.pg_backend_pid(),pg_catalog.txid_current(),current_user",
                (row,n)->new Ticket(id,holder,row.getInt(1),row.getLong(2),row.getString(3)));
            if(ticket==null)throw invalid();
            TransactionSynchronizationManager.registerSynchronization(ticket);return ticket;
        } catch(RuntimeException failure){poison(null);throw failure;}
    }
    // Check context inside ticket-consuming bodies so foreign-thread misuse can poison the issuing holder.
    @Override
    public Contribution read(Admission admission) {
        try {
            var ticket=ticket(admission);
            if(!ticket.used.compareAndSet(false,true))throw invalid();
            var image=image(ticket);ticket.image=image;
            ticket(admission);checkCatalog();check(HELD);return image.contribution();
        }catch(RuntimeException failure){poison(admission);throw failure;}
    }
    @Override
    public void requireCurrent(Admission admission) {
        try {
            var ticket=ticket(admission);
            if(ticket.image==null || !ticket.image.equals(image(ticket)))throw invalid();
            ticket(admission);checkCatalog();check(HELD);
        }catch(RuntimeException failure){poison(admission);throw failure;}
    }
    @Override
    public List<ProtectedRelationFence> finalFences(Admission admission) {
        try {
            ticket(admission);checkCatalog();check(HELD);
            return List.of(new ProtectedRelationFence(EQUIPMENT,ProtectedRelationFence.Mode.SHARE_ROW_EXCLUSIVE),
                new ProtectedRelationFence(FACILITY,ProtectedRelationFence.Mode.SHARE_ROW_EXCLUSIVE),
                new ProtectedRelationFence(GENERATION,ProtectedRelationFence.Mode.ACCESS_SHARE));
        }catch(RuntimeException failure){poison(admission);throw failure;}
    }
    private Image image(Ticket ticket) {
        checkCatalog();check(HELD);
        var rows=jdbc.query(EVIDENCE,(row,n)->{
            if(!ticket.id.equals(row.getString("position_id")) || row.getString("node_id")!=null || row.getString("pipeline_segment_id")!=null
                || !row.getBoolean("present") || row.getLong("generation")<=0)throw invalid();
            String es=EquipmentStatus.valueOf(row.getString("equipment_status")).name();
            String fs=FacilityStatus.valueOf(row.getString("facility_status")).name();
            String kind=FacilityKind.valueOf(row.getString("facility_kind")).name();
            var c=new Contribution(ticket.id,exact(row.getString("equipment_type_id")),exact(row.getString("facility_id")),
                exact(row.getString("owner_party_id")),es,fs,kind,row.getLong("generation"),ticket.pid,ticket.xid,ticket.role);
            String p=row.getString("position_image"),f=row.getString("facility_image");
            if(p==null || f==null || !p.matches("[0-9a-f]{64}") || !f.matches("[0-9a-f]{64}"))throw invalid();
            return new Image(c,p,f);
        },ticket.id);
        if(rows.size()!=1)throw invalid();return rows.get(0);
    }
    private record Image(Contribution contribution,String positionImage,String facilityImage) {}
    private Ticket ticket(Admission admission) {
        var holder=context();
        if(!(admission instanceof Ticket t) || t.issuer!=this || !t.active || t.thread!=Thread.currentThread()
            || t.holder!=holder || t.connection!=holder.getConnection())throw invalid();
        if(!Boolean.TRUE.equals(jdbc.queryForObject("SELECT pg_catalog.pg_backend_pid()=? AND pg_catalog.txid_current()=? AND current_user=?",
            Boolean.class,t.pid,t.xid,t.role)))throw invalid();return t;
    }
    private ConnectionHolder context() {
        if(!TransactionSynchronizationManager.isActualTransactionActive() || !TransactionSynchronizationManager.isSynchronizationActive()
            || TransactionSynchronizationManager.isCurrentTransactionReadOnly()
            || !(TransactionSynchronizationManager.getResource(source) instanceof ConnectionHolder holder) || holder.isRollbackOnly())throw invalid();
        return holder;
    }
    private void checkCatalog() {
        String schemas=jdbc.queryForObject("SELECT pg_catalog.array_to_json(pg_catalog.current_schemas(true))::pg_catalog.text",String.class);
        if(schemas==null || !(schemas.equals("[\"pg_catalog\"]") || schemas.startsWith("[\"pg_catalog\",")))throw invalid();
        check(GUARD);check(OWNER_LOCK_GUARD);
    }
    private void check(String sql) { if(!Boolean.TRUE.equals(jdbc.queryForObject(sql,Boolean.class)))throw invalid(); }
    private static String exact(String id) {
        if(id==null || id.isBlank() || !id.equals(id.strip()) || id.codePointCount(0,id.length())>80)
            throw new IllegalArgumentException("Exact bounded position/owner identity required");
        return id;
    }
    private static IllegalStateException invalid() { return new IllegalStateException("Protected installed-unit position ownership unavailable"); }
    private void poison(Admission candidate) {
        if(candidate instanceof Ticket t){t.active=false;rollbackHolder(t.holder);}
        if(TransactionSynchronizationManager.getResource(source) instanceof ConnectionHolder h)rollbackHolder(h);
        try{TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();}
        catch(org.springframework.transaction.NoTransactionException absent){/* TransactionTemplate caller. */}
    }
    private static void rollbackHolder(ConnectionHolder holder) {
        holder.setRollbackOnly();
        if(TransactionSynchronizationManager.isSynchronizationActive()
            && TransactionSynchronizationManager.getResourceMap().containsValue(holder))
            TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronization(){
                @Override public void beforeCommit(boolean readOnly){if(holder.isRollbackOnly())throw invalid();}
            });
    }
    private final class Ticket implements Admission,TransactionSynchronization {
        private final JdbcInstalledUnitPositionOwnershipAdapter issuer=JdbcInstalledUnitPositionOwnershipAdapter.this;
        private final String id,role;
        private final ConnectionHolder holder;
        private final Connection connection;
        private final Thread thread=Thread.currentThread();
        private final int pid;
        private final long xid;
        private final AtomicBoolean used=new AtomicBoolean();
        private volatile boolean active=true;
        private Image image;
        private Ticket(String id,ConnectionHolder holder,int pid,long xid,String role){
            this.id=id;this.holder=holder;this.connection=holder.getConnection();this.pid=pid;this.xid=xid;this.role=role;
        }
        @Override public void suspend(){active=false;}
        @Override public void afterCompletion(int status){active=false;}
        @Override public void beforeCommit(boolean readOnly){if(!active || holder.isRollbackOnly())throw invalid();}
        @Override public String toString(){return "Admission[opaque Topology position]";}
    }
}
