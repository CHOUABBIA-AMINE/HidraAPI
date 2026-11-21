/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : Gz2SignedWorkflowNativeHttpPostgresIntegrationTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-11-15
 *
 * @Type        : Class
 * @Layer       : Test
 * @Module      : qualification
 * @Package     : dz.sh.hidra.qualification
 *
 * @Description : Verifies the signed GZ2 journey and durable owner evidence across an application restart.
 *
 */
package dz.sh.hidra.qualification;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.junit.jupiter.api.Test;
import org.testcontainers.containers.PostgreSQLContainer;

import java.net.http.HttpResponse;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Map;

import static dz.sh.hidra.qualification.Gz2SignedWorkflowFixture.id;
import static dz.sh.hidra.qualification.Gz2SignedWorkflowFixture.query;
import static org.junit.jupiter.api.Assertions.*;

class Gz2SignedWorkflowNativeHttpPostgresIntegrationTest {
    private static final ObjectMapper JSON = new ObjectMapper();
    private static final String BASELINE = "/api/v1/topology/engineering-baselines";
    private static final String NETWORK = "/api/v1/topology/engineering-networks";
    private static final String PRODUCT = "/api/v1/custody/engineering-products";
    private static final String PLAN = "/api/v1/planning";
    private static final String POINT = "/api/v1/telemetry/points/";
    private static final String ASSET_POINTS = "/api/v1/telemetry/assets/{assetType}/{assetId}/points";
    private static final String RULE = "/api/v1/monitoring/rules";
    private static final String ALARM = "/api/v1/alarm/alarms";
    private static final String LEAK = "/api/v1/leakdetection/candidates";
    private static final String INCIDENT = "/api/v1/incident/incidents";

    private record Baselines(JsonNode original, JsonNode successor, Instant asOfOriginal, Instant asOfSuccessor) { }
    private record T31Scenario(JsonNode first,JsonNode second,JsonNode third) { }
    private record Plan(String revision, String target, String instance) { }
    private record Reading(String raw, String assessment, String trusted) { }
    private record ResponseChain(String rule, String evaluation, String alarm, String candidate, String incident,
                                 Map<String, String> escalation, JsonNode decision, JsonNode timeline) { }

    @Test
    void signedGz2WorkflowSurvivesSupersessionAndIndependentJvmRestart() throws Exception {
        try (var postgres = new PostgreSQLContainer<>("postgres:16")
                .withDatabaseName("hidra_gz2_qualification")
                .withCommand("postgres", "-c", "track_commit_timestamp=on", "-c", "autovacuum=off")) {
            postgres.start();
            var fixture = new Gz2SignedWorkflowFixture(postgres);
            grantEngineering(fixture);
            grantPlanning(fixture);
            grantOperatingAuthorities(fixture);
            grantScenarioInputPermissions(fixture);

            Instant start = now().minusSeconds(900);
            Instant end = start.plusSeconds(7_200);
            Baselines baselines;
            String dossierPipeline;
            T31Scenario scenario;
            T37History t37;
            T38Proof t38;
            Plan plan;
            Reading reading;
            ResponseChain response;
            JsonNode commissioned;
            long firstPid;
            try (var runtime = fixture.start()) {
                firstPid = runtime.pid();
                verifyPublishedOperations(runtime);
                String author = runtime.login(fixture.author);
                String reviewer = runtime.login(fixture.reviewer);
                String operator = runtime.login(fixture.operator);
                String producer = runtime.login(fixture.producer);

                JsonNode network = publishNetwork(fixture, runtime, author);
                JsonNode product = publishProduct(fixture, runtime, author, reviewer);
                dossierPipeline = prepareProtectedDossier(fixture);
                String dossierPath = "/api/v1/topology/pipeline-dossiers/" + dossierPipeline
                        + "/baseline?companyPartyId=" + fixture.company;
                String dossierReader = runtime.login(fixture.reader);
                JsonNode noPublication = runtime.get(dossierPath, dossierReader);
                assertEquals("UNAVAILABLE", noPublication.path("availability").asText());
                assertTrue(noPublication.path("receiptId").isNull()
                        || noPublication.path("receiptId").isMissingNode());
                var review = fixture.review("topology", "ENGINEERING_BASELINE_REVISION", "ENGINEERING_BASELINE_APPROVAL", false);
                Instant firstEffective = now();
                JsonNode baseline = publishBaseline(fixture, runtime, author, reviewer, review, network, product, firstEffective, 0);
                // T35 uses the real T07 receipt and fresh regional authorization.
                String constraint = qualifiedT36(fixture, runtime, author, reviewer, dossierReader,
                        dossierPipeline, baseline, network);
                t38 = verifyT38Lifecycle(fixture, runtime, author, reviewer, dossierReader, dossierPipeline, baseline);
                assertEquals("QUALIFIED", runtime.get(constraint, dossierReader).path("status").asText());
                JsonNode firstDossierReceipt = runtime.get(dossierPath, dossierReader);
                assertEquals(field(baseline, "receiptId"),
                        firstDossierReceipt.path("receiptId").asText());
                assertEquals("PIPELINE_SYSTEM", firstDossierReceipt.path("scopeType").asText());
                assertEquals(fixture.pipeline, firstDossierReceipt.path("pipelineSystemId").asText());
                assertEquals("AVAILABLE", firstDossierReceipt.path("availability").asText());
                assertEquals(404, runtime.send("GET", dossierPath, operator, null).statusCode());

                Instant beforeSuccessor = databaseTime(fixture);
                T31Scenario prepared=prepareScenarioInputs(fixture,runtime,author,reviewer,baseline);

                commissioned = commission(fixture, runtime, producer, start);
                plan = approvePlan(fixture, runtime, author, reviewer, field(commissioned, "pointId"), start, end);
                String point = field(commissioned, "pointId");
                String quality = prepareTelemetry(fixture, commissioned);
                producer = runtime.login(fixture.producer);
                Instant observed = now().minusSeconds(5);
                reading = trust(fixture, runtime, producer, commissioned, quality, observed);
                Reading stale = trust(fixture, runtime, producer, commissioned, quality, observed.minusSeconds(300));
                assertEquals(reading.trusted(), field(runtime.get(trustedRoute(fixture, point, reading.trusted()), producer), "id"));

                response = respond(fixture, runtime, operator, point, plan, reading, stale, start, end);
                JsonNode successor = publishBaseline(fixture, runtime, author, reviewer, review,
                        network, product, now(), 1);
                assertNotEquals(field(baseline, "receiptId"), field(successor, "receiptId"));
                JsonNode successorDossierReceipt = runtime.get(dossierPath, dossierReader);
                assertEquals(field(successor, "receiptId"),
                        successorDossierReceipt.path("receiptId").asText());
                assertNotEquals(firstDossierReceipt.path("manifestDigest").asText(),
                        "not-a-receipt-digest");
                String originalReceipt = BASELINE + "/publications/"
                        + field(baseline, "receiptId") + "?companyPartyId="
                        + fixture.company + "&pipelineSystemId=" + fixture.pipeline;
                JsonNode stillHistorical = runtime.get(originalReceipt, dossierReader);
                assertEquals(field(baseline, "receiptId"), stillHistorical.path("receiptId").asText());

                t37 = verifyAssetObservations(fixture, runtime, commissioned, reading, stale, start);
                scenario=appendTransientScenario(fixture,runtime,author,prepared,successor);
                baselines = new Baselines(baseline, successor, beforeSuccessor, databaseTime(fixture));
                System.out.printf("GZ2 phase=committed pid=%d baseline=%s successor=%s target=%s trusted=%s evaluation=%s incident=%s%n",
                        firstPid, field(baseline, "receiptId"), field(successor, "receiptId"), plan.target(), reading.trusted(),
                        response.evaluation(), response.incident());
            }

            try (var restarted = fixture.start()) {
                assertNotEquals(firstPid, restarted.pid(), "The original application JVM must be replaced");
                verifyPublishedOperations(restarted);
                readAfterRestart(fixture, restarted, baselines, commissioned, plan, reading, response);
                String dossierAfterRestart = "/api/v1/topology/pipeline-dossiers/" + dossierPipeline
                        + "/baseline?companyPartyId=" + fixture.company;
                assertEquals(field(baselines.successor(), "receiptId"),
                        restarted.get(dossierAfterRestart, restarted.login(fixture.reader))
                                .path("receiptId").asText());

                verifyCurrentScope(fixture, restarted, baselines, commissioned, plan, response);
                verifyScenarioAfterRestart(fixture,restarted,scenario,baselines);
                JsonNode restored = restarted.get(t37.route(), restarted.login(fixture.reader))
                        .path("observations").get(0);
                assertEquals(t37.trustedId(), field(restored, "trustedId"));
                assertEquals(t37.bindingId(), field(restored.path("binding"), "id"));
                var after = restarted.get(t38.evidencePath(), restarted.login(fixture.author));
                assertEquals(t38.actionId(), field(after.path("decisions").get(0), "actionId"));
                assertEquals(t38.auditId(), field(after.path("decisions").get(0), "auditEventId"));

                System.out.printf("GZ2 phase=reconstructed pid=%d originalPid=%d scope=foreign,wrong-asset,revoked%n",
                        restarted.pid(), firstPid);
            }
        }
    }

    /**
     * T35: minimal independent regional identity proof over an actual T07 system.
     * Avoid manufacturing a second baseline or changing any publication source.
     */
    private static String prepareProtectedDossier(Gz2SignedWorkflowFixture f) {
        f.sql.update("UPDATE hidra_topology_pipeline_system SET status='ACTIVE' WHERE id=?", f.pipeline);
        f.sql.update("""
                INSERT INTO hidra_topology_foundation_system_owner(pipeline_system_id,company_party_id)
                SELECT ?,? WHERE NOT EXISTS (
                    SELECT 1 FROM hidra_topology_foundation_system_owner
                    WHERE pipeline_system_id=? AND company_party_id=?)
                """, f.pipeline,f.company,f.pipeline,f.company);
        String type = id();
        String pipe = id();
        f.sql.update("""
                INSERT INTO hidra_topology_pipeline_type(id,code,active,created_at,updated_at)
                VALUES (?,?,true,now(),now())
                """,type,"T35-GZ2-"+type);
        f.sql.update("""
                INSERT INTO hidra_topology_pipeline(
                  id,pipeline_system_id,code,pipeline_type_id,status,created_at,updated_at)
                VALUES (?,?,?,?,'ACTIVE',now(),now())
                """,pipe,f.pipeline,"T35-GZ2-PIPELINE",type);
        f.sql.update("""
                INSERT INTO hidra_topology_pipeline_authoring_revision(
                  pipeline_id,revision,company_party_id,pipeline_system_id,
                  code,pipeline_type_id,actor_user_id,recorded_at)
                VALUES (?,1,?,?,?,?,?,now())
                """,pipe,f.company,f.pipeline,"T35-GZ2-PIPELINE",type,f.reader);
        for (String actor : List.of(f.reader, f.operator, f.author, f.reviewer)) {
            f.permit(actor, "topology:pipeline-dossiers:read",
                    "TOPOLOGY_PIPELINE_DOSSIERS", "GLOBAL", null);
            f.permit(actor, "topology:regional-resource:read",
                    "TOPOLOGY_REGIONAL_RESOURCE", "PIPELINE_SYSTEM", f.pipeline);
            String employee = id();
            f.sql.update("""
                    INSERT INTO hidra_org_employee(
                      id,employee_number,employee_type,status,identity_user_reference,created_at,updated_at)
                    VALUES (?,?,'PERMANENT','ACTIVE',?,now(),now())
                    """,employee,"T35-"+employee,actor);
            f.sql.update("UPDATE hidra_identity_user SET employee_reference_id=? WHERE id=?",
                    employee,actor);
            List<Long> registered = f.sql.queryForList("""
                    SELECT id FROM hidra_org_operational_scope
                    WHERE scope_type='PIPELINE_SYSTEM' AND target_id=?
                    """,Long.class,f.pipeline);
            Long scope = registered.isEmpty()
                    ? f.sql.queryForObject("""
                        INSERT INTO hidra_org_operational_scope(scope_type,target_id)
                        VALUES('PIPELINE_SYSTEM',?) RETURNING id
                        """,Long.class,f.pipeline)
                    : registered.getFirst();
            f.sql.update("""
                    INSERT INTO hidra_org_responsibility_assignment(
                      id,responsibility_type,assignee_type,assignee_id,scope_id,
                      valid_from,valid_to,status,created_at,updated_at)
                    VALUES(?,'RESPONSIBLE','EMPLOYEE',?,?,
                      now()-interval '2 hours',now()+interval '2 hours','ACTIVE',now(),now())
                    """,id(),employee,scope);
        }
        return pipe;
    }

    private record T38Proof(String evidencePath, String actionId, String auditId) { }

