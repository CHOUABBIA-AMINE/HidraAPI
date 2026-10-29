/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : JdbcTopologyInstalledUnitCompanyEvidenceAdapter
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-29
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : party
 * @Package     : dz.sh.hidra.modules.party.infrastructure.persistence.adapter
 *
 * @Description : Reads current company evidence with SELECT-only credentials and a fixed Party lock owner.
 *
 */
package dz.sh.hidra.modules.party.infrastructure.persistence.adapter;

import dz.sh.hidra.modules.party.application.contract.topology.TopologyInstalledUnitCompanyEvidenceContract;
import dz.sh.hidra.modules.party.domain.value.PartyStatus;
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

/** Current company existence/status only. Manufacturer and Identity authority remain independent. */
@Repository
public class JdbcTopologyInstalledUnitCompanyEvidenceAdapter implements TopologyInstalledUnitCompanyEvidenceContract {
    private static final String PARTY="public.hidra_party_party",GENERATION="public.hidra_party_eligibility_generation";
    private static final String ROLE="public.hidra_party_role",ASSIGNMENT="public.hidra_party_role_assignment",
        PROFILE="public.hidra_party_manufacturer_profile";
    private static final String OWNER_LOCK="public.hidra_party_lock_installed_unit_manufacturer_evidence()";
    private static final String GUARD="""
        SELECT pg_catalog.current_setting('transaction_isolation')='read committed'
          AND pg_catalog.current_setting('transaction_read_only')='off'
          AND pg_catalog.current_setting('session_replication_role')='origin'
          AND NOT pg_catalog.has_schema_privilege(current_user,'public','CREATE')
          AND NOT pg_catalog.has_schema_privilege(current_user,'pg_catalog','CREATE')
          AND NOT EXISTS (SELECT 1 FROM pg_catalog.pg_roles r
              WHERE (r.rolname=current_user OR pg_catalog.pg_has_role(current_user,r.oid,'MEMBER'))
                AND (r.rolsuper OR r.rolbypassrls OR r.rolcreaterole OR r.rolcreatedb OR r.rolreplication))
          AND NOT EXISTS (SELECT 1 FROM pg_catalog.pg_class c
              WHERE c.oid IN (pg_catalog.to_regclass('public.hidra_party_party'),pg_catalog.to_regclass('public.hidra_party_eligibility_generation'))
                AND (c.relkind<>'r' OR c.relpersistence<>'p' OR c.relrowsecurity OR c.relforcerowsecurity
                  OR c.relispartition OR pg_catalog.pg_has_role(current_user,c.relowner,'MEMBER')))
          AND NOT EXISTS (SELECT 1 FROM pg_catalog.pg_inherits
              WHERE inhrelid IN (pg_catalog.to_regclass('public.hidra_party_party'),pg_catalog.to_regclass('public.hidra_party_eligibility_generation'))
                 OR inhparent IN (pg_catalog.to_regclass('public.hidra_party_party'),pg_catalog.to_regclass('public.hidra_party_eligibility_generation')))
          AND NOT pg_catalog.has_table_privilege(current_user,'public.hidra_party_party','TRIGGER')
          AND NOT pg_catalog.has_table_privilege(current_user,'public.hidra_party_eligibility_generation','INSERT,UPDATE,DELETE,TRUNCATE,REFERENCES,TRIGGER')
          AND NOT pg_catalog.has_any_column_privilege(current_user,'public.hidra_party_eligibility_generation','INSERT,UPDATE,REFERENCES')
          AND (SELECT pg_catalog.count(*)=3 AND pg_catalog.bool_and(attnotnull) AND
              pg_catalog.array_agg(attname::text ORDER BY attnum)=ARRAY['party_id','generation','present'] AND
              pg_catalog.array_agg(atttypid::integer ORDER BY attnum)=ARRAY[1043,20,16] AND
              pg_catalog.array_agg(atttypmod ORDER BY attnum)=ARRAY[84,-1,-1]
              FROM pg_catalog.pg_attribute WHERE attrelid=pg_catalog.to_regclass('public.hidra_party_eligibility_generation') AND attnum>0 AND NOT attisdropped)
          AND (SELECT pg_catalog.count(*)=2 AND pg_catalog.count(*) FILTER (WHERE contype='p' AND convalidated AND NOT condeferrable AND conkey=ARRAY[1]::smallint[])=1
              AND pg_catalog.count(*) FILTER (WHERE contype='c' AND convalidated AND NOT condeferrable
                AND pg_catalog.pg_get_constraintdef(oid)='CHECK ((generation > 0))')=1
              FROM pg_catalog.pg_constraint WHERE conrelid=pg_catalog.to_regclass('public.hidra_party_eligibility_generation'))
          AND (SELECT pg_catalog.count(*)=2 AND pg_catalog.bool_and(prosecdef AND prokind='f' AND pronargs=0
              AND prorettype='trigger'::regtype AND prolang=(SELECT oid FROM pg_catalog.pg_language WHERE lanname='plpgsql')
              AND NOT proleakproof AND NOT proisstrict AND provolatile='v' AND proparallel='u'
              AND proconfig=ARRAY['search_path=pg_catalog, pg_temp']
              AND NOT pg_catalog.pg_has_role(current_user,proowner,'MEMBER') AND NOT pg_catalog.has_function_privilege(current_user,oid,'EXECUTE')
              AND ((oid=pg_catalog.to_regprocedure('public.hidra_p25_party_eligibility_write()') AND pg_catalog.md5(prosrc)='6aa06c78c97082be80c99071b7504f4f')
                OR (oid=pg_catalog.to_regprocedure('public.hidra_p25_party_eligibility_truncate()') AND pg_catalog.md5(prosrc)='c2a48183395f90cd23e2dbe84d424d01')))
              FROM pg_catalog.pg_proc WHERE oid IN (pg_catalog.to_regprocedure('public.hidra_p25_party_eligibility_write()'),pg_catalog.to_regprocedure('public.hidra_p25_party_eligibility_truncate()')))
          AND (SELECT pg_catalog.count(*)=3 AND pg_catalog.bool_and(tgenabled='O' AND tgqual IS NULL AND tgnargs=0 AND tgconstraint=0 AND tgparentid=0
              AND ((tgrelid=pg_catalog.to_regclass('public.hidra_party_party') AND tgname='trg_p25_party_eligibility_write' AND tgtype=29
                    AND tgfoid=pg_catalog.to_regprocedure('public.hidra_p25_party_eligibility_write()')
                    AND ARRAY(SELECT a.attname::text FROM pg_catalog.pg_attribute a WHERE a.attrelid=tgrelid
                              AND a.attnum=ANY(tgattr::smallint[]) ORDER BY a.attnum)=ARRAY['id','status'])
                OR (tgrelid=pg_catalog.to_regclass('public.hidra_party_party') AND tgname='trg_p25_party_eligibility_root_truncate' AND tgtype=34
                    AND tgfoid=pg_catalog.to_regprocedure('public.hidra_p25_party_eligibility_truncate()') AND pg_catalog.cardinality(tgattr::smallint[])=0)
                OR (tgrelid=pg_catalog.to_regclass('public.hidra_party_eligibility_generation') AND tgname='trg_p25_party_eligibility_registry_truncate' AND tgtype=34
                    AND tgfoid=pg_catalog.to_regprocedure('public.hidra_p25_party_eligibility_truncate()') AND pg_catalog.cardinality(tgattr::smallint[])=0)))
              FROM pg_catalog.pg_trigger WHERE tgrelid IN (pg_catalog.to_regclass('public.hidra_party_party'),pg_catalog.to_regclass('public.hidra_party_eligibility_generation')) AND NOT tgisinternal)
                  AND (SELECT pg_catalog.count(*)=5 AND pg_catalog.bool_and(
              c.relkind='r' AND c.relpersistence='p' AND NOT c.relrowsecurity
              AND NOT c.relforcerowsecurity AND NOT c.relispartition
              AND NOT pg_catalog.pg_has_role(current_user,c.relowner,'MEMBER')
              AND pg_catalog.has_table_privilege(current_user,c.oid,'SELECT')
              AND NOT pg_catalog.has_table_privilege(current_user,c.oid,'INSERT,UPDATE,DELETE,TRUNCATE,REFERENCES,TRIGGER')
              AND NOT pg_catalog.has_any_column_privilege(current_user,c.oid,'INSERT,UPDATE,REFERENCES'))
            FROM pg_catalog.pg_class c WHERE c.oid IN (
              pg_catalog.to_regclass('public.hidra_party_party'),
              pg_catalog.to_regclass('public.hidra_party_eligibility_generation'),
              pg_catalog.to_regclass('public.hidra_party_role'),
              pg_catalog.to_regclass('public.hidra_party_role_assignment'),
              pg_catalog.to_regclass('public.hidra_party_manufacturer_profile')))
          AND NOT EXISTS (SELECT 1 FROM pg_catalog.pg_inherits WHERE inhrelid IN (
              pg_catalog.to_regclass('public.hidra_party_role'),pg_catalog.to_regclass('public.hidra_party_role_assignment'),
              pg_catalog.to_regclass('public.hidra_party_manufacturer_profile')) OR inhparent IN (
              pg_catalog.to_regclass('public.hidra_party_role'),pg_catalog.to_regclass('public.hidra_party_role_assignment'),
              pg_catalog.to_regclass('public.hidra_party_manufacturer_profile')))
        """;
    private static final String OWNER_LOCK_GUARD="""
        SELECT EXISTS (
          SELECT 1 FROM pg_catalog.pg_proc p
          JOIN pg_catalog.pg_namespace n ON n.oid=p.pronamespace
          JOIN pg_catalog.pg_language l ON l.oid=p.prolang
          JOIN pg_catalog.pg_roles o ON o.oid=p.proowner
          WHERE p.oid=pg_catalog.to_regprocedure('public.hidra_party_lock_installed_unit_manufacturer_evidence()')
            AND n.nspname='public' AND p.proname='hidra_party_lock_installed_unit_manufacturer_evidence'
            AND p.prokind='f' AND p.prosecdef AND NOT p.proretset
            AND NOT p.proisstrict AND NOT p.proleakproof
            AND p.pronargs=0 AND p.pronargdefaults=0 AND p.provariadic=0
            AND p.proargtypes=''::pg_catalog.oidvector
            AND p.proallargtypes IS NULL AND p.proargmodes IS NULL AND p.proargnames IS NULL
            AND p.prorettype='pg_catalog.void'::pg_catalog.regtype
            AND p.provolatile='v' AND p.proparallel='u'
            AND l.lanname='plpgsql' AND l.lanpltrusted
            AND p.proconfig=ARRAY['search_path=pg_catalog, pg_temp']::pg_catalog.text[]
            AND pg_catalog.encode(pg_catalog.sha256(pg_catalog.convert_to(p.prosrc,'UTF8')),'hex')='81b4ac0abd37fca0f0b539e59ac5d0ea1a3c21217209be362248275bd7f15da1'
            AND NOT o.rolsuper AND NOT o.rolbypassrls AND NOT o.rolcanlogin
            AND NOT o.rolcreaterole AND NOT o.rolcreatedb AND NOT o.rolreplication
            AND NOT o.rolinherit
            AND NOT EXISTS (SELECT 1 FROM pg_catalog.pg_auth_members m WHERE m.member=p.proowner OR m.roleid=p.proowner)
            AND NOT pg_catalog.pg_has_role(current_user,p.proowner,'MEMBER')
            AND NOT pg_catalog.has_schema_privilege(o.oid,'public','CREATE')
            AND NOT pg_catalog.has_schema_privilege(o.oid,'pg_catalog','CREATE')
            AND NOT pg_catalog.pg_has_role(o.oid,(SELECT relowner FROM pg_catalog.pg_class
                WHERE oid=pg_catalog.to_regclass('public.hidra_party_eligibility_generation')),'MEMBER')
            AND NOT pg_catalog.has_table_privilege(o.oid,pg_catalog.to_regclass('public.hidra_party_eligibility_generation'),
                'SELECT,INSERT,UPDATE,DELETE,TRUNCATE,REFERENCES,TRIGGER')
            AND NOT pg_catalog.has_any_column_privilege(o.oid,pg_catalog.to_regclass('public.hidra_party_eligibility_generation'),
                'SELECT,INSERT,UPDATE,REFERENCES')
            AND (SELECT pg_catalog.count(*)=4 AND pg_catalog.bool_and(
                    c.relowner<>p.proowner
                    AND pg_catalog.has_table_privilege(o.oid,c.oid,'UPDATE')
                    AND NOT pg_catalog.has_table_privilege(o.oid,c.oid,'UPDATE WITH GRANT OPTION')
                    AND NOT pg_catalog.has_any_column_privilege(o.oid,c.oid,'UPDATE WITH GRANT OPTION')
                    AND NOT pg_catalog.has_table_privilege(o.oid,c.oid,'SELECT,INSERT,DELETE,TRUNCATE,REFERENCES,TRIGGER')
                    AND NOT pg_catalog.has_any_column_privilege(o.oid,c.oid,'SELECT,INSERT,REFERENCES'))
                 FROM pg_catalog.pg_class c WHERE c.oid IN (
                    pg_catalog.to_regclass('public.hidra_party_party'),
                    pg_catalog.to_regclass('public.hidra_party_role'),
                    pg_catalog.to_regclass('public.hidra_party_role_assignment'),
                    pg_catalog.to_regclass('public.hidra_party_manufacturer_profile')))
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
        SELECT
          EXISTS (SELECT 1 FROM pg_catalog.pg_locks WHERE pid=pg_catalog.pg_backend_pid()
              AND granted AND locktype='relation' AND relation=pg_catalog.to_regclass('public.hidra_party_party')
              AND mode='ShareRowExclusiveLock')
          AND EXISTS (SELECT 1 FROM pg_catalog.pg_locks WHERE pid=pg_catalog.pg_backend_pid()
              AND granted AND locktype='relation' AND relation=pg_catalog.to_regclass('public.hidra_party_role')
              AND mode='ShareRowExclusiveLock')
          AND EXISTS (SELECT 1 FROM pg_catalog.pg_locks WHERE pid=pg_catalog.pg_backend_pid()
              AND granted AND locktype='relation' AND relation=pg_catalog.to_regclass('public.hidra_party_role_assignment')
              AND mode='ShareRowExclusiveLock')
          AND EXISTS (SELECT 1 FROM pg_catalog.pg_locks WHERE pid=pg_catalog.pg_backend_pid()
              AND granted AND locktype='relation' AND relation=pg_catalog.to_regclass('public.hidra_party_manufacturer_profile')
              AND mode='ShareRowExclusiveLock')
          AND EXISTS (SELECT 1 FROM pg_catalog.pg_locks WHERE pid=pg_catalog.pg_backend_pid()
              AND granted AND locktype='relation' AND relation=pg_catalog.to_regclass('public.hidra_party_eligibility_generation')
              AND mode='AccessShareLock')
                """;
    private static final String EVIDENCE="""
        SELECT p.id,p.status,g.generation,g.present,
          pg_catalog.encode(pg_catalog.sha256(pg_catalog.convert_to(pg_catalog.row_to_json(p)::text,'UTF8')),'hex') AS party_image,
          pg_catalog.encode(pg_catalog.sha256(pg_catalog.convert_to(pg_catalog.row_to_json(g)::text,'UTF8')),'hex') AS generation_image
        FROM public.hidra_party_party p JOIN public.hidra_party_eligibility_generation g ON g.party_id=p.id
        WHERE p.id=? AND p.tableoid=pg_catalog.to_regclass('public.hidra_party_party')
          AND g.tableoid=pg_catalog.to_regclass('public.hidra_party_eligibility_generation')
        """;
    private final JdbcTemplate jdbc;
    private final DataSource source;
    public JdbcTopologyInstalledUnitCompanyEvidenceAdapter(JdbcTemplate jdbc) {
        this.jdbc=Objects.requireNonNull(jdbc);source=Objects.requireNonNull(jdbc.getDataSource());
    }
    @Override @Transactional(propagation=Propagation.MANDATORY)
    public Admission acquire(String exactCompanyPartyId) {
        try {
            String id=exact(exactCompanyPartyId);var holder=context();checkCatalog();
            // The fixed helper alone takes strong source locks. Reader has SELECT and explicit EXECUTE only.
            // Admission SELECTs must use executeQuery; consume the helper's single void row.
            jdbc.queryForObject("SELECT " + OWNER_LOCK, (row,n) -> Boolean.TRUE);
            jdbc.execute("LOCK TABLE "+GENERATION+" IN ACCESS SHARE MODE NOWAIT");
            checkCatalog();check(HELD);
            var ticket=jdbc.queryForObject("SELECT pg_catalog.pg_backend_pid(),pg_catalog.txid_current(),current_user",
                (row,n)->new Ticket(id,holder,row.getInt(1),row.getLong(2),row.getString(3)));
            if(ticket==null)throw invalid();
            TransactionSynchronizationManager.registerSynchronization(ticket);return ticket;
        }catch(RuntimeException failure){poison(null);throw failure;}
    }
    // Body checks are intentional: an interceptor must not bypass original-holder invalidation on a foreign thread.
    @Override public Contribution read(Admission admission) {
        try {
            var ticket=ticket(admission);
            if(!ticket.used.compareAndSet(false,true))throw invalid();
            checkCatalog();check(HELD);var image=image(ticket);ticket.image=image;
            ticket(admission);checkCatalog();check(HELD);return image.contribution();
        }catch(RuntimeException failure){poison(admission);throw failure;}
    }
    @Override public void requireCurrent(Admission admission) {
        try {
            var ticket=ticket(admission);checkCatalog();check(HELD);
            if(ticket.image==null || !ticket.image.equals(image(ticket)))throw invalid();
            ticket(admission);checkCatalog();check(HELD);
        }catch(RuntimeException failure){poison(admission);throw failure;}
    }
    @Override public List<ProtectedRelationFence> finalFences(Admission admission) {
        try {
            ticket(admission);checkCatalog();check(HELD);
            return List.of(new ProtectedRelationFence(PARTY,ProtectedRelationFence.Mode.SHARE_ROW_EXCLUSIVE),
                new ProtectedRelationFence(ROLE,ProtectedRelationFence.Mode.SHARE_ROW_EXCLUSIVE),
                new ProtectedRelationFence(ASSIGNMENT,ProtectedRelationFence.Mode.SHARE_ROW_EXCLUSIVE),
                new ProtectedRelationFence(PROFILE,ProtectedRelationFence.Mode.SHARE_ROW_EXCLUSIVE),
                new ProtectedRelationFence(GENERATION,ProtectedRelationFence.Mode.ACCESS_SHARE));
        }catch(RuntimeException failure){poison(admission);throw failure;}
    }
    private Image image(Ticket ticket) {
        var rows=jdbc.query(EVIDENCE,(row,n)->{
            if(!ticket.id.equals(row.getString("id")) || !row.getBoolean("present") || row.getLong("generation")<=0
                || PartyStatus.valueOf(row.getString("status"))!=PartyStatus.ACTIVE)throw invalid();
            var contribution=new Contribution(ticket.id,"ACTIVE",row.getLong("generation"),ticket.pid,ticket.xid,ticket.role);
            String root=row.getString("party_image"),generation=row.getString("generation_image");
            if(root==null || generation==null || !root.matches("[0-9a-f]{64}") || !generation.matches("[0-9a-f]{64}"))throw invalid();
            return new Image(contribution,root,generation);
        },ticket.id);
        if(rows.size()!=1)throw invalid();return rows.get(0);
    }
    private record Image(Contribution contribution,String partyImage,String generationImage) {}
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
        try {
            Connection c=holder.getConnection();
            if(c.getAutoCommit() || c.isReadOnly() || c.getTransactionIsolation()!=Connection.TRANSACTION_READ_COMMITTED)throw invalid();
        }catch(java.sql.SQLException failure){throw invalid();}
        return holder;
    }
    private void checkCatalog() {
        String schemas=jdbc.queryForObject("SELECT pg_catalog.array_to_json(pg_catalog.current_schemas(true))::pg_catalog.text",String.class);
        if(schemas==null || !(schemas.equals("[\"pg_catalog\"]") || schemas.startsWith("[\"pg_catalog\",")))throw invalid();
        check(GUARD);check(OWNER_LOCK_GUARD);
    }
    private void check(String sql){if(!Boolean.TRUE.equals(jdbc.queryForObject(sql,Boolean.class)))throw invalid();}
    private static String exact(String id) {
        if(id==null || id.isBlank() || !id.equals(id.strip()) || id.codePointCount(0,id.length())>80)
            throw new IllegalArgumentException("Exact bounded company Party identity required");
        return id;
    }
    private static IllegalStateException invalid(){return new IllegalStateException("Protected installed-unit company evidence unavailable");}
    private void poison(Admission candidate) {
        if(candidate instanceof Ticket t){t.active=false;t.holder.setRollbackOnly();}
        if(TransactionSynchronizationManager.getResource(source) instanceof ConnectionHolder holder)holder.setRollbackOnly();
        try{TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();}
        catch(org.springframework.transaction.NoTransactionException absent){/* TransactionTemplate or foreign-thread caller. */}
    }
    private final class Ticket implements Admission,TransactionSynchronization {
        private final JdbcTopologyInstalledUnitCompanyEvidenceAdapter issuer=JdbcTopologyInstalledUnitCompanyEvidenceAdapter.this;
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
            this.id=id;this.holder=holder;connection=holder.getConnection();this.pid=pid;this.xid=xid;this.role=role;
        }
        @Override public void suspend(){active=false;holder.setRollbackOnly();}
        @Override public void afterCompletion(int status){active=false;}
        @Override public void beforeCommit(boolean readOnly){
            // Deliberately SQL-free; a failure caught by user code must never allow commit.
            if(!active || holder.isRollbackOnly() || thread!=Thread.currentThread()
                || TransactionSynchronizationManager.getResource(source)!=holder)throw invalid();
        }
        @Override public String toString(){return "Admission[opaque Party company]";}
    }
}
