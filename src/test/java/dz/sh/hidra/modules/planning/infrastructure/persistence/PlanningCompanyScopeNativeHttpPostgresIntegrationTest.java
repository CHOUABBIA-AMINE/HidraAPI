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
            }
        }
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
                "operational-plans:read", "revisions:read", "targets:read", "nominations:read")) {
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
