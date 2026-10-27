/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : JdbcSuppliedFluidPropertyRevisionRepositoryAdapter
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-27
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : custody
 * @Package     : dz.sh.hidra.modules.custody.infrastructure.persistence.adapter
 *
 * @Description : Preserves exact authored fluid facts independently of numerical qualification.
 *
 */
package dz.sh.hidra.modules.custody.infrastructure.persistence.adapter;
import dz.sh.hidra.modules.custody.application.port.in.SuppliedFluidPropertyRevisionUseCase.*;
import dz.sh.hidra.modules.custody.application.port.out.SuppliedFluidPropertyRevisionRepositoryPort;
import dz.sh.hidra.modules.custody.application.service.ProtectedSuppliedFluidPropertyAdmissionCoordinator.Contribution;
import dz.sh.hidra.modules.custody.domain.model.SuppliedFluidPropertyRevision;
import dz.sh.hidra.modules.custody.domain.model.SuppliedFluidPropertyRevision.*;
import dz.sh.hidra.platform.persistence.*;
import java.sql.*;
import java.util.*;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.ConnectionHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.*;
import org.springframework.transaction.support.*;
@Repository
public class JdbcSuppliedFluidPropertyRevisionRepositoryAdapter implements SuppliedFluidPropertyRevisionRepositoryPort {
    private static final String ROOT_INSERT="INSERT INTO public.hidra_custody_supplied_fluid_property_draft (product_draft_id) VALUES (?)";
    private static final String VERSION_INSERT="INSERT INTO public.hidra_custody_supplied_fluid_property_version (product_draft_id,version,request_id,request_sha256,expected_version,product_metadata_version,classification,payload_format,canonical_payload,sha256,predecessor_sha256,actor_binding_id,actor_session_id,actor_user_id,party_generation,sampled_at,valid_until,backend_pid,transaction_id) SELECT ?,?,?,?,?,?,?,?,?,?,?,?::uuid,?::uuid,?,?,?,?,?,? WHERE COALESCE((SELECT max(version) FROM public.hidra_custody_supplied_fluid_property_version WHERE product_draft_id=?),0)=?";
    private static final Set<String> SQL=Set.of(ROOT_INSERT,VERSION_INSERT);
    private static final String ROOT="public.hidra_custody_supplied_fluid_property_draft",VERSIONS="public.hidra_custody_supplied_fluid_property_version";
    private static final List<String> RELATIONS=List.of("public.hidra_custody_catalog_entry","public.hidra_custody_engineering_product_policy","public.hidra_custody_product_engineering_draft","public.hidra_custody_product_engineering_draft_version",ROOT,VERSIONS);
    private static final String CATALOG="""
        SELECT current_setting('session_replication_role')='origin'
          AND NOT has_schema_privilege(current_user,'public','CREATE')
          AND NOT has_schema_privilege(current_user,'pg_catalog','CREATE')
          AND NOT EXISTS (SELECT 1 FROM pg_roles r WHERE (r.rolname=current_user OR pg_has_role(current_user,r.oid,'MEMBER'))
            AND (r.rolsuper OR r.rolbypassrls OR r.rolcreaterole OR r.rolcreatedb OR r.rolreplication
              OR r.rolname IN ('pg_execute_server_program','pg_read_server_files','pg_write_server_files')))
          AND (SELECT count(*)=4 AND bool_and(c.relkind='r' AND c.relpersistence='p' AND NOT c.relrowsecurity
            AND NOT c.relforcerowsecurity AND NOT c.relispartition AND NOT pg_has_role(current_user,c.relowner,'MEMBER')
            AND has_table_privilege(current_user,c.oid,'SELECT') AND has_table_privilege(current_user,c.oid,'UPDATE')
            AND NOT has_table_privilege(current_user,c.oid,'DELETE,TRUNCATE,TRIGGER')
            AND NOT EXISTS (SELECT 1 FROM pg_inherits h WHERE h.inhrelid=c.oid OR h.inhparent=c.oid))
            FROM pg_class c WHERE c.oid IN ('public.hidra_custody_catalog_entry'::regclass,
              'public.hidra_custody_engineering_product_policy'::regclass,
              'public.hidra_custody_product_engineering_draft'::regclass,'public.hidra_custody_product_engineering_draft_version'::regclass))
          AND has_table_privilege(current_user,'public.hidra_custody_product_engineering_draft','INSERT')
          AND has_table_privilege(current_user,'public.hidra_custody_product_engineering_draft_version','INSERT')
          AND NOT has_table_privilege(current_user,'public.hidra_custody_engineering_product_policy','INSERT')
          AND (SELECT count(*)=5 AND bool_and(t.tgenabled='O' AND t.tgqual IS NULL AND t.tgnargs=0 AND t.tgparentid=0
            AND cardinality(t.tgattr::smallint[])=0 AND t.tgconstraint=0
            AND (t.tgname,t.tgrelid,t.tgtype) IN (
              ('custody_product_draft_immutable','public.hidra_custody_product_engineering_draft'::regclass,26),
              ('custody_product_draft_no_truncate','public.hidra_custody_product_engineering_draft'::regclass,34),
              ('custody_product_draft_version_immutable','public.hidra_custody_product_engineering_draft_version'::regclass,26),
              ('custody_product_draft_version_no_truncate','public.hidra_custody_product_engineering_draft_version'::regclass,34),
              ('custody_product_next_version','public.hidra_custody_product_engineering_draft_version'::regclass,7)) AND
            ((t.tgfoid='public.hidra_custody_product_draft_immutable()'::regprocedure AND t.tgtype IN (26,34))
             OR (t.tgfoid='public.hidra_custody_product_draft_next_version()'::regprocedure AND t.tgtype=7
                AND t.tgrelid='public.hidra_custody_product_engineering_draft_version'::regclass)))
            FROM pg_trigger t WHERE NOT t.tgisinternal AND t.tgrelid IN
              ('public.hidra_custody_product_engineering_draft'::regclass,'public.hidra_custody_product_engineering_draft_version'::regclass))
          AND (SELECT count(*)=2 AND bool_and(p.prosecdef AND p.prokind='f' AND p.pronargs=0
             AND p.prorettype='trigger'::regtype AND p.provolatile='v' AND NOT p.proleakproof
             AND p.proconfig=ARRAY['search_path=pg_catalog, pg_temp']
             AND p.prolang=(SELECT oid FROM pg_language WHERE lanname='plpgsql')
             AND NOT pg_has_role(current_user,p.proowner,'MEMBER') AND NOT has_function_privilege(current_user,p.oid,'EXECUTE')
             AND ((p.oid='public.hidra_custody_product_draft_immutable()'::regprocedure AND md5(p.prosrc)='340d54e302d11e99fe5f495227738180')
               OR (p.oid='public.hidra_custody_product_draft_next_version()'::regprocedure AND md5(p.prosrc)='563784c350c5b861d81cb408f0bba6c8')))
            FROM pg_proc p WHERE p.oid IN ('public.hidra_custody_product_draft_immutable()'::regprocedure,'public.hidra_custody_product_draft_next_version()'::regprocedure))
          AND (SELECT count(*)=2 AND bool_and(a.attcollation='"C"'::regcollation)
            FROM pg_attribute a WHERE a.attrelid='public.hidra_custody_product_engineering_draft'::regclass
              AND a.attname IN ('company_party_id','local_code'))
          AND (SELECT count(*)=8 AND bool_and(k.convalidated AND NOT k.condeferrable)
            FROM pg_constraint k WHERE k.conrelid='public.hidra_custody_product_engineering_draft'::regclass)
          AND (SELECT count(*)=17 AND bool_and(k.convalidated AND NOT k.condeferrable)
            FROM pg_constraint k WHERE k.conrelid='public.hidra_custody_product_engineering_draft_version'::regclass)
          AND (SELECT count(*)=2 AND bool_and(k.convalidated AND NOT k.condeferrable)
            FROM pg_constraint k WHERE k.conrelid='public.hidra_custody_engineering_product_policy'::regclass)
          AND NOT EXISTS(SELECT 1 FROM pg_trigger t WHERE NOT t.tgisinternal AND t.tgrelid='public.hidra_custody_engineering_product_policy'::regclass)
          AND EXISTS(SELECT 1 FROM pg_constraint k WHERE k.conrelid='public.hidra_custody_engineering_product_policy'::regclass
            AND k.contype='f' AND k.conkey=ARRAY[1]::smallint[] AND k.confrelid='public.hidra_custody_catalog_entry'::regclass AND k.confkey=ARRAY[1]::smallint[])
          AND EXISTS(SELECT 1 FROM pg_constraint k WHERE k.conrelid='public.hidra_custody_product_engineering_draft'::regclass
            AND k.contype='f' AND k.conkey=ARRAY[4]::smallint[] AND k.confrelid='public.hidra_custody_catalog_entry'::regclass AND k.confkey=ARRAY[1]::smallint[])
          AND (SELECT count(*)=2 FROM pg_constraint k WHERE k.conrelid='public.hidra_custody_product_engineering_draft'::regclass
            AND k.contype='u' AND k.conkey IN (ARRAY[2,3]::smallint[],ARRAY[2,5]::smallint[]))
          AND EXISTS(SELECT 1 FROM pg_constraint k WHERE k.conrelid='public.hidra_custody_product_engineering_draft_version'::regclass
            AND k.contype='f' AND k.conkey=ARRAY[1]::smallint[] AND k.confrelid='public.hidra_custody_product_engineering_draft'::regclass AND k.confkey=ARRAY[1]::smallint[])
          AND (SELECT array_agg(a.attname::text ORDER BY a.attnum)=ARRAY['id','company_party_id','local_code','catalog_product_id','creation_request_id','request_sha256','created_at']
               AND bool_and(a.attnotnull AND NOT a.attisdropped AND a.attidentity='' AND a.attgenerated='')
            FROM pg_attribute a WHERE a.attrelid='public.hidra_custody_product_engineering_draft'::regclass AND a.attnum>0)
          AND (SELECT array_agg(a.attname::text ORDER BY a.attnum)=ARRAY['draft_id','version','payload_format','canonical_payload','sha256','predecessor_sha256','name','description','evidence_reference','classification','actor_binding_id','actor_session_id','actor_user_id','party_generation','sampled_at','valid_until','backend_pid','transaction_id','created_at']
               AND bool_and(NOT a.attisdropped AND a.attidentity='' AND a.attgenerated=''
                 AND a.attnotnull=(a.attname NOT IN ('predecessor_sha256','name','description','evidence_reference','classification')))
            FROM pg_attribute a WHERE a.attrelid='public.hidra_custody_product_engineering_draft_version'::regclass AND a.attnum>0)
          AND NOT EXISTS(SELECT 1 FROM (VALUES
            ('public.hidra_custody_engineering_product_policy'::regclass,1,'catalog_product_id','varchar'::regtype,84,true),
            ('public.hidra_custody_engineering_product_policy'::regclass,2,'active','bool'::regtype,-1,true),
            ('public.hidra_custody_engineering_product_policy'::regclass,3,'updated_at','timestamptz'::regtype,-1,true),
            ('public.hidra_custody_product_engineering_draft'::regclass,1,'id','uuid'::regtype,-1,true),
            ('public.hidra_custody_product_engineering_draft'::regclass,2,'company_party_id','varchar'::regtype,84,true),
            ('public.hidra_custody_product_engineering_draft'::regclass,3,'local_code','varchar'::regtype,84,true),
            ('public.hidra_custody_product_engineering_draft'::regclass,4,'catalog_product_id','varchar'::regtype,84,true),
            ('public.hidra_custody_product_engineering_draft'::regclass,5,'creation_request_id','uuid'::regtype,-1,true),
            ('public.hidra_custody_product_engineering_draft'::regclass,6,'request_sha256','varchar'::regtype,68,true),
            ('public.hidra_custody_product_engineering_draft'::regclass,7,'created_at','timestamptz'::regtype,-1,true),
            ('public.hidra_custody_product_engineering_draft_version'::regclass,1,'draft_id','uuid'::regtype,-1,true),
            ('public.hidra_custody_product_engineering_draft_version'::regclass,2,'version','int8'::regtype,-1,true),
            ('public.hidra_custody_product_engineering_draft_version'::regclass,3,'payload_format','varchar'::regtype,44,true),
            ('public.hidra_custody_product_engineering_draft_version'::regclass,4,'canonical_payload','bytea'::regtype,-1,true),
            ('public.hidra_custody_product_engineering_draft_version'::regclass,5,'sha256','varchar'::regtype,68,true),
            ('public.hidra_custody_product_engineering_draft_version'::regclass,6,'predecessor_sha256','varchar'::regtype,68,false),
            ('public.hidra_custody_product_engineering_draft_version'::regclass,7,'name','varchar'::regtype,244,false),
            ('public.hidra_custody_product_engineering_draft_version'::regclass,8,'description','varchar'::regtype,4004,false),
            ('public.hidra_custody_product_engineering_draft_version'::regclass,9,'evidence_reference','varchar'::regtype,1004,false),
            ('public.hidra_custody_product_engineering_draft_version'::regclass,10,'classification','varchar'::regtype,10,false),
            ('public.hidra_custody_product_engineering_draft_version'::regclass,11,'actor_binding_id','uuid'::regtype,-1,true),
            ('public.hidra_custody_product_engineering_draft_version'::regclass,12,'actor_session_id','uuid'::regtype,-1,true),
            ('public.hidra_custody_product_engineering_draft_version'::regclass,13,'actor_user_id','varchar'::regtype,84,true),
            ('public.hidra_custody_product_engineering_draft_version'::regclass,14,'party_generation','int8'::regtype,-1,true),
            ('public.hidra_custody_product_engineering_draft_version'::regclass,15,'sampled_at','timestamptz'::regtype,-1,true),
            ('public.hidra_custody_product_engineering_draft_version'::regclass,16,'valid_until','timestamptz'::regtype,-1,true),
            ('public.hidra_custody_product_engineering_draft_version'::regclass,17,'backend_pid','int4'::regtype,-1,true),
            ('public.hidra_custody_product_engineering_draft_version'::regclass,18,'transaction_id','int8'::regtype,-1,true),
            ('public.hidra_custody_product_engineering_draft_version'::regclass,19,'created_at','timestamptz'::regtype,-1,true)) expected(relation,position,name,type,typmod,required) LEFT JOIN pg_attribute a ON a.attrelid=expected.relation AND a.attnum=expected.position WHERE a.attname IS DISTINCT FROM expected.name OR a.atttypid IS DISTINCT FROM expected.type OR a.atttypmod IS DISTINCT FROM expected.typmod OR a.attnotnull IS DISTINCT FROM expected.required OR a.attisdropped OR a.attidentity<>'' OR a.attgenerated<>'')
        """;

