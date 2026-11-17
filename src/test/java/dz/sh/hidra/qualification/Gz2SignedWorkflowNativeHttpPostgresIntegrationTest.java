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
    private static final String RULE = "/api/v1/monitoring/rules";
    private static final String ALARM = "/api/v1/alarm/alarms";
    private static final String LEAK = "/api/v1/leakdetection/candidates";
    private static final String INCIDENT = "/api/v1/incident/incidents";

    private record Baselines(JsonNode original, JsonNode successor, Instant asOfOriginal, Instant asOfSuccessor) { }
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

            Instant start = now().minusSeconds(900);
            Instant end = start.plusSeconds(7_200);
            Baselines baselines;
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
                var review = fixture.review("topology", "ENGINEERING_BASELINE_REVISION", "ENGINEERING_BASELINE_APPROVAL", false);
                Instant firstEffective = now();
                JsonNode baseline = publishBaseline(fixture, runtime, author, reviewer, review, network, product, firstEffective, 0);
                Instant beforeSuccessor = databaseTime(fixture);

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
                baselines = new Baselines(baseline, successor, beforeSuccessor, databaseTime(fixture));
                System.out.printf("GZ2 phase=committed pid=%d baseline=%s successor=%s target=%s trusted=%s evaluation=%s incident=%s%n",
                        firstPid, field(baseline, "receiptId"), field(successor, "receiptId"), plan.target(), reading.trusted(),
                        response.evaluation(), response.incident());
            }

            try (var restarted = fixture.start()) {
                assertNotEquals(firstPid, restarted.pid(), "The original application JVM must be replaced");
                verifyPublishedOperations(restarted);
                readAfterRestart(fixture, restarted, baselines, commissioned, plan, reading, response);
                verifyCurrentScope(fixture, restarted, baselines, commissioned, plan, response);
                System.out.printf("GZ2 phase=reconstructed pid=%d originalPid=%d scope=foreign,wrong-asset,revoked%n",
                        restarted.pid(), firstPid);
            }
        }
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
        JsonNode receipt = r.json("POST", "/api/v1/telemetry/commissioning", producer, Map.ofEntries(
                Map.entry("requestId", id()), Map.entry("companyPartyId", f.company), Map.entry("assetType", "FACILITY"),
                Map.entry("assetId", f.asset), Map.entry("sourceCode", "GZ2-S"), Map.entry("sourceNameFr", "Source synthétique"),
                Map.entry("sourceTypeId", f.telemetryType("SOURCE_TYPE", null)), Map.entry("protocolId", f.telemetryType("PROTOCOL", null)),
                Map.entry("deviceCode", "GZ2-D"), Map.entry("deviceNameFr", "Appareil synthétique"),
                Map.entry("deviceTypeId", f.telemetryType("DEVICE_TYPE", null)), Map.entry("pointCode", "GZ2-P"),
                Map.entry("pointNameFr", "Pression synthétique"), Map.entry("pointTypeId", f.telemetryType("POINT_TYPE", null)),
                Map.entry("signalTypeId", f.telemetryType("SIGNAL_TYPE", "NUMERIC")), Map.entry("unitId", f.pressureUnit),
                Map.entry("bindingRoleId", f.telemetryType("BINDING_ROLE", null)), Map.entry("externalTagName", "GZ2.PRESSURE"),
                Map.entry("validFrom", start.toString())), 200);
        assertEquals(f.asset, field(receipt, "assetId"));
        assertFalse(field(receipt, "bindingId").isBlank());
        return receipt;
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
        String point = field(commissioned, "pointId");
        String reference = "telemetry-source-point:" + field(commissioned, "sourceId") + ":" + point;
        f.paired(f.producer, "telemetry:raw-reading", "TELEMETRY_READING", "telemetry-company:" + f.company,
                reference, "ingest", "read");
        f.paired(f.producer, "telemetry:trusted-reading", "TRUSTED_READING", "telemetry-company:" + f.company,
                reference, "qualify", "read");
        f.paired(f.reader, "telemetry:raw-reading", "TELEMETRY_READING", "telemetry-company:" + f.company, reference, "read");
        f.paired(f.reader, "telemetry:trusted-reading", "TRUSTED_READING", "telemetry-company:" + f.company, reference, "read");
        f.global(f.reader, "telemetry:points:read");
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
        for (String route : List.of(BASELINE + "/publications/{receiptId}", PLAN + "/targets/{id}",
                POINT + "{pointId}/trusted-readings/{trustedId}", RULE + "/{id}/evaluations/{evaluationId}",
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
