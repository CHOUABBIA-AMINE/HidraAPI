/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : JdbcEngineeringRevisionPublicationRepositoryAdapter
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-20
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.infrastructure.persistence.adapter
 *
 * @Description : Persists immutable governed snapshots and validates the complete publication ledger in one final guard.
 *
 */
package dz.sh.hidra.modules.topology.infrastructure.persistence.adapter;

import dz.sh.hidra.modules.topology.application.port.in.EngineeringRevisionPublicationUseCase.*;
import dz.sh.hidra.modules.topology.application.port.out.EngineeringRevisionPublicationRepositoryPort;
import dz.sh.hidra.modules.topology.application.port.out.EngineeringGraphStationWitnessPort;
import dz.sh.hidra.modules.topology.application.model.EngineeringRevisionPublicationFailure;
import dz.sh.hidra.modules.topology.application.model.EngineeringRevisionPublicationFailure.Reason;
import dz.sh.hidra.modules.topology.domain.model.EngineeringRevisionPublication;
import dz.sh.hidra.modules.topology.domain.model.EngineeringRevisionPublication.*;
import dz.sh.hidra.modules.topology.domain.model.EngineeringGraphDraft;
import dz.sh.hidra.platform.persistence.*;
import jakarta.persistence.EntityManager;
import java.io.*;
import java.sql.*;
import java.time.Instant;
import java.util.*;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.ConnectionHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.*;
import org.springframework.transaction.support.*;

@Repository
public class JdbcEngineeringRevisionPublicationRepositoryAdapter implements EngineeringRevisionPublicationRepositoryPort {
    public static final String SNAPSHOT_INS="INSERT INTO public.hidra_topology_engineering_publication_snapshot (revision_id,context_id,draft_id,draft_version,payload_format,canonical_payload,sha256,engineering_qualified,transaction_id) VALUES (?,?,?,?,?,?,?,false,?)";
    public static final String EVENT_INS="INSERT INTO public.hidra_topology_engineering_publication_event (event_id,context_id,epoch,operation,prior_revision_id,revision_id,resulting_state,request_id,command_sha256,actor_binding_id,actor_session_id,actor_user_id,party_generation,station_evidence,sampled_at,valid_until,effective_at,backend_pid,transaction_id) VALUES (?,?,?,?,?,?,?,?,?,?::uuid,?::uuid,?,?,?,?,?,?,?,?)";
    public static final String HEAD_INS="INSERT INTO public.hidra_topology_engineering_publication_head (context_id,revision_id,epoch,state,last_event_id) VALUES (?,?,?,?,?)";
    public static final String HEAD_UPDATE="UPDATE public.hidra_topology_engineering_publication_head SET revision_id=?,epoch=?,state=?,last_event_id=? WHERE context_id=? AND epoch=? AND revision_id=? AND state=? AND last_event_id=?";
    private static final Set<String> DML=Set.of(SNAPSHOT_INS,EVENT_INS,HEAD_INS,HEAD_UPDATE);
    private static final String FACILITY_OWNER_GENERATION="public.hidra_topology_facility_owner_generation";
    private static final List<String> RELATIONS=List.of(
        "public.hidra_topology_pipeline_system","public.hidra_topology_pipeline_system_type",
        "public.hidra_topology_engineering_context","public.hidra_topology_engineering_draft",
        "public.hidra_topology_engineering_draft_version","public.hidra_topology_facility",
        FACILITY_OWNER_GENERATION,"public.hidra_topology_engineering_graph_snapshot",
        "public.hidra_topology_engineering_survey_snapshot",
        "public.hidra_topology_engineering_publication_snapshot",
        "public.hidra_topology_engineering_publication_event",
        "public.hidra_topology_engineering_publication_head");
    private static final String CATALOG="""
        SELECT current_setting('session_replication_role')='origin'
          AND NOT has_schema_privilege(current_user,'public','CREATE')
          AND NOT has_schema_privilege(current_user,'pg_catalog','CREATE')
          AND NOT EXISTS (SELECT 1 FROM pg_roles r WHERE (r.rolname=current_user OR pg_has_role(current_user,r.oid,'MEMBER'))
            AND (r.rolsuper OR r.rolbypassrls OR r.rolcreaterole OR r.rolcreatedb OR r.rolreplication
              OR r.rolname IN ('pg_execute_server_program','pg_read_server_files','pg_write_server_files')))
          AND (SELECT count(*)=3 AND bool_and(c.relkind='r' AND c.relpersistence='p' AND NOT c.relrowsecurity
            AND NOT c.relforcerowsecurity AND NOT c.relispartition AND NOT pg_has_role(current_user,c.relowner,'MEMBER')
            AND has_table_privilege(current_user,c.oid,'SELECT') AND has_table_privilege(current_user,c.oid,'INSERT')
            AND has_table_privilege(current_user,c.oid,'UPDATE') AND NOT has_table_privilege(current_user,c.oid,'DELETE,TRUNCATE,TRIGGER')
            AND NOT EXISTS (SELECT 1 FROM pg_inherits h WHERE h.inhrelid=c.oid OR h.inhparent=c.oid))
            FROM pg_class c WHERE c.oid IN ('public.hidra_topology_engineering_context'::regclass,
              'public.hidra_topology_engineering_draft'::regclass,'public.hidra_topology_engineering_draft_version'::regclass))
          AND (SELECT count(*)=7 AND bool_and(t.tgenabled='O' AND t.tgqual IS NULL AND t.tgnargs=0 AND t.tgparentid=0
            AND cardinality(t.tgattr::smallint[])=0 AND t.tgconstraint=0 AND
            ((t.tgfoid='public.hidra_p25_draft_immutable()'::regprocedure AND t.tgtype IN (26,34))
             OR (t.tgfoid='public.hidra_p25_draft_next_version()'::regprocedure AND t.tgtype=7
                AND t.tgrelid='public.hidra_topology_engineering_draft_version'::regclass)))
            FROM pg_trigger t WHERE NOT t.tgisinternal AND t.tgrelid IN
              ('public.hidra_topology_engineering_context'::regclass,'public.hidra_topology_engineering_draft'::regclass,
               'public.hidra_topology_engineering_draft_version'::regclass))
          AND (SELECT count(*)=2 AND bool_and(p.prosecdef AND p.prokind='f' AND p.pronargs=0
             AND p.prorettype='trigger'::regtype AND p.provolatile='v' AND NOT p.proleakproof
             AND p.proconfig=ARRAY['search_path=pg_catalog, pg_temp']
             AND p.prolang=(SELECT oid FROM pg_language WHERE lanname='plpgsql')
             AND NOT pg_has_role(current_user,p.proowner,'MEMBER') AND NOT has_function_privilege(current_user,p.oid,'EXECUTE')
             AND ((p.oid='public.hidra_p25_draft_immutable()'::regprocedure AND md5(p.prosrc)='543600367aa907964dac9777269caa07')
               OR (p.oid='public.hidra_p25_draft_next_version()'::regprocedure AND md5(p.prosrc)='ce8ad03dffcf0ca3fcb063be59a26bf7')))
            FROM pg_proc p WHERE p.oid IN ('public.hidra_p25_draft_immutable()'::regprocedure,'public.hidra_p25_draft_next_version()'::regprocedure))
          AND NOT EXISTS (SELECT 1 FROM pg_constraint k WHERE k.conrelid IN
             ('public.hidra_topology_engineering_context'::regclass,'public.hidra_topology_engineering_draft'::regclass,
              'public.hidra_topology_engineering_draft_version'::regclass) AND (NOT k.convalidated OR k.condeferrable))
        """;
    private static final String GRAPH_CATALOG="""
        SELECT current_setting('session_replication_role')='origin'
          AND NOT has_table_privilege(current_user,'public.hidra_topology_facility_owner_generation',
            'INSERT,UPDATE,DELETE,TRUNCATE,REFERENCES,TRIGGER')
          AND NOT has_any_column_privilege(current_user,'public.hidra_topology_facility_owner_generation',
            'INSERT,UPDATE,REFERENCES')
          AND (SELECT count(*)=1 AND bool_and(c.relkind='r' AND c.relpersistence='p'
            AND NOT c.relispartition AND NOT c.relrowsecurity AND NOT c.relforcerowsecurity
            AND NOT pg_has_role(current_user,c.relowner,'MEMBER')
            AND has_table_privilege(current_user,c.oid,'SELECT')
            AND has_table_privilege(current_user,c.oid,'INSERT')
            AND has_table_privilege(current_user,c.oid,'UPDATE')
            AND NOT has_table_privilege(current_user,c.oid,'DELETE,TRUNCATE,TRIGGER')
            AND NOT EXISTS (SELECT 1 FROM pg_inherits h WHERE h.inhrelid=c.oid OR h.inhparent=c.oid))
           FROM pg_class c WHERE c.oid='public.hidra_topology_engineering_graph_snapshot'::regclass)
          AND (SELECT count(*)=3 AND bool_and(t.tgenabled='O' AND t.tgqual IS NULL
            AND t.tgnargs=0 AND t.tgparentid=0 AND t.tgconstraint=0
            AND ((t.tgname='trg_p25_engineering_graph_same_transaction'
                 AND t.tgfoid='public.hidra_p25_graph_snapshot_same_transaction()'::regprocedure AND t.tgtype=7)
              OR (t.tgname IN ('trg_p25_engineering_graph_immutable','trg_p25_engineering_graph_no_truncate')
                 AND t.tgfoid='public.hidra_p25_draft_immutable()'::regprocedure AND t.tgtype IN (26,34))))
            FROM pg_trigger t WHERE NOT t.tgisinternal
              AND t.tgrelid='public.hidra_topology_engineering_graph_snapshot'::regclass)
          AND (SELECT count(*)=1 AND bool_and(p.prosecdef AND p.prokind='f' AND p.pronargs=0
              AND p.prorettype='trigger'::regtype AND p.provolatile='v' AND NOT p.proleakproof
              AND p.proconfig=ARRAY['search_path=pg_catalog, pg_temp']
              AND p.prolang=(SELECT oid FROM pg_language WHERE lanname='plpgsql')
              AND NOT pg_has_role(current_user,p.proowner,'MEMBER')
              AND NOT has_function_privilege(current_user,p.oid,'EXECUTE')
              AND md5(p.prosrc)='f21c1f96289a7e167165be8637d1d28d')
            FROM pg_proc p WHERE p.oid='public.hidra_p25_graph_snapshot_same_transaction()'::regprocedure)
          AND NOT EXISTS (SELECT 1 FROM pg_constraint k
            WHERE k.conrelid='public.hidra_topology_engineering_graph_snapshot'::regclass
              AND (NOT k.convalidated OR k.condeferrable))
        """;
    private static final String SURVEY_CATALOG="""
        SELECT current_setting('session_replication_role')='origin'
          AND NOT has_table_privilege(current_user,'public.hidra_topology_facility_owner_generation',
            'INSERT,UPDATE,DELETE,TRUNCATE,REFERENCES,TRIGGER')
          AND NOT has_any_column_privilege(current_user,'public.hidra_topology_facility_owner_generation',
            'INSERT,UPDATE,REFERENCES')
          AND (SELECT count(*)=1 AND bool_and(c.relkind='r' AND c.relpersistence='p'
            AND NOT c.relispartition AND NOT c.relrowsecurity AND NOT c.relforcerowsecurity
            AND NOT pg_has_role(current_user,c.relowner,'MEMBER')
            AND has_table_privilege(current_user,c.oid,'SELECT')
            AND has_table_privilege(current_user,c.oid,'INSERT')
            AND has_table_privilege(current_user,c.oid,'UPDATE')
            AND NOT has_table_privilege(current_user,c.oid,'DELETE,TRUNCATE,TRIGGER')
            AND NOT EXISTS (SELECT 1 FROM pg_inherits h WHERE h.inhrelid=c.oid OR h.inhparent=c.oid))
           FROM pg_class c WHERE c.oid='public.hidra_topology_engineering_survey_snapshot'::regclass)
          AND (SELECT count(*)=3 AND bool_and(t.tgenabled='O' AND t.tgqual IS NULL
            AND t.tgnargs=0 AND t.tgparentid=0 AND t.tgconstraint=0
            AND ((t.tgname='trg_p25_engineering_survey_same_transaction'
                 AND t.tgfoid='public.hidra_p25_survey_snapshot_same_transaction()'::regprocedure AND t.tgtype=7)
              OR (t.tgname IN ('trg_p25_engineering_survey_immutable','trg_p25_engineering_survey_no_truncate')
                 AND t.tgfoid='public.hidra_p25_draft_immutable()'::regprocedure AND t.tgtype IN (26,34))))
            FROM pg_trigger t WHERE NOT t.tgisinternal
              AND t.tgrelid='public.hidra_topology_engineering_survey_snapshot'::regclass)
          AND (SELECT count(*)=1 AND bool_and(p.prosecdef AND p.prokind='f' AND p.pronargs=0
              AND p.prorettype='trigger'::regtype AND p.provolatile='v' AND NOT p.proleakproof
              AND p.proconfig=ARRAY['search_path=pg_catalog, pg_temp']
              AND p.prolang=(SELECT oid FROM pg_language WHERE lanname='plpgsql')
              AND NOT pg_has_role(current_user,p.proowner,'MEMBER')
              AND NOT has_function_privilege(current_user,p.oid,'EXECUTE')
              AND md5(p.prosrc)='a65f0d43b26c1493b6f943b3d6f2963c')
            FROM pg_proc p WHERE p.oid='public.hidra_p25_survey_snapshot_same_transaction()'::regprocedure)
          AND NOT EXISTS (SELECT 1 FROM pg_constraint k
            WHERE k.conrelid='public.hidra_topology_engineering_survey_snapshot'::regclass
              AND (NOT k.convalidated OR k.condeferrable))

          AND (SELECT count(*)=9 AND bool_and(a.attnotnull AND NOT a.attisdropped AND a.attidentity='' AND a.attgenerated='')
            FROM pg_attribute a WHERE a.attrelid='public.hidra_topology_engineering_survey_snapshot'::regclass AND a.attnum>0)
          AND (SELECT count(*)=11 AND count(*) FILTER(WHERE k.contype='c')=8
              AND count(*) FILTER(WHERE k.contype='p' AND k.conkey=ARRAY[1,2,3]::smallint[])=1
              AND count(*) FILTER(WHERE k.contype='f' AND k.conkey=ARRAY[1,3]::smallint[]
                AND k.confrelid='public.hidra_topology_engineering_draft_version'::regclass AND k.confkey=ARRAY[1,2]::smallint[] AND k.confdeltype='r' AND k.confupdtype='a')=1
              AND count(*) FILTER(WHERE k.contype='f' AND k.conkey=ARRAY[1,4]::smallint[]
                AND k.confrelid='public.hidra_topology_engineering_graph_snapshot'::regclass AND k.confkey=ARRAY[1,2]::smallint[] AND k.confdeltype='r' AND k.confupdtype='a')=1
              AND bool_and(k.convalidated AND NOT k.condeferrable)
            FROM pg_constraint k WHERE k.conrelid='public.hidra_topology_engineering_survey_snapshot'::regclass)
        """;

