/**
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 * @Name        : Gz2RiskAssessmentGovernanceNativeHttpPostgresIntegrationTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-11-17
 * @Type        : Class
 * @Layer       : Test
 * @Module      : qualification
 * @Package     : dz.sh.hidra.qualification
 * @Description : Qualifies signed scoped Risk provenance, independent decisions and immutable reads on PostgreSQL.
 */
package dz.sh.hidra.qualification;

import com.fasterxml.jackson.databind.JsonNode;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.security.MessageDigest;
import java.util.HexFormat;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Test;
import org.testcontainers.containers.PostgreSQLContainer;
import static org.junit.jupiter.api.Assertions.*;

class Gz2RiskAssessmentGovernanceNativeHttpPostgresIntegrationTest {
    private static String id(){return Gz2SignedWorkflowFixture.id();}
    private static String field(JsonNode json,String name) {
        assertTrue(json.hasNonNull(name),"Missing "+name+": "+json);
        return json.get(name).asText();
    }
    private static void denied(Gz2SignedWorkflowFixture.Runtime runtime,String method,
            String path,String bearer,Object body) throws Exception {
        int status=runtime.send(method,path,bearer,body).statusCode();
        assertTrue(status==400||status==403||status==404||status==409||status==422,
                ()->"Protected request unexpectedly accepted: "+method+" "+path+" status "+status);
    }

    /** Bulk synthetic Audit-owned evidence only for physical row/byte limit tests, never native owner admission. */
    private static void bulkAuditLimitFixture(Gz2SignedWorkflowFixture f,String sourceEvent,
            String key,Instant start,int rows) {
        f.sql.update("""
            INSERT INTO public.hidra_audit_event
            SELECT (jsonb_populate_record(NULL::public.hidra_audit_event,
                to_jsonb(e) || jsonb_build_object(
                    'id', ?::text||lpad(n::text,70,'e'),
                    'target_id', ?::text||lpad(n::text,109,'r'),
                    'workflow_action_id', ?::text||lpad(n::text,109,'w'),
                    'occurred_at', ?::timestamptz + n * interval '1 microsecond',
                    'recorded_at', ?::timestamptz + n * interval '1 microsecond'
                ))).*
            FROM public.hidra_audit_event e CROSS JOIN generate_series(1,?) n
            WHERE e.id=?
            """,key,key,key,java.sql.Timestamp.from(start),
                java.sql.Timestamp.from(start),rows,sourceEvent);
        f.sql.update("""
            INSERT INTO public.hidra_audit_t28_event_scope
              (event_id,company_id,facility_id,event_type,workflow_action_id,occurred_at)
            SELECT id,?,?,'RISK_ASSESSMENT_APPROVED',workflow_action_id,occurred_at
            FROM public.hidra_audit_event
            WHERE id LIKE ? AND occurred_at >= ? AND occurred_at < ?
            """,f.company,f.asset,key+"%",java.sql.Timestamp.from(start),
                java.sql.Timestamp.from(start.plus(1,ChronoUnit.MINUTES)));
    }

