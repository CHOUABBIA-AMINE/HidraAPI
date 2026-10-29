/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : JdbcProtectedInstalledUnitRepositoryAdapter
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-29
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.infrastructure.persistence.adapter
 *
 * @Description : Implements the protected installed-unit lifecycle within its owning boundary.
 *
 */
package dz.sh.hidra.modules.topology.infrastructure.persistence.adapter;

import dz.sh.hidra.modules.topology.application.port.in.InstalledUnitLifecycleUseCase.*;
import dz.sh.hidra.modules.topology.application.port.out.ProtectedInstalledUnitRepositoryPort;
import dz.sh.hidra.modules.topology.domain.model.*;
import dz.sh.hidra.platform.persistence.*;
import jakarta.persistence.EntityManager;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.sql.*;
import java.time.Instant;
import dz.sh.hidra.modules.topology.application.model.InstalledUnitLifecycleFailure;
import dz.sh.hidra.modules.topology.application.model.InstalledUnitLifecycleFailure.Kind;
import java.util.*;
import javax.sql.DataSource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.ConnectionHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.support.*;

/** Only the compiled Topology coordinator may use this issuer; scalars never admit DML. */
@Repository
public class JdbcProtectedInstalledUnitRepositoryAdapter implements ProtectedInstalledUnitRepositoryPort {
    private static final String UNIT_INSERT="INSERT INTO public.hidra_topology_installed_unit (id,company_party_id,equipment_kind_id,manufacturer_party_value,manufacturer_party_verification,manufacturer_party_source,manufacturer_model_value,manufacturer_model_verification,manufacturer_model_source,serial_value,serial_verification,serial_source,maintainable_asset_id) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?)";
    private static final String INSTALL_INSERT="INSERT INTO public.hidra_topology_installed_unit_installation (installation_id,unit_id,position_id,company_party_id,effective_from,effective_until,recorded_at,evidence_reference,revision) VALUES (?,?,?,?,?,?,?,?,?)";
    private static final String CLOSE="UPDATE public.hidra_topology_installed_unit_installation SET effective_until=?,revision=revision+1 WHERE company_party_id=? AND installation_id=? AND revision=? AND effective_until IS NULL";
    private static final String RECEIPT_INSERT="INSERT INTO public.hidra_topology_installed_unit_operation_request (request_id,operation,company_party_id,unit_id,installation_id,replacement_installation_id,expected_revision,canonical_command,command_sha256,result_payload,result_sha256,actor_user_id,binding_id,session_id,sampled_at,valid_until,backend_pid,transaction_id,database_role) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
    private static final String REPLACEMENT_RECEIPT_INSERT="INSERT INTO public.hidra_topology_installed_unit_operation_request (request_id,operation,company_party_id,unit_id,installation_id,replacement_installation_id,expected_revision,canonical_command,command_sha256,result_payload,result_sha256,actor_user_id,binding_id,session_id,sampled_at,valid_until,backend_pid,transaction_id,database_role,replacement_unit_id) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
    private static final Set<String> MUTATIONS=Set.of(UNIT_INSERT,INSTALL_INSERT,CLOSE,RECEIPT_INSERT,REPLACEMENT_RECEIPT_INSERT);
    private static final String CATALOG="""
pg_catalog.current_setting('transaction_isolation')='read committed' AND pg_catalog.current_setting('transaction_read_only')='off'
 AND pg_catalog.current_setting('session_replication_role')='origin'
 AND NOT pg_catalog.has_schema_privilege(current_user,'public','CREATE')
 AND NOT pg_catalog.has_schema_privilege(current_user,'pg_catalog','CREATE')
 AND NOT EXISTS(SELECT 1 FROM pg_catalog.pg_roles r WHERE (r.rolname=current_user OR pg_catalog.pg_has_role(current_user,r.oid,'MEMBER'))
   AND (r.rolsuper OR r.rolbypassrls OR r.rolcreaterole OR r.rolcreatedb OR r.rolreplication))
 AND (SELECT count(*)=4 AND bool_and(relkind='r' AND relpersistence='p' AND NOT relrowsecurity AND NOT relforcerowsecurity AND NOT relispartition
   AND NOT pg_catalog.pg_has_role(current_user,relowner,'MEMBER') AND pg_catalog.has_table_privilege(current_user,oid,'SELECT,INSERT')
   AND pg_catalog.has_table_privilege(current_user,oid,'SELECT') AND pg_catalog.has_table_privilege(current_user,oid,'INSERT')
   AND pg_catalog.has_table_privilege(current_user,oid,'UPDATE')
   AND NOT pg_catalog.has_table_privilege(current_user,oid,'DELETE,TRUNCATE,TRIGGER,REFERENCES')) FROM pg_catalog.pg_class WHERE oid IN (pg_catalog.to_regclass('public.hidra_topology_installed_unit'),pg_catalog.to_regclass('public.hidra_topology_installed_unit_installation'),pg_catalog.to_regclass('public.hidra_topology_installed_unit_installation_event'),pg_catalog.to_regclass('public.hidra_topology_installed_unit_operation_request')))
 AND NOT EXISTS(SELECT 1 FROM pg_catalog.pg_inherits WHERE inhrelid IN (pg_catalog.to_regclass('public.hidra_topology_installed_unit'),pg_catalog.to_regclass('public.hidra_topology_installed_unit_installation'),pg_catalog.to_regclass('public.hidra_topology_installed_unit_installation_event'),pg_catalog.to_regclass('public.hidra_topology_installed_unit_operation_request')) OR inhparent IN (pg_catalog.to_regclass('public.hidra_topology_installed_unit'),pg_catalog.to_regclass('public.hidra_topology_installed_unit_installation'),pg_catalog.to_regclass('public.hidra_topology_installed_unit_installation_event'),pg_catalog.to_regclass('public.hidra_topology_installed_unit_operation_request')))
 AND EXISTS(SELECT 1 FROM pg_catalog.pg_class c WHERE c.oid=pg_catalog.to_regclass('public.hidra_topology_equipment_type')
   AND c.relkind='r' AND c.relpersistence='p' AND NOT c.relrowsecurity AND NOT c.relforcerowsecurity AND NOT c.relispartition
   AND NOT pg_catalog.pg_has_role(current_user,c.relowner,'MEMBER') AND pg_catalog.has_table_privilege(current_user,c.oid,'SELECT')
   AND NOT pg_catalog.has_table_privilege(current_user,c.oid,'INSERT,UPDATE,DELETE,TRUNCATE,REFERENCES,TRIGGER')
   AND NOT pg_catalog.has_any_column_privilege(current_user,c.oid,'INSERT,UPDATE,REFERENCES'))
 AND NOT EXISTS(SELECT 1 FROM pg_catalog.pg_inherits WHERE inhrelid=pg_catalog.to_regclass('public.hidra_topology_equipment_type') OR inhparent=pg_catalog.to_regclass('public.hidra_topology_equipment_type'))
 AND EXISTS(SELECT 1 FROM pg_catalog.pg_constraint WHERE conrelid=pg_catalog.to_regclass('public.hidra_topology_installed_unit')
   AND conname='fk_installed_unit_equipment_type' AND contype='f' AND convalidated AND NOT condeferrable AND NOT condeferred
   AND confrelid=pg_catalog.to_regclass('public.hidra_topology_equipment_type') AND conkey=ARRAY[3]::smallint[] AND confkey=ARRAY[1]::smallint[]
   AND confupdtype='r' AND confdeltype='r' AND confmatchtype='s'
   AND (SELECT count(*)=4 AND count(DISTINCT tgfoid)=4 AND bool_and(tgenabled='O' AND tgisinternal
     AND tgfoid IN (SELECT p.oid FROM pg_catalog.pg_proc p JOIN pg_catalog.pg_namespace n ON n.oid=p.pronamespace
       WHERE n.nspname='pg_catalog' AND p.pronargs=0 AND p.prorettype='trigger'::regtype
       AND p.proname IN ('RI_FKey_check_ins','RI_FKey_check_upd','RI_FKey_restrict_del','RI_FKey_restrict_upd')))
     FROM pg_catalog.pg_trigger WHERE tgconstraint=pg_constraint.oid))
 AND (SELECT count(*)=4 AND bool_and(contype='p' AND convalidated AND NOT condeferrable AND
   ((conrelid=pg_catalog.to_regclass('public.hidra_topology_installed_unit_installation_event') AND conkey=ARRAY[1,2]::smallint[])
    OR (conrelid<>pg_catalog.to_regclass('public.hidra_topology_installed_unit_installation_event') AND conkey=ARRAY[1]::smallint[])))
   FROM pg_catalog.pg_constraint WHERE conrelid IN (pg_catalog.to_regclass('public.hidra_topology_installed_unit'),pg_catalog.to_regclass('public.hidra_topology_installed_unit_installation'),pg_catalog.to_regclass('public.hidra_topology_installed_unit_installation_event'),pg_catalog.to_regclass('public.hidra_topology_installed_unit_operation_request')) AND contype='p')
 AND (SELECT count(*)=2 AND bool_and(convalidated AND NOT condeferrable
   AND ((conname='ex_installed_unit_effective_unit' AND pg_catalog.pg_get_constraintdef(oid)='EXCLUDE USING gist (unit_id WITH =, tstzrange(effective_from, effective_until, ''[)''::text) WITH &&)')
    OR (conname='ex_installed_unit_effective_position' AND pg_catalog.pg_get_constraintdef(oid)='EXCLUDE USING gist (position_id WITH =, tstzrange(effective_from, effective_until, ''[)''::text) WITH &&)')))
   FROM pg_catalog.pg_constraint WHERE conrelid=pg_catalog.to_regclass('public.hidra_topology_installed_unit_installation') AND contype='x')
 AND (SELECT count(*)=9 AND bool_and(tgenabled='O' AND tgqual IS NULL AND tgnargs=0 AND tgparentid=0 AND tgconstraint=0 AND ((tgrelid=pg_catalog.to_regclass('public.hidra_topology_installed_unit') AND tgname='topology_installed_unit_immutable' AND tgtype=27 AND tgfoid=pg_catalog.to_regprocedure('public.hidra_topology_installed_unit_immutable()')) OR (tgrelid=pg_catalog.to_regclass('public.hidra_topology_installed_unit') AND tgname='topology_installed_unit_no_truncate' AND tgtype=34 AND tgfoid=pg_catalog.to_regprocedure('public.hidra_topology_installed_unit_immutable()')) OR (tgrelid=pg_catalog.to_regclass('public.hidra_topology_installed_unit_installation') AND tgname='topology_installed_installation_guard' AND tgtype=31 AND tgfoid=pg_catalog.to_regprocedure('public.hidra_topology_installed_installation_guard()')) OR (tgrelid=pg_catalog.to_regclass('public.hidra_topology_installed_unit_installation') AND tgname='topology_installed_installation_no_truncate' AND tgtype=34 AND tgfoid=pg_catalog.to_regprocedure('public.hidra_topology_installed_unit_immutable()')) OR (tgrelid=pg_catalog.to_regclass('public.hidra_topology_installed_unit_installation') AND tgname='topology_installed_installation_event_append' AND tgtype=21 AND tgfoid=pg_catalog.to_regprocedure('public.hidra_topology_installed_installation_event_append()')) OR (tgrelid=pg_catalog.to_regclass('public.hidra_topology_installed_unit_installation_event') AND tgname='topology_installed_installation_event_guard' AND tgtype=31 AND tgfoid=pg_catalog.to_regprocedure('public.hidra_topology_installed_installation_event_guard()')) OR (tgrelid=pg_catalog.to_regclass('public.hidra_topology_installed_unit_installation_event') AND tgname='topology_installed_installation_event_no_truncate' AND tgtype=34 AND tgfoid=pg_catalog.to_regprocedure('public.hidra_topology_installed_unit_immutable()')) OR (tgrelid=pg_catalog.to_regclass('public.hidra_topology_installed_unit_operation_request') AND tgname='topology_installed_unit_request_guard' AND tgtype=31 AND tgfoid=pg_catalog.to_regprocedure('public.hidra_topology_installed_unit_request_guard()')) OR (tgrelid=pg_catalog.to_regclass('public.hidra_topology_installed_unit_operation_request') AND tgname='topology_installed_unit_request_no_truncate' AND tgtype=34 AND tgfoid=pg_catalog.to_regprocedure('public.hidra_topology_installed_unit_immutable()'))))
   FROM pg_catalog.pg_trigger WHERE tgrelid IN (pg_catalog.to_regclass('public.hidra_topology_installed_unit'),pg_catalog.to_regclass('public.hidra_topology_installed_unit_installation'),pg_catalog.to_regclass('public.hidra_topology_installed_unit_installation_event'),pg_catalog.to_regclass('public.hidra_topology_installed_unit_operation_request')) AND NOT tgisinternal)
 AND (SELECT count(*)=5 AND bool_and(prokind='f' AND pronargs=0 AND prorettype='trigger'::regtype
   AND NOT proretset AND provariadic=0 AND pronargdefaults=0 AND proallargtypes IS NULL AND NOT prosecdef AND NOT proleakproof AND NOT proisstrict AND NOT pg_catalog.pg_has_role(current_user,p.proowner,'MEMBER') AND (p.proname='hidra_topology_installed_unit_request_guard' OR p.proconfig IS NULL) AND provolatile='v' AND proparallel='u' AND prolang=(SELECT oid FROM pg_catalog.pg_language WHERE lanname='plpgsql')
   AND ((p.oid=pg_catalog.to_regprocedure('public.hidra_topology_installed_unit_immutable()') AND pg_catalog.encode(pg_catalog.sha256(pg_catalog.convert_to(p.prosrc,'UTF8')),'hex')='e97580d007ee3d86c90bf2b2be5e1cc8ca295848db5766bc0e38c5790db58238') OR (p.oid=pg_catalog.to_regprocedure('public.hidra_topology_installed_installation_guard()') AND pg_catalog.encode(pg_catalog.sha256(pg_catalog.convert_to(p.prosrc,'UTF8')),'hex')='5198d922c0927955ed97e5dbde6a34a0f3ddedf4894aaa1629d19237f1a014ac') OR (p.oid=pg_catalog.to_regprocedure('public.hidra_topology_installed_installation_event_append()') AND pg_catalog.encode(pg_catalog.sha256(pg_catalog.convert_to(p.prosrc,'UTF8')),'hex')='59ad5bba6110d57082fdb883d76641045d9e0df2fa043b875cf3fdf7d935bb0b') OR (p.oid=pg_catalog.to_regprocedure('public.hidra_topology_installed_installation_event_guard()') AND pg_catalog.encode(pg_catalog.sha256(pg_catalog.convert_to(p.prosrc,'UTF8')),'hex')='c5985c26f6c52725a30021e07639894132ecdcc0e7ff33bf31b390d929510a36') OR (p.oid=pg_catalog.to_regprocedure('public.hidra_topology_installed_unit_request_guard()') AND pg_catalog.encode(pg_catalog.sha256(pg_catalog.convert_to(p.prosrc,'UTF8')),'hex')='1f979a40da87097e79098f1fcb6591bec8e9c0cdb0e07bbfdd2235214363878b'))) FROM pg_catalog.pg_proc p WHERE p.oid IN (pg_catalog.to_regprocedure('public.hidra_topology_installed_unit_immutable()'),pg_catalog.to_regprocedure('public.hidra_topology_installed_installation_guard()'),pg_catalog.to_regprocedure('public.hidra_topology_installed_installation_event_append()'),pg_catalog.to_regprocedure('public.hidra_topology_installed_installation_event_guard()'),pg_catalog.to_regprocedure('public.hidra_topology_installed_unit_request_guard()')))
 AND (SELECT proconfig=ARRAY['search_path=pg_catalog, pg_temp']::text[] FROM pg_catalog.pg_proc
 WHERE oid=pg_catalog.to_regprocedure('public.hidra_topology_installed_unit_request_guard()'))
        """;
    private static final String REPLACEMENT_CATALOG="""
 AND EXISTS(SELECT 1 FROM pg_catalog.pg_attribute WHERE attrelid=pg_catalog.to_regclass('public.hidra_topology_installed_unit_operation_request')
   AND attname='replacement_unit_id' AND attnum=20 AND atttypid='varchar'::regtype AND atttypmod=84 AND NOT attnotnull
   AND NOT attisdropped AND attidentity='' AND attgenerated='' AND NOT atthasdef)
 AND EXISTS(SELECT 1 FROM pg_catalog.pg_constraint WHERE conrelid=pg_catalog.to_regclass('public.hidra_topology_installed_unit_operation_request')
   AND conname='fk_installed_request_replacement_unit_company' AND contype='f' AND convalidated AND NOT condeferrable AND NOT condeferred
   AND confrelid=pg_catalog.to_regclass('public.hidra_topology_installed_unit') AND conkey=ARRAY[20,3]::smallint[] AND confkey=ARRAY[1,2]::smallint[]
   AND confupdtype='r' AND confdeltype='r' AND confmatchtype='s'
   AND (SELECT count(*)=4 AND count(DISTINCT tgfoid)=4 AND bool_and(tgenabled='O' AND tgisinternal
     AND tgfoid IN (SELECT p.oid FROM pg_catalog.pg_proc p JOIN pg_catalog.pg_namespace n ON n.oid=p.pronamespace
       WHERE n.nspname='pg_catalog' AND p.pronargs=0 AND p.prorettype='trigger'::regtype
       AND p.proname IN ('RI_FKey_check_ins','RI_FKey_check_upd','RI_FKey_restrict_del','RI_FKey_restrict_upd')))
     FROM pg_catalog.pg_trigger WHERE tgconstraint=pg_constraint.oid))
 AND (SELECT count(*)=2 AND bool_and(contype='c' AND convalidated AND NOT condeferrable AND NOT connoinherit AND
   ((conname='ck_installed_request_operation' AND regexp_replace(pg_catalog.pg_get_constraintdef(oid),'[()[:space:]]','','g')=
     'CHECKoperation::text=ANYARRAY[''REGISTER_UNIT''::charactervarying,''RECORD_INSTALLATION''::charactervarying,''CLOSE_INSTALLATION''::charactervarying,''RELOCATE''::charactervarying,''REPLACE''::charactervarying]::text[]')
    OR (conname='ck_installed_request_replacement_unit' AND regexp_replace(pg_catalog.pg_get_constraintdef(oid),'[()[:space:]]','','g')=
     'CHECKCASEWHENoperation::text=''REPLACE''::textTHENreplacement_unit_idISNOTNULLANDreplacement_unit_id::text<>unit_id::textELSEreplacement_unit_idISNULLEND')))
   FROM pg_catalog.pg_constraint WHERE conrelid=pg_catalog.to_regclass('public.hidra_topology_installed_unit_operation_request')
    AND conname IN ('ck_installed_request_operation','ck_installed_request_replacement_unit'))
        """;
    private static final String U="public.hidra_topology_installed_unit",I=U+"_installation",H=I+"_event",R=U+"_operation_request";
    private final JdbcTemplate jdbc;private final DataSource source;private final EntityManager em;private final ProtectedTransactionExecutor executor;
    public JdbcProtectedInstalledUnitRepositoryAdapter(JdbcTemplate jdbc,EntityManager em,ProtectedTransactionExecutor executor) {
        this.jdbc=Objects.requireNonNull(jdbc);source=Objects.requireNonNull(jdbc.getDataSource());this.em=Objects.requireNonNull(em);this.executor=Objects.requireNonNull(executor);
    }
    private static IllegalStateException invalid(){return new IllegalStateException("Installed-unit storage unavailable");}
    private void caller() {
        if(!StackWalker.getInstance().walk(s->s.anyMatch(f->f.getClassName().equals("dz.sh.hidra.modules.topology.application.service.ProtectedInstalledUnitAdmissionCoordinator")
            || f.getClassName().equals("dz.sh.hidra.modules.topology.application.service.InstalledUnitLifecycleApplicationService"))))throw invalid();
    }
    private ConnectionHolder context() {
        caller();if(!TransactionSynchronizationManager.isActualTransactionActive() || !TransactionSynchronizationManager.isSynchronizationActive()
            || TransactionSynchronizationManager.isCurrentTransactionReadOnly() || !(TransactionSynchronizationManager.getResource(source) instanceof ConnectionHolder h) || h.isRollbackOnly())throw invalid();return h;
    }
    private Ticket ticket(Admission a) {
        var h=context();if(!(a instanceof Ticket t) || t.issuer!=this || !t.active || t.thread!=Thread.currentThread() || t.holder!=h || t.connection!=h.getConnection())throw invalid();return t;
    }
    private static String quote(String value){return "pg_catalog.convert_from(pg_catalog.decode('"+HexFormat.of().formatHex(utf8(value))+"','hex'),'UTF8')";}
    private String ordinaryDenied(String ordinary) {
        String who=quote(ordinary);return " AND current_user<>"+who+" AND NOT pg_catalog.pg_has_role("+who+"::name,current_user,'MEMBER')"
            +" AND NOT EXISTS(SELECT 1 FROM pg_catalog.pg_class c WHERE c.oid IN ('"+U+"'::regclass,'"+I+"'::regclass,'"+H+"'::regclass,'"+R+"'::regclass) AND (pg_catalog.pg_has_role("+who+"::name,c.relowner,'MEMBER') OR pg_catalog.has_table_privilege("+who+"::name,c.oid,'INSERT,UPDATE,DELETE,TRUNCATE,TRIGGER') OR pg_catalog.has_any_column_privilege("+who+"::name,c.oid,'INSERT,UPDATE,REFERENCES')))";
    }
    private void catalog() { catalog(null); }
    private void catalog(Ticket ticket) {
        var identity=executor.publicationDatabaseIdentity();
        String schemas=jdbc.queryForObject("SELECT pg_catalog.array_to_json(pg_catalog.current_schemas(true))::pg_catalog.text",String.class);
        if(schemas==null || !(schemas.equals("[\"pg_catalog\"]") || schemas.startsWith("[\"pg_catalog\",")))throw invalid();
        String coordinates=ticket==null?"":" AND pg_catalog.pg_backend_pid()="+ticket.pid+" AND pg_catalog.txid_current()="+ticket.xid
            +" AND current_user="+quote(ticket.role)+" AND "+held(finalFences(ticket));
        if(!Boolean.TRUE.equals(jdbc.queryForObject("SELECT "+CATALOG+REPLACEMENT_CATALOG
            +ordinaryDenied(identity.ordinaryUser())+" AND current_user=? AND session_user=current_user"+coordinates,
            Boolean.class,identity.publisherUser())))throw invalid();
    }
    @Override public Hint discover(Command command) {
        try{context();catalog();return new Hint(unit(command),installation(command.companyPartyId(),command.installationId()),unit(command.companyPartyId(),command.replacementUnitId()));}
        catch(RuntimeException failure){reject(null);throw failure;}
    }
    @Override public Admission acquire(Command command,Hint expected,List<ProtectedRelationFence> expectedFences) {
        try {
            var h=context();
            for(String table:List.of(U,I,H,R))jdbc.execute("LOCK TABLE "+table+" IN SHARE ROW EXCLUSIVE MODE NOWAIT");
            jdbc.execute("LOCK TABLE public.hidra_topology_equipment_type IN ACCESS SHARE MODE NOWAIT");catalog();
            var principals=executor.publicationDatabaseIdentity();
            var t=jdbc.queryForObject("SELECT pg_catalog.pg_backend_pid(),pg_catalog.txid_current(),current_user",(row,n)->new Ticket(command,h,row.getInt(1),row.getLong(2),row.getString(3),principals.ordinaryUser()));
            if(t==null || !expected.equals(new Hint(unit(command),installation(command.companyPartyId(),command.installationId()),unit(command.companyPartyId(),command.replacementUnitId()))))throw invalid();
            var logical=expected.unit()==null?command.registration():expected.unit();
            if(logical==null || !Boolean.TRUE.equals(jdbc.queryForObject("SELECT EXISTS(SELECT 1 FROM public.hidra_topology_equipment_type WHERE id=? AND tableoid=pg_catalog.to_regclass('public.hidra_topology_equipment_type'))",Boolean.class,logical.equipmentKindId())))throw invalid();
            t.hint=expected;t.logical=logical;t.baselineFences=List.copyOf(expectedFences);
            if(expectedFences.stream().map(ProtectedRelationFence::relation).distinct().count()!=expectedFences.size()
                || !expectedFences.containsAll(finalFences(t)) || !Boolean.TRUE.equals(jdbc.queryForObject("SELECT "+held(expectedFences),Boolean.class)))throw invalid();
            // Observations precede all owner samples; they grant no authority. The coordinator
            // later requires the actual issuer fence union to equal this compiled expectation.
            t.initialCatalog=jdbc.queryForObject("SELECT "+catalogImage(expectedFences),String.class);
            t.initialSources=jdbc.queryForObject("SELECT "+sourceSql(t),String.class);
            TransactionSynchronizationManager.registerSynchronization(t);return t;
        }catch(RuntimeException failure){reject(null);throw failure;}
    }
    private InstalledUnit unit(Command c) {return unit(c.companyPartyId(),c.unitId());}
    private InstalledUnit unit(String company,String id) {
        if(id==null)return null;
        var list=jdbc.query("SELECT * FROM "+U+" WHERE id=? AND company_party_id=? AND tableoid=pg_catalog.to_regclass('"+U+"')",(row,n)->new InstalledUnit(row.getString("id"),row.getString("company_party_id"),row.getString("equipment_kind_id"),
            evidence(row,"manufacturer_party"),evidence(row,"manufacturer_model"),evidence(row,"serial"),row.getString("maintainable_asset_id")),id,company);
        if(list.size()>1)throw invalid();return list.isEmpty()?null:list.get(0);
    }
    private static InstalledUnit.Evidence evidence(ResultSet r,String p)throws SQLException{return new InstalledUnit.Evidence(r.getString(p+"_value"),InstalledUnit.Verification.valueOf(r.getString(p+"_verification")),r.getString(p+"_source"));}
    private static InstalledUnitInstallation row(ResultSet r)throws SQLException {
        return new InstalledUnitInstallation(r.getString("installation_id"),r.getString("unit_id"),r.getString("position_id"),r.getString("company_party_id"),
            r.getTimestamp("effective_from").toInstant(),r.getTimestamp("effective_until")==null?null:r.getTimestamp("effective_until").toInstant(),r.getTimestamp("recorded_at").toInstant(),r.getString("evidence_reference"),r.getLong("revision"));
    }
    private InstalledUnitInstallation installation(String company,String id) {
        if(id==null)return null;var list=jdbc.query("SELECT * FROM "+I+" WHERE installation_id=? AND company_party_id=? AND tableoid=pg_catalog.to_regclass('"+I+"')",(r,n)->row(r),id,company);
        if(list.size()>1)throw invalid();return list.isEmpty()?null:list.get(0);
    }
    private List<InstalledUnitInstallation> history(String company,String id) {
        if(id==null)return List.of();var rows=jdbc.query("SELECT * FROM "+H+" WHERE installation_id=? AND company_party_id=? AND tableoid=pg_catalog.to_regclass('"+H+"') ORDER BY revision LIMIT 3",(r,n)->row(r),id,company);
        if(rows.size()>2)throw invalid();return List.copyOf(rows);
    }
    @Override public Snapshot observe(Admission a) {
        try {
            var t=ticket(a);catalog(t);var c=t.command;
            var receipts=c.requestId()==null?List.<Map<String,Object>>of():jdbc.queryForList("SELECT * FROM "+R+" WHERE request_id=? AND company_party_id=? AND tableoid=pg_catalog.to_regclass('"+R+"')",c.requestId(),c.companyPartyId());
            String digest=null;Result result=null;
            if(receipts.size()>1)throw invalid();
            if(!receipts.isEmpty()) {
                var receipt=receipts.get(0);byte[] canonical=(byte[])receipt.get("canonical_command"),payload=(byte[])receipt.get("result_payload");
                digest=(String)receipt.get("command_sha256");
                if(!Objects.equals(digest,sha(canonical)) || !Objects.equals(receipt.get("result_sha256"),sha(payload)))throw invalid();
                var prior=decode(payload);
                if(Objects.equals(receipt.get("unit_id"),c.unitId()) && !Arrays.equals(canonical,commandBytes(c)))
                    throw new InstalledUnitLifecycleFailure(Kind.CONFLICT,null);
                if(!Objects.equals(receipt.get("unit_id"),c.unitId()) || !Objects.equals(receipt.get("operation"),c.operation().name())
                    || !Objects.equals(receipt.get("replacement_unit_id"),c.replacementUnitId())
                    || !Arrays.equals(canonical,commandBytes(c)))throw invalid();result=prior;
            }
            return new Snapshot(unit(c),installation(c.companyPartyId(),c.installationId()),installation(c.companyPartyId(),c.replacementInstallationId()),
                history(c.companyPartyId(),c.installationId()),history(c.companyPartyId(),c.replacementInstallationId()),digest,result,unit(c.companyPartyId(),c.replacementUnitId()));
        }catch(RuntimeException failure){reject(a);throw failure;}
    }
    @Override public Set<String> mutationSql(){caller();return MUTATIONS;}
    @Override public List<ProtectedRelationFence> finalFences(Admission a) {
        ticket(a);return List.of(new ProtectedRelationFence(U,ProtectedRelationFence.Mode.SHARE_ROW_EXCLUSIVE),new ProtectedRelationFence(I,ProtectedRelationFence.Mode.SHARE_ROW_EXCLUSIVE),
            new ProtectedRelationFence(H,ProtectedRelationFence.Mode.SHARE_ROW_EXCLUSIVE),new ProtectedRelationFence(R,ProtectedRelationFence.Mode.SHARE_ROW_EXCLUSIVE),
            new ProtectedRelationFence("public.hidra_topology_equipment_type",ProtectedRelationFence.Mode.ACCESS_SHARE));
    }
    private static Timestamp timestamp(Instant i){return i==null?null:Timestamp.from(i);}
    private void insert(InstalledUnitInstallation row) {
        if(jdbc.update(INSTALL_INSERT,row.installationId(),row.installedUnitId(),row.equipmentPositionId(),row.companyPartyId(),timestamp(row.effectiveFrom()),timestamp(row.effectiveUntil()),timestamp(row.recordedAt()),row.evidenceReference(),row.revision())!=1)throw invalid();
    }
    @Override public void apply(Admission a,Result result,Origin origin) {
        try {
            var t=ticket(a);if(t.mutated || t.frozen || !t.command.mutation() || origin.backendPid()!=t.pid || origin.transactionId()!=t.xid)throw invalid();
            t.mutated=true;var c=t.command;
            switch(c.operation()) {
                case REGISTER_UNIT -> {
                    if(t.hint.unit()==null) {
                        var u=result.unit();
                        if(jdbc.update(UNIT_INSERT,u.id(),u.companyPartyId(),u.equipmentKindId(),u.manufacturerParty().value(),u.manufacturerParty().verification().name(),u.manufacturerParty().sourceReference(),
                            u.manufacturerModel().value(),u.manufacturerModel().verification().name(),u.manufacturerModel().sourceReference(),u.serialNumber().value(),u.serialNumber().verification().name(),u.serialNumber().sourceReference(),u.maintainableAssetId())!=1)throw invalid();
                    }
                }
                case RECORD_INSTALLATION -> insert(result.installation());
                case CLOSE_INSTALLATION,RELOCATE,REPLACE -> {
                    if(jdbc.update(CLOSE,timestamp(c.effectiveUntil()),c.companyPartyId(),c.installationId(),c.expectedRevision())!=1)throw new InstalledUnitLifecycleFailure(Kind.CONFLICT,null);
                    if(c.operation()==Operation.RELOCATE || c.operation()==Operation.REPLACE)insert(result.replacement());
                }
                default -> throw invalid();
            }
            byte[] canonical=commandBytes(c),payload=encode(result);
            if(c.operation()==Operation.REPLACE) {
                if(jdbc.update(REPLACEMENT_RECEIPT_INSERT,c.requestId(),c.operation().name(),c.companyPartyId(),c.unitId(),c.installationId(),c.replacementInstallationId(),c.expectedRevision(),canonical,sha(canonical),payload,sha(payload),origin.actorId(),origin.bindingId(),origin.sessionId(),timestamp(origin.sampledAt()),timestamp(origin.validUntil()),origin.backendPid(),origin.transactionId(),t.role,c.replacementUnitId())!=1)throw invalid();
            }else {
            if(jdbc.update(RECEIPT_INSERT,c.requestId(),c.operation().name(),c.companyPartyId(),c.unitId(),c.installationId(),c.replacementInstallationId(),c.expectedRevision(),canonical,sha(canonical),payload,sha(payload),origin.actorId(),origin.bindingId(),origin.sessionId(),timestamp(origin.sampledAt()),timestamp(origin.validUntil()),origin.backendPid(),origin.transactionId(),t.role)!=1)throw invalid();
            }
        }catch(RuntimeException failure){reject(a);
            for(Throwable cause=failure;cause!=null;cause=cause.getCause())if(cause instanceof SQLException sql && "23P01".equals(sql.getSQLState())) {
                try {
                    Object diagnostic=sql.getClass().getMethod("getServerErrorMessage").invoke(sql);
                    if(diagnostic==null)continue;
                    Object constraint=diagnostic.getClass().getMethod("getConstraint").invoke(diagnostic);
                    if(constraint instanceof String name && Set.of("ex_installed_unit_effective_unit","ex_installed_unit_effective_position").contains(name))
                        throw new InstalledUnitLifecycleFailure(Kind.CONFLICT,failure);
                }catch(ReflectiveOperationException | SecurityException unavailable){/* Unknown diagnostics remain unavailable. */}
            }
            throw failure;
        }
    }
    @Override public void verifyResult(Admission a,Result result,boolean replay) {
        try {
            var t=ticket(a);var s=observe(a);var c=t.command;
            if(!Objects.equals(result.unit(),s.unit()) || !result.unit().id().equals(c.unitId()) || !result.unit().companyPartyId().equals(c.companyPartyId()))throw invalid();
            if(c.operation()==Operation.REGISTER_UNIT && !result.unit().equals(c.registration()))throw invalid();
            if(!Objects.equals(result.replacementUnit(),s.replacementUnit()))throw invalid();
            if(c.operation()==Operation.REPLACE ? result.replacementUnit()==null
                || !result.replacementUnit().id().equals(c.replacementUnitId())
                || !result.replacementUnit().companyPartyId().equals(c.companyPartyId())
                || !result.replacementUnit().equipmentKindId().equals(result.unit().equipmentKindId()) : result.replacementUnit()!=null)throw invalid();
            if(c.mutation()) {
                if(!Objects.equals(s.receiptDigest(),commandDigest(c)) || s.receiptResult()==null
                    || !Arrays.equals(encode(new Result(result.unit(),result.installation(),result.replacement(),result.history(),false,result.replacementUnit())),encode(s.receiptResult())))throw invalid();
            }
            if(result.installation()!=null) {
                var x=result.installation();
                var position=c.operation()==Operation.RECORD_INSTALLATION?c.destination():c.source();
                if(!x.installationId().equals(c.installationId()) || !x.companyPartyId().equals(c.companyPartyId())
                    || position==null || !x.equipmentPositionId().equals(position.positionId()))throw invalid();
                if(c.operation()==Operation.RECORD_INSTALLATION && (!x.effectiveFrom().equals(c.effectiveFrom())
                    || !Objects.equals(x.effectiveUntil(),c.effectiveUntil()) || !x.evidenceReference().equals(c.evidenceReference()) || x.revision()!=1))throw invalid();
                if((c.operation()==Operation.CLOSE_INSTALLATION || c.operation()==Operation.RELOCATE || c.operation()==Operation.REPLACE)
                    && (!Objects.equals(x.effectiveUntil(),c.effectiveUntil()) || x.revision()!=c.expectedRevision()+1))throw invalid();
                if(!result.installation().installedUnitId().equals(c.unitId()) || !s.history().contains(result.installation()))throw invalid();
                if(!replay && !result.installation().equals(s.source()))throw invalid();
            } else if(c.installationId()!=null)throw invalid();
            if(result.replacement()!=null && ((c.operation()!=Operation.RELOCATE && c.operation()!=Operation.REPLACE) || !result.replacement().installationId().equals(c.replacementInstallationId())
                || !result.replacement().equipmentPositionId().equals(c.destination().positionId()) || !result.replacement().companyPartyId().equals(c.companyPartyId())
                || !result.replacement().effectiveFrom().equals(c.effectiveFrom()) || result.replacement().effectiveUntil()!=null
                || !result.replacement().evidenceReference().equals(c.evidenceReference()) || result.replacement().revision()!=1))throw invalid();
            if((c.operation()==Operation.RELOCATE || c.operation()==Operation.REPLACE) && result.replacement()==null)throw invalid();
            if(result.replacement()!=null && (!s.replacementHistory().contains(result.replacement()) || !result.replacement().installedUnitId().equals(c.operation()==Operation.REPLACE?c.replacementUnitId():c.unitId())
                || !replay && !result.replacement().equals(s.replacement())))throw invalid();
            for(var h:result.history())if(!s.history().contains(h))throw invalid();
            if(c.operation()==Operation.READ_HISTORY && !result.history().equals(s.history()))throw invalid();
            t.state=state(t);
        }catch(RuntimeException failure){reject(a);throw failure;}
    }
    private static String held(List<ProtectedRelationFence> fences) {
        if(fences.isEmpty() || fences.stream().map(ProtectedRelationFence::relation).distinct().count()!=fences.size())throw invalid();
        String required=fences.stream().map(f->"(pg_catalog.to_regclass("+quote(f.relation())+"),"+quote(f.mode().catalog())+")")
            .collect(java.util.stream.Collectors.joining(","));
        // One pg_locks scan matches every exact relation AND mode; a stronger/different mode is not substituted.
        return "(SELECT count(DISTINCT required.relation)="+fences.size()+" FROM (VALUES "+required+") required(relation,mode)"
            +" JOIN pg_catalog.pg_locks actual ON actual.relation=required.relation AND actual.mode=required.mode"
            +" WHERE actual.pid=pg_catalog.pg_backend_pid() AND actual.granted AND actual.locktype='relation')";
    }
    private String stateSql(Ticket t) {
        var c=t.command;String unit=quote(c.unitId())+(c.replacementUnitId()==null?"":","+quote(c.replacementUnitId())),company=quote(c.companyPartyId());String ids=quote(c.installationId()==null?"":c.installationId())+","+quote(c.replacementInstallationId()==null?"":c.replacementInstallationId());
        return "pg_catalog.encode(pg_catalog.sha256(pg_catalog.convert_to(pg_catalog.jsonb_build_array("
            +"(SELECT jsonb_agg(to_jsonb(x) ORDER BY id) FROM "+U+" x WHERE id IN ("+unit+")),"
            +"(SELECT jsonb_agg(to_jsonb(x) ORDER BY installation_id) FROM "+I+" x WHERE company_party_id="+company+" AND installation_id IN ("+ids+")),"
            +"(SELECT jsonb_agg(to_jsonb(x) ORDER BY installation_id,revision) FROM "+H+" x WHERE company_party_id="+company+" AND installation_id IN ("+ids+")),"
            +"(SELECT jsonb_agg(to_jsonb(x) ORDER BY request_id) FROM "+R+" x WHERE request_id="+quote(c.requestId()==null?"":c.requestId())+"))::text,'UTF8')),'hex')";
    }
    private String state(Ticket t){return jdbc.queryForObject("SELECT "+stateSql(t),String.class);}
    private static String catalogImage(List<ProtectedRelationFence> fences) {
        String oids=fences.stream().map(f->"(pg_catalog.to_regclass("+quote(f.relation())+"))").collect(java.util.stream.Collectors.joining(","));
        String relations="SELECT oid FROM protected_catalog_relations";
        // Resolve the retained relation identities once per observation, including the sole final statement.
        return "(WITH protected_catalog_relations AS MATERIALIZED (SELECT oid FROM (VALUES "+oids+") required(oid)) SELECT pg_catalog.encode(pg_catalog.sha256(pg_catalog.convert_to(pg_catalog.jsonb_build_array("
            +"(SELECT jsonb_agg(jsonb_build_array(oid,relowner,relacl,relkind,relpersistence,relrowsecurity,relforcerowsecurity,relispartition) ORDER BY oid) FROM pg_catalog.pg_class WHERE oid IN ("+relations+")),"
            +"(SELECT jsonb_agg(to_jsonb(x) ORDER BY oid) FROM pg_catalog.pg_constraint x WHERE conrelid IN ("+relations+") OR confrelid IN ("+relations+")),"
            +"(SELECT jsonb_agg(to_jsonb(x) ORDER BY oid) FROM pg_catalog.pg_trigger x WHERE tgrelid IN ("+relations+")),"
            +"(SELECT jsonb_agg(jsonb_build_array(p.oid,p.proowner,p.proacl,p.proconfig,p.prosecdef,p.prosrc) ORDER BY p.oid) FROM pg_catalog.pg_proc p WHERE p.oid IN (SELECT tgfoid FROM pg_catalog.pg_trigger WHERE tgrelid IN ("+relations+")) OR p.oid IN (pg_catalog.to_regprocedure('public.hidra_party_lock_installed_unit_manufacturer_evidence()'),pg_catalog.to_regprocedure('public.hidra_topology_lock_installed_unit_position_ownership()'))),"
            +"(SELECT jsonb_agg(to_jsonb(x) ORDER BY oid) FROM pg_catalog.pg_roles x),"
            +"(SELECT jsonb_agg(to_jsonb(x) ORDER BY roleid,member,grantor) FROM pg_catalog.pg_auth_members x))::text,'UTF8')),'hex'))";
    }
    private static String sourceSql(Ticket t) {
        String parties=quote(t.command.companyPartyId())+(t.logical.manufacturerParty().isKnown()?","+quote(t.logical.manufacturerParty().value()):"")
            +(t.hint.replacementUnit()!=null && t.hint.replacementUnit().manufacturerParty().isKnown()?","+quote(t.hint.replacementUnit().manufacturerParty().value()):"");
        var placements=new ArrayList<Position>();if(t.command.source()!=null)placements.add(t.command.source());if(t.command.destination()!=null)placements.add(t.command.destination());
        String positions=placements.isEmpty()?quote(""):placements.stream().map(p->quote(p.positionId())).collect(java.util.stream.Collectors.joining(","));
        String facilities=placements.isEmpty()?quote(""):placements.stream().map(p->quote(p.facilityId())).collect(java.util.stream.Collectors.joining(","));
        String placementData=placements.isEmpty()?"NULL,NULL,NULL,":
            "(SELECT jsonb_agg(to_jsonb(x) ORDER BY id) FROM public.hidra_topology_equipment x WHERE id IN ("+positions+")),"
            +"(SELECT jsonb_agg(to_jsonb(x) ORDER BY id) FROM public.hidra_topology_facility x WHERE id IN ("+facilities+")),"
            +"(SELECT jsonb_agg(to_jsonb(x) ORDER BY facility_id) FROM public.hidra_topology_facility_owner_generation x WHERE facility_id IN ("+facilities+")),";
        return "pg_catalog.encode(pg_catalog.sha256(pg_catalog.convert_to(pg_catalog.jsonb_build_array("
            +"(SELECT jsonb_agg(to_jsonb(x) ORDER BY id) FROM public.hidra_party_party x WHERE id IN ("+parties+")),"
            +"(SELECT jsonb_agg(to_jsonb(x) ORDER BY party_id) FROM public.hidra_party_eligibility_generation x WHERE party_id IN ("+parties+")),"
            +placementData
            +"(SELECT jsonb_agg(to_jsonb(x)) FROM public.hidra_identity_external_mapping_generation x))::text,'UTF8')),'hex')";
    }
    @Override public void freeze(Admission a,Origin origin,List<ProtectedRelationFence> fences) {
        try {
            var t=ticket(a);if(t.frozen || t.state==null || origin.backendPid()!=t.pid || origin.transactionId()!=t.xid)throw invalid();
            catalog(t);
            if(fences.stream().map(ProtectedRelationFence::relation).distinct().count()!=fences.size() || !fences.containsAll(finalFences(t)))throw invalid();
            if(!t.baselineFences.equals(fences) || !Boolean.TRUE.equals(jdbc.queryForObject(
                "SELECT "+stateSql(t)+"=? AND "+catalogImage(fences)+"=? AND "+sourceSql(t)+"=?",
                Boolean.class,t.state,t.initialCatalog,t.initialSources)))throw invalid();
            t.origin=origin;t.fences=List.copyOf(fences);t.catalog=t.initialCatalog;t.frozen=true;
        }catch(RuntimeException failure){reject(a);throw failure;}
    }
    @Override public boolean finalGuard(Admission a) {
        var t=ticket(a);if(!t.frozen || t.guarded)throw invalid();t.guarded=true;
        return Boolean.TRUE.equals(jdbc.queryForObject("WITH observed AS MATERIALIZED (SELECT pg_catalog.clock_timestamp() t) SELECT "
            +"(SELECT t FROM observed)>=?::timestamptz AND (SELECT t FROM observed)<?::timestamptz AND pg_catalog.pg_backend_pid()=? AND pg_catalog.txid_current()=? AND current_user=? AND session_user=current_user"
            +" AND "+CATALOG+REPLACEMENT_CATALOG+ordinaryDenied(t.ordinary)+" AND "+held(t.fences)+" AND "+stateSql(t)+"=? AND "+catalogImage(t.fences)+"=? AND "+sourceSql(t)+"=?",
            Boolean.class,timestamp(t.origin.sampledAt()),timestamp(t.origin.validUntil()),t.pid,t.xid,t.role,t.state,t.catalog,t.initialSources));
    }
    @Override public void flush(){caller();context();em.flush();}
    @Override public void reject(Admission a) {
        if(a instanceof Ticket t){t.active=false;t.holder.setRollbackOnly();}
        if(TransactionSynchronizationManager.getResource(source) instanceof ConnectionHolder h)h.setRollbackOnly();
    }
    private final class Ticket implements Admission,TransactionSynchronization {
        private final JdbcProtectedInstalledUnitRepositoryAdapter issuer=JdbcProtectedInstalledUnitRepositoryAdapter.this;
        private final Command command;private final ConnectionHolder holder;private final Connection connection;
        private final int pid;private final long xid;private final String role,ordinary;private final Thread thread=Thread.currentThread();
        private volatile boolean active=true;private boolean mutated,frozen,guarded;private Hint hint;private InstalledUnit logical;private Origin origin;private List<ProtectedRelationFence> fences,baselineFences;private String state,catalog,initialCatalog,initialSources;
        private Ticket(Command command,ConnectionHolder holder,int pid,long xid,String role,String ordinary){this.command=command;this.holder=holder;connection=holder.getConnection();this.pid=pid;this.xid=xid;this.role=role;this.ordinary=ordinary;}
        @Override public void suspend(){active=false;holder.setRollbackOnly();}
        @Override public void afterCompletion(int status){active=false;}
        @Override public void beforeCommit(boolean readOnly){if(!active || !guarded || holder.isRollbackOnly() || thread!=Thread.currentThread())throw invalid();}
    }
    private static String sha(byte[] bytes) {
        try{return HexFormat.of().formatHex(java.security.MessageDigest.getInstance("SHA-256").digest(bytes));}
        catch(java.security.NoSuchAlgorithmException impossible){throw new IllegalStateException(impossible);}
    }
    private interface Writer {void write(DataOutputStream out)throws IOException;}
    private static byte[] bytes(Writer writer) {
        try{var b=new ByteArrayOutputStream();var out=new DataOutputStream(b);out.writeInt(1);writer.write(out);out.flush();if(b.size()>1048576)throw invalid();return b.toByteArray();}
        catch(IOException failure){throw invalid();}
    }
    private static byte[] replacementBytes(Writer writer) {
        try{var b=new ByteArrayOutputStream();var out=new DataOutputStream(b);out.writeInt(2);writer.write(out);out.flush();if(b.size()>1048576)throw invalid();return b.toByteArray();}
        catch(IOException failure){throw invalid();}
    }
    private static byte[] utf8(String value) {
        try {
            var buffer=StandardCharsets.UTF_8.newEncoder().onMalformedInput(java.nio.charset.CodingErrorAction.REPORT)
                .onUnmappableCharacter(java.nio.charset.CodingErrorAction.REPORT).encode(java.nio.CharBuffer.wrap(value));
            byte[] encoded=new byte[buffer.remaining()];buffer.get(encoded);return encoded;
        }catch(java.nio.charset.CharacterCodingException failure){throw invalid();}
    }
    private static void text(DataOutputStream out,String text)throws IOException {
        if(text==null){out.writeInt(-1);return;}byte[] value=utf8(text);if(value.length>65536)throw invalid();out.writeInt(value.length);out.write(value);
    }
    private static String text(DataInputStream in)throws IOException {
        int n=in.readInt();if(n==-1)return null;if(n<0 || n>65536 || n>in.available())throw invalid();byte[] value=in.readNBytes(n);String text=new String(value,StandardCharsets.UTF_8);if(!Arrays.equals(value,text.getBytes(StandardCharsets.UTF_8)))throw invalid();return text;
    }
    private static void instant(DataOutputStream out,Instant value)throws IOException{text(out,value==null?null:value.toString());}
    private static Instant instant(DataInputStream in)throws IOException{String s=text(in);return s==null?null:Instant.parse(s);}
    private static void unit(DataOutputStream out,InstalledUnit u)throws IOException {
        out.writeBoolean(u!=null);if(u==null)return;text(out,u.id());text(out,u.companyPartyId());text(out,u.equipmentKindId());
        for(var e:List.of(u.manufacturerParty(),u.manufacturerModel(),u.serialNumber())){text(out,e.value());text(out,e.verification().name());text(out,e.sourceReference());}text(out,u.maintainableAssetId());
    }
    private static InstalledUnit unit(DataInputStream in)throws IOException {
        if(!in.readBoolean())return null;String id=text(in),company=text(in),kind=text(in);var e=new ArrayList<InstalledUnit.Evidence>();
        for(int n=0;n<3;n++)e.add(new InstalledUnit.Evidence(text(in),InstalledUnit.Verification.valueOf(text(in)),text(in)));
        return new InstalledUnit(id,company,kind,e.get(0),e.get(1),e.get(2),text(in));
    }
    private static void installation(DataOutputStream out,InstalledUnitInstallation x)throws IOException {
        out.writeBoolean(x!=null);if(x==null)return;text(out,x.installationId());text(out,x.installedUnitId());text(out,x.equipmentPositionId());text(out,x.companyPartyId());
        instant(out,x.effectiveFrom());instant(out,x.effectiveUntil());instant(out,x.recordedAt());text(out,x.evidenceReference());out.writeLong(x.revision());
    }
    private static InstalledUnitInstallation installation(DataInputStream in)throws IOException {
        return !in.readBoolean()?null:new InstalledUnitInstallation(text(in),text(in),text(in),text(in),instant(in),instant(in),instant(in),text(in),in.readLong());
    }
    private static byte[] encode(Result result) {
        if(result.replacementUnit()!=null)return replacementBytes(out->{unit(out,result.unit());installation(out,result.installation());installation(out,result.replacement());out.writeInt(result.history().size());for(var x:result.history())installation(out,x);out.writeBoolean(result.replayed());unit(out,result.replacementUnit());});
        return bytes(out->{unit(out,result.unit());installation(out,result.installation());installation(out,result.replacement());out.writeInt(result.history().size());for(var x:result.history())installation(out,x);out.writeBoolean(result.replayed());});
    }
    private static Result decode(byte[] value) {
        if(value==null || value.length>1048576)throw invalid();
        try{var in=new DataInputStream(new ByteArrayInputStream(value));int version=in.readInt();if(version!=1 && version!=2)throw invalid();var unit=unit(in);var source=installation(in);var replacement=installation(in);int n=in.readInt();if(n<0 || n>2)throw invalid();var history=new ArrayList<InstalledUnitInstallation>();for(int k=0;k<n;k++)history.add(installation(in));boolean replayed=in.readBoolean();var second=version==2?unit(in):null;if(version==2 && second==null)throw invalid();var result=new Result(unit,source,replacement,history,replayed,second);if(in.available()!=0 || result.replayed() || !Arrays.equals(value,encode(result)))throw invalid();return result;}
        catch(IOException failure){throw invalid();}
    }
    private static byte[] commandBytes(Command c) {
        if(c.operation()==Operation.REPLACE)return replacementBytes(out->{text(out,c.operation().name());text(out,c.requestId());text(out,c.companyPartyId());text(out,c.unitId());text(out,c.installationId());text(out,c.replacementInstallationId());
            for(var p:new Position[]{c.source(),c.destination()}){out.writeBoolean(p!=null);if(p!=null){text(out,p.positionId());text(out,p.facilityId());}}
            unit(out,c.registration());instant(out,c.effectiveFrom());instant(out,c.effectiveUntil());text(out,c.evidenceReference());out.writeLong(c.expectedRevision());text(out,c.replacementUnitId());});
        return bytes(out->{text(out,c.operation().name());text(out,c.requestId());text(out,c.companyPartyId());text(out,c.unitId());text(out,c.installationId());text(out,c.replacementInstallationId());
            for(var p:new Position[]{c.source(),c.destination()}){out.writeBoolean(p!=null);if(p!=null){text(out,p.positionId());text(out,p.facilityId());}}
            unit(out,c.registration());instant(out,c.effectiveFrom());instant(out,c.effectiveUntil());text(out,c.evidenceReference());out.writeLong(c.expectedRevision());});
    }
    @Override public String commandDigest(Command command){caller();return sha(commandBytes(command));}
}
