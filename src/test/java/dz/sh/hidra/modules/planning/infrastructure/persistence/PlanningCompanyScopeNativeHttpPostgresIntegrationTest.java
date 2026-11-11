/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : PlanningCompanyScopeNativeHttpPostgresIntegrationTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-11-10
 *
 * @Type        : Class
 * @Layer       : Test
 * @Module      : planning
 * @Package     : dz.sh.hidra.modules.planning.infrastructure.persistence
 *
 * @Description : Verifies company and asset scope over signed HTTP and migrated PostgreSQL.
 *
 */
package dz.sh.hidra.modules.planning.infrastructure.persistence;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import dz.sh.hidra.HidraApplication;
import dz.sh.hidra.modules.identity.application.model.DirectAuthenticationCommand;
import dz.sh.hidra.modules.identity.application.port.in.AuthenticateDirectUserUseCase;
import dz.sh.hidra.modules.identity.domain.value.ProviderType;
import dz.sh.hidra.platform.permissions.HidraRoutePermissionCatalogService;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.Callable;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import org.flywaydb.core.Flyway;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.servlet.context.ServletWebServerApplicationContext;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@Testcontainers(disabledWithoutDocker = true)
@ActiveProfiles("test")
@SpringBootTest(classes = HidraApplication.class, webEnvironment = SpringBootTest.WebEnvironment.MOCK,
        properties = {
            "hidra.platform.security.jwt.hmac-secret=" + PlanningCompanyScopeNativeHttpPostgresIntegrationTest.SECRET,
            "hidra.platform.security.jwt.token-issuer=" + PlanningCompanyScopeNativeHttpPostgresIntegrationTest.ISSUER,
            "hidra.platform.security.jwt.audience=hidra-api",
            "springdoc.api-docs.enabled=true"
        })
class PlanningCompanyScopeNativeHttpPostgresIntegrationTest {

    static final String SECRET = "synthetic-t01-planning-native-signing-key-32-bytes";
    static final String ISSUER = "https://identity.hidra.invalid/planning";
    private static final String PASSWORD = "synthetic-t01-local-password";
    private static final String V1 = "/api/v1/planning";
    private static final String V2 = "/api/v2/planning/periods";
    private static final String LEGACY_PERIOD = "t01-unowned-legacy-period";
    private static final String PERIOD_TYPE = "t01-period-type";
    private static final ObjectMapper JSON = new ObjectMapper();

    @Container
    static final PostgreSQLContainer<?> PG = new PostgreSQLContainer<>("postgres:16-alpine")
            .withDatabaseName("hidra_planning_t01");

    @DynamicPropertySource
    static void datasource(DynamicPropertyRegistry registry) {
        if (!PG.isRunning()) {
            PG.start();
        }
        var datasource = new DriverManagerDataSource(PG.getJdbcUrl(), PG.getUsername(), PG.getPassword());
        Flyway.configure().dataSource(datasource).target("20261110.001").load().migrate();
        var jdbc = new JdbcTemplate(datasource);
        addCatalog(jdbc, PERIOD_TYPE, "PERIOD_TYPE");
        jdbc.update("""
                INSERT INTO hidra_planning_period (id,code,name_fr,period_type_id,period_start,period_end,
                    time_zone,status,created_by_actor_id,created_at,updated_at)
                VALUES (?,?,'Legacy period',?,now(),now()+interval '1 day','Africa/Algiers','OPEN','legacy',now(),now())
                """, LEGACY_PERIOD, LEGACY_PERIOD, PERIOD_TYPE);
        Flyway.configure().dataSource(datasource).load().migrate();
        registry.add("spring.datasource.url", PG::getJdbcUrl);
        registry.add("spring.datasource.username", PG::getUsername);
        registry.add("spring.datasource.password", PG::getPassword);
        registry.add("spring.flyway.url", PG::getJdbcUrl);
        registry.add("spring.flyway.user", PG::getUsername);
        registry.add("spring.flyway.password", PG::getPassword);
        registry.add("spring.jpa.hibernate.ddl-auto", () -> "validate");
    }

    @Autowired private WebApplicationContext context;
    @Autowired private AuthenticateDirectUserUseCase authentication;
    @Autowired private PasswordEncoder passwords;
    @Autowired private HidraRoutePermissionCatalogService catalog;

    private JdbcTemplate db;
    private MockMvc http;
    private String company;
    private String otherCompany;
    private String facility;
    private String otherFacility;
    private String foreignFacility;
    private String planType;

    @BeforeEach
    void fixture() {
        SecurityContextHolder.clearContext();
        db = new JdbcTemplate(new DriverManagerDataSource(PG.getJdbcUrl(), PG.getUsername(), PG.getPassword()));
        http = MockMvcBuilders.webAppContextSetup(context).apply(springSecurity()).build();
        String partyType = id();
        String facilityType = id();
        db.update("INSERT INTO hidra_party_type(id,code,status,created_at,updated_at) VALUES (?,?,'ACTIVE',now(),now())",
                partyType, partyType);
        company = company(partyType);
        otherCompany = company(partyType);
        db.update("""
                INSERT INTO hidra_topology_facility_type(id,code,name,facility_kind,status,created_at,updated_at)
                VALUES (?,?,'Synthetic gas station','COMPRESSOR_STATION','ACTIVE',now(),now())
                """, facilityType, facilityType);
        facility = facility(facilityType, company);
        otherFacility = facility(facilityType, company);
        foreignFacility = facility(facilityType, otherCompany);
        planType = id();
        addCatalog(db, planType, "PLAN_TYPE");
        db.update("UPDATE hidra_identity_provider SET status='INACTIVE' WHERE status='ACTIVE'");
        String provider = id();
        db.update("""
                INSERT INTO hidra_identity_provider(id,code,name,provider_type,status,sync_enabled,
                    just_in_time_provisioning_enabled,created_at,updated_at)
                VALUES (?,?,'Synthetic Planning identity','LOCAL','ACTIVE',false,false,now(),now())
                """, provider, provider);
    }

