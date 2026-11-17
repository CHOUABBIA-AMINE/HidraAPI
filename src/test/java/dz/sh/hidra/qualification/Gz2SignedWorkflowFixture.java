/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : Gz2SignedWorkflowFixture
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-11-15
 *
 * @Type        : Class
 * @Layer       : Test
 * @Module      : qualification
 * @Package     : dz.sh.hidra.qualification
 *
 * @Description : Supplies synthetic GZ2 prerequisites and a real signed HTTP runtime.
 *
 */
package dz.sh.hidra.qualification;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import dz.sh.hidra.HidraApplication;
import org.flywaydb.core.Flyway;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.testcontainers.containers.PostgreSQLContainer;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

class Gz2SignedWorkflowFixture {
    private static final ObjectMapper JSON = new ObjectMapper();
    private static final String APP = "gz2_runtime";
    private static final String PUBLICATION = "gz2_publication";
    private static final String APP_PASSWORD = "synthetic-gz2-runtime";
    private static final String PUBLICATION_PASSWORD = "synthetic-gz2-publication";
    private static final String HUMAN_PASSWORD = "synthetic-gz2-human-password";
    private static final String JWT_KEY = "synthetic-gz2-test-only-jwt-key-32-bytes";

    final PostgreSQLContainer<?> postgres;
    final JdbcTemplate sql;
    final String company = id();
    final String foreignCompany = id();
    final String pipeline = id();
    final String asset = id();
    final String secondAsset = id();
    final String product = id();
    final String pressureUnit = "gz2-pressure";
    final String author;
    final String reviewer;
    final String producer;
    final String operator;
    final String reader;
    final String foreign;
    final String wrongAsset;
    final String revoked;

    Gz2SignedWorkflowFixture(PostgreSQLContainer<?> postgres) {
        this.postgres = postgres;
        sql = new JdbcTemplate(new DriverManagerDataSource(postgres.getJdbcUrl(),
                postgres.getUsername(), postgres.getPassword()));
        for (String role : List.of(APP, PUBLICATION)) {
            String password = role.equals(APP) ? APP_PASSWORD : PUBLICATION_PASSWORD;
            sql.execute("CREATE ROLE " + role + " LOGIN PASSWORD '" + password + "'");
            sql.execute("GRANT USAGE ON SCHEMA public TO " + role);
            sql.execute("ALTER DEFAULT PRIVILEGES IN SCHEMA public GRANT SELECT,INSERT,UPDATE,DELETE ON TABLES TO " + role);
        }
        sql.execute("REVOKE CREATE ON SCHEMA public FROM PUBLIC");
        Flyway flyway = Flyway.configure()
                .dataSource(postgres.getJdbcUrl(), postgres.getUsername(), postgres.getPassword()).load();
        flyway.migrate();
        flyway.validate();
        assertEquals(0, flyway.info().pending().length);
        restrictEngineeringRoles();
        seedScope();
        verifyRuntimeUnitLock();
        author = human("author");
        reviewer = human("reviewer");
        producer = human("producer");
        operator = human("operator");
        reader = human("reader");
        foreign = human("foreign");
        wrongAsset = human("wrong-asset");
        revoked = human("revoked");
    }

