/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : MonitoringRuleNativeHttpPostgresIntegrationTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-11-13
 *
 * @Type        : Class
 * @Layer       : Test
 * @Module      : monitoring
 * @Package     : dz.sh.hidra.modules.monitoring.infrastructure.persistence
 *
 * @Description : Verifies the signed T10 rule lifecycle and physical activation invariants against migrated PostgreSQL.
 *
 */
package dz.sh.hidra.modules.monitoring.infrastructure.persistence;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import dz.sh.hidra.HidraApplication;
import dz.sh.hidra.modules.identity.application.model.DirectAuthenticationCommand;
import dz.sh.hidra.modules.identity.application.port.in.AuthenticateDirectUserUseCase;
import dz.sh.hidra.modules.identity.domain.value.ProviderType;
import dz.sh.hidra.modules.monitoring.domain.model.MonitoringRuleRevision;
import dz.sh.hidra.modules.monitoring.domain.value.MonitoringRuleType;
import dz.sh.hidra.modules.monitoring.infrastructure.persistence.adapter.JdbcMonitoringRuleRevisionAdapter;
import dz.sh.hidra.platform.permissions.HidraTrustedNativeProtectedRouteRegistry;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import org.flywaydb.core.Flyway;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.junit.jupiter.api.Assertions.*;

@Testcontainers(disabledWithoutDocker = true)
@SpringBootTest(classes = HidraApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        properties = {
            "hidra.platform.security.jwt.hmac-secret=synthetic-t10-local-native-key-for-tests",
            "hidra.platform.security.jwt.token-issuer=https://identity.hidra.invalid/monitoring",
            "hidra.platform.security.jwt.audience=hidra-api"
        })
@ActiveProfiles("test")
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
class MonitoringRuleNativeHttpPostgresIntegrationTest {
    private static final String RULES = "/api/v1/monitoring/rules";
    private static final String POLICY = "TRUSTED_PLAN_COMPARISON";
    private static final String PASSWORD = "synthetic-t10-local-secret";
    private static final ObjectMapper JSON = new ObjectMapper();

    @Container
    static final PostgreSQLContainer<?> PG =
            new PostgreSQLContainer<>("postgres:16-alpine").withDatabaseName("hidra_t10_rules");

    @DynamicPropertySource
    static void datasource(DynamicPropertyRegistry registry) {
        if (!PG.isRunning()) PG.start();
        Flyway.configure().dataSource(PG.getJdbcUrl(), PG.getUsername(), PG.getPassword()).load().migrate();
        registry.add("spring.datasource.url", PG::getJdbcUrl);
        registry.add("spring.datasource.username", PG::getUsername);
        registry.add("spring.datasource.password", PG::getPassword);
        registry.add("spring.flyway.url", PG::getJdbcUrl);
        registry.add("spring.flyway.user", PG::getUsername);
        registry.add("spring.flyway.password", PG::getPassword);
    }

    @Autowired private JdbcTemplate database;
    @Autowired private AuthenticateDirectUserUseCase authentication;
    @Autowired private PasswordEncoder passwords;
    @Value("${local.server.port}") private int port;