    @Test
    void enforcesVersionedPeriodContractAndCompanyUniqueness() throws Exception {
        var author = actor(company, facility, true);
        var foreign = actor(otherCompany, foreignFacility, true);
        var routeDenied = actor(company, facility, false);
        String code = id();
        createPeriod(author, code, otherCompany).andExpect(status().isForbidden());
        createPeriod(routeDenied, code, company).andExpect(status().isForbidden());
        createPeriod(author, code, null).andExpect(status().isBadRequest());
        createPeriod(author, code, " ").andExpect(status().isBadRequest());
        for (String legacy : List.of(V1 + "/periods", V1 + "/create-planning-period")) {
            http.perform(post(legacy).contentType(MediaType.APPLICATION_JSON).content(periodPayload(code, null)))
                    .andExpect(status().isUnauthorized());
            http.perform(post(legacy).header("Authorization", author.bearer())
                    .contentType(MediaType.APPLICATION_JSON).content(periodPayload(code, null)))
                    .andExpect(status().isForbidden());
        }
        assertEquals(0L, periodCount(code));
        String period = period(author, code, company);
        String foreignPeriod = period(foreign, code, otherCompany);
        assertNotEquals(period, foreignPeriod);
        createPeriod(author, code, company).andExpect(status().isConflict());
        assertEquals(2L, periodCount(code));
        get(author, "/periods/" + period).andExpect(status().isOk())
                .andExpect(jsonPath("$.companyPartyId").value(company))
                .andExpect(jsonPath("$.createdByActorId").value(author.id()));
        get(author, "/periods/" + foreignPeriod).andExpect(status().isNotFound());
        get(author, "/periods/" + LEGACY_PERIOD).andExpect(status().isNotFound());
        get(author, "/periods").andExpect(status().isOk()).andExpect(jsonPath("$.totalElements").value(1));
        assertThrows(DataIntegrityViolationException.class, () -> db.update("""
                INSERT INTO hidra_planning_period (id,code,name_fr,period_type_id,period_start,period_end,
                    time_zone,status,created_by_actor_id,created_at,updated_at)
                VALUES (?,?,'Unowned',?,now(),now()+interval '1 day','Africa/Algiers','OPEN',?,now(),now())
                """, id(), id(), PERIOD_TYPE, author.id()));
        assertThrows(DataIntegrityViolationException.class, () -> db.update(
                "UPDATE hidra_planning_period SET company_party_id=? WHERE id=?", otherCompany, period));
    }

    @Test
    void filtersPlansAndDescendantsBeforeDisclosureAndHonorsCurrentDenies() throws Exception {
        var author = actor(company, facility, true);
        var sameCompanyOtherAsset = actor(company, otherFacility, true);
        var foreign = actor(otherCompany, foreignFacility, true);
        String period = period(author, id(), company);
        String foreignPeriod = period(foreign, id(), otherCompany);
        String plan = plan(author, period, facility);
        String otherPlan = plan(sameCompanyOtherAsset, period, otherFacility);
        String foreignPlan = plan(foreign, foreignPeriod, foreignFacility);
        createPlan(author, period, otherFacility).andExpect(status().isNotFound());
        createPlan(author, foreignPeriod, facility).andExpect(status().isNotFound());
        get(author, "/operational-plans").andExpect(status().isOk())
                .andExpect(jsonPath("$.totalElements").value(1))
                .andExpect(jsonPath("$.content[0].id").value(plan));
        get(author, "/operational-plans/" + plan).andExpect(status().isOk())
                .andExpect(jsonPath("$.createdByActorId").value(author.id()));
        for (String hidden : List.of(otherPlan, foreignPlan, id())) {
            get(author, "/operational-plans/" + hidden).andExpect(status().isNotFound())
                    .andExpect(jsonPath("$.detail").value("Planning resource unavailable."));
            get(author, "/revisions?planId=" + hidden).andExpect(status().isNotFound());
        }
        String revision = revision(plan);
        String hiddenRevision = revision(otherPlan);
        get(author, "/revisions/" + revision).andExpect(status().isOk());
        get(author, "/revisions?planId=" + plan).andExpect(status().isOk())
                .andExpect(jsonPath("$.totalElements").value(1));
        get(author, "/revisions/" + hiddenRevision).andExpect(status().isNotFound());
        get(author, "/revisions/" + hiddenRevision + "/approval").andExpect(status().isNotFound());
        get(author, "/targets?revisionId=" + hiddenRevision).andExpect(status().isNotFound());
        get(author, "/nominations?revisionId=" + hiddenRevision).andExpect(status().isNotFound());

        grant(author.id(), permission("planning:scoped-plan:read"), "FACILITY", facility, "DENY");
        get(author, "/operational-plans/" + plan).andExpect(status().isNotFound());
        get(author, "/operational-plans").andExpect(status().isOk())
                .andExpect(jsonPath("$.totalElements").value(0));

        db.update("""
                UPDATE hidra_identity_user_permission_grant SET status='REVOKED',revoked_at=now()
                WHERE user_id=? AND scope_reference_id=?
                """, author.id(), "planning-company:" + company);
        get(author, "/periods/" + period).andExpect(status().isNotFound());
        createPeriod(author, id(), company).andExpect(status().isForbidden());
    }