    private void restrictEngineeringRoles() {
        restrict("hidra_identity_external_mapping_generation,hidra_party_eligibility_generation,"
                + "hidra_topology_facility_owner_generation", "SELECT", "SELECT");
        restrict("hidra_custody_product_engineering_draft,hidra_custody_product_engineering_draft_version,"
                + "hidra_custody_supplied_fluid_property_draft,hidra_custody_supplied_fluid_property_version,"
                + "hidra_custody_product_publication,hidra_custody_product_publication_candidate,"
                + "hidra_custody_product_publication_head,hidra_custody_product_publication_event,"
                + "hidra_custody_product_publication_request,hidra_topology_engineering_context,"
                + "hidra_topology_engineering_draft,hidra_topology_engineering_draft_version",
                "SELECT", "SELECT,INSERT,UPDATE");
        restrict("hidra_custody_catalog_entry,hidra_custody_engineering_product_policy,"
                + "hidra_telemetry_unit,hidra_telemetry_fluid_unit_policy,"
                + "hidra_workflow_custody_publication_policy", "SELECT", "SELECT,UPDATE");
        // PostgreSQL requires UPDATE on one column for a locking read. Planning,
        // ingestion and Monitoring all take a shared lock on the unit row.
        sql.execute("GRANT UPDATE (updated_at) ON public.hidra_telemetry_unit TO " + APP);
        restrict("hidra_topology_station_graph_head,hidra_topology_station_graph_operation_event",
                "SELECT", "SELECT,INSERT,UPDATE,DELETE");
        restrict("hidra_topology_engineering_graph_snapshot,hidra_topology_engineering_survey_snapshot,"
                + "hidra_topology_engineering_publication_snapshot,hidra_topology_engineering_publication_event,"
                + "hidra_topology_engineering_publication_head,hidra_topology_engineering_baseline_draft,"
                + "hidra_topology_engineering_baseline_revision,hidra_topology_engineering_baseline_request",
                null, "SELECT,INSERT,UPDATE");
        sql.execute("GRANT SELECT,INSERT ON hidra_topology_baseline_submission,hidra_topology_baseline_publication TO " + PUBLICATION);
        for (String function : List.of("hidra_topology_baseline_lock_storage", "hidra_topology_baseline_lock_sources",
                "hidra_custody_baseline_lock_product_source", "hidra_topology_baseline_lock_publication")) {
            sql.execute("GRANT EXECUTE ON FUNCTION public." + function + "() TO " + PUBLICATION);
        }
    }

    private void restrict(String tables, String appRights, String publicationRights) {
        sql.execute("REVOKE ALL ON " + tables + " FROM " + APP + "," + PUBLICATION);
        if (appRights != null) {
            sql.execute("GRANT " + appRights + " ON " + tables + " TO " + APP);
        }
        sql.execute("GRANT " + publicationRights + " ON " + tables + " TO " + PUBLICATION);
    }

    void enablePlanningProductReferenceLock() {
        // The signed runtime takes FOR SHARE on the Custody catalog row. It needs a
        // column-level UPDATE privilege for that lock, without writable product attributes.
        sql.execute("GRANT UPDATE (updated_at) ON public.hidra_custody_catalog_entry TO " + APP);
    }

    private void verifyRuntimeUnitLock() {
        var runtimeSql = new JdbcTemplate(new DriverManagerDataSource(postgres.getJdbcUrl(), APP, APP_PASSWORD));
        assertEquals(pressureUnit, runtimeSql.queryForObject("""
                SELECT id FROM public.hidra_telemetry_unit WHERE id = ? AND active FOR SHARE
                """, String.class, pressureUnit));
    }