    private static final String PUBLICATION_CATALOG="""

        SELECT current_setting('session_replication_role')='origin'
          AND (SELECT count(*)=3 AND bool_and(c.relkind='r' AND c.relpersistence='p'
              AND NOT c.relispartition AND NOT c.relrowsecurity AND NOT c.relforcerowsecurity
              AND NOT pg_has_role(current_user,c.relowner,'MEMBER')
              AND has_table_privilege(current_user,c.oid,'SELECT')
              AND has_table_privilege(current_user,c.oid,'INSERT')
              AND has_table_privilege(current_user,c.oid,'UPDATE')
              AND NOT has_table_privilege(current_user,c.oid,'DELETE,TRUNCATE,REFERENCES,TRIGGER')
              AND NOT has_any_column_privilege(current_user,c.oid,'REFERENCES')
              AND NOT EXISTS(SELECT 1 FROM pg_inherits h WHERE h.inhrelid=c.oid OR h.inhparent=c.oid))
            FROM pg_class c WHERE c.oid IN
             ('public.hidra_topology_engineering_publication_snapshot'::regclass,
              'public.hidra_topology_engineering_publication_event'::regclass,
              'public.hidra_topology_engineering_publication_head'::regclass))
          AND (SELECT count(*)=3 AND bool_and(p.prosecdef AND p.prokind='f' AND p.pronargs=0
              AND p.prorettype='trigger'::regtype AND p.provolatile='v' AND NOT p.proleakproof
              AND p.proconfig=ARRAY['search_path=pg_catalog, pg_temp']
              AND p.prolang=(SELECT oid FROM pg_language WHERE lanname='plpgsql')
              AND NOT pg_has_role(current_user,p.proowner,'MEMBER')
              AND NOT has_function_privilege(current_user,p.oid,'EXECUTE')
              AND ((p.oid='public.hidra_p25_publication_snapshot_guard()'::regprocedure AND encode(sha256(convert_to(p.prosrc,'UTF8')),'hex')='4739555ab373d7404a812d84235491dd32695896637011b510edc46886890b3a')
                OR (p.oid='public.hidra_p25_publication_event_guard()'::regprocedure AND encode(sha256(convert_to(p.prosrc,'UTF8')),'hex')='8c371f9021bec574b57958faeed45a34cb365b849f27de6b11a1cc7aa7243b3d')
                OR (p.oid='public.hidra_p25_publication_head_guard()'::regprocedure AND encode(sha256(convert_to(p.prosrc,'UTF8')),'hex')='bec0f117cf4417fc4e99ff3c59cb1c11cd632fc148790c4bb9a513731ea48d1f')))
            FROM pg_proc p WHERE p.oid IN ('public.hidra_p25_publication_snapshot_guard()'::regprocedure,'public.hidra_p25_publication_event_guard()'::regprocedure,'public.hidra_p25_publication_head_guard()'::regprocedure))
          AND (SELECT count(*)=9 AND bool_and(t.tgenabled='O' AND t.tgqual IS NULL
              AND t.tgnargs=0 AND t.tgparentid=0 AND t.tgconstraint=0 AND cardinality(t.tgattr::smallint[])=0
              AND ((t.tgname='p25_publication_snapshot_insert' AND t.tgtype=7 AND t.tgrelid='public.hidra_topology_engineering_publication_snapshot'::regclass AND t.tgfoid='public.hidra_p25_publication_snapshot_guard()'::regprocedure)
                OR (t.tgname='p25_publication_event_insert' AND t.tgtype=7 AND t.tgrelid='public.hidra_topology_engineering_publication_event'::regclass AND t.tgfoid='public.hidra_p25_publication_event_guard()'::regprocedure)
                OR (t.tgname='p25_publication_head_write' AND t.tgtype=23 AND t.tgrelid='public.hidra_topology_engineering_publication_head'::regclass AND t.tgfoid='public.hidra_p25_publication_head_guard()'::regprocedure)
                OR (t.tgname IN ('p25_publication_snapshot_immutable','p25_publication_event_immutable') AND t.tgtype=26 AND t.tgfoid='public.hidra_p25_draft_immutable()'::regprocedure)
                OR (t.tgname='p25_publication_head_no_delete' AND t.tgtype=10 AND t.tgfoid='public.hidra_p25_draft_immutable()'::regprocedure)
                OR (t.tgname IN ('p25_publication_snapshot_no_truncate','p25_publication_event_no_truncate','p25_publication_head_no_truncate') AND t.tgtype=34 AND t.tgfoid='public.hidra_p25_draft_immutable()'::regprocedure)))
            FROM pg_trigger t WHERE NOT t.tgisinternal AND t.tgrelid IN
             ('public.hidra_topology_engineering_publication_snapshot'::regclass,'public.hidra_topology_engineering_publication_event'::regclass,'public.hidra_topology_engineering_publication_head'::regclass))
          AND NOT EXISTS(SELECT 1 FROM pg_constraint k WHERE k.conrelid IN
             ('public.hidra_topology_engineering_publication_snapshot'::regclass,'public.hidra_topology_engineering_publication_event'::regclass,'public.hidra_topology_engineering_publication_head'::regclass)
             AND (NOT k.convalidated OR k.condeferrable))
          AND (SELECT count(*)=10 FROM pg_constraint WHERE conrelid='public.hidra_topology_engineering_publication_snapshot'::regclass)
          AND (SELECT count(*)=22 FROM pg_constraint WHERE conrelid='public.hidra_topology_engineering_publication_event'::regclass)
          AND (SELECT count(*)=5 FROM pg_constraint WHERE conrelid='public.hidra_topology_engineering_publication_head'::regclass)

          AND (SELECT count(*)=9 AND bool_and(a.attnotnull AND NOT a.attisdropped AND a.attidentity='' AND a.attgenerated='')
            FROM pg_attribute a WHERE a.attrelid='public.hidra_topology_engineering_publication_snapshot'::regclass AND a.attnum>0)
          AND (SELECT count(*)=19 AND bool_and((a.attnotnull OR a.attnum=5) AND NOT a.attisdropped AND a.attidentity='' AND a.attgenerated='')
            FROM pg_attribute a WHERE a.attrelid='public.hidra_topology_engineering_publication_event'::regclass AND a.attnum>0)
          AND (SELECT count(*)=5 AND bool_and(a.attnotnull AND NOT a.attisdropped AND a.attidentity='' AND a.attgenerated='')
            FROM pg_attribute a WHERE a.attrelid='public.hidra_topology_engineering_publication_head'::regclass AND a.attnum>0)
          AND (SELECT count(*) FILTER(WHERE k.contype='p' AND k.conkey=ARRAY[1]::smallint[])=1
               AND count(*) FILTER(WHERE k.contype='u' AND k.conkey=ARRAY[2,1]::smallint[])=1
               AND count(*) FILTER(WHERE k.contype='f' AND k.conkey=ARRAY[3,4]::smallint[] AND k.confrelid='public.hidra_topology_engineering_draft_version'::regclass AND k.confkey=ARRAY[1,2]::smallint[] AND k.confdeltype='r')=1
            FROM pg_constraint k WHERE k.conrelid='public.hidra_topology_engineering_publication_snapshot'::regclass)
          AND (SELECT count(*) FILTER(WHERE k.contype='u' AND k.conkey=ARRAY[2,3]::smallint[])=1
               AND count(*) FILTER(WHERE k.contype='u' AND k.conkey=ARRAY[2,12,8]::smallint[])=1
               AND count(*) FILTER(WHERE k.contype='u' AND k.conkey=ARRAY[2,3,6,7,1]::smallint[])=1
               AND count(*) FILTER(WHERE k.contype='f' AND k.conkey=ARRAY[2,6]::smallint[] AND k.confrelid='public.hidra_topology_engineering_publication_snapshot'::regclass AND k.confkey=ARRAY[2,1]::smallint[] AND k.confdeltype='r')=1
               AND count(*) FILTER(WHERE k.contype='f' AND k.conkey=ARRAY[2,5]::smallint[] AND k.confrelid='public.hidra_topology_engineering_publication_snapshot'::regclass AND k.confkey=ARRAY[2,1]::smallint[] AND k.confdeltype='r')=1
            FROM pg_constraint k WHERE k.conrelid='public.hidra_topology_engineering_publication_event'::regclass)
          AND (SELECT count(*) FILTER(WHERE k.contype='p' AND k.conkey=ARRAY[1]::smallint[])=1
               AND count(*) FILTER(WHERE k.contype='f' AND k.conkey=ARRAY[1,3,2,4,5]::smallint[] AND k.confrelid='public.hidra_topology_engineering_publication_event'::regclass AND k.confkey=ARRAY[2,3,6,7,1]::smallint[] AND k.confdeltype='r')=1
            FROM pg_constraint k WHERE k.conrelid='public.hidra_topology_engineering_publication_head'::regclass)

        """;
    private final JdbcTemplate jdbc;
    private final ProtectedTransactionExecutor executor;
    private final EntityManager em;
    private final EngineeringGraphStationWitnessPort stationWitness;
    private final EngineeringRevisionPublicationCodec codec=new EngineeringRevisionPublicationCodec();
    private final EngineeringRevisionPublicationCursorCodec cursors=new EngineeringRevisionPublicationCursorCodec();
    public JdbcEngineeringRevisionPublicationRepositoryAdapter(JdbcTemplate jdbc,ProtectedTransactionExecutor executor,
            EntityManager em,EngineeringGraphStationWitnessPort stationWitness) {
        this.jdbc=Objects.requireNonNull(jdbc);this.executor=Objects.requireNonNull(executor);
        this.em=Objects.requireNonNull(em);this.stationWitness=Objects.requireNonNull(stationWitness);
    }
    private final class Ticket implements Admission,TransactionSynchronization {
        final JdbcEngineeringRevisionPublicationRepositoryAdapter owner=JdbcEngineeringRevisionPublicationRepositoryAdapter.this;
        final Thread thread=Thread.currentThread();final Command command;
        final ConnectionHolder holder;final Connection connection;final int pid;final long xid;final String role,ordinary;
        boolean active=true,eligible,resolved,applied,mutation;
        Scope scope;Observation observation;Head before,after;Snapshot source;Receipt receipt;
        String commandDigest,stationEvidence;Event event;Result result;
        List<EngineeringGraphStationWitnessPort.Witness> witnesses=List.of();
        final Map<UUID,EngineeringRevisionPublication> retained=new LinkedHashMap<>();
        Ticket(Command command,ConnectionHolder holder,int pid,long xid,String role,String ordinary) {
            this.command=command;this.holder=holder;connection=holder.getConnection();
            this.pid=pid;this.xid=xid;this.role=role;this.ordinary=ordinary;
        }
        @Override public void suspend(){active=false;}
        @Override public void afterCompletion(int status){active=false;}
    }
    private static EngineeringRevisionPublicationFailure fail(Reason reason){return new EngineeringRevisionPublicationFailure(reason);}
    private ConnectionHolder physical() {
        if(!StackWalker.getInstance().walk(f->f.anyMatch(x->x.getClassName().equals(
                "dz.sh.hidra.modules.topology.application.service.EngineeringRevisionPublicationApplicationService")))
                || !TransactionSynchronizationManager.isActualTransactionActive()
                || TransactionSynchronizationManager.isCurrentTransactionReadOnly()
                || !(TransactionSynchronizationManager.getResource(jdbc.getDataSource()) instanceof ConnectionHolder holder)
                || holder.isRollbackOnly())throw fail(Reason.UNAVAILABLE);
        return holder;
    }
    private Ticket ticket(Admission admission) {
        var holder=physical();
        if(!(admission instanceof Ticket t) || t.owner!=this || t.thread!=Thread.currentThread()
                || !t.active || t.holder!=holder || t.connection!=holder.getConnection())throw fail(Reason.UNAVAILABLE);
        return t;
    }
    @Override @Transactional(propagation=Propagation.MANDATORY)
    public Admission acquire(Command command) {
        try {
            var holder=physical();
            for(String relation:RELATIONS)jdbc.execute("LOCK TABLE "+relation+" IN "+
                    (relation.equals(FACILITY_OWNER_GENERATION)?"ACCESS SHARE":"SHARE ROW EXCLUSIVE")+" MODE NOWAIT");
            var principals=executor.publicationDatabaseIdentity();
            if(principals.publisherUser().equals(principals.ordinaryUser())
                    || !Boolean.TRUE.equals(jdbc.queryForObject("SELECT session_user=? AND current_user=? AND NOT pg_has_role(?::name,?::name,'MEMBER')",
                        Boolean.class,principals.publisherUser(),principals.publisherUser(),principals.ordinaryUser(),principals.publisherUser())))
                throw fail(Reason.UNAVAILABLE);
            for(String relation:RELATIONS.subList(7,12)) {
                if(!Boolean.TRUE.equals(jdbc.queryForObject("""
                    SELECT NOT pg_has_role(?::name,c.relowner,'MEMBER')
                     AND NOT has_table_privilege(?::name,c.oid,'SELECT,INSERT,UPDATE,DELETE,TRUNCATE,REFERENCES,TRIGGER')
                     AND NOT has_any_column_privilege(?::name,c.oid,'SELECT,INSERT,UPDATE,REFERENCES')
                    FROM pg_class c WHERE c.oid=?::regclass
                    """,Boolean.class,principals.ordinaryUser(),principals.ordinaryUser(),principals.ordinaryUser(),relation)))
                    throw fail(Reason.UNAVAILABLE);
            }
            if(!ordinarySafe(principals.ordinaryUser(),principals.publisherUser()) || !catalog())throw fail(Reason.UNAVAILABLE);
            var result=jdbc.queryForObject("SELECT pg_backend_pid(),txid_current(),current_user",
                    (r,n)->new Ticket(command,holder,r.getInt(1),r.getLong(2),r.getString(3),principals.ordinaryUser()));
            if(result==null)throw fail(Reason.UNAVAILABLE);
            TransactionSynchronizationManager.registerSynchronization(result);return result;
        }catch(RuntimeException error){executor.rejectAdmission();throw error;}
    }
    private boolean ordinarySafe(String ordinary,String publisher) {
        return Boolean.TRUE.equals(jdbc.queryForObject("""
            SELECT NOT pg_has_role(?::name,?::name,'MEMBER')
             AND NOT has_schema_privilege(?::name,'public','CREATE')
             AND NOT has_schema_privilege(?::name,'pg_catalog','CREATE')
             AND NOT EXISTS(SELECT 1 FROM pg_roles r WHERE (r.rolname=?::name OR pg_has_role(?::name,r.oid,'MEMBER'))
              AND (r.rolsuper OR r.rolbypassrls OR r.rolcreaterole OR r.rolcreatedb OR r.rolreplication
               OR r.rolname IN ('pg_execute_server_program','pg_read_server_files','pg_write_server_files')))
            """,Boolean.class,ordinary,publisher,ordinary,ordinary,ordinary,ordinary));
    }

