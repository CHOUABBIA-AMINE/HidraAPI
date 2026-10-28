/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : JdbcTopologyInstalledUnitManufacturerEvidenceAdapter
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-28
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : party
 * @Package     : dz.sh.hidra.modules.party.infrastructure.persistence.adapter
 *
 * @Description : Reads transaction-fenced manufacturer role and qualification evidence owned by Party.
 *
 */
package dz.sh.hidra.modules.party.infrastructure.persistence.adapter;

import dz.sh.hidra.modules.party.application.contract.topology.TopologyInstalledUnitManufacturerEvidenceContract;
import dz.sh.hidra.modules.party.domain.value.ManufacturerCapabilityType;
import dz.sh.hidra.modules.party.domain.value.PartyCatalogStatus;
import dz.sh.hidra.modules.party.domain.value.PartyRoleAssignmentStatus;
import dz.sh.hidra.modules.party.domain.value.PartyStatus;
import dz.sh.hidra.modules.party.domain.value.QualificationStatus;
import dz.sh.hidra.platform.persistence.ProtectedRelationFence;
import java.sql.Connection;
import java.time.Instant;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Objects;
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

/**
 * Protected, query-only Party evidence. The manufacturer role label is a lexical
 * filter, not a grant. Company/position permission, model qualification and
 * equipment-kind compatibility remain separately guarded Package B concerns.
 */