    @Test void compiledNativeRuleRoutesAreIndependentlyRegistered() {
        assertNotNull(new HidraTrustedNativeProtectedRouteRegistry());
    }
    @Test void freshPostgresActivationRetainsImmutableHistoryAndRejectsStaleHead() {
        var datasource = new DriverManagerDataSource(PG.getJdbcUrl(), PG.getUsername(), PG.getPassword());
        var jdbc=new JdbcTemplate(datasource);
        var tx=new TransactionTemplate(new DataSourceTransactionManager(datasource));
        var repository=new JdbcMonitoringRuleRevisionAdapter(jdbc);
        Instant now=Instant.now();
        tx.execute(status->{
            jdbc.update("""
                insert into public.hidra_monitoring_rule
                (id,code,name_fr,rule_type,status,created_at,updated_at)
                values (?,?,?,?,?,now(),now())
                ""","t10-rule","T10-RULE","T10 Rule","PLAN_COMPARISON","DRAFT");
            ensureEvidencePolicy(jdbc);
            repository.register("t10-rule","company","FACILITY","asset","actor");
            assertTrue(repository.policyAvailable("TRUSTED_PLAN_COMPARISON"));
            assertFalse(repository.policyAvailable("UNSUPPORTED"));
            var first=new MonitoringRuleRevision("t10-rule",1,"company","FACILITY","asset","point",
                    MonitoringRuleType.PLAN_COMPARISON,"unit","TRUSTED_PLAN_COMPARISON",
                    now.minusSeconds(60),now.plusSeconds(7200),"actor",now,null,null,null,null);
            assertEquals(1,repository.create(first).revision());
            assertEquals(1,repository.activate("t10-rule",1,0,now.plusSeconds(1),
                    now.plusSeconds(3600),"actor").revision());
            var second=new MonitoringRuleRevision("t10-rule",1,"company","FACILITY","asset","point",
                    MonitoringRuleType.PLAN_COMPARISON,"unit","TRUSTED_PLAN_COMPARISON",
                    now.plusSeconds(3500),now.plusSeconds(10800),"actor",now,null,null,null,null);
            assertEquals(2,repository.create(second).revision());
            // Exactly repeated activation is one persisted event, with its original actor and timestamps.
            var replay=repository.activate("t10-rule",1,0,now.plusSeconds(1),
                    now.plusSeconds(3600),"actor");
            assertEquals(1,replay.revision());
            assertNotNull(replay.activatedAt());
            assertEquals(now.plusSeconds(1).truncatedTo(java.time.temporal.ChronoUnit.MICROS),
                    replay.effectiveFrom());
            assertThrows(IllegalStateException.class,()->repository.activate(
                    "t10-rule",2,0,now.plusSeconds(3600),now.plusSeconds(7200),"actor"));
            repository.activate("t10-rule",2,1,now.plusSeconds(3600),
                    now.plusSeconds(7200),"actor");
            assertEquals(1,repository.effective("t10-rule",now.plusSeconds(100)).orElseThrow().revision());
            assertEquals(2,repository.effective("t10-rule",now.plusSeconds(5000)).orElseThrow().revision());
            assertEquals(2,repository.history("t10-rule",10).size());
            assertEquals("company",repository.scope("t10-rule").orElseThrow().company());
            return null;
        });
        assertThrows(DataAccessException.class,()->tx.execute(status->{
            jdbc.update("""
                update public.hidra_monitoring_rule_revision set unit_id='forged'
                where rule_id='t10-rule' and revision=1
                """);
            return null;
        }));
        assertEquals(2L,jdbc.queryForObject("""
            select count(*) from public.hidra_monitoring_rule_activation where rule_id='t10-rule'
            """,Long.class));
    }