    private boolean catalog() {
        return Boolean.TRUE.equals(jdbc.queryForObject("SELECT ("+CATALOG+") AND ("+GRAPH_CATALOG+") AND ("+SURVEY_CATALOG+") AND ("+PUBLICATION_CATALOG+")",Boolean.class));
    }
    @Override public List<ProtectedRelationFence> finalFences(Admission admission) {
        ticket(admission);
        return RELATIONS.stream().map(r->new ProtectedRelationFence(r,r.equals(FACILITY_OWNER_GENERATION)
                ?ProtectedRelationFence.Mode.ACCESS_SHARE:ProtectedRelationFence.Mode.SHARE_ROW_EXCLUSIVE)).toList();
    }
    @Override @Transactional(propagation=Propagation.MANDATORY)
    public void eligible(Admission admission) {
        try {
            var t=ticket(admission);if(t.eligible)throw fail(Reason.UNAVAILABLE);
            var c=t.command;
            var scopes=jdbc.query("""
                SELECT x.id FROM public.hidra_topology_engineering_context x
                JOIN public.hidra_topology_engineering_draft d ON d.context_id=x.id
                JOIN public.hidra_topology_pipeline_system s ON s.id=x.pipeline_system_id
                JOIN public.hidra_topology_pipeline_system_type y ON y.id=s.system_type_id
                WHERE x.company_party_id=? AND x.pipeline_system_id=? AND d.id=?
                  AND s.status IN ('DRAFT','ACTIVE') AND y.active
                """,(r,n)->new Scope(r.getObject(1,UUID.class),c.companyPartyId(),c.pipelineSystemId()),
                c.companyPartyId(),c.pipelineSystemId(),c.draftId());
            if(scopes.size()!=1)throw fail(Reason.NOT_FOUND);
            t.scope=scopes.get(0);t.eligible=true;
        }catch(RuntimeException error){executor.rejectAdmission();throw error;}
    }
    @Override public Scope scope(Admission admission) {
        var t=ticket(admission);if(!t.eligible)throw fail(Reason.UNAVAILABLE);return t.scope;
    }
    @Override public void observe(Admission admission,Observation observation) {
        var t=ticket(admission);
        if(!t.eligible || t.observation!=null || !t.scope.equals(observation.scope())
                || observation.backendPid()!=t.pid || observation.transactionId()!=t.xid)throw fail(Reason.UNAVAILABLE);
        t.observation=observation;
    }
    private Head head(Ticket t) {
        var rows=jdbc.query("""
            SELECT h.revision_id,h.epoch,h.state,h.last_event_id,e.effective_at
            FROM public.hidra_topology_engineering_publication_head h
            JOIN public.hidra_topology_engineering_publication_event e ON e.event_id=h.last_event_id
            WHERE h.context_id=?
            """,(r,n)->new Head(t.scope,r.getObject(1,UUID.class),r.getLong(2),State.valueOf(r.getString(3)),
                r.getObject(4,UUID.class),r.getTimestamp(5).toInstant()),t.scope.contextId());
        return rows.isEmpty()?null:rows.get(0);
    }
    private Event event(Scope scope,ResultSet r)throws SQLException {
        return new Event(r.getObject("event_id",UUID.class),scope,r.getLong("epoch"),Action.valueOf(r.getString("operation")),
                r.getObject("prior_revision_id",UUID.class),r.getObject("revision_id",UUID.class),
                State.valueOf(r.getString("resulting_state")),r.getObject("request_id",UUID.class),
                r.getString("command_sha256"),r.getTimestamp("effective_at").toInstant());
    }
    private static boolean mutation(Kind kind){return kind==Kind.PUBLISH || kind==Kind.WITHDRAW || kind==Kind.REACTIVATE;}
    private String commandDigest(Command c) {
        try {
            var bytes=new ByteArrayOutputStream();var out=new DataOutputStream(bytes);
            out.writeUTF("HIDRA_ENGINEERING_PUBLICATION_COMMAND_V1");out.writeUTF(c.kind().name());
            out.writeUTF(c.companyPartyId());out.writeUTF(c.pipelineSystemId());out.writeUTF(c.draftId().toString());
            out.writeUTF(c.requestId().toString());out.writeLong(c.draftVersion()==null?0:c.draftVersion());
            out.writeLong(c.expectedEpoch());out.writeUTF(c.expectedRevisionId()==null?"":c.expectedRevisionId().toString());
            out.flush();return EngineeringRevisionPublication.hash(bytes.toByteArray());
        }catch(IOException impossible){throw new IllegalStateException(impossible);}
    }
    private Receipt receiptRow(Ticket t,UUID request) {
        var rows=jdbc.query("SELECT * FROM public.hidra_topology_engineering_publication_event WHERE context_id=? AND actor_user_id=? AND request_id=?",
                (r,n)->new Receipt(t.scope,t.observation.actorUserId(),request,r.getString("command_sha256"),event(t.scope,r)),
                t.scope.contextId(),t.observation.actorUserId(),request);
        return rows.isEmpty()?null:rows.get(0);
    }
    private Snapshot loadSource(Ticket t) {
        long version=t.command.draftVersion();
        var metadata=jdbc.query("""
            SELECT v.version,v.payload_format,v.canonical_payload,v.sha256,
             (SELECT max(n.version) FROM public.hidra_topology_engineering_draft_version n WHERE n.draft_id=v.draft_id) AS latest
            FROM public.hidra_topology_engineering_draft_version v WHERE v.draft_id=? AND v.version=?
            """,(r,n)->{
                if(r.getLong("latest")!=version)throw fail(Reason.CONFLICT);
                return component(r);
            },t.command.draftId(),version);
        if(metadata.size()!=1)throw fail(Reason.CONFLICT);
        var graphs=jdbc.query("SELECT version,payload_format,canonical_payload,sha256 FROM public.hidra_topology_engineering_graph_snapshot WHERE draft_id=? AND version<=? ORDER BY version DESC LIMIT 1",
                (r,n)->component(r),t.command.draftId(),version);
        Component graph=graphs.isEmpty()?null:graphs.get(0);
        var findings=new ArrayList<String>();
        var values=new EngineeringNetworkDraftCodec().decode(Base64.getDecoder().decode(metadata.get(0).base64()));
        if(values.title()==null)findings.add("TITLE_NOT_SUPPLIED");
        if(values.evidenceReference()==null)findings.add("EVIDENCE_NOT_SUPPLIED");
        var surveys=new ArrayList<Survey>();
        if(graph==null)findings.add("GRAPH_NOT_AUTHORED");
        else {
            var decoded=new EngineeringGraphDraftCodec().decode(Base64.getDecoder().decode(graph.base64()));
            findings.addAll(decoded.diagnostics());
            // One bounded as-of selection per branch set, never one query per survey point.
            var pipeIds=decoded.branches().stream().filter(branch->branch.kind()==EngineeringGraphDraft.BranchKind.PIPE)
                .map(EngineeringGraphDraft.Branch::id).toList();
            String surveyQuery="SELECT DISTINCT ON (branch_id) branch_id,version,graph_version,graph_sha256,payload_format,canonical_payload,sha256 FROM public.hidra_topology_engineering_survey_snapshot WHERE draft_id=? AND version<=? AND branch_id IN ("+String.join(",",Collections.nCopies(pipeIds.size(),"?"))+") ORDER BY branch_id,version DESC";
            var selectionArgs=new ArrayList<Object>();selectionArgs.add(t.command.draftId());selectionArgs.add(version);selectionArgs.addAll(pipeIds);
            var rows=new ArrayList<Survey>();
            if(!pipeIds.isEmpty()) {
                Long surveyBytes=jdbc.queryForObject("SELECT COALESCE(sum(octet_length(canonical_payload)),0) FROM ("+surveyQuery+") selected",Long.class,selectionArgs.toArray());
                long componentBytes=Base64.getDecoder().decode(metadata.get(0).base64()).length+Base64.getDecoder().decode(graph.base64()).length;
                if(surveyBytes==null || surveyBytes>EngineeringRevisionPublication.MAX_BYTES-componentBytes)throw fail(Reason.INVALID);
                rows.addAll(jdbc.query(surveyQuery,(r,n)->new Survey(r.getString("branch_id"),component(r),r.getLong("graph_version"),
                    r.getString("graph_sha256"),List.of(EngineeringRevisionPublication.UNQUALIFIED)),selectionArgs.toArray()));
            }
            if(rows.size()>EngineeringGraphDraft.MAX_BRANCHES)throw fail(Reason.UNAVAILABLE);
            var byBranch=new HashMap<String,Survey>();for(var row:rows)byBranch.put(row.branchId(),row);
            for(var branch:decoded.branches())if(branch.kind()==EngineeringGraphDraft.BranchKind.PIPE) {
                Survey candidate=byBranch.get(branch.id());
                if(candidate==null)candidate=new Survey(branch.id(),null,null,null,List.of("SURVEY_NOT_AUTHORED"));
                else if(candidate.graphVersion()!=graph.version() || !candidate.graphSha256().equals(graph.sha256()))
                    candidate=new Survey(branch.id(),null,null,null,List.of("SURVEY_GRAPH_STALE"));
                surveys.add(candidate);
            }
        }
        findings.add(EngineeringRevisionPublication.UNQUALIFIED);
        var snapshot=new Snapshot(t.scope,t.command.draftId(),version,metadata.get(0),graph,surveys,findings);
        try {codec.encode(snapshot);}catch(IllegalArgumentException invalid){throw new EngineeringRevisionPublicationFailure(Reason.INVALID,invalid);}
        return snapshot;
    }
    private Component component(ResultSet r)throws SQLException {
        byte[] bytes=r.getBytes("canonical_payload");
        return new Component(r.getLong("version"),r.getString("payload_format"),r.getString("sha256"),
                Base64.getEncoder().encodeToString(bytes));
    }
    private EngineeringRevisionPublication retained(Ticket t,UUID revisionId) {
        var cached=t.retained.get(revisionId);if(cached!=null)return cached;
        var rows=jdbc.query("SELECT draft_id,draft_version,payload_format,canonical_payload,sha256,engineering_qualified FROM public.hidra_topology_engineering_publication_snapshot WHERE context_id=? AND revision_id=?",
            (r,n)->{
                byte[] bytes=r.getBytes("canonical_payload");
                if(r.getBoolean("engineering_qualified") || !EngineeringRevisionPublicationCodec.FORMAT.equals(r.getString("payload_format"))
                    || !EngineeringRevisionPublication.hash(bytes).equals(r.getString("sha256")))throw fail(Reason.UNAVAILABLE);
                var snapshot=codec.decode(bytes,t.scope);
                if(!snapshot.draftId().equals(r.getObject("draft_id",UUID.class)) || snapshot.version()!=r.getLong("draft_version"))
                    throw fail(Reason.UNAVAILABLE);
                return new EngineeringRevisionPublication(revisionId,snapshot);
            },t.scope.contextId(),revisionId);
        if(rows.size()!=1)throw fail(Reason.NOT_FOUND);
        t.retained.put(revisionId,rows.get(0));return rows.get(0);
    }
    private String evidence(List<EngineeringGraphStationWitnessPort.Witness> witnesses) {
        try {
            var bytes=new ByteArrayOutputStream();var out=new DataOutputStream(bytes);
            out.writeInt(witnesses.size());for(var w:witnesses){out.writeUTF(w.facilityId());out.writeLong(w.ownerGeneration());}
            return Base64.getEncoder().encodeToString(bytes.toByteArray());
        }catch(IOException impossible){throw new IllegalStateException(impossible);}
    }
    private List<EngineeringGraphStationWitnessPort.Witness> witness(Snapshot snapshot) {
        var graph=snapshot.graph()==null?new EngineeringGraphDraft(List.of(),List.of(),List.of()):
                new EngineeringGraphDraftCodec().decode(Base64.getDecoder().decode(snapshot.graph().base64()));
        try {return stationWitness.requireCurrent(snapshot.scope().companyPartyId(),graph);}
        catch(RuntimeException conflict){throw new EngineeringRevisionPublicationFailure(Reason.CONFLICT,conflict);}
    }
    private void verifyRetainedStations(Ticket t,EngineeringRevisionPublication publication) {
        var current=witness(publication.snapshot());
        var original=jdbc.queryForList("SELECT station_evidence FROM public.hidra_topology_engineering_publication_event WHERE context_id=? AND revision_id=? AND operation='PUBLISH'",
                String.class,t.scope.contextId(),publication.revisionId());
        if(original.size()!=1 || !original.get(0).equals(evidence(current)))throw fail(Reason.CONFLICT);
        var all=new ArrayList<>(t.witnesses);all.addAll(current);t.witnesses=List.copyOf(all);
    }
    @Override @Transactional(propagation=Propagation.MANDATORY)
    public void resolve(Admission admission) {
        try {
            var t=ticket(admission);executor.requireAdmission();
            if(t.resolved || t.observation==null)throw fail(Reason.UNAVAILABLE);
            var c=t.command;var observed=t.observation;
            if(c.asOf()!=null && c.asOf().isAfter(observed.sampledAt()))throw fail(Reason.INVALID);
            t.before=head(t);t.after=t.before;
            if(mutation(c.kind())) {
                t.commandDigest=commandDigest(c);t.receipt=receiptRow(t,c.requestId());
                if(t.receipt!=null) {
                    try {t.event=t.receipt.replay(t.scope,observed.actorUserId(),c.requestId(),t.commandDigest);}
                    catch(IllegalArgumentException conflict){throw fail(Reason.CONFLICT);}
                    var publication=retained(t,t.event.revisionId());verifyRetainedStations(t,publication);
                    t.result=new Result(t.before,publication,List.of(t.event),t.receipt,null,true);
                }else {
                    UUID revision;
                    if(c.kind()==Kind.PUBLISH) {
                        t.source=loadSource(t);t.witnesses=witness(t.source);revision=UUID.randomUUID();
                    }else {
                        if(t.before==null)throw fail(Reason.CONFLICT);
                        revision=t.before.revisionId();
                        verifyRetainedStations(t,retained(t,revision));
                    }
                    t.stationEvidence=evidence(t.witnesses);
                    try {t.event=EngineeringRevisionPublication.transition(t.scope,t.before,c.expectedEpoch(),c.expectedRevisionId(),
                            Action.valueOf(c.kind().name()),revision,UUID.randomUUID(),c.requestId(),t.commandDigest,observed.sampledAt());}
                    catch(IllegalArgumentException conflict){throw fail(Reason.CONFLICT);}
                    t.after=t.event.head();
                    var publication=c.kind()==Kind.PUBLISH?new EngineeringRevisionPublication(revision,t.source):retained(t,revision);
                    t.retained.put(revision,publication);
                    t.receipt=new Receipt(t.scope,observed.actorUserId(),c.requestId(),t.commandDigest,t.event);
                    t.result=new Result(t.after,publication,List.of(t.event),t.receipt,null,false);
                    t.mutation=true;
                }
            }else {
                switch(c.kind()) {
                    case READ_EXACT -> {
                        var publication=retained(t,c.revisionId());verifyRetainedStations(t,publication);
                        t.result=new Result(t.before,publication,List.of(),null,null,false);
                    }
                    case READ_CURRENT -> {
                        var publication=t.before!=null && t.before.state()==State.ACTIVE?retained(t,t.before.revisionId()):null;
                        if(t.before!=null)verifyRetainedStations(t,retained(t,t.before.revisionId()));
                        t.result=new Result(t.before,publication,List.of(),null,null,false);
                    }
                    case READ_EFFECTIVE -> {
                        var events=jdbc.query("SELECT * FROM public.hidra_topology_engineering_publication_event WHERE context_id=? AND effective_at<=? ORDER BY effective_at DESC,epoch DESC LIMIT 1",
                            (r,n)->event(t.scope,r),t.scope.contextId(),Timestamp.from(c.asOf()));
                        EngineeringRevisionPublication publication=null;
                        if(!events.isEmpty() && events.get(0).state()==State.ACTIVE) {
                            publication=retained(t,events.get(0).revisionId());verifyRetainedStations(t,publication);
                        }
                        if(!events.isEmpty() && publication==null)verifyRetainedStations(t,retained(t,events.get(0).revisionId()));
                        t.result=new Result(t.before,publication,events,null,null,false);
                    }
                    case READ_REQUEST -> {
                        t.receipt=receiptRow(t,c.requestId());if(t.receipt==null)throw fail(Reason.NOT_FOUND);
                        var publication=retained(t,t.receipt.outcome().revisionId());verifyRetainedStations(t,publication);
                        t.result=new Result(t.before,publication,List.of(t.receipt.outcome()),t.receipt,null,false);
                    }
                    case LIST_HISTORY -> history(t);
                    default -> throw fail(Reason.INVALID);
                }
            }
            t.resolved=true;
        }catch(RuntimeException error){executor.rejectAdmission();throw error;}
    }
    private void history(Ticket t) {
        var c=t.command;
        long high=t.before==null?0:t.before.epoch();
        Long before=null;
        if(c.cursor()!=null) {
            Cursor cursor;
            try {cursor=cursors.decode(c.cursor(),t.scope,c.asOf());}catch(IllegalArgumentException invalid){throw fail(Reason.INVALID);}
            high=cursor.highWaterEpoch();before=cursor.beforeEpoch();
            if(t.before==null || high>t.before.epoch() || jdbc.queryForList(
                "SELECT epoch FROM public.hidra_topology_engineering_publication_event WHERE context_id=? AND epoch IN (?,?)",
                    Long.class,t.scope.contextId(),high,before).size()!=(high==before?1:2))throw fail(Reason.INVALID);
        }
        if(before!=null && c.asOf()!=null && jdbc.queryForList(
                "SELECT epoch FROM public.hidra_topology_engineering_publication_event WHERE context_id=? AND epoch=? AND effective_at<=?",
                Long.class,t.scope.contextId(),before,Timestamp.from(c.asOf())).size()!=1)throw fail(Reason.INVALID);
        var args=new ArrayList<Object>();args.add(t.scope.contextId());args.add(high);
        String query="SELECT * FROM public.hidra_topology_engineering_publication_event WHERE context_id=? AND epoch<=?";
        if(before!=null){query+=" AND epoch<?";args.add(before);}
        if(c.asOf()!=null){query+=" AND effective_at<=?";args.add(Timestamp.from(c.asOf()));}
        query+=" ORDER BY epoch DESC LIMIT ?";args.add(c.limit()+1);
        var events=jdbc.query(query,(r,n)->event(t.scope,r),args.toArray());
        String next=null;
        if(events.size()>c.limit()) {
            events=List.copyOf(events.subList(0,c.limit()));
            next=cursors.encode(new Cursor(t.scope,c.asOf(),high,events.get(events.size()-1).epoch()));
        }
        historyWitnesses(t,events);
        t.result=new Result(t.before,null,List.copyOf(events),null,next,false);
    }