    private static T38Proof verifyT38Lifecycle(Gz2SignedWorkflowFixture f, Gz2SignedWorkflowFixture.Runtime r,
            String author, String reviewer, String reader, String pipeline, JsonNode baseline) throws Exception {
        String condition = "T38-NOMINAL";
        f.sql.update("""
                INSERT INTO public.hidra_topology_t36_condition_policy
                    (company_party_id,condition_code,pressure_unit,recorded_at)
                VALUES (?,?,'bar',clock_timestamp())
                """, f.company, condition);
        String topology = "/api/v1/topology/operating-constraints/pipelines/" + pipeline;
        String root = topology + "/restriction-requests";
        String receipt = field(baseline, "receiptId");
        var review = f.review("topology", "OPERATING_PRESSURE_RESTRICTION",
                "OPERATING_PRESSURE_RESTRICTION_APPROVAL", false);
        Instant initialAt = now().minusSeconds(30);
        Instant replacedAt = initialAt.plusSeconds(10);
        Instant withdrawnAt = replacedAt.plusSeconds(10);
        String first = id();
        JsonNode pending = submitT38(r, author, root, f.company, condition, receipt, first, 0,
                Map.of("kind", "INITIAL", "pressure", t38Pressure("64", initialAt)));
        assertEquals(1, pending.path("candidates").size());
        String original = field(pending.path("candidates").get(0), "candidateId");
        List<Map<String,String>> decision = List.of(t38FirstApproval(f, r, author, reviewer, review, pending));
        assertEquals("UNAVAILABLE", field(r.get(t38Effective(topology, f.company, condition, initialAt), reader), "status"));
        JsonNode published = publishT38(r, author, root, f.company, first, 0, decision);
        assertEquals(original, field(published, "revisionId"));
        assertEquals(published, publishT38(r, author, root, f.company, first, 0, decision));
        String evidencePath = query(root + "/" + first + "/evidence", "companyPartyId", f.company);
        var proof = r.get(evidencePath, author);
        assertEquals(decision.getFirst().get("actionId"), field(proof.path("decisions").get(0), "actionId"));
        assertFalse(field(proof.path("decisions").get(0), "auditEventId").isBlank());
        assertEquals(404, r.send("GET", query(root + "/" + first + "/evidence", "companyPartyId", f.company),
                reviewer, null).statusCode());
        assertEquals("QUALIFIED", field(r.get(t38Effective(topology, f.company, condition, initialAt), reader), "status"));

        String second = id();
        JsonNode replacement = submitT38(r, author, root, f.company, condition, receipt, second, 1,
                Map.of("kind", "REPLACEMENT", "predecessorId", original, "pressure", t38Pressure("55", replacedAt)));
        assertEquals(2, replacement.path("candidates").size());
        var successorApprovals = t38Approve(f, r, author, reviewer, review, replacement);
        assertEquals(409, r.send("POST", root + "/" + second + "/publication", author,
                Map.of("companyPartyId", f.company, "expectedVersion", 1, "decisions", decision)).statusCode());
        f.sql.execute("""
                CREATE FUNCTION public.t38_fixture_reject_witness() RETURNS trigger LANGUAGE plpgsql AS $$
                BEGIN
                  IF NEW.position=2 THEN RAISE EXCEPTION 'Synthetic rollback fixture' USING ERRCODE='23514'; END IF;
                  RETURN NEW;
                END $$;
                """);
        f.sql.execute("""
                CREATE TRIGGER t38_fixture_reject_witness BEFORE INSERT
                ON public.hidra_topology_pressure_decision_witness
                FOR EACH ROW EXECUTE FUNCTION public.t38_fixture_reject_witness()
                """);
        try {
            assertEquals(409, r.send("POST", root + "/" + second + "/publication", author,
                    Map.of("companyPartyId", f.company, "expectedVersion", 1, "decisions", successorApprovals)).statusCode());
        } finally {
            f.sql.execute("DROP TRIGGER t38_fixture_reject_witness ON public.hidra_topology_pressure_decision_witness");
            f.sql.execute("DROP FUNCTION public.t38_fixture_reject_witness()");
        }
        for (var approval : successorApprovals) {
            assertEquals(0, f.sql.queryForObject("SELECT count(*) FROM public.hidra_audit_event WHERE workflow_action_id=?",
                    Integer.class, approval.get("actionId")));
            assertEquals(1, f.sql.queryForObject("SELECT count(*) FROM public.hidra_workflow_action WHERE id=?",
                    Integer.class, approval.get("actionId")));
        }
        assertEquals(original, field(r.get(t38Effective(topology, f.company, condition, replacedAt), reader), "revisionId"));
        JsonNode updated = publishT38(r, author, root, f.company, second, 1, successorApprovals);
        String successor = field(updated, "revisionId");
        assertFalse(field(updated, "terminationId").isBlank());
        assertEquals(original, field(r.get(t38Effective(topology, f.company, condition, replacedAt.minusNanos(1000)), reader),
                "revisionId"));
        assertEquals(successor, field(r.get(t38Effective(topology, f.company, condition, replacedAt), reader), "revisionId"));

        String third = id();
        JsonNode withdrawal = submitT38(r, author, root, f.company, condition, receipt, third, 2,
                Map.of("kind", "WITHDRAWAL", "predecessorId", successor, "effectiveAt", withdrawnAt.toString()));
        var withdrawalApprovals = t38Approve(f, r, author, reviewer, review, withdrawal);
        publishT38(r, author, root, f.company, third, 2, withdrawalApprovals);
        assertEquals("UNAVAILABLE", field(r.get(t38Effective(topology, f.company, condition, withdrawnAt), reader), "status"));
        assertEquals(successor, field(r.get(t38Effective(topology, f.company, condition, replacedAt), reader), "revisionId"));

        String firstCompeting = id();
        String secondCompeting = id();
        var change = Map.of("kind", "INITIAL", "pressure", t38Pressure("57", withdrawnAt.plusSeconds(1)));
        var firstPending = submitT38(r, author, root, f.company, condition, receipt, firstCompeting, 3, change);
        var secondPending = submitT38(r, author, root, f.company, condition, receipt, secondCompeting, 3, change);
        var firstDecision = t38Approve(f, r, author, reviewer, review, firstPending);
        var secondDecision = t38Approve(f, r, author, reviewer, review, secondPending);
        try (var workers = java.util.concurrent.Executors.newVirtualThreadPerTaskExecutor()) {
            var start = new java.util.concurrent.CountDownLatch(1);
            var left = workers.submit(() -> {
                start.await();
                return r.send("POST", root + "/" + firstCompeting + "/publication", author,
                        Map.of("companyPartyId", f.company, "expectedVersion", 3, "decisions", firstDecision));
            });
            var right = workers.submit(() -> {
                start.await();
                return r.send("POST", root + "/" + secondCompeting + "/publication", author,
                        Map.of("companyPartyId", f.company, "expectedVersion", 3, "decisions", secondDecision));
            });
            start.countDown();
            var one = left.get();
            var two = right.get();
            assertEquals(List.of(200, 409), java.util.stream.Stream.of(one, two).map(response -> response.statusCode())
                    .sorted().toList(), () -> one.body() + " | " + two.body());
        }
        assertEquals(1, f.sql.queryForObject("""
                SELECT count(*) FROM public.hidra_topology_pressure_decision_publication WHERE request_id IN (?,?)
                """, Integer.class, java.util.UUID.fromString(firstCompeting), java.util.UUID.fromString(secondCompeting)));
        f.sql.update("""
                UPDATE public.hidra_identity_user_permission_grant SET status='REVOKED', revoked_at=clock_timestamp()
                WHERE user_id=? AND permission_id='t38_pressure_publish'
                """, f.author);
        try {
            assertEquals(404, r.send("POST", root + "/" + first + "/publication", author,
                    Map.of("companyPartyId", f.company, "expectedVersion", 0, "decisions", decision)).statusCode());
        } finally {
            f.permit(f.author, "topology:pressure-restriction:publish", "OPERATING_PRESSURE_RESTRICTION",
                    "PIPELINE_SYSTEM", f.pipeline);
        }
        assertEquals(404, r.send("GET", query(root + "/" + first, "companyPartyId", f.foreignCompany),
                author, null).statusCode());
        return new T38Proof(evidencePath, decision.getFirst().get("actionId"),
                field(proof.path("decisions").get(0), "auditEventId"));
    }

    private static Map<String,String> t38FirstApproval(Gz2SignedWorkflowFixture f, Gz2SignedWorkflowFixture.Runtime r,
            String author, String reviewer, Gz2SignedWorkflowFixture.Review review, JsonNode submission) throws Exception {
        var candidate = submission.path("candidates").get(0);
        String candidateId = field(candidate, "candidateId");
        String correlation = id();
        var start = Map.of("definitionId", review.definition(), "definitionVersion", 1,
                "workflowPurposeId", review.purpose(), "targetModule", "topology", "targetTypeId", review.type(),
                "targetId", candidateId, "targetCodeSnapshot", field(candidate, "workflowLocator"),
                "currentStepId", review.step(), "startedByActorId", f.author, "correlationId", correlation);
        String instance = field(r.json("POST", "/api/v1/workflow/instances", author, start, 200), "id");
        String tasks = "/api/v1/workflow/tasks";
        var self = r.send("POST", tasks, author, Map.of("instanceId", instance, "stepId", review.step(),
                "assignedActorId", f.author, "taskLabelSnapshot", "Synthetic self-review rejected"));
        assertTrue(self.statusCode() >= 400 && self.statusCode() < 500, self::body);
        String task = field(r.json("POST", tasks, author, Map.of("instanceId", instance, "stepId", review.step(),
                "assignedActorId", f.reviewer, "taskLabelSnapshot", "Synthetic restriction review"), 200), "id");
        String taskPath = tasks + "/" + task;
        String execute = taskPath + "/transitions/" + review.transition() + "/execute";
        var command = Map.of("expectedTaskUpdatedAt", field(r.get(taskPath, reviewer), "updatedAt"),
                "correlationId", correlation);
        f.sql.update("""
                UPDATE public.hidra_identity_user_permission_grant SET status='REVOKED', revoked_at=clock_timestamp()
                WHERE user_id=? AND permission_id='t38_pressure_review'
                """, f.reviewer);
        try {
            var denied = r.send("POST", execute, reviewer, command);
            assertTrue(denied.statusCode() == 403 || denied.statusCode() == 404, denied::body);
        } finally {
            f.permit(f.reviewer, "topology:pressure-restriction:review", "OPERATING_PRESSURE_RESTRICTION",
                    "PIPELINE_SYSTEM", f.pipeline);
        }
        var approved = r.json("POST", execute, reviewer, command, 200);
        assertEquals("APPROVE", field(approved, "decision"));
        return Map.of("candidateId", candidateId, "instanceId", instance, "taskId", task,
                "actionId", field(approved, "actionId"));
    }

    private static Map<String,String> t38Pressure(String value, Instant start) {
        return Map.of("upperPressure", value, "unit", "bar", "basis", "GAUGE", "effectiveFrom", start.toString());
    }

    private static String t38Effective(String path, String company, String condition, Instant at) {
        return query(path, "companyPartyId", company, "conditionCode", condition, "effectiveAt", at.toString());
    }

    private static JsonNode submitT38(Gz2SignedWorkflowFixture.Runtime r, String actor, String root, String company,
            String condition, String receipt, String requestId, int version, Map<String,?> change) throws Exception {
        return r.json("POST", root, actor, Map.of("requestId",requestId,"companyPartyId",company,
                "conditionCode",condition,"baselineReceiptId",receipt,"expectedVersion",version,"change",change), 200);
    }

    private static JsonNode publishT38(Gz2SignedWorkflowFixture.Runtime r, String actor, String root, String company,
            String requestId, int version, List<Map<String,String>> decisions) throws Exception {
        return r.json("POST", root + "/" + requestId + "/publication", actor,
                Map.of("companyPartyId",company,"expectedVersion",version,"decisions",decisions), 200);
    }

    private static List<Map<String,String>> t38Approve(Gz2SignedWorkflowFixture f, Gz2SignedWorkflowFixture.Runtime r,
            String author, String reviewer, Gz2SignedWorkflowFixture.Review review, JsonNode submission) throws Exception {
        var complete = new java.util.ArrayList<Map<String,String>>();
        for (JsonNode candidate : submission.path("candidates")) {
            String id = field(candidate, "candidateId");
            var reference = new java.util.HashMap<>(workflowDecision(f, r, author, reviewer, review, "topology", id,
                    field(candidate, "workflowLocator"), id()));
            reference.put("candidateId", id);
            complete.add(Map.copyOf(reference));
        }
        return List.copyOf(complete);
    }