@Repository
public class JdbcTopologyInstalledUnitManufacturerEvidenceAdapter
        implements TopologyInstalledUnitManufacturerEvidenceContract {
    private static final String PARTY = "public.hidra_party_party";
    private static final String GENERATION = "public.hidra_party_eligibility_generation";
    private static final String ROLE = "public.hidra_party_role";
    private static final String ASSIGNMENT = "public.hidra_party_role_assignment";
    private static final String PROFILE = "public.hidra_party_manufacturer_profile";

    private static final String GUARD = """
        SELECT pg_catalog.current_setting('transaction_isolation')='read committed'
          AND pg_catalog.current_setting('transaction_read_only')='off'
          AND pg_catalog.current_setting('session_replication_role')='origin'
          AND NOT pg_catalog.has_schema_privilege(current_user,'public','CREATE')
          AND NOT pg_catalog.has_schema_privilege(current_user,'pg_catalog','CREATE')
          AND NOT EXISTS (
              SELECT 1 FROM pg_catalog.pg_roles r
              WHERE (r.rolname=current_user OR pg_catalog.pg_has_role(current_user,r.oid,'MEMBER'))
                AND (r.rolsuper OR r.rolbypassrls))
          AND (SELECT pg_catalog.count(*)=5 AND pg_catalog.bool_and(
                       c.relkind='r' AND c.relpersistence='p'
                       AND NOT c.relrowsecurity AND NOT c.relforcerowsecurity
                       AND NOT c.relispartition
                       AND NOT pg_catalog.pg_has_role(current_user,c.relowner,'MEMBER')
                       AND NOT pg_catalog.has_table_privilege(current_user,c.oid,'TRIGGER'))
              FROM pg_catalog.pg_class c
              WHERE c.oid IN (
                  'public.hidra_party_party'::regclass,
                  'public.hidra_party_eligibility_generation'::regclass,
                  'public.hidra_party_role'::regclass,
                  'public.hidra_party_role_assignment'::regclass,
                  'public.hidra_party_manufacturer_profile'::regclass))
          AND NOT pg_catalog.has_table_privilege(current_user,'public.hidra_party_role',
                  'INSERT,UPDATE,DELETE,TRUNCATE,REFERENCES,TRIGGER')
          AND NOT pg_catalog.has_table_privilege(current_user,'public.hidra_party_role_assignment',
                  'INSERT,UPDATE,DELETE,TRUNCATE,REFERENCES,TRIGGER')
          AND NOT pg_catalog.has_table_privilege(current_user,'public.hidra_party_manufacturer_profile',
                  'INSERT,UPDATE,DELETE,TRUNCATE,REFERENCES,TRIGGER')
          AND NOT pg_catalog.has_any_column_privilege(current_user,'public.hidra_party_role',
                  'INSERT,UPDATE,REFERENCES')
          AND NOT pg_catalog.has_any_column_privilege(current_user,'public.hidra_party_role_assignment',
                  'INSERT,UPDATE,REFERENCES')
          AND NOT pg_catalog.has_any_column_privilege(current_user,'public.hidra_party_manufacturer_profile',
                  'INSERT,UPDATE,REFERENCES')
          AND NOT EXISTS (
              SELECT 1 FROM pg_catalog.pg_inherits i
              WHERE i.inhrelid IN (
                  'public.hidra_party_party'::regclass,
                  'public.hidra_party_eligibility_generation'::regclass,
                  'public.hidra_party_role'::regclass,
                  'public.hidra_party_role_assignment'::regclass,
                  'public.hidra_party_manufacturer_profile'::regclass)
              OR i.inhparent IN (
                  'public.hidra_party_party'::regclass,
                  'public.hidra_party_eligibility_generation'::regclass,
                  'public.hidra_party_role'::regclass,
                  'public.hidra_party_role_assignment'::regclass,
                  'public.hidra_party_manufacturer_profile'::regclass))
        """;

    private static final String OWNER_LOCK = "public.hidra_party_lock_installed_unit_manufacturer_evidence()";
    private static final String OWNER_LOCK_GUARD = """
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
            AND (SELECT pg_catalog.count(*)=4 AND pg_catalog.bool_and(
                    c.relowner<>p.proowner
                    AND pg_catalog.has_table_privilege(o.oid,c.oid,'UPDATE')
                    AND NOT pg_catalog.has_table_privilege(o.oid,c.oid,'UPDATE WITH GRANT OPTION'))
                 FROM pg_catalog.pg_class c WHERE c.oid IN (
                    'public.hidra_party_party'::pg_catalog.regclass,
                    'public.hidra_party_role'::pg_catalog.regclass,
                    'public.hidra_party_role_assignment'::pg_catalog.regclass,
                    'public.hidra_party_manufacturer_profile'::pg_catalog.regclass))
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

    private static final String HELD = """
        SELECT
          EXISTS (SELECT 1 FROM pg_catalog.pg_locks WHERE pid=pg_catalog.pg_backend_pid()
              AND granted AND locktype='relation' AND relation='public.hidra_party_party'::regclass
              AND mode='ShareRowExclusiveLock')
          AND EXISTS (SELECT 1 FROM pg_catalog.pg_locks WHERE pid=pg_catalog.pg_backend_pid()
              AND granted AND locktype='relation' AND relation='public.hidra_party_role'::regclass
              AND mode='ShareRowExclusiveLock')
          AND EXISTS (SELECT 1 FROM pg_catalog.pg_locks WHERE pid=pg_catalog.pg_backend_pid()
              AND granted AND locktype='relation' AND relation='public.hidra_party_role_assignment'::regclass
              AND mode='ShareRowExclusiveLock')
          AND EXISTS (SELECT 1 FROM pg_catalog.pg_locks WHERE pid=pg_catalog.pg_backend_pid()
              AND granted AND locktype='relation' AND relation='public.hidra_party_manufacturer_profile'::regclass
              AND mode='ShareRowExclusiveLock')
          AND EXISTS (SELECT 1 FROM pg_catalog.pg_locks WHERE pid=pg_catalog.pg_backend_pid()
              AND granted AND locktype='relation' AND relation='public.hidra_party_eligibility_generation'::regclass
              AND mode='AccessShareLock')
        """;

    private static final String EVIDENCE = """
        SELECT p.id AS party_id, p.status AS party_status,
               g.generation, g.present,
               r.code AS role_code, r.status AS role_status,
               a.id AS assignment_id, a.status AS assignment_status,
               a.valid_from, a.valid_to, a.qualification_required,
               mp.capability_type, mp.qualification_status,
               pg_catalog.clock_timestamp() AS observed_at
        FROM public.hidra_party_party p
        JOIN public.hidra_party_eligibility_generation g ON g.party_id=p.id
        JOIN public.hidra_party_role_assignment a ON a.party_id=p.id
        JOIN public.hidra_party_role r ON r.id=a.role_id
        JOIN public.hidra_party_manufacturer_profile mp ON mp.party_id=p.id
        WHERE p.id=? AND r.code='MANUFACTURER'
        """;

    private final JdbcTemplate jdbc;
    private final DataSource source;

    public JdbcTopologyInstalledUnitManufacturerEvidenceAdapter(JdbcTemplate jdbc) {
        this.jdbc = Objects.requireNonNull(jdbc);
        this.source = Objects.requireNonNull(jdbc.getDataSource());
    }

    @Override
    @Transactional(propagation=Propagation.MANDATORY)
    public Admission acquire(String manufacturerPartyId) {
        try {
            String id = exact(manufacturerPartyId);
            ConnectionHolder holder = context();
            trustedSearchPath();
            // Validate the fixed owner capability before invoking it; ordinary reader keeps no DML.
            check(GUARD);
            check(OWNER_LOCK_GUARD);
            jdbc.execute("SELECT " + OWNER_LOCK);
            jdbc.execute("LOCK TABLE " + GENERATION + " IN ACCESS SHARE MODE NOWAIT");
            check(GUARD);
            check(OWNER_LOCK_GUARD);
            check(HELD);
            Ticket ticket = jdbc.queryForObject(
                    "SELECT pg_catalog.pg_backend_pid(),pg_catalog.txid_current(),current_user",
                    (row,n) -> new Ticket(id,holder,holder.getConnection(),row.getInt(1),
                            row.getLong(2),row.getString(3)));
            if (ticket == null) throw invalid();
            TransactionSynchronizationManager.registerSynchronization(ticket);
            return ticket;
        } catch (RuntimeException failure) {
            rollback();
            throw failure;
        }
    }

    @Override
    @Transactional(propagation=Propagation.MANDATORY)
    public Contribution read(Admission admission) {
        try {
            Ticket ticket = ticket(admission);
            if (!ticket.used.compareAndSet(false,true)) throw invalid();
            trustedSearchPath();
            check(GUARD);
            check(OWNER_LOCK_GUARD);
            check(HELD);
            List<Contribution> rows = jdbc.query(EVIDENCE,(row,n) -> {
                if (!ticket.id.equals(row.getString("party_id"))
                        || !row.getBoolean("present")
                        || row.getLong("generation")<=0
                        || PartyStatus.valueOf(row.getString("party_status"))!=PartyStatus.ACTIVE
                        || PartyCatalogStatus.valueOf(row.getString("role_status"))!=PartyCatalogStatus.ACTIVE
                        || PartyRoleAssignmentStatus.valueOf(row.getString("assignment_status"))!=PartyRoleAssignmentStatus.ACTIVE)
                    throw invalid();
                String code=row.getString("role_code");
                if (!"MANUFACTURER".equals(code)) throw invalid();
                ManufacturerCapabilityType capability=ManufacturerCapabilityType.valueOf(
                        row.getString("capability_type"));
                if (capability!=ManufacturerCapabilityType.EQUIPMENT
                        && capability!=ManufacturerCapabilityType.INSTRUMENTATION) throw invalid();
                QualificationStatus qualification=QualificationStatus.valueOf(
                        row.getString("qualification_status"));
                if (qualification!=QualificationStatus.QUALIFIED) throw invalid();
                Instant observed=row.getObject("observed_at",OffsetDateTime.class).toInstant();
                Instant from=row.getObject("valid_from",OffsetDateTime.class).toInstant();
                OffsetDateTime until=row.getObject("valid_to",OffsetDateTime.class);
                Instant to=until==null?null:until.toInstant();
                if (from.isAfter(observed) || (to!=null && !observed.isBefore(to)))
                    throw invalid();
                return new Contribution(ticket.id,row.getLong("generation"),code,
                        row.getString("assignment_id"),from,to,
                        row.getBoolean("qualification_required"),capability.name(),
                        qualification.name(),observed,ticket.pid,ticket.xid);
            },ticket.id);
            if (rows.size()!=1) throw invalid(); // Missing, duplicate role or profile: never guess.
            ticket(admission); // Recheck physical transaction, owner capability and fences before disclosure.
            check(GUARD);
            check(OWNER_LOCK_GUARD);
            check(HELD);
            return rows.get(0);
        } catch (RuntimeException failure) {
            rollback();
            throw failure instanceof IllegalArgumentException ? invalid() : failure;
        }
    }

    @Override
    @Transactional(propagation=Propagation.MANDATORY)
    public List<ProtectedRelationFence> finalFences(Admission admission) {
        try {
            ticket(admission);
            check(GUARD);
            check(OWNER_LOCK_GUARD);
            check(HELD);
            return List.of(
                    new ProtectedRelationFence(PARTY,ProtectedRelationFence.Mode.SHARE_ROW_EXCLUSIVE),
                    new ProtectedRelationFence(ROLE,ProtectedRelationFence.Mode.SHARE_ROW_EXCLUSIVE),
                    new ProtectedRelationFence(ASSIGNMENT,ProtectedRelationFence.Mode.SHARE_ROW_EXCLUSIVE),
                    new ProtectedRelationFence(PROFILE,ProtectedRelationFence.Mode.SHARE_ROW_EXCLUSIVE),
                    new ProtectedRelationFence(GENERATION,ProtectedRelationFence.Mode.ACCESS_SHARE));
        } catch (RuntimeException failure) {
            rollback();
            throw failure;
        }
    }

    private Ticket ticket(Admission admission) {
        ConnectionHolder holder=context();
        if (!(admission instanceof Ticket ticket)
                || ticket.issuer!=this || !ticket.active || ticket.thread!=Thread.currentThread()
                || ticket.holder!=holder || ticket.connection!=holder.getConnection())
            throw invalid();
        if (!Boolean.TRUE.equals(jdbc.queryForObject(
                "SELECT pg_catalog.pg_backend_pid()=? AND pg_catalog.txid_current()=? AND current_user=?",
                Boolean.class,ticket.pid,ticket.xid,ticket.role))) throw invalid();
        return ticket;
    }

    private ConnectionHolder context() {
        if (!TransactionSynchronizationManager.isActualTransactionActive()
                || !TransactionSynchronizationManager.isSynchronizationActive()
                || TransactionSynchronizationManager.isCurrentTransactionReadOnly()
                || !(TransactionSynchronizationManager.getResource(source) instanceof ConnectionHolder holder)
                || holder.isRollbackOnly()) throw invalid();
        return holder;
    }

    private void trustedSearchPath() {
        String schemas = jdbc.queryForObject(
                "SELECT pg_catalog.array_to_json(pg_catalog.current_schemas(true))::pg_catalog.text",
                String.class);
        if (schemas==null || !(schemas.equals("[\"pg_catalog\"]")
                || schemas.startsWith("[\"pg_catalog\","))) throw invalid();
    }
    private void check(String sql) {
        if (!Boolean.TRUE.equals(jdbc.queryForObject(sql,Boolean.class))) throw invalid();
    }
    private static String exact(String id) {
        if (id==null || id.isBlank() || !id.equals(id.trim()) || id.length()>80)
            throw new IllegalArgumentException("Exact manufacturer Party id required");
        return id;
    }
    private static IllegalStateException invalid() {
        return new IllegalStateException("Protected Party manufacturer evidence unavailable");
    }
    private void rollback() {
        Object resource=TransactionSynchronizationManager.getResource(source);
        if (resource instanceof ConnectionHolder holder) holder.setRollbackOnly();
        try { TransactionAspectSupport.currentTransactionStatus().setRollbackOnly(); }
        catch (org.springframework.transaction.NoTransactionException absent) {
            // Direct transaction-template entry.
        }
    }

    private final class Ticket implements Admission,TransactionSynchronization {
        private final JdbcTopologyInstalledUnitManufacturerEvidenceAdapter issuer =
                JdbcTopologyInstalledUnitManufacturerEvidenceAdapter.this;
        private final String id,role;
        private final ConnectionHolder holder;
        private final Connection connection;
        private final Thread thread=Thread.currentThread();
        private final int pid;
        private final long xid;
        private final AtomicBoolean used=new AtomicBoolean();
        private volatile boolean active=true;

        private Ticket(String id,ConnectionHolder holder,Connection connection,int pid,long xid,String role) {
            this.id=id;this.holder=holder;this.connection=connection;
            this.pid=pid;this.xid=xid;this.role=role;
        }
        @Override public void suspend() { active=false; }
        @Override public void afterCompletion(int status) { active=false; }
    }
}