    private void seedScope() {
        String partyType = id();
        sql.update("INSERT INTO hidra_party_type(id,code,status,created_at,updated_at) VALUES (?,?,'ACTIVE',now(),now())", partyType, partyType);
        for (String owner : List.of(company, foreignCompany)) {
            sql.update("""
                    INSERT INTO hidra_party_party(id,code,party_type_id,legal_name,country_code,status,created_at,updated_at)
                    VALUES (?,?,?,'Synthetic GZ2 company','DZ','ACTIVE',now(),now())
                    """, owner, owner, partyType);
        }
        String pipelineType = id();
        sql.update("INSERT INTO hidra_topology_pipeline_system_type(id,code,active,created_at,updated_at) VALUES (?,?,true,now(),now())",
                pipelineType, pipelineType);
        sql.update("INSERT INTO hidra_topology_pipeline_system(id,code,system_type_id,status,created_at,updated_at) VALUES (?,?,?,'DRAFT',now(),now())",
                pipeline, "GZ2-" + pipeline, pipelineType);
        String facilityType = id();
        sql.update("""
                INSERT INTO hidra_topology_facility_type(id,code,name,facility_kind,status,created_at,updated_at)
                VALUES (?,?,'Synthetic gas station','COMPRESSOR_STATION','ACTIVE',now(),now())
                """, facilityType, facilityType);
        for (String station : List.of(asset, secondAsset)) {
            sql.update("""
                    INSERT INTO hidra_topology_facility(id,code,name_fr,facility_type_id,facility_kind,owner_party_id,status,created_at,updated_at)
                    VALUES (?,?,'Synthetic GZ2 station',?,'COMPRESSOR_STATION',?,'ACTIVE',now(),now())
                    """, station, station, facilityType, company);
        }
        sql.update("""
                INSERT INTO hidra_topology_pipeline_system_facility(id,pipeline_system_id,facility_id,status,created_at,updated_at)
                VALUES (?,?,?,'ACTIVE',now(),now())
                """, id(), pipeline, asset);
        sql.update("""
                INSERT INTO hidra_custody_catalog_entry(id,catalog_name,code,active,sort_order,system_defined,created_at,updated_at)
                VALUES (?,'PRODUCT',?,true,0,false,now(),now())
                """, product, product);
        sql.update("INSERT INTO hidra_custody_engineering_product_policy(catalog_product_id,active) VALUES (?,true)", product);
        for (String[] unit : List.of(new String[]{pressureUnit, "ABSOLUTE_PRESSURE", "bar", "PRESSURE"},
                new String[]{"gz2-density", "DENSITY", "kg/m3", "DENSITY"},
                new String[]{"gz2-temperature", "ABSOLUTE_TEMPERATURE", "K", "TEMPERATURE"})) {
            sql.update("""
                    INSERT INTO hidra_telemetry_unit(id,code,symbol,dimension,active,system_defined,created_at,updated_at)
                    VALUES (?,?,?,?,true,false,now(),now())
                    """, unit[0], unit[0], unit[2], unit[3]);
            sql.update("""
                    INSERT INTO hidra_telemetry_fluid_unit_policy(unit_id,usage_role,expected_dimension,active,created_at,updated_at)
                    VALUES (?,?,?,true,now(),now())
                    """, unit[0], unit[1], unit[3]);
        }
        sql.update("UPDATE hidra_identity_provider SET status='INACTIVE' WHERE provider_type='LOCAL' AND status='ACTIVE'");
        String provider = id();
        sql.update("""
                INSERT INTO hidra_identity_provider(id,code,name,provider_type,status,sync_enabled,
                    just_in_time_provisioning_enabled,created_at,updated_at)
                VALUES (?,?,'Synthetic GZ2','LOCAL','ACTIVE',false,false,now(),now())
                """, provider, provider);
    }

    private String human(String label) {
        String actor = id();
        sql.update("""
                INSERT INTO hidra_identity_user(id,username,display_name,user_type,status,failed_login_count,
                    created_at,updated_at,activated_at)
                VALUES (?,?,?,'HUMAN','ACTIVE',0,now(),now(),now())
                """, actor, actor, "Synthetic GZ2 " + label);
        sql.update("""
                INSERT INTO hidra_identity_local_credential(id,user_id,password_hash,credential_status,created_at,updated_at)
                VALUES (?,?,?,'ACTIVE',now(),now())
                """, id(), actor, new BCryptPasswordEncoder().encode(HUMAN_PASSWORD));
        return actor;
    }

    static String id() {
        return UUID.randomUUID().toString();
    }

    void permit(String actor, String code, String resource, String scope, String reference) {
        String[] parts = code.split(":");
        sql.update("""
                INSERT INTO hidra_identity_permission(id,code,permission_domain,resource_type,action,sensitive,status,created_at,updated_at)
                VALUES (?,?,?,?,?,false,'ACTIVE',now(),now()) ON CONFLICT (code) DO NOTHING
                """, id(), code, parts[0], resource, parts[parts.length - 1]);
        sql.update("""
                INSERT INTO hidra_identity_user_permission_grant(id,user_id,permission_id,effect,scope_type,scope_reference_id,
                    grant_reason,emergency_access,valid_from,valid_to,status,created_at)
                SELECT ?,?,id,'GRANT',?,?, 'Synthetic GZ2 qualification',false,now()-interval '1 hour',now()+interval '2 hours','ACTIVE',now()
                FROM hidra_identity_permission WHERE code=?
                """, id(), actor, scope, reference, code);
    }

    void global(String actor, String... codes) {
        for (String code : codes) {
            permit(actor, code, "ROUTE", "GLOBAL", null);
        }
    }

    void paired(String actor, String prefix, String resource, String left, String right, String... actions) {
        for (String action : actions) {
            permit(actor, prefix + ":" + action, resource, "CUSTOM", left);
            permit(actor, prefix + ":" + action, resource, "CUSTOM", right);
        }
    }

    void assetGrant(String actor, String code, String owner, String station) {
        String module = code.substring(0, code.indexOf(':'));
        permit(actor, code, module.toUpperCase(java.util.Locale.ROOT), "CUSTOM", module + "-company:" + owner);
        permit(actor, code, module.toUpperCase(java.util.Locale.ROOT), "FACILITY", station);
    }