    /**
     * T36: the restrictive pressure value is NOT derived from design pressure or
     * baseline approval. Synthetic, independent signed Workflow approval plus
     * immutable Audit evidence are joined by the protected SQL admission trigger.
     * No T36 application writer or runtime operating record is introduced.
     */
    private static String qualifiedT36(Gz2SignedWorkflowFixture f, Gz2SignedWorkflowFixture.Runtime r,
            String authorToken, String reviewerToken, String readerToken, String pipe,
            JsonNode baseline, JsonNode network) throws Exception {
        String path = query("/api/v1/topology/operating-constraints/pipelines/" + pipe,
                "companyPartyId", f.company, "conditionCode", "NOMINAL",
                "effectiveAt", now().toString());
        f.permit(f.reader, "topology:operating-constraints:read", "TOPOLOGY_OPERATING_CONSTRAINT", "GLOBAL", null);
        f.permit(f.operator, "topology:operating-constraints:read", "TOPOLOGY_OPERATING_CONSTRAINT", "GLOBAL", null);
        assertEquals("UNAVAILABLE", r.get(path, readerToken).path("status").asText());
        assertTrue(java.util.Set.of(403,404).contains(r.send("GET",path,r.login(f.foreign),null).statusCode()));

        // Explicit synthetic policy and the three read projections. The
        // migrated runtime roles must not retain default INSERT/UPDATE/DELETE.
        String tables="public.hidra_topology_t36_condition_policy,"
                +"public.hidra_topology_t36_pressure_revision,"
                +"public.hidra_topology_t36_pressure_termination,"
                +"public.hidra_topology_t36_pressure_candidate";
        f.sql.execute("REVOKE ALL ON " + tables + " FROM gz2_runtime,gz2_publication");
        f.sql.execute("GRANT SELECT ON " + tables + " TO gz2_runtime");
        f.sql.update("""
                INSERT INTO public.hidra_topology_t36_condition_policy(
                  company_party_id,condition_code,pressure_unit,recorded_at)
                VALUES (?,'NOMINAL','bar',clock_timestamp())
                """,f.company);

        f.sql.execute("GRANT SELECT ON " + tables + " TO gz2_publication");
        for (String operation : List.of("submit","publish","evidence-read")) {
            f.permit(f.author,"topology:pressure-restriction:"+operation,"OPERATING_PRESSURE_RESTRICTION","PIPELINE_SYSTEM",f.pipeline);
        }
        f.permit(f.reviewer,"topology:pressure-restriction:review","OPERATING_PRESSURE_RESTRICTION","PIPELINE_SYSTEM",f.pipeline);
        f.global(f.author,"topology:operating-constraints:execute","topology:operating-constraints:read");
        f.global(f.reviewer,"topology:operating-constraints:execute","topology:operating-constraints:read");

        String revision=id();
        Instant initialFrom=now().minusSeconds(3600);
        t36Candidate(f,pipe,"",revision,"REVISION","68",
                initialFrom,null,null,null,baseline,network);
        var approval=f.review("topology","OPERATING_PRESSURE_RESTRICTION",
                "OPERATING_PRESSURE_RESTRICTION_APPROVAL",false);
        var decision=workflowDecision(f,r,authorToken,reviewerToken,approval,
                "topology",revision,"t36-v1:"+revision,id());
        String auditType=f.catalog("audit","EVENT_TYPE","OPERATING_PRESSURE_RESTRICTION_APPROVED");
        String auditCategory=f.catalog("audit","EVENT_CATEGORY","T36_SYNTHETIC_ENGINEERING");
        String auditId=id();
        java.sql.Timestamp acted=f.sql.queryForObject(
                "SELECT acted_at FROM public.hidra_workflow_action WHERE id=?",
                java.sql.Timestamp.class,decision.get("actionId"));
        f.sql.update("""
                INSERT INTO public.hidra_audit_event(
                  id,event_type_id,event_category_id,source_module,action_code,
                  event_status,actor_id,actor_type,target_module,target_type,
                  target_id,operation,workflow_instance_id,workflow_task_id,
                  workflow_action_id,occurred_at,recorded_at)
                VALUES (?,?,?,'topology','APPROVE_OPERATING_PRESSURE_RESTRICTION',
                  'RECORDED',?,'HUMAN','topology','OPERATING_PRESSURE_RESTRICTION',
                  ?,'APPROVE',?,?,?, ?,clock_timestamp())
                """,auditId,auditType,auditCategory,f.reviewer,revision,
                decision.get("instanceId"),decision.get("taskId"),decision.get("actionId"),acted);
        // The exact pressure value was fixed before independent review.
        // Even a real approved action cannot qualify a substituted limit.
        assertThrows(org.springframework.dao.DataAccessException.class,()->
                t36Revision(f,pipe,"",revision,null,baseline,network,
                        initialFrom,null,"69",
                        new T36Witness(decision.get("actionId"),auditId,acted)),
                "Approved decision must bind the exact reviewed pressure");
        // Admission must reject an unrelated or unapproved Workflow action.
        assertThrows(org.springframework.dao.DataAccessException.class,()->f.sql.update("""
                INSERT INTO public.hidra_topology_t36_pressure_revision(
                  revision_id,company_party_id,pipeline_id,section_id,pipeline_system_id,
                  condition_code,upper_pressure,pressure_unit,pressure_basis,effective_from,
                  recorded_at,baseline_receipt_id,baseline_manifest_digest,
                  engineering_source_revision,decision_action_id,audit_event_id,
                  submitted_by,reviewed_by,approved_at)
                VALUES (?,?,?,?,?,'NOMINAL',68,'bar','GAUGE',?,
                   clock_timestamp(),?,?,?,?,?,?,?,?)
                """,java.util.UUID.fromString(id()),f.company,pipe,"",f.pipeline,
                java.sql.Timestamp.from(initialFrom),
                java.util.UUID.fromString(field(baseline,"receiptId")),
                field(baseline.path("submission"),"canonicalSha256"),
                field(network,"revisionId"),id(),auditId,f.author,f.reviewer,acted));

        f.sql.update("""
                INSERT INTO public.hidra_topology_t36_pressure_revision(
                  revision_id,company_party_id,pipeline_id,section_id,pipeline_system_id,
                  condition_code,upper_pressure,pressure_unit,pressure_basis,effective_from,
                  recorded_at,baseline_receipt_id,baseline_manifest_digest,
                  engineering_source_revision,decision_action_id,audit_event_id,
                  submitted_by,reviewed_by,approved_at)
                VALUES (?,?,?,?,?,'NOMINAL',68,'bar','GAUGE',?,
                  clock_timestamp(),?,?,?,?,?,?,?,?)
                """,java.util.UUID.fromString(revision),f.company,pipe,"",f.pipeline,
                java.sql.Timestamp.from(initialFrom),
                java.util.UUID.fromString(field(baseline,"receiptId")),
                field(baseline.path("submission"),"canonicalSha256"),
                field(network,"revisionId"),decision.get("actionId"),auditId,
                f.author,f.reviewer,acted);
        JsonNode qualified=r.get(path,readerToken);
        assertEquals("QUALIFIED",qualified.path("status").asText());
        assertEquals(revision,qualified.path("revisionId").asText());
        assertEquals(0,new java.math.BigDecimal("68").compareTo(qualified.path("upperPressure").decimalValue()));
        assertEquals("GAUGE",qualified.path("pressureBasis").asText());
        assertEquals("bar",qualified.path("pressureUnit").asText());
        assertEquals(field(baseline,"receiptId"),qualified.path("sourceBaselineReceiptId").asText());
        assertFalse(qualified.hasNonNull("auditEventId"));
        assertTrue(r.send("GET",path,readerToken,null).headers()
                .firstValue("cache-control").orElse("").contains("no-store"));
        // A canonical exact section is NOT a child-copy of its pipeline limit.
        String nodeA=id(),nodeB=id(),section=id();
        f.sql.update("""
                INSERT INTO public.hidra_topology_node(
                 id,code,node_type,status,created_at,updated_at)
                VALUES(?,?,'JUNCTION','ACTIVE',now(),now()),
                      (?,?,'JUNCTION','ACTIVE',now(),now())
                """,nodeA,nodeA,nodeB,nodeB);
        f.sql.update("""
                INSERT INTO public.hidra_topology_pipeline_segment(
                 id,pipeline_id,code,segment_type,from_node_id,to_node_id,
                 flow_direction,status,created_at,updated_at)
                VALUES(?,?,'T36-GZ2-SECTION','MAINLINE',?,?,'DIRECTED','ACTIVE',now(),now())
                """,section,pipe,nodeA,nodeB);
        // The existing pipeline-system grant and responsibility cover its active sections.
        // PIPELINE_SEGMENT is a Topology resource kind, not an Identity grant scope.
        String sectionPath=query("/api/v1/topology/operating-constraints/pipelines/" + pipe
                + "/sections/" + section,"companyPartyId",f.company,
                "conditionCode","NOMINAL","effectiveAt",now().toString());
        assertEquals("UNAVAILABLE",r.get(sectionPath,readerToken).path("status").asText(),
                "Pipeline limits do not inherit into sections");

        // The section receives a DIFFERENT signed human decision and exact Audit
        // witness. A section restriction is qualified only for its own target.
        String sectionRevision=id();
        Instant sectionFrom=now().minusSeconds(3600);
        t36Candidate(f,pipe,section,sectionRevision,"REVISION","54",
                sectionFrom,null,null,null,baseline,network);
        var sectionDecision=workflowDecision(f,r,authorToken,reviewerToken,approval,
                "topology",sectionRevision,"t36-v1:"+sectionRevision,id());
        String sectionAudit=id();
        java.sql.Timestamp sectionApproved=f.sql.queryForObject(
                "SELECT acted_at FROM public.hidra_workflow_action WHERE id=?",
                java.sql.Timestamp.class,sectionDecision.get("actionId"));
        f.sql.update("""
                INSERT INTO public.hidra_audit_event(
                 id,event_type_id,event_category_id,source_module,action_code,
                 event_status,actor_id,actor_type,target_module,target_type,target_id,
                 operation,workflow_instance_id,workflow_task_id,workflow_action_id,
                 occurred_at,recorded_at)
                VALUES(?,?,?,'topology','APPROVE_OPERATING_PRESSURE_RESTRICTION',
                  'RECORDED',?,'HUMAN','topology','OPERATING_PRESSURE_RESTRICTION',
                  ?,'APPROVE',?,?,?, ?,clock_timestamp())
                """,sectionAudit,auditType,auditCategory,f.reviewer,sectionRevision,
                sectionDecision.get("instanceId"),sectionDecision.get("taskId"),
                sectionDecision.get("actionId"),sectionApproved);
        f.sql.update("""
                INSERT INTO public.hidra_topology_t36_pressure_revision(
                  revision_id,company_party_id,pipeline_id,section_id,pipeline_system_id,
                  condition_code,upper_pressure,pressure_unit,pressure_basis,effective_from,
                  recorded_at,baseline_receipt_id,baseline_manifest_digest,
                  engineering_source_revision,decision_action_id,audit_event_id,
                  submitted_by,reviewed_by,approved_at)
                VALUES(?,?,?,?,?,'NOMINAL',54,'bar','GAUGE',?,
                  clock_timestamp(),?,?,?,?,?,?,?,?)
                """,java.util.UUID.fromString(sectionRevision),f.company,pipe,section,f.pipeline,
                java.sql.Timestamp.from(sectionFrom),
                java.util.UUID.fromString(field(baseline,"receiptId")),
                field(baseline.path("submission"),"canonicalSha256"),
                field(network,"revisionId"),sectionDecision.get("actionId"),sectionAudit,
                f.author,f.reviewer,sectionApproved);
        assertEquals("QUALIFIED",r.get(sectionPath,readerToken).path("status").asText());
        assertEquals(sectionRevision,r.get(sectionPath,readerToken).path("revisionId").asText());
        assertEquals(0,new java.math.BigDecimal("54").compareTo(
                r.get(sectionPath,readerToken).path("upperPressure").decimalValue()));
        assertEquals(revision,r.get(path,readerToken).path("revisionId").asText(),
                "Section restriction must not replace pipeline restriction");
        assertEquals(404,r.send("GET",sectionPath.replace("/pipelines/"+pipe+"/",
                "/pipelines/"+id()+"/"),readerToken,null).statusCode());

        // Two authorized restriction revisions and two separately approved
        // timeline events. A second independent restriction exposes CONFLICT,
        // without leaking either pressure value or candidate count.
        Instant changeAt=now().plusSeconds(3600);
        String successor=id();
        t36Candidate(f,pipe,"",successor,"REVISION","62",
                changeAt,null,revision,null,baseline,network);
        var nextWitness=t36Witness(f,r,authorToken,reviewerToken,approval,successor,
                "OPERATING_PRESSURE_RESTRICTION_APPROVED",
                "APPROVE_OPERATING_PRESSURE_RESTRICTION");
        t36Revision(f,pipe,"",successor,revision,baseline,network,changeAt,
                null,"62",nextWitness);
        String supersession=id();
        t36Candidate(f,pipe,"",supersession,"SUPERSEDE",null,
                changeAt,null,revision,successor,baseline,network);
        var supersessionWitness=t36Witness(f,r,authorToken,reviewerToken,approval,supersession,
                "OPERATING_PRESSURE_RESTRICTION_SUPERSEDED",
                "SUPERSEDE_OPERATING_PRESSURE_RESTRICTION");
        t36Termination(f,pipe,supersession,revision,"SUPERSEDE",successor,
                changeAt,baseline,network,supersessionWitness);
        String futurePath=query("/api/v1/topology/operating-constraints/pipelines/" + pipe,
                "companyPartyId",f.company,"conditionCode","NOMINAL",
                "effectiveAt",changeAt.plusSeconds(10).toString());
        assertEquals(successor,r.get(futurePath,readerToken).path("revisionId").asText());
        assertEquals(revision,r.get(path,readerToken).path("revisionId").asText(),
                "Supersession must not rewrite the earlier effective revision");

        String competitor=id();
        t36Candidate(f,pipe,"",competitor,"REVISION","58",
                changeAt.plusSeconds(20),changeAt.plusSeconds(120),
                null,null,baseline,network);
        var competingWitness=t36Witness(f,r,authorToken,reviewerToken,approval,competitor,
                "OPERATING_PRESSURE_RESTRICTION_APPROVED",
                "APPROVE_OPERATING_PRESSURE_RESTRICTION");
        t36Revision(f,pipe,"",competitor,null,baseline,network,changeAt.plusSeconds(20),
                changeAt.plusSeconds(120),"58",competingWitness);
        String conflictPath=query("/api/v1/topology/operating-constraints/pipelines/" + pipe,
                "companyPartyId",f.company,"conditionCode","NOMINAL",
                "effectiveAt",changeAt.plusSeconds(60).toString());
        var conflict=r.get(conflictPath,readerToken);
        assertEquals("CONFLICT",conflict.path("status").asText());
        assertTrue(conflict.path("upperPressure").isNull()
                || conflict.path("upperPressure").isMissingNode());
        assertTrue(conflict.path("revisionId").isNull()
                || conflict.path("revisionId").isMissingNode());

        String withdrawal=id();
        t36Candidate(f,pipe,"",withdrawal,"WITHDRAW",null,
                changeAt.plusSeconds(300),null,successor,null,baseline,network);
        var withdrawalWitness=t36Witness(f,r,authorToken,reviewerToken,approval,withdrawal,
                "OPERATING_PRESSURE_RESTRICTION_WITHDRAWN",
                "WITHDRAW_OPERATING_PRESSURE_RESTRICTION");
        t36Termination(f,pipe,withdrawal,successor,"WITHDRAW",null,
                changeAt.plusSeconds(300),baseline,network,withdrawalWitness);
        String withdrawnPath=query("/api/v1/topology/operating-constraints/pipelines/" + pipe,
                "companyPartyId",f.company,"conditionCode","NOMINAL",
                "effectiveAt",changeAt.plusSeconds(301).toString());
        assertEquals("UNAVAILABLE",r.get(withdrawnPath,readerToken).path("status").asText(),
                "Withdrawn restrictions must never resurrect");
        assertEquals(successor,r.get(futurePath,readerToken).path("revisionId").asText(),
                "Withdrawal must preserve earlier qualified historical resolution");

        // The operator has a route grant but not the qualified T07 source.
        assertEquals("UNAVAILABLE",r.get(path,r.login(f.operator)).path("status").asText());
        var appJdbc=new org.springframework.jdbc.core.JdbcTemplate(
                new org.springframework.jdbc.datasource.DriverManagerDataSource(
                        f.postgres.getJdbcUrl(),"gz2_runtime","synthetic-gz2-runtime"));
        assertThrows(org.springframework.dao.DataAccessException.class,()->
                appJdbc.update("DELETE FROM public.hidra_topology_t36_pressure_revision WHERE revision_id=?",
                        java.util.UUID.fromString(revision)));
        assertThrows(org.springframework.dao.DataAccessException.class,()->
                f.sql.update("UPDATE public.hidra_topology_t36_pressure_revision SET upper_pressure=10 WHERE revision_id=?",
                        java.util.UUID.fromString(revision)));
        assertThrows(org.springframework.dao.DataAccessException.class,()->
                f.sql.update("UPDATE public.hidra_topology_t36_pressure_candidate SET upper_pressure=40 WHERE candidate_id=?",
                        java.util.UUID.fromString(revision)),
                "An approved Workflow target cannot be rewritten after review");
        assertThrows(org.springframework.dao.DataAccessException.class,()->
                appJdbc.update("DELETE FROM public.hidra_topology_t36_pressure_candidate WHERE candidate_id=?",
                        java.util.UUID.fromString(revision)),
                "Runtime user cannot alter proposed engineering evidence");
        return path;
    }

    /**
     * Unapproved exact target admitted by Topology. This is a synthetic
     * database-owner fixture, never a runtime writer or implied authority.
     * Workflow and the publication insert guard independently recheck it.
     */
    private static void t36Candidate(Gz2SignedWorkflowFixture f,String pipe,String section,
            String target,String kind,String pressure,Instant from,Instant until,
            String retired,String successor,JsonNode baseline,JsonNode network) {
        f.sql.update("""
                INSERT INTO public.hidra_topology_t36_pressure_candidate(
                  candidate_id,candidate_kind,company_party_id,pipeline_id,section_id,
                  pipeline_system_id,condition_code,upper_pressure,pressure_unit,
                  pressure_basis,effective_from,effective_until,retired_revision_id,
                  successor_revision_id,baseline_receipt_id,baseline_manifest_digest,
                  engineering_source_revision,submitted_by,created_at)
                VALUES(?,?,?,?,?,?,'NOMINAL',?,?,?, ?,?,?,?, ?,?,?,?,clock_timestamp())
                """,java.util.UUID.fromString(target),kind,f.company,pipe,section,
                f.pipeline,pressure==null?null:new java.math.BigDecimal(pressure),
                pressure==null?null:"bar",pressure==null?null:"GAUGE",
                java.sql.Timestamp.from(from),
                until==null?null:java.sql.Timestamp.from(until),
                retired==null?null:java.util.UUID.fromString(retired),
                successor==null?null:java.util.UUID.fromString(successor),
                java.util.UUID.fromString(field(baseline,"receiptId")),
                field(baseline.path("submission"),"canonicalSha256"),
                field(network,"revisionId"),f.author);
    }

    private record T36Witness(String actionId,String auditId,java.sql.Timestamp reviewedAt) { }