    private void historyWitnesses(Ticket t,List<Event> events) {
        var revisions=events.stream().map(Event::revisionId).distinct().toList();
        if(revisions.isEmpty())return;
        var args=new ArrayList<Object>();args.add(t.scope.contextId());args.addAll(revisions);
        var evidence=jdbc.queryForList("SELECT station_evidence FROM public.hidra_topology_engineering_publication_event WHERE context_id=? AND operation='PUBLISH' AND revision_id IN ("+String.join(",",Collections.nCopies(revisions.size(),"?"))+")",String.class,args.toArray());
        if(evidence.size()!=revisions.size())throw fail(Reason.UNAVAILABLE);
        var witnesses=new ArrayList<EngineeringGraphStationWitnessPort.Witness>();
        for(String encoded:evidence) {
            if(encoded==null || encoded.length()>1048576)throw fail(Reason.UNAVAILABLE);
            byte[] bytes=Base64.getDecoder().decode(encoded);
            if(!Base64.getEncoder().encodeToString(bytes).equals(encoded))throw fail(Reason.UNAVAILABLE);
            try {
                var input=new DataInputStream(new ByteArrayInputStream(bytes));int count=input.readInt();
                if(count<0 || count>EngineeringGraphDraft.MAX_NODES)throw fail(Reason.UNAVAILABLE);
                for(int n=0;n<count;n++)witnesses.add(new EngineeringGraphStationWitnessPort.Witness(input.readUTF(),input.readLong()));
                if(input.available()!=0)throw fail(Reason.UNAVAILABLE);
            }catch(IOException invalid){throw new EngineeringRevisionPublicationFailure(Reason.UNAVAILABLE,invalid);}
        }
        t.witnesses=witnesses.stream().distinct().toList();
        if(!t.witnesses.isEmpty()) {
            var ids=t.witnesses.stream().map(EngineeringGraphStationWitnessPort.Witness::facilityId).toArray(String[]::new);
            var generations=t.witnesses.stream().map(EngineeringGraphStationWitnessPort.Witness::ownerGeneration).toArray(Long[]::new);
            if(!Boolean.TRUE.equals(jdbc.queryForObject("""
                SELECT NOT EXISTS(SELECT 1 FROM unnest(?::varchar[],?::bigint[]) expected(facility_id,generation)
                 LEFT JOIN public.hidra_topology_facility f ON f.id=expected.facility_id
                 LEFT JOIN public.hidra_topology_facility_owner_generation g ON g.facility_id=f.id
                 WHERE f.id IS NULL OR f.owner_party_id IS DISTINCT FROM ? OR f.status<>'ACTIVE'
                  OR f.facility_kind NOT IN ('STATION','COMPRESSOR_STATION','PUMP_STATION','METERING_STATION')
                  OR g.present IS DISTINCT FROM TRUE OR g.generation IS DISTINCT FROM expected.generation)
                """,Boolean.class,ids,generations,t.scope.companyPartyId())))throw fail(Reason.CONFLICT);
        }
    }

