/**
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 * @Name        : Gz2SyntheticExchangeNativeHttpPostgresIntegrationTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-11-18
 * @Type        : Class
 * @Layer       : Test
 * @Module      : qualification
 * @Package     : dz.sh.hidra.qualification
 * @Description : Signed native Integration-to-Documents transient failure, manual replay and restart qualification.
 */
package dz.sh.hidra.qualification;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.net.http.HttpResponse;
import java.nio.file.Path;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.junit.jupiter.api.io.TempDir;
import org.springframework.dao.DataAccessException;
import org.testcontainers.containers.PostgreSQLContainer;
import static dz.sh.hidra.qualification.Gz2SignedWorkflowFixture.id;
import static org.junit.jupiter.api.Assertions.*;

class Gz2SyntheticExchangeNativeHttpPostgresIntegrationTest {
    private static final ObjectMapper JSON = new ObjectMapper();
    @TempDir Path documents;

    private static String configure(Gz2SignedWorkflowFixture f) {
        String external=id(),endpoint=id(),connector=id(),contract=id(),
                schema=id(),mapping=id(),job=id(),binding=id();
        String systemType=f.catalog("integration","EXTERNAL_SYSTEM_TYPE","SYNTHETIC_LOCAL");
        String endpointType=f.catalog("integration","ENDPOINT_TYPE","SYNTHETIC_LOCAL");
        String protocol=f.catalog("integration","PROTOCOL","SYNTHETIC_LOCAL");
        String connectorType=f.catalog("integration","CONNECTOR_TYPE","SYNTHETIC_LOCAL");
        String jobType=f.catalog("integration","JOB_TYPE","IMPORT");
        String contractType=f.catalog("integration","CONTRACT_TYPE","SYNTHETIC_DOCUMENT");
        String messageType=f.catalog("integration","MESSAGE_TYPE","SYNTHETIC_DRAFT");
        String payloadFormat=f.catalog("integration","PAYLOAD_FORMAT","JSON");
        String type=f.catalog("documents","DOCUMENT_TYPE","GZ2_SYNTHETIC_DRAFT");
        String classification=f.catalog("documents","DOCUMENT_CLASSIFICATION","GZ2_SYNTHETIC_DRAFT");
        f.sql.update("""
                INSERT INTO public.hidra_integration_external_system
                    (id,code,name_fr,system_type_id,environment,criticality,status,created_at,updated_at)
                VALUES (?,?,'Source synthétique','?','SANDBOX','LOW','ACTIVE',now(),now())
                """.replace("'?'","?"),external,external,systemType);
        f.sql.update("""
                INSERT INTO public.hidra_integration_external_endpoint
                    (id,external_system_id,code,endpoint_type_id,direction,protocol_id,
                     tls_required,active,created_at,updated_at)
                VALUES (?,?,?,?, 'INBOUND',?,false,true,now(),now())
                """,endpoint,external,"SYNTHETIC_INBOUND",endpointType,protocol);
        f.sql.update("""
                INSERT INTO public.hidra_integration_connector_instance
                    (id,external_system_id,endpoint_id,code,connector_type_id,
                     connector_implementation,direction,active,created_at,updated_at)
                VALUES (?,?,?,?,?,'NOOP','INBOUND',true,now(),now())
                """,connector,external,endpoint,"T26-CONFIG",connectorType);
        f.sql.update("""
                INSERT INTO public.hidra_integration_data_contract
                    (id,code,name_fr,contract_type_id,payload_format_id,owning_target_module,status,created_at,updated_at)
                VALUES (?,?,'Contrat synthétique',?,?,'documents','ACTIVE',now(),now())
                """,contract,contract,contractType,payloadFormat);
        String definition="{\"type\":\"object\",\"properties\":{\"titleFr\":{\"type\":\"string\"}}}";
        f.sql.update("""
                INSERT INTO public.hidra_integration_schema_version
                    (id,data_contract_id,version_number,schema_definition,checksum,status,created_at,updated_at)
                VALUES (?,?,1,?::jsonb,encode(sha256(convert_to((?::jsonb)::text,'UTF8')),'hex'),
                        'ACTIVE',now(),now())
                """,schema,contract,definition,definition);
        f.sql.update("""
                INSERT INTO public.hidra_integration_mapping_profile
                    (id,code,external_system_id,data_contract_id,schema_version_id,target_module,
                     target_type_code,direction,status,validation_mode,created_at,updated_at)
                VALUES (?,'SYNTHETIC_DOCUMENT_DRAFT_V1',?,?,?,'documents','DOCUMENT','INBOUND',
                        'ACTIVE','STRICT',now(),now())
                """,mapping,external,contract,schema);
        f.sql.update("""
                INSERT INTO public.hidra_integration_field_mapping
                    (id,mapping_profile_id,source_path,target_path,data_type,required,display_order,active)
                VALUES (?,?,'titleFr','titleFr','STRING',true,1,true)
                """,id(),mapping);
        f.sql.update("""
                INSERT INTO public.hidra_integration_job_definition
                    (id,code,name_fr,connector_instance_id,mapping_profile_id,job_type_id,
                     direction,target_module,manual_run_allowed,active,created_at,updated_at)
                VALUES (?,?,'Import synthétique',?,? ,?,'INBOUND','documents',true,true,now(),now())
                """,job,job,connector,mapping,jobType);
        f.sql.update("""
                INSERT INTO public.hidra_integration_t26_mapping_binding
                  (id,company_id,facility_id,mapping_profile_id,schema_version_id,job_definition_id,
                   external_system_id,message_type_id,payload_format_id,document_type_id,classification_id,status)
                VALUES (?,?,?,?,?,?,?,?,?,?,?,'ACTIVE')
                """,binding,f.company,f.asset,mapping,schema,job,external,messageType,payloadFormat,type,classification);
        return mapping;
    }

