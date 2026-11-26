/**
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 * @Name        : Gz2HseCapaNativeHttpPostgresIntegrationTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-11-26
 * @Type        : Class
 * @Layer       : Test
 * @Module      : hse
 * @Package     : dz.sh.hidra.qualification
 * @Description : Signed GZ2 fresh-schema negative access and exact T17 route discovery across runtime restart.
 */
package dz.sh.hidra.qualification;

import com.fasterxml.jackson.databind.JsonNode;
import static dz.sh.hidra.qualification.Gz2SignedWorkflowFixture.id;
import org.junit.jupiter.api.Test;
import org.testcontainers.containers.PostgreSQLContainer;
import static org.junit.jupiter.api.Assertions.*;

class Gz2HseCapaNativeHttpPostgresIntegrationTest {
    @org.junit.jupiter.api.io.TempDir java.nio.file.Path objects;
    private static String route(Gz2SignedWorkflowFixture f){
        return "/api/v1/hse/companies/"+f.company+"/facilities/"+f.asset+"/cases";
    }
    private static void denied(int status){
        assertTrue(status==403||status==404,"HSE protected resource must not be disclosed: "+status);
    }
    private static void deniedPage(java.net.http.HttpResponse<String> response,String... protectedIds){
        denied(response.statusCode());
        String body=response.body();
        assertFalse(body.contains("\"items\"")||body.contains("\"total\"")||body.contains("\"totalElements\""));
        for(String protectedId:protectedIds)assertFalse(body.contains(protectedId));
    }
    private static void page(JsonNode value,int page,int size,int total,String... expectedIds){
        assertEquals(page,value.path("page").asInt(-1));
        assertEquals(size,value.path("size").asInt(-1));
        assertEquals(total,value.path("total").asInt(-1));
        JsonNode rows=value.path("items");
        assertTrue(rows.isArray());
        assertEquals(expectedIds.length,rows.size());
        for(int i=0;i<expectedIds.length;i++)assertEquals(expectedIds[i],required(rows.get(i),"id"));
    }
    @Test void signedCompanyAndFacilityReadsConcealNonexistentCasesAndRejectWrongScope() throws Exception {
        try(var postgres=new PostgreSQLContainer<>("postgres:16")
                .withDatabaseName("hidra_gz2_t17").withCommand("postgres","-c","autovacuum=off")){
            postgres.start();
            var f=new Gz2SignedWorkflowFixture(postgres);
            // Direct current permits at both registered owner scopes; no role name inference.
            f.permit(f.author,"hse:capa:read","HSE_CAPA","CUSTOM","documents-company:"+f.company);
            f.permit(f.author,"hse:capa:read","HSE_CAPA","FACILITY",f.asset);
            f.permit(f.foreign,"hse:capa:read","HSE_CAPA","CUSTOM","documents-company:"+f.foreignCompany);
            f.permit(f.foreign,"hse:capa:read","HSE_CAPA","FACILITY",f.asset);
            String missing=Gz2SignedWorkflowFixture.id();
            try(var runtime=f.start()){
                String author=runtime.login(f.author), foreign=runtime.login(f.foreign);
                denied(runtime.send("GET",route(f)+"/"+missing,author,null).statusCode());
                denied(runtime.send("GET",route(f)+"/"+missing,foreign,null).statusCode());
                denied(runtime.send("GET",route(f),foreign,null).statusCode());
                denied(runtime.send("GET",route(f)+"/"+missing+"/history",foreign,null).statusCode());
                int code=runtime.send("GET",route(f)+"/"+missing,null,null).statusCode();
                assertTrue(code==401||code==403);
            }
            // Restart must not create shadow records for denied/nonexistent resources.
            try(var restarted=f.start()){
                var author=restarted.login(f.author);
                denied(restarted.send("GET",route(f)+"/"+missing,author,null).statusCode());
                assertEquals(0L,f.sql.queryForObject("SELECT count(*) FROM hidra_hse_t17_case_scope",
                        Long.class));
            }
        }
    }
    @Test void signedImpactAssessmentAndApprovedClosureSurviveRestart() throws Exception {
        try (var postgres = new PostgreSQLContainer<>("postgres:16").withDatabaseName("hidra_gz2_t17_impact")
                .withCommand("postgres", "-c", "autovacuum=off")) {
            postgres.start();
            var f = new Gz2SignedWorkflowFixture(postgres);
            String incident = f.seedReviewedHseIncident();
            String unreadableIncident = f.seedReviewedHseIncident();
            String caseType = f.catalog("hse", "CASE_TYPE", "GZ2_T17_CASE");
            String severity = f.catalog("hse", "CASE_SEVERITY", "GZ2_T17_CASE_SEVERITY");
            String impactType = f.catalog("hse", "GZ2_T17_IMPACT", "SYNTHETIC_OPERATIONS");
            f.sql.update("""
                    INSERT INTO hidra_hse_catalog_field_policy
                        (field_role,catalog_name,active,created_at,updated_at)
                    VALUES ('CASE_IMPACT_TYPE','GZ2_T17_IMPACT',true,now(),now())
                    """);
            String provider = f.catalog("documents", "DOCUMENT_STORAGE_PROVIDER", "T17_LOCAL");
            String documentType = f.catalog("documents", "DOCUMENT_TYPE", "T17_EVIDENCE");
            String classification = f.catalog("documents", "DOCUMENT_CLASSIFICATION", "T17_INTERNAL");
            String linkRole = f.catalog("documents", "DOCUMENT_LINK_ROLE", "T17_EVIDENCE");
            String actionType = f.catalog("hse", "GZ2_T17_CAPA", "SYNTHETIC_CORRECTION");
            f.sql.update("""
                    INSERT INTO hidra_hse_catalog_field_policy
                        (field_role,catalog_name,active,created_at,updated_at)
                    VALUES ('CAPA_ACTION_TYPE','GZ2_T17_CAPA',true,now(),now())
                    """);
            var capaReview = f.review("hse", "HSE_CAPA", "HSE_CAPA_APPROVAL", true);
            var caseReview = f.review("hse", "HSE_CASE", "HSE_CASE_APPROVAL", true);
            f.global(f.author, "workflow:instances:execute", "workflow:tasks:execute",
                    "hse:cases:read", "hse:capas:read");
            f.global(f.reviewer, "workflow:tasks:execute", "workflow:tasks:read");
            f.global(f.foreign, "workflow:instances:execute", "workflow:tasks:read");
            f.grantScopedHse(f.author, "create", "read", "assess", "execute", "close");
            f.grantScopedHse(f.reviewer, "read", "execute", "review");
            f.grantScopedHse(f.author, "review");
            for (String code : java.util.List.of("documents:controlled:create", "documents:controlled:write",
                    "documents:controlled:read")) f.assetGrant(f.author, code, f.company, f.asset);
            f.assetGrant(f.reviewer, "documents:controlled:read", f.company, f.asset);
            f.assetGrant(f.reviewer, "documents:controlled:accept", f.company, f.asset);
            f.grantScopedHse(f.revoked, "read", "assess");
            for (String actor : java.util.List.of(f.author, f.revoked, f.reviewer)) {
                for (String code : java.util.List.of("incident:incidents:read", "alarm:alarms:read",
                        "monitoring:rule:read")) f.assetGrant(actor, code, f.company, f.asset);
            }
            String casePath;
            String assessmentId;
            long originalPid;
            ClosedJourney closed;
            var command = impact(Gz2SignedWorkflowFixture.id(), 0, impactType, "Synthetic impact assessment");
            String caseNumber = "T17-" + id();
            try (var runtime = f.start(objects, provider)) {
                originalPid = runtime.pid();
                String author = runtime.login(f.author);
                var enrolled = runtime.json("POST", route(f) + "/from-incident", author, java.util.Map.of(
                        "requestId", Gz2SignedWorkflowFixture.id(), "incidentId", incident,
                        "caseNumber", caseNumber, "title", "GZ2 HSE assessment",
                        "caseTypeId", caseType, "severityId", severity), 201);
                String caseId = enrolled.path("value").path("id").asText();
                assertFalse(caseId.isBlank());
                casePath = route(f) + "/" + caseId;
                String url = casePath + "/impact-assessments";
                assertEquals(409, runtime.send("POST", casePath + "/close", author,
                        action(0, id(), null, null)).statusCode());
                denied(runtime.send("POST", url, runtime.login(f.foreign), command).statusCode());
                var revoked = runtime.login(f.revoked);
                f.sql.update("""
                        UPDATE hidra_identity_user_permission_grant SET valid_to=now()-interval '1 second'
                        WHERE user_id=? AND permission_id=(SELECT id FROM hidra_identity_permission
                            WHERE code='hse:capa:assess') AND scope_type='FACILITY'
                        """, f.revoked);
                denied(runtime.send("POST", url, revoked, command).statusCode());
                assertEquals(409, runtime.send("POST", url, author,
                        impact(Gz2SignedWorkflowFixture.id(), 1, impactType, "Stale revision")).statusCode());
                assertEquals(400, runtime.send("POST", url, author,
                        impact(Gz2SignedWorkflowFixture.id(), 0, severity, "Wrong catalog family")).statusCode());
                assertEquals(0L, f.sql.queryForObject(
                        "SELECT count(*) FROM hidra_hse_impact_assessment WHERE hse_case_id=?", Long.class, caseId));
                try (var workers = java.util.concurrent.Executors.newFixedThreadPool(2)) {
                    var first = workers.submit(() -> runtime.send("POST", url, author, command));
                    var second = workers.submit(() -> runtime.send("POST", url, author, command));
                    var a = first.get(45, java.util.concurrent.TimeUnit.SECONDS);
                    var b = second.get(45, java.util.concurrent.TimeUnit.SECONDS);
                    assertEquals(java.util.List.of(200, 201),
                            java.util.stream.Stream.of(a, b).map(x -> x.statusCode()).sorted().toList(),
                            () -> a.body() + "\n" + b.body());
                }
                JsonNode replay = runtime.json("POST", url, author, command, 200);
                assertTrue(replay.path("replayed").asBoolean());
                assessmentId = replay.path("assessmentId").asText();
                assertFalse(assessmentId.isBlank());
                assertEquals(1, replay.path("caseVersion").asInt());
                assertEquals(409, runtime.send("POST", url, author,
                        impact(command.get("requestId").toString(), 0, impactType, "Changed replay")).statusCode());
                assertEquals(409, runtime.send("POST", url, author,
                        impact(Gz2SignedWorkflowFixture.id(), 1, impactType, "Second assessment")).statusCode());
                assertEquals(1L, f.sql.queryForObject(
                        "SELECT count(*) FROM hidra_hse_impact_assessment WHERE hse_case_id=?", Long.class, caseId));
                assertThrows(org.springframework.dao.DataAccessException.class, () -> f.sql.update(
                        "UPDATE hidra_hse_impact_assessment SET description='changed' WHERE id=?", assessmentId));
                closed = completeClosure(f, runtime, casePath, assessmentId, author, documentType,
                        classification, linkRole, capaReview, caseReview, actionType);
                // A13: same case number is not unique within a company/FACILITY.
                String otherId=required(runtime.json("POST",route(f)+"/from-incident",author,java.util.Map.of(
                        "requestId",id(),"incidentId",unreadableIncident,"caseNumber",caseNumber,
                        "title","Second synthetic case","caseTypeId",caseType,
                        "severityId",severity),201).path("value"),"id");
                String otherPath=route(f)+"/"+otherId;
                // No actionNumber parameter: an admitted empty parent is distinct from denial.
                page(runtime.get(otherPath+"/capas",author),0,25,0);
                page(runtime.get(otherPath+"/capas?page=1&size=1",author),1,1,0);
                var matched=runtime.get(route(f)+"?caseNumber="+caseNumber+"&page=0&size=1",author);
                assertEquals(2,matched.path("total").asInt());
                assertEquals(1,matched.path("items").size());
                assertEquals(caseId,required(matched.path("items").get(0),"id"));
                var second=runtime.get(route(f)+"?caseNumber="+caseNumber+"&page=1&size=1",author);
                assertEquals(2,second.path("total").asInt());
                assertEquals(otherId,required(second.path("items").get(0),"id"));
                var beyond=runtime.get(route(f)+"?caseNumber="+caseNumber+"&page=2&size=1",author);
                assertEquals(2,beyond.path("total").asInt());
                assertTrue(beyond.path("items").isEmpty());
                var noMatch=runtime.get(route(f)+"?caseNumber=NOT-FOUND&page=0&size=1",author);
                assertEquals(0,noMatch.path("total").asInt());
                assertTrue(noMatch.path("items").isEmpty());
                assertEquals(400,runtime.send("GET",route(f)+"?caseNumber=%20%20",author,null).statusCode());
                assertEquals(400,runtime.send("GET",route(f)+"?caseNumber="+"X".repeat(81),author,null).statusCode());
                assertEquals(400,runtime.send("GET",otherPath+"/capas?actionNumber=%20%20",author,null).statusCode());
                String priorCapaId=closed.capaPath().substring(closed.capaPath().lastIndexOf('/')+1);
                String number=f.sql.queryForObject("SELECT action_number FROM hidra_hse_capa WHERE id=?",
                        String.class,priorCapaId);
                var added1=runtime.json("POST",otherPath+"/capas",author,java.util.Map.of(
                        "requestId",id(),"expectedCaseVersion",0,"actionNumber",number,
                        "actionTypeId",actionType,"title","Duplicate CAPA one","ownerActorId",f.author),201);
                var added2=runtime.json("POST",otherPath+"/capas",author,java.util.Map.of(
                        "requestId",id(),"expectedCaseVersion",1,"actionNumber",number,
                        "actionTypeId",actionType,"title","Duplicate CAPA two","ownerActorId",f.author),201);
                String firstCapa=required(added1.path("value"),"id");
                String secondCapa=required(added2.path("value"),"id");
                // Unfiltered and filtered snapshots must preserve exact owner IDs,
                // stable order, full totals, and empty/beyond-end semantics.
                var unfiltered=runtime.get(otherPath+"/capas",author);
                page(unfiltered,0,25,2,firstCapa,secondCapa);
                for(var item:unfiltered.path("items"))assertEquals(otherId,required(item,"caseId"));
                page(runtime.get(otherPath+"/capas?page=0&size=1",author),0,1,2,firstCapa);
                page(runtime.get(otherPath+"/capas?page=1&size=1",author),1,1,2,secondCapa);
                page(runtime.get(otherPath+"/capas?page=2&size=1",author),2,1,2);
                page(runtime.get(otherPath+"/capas?actionNumber=NOT-FOUND",author),0,25,0);
                page(runtime.get(casePath+"/capas",author),0,25,1,priorCapaId);
                assertEquals(400,runtime.send("GET",otherPath+"/capas?page=-1",author,null).statusCode());
                assertEquals(400,runtime.send("GET",otherPath+"/capas?size=0",author,null).statusCode());
                assertEquals(400,runtime.send("GET",otherPath+"/capas?size=101",author,null).statusCode());
                assertEquals(400,runtime.send("GET",otherPath+"/capas?actionNumber="+"X".repeat(81),author,null).statusCode());
                deniedPage(runtime.send("GET",otherPath+"/capas",runtime.login(f.foreign),null),firstCapa,secondCapa);
                deniedPage(runtime.send("GET",otherPath.replace(f.company,f.foreignCompany)+"/capas",author,null),firstCapa,secondCapa);
                deniedPage(runtime.send("GET",otherPath.replace(f.asset,f.secondAsset)+"/capas",author,null),firstCapa,secondCapa);
                deniedPage(runtime.send("GET",route(f)+"/"+id()+"/capas",author,null),firstCapa,secondCapa);
                var selectedCapas=runtime.get(otherPath+"/capas?actionNumber="+number+"&page=0&size=1",author);
                assertEquals(2,selectedCapas.path("total").asInt());
                assertEquals(firstCapa,required(selectedCapas.path("items").get(0),"id"));
                var selectedNext=runtime.get(otherPath+"/capas?actionNumber="+number+"&page=1&size=1",author);
                assertEquals(2,selectedNext.path("total").asInt());
                assertEquals(secondCapa,required(selectedNext.path("items").get(0),"id"));
                var selectedBeyond=runtime.get(otherPath+"/capas?actionNumber="+number+"&page=2&size=1",author);
                assertEquals(2,selectedBeyond.path("total").asInt());
                assertTrue(selectedBeyond.path("items").isEmpty());
                assertEquals(1,runtime.get(casePath+"/capas?actionNumber="+number,author).path("total").asInt());
                assertEquals(0,runtime.get(otherPath+"/capas?actionNumber=NOT-FOUND",author).path("total").asInt());
                denied(runtime.send("GET",route(f)+"?caseNumber="+caseNumber,
                        runtime.login(f.foreign),null).statusCode());
                denied(runtime.send("GET",route(f).replace(f.company,f.foreignCompany)
                        +"?caseNumber="+caseNumber,author,null).statusCode());
                denied(runtime.send("GET",route(f).replace(f.asset,f.secondAsset)
                        +"?caseNumber="+caseNumber,author,null).statusCode());
                String revokedReader=runtime.login(f.revoked);
                f.sql.update("""
                        UPDATE hidra_identity_user_permission_grant SET valid_to=now()-interval '1 second'
                        WHERE user_id=? AND permission_id=(SELECT id FROM hidra_identity_permission
                            WHERE code='hse:capa:read') AND valid_to>now()
                        """,f.revoked);
                denied(runtime.send("GET",route(f)+"?caseNumber="+caseNumber,revokedReader,null).statusCode());
                deniedPage(runtime.send("GET",otherPath+"/capas",revokedReader,null),firstCapa,secondCapa);
                // The generic legacy routes must not enumerate T17-enrolled rows or
                // distinguish an enrolled exact ID from an unknown well-formed ID.
                JsonNode legacyCases=runtime.get("/api/v1/hse/cases?page=0&size=20",author);
                assertEquals(0,legacyCases.path("total").asInt());
                assertTrue(legacyCases.path("content").isEmpty());
                JsonNode legacyCapas=runtime.get("/api/v1/hse/capas?page=0&size=20",author);
                assertEquals(0,legacyCapas.path("total").asInt());
                assertTrue(legacyCapas.path("content").isEmpty());
                var legacyCase=runtime.send("GET","/api/v1/hse/cases/"+caseId,author,null);
                var absentCase=runtime.send("GET","/api/v1/hse/cases/"+id(),author,null);
                assertEquals(404,legacyCase.statusCode());
                assertEquals(legacyCase.statusCode(),absentCase.statusCode());
                var legacyCapa=runtime.send("GET","/api/v1/hse/capas/"+priorCapaId,author,null);
                var absentCapa=runtime.send("GET","/api/v1/hse/capas/"+id(),author,null);
                assertEquals(404,legacyCapa.statusCode());
                assertEquals(legacyCapa.statusCode(),absentCapa.statusCode());
                // This second match is outside the first page. Losing its actual Incident
                // review must fail the entire collection, including its protected total.
                f.sql.update("UPDATE hidra_incident_investigation SET state='SUBMITTED' WHERE incident_id=?",
                        unreadableIncident);
                denied(runtime.send("GET",route(f)+"?caseNumber="+caseNumber+"&page=0&size=1",
                        author,null).statusCode());
                denied(runtime.send("GET",otherPath,author,null).statusCode());
                deniedPage(runtime.send("GET",otherPath+"/capas",author,null),firstCapa,secondCapa);
                deniedPage(runtime.send("GET",otherPath+"/capas?actionNumber="+number,author,null),firstCapa,secondCapa);
                deniedPage(runtime.send("GET",otherPath+"/history",author,null),firstCapa,secondCapa);
            }
            try (var restarted = f.start(objects, provider)) {
                assertNotEquals(originalPid, restarted.pid());
                String author = restarted.login(f.author);
                var history = restarted.get(casePath + "/history", author);
                assertEquals("CLOSED", required(restarted.get(closed.casePath(), author), "status"));
                assertEquals(closed.closureId(), required(restarted.get(closed.casePath(), author), "closureId"));
                assertEquals("CLOSED", required(restarted.get(closed.capaPath(), author), "status"));
                String persistedCapa=closed.capaPath().substring(closed.capaPath().lastIndexOf('/')+1);
                var persistedPage=restarted.get(casePath+"/capas?page=0&size=25",author);
                page(persistedPage,0,25,1,persistedCapa);
                assertEquals(restarted.get(closed.capaPath(),author),persistedPage.path("items").get(0));
                assertEquals("CLOSED",required(persistedPage.path("items").get(0),"status"));
                assertFalse(required(persistedPage.path("items").get(0),"evidenceId").isBlank());
                var content = restarted.download(closed.contentPath(), author);
                assertEquals(200, content.statusCode());
                assertArrayEquals(closed.bytes(), content.body());
                assertEquals(closed.workflowAction(), f.sql.queryForObject(
                        "SELECT workflow_action_id FROM hidra_hse_t17_case_close_receipt WHERE closure_id=?",
                        String.class, closed.closureId()));
                assertTrue(history.path("total").asInt() >= 9);
                JsonNode event = null;
                for (var item : history.path("items")) {
                    if ("ASSESS_IMPACT".equals(item.path("operation").asText())) event = item;
                }
                assertNotNull(event);
                assertEquals("ASSESS_IMPACT", event.path("operation").asText());
                assertEquals(f.author, event.path("actorId").asText());
                assertEquals(assessmentId, event.path("referenceId").asText());
                assertEquals(assessmentId, restarted.json("POST", casePath + "/impact-assessments",
                        author, command, 200).path("assessmentId").asText());
            }
        }
    }

