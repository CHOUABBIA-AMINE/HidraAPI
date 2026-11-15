/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : Gz2PlanningNominationNativeHttpPostgresIntegrationTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-11-15
 *
 * @Type        : Class
 * @Layer       : Test
 * @Module      : qualification
 * @Package     : dz.sh.hidra.qualification
 *
 * @Description : Exercises signed scoped nomination lifecycle on fresh migrated PostgreSQL.
 *
 */
package dz.sh.hidra.qualification;

import com.fasterxml.jackson.databind.JsonNode;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Test;
import org.testcontainers.containers.PostgreSQLContainer;

import static org.junit.jupiter.api.Assertions.*;

class Gz2PlanningNominationNativeHttpPostgresIntegrationTest {
    private static final String PLAN = "/api/v1/planning";

    @Test
    void currentApprovedRevisionSupportsIndependentNominationDisposition() throws Exception {
        try (var postgres = new PostgreSQLContainer<>("postgres:16").withDatabaseName("hidra_gz2_t19")) {
            postgres.start();
            var f = new Gz2SignedWorkflowFixture(postgres);
            grantPlanning(f);
            var review = f.review("planning", "PLAN_REVISION", "PLAN_REVISION_APPROVAL", true);
            String nominationType = f.catalog("planning", "NOMINATION_TYPE", "T19_SYNTHETIC_MOVEMENT");
            String targetType = f.catalog("planning", "TARGET_TYPE", "T19_SYNTHETIC_TARGET");
            f.sql.update("""
                    INSERT INTO hidra_planning_target_value_policy(target_type_id,representation_kind,active)
                    VALUES (?,'TEXT',true)
                    """, targetType);
            String quantityUnit = unit(f, "QUANTITY");
            String rateUnit = unit(f, "RATE");
            f.sql.update("INSERT INTO hidra_custody_planning_product_policy VALUES (?,true,'T19_SYNTHETIC')", f.product);
            f.sql.update("INSERT INTO hidra_telemetry_planning_unit_pair VALUES (?,?,true,'T19_SYNTHETIC')",
                    quantityUnit, rateUnit);

            Instant start = Instant.now().truncatedTo(ChronoUnit.SECONDS);
            Instant end = start.plus(4, ChronoUnit.HOURS);
            try (var runtime = f.start()) {
                String author = runtime.login(f.author);
                String reviewer = runtime.login(f.reviewer);
                String wrongAsset = runtime.login(f.wrongAsset);
                String foreign = runtime.login(f.foreign);
                String revoked = runtime.login(f.revoked);
                String plan = createPlan(f, runtime, author, start, end);
                ApprovedRevision original = approve(f, runtime, review, plan, targetType, author, reviewer, start, end);
                String createPath = PLAN + "/revisions/" + original.id() + "/nominations";
                Map<String, Object> request = createRequest(f, original, nominationType, quantityUnit, rateUnit, start, end);

                var sameEndpoint = new LinkedHashMap<>(request);
                sameEndpoint.put("destinationFacilityId", f.asset);
                assertEquals(404, runtime.send("POST", createPath, author, sameEndpoint).statusCode());
                var unapprovedProduct = new LinkedHashMap<>(request);
                unapprovedProduct.put("productTypeId", f.catalog("custody", "PRODUCT", "T19_UNAPPROVED_PRODUCT"));
                assertEquals(400, runtime.send("POST", createPath, author, unapprovedProduct).statusCode());
                var unapprovedUnit = new LinkedHashMap<>(request);
                unapprovedUnit.put("quantityUnitId", f.pressureUnit);
                assertEquals(400, runtime.send("POST", createPath, author, unapprovedUnit).statusCode());
                assertEquals(0, f.sql.queryForObject(
                        "SELECT count(*) FROM hidra_planning_nomination WHERE revision_id=?", Integer.class, original.id()));

                JsonNode draft = runtime.json("POST", createPath, author, request, 200).path("event");
                String nominationId = draft.path("nominationId").asText();
                assertFalse(nominationId.isBlank());
                assertEquals("DRAFT", draft.path("status").asText());
                assertEquals(original.id(), draft.path("revisionId").asText());
                assertTrue(runtime.json("POST", createPath, author, request, 200).path("replay").asBoolean());
                var differentPayload = new LinkedHashMap<>(request);
                differentPayload.put("quantity", 120);
                assertEquals(409, runtime.send("POST", createPath, author, differentPayload).statusCode());

                String resource = PLAN + "/nominations/" + nominationId;
                String list = Gz2SignedWorkflowFixture.query(PLAN + "/nominations", "revisionId", original.id());
                JsonNode detail = runtime.get(resource, author);
                assertEquals(original.id(), detail.path("revisionId").asText());
                assertEquals(f.company, detail.path("shipperPartyId").asText());
                assertEquals(f.secondAsset, detail.path("destinationAssetId").asText());
                assertEquals(1, runtime.get(list, author).path("totalElements").asLong());
                assertEquals(0, runtime.get(list, wrongAsset).path("totalElements").asLong());
                assertEquals(404, runtime.send("GET", resource, wrongAsset, null).statusCode());
                assertEquals(404, runtime.send("GET", resource + "/history", wrongAsset, null).statusCode());
                assertEquals(404, runtime.send("GET", resource + "/history", foreign, null).statusCode());

                var confirm = disposition("CONFIRM", 0);
                assertEquals(404, runtime.send("POST", resource + "/disposition", wrongAsset, confirm).statusCode());
                assertEquals(409, runtime.send("POST", resource + "/disposition", author, confirm).statusCode());
                assertEquals(409, runtime.send("POST", resource + "/disposition", reviewer,
                        disposition("CONFIRM", 1)).statusCode());
                JsonNode confirmed = runtime.json("POST", resource + "/disposition", reviewer, confirm, 200).path("event");
                assertEquals("CONFIRMED", confirmed.path("status").asText());
                assertEquals(f.reviewer, confirmed.path("actorId").asText());
                assertTrue(runtime.json("POST", resource + "/disposition", reviewer, confirm, 200).path("replay").asBoolean());

                String rejected = draft(runtime, createPath, author, request, "T19-REJECTED");
                assertEquals("REJECTED", runtime.json("POST", PLAN + "/nominations/" + rejected + "/disposition",
                        reviewer, disposition("REJECT", 0), 200).path("event").path("status").asText());
                String pending = draft(runtime, createPath, author, request, "T19-HISTORICAL-DRAFT");
                assertEquals("ACTIVE", runtime.get(PLAN + "/targets/" + original.targetId(), author).path("status").asText());
                assertEquals(original.updatedAt(), runtime.get(PLAN + "/revisions/" + original.id(),
                        author).path("updatedAt").asText());
                assertEquals(2, runtime.get(resource + "/history", author).size());

                f.sql.update("""
                        UPDATE hidra_identity_user_permission_grant SET status='REVOKED', revoked_at=now()
                        WHERE user_id=? AND scope_type='FACILITY' AND scope_reference_id=?
                        """, f.revoked, f.secondAsset);
                assertEquals(404, runtime.send("GET", resource + "/history", revoked, null).statusCode());
                assertEquals(0, runtime.get(list, revoked).path("totalElements").asLong());

                approve(f, runtime, review, plan, targetType, author, reviewer, start, end);
                assertEquals("SUPERSEDED", runtime.get(PLAN + "/revisions/" + original.id(), author).path("status").asText());
                assertEquals("CONFIRMED", runtime.get(resource, author).path("status").asText());
                assertEquals(2, runtime.get(resource + "/history", author).size());
                assertEquals(409, runtime.send("POST", PLAN + "/nominations/" + pending + "/disposition",
                        reviewer, disposition("CONFIRM", 0)).statusCode());
                assertEquals(1, runtime.get(PLAN + "/nominations/" + pending + "/history", author).size());
            }
        }
    }

