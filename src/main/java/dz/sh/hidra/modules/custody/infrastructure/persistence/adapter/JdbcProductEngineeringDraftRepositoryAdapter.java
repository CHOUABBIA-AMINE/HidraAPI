/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : JdbcProductEngineeringDraftRepositoryAdapter
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-27
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : custody
 * @Package     : dz.sh.hidra.modules.custody.infrastructure.persistence.adapter
 *
 * @Description : Provides company-owned catalog-linked product metadata drafts.
 *
 */
package dz.sh.hidra.modules.custody.infrastructure.persistence.adapter;

import dz.sh.hidra.modules.custody.application.port.in.ProductEngineeringDraftUseCase.*;
import dz.sh.hidra.modules.custody.application.port.out.ProductEngineeringDraftRepositoryPort;
import dz.sh.hidra.modules.custody.application.service.ProtectedProductDraftAdmissionCoordinator.Contribution;
import dz.sh.hidra.modules.custody.domain.model.ProductEngineeringDraft;
import dz.sh.hidra.modules.custody.domain.model.ProductEngineeringDraft.Metadata;
import dz.sh.hidra.platform.persistence.*;
import java.sql.*;
import java.util.*;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.ConnectionHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.*;
import org.springframework.transaction.support.*;

@Repository
public class JdbcProductEngineeringDraftRepositoryAdapter implements ProductEngineeringDraftRepositoryPort {
    private static final String DRAFT_INSERT="INSERT INTO public.hidra_custody_product_engineering_draft (id,company_party_id,local_code,catalog_product_id,creation_request_id,request_sha256) VALUES (?,?,?,?,?,?)";
    private static final String VERSION_INSERT="INSERT INTO public.hidra_custody_product_engineering_draft_version (draft_id,version,payload_format,canonical_payload,sha256,predecessor_sha256,name,description,evidence_reference,classification,actor_binding_id,actor_session_id,actor_user_id,party_generation,sampled_at,valid_until,backend_pid,transaction_id) SELECT ?,?,?,?,?,?,?,?,?,?,?::uuid,?::uuid,?,?,?,?,?,? WHERE COALESCE((SELECT max(version) FROM public.hidra_custody_product_engineering_draft_version WHERE draft_id=?),0)=?";
    private static final Set<String> SQL=Set.of(DRAFT_INSERT,VERSION_INSERT);
    private static final String DRAFT="public.hidra_custody_product_engineering_draft";
    private static final String VERSIONS="public.hidra_custody_product_engineering_draft_version";
    private static final String POLICY="public.hidra_custody_engineering_product_policy";
    private static final String CATALOG_TABLE="public.hidra_custody_catalog_entry";
    private static final List<String> RELATIONS=List.of(CATALOG_TABLE,POLICY,DRAFT,VERSIONS);
    // Bodies below are hashed from this package's actual migration, never guessed catalog values.
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
    private final JdbcTemplate jdbc;private final ProtectedTransactionExecutor executor;
    private final ProductEngineeringDraftMetadataCodec codec=new ProductEngineeringDraftMetadataCodec();
    public JdbcProductEngineeringDraftRepositoryAdapter(JdbcTemplate jdbc,ProtectedTransactionExecutor executor) {
        this.jdbc=Objects.requireNonNull(jdbc);this.executor=Objects.requireNonNull(executor);
    }
    private final class Ticket implements Admission,TransactionSynchronization {
        final JdbcProductEngineeringDraftRepositoryAdapter owner=JdbcProductEngineeringDraftRepositoryAdapter.this;
        final Command command;final Thread thread=Thread.currentThread();final ConnectionHolder holder;final Connection connection;
        final int pid;final long xid;final String role;final String product;
        volatile boolean active=true,rejected;boolean eligible,applied,replayed;UUID draftId,requestId;String code,requestDigest;
        long latest;byte[] latestBytes;String latestDigest;Result result;
        Ticket(Command command,ConnectionHolder holder,int pid,long xid,String role,String product) {
            this.command=command;this.holder=holder;this.connection=holder.getConnection();this.pid=pid;this.xid=xid;this.role=role;this.product=product;
        }
        @Override public void suspend(){active=false;rejected=true;holder.setRollbackOnly();}
        @Override public void beforeCommit(boolean readOnly){if(rejected || !active || readOnly || thread!=Thread.currentThread())throw fail(Reason.UNAVAILABLE);}
        @Override public void afterCompletion(int status){active=false;}
        @Override public String toString(){return "Admission[opaque product storage]";}
    }
    private static Failure fail(Reason reason){return new Failure(reason);}
    private ConnectionHolder physical() {
        if(!StackWalker.getInstance().walk(f->f.anyMatch(x->x.getClassName().equals(
                "dz.sh.hidra.modules.custody.application.service.ProductEngineeringDraftApplicationService"))))throw fail(Reason.UNAVAILABLE);
        if(!TransactionSynchronizationManager.isActualTransactionActive() || TransactionSynchronizationManager.isCurrentTransactionReadOnly()
                || !(TransactionSynchronizationManager.getResource(jdbc.getDataSource()) instanceof ConnectionHolder holder)
                || holder.isRollbackOnly())throw fail(Reason.UNAVAILABLE);
        return holder;
    }
    private Ticket ticket(Admission admission) {
        if(!(admission instanceof JdbcProductEngineeringDraftRepositoryAdapter.Ticket t) || t.owner!=this)throw fail(Reason.UNAVAILABLE);
        try {
            if(t.thread!=Thread.currentThread() || !t.active || t.rejected)throw fail(Reason.UNAVAILABLE);
            var holder=physical();
            if(t.holder!=holder || t.connection!=holder.getConnection())throw fail(Reason.UNAVAILABLE);
            return t;
        }catch(RuntimeException failure){t.rejected=true;t.holder.setRollbackOnly();throw failure;}
    }
    @Override @Transactional(propagation=Propagation.MANDATORY)
    public Admission acquire(Command command) {
        try {
            var holder=physical();
            for(String relation:RELATIONS)jdbc.execute("LOCK TABLE "+relation+" IN SHARE ROW EXCLUSIVE MODE NOWAIT");
            var principals=executor.publicationDatabaseIdentity();
            if(!Boolean.TRUE.equals(jdbc.queryForObject(CATALOG,Boolean.class)))throw fail(Reason.UNAVAILABLE);
            if(!Boolean.TRUE.equals(jdbc.queryForObject("""
                SELECT current_user=? AND session_user=? AND ?<>?
                  AND NOT pg_has_role(?::name,?::name,'MEMBER')
                  AND NOT has_schema_privilege(?::name,'public','CREATE')
                  AND NOT has_schema_privilege(?::name,'pg_catalog','CREATE')
                  AND NOT has_table_privilege(?::name,'public.hidra_custody_engineering_product_policy','INSERT,UPDATE,DELETE,TRUNCATE,TRIGGER')
                  AND NOT has_any_column_privilege(?::name,'public.hidra_custody_engineering_product_policy','INSERT,UPDATE,REFERENCES')
                  AND NOT EXISTS(SELECT 1 FROM pg_class c WHERE c.oid IN
                    ('public.hidra_custody_product_engineering_draft'::regclass,'public.hidra_custody_product_engineering_draft_version'::regclass)
                    AND (pg_has_role(?::name,c.relowner,'MEMBER')
                      OR NOT has_table_privilege(?::name,c.oid,'SELECT')
                      OR has_table_privilege(?::name,c.oid,'INSERT,UPDATE,DELETE,TRUNCATE,TRIGGER')
                      OR has_any_column_privilege(?::name,c.oid,'INSERT,UPDATE,REFERENCES')))
                """,Boolean.class,principals.publisherUser(),principals.publisherUser(),principals.ordinaryUser(),principals.publisherUser(),
                    principals.ordinaryUser(),principals.publisherUser(),principals.ordinaryUser(),principals.ordinaryUser(),
                    principals.ordinaryUser(),principals.ordinaryUser(),principals.ordinaryUser(),principals.ordinaryUser(),principals.ordinaryUser(),principals.ordinaryUser())))throw fail(Reason.UNAVAILABLE);
            String product=command.catalogProductId();
            if(command.kind()!=Kind.CREATE) {
                var references=jdbc.queryForList("SELECT catalog_product_id FROM public.hidra_custody_product_engineering_draft WHERE id=? AND company_party_id=?",String.class,command.draftId(),command.companyPartyId());
                if(references.size()!=1)throw fail(Reason.DENIED);product=references.getFirst();
            }
            final String selected=product;
            var t=jdbc.queryForObject("SELECT pg_backend_pid(),txid_current(),current_user",
                    (row,n)->new Ticket(command,holder,row.getInt(1),row.getLong(2),row.getString(3),selected));
            if(t==null)throw fail(Reason.UNAVAILABLE);TransactionSynchronizationManager.registerSynchronization(t);return t;
        }catch(RuntimeException failure){executor.rejectAdmission();throw failure;}
    }
    @Override public String catalogProductId(Admission admission){return ticket(admission).product;}
    @Override public List<ProtectedRelationFence> finalFences(Admission admission) {
        ticket(admission);return RELATIONS.stream().map(r->new ProtectedRelationFence(r,ProtectedRelationFence.Mode.SHARE_ROW_EXCLUSIVE)).toList();
    }
    private record Stored(long version,byte[] bytes,String digest) {}
    private Stored stored(UUID draftId,boolean first) {
        var found=jdbc.query("SELECT version,payload_format,canonical_payload,sha256,name,description,evidence_reference,classification FROM public.hidra_custody_product_engineering_draft_version WHERE draft_id=? ORDER BY version "+(first?"ASC":"DESC")+" LIMIT 1",(r,n)->{
            byte[] bytes=r.getBytes(3);var metadata=codec.decode(bytes);
            if(!ProductEngineeringDraftMetadataCodec.FORMAT.equals(r.getString(2))
                    || !ProductEngineeringDraftMetadataCodec.digest(bytes).equals(r.getString(4))
                    || !Objects.equals(metadata.name(),r.getString(5)) || !Objects.equals(metadata.description(),r.getString(6))
                    || !Objects.equals(metadata.evidenceReference(),r.getString(7))
                    || !Objects.equals(metadata.classification()==null?null:metadata.classification().name(),r.getString(8)))throw fail(Reason.UNAVAILABLE);
            return new Stored(r.getLong(1),bytes,r.getString(4));
        },draftId);
        return found.isEmpty()?null:found.getFirst();
    }
    @Override @Transactional(propagation=Propagation.MANDATORY)
    public void eligible(Admission admission) {
        try {
            var t=ticket(admission);if(t.eligible)throw fail(Reason.UNAVAILABLE);var command=t.command;
            if(!Boolean.TRUE.equals(jdbc.queryForObject("SELECT EXISTS(SELECT 1 FROM public.hidra_custody_catalog_entry c JOIN public.hidra_custody_engineering_product_policy p ON p.catalog_product_id=c.id WHERE c.id=? AND c.active AND p.active)",Boolean.class,t.product)))throw fail(Reason.DENIED);
            if(command.kind()==Kind.CREATE) {
                t.requestDigest=codec.requestDigest(command.companyPartyId(),command.localCode(),t.product,command.creationRequestId(),command.metadata());
                var rows=jdbc.queryForList("SELECT id,local_code,catalog_product_id,creation_request_id,request_sha256 FROM public.hidra_custody_product_engineering_draft WHERE company_party_id=? AND creation_request_id=? FOR UPDATE",command.companyPartyId(),command.creationRequestId());
                t.replayed=!rows.isEmpty();t.draftId=t.replayed?(UUID)rows.getFirst().get("id"):UUID.randomUUID();
                t.code=command.localCode();t.requestId=command.creationRequestId();
                if(t.replayed) {
                    var row=rows.getFirst();
                    if(!Objects.equals(t.requestDigest,row.get("request_sha256")) || !t.code.equals(row.get("local_code"))
                            || !t.product.equals(row.get("catalog_product_id")))throw fail(Reason.CONFLICT);
                    var initial=stored(t.draftId,true);
                    if(initial==null || initial.version()!=1 || !Arrays.equals(initial.bytes(),codec.encode(command.metadata())))throw fail(Reason.UNAVAILABLE);
                } else if(!jdbc.queryForList("SELECT id FROM public.hidra_custody_product_engineering_draft WHERE company_party_id=? AND local_code=?",UUID.class,command.companyPartyId(),t.code).isEmpty())throw fail(Reason.CONFLICT);
            } else {
                var rows=jdbc.queryForList("SELECT local_code,creation_request_id,request_sha256 FROM public.hidra_custody_product_engineering_draft WHERE id=? AND company_party_id=? AND catalog_product_id=? FOR UPDATE",command.draftId(),command.companyPartyId(),t.product);
                if(rows.size()!=1)throw fail(Reason.DENIED);
                t.draftId=command.draftId();t.code=(String)rows.getFirst().get("local_code");t.requestId=(UUID)rows.getFirst().get("creation_request_id");t.requestDigest=(String)rows.getFirst().get("request_sha256");
            }
            var latest=stored(t.draftId,false);
            if(latest==null && (command.kind()!=Kind.CREATE || t.replayed))throw fail(Reason.UNAVAILABLE);
            t.latest=latest==null?0:latest.version();t.latestBytes=latest==null?null:latest.bytes();t.latestDigest=latest==null?null:latest.digest();
            if(command.kind()==Kind.UPDATE && (command.expectedVersion()!=t.latest || t.latest==Long.MAX_VALUE))throw fail(Reason.CONFLICT);
            long version=command.kind()==Kind.UPDATE?t.latest+1:latest==null?1:t.latest;
            Metadata metadata=command.kind()==Kind.UPDATE || (command.kind()==Kind.CREATE && !t.replayed)?command.metadata():codec.decode(t.latestBytes);
            var draft=command.kind()==Kind.UPDATE
                ? new ProductEngineeringDraft(t.draftId,command.companyPartyId(),t.code,t.product,t.requestId,t.latest,codec.decode(t.latestBytes))
                    .revise(command.expectedVersion(),command.metadata())
                : new ProductEngineeringDraft(t.draftId,command.companyPartyId(),t.code,t.product,t.requestId,version,metadata);
            t.result=new Result(draft,t.replayed);
            t.eligible=true;
        }catch(RuntimeException failure){executor.rejectAdmission();throw failure;}
    }
    @Override public boolean mutation(Admission admission) {var t=ticket(admission);if(!t.eligible)throw fail(Reason.UNAVAILABLE);return t.command.kind()==Kind.UPDATE || (t.command.kind()==Kind.CREATE && !t.replayed);}
    @Override public Set<String> mutationSql(){return SQL;}
    @Override @Transactional(propagation=Propagation.MANDATORY)
    public Result apply(Admission admission,Contribution contribution) {
        try {
            var t=ticket(admission);var c=contribution;
            if(!t.eligible || t.applied || c==null || c.backendPid()!=t.pid || c.transactionId()!=t.xid
                    || !c.request().companyPartyId().equals(t.command.companyPartyId()) || !c.request().catalogProductId().equals(t.product)
                    || !c.request().action().name().equals(t.command.kind().name()))throw fail(Reason.UNAVAILABLE);
            if(t.command.kind()==Kind.CREATE && !t.replayed)
                if(jdbc.update(DRAFT_INSERT,t.draftId,t.command.companyPartyId(),t.code,t.product,t.requestId,t.requestDigest)!=1)throw fail(Reason.CONFLICT);
            if(mutation(t)) {
                var metadata=t.result.draft().metadata();byte[] bytes=codec.encode(metadata);String digest=ProductEngineeringDraftMetadataCodec.digest(bytes);
                if(jdbc.update(VERSION_INSERT,t.draftId,t.result.draft().version(),ProductEngineeringDraftMetadataCodec.FORMAT,bytes,digest,t.latestDigest,
                        metadata.name(),metadata.description(),metadata.evidenceReference(),metadata.classification()==null?null:metadata.classification().name(),
                        c.bindingId(),c.sessionId(),c.userId(),c.partyGeneration(),Timestamp.from(c.sampledAt()),Timestamp.from(c.validUntil()),t.pid,t.xid,t.draftId,t.latest)!=1)throw fail(Reason.CONFLICT);
                t.latest=t.result.draft().version();t.latestBytes=bytes;t.latestDigest=digest;
            }
            t.applied=true;return t.result;
        }catch(RuntimeException failure){executor.rejectAdmission();throw failure;}
    }
    @Override public void flush(){physical();} // JDBC-only writes; no persistence model or deferred ORM write.
    @Override @Transactional(propagation=Propagation.MANDATORY)
    public boolean finalGuard(Admission admission,Contribution c,List<ProtectedRelationFence> fences) {
        var t=ticket(admission);
        if(!t.applied || c==null || fences==null || fences.size()!=30
                || fences.stream().map(ProtectedRelationFence::relation).distinct().count()!=30
                || !fences.containsAll(finalFences(t)))throw fail(Reason.UNAVAILABLE);
        String required=String.join(",",Collections.nCopies(fences.size(),"(?::regclass,?::text)"));
        String guard="""
            WITH sampled_clock AS MATERIALIZED (SELECT clock_timestamp() AS t)
            SELECT (SELECT t FROM sampled_clock)>=?::timestamptz AND (SELECT t FROM sampled_clock)<?::timestamptz
              AND pg_backend_pid()=? AND txid_current()=? AND current_user=? AND session_user=?
              AND EXISTS(SELECT 1 FROM public.hidra_party_party p JOIN public.hidra_party_eligibility_generation g ON g.party_id=p.id
                WHERE p.id=? AND p.status='ACTIVE' AND g.generation=? AND g.present)
              AND EXISTS(SELECT 1 FROM public.hidra_custody_catalog_entry c JOIN public.hidra_custody_engineering_product_policy p ON p.catalog_product_id=c.id
                WHERE c.id=? AND c.active AND p.active)
              AND EXISTS(SELECT 1 FROM public.hidra_custody_product_engineering_draft d WHERE d.id=? AND d.company_party_id=? AND d.local_code=?
                AND d.catalog_product_id=? AND d.creation_request_id=? AND d.request_sha256=?)
              AND EXISTS(SELECT 1 FROM public.hidra_custody_product_engineering_draft_version v WHERE v.draft_id=? AND v.version=?
                AND v.payload_format='HIDRA_PRODUCT_METADATA_V1' AND v.sha256=? AND v.canonical_payload=?
                AND v.sha256=encode(sha256(v.canonical_payload),'hex')
                AND NOT EXISTS(SELECT 1 FROM public.hidra_custody_product_engineering_draft_version n WHERE n.draft_id=v.draft_id AND n.version>v.version))
            """;
        var args=new ArrayList<Object>(Arrays.asList(Timestamp.from(c.sampledAt()),Timestamp.from(c.validUntil()),t.pid,t.xid,t.role,t.role,
                t.command.companyPartyId(),c.partyGeneration(),t.product,t.draftId,t.command.companyPartyId(),t.code,t.product,t.requestId,t.requestDigest,
                t.draftId,t.latest,t.latestDigest,t.latestBytes));
        if(mutation(t)) {
            guard+=" AND EXISTS(SELECT 1 FROM public.hidra_custody_product_engineering_draft_version v WHERE v.draft_id=? AND v.version=? AND v.actor_binding_id=?::uuid AND v.actor_session_id=?::uuid AND v.actor_user_id=? AND v.party_generation=? AND v.sampled_at=? AND v.valid_until=? AND v.backend_pid=? AND v.transaction_id=?)";
            args.addAll(Arrays.asList(t.draftId,t.latest,c.bindingId(),c.sessionId(),c.userId(),c.partyGeneration(),Timestamp.from(c.sampledAt()),Timestamp.from(c.validUntil()),t.pid,t.xid));
        }
        guard+=" AND ("+CATALOG+") AND NOT EXISTS(SELECT 1 FROM (VALUES "+required+") required(relation,mode) WHERE NOT EXISTS(SELECT 1 FROM pg_locks l WHERE l.pid=pg_backend_pid() AND l.granted AND l.locktype='relation' AND l.relation=required.relation AND l.mode=required.mode))";
        for(var fence:fences){args.add(fence.relation());args.add(fence.mode().catalog());}
        return Boolean.TRUE.equals(jdbc.queryForObject(guard,Boolean.class,args.toArray()));
    }
}
