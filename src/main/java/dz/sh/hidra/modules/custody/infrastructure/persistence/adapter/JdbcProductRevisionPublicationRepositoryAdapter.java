/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : JdbcProductRevisionPublicationRepositoryAdapter
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-28
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : custody
 * @Package     : dz.sh.hidra.modules.custody.infrastructure.persistence.adapter
 *
 * @Description : Preserves governed product publication evidence under exact owner admission.
 *
 */
package dz.sh.hidra.modules.custody.infrastructure.persistence.adapter;
import dz.sh.hidra.modules.custody.application.port.in.ProductRevisionPublicationUseCase.*;
import dz.sh.hidra.modules.custody.application.port.out.ProductRevisionPublicationRepositoryPort;
import dz.sh.hidra.modules.custody.application.service.ProtectedProductPublicationAdmissionCoordinator.Contribution;
import dz.sh.hidra.modules.custody.domain.model.ProductRevisionPublication;
import dz.sh.hidra.modules.custody.domain.model.ProductRevisionPublication.*;
import dz.sh.hidra.modules.custody.domain.model.SuppliedFluidPropertyRevision.Facts;
import dz.sh.hidra.platform.persistence.*;
import java.sql.*;
import java.time.Instant;
import java.util.*;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.ConnectionHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.*;
import org.springframework.transaction.support.*;
@Repository
public class JdbcProductRevisionPublicationRepositoryAdapter implements ProductRevisionPublicationRepositoryPort {
    private static final String PREFIX="public.hidra_custody_product_publication";
    private static final List<String> RELATIONS=List.of("public.hidra_custody_catalog_entry","public.hidra_custody_engineering_product_policy",
        "public.hidra_custody_product_engineering_draft","public.hidra_custody_product_engineering_draft_version",
        "public.hidra_custody_supplied_fluid_property_draft","public.hidra_custody_supplied_fluid_property_version",
        PREFIX+"_candidate",PREFIX,PREFIX+"_head",PREFIX+"_event",PREFIX+"_request");
    private static final String CANDIDATE_INSERT="INSERT INTO public.hidra_custody_product_publication_candidate (product_draft_id,company_party_id,catalog_product_id,candidate_digest,metadata_version,supplied_version,payload_format,canonical_payload,sha256,actor_binding_id,actor_session_id,actor_user_id,party_generation,sampled_at,valid_until,backend_pid,transaction_id) VALUES (?,?,?,?,?,?,?,?,?,?::uuid,?::uuid,?,?,?,?,?,?)";
    private static final String PUBLICATION_INSERT="INSERT INTO public.hidra_custody_product_publication (product_draft_id,company_party_id,catalog_product_id,publication_id,candidate_digest,payload_format,canonical_payload,sha256,actor_binding_id,actor_session_id,actor_user_id,party_generation,sampled_at,valid_until,backend_pid,transaction_id) VALUES (?,?,?,?,?,?,?,?,?::uuid,?::uuid,?,?,?,?,?,?)";
    private static final String EVENT_INSERT="INSERT INTO public.hidra_custody_product_publication_event (product_draft_id,company_party_id,catalog_product_id,event_id,epoch,action,prior_publication_id,publication_id,state,request_id,command_sha256,effective_at,payload_format,canonical_payload,sha256,actor_binding_id,actor_session_id,actor_user_id,party_generation,sampled_at,valid_until,backend_pid,transaction_id) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?::uuid,?::uuid,?,?,?,?,?,?)";
    private static final String HEAD_INSERT="INSERT INTO public.hidra_custody_product_publication_head (product_draft_id,company_party_id,catalog_product_id,publication_id,epoch,state,event_id,effective_at,actor_binding_id,actor_session_id,actor_user_id,party_generation,sampled_at,valid_until,backend_pid,transaction_id) VALUES (?,?,?,?,?,?,?,?,?::uuid,?::uuid,?,?,?,?,?,?)";
    private static final String HEAD_UPDATE="UPDATE public.hidra_custody_product_publication_head SET publication_id=?,epoch=?,state=?,event_id=?,effective_at=?,actor_binding_id=?::uuid,actor_session_id=?::uuid,actor_user_id=?,party_generation=?,sampled_at=?,valid_until=?,backend_pid=?,transaction_id=? WHERE product_draft_id=? AND epoch=? AND publication_id=?";
    private static final String REQUEST_INSERT="INSERT INTO public.hidra_custody_product_publication_request (product_draft_id,company_party_id,catalog_product_id,request_id,kind,command_sha256,candidate_digest,event_id,payload_format,canonical_payload,sha256,actor_binding_id,actor_session_id,actor_user_id,party_generation,sampled_at,valid_until,backend_pid,transaction_id) VALUES (?,?,?,?,?,?,?,?,?,?,?,?::uuid,?::uuid,?,?,?,?,?,?)";
    private static final Set<String> SQL=Set.of(CANDIDATE_INSERT,PUBLICATION_INSERT,EVENT_INSERT,HEAD_INSERT,HEAD_UPDATE,REQUEST_INSERT);
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