    private static final String FLUID_CATALOG="""
        SELECT (SELECT count(*)=2 AND bool_and(c.relkind='r' AND c.relpersistence='p' AND NOT c.relrowsecurity AND NOT c.relforcerowsecurity
          AND NOT c.relispartition AND NOT pg_has_role(current_user,c.relowner,'MEMBER')
          AND has_table_privilege(current_user,c.oid,'SELECT') AND has_table_privilege(current_user,c.oid,'INSERT') AND has_table_privilege(current_user,c.oid,'UPDATE')
          AND NOT has_table_privilege(current_user,c.oid,'DELETE,TRUNCATE,TRIGGER')
          AND NOT EXISTS(SELECT 1 FROM pg_inherits h WHERE h.inhrelid=c.oid OR h.inhparent=c.oid))
          FROM pg_class c WHERE c.oid IN ('public.hidra_custody_supplied_fluid_property_draft'::regclass,'public.hidra_custody_supplied_fluid_property_version'::regclass))
        AND (SELECT count(*)=5 AND bool_and(t.tgenabled='O' AND t.tgqual IS NULL AND t.tgnargs=0 AND t.tgparentid=0 AND cardinality(t.tgattr::smallint[])=0 AND t.tgconstraint=0
          AND ((t.tgfoid='public.hidra_custody_supplied_fluid_immutable()'::regprocedure AND (t.tgname,t.tgrelid,t.tgtype) IN (
            ('custody_supplied_fluid_immutable','public.hidra_custody_supplied_fluid_property_draft'::regclass,26),
            ('custody_supplied_fluid_no_truncate','public.hidra_custody_supplied_fluid_property_draft'::regclass,34),
            ('custody_supplied_fluid_version_immutable','public.hidra_custody_supplied_fluid_property_version'::regclass,26),
            ('custody_supplied_fluid_version_no_truncate','public.hidra_custody_supplied_fluid_property_version'::regclass,34)))
            OR (t.tgfoid='public.hidra_custody_supplied_fluid_next_version()'::regprocedure AND t.tgname='custody_supplied_fluid_next_version'
              AND t.tgrelid='public.hidra_custody_supplied_fluid_property_version'::regclass AND t.tgtype=7)))
          FROM pg_trigger t WHERE NOT t.tgisinternal AND t.tgrelid IN ('public.hidra_custody_supplied_fluid_property_draft'::regclass,'public.hidra_custody_supplied_fluid_property_version'::regclass))
        AND (SELECT count(*)=2 AND bool_and(p.prosecdef AND p.prokind='f' AND p.pronargs=0 AND p.prorettype='trigger'::regtype
          AND p.provolatile='v' AND NOT p.proleakproof AND p.proconfig=ARRAY['search_path=pg_catalog, pg_temp']
          AND p.prolang=(SELECT oid FROM pg_language WHERE lanname='plpgsql')
          AND NOT pg_has_role(current_user,p.proowner,'MEMBER') AND NOT has_function_privilege(current_user,p.oid,'EXECUTE')
          AND ((p.oid='public.hidra_custody_supplied_fluid_immutable()'::regprocedure AND md5(p.prosrc)='e9a43398f3918a7299560e7eba9b7ef9')
            OR (p.oid='public.hidra_custody_supplied_fluid_next_version()'::regprocedure AND md5(p.prosrc)='f2912c37f158872cf0e3a0246b5c0bc7')))
          FROM pg_proc p WHERE p.oid IN ('public.hidra_custody_supplied_fluid_immutable()'::regprocedure,'public.hidra_custody_supplied_fluid_next_version()'::regprocedure))
        AND (SELECT count(*)=2 AND bool_and(k.convalidated AND NOT k.condeferrable) FROM pg_constraint k WHERE k.conrelid='public.hidra_custody_supplied_fluid_property_draft'::regclass)
        AND (SELECT count(*)=18 AND bool_and(k.convalidated AND NOT k.condeferrable) FROM pg_constraint k WHERE k.conrelid='public.hidra_custody_supplied_fluid_property_version'::regclass)
        AND EXISTS(SELECT 1 FROM pg_constraint k WHERE k.conrelid='public.hidra_custody_supplied_fluid_property_draft'::regclass AND k.contype='p' AND k.conkey=ARRAY[1]::smallint[])
        AND EXISTS(SELECT 1 FROM pg_constraint k WHERE k.conrelid='public.hidra_custody_supplied_fluid_property_draft'::regclass AND k.contype='f' AND k.conkey=ARRAY[1]::smallint[] AND k.confrelid='public.hidra_custody_product_engineering_draft'::regclass AND k.confkey=ARRAY[1]::smallint[])
        AND EXISTS(SELECT 1 FROM pg_constraint k WHERE k.conrelid='public.hidra_custody_supplied_fluid_property_version'::regclass AND k.contype='p' AND k.conkey=ARRAY[1,2]::smallint[])
        AND EXISTS(SELECT 1 FROM pg_constraint k WHERE k.conrelid='public.hidra_custody_supplied_fluid_property_version'::regclass AND k.contype='u' AND k.conkey=ARRAY[1,3]::smallint[])
        AND EXISTS(SELECT 1 FROM pg_constraint k WHERE k.conrelid='public.hidra_custody_supplied_fluid_property_version'::regclass AND k.contype='f' AND k.conkey=ARRAY[1]::smallint[] AND k.confrelid='public.hidra_custody_supplied_fluid_property_draft'::regclass AND k.confkey=ARRAY[1]::smallint[])
        AND EXISTS(SELECT 1 FROM pg_constraint k WHERE k.conrelid='public.hidra_custody_supplied_fluid_property_version'::regclass AND k.contype='f' AND k.conkey=ARRAY[1,6]::smallint[] AND k.confrelid='public.hidra_custody_product_engineering_draft_version'::regclass AND k.confkey=ARRAY[1,2]::smallint[])
        AND (SELECT count(*)=2 FROM pg_attribute a WHERE a.attrelid='public.hidra_custody_supplied_fluid_property_draft'::regclass AND a.attnum>0)
        AND (SELECT count(*)=20 FROM pg_attribute a WHERE a.attrelid='public.hidra_custody_supplied_fluid_property_version'::regclass AND a.attnum>0)
        AND NOT EXISTS(SELECT 1 FROM (VALUES ('public.hidra_custody_supplied_fluid_property_draft'::regclass,1,'product_draft_id','uuid'::regtype,-1,true),
('public.hidra_custody_supplied_fluid_property_draft'::regclass,2,'created_at','timestamptz'::regtype,-1,true),
('public.hidra_custody_supplied_fluid_property_version'::regclass,1,'product_draft_id','uuid'::regtype,-1,true),
('public.hidra_custody_supplied_fluid_property_version'::regclass,2,'version','int8'::regtype,-1,true),
('public.hidra_custody_supplied_fluid_property_version'::regclass,3,'request_id','uuid'::regtype,-1,true),
('public.hidra_custody_supplied_fluid_property_version'::regclass,4,'request_sha256','varchar'::regtype,68,true),
('public.hidra_custody_supplied_fluid_property_version'::regclass,5,'expected_version','int8'::regtype,-1,true),
('public.hidra_custody_supplied_fluid_property_version'::regclass,6,'product_metadata_version','int8'::regtype,-1,true),
('public.hidra_custody_supplied_fluid_property_version'::regclass,7,'classification','varchar'::regtype,10,true),
('public.hidra_custody_supplied_fluid_property_version'::regclass,8,'payload_format','varchar'::regtype,44,true),
('public.hidra_custody_supplied_fluid_property_version'::regclass,9,'canonical_payload','bytea'::regtype,-1,true),
('public.hidra_custody_supplied_fluid_property_version'::regclass,10,'sha256','varchar'::regtype,68,true),
('public.hidra_custody_supplied_fluid_property_version'::regclass,11,'predecessor_sha256','varchar'::regtype,68,false),
('public.hidra_custody_supplied_fluid_property_version'::regclass,12,'actor_binding_id','uuid'::regtype,-1,true),
('public.hidra_custody_supplied_fluid_property_version'::regclass,13,'actor_session_id','uuid'::regtype,-1,true),
('public.hidra_custody_supplied_fluid_property_version'::regclass,14,'actor_user_id','varchar'::regtype,84,true),
('public.hidra_custody_supplied_fluid_property_version'::regclass,15,'party_generation','int8'::regtype,-1,true),
('public.hidra_custody_supplied_fluid_property_version'::regclass,16,'sampled_at','timestamptz'::regtype,-1,true),
('public.hidra_custody_supplied_fluid_property_version'::regclass,17,'valid_until','timestamptz'::regtype,-1,true),
('public.hidra_custody_supplied_fluid_property_version'::regclass,18,'backend_pid','int4'::regtype,-1,true),
('public.hidra_custody_supplied_fluid_property_version'::regclass,19,'transaction_id','int8'::regtype,-1,true),
('public.hidra_custody_supplied_fluid_property_version'::regclass,20,'created_at','timestamptz'::regtype,-1,true)) expected(relation,position,name,type,typmod,required)
          LEFT JOIN pg_attribute a ON a.attrelid=expected.relation AND a.attnum=expected.position WHERE a.attname IS DISTINCT FROM expected.name OR a.atttypid IS DISTINCT FROM expected.type OR a.atttypmod IS DISTINCT FROM expected.typmod OR a.attnotnull IS DISTINCT FROM expected.required OR a.attisdropped OR a.attidentity<>'' OR a.attgenerated<>'')
        """;