    @Override public Snapshot source(Admission admission){var t=ticket(admission);if(!t.resolved)throw fail(Reason.UNAVAILABLE);return t.source;}
    @Override public Head currentHead(Admission admission){var t=ticket(admission);if(!t.resolved)throw fail(Reason.UNAVAILABLE);return t.before;}
    @Override public Optional<Receipt> receipt(Admission admission){
        var t=ticket(admission);if(!t.resolved)throw fail(Reason.UNAVAILABLE);
        return t.mutation?Optional.empty():Optional.ofNullable(t.receipt);
    }
    @Override public Set<String> mutationSql(){return DML;}
    @Override @Transactional(propagation=Propagation.MANDATORY)
    public Result apply(Admission admission,Observation observation) {
        try {
            var t=ticket(admission);
            if(!t.resolved || t.applied || t.observation!=observation)throw fail(Reason.UNAVAILABLE);
            if(t.mutation) {
                var e=t.event;
                if(t.command.kind()==Kind.PUBLISH) {
                    byte[] bytes=codec.encode(t.source);
                    if(jdbc.update(SNAPSHOT_INS,e.revisionId(),t.scope.contextId(),t.source.draftId(),t.source.version(),
                        EngineeringRevisionPublicationCodec.FORMAT,bytes,EngineeringRevisionPublication.hash(bytes),t.xid)!=1)throw fail(Reason.CONFLICT);
                }
                if(jdbc.update(EVENT_INS,e.eventId(),t.scope.contextId(),e.epoch(),e.action().name(),e.priorRevisionId(),e.revisionId(),
                    e.state().name(),e.requestId(),e.commandSha256(),observation.nativeBindingId(),observation.sessionId(),
                    observation.actorUserId(),observation.partyGeneration(),t.stationEvidence,Timestamp.from(observation.sampledAt()),
                    Timestamp.from(observation.validUntil()),Timestamp.from(e.effectiveAt()),t.pid,t.xid)!=1)throw fail(Reason.CONFLICT);
                int updated=t.before==null?jdbc.update(HEAD_INS,t.scope.contextId(),e.revisionId(),e.epoch(),e.state().name(),e.eventId()):
                    jdbc.update(HEAD_UPDATE,e.revisionId(),e.epoch(),e.state().name(),e.eventId(),t.scope.contextId(),
                        t.before.epoch(),t.before.revisionId(),t.before.state().name(),t.before.eventId());
                if(updated!=1)throw fail(Reason.CONFLICT);
            }
            t.applied=true;return t.result;
        }catch(RuntimeException error){executor.rejectAdmission();throw error;}
    }
    @Override @Transactional(propagation=Propagation.MANDATORY)
    public void flush(){physical();em.flush();}