    private static final String NEW_FUNCTIONS="EXISTS(SELECT 1 FROM pg_proc p WHERE p.oid='public.hidra_custody_publication_immutable()'::regprocedure AND p.prosecdef AND p.prokind='f' AND p.pronargs=0 AND p.prorettype='trigger'::regtype AND p.provolatile='v' AND NOT p.proleakproof AND p.prolang=(SELECT oid FROM pg_language WHERE lanname='plpgsql') AND NOT pg_has_role(current_user,p.proowner,'MEMBER') AND p.proconfig=ARRAY['search_path=pg_catalog, pg_temp'] AND encode(sha256(convert_to(p.prosrc,'UTF8')),'hex')='036cb8f55bd4b972a5338600958089c2bf7b88a117ee18bcfc2bdc8d263eeff1' AND NOT has_function_privilege(current_user,p.oid,'EXECUTE')) AND EXISTS(SELECT 1 FROM pg_proc p WHERE p.oid='public.hidra_custody_publication_admit()'::regprocedure AND p.prosecdef AND p.prokind='f' AND p.pronargs=0 AND p.prorettype='trigger'::regtype AND p.provolatile='v' AND NOT p.proleakproof AND p.prolang=(SELECT oid FROM pg_language WHERE lanname='plpgsql') AND NOT pg_has_role(current_user,p.proowner,'MEMBER') AND p.proconfig=ARRAY['search_path=pg_catalog, pg_temp'] AND encode(sha256(convert_to(p.prosrc,'UTF8')),'hex')='bb2c7e7a9c5b864ca4d2b573dad078297ce5215c95165c7ea915f395e86543e8' AND NOT has_function_privilege(current_user,p.oid,'EXECUTE'))";
    private static final String NEW_SCHEMA="""
        (SELECT count(*)=15 AND bool_and(k.convalidated AND NOT k.condeferrable) FROM pg_constraint k WHERE k.conrelid='public.hidra_custody_product_publication_candidate'::regclass) AND (SELECT count(*)=18 FROM pg_attribute a WHERE a.attrelid='public.hidra_custody_product_publication_candidate'::regclass AND a.attnum>0) AND EXISTS(SELECT 1 FROM pg_constraint k WHERE k.conrelid='public.hidra_custody_product_publication_candidate'::regclass AND k.contype='p' AND k.conkey=ARRAY[4]::smallint[]) AND EXISTS(SELECT 1 FROM pg_constraint k WHERE k.conrelid='public.hidra_custody_product_publication_candidate'::regclass AND k.contype='u' AND k.conkey=ARRAY[1,4]::smallint[]) AND (SELECT count(*)=11 AND bool_and(k.convalidated AND NOT k.condeferrable) FROM pg_constraint k WHERE k.conrelid='public.hidra_custody_product_publication'::regclass) AND (SELECT count(*)=17 FROM pg_attribute a WHERE a.attrelid='public.hidra_custody_product_publication'::regclass AND a.attnum>0) AND EXISTS(SELECT 1 FROM pg_constraint k WHERE k.conrelid='public.hidra_custody_product_publication'::regclass AND k.contype='p' AND k.conkey=ARRAY[4]::smallint[]) AND EXISTS(SELECT 1 FROM pg_constraint k WHERE k.conrelid='public.hidra_custody_product_publication'::regclass AND k.contype='u' AND k.conkey=ARRAY[1,4]::smallint[]) AND (SELECT count(*)=21 AND bool_and(k.convalidated AND NOT k.condeferrable) FROM pg_constraint k WHERE k.conrelid='public.hidra_custody_product_publication_event'::regclass) AND (SELECT count(*)=24 FROM pg_attribute a WHERE a.attrelid='public.hidra_custody_product_publication_event'::regclass AND a.attnum>0) AND EXISTS(SELECT 1 FROM pg_constraint k WHERE k.conrelid='public.hidra_custody_product_publication_event'::regclass AND k.contype='p' AND k.conkey=ARRAY[4]::smallint[]) AND EXISTS(SELECT 1 FROM pg_constraint k WHERE k.conrelid='public.hidra_custody_product_publication_event'::regclass AND k.contype='u' AND k.conkey=ARRAY[1,5]::smallint[]) AND EXISTS(SELECT 1 FROM pg_constraint k WHERE k.conrelid='public.hidra_custody_product_publication_event'::regclass AND k.contype='u' AND k.conkey=ARRAY[1,10]::smallint[]) AND EXISTS(SELECT 1 FROM pg_constraint k WHERE k.conrelid='public.hidra_custody_product_publication_event'::regclass AND k.contype='u' AND k.conkey=ARRAY[1,4]::smallint[]) AND (SELECT count(*)=10 AND bool_and(k.convalidated AND NOT k.condeferrable) FROM pg_constraint k WHERE k.conrelid='public.hidra_custody_product_publication_head'::regclass) AND (SELECT count(*)=17 FROM pg_attribute a WHERE a.attrelid='public.hidra_custody_product_publication_head'::regclass AND a.attnum>0) AND EXISTS(SELECT 1 FROM pg_constraint k WHERE k.conrelid='public.hidra_custody_product_publication_head'::regclass AND k.contype='p' AND k.conkey=ARRAY[1]::smallint[]) AND (SELECT count(*)=15 AND bool_and(k.convalidated AND NOT k.condeferrable) FROM pg_constraint k WHERE k.conrelid='public.hidra_custody_product_publication_request'::regclass) AND (SELECT count(*)=20 FROM pg_attribute a WHERE a.attrelid='public.hidra_custody_product_publication_request'::regclass AND a.attnum>0) AND EXISTS(SELECT 1 FROM pg_constraint k WHERE k.conrelid='public.hidra_custody_product_publication_request'::regclass AND k.contype='p' AND k.conkey=ARRAY[4]::smallint[]) AND EXISTS(SELECT 1 FROM pg_constraint k WHERE k.conrelid='public.hidra_custody_product_publication_request'::regclass AND k.contype='u' AND k.conkey=ARRAY[1,4]::smallint[]) AND NOT EXISTS(SELECT 1 FROM (VALUES ('public.hidra_custody_product_publication_candidate'::regclass,1,'product_draft_id','uuid'::regtype,-1,true),('public.hidra_custody_product_publication_candidate'::regclass,2,'company_party_id','varchar'::regtype,84,true),('public.hidra_custody_product_publication_candidate'::regclass,3,'catalog_product_id','varchar'::regtype,84,true),('public.hidra_custody_product_publication_candidate'::regclass,4,'candidate_digest','varchar'::regtype,68,true),('public.hidra_custody_product_publication_candidate'::regclass,5,'metadata_version','int8'::regtype,-1,true),('public.hidra_custody_product_publication_candidate'::regclass,6,'supplied_version','int8'::regtype,-1,true),('public.hidra_custody_product_publication_candidate'::regclass,7,'payload_format','varchar'::regtype,44,true),('public.hidra_custody_product_publication_candidate'::regclass,8,'canonical_payload','bytea'::regtype,-1,true),('public.hidra_custody_product_publication_candidate'::regclass,9,'sha256','varchar'::regtype,68,true),('public.hidra_custody_product_publication_candidate'::regclass,10,'actor_binding_id','uuid'::regtype,-1,true),('public.hidra_custody_product_publication_candidate'::regclass,11,'actor_session_id','uuid'::regtype,-1,true),('public.hidra_custody_product_publication_candidate'::regclass,12,'actor_user_id','varchar'::regtype,84,true),('public.hidra_custody_product_publication_candidate'::regclass,13,'party_generation','int8'::regtype,-1,true),('public.hidra_custody_product_publication_candidate'::regclass,14,'sampled_at','timestamptz'::regtype,-1,true),('public.hidra_custody_product_publication_candidate'::regclass,15,'valid_until','timestamptz'::regtype,-1,true),('public.hidra_custody_product_publication_candidate'::regclass,16,'backend_pid','int4'::regtype,-1,true),('public.hidra_custody_product_publication_candidate'::regclass,17,'transaction_id','int8'::regtype,-1,true),('public.hidra_custody_product_publication_candidate'::regclass,18,'created_at','timestamptz'::regtype,-1,true),('public.hidra_custody_product_publication'::regclass,1,'product_draft_id','uuid'::regtype,-1,true),('public.hidra_custody_product_publication'::regclass,2,'company_party_id','varchar'::regtype,84,true),('public.hidra_custody_product_publication'::regclass,3,'catalog_product_id','varchar'::regtype,84,true),('public.hidra_custody_product_publication'::regclass,4,'publication_id','uuid'::regtype,-1,true),('public.hidra_custody_product_publication'::regclass,5,'candidate_digest','varchar'::regtype,68,true),('public.hidra_custody_product_publication'::regclass,6,'payload_format','varchar'::regtype,44,true),('public.hidra_custody_product_publication'::regclass,7,'canonical_payload','bytea'::regtype,-1,true),('public.hidra_custody_product_publication'::regclass,8,'sha256','varchar'::regtype,68,true),('public.hidra_custody_product_publication'::regclass,9,'actor_binding_id','uuid'::regtype,-1,true),('public.hidra_custody_product_publication'::regclass,10,'actor_session_id','uuid'::regtype,-1,true),('public.hidra_custody_product_publication'::regclass,11,'actor_user_id','varchar'::regtype,84,true),('public.hidra_custody_product_publication'::regclass,12,'party_generation','int8'::regtype,-1,true),('public.hidra_custody_product_publication'::regclass,13,'sampled_at','timestamptz'::regtype,-1,true),('public.hidra_custody_product_publication'::regclass,14,'valid_until','timestamptz'::regtype,-1,true),('public.hidra_custody_product_publication'::regclass,15,'backend_pid','int4'::regtype,-1,true),('public.hidra_custody_product_publication'::regclass,16,'transaction_id','int8'::regtype,-1,true),('public.hidra_custody_product_publication'::regclass,17,'created_at','timestamptz'::regtype,-1,true),('public.hidra_custody_product_publication_event'::regclass,1,'product_draft_id','uuid'::regtype,-1,true),('public.hidra_custody_product_publication_event'::regclass,2,'company_party_id','varchar'::regtype,84,true),('public.hidra_custody_product_publication_event'::regclass,3,'catalog_product_id','varchar'::regtype,84,true),('public.hidra_custody_product_publication_event'::regclass,4,'event_id','uuid'::regtype,-1,true),('public.hidra_custody_product_publication_event'::regclass,5,'epoch','int8'::regtype,-1,true),('public.hidra_custody_product_publication_event'::regclass,6,'action','varchar'::regtype,14,true),('public.hidra_custody_product_publication_event'::regclass,7,'prior_publication_id','uuid'::regtype,-1,false),('public.hidra_custody_product_publication_event'::regclass,8,'publication_id','uuid'::regtype,-1,true),('public.hidra_custody_product_publication_event'::regclass,9,'state','varchar'::regtype,13,true),('public.hidra_custody_product_publication_event'::regclass,10,'request_id','uuid'::regtype,-1,true),('public.hidra_custody_product_publication_event'::regclass,11,'command_sha256','varchar'::regtype,68,true),('public.hidra_custody_product_publication_event'::regclass,12,'effective_at','timestamptz'::regtype,-1,true),('public.hidra_custody_product_publication_event'::regclass,13,'payload_format','varchar'::regtype,44,true),('public.hidra_custody_product_publication_event'::regclass,14,'canonical_payload','bytea'::regtype,-1,true),('public.hidra_custody_product_publication_event'::regclass,15,'sha256','varchar'::regtype,68,true),('public.hidra_custody_product_publication_event'::regclass,16,'actor_binding_id','uuid'::regtype,-1,true),('public.hidra_custody_product_publication_event'::regclass,17,'actor_session_id','uuid'::regtype,-1,true),('public.hidra_custody_product_publication_event'::regclass,18,'actor_user_id','varchar'::regtype,84,true),('public.hidra_custody_product_publication_event'::regclass,19,'party_generation','int8'::regtype,-1,true),('public.hidra_custody_product_publication_event'::regclass,20,'sampled_at','timestamptz'::regtype,-1,true),('public.hidra_custody_product_publication_event'::regclass,21,'valid_until','timestamptz'::regtype,-1,true),('public.hidra_custody_product_publication_event'::regclass,22,'backend_pid','int4'::regtype,-1,true),('public.hidra_custody_product_publication_event'::regclass,23,'transaction_id','int8'::regtype,-1,true),('public.hidra_custody_product_publication_event'::regclass,24,'created_at','timestamptz'::regtype,-1,true),('public.hidra_custody_product_publication_head'::regclass,1,'product_draft_id','uuid'::regtype,-1,true),('public.hidra_custody_product_publication_head'::regclass,2,'company_party_id','varchar'::regtype,84,true),('public.hidra_custody_product_publication_head'::regclass,3,'catalog_product_id','varchar'::regtype,84,true),('public.hidra_custody_product_publication_head'::regclass,4,'publication_id','uuid'::regtype,-1,true),('public.hidra_custody_product_publication_head'::regclass,5,'epoch','int8'::regtype,-1,true),('public.hidra_custody_product_publication_head'::regclass,6,'state','varchar'::regtype,13,true),('public.hidra_custody_product_publication_head'::regclass,7,'event_id','uuid'::regtype,-1,true),('public.hidra_custody_product_publication_head'::regclass,8,'effective_at','timestamptz'::regtype,-1,true),('public.hidra_custody_product_publication_head'::regclass,9,'actor_binding_id','uuid'::regtype,-1,true),('public.hidra_custody_product_publication_head'::regclass,10,'actor_session_id','uuid'::regtype,-1,true),('public.hidra_custody_product_publication_head'::regclass,11,'actor_user_id','varchar'::regtype,84,true),('public.hidra_custody_product_publication_head'::regclass,12,'party_generation','int8'::regtype,-1,true),('public.hidra_custody_product_publication_head'::regclass,13,'sampled_at','timestamptz'::regtype,-1,true),('public.hidra_custody_product_publication_head'::regclass,14,'valid_until','timestamptz'::regtype,-1,true),('public.hidra_custody_product_publication_head'::regclass,15,'backend_pid','int4'::regtype,-1,true),('public.hidra_custody_product_publication_head'::regclass,16,'transaction_id','int8'::regtype,-1,true),('public.hidra_custody_product_publication_head'::regclass,17,'created_at','timestamptz'::regtype,-1,true),('public.hidra_custody_product_publication_request'::regclass,1,'product_draft_id','uuid'::regtype,-1,true),('public.hidra_custody_product_publication_request'::regclass,2,'company_party_id','varchar'::regtype,84,true),('public.hidra_custody_product_publication_request'::regclass,3,'catalog_product_id','varchar'::regtype,84,true),('public.hidra_custody_product_publication_request'::regclass,4,'request_id','uuid'::regtype,-1,true),('public.hidra_custody_product_publication_request'::regclass,5,'kind','varchar'::regtype,14,true),('public.hidra_custody_product_publication_request'::regclass,6,'command_sha256','varchar'::regtype,68,true),('public.hidra_custody_product_publication_request'::regclass,7,'candidate_digest','varchar'::regtype,68,true),('public.hidra_custody_product_publication_request'::regclass,8,'event_id','uuid'::regtype,-1,false),('public.hidra_custody_product_publication_request'::regclass,9,'payload_format','varchar'::regtype,44,true),('public.hidra_custody_product_publication_request'::regclass,10,'canonical_payload','bytea'::regtype,-1,true),('public.hidra_custody_product_publication_request'::regclass,11,'sha256','varchar'::regtype,68,true),('public.hidra_custody_product_publication_request'::regclass,12,'actor_binding_id','uuid'::regtype,-1,true),('public.hidra_custody_product_publication_request'::regclass,13,'actor_session_id','uuid'::regtype,-1,true),('public.hidra_custody_product_publication_request'::regclass,14,'actor_user_id','varchar'::regtype,84,true),('public.hidra_custody_product_publication_request'::regclass,15,'party_generation','int8'::regtype,-1,true),('public.hidra_custody_product_publication_request'::regclass,16,'sampled_at','timestamptz'::regtype,-1,true),('public.hidra_custody_product_publication_request'::regclass,17,'valid_until','timestamptz'::regtype,-1,true),('public.hidra_custody_product_publication_request'::regclass,18,'backend_pid','int4'::regtype,-1,true),('public.hidra_custody_product_publication_request'::regclass,19,'transaction_id','int8'::regtype,-1,true),('public.hidra_custody_product_publication_request'::regclass,20,'created_at','timestamptz'::regtype,-1,true)) expected(relation,position,name,type,typmod,required) LEFT JOIN pg_attribute a ON a.attrelid=expected.relation AND a.attnum=expected.position WHERE a.attname IS DISTINCT FROM expected.name OR a.atttypid IS DISTINCT FROM expected.type OR a.atttypmod IS DISTINCT FROM expected.typmod OR a.attnotnull IS DISTINCT FROM expected.required OR a.attisdropped OR a.attidentity<>'' OR a.attgenerated<>'')
        """;
    private final JdbcTemplate jdbc;private final ProtectedTransactionExecutor executor;
    private final ProductRevisionPublicationCodec codec=new ProductRevisionPublicationCodec();
    public JdbcProductRevisionPublicationRepositoryAdapter(JdbcTemplate jdbc,ProtectedTransactionExecutor executor){this.jdbc=Objects.requireNonNull(jdbc);this.executor=Objects.requireNonNull(executor);}
    private record Pin(String relation,String column,Object id,String sha){}
    private final class Ticket implements Admission,TransactionSynchronization {
        final JdbcProductRevisionPublicationRepositoryAdapter owner=JdbcProductRevisionPublicationRepositoryAdapter.this;
        final Command command;final Scope scope;final Thread thread=Thread.currentThread();final ConnectionHolder holder;final Connection connection;
        final int pid;final long xid;final String role;final List<Pin> pins=new ArrayList<>();
        boolean active=true,rejected,authorized,applied,replayed,eligibility;String actor,commandDigest,digest,image;Envelope envelope;
        ProductRevisionPublication publication;Receipt receipt;Head head,selected;List<Event> history=List.of();String nextCursor;
        Component metadata,supplied;long suppliedMetadata;String classification;Facts facts;
        List<ProtectedRelationFence> fences;Instant now;
        Ticket(Command c,Scope scope,ConnectionHolder h,int pid,long xid,String role){command=c;this.scope=scope;holder=h;connection=h.getConnection();this.pid=pid;this.xid=xid;this.role=role;}
        @Override public void suspend(){active=false;rejected=true;holder.setRollbackOnly();}
        @Override public void beforeCommit(boolean readOnly){if(!active || rejected || readOnly || thread!=Thread.currentThread())throw fail(Reason.UNAVAILABLE);}
        @Override public void afterCompletion(int status){active=false;}
        @Override public String toString(){return "Admission[opaque product publication storage]";}
    }
    private static Failure fail(Reason reason){return new Failure(reason);}
    private ConnectionHolder physical(){
        if(!StackWalker.getInstance().walk(f->f.anyMatch(x->x.getClassName().equals("dz.sh.hidra.modules.custody.application.service.ProductRevisionPublicationApplicationService"))))throw fail(Reason.UNAVAILABLE);
        if(!TransactionSynchronizationManager.isActualTransactionActive() || TransactionSynchronizationManager.isCurrentTransactionReadOnly()
            || !(TransactionSynchronizationManager.getResource(jdbc.getDataSource()) instanceof ConnectionHolder h) || h.isRollbackOnly())throw fail(Reason.UNAVAILABLE);return h;
    }
    private Ticket ticket(Admission a){if(!(a instanceof JdbcProductRevisionPublicationRepositoryAdapter.Ticket t) || t.owner!=this)throw fail(Reason.UNAVAILABLE);
        try{if(t.thread!=Thread.currentThread() || !t.active || t.rejected || physical()!=t.holder || t.holder.getConnection()!=t.connection)throw fail(Reason.UNAVAILABLE);return t;}
        catch(RuntimeException e){t.rejected=true;t.holder.setRollbackOnly();throw e;}}
    private boolean safe(){
        var roles=executor.publicationDatabaseIdentity();
        if(!Boolean.TRUE.equals(jdbc.queryForObject(CATALOG,Boolean.class)) || !Boolean.TRUE.equals(jdbc.queryForObject(FLUID_CATALOG,Boolean.class)) || !Boolean.TRUE.equals(jdbc.queryForObject("SELECT "+NEW_SCHEMA,Boolean.class)))return false;
        String tables=String.join(",",RELATIONS.subList(6,11).stream().map(r->"'"+r+"'::regclass").toList());
        return Boolean.TRUE.equals(jdbc.queryForObject("SELECT current_user=? AND session_user=? AND ?<>? AND NOT pg_has_role(?::name,?::name,'MEMBER')"
            +" AND (SELECT count(*)=5 AND bool_and(c.relkind='r' AND c.relpersistence='p' AND NOT c.relrowsecurity AND NOT c.relforcerowsecurity AND NOT c.relispartition"
            +" AND NOT pg_has_role(current_user,c.relowner,'MEMBER') AND has_table_privilege(current_user,c.oid,'SELECT') AND has_table_privilege(current_user,c.oid,'INSERT') AND has_table_privilege(current_user,c.oid,'UPDATE')"
            +" AND NOT has_table_privilege(current_user,c.oid,'DELETE,TRUNCATE,TRIGGER') AND NOT pg_has_role(?::name,c.relowner,'MEMBER')"
            +" AND has_table_privilege(?::name,c.oid,'SELECT') AND NOT has_table_privilege(?::name,c.oid,'INSERT,UPDATE,DELETE,TRUNCATE,TRIGGER')"
            +" AND NOT has_any_column_privilege(?::name,c.oid,'INSERT,UPDATE,REFERENCES') AND NOT EXISTS(SELECT 1 FROM pg_inherits h WHERE h.inhrelid=c.oid OR h.inhparent=c.oid)) FROM pg_class c WHERE c.oid IN ("+tables+"))"
            +" AND (SELECT count(*)=15 AND bool_and(t.tgenabled='O' AND t.tgqual IS NULL AND t.tgnargs=0 AND t.tgparentid=0 AND cardinality(t.tgattr::smallint[])=0 AND t.tgconstraint=0"
            +" AND (t.tgname,t.tgrelid,t.tgtype,t.tgfoid) IN (('custody_publication_candidate_admit','public.hidra_custody_product_publication_candidate'::regclass,7,'public.hidra_custody_publication_admit()'::regprocedure),('custody_publication_candidate_immutable','public.hidra_custody_product_publication_candidate'::regclass,26,'public.hidra_custody_publication_immutable()'::regprocedure),('custody_publication_candidate_no_truncate','public.hidra_custody_product_publication_candidate'::regclass,34,'public.hidra_custody_publication_immutable()'::regprocedure),('custody_publication_snapshot_admit','public.hidra_custody_product_publication'::regclass,7,'public.hidra_custody_publication_admit()'::regprocedure),('custody_publication_snapshot_immutable','public.hidra_custody_product_publication'::regclass,26,'public.hidra_custody_publication_immutable()'::regprocedure),('custody_publication_snapshot_no_truncate','public.hidra_custody_product_publication'::regclass,34,'public.hidra_custody_publication_immutable()'::regprocedure),('custody_publication_head_admit','public.hidra_custody_product_publication_head'::regclass,23,'public.hidra_custody_publication_admit()'::regprocedure),('custody_publication_head_immutable','public.hidra_custody_product_publication_head'::regclass,10,'public.hidra_custody_publication_immutable()'::regprocedure),('custody_publication_head_no_truncate','public.hidra_custody_product_publication_head'::regclass,34,'public.hidra_custody_publication_immutable()'::regprocedure),('custody_publication_event_admit','public.hidra_custody_product_publication_event'::regclass,7,'public.hidra_custody_publication_admit()'::regprocedure),('custody_publication_event_immutable','public.hidra_custody_product_publication_event'::regclass,26,'public.hidra_custody_publication_immutable()'::regprocedure),('custody_publication_event_no_truncate','public.hidra_custody_product_publication_event'::regclass,34,'public.hidra_custody_publication_immutable()'::regprocedure),('custody_publication_request_admit','public.hidra_custody_product_publication_request'::regclass,7,'public.hidra_custody_publication_admit()'::regprocedure),('custody_publication_request_immutable','public.hidra_custody_product_publication_request'::regclass,26,'public.hidra_custody_publication_immutable()'::regprocedure),('custody_publication_request_no_truncate','public.hidra_custody_product_publication_request'::regclass,34,'public.hidra_custody_publication_immutable()'::regprocedure)) AND t.tgfoid IN ('public.hidra_custody_publication_admit()'::regprocedure,'public.hidra_custody_publication_immutable()'::regprocedure)) FROM pg_trigger t WHERE t.tgrelid IN ("+tables+") AND NOT t.tgisinternal) AND "+NEW_FUNCTIONS,
            Boolean.class,roles.publisherUser(),roles.publisherUser(),roles.ordinaryUser(),roles.publisherUser(),roles.ordinaryUser(),roles.publisherUser(),roles.ordinaryUser(),roles.ordinaryUser(),roles.ordinaryUser(),roles.ordinaryUser()));
    }
    @Override @Transactional(propagation=Propagation.MANDATORY)
    public Admission acquire(Command c){try{
        var h=physical();for(String r:new TreeSet<>(RELATIONS))jdbc.execute("LOCK TABLE "+r+" IN SHARE ROW EXCLUSIVE MODE NOWAIT");if(!safe())throw fail(Reason.UNAVAILABLE);
        List<Map<String,Object>> rows;
        if(c.kind()==Kind.READ_CANDIDATE_FOR_WORKFLOW)rows=jdbc.queryForList("SELECT d.id,d.company_party_id,d.catalog_product_id FROM "+PREFIX+"_candidate p JOIN public.hidra_custody_product_engineering_draft d ON d.id=p.product_draft_id WHERE p.candidate_digest=?",c.candidateDigest());
        else rows=jdbc.queryForList("SELECT id,company_party_id,catalog_product_id FROM public.hidra_custody_product_engineering_draft WHERE id=? AND company_party_id=?",c.productDraftId(),c.companyPartyId());
        if(rows.size()!=1)throw fail(Reason.DENIED);var r=rows.getFirst();var scope=new Scope((UUID)r.get("id"),(String)r.get("company_party_id"),(String)r.get("catalog_product_id"));
        var t=jdbc.queryForObject("SELECT pg_backend_pid(),txid_current(),current_user",(rs,n)->new Ticket(c,scope,h,rs.getInt(1),rs.getLong(2),rs.getString(3)));if(t==null)throw fail(Reason.UNAVAILABLE);
        TransactionSynchronizationManager.registerSynchronization(t);return t;
    }catch(RuntimeException e){executor.rejectAdmission();throw e;}}
    @Override public Scope scope(Admission a){return ticket(a).scope;}
    private Instant now(){return jdbc.queryForObject("SELECT clock_timestamp()",Timestamp.class).toInstant();}
    private Head head(Ticket t){var rows=jdbc.query("SELECT publication_id,epoch,state,event_id,effective_at FROM "+PREFIX+"_head WHERE product_draft_id=?",(r,n)->new Head(t.scope,r.getObject(1,UUID.class),r.getLong(2),State.valueOf(r.getString(3)),r.getObject(4,UUID.class),r.getTimestamp(5).toInstant()),t.scope.productDraftId());return rows.isEmpty()?null:rows.getFirst();}
    private byte[] bytes(Ticket t,String relation,String column,Object id){
        var rows=jdbc.query("SELECT canonical_payload,sha256,payload_format FROM "+relation+" WHERE "+column+"=? AND product_draft_id=? AND company_party_id=? AND catalog_product_id=?",(r,n)->{
            byte[] b=r.getBytes(1);if(!ProductRevisionPublicationCodec.FORMAT.equals(r.getString(3)) || !ProductRevisionPublication.hash(b).equals(r.getString(2)))throw fail(Reason.UNAVAILABLE);
            t.pins.add(new Pin(relation,column,id,r.getString(2)));return b;},id,t.scope.productDraftId(),t.scope.companyPartyId(),t.scope.catalogProductId());
        if(rows.size()!=1)throw fail(Reason.DENIED);return rows.getFirst();
    }
    private Envelope candidate(Ticket t,String digest){var e=codec.decode(bytes(t,PREFIX+"_candidate","candidate_digest",digest));if(!e.scope().equals(t.scope))throw fail(Reason.UNAVAILABLE);return e;}
    private ProductRevisionPublication publication(Ticket t,UUID id){var p=codec.publication(bytes(t,PREFIX,"publication_id",id));
        if(!id.equals(p.publicationId()) || !p.envelope().scope().equals(t.scope) || !p.approval().digest().equals(ProductRevisionPublication.hash(codec.encode(p.envelope()))))throw fail(Reason.UNAVAILABLE);return p;}
    private Event event(Ticket t,UUID id){var e=codec.event(bytes(t,PREFIX+"_event","event_id",id));if(!e.eventId().equals(id) || !e.scope().equals(t.scope))throw fail(Reason.UNAVAILABLE);return e;}
    @Override public void authorized(Admission admission,String actor){try{
        var t=ticket(admission);if(t.authorized)throw fail(Reason.UNAVAILABLE);t.actor=actor;t.authorized=true;t.now=now();t.head=head(t);t.commandDigest=codec.commandDigest(t.command);var c=t.command;
        if(c.asOf()!=null && c.asOf().isAfter(t.now))throw fail(Reason.INVALID_REQUEST);
        if(c.mutating() || c.kind()==Kind.READ_RECEIPT){
            var prior=jdbc.queryForList("SELECT product_draft_id FROM "+PREFIX+"_request WHERE request_id=?",c.requestId());
            if(!prior.isEmpty()){
                if(!t.scope.productDraftId().equals(prior.getFirst().get("product_draft_id")))throw fail(Reason.CONFLICT);
                t.receipt=codec.receipt(bytes(t,PREFIX+"_request","request_id",c.requestId()));
                if(!t.receipt.scope().equals(t.scope) || !t.receipt.actorId().equals(actor))throw fail(Reason.CONFLICT);
                if(c.mutating())try{t.receipt.requireReplay(t.scope,actor,c.requestId(),t.commandDigest);}catch(IllegalArgumentException e){throw fail(Reason.CONFLICT);}
                t.replayed=c.mutating();t.digest=t.receipt.candidateDigest();t.envelope=candidate(t,t.digest);
                if(t.receipt.event()!=null)t.publication=publication(t,t.receipt.event().publicationId());return;
            }else if(c.kind()==Kind.READ_RECEIPT)throw fail(Reason.DENIED);
        }
        switch(c.kind()){
            case PREPARE -> loadSources(t);
            case PUBLISH,READ_CANDIDATE,READ_CANDIDATE_FOR_WORKFLOW -> {t.digest=c.candidateDigest();t.envelope=candidate(t,t.digest);if(c.kind()==Kind.PUBLISH)loadSources(t);}
            case WITHDRAW,REACTIVATE,READ_EXACT -> {t.publication=publication(t,c.publicationId());t.envelope=t.publication.envelope();t.digest=ProductRevisionPublication.hash(codec.encode(t.envelope));}
            case READ_CURRENT,ELIGIBLE_CURRENT -> {t.selected=t.head;loadSelection(t);}
            case READ_EFFECTIVE,ELIGIBLE_EFFECTIVE -> {var ids=jdbc.queryForList("SELECT event_id FROM "+PREFIX+"_event WHERE product_draft_id=? AND effective_at<=? ORDER BY epoch DESC LIMIT 1",t.scope.productDraftId(),Timestamp.from(c.asOf()));t.selected=ids.isEmpty()?null:event(t,(UUID)ids.getFirst().get("event_id")).head();loadSelection(t);}
            case READ_HISTORY -> history(t);
            default -> throw fail(Reason.INVALID_REQUEST);
        }
    }catch(RuntimeException e){executor.rejectAdmission();throw e;}}
    private void loadSelection(Ticket t){if(t.selected!=null && !(Set.of(Kind.READ_EFFECTIVE,Kind.ELIGIBLE_EFFECTIVE).contains(t.command.kind()) && t.selected.state()==State.WITHDRAWN)){t.publication=publication(t,t.selected.publicationId());t.envelope=t.publication.envelope();t.digest=ProductRevisionPublication.hash(codec.encode(t.envelope));}}
    private void history(Ticket t){
        var c=t.command;long high=jdbc.queryForObject("SELECT COALESCE(max(epoch),0) FROM "+PREFIX+"_event WHERE product_draft_id=? AND effective_at<=?",Long.class,t.scope.productDraftId(),Timestamp.from(c.asOf()));
        long before;try{before=Math.addExact(high,1);}catch(ArithmeticException e){throw fail(Reason.CONFLICT);}
        if(c.cursor()!=null){Cursor cur;try{cur=codec.cursor(c.cursor());}catch(RuntimeException e){throw fail(Reason.INVALID_REQUEST);}
            if(!cur.scope().equals(t.scope) || !cur.asOf().equals(c.asOf()) || cur.highWaterEpoch()>high)throw fail(Reason.INVALID_REQUEST);high=cur.highWaterEpoch();before=cur.beforeEpoch();}
        var ids=jdbc.queryForList("SELECT event_id FROM "+PREFIX+"_event WHERE product_draft_id=? AND effective_at<=? AND epoch<=? AND epoch<? ORDER BY epoch DESC LIMIT ?",t.scope.productDraftId(),Timestamp.from(c.asOf()),high,before,c.limit()+1);
        var list=new ArrayList<Event>();for(int n=0;n<Math.min(c.limit(),ids.size());n++)list.add(event(t,(UUID)ids.get(n).get("event_id")));t.history=List.copyOf(list);
        if(ids.size()>c.limit())t.nextCursor=codec.cursor(new Cursor(t.scope,c.asOf(),high,list.getLast().epoch()));
    }
    private void loadSources(Ticket t){
        var m=jdbc.queryForList("SELECT version,payload_format,canonical_payload,sha256,classification FROM public.hidra_custody_product_engineering_draft_version WHERE draft_id=? ORDER BY version DESC LIMIT 1",t.scope.productDraftId());
        var f=jdbc.queryForList("SELECT version,product_metadata_version,payload_format,canonical_payload,sha256,classification FROM public.hidra_custody_supplied_fluid_property_version WHERE product_draft_id=? ORDER BY version DESC LIMIT 1",t.scope.productDraftId());
        if(m.size()!=1 || f.size()!=1)throw fail(Reason.CONFLICT);var mr=m.getFirst();var fr=f.getFirst();
        t.metadata=new Component(((Number)mr.get("version")).longValue(),(String)mr.get("payload_format"),(String)mr.get("sha256"),Base64.getEncoder().encodeToString((byte[])mr.get("canonical_payload")));
        t.supplied=new Component(((Number)fr.get("version")).longValue(),(String)fr.get("payload_format"),(String)fr.get("sha256"),Base64.getEncoder().encodeToString((byte[])fr.get("canonical_payload")));
        t.suppliedMetadata=((Number)fr.get("product_metadata_version")).longValue();t.classification=(String)fr.get("classification");
        if(t.suppliedMetadata!=t.metadata.version() || !Objects.equals(t.classification,mr.get("classification")))throw fail(Reason.CONFLICT);
        if(t.command.kind()==Kind.PREPARE && (t.command.metadataVersion()!=t.metadata.version() || t.command.suppliedVersion()!=t.supplied.version()))throw fail(Reason.CONFLICT);
        if(t.command.kind()==Kind.PUBLISH && (!t.envelope.metadata().equals(t.metadata) || !t.envelope.supplied().equals(t.supplied)))throw fail(Reason.CONFLICT);
        t.facts=new SuppliedFluidPropertyRevisionCodec().decode(t.supplied.bytes());
    }
    @Override public boolean replayed(Admission a){return ticket(a).replayed;}
    @Override public boolean needsEligibility(Admission a){var t=ticket(a);if(!t.authorized)throw fail(Reason.UNAVAILABLE);return !t.replayed && Set.of(Kind.PREPARE,Kind.PUBLISH,Kind.REACTIVATE,Kind.ELIGIBLE_CURRENT,Kind.ELIGIBLE_EFFECTIVE).contains(t.command.kind()) && (t.command.mutating() || t.selected!=null && t.selected.state()==State.ACTIVE);}
    @Override public void eligible(Admission a){var t=ticket(a);if(!needsEligibility(t) || t.eligibility)throw fail(Reason.UNAVAILABLE);
        if(!Boolean.TRUE.equals(jdbc.queryForObject("SELECT EXISTS(SELECT 1 FROM public.hidra_custody_catalog_entry c JOIN public.hidra_custody_engineering_product_policy p ON p.catalog_product_id=c.id WHERE c.id=? AND c.active AND p.active)",Boolean.class,t.scope.catalogProductId())))throw fail(Reason.DENIED);t.eligibility=true;}
    @Override public Facts facts(Admission a){var t=ticket(a);if(!t.eligibility)throw fail(Reason.UNAVAILABLE);return t.facts!=null?t.facts:new SuppliedFluidPropertyRevisionCodec().decode(t.envelope.supplied().bytes());}
    @Override public String targetDigest(Admission a){return ticket(a).digest;}
    @Override public Selection selection(Admission a){var t=ticket(a);if(t.command.approval()!=null)return t.command.approval();
        if(Set.of(Kind.ELIGIBLE_CURRENT,Kind.ELIGIBLE_EFFECTIVE).contains(t.command.kind()) && t.selected!=null){var approval=event(t,t.selected.eventId()).approval();return new Selection(approval.instanceId(),approval.taskId(),approval.actionId());}return null;}
    @Override public List<ProtectedRelationFence> finalFences(Admission a){ticket(a);return RELATIONS.stream().map(r->new ProtectedRelationFence(r,ProtectedRelationFence.Mode.SHARE_ROW_EXCLUSIVE)).toList();}
    @Override public boolean mutation(Admission a){var t=ticket(a);if(!t.authorized)throw fail(Reason.UNAVAILABLE);return t.command.mutating() && !t.replayed;}
    @Override public Set<String> mutationSql(){return SQL;}
    private List<Object> audit(Contribution c){return Arrays.asList(c.bindingId(),c.sessionId(),c.userId(),c.partyGeneration(),Timestamp.from(c.sampledAt()),Timestamp.from(c.validUntil()),c.backendPid(),c.transactionId());}
    private static void add(List<Object> args,Object... values){args.addAll(Arrays.asList(values));}
    private void update(String sql,List<Object> args){if(jdbc.update(sql,args.toArray())!=1)throw fail(Reason.CONFLICT);}
    private List<Object> scopeArgs(Ticket t){return new ArrayList<>(Arrays.asList(t.scope.productDraftId(),t.scope.companyPartyId(),t.scope.catalogProductId()));}
    private void payload(List<Object> args,byte[] bytes){add(args,ProductRevisionPublicationCodec.FORMAT,bytes,ProductRevisionPublication.hash(bytes));}
    @Override public Result apply(Admission admission,Contribution c){try{
        var t=ticket(admission);if(!t.authorized || t.applied || c==null || c.backendPid()!=t.pid || c.transactionId()!=t.xid || !c.userId().equals(t.actor)
            || !c.request().companyPartyId().equals(t.scope.companyPartyId()) || !c.request().catalogProductId().equals(t.scope.catalogProductId()))throw fail(Reason.UNAVAILABLE);
        if(needsEligibility(t)){
            if(!t.eligibility || c.policy()==null)throw fail(Reason.UNAVAILABLE);
            if(t.command.kind()==Kind.PREPARE){t.envelope=new Envelope(t.scope,t.metadata,t.supplied,t.suppliedMetadata,t.classification,ProductRevisionPublicationCodec.diagnostics(t.metadata,t.supplied),c.units(),c.policy());t.digest=ProductRevisionPublication.hash(codec.encode(t.envelope));}
            else if(!t.envelope.policy().equals(c.policy()) || !t.envelope.units().equals(c.units()) || c.approval()==null || !t.digest.equals(c.approval().digest()))throw fail(Reason.CONFLICT);
        }
        if(mutation(t)){
            Instant at=now();var command=t.command;Event event=null;
            if(command.kind()==Kind.PREPARE){var existing=jdbc.queryForList("SELECT candidate_digest FROM "+PREFIX+"_candidate WHERE candidate_digest=?",t.digest);
                if(existing.isEmpty()){var args=scopeArgs(t);add(args,t.digest,t.metadata.version(),t.supplied.version());payload(args,codec.encode(t.envelope));args.addAll(audit(c));update(CANDIDATE_INSERT,args);}
                else if(!candidate(t,t.digest).equals(t.envelope))throw fail(Reason.CONFLICT);
            }else{
                UUID publicationId=command.publicationId();
                if(command.kind()==Kind.PUBLISH){publicationId=UUID.randomUUID();t.publication=new ProductRevisionPublication(publicationId,t.envelope,c.approval());var args=scopeArgs(t);add(args,publicationId,t.digest);payload(args,codec.encode(t.publication));args.addAll(audit(c));update(PUBLICATION_INSERT,args);}
                try{event=ProductRevisionPublication.transition(t.scope,t.head,command.expectedEpoch(),command.expectedPublicationId(),Action.valueOf(command.kind().name()),publicationId,UUID.randomUUID(),command.requestId(),t.commandDigest,at,c.approval());}
                catch(IllegalArgumentException e){throw fail(Reason.CONFLICT);}
                var args=scopeArgs(t);add(args,event.eventId(),event.epoch(),event.action().name(),event.priorPublicationId(),event.publicationId(),event.state().name(),event.requestId(),event.commandSha256(),Timestamp.from(at));payload(args,codec.encode(event));args.addAll(audit(c));update(EVENT_INSERT,args);
                if(t.head==null){args=scopeArgs(t);add(args,event.publicationId(),event.epoch(),event.state().name(),event.eventId(),Timestamp.from(at));args.addAll(audit(c));update(HEAD_INSERT,args);}
                else{args=new ArrayList<>(Arrays.asList(event.publicationId(),event.epoch(),event.state().name(),event.eventId(),Timestamp.from(at)));args.addAll(audit(c));add(args,t.scope.productDraftId(),command.expectedEpoch(),command.expectedPublicationId());update(HEAD_UPDATE,args);}
                t.head=event.head();t.selected=t.head;
            }
            t.receipt=new Receipt(t.scope,c.userId(),command.requestId(),t.commandDigest,command.kind().name(),t.digest,event,at);
            var args=scopeArgs(t);add(args,command.requestId(),command.kind().name(),t.commandDigest,t.digest,event==null?null:event.eventId());payload(args,codec.encode(t.receipt));args.addAll(audit(c));update(REQUEST_INSERT,args);
            // Pin the actual persisted bytes and audit before leaving provisional mode.
        }
        t.applied=true;
        return new Result(t.envelope,t.digest,t.publication,t.head,t.selected,t.receipt,t.history,t.nextCursor,t.replayed,needsEligibility(t) && t.eligibility);
    }catch(RuntimeException e){executor.rejectAdmission();throw e;}}
    @Override public void flush(){physical();}
    private static String imageSql(List<ProtectedRelationFence> fences){
        String values=String.join(",",fences.stream().map(f->"'"+f.relation()+"'::regclass").toList());
        return "SELECT jsonb_build_object('relations',(SELECT jsonb_agg(jsonb_build_object('oid',c.oid,'kind',c.relkind,'persistence',c.relpersistence,'owner',c.relowner,'acl',c.relacl::text,'rls',c.relrowsecurity,'force',c.relforcerowsecurity,'partition',c.relispartition) ORDER BY c.oid) FROM pg_class c WHERE c.oid IN ("+values+")),"
            +"'columns',(SELECT jsonb_agg(to_jsonb(a) ORDER BY a.attrelid,a.attnum) FROM pg_attribute a WHERE a.attrelid IN ("+values+") AND a.attnum>0),"
            +"'constraints',(SELECT jsonb_agg(to_jsonb(k) ORDER BY k.oid) FROM pg_constraint k WHERE k.conrelid IN ("+values+")),"
            +"'triggers',(SELECT jsonb_agg(jsonb_build_object('trigger',to_jsonb(t),'function',pg_get_functiondef(t.tgfoid),'acl',p.proacl::text,'owner',p.proowner) ORDER BY t.oid) FROM pg_trigger t JOIN pg_proc p ON p.oid=t.tgfoid WHERE t.tgrelid IN ("+values+") AND NOT t.tgisinternal),"
            +"'roles',(SELECT jsonb_agg(to_jsonb(r) ORDER BY r.oid) FROM pg_roles r),'members',(SELECT jsonb_agg(to_jsonb(m) ORDER BY m.roleid,m.member) FROM pg_auth_members m))::text";
    }
    /** Freeze physical images after all owners acquired their exact relation fences. */
    public void captureImages(Admission a,List<ProtectedRelationFence> fences){var t=ticket(a);
        if(!t.applied || t.image!=null || fences==null || fences.size()!=(needsEligibility(t)?50:37) || !new HashSet<>(fences).containsAll(finalFences(t)))throw fail(Reason.UNAVAILABLE);
        t.fences=List.copyOf(fences);t.image=jdbc.queryForObject(imageSql(fences),String.class);
    }
    @Override public boolean finalGuard(Admission a,Contribution c,List<ProtectedRelationFence> fences){var t=ticket(a);
        if(!t.applied || t.image==null || !t.fences.equals(fences) || c==null || fences.stream().map(ProtectedRelationFence::relation).distinct().count()!=fences.size())throw fail(Reason.UNAVAILABLE);
        String guard="WITH sampled_clock AS MATERIALIZED (SELECT clock_timestamp() AS t) SELECT (SELECT t FROM sampled_clock)>=?::timestamptz AND (SELECT t FROM sampled_clock)<?::timestamptz"
            +" AND pg_backend_pid()=? AND txid_current()=? AND current_user=? AND session_user=?"
            +" AND EXISTS(SELECT 1 FROM public.hidra_party_party p JOIN public.hidra_party_eligibility_generation g ON g.party_id=p.id WHERE p.id=? AND p.status='ACTIVE' AND g.generation=? AND g.present)"
            +" AND EXISTS(SELECT 1 FROM public.hidra_custody_product_engineering_draft d WHERE d.id=? AND d.company_party_id=? AND d.catalog_product_id=?)"
            +" AND ("+imageSql(fences)+")=?";
        var args=new ArrayList<Object>(Arrays.asList(Timestamp.from(c.sampledAt()),Timestamp.from(c.validUntil()),t.pid,t.xid,t.role,t.role,t.scope.companyPartyId(),c.partyGeneration(),t.scope.productDraftId(),t.scope.companyPartyId(),t.scope.catalogProductId(),t.image));
        for(var pin:t.pins){guard+=" AND EXISTS(SELECT 1 FROM "+pin.relation()+" p WHERE p."+pin.column()+"=? AND p.product_draft_id=? AND p.company_party_id=? AND p.catalog_product_id=? AND p.sha256=? AND p.sha256=encode(sha256(p.canonical_payload),'hex'))";add(args,pin.id(),t.scope.productDraftId(),t.scope.companyPartyId(),t.scope.catalogProductId(),pin.sha());}
        if(needsEligibility(t)){guard+=" AND EXISTS(SELECT 1 FROM public.hidra_custody_catalog_entry c JOIN public.hidra_custody_engineering_product_policy p ON p.catalog_product_id=c.id WHERE c.id=? AND c.active AND p.active)";add(args,t.scope.catalogProductId());}
        if(t.metadata!=null){guard+=" AND EXISTS(SELECT 1 FROM public.hidra_custody_product_engineering_draft_version m WHERE m.draft_id=? AND m.version=? AND m.sha256=? AND m.canonical_payload=? AND NOT EXISTS(SELECT 1 FROM public.hidra_custody_product_engineering_draft_version n WHERE n.draft_id=m.draft_id AND n.version>m.version))"
            +" AND EXISTS(SELECT 1 FROM public.hidra_custody_supplied_fluid_property_version f WHERE f.product_draft_id=? AND f.version=? AND f.sha256=? AND f.canonical_payload=? AND f.product_metadata_version=? AND NOT EXISTS(SELECT 1 FROM public.hidra_custody_supplied_fluid_property_version n WHERE n.product_draft_id=f.product_draft_id AND n.version>f.version))";
            add(args,t.scope.productDraftId(),t.metadata.version(),t.metadata.sha256(),t.metadata.bytes(),t.scope.productDraftId(),t.supplied.version(),t.supplied.sha256(),t.supplied.bytes(),t.metadata.version());}
        if(t.envelope!=null){guard+=" AND EXISTS(SELECT 1 FROM "+PREFIX+"_candidate v WHERE v.candidate_digest=? AND v.product_draft_id=? AND v.canonical_payload=? AND v.sha256=encode(sha256(v.canonical_payload),'hex'))";add(args,t.digest,t.scope.productDraftId(),codec.encode(t.envelope));}
        if(t.publication!=null){guard+=" AND EXISTS(SELECT 1 FROM "+PREFIX+" p WHERE p.publication_id=? AND p.product_draft_id=? AND p.canonical_payload=? AND p.sha256=encode(sha256(p.canonical_payload),'hex'))";add(args,t.publication.publicationId(),t.scope.productDraftId(),codec.encode(t.publication));}
        if(mutation(t) && t.receipt.event()!=null){guard+=" AND EXISTS(SELECT 1 FROM "+PREFIX+"_event e WHERE e.event_id=? AND e.canonical_payload=? AND e.sha256=encode(sha256(e.canonical_payload),'hex'))";add(args,t.receipt.event().eventId(),codec.encode(t.receipt.event()));}
        if(t.head!=null){guard+=" AND EXISTS(SELECT 1 FROM "+PREFIX+"_head h WHERE h.product_draft_id=? AND h.publication_id=? AND h.epoch=? AND h.state=? AND h.event_id=? AND h.effective_at=?)";add(args,t.scope.productDraftId(),t.head.publicationId(),t.head.epoch(),t.head.state().name(),t.head.eventId(),Timestamp.from(t.head.effectiveAt()));}
        else{guard+=" AND NOT EXISTS(SELECT 1 FROM "+PREFIX+"_head h WHERE h.product_draft_id=?)";add(args,t.scope.productDraftId());}
        if(mutation(t)){guard+=" AND EXISTS(SELECT 1 FROM "+PREFIX+"_request r WHERE r.request_id=? AND r.product_draft_id=? AND r.canonical_payload=? AND r.sha256=encode(sha256(r.canonical_payload),'hex') AND r.actor_binding_id=?::uuid AND r.actor_session_id=?::uuid AND r.actor_user_id=? AND r.party_generation=? AND r.sampled_at=? AND r.valid_until=? AND r.backend_pid=? AND r.transaction_id=?)";
            add(args,t.command.requestId(),t.scope.productDraftId(),codec.encode(t.receipt));args.addAll(audit(c));}
        guard+=" AND ("+CATALOG+") AND ("+FLUID_CATALOG+") AND "+NEW_FUNCTIONS
            +" AND NOT EXISTS(SELECT 1 FROM (VALUES "+String.join(",",Collections.nCopies(fences.size(),"(?::regclass,?::text)"))+") required(relation,mode) WHERE NOT EXISTS(SELECT 1 FROM pg_locks l WHERE l.pid=pg_backend_pid() AND l.granted AND l.locktype='relation' AND l.relation=required.relation AND l.mode=required.mode))";
        for(var f:fences){add(args,f.relation(),f.mode().catalog());}return Boolean.TRUE.equals(jdbc.queryForObject(guard,Boolean.class,args.toArray()));
    }
}