    @Test void transientReceiverRollsBackThenDistinctAuthorizedReplayCreatesOneDraftAcrossRestart()
            throws Exception {
        try(var postgres=new PostgreSQLContainer<>("postgres:16")
                .withDatabaseName("hidra_gz2_t26").withCommand("postgres","-c","autovacuum=off")) {
            postgres.start();
            var f=new Gz2SignedWorkflowFixture(postgres);
            String profile=configure(f);
            String provider=f.catalog("documents","DOCUMENT_STORAGE_PROVIDER","T26_LOCAL");
            for(String action:new String[]{"read","execute"})
                f.assetGrant(f.author,"integration:synthetic-exchange:"+action,f.company,f.asset);
            for(String action:new String[]{"read","replay"})
                f.assetGrant(f.reviewer,"integration:synthetic-exchange:"+action,f.company,f.asset);
            for(String actor:new String[]{f.author,f.reviewer}) {
                f.assetGrant(actor,"documents:controlled:read",f.company,f.asset);
                f.assetGrant(actor,"documents:controlled:create",f.company,f.asset);
            }
            // Explicit invalid-scope grants isolate owner/binding checks from missing permissions.
            f.permit(f.author,"integration:synthetic-exchange:read","INTEGRATION",
                    "FACILITY",f.secondAsset);
            f.permit(f.author,"integration:synthetic-exchange:read","INTEGRATION",
                    "CUSTOM","integration-company:"+f.foreignCompany);
            String root="/api/v1/integration/companies/"+f.company+"/facilities/"+f.asset;
            String key=id(), exchangeId, documentId, failedRecoveryId;
            String schema=f.sql.queryForObject(
                    "SELECT schema_version_id FROM hidra_integration_mapping_profile WHERE id=?",
                    String.class,profile);
            Map<String,Object> payload=Map.of("requestKey",key,"mappingProfileId",profile,
                    "schemaVersionId",schema,"titleFr","Document synthétique GZ2");
            String path=root+"/synthetic-exchanges";
            try(var r=f.start(documents,provider)) {
                String author=r.login(f.author),reviewer=r.login(f.reviewer),foreign=r.login(f.foreign);
                assertEquals(profile,r.get(root+"/synthetic-mappings/"+profile,author)
                        .path("mappingProfileId").asText());
                int protectedStatus=r.send("GET",root+"/synthetic-mappings/"+profile,foreign,null).statusCode();
                assertTrue(protectedStatus==403 || protectedStatus==404);
                f.sql.execute("""
                        CREATE FUNCTION public.t26_test_receiver_fault() RETURNS trigger LANGUAGE plpgsql AS $$
                        BEGIN RAISE EXCEPTION 'Synthetic transient Documents fault' USING ERRCODE='40001'; END $$;
                        CREATE TRIGGER t26_test_receiver_fault BEFORE INSERT ON public.hidra_documents_controlled_scope
                        FOR EACH ROW EXECUTE FUNCTION public.t26_test_receiver_fault()
                        """);
                JsonNode failed, failedRecovery;
                try {
                    failed=r.json("POST",path,author,payload,201);
                    failedRecovery=r.json("POST",path,author,Map.of(
                            "requestKey",id(),"mappingProfileId",profile,
                            "schemaVersionId",schema,"titleFr","Reprise en echec GZ2"),201);
                } finally {
                    f.sql.execute("DROP TRIGGER t26_test_receiver_fault ON public.hidra_documents_controlled_scope");
                    f.sql.execute("DROP FUNCTION public.t26_test_receiver_fault()");
                }
                exchangeId=failed.path("exchangeId").asText();
                failedRecoveryId=failedRecovery.path("exchangeId").asText();
                assertEquals("FAILED",failed.path("status").asText());
                assertEquals("FAILED",failedRecovery.path("status").asText());
                assertNotEquals(exchangeId,failedRecoveryId);
                assertEquals(0,f.sql.queryForObject("SELECT count(*) FROM hidra_documents_controlled_scope",
                        Integer.class));
                assertEquals(2,f.sql.queryForObject("SELECT count(*) FROM hidra_integration_dead_letter_record",
                        Integer.class));
                assertEquals("FAILED",r.get(path+"/"+exchangeId,author).path("status").asText());
                assertEquals(1,r.get(path+"/"+exchangeId+"/attempts",author)
                        .path("attempts").size());
                assertEquals(200,r.send("POST",path,author,payload).statusCode());
                assertEquals(409,r.send("POST",path,author,Map.of("requestKey",key,
                        "mappingProfileId",profile,"schemaVersionId",schema,"titleFr","Changed")).statusCode());
                int foreignRead=r.send("GET",path+"/"+exchangeId,foreign,null).statusCode();
                assertTrue(foreignRead==403 || foreignRead==404);
                assertNotEquals(200,r.send("POST",path+"/"+exchangeId+"/replay",author,
                        Map.of("requestKey",id(),"expectedAttempt",1,"reason","Only a different operator")).statusCode());

                // Prove explicit wrong scope, inactive mapping and stale schema reject before receipts.
                String otherFacility="/api/v1/integration/companies/"+f.company
                        +"/facilities/"+f.secondAsset;
                String wrongCompany="/api/v1/integration/companies/"+f.foreignCompany
                        +"/facilities/"+f.asset;
                assertEquals(404,r.send("GET",otherFacility+"/synthetic-mappings/"+profile,
                        author,null).statusCode());
                assertEquals(404,r.send("GET",wrongCompany+"/synthetic-mappings/"+profile,
                        author,null).statusCode());
                assertEquals(404,r.send("GET",otherFacility+"/synthetic-exchanges/"+exchangeId,
                        author,null).statusCode());
                assertEquals(409,r.send("POST",path,author,Map.of(
                        "requestKey",id(),"mappingProfileId",profile,
                        "schemaVersionId",id(),"titleFr","Invalid schema")).statusCode());
                assertEquals(404,r.send("GET",root+"/synthetic-mappings/"+id(),
                        author,null).statusCode());
                String binding=f.sql.queryForObject(
                        "SELECT id FROM public.hidra_integration_t26_mapping_binding WHERE mapping_profile_id=?",
                        String.class,profile);
                f.sql.update("UPDATE public.hidra_integration_t26_mapping_binding SET status='RETIRED' WHERE id=?",
                        binding);
                try {
                    assertEquals(404,r.send("POST",path+"/"+exchangeId+"/replay",reviewer,
                            Map.of("requestKey",id(),"expectedAttempt",1,
                                    "reason","Retired binding must deny replay")).statusCode());
                } finally {
                    f.sql.update("UPDATE public.hidra_integration_t26_mapping_binding SET status='ACTIVE' WHERE id=?",
                            binding);
                }
                assertEquals(1,r.get(path+"/"+exchangeId+"/attempts",author).path("attempts").size());

                // Two authorized competing replays admit exactly one recipient draft.
                var firstReplay=Map.of("requestKey",id(),"expectedAttempt",1,
                        "reason","Concurrent controlled replay A");
                var secondReplay=Map.of("requestKey",id(),"expectedAttempt",1,
                        "reason","Concurrent controlled replay B");
                List<HttpResponse<String>> contenders=race(
                        ()->r.send("POST",path+"/"+exchangeId+"/replay",reviewer,firstReplay),
                        ()->r.send("POST",path+"/"+exchangeId+"/replay",reviewer,secondReplay));
                assertEquals(1,contenders.stream().filter(x->x.statusCode()==200).count());
                assertEquals(1,contenders.stream().filter(x->x.statusCode()==409).count());
                int winningIndex=contenders.getFirst().statusCode()==200?0:1;
                var replay=winningIndex==0?firstReplay:secondReplay;
                var success=JSON.readTree(contenders.get(winningIndex).body());
                assertEquals(200,r.send("POST",path+"/"+exchangeId+"/replay",reviewer,replay)
                        .statusCode());
                assertEquals("SUCCEEDED",success.path("status").asText());
                documentId=success.path("documentId").asText();
                assertFalse(documentId.isBlank());
                assertEquals(2,r.get(path+"/"+exchangeId+"/attempts",reviewer).path("attempts").size());
                assertEquals(1,f.sql.queryForObject("SELECT count(*) FROM hidra_documents_controlled_scope WHERE document_id=?",
                        Integer.class,documentId));
                assertEquals(1,f.sql.queryForObject("SELECT count(*) FROM hidra_integration_t26_exchange_receipt WHERE document_id=?",
                        Integer.class,documentId));
                assertEquals(2,f.sql.queryForObject("SELECT count(*) FROM hidra_integration_job_run WHERE correlation_id LIKE ?",
                        Integer.class,"T26:"+exchangeId+":%"));
                assertEquals("REPLAYED",f.sql.queryForObject(
                        "SELECT status FROM hidra_integration_dead_letter_record WHERE job_run_id IN (SELECT job_run_id FROM hidra_integration_t26_attempt_receipt WHERE exchange_id=?)",
                        String.class,exchangeId));
                assertNotEquals(200,r.send("POST",path+"/"+exchangeId+"/replay",foreign,replay).statusCode());
                assertEquals(409,r.send("POST",path+"/"+exchangeId+"/replay",reviewer,
                        Map.of("requestKey",id(),"expectedAttempt",1,"reason","Changed")).statusCode());

                // Parallel first submission must yield one event, one native attempt and one draft.
                String parallelKey=id();
                var parallelPayload=Map.of("requestKey",parallelKey,"mappingProfileId",profile,
                        "schemaVersionId",schema,"titleFr","Concurrent GZ2 document");
                List<HttpResponse<String>> submits=race(
                        ()->r.send("POST",path,author,parallelPayload),
                        ()->r.send("POST",path,author,parallelPayload));
                assertEquals(1,submits.stream().filter(x->x.statusCode()==201).count());
                assertEquals(1,submits.stream().filter(x->x.statusCode()==200).count());
                String sharedId=JSON.readTree(submits.getFirst().body()).path("exchangeId").asText();
                assertFalse(sharedId.isBlank());
                assertEquals(sharedId,JSON.readTree(submits.get(1).body()).path("exchangeId").asText());
                assertEquals("SUCCEEDED",r.get(path+"/"+sharedId,author).path("status").asText());
                assertEquals(1,f.sql.queryForObject(
                        "SELECT count(*) FROM public.hidra_integration_t26_exchange_receipt WHERE company_id=? AND facility_id=? AND request_key=?",
                        Integer.class,f.company,f.asset,parallelKey));
                assertEquals(1,f.sql.queryForObject(
                        "SELECT count(*) FROM public.hidra_integration_t26_attempt_receipt WHERE exchange_id=?",
                        Integer.class,sharedId));
                assertEquals(1,f.sql.queryForObject(
                        "SELECT count(*) FROM public.hidra_documents_controlled_scope WHERE create_request_id=?",
                        Integer.class,"T26-"+sharedId));

                // SQL preservation and deferred native-identity commit guards must hold.
                assertSqlGuard(()->f.sql.update(
                        "UPDATE public.hidra_integration_t26_exchange_receipt SET submitter_id=? WHERE id=?",
                        id(),exchangeId));
                assertSqlGuard(()->f.sql.update(
                        "UPDATE public.hidra_integration_t26_attempt_receipt SET actor_id=? WHERE exchange_id=? AND attempt_ordinal=1",
                        id(),exchangeId));
                assertSqlGuard(()->f.sql.update(
                        "DELETE FROM public.hidra_integration_t26_exchange_receipt WHERE id=?",exchangeId));
                String runId=id();
                String job=f.sql.queryForObject(
                        "SELECT job_definition_id FROM public.hidra_integration_t26_exchange_receipt WHERE id=?",
                        String.class,exchangeId);
                assertSqlGuard(()->f.sql.update("""
                        INSERT INTO public.hidra_integration_job_run
                            (id,job_definition_id,run_number,trigger_type,triggered_by_actor_id,status,
                             correlation_id,started_at,received_count,mapped_count,accepted_count,
                             rejected_count,dead_letter_count,retry_count,created_at,updated_at)
                        VALUES (?,?,0,'MANUAL',?,'RUNNING',?,now(),1,1,0,0,0,0,now(),now())
                        """,runId,job,f.author,"T26:"+id()+":1"));
                assertEquals(0,f.sql.queryForObject(
                        "SELECT count(*) FROM public.hidra_integration_job_run WHERE id=?",
                        Integer.class,runId));
            }
            try(var restarted=f.start(documents,provider)) {
                String reviewer=restarted.login(f.reviewer);
                var saved=restarted.get(path+"/"+exchangeId,reviewer);
                assertEquals("SUCCEEDED",saved.path("status").asText());
                assertEquals(documentId,saved.path("documentId").asText());
                assertEquals(2,restarted.get(path+"/"+exchangeId+"/attempts",reviewer)
                        .path("attempts").size());

                // A failed first attempt remains visible after restart, then a failed retry stays failed.
                assertEquals("FAILED",restarted.get(path+"/"+failedRecoveryId,reviewer)
                        .path("status").asText());
                f.sql.execute("""
                        CREATE FUNCTION public.t26_test_recovery_fault() RETURNS trigger LANGUAGE plpgsql AS $
                        BEGIN RAISE EXCEPTION 'Synthetic recovery fault' USING ERRCODE='40001'; END $;
                        CREATE TRIGGER t26_test_recovery_fault BEFORE INSERT ON public.hidra_documents_controlled_scope
                        FOR EACH ROW EXECUTE FUNCTION public.t26_test_recovery_fault()
                        """);
                try {
                    var recovery=restarted.json("POST",path+"/"+failedRecoveryId+"/replay",reviewer,
                            Map.of("requestKey",id(),"expectedAttempt",1,
                                    "reason","Post-restart controlled failed recovery"),200);
                    assertEquals("FAILED",recovery.path("status").asText());
                    assertTrue(recovery.path("documentId").isMissingNode()
                            || recovery.path("documentId").isNull());
                } finally {
                    f.sql.execute("DROP TRIGGER t26_test_recovery_fault ON public.hidra_documents_controlled_scope");
                    f.sql.execute("DROP FUNCTION public.t26_test_recovery_fault()");
                }
                assertEquals("FAILED",restarted.get(path+"/"+failedRecoveryId,reviewer)
                        .path("status").asText());
                assertEquals(2,restarted.get(path+"/"+failedRecoveryId+"/attempts",reviewer)
                        .path("attempts").size());
                assertEquals(0,f.sql.queryForObject(
                        "SELECT count(*) FROM public.hidra_documents_controlled_scope WHERE create_request_id=?",
                        Integer.class,"T26-"+failedRecoveryId));
                assertEquals(0,f.sql.queryForObject(
                        "SELECT count(*) FROM public.hidra_integration_t26_exchange_receipt WHERE id=? AND document_id IS NOT NULL",
                        Integer.class,failedRecoveryId));

                // An already-signed LOCAL token is denied after a current facility grant is revoked.
                assertEquals(1,f.sql.update("""
                        UPDATE public.hidra_identity_user_permission_grant
                        SET valid_to=now()-interval '1 second'
                        WHERE user_id=? AND scope_type='FACILITY' AND scope_reference_id=?
                          AND status='ACTIVE'
                          AND permission_id=(SELECT id FROM public.hidra_identity_permission
                                             WHERE code='integration:synthetic-exchange:read')
                        """,f.reviewer,f.asset));
                int revokedStatus=restarted.send("GET",path+"/"+exchangeId,reviewer,null).statusCode();
                assertTrue(revokedStatus==403 || revokedStatus==404,
                        "The existing signed token must not retain revoked read authority");
            }
        }
    }