    @Test
    void signedRuleLifecyclePreservesEffectiveHistoryAndEnforcesCurrentScope() throws Exception {
        RuleFixture fixture = ruleFixture();
        String owner = user();
        String foreign = user();
        for (String operation : List.of("create", "revise", "activate", "read")) {
            grant(owner, operation, "CUSTOM", "monitoring-company:" + fixture.company());
            grant(owner, operation, "FACILITY", fixture.asset());
        }
        grant(foreign, "read", "CUSTOM", "monitoring-company:" + fixture.otherCompany());
        grant(foreign, "read", "FACILITY", fixture.asset());

        String ownerToken = signIn(owner);
        String foreignToken = signIn(foreign);
        Instant start = Instant.now().truncatedTo(ChronoUnit.SECONDS).minusSeconds(60);
        Instant boundary = start.plusSeconds(3600);
        Instant end = boundary.plusSeconds(3600);
        String code = id();
        var request = Map.of("code", code, "nameFr", "Règle GZ2 synthétique",
                "ruleType", "PLAN_COMPARISON", "topologyAssetType", "FACILITY",
                "topologyAssetId", fixture.asset(), "telemetryPointId", fixture.point(),
                "createdByActorId", foreign);

        try (HttpClient client = HttpClient.newBuilder().connectTimeout(Duration.ofSeconds(10)).build()) {
            JsonNode created = requireStatus(send(client, "POST", RULES, ownerToken, request), 200);
            String ruleId = created.path("id").asText();
            assertFalse(ruleId.isBlank());
            String rule = RULES + "/" + ruleId;
            String revisions = rule + "/revisions";
            String scopedList = RULES + "?topologyAssetType=FACILITY&topologyAssetId=" + fixture.asset();
            assertEquals(owner, database.queryForObject("""
                    select created_by_actor_id from hidra_monitoring_rule_scope where rule_id=?
                    """, String.class, ruleId));
            assertEquals(1, requireStatus(send(client, "GET", scopedList, ownerToken, null), 200)
                    .path("totalElements").asInt());
            requireStatus(send(client, "GET", rule, null, null), 401);
            requireStatus(send(client, "GET", rule, foreignToken, null), 403);
            requireStatus(send(client, "GET", scopedList, foreignToken, null), 403);
            requireStatus(send(client, "GET", rule + "/history", foreignToken, null), 403);
            requireStatus(send(client, "GET", RULES + "/" + id() + "/history", foreignToken, null), 403);

            requireStatus(send(client, "POST", revisions, ownerToken,
                    draft(id(), POLICY, start, end)), 403);
            requireStatus(send(client, "POST", revisions, ownerToken,
                    draft(fixture.unit(), "UNSUPPORTED", start, end)), 400);
            requireStatus(send(client, "POST", revisions, ownerToken,
                    draft(fixture.unit(), POLICY, end, start)), 400);

            var validDraft = draft(fixture.unit(), POLICY, start, end);
            JsonNode first = requireStatus(send(client, "POST", revisions, ownerToken, validDraft), 201);
            assertRevision(first, 1, fixture, owner);
            var firstActivation = activation(0, start, boundary);
            String activateFirst = revisions + "/1/activate";
            JsonNode activated = requireStatus(send(client, "POST", activateFirst, ownerToken, firstActivation), 200);
            assertEquals(owner, activated.path("activatedByActorId").asText());
            assertFalse(activated.path("activatedAt").asText().isBlank());
            assertEquals(activated, requireStatus(send(client, "POST", activateFirst, ownerToken, firstActivation), 200));
            assertEquals(2, requireStatus(send(client, "POST", revisions, ownerToken, validDraft), 201)
                    .path("revision").asLong());
            assertEquals(3, requireStatus(send(client, "POST", revisions, ownerToken, validDraft), 201)
                    .path("revision").asLong());
            requireStatus(send(client, "POST", revisions + "/2/activate", ownerToken,
                    activation(0, boundary, end)), 409);

            requireStatus(send(client, "POST", revisions + "/2/activate", ownerToken,
                    activation(1, boundary.minusSeconds(1), end)), 409);

            var simultaneousActivation = activation(1, boundary, end);
            long winner;
            try (var workers = Executors.newFixedThreadPool(2)) {
                var ready = new CountDownLatch(2);
                var release = new CountDownLatch(1);
                var second = workers.submit(() -> {
                    ready.countDown();
                    assertTrue(release.await(10, TimeUnit.SECONDS));
                    return send(client, "POST", revisions + "/2/activate", ownerToken, simultaneousActivation);
                });
                var third = workers.submit(() -> {
                    ready.countDown();
                    assertTrue(release.await(10, TimeUnit.SECONDS));
                    return send(client, "POST", revisions + "/3/activate", ownerToken, simultaneousActivation);
                });
                assertTrue(ready.await(10, TimeUnit.SECONDS));
                release.countDown();
                var secondResponse = second.get(45, TimeUnit.SECONDS);
                var thirdResponse = third.get(45, TimeUnit.SECONDS);
                assertEquals(List.of(200, 409), List.of(secondResponse.statusCode(), thirdResponse.statusCode())
                        .stream().sorted().toList());
                winner = requireStatus(secondResponse.statusCode() == 200 ? secondResponse : thirdResponse, 200)
                        .path("revision").asLong();
                assertEquals(secondResponse.statusCode() == 200 ? 2 : 3, winner);
            }
            assertEquals(2L, database.queryForObject(
                    "select count(*) from hidra_monitoring_rule_activation where rule_id=?", Long.class, ruleId));
            assertRevision(requireStatus(send(client, "GET", revisions + "/1", ownerToken, null), 200),
                    1, fixture, owner);
            assertEquals(1, requireStatus(send(client, "GET",
                    rule + "/effective?at=" + boundary.minusSeconds(1), ownerToken, null), 200).path("revision").asLong());
            assertEquals(winner, requireStatus(send(client, "GET",
                    rule + "/effective?at=" + boundary, ownerToken, null), 200).path("revision").asLong());
            assertEquals(3, requireStatus(send(client, "GET", rule + "/history", ownerToken, null), 200).size());

            assertEquals(1, database.update("""
                    update hidra_identity_user_permission_grant set status='REVOKED', revoked_at=now()
                    where user_id=? and permission_id='t10-rule-read' and scope_type='FACILITY'
                    """, owner));
            for (String path : List.of(scopedList, rule, revisions + "/1",
                    rule + "/effective?at=" + start, rule + "/history")) {
                requireStatus(send(client, "GET", path, ownerToken, null), 403);
            }
        }
    }