    String catalog(String module, String family, String code) {
        var matches = sql.queryForList("SELECT id FROM hidra_" + module
                + "_catalog_entry WHERE catalog_name=? AND code=? AND active=true", String.class, family, code);
        if (!matches.isEmpty()) {
            return matches.getFirst();
        }
        String id = id();
        sql.update("INSERT INTO hidra_" + module + "_catalog_entry"
                + "(id,catalog_name,code,active,sort_order,system_defined,created_at,updated_at) VALUES (?,?,?,true,0,false,now(),now())",
                id, family, code);
        return id;
    }

    String telemetryType(String family, String shape) {
        String id = id();
        sql.update("""
                INSERT INTO hidra_telemetry_type_catalog(id,catalog_name,code,active,value_shape,numeric_unit_exempt,
                    sort_order,system_defined,created_at,updated_at)
                VALUES (?,?,?,true,?,false,0,false,now(),now())
                """, id, family, id, shape);
        return id;
    }

    record Review(String definition, String type, String purpose, String step, String transition) { }

    Review review(String module, String target, String purposeCode, boolean planning) {
        String type = workflowType("WORKFLOW_TARGET_TYPE", target);
        String purpose = workflowType("WORKFLOW_PURPOSE", purposeCode);
        String definition = id();
        String initial = id();
        String terminal = id();
        String transition = id();
        sql.update("""
                INSERT INTO hidra_workflow_definition(id,code,name_fr,type_id,status,version,created_at,updated_at)
                VALUES (?,?,'Synthetic GZ2 review',?,'ACTIVE',1,now(),now())
                """, definition, definition, type);
        sql.update("""
                INSERT INTO hidra_workflow_definition_target_binding(id,definition_id,target_module,target_type_id,
                    workflow_purpose_id,active,created_at,updated_at)
                VALUES (?,?,?,?,?,true,now(),now())
                """, id(), definition, module, type, purpose);
        for (String step : List.of(initial, terminal)) {
            sql.update("""
                    INSERT INTO hidra_workflow_step(id,definition_id,code,name_fr,step_order,mandatory,allow_claim,
                        allow_delegation,allow_escalation,created_at,updated_at)
                    VALUES (?,?,?,'Synthetic GZ2 review',?,true,false,false,false,now(),now())
                    """, step, definition, step, step.equals(initial) ? 0 : 1);
        }
        sql.update("""
                INSERT INTO hidra_workflow_transition(id,definition_id,from_step_id,to_step_id,decision,reason_required,
                    comment_required,created_at,updated_at)
                VALUES (?,?,?,?,'APPROVE',false,false,now(),now())
                """, transition, definition, initial, terminal);
        if (planning) {
            sql.update("""
                    INSERT INTO hidra_workflow_step_assignment_rule(id,definition_id,step_id,assignment_mode_id,actor_id,
                        active,created_at,updated_at) VALUES (?,?,?,?,?,true,now(),now())
                    """, id(), definition, initial, workflowType("WORKFLOW_ASSIGNMENT_MODE", "DIRECT"), reviewer);
        }
        return new Review(definition, type, purpose, initial, transition);
    }


    record RiskReview(String definition,String type,String purpose,String firstStep,
                      String firstTransition,String finalTransition) { }

    /** A genuine intermediate human review followed by independent final approval. */
    RiskReview riskReview() {
        String type=workflowType("WORKFLOW_TARGET_TYPE","RISK_ASSESSMENT");
        String purpose=workflowType("WORKFLOW_PURPOSE","RISK_ASSESSMENT_APPROVAL");
        String definition=id(),first=id(),middle=id(),last=id(),reviewTransition=id(),approveTransition=id();
        sql.update("""
            INSERT INTO hidra_workflow_definition(id,code,name_fr,type_id,status,version,created_at,updated_at)
            VALUES (?,?,'Synthetic scoped Risk review',?,'ACTIVE',1,now(),now())
            """,definition,definition,type);
        sql.update("""
            INSERT INTO hidra_workflow_definition_target_binding(id,definition_id,target_module,target_type_id,
                workflow_purpose_id,active,created_at,updated_at)
            VALUES (?,?,'risk',?,?,true,now(),now())
            """,id(),definition,type,purpose);
        for (String[] step : List.of(new String[]{first,"0"},
                new String[]{middle,"1"},new String[]{last,"2"})) {
            sql.update("""
                INSERT INTO hidra_workflow_step(id,definition_id,code,name_fr,step_order,
                    mandatory,allow_claim,allow_delegation,allow_escalation,created_at,updated_at)
                VALUES (?,?,?,'Synthetic Risk decision',?,true,false,false,false,now(),now())
                """,step[0],definition,step[0],Integer.parseInt(step[1]));
        }
        String mode=workflowType("WORKFLOW_ASSIGNMENT_MODE","DIRECT");
        String rule=id();
        sql.update("""
            INSERT INTO hidra_workflow_step_assignment_rule(id,definition_id,step_id,
                    assignment_mode_id,actor_id,active,created_at,updated_at)
            VALUES (?,?,?,?,?,true,now(),now())
            """,rule,definition,middle,mode,operator);
        sql.update("UPDATE hidra_workflow_step SET default_assignment_rule_id=? WHERE id=?",rule,middle);
        for(String[] transition:List.of(new String[]{reviewTransition,first,middle},
                new String[]{approveTransition,middle,last})) {
            sql.update("""
                INSERT INTO hidra_workflow_transition(id,definition_id,from_step_id,to_step_id,
                    decision,reason_required,comment_required,created_at,updated_at)
                VALUES (?,?,?,?,'APPROVE',false,false,now(),now())
                """,transition[0],definition,transition[1],transition[2]);
        }
        return new RiskReview(definition,type,purpose,first,reviewTransition,approveTransition);
    }

    private String workflowType(String family, String code) {
        var found = sql.queryForList("SELECT id FROM hidra_workflow_type_catalog WHERE catalog_name=? AND code=? AND active=true",
                String.class, family, code);
        if (!found.isEmpty()) {
            return found.getFirst();
        }
        String id = id();
        sql.update("""
                INSERT INTO hidra_workflow_type_catalog(id,catalog_name,code,active,sort_order,system_defined,created_at,updated_at)
                VALUES (?,?,?,true,0,false,now(),now())
                """, id, family, code);
        return id;
    }

    Runtime start() throws Exception {
        return new Runtime();
    }

    final class Runtime implements AutoCloseable {
        private final HttpClient client = HttpClient.newBuilder().connectTimeout(Duration.ofSeconds(2)).build();
        private final Process child;
        private final Path log;
        private final String base;

        Runtime() throws Exception {
            int port;
            try (var socket = new ServerSocket(0, 0, InetAddress.getByName("127.0.0.1"))) {
                port = socket.getLocalPort();
            }
            base = "http://127.0.0.1:" + port;
            log = Files.createTempFile("hidra-gz2-runtime-", ".log");
            ProcessBuilder launcher = new ProcessBuilder(
                    Path.of(System.getProperty("java.home"), "bin", "java").toString(),
                    "-cp", System.getProperty("surefire.test.class.path", System.getProperty("java.class.path")),
                    HidraApplication.class.getName(), "--spring.profiles.active=test",
                    "--server.address=127.0.0.1", "--server.port=" + port,
                    "--management.server.port=" + port, "--springdoc.api-docs.enabled=true",
                    "--spring.datasource.url=" + postgres.getJdbcUrl(), "--spring.datasource.username=" + APP,
                    "--spring.flyway.url=" + postgres.getJdbcUrl(), "--spring.flyway.user=" + postgres.getUsername(),
                    "--hidra.platform.persistence.publication.enabled=true",
                    "--hidra.platform.persistence.publication.username=" + PUBLICATION,
                    "--hidra.platform.security.jwt.token-issuer=hidra-api",
                    "--hidra.api.errors.include-debug-details=false");
            launcher.environment().put("SPRING_DATASOURCE_PASSWORD", APP_PASSWORD);
            launcher.environment().put("SPRING_FLYWAY_PASSWORD", postgres.getPassword());
            launcher.environment().put("HIDRA_PLATFORM_PERSISTENCE_PUBLICATION_PASSWORD", PUBLICATION_PASSWORD);
            launcher.environment().put("HIDRA_JWT_HMAC_SECRET", JWT_KEY);
            launcher.redirectErrorStream(true).redirectOutput(log.toFile());
            try {
                child = launcher.start();
            } catch (IOException e) {
                Files.deleteIfExists(log);
                client.close();
                throw e;
            }
            try {
                assertNotEquals(ProcessHandle.current().pid(), child.pid());
                long deadline = System.nanoTime() + TimeUnit.SECONDS.toNanos(100);
                while (child.isAlive() && System.nanoTime() < deadline) {
                    try {
                        var response = send("GET", "/actuator/health", null, null);
                        if (response.statusCode() == 200 && "UP".equals(JSON.readTree(response.body()).path("status").asText())) {
                            return;
                        }
                    } catch (IOException unavailable) {
                        // The listener is unavailable until the child finishes startup.
                    }
                    Thread.sleep(300);
                }
                String diagnostics = Files.readString(log);
                fail("GZ2 child failed readiness: " + redact(diagnostics.substring(Math.max(0, diagnostics.length() - 4000))));
            } catch (Exception | AssertionError failure) {
                try {
                    close();
                } catch (Exception | AssertionError cleanup) {
                    failure.addSuppressed(cleanup);
                }
                throw failure;
            }
        }

        long pid() {
            return child.pid();
        }

        String login(String actor) throws Exception {
            var response = send("POST", "/api/v1/identity/authentication/login", null,
                    Map.of("providerType", "LOCAL", "principal", actor, "credentials", HUMAN_PASSWORD));
            assertEquals(200, response.statusCode(), "Native LOCAL login status");
            JsonNode body = JSON.readTree(response.body());
            assertEquals(actor, body.path("userId").asText());
            String token = body.path("accessToken").asText();
            assertFalse(token.isBlank(), "Identity must issue a signed access token");
            return token;
        }

        HttpResponse<String> send(String method, String route, String token, Object body) throws Exception {
            var request = HttpRequest.newBuilder(URI.create(base + route)).timeout(Duration.ofSeconds(25));
            if (token != null) {
                request.header("Authorization", "Bearer " + token);
            }
            if (body != null) {
                request.header("Content-Type", "application/json");
            }
            request.method(method, body == null ? HttpRequest.BodyPublishers.noBody()
                    : HttpRequest.BodyPublishers.ofString(JSON.writeValueAsString(body)));
            return client.send(request.build(), HttpResponse.BodyHandlers.ofString());
        }

        JsonNode json(String method, String route, String token, Object body, int status) throws Exception {
            var response = send(method, route, token, body);
            assertEquals(status, response.statusCode(), () -> method + " " + route + ": "
                    + redact(response.body()) + (response.statusCode() >= 500 ? serverDiagnostics() : ""));
            return JSON.readTree(response.body());
        }

        private String serverDiagnostics() {
            try {
                String logs = Files.readString(log);
                String tail = logs.substring(Math.max(0, logs.length() - 6000));
                return "\nGZ2 child log tail:\n" + redact(tail)
                        .replaceAll("[A-Za-z0-9_-]{12,}\\.[A-Za-z0-9_-]{12,}\\.[A-Za-z0-9_-]{12,}", "[redacted JWT]");
            } catch (IOException unavailable) {
                return "\nGZ2 child log could not be read.";
            }
        }

        JsonNode get(String route, String token) throws Exception {
            return json("GET", route, token, null, 200);
        }

        @Override
        public void close() throws Exception {
            try {
                child.destroy();
                if (!child.waitFor(10, TimeUnit.SECONDS)) {
                    child.destroyForcibly();
                    assertTrue(child.waitFor(10, TimeUnit.SECONDS), "Child JVM must stop");
                }
                assertFalse(child.isAlive());
            } finally {
                client.close();
                Files.deleteIfExists(log);
            }
        }
    }

    static String query(String path, String... pairs) {
        StringBuilder url = new StringBuilder(path);
        for (int i = 0; i < pairs.length; i += 2) {
            url.append(i == 0 ? '?' : '&').append(pairs[i]).append('=')
                    .append(URLEncoder.encode(pairs[i + 1], StandardCharsets.UTF_8));
        }
        return url.toString();
    }

    private String redact(String value) {
        return value.replace(APP_PASSWORD, "[redacted]").replace(PUBLICATION_PASSWORD, "[redacted]")
                .replace(HUMAN_PASSWORD, "[redacted]").replace(postgres.getPassword(), "[redacted]")
                .replace(JWT_KEY, "[redacted]");
    }
}