    private static List<HttpResponse<String>> race(Callable<HttpResponse<String>> first,
            Callable<HttpResponse<String>> second) throws Exception {
        try(var workers=Executors.newVirtualThreadPerTaskExecutor()) {
            CountDownLatch ready=new CountDownLatch(2),go=new CountDownLatch(1);
            Future<HttpResponse<String>> left=workers.submit(()->{
                ready.countDown();
                go.await();
                return first.call();
            });
            Future<HttpResponse<String>> right=workers.submit(()->{
                ready.countDown();
                go.await();
                return second.call();
            });
            assertTrue(ready.await(10,TimeUnit.SECONDS),"Both competing clients must be ready");
            go.countDown();
            return List.of(left.get(45,TimeUnit.SECONDS),right.get(45,TimeUnit.SECONDS));
        }
    }

    private static void assertSqlGuard(Executable statement) {
        DataAccessException rejected=assertThrows(DataAccessException.class,statement);
        Throwable cause=rejected;
        while(cause!=null && !(cause instanceof SQLException)) cause=cause.getCause();
        assertInstanceOf(SQLException.class,cause,"PostgreSQL must reject the committed mutation");
        assertEquals("23514",((SQLException)cause).getSQLState(),
                "The rejection must come from an enforcing T26 physical constraint");
    }
}