    private record ClosedJourney(String casePath, String capaPath, String contentPath,
                                 String closureId, String workflowAction, byte[] bytes) { }

    private static ClosedJourney completeClosure(Gz2SignedWorkflowFixture f, Gz2SignedWorkflowFixture.Runtime runtime,
            String casePath, String assessment, String author, String documentType, String classification,
            String linkRole, Gz2SignedWorkflowFixture.Review capaReview,
            Gz2SignedWorkflowFixture.Review caseReview, String actionType) throws Exception {
        String reviewer = runtime.login(f.reviewer);
        String foreign = runtime.login(f.foreign);
        String caseId = casePath.substring(casePath.lastIndexOf('/') + 1);
        var proposed = runtime.json("POST", casePath + "/capas", author, java.util.Map.of(
                "requestId", id(), "expectedCaseVersion", 1, "actionNumber", "CAPA-" + id(),
                "actionTypeId", actionType, "title", "Synthetic corrective action", "ownerActorId", f.author), 201);
        String capaId = required(proposed.path("value"), "id");
        String capaPath = casePath + "/capas/" + capaId;
        var capaApproval = approveWorkflow(f, runtime, author, reviewer, foreign, capaReview, capaId);
        denied(runtime.send("POST", capaPath + "/approve", author,
                action(0, capaApproval, null, null)).statusCode());
        assertEquals("APPROVED", required(runtime.json("POST", capaPath + "/approve", reviewer,
                action(0, capaApproval, null, null), 200).path("value"), "status"));
        runtime.json("POST", capaPath + "/assign", author, action(1, f.author, null, null), 200);

        String documents = "/api/v1/documents/companies/" + f.company + "/facilities/" + f.asset
                + "/controlled-documents";
        String docId = required(runtime.json("POST", documents, author, java.util.Map.of(
                "requestId", id(), "code", "HSE-" + id(), "titleFr", "Preuve GZ2",
                "documentTypeId", documentType, "classificationId", classification), 201)
                .path("document"), "id");
        byte[] bytes = "Synthetic GZ2 CAPA evidence".getBytes(java.nio.charset.StandardCharsets.UTF_8);
        String versions = documents + "/" + docId + "/versions";
        var upload = runtime.multipart(versions + "/upload", author, java.util.Map.of(
                "documentId", docId, "versionNumber", 1, "versionLabel", "v1",
                "titleFr", "Preuve GZ2", "uploadedByActorId", f.author), bytes);
        assertEquals(200, upload.statusCode(), new String(upload.body(), java.nio.charset.StandardCharsets.UTF_8));
        var document = new com.fasterxml.jackson.databind.ObjectMapper().readTree(upload.body());
        String version = required(document, "id");
        denied(runtime.send("POST", capaPath + "/evidence", author,
                action(2, docId, version, null)).statusCode());
        var accepted = runtime.json("POST", versions + "/" + version + "/accept", reviewer,
                java.util.Map.of("requestId", id(), "reason", "Independent evidence acceptance",
                        "linkRoleId", linkRole), 201);
        String acceptance = required(accepted, "id");
        assertNotEquals(acceptance, required(accepted, "linkId"));
        String digest = java.util.HexFormat.of().formatHex(java.security.MessageDigest.getInstance("SHA-256").digest(bytes));
        assertEquals(digest, required(accepted, "checksumSha256"));
        denied(runtime.send("POST", capaPath + "/evidence", author,
                action(2, docId, id(), null)).statusCode());
        String evidence = required(runtime.json("POST", capaPath + "/evidence", author,
                action(2, docId, version, null), 200).path("value"), "evidenceId");
        assertEquals(acceptance, f.sql.queryForObject(
                "SELECT acceptance_id FROM hidra_hse_t17_capa_evidence WHERE id=?", String.class, evidence));
        runtime.json("POST", capaPath + "/complete", author, action(3, null, null, null), 200);
        denied(runtime.send("POST", capaPath + "/verify", author,
                action(4, evidence, null, "EFFECTIVE")).statusCode());
        assertEquals("VERIFIED", required(runtime.json("POST", capaPath + "/verify", reviewer,
                action(4, evidence, null, "EFFECTIVE"), 200).path("value"), "status"));
        String closureApproval = approveWorkflow(f, runtime, author, reviewer, foreign, caseReview, caseId);
        long head = runtime.get(casePath + "/history", author).path("total").asLong();
        denied(runtime.send("POST", casePath + "/close", author,
                action(head, capaApproval, null, null)).statusCode());
        assertEquals(0L, f.sql.queryForObject(
                "SELECT count(*) FROM hidra_hse_t17_case_close_receipt WHERE case_id=?", Long.class, caseId));
        var close = action(head, closureApproval, null, null);
        f.sql.execute("""
                CREATE FUNCTION public.t17_test_reject_close() RETURNS trigger LANGUAGE plpgsql AS $$
                BEGIN RAISE EXCEPTION 'Synthetic storage fault after receipt insert'; END $$;
                CREATE TRIGGER t17_test_reject_close AFTER INSERT ON public.hidra_hse_t17_case_close_receipt
                FOR EACH ROW EXECUTE FUNCTION public.t17_test_reject_close()
                """);
        try {
            assertTrue(runtime.send("POST", casePath + "/close", author, close).statusCode() >= 400);
            assertEquals("OPEN", required(runtime.get(casePath, author), "status"));
            assertEquals("VERIFIED", required(runtime.get(capaPath, author), "status"));
            assertEquals(head, runtime.get(casePath + "/history", author).path("total").asLong());
            assertEquals(0L, f.sql.queryForObject(
                    "SELECT count(*) FROM hidra_hse_closure WHERE hse_case_id=?", Long.class, caseId));
        } finally {
            f.sql.execute("DROP TRIGGER t17_test_reject_close ON public.hidra_hse_t17_case_close_receipt");
            f.sql.execute("DROP FUNCTION public.t17_test_reject_close()");
        }
        String closureId = required(runtime.json("POST", casePath + "/close", author, close, 200)
                .path("value"), "closureId");
        assertEquals(assessment, f.sql.queryForObject(
                "SELECT assessment_id FROM hidra_hse_t17_case_close_receipt WHERE closure_id=?", String.class, closureId));
        assertEquals(closureApproval, f.sql.queryForObject(
                "SELECT workflow_action_id FROM hidra_hse_t17_case_close_receipt WHERE closure_id=?", String.class, closureId));
        return new ClosedJourney(casePath, capaPath, versions + "/" + version + "/content",
                closureId, closureApproval, bytes);
    }