    private RuleFixture ruleFixture() {
        String partyType = id();
        String company = id();
        String otherCompany = id();
        String facilityType = id();
        String asset = id();
        database.update("insert into hidra_party_type(id,code,status,created_at,updated_at) values (?,?,'ACTIVE',now(),now())",
                partyType, partyType);
        for (String party : List.of(company, otherCompany)) {
            database.update("""
                    insert into hidra_party_party(id,code,party_type_id,legal_name,country_code,status,created_at,updated_at)
                    values (?,?,?,'Synthetic T10 company','DZ','ACTIVE',now(),now())
                    """, party, party, partyType);
        }
        database.update("""
                insert into hidra_topology_facility_type(id,code,name,facility_kind,status,created_at,updated_at)
                values (?,?,'Synthetic gas station','COMPRESSOR_STATION','ACTIVE',now(),now())
                """, facilityType, facilityType);
        database.update("""
                insert into hidra_topology_facility(id,code,name_fr,facility_type_id,facility_kind,owner_party_id,status,created_at,updated_at)
                values (?,?,'Synthetic GZ2',?,'COMPRESSOR_STATION',?,'ACTIVE',now(),now())
                """, asset, asset, facilityType, company);
        String source = id();
        String device = id();
        String point = id();
        String unit = id();
        database.update("""
                insert into hidra_telemetry_unit(id,code,symbol,dimension,active,system_defined,created_at,updated_at)
                values (?,?,'bar','PRESSURE',true,false,now(),now())
                """, unit, unit);
        database.update("""
                insert into hidra_telemetry_source(id,code,name_fr,source_type_id,protocol_id,status,created_at,updated_at)
                values (?,?,'Synthetic source',?,?,'ACTIVE',now(),now())
                """, source, source, catalog("SOURCE_TYPE", null), catalog("PROTOCOL", null));
        database.update("""
                insert into hidra_telemetry_device(id,source_id,code,name_fr,device_type_id,status,created_at,updated_at)
                values (?,?,?,'Synthetic device',?,'ACTIVE',now(),now())
                """, device, source, device, catalog("DEVICE_TYPE", null));
        database.update("""
                insert into hidra_telemetry_point(id,device_id,code,name_fr,point_type_id,signal_type_id,unit_id,status,created_at,updated_at)
                values (?,?,?,'Synthetic pressure',?,?,?,'ACTIVE',now(),now())
                """, point, device, point, catalog("POINT_TYPE", null), catalog("SIGNAL_TYPE", "NUMERIC"), unit);
        database.update("""
                insert into hidra_telemetry_point_binding(id,point_id,topology_asset_type_code,topology_asset_id,
                    topology_asset_code,binding_role_id,active,valid_from,created_at,updated_at)
                values (?,?,'FACILITY',?,?,?,true,now()-interval '1 day',now(),now())
                """, id(), point, asset, asset, catalog("BINDING_ROLE", null));
        database.update("update hidra_identity_provider set status='INACTIVE' where status='ACTIVE'");
        String provider = id();
        database.update("""
                insert into hidra_identity_provider(id,code,name,provider_type,status,sync_enabled,
                    just_in_time_provisioning_enabled,created_at,updated_at)
                values (?,?,'Synthetic T10 provider','LOCAL','ACTIVE',false,false,now(),now())
                """, provider, provider);
        ensureEvidencePolicy(database);
        return new RuleFixture(company, otherCompany, asset, point, unit);
    }

