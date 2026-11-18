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
import java.nio.file.Path;
import java.util.Map;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.testcontainers.containers.PostgreSQLContainer;
import static dz.sh.hidra.qualification.Gz2SignedWorkflowFixture.id;
import static org.junit.jupiter.api.Assertions.*;

class Gz2SyntheticExchangeNativeHttpPostgresIntegrationTest {
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
            String root="/api/v1/integration/companies/"+f.company+"/facilities/"+f.asset;
            String key=id(), exchangeId, documentId;
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
                JsonNode failed;
                try {
                    failed=r.json("POST",path,author,payload,201);
                } finally {
                    f.sql.execute("DROP TRIGGER t26_test_receiver_fault ON public.hidra_documents_controlled_scope");
                    f.sql.execute("DROP FUNCTION public.t26_test_receiver_fault()");
                }
                exchangeId=failed.path("exchangeId").asText();
                assertEquals("FAILED",failed.path("status").asText());
                assertEquals(0,f.sql.queryForObject("SELECT count(*) FROM hidra_documents_controlled_scope",
                        Integer.class));
                assertEquals(1,f.sql.queryForObject("SELECT count(*) FROM hidra_integration_dead_letter_record",
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
                var replay=Map.of("requestKey",id(),"expectedAttempt",1,
                        "reason","Synthetic controlled operator replay");
                var success=r.json("POST",path+"/"+exchangeId+"/replay",reviewer,replay,200);
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
            }
            try(var restarted=f.start(documents,provider)) {
                String reviewer=restarted.login(f.reviewer);
                var saved=restarted.get(path+"/"+exchangeId,reviewer);
                assertEquals("SUCCEEDED",saved.path("status").asText());
                assertEquals(documentId,saved.path("documentId").asText());
                assertEquals(2,restarted.get(path+"/"+exchangeId+"/attempts",reviewer)
                        .path("attempts").size());
            }
        }
    }
}