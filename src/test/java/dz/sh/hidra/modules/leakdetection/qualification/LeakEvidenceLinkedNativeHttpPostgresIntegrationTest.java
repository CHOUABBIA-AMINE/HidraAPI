/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : LeakEvidenceLinkedNativeHttpPostgresIntegrationTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-11-13
 *
 * @Type        : Class
 * @Layer       : Test
 * @Module      : alarm
 * @Package     : dz.sh.hidra.modules.leakdetection.qualification
 *
 * @Description : Verifies one signed, durable Alarm creation and acknowledgement journey.
 *
 */
package dz.sh.hidra.modules.leakdetection.qualification;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import dz.sh.hidra.HidraApplication;
import dz.sh.hidra.modules.identity.application.model.DirectAuthenticationCommand;
import dz.sh.hidra.modules.identity.application.port.in.AuthenticateDirectUserUseCase;
import dz.sh.hidra.modules.identity.domain.value.ProviderType;
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
import org.flywaydb.core.Flyway;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
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
            "hidra.platform.security.jwt.hmac-secret=synthetic-t11-local-native-key-for-tests",
            "hidra.platform.security.jwt.token-issuer=https://identity.hidra.invalid/monitoring",
            "hidra.platform.security.jwt.audience=hidra-api"
        })
@ActiveProfiles("test")
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
class LeakEvidenceLinkedNativeHttpPostgresIntegrationTest {
    private static final String RULES = "/api/v1/monitoring/rules";
    private static final String POLICY = "TRUSTED_PLAN_COMPARISON";
    private static final String PASSWORD = "synthetic-t11-local-secret";
    private static final ObjectMapper JSON = new ObjectMapper();

    @Container
    static final PostgreSQLContainer<?> PG =
            new PostgreSQLContainer<>("postgres:16-alpine").withDatabaseName("hidra_t13_leak");

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



    @Test
    void signedEvaluationCreatesOneDurableSuspectedLeakCandidate() throws Exception {
        RuleFixture fixture=ruleFixture();
        String operator=user();
        String foreign=user();
        String leakRead=leakPermission("leakdetection:candidates:read","read");
        String leakExecute=leakPermission("leakdetection:candidates:execute","execute");
        for(String permission:List.of("t10-rule-create","t10-rule-revise","t10-rule-activate",
                "t10-rule-read","t11-rule-evaluate")) {
            grant(operator,permission,"CUSTOM","monitoring-company:"+fixture.company());
            grant(operator,permission,"FACILITY",fixture.asset());
        }
        grant(foreign,"t10-rule-read","CUSTOM","monitoring-company:"+fixture.otherCompany());
        grant(foreign,"t10-rule-read","FACILITY",fixture.asset());
        for(String permission:List.of(leakRead,leakExecute)) {
            grant(operator,permission,"GLOBAL",null);
            grant(operator,permission,"CUSTOM","leakdetection-company:"+fixture.company());
            grant(operator,permission,"FACILITY",fixture.asset());
        }
        String leakOnly=user();
        grant(leakOnly,leakRead,"GLOBAL",null);
        grant(leakOnly,leakRead,"CUSTOM","leakdetection-company:"+fixture.company());
        grant(leakOnly,leakRead,"FACILITY",fixture.asset());
        String leakOnlyToken=signIn(leakOnly);
        String quality=configureTelemetry(fixture,operator);
        String token=signIn(operator);
        String foreignToken=signIn(foreign);
        Instant start=Instant.now().truncatedTo(ChronoUnit.SECONDS).minusSeconds(3600);
        Instant end=start.plusSeconds(7200);
        PlanFixture plan=approvedPlan(fixture,operator,foreign,start,end);

        String methodId=id();
        String profileId=id();
        database.update("""
                insert into hidra_leak_detection_method
                    (id,code,method_family,status,system_defined,created_at,updated_at)
                values (?,?,'MANUAL_DECLARATION','ACTIVE',false,now(),now())
                """,methodId,"T13-"+id());
        database.update("""
                insert into hidra_leak_detection_profile
                    (id,code,name_fr,topology_asset_type,topology_asset_id,topology_asset_code,
                     method_id,status,created_at,updated_at)
                values (?,?,'Déclaration synthétique GZ2','FACILITY',?,?,?,'ACTIVE',now(),now())
                """,profileId,"T13-"+id(),fixture.asset(),fixture.asset(),methodId);
        try(HttpClient client=HttpClient.newBuilder().connectTimeout(Duration.ofSeconds(10)).build()) {
            String rule=createRule(client,token,fixture,start,end);
            String fresh=publishReading(client,token,fixture,quality,Instant.now().minusSeconds(10));
            String stale=publishReading(client,token,fixture,quality,Instant.now().minusSeconds(300));
            String evalPath=RULES+"/"+rule+"/evaluations";
            Instant at=Instant.now().truncatedTo(ChronoUnit.MICROS);
            assertOutcome(requireStatus(send(client,"POST",evalPath,token,
                    request(plan.target(),fresh,at)),200),"POLICY_UNAVAILABLE");
            database.update("""
                insert into hidra_monitoring_evaluation_freshness_policy(rule_id,rule_revision,maximum_age_seconds)
                values (?,1,120)
                """,rule);
            at=at.plus(1,ChronoUnit.MICROS);
            JsonNode evaluation=requireStatus(send(client,"POST",evalPath,token,
                    request(plan.target(),fresh,at)),200);
            assertOutcome(evaluation,"COMPUTED");
            assertEquals(5,evaluation.path("difference").asInt());
            String evaluationId=evaluation.path("id").asText();

            String base="/api/v1/leakdetection/candidates";
            String commandPath=base+"/from-evaluation";
            var command=Map.of("evaluationId",evaluationId,"profileId",profileId,
                    "declaredConfidence",0.60,"reason","Synthetic operator suspicion only");
            JsonNode first=requireStatus(send(client,"POST",commandPath,token,command),201);
            String candidateId=first.path("candidateId").asText();
            assertFalse(candidateId.isBlank());
            assertEquals(evaluationId,first.path("evaluationId").asText());
            assertEquals("NEW",first.path("status").asText());
            assertEquals(candidateId,requireStatus(send(client,"POST",commandPath,token,command),200)
                    .path("candidateId").asText());
            var request=HttpRequest.newBuilder(URI.create("http://127.0.0.1:"+port+commandPath))
                    .timeout(Duration.ofSeconds(30)).header("Authorization","Bearer "+token)
                    .header("Content-Type","application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(JSON.writeValueAsString(command))).build();
            var r1=client.sendAsync(request,HttpResponse.BodyHandlers.ofString());
            var r2=client.sendAsync(request,HttpResponse.BodyHandlers.ofString());
            assertEquals(candidateId,requireStatus(r1.get(35,java.util.concurrent.TimeUnit.SECONDS),200)
                    .path("candidateId").asText());
            assertEquals(candidateId,requireStatus(r2.get(35,java.util.concurrent.TimeUnit.SECONDS),200)
                    .path("candidateId").asText());
            assertEquals(candidateId,requireStatus(send(client,"GET",base+"/"+candidateId,token,null),200)
                    .path("id").asText());
            assertEquals(1,requireStatus(send(client,"GET",base,token,null),200).path("totalElements").asInt());
            assertEquals(evaluationId,requireStatus(send(client,"GET",base+"/"+candidateId+"/evidence",token,null),200)
                    .path("evaluationId").asText());
            assertFalse(requireStatus(send(client,"GET",base+"/"+candidateId,leakOnlyToken,null),200)
                    .toString().contains(evaluationId));
            requireStatus(send(client,"GET",base+"/"+candidateId+"/evidence",leakOnlyToken,null),403);
            requireStatus(send(client,"POST",commandPath,leakOnlyToken,command),403);
            requireStatus(send(client,"POST",commandPath,foreignToken,command),403);
            // Both historical candidate-create URLs must be fail-closed before body parsing.
            requireStatus(send(client,"POST",base,foreignToken,Map.of()),403);
            requireStatus(send(client,"POST","/api/v1/leakdetection/create-leak-candidate",
                    foreignToken,Map.of()),403);
            requireStatus(send(client,"POST",commandPath,token,Map.of("evaluationId",evaluationId,
                    "profileId",profileId,"declaredConfidence",0.61,"reason","Changed declaration")),409);
            assertEquals(1L,database.queryForObject(
                    "select count(*) from hidra_leak_detection_evaluation_trigger where evaluation_id=?",
                    Long.class,evaluationId));
            assertEquals(1L,database.queryForObject("""
                    select count(*) from hidra_leak_detection_evidence_link where candidate_id=?
                    and evidence_type='MONITORING_DEVIATION'
                    """,Long.class,candidateId));
            assertThrows(org.springframework.dao.DataAccessException.class,()->database.update(
                    "update hidra_leak_detection_evaluation_trigger set rule_id=? where evaluation_id=?",
                    id(),evaluationId));
            JsonNode staleEvaluation=requireStatus(send(client,"POST",evalPath,token,
                    request(plan.target(),stale,at)),200);
            assertOutcome(staleEvaluation,"STALE");
            requireStatus(send(client,"POST",commandPath,token,Map.of(
                    "evaluationId",staleEvaluation.path("id").asText(),
                    "profileId",profileId,"declaredConfidence",0.6,"reason","Not qualified")),422);
            assertEquals(1,database.update("""
                update hidra_identity_user_permission_grant set status='REVOKED',revoked_at=now()
                where user_id=? and permission_id='t10-rule-read' and scope_type='FACILITY'
                """,operator));
            requireStatus(send(client,"GET",base+"/"+candidateId+"/evidence",token,null),403);
            requireStatus(send(client,"POST",commandPath,token,command),403);
        }
    }

    private String leakPermission(String code,String action) {
        database.update("""
            insert into hidra_identity_permission
                (id,code,permission_domain,resource_type,action,sensitive,status,created_at,updated_at)
            values (?,?,'leakdetection','LEAK_CANDIDATE',?,false,'ACTIVE',now(),now())
            on conflict(code) do nothing
            """,id(),code,action);
        return database.queryForObject("select id from hidra_identity_permission where code=?",String.class,code);
    }

    private String createRule(HttpClient client, String token, RuleFixture fixture, Instant start, Instant end)
            throws Exception {
        var body = Map.of("code", id(), "nameFr", "Comparaison GZ2 synthétique", "ruleType", "PLAN_COMPARISON",
                "topologyAssetType", "FACILITY", "topologyAssetId", fixture.asset(), "telemetryPointId", fixture.point());
        String rule = requireStatus(send(client, "POST", RULES, token, body), 200).path("id").asText();
        String revisions = RULES + "/" + rule + "/revisions";
        requireStatus(send(client, "POST", revisions, token, Map.of("unitId", fixture.unit(),
                "evidencePolicyCode", POLICY, "validFrom", start.toString(), "validTo", end.toString())), 201);
        requireStatus(send(client, "POST", revisions + "/1/activate", token, Map.of("expectedHeadRevision", 0,
                "effectiveFrom", start.toString(), "effectiveUntil", end.toString())), 200);
        return rule;
    }

    private String configureTelemetry(RuleFixture fixture, String operator) {
        database.update("""
                insert into hidra_identity_permission(id,code,permission_domain,resource_type,action,sensitive,status,created_at,updated_at)
                values ('t11-fixture-telemetry-execute','telemetry:points:execute','telemetry','TELEMETRY_READING',
                    'execute',false,'ACTIVE',now(),now())
                on conflict (code) do nothing
                """);
        String routePermission = database.queryForObject(
                "select id from hidra_identity_permission where code='telemetry:points:execute'", String.class);
        grant(operator, routePermission, "GLOBAL", null);
        grant(operator, "hapi933-ingest", "CUSTOM", "telemetry-company:" + fixture.company());
        grant(operator, "hapi933-ingest", "CUSTOM", "telemetry-source-point:" + fixture.source() + ":" + fixture.point());
        String qualify = database.queryForObject(
                "select id from hidra_identity_permission where code='telemetry:trusted-reading:qualify'", String.class);
        grant(operator, qualify, "CUSTOM", "telemetry-company:" + fixture.company());
        grant(operator, qualify, "CUSTOM", "telemetry-source-point:" + fixture.source() + ":" + fixture.point());
        database.update("""
                insert into hidra_telemetry_external_tag_mapping
                    (id,source_id,device_id,point_id,external_tag_name,mapping_mode,active,valid_from,created_at,updated_at)
                values (?,?,?,?,'GZ2.T11','DIRECT',true,now()-interval '1 hour',now(),now())
                """, id(), fixture.source(), fixture.device(), fixture.point());
        String quality = catalog("QUALITY_CODE", null);
        database.update("""
                insert into hidra_telemetry_validation_rule
                    (id,code,name,scope_type,scope_reference_id,rule_type_id,severity,action_on_failure,
                     configuration_json,active,valid_from,created_at,updated_at)
                values (?,'T09_RAW_QUALITY','Synthetic quality','POINT',?,?,'ERROR','REJECT',cast(? as jsonb),
                    true,now()-interval '1 hour',now(),now())
                """, id(), fixture.point(), catalog("VALIDATION_RULE_TYPE", null),
                """
                {"qualityCodeId":"%s","trustLevel":"MEDIUM","maxPastAgeSeconds":3600,"maxFutureSkewSeconds":120}
                """.formatted(quality));
        return quality;
    }

    private String publishReading(HttpClient client, String token, RuleFixture fixture, String quality, Instant observed)
            throws Exception {
        String path = "/api/v1/telemetry/points/" + fixture.point() + "/readings";
        var raw = Map.of("sourceId", fixture.source(), "sourceSequenceNumber", id(), "numericValue", 105,
                "qualityCodeId", quality, "unitId", fixture.unit(), "sourceTimestamp", observed.toString());
        String reading = requireStatus(send(client, "POST", path, token, raw), 201).path("id").asText();
        var scope = Map.of("companyPartyId", fixture.company());
        requireStatus(send(client, "POST", path + "/" + reading + "/assessment", token, scope), 201);
        return requireStatus(send(client, "POST", path + "/" + reading + "/trust", token, scope), 201)
                .path("id").asText();
    }

    private PlanFixture approvedPlan(RuleFixture fixture, String author, String reviewer, Instant start, Instant end) {
        String period = id();
        String plan = id();
        String revision = id();
        String approval = id();
        String target = id();
        String incompatible = id();
        database.update("""
                insert into hidra_planning_period(id,code,name_fr,period_type_id,period_start,period_end,time_zone,
                    status,created_by_actor_id,created_at,updated_at,company_party_id)
                values (?,?,'Synthetic period',?,?,?,'Africa/Algiers','OPEN',?,now(),now(),?)
                """, period, period, planningCatalog("PERIOD_TYPE"), java.sql.Timestamp.from(start),
                java.sql.Timestamp.from(end), author, fixture.company());
        database.update("""
                insert into hidra_planning_operational_plan(id,period_id,code,name_fr,plan_type_id,
                    topology_scope_type,topology_scope_id,topology_scope_code,status,created_by_actor_id,created_at,updated_at)
                values (?,?,?,'Synthetic plan',?,'FACILITY',?,?,'DRAFT',?,now(),now())
                """, plan, period, plan, planningCatalog("PLAN_TYPE"), fixture.asset(), fixture.asset(), author);
        database.update("""
                insert into hidra_planning_plan_revision(id,plan_id,revision_number,revision_code,status,
                    submitted_by_actor_id,submitted_at,approved_by_actor_id,approved_at,workflow_instance_id,created_at,updated_at)
                values (?,?,1,'R1','APPROVED',?,?,?,?,?,?,?)
                """, revision, plan, author, java.sql.Timestamp.from(start.minusSeconds(2)), reviewer,
                java.sql.Timestamp.from(start.minusSeconds(1)), approval,
                java.sql.Timestamp.from(start.minusSeconds(3)), java.sql.Timestamp.from(start.minusSeconds(1)));
        database.update("""
                update hidra_planning_operational_plan
                set status='ACTIVE',current_revision_id=?,approved_revision_id=? where id=?
                """, revision, revision, plan);
        String type = planningCatalog("TARGET_TYPE");
        database.update("""
                insert into hidra_planning_target_value_policy(target_type_id,representation_kind,active)
                values (?,'NUMERIC',true)
                """, type);
        for (String key : List.of(target, incompatible)) {
            database.update("""
                    insert into hidra_planning_plan_target(id,revision_id,target_type_id,topology_asset_type,
                        topology_asset_id,topology_asset_code,telemetry_point_id,target_value,unit_id,
                        valid_from,valid_to,status,created_at,updated_at)
                    values (?,?,?,'FACILITY',?,?,?,100,?,?,?,'ACTIVE',now(),now())
                    """, key, revision, type, fixture.asset(), fixture.asset(), fixture.point(),
                    key.equals(target) ? fixture.unit() : id(), java.sql.Timestamp.from(start),
                    java.sql.Timestamp.from(end));
        }
        return new PlanFixture(target, incompatible, revision, approval);
    }

    private String alarmPermission(String code, String action) {
        database.update("""
                insert into hidra_identity_permission(id,code,permission_domain,resource_type,action,
                    sensitive,status,created_at,updated_at)
                values (?,?,'alarm','ALARM',?,false,'ACTIVE',now(),now())
                on conflict (code) do nothing
                """, id(), code, action);
        return database.queryForObject("select id from hidra_identity_permission where code=?",
                String.class, code);
    }

    private String alarmCatalog(String family) {
        String key = id();
        database.update("""
                insert into hidra_alarm_catalog_entry(id,catalog_name,code,active,sort_order,
                    system_defined,created_at,updated_at)
                values (?,?,?,true,0,false,now(),now())
                """, key, family, key);
        return key;
    }

    private String planningCatalog(String family) {
        String key = id();
        database.update("""
                insert into hidra_planning_catalog_entry(id,catalog_name,code,active,sort_order,system_defined,created_at,updated_at)
                values (?,?,?,true,0,false,now(),now())
                """, key, family, key);
        return key;
    }

    private static Map<String, String> request(String target, String reading, Instant at) {
        return Map.of("targetId", target, "trustedReadingId", reading, "evaluatedAt", at.toString());
    }

    private static void assertOutcome(JsonNode receipt, String outcome) {
        assertEquals(outcome, receipt.path("outcome").asText());
        assertFalse(receipt.path("difference").isNumber());
        assertFalse(receipt.path("actual").isNumber());
        assertFalse(receipt.path("expected").isNumber());
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
                    values (?,?,?,'Synthetic T11 company','DZ','ACTIVE',now(),now())
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
                values (?,?,'Synthetic T11 provider','LOCAL','ACTIVE',false,false,now(),now())
                """, provider, provider);
        ensureEvidencePolicy(database);
        return new RuleFixture(company, otherCompany, asset, source, device, point, unit);
    }

    private static void ensureEvidencePolicy(JdbcTemplate jdbc) {
        jdbc.update("""
                insert into hidra_monitoring_catalog_entry
                    (id,catalog_name,code,active,sort_order,system_defined,created_at,updated_at)
                values ('t11-fixture-policy','EVIDENCE_POLICY',?,true,1,false,now(),now())
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

    private void grant(String user, String permission, String scope, String reference) {
        database.update("""
                insert into hidra_identity_user_permission_grant(id,user_id,permission_id,effect,scope_type,
                    scope_reference_id,grant_reason,emergency_access,valid_from,valid_to,status,created_at)
                values (?,?,?,'GRANT',?,?,'synthetic T11',false,now()-interval '1 hour',
                    now()+interval '2 hours','ACTIVE',now())
                """, id(), user, permission, scope, reference);
    }

    private String signIn(String user) {
        try {
            return authentication.authenticate(new DirectAuthenticationCommand(
                    ProviderType.LOCAL, user, PASSWORD, "synthetic-ip", "synthetic-t11", id())).accessToken();
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


    private static String id() {
        return UUID.randomUUID().toString();
    }

    private record RuleFixture(String company, String otherCompany, String asset, String source,
                               String device, String point, String unit) { }

    private record PlanFixture(String target, String incompatible, String revision, String approval) { }
}