    private static void ensureEvidencePolicy(JdbcTemplate jdbc) {
        jdbc.update("""
                insert into hidra_monitoring_catalog_entry
                    (id,catalog_name,code,active,sort_order,system_defined,created_at,updated_at)
                values ('t10-fixture-policy','EVIDENCE_POLICY',?,true,1,false,now(),now())
                on conflict (id) do nothing
                """, POLICY);
    }

    private String catalog(String name, String shape) {
        String id = id();
        database.update("""
                insert into hidra_telemetry_type_catalog(id,catalog_name,code,active,value_shape,numeric_unit_exempt,
                    sort_order,system_defined,created_at,updated_at)
                values (?,?,?,true,?,false,0,false,now(),now())
                """, id, name, id, shape);
        return id;
    }

    private String user() {
        String user = id();
        database.update("""
                insert into hidra_identity_user(id,username,display_name,user_type,status,failed_login_count,
                    created_at,updated_at,activated_at)
                values (?,?,'Synthetic Monitoring operator','HUMAN','ACTIVE',0,now(),now(),now())
                """, user, user);
        database.update("""
                insert into hidra_identity_local_credential(id,user_id,password_hash,credential_status,created_at,updated_at)
                values (?,?,?,'ACTIVE',now(),now())
                """, id(), user, passwords.encode(PASSWORD));
        return user;
    }

    private void grant(String user, String operation, String scope, String reference) {
        database.update("""
                insert into hidra_identity_user_permission_grant(id,user_id,permission_id,effect,scope_type,
                    scope_reference_id,grant_reason,emergency_access,valid_from,valid_to,status,created_at)
                values (?,?,?,'GRANT',?,?,'synthetic T10',false,now()-interval '1 hour',
                    now()+interval '2 hours','ACTIVE',now())
                """, id(), user, "t10-rule-" + operation, scope, reference);
    }

    private String signIn(String user) {
        try {
            return authentication.authenticate(new DirectAuthenticationCommand(
                    ProviderType.LOCAL, user, PASSWORD, "synthetic-ip", "synthetic-t10", id())).accessToken();
        } finally {
            SecurityContextHolder.clearContext();
        }
    }

    private HttpResponse<String> send(HttpClient client, String method, String path, String token, Object body)
            throws Exception {
        var request = HttpRequest.newBuilder(URI.create("http://127.0.0.1:" + port + path))
                .timeout(Duration.ofSeconds(30));
        if (token != null) request.header("Authorization", "Bearer " + token);
        if (body != null) request.header("Content-Type", "application/json");
        return client.send(request.method(method, body == null ? HttpRequest.BodyPublishers.noBody()
                : HttpRequest.BodyPublishers.ofString(JSON.writeValueAsString(body))).build(),
                HttpResponse.BodyHandlers.ofString());
    }

    private static JsonNode requireStatus(HttpResponse<String> response, int expected) throws Exception {
        assertEquals(expected, response.statusCode(), () -> response.uri() + " " + response.body());
        return response.body().isBlank() ? JSON.getNodeFactory().nullNode() : JSON.readTree(response.body());
    }

    private static Map<String, String> draft(String unit, String policy, Instant from, Instant to) {
        return Map.of("unitId", unit, "evidencePolicyCode", policy, "validFrom", from.toString(),
                "validTo", to.toString());
    }

    private static Map<String, Object> activation(long head, Instant from, Instant until) {
        return Map.of("expectedHeadRevision", head, "effectiveFrom", from.toString(),
                "effectiveUntil", until.toString());
    }

    private static void assertRevision(JsonNode response, long revision, RuleFixture fixture, String actor) {
        assertEquals(revision, response.path("revision").asLong());
        assertEquals(fixture.company(), response.path("companyPartyId").asText());
        assertEquals(fixture.asset(), response.path("assetId").asText());
        assertEquals(fixture.point(), response.path("telemetryPointId").asText());
        assertEquals(fixture.unit(), response.path("unitId").asText());
        assertEquals(POLICY, response.path("evidencePolicyCode").asText());
        assertEquals(actor, response.path("createdByActorId").asText());
    }

    private static String id() {
        return UUID.randomUUID().toString();
    }

    private record RuleFixture(String company, String otherCompany, String asset, String point, String unit) { }
}
