/**
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 * @Name        : Gz2ControlledDocumentsNativeHttpPostgresIntegrationTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-11-17
 * @Type        : Class
 * @Layer       : Test
 * @Module      : qualification
 * @Package     : dz.sh.hidra.qualification
 * @Description : Signed GZ2 scoped Documents lifecycle, independent acceptance and restarted byte integrity proof.
 */
package dz.sh.hidra.qualification;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;
import java.util.Comparator;
import java.util.UUID;
import java.util.concurrent.Executors;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import org.junit.jupiter.api.Test;
import org.springframework.dao.DataAccessException;
import org.testcontainers.containers.PostgreSQLContainer;
import static dz.sh.hidra.qualification.Gz2SignedWorkflowFixture.id;
import static org.junit.jupiter.api.Assertions.*;

class Gz2ControlledDocumentsNativeHttpPostgresIntegrationTest {
    private static final ObjectMapper JSON=new ObjectMapper();

    private static String base(Gz2SignedWorkflowFixture f){
        return "/api/v1/documents/companies/"+f.company+"/facilities/"+f.asset+"/controlled-documents";
    }
    private static void deny(int status){
        assertTrue(status==403||status==404,"Unexpected protected disclosure "+status);
    }
    private static Map<String,Object> upload(String doc,String actor,int n){
        return Map.of("documentId",doc,"versionNumber",n,"versionLabel","v"+n,
                "titleFr","Preuve GZ2 "+n,"uploadedByActorId",actor);
    }
    private static String required(JsonNode v,String key){
        String text=v.path(key).asText(null);
        assertNotNull(text,"Required key: "+key);
        return text;
    }