    @Override @Transactional(propagation=Propagation.MANDATORY)
    public boolean finalGuard(Admission admission,Observation observation,List<ProtectedRelationFence> fences) {
        var t=ticket(admission);
        if(!t.applied || t.observation!=observation || fences.size()!=38
            || fences.stream().map(ProtectedRelationFence::relation).distinct().count()!=38
            || fences.stream().filter(f->f.mode()==ProtectedRelationFence.Mode.SHARE_ROW_EXCLUSIVE).count()!=34
            || fences.stream().filter(f->f.mode()==ProtectedRelationFence.Mode.ACCESS_SHARE).count()!=3
            || fences.stream().filter(f->f.mode()==ProtectedRelationFence.Mode.SHARE).count()!=1)
            throw fail(Reason.UNAVAILABLE);
        var args=new ArrayList<Object>(Arrays.asList(Timestamp.from(observation.sampledAt()),
            Timestamp.from(observation.validUntil()),t.pid,t.xid,t.role,t.role,
            t.scope.companyPartyId(),observation.partyGeneration(),t.scope.pipelineSystemId(),
            t.scope.contextId(),t.scope.companyPartyId(),t.scope.pipelineSystemId(),t.command.draftId()));
        String guard="""
            WITH sampled_clock AS MATERIALIZED (SELECT clock_timestamp() AS t)
            SELECT (SELECT t FROM sampled_clock)>=?::timestamptz AND (SELECT t FROM sampled_clock)<?::timestamptz
             AND pg_backend_pid()=? AND txid_current()=? AND current_user=? AND session_user=?
             AND EXISTS(SELECT 1 FROM public.hidra_party_party p JOIN public.hidra_party_eligibility_generation g ON g.party_id=p.id
                WHERE p.id=? AND p.status='ACTIVE' AND g.generation=? AND g.present)
             AND EXISTS(SELECT 1 FROM public.hidra_topology_pipeline_system s JOIN public.hidra_topology_pipeline_system_type y ON y.id=s.system_type_id
                WHERE s.id=? AND s.status IN ('DRAFT','ACTIVE') AND y.active)
             AND EXISTS(SELECT 1 FROM public.hidra_topology_engineering_context x JOIN public.hidra_topology_engineering_draft d ON d.context_id=x.id
                WHERE x.id=? AND x.company_party_id=? AND x.pipeline_system_id=? AND d.id=?)
            """;
        if(t.after==null) {
            guard+=" AND NOT EXISTS(SELECT 1 FROM public.hidra_topology_engineering_publication_head WHERE context_id=?)";
            args.add(t.scope.contextId());
            guard+=" AND NOT EXISTS(SELECT 1 FROM public.hidra_topology_engineering_publication_event WHERE context_id=?)";
            args.add(t.scope.contextId());
        }else {
            guard+=" AND EXISTS(SELECT 1 FROM public.hidra_topology_engineering_publication_head h JOIN public.hidra_topology_engineering_publication_event e ON e.event_id=h.last_event_id WHERE h.context_id=? AND h.revision_id=? AND h.epoch=? AND h.state=? AND h.last_event_id=? AND e.context_id=h.context_id AND e.revision_id=h.revision_id AND e.epoch=h.epoch AND e.resulting_state=h.state AND e.effective_at=? AND NOT EXISTS(SELECT 1 FROM public.hidra_topology_engineering_publication_event n WHERE n.context_id=h.context_id AND n.epoch>h.epoch))";
            args.addAll(Arrays.asList(t.scope.contextId(),t.after.revisionId(),t.after.epoch(),t.after.state().name(),
                t.after.eventId(),Timestamp.from(t.after.effectiveAt())));
        }
        for(var publication:t.retained.values()) {
            Snapshot snapshot=publication.snapshot();byte[] bytes=codec.encode(snapshot);
            guard+=" AND EXISTS(SELECT 1 FROM public.hidra_topology_engineering_publication_snapshot p WHERE p.context_id=? AND p.revision_id=? AND p.draft_id=? AND p.draft_version=? AND p.payload_format=? AND p.canonical_payload=? AND p.sha256=? AND NOT p.engineering_qualified AND p.sha256=encode(sha256(p.canonical_payload),'hex'))";
            args.addAll(Arrays.asList(t.scope.contextId(),publication.revisionId(),snapshot.draftId(),snapshot.version(),
                EngineeringRevisionPublicationCodec.FORMAT,bytes,EngineeringRevisionPublication.hash(bytes)));
            guard+=" AND EXISTS(SELECT 1 FROM public.hidra_topology_engineering_draft d JOIN public.hidra_topology_engineering_draft_version v ON v.draft_id=d.id WHERE d.id=? AND d.context_id=? AND v.version=? AND v.payload_format=? AND v.sha256=? AND v.canonical_payload=? AND v.sha256=encode(sha256(v.canonical_payload),'hex'))";
            args.addAll(Arrays.asList(snapshot.draftId(),t.scope.contextId(),snapshot.version(),snapshot.metadata().format(),
                snapshot.metadata().sha256(),Base64.getDecoder().decode(snapshot.metadata().base64())));
            if(t.mutation && t.command.kind()==Kind.PUBLISH) {
                guard+=" AND NOT EXISTS(SELECT 1 FROM public.hidra_topology_engineering_draft_version WHERE draft_id=? AND version>?)";
                args.addAll(Arrays.asList(snapshot.draftId(),snapshot.version()));
            }
            if(snapshot.graph()==null) {
                guard+=" AND NOT EXISTS(SELECT 1 FROM public.hidra_topology_engineering_graph_snapshot WHERE draft_id=? AND version<=?)";
                args.addAll(Arrays.asList(snapshot.draftId(),snapshot.version()));
            }else {
                var graph=snapshot.graph();
                guard+=" AND EXISTS(SELECT 1 FROM public.hidra_topology_engineering_graph_snapshot g WHERE g.draft_id=? AND g.version=? AND g.sha256=? AND g.payload_format=? AND g.canonical_payload=? AND g.sha256=encode(sha256(g.canonical_payload),'hex') AND NOT EXISTS(SELECT 1 FROM public.hidra_topology_engineering_graph_snapshot n WHERE n.draft_id=g.draft_id AND n.version>g.version AND n.version<=?))";
                args.addAll(Arrays.asList(snapshot.draftId(),graph.version(),graph.sha256(),graph.format(),
                    Base64.getDecoder().decode(graph.base64()),snapshot.version()));
                for(var survey:snapshot.surveys()) {
                    if(survey.source()==null) {
                        if(survey.diagnostics().contains("SURVEY_GRAPH_STALE")) {
                            guard+=" AND EXISTS(SELECT 1 FROM public.hidra_topology_engineering_survey_snapshot s WHERE s.draft_id=? AND s.branch_id=? AND s.version<=? AND (s.graph_version<>? OR s.graph_sha256<>?) AND NOT EXISTS(SELECT 1 FROM public.hidra_topology_engineering_survey_snapshot n WHERE n.draft_id=s.draft_id AND n.branch_id=s.branch_id AND n.version>s.version AND n.version<=?))";
                            args.addAll(Arrays.asList(snapshot.draftId(),survey.branchId(),snapshot.version(),graph.version(),graph.sha256(),snapshot.version()));
                        }else {
                            guard+=" AND NOT EXISTS(SELECT 1 FROM public.hidra_topology_engineering_survey_snapshot WHERE draft_id=? AND branch_id=? AND version<=?)";
                            args.addAll(Arrays.asList(snapshot.draftId(),survey.branchId(),snapshot.version()));
                        }
                    }else {
                        var source=survey.source();
                        guard+=" AND EXISTS(SELECT 1 FROM public.hidra_topology_engineering_survey_snapshot s WHERE s.draft_id=? AND s.branch_id=? AND s.version=? AND s.graph_version=? AND s.graph_sha256=? AND s.payload_format=? AND s.canonical_payload=? AND s.sha256=? AND s.sha256=encode(sha256(s.canonical_payload),'hex') AND NOT EXISTS(SELECT 1 FROM public.hidra_topology_engineering_survey_snapshot n WHERE n.draft_id=s.draft_id AND n.branch_id=s.branch_id AND n.version>s.version AND n.version<=?))";
                        args.addAll(Arrays.asList(snapshot.draftId(),survey.branchId(),source.version(),survey.graphVersion(),
                            survey.graphSha256(),source.format(),Base64.getDecoder().decode(source.base64()),source.sha256(),snapshot.version()));
                    }
                }
            }
        }
        for(Event event:t.result.events()) {
            guard+=" AND EXISTS(SELECT 1 FROM public.hidra_topology_engineering_publication_event e WHERE e.event_id=? AND e.context_id=? AND e.epoch=? AND e.operation=? AND e.prior_revision_id IS NOT DISTINCT FROM ?::uuid AND e.revision_id=? AND e.resulting_state=? AND e.request_id=? AND e.command_sha256=? AND e.effective_at=? AND EXISTS(SELECT 1 FROM public.hidra_topology_engineering_publication_snapshot ps JOIN public.hidra_topology_engineering_draft d ON d.id=ps.draft_id AND d.context_id=ps.context_id JOIN public.hidra_topology_engineering_draft_version v ON v.draft_id=ps.draft_id AND v.version=ps.draft_version WHERE ps.revision_id=e.revision_id AND ps.context_id=e.context_id AND ps.sha256=encode(sha256(ps.canonical_payload),'hex') AND NOT ps.engineering_qualified AND v.sha256=encode(sha256(v.canonical_payload),'hex')))";
            args.addAll(Arrays.asList(event.eventId(),event.scope().contextId(),event.epoch(),event.action().name(),
                event.priorRevisionId(),event.revisionId(),event.state().name(),event.requestId(),event.commandSha256(),Timestamp.from(event.effectiveAt())));
        }
        if(t.receipt!=null) {
            guard+=" AND EXISTS(SELECT 1 FROM public.hidra_topology_engineering_publication_event e WHERE e.context_id=? AND e.actor_user_id=? AND e.request_id=? AND e.command_sha256=? AND e.event_id=?)";
            args.addAll(Arrays.asList(t.scope.contextId(),observation.actorUserId(),t.receipt.requestId(),t.receipt.commandSha256(),t.receipt.outcome().eventId()));
        }
        if(t.mutation) {
            guard+=" AND EXISTS(SELECT 1 FROM public.hidra_topology_engineering_publication_event e WHERE e.event_id=? AND e.actor_binding_id=?::uuid AND e.actor_session_id=?::uuid AND e.actor_user_id=? AND e.party_generation=? AND e.station_evidence=? AND e.sampled_at=? AND e.valid_until=? AND e.effective_at=e.sampled_at AND e.backend_pid=? AND e.transaction_id=?)";
            args.addAll(Arrays.asList(t.event.eventId(),observation.nativeBindingId(),observation.sessionId(),
                observation.actorUserId(),observation.partyGeneration(),t.stationEvidence,Timestamp.from(observation.sampledAt()),
                Timestamp.from(observation.validUntil()),t.pid,t.xid));
        }
        if(!t.witnesses.isEmpty()) {
            var unique=t.witnesses.stream().distinct().toList();
            guard+=" AND NOT EXISTS(SELECT 1 FROM unnest(?::varchar[],?::bigint[]) required(facility_id,generation) LEFT JOIN public.hidra_topology_facility f ON f.id=required.facility_id LEFT JOIN public.hidra_topology_facility_owner_generation g ON g.facility_id=f.id WHERE f.id IS NULL OR f.owner_party_id IS DISTINCT FROM ? OR f.status<>'ACTIVE' OR f.facility_kind NOT IN ('STATION','COMPRESSOR_STATION','PUMP_STATION','METERING_STATION') OR g.present IS DISTINCT FROM TRUE OR g.generation IS DISTINCT FROM required.generation)";
            args.add(unique.stream().map(EngineeringGraphStationWitnessPort.Witness::facilityId).toArray(String[]::new));
            args.add(unique.stream().map(EngineeringGraphStationWitnessPort.Witness::ownerGeneration).toArray(Long[]::new));
            args.add(t.scope.companyPartyId());
        }
        guard+=" AND ("+CATALOG+") AND ("+GRAPH_CATALOG+") AND ("+SURVEY_CATALOG+") AND ("+PUBLICATION_CATALOG+")";
        guard+=" AND NOT pg_has_role(?::name,?::name,'MEMBER') AND NOT has_schema_privilege(?::name,'public','CREATE') AND NOT has_schema_privilege(?::name,'pg_catalog','CREATE') AND NOT EXISTS(SELECT 1 FROM pg_roles r WHERE (r.rolname=?::name OR pg_has_role(?::name,r.oid,'MEMBER')) AND (r.rolsuper OR r.rolbypassrls OR r.rolcreaterole OR r.rolcreatedb OR r.rolreplication OR r.rolname IN ('pg_execute_server_program','pg_read_server_files','pg_write_server_files')))";
        args.addAll(Arrays.asList(t.ordinary,t.role,t.ordinary,t.ordinary,t.ordinary,t.ordinary));
        for(String relation:RELATIONS.subList(7,12)) {
            guard+=" AND NOT EXISTS(SELECT 1 FROM pg_class c WHERE c.oid=?::regclass AND (pg_has_role(?::name,c.relowner,'MEMBER') OR has_table_privilege(?::name,c.oid,'SELECT,INSERT,UPDATE,DELETE,TRUNCATE,REFERENCES,TRIGGER') OR has_any_column_privilege(?::name,c.oid,'SELECT,INSERT,UPDATE,REFERENCES')))";
            args.addAll(Arrays.asList(relation,t.ordinary,t.ordinary,t.ordinary));
        }
        guard+=" AND NOT EXISTS(SELECT 1 FROM (VALUES "+String.join(",",Collections.nCopies(fences.size(),"(?::regclass,?::text)"))+") required(relation,mode) WHERE NOT EXISTS(SELECT 1 FROM pg_locks l WHERE l.pid=pg_backend_pid() AND l.granted AND l.locktype='relation' AND l.relation=required.relation AND l.mode=required.mode))";
        for(var fence:fences){args.add(fence.relation());args.add(fence.mode().catalog());}
        return Boolean.TRUE.equals(jdbc.queryForObject(guard,Boolean.class,args.toArray()));
    }

}