    private final JdbcTemplate jdbc;private final ProtectedTransactionExecutor executor;
    private final SuppliedFluidPropertyRevisionCodec codec=new SuppliedFluidPropertyRevisionCodec();
    public JdbcSuppliedFluidPropertyRevisionRepositoryAdapter(JdbcTemplate jdbc,ProtectedTransactionExecutor executor){this.jdbc=Objects.requireNonNull(jdbc);this.executor=Objects.requireNonNull(executor);}
    private final class Ticket implements Admission,TransactionSynchronization {
        final JdbcSuppliedFluidPropertyRevisionRepositoryAdapter owner=JdbcSuppliedFluidPropertyRevisionRepositoryAdapter.this;
        final Command command;final Thread thread=Thread.currentThread();final ConnectionHolder holder;final Connection connection;
        final int pid;final long xid;final String role,product,code;final long metadataVersion;final String metadataDigest;final byte[] metadataBytes;final String metadataClassification;
        volatile boolean active=true,rejected;boolean eligible,applied,replayed;long latest;String latestDigest,requestDigest;byte[] latestBytes;Result result;
        Ticket(Command command,ConnectionHolder holder,int pid,long xid,String role,String product,String code,long mv,String md,byte[] mb,String mc){this.command=command;this.holder=holder;connection=holder.getConnection();this.pid=pid;this.xid=xid;this.role=role;this.product=product;this.code=code;metadataVersion=mv;metadataDigest=md;metadataBytes=mb;metadataClassification=mc;}
        @Override public void suspend(){active=false;rejected=true;holder.setRollbackOnly();}
        @Override public void beforeCommit(boolean readOnly){if(rejected || !active || readOnly || thread!=Thread.currentThread())throw fail(Reason.UNAVAILABLE);}
        @Override public void afterCompletion(int status){active=false;}
        @Override public String toString(){return "Admission[opaque supplied fluid storage]";}
    }
    private static Failure fail(Reason r){return new Failure(r);}
    private ConnectionHolder physical(){
        if(!StackWalker.getInstance().walk(f->f.anyMatch(x->x.getClassName().equals("dz.sh.hidra.modules.custody.application.service.SuppliedFluidPropertyRevisionApplicationService"))))throw fail(Reason.UNAVAILABLE);
        if(!TransactionSynchronizationManager.isActualTransactionActive() || TransactionSynchronizationManager.isCurrentTransactionReadOnly()
            || !(TransactionSynchronizationManager.getResource(jdbc.getDataSource()) instanceof ConnectionHolder h) || h.isRollbackOnly())throw fail(Reason.UNAVAILABLE);return h;
    }
    private Ticket ticket(Admission a){if(!(a instanceof JdbcSuppliedFluidPropertyRevisionRepositoryAdapter.Ticket t) || t.owner!=this)throw fail(Reason.UNAVAILABLE);
        try{if(t.thread!=Thread.currentThread() || !t.active || t.rejected)throw fail(Reason.UNAVAILABLE);var h=physical();if(h!=t.holder || h.getConnection()!=t.connection)throw fail(Reason.UNAVAILABLE);return t;}
        catch(RuntimeException e){t.rejected=true;t.holder.setRollbackOnly();throw e;}}
    private boolean catalogSafe(){return Boolean.TRUE.equals(jdbc.queryForObject(CATALOG,Boolean.class)) && Boolean.TRUE.equals(jdbc.queryForObject(FLUID_CATALOG,Boolean.class));}
    @Override @Transactional(propagation=Propagation.MANDATORY)
    public Admission acquire(Command command){try{
        var h=physical();for(String relation:RELATIONS)jdbc.execute("LOCK TABLE "+relation+" IN SHARE ROW EXCLUSIVE MODE NOWAIT");
        if(!catalogSafe())throw fail(Reason.UNAVAILABLE);
        var principals=executor.publicationDatabaseIdentity();
        if(!Boolean.TRUE.equals(jdbc.queryForObject("""
            SELECT current_user=? AND session_user=? AND ?<>? AND NOT pg_has_role(?::name,?::name,'MEMBER')
            AND NOT has_schema_privilege(?::name,'public','CREATE') AND NOT has_schema_privilege(?::name,'pg_catalog','CREATE')
            AND NOT EXISTS(SELECT 1 FROM pg_class c WHERE c.oid IN ('public.hidra_custody_supplied_fluid_property_draft'::regclass,'public.hidra_custody_supplied_fluid_property_version'::regclass)
              AND (pg_has_role(?::name,c.relowner,'MEMBER') OR NOT has_table_privilege(?::name,c.oid,'SELECT') OR has_table_privilege(?::name,c.oid,'INSERT,UPDATE,DELETE,TRUNCATE,TRIGGER') OR has_any_column_privilege(?::name,c.oid,'INSERT,UPDATE,REFERENCES')))
            """,Boolean.class,principals.publisherUser(),principals.publisherUser(),principals.ordinaryUser(),principals.publisherUser(),principals.ordinaryUser(),principals.publisherUser(),principals.ordinaryUser(),principals.ordinaryUser(),principals.ordinaryUser(),principals.ordinaryUser(),principals.ordinaryUser(),principals.ordinaryUser())))throw fail(Reason.UNAVAILABLE);
        var rows=jdbc.queryForList("SELECT catalog_product_id,local_code FROM public.hidra_custody_product_engineering_draft WHERE id=? AND company_party_id=?",command.productDraftId(),command.companyPartyId());
        if(rows.size()!=1)throw fail(Reason.DENIED);var row=rows.getFirst();String product=(String)row.get("catalog_product_id"),code=(String)row.get("local_code");
        var metadata=jdbc.queryForList("SELECT version,classification,canonical_payload,sha256 FROM public.hidra_custody_product_engineering_draft_version WHERE draft_id=? ORDER BY version DESC LIMIT 1",command.productDraftId());
        if(metadata.size()!=1)throw fail(Reason.UNAVAILABLE);var m=metadata.getFirst();byte[] bytes=(byte[])m.get("canonical_payload");String digest=(String)m.get("sha256");
        var decoded=new ProductEngineeringDraftMetadataCodec().decode(bytes);if(!ProductEngineeringDraftMetadataCodec.digest(bytes).equals(digest) || !Objects.equals(decoded.classification()==null?null:decoded.classification().name(),m.get("classification")))throw fail(Reason.UNAVAILABLE);
        var t=jdbc.queryForObject("SELECT pg_backend_pid(),txid_current(),current_user",(r,n)->new Ticket(command,h,r.getInt(1),r.getLong(2),r.getString(3),product,code,((Number)m.get("version")).longValue(),digest,bytes,(String)m.get("classification")));
        if(t==null)throw fail(Reason.UNAVAILABLE);TransactionSynchronizationManager.registerSynchronization(t);return t;
    }catch(RuntimeException e){executor.rejectAdmission();throw e;}}
    @Override public String catalogProductId(Admission a){return ticket(a).product;}
    @Override public List<ProtectedRelationFence> finalFences(Admission a){ticket(a);return RELATIONS.stream().map(r->new ProtectedRelationFence(r,ProtectedRelationFence.Mode.SHARE_ROW_EXCLUSIVE)).toList();}
    private record Stored(long version,long metadataVersion,String classification,byte[] bytes,String digest){}
    private Stored stored(UUID id){var rows=jdbc.query("SELECT version,product_metadata_version,classification,payload_format,canonical_payload,sha256 FROM public.hidra_custody_supplied_fluid_property_version WHERE product_draft_id=? ORDER BY version DESC LIMIT 1",(r,n)->{
        byte[] bytes=r.getBytes(5);if(!SuppliedFluidPropertyRevisionCodec.FORMAT.equals(r.getString(4)) || !SuppliedFluidPropertyRevisionCodec.digest(bytes).equals(r.getString(6)))throw fail(Reason.UNAVAILABLE);codec.decode(bytes).validate(Classification.valueOf(r.getString(3)));return new Stored(r.getLong(1),r.getLong(2),r.getString(3),bytes,r.getString(6));},id);return rows.isEmpty()?null:rows.getFirst();}
    @Override @Transactional(propagation=Propagation.MANDATORY)
    public void eligible(Admission a){try{
        var t=ticket(a);if(t.eligible)throw fail(Reason.UNAVAILABLE);var c=t.command;
        if(!Boolean.TRUE.equals(jdbc.queryForObject("SELECT EXISTS(SELECT 1 FROM public.hidra_custody_catalog_entry c JOIN public.hidra_custody_engineering_product_policy p ON p.catalog_product_id=c.id WHERE c.id=? AND c.active AND p.active)",Boolean.class,t.product)))throw fail(Reason.DENIED);
        var last=stored(c.productDraftId());t.latest=last==null?0:last.version();t.latestBytes=last==null?null:last.bytes();t.latestDigest=last==null?null:last.digest();
        if(c.kind()==Kind.READ && last==null)throw fail(Reason.DENIED);
        if(c.kind()==Kind.WRITE){t.requestDigest=codec.requestDigest(c.productDraftId(),c.requestId(),c.expectedVersion(),c.expectedProductMetadataVersion(),c.facts());
            var prior=jdbc.queryForList("SELECT request_sha256,canonical_payload FROM public.hidra_custody_supplied_fluid_property_version WHERE product_draft_id=? AND request_id=?",c.productDraftId(),c.requestId());
            t.replayed=!prior.isEmpty();
            if(t.replayed){if(!t.requestDigest.equals(prior.getFirst().get("request_sha256")) || !Arrays.equals(codec.encode(c.facts()),(byte[])prior.getFirst().get("canonical_payload")))throw fail(Reason.CONFLICT);if(last==null)throw fail(Reason.UNAVAILABLE);}
            else {if(c.expectedVersion()!=t.latest || c.expectedProductMetadataVersion()!=t.metadataVersion)throw fail(Reason.CONFLICT);
                if(t.metadataClassification==null)throw fail(Reason.CONFLICT);c.facts().validate(Classification.valueOf(t.metadataClassification));}
        }
        boolean mutation=c.kind()==Kind.WRITE && !t.replayed;long version=mutation?t.latest+1:t.latest;
        var facts=mutation?c.facts():codec.decode(t.latestBytes);long metadata=mutation?t.metadataVersion:last.metadataVersion();String classification=mutation?t.metadataClassification:last.classification();
        var revision=new SuppliedFluidPropertyRevision(c.productDraftId(),c.companyPartyId(),t.code,t.product,version,metadata,Classification.valueOf(classification),facts);
        t.result=new Result(revision,t.replayed,metadata==t.metadataVersion && Objects.equals(classification,t.metadataClassification));t.eligible=true;
    }catch(RuntimeException e){executor.rejectAdmission();throw e;}}
    @Override public Facts facts(Admission a){var t=ticket(a);if(!t.eligible)throw fail(Reason.UNAVAILABLE);return t.result.draft().facts();}
    @Override public boolean mutation(Admission a){var t=ticket(a);if(!t.eligible)throw fail(Reason.UNAVAILABLE);return t.command.kind()==Kind.WRITE && !t.replayed;}
    @Override public Set<String> mutationSql(){return SQL;}
    @Override @Transactional(propagation=Propagation.MANDATORY)
    public Result apply(Admission a,Contribution c){try{var t=ticket(a);if(!t.eligible || t.applied || c==null || c.backendPid()!=t.pid || c.transactionId()!=t.xid || !c.request().companyPartyId().equals(t.command.companyPartyId()) || !c.request().catalogProductId().equals(t.product) || !c.request().action().name().equals(t.command.kind().name()))throw fail(Reason.UNAVAILABLE);
        if(mutation(t)){if(t.latest==0 && jdbc.update(ROOT_INSERT,t.command.productDraftId())!=1)throw fail(Reason.CONFLICT);
            var d=t.result.draft();byte[] bytes=codec.encode(d.facts());String digest=SuppliedFluidPropertyRevisionCodec.digest(bytes);
            if(jdbc.update(VERSION_INSERT,t.command.productDraftId(),d.version(),t.command.requestId(),t.requestDigest,t.command.expectedVersion(),d.productMetadataVersion(),d.classification().name(),SuppliedFluidPropertyRevisionCodec.FORMAT,bytes,digest,t.latestDigest,c.bindingId(),c.sessionId(),c.userId(),c.partyGeneration(),Timestamp.from(c.sampledAt()),Timestamp.from(c.validUntil()),t.pid,t.xid,t.command.productDraftId(),t.latest)!=1)throw fail(Reason.CONFLICT);
            t.latest=d.version();t.latestBytes=bytes;t.latestDigest=digest;
        }t.applied=true;return t.result;
    }catch(RuntimeException e){executor.rejectAdmission();throw e;}}
    @Override public void flush(){physical();}
    @Override @Transactional(propagation=Propagation.MANDATORY)
    public boolean finalGuard(Admission a,Contribution c,List<ProtectedRelationFence> fences){var t=ticket(a);
        if(!t.applied || c==null || fences==null || fences.size()!=34 || fences.stream().map(ProtectedRelationFence::relation).distinct().count()!=34 || !fences.containsAll(finalFences(t)))throw fail(Reason.UNAVAILABLE);
        String guard="""
            WITH sampled_clock AS MATERIALIZED (SELECT clock_timestamp() AS t)
            SELECT (SELECT t FROM sampled_clock)>=?::timestamptz AND (SELECT t FROM sampled_clock)<?::timestamptz
              AND pg_backend_pid()=? AND txid_current()=? AND current_user=? AND session_user=?
              AND EXISTS(SELECT 1 FROM public.hidra_party_party p JOIN public.hidra_party_eligibility_generation g ON g.party_id=p.id WHERE p.id=? AND p.status='ACTIVE' AND g.generation=? AND g.present)
              AND EXISTS(SELECT 1 FROM public.hidra_custody_catalog_entry c JOIN public.hidra_custody_engineering_product_policy p ON p.catalog_product_id=c.id WHERE c.id=? AND c.active AND p.active)
              AND EXISTS(SELECT 1 FROM public.hidra_custody_product_engineering_draft d WHERE d.id=? AND d.company_party_id=? AND d.local_code=? AND d.catalog_product_id=?)
              AND EXISTS(SELECT 1 FROM public.hidra_custody_product_engineering_draft_version v WHERE v.draft_id=? AND v.version=? AND v.sha256=? AND v.canonical_payload=? AND v.sha256=encode(sha256(v.canonical_payload),'hex') AND NOT EXISTS(SELECT 1 FROM public.hidra_custody_product_engineering_draft_version n WHERE n.draft_id=v.draft_id AND n.version>v.version))
              AND EXISTS(SELECT 1 FROM public.hidra_custody_supplied_fluid_property_draft d JOIN public.hidra_custody_supplied_fluid_property_version v ON v.product_draft_id=d.product_draft_id WHERE d.product_draft_id=? AND v.version=? AND v.sha256=? AND v.canonical_payload=? AND v.payload_format='HIDRA_SUPPLIED_FLUID_V1' AND v.sha256=encode(sha256(v.canonical_payload),'hex') AND v.product_metadata_version=? AND v.classification=? AND NOT EXISTS(SELECT 1 FROM public.hidra_custody_supplied_fluid_property_version n WHERE n.product_draft_id=v.product_draft_id AND n.version>v.version))
            """;
        var args=new ArrayList<Object>(Arrays.asList(Timestamp.from(c.sampledAt()),Timestamp.from(c.validUntil()),t.pid,t.xid,t.role,t.role,t.command.companyPartyId(),c.partyGeneration(),t.product,t.command.productDraftId(),t.command.companyPartyId(),t.code,t.product,t.command.productDraftId(),t.metadataVersion,t.metadataDigest,t.metadataBytes,t.command.productDraftId(),t.latest,t.latestDigest,t.latestBytes,t.result.draft().productMetadataVersion(),t.result.draft().classification().name()));
        if(mutation(t)){guard+=" AND EXISTS(SELECT 1 FROM public.hidra_custody_supplied_fluid_property_version v WHERE v.product_draft_id=? AND v.version=? AND v.actor_binding_id=?::uuid AND v.actor_session_id=?::uuid AND v.actor_user_id=? AND v.party_generation=? AND v.sampled_at=? AND v.valid_until=? AND v.backend_pid=? AND v.transaction_id=?)";args.addAll(Arrays.asList(t.command.productDraftId(),t.latest,c.bindingId(),c.sessionId(),c.userId(),c.partyGeneration(),Timestamp.from(c.sampledAt()),Timestamp.from(c.validUntil()),t.pid,t.xid));}
        for(var unit:c.units()) {guard+=" AND EXISTS(SELECT 1 FROM public.hidra_telemetry_unit u JOIN public.hidra_telemetry_fluid_unit_policy p ON p.unit_id=u.id WHERE u.id=? AND p.usage_role=? AND u.active AND p.active AND u.dimension=p.expected_dimension AND u.dimension=? AND u.code=? AND u.symbol=? AND u.updated_at=?)";args.addAll(Arrays.asList(unit.unitId(),unit.role(),unit.dimension(),unit.code(),unit.symbol(),Timestamp.from(unit.updatedAt())));}
        guard+=" AND ("+CATALOG+") AND ("+FLUID_CATALOG+") AND NOT EXISTS(SELECT 1 FROM (VALUES "+String.join(",",Collections.nCopies(34,"(?::regclass,?::text)"))+") required(relation,mode) WHERE NOT EXISTS(SELECT 1 FROM pg_locks l WHERE l.pid=pg_backend_pid() AND l.granted AND l.locktype='relation' AND l.relation=required.relation AND l.mode=required.mode))";
        for(var f:fences){args.add(f.relation());args.add(f.mode().catalog());}return Boolean.TRUE.equals(jdbc.queryForObject(guard,Boolean.class,args.toArray()));
    }
}