    @Test void discoversAcceptsAndSupersedesScopedDocumentVersions()
            throws Exception {
        Path objects=Files.createTempDirectory("gz2-documents-t24-");
        try (var postgres=new PostgreSQLContainer<>("postgres:16")
                .withDatabaseName("hidra_gz2_t24")
                .withCommand("postgres","-c","autovacuum=off")){
            postgres.start();
            var f=new Gz2SignedWorkflowFixture(postgres);
            String provider=f.catalog("documents","DOCUMENT_STORAGE_PROVIDER","GZ2_LOCAL");
            String type=f.catalog("documents","DOCUMENT_TYPE","ENGINEERING_EVIDENCE");
            String classification=f.catalog("documents","DOCUMENT_CLASSIFICATION","INTERNAL");
            String role=f.catalog("documents","DOCUMENT_LINK_ROLE","FACILITY_EVIDENCE");
            f.assetGrant(f.author,"documents:controlled:create",f.company,f.asset);
            f.assetGrant(f.author,"documents:controlled:read",f.company,f.asset);
            f.assetGrant(f.author,"documents:controlled:accept",f.company,f.asset);
            f.assetGrant(f.producer,"documents:controlled:write",f.company,f.asset);
            f.assetGrant(f.producer,"documents:controlled:read",f.company,f.asset);
            f.assetGrant(f.producer,"documents:controlled:accept",f.company,f.asset);
            f.assetGrant(f.reviewer,"documents:controlled:accept",f.company,f.asset);
            f.assetGrant(f.reviewer,"documents:controlled:read",f.company,f.asset);
            f.assetGrant(f.reader,"documents:controlled:read",f.company,f.asset);
            f.assetGrant(f.revoked,"documents:controlled:read",f.company,f.asset);
            f.assetGrant(f.foreign,"documents:controlled:read",f.foreignCompany,f.asset);
            f.assetGrant(f.wrongAsset,"documents:controlled:read",f.company,f.secondAsset);
            byte[] original="signed GZ2 synthetic inspection evidence".getBytes(StandardCharsets.UTF_8);
            byte[] successor="signed GZ2 updated engineering evidence".getBytes(StandardCharsets.UTF_8);
            String doc,first,second,digest,firstLink;
            long priorPid;
            try(var runtime=f.start(objects,provider)){
                priorPid=runtime.pid();
                String author=runtime.login(f.author),uploader=runtime.login(f.producer),
                        reviewer=runtime.login(f.reviewer),reader=runtime.login(f.reader);
                String createKey=id();
                var create=Map.of("requestId",createKey,"code","GZ2-DOC-"+id(),
                        "titleFr","Preuve d'inspection GZ2","documentTypeId",type,
                        "classificationId",classification);
                JsonNode created=runtime.json("POST",base(f),author,create,201);
                doc=required(created.path("document"),"id");
                assertEquals("DRAFT",required(created.path("document"),"status"));
                assertEquals(doc,required(runtime.json("POST",base(f),author,create,200)
                        .path("document"),"id"));
                assertEquals(409,runtime.send("POST",base(f),author,
                        Map.of("requestId",createKey,"code","OTHER-"+id(),
                                "titleFr","Autre","documentTypeId",type,
                                "classificationId",classification)).statusCode());

                String route=base(f)+"/"+doc;
                assertEquals(doc,required(runtime.get(route,reader),"id"));
                assertEquals(1,runtime.get(base(f)+"?page=0&size=5",reader).path("total").asInt());
                String foreign=runtime.login(f.foreign),wrong=runtime.login(f.wrongAsset);
                deny(runtime.send("GET",route,foreign,null).statusCode());
                deny(runtime.send("GET",base(f)+"?page=0&size=5",foreign,null).statusCode());
                deny(runtime.send("GET",base(f)+"?page=0&size=5",wrong,null).statusCode());
                deny(runtime.send("GET",route,runtime.login(f.wrongAsset),null).statusCode());
                deny(runtime.send("POST",base(f),uploader,create).statusCode());
                f.sql.update("UPDATE hidra_identity_user_permission_grant SET valid_to=now()-interval '1 second'"
                        +" WHERE user_id=? AND permission_id IN"
                        +" (SELECT id FROM hidra_identity_permission WHERE code='documents:controlled:read')",f.revoked);
                deny(runtime.send("GET",route,runtime.login(f.revoked),null).statusCode());

                // Two binary uploads preserve independent DRAFT versions and exact SHA-256 bytes.
                var up=runtime.multipart(route+"/versions/upload",uploader,upload(doc,f.producer,1),original);
                assertEquals(200,up.statusCode(),new String(up.body(),StandardCharsets.UTF_8));
                JsonNode firstVersion=JSON.readTree(up.body());
                first=required(firstVersion,"id");
                digest=required(firstVersion,"checksumValue");
                assertEquals(64,digest.length());
                assertEquals(original.length,firstVersion.path("fileSizeBytes").asLong());
                assertEquals(digest,java.util.HexFormat.of().formatHex(
                        java.security.MessageDigest.getInstance("SHA-256").digest(original)));
                assertEquals(1,runtime.get(route+"/versions?page=0&size=5",reader).path("total").asInt());
                var firstAcceptance=Map.of("requestId",id(),"reason","Independent verified evidence",
                        "linkRoleId",role);
                assertEquals(409,runtime.send("POST",route+"/versions/"+first+"/accept",
                        author,firstAcceptance).statusCode(),
                        "Creator has custodian grant but is never an independent custodian.");
                assertEquals(409,runtime.send("POST",route+"/versions/"+first+"/accept",
                        uploader,firstAcceptance).statusCode(),
                        "Uploader has custodian grant but cannot accept the same version.");
                JsonNode acceptance=runtime.json("POST",route+"/versions/"+first+"/accept",
                        reviewer,firstAcceptance,201);
                assertEquals("ACCEPT",required(acceptance,"action"));
                firstLink=required(acceptance,"linkId");
                assertEquals(first,required(runtime.get(route,reader),"currentVersionId"));
                assertEquals(firstLink,required(runtime.get(route+"/links?page=0&size=5",reader)
                        .path("items").get(0),"id"));
                assertArrayEquals(original,runtime.download(route+"/versions/"+first+"/content",reader).body());

                var again=runtime.multipart(route+"/versions/upload",uploader,upload(doc,f.producer,2),successor);
                assertEquals(200,again.statusCode(),new String(again.body(),StandardCharsets.UTF_8));
                JsonNode secondVersion=JSON.readTree(again.body());
                second=required(secondVersion,"id");
                assertEquals(successor.length,secondVersion.path("fileSizeBytes").asLong());
                assertEquals(java.util.HexFormat.of().formatHex(
                        java.security.MessageDigest.getInstance("SHA-256").digest(successor)),
                        required(secondVersion,"checksumValue"));
                String storage=f.sql.queryForObject(
                        "SELECT storage_object_id FROM hidra_documents_document_version WHERE id=?",
                        String.class,second);
                // Alter the stored bytes without changing owner metadata. Approval MUST reject.
                Files.writeString(objects.resolve(storage),"tampered",StandardCharsets.UTF_8);
                var newReceipt=Map.of("requestId",id(),"reason","Approved newer revision",
                        "linkRoleId",role,"expectedCurrentVersionId",first);
                assertEquals(409,runtime.send("POST",route+"/versions/"+second+"/supersede",
                        reviewer,newReceipt).statusCode());
                Files.write(objects.resolve(storage),successor);
                assertEquals(409,runtime.send("POST",route+"/versions/"+second+"/supersede",
                        reviewer,Map.of("requestId",id(),"reason","Wrong head",
                        "linkRoleId",role,"expectedCurrentVersionId",id())).statusCode());
                // Competing accepted-head requests cannot both advance the sole current pointer.
                Map<String,String> staleAttempt=Map.of("requestId",id(),"reason","Competing head",
                        "linkRoleId",role,"expectedCurrentVersionId",first);
                JsonNode supersession;
                try(var executor=Executors.newFixedThreadPool(2)) {
                    CountDownLatch gate=new CountDownLatch(1);
                    var left=executor.submit(()->{
                        gate.await();
                        return runtime.send("POST",route+"/versions/"+second+"/supersede",reviewer,newReceipt);
                    });
                    var right=executor.submit(()->{
                        gate.await();
                        return runtime.send("POST",route+"/versions/"+second+"/supersede",reviewer,staleAttempt);
                    });
                    gate.countDown();
                    var one=left.get(40,TimeUnit.SECONDS);
                    var two=right.get(40,TimeUnit.SECONDS);
                    assertEquals(java.util.List.of(201,409),java.util.List.of(
                            Math.min(one.statusCode(),two.statusCode()),
                            Math.max(one.statusCode(),two.statusCode())));
                    var success=one.statusCode()==201?one:two;
                    supersession=JSON.readTree(success.body());
                    if(two.statusCode()==201) {
                        assertEquals(409,runtime.send("POST",route+"/versions/"+second+"/supersede",
                                reviewer,newReceipt).statusCode());
                    }
                }
                assertEquals("SUPERSEDE",required(supersession,"action"));
                assertEquals(first,required(supersession,"previousVersionId"));
                String winningRequest=required(supersession,"requestId");
                Map<String,String> winner=winningRequest.equals(newReceipt.get("requestId"))
                        ?newReceipt:staleAttempt;
                assertEquals(required(supersession,"id"),required(runtime.json("POST",
                        route+"/versions/"+second+"/supersede",reviewer,winner,200),"id"),
                        "The accepted request replays after the document was superseded.");
                assertEquals(second,required(runtime.get(route,reader),"currentVersionId"));
                JsonNode history=runtime.get(route+"/versions?page=0&size=10",reader);
                assertEquals(2,history.path("total").asInt());
                assertEquals("SUPERSEDED",required(history.path("items").get(0),"status"));
                assertEquals("CURRENT",required(history.path("items").get(1),"status"));
                assertArrayEquals(original,runtime.download(route+"/versions/"+first+"/content",reader).body());
                assertArrayEquals(successor,runtime.download(route+"/versions/"+second+"/content",reader).body());
                // A later byte-level modification cannot create a false successful download.
                String priorStorage=f.sql.queryForObject(
                        "SELECT storage_object_id FROM hidra_documents_document_version WHERE id=?",
                        String.class,first);
                Files.writeString(objects.resolve(priorStorage),"changed after acceptance");
                assertEquals(409,runtime.download(route+"/versions/"+first+"/content",reader).statusCode());
                Files.write(objects.resolve(priorStorage),original);
                // Duplicating the exact version number must fail without a leaked blob or metadata.
                long count=f.sql.queryForObject(
                        "SELECT count(*) FROM hidra_documents_storage_object",Long.class);
                var duplicate=runtime.multipart(route+"/versions/upload",uploader,
                        upload(doc,f.producer,2),"duplicate".getBytes(StandardCharsets.UTF_8));
                assertTrue(duplicate.statusCode()>=400);
                assertEquals(count,f.sql.queryForObject(
                        "SELECT count(*) FROM hidra_documents_storage_object",Long.class));

                // Current grants and actual ownership are rechecked, not cached in a prior receipt.
                deny(runtime.send("GET",route,runtime.login(f.foreign),null).statusCode());
                f.sql.update("UPDATE hidra_identity_user_permission_grant SET valid_to=now()-interval '1 second'"
                        +" WHERE user_id=? AND permission_id IN"
                        +" (SELECT id FROM hidra_identity_permission WHERE code='documents:controlled:read')",f.reader);
                deny(runtime.send("GET",route,reader,null).statusCode());
            }
            assertEquals(1L,f.sql.queryForObject(
                    "SELECT count(*) FROM hidra_documents_controlled_scope WHERE document_id=?",Long.class,doc));
            assertEquals(2L,f.sql.queryForObject(
                    "SELECT count(*) FROM hidra_documents_controlled_decision WHERE document_id=?",Long.class,doc));
            assertThrows(DataAccessException.class,()->f.sql.update(
                    "UPDATE hidra_documents_controlled_decision SET reason='forged' WHERE document_id=?",doc));
            assertThrows(DataAccessException.class,()->f.sql.update(
                    "DELETE FROM hidra_documents_controlled_scope WHERE document_id=?",doc));
            assertThrows(DataAccessException.class,()->f.sql.update(
                    "UPDATE hidra_documents_document SET status='ARCHIVED' WHERE id=?",doc));
            assertThrows(DataAccessException.class,()->f.sql.update(
                    "DELETE FROM hidra_documents_document_version WHERE id=?",first));
            assertThrows(DataAccessException.class,()->f.sql.update(
                    "UPDATE hidra_documents_target_link SET active=false WHERE id=?",firstLink));
            assertThrows(DataAccessException.class,()->f.sql.update(
                    "TRUNCATE TABLE hidra_documents_controlled_decision"));
            // A new independent JVM observes durable same IDs and both files.
            try(var restarted=f.start(objects,provider)){
                assertNotEquals(priorPid,restarted.pid());
                String reader=restarted.login(f.reviewer);
                String route=base(f)+"/"+doc;
                assertEquals(second,required(restarted.get(route,reader),"currentVersionId"));
                assertArrayEquals(original,restarted.download(
                        route+"/versions/"+first+"/content",reader).body());
                assertArrayEquals(successor,restarted.download(
                        route+"/versions/"+second+"/content",reader).body());
                assertEquals(2,restarted.get(route+"/links?page=0&size=5",reader)
                        .path("total").asInt());
            }
        } finally {
            try(var files=Files.walk(objects)){
                files.sorted(Comparator.reverseOrder()).forEach(path->{
                    try{Files.deleteIfExists(path);}catch(java.io.IOException ignored){}
                });
            }
        }
    }
}