    private static String approveWorkflow(Gz2SignedWorkflowFixture f, Gz2SignedWorkflowFixture.Runtime runtime,
            String author, String reviewer, String foreign, Gz2SignedWorkflowFixture.Review review, String target)
            throws Exception {
        String base = "/api/v1/workflow";
        var start = new java.util.HashMap<String, Object>(java.util.Map.of(
                "definitionId", review.definition(), "definitionVersion", 1, "workflowPurposeId", review.purpose(),
                "targetModule", "hse", "targetTypeId", review.type(), "targetId", target,
                "currentStepId", review.step(), "startedByActorId", f.author, "correlationId", id()));
        var foreignStart = new java.util.HashMap<>(start);
        foreignStart.put("startedByActorId", f.foreign);
        denied(runtime.send("POST", base + "/instances", foreign, foreignStart).statusCode());
        String instance = required(runtime.json("POST", base + "/instances", author, start, 200), "id");
        String task = required(runtime.json("POST", base + "/tasks", author, java.util.Map.of(
                "instanceId", instance, "stepId", review.step(), "assignedActorId", f.reviewer,
                "taskLabelSnapshot", "Independent synthetic review"), 200), "id");
        String taskPath = base + "/tasks/" + task;
        denied(runtime.send("GET", taskPath, foreign, null).statusCode());
        String updatedAt = required(runtime.get(taskPath, reviewer), "updatedAt");
        var request = java.util.Map.of("expectedTaskUpdatedAt", updatedAt, "correlationId", id());
        String transition = taskPath + "/transitions/" + review.transition() + "/execute";
        f.sql.update("""
                UPDATE hidra_identity_user_permission_grant SET valid_to=now()-interval '1 second'
                WHERE user_id=? AND permission_id=(SELECT id FROM hidra_identity_permission
                    WHERE code='hse:capa:review') AND scope_type='FACILITY' AND valid_to>now()
                """, f.reviewer);
        denied(runtime.send("POST", transition, reviewer, request).statusCode());
        assertEquals(0L, f.sql.queryForObject(
                "SELECT count(*) FROM hidra_workflow_action WHERE instance_id=?", Long.class, instance));
        f.permit(f.reviewer, "hse:capa:review", "HSE_CAPA", "FACILITY", f.asset);
        var result = runtime.json("POST", transition, reviewer, request, 200);
        assertEquals("APPROVE", required(result, "decision"));
        assertEquals("COMPLETED", required(result, "instanceStatus"));
        return required(result, "actionId");
    }

    private static java.util.Map<String, Object> action(long version, String reference, String supporting, String finding) {
        var payload = new java.util.HashMap<String, Object>();
        payload.put("requestId", id());
        payload.put("expectedVersion", version);
        payload.put("reason", "Synthetic HSE qualification");
        if (reference != null) payload.put("referenceId", reference);
        if (supporting != null) payload.put("supportingId", supporting);
        if (finding != null) payload.put("finding", finding);
        return payload;
    }

    private static String required(JsonNode value, String key) {
        String result = value.path(key).asText("");
        assertFalse(result.isBlank(), () -> "Missing " + key + " in " + value);
        return result;
    }

    private static java.util.Map<String, Object> impact(String key, long revision, String type, String description) {
        return java.util.Map.of("requestId", key, "expectedCaseVersion", revision,
                "impactDomain", "OPERATIONS", "impactTypeId", type, "severity", "LOW",
                "description", description);
    }
}