    @Test
    void concurrentSameCompanyCreatesHaveExactlyOneWinner() throws Exception {
        var author = actor(company, facility, true);
        String code = id();
        Callable<Integer> create = () -> createPeriod(author, code, company)
                .andReturn().getResponse().getStatus();
        try (var pool = Executors.newFixedThreadPool(2)) {
            var responses = pool.invokeAll(List.of(create, create), 30, TimeUnit.SECONDS);
            var results = new java.util.ArrayList<Integer>();
            for (var response : responses) {
                results.add(response.get());
            }
            results.sort(Integer::compareTo);
            assertEquals(List.of(200, 409), results);
        }
        assertEquals(1L, periodCount(code));
    }

    @Test
    void publishesRuntimeContractAndCatalogAndReadsPersistedPeriodAfterApplicationRestart() throws Exception {
        var author = actor(company, facility, true);
        String period = period(author, id(), company);
        String plan = plan(author, period, facility);
        JsonNode draft = body(createRevision(author, plan, planToken(author, plan)));
        JsonNode target = body(writeTarget(author, draft.path("id").asText(), null,
                targetBody(draft.path("updatedAt").asText(), targetType("NUMERIC"), telemetryPoint(facility))));
        String targetId = target.path("target").path("id").asText();
        assertTrue(catalog.routes().stream().anyMatch(route -> V2.equals(route.route())
                && route.methods().contains("POST") && route.permission().equals("planning:periods:execute")));
        JsonNode api = JSON.readTree(http.perform(
                org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get("/v3/api-docs"))
                .andExpect(status().isOk()).andReturn().getResponse().getContentAsString());
        for (String legacy : List.of(V1 + "/periods", V1 + "/create-planning-period")) {
            assertEquals("#/components/schemas/CreatePlanningPeriodRequest",
                    api.path("paths").path(legacy).path("post").path("requestBody").path("content")
                            .path("application/json").path("schema").path("$ref").asText());
        }
        assertTrue(api.at("/components/schemas/CreateCompanyPlanningPeriodRequest/required")
                .valueStream().anyMatch(node -> node.asText().equals("companyPartyId")));

        try (var restarted = new SpringApplicationBuilder(HidraApplication.class).profiles("test").run(
                "--server.port=0", "--management.server.port=0", "--spring.datasource.url=" + PG.getJdbcUrl(),
                "--spring.datasource.username=" + PG.getUsername(), "--spring.datasource.password=" + PG.getPassword(),
                "--spring.flyway.url=" + PG.getJdbcUrl(), "--spring.flyway.user=" + PG.getUsername(),
                "--spring.flyway.password=" + PG.getPassword(), "--spring.jpa.hibernate.ddl-auto=validate",
                "--hidra.platform.security.jwt.hmac-secret=" + SECRET,
                "--hidra.platform.security.jwt.token-issuer=" + ISSUER,
                "--hidra.platform.security.jwt.audience=hidra-api")) {
            int port = ((ServletWebServerApplicationContext) restarted).getWebServer().getPort();
            var request = HttpRequest.newBuilder(URI.create("http://127.0.0.1:" + port + V1 + "/periods/" + period))
                    .header("Authorization", author.bearer()).GET().build();
            try (var client = HttpClient.newHttpClient()) {
                var response = client.send(request, HttpResponse.BodyHandlers.ofString());
                assertEquals(200, response.statusCode());
                assertEquals(period, JSON.readTree(response.body()).path("id").asText());
                var targetRequest = HttpRequest.newBuilder(URI.create(
                        "http://127.0.0.1:" + port + V1 + "/targets/" + targetId))
                        .header("Authorization", author.bearer()).GET().build();
                var targetReadback = client.send(targetRequest, HttpResponse.BodyHandlers.ofString());
                assertEquals(200, targetReadback.statusCode());
                assertEquals(100, JSON.readTree(targetReadback.body()).path("targetValue").asInt());
                assertEquals("DRAFT", JSON.readTree(targetReadback.body()).path("status").asText());
            }
        }
    }