    private static T36Witness t36Witness(Gz2SignedWorkflowFixture f,
            Gz2SignedWorkflowFixture.Runtime runtime,String authorToken,String reviewerToken,
            Gz2SignedWorkflowFixture.Review workflow,String target,String eventCode,
            String actionCode) throws Exception {
        var decision=workflowDecision(f,runtime,authorToken,reviewerToken,workflow,
                "topology",target,"t36-v1:"+target,id());
        String auditEvent=id();
        String eventType=f.catalog("audit","EVENT_TYPE",eventCode);
        String category=f.catalog("audit","EVENT_CATEGORY","T36_SYNTHETIC_ENGINEERING");
        java.sql.Timestamp acted=f.sql.queryForObject(
                "SELECT acted_at FROM public.hidra_workflow_action WHERE id=?",
                java.sql.Timestamp.class,decision.get("actionId"));
        f.sql.update("""
                INSERT INTO public.hidra_audit_event(
                 id,event_type_id,event_category_id,source_module,action_code,
                 event_status,actor_id,actor_type,target_module,target_type,target_id,
                 operation,workflow_instance_id,workflow_task_id,workflow_action_id,
                 occurred_at,recorded_at)
                VALUES(?,?,?,'topology',?,
                  'RECORDED',?,'HUMAN','topology','OPERATING_PRESSURE_RESTRICTION',
                  ?,'APPROVE',?,?,?, ?,clock_timestamp())
                """,auditEvent,eventType,category,actionCode,f.reviewer,target,
                decision.get("instanceId"),decision.get("taskId"),decision.get("actionId"),acted);
        return new T36Witness(decision.get("actionId"),auditEvent,acted);
    }

    private static void t36Revision(Gz2SignedWorkflowFixture f,String pipe,
            String section,String revision,String supersedes,JsonNode baseline,JsonNode network,
            Instant from,Instant until,String pressure,T36Witness decision) {
        f.sql.update("""
                INSERT INTO public.hidra_topology_t36_pressure_revision(
                 revision_id,company_party_id,pipeline_id,section_id,pipeline_system_id,
                 condition_code,upper_pressure,pressure_unit,pressure_basis,
                 effective_from,effective_until,recorded_at,baseline_receipt_id,
                 baseline_manifest_digest,engineering_source_revision,decision_action_id,
                 audit_event_id,submitted_by,reviewed_by,approved_at,supersedes_revision_id)
                VALUES(?,?,?,?,?,'NOMINAL',?,'bar','GAUGE',?,?,clock_timestamp(),
                 ?,?,?,?,?,?,?,?,?)
                """,java.util.UUID.fromString(revision),f.company,pipe,section,f.pipeline,
                new java.math.BigDecimal(pressure),java.sql.Timestamp.from(from),
                until==null?null:java.sql.Timestamp.from(until),
                java.util.UUID.fromString(field(baseline,"receiptId")),
                field(baseline.path("submission"),"canonicalSha256"),
                field(network,"revisionId"),decision.actionId(),decision.auditId(),
                f.author,f.reviewer,decision.reviewedAt(),
                supersedes==null?null:java.util.UUID.fromString(supersedes));
    }

    private static void t36Termination(Gz2SignedWorkflowFixture f,String pipe,
            String event,String retired,String kind,String successor,Instant effective,
            JsonNode baseline,JsonNode network,T36Witness decision) {
        f.sql.update("""
                INSERT INTO public.hidra_topology_t36_pressure_termination(
                 event_id,company_party_id,pipeline_id,section_id,pipeline_system_id,
                 condition_code,retired_revision_id,event_kind,successor_revision_id,
                 effective_at,recorded_at,baseline_receipt_id,baseline_manifest_digest,
                 engineering_source_revision,decision_action_id,audit_event_id,
                 submitted_by,reviewed_by,approved_at)
                VALUES(?,?,?,?,?,'NOMINAL',?,?,?, ?,clock_timestamp(),
                 ?,?,?,?,?,?,?,?)
                """,java.util.UUID.fromString(event),f.company,pipe,"",f.pipeline,
                java.util.UUID.fromString(retired),kind,
                successor==null?null:java.util.UUID.fromString(successor),
                java.sql.Timestamp.from(effective),
                java.util.UUID.fromString(field(baseline,"receiptId")),
                field(baseline.path("submission"),"canonicalSha256"),
                field(network,"revisionId"),decision.actionId(),decision.auditId(),
                f.author,f.reviewer,decision.reviewedAt());
    }

    private static void grantEngineering(Gz2SignedWorkflowFixture f) {
        for (String actor : List.of(f.author, f.reviewer, f.reader, f.revoked)) {
            f.paired(actor, "custody:engineering-product-publication", "PRODUCT_ENGINEERING_PUBLICATION",
                    "custody-company:" + f.company, "custody-product:" + f.product,
                    actor.equals(f.author) ? new String[]{"read", "prepare", "publish"} : new String[]{"read"});
            f.paired(actor, "topology:engineering-publication", "ENGINEERING_NETWORK_PUBLICATION",
                    "topology-company:" + f.company, "topology-network:" + f.pipeline,
                    actor.equals(f.author) ? new String[]{"publish", "read"} : new String[]{"read"});
            f.paired(actor, "topology:engineering-baseline", "ENGINEERING_BASELINE_DRAFT",
                    "topology-company:" + f.company, "topology-network:" + f.pipeline,
                    actor.equals(f.author) ? new String[]{"create", "read", "validate", "submit", "publish"}
                            : actor.equals(f.reviewer) ? new String[]{"read", "review"} : new String[]{"read"});
        }
        f.paired(f.author, "topology:engineering-draft", "ENGINEERING_NETWORK_DRAFT",
                "topology-company:" + f.company, "topology-network:" + f.pipeline, "create", "read", "update", "validate");
        f.paired(f.author, "custody:engineering-product", "PRODUCT_ENGINEERING_DRAFT",
                "custody-company:" + f.company, "custody-product:" + f.product, "create", "read", "update");
        f.paired(f.author, "custody:engineering-product-properties", "PRODUCT_ENGINEERING_PROPERTIES",
                "custody-company:" + f.company, "custody-product:" + f.product, "write", "read");
        f.global(f.author, "workflow:instances:execute", "workflow:tasks:execute");
        f.global(f.reviewer, "workflow:tasks:execute", "workflow:tasks:read");
        f.paired(f.foreign, "topology:engineering-baseline", "ENGINEERING_BASELINE_DRAFT",
                "topology-company:" + f.foreignCompany, "topology-network:" + f.pipeline, "read");
    }

    private static void grantScenarioInputPermissions(Gz2SignedWorkflowFixture f) {
        // The new permission rows are created ungranted. Grant exact, independent
        // company and PIPELINE_SYSTEM scopes only for signed synthetic actors.
        for(String actor:List.of(f.author,f.reader,f.reviewer,f.wrongAsset,f.revoked)) {
            f.permit(actor,"simulation:scenario-inputs:read","SCENARIO_INPUT_REVISION",
                    "CUSTOM","simulation-company:"+f.company);
            f.permit(actor,"simulation:scenario-inputs:read","SCENARIO_INPUT_REVISION",
                    "PIPELINE_SYSTEM",f.pipeline);
        }
        for(String actor:List.of(f.author,f.revoked)) {
            f.permit(actor,"simulation:scenario-inputs:write","SCENARIO_INPUT_REVISION",
                    "CUSTOM","simulation-company:"+f.company);
            f.permit(actor,"simulation:scenario-inputs:write","SCENARIO_INPUT_REVISION",
                    "PIPELINE_SYSTEM",f.pipeline);
        }
        f.permit(f.foreign,"simulation:scenario-inputs:read","SCENARIO_INPUT_REVISION",
                "CUSTOM","simulation-company:"+f.foreignCompany);
        f.permit(f.foreign,"simulation:scenario-inputs:read","SCENARIO_INPUT_REVISION",
                "PIPELINE_SYSTEM",f.pipeline);
    }

    private static String scenarioBase(Gz2SignedWorkflowFixture f) {
        return "/api/v1/simulation/companies/"+f.company+"/pipeline-systems/"+f.pipeline+"/scenarios";
    }

    private static Map<String,Object> t31Input(JsonNode baseline,String mode,String key) {
        var body=new java.util.LinkedHashMap<String,Object>();
        body.put("baselineReceiptId",field(baseline,"receiptId"));
        body.put("expectedManifestDigest",field(baseline.path("submission"),"canonicalSha256"));
        body.put("name","Synthetic GZ2 scenario");
        body.put("mode",mode);
        body.put("boundaries",List.of(
            Map.of("slot","SUPPLY_PRESSURE","value",new java.math.BigDecimal("50.250000"),
                   "unit","BAR_ABSOLUTE"),
            Map.of("slot","DELIVERY_MASS_FLOW","value",new java.math.BigDecimal("12.500000"),
                   "unit","KG_PER_S")));
        if("TRANSIENT".equals(mode)) {
            body.put("durationSeconds",3600);
            body.put("stepSeconds",60);
        }
        body.put("requestKey",key);
        return body;
    }

    private static T31Scenario prepareScenarioInputs(Gz2SignedWorkflowFixture f,
            Gz2SignedWorkflowFixture.Runtime r,String author,String reviewer,JsonNode baseline) throws Exception {
        String root=scenarioBase(f);
        var steady=t31Input(baseline,"STEADY",id());
        JsonNode first=r.json("POST",root,author,steady,201);
        String scenarioId=field(first,"scenarioId");
        assertEquals(1,first.path("revision").asInt());
        assertEquals("VALIDATED_INPUTS",field(first,"status"));
        assertEquals("NOT_AVAILABLE",field(first,"numericalCapability"));
        assertEquals("T31_INPUT_V1",field(first,"policyId"));
        assertEquals("5025000",first.path("boundaries").get(0).path("siValue").decimalValue()
                .stripTrailingZeros().toPlainString());
        assertEquals(scenarioId,field(r.json("POST",root,author,steady,200),"scenarioId"));
        assertEquals(0,r.get(root+"/"+scenarioId+"/revisions/1/validation",author)
                .path("diagnostics").size());
        assertEquals("NOT_AVAILABLE",field(r.get(root+"/"+scenarioId+"/revisions/1/validation",author),
                "numericalCapability"));
        var badDigest=new java.util.LinkedHashMap<String,Object>(steady);
        badDigest.put("expectedManifestDigest","0".repeat(64));
        assertEquals(400,r.send("POST",root,author,badDigest).statusCode());
        var badMode=new java.util.LinkedHashMap<String,Object>(steady);
        badMode.put("requestKey",id());badMode.put("mode","TRANSIENT");
        assertEquals(400,r.send("POST",root,author,badMode).statusCode());
        var badUnit=new java.util.LinkedHashMap<String,Object>(steady);
        badUnit.put("requestKey",id());
        badUnit.put("boundaries",List.of(Map.of("slot","SUPPLY_PRESSURE","value",50,
                        "unit","KG_PER_S"),Map.of("slot","DELIVERY_MASS_FLOW","value",12,"unit","KG_PER_S")));
        assertEquals(400,r.send("POST",root,author,badUnit).statusCode());
        noDisclosure(r.send("POST",root,reviewer,steady),scenarioId);
        String append=root+"/"+scenarioId+"/revisions";
        var a=new java.util.LinkedHashMap<String,Object>(t31Input(baseline,"STEADY",id()));
        a.put("expectedRevision",1);
        var b=new java.util.LinkedHashMap<String,Object>(t31Input(baseline,"STEADY",id()));
        b.put("expectedRevision",1);
        var pool=java.util.concurrent.Executors.newFixedThreadPool(2);
        java.net.http.HttpResponse<String> one,two;
        try {
            var futureA=pool.submit(()->r.send("POST",append,author,a));
            var futureB=pool.submit(()->r.send("POST",append,author,b));
            one=futureA.get();two=futureB.get();
        }finally{pool.shutdownNow();}
        assertEquals(java.util.Set.of(201,409),java.util.Set.of(one.statusCode(),two.statusCode()));
        JsonNode second=JSON.readTree(one.statusCode()==201?one.body():two.body());
        assertEquals(2,second.path("revision").asInt());
        var chosen=one.statusCode()==201?a:b;
        assertEquals(scenarioId,field(r.json("POST",append,author,chosen,200),"scenarioId"));
        var stale=new java.util.LinkedHashMap<String,Object>(t31Input(baseline,"STEADY",id()));
        stale.put("expectedRevision",1);
        assertEquals(409,r.send("POST",append,author,stale).statusCode());
        var keyConflict=new java.util.LinkedHashMap<String,Object>(chosen);
        keyConflict.put("name","Other synthetic inputs");
        assertEquals(409,r.send("POST",append,author,keyConflict).statusCode());
        assertEquals(2L,f.sql.queryForObject("SELECT count(*) FROM hidra_simulation_t31_scenario_input_revision WHERE scenario_id=?",
                Long.class,java.util.UUID.fromString(scenarioId)));
        return new T31Scenario(first,second,null);
    }

    private static T31Scenario appendTransientScenario(Gz2SignedWorkflowFixture f,
            Gz2SignedWorkflowFixture.Runtime r,String author,T31Scenario prepared,JsonNode successor) throws Exception {
        String scenarioId=field(prepared.first(),"scenarioId");
        String root=scenarioBase(f);
        var transientInput=new java.util.LinkedHashMap<String,Object>(t31Input(successor,"TRANSIENT",id()));
        transientInput.put("expectedRevision",2);
        JsonNode third=r.json("POST",root+"/"+scenarioId+"/revisions",author,transientInput,201);
        assertEquals(3,third.path("revision").asInt());
        assertEquals(3600,third.path("durationSeconds").asInt());
        assertEquals(60,third.path("stepSeconds").asInt());
        assertEquals("NOT_AVAILABLE",field(third,"numericalCapability"));
        assertEquals(field(successor,"receiptId"),field(third.path("baseline"),"receiptId"));
        assertEquals(3L,f.sql.queryForObject("SELECT count(*) FROM hidra_simulation_t31_scenario_input_revision WHERE scenario_id=?",
                Long.class,java.util.UUID.fromString(scenarioId)));
        // Database must independently preserve append-only history, even for the migration owner.
        assertThrows(org.springframework.dao.DataAccessException.class,()->f.sql.update(
                "UPDATE hidra_simulation_t31_scenario_input_revision SET scenario_name='mutated' WHERE scenario_id=?",
                java.util.UUID.fromString(scenarioId)));
        assertThrows(org.springframework.dao.DataAccessException.class,()->f.sql.update(
                "DELETE FROM hidra_simulation_t31_scenario_input_revision WHERE scenario_id=?",
                java.util.UUID.fromString(scenarioId)));
        assertThrows(org.springframework.dao.DataAccessException.class,()->f.sql.execute(
                "TRUNCATE hidra_simulation_t31_scenario_input_revision"));
        assertThrows(org.springframework.dao.DataAccessException.class,()->f.sql.update("""
            INSERT INTO hidra_simulation_t31_scenario_input_revision
            (scenario_id,revision,predecessor_revision,company_id,pipeline_system_id,
             baseline_receipt_id,manifest_id,baseline_epoch,baseline_digest,scenario_name,
             mode,pressure_value,pressure_unit,pressure_si_pa,flow_value,flow_unit,flow_si_kg_per_s,
             duration_seconds,step_seconds,actor_id,recorded_at,policy_id,input_digest,request_key,request_fingerprint)
            SELECT scenario_id,4,3,company_id,?,baseline_receipt_id,manifest_id,baseline_epoch,
                   baseline_digest,scenario_name,mode,pressure_value,pressure_unit,pressure_si_pa,
                   flow_value,flow_unit,flow_si_kg_per_s,duration_seconds,step_seconds,
                   actor_id,recorded_at,policy_id,input_digest,?,request_fingerprint
            FROM hidra_simulation_t31_scenario_input_revision WHERE scenario_id=? AND revision=3
            """,f.secondAsset,id(),java.util.UUID.fromString(scenarioId)));
        assertEquals(0L,f.sql.queryForObject("SELECT count(*) FROM hidra_simulation_run",Long.class));
        assertEquals(0L,f.sql.queryForObject("SELECT count(*) FROM hidra_simulation_result_value",Long.class));
        return new T31Scenario(prepared.first(),prepared.second(),third);
    }