    @Test void approvesScopedRiskAndReadsImmutableDecisionHistory() throws Exception {
        try(var postgres=new PostgreSQLContainer<>("postgres:16")
                .withDatabaseName("hidra_gz2_t22")) {
            postgres.start();
            var f=new Gz2SignedWorkflowFixture(postgres);
            var review=f.riskReview();
            String registerType=f.catalog("risk","RISK_REGISTER_TYPE","T22_GAS_REGISTER");
            String assessmentType=f.catalog("risk","RISK_ASSESSMENT_TYPE","T22_GAS_RISK");
            String method=f.catalog("risk","RISK_METHODLOGY","T22_MATRIX");
            String matrixType=f.catalog("risk","RISK_MATRIX_TYPE","T22_MATRIX");
            String likelihood=f.catalog("risk","RISK_LIKELIHOOD_LEVEL","T22_POSSIBLE");
            String consequence=f.catalog("risk","RISK_CONSEQUENCE_LEVEL","T22_CONSEQUENCE");
            String rating=f.catalog("risk","RISK_RATING","T22_RATING");
            String scenarioType=f.catalog("risk","RISK_SCENARIO_TYPE","T22_GAS_SCENARIO");
            String register=id(), matrix=id(),cell=id(),alarm=id(),scenario=id();
            // Assessment persistence references the concrete Risk scenario table.
            // Both the Risk-owned scenario and its cataloged type must be seeded.
            f.sql.update("""
                INSERT INTO hidra_risk_scenario(id,code,name_fr,scenario_type_id,
                    active,created_at,updated_at)
                VALUES (?, ?, 'Synthetic GZ2 Risk scenario', ?, true, now(), now())
                """,scenario,"GZ2-"+scenario,scenarioType);
            f.sql.update("""
                INSERT INTO hidra_risk_register(id,code,name_fr,register_type_id,
                    scope_type,scope_id,status,created_at,updated_at)
                VALUES (?,?,'Synthetic GZ2 facility risk',?,'FACILITY',?,'DRAFT',now(),now())
                """,register,"GZ2-"+register,registerType,f.asset);
            f.sql.update("""
                INSERT INTO hidra_risk_matrix(id,code,name_fr,matrix_type_id,version,status,
                    created_at,updated_at)
                VALUES (?,?,'Synthetic inherent Risk matrix',?,'t22-v1','ACTIVE',now(),now())
                """,matrix,"GZ2-"+matrix,matrixType);
            // The matrix cell uses a concrete Risk rating entity, not merely its
            // independently validated catalog entry. Seed both with one ID so
            // PostgreSQL's same-owner FK remains enforced in the synthetic journey.
            f.sql.update("""
                INSERT INTO hidra_risk_rating(
                    id,code,name_fr,severity_order,requires_treatment,
                    requires_approval,active,created_at,updated_at)
                VALUES (?,'T22_RATING','Synthetic GZ2 Risk rating',1,false,true,true,now(),now())
                """,rating);
            f.sql.update("""
                INSERT INTO hidra_risk_matrix_cell(id,risk_matrix_id,likelihood_level_id,
                    consequence_level_id,score_value,rating_id,requires_treatment,
                    requires_approval,requires_executive_acceptance,created_at,updated_at)
                VALUES (?,?,?,?,17.25,?,false,true,false,now(),now())
                """,cell,matrix,likelihood,consequence,rating);
            String alarmType=f.catalog("alarm","ALARM_TYPE","T22_TEST_ALARM");
            String severity=f.catalog("alarm","ALARM_SEVERITY","T22_TEST_SEVERITY");
            f.sql.update("""
                INSERT INTO hidra_alarm(id,alarm_number,alarm_type_id,severity_id,title_fr,
                    source_type,topology_asset_type_code,topology_asset_id,topology_asset_code,
                    current_state,raised_at,last_updated_at,created_at,updated_at)
                VALUES (?,?,?,?,?,'MANUAL','FACILITY',?,?,'ACTIVE',now(),now(),now(),now())
                """,alarm,"T22-"+alarm,alarmType,severity,"GZ2 synthetic observation",f.asset,f.asset);
            for(String principal:List.of(f.author,f.reviewer,f.operator,f.reader)) {
                f.assetGrant(principal,"risk:assessments:read",f.company,f.asset);
            }
            f.assetGrant(f.author,"risk:assessments:author",f.company,f.asset);
            f.assetGrant(f.reviewer,"risk:assessments:review",f.company,f.asset);
            f.assetGrant(f.operator,"risk:assessments:review",f.company,f.asset);
            f.assetGrant(f.author,"alarm:alarms:read",f.company,f.asset);
            f.assetGrant(f.foreign,"risk:assessments:read",f.foreignCompany,f.asset);
            f.assetGrant(f.wrongAsset,"risk:assessments:read",f.company,f.secondAsset);
            f.assetGrant(f.revoked,"risk:assessments:read",f.company,f.asset);
            f.assetGrant(f.reader,"audit:scoped-evidence:read",f.company,f.asset);
            f.assetGrant(f.reader,"audit:scoped-evidence:export",f.company,f.asset);
            f.assetGrant(f.revoked,"audit:scoped-evidence:read",f.company,f.asset);
            f.assetGrant(f.revoked,"audit:scoped-evidence:export",f.company,f.asset);
            f.assetGrant(f.author,"audit:scoped-evidence:read",f.company,f.asset);
            f.global(f.author,"risk:assessments:execute","risk:evidence:execute",
                    "workflow:instances:execute","workflow:tasks:execute");
            // Both reviewers must pass the Risk route gate; the final Workflow
            // approval still requires its independent actor and current scope.
            f.global(f.reviewer,"risk:assessments:execute",
                    "workflow:tasks:execute","workflow:tasks:read");
            f.global(f.operator,"risk:assessments:execute",
                    "workflow:tasks:execute","workflow:tasks:read");
            String[] frozenAfterRestart=new String[3];
            try(var runtime=f.start()) {
                String author=runtime.login(f.author),reviewer=runtime.login(f.reviewer);
                String approver=runtime.login(f.operator),reader=runtime.login(f.reader);
                String foreign=runtime.login(f.foreign),wrong=runtime.login(f.wrongAsset);
                String revoked=runtime.login(f.revoked);
                String create="/api/v1/risk/assessments";
                Map<String,Object> request=Map.ofEntries(
                    Map.entry("riskRegisterId",register),Map.entry("assessmentNumber","T22-"+id()),
                    Map.entry("title","GZ2 synthetic scoped assessment"),
                    Map.entry("assessmentTypeId",assessmentType),
                    Map.entry("methodologyId",method),Map.entry("riskScenarioId",scenario),
                    Map.entry("assessedByActorId",f.author),
                    Map.entry("scopes",List.of(Map.of("scopeType","FACILITY","scopeId",f.asset,
                            "included",true))));
                var created=runtime.json("POST",create,author,request,200);
                String assessment=field(created,"id"),base=create+"/"+assessment;
                denied(runtime,"GET",base,foreign,null);
                denied(runtime,"GET",base,wrong,null);
                denied(runtime,"POST",create,foreign,request);
                denied(runtime,"POST","/api/v1/risk/evidence",author,Map.of(
                        "riskAssessmentId",assessment,"evidenceModule","alarm",
                        "evidenceType","Alarm","evidenceId",f.secondAsset));
                String evidence="/api/v1/risk/evidence";
                assertEquals(200,runtime.send("POST",evidence,author,Map.of(
                        "riskAssessmentId",assessment,"evidenceModule","alarm",
                        "evidenceType","Alarm","evidenceId",alarm)).statusCode());
                var scored=runtime.json("POST",base+"/score",author,
                        Map.of("assessmentId",assessment,"inherentCellId",cell),200);
                assertEquals("17.25",scored.path("inherentScore").decimalValue().stripTrailingZeros().toPlainString());
                var submitted=runtime.json("POST",base+"/submit",author,null,200);
                assertEquals("UNDER_REVIEW",field(submitted,"status"));
                JsonNode before=runtime.get(base,reader);
                String digest=field(before,"submissionDigest");
                assertEquals(64,digest.length());
                assertEquals(cell,field(before,"inherentCellId"));
                assertEquals("t22-v1",field(before,"inherentMatrixVersion"));
                var rejectedScore=runtime.json("POST",base+"/score",author,
                        Map.of("assessmentId",assessment,"inherentCellId",cell),422);
                assertEquals("RISK_INVALID_OPERATION",field(rejectedScore,"code"));
                denied(runtime,"POST",evidence,author,Map.of("riskAssessmentId",assessment,
                        "evidenceModule","alarm","evidenceType","Alarm","evidenceId",alarm));
                String instance=field(runtime.json("POST","/api/v1/workflow/instances",author,Map.of(
                        "definitionId",review.definition(),"definitionVersion",1,
                        "workflowPurposeId",review.purpose(),"targetModule","risk",
                        "targetTypeId",review.type(),"targetId",assessment,
                        "targetCodeSnapshot",field(created,"assessmentNumber"),
                        "currentStepId",review.firstStep(),"startedByActorId",f.author),200),"id");
                String initialTask=field(runtime.json("POST","/api/v1/workflow/tasks",author,
                        Map.of("instanceId",instance,"stepId",review.firstStep(),
                                "assignedActorId",f.reviewer,"taskLabelSnapshot","Synthetic T22 reviewer"),200),"id");
                String firstTaskPath="/api/v1/workflow/tasks/"+initialTask;
                JsonNode loaded=runtime.get(firstTaskPath,reviewer);
                JsonNode first=runtime.json("POST",firstTaskPath+"/transitions/"
                        +review.firstTransition()+"/execute",reviewer,
                        Map.of("expectedTaskUpdatedAt",field(loaded,"updatedAt")),200);
                String reviewAction=field(first,"actionId");
                String next=field(first,"nextTaskId");
                String approvalTaskPath="/api/v1/workflow/tasks/"+next;
                JsonNode approvalTask=runtime.get(approvalTaskPath,approver);
                Map<String,Object> approval=Map.ofEntries(
                        Map.entry("assessmentId",assessment),Map.entry("workflowInstanceId",instance),
                        Map.entry("taskId",next),Map.entry("transitionId",review.finalTransition()),
                        Map.entry("reviewActionId",reviewAction),
                        Map.entry("expectedTaskUpdatedAt",field(approvalTask,"updatedAt")));
                // Neither a generic terminal decision nor a stale approval may publish Risk.
                denied(runtime,"POST",approvalTaskPath+"/transitions/"
                        +review.finalTransition()+"/execute",approver,
                        Map.of("expectedTaskUpdatedAt",field(approvalTask,"updatedAt")));
                // Reviewer has route permission, but cannot approve their own review.
                var rejectedSelfApproval=runtime.json("POST",base+"/approve",reviewer,approval,400);
                assertEquals("INVALID_REQUEST",field(rejectedSelfApproval,"code"));
                assertEquals(0L,f.sql.queryForObject(
                        "SELECT count(*) FROM hidra_risk_governed_approval WHERE assessment_id=?",
                        Long.class,assessment));
                // T28 fault injection: real finalization, Workflow and Audit must roll back together.
                f.sql.execute("""
                    CREATE FUNCTION t28_test_reject() RETURNS trigger LANGUAGE plpgsql AS $$
                    BEGIN RAISE EXCEPTION 'Synthetic binding failure' USING ERRCODE='23514'; END $$;
                    """);
                f.sql.execute("""
                    CREATE TRIGGER t28_test_insert BEFORE INSERT ON hidra_audit_t28_event_scope
                    FOR EACH ROW EXECUTE FUNCTION t28_test_reject()
                    """);
                try {
                    var failed=runtime.send("POST",base+"/approve",approver,approval);
                    assertTrue(failed.statusCode()>=400);
                    assertEquals(0L,f.sql.queryForObject(
                        "SELECT count(*) FROM hidra_audit_t28_event_scope",Long.class));
                    assertEquals(0L,f.sql.queryForObject(
                        "SELECT count(*) FROM hidra_risk_governed_approval WHERE assessment_id=?",
                        Long.class,assessment));
                    assertEquals(0L,f.sql.queryForObject(
                        "SELECT count(*) FROM hidra_audit_event WHERE target_id=? AND action_code='APPROVE_RISK_ASSESSMENT'",
                        Long.class,assessment));
                } finally {
                    f.sql.execute("DROP TRIGGER t28_test_insert ON hidra_audit_t28_event_scope");
                    f.sql.execute("DROP FUNCTION t28_test_reject()");
                }
                var accepted=runtime.json("POST",base+"/approve",approver,approval,200);
                assertEquals("APPROVED",field(accepted,"status"));
                // A17: exact scoped owner event is the only exportable Audit evidence.
                assertEquals(1L,f.sql.queryForObject(
                        "SELECT count(*) FROM hidra_audit_t28_event_scope WHERE company_id=? AND facility_id=?",
                        Long.class,f.company,f.asset));
                var binding=f.sql.queryForMap("""
                    SELECT event_id,workflow_action_id,occurred_at FROM hidra_audit_t28_event_scope
                    WHERE company_id=? AND facility_id=?
                    """,f.company,f.asset);
                assertEquals(String.valueOf(binding.get("workflow_action_id")),f.sql.queryForObject(
                        "SELECT workflow_action_id FROM hidra_audit_event WHERE id=?",
                        String.class,binding.get("event_id")));
                String eventId=(String)binding.get("event_id");
                Instant occurred=((java.sql.Timestamp)binding.get("occurred_at")).toInstant();
                String from=occurred.minus(1,ChronoUnit.HOURS).toString();
                String to=occurred.plus(1,ChronoUnit.HOURS).toString();
                String auditPath="/api/v1/audit/companies/"+f.company+"/facilities/"+f.asset;
                String filter="?eventType=RISK_ASSESSMENT_APPROVED&from="+from+"&to="+to;
                JsonNode eventPage=runtime.get(auditPath+"/events"+filter,reader);
                assertEquals(1,eventPage.path("items").size());
                assertEquals(eventId,field(eventPage.path("items").get(0),"eventId"));
                assertFalse(eventPage.toString().contains("payloadJson"));
                assertEquals(0,runtime.get(auditPath+"/events?eventType=RISK_ASSESSMENT_APPROVED&from="
                    +from+"&to="+occurred,reader).path("items").size());
                assertEquals(1,runtime.get(auditPath+"/events?eventType=RISK_ASSESSMENT_APPROVED&from="
                    +occurred+"&to="+to,reader).path("items").size());
                assertEquals(1,runtime.get(auditPath+"/events"+filter,author).path("items").size());
                denied(runtime,"GET",auditPath+"/events"+filter,foreign,null);
                denied(runtime,"GET","/api/v1/audit/companies/"+f.foreignCompany+
                        "/facilities/"+f.asset+"/events"+filter,reader,null);
                denied(runtime,"GET","/api/v1/audit/companies/"+f.company+
                        "/facilities/"+f.secondAsset+"/events"+filter,reader,null);
                // Current grant does not disclose a protected facility association to outsiders.
                assertEquals(400,runtime.send("GET",auditPath+"/events"+filter+
                        "&limit=101",reader,null).statusCode());
                assertEquals(400,runtime.send("GET",auditPath+
                        "/events?eventType=OTHER&from="+from+"&to="+to,reader,null).statusCode());
                assertEquals(400,runtime.send("GET",auditPath+
                        "/events?eventType=RISK_ASSESSMENT_APPROVED&from="+
                        occurred.minus(32,ChronoUnit.DAYS)+"&to="+to,reader,null).statusCode());
                Instant emptyStart=occurred.minus(5,ChronoUnit.DAYS).truncatedTo(ChronoUnit.SECONDS);
                String emptyFrom=emptyStart.toString(),emptyTo=emptyStart.plus(1,ChronoUnit.MINUTES).toString();
                assertEquals(0,runtime.get(auditPath+
                        "/events?eventType=RISK_ASSESSMENT_APPROVED&from="+emptyFrom+"&to="+emptyTo,
                        reader).path("items").size());

                String key="t28-"+id();
                Map<String,Object> criteria=Map.of("requestKey",key,"purpose","GZ2_SYNTHETIC_ASSURANCE",
                        "eventType","RISK_ASSESSMENT_APPROVED","from",from,"to",to);
                denied(runtime,"POST",auditPath+"/exports",author,criteria);
                var firstExport=runtime.json("POST",auditPath+"/exports",reader,criteria,201);
                assertEquals("COMPLETED",field(firstExport,"status"));
                var emptyExport=runtime.json("POST",auditPath+"/exports",reader,
                        Map.of("requestKey","empty-"+id(),"purpose","GZ2_SYNTHETIC_ASSURANCE",
                            "eventType","RISK_ASSESSMENT_APPROVED","from",emptyFrom,"to",emptyTo),201);
                assertEquals(0,emptyExport.path("eventCount").asInt());

                var retry=runtime.json("POST",auditPath+"/exports",reader,criteria,200);
                assertEquals(field(firstExport,"exportId"),field(retry,"exportId"));
                String exportId=field(firstExport,"exportId");
                // Race a separate exact request key: serialize by scoped PostgreSQL transaction lock.
                Map<String,Object> racing=Map.of("requestKey","t28-race-"+id(),
                        "purpose","GZ2_SYNTHETIC_ASSURANCE",
                        "eventType","RISK_ASSESSMENT_APPROVED","from",from,"to",to);
                try(var concurrent=java.util.concurrent.Executors.newFixedThreadPool(2)) {
                    var a=concurrent.submit(()->runtime.send("POST",auditPath+"/exports",reader,racing));
                    var b=concurrent.submit(()->runtime.send("POST",auditPath+"/exports",reader,racing));
                    var ra=a.get();var rb=b.get();
                    assertEquals(java.util.Set.of(200,201),java.util.Set.of(ra.statusCode(),rb.statusCode()));
                    var json=new com.fasterxml.jackson.databind.ObjectMapper();
                    assertEquals(json.readTree(ra.body()).path("exportId").asText(),
                                 json.readTree(rb.body()).path("exportId").asText());
                }

                var changed=Map.of("requestKey",key,"purpose","GZ2_SYNTHETIC_ASSURANCE",
                        "eventType","RISK_ASSESSMENT_APPROVED",
                        "from",occurred.minus(2,ChronoUnit.HOURS).toString(),"to",to);
                assertEquals(409,runtime.send("POST",auditPath+"/exports",reader,changed).statusCode());
                var frozen=runtime.get(auditPath+"/exports/"+exportId,reader);
                assertEquals(1,frozen.path("eventCount").asInt());
                var download=runtime.send("GET",auditPath+"/exports/"+exportId+"/content",reader,null);
                assertEquals(200,download.statusCode());
                byte[] bytes=download.body().getBytes(StandardCharsets.UTF_8);
                String exportDigest=HexFormat.of().formatHex(MessageDigest.getInstance("SHA-256").digest(bytes));
                assertEquals(field(frozen,"sha256"),exportDigest);
                assertEquals(bytes.length,frozen.path("byteLength").asInt());
                assertEquals(String.valueOf(bytes.length),download.headers()
                    .firstValue("content-length").orElse(""));
                assertTrue(download.headers().firstValue("content-digest").orElse("")
                    .contains(java.util.Base64.getEncoder().encodeToString(HexFormat.of().parseHex(exportDigest))));
                assertTrue(download.body().contains(eventId));
                var secondDownload=runtime.send("GET",auditPath+"/exports/"+exportId+"/content",reader,null);
                assertEquals(200,secondDownload.statusCode());
                assertEquals(download.body(),secondDownload.body());
                // Exactly two successful downloads above must each commit one ALLOW receipt.
                assertEquals(2L,f.sql.queryForObject("""
                    SELECT count(*) FROM hidra_audit_t28_access_receipt
                    WHERE actor_id=? AND action='DOWNLOAD' AND outcome='ALLOW' AND export_id=?::uuid
                    """,Long.class,f.reader,exportId));
                // A successful artifact must never leave the service if its access receipt fails.
                f.sql.execute("""
                    CREATE FUNCTION t28_test_refuse_receipt() RETURNS trigger LANGUAGE plpgsql AS $$
                    BEGIN RAISE EXCEPTION 'synthetic receipt write failed' USING ERRCODE='23514'; END $$;
                    """);
                f.sql.execute("""
                    CREATE TRIGGER t28_test_receipt BEFORE INSERT ON hidra_audit_t28_access_receipt
                    FOR EACH ROW EXECUTE FUNCTION t28_test_refuse_receipt()
                    """);
                try {
                    var deniedBytes=runtime.send("GET",auditPath+"/exports/"+exportId+"/content",reader,null);
                    assertEquals(503,deniedBytes.statusCode());
                    assertFalse(deniedBytes.body().contains(eventId));
                } finally {
                    f.sql.execute("DROP TRIGGER t28_test_receipt ON hidra_audit_t28_access_receipt");
                    f.sql.execute("DROP FUNCTION t28_test_refuse_receipt()");
                }
                // Size caps must be tested against admitted synthetic bindings in isolated windows.
                Instant rowStart=occurred.minus(10,ChronoUnit.DAYS).truncatedTo(ChronoUnit.SECONDS);
                bulkAuditLimitFixture(f,eventId,"t28rows",rowStart,1001);
                Instant byteStart=occurred.minus(8,ChronoUnit.DAYS).truncatedTo(ChronoUnit.SECONDS);
                bulkAuditLimitFixture(f,eventId,"t28byte",byteStart,900);
                for(var bound:List.of(rowStart,byteStart)) {
                    String attempt="t28-cap-"+id();
                    Map<String,Object> cap=Map.of("requestKey",attempt,
                            "purpose","GZ2_SYNTHETIC_ASSURANCE",
                            "eventType","RISK_ASSESSMENT_APPROVED",
                            "from",bound.toString(),
                            "to",bound.plus(1,ChronoUnit.MINUTES).toString());
                    assertEquals(422,runtime.send("POST",auditPath+"/exports",reader,cap).statusCode());
                    assertEquals(0L,f.sql.queryForObject(
                            "SELECT count(*) FROM hidra_audit_t28_export WHERE request_key=?",
                            Long.class,attempt));
                }

                assertFalse(download.body().contains("payloadJson"));
                frozenAfterRestart[0]=auditPath;
                frozenAfterRestart[1]=exportId;
                frozenAfterRestart[2]=download.body();

                // A later appended qualifying event does not mutate a frozen export.
                bulkAuditLimitFixture(f,eventId,"t28new",occurred.plus(1,ChronoUnit.SECONDS),1);
                assertEquals(2,runtime.get(auditPath+"/events"+filter,reader).path("items").size());
                var firstPage=runtime.get(auditPath+"/events"+filter+"&limit=1",reader);
                assertEquals(1,firstPage.path("items").size());
                String cursor=field(firstPage,"nextCursor");
                var secondPage=runtime.get(auditPath+"/events"+filter+"&limit=1&cursor="+cursor,reader);
                assertEquals(1,secondPage.path("items").size());
                assertNotEquals(field(firstPage.path("items").get(0),"eventId"),
                        field(secondPage.path("items").get(0),"eventId"));
                assertFalse(secondPage.hasNonNull("nextCursor"));

                assertEquals(download.body(),runtime.send("GET",
                        auditPath+"/exports/"+exportId+"/content",reader,null).body());

                denied(runtime,"GET",auditPath+"/exports/"+exportId,author,null);
                denied(runtime,"GET",auditPath+"/exports/"+id(),reader,null);
                f.sql.update("""
                    UPDATE hidra_identity_user_permission_grant SET valid_to=now()-interval '1 second'
                    WHERE user_id=? AND permission_id IN (
                      SELECT id FROM hidra_identity_permission WHERE code='audit:scoped-evidence:export')
                    """,f.reader);
                assertEquals(404,runtime.send("GET",auditPath+"/exports/"+exportId+"/content",reader,null)
                    .statusCode());
                assertTrue(f.sql.queryForObject("""
                    SELECT count(*) FROM hidra_audit_t28_access_receipt
                    WHERE actor_id=? AND action='DOWNLOAD' AND outcome='DENY' AND export_id IS NULL
                    """,Long.class,f.reader)>0);
                assertThrows(org.springframework.dao.DataAccessException.class,()->
                    f.sql.update("UPDATE hidra_audit_t28_event_scope SET facility_id=? WHERE event_id=?",
                            f.secondAsset,eventId));
                assertThrows(org.springframework.dao.DataAccessException.class,()->
                    f.sql.update("UPDATE hidra_audit_t28_export SET byte_length=0 WHERE id=?::uuid",exportId));
                assertThrows(org.springframework.dao.DataAccessException.class,()->
                    f.sql.update("UPDATE hidra_audit_t28_access_receipt SET outcome='DENY' WHERE export_id=?::uuid",exportId));
                JsonNode finalRead=runtime.get(base,reader);
                assertEquals("APPROVED",field(finalRead,"status"));
                assertEquals(digest,field(finalRead,"submissionDigest"));
                assertEquals(alarm,field(finalRead,"alarmId"));
                JsonNode history=runtime.get(base+"/decision-history",reader);
                assertTrue(history.isArray());assertEquals(1,history.size());
                assertEquals(reviewAction,field(history.get(0),"reviewActionId"));
                assertEquals(f.reviewer,field(history.get(0),"reviewerId"));
                assertEquals(f.operator,field(history.get(0),"approverId"));
                assertEquals(digest,field(history.get(0),"submissionDigest"));
                denied(runtime,"GET",base+"/decision-history",foreign,null);
                denied(runtime,"GET",base+"/decision-history",wrong,null);
                f.sql.update("""
                    UPDATE hidra_identity_user_permission_grant SET valid_to=now()-interval '1 second'
                    WHERE user_id=? AND permission_id IN (
                        SELECT id FROM hidra_identity_permission WHERE code='risk:assessments:read')
                    """,f.revoked);
                denied(runtime,"GET",base,revoked,null);
                assertThrows(org.springframework.dao.DataAccessException.class,()->
                    f.sql.update("UPDATE hidra_risk_governed_submission SET digest=? WHERE assessment_id=?",
                            "0".repeat(64),assessment));
                assertThrows(org.springframework.dao.DataAccessException.class,()->
                    f.sql.update("DELETE FROM hidra_risk_governed_approval WHERE assessment_id=?",assessment));
                assertEquals(digest,field(runtime.get(base,reader),"submissionDigest"));
            }
            // New signed process reads exactly the already-frozen original bytes.
            f.assetGrant(f.reader,"audit:scoped-evidence:export",f.company,f.asset);
            try(var restarted=f.start()) {
                String renewedReader=restarted.login(f.reader);
                var restored=restarted.send("GET",frozenAfterRestart[0]+"/exports/"+
                        frozenAfterRestart[1]+"/content",renewedReader,null);
                assertEquals(200,restored.statusCode());
                assertEquals(frozenAfterRestart[2],restored.body());
            }
        }
    }
}