    @Test
    void authorsDraftTargetsWithAtomicVersionChecksAndScopedReadback() throws Exception {
        var author = actor(company, facility, true);
        var foreign = actor(otherCompany, foreignFacility, true);
        String period = period(author, id(), company);
        String plan = plan(author, period, facility);
        JsonNode revision = body(createRevision(author, plan, planToken(author, plan)));
        String revisionId = revision.path("id").asText();
        assertEquals(1, revision.path("revisionNumber").asInt());
        assertEquals("DRAFT", revision.path("status").asText());
        assertEquals(revisionId, body(get(author, "/operational-plans/" + plan)).path("currentRevisionId").asText());
        TelemetryFixture point = telemetryPoint(facility);
        String targetType = targetType("NUMERIC");
        var draft = targetBody(revision.path("updatedAt").asText(), targetType, point);
        JsonNode created = body(writeTarget(author, revisionId, null, draft));
        String target = created.path("target").path("id").asText();
        get(author, "/targets/" + target).andExpect(status().isOk())
                .andExpect(jsonPath("$.targetValue").value(100))
                .andExpect(jsonPath("$.status").value("DRAFT"));
        get(foreign, "/targets/" + target).andExpect(status().isNotFound());

        writeTarget(author, revisionId, target, draft).andExpect(status().isConflict());
        draft.put("expectedRevisionUpdatedAt", created.path("revisionUpdatedAt").asText());
        draft.put("expectedTargetUpdatedAt", created.path("target").path("updatedAt").asText());
        draft.put("targetValue", 105);
        JsonNode updated = body(writeTarget(author, revisionId, target, draft));
        assertEquals(target, updated.path("target").path("id").asText());
        assertEquals(105, updated.path("target").path("targetValue").asInt());
        http.perform(patch(V1 + "/revisions/" + revisionId).header("Authorization", author.bearer())
                .contentType(MediaType.APPLICATION_JSON).content(JSON.writeValueAsString(Map.of(
                        "expectedUpdatedAt", created.path("revisionUpdatedAt").asText(), "changeReasonText", "stale"))))
                .andExpect(status().isConflict());
        http.perform(patch(V1 + "/revisions/" + revisionId).header("Authorization", author.bearer())
                .contentType(MediaType.APPLICATION_JSON).content(JSON.writeValueAsString(Map.of(
                        "expectedUpdatedAt", updated.path("revisionUpdatedAt").asText(), "changeReasonText", "Synthetic update"))))
                .andExpect(status().isOk()).andExpect(jsonPath("$.changeReasonText").value("Synthetic update"));
        db.update("""
                UPDATE hidra_identity_user_permission_grant SET status='REVOKED',revoked_at=now()
                WHERE user_id=? AND permission_id=? AND scope_reference_id=?
                """, author.id(), permission("planning:scoped-plan:author"), facility);
        draft.put("expectedRevisionUpdatedAt", body(get(author, "/revisions/" + revisionId)).path("updatedAt").asText());
        writeTarget(author, revisionId, null, draft).andExpect(status().isNotFound());
        assertEquals(1L, targetCount(revisionId));
    }

    @Test
    void rejectsInvalidTargetEvidenceWithoutAdvancingDraftVersion() throws Exception {
        var author = actor(company, facility, true);
        String period = period(author, id(), company);
        String plan = plan(author, period, facility);
        JsonNode revision = body(createRevision(author, plan, planToken(author, plan)));
        String revisionId = revision.path("id").asText();
        String token = revision.path("updatedAt").asText();
        TelemetryFixture point = telemetryPoint(facility);
        TelemetryFixture wrongPoint = telemetryPoint(otherFacility);
        String targetType = targetType("NUMERIC");
        var draft = targetBody(token, targetType, point);
        draft.put("targetValue", new java.math.BigDecimal("100.0000001"));
        writeTarget(author, revisionId, null, draft).andExpect(status().isBadRequest());
        draft.put("targetValue", 100);
        draft.put("unitId", wrongPoint.unit());
        writeTarget(author, revisionId, null, draft).andExpect(status().isBadRequest());
        draft.put("unitId", point.unit());
        draft.put("telemetryPointId", wrongPoint.point());
        writeTarget(author, revisionId, null, draft).andExpect(status().isBadRequest());
        draft.put("telemetryPointId", point.point());
        draft.put("topologyAssetId", otherFacility);
        writeTarget(author, revisionId, null, draft).andExpect(status().isBadRequest());
        draft.put("topologyAssetId", facility);
        draft.put("validTo", "2026-11-12T00:00:00Z");
        writeTarget(author, revisionId, null, draft).andExpect(status().isBadRequest());
        draft.put("validTo", "2026-11-11T00:00:00Z");
        db.update("UPDATE hidra_planning_target_value_policy SET active=false WHERE target_type_id=?", targetType);
        writeTarget(author, revisionId, null, draft).andExpect(status().isBadRequest());
        assertEquals(0L, targetCount(revisionId));
        assertEquals(token, body(get(author, "/revisions/" + revisionId)).path("updatedAt").asText());
        db.update("UPDATE hidra_planning_target_value_policy SET active=true WHERE target_type_id=?", targetType);
        JsonNode numeric = body(writeTarget(author, revisionId, null, draft));
        var text = new java.util.LinkedHashMap<String, Object>(draft);
        text.put("expectedRevisionUpdatedAt", numeric.path("revisionUpdatedAt").asText());
        text.put("targetTypeId", targetType("TEXT"));
        text.remove("targetValue");
        text.remove("unitId");
        text.remove("telemetryPointId");
        text.put("targetTextValue", "Synthetic operating instruction");
        body(writeTarget(author, revisionId, null, text));
        assertEquals(2L, targetCount(revisionId));
    }