    private static void verifyScenarioAfterRestart(Gz2SignedWorkflowFixture f,
            Gz2SignedWorkflowFixture.Runtime r,T31Scenario scenario,Baselines baselines) throws Exception {
        String root=scenarioBase(f);
        String id=field(scenario.first(),"scenarioId");
        String reader=r.login(f.reader);
        for(JsonNode saved:List.of(scenario.first(),scenario.second(),scenario.third())) {
            int version=saved.path("revision").asInt();
            String path=root+"/"+id+"/revisions/"+version;
            JsonNode actual=r.get(path,reader);
            assertEquals(field(saved,"inputDigest"),field(actual,"inputDigest"));
            assertEquals(field(saved.path("baseline"),"receiptId"),
                    field(actual.path("baseline"),"receiptId"));
            assertEquals("T31_INPUT_V1",field(actual,"policyId"));
            assertEquals("NOT_AVAILABLE",field(actual,"numericalCapability"));
            JsonNode structural=r.get(path+"/validation",reader);
            assertEquals("VALIDATED_INPUTS",field(structural,"status"));
            assertEquals("NOT_AVAILABLE",field(structural,"numericalCapability"));
            assertEquals(0,structural.path("diagnostics").size());
        }
        assertEquals(field(baselines.original(),"receiptId"),
                field(scenario.first().path("baseline"),"receiptId"));
        assertEquals(field(baselines.successor(),"receiptId"),
                field(scenario.third().path("baseline"),"receiptId"));
        String first=root+"/"+id+"/revisions/1";
        noDisclosure(r.send("GET",first,r.login(f.foreign),null),id);
        noDisclosure(r.send("GET",first,r.login(f.wrongAsset),null),id);
        noDisclosure(r.send("GET",first,r.login(f.revoked),null),id);
        noDisclosure(r.send("GET",root+"/"+java.util.UUID.randomUUID()+"/revisions/1",reader,null),id);
        noDisclosure(r.send("GET",root.replace(f.pipeline,f.secondAsset)+"/"+id+"/revisions/1",reader,null),id);
        assertEquals(3L,f.sql.queryForObject("SELECT count(*) FROM hidra_simulation_t31_scenario_input_revision WHERE scenario_id=?",
                Long.class,java.util.UUID.fromString(id)));
    }

    private static void grantPlanning(Gz2SignedWorkflowFixture f) {
        for (String actor : List.of(f.author, f.reviewer, f.reader, f.foreign, f.wrongAsset, f.revoked)) {
            String company = actor.equals(f.foreign) ? f.foreignCompany : f.company;
            String asset = actor.equals(f.wrongAsset) ? f.secondAsset : f.asset;
            f.global(actor, "planning:periods:read", "planning:operational-plans:read",
                    "planning:revisions:read", "planning:targets:read");
            f.permit(actor, "planning:scoped-period:read", "PLANNING", "CUSTOM", "planning-company:" + company);
            f.assetGrant(actor, "planning:scoped-plan:read", company, asset);
        }
        f.global(f.author, "planning:periods:execute", "planning:operational-plans:execute", "planning:revisions:execute");
        f.global(f.reviewer, "planning:revisions:execute");
        f.permit(f.author, "planning:scoped-period:create", "PLANNING", "CUSTOM", "planning-company:" + f.company);
        f.assetGrant(f.author, "planning:scoped-plan:create", f.company, f.asset);
        f.assetGrant(f.author, "planning:scoped-plan:author", f.company, f.asset);
        f.assetGrant(f.reviewer, "planning:scoped-plan:review", f.company, f.asset);
    }

    private static void grantOperatingAuthorities(Gz2SignedWorkflowFixture f) {
        f.permit(f.producer, "telemetry:commission:manage", "TELEMETRY_COMMISSIONING", "CUSTOM", "telemetry-company:" + f.company);
        f.global(f.producer, "telemetry:points:execute", "telemetry:points:read");
        for (String action : List.of("create", "revise", "activate", "evaluate")) {
            f.assetGrant(f.operator, "monitoring:rule:" + action, f.company, f.asset);
        }
        for (String actor : List.of(f.operator, f.reader, f.foreign, f.wrongAsset, f.revoked)) {
            String company = actor.equals(f.foreign) ? f.foreignCompany : f.company;
            String asset = actor.equals(f.wrongAsset) ? f.secondAsset : f.asset;
            f.assetGrant(actor, "monitoring:rule:read", company, asset);
            f.global(actor, "alarm:alarms:read", "leakdetection:candidates:read", "incident:incidents:read");
            if (List.of(f.foreign, f.wrongAsset, f.revoked).contains(actor)) {
                f.global(actor, "incident:incidents:execute");
                f.assetGrant(actor, "incident:incidents:execute", company, asset);
            }
            for (String code : List.of("alarm:alarms:read", "leakdetection:candidates:read", "incident:incidents:read")) {
                f.assetGrant(actor, code, company, asset);
            }
        }
        for (String code : List.of("alarm:alarms:execute", "leakdetection:candidates:execute", "incident:incidents:execute")) {
            f.global(f.operator, code);
            f.assetGrant(f.operator, code, f.company, f.asset);
        }
    }

    private static JsonNode publishNetwork(Gz2SignedWorkflowFixture f, Gz2SignedWorkflowFixture.Runtime r, String author)
            throws Exception {
        JsonNode draft = r.json("POST", NETWORK, author, Map.of("companyPartyId", f.company, "pipelineSystemId", f.pipeline,
                "creationRequestId", id(), "title", "Synthetic GZ2 network", "evidenceReference", "gz2-synthetic-network"), 201);
        String draftId = field(draft, "draftId");
        String path = NETWORK + "/" + draftId;
        r.json("PUT", engineeringScope(f, path + "/graph"), author, Map.of("expectedVersion", 1,
                "nodes", List.of(Map.of("id", "start", "kind", "JUNCTION"), Map.of("id", "end", "kind", "JUNCTION")),
                "ports", List.of(Map.of("id", "start-out", "nodeId", "start", "direction", "OUTLET"),
                        Map.of("id", "end-in", "nodeId", "end", "direction", "INLET")),
                "branches", List.of(Map.of("id", "gas-pipe", "kind", "PIPE", "fromPortId", "start-out", "toPortId", "end-in"))), 200);
        var profile = Map.of("branchId", "gas-pipe", "horizontalCrs",
                Map.of("authority", "SYNTHETIC", "code", "GZ2-LOCAL",
                        "axes", List.of(Map.of("name", "north", "unitIdentifier", "metre"),
                                Map.of("name", "east", "unitIdentifier", "metre"))),
                "chainageOriginReference", "synthetic-origin", "direction", "FROM_TO", "evidenceReference", "gz2-synthetic-route",
                "points", List.of(surveyPoint("p0", "1", "0"), surveyPoint("p1", "10", "10")),
                "sections", List.of(Map.of("id", "section-1", "startChainageMetres", "0", "endChainageMetres", "10",
                        "evidenceReference", "gz2-synthetic-section")));
        r.json("PUT", engineeringScope(f, path + "/survey/gas-pipe"), author, Map.of("expectedVersion", 2, "profile", profile), 200);
        r.json("POST", engineeringScope(f, path + "/publications"), author,
                Map.of("requestId", id(), "draftVersion", 3, "expectedEpoch", 0), 200);
        String discovery = query("/api/v1/topology/engineering-sources", "companyPartyId", f.company,
                "pipelineSystemId", f.pipeline, "kind", "NETWORK_PUBLICATION", "businessKey", draftId, "limit", "1");
        JsonNode items = r.get(discovery, author).path("items");
        assertEquals(1, items.size());
        assertTrue(items.get(0).path("currentlyEligible").asBoolean());
        return items.get(0).path("baselinePin");
    }

    private static Map<String, String> surveyPoint(String id, String coordinate, String chainage) {
        return Map.of("id", id, "firstCoordinate", coordinate, "secondCoordinate", coordinate,
                "chainageMetres", chainage, "coordinatePrecision", "1", "chainagePrecisionMetres", "1");
    }

    private static JsonNode publishProduct(Gz2SignedWorkflowFixture f, Gz2SignedWorkflowFixture.Runtime r,
            String author, String reviewer) throws Exception {
        JsonNode created = r.json("POST", PRODUCT, author, Map.of("companyPartyId", f.company,
                "catalogProductId", f.product, "localCode", "GZ2-GAS", "creationRequestId", id(),
                "name", "Synthetic GZ2 gas", "evidenceReference", "gz2-synthetic-product", "classification", "GAS"), 201);
        String path = PRODUCT + "/" + field(created, "draftId");
        var source = Map.of("origin", "SYNTHETIC", "reference", "GZ2-SYNTHETIC", "evidenceReference", "gz2-synthetic-gas");
        var method = Map.of("reference", "gz2-synthetic-method", "revision", "v1");
        var pressure = Map.of("minimum", "50", "maximum", "150", "unitId", f.pressureUnit);
        var temperature = Map.of("minimum", "250", "maximum", "350", "unitId", "gz2-temperature");
        var facts = Map.of("requestId", id(), "expectedVersion", 0, "expectedProductMetadataVersion", 1,
                "source", source, "composition", Map.of("basis", "MOLE_FRACTION",
                        "components", List.of(Map.of("reference", "GAS-A", "fraction", "1.0000")), "method", method, "source", source),
                "methods", List.of(Map.of("key", method, "origin", "SYNTHETIC", "basis", "MOLE_FRACTION",
                        "components", List.of("GAS-A"), "properties", List.of("DENSITY"),
                        "absolutePressure", pressure, "absoluteTemperature", temperature, "evidenceReference", "gz2-method")),
                "properties", List.of(Map.of("observationId", "density", "property", "DENSITY", "value", "1.200",
                        "unitId", "gz2-density", "absolutePressure", Map.of("value", "100", "unitId", f.pressureUnit),
                        "absoluteTemperature", Map.of("value", "300", "unitId", "gz2-temperature"), "method", method, "source", source)));
        r.json("PUT", query(path + "/properties", "companyPartyId", f.company), author, facts, 201);
        var review = f.review("custody", "PRODUCT_SOURCE_PUBLICATION", "PRODUCT_SOURCE_PUBLICATION_APPROVAL", false);
        f.sql.update("""
                INSERT INTO hidra_workflow_custody_publication_policy
                (company_party_id,definition_id,definition_version,target_type_id,purpose_id,active,created_at,updated_at)
                VALUES (?,?,1,?,?,true,now(),now())
                """, f.company, review.definition(), review.type(), review.purpose());
        String publications = path + "/publications";
        String candidate = field(r.json("POST", query(publications + "/candidates", "companyPartyId", f.company),
                author, Map.of("requestId", id(), "metadataVersion", 1, "suppliedVersion", 1), 201), "candidateDigest");
        var decision = workflowDecision(f, r, author, reviewer, review, "custody", candidate, candidate, id());
        JsonNode publication = r.json("POST", query(publications, "companyPartyId", f.company), author,
                Map.of("requestId", id(), "candidateDigest", candidate, "expectedEpoch", 0, "approval", decision), 201);
        assertEquals(0, publication.path("publication").path("envelope").path("diagnostics").size());
        JsonNode current = r.get(query(publications + "/current", "companyPartyId", f.company), author);
        assertEquals(publication.path("baselinePin"), current.path("baselinePin"));
        return current.path("baselinePin");
    }

    private static Map<String, String> workflowDecision(Gz2SignedWorkflowFixture f, Gz2SignedWorkflowFixture.Runtime r,
            String author, String reviewer, Gz2SignedWorkflowFixture.Review review, String module,
            String target, String locator, String correlation) throws Exception {
        String instance = field(r.json("POST", "/api/v1/workflow/instances", author, Map.of(
                "definitionId", review.definition(), "definitionVersion", 1, "workflowPurposeId", review.purpose(),
                "targetModule", module, "targetTypeId", review.type(), "targetId", target, "targetCodeSnapshot", locator,
                "currentStepId", review.step(), "startedByActorId", f.author, "correlationId", correlation), 200), "id");
        String task = field(r.json("POST", "/api/v1/workflow/tasks", author, Map.of("instanceId", instance,
                "stepId", review.step(), "assignedActorId", f.reviewer, "taskLabelSnapshot", "Synthetic review"), 200), "id");
        String taskPath = "/api/v1/workflow/tasks/" + task;
        JsonNode current = r.get(taskPath, reviewer);
        JsonNode result = r.json("POST", taskPath + "/transitions/" + review.transition() + "/execute", reviewer,
                Map.of("expectedTaskUpdatedAt", field(current, "updatedAt"), "correlationId", correlation), 200);
        assertEquals("APPROVE", field(result, "decision"));
        assertEquals("COMPLETED", field(result, "instanceStatus"));
        return Map.of("instanceId", instance, "taskId", task, "actionId", field(result, "actionId"));
    }

    private static JsonNode publishBaseline(Gz2SignedWorkflowFixture f, Gz2SignedWorkflowFixture.Runtime r, String author,
            String reviewer, Gz2SignedWorkflowFixture.Review review, JsonNode network, JsonNode product,
            Instant effective, int expectedEpoch) throws Exception {
        String manifest = id();
        assertEquals("NETWORK_PUBLICATION", field(network, "kind"));
        assertEquals("PRODUCT_PUBLICATION", field(product, "kind"));
        JsonNode draft = r.json("POST", BASELINE, author, Map.of("manifestId", manifest, "companyPartyId", f.company,
                "pipelineSystemId", f.pipeline, "requestId", id(), "effectiveFrom", effective.toString(),
                "pins", List.of(network, product)), 200);
        String path = BASELINE + "/" + manifest;
        assertTrue(r.json("POST", engineeringScope(f, path + "/validation"), author, null, 200)
                .path("currentCompleteness").path("complete").asBoolean());
        String digest = field(draft, "canonicalSha256");
        JsonNode submission = r.json("POST", path + "/submissions", author, Map.of("companyPartyId", f.company,
                "pipelineSystemId", f.pipeline, "requestId", id(), "version", 1, "canonicalSha256", digest), 200);
        String submissionId = field(submission, "submissionId");
        var decision = workflowDecision(f, r, author, reviewer, review, "topology", digest,
                field(submission, "workflowLocator"), submissionId);
        JsonNode published = r.json("POST", BASELINE + "/submissions/" + submissionId + "/publications", author, Map.of(
                "companyPartyId", f.company, "pipelineSystemId", f.pipeline, "requestId", id(), "expectedEpoch", expectedEpoch,
                "decision", Map.of("definitionId", review.definition(), "definitionVersion", 1, "targetTypeId", review.type(),
                        "purposeId", review.purpose(), "instanceId", decision.get("instanceId"),
                        "taskId", decision.get("taskId"), "actionId", decision.get("actionId"))), 200);
        assertEquals(expectedEpoch + 1, published.path("epoch").asInt());
        assertEquals(f.reviewer, field(published, "reviewerId"));
        assertTrue(published.has("engineeringQualified"));
        assertFalse(published.path("engineeringQualified").asBoolean());
        assertEquals(digest, field(published.path("submission"), "canonicalSha256"));
        assertEquals(decision.get("actionId"), field(published, "workflowActionId"));
        return published;
    }