    private static String draft(Gz2SignedWorkflowFixture.Runtime runtime,
            String createPath, String author, Map<String, Object> original, String code) throws Exception {
        var request = new LinkedHashMap<>(original);
        request.put("commandId", Gz2SignedWorkflowFixture.id());
        request.put("code", code);
        return runtime.json("POST", createPath, author, request, 200).path("event").path("nominationId").asText();
    }

    private static Map<String, Object> disposition(String decision, long version) {
        return Map.of("commandId", Gz2SignedWorkflowFixture.id(), "expectedVersion", version,
                "decision", decision, "reason", "Independent synthetic movement disposition");
    }

    private static Map<String, Object> createRequest(Gz2SignedWorkflowFixture f, ApprovedRevision original,
            String type, String quantityUnit, String rateUnit, Instant start, Instant end) {
        return Map.ofEntries(Map.entry("commandId", Gz2SignedWorkflowFixture.id()),
                Map.entry("expectedRevisionUpdatedAt", original.updatedAt()), Map.entry("code", "T19-GZ2"),
                Map.entry("nominationTypeId", type), Map.entry("productTypeId", f.product),
                Map.entry("quantity", 100), Map.entry("quantityUnitId", quantityUnit),
                Map.entry("rate", 25), Map.entry("rateUnitId", rateUnit),
                Map.entry("destinationFacilityId", f.secondAsset), Map.entry("periodStart", start.toString()),
                Map.entry("periodEnd", end.toString()));
    }