    @Test
    void serializesDraftCreationAndRejectsSubmittedEdits() throws Exception {
        var author = actor(company, facility, true);
        String period = period(author, id(), company);
        String plan = plan(author, period, facility);
        String token = planToken(author, plan);
        Callable<Integer> create = () -> createRevision(author, plan, token).andReturn().getResponse().getStatus();
        try (var pool = Executors.newFixedThreadPool(2)) {
            var outcomes = pool.invokeAll(List.of(create, create), 30, TimeUnit.SECONDS);
            var statuses = new java.util.ArrayList<Integer>();
            for (var outcome : outcomes) {
                statuses.add(outcome.get());
            }
            statuses.sort(Integer::compareTo);
            assertEquals(List.of(200, 409), statuses);
        }
        JsonNode revision = body(get(author, "/revisions?planId=" + plan)).path("content").get(0);
        String revisionId = revision.path("id").asText();
        db.update("UPDATE hidra_planning_plan_revision SET status='SUBMITTED' WHERE id=?", revisionId);
        var target = targetBody(revision.path("updatedAt").asText(), targetType("NUMERIC"), telemetryPoint(facility));
        writeTarget(author, revisionId, null, target).andExpect(status().isConflict());
        http.perform(patch(V1 + "/revisions/" + revisionId).header("Authorization", author.bearer())
                .contentType(MediaType.APPLICATION_JSON)
                .content(JSON.writeValueAsString(Map.of("expectedUpdatedAt", revision.path("updatedAt").asText()))))
                .andExpect(status().isConflict());
        createRevision(author, plan, planToken(author, plan)).andExpect(status().isConflict());
        assertEquals(0L, targetCount(revisionId));
        db.update("UPDATE hidra_planning_plan_revision SET status='WITHDRAWN' WHERE id=?", revisionId);
        createRevision(author, plan, planToken(author, plan)).andExpect(status().isOk())
                .andExpect(jsonPath("$.revisionNumber").value(2))
                .andExpect(jsonPath("$.baseRevisionId").value(revisionId));
    }

    @Test
    void submitsAndIndependentlyApprovesScopedTargetsWithoutGenericWorkflowBypass() throws Exception {
        var author = actor(company, facility, true);
        var reviewer = actor(company, facility, true);
        String reviewPermission = permission("planning:scoped-plan:review");
        grant(reviewer.id(), reviewPermission, "CUSTOM", "planning-company:" + company, "GRANT");
        grant(reviewer.id(), reviewPermission, "FACILITY", facility, "GRANT");
        // The independent reviewer has review authority, not draft-author authority.
        db.update("""
                UPDATE hidra_identity_user_permission_grant SET status='REVOKED',revoked_at=now()
                WHERE user_id=? AND permission_id=?
                """, reviewer.id(), permission("planning:scoped-plan:author"));

        String type = workflowCatalog("WORKFLOW_TARGET_TYPE", "PLAN_REVISION");
        String purpose = workflowCatalog("WORKFLOW_PURPOSE", "PLAN_REVISION_APPROVAL");
        String mode = workflowCatalog("WORKFLOW_ASSIGNMENT_MODE", "DIRECT");
        String definition = id(), reviewStep = id(), approvedStep = id(), rejectedStep = id();
        String approveTransition = id(), rejectTransition = id();
        db.update("""
                INSERT INTO hidra_workflow_definition
                    (id,code,name_fr,type_id,status,version,created_at,updated_at)
                VALUES (?,?,'Synthetic revision approval',?,'ACTIVE',1,now(),now())
                """, definition, definition, type);
        for (String[] step : List.of(new String[] {reviewStep, "0"},
                new String[] {approvedStep, "1"}, new String[] {rejectedStep, "2"})) {
            db.update("""
                    INSERT INTO hidra_workflow_step
                        (id,definition_id,code,name_fr,step_order,mandatory,allow_claim,
                            allow_delegation,allow_escalation,created_at,updated_at)
                    VALUES (?,?,?,'Synthetic review',?,true,false,false,false,now(),now())
                    """, step[0], definition, step[0], Integer.parseInt(step[1]));
        }
        db.update("""
                INSERT INTO hidra_workflow_step_assignment_rule
                    (id,definition_id,step_id,assignment_mode_id,actor_id,active,created_at,updated_at)
                VALUES (?,?,?,?,?,true,now(),now())
                """, id(), definition, reviewStep, mode, reviewer.id());
        db.update("""
                INSERT INTO hidra_workflow_definition_target_binding
                    (id,definition_id,target_module,target_type_id,workflow_purpose_id,active,created_at,updated_at)
                VALUES (?,?,'planning',?,?,true,now(),now())
                """, id(), definition, type, purpose);
        for (String[] transition : List.of(new String[]{approveTransition, approvedStep, "APPROVE"},
                new String[]{rejectTransition, rejectedStep, "REJECT"})) {
            db.update("""
                    INSERT INTO hidra_workflow_transition
                        (id,definition_id,from_step_id,to_step_id,decision,reason_required,
                            comment_required,created_at,updated_at)
                    VALUES (?,?,?,?,?,false,false,now(),now())
                    """, transition[0], definition, reviewStep, transition[1], transition[2]);
        }

        for (var route : catalog.routes()) {
            if (route.route().contains("/planning/revisions/") && route.methods().contains("POST")
                    && route.route().endsWith("/submit")) {
                grant(author.id(), permission(route.permission()), "GLOBAL", null, "GRANT");
            }
            if (route.route().contains("/planning/revisions/") && route.methods().contains("POST")
                    && route.route().contains("/approval/actions/")) {
                grant(reviewer.id(), permission(route.permission()), "GLOBAL", null, "GRANT");
            }
        }

        String period = period(author, id(), company);
        String plan = plan(author, period, facility);
        JsonNode revision = body(createRevision(author, plan, planToken(author, plan)));
        String revisionId = revision.path("id").asText();
        JsonNode target = body(writeTarget(author, revisionId, null, targetBody(
                revision.path("updatedAt").asText(), targetType("NUMERIC"), telemetryPoint(facility))));
        String targetId = target.path("target").path("id").asText();
        String submitPayload = JSON.writeValueAsString(Map.of(
                "expectedPlanUpdatedAt", planToken(author, plan),
                "expectedRevisionUpdatedAt", target.path("revisionUpdatedAt").asText(),
                "definitionId", definition, "definitionVersion", 1,
                "targetTypeId", type, "workflowPurposeId", purpose, "reviewerActorId", reviewer.id()));
        var submitRoute = V1 + "/revisions/" + revisionId + "/submit";
        JsonNode submitted = body(http.perform(post(submitRoute).header("Authorization", author.bearer())
                .contentType(MediaType.APPLICATION_JSON).content(submitPayload)));
        assertEquals("SUBMITTED", submitted.path("revisionStatus").asText());
        String instanceId = submitted.path("workflowInstanceId").asText();
        String taskId = submitted.path("workflowTaskId").asText();
        assertFalse(instanceId.isBlank());
        assertFalse(taskId.isBlank());
        http.perform(post(submitRoute).header("Authorization", author.bearer())
                .contentType(MediaType.APPLICATION_JSON).content(submitPayload))
                .andExpect(status().isConflict());
        writeTarget(author, revisionId, null, targetBody(submitted.path("revisionUpdatedAt").asText(),
                targetType("NUMERIC"), telemetryPoint(facility))).andExpect(status().isConflict());
        get(author, "/targets/" + targetId).andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("DRAFT"));