    private static JsonNode commission(Gz2SignedWorkflowFixture f, Gz2SignedWorkflowFixture.Runtime r, String producer,
            Instant start) throws Exception {
        return commission(f, r, producer, start, id(), "GZ2");
    }

    private static JsonNode commission(Gz2SignedWorkflowFixture f, Gz2SignedWorkflowFixture.Runtime r, String producer,
            Instant start, String requestId, String code) throws Exception {
        JsonNode receipt = r.json("POST", "/api/v1/telemetry/commissioning", producer, Map.ofEntries(
                Map.entry("requestId", requestId), Map.entry("companyPartyId", f.company), Map.entry("assetType", "FACILITY"),
                Map.entry("assetId", f.asset), Map.entry("sourceCode", code + "-S"), Map.entry("sourceNameFr", "Source synthétique"),
                Map.entry("sourceTypeId", f.telemetryType("SOURCE_TYPE", null)), Map.entry("protocolId", f.telemetryType("PROTOCOL", null)),
                Map.entry("deviceCode", code + "-D"), Map.entry("deviceNameFr", "Appareil synthétique"),
                Map.entry("deviceTypeId", f.telemetryType("DEVICE_TYPE", null)), Map.entry("pointCode", code + "-P"),
                Map.entry("pointNameFr", "Pression synthétique"), Map.entry("pointTypeId", f.telemetryType("POINT_TYPE", null)),
                Map.entry("signalTypeId", f.telemetryType("SIGNAL_TYPE", "NUMERIC")), Map.entry("unitId", f.pressureUnit),
                Map.entry("bindingRoleId", f.telemetryType("BINDING_ROLE", null)), Map.entry("externalTagName", code + ".PRESSURE"),
                Map.entry("validFrom", start.toString())), 200);
        assertEquals(f.asset, field(receipt, "assetId"));
        assertFalse(field(receipt, "bindingId").isBlank());
        return receipt;
    }

    private record T37History(String route, String trustedId, String bindingId) { }

    private static T37History verifyAssetObservations(Gz2SignedWorkflowFixture f, Gz2SignedWorkflowFixture.Runtime r,
            JsonNode primary, Reading original, Reading stale, Instant start) throws Exception {
        createT37SecondRegion(f);
        for (String actor : List.of(f.reader, f.wrongAsset, f.producer, f.operator, f.foreign, f.reviewer)) {
            f.global(actor, "telemetry:assets:read");
        }
        String producer = r.login(f.producer);
        String originalPoint = field(primary, "pointId");
        List<String> requests = List.of("00000000-0000-0000-0037-000000000001",
                "00000000-0000-0000-0037-000000000002", "00000000-0000-0000-0037-000000000003").stream()
                .sorted(java.util.Comparator.comparing(Gz2SignedWorkflowNativeHttpPostgresIntegrationTest::t37PointId))
                .toList();
        String hiddenRequest = requests.get(1);
        String movableRequest = originalPoint.compareTo(t37PointId(hiddenRequest)) < 0
                ? requests.getLast() : requests.getFirst();
        JsonNode movable = commission(f, r, producer, start, movableRequest, "GZ2-T37-MOVABLE");
        String point = field(movable, "pointId");
        String source = field(movable, "sourceId");
        String previousBinding = field(movable, "bindingId");
        String quality = prepareTelemetry(f, movable, false);
        String pointScope = "telemetry-source-point:" + source + ":" + point;
        f.permit(f.producer, "telemetry:commission:manage", "TELEMETRY_COMMISSIONING", "CUSTOM", pointScope);
        for (String actor : List.of(f.wrongAsset, f.reviewer, f.foreign)) {
            f.paired(actor, "telemetry:trusted-reading", "TRUSTED_READING",
                    "telemetry-company:" + (actor.equals(f.foreign) ? f.foreignCompany : f.company), pointScope, "read");
        }
        String reader = r.login(f.reader);
        String regionalOnly = r.login(f.operator);
        String foreign = r.login(f.foreign);
        String reviewer = r.login(f.reviewer);
        String secondReader = r.login(f.wrongAsset);
        producer = r.login(f.producer);

        JsonNode hidden = commission(f, r, producer, start, hiddenRequest, "GZ2-T37-HIDDEN");
        String hiddenPoint = field(hidden, "pointId");
        String root = t37AssetPoints(f.asset);
        String other = t37AssetPoints(f.secondAsset);
        Instant discoveredAt = now();
        String first = query(root, "companyPartyId", f.company, "at", discoveredAt.toString(), "limit", "1");
        JsonNode page = r.get(first, reader);
        assertEquals(1, page.path("points").size());
        String firstVisible = originalPoint.compareTo(point) < 0 ? originalPoint : point;
        assertEquals(firstVisible, field(page.path("points").get(0), "pointId"));
        assertFalse(page.has("total"));
        String next = field(page, "nextPointId");
        assertEquals(firstVisible, next);
        JsonNode secondPage = r.get(first + "&afterPointId=" + next, reader);
        assertEquals(1, secondPage.path("points").size());
        assertEquals(firstVisible.equals(originalPoint) ? point : originalPoint,
                field(secondPage.path("points").get(0), "pointId"));
        assertFalse(secondPage.hasNonNull("nextPointId"));
        assertFalse(page.toString().contains(hiddenPoint) || secondPage.toString().contains(hiddenPoint));

        String latest = t37Latest(root, point, f.company, 120);
        String primaryLatest = t37Latest(root, originalPoint, f.company, 600);
        JsonNode fresh = r.get(primaryLatest, reader);
        assertEquals("FRESH", field(fresh, "status"));
        assertT37Lineage(f, fresh.path("observation"), primary, original);
        assertEquals("STALE", field(r.get(t37Latest(root, originalPoint, f.company, 1), reader), "status"));
        String originalHistory = t37History(root, originalPoint, f.company, start, now(), 1);
        JsonNode paged = r.get(originalHistory, reader);
        assertEquals(original.trusted(), field(paged.path("observations").get(0), "trustedId"));
        JsonNode cursor = paged.path("next");
        JsonNode pagedNext = r.get(originalHistory + "&afterObservedAt=" + field(cursor, "observedAt")
                + "&afterTrustedId=" + field(cursor, "trustedId"), reader);
        assertEquals(stale.trusted(), field(pagedNext.path("observations").get(0), "trustedId"));
        assertFalse(pagedNext.hasNonNull("next"));

        JsonNode empty = r.get(latest, reader);
        assertEquals("UNAVAILABLE", field(empty, "status"));
        assertFalse(empty.hasNonNull("observation"));
        assertTrue(r.get(t37History(root, point, f.company, start, now(), 20), reader).path("observations").isEmpty());
        assertEquals(401, r.send("GET", latest, null, null).statusCode());
        assertEquals(403, r.send("GET", latest, r.login(f.revoked), null).statusCode());
        for (String denied : List.of(regionalOnly, producer, secondReader, foreign)) {
            var result = r.send("GET", latest, denied, null);
            assertEquals(404, result.statusCode(), result::body);
            assertEquals("Resource unavailable", field(JSON.readTree(result.body()), "detail"));
        }
        var foreignClaim = r.send("GET", t37Latest(root, point, f.foreignCompany, 120), foreign, null);
        assertEquals(404, foreignClaim.statusCode(), foreignClaim::body);
        assertEquals(404, r.send("GET", t37Latest(root, hiddenPoint, f.company, 120), reader, null).statusCode());
        assertEquals(404, r.send("GET", t37Latest(other, point, f.company, 120), secondReader, null).statusCode());

        Instant observedBefore = now().minusSeconds(5);
        Reading before = trust(f, r, producer, movable, quality, observedBefore);
        assertT37Lineage(f, r.get(latest, reviewer).path("observation"), movable, before);
        assertEquals(1, f.sql.update("""
                UPDATE hidra_identity_user_permission_grant SET status='REVOKED',revoked_at=now()
                WHERE user_id=? AND scope_reference_id=?
                  AND permission_id=(SELECT id FROM hidra_identity_permission WHERE code='telemetry:trusted-reading:read')
                """, f.reviewer, pointScope));
        assertEquals(404, r.send("GET", latest, reviewer, null).statusCode());
        assertEquals(404, r.send("GET", t37History(root, point, f.company, start, now(), 20),
                reviewer, null).statusCode());

        String rawPath = POINT + point + "/readings";
        assertEquals(403, r.send("POST", rawPath, producer,
                t37Raw(source, "gz2-temperature", quality, now())).statusCode());
        String pending = field(r.json("POST", rawPath, producer,
                t37Raw(source, f.pressureUnit, quality, now()), 201), "id");
        String rejectedQuality = f.telemetryType("QUALITY_CODE", null);
        String failed = field(r.json("POST", rawPath, producer,
                t37Raw(source, f.pressureUnit, rejectedQuality, now()), 201), "id");
        Map<String, String> companyScope = Map.of("companyPartyId", f.company);
        JsonNode assessment = r.json("POST", rawPath + "/" + failed + "/assessment", producer, companyScope, 201);
        assertEquals("FAILED", field(assessment, "status"));
        assertEquals(400, r.send("POST", rawPath + "/" + failed + "/trust", producer, companyScope).statusCode());

        Instant switchAt = now().plusSeconds(4);
        JsonNode successor = r.json("POST", "/api/v1/telemetry/commissioning/points/" + point + "/bindings",
                producer, Map.of("requestId", id(), "companyPartyId", f.company, "assetType", "FACILITY",
                        "assetId", f.secondAsset, "bindingRoleId", field(movable, "bindingRoleId"),
                        "externalTagName", "GZ2.T37.RELOCATED", "validFrom", switchAt.toString()), 200);
        String successorBinding = field(successor, "bindingId");
        assertNotEquals(previousBinding, successorBinding);
        while (now().isBefore(switchAt)) Thread.sleep(50);
        assertEquals(403, r.send("POST", rawPath + "/" + pending + "/assessment", producer, companyScope).statusCode());

        String successorLatest = t37Latest(other, point, f.company, 120);
        // T09 records the publication binding. A late sample stamped before the rebind must stay
        // out of T37 because that recorded binding was not effective when the sample was measured.
        Reading late = trust(f, r, producer, successor, quality, switchAt.minus(1, ChronoUnit.MICROS));
        JsonNode incompatible = r.get(successorLatest, secondReader);
        assertEquals("UNAVAILABLE", field(incompatible, "status"));
        assertFalse(incompatible.hasNonNull("observation"));

        Reading boundary = trust(f, r, producer, successor, quality, switchAt);
        Reading after = trust(f, r, producer, successor, quality, now());
        assertT37Lineage(f, r.get(successorLatest, secondReader).path("observation"), successor, after);
        assertEquals(404, r.send("GET", successorLatest, reader, null).statusCode());
        assertEquals(404, r.send("GET", latest, reader, null).statusCode());

        Instant until = now();
        String oldRoute = t37History(root, point, f.company, start, until, 20);
        JsonNode old = r.get(oldRoute, reader);
        assertEquals(1, old.path("observations").size());
        assertT37Lineage(f, old.path("observations").get(0), movable, before);
        assertEquals(switchAt, Instant.parse(field(old.path("observations").get(0).path("binding"), "validUntil")));
        var olderScope = r.send("GET", oldRoute, secondReader, null);
        assertEquals(404, olderScope.statusCode(), olderScope::body);
        JsonNode newHistory = r.get(t37History(other, point, f.company, switchAt, until, 20), secondReader);
        assertEquals(2, newHistory.path("observations").size());
        assertEquals(after.trusted(), field(newHistory.path("observations").get(0), "trustedId"));
        assertEquals(boundary.trusted(), field(newHistory.path("observations").get(1), "trustedId"));
        assertFalse(newHistory.toString().contains(late.trusted()));
        assertEquals(point, field(r.get(query(other, "companyPartyId", f.company, "at", switchAt.toString()),
                secondReader).path("points").get(0), "pointId"));
        assertEquals(0, r.get(query(other, "companyPartyId", f.company,
                "at", switchAt.minus(1, ChronoUnit.MICROS).toString()), secondReader).path("points").size());
        return new T37History(oldRoute, before.trusted(), previousBinding);
    }

    private static void createT37SecondRegion(Gz2SignedWorkflowFixture f) {
        String system = id();
        String employee = id();
        f.sql.update("""
                INSERT INTO hidra_topology_pipeline_system(id,code,system_type_id,status,created_at,updated_at)
                SELECT ?,?,system_type_id,'ACTIVE',now(),now() FROM hidra_topology_pipeline_system WHERE id=?
                """, system, "T37-" + system, f.pipeline);
        f.sql.update("INSERT INTO hidra_topology_foundation_system_owner(pipeline_system_id,company_party_id) VALUES (?,?)",
                system, f.company);
        f.sql.update("""
                INSERT INTO hidra_topology_pipeline_system_facility(id,pipeline_system_id,facility_id,status,created_at,updated_at)
                VALUES (?,?,?,'ACTIVE',now(),now())
                """, id(), system, f.secondAsset);
        f.sql.update("""
                INSERT INTO hidra_org_employee(id,employee_number,employee_type,status,identity_user_reference,created_at,updated_at)
                VALUES (?,?,'PERMANENT','ACTIVE',?,now(),now())
                """, employee, "T37-" + employee, f.wrongAsset);
        f.sql.update("UPDATE hidra_identity_user SET employee_reference_id=? WHERE id=?", employee, f.wrongAsset);
        Long scope = f.sql.queryForObject("""
                INSERT INTO hidra_org_operational_scope(scope_type,target_id)
                VALUES ('PIPELINE_SYSTEM',?) RETURNING id
                """, Long.class, system);
        f.sql.update("""
                INSERT INTO hidra_org_responsibility_assignment(
                    id,responsibility_type,assignee_type,assignee_id,scope_id,valid_from,valid_to,status,created_at,updated_at)
                VALUES (?,'RESPONSIBLE','EMPLOYEE',?,?,now()-interval '2 hours',now()+interval '2 hours','ACTIVE',now(),now())
                """, id(), employee, scope);
        f.permit(f.wrongAsset, "topology:regional-resource:read", "TOPOLOGY_REGIONAL_RESOURCE", "PIPELINE_SYSTEM", system);
    }

    private static String t37PointId(String request) {
        return java.util.UUID.nameUUIDFromBytes(("HIDRA_T08|" + request + "|point")
                .getBytes(java.nio.charset.StandardCharsets.UTF_8)).toString();
    }

    private static Map<String, Object> t37Raw(String source, String unit, String quality, Instant observed) {
        return Map.of("sourceId", source, "sourceSequenceNumber", id(), "numericValue", 103,
                "qualityCodeId", quality, "unitId", unit, "sourceTimestamp", observed.toString());
    }