    private static String createPlan(Gz2SignedWorkflowFixture f, Gz2SignedWorkflowFixture.Runtime runtime,
            String author, Instant start, Instant end) throws Exception {
        String periodId = runtime.json("POST", "/api/v2/planning/periods", author, Map.of(
                "companyPartyId", f.company, "code", "T19", "nameFr", "Période synthétique",
                "periodTypeId", f.catalog("planning", "PERIOD_TYPE", "T19_PERIOD"),
                "periodStart", start.toString(), "periodEnd", end.toString()), 200).path("id").asText();
        return runtime.json("POST", PLAN + "/operational-plans", author, Map.of(
                "periodId", periodId, "code", "T19", "nameFr", "Plan synthétique",
                "planTypeId", f.catalog("planning", "PLAN_TYPE", "T19_PLAN"),
                "topologyScopeType", "FACILITY", "topologyScopeId", f.asset), 200).path("id").asText();
    }

    private static ApprovedRevision approve(Gz2SignedWorkflowFixture f, Gz2SignedWorkflowFixture.Runtime runtime,
            Gz2SignedWorkflowFixture.Review review, String plan, String targetType, String author,
            String reviewer, Instant start, Instant end) throws Exception {
        String planPath = PLAN + "/operational-plans/" + plan;
        JsonNode draft = runtime.json("POST", planPath + "/revisions", author,
                Map.of("expectedPlanUpdatedAt", runtime.get(planPath, author).path("updatedAt").asText()), 200);
        String id = draft.path("id").asText();
        String revisionPath = PLAN + "/revisions/" + id;
        JsonNode target = runtime.json("POST", revisionPath + "/targets", author, Map.of(
                "expectedRevisionUpdatedAt", draft.path("updatedAt").asText(), "targetTypeId", targetType,
                "topologyAssetType", "FACILITY", "topologyAssetId", f.asset, "targetTextValue", "Synthetic",
                "validFrom", start.toString(), "validTo", end.toString()), 200);
        JsonNode submitted = runtime.json("POST", revisionPath + "/submit", author, Map.of(
                "expectedPlanUpdatedAt", runtime.get(planPath, author).path("updatedAt").asText(),
                "expectedRevisionUpdatedAt", target.path("revisionUpdatedAt").asText(),
                "definitionId", review.definition(), "definitionVersion", 1, "targetTypeId", review.type(),
                "workflowPurposeId", review.purpose(), "reviewerActorId", f.reviewer), 200);
        JsonNode result = runtime.json("POST", revisionPath + "/approval/actions/" + review.transition() + "/execute",
                reviewer, Map.of("expectedTaskUpdatedAt", submitted.path("workflowTaskUpdatedAt").asText()), 200);
        assertEquals("APPROVED", result.path("revisionStatus").asText());
        return new ApprovedRevision(id, runtime.get(revisionPath, author).path("updatedAt").asText(),
                target.path("target").path("id").asText());
    }

    private static void grantPlanning(Gz2SignedWorkflowFixture f) {
        for (String actor : List.of(f.author, f.reviewer, f.wrongAsset, f.foreign, f.revoked)) {
            f.global(actor, "planning:operational-plans:read", "planning:revisions:read",
                    "planning:targets:read", "planning:nominations:read");
            if (actor.equals(f.foreign)) {
                f.assetGrant(actor, "planning:scoped-plan:read", f.foreignCompany, f.asset);
            } else {
                f.assetGrant(actor, "planning:scoped-plan:read", f.company, f.asset);
                if (!actor.equals(f.wrongAsset)) {
                    f.permit(actor, "planning:scoped-plan:read", "PLANNING", "FACILITY", f.secondAsset);
                }
            }
        }
        f.global(f.author, "planning:periods:execute", "planning:operational-plans:execute", "planning:revisions:execute");
        f.global(f.reviewer, "planning:revisions:execute");
        f.permit(f.author, "planning:scoped-period:create", "PLANNING", "CUSTOM", "planning-company:" + f.company);
        f.assetGrant(f.author, "planning:scoped-plan:create", f.company, f.asset);
        f.assetGrant(f.author, "planning:scoped-plan:author", f.company, f.asset);
        f.permit(f.author, "planning:scoped-plan:author", "PLANNING", "FACILITY", f.secondAsset);
        for (String actor : List.of(f.reviewer, f.author, f.wrongAsset)) {
            f.assetGrant(actor, "planning:scoped-plan:review", f.company, f.asset);
            if (!actor.equals(f.wrongAsset)) {
                f.permit(actor, "planning:scoped-plan:review", "PLANNING", "FACILITY", f.secondAsset);
            }
        }
    }

    private static String unit(Gz2SignedWorkflowFixture f, String role) {
        String unit = Gz2SignedWorkflowFixture.id();
        f.sql.update("""
                INSERT INTO hidra_telemetry_unit(id,code,symbol,dimension,active,system_defined,created_at,updated_at)
                VALUES (?,?,?,'FIXTURE',true,false,now(),now())
                """, unit, "T19_" + role, role);
        f.sql.update("INSERT INTO hidra_telemetry_planning_unit_role VALUES (?,?,true,'T19_SYNTHETIC')", unit, role);
        return unit;
    }

    private record ApprovedRevision(String id, String updatedAt, String targetId) { }
}