        String taskVersion = submitted.path("workflowTaskUpdatedAt").asText();
        String executionPayload = JSON.writeValueAsString(Map.of("expectedTaskUpdatedAt", taskVersion));
        http.perform(post("/api/v1/workflow/tasks/" + taskId + "/transitions/"
                        + approveTransition + "/execute")
                .header("Authorization", reviewer.bearer()).contentType(MediaType.APPLICATION_JSON)
                .content(executionPayload)).andExpect(status().is4xxClientError());
        get(author, "/targets/" + targetId).andExpect(jsonPath("$.status").value("DRAFT"));

        var decision = body(http.perform(post(V1 + "/revisions/" + revisionId
                        + "/approval/actions/" + approveTransition + "/execute")
                .header("Authorization", reviewer.bearer()).contentType(MediaType.APPLICATION_JSON)
                .content(executionPayload)));
        assertEquals("APPROVED", decision.path("revisionStatus").asText());
        assertEquals("COMPLETED", decision.path("workflowInstanceStatus").asText());
        get(author, "/targets/" + targetId).andExpect(jsonPath("$.status").value("ACTIVE"));
        get(author, "/operational-plans/" + plan)
                .andExpect(jsonPath("$.approvedRevisionId").value(revisionId));
        get(author, "/revisions/" + revisionId)
                .andExpect(jsonPath("$.status").value("APPROVED"));
        assertEquals(1L, db.queryForObject("""
                SELECT count(*) FROM hidra_workflow_action
                WHERE instance_id=? AND actor_id=?
                """, Long.class, instanceId, reviewer.id()));
        http.perform(post(V1 + "/revisions/" + revisionId
                        + "/approval/actions/" + approveTransition + "/execute")
                .header("Authorization", reviewer.bearer()).contentType(MediaType.APPLICATION_JSON)
                .content(executionPayload)).andExpect(status().isConflict());
    }

    private String workflowCatalog(String family, String code) {
        String key = id();
        db.update("""
                INSERT INTO hidra_workflow_type_catalog
                    (id,catalog_name,code,active,sort_order,system_defined,created_at,updated_at)
                VALUES (?,?,?,true,0,false,now(),now())
                """, key, family, code);
        return key;
    }

    private ResultActions createRevision(Actor author, String plan, String token) throws Exception {
        return http.perform(post(V1 + "/operational-plans/" + plan + "/revisions")
                .header("Authorization", author.bearer()).contentType(MediaType.APPLICATION_JSON)
                .content(JSON.writeValueAsString(Map.of("expectedPlanUpdatedAt", token))));
    }

    private String planToken(Actor author, String plan) throws Exception {
        return body(get(author, "/operational-plans/" + plan)).path("updatedAt").asText();
    }

    private java.util.LinkedHashMap<String, Object> targetBody(String token, String type, TelemetryFixture point) {
        var body = new java.util.LinkedHashMap<String, Object>();
        body.put("expectedRevisionUpdatedAt", token);
        body.put("targetTypeId", type);
        body.put("topologyAssetType", "FACILITY");
        body.put("topologyAssetId", facility);
        body.put("telemetryPointId", point.point());
        body.put("unitId", point.unit());
        body.put("targetValue", 100);
        body.put("validFrom", "2026-11-10T00:00:00Z");
        body.put("validTo", "2026-11-11T00:00:00Z");
        return body;
    }

    private ResultActions writeTarget(Actor author, String revision, String target, Map<String, Object> body)
            throws Exception {
        var request = target == null ? post(V1 + "/revisions/" + revision + "/targets")
                : put(V1 + "/targets/" + target);
        return http.perform(request.header("Authorization", author.bearer()).contentType(MediaType.APPLICATION_JSON)
                .content(JSON.writeValueAsString(body)));
    }

    private long targetCount(String revision) {
        return db.queryForObject("SELECT count(*) FROM hidra_planning_plan_target WHERE revision_id=?", Long.class, revision);
    }

    private String targetType(String representation) {
        String type = id();
        addCatalog(db, type, "TARGET_TYPE");
        db.update("""
                INSERT INTO hidra_planning_target_value_policy(target_type_id,representation_kind,active)
                VALUES (?,?,true)
                """, type, representation);
        return type;
    }

    private record TelemetryFixture(String point, String unit) { }

    private TelemetryFixture telemetryPoint(String asset) {
        String sourceType = telemetryCatalog("SOURCE_TYPE", null);
        String protocol = telemetryCatalog("PROTOCOL", null);
        String deviceType = telemetryCatalog("DEVICE_TYPE", null);
        String pointType = telemetryCatalog("POINT_TYPE", null);
        String signal = telemetryCatalog("SIGNAL_TYPE", "NUMERIC");
        String binding = telemetryCatalog("BINDING_ROLE", null);
        String unit = id(), source = id(), device = id(), point = id();
        db.update("""
                INSERT INTO hidra_telemetry_unit(id,code,symbol,dimension,active,system_defined,created_at,updated_at)
                VALUES (?,?,'bar','PRESSURE',true,false,now(),now())
                """, unit, unit);
        db.update("""
                INSERT INTO hidra_telemetry_source(id,code,name_fr,source_type_id,protocol_id,status,created_at,updated_at)
                VALUES (?,?,'Synthetic source',?,?,'ACTIVE',now(),now())
                """, source, source, sourceType, protocol);
        db.update("""
                INSERT INTO hidra_telemetry_device(id,source_id,code,name_fr,device_type_id,status,created_at,updated_at)
                VALUES (?,?,?,'Synthetic device',?,'ACTIVE',now(),now())
                """, device, source, device, deviceType);
        db.update("""
                INSERT INTO hidra_telemetry_point(id,device_id,code,name_fr,point_type_id,signal_type_id,unit_id,status,created_at,updated_at)
                VALUES (?,?,?,'Synthetic pressure',?,?,?,'ACTIVE',now(),now())
                """, point, device, point, pointType, signal, unit);
        db.update("""
                INSERT INTO hidra_telemetry_point_binding(id,point_id,topology_asset_type_code,topology_asset_id,
                    topology_asset_code,binding_role_id,active,valid_from,created_at,updated_at)
                VALUES (?,?,'FACILITY',?,?,?,true,'2026-11-09T00:00:00Z',now(),now())
                """, id(), point, asset, asset, binding);
        return new TelemetryFixture(point, unit);
    }

    private String telemetryCatalog(String family, String shape) {
        String key = id();
        db.update("""
                INSERT INTO hidra_telemetry_type_catalog(id,catalog_name,code,active,value_shape,numeric_unit_exempt,
                    sort_order,system_defined,created_at,updated_at)
                VALUES (?,?,?,true,?,false,0,false,now(),now())
                """, key, family, key, shape);
        return key;
    }

    private static JsonNode body(ResultActions result) throws Exception {
        return JSON.readTree(result.andExpect(status().isOk()).andReturn().getResponse().getContentAsString());
    }

    private record Actor(String id, String bearer) { }

    private Actor actor(String owner, String asset, boolean createRoute) {
        String user = id();
        db.update("""
                INSERT INTO hidra_identity_user(id,username,display_name,user_type,status,failed_login_count,
                    created_at,updated_at,activated_at)
                VALUES (?,?,'Synthetic Planning operator','HUMAN','ACTIVE',0,now(),now(),now())
                """, user, user);
        db.update("""
                INSERT INTO hidra_identity_local_credential(id,user_id,password_hash,credential_status,created_at,updated_at)
                VALUES (?,?,?,'ACTIVE',now(),now())
                """, id(), user, passwords.encode(PASSWORD));
        for (String route : List.of("periods:read", "create-planning-period:execute", "operational-plans:execute",
                "operational-plans:read", "revisions:read", "targets:read", "nominations:read",
                "revisions:execute", "revisions:update", "targets:update")) {
            grant(user, permission("planning:" + route), "GLOBAL", null, "GRANT");
        }
        if (createRoute) {
            grant(user, permission("planning:periods:execute"), "GLOBAL", null, "GRANT");
        }
        for (String action : List.of("read", "create")) {
            grant(user, permission("planning:scoped-period:" + action), "CUSTOM", "planning-company:" + owner, "GRANT");
            String planPermission = permission("planning:scoped-plan:" + action);
            grant(user, planPermission, "CUSTOM", "planning-company:" + owner, "GRANT");
            grant(user, planPermission, "FACILITY", asset, "GRANT");
        }
        String authorPermission = permission("planning:scoped-plan:author");
        grant(user, authorPermission, "CUSTOM", "planning-company:" + owner, "GRANT");
        grant(user, authorPermission, "FACILITY", asset, "GRANT");
        String token = authentication.authenticate(new DirectAuthenticationCommand(
                ProviderType.LOCAL, user, PASSWORD, "synthetic-ip", "synthetic-t01", "synthetic-login")).accessToken();
        SecurityContextHolder.clearContext();
        return new Actor(user, "Bearer " + token);
    }

    private String permission(String code) {
        var matches = db.queryForList("SELECT id FROM hidra_identity_permission WHERE code=?", String.class, code);
        if (!matches.isEmpty()) {
            return matches.getFirst();
        }
        String permission = id();
        db.update("""
                INSERT INTO hidra_identity_permission (id,code,permission_domain,resource_type,action,sensitive,status,created_at,updated_at)
                VALUES (?,?,'planning','PLANNING',?,false,'ACTIVE',now(),now())
                """, permission, code, code.substring(code.lastIndexOf(':') + 1));
        return permission;
    }

    private void grant(String actor, String permission, String type, String scope, String effect) {
        db.update("""
                INSERT INTO hidra_identity_user_permission_grant
                    (id,user_id,permission_id,effect,scope_type,scope_reference_id,grant_reason,emergency_access,
                        valid_from,valid_to,status,created_at)
                VALUES (?,?,?,?,?,?,'synthetic T01',false,now()-interval '1 hour',now()+interval '2 hours','ACTIVE',now())
                """, id(), actor, permission, effect, type, scope);
    }

    private String company(String partyType) {
        String party = id();
        db.update("""
                INSERT INTO hidra_party_party (id,code,party_type_id,legal_name,country_code,status,created_at,updated_at)
                VALUES (?,?,?,'Synthetic company','DZ','ACTIVE',now(),now())
                """, party, party, partyType);
        return party;
    }

    private String facility(String facilityType, String owner) {
        String facilityId = id();
        db.update("""
                INSERT INTO hidra_topology_facility (id,code,name_fr,facility_type_id,facility_kind,owner_party_id,status,created_at,updated_at)
                VALUES (?,?,'Synthetic GZ2 station',?,'COMPRESSOR_STATION',?,'ACTIVE',now(),now())
                """, facilityId, facilityId, facilityType, owner);
        return facilityId;
    }

    private static void addCatalog(JdbcTemplate jdbc, String key, String family) {
        jdbc.update("""
                INSERT INTO hidra_planning_catalog_entry (id,catalog_name,code,active,sort_order,system_defined,created_at,updated_at)
                VALUES (?,?,?,true,0,false,now(),now())
                """, key, family, key);
    }

    private long periodCount(String code) {
        return db.queryForObject("SELECT count(*) FROM hidra_planning_period WHERE code=?", Long.class, code);
    }

    private String revision(String plan) {
        String revisionId = id();
        db.update("""
                INSERT INTO hidra_planning_plan_revision (id,plan_id,revision_number,revision_code,status,created_at,updated_at)
                VALUES (?,?,1,'R1','DRAFT',now(),now())
                """, revisionId, plan);
        return revisionId;
    }

    private ResultActions createPeriod(Actor actor, String code, String owner) throws Exception {
        return http.perform(post(V2).header("Authorization", actor.bearer())
                .contentType(MediaType.APPLICATION_JSON).content(periodPayload(code, owner)));
    }

    private String period(Actor actor, String code, String owner) throws Exception {
        return readId(createPeriod(actor, code, owner));
    }

    private static String periodPayload(String code, String owner) throws Exception {
        var body = JSON.valueToTree(Map.of("code", code, "nameFr", "Synthetic period", "periodTypeId", PERIOD_TYPE,
                "periodStart", "2026-11-10T00:00:00Z", "periodEnd", "2026-11-11T00:00:00Z"));
        if (owner != null) {
            ((com.fasterxml.jackson.databind.node.ObjectNode) body).put("companyPartyId", owner);
        }
        return JSON.writeValueAsString(body);
    }

    private ResultActions createPlan(Actor actor, String period, String asset) throws Exception {
        String body = JSON.writeValueAsString(Map.of("periodId", period, "code", id(), "nameFr", "Synthetic plan",
                "planTypeId", planType, "topologyScopeType", "FACILITY", "topologyScopeId", asset));
        return http.perform(post(V1 + "/operational-plans").header("Authorization", actor.bearer())
                .contentType(MediaType.APPLICATION_JSON).content(body));
    }

    private String plan(Actor actor, String period, String asset) throws Exception {
        return readId(createPlan(actor, period, asset));
    }

    private static String readId(ResultActions result) throws Exception {
        return JSON.readTree(result.andExpect(status().isOk()).andReturn().getResponse().getContentAsString())
                .path("id").asText();
    }

    private ResultActions get(Actor actor, String route) throws Exception {
        return http.perform(org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get(V1 + route)
                .header("Authorization", actor.bearer()));
    }

    private static String id() {
        return UUID.randomUUID().toString();
    }
}