    private static String t37AssetPoints(String asset) {
        return ASSET_POINTS.replace("{assetType}", "FACILITY").replace("{assetId}", asset);
    }

    private static String t37Latest(String root, String point, String company, int maxAge) {
        return query(root + "/" + point + "/trusted-readings/latest",
                "companyPartyId", company, "maxAgeSeconds", Integer.toString(maxAge));
    }

    private static String t37History(String root, String point, String company, Instant from, Instant until, int limit) {
        return query(root + "/" + point + "/trusted-readings/history", "companyPartyId", company,
                "from", from.toString(), "until", until.toString(), "maxAgeSeconds", "600", "limit", Integer.toString(limit));
    }

    private static void assertT37Lineage(Gz2SignedWorkflowFixture f, JsonNode view, JsonNode commissioned, Reading reading) {
        assertEquals(reading.trusted(), field(view, "trustedId"));
        assertEquals(reading.raw(), field(view, "rawReadingId"));
        assertEquals(reading.assessment(), field(view, "assessmentId"));
        assertEquals("PASSED", field(view, "assessmentStatus"));
        assertEquals("HIGH", field(view, "trustLevel"));
        assertEquals(f.pressureUnit, field(view, "unitId"));
        assertEquals(field(commissioned, "sourceId"), field(view, "sourceId"));
        assertEquals(field(commissioned, "bindingId"), field(view.path("binding"), "id"));
        assertFalse(Instant.parse(field(view, "observedAt")).isAfter(Instant.parse(field(view, "receivedAt"))));
        assertFalse(field(view, "qualityCodeId").isBlank());
    }

    private static Plan approvePlan(Gz2SignedWorkflowFixture f, Gz2SignedWorkflowFixture.Runtime r, String author,
            String reviewer, String point, Instant start, Instant end) throws Exception {
        var review = f.review("planning", "PLAN_REVISION", "PLAN_REVISION_APPROVAL", true);
        String targetType = f.catalog("planning", "TARGET_TYPE", "GZ2_PRESSURE");
        f.sql.update("INSERT INTO hidra_planning_target_value_policy(target_type_id,representation_kind,active) VALUES (?,'NUMERIC',true)",
                targetType);
        String period = field(r.json("POST", "/api/v2/planning/periods", author, Map.of("companyPartyId", f.company,
                "code", "GZ2", "nameFr", "Période synthétique", "periodTypeId", f.catalog("planning", "PERIOD_TYPE", "GZ2_PERIOD"),
                "periodStart", start.toString(), "periodEnd", end.toString()), 200), "id");
        String plan = field(r.json("POST", PLAN + "/operational-plans", author, Map.of("periodId", period, "code", "GZ2",
                "nameFr", "Plan synthétique", "planTypeId", f.catalog("planning", "PLAN_TYPE", "GZ2_PLAN"),
                "topologyScopeType", "FACILITY", "topologyScopeId", f.asset), 200), "id");
        String planPath = PLAN + "/operational-plans/" + plan;
        JsonNode revision = r.json("POST", planPath + "/revisions", author,
                Map.of("expectedPlanUpdatedAt", field(r.get(planPath, author), "updatedAt")), 200);
        String revisionId = field(revision, "id");
        String revisionPath = PLAN + "/revisions/" + revisionId;
        JsonNode target = r.json("POST", revisionPath + "/targets", author, Map.of(
                "expectedRevisionUpdatedAt", field(revision, "updatedAt"), "targetTypeId", targetType,
                "topologyAssetType", "FACILITY", "topologyAssetId", f.asset, "telemetryPointId", point,
                "unitId", f.pressureUnit, "targetValue", 100, "validFrom", start.toString(), "validTo", end.toString()), 200);
        String targetId = field(target.path("target"), "id");
        JsonNode submission = r.json("POST", revisionPath + "/submit", author, Map.of(
                "expectedPlanUpdatedAt", field(r.get(planPath, author), "updatedAt"),
                "expectedRevisionUpdatedAt", field(target, "revisionUpdatedAt"), "definitionId", review.definition(),
                "definitionVersion", 1, "targetTypeId", review.type(), "workflowPurposeId", review.purpose(),
                "reviewerActorId", f.reviewer), 200);
        assertEquals("SUBMITTED", field(submission, "revisionStatus"));
        String transition = revisionPath + "/approval/actions/" + review.transition() + "/execute";
        var command = Map.of("expectedTaskUpdatedAt", field(submission, "workflowTaskUpdatedAt"));
        assertDenied(r.send("POST", transition, author, command).statusCode());
        assertEquals("DRAFT", field(r.get(PLAN + "/targets/" + targetId, author), "status"));
        JsonNode decision = r.json("POST", transition, reviewer, command, 200);
        assertEquals("APPROVED", field(decision, "revisionStatus"));
        assertEquals("ACTIVE", field(r.get(PLAN + "/targets/" + targetId, author), "status"));
        return new Plan(revisionId, targetId, field(submission, "workflowInstanceId"));
    }

    private static String prepareTelemetry(Gz2SignedWorkflowFixture f, JsonNode commissioned) {
        return prepareTelemetry(f, commissioned, true);
    }

    private static String prepareTelemetry(Gz2SignedWorkflowFixture f, JsonNode commissioned, boolean provisionSharedScopes) {
        String point = field(commissioned, "pointId");
        String reference = "telemetry-source-point:" + field(commissioned, "sourceId") + ":" + point;
        if (provisionSharedScopes) {
            String company = "telemetry-company:" + f.company;
            f.permit(f.producer, "telemetry:raw-reading:ingest", "TELEMETRY_READING", "CUSTOM", company);
            f.permit(f.producer, "telemetry:raw-reading:read", "TELEMETRY_READING", "CUSTOM", company);
            f.permit(f.producer, "telemetry:trusted-reading:qualify", "TRUSTED_READING", "CUSTOM", company);
            f.permit(f.producer, "telemetry:trusted-reading:read", "TRUSTED_READING", "CUSTOM", company);
            f.permit(f.reader, "telemetry:raw-reading:read", "TELEMETRY_READING", "CUSTOM", company);
            f.permit(f.reader, "telemetry:trusted-reading:read", "TRUSTED_READING", "CUSTOM", company);
            f.global(f.reader, "telemetry:points:read");
        }
        f.permit(f.producer, "telemetry:raw-reading:ingest", "TELEMETRY_READING", "CUSTOM", reference);
        f.permit(f.producer, "telemetry:raw-reading:read", "TELEMETRY_READING", "CUSTOM", reference);
        f.permit(f.producer, "telemetry:trusted-reading:qualify", "TRUSTED_READING", "CUSTOM", reference);
        f.permit(f.producer, "telemetry:trusted-reading:read", "TRUSTED_READING", "CUSTOM", reference);
        f.permit(f.reader, "telemetry:raw-reading:read", "TELEMETRY_READING", "CUSTOM", reference);
        f.permit(f.reader, "telemetry:trusted-reading:read", "TRUSTED_READING", "CUSTOM", reference);
        String quality = f.telemetryType("QUALITY_CODE", null);
        f.sql.update("""
                INSERT INTO hidra_telemetry_validation_rule(id,code,name,scope_type,scope_reference_id,rule_type_id,
                    severity,action_on_failure,configuration_json,active,valid_from,created_at,updated_at)
                VALUES (?,'T09_RAW_QUALITY','Synthetic GZ2 trust','POINT',?,?,'ERROR','REJECT',
                    cast(? AS jsonb),true,now()-interval '1 hour',now(),now())
                """, id(), point, f.telemetryType("VALIDATION_RULE_TYPE", null),
                "{\"qualityCodeId\":\"" + quality + "\",\"trustLevel\":\"HIGH\",\"maxPastAgeSeconds\":3600,\"maxFutureSkewSeconds\":120}");
        return quality;
    }

    private static Reading trust(Gz2SignedWorkflowFixture f, Gz2SignedWorkflowFixture.Runtime r, String producer,
            JsonNode commissioned, String quality, Instant observed) throws Exception {
        return trust(f,r,producer,commissioned,quality,observed,105);
    }
    private static Reading trust(Gz2SignedWorkflowFixture f, Gz2SignedWorkflowFixture.Runtime r, String producer,
            JsonNode commissioned, String quality, Instant observed,int numeric) throws Exception {
        String path = POINT + field(commissioned, "pointId") + "/readings";
        var raw = Map.of("sourceId", field(commissioned, "sourceId"), "sourceSequenceNumber", id(), "numericValue", numeric,
                "qualityCodeId", quality, "unitId", f.pressureUnit, "sourceTimestamp", observed.toString());
        String rawId = field(r.json("POST", path, producer, raw, 201), "id");
        JsonNode replay = r.json("POST", path, producer, raw, 200);
        assertEquals(rawId, field(replay, "id"));
        assertTrue(replay.path("replayed").asBoolean());
        var scope = Map.of("companyPartyId", f.company);
        String assessment = field(r.json("POST", path + "/" + rawId + "/assessment", producer, scope, 201), "id");
        JsonNode trusted = r.json("POST", path + "/" + rawId + "/trust", producer, scope, 201);
        assertEquals(rawId, field(trusted, "rawReadingId"));
        assertEquals(assessment, field(trusted, "assessmentId"));
        assertEquals(field(commissioned, "bindingId"), field(trusted, "bindingId"));
        assertEquals(f.pressureUnit, field(trusted, "unitId"));
        return new Reading(rawId, assessment, field(trusted, "id"));
    }


    /**
     * T23 reuse: authentic T11 source receipts without constructing unrelated Alarm or Incident state.
     * Keeps the existing T15 journey and its synthetic expected actual=105 unchanged.
     */
    static record MetricEvidence(String computedId,String staleId,String ruleId) { }

    static MetricEvidence prepareDeviationMetricEvidence(
            Gz2SignedWorkflowFixture f,Gz2SignedWorkflowFixture.Runtime r) throws Exception {
        grantPlanning(f);
        grantOperatingAuthorities(f);
        f.global(f.author,"workflow:instances:execute","workflow:tasks:execute");
        f.global(f.reviewer,"workflow:tasks:execute","workflow:tasks:read");
        String author=r.login(f.author), reviewer=r.login(f.reviewer);
        String producer=r.login(f.producer), operator=r.login(f.operator);
        Instant start=now().minusSeconds(900);
        Instant end=start.plusSeconds(7200);
        JsonNode commissioned=commission(f,r,producer,start);
        String point=field(commissioned,"pointId");
        Plan plan=approvePlan(f,r,author,reviewer,point,start,end);
        String quality=prepareTelemetry(f,commissioned);
        producer=r.login(f.producer);
        Instant observed=now().minusSeconds(5);
        Reading fresh=trust(f,r,producer,commissioned,quality,observed,95);
        Reading aged=trust(f,r,producer,commissioned,quality,observed.minusSeconds(300),95);
        String policy="TRUSTED_PLAN_COMPARISON";
        f.catalog("monitoring","EVIDENCE_POLICY",policy);
        String rule=field(r.json("POST",RULE,operator,Map.of(
                "code","GZ2-T23-COMPARISON","nameFr","Comparaison absolue synthétique",
                "ruleType","PLAN_COMPARISON","topologyAssetType","FACILITY",
                "topologyAssetId",f.asset,"telemetryPointId",point),200),"id");
        String root=RULE+"/"+rule;
        r.json("POST",root+"/revisions",operator,Map.of("unitId",f.pressureUnit,
                "evidencePolicyCode",policy,"validFrom",start.toString(),"validTo",end.toString()),201);
        r.json("POST",root+"/revisions/1/activate",operator,Map.of("expectedHeadRevision",0,
                "effectiveFrom",start.toString(),"effectiveUntil",end.toString()),200);
        f.sql.update("""
                INSERT INTO hidra_monitoring_evaluation_freshness_policy(rule_id,rule_revision,maximum_age_seconds)
                VALUES (?,1,120)
                """,rule);
        Instant evaluatedAt=now();
        JsonNode computed=r.json("POST",root+"/evaluations",operator,
                evaluationRequest(plan,fresh,evaluatedAt),200);
        assertEquals("COMPUTED",field(computed,"outcome"));
        assertEquals(100,computed.path("expected").asInt(-1));
        assertEquals(95,computed.path("actual").asInt(-1));
        JsonNode stale=r.json("POST",root+"/evaluations",operator,
                evaluationRequest(plan,aged,evaluatedAt),200);
        assertEquals("STALE",field(stale,"outcome"));
        return new MetricEvidence(field(computed,"id"),field(stale,"id"),rule);
    }

    private static ResponseChain respond(Gz2SignedWorkflowFixture f, Gz2SignedWorkflowFixture.Runtime r, String operator,
            String point, Plan plan, Reading reading, Reading stale, Instant start, Instant end) throws Exception {
        String policy = "TRUSTED_PLAN_COMPARISON";
        f.catalog("monitoring", "EVIDENCE_POLICY", policy);
        String rule = field(r.json("POST", RULE, operator, Map.of("code", "GZ2-COMPARISON", "nameFr", "Comparaison synthétique",
                "ruleType", "PLAN_COMPARISON", "topologyAssetType", "FACILITY", "topologyAssetId", f.asset,
                "telemetryPointId", point), 200), "id");
        String rulePath = RULE + "/" + rule;
        r.json("POST", rulePath + "/revisions", operator, Map.of("unitId", f.pressureUnit,
                "evidencePolicyCode", policy, "validFrom", start.toString(), "validTo", end.toString()), 201);
        r.json("POST", rulePath + "/revisions/1/activate", operator, Map.of("expectedHeadRevision", 0,
                "effectiveFrom", start.toString(), "effectiveUntil", end.toString()), 200);
        f.sql.update("""
                INSERT INTO hidra_monitoring_evaluation_freshness_policy(rule_id,rule_revision,maximum_age_seconds)
                VALUES (?,1,120)
                """, rule);
        String evaluations = rulePath + "/evaluations";
        Instant evaluatedAt = now();
        JsonNode computed = r.json("POST", evaluations, operator, evaluationRequest(plan, reading, evaluatedAt), 200);
        assertDeviation(computed, plan, reading, f.pressureUnit);
        String evaluation = field(computed, "id");
        JsonNode aged = r.json("POST", evaluations, operator, evaluationRequest(plan, stale, evaluatedAt), 200);
        assertEquals("STALE", field(aged, "outcome"));
        assertFalse(aged.path("difference").isNumber());

        String alarmType = f.catalog("alarm", "ALARM_TYPE", "GZ2_DEVIATION");
        String alarmSeverity = f.catalog("alarm", "ALARM_SEVERITY", "GZ2_REVIEW");
        r.json("POST", ALARM + "/from-evaluation", operator, Map.of("evaluationId", field(aged, "id"),
                "alarmTypeId", alarmType, "severityId", alarmSeverity, "titleFr", "Stale synthetic source"), 422);
        String alarm = field(r.json("POST", ALARM + "/from-evaluation", operator, Map.of("evaluationId", evaluation,
                "alarmTypeId", alarmType, "severityId", alarmSeverity, "titleFr", "Deviation synthétique"), 201), "alarmId");
        var acknowledgement = r.send("POST", ALARM + "/acknowledgements", operator,
                Map.of("alarmId", alarm, "comment", "Reviewed synthetic deviation"));
        assertEquals(200, acknowledgement.statusCode(), "Alarm acknowledgement status");
        assertFalse(acknowledgement.body().isBlank(), "Alarm acknowledgement must return its ID");
        assertEquals("ACKNOWLEDGED", field(r.get(ALARM + "/" + alarm, operator), "currentState"));

        String method = id();
        String profile = id();
        f.sql.update("""
                INSERT INTO hidra_leak_detection_method(id,code,method_family,status,system_defined,created_at,updated_at)
                VALUES (?,?,'MANUAL_DECLARATION','ACTIVE',false,now(),now())
                """, method, method);
        f.sql.update("""
                INSERT INTO hidra_leak_detection_profile(id,code,name_fr,topology_asset_type,topology_asset_id,
                    topology_asset_code,method_id,status,created_at,updated_at)
                VALUES (?,?,'Déclaration synthétique GZ2','FACILITY',?,?,?,'ACTIVE',now(),now())
                """, profile, profile, f.asset, f.asset, method);
        JsonNode suspect = r.json("POST", LEAK + "/from-evaluation", operator, Map.of("evaluationId", evaluation,
                "profileId", profile, "declaredConfidence", 0.60, "reason", "Synthetic operator suspicion"), 201);
        String candidate = field(suspect, "candidateId");
        assertEquals("NEW", field(suspect, "status"));

        String classification = f.catalog("incident", "INCIDENT_CLASSIFICATION", "GZ2_ESCALATION");
        String severity = f.catalog("incident", "INCIDENT_SEVERITY", "GZ2_REVIEW");
        var escalation = Map.of("sourceType", "ALARM", "sourceId", alarm, "classificationId", classification,
                "severityId", severity, "titleFr", "Incident synthétique GZ2", "reason", "Human requests formal response");
        String incident = field(r.json("POST", INCIDENT + "/from-evidence", operator, escalation, 201), "incidentId");
        assertEquals(incident, field(r.json("POST", INCIDENT + "/from-evidence", operator, escalation, 200), "incidentId"));
        var secondSource = new java.util.HashMap<>(escalation);
        secondSource.put("sourceType", "LEAK_CANDIDATE");
        secondSource.put("sourceId", candidate);
        assertEquals(incident, field(r.json("POST", INCIDENT + "/from-evidence", operator, secondSource, 200), "incidentId"));
        String incidentPath = INCIDENT + "/" + incident;
        JsonNode decision = r.get(incidentPath + "/escalation", operator);
        assertEquals(f.operator, field(decision, "actorId"));
        assertEquals(evaluation, field(decision, "evaluationId"));
        assertEquals("OPEN", field(decision, "status"));
        JsonNode timeline = r.get(incidentPath + "/timeline", operator);
        assertEquals(2, timeline.size());
        assertEquals(1, r.get(INCIDENT, operator).path("totalElements").asInt(-1));
        return new ResponseChain(rule, evaluation, alarm, candidate, incident, escalation, decision, timeline);
    }

    private static Map<String, String> evaluationRequest(Plan plan, Reading reading, Instant at) {
        return Map.of("targetId", plan.target(), "trustedReadingId", reading.trusted(), "evaluatedAt", at.toString());
    }

    private static void assertDeviation(JsonNode receipt, Plan plan, Reading reading, String unit) {
        assertEquals("COMPUTED", field(receipt, "outcome"));
        assertEquals(105, receipt.path("actual").asInt(-1));
        assertEquals(100, receipt.path("expected").asInt(-1));
        assertEquals(5, receipt.path("difference").asInt(-1));
        assertEquals(unit, field(receipt, "unitId"));
        assertEquals(plan.target(), field(receipt, "targetId"));
        assertEquals(plan.revision(), field(receipt, "targetRevisionId"));
        assertEquals(plan.instance(), field(receipt, "approvalId"));
        assertEquals(reading.raw(), field(receipt, "rawReadingId"));
        assertEquals(reading.assessment(), field(receipt, "assessmentId"));
        assertEquals(reading.trusted(), field(receipt, "trustedReadingId"));
        assertEquals(1, receipt.path("ruleRevision").asInt(-1));
    }

    private static void readAfterRestart(Gz2SignedWorkflowFixture f, Gz2SignedWorkflowFixture.Runtime r, Baselines baselines,
            JsonNode commissioned, Plan plan, Reading reading, ResponseChain response) throws Exception {
        String reader = r.login(f.reader);
        String reviewer = r.login(f.reviewer);
        String operator = r.login(f.operator);
        String publications = BASELINE + "/publications";
        String old = field(baselines.original(), "receiptId");
        String successor = field(baselines.successor(), "receiptId");
        assertEquals(baselines.original(), r.get(engineeringScope(f, publications + "/" + old), reader));
        assertEquals(baselines.successor(), r.get(engineeringScope(f, publications + "/current"), reader));
        assertEquals(old, field(r.get(query(publications + "/effective", "companyPartyId", f.company, "pipelineSystemId", f.pipeline,
                "asOf", baselines.asOfOriginal().toString()), reader), "receiptId"));
        assertEquals(successor, field(r.get(query(publications + "/effective", "companyPartyId", f.company, "pipelineSystemId", f.pipeline,
                "asOf", baselines.asOfSuccessor().toString()), reader), "receiptId"));
        JsonNode history = r.get(query(publications + "/history", "companyPartyId", f.company, "pipelineSystemId", f.pipeline,
                "asOf", baselines.asOfSuccessor().toString(), "limit", "10"), reader).path("items");
        assertEquals(2, history.size());
        assertEquals(successor, field(history.get(0), "receiptId"));
        assertEquals(old, field(history.get(1), "receiptId"));
        JsonNode comparison = r.get(query(publications + "/compare", "companyPartyId", f.company, "pipelineSystemId", f.pipeline,
                "leftReceiptId", old, "rightReceiptId", successor), reader);
        assertEquals(baselines.original(), comparison.path("left"));
        assertEquals(baselines.successor(), comparison.path("right"));
        assertTrue(comparison.has("sameCanonicalSha256"));
        assertFalse(comparison.path("sameCanonicalSha256").asBoolean());

        String revisionPath = PLAN + "/revisions/" + plan.revision();
        assertEquals("APPROVED", field(r.get(revisionPath, reader), "status"));
        JsonNode target = r.get(PLAN + "/targets/" + plan.target(), reader);
        assertEquals("ACTIVE", field(target, "status"));
        assertEquals(100, target.path("targetValue").asInt(-1));
        JsonNode approval = r.get(revisionPath + "/approval", reviewer);
        assertEquals(plan.instance(), field(approval, "workflowInstanceId"));
        assertEquals("COMPLETED", field(approval, "workflowInstanceStatus"));
        String point = field(commissioned, "pointId");
        JsonNode trusted = r.get(trustedRoute(f, point, reading.trusted()), reader);
        assertEquals(reading.raw(), field(trusted, "rawReadingId"));
        assertEquals(reading.assessment(), field(trusted, "assessmentId"));
        assertEquals(field(commissioned, "bindingId"), field(trusted, "bindingId"));
        assertEquals(f.pressureUnit, field(trusted, "unitId"));
        JsonNode raw = r.get(POINT + point + "/readings", reader).path("content");
        assertTrue(raw.isArray());
        assertTrue(java.util.stream.StreamSupport.stream(raw.spliterator(), false)
                .anyMatch(item -> reading.raw().equals(item.path("id").asText())));
        String rulePath = RULE + "/" + response.rule();
        assertEquals(200, r.send("GET", rulePath + "/revisions/1", reader, null).statusCode());
        assertDeviation(r.get(rulePath + "/evaluations/" + response.evaluation(), reader), plan, reading, f.pressureUnit);
        assertEquals("ACKNOWLEDGED", field(r.get(ALARM + "/" + response.alarm(), reader), "currentState"));
        assertEquals(response.evaluation(), field(r.get(ALARM + "/" + response.alarm() + "/evidence", reader), "evaluationId"));
        assertEquals("NEW", field(r.get(LEAK + "/" + response.candidate(), reader), "status"));
        assertEquals(response.evaluation(), field(r.get(LEAK + "/" + response.candidate() + "/evidence", reader), "evaluationId"));
        String incidentPath = INCIDENT + "/" + response.incident();
        assertEquals(response.incident(), field(r.get(incidentPath, reader), "id"));
        assertEquals(response.decision(), r.get(incidentPath + "/escalation", reader));
        assertEquals(response.timeline(), r.get(incidentPath + "/timeline", reader));
        assertEquals(response.incident(), field(r.json("POST", INCIDENT + "/from-evidence", operator, response.escalation(), 200), "incidentId"));
        assertEquals(1, r.get(INCIDENT, reader).path("totalElements").asInt(-1));
    }

    private static void verifyCurrentScope(Gz2SignedWorkflowFixture f, Gz2SignedWorkflowFixture.Runtime r, Baselines baselines,
            JsonNode commissioned, Plan plan, ResponseChain response) throws Exception {
        String incidentPath = INCIDENT + "/" + response.incident();
        String old = field(baselines.original(), "receiptId");
        String baseline = engineeringScope(f, BASELINE + "/publications/" + old);
        String point = field(commissioned, "pointId");
        String foreign = r.login(f.foreign);
        String baselineHistory = query(BASELINE + "/publications/history", "companyPartyId", f.company,
                "pipelineSystemId", f.pipeline, "asOf", baselines.asOfSuccessor().toString());
        noDisclosure(r.send("GET", baseline, foreign, null), old);
        noDisclosure(r.send("GET", baselineHistory, foreign, null), old, field(baselines.successor(), "receiptId"));
        noDisclosure(r.send("GET", POINT + point + "/readings", foreign, null), point);
        for (String actor : List.of(f.foreign, f.wrongAsset, f.revoked)) {
            String bearer = actor.equals(f.foreign) ? foreign : r.login(actor);
            if (actor.equals(f.revoked)) {
                assertEquals(1, r.get(INCIDENT, bearer).path("totalElements").asInt(-1));
                assertEquals(response.incident(), field(r.get(incidentPath, bearer), "id"));
                f.sql.update("""
                        UPDATE hidra_identity_user_permission_grant SET status='REVOKED',revoked_at=now()
                        WHERE user_id=? AND scope_type<>'GLOBAL' AND status='ACTIVE'
                        """, actor);
                noDisclosure(r.send("GET", baseline, bearer, null), old);
                noDisclosure(r.send("GET", baselineHistory, bearer, null), old, field(baselines.successor(), "receiptId"));
            }
            noDisclosure(r.send("GET", incidentPath + "/timeline", bearer, null), response.incident(), response.evaluation());
            noDisclosure(r.send("GET", PLAN + "/targets/" + plan.target(), bearer, null), plan.target());
            noDisclosure(r.send("GET", RULE + "/" + response.rule() + "/evaluations/" + response.evaluation(), bearer, null),
                    response.evaluation());
            noDisclosure(r.send("GET", ALARM + "/" + response.alarm(), bearer, null), response.alarm());
            noDisclosure(r.send("GET", LEAK + "/" + response.candidate() + "/evidence", bearer, null), response.candidate());
            var listing = r.send("GET", INCIDENT, bearer, null);
            if (listing.statusCode() == 200) {
                var page = JSON.readTree(listing.body());
                assertEquals(0, page.path("totalElements").asInt(-1));
                assertEquals(0, page.path("content").size());
                assertFalse(listing.body().contains(response.incident()));
            } else {
                noDisclosure(listing, response.incident());
            }
            noDisclosure(r.send("POST", INCIDENT + "/from-evidence", bearer, response.escalation()), response.incident());
        }
        assertEquals(1L, f.sql.queryForObject("SELECT count(*) FROM hidra_incident_evaluation_case WHERE evaluation_id=?",
                Long.class, response.evaluation()));
    }

    private static void noDisclosure(HttpResponse<String> response, String... protectedIds) throws Exception {
        assertDenied(response.statusCode());
        JsonNode payload = JSON.readTree(response.body());
        if (payload instanceof ObjectNode problem) {
            String requestPath = response.request().uri().getPath();
            for (String field : List.of("instance", "path")) {
                if (requestPath.equals(problem.path(field).asText())) {
                    problem.remove(field);
                }
            }
        }
        String body = payload == null ? "" : payload.toString();
        for (String id : protectedIds) {
            assertFalse(body.contains(id), "Denial disclosed a protected identifier");
        }
        assertFalse(body.contains("\"totalElements\""), "Denial disclosed a protected count");
    }

    private static void assertDenied(int status) {
        assertTrue(status == 403 || status == 404, "Expected authority denial, received " + status);
    }

    private static void verifyPublishedOperations(Gz2SignedWorkflowFixture.Runtime runtime) throws Exception {
        JsonNode paths = runtime.get("/v3/api-docs", null).path("paths");
        String scenario="/api/v1/simulation/companies/{companyId}/pipeline-systems/{pipelineSystemId}/scenarios";
        assertTrue(paths.path(scenario).has("post"));
        assertTrue(paths.path(scenario+"/{scenarioId}/revisions").has("post"));
        assertTrue(paths.path(scenario+"/{scenarioId}/revisions/{revision}").has("get"));
        assertTrue(paths.path(scenario+"/{scenarioId}/revisions/{revision}/validation").has("get"));
        for (String route : List.of(BASELINE + "/publications/{receiptId}", PLAN + "/targets/{id}",
                POINT + "{pointId}/trusted-readings/{trustedId}", ASSET_POINTS,
                ASSET_POINTS + "/{pointId}/trusted-readings/latest", ASSET_POINTS + "/{pointId}/trusted-readings/history",
                RULE + "/{id}/evaluations/{evaluationId}",
                ALARM + "/{id}/evidence", LEAK + "/{id}/evidence", INCIDENT + "/{id}/timeline")) {
            assertTrue(paths.path(route).has("get"), () -> "Runtime OpenAPI omits GET " + route);
        }
        for (String route : List.of(BASELINE + "/submissions/{submissionId}/publications", PLAN + "/revisions/{revisionId}/submit",
                POINT + "{pointId}/readings/{readingId}/trust", ALARM + "/from-evaluation", LEAK + "/from-evaluation",
                INCIDENT + "/from-evidence")) {
            assertTrue(paths.path(route).has("post"), () -> "Runtime OpenAPI omits POST " + route);
        }
    }

    private static String trustedRoute(Gz2SignedWorkflowFixture f, String point, String reading) {
        return query(POINT + point + "/trusted-readings/" + reading, "companyPartyId", f.company);
    }

    private static String engineeringScope(Gz2SignedWorkflowFixture f, String path) {
        return query(path, "companyPartyId", f.company, "pipelineSystemId", f.pipeline);
    }

    private static Instant databaseTime(Gz2SignedWorkflowFixture f) {
        return f.sql.queryForObject("SELECT clock_timestamp()", java.sql.Timestamp.class).toInstant();
    }

    private static Instant now() {
        return Instant.now().truncatedTo(ChronoUnit.MICROS);
    }

    private static String field(JsonNode result, String name) {
        String value = result.path(name).asText();
        assertFalse(value.isBlank(), () -> "Missing " + name + " in a successful synthetic response");
        return value;
    }
}
