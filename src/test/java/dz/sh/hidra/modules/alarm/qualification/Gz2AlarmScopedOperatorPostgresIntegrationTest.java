/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : Gz2AlarmScopedOperatorPostgresIntegrationTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-11-09
 *
 * @Type        : Class
 * @Layer       : Test
 * @Module      : alarm
 * @Package     : dz.sh.hidra.modules.alarm.qualification
 *
 * @Description : Native LOCAL bearer + fresh migrated PostgreSQL three-actor Alarm scope qualification.
 *
 */
package dz.sh.hidra.modules.alarm.qualification;

import com.fasterxml.jackson.databind.ObjectMapper;
import dz.sh.hidra.HidraApplication;
import dz.sh.hidra.modules.identity.application.model.DirectAuthenticationCommand;
import dz.sh.hidra.modules.identity.application.port.in.AuthenticateDirectUserUseCase;
import dz.sh.hidra.modules.identity.domain.value.ProviderType;
import java.time.Instant;
import java.util.List;
import java.util.UUID;
import org.flywaydb.core.Flyway;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Only FACILITY-owned synthetic GZ2 station alarms are admitted. Three genuine local
 * bearer sessions carry the same route grants; only paired company+asset grants differ.
 * No mock authorization provider, fabricated OT data, or production deployment evidence.
 */
@Testcontainers(disabledWithoutDocker = true)
@SpringBootTest(classes = HidraApplication.class, webEnvironment = SpringBootTest.WebEnvironment.MOCK,
        properties = {
                "hidra.platform.security.jwt.hmac-secret=synthetic-alarm-native-qualification-key-32-bytes",
                "hidra.platform.security.jwt.token-issuer=hidra-api",
                "hidra.platform.security.jwt.audience=hidra-api"
        })
@ActiveProfiles("test")
@org.springframework.test.annotation.DirtiesContext(
        classMode = org.springframework.test.annotation.DirtiesContext.ClassMode.AFTER_CLASS)
class Gz2AlarmScopedOperatorPostgresIntegrationTest {
    private static final String ROOT = "/api/v1/alarm/alarms";
    private static final String PASSWORD = "synthetic-gz2-alarm-qualification-password";
    private static final ObjectMapper JSON = new ObjectMapper();

    @Container
    static final PostgreSQLContainer<?> PG = new PostgreSQLContainer<>("postgres:16-alpine")
            .withDatabaseName("hidra_alarm_scope_qualification");

    @DynamicPropertySource
    static void datasource(DynamicPropertyRegistry registry) {
        if (!PG.isRunning()) PG.start();
        Flyway.configure().dataSource(PG.getJdbcUrl(), PG.getUsername(), PG.getPassword())
                .load().migrate();
        registry.add("spring.datasource.url", PG::getJdbcUrl);
        registry.add("spring.datasource.username", PG::getUsername);
        registry.add("spring.datasource.password", PG::getPassword);
        registry.add("spring.flyway.url", PG::getJdbcUrl);
        registry.add("spring.flyway.user", PG::getUsername);
        registry.add("spring.flyway.password", PG::getPassword);
        registry.add("spring.jpa.hibernate.ddl-auto", () -> "validate");
    }

    @Autowired WebApplicationContext context;
    @Autowired AuthenticateDirectUserUseCase authenticate;
    @Autowired PasswordEncoder passwords;
    @Autowired @org.springframework.beans.factory.annotation.Qualifier("hidraJwtDecoder")
    org.springframework.security.oauth2.jwt.JwtDecoder nativeDecoder;
    @Autowired dz.sh.hidra.modules.identity.application.port.out.OperationAuthenticationBindingRepositoryPort
            operationBindings;
    @Autowired dz.sh.hidra.modules.identity.application.port.out.AuthenticationSecurityStateQueryPort
            authenticationStates;

    private JdbcTemplate db;
    private MockMvc http;
    private String companyA, companyB, facilityA, facilityB, facilityForeign;
    private String alarmA, alarmB, alarmForeign;
    private Actor inScope, sameCompanyOtherAsset, otherCompany;
    private String permissionRead, permissionExecute;

    private record Actor(String id, String bearer, String company, String facility) { }
    private static String id() { return UUID.randomUUID().toString(); }

    @BeforeEach
    void fixture() throws Exception {
        SecurityContextHolder.clearContext();
        db = new JdbcTemplate(new DriverManagerDataSource(PG.getJdbcUrl(),
                PG.getUsername(), PG.getPassword()));
        db.setQueryTimeout(10);
        http = MockMvcBuilders.webAppContextSetup(context)
                .apply(org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity())
                .build();

        companyA = id(); companyB = id();
        facilityA = id(); facilityB = id(); facilityForeign = id();
        String partyType = id(), facilityType = id();
        db.update("INSERT INTO hidra_party_type(id,code,status,created_at,updated_at)" +
                " VALUES (?,?,'ACTIVE',now(),now())", partyType, partyType);
        for (String company : List.of(companyA, companyB))
            db.update("INSERT INTO hidra_party_party(id,code,party_type_id,legal_name,country_code,status,created_at,updated_at)" +
                    " VALUES (?,?,?,'Synthetic GZ2 company','DZ','ACTIVE',now(),now())",
                    company, company, partyType);
        db.update("INSERT INTO hidra_topology_facility_type" +
                "(id,code,name,facility_kind,status,created_at,updated_at)" +
                " VALUES (?,?,'Synthetic gas station','COMPRESSOR_STATION','ACTIVE',now(),now())",
                facilityType, facilityType);
        station(facilityA, facilityType, companyA, "GZ2-SYNTHETIC-A");
        station(facilityB, facilityType, companyA, "GZ2-SYNTHETIC-B");
        station(facilityForeign, facilityType, companyB, "GZ2-SYNTHETIC-FOREIGN");

        db.update("UPDATE hidra_identity_provider SET status='INACTIVE' WHERE status='ACTIVE'");
        String provider = id();
        db.update("INSERT INTO hidra_identity_provider" +
                "(id,code,name,provider_type,status,sync_enabled,just_in_time_provisioning_enabled,created_at,updated_at)" +
                " VALUES (?,?,'Synthetic GZ2 local authority','LOCAL','ACTIVE',false,false,now(),now())",
                provider, provider);
        catalogs();
        permissionRead = permission("alarm:alarms:read", "read");
        permissionExecute = permission("alarm:alarms:execute", "execute");
        inScope = actor(companyA, facilityA);
        sameCompanyOtherAsset = actor(companyA, facilityB);
        otherCompany = actor(companyB, facilityForeign);
        alarmA = raise(inScope, facilityA, "AL-GZ2-ONE");
        alarmB = raise(sameCompanyOtherAsset, facilityB, "AL-GZ2-TWO");
        alarmForeign = raise(otherCompany, facilityForeign, "AL-GZ2-FOREIGN");
    }

    private void station(String facility, String type, String company, String name) {
        db.update("INSERT INTO hidra_topology_facility" +
                "(id,code,name_fr,facility_type_id,facility_kind,owner_party_id,status,created_at,updated_at)" +
                " VALUES (?,?,?,?,'COMPRESSOR_STATION',?,'ACTIVE',now(),now())",
                facility, facility, name, type, company);
    }

    private String permission(String code, String action) {
        var found = db.queryForList("SELECT id FROM hidra_identity_permission WHERE code=?", String.class, code);
        if (!found.isEmpty()) return found.getFirst();
        String value = id();
        db.update("INSERT INTO hidra_identity_permission" +
                "(id,code,permission_domain,resource_type,action,sensitive,status,created_at,updated_at)" +
                " VALUES (?,?,'alarm','ALARM',?,false,'ACTIVE',now(),now())",
                value, code, action);
        return value;
    }

    private Actor actor(String company, String facility) {
        String user = id();
        db.update("INSERT INTO hidra_identity_user" +
                "(id,username,display_name,user_type,status,failed_login_count,created_at,updated_at,activated_at)" +
                " VALUES (?,?,'Synthetic GZ2 operator','HUMAN','ACTIVE',0,now(),now(),now())",
                user, user);
        db.update("INSERT INTO hidra_identity_local_credential" +
                "(id,user_id,password_hash,credential_status,created_at,updated_at)" +
                " VALUES (?,?,?,'ACTIVE',now(),now())", id(), user, passwords.encode(PASSWORD));
        for (String p : List.of(permissionRead, permissionExecute)) {
            grant(user, p, "GLOBAL", null);
            grant(user, p, "CUSTOM", "alarm-company:" + company);
            grant(user, p, "FACILITY", facility);
        }
        String bearer = authenticate.authenticate(new DirectAuthenticationCommand(
                ProviderType.LOCAL, user, PASSWORD, "synthetic-ip", "synthetic-agent",
                "synthetic-gz2-login")).accessToken();
        SecurityContextHolder.clearContext();
        return new Actor(user, bearer, company, facility);
    }

    private void grant(String user, String permission, String scopeType, String ref) {
        db.update("INSERT INTO hidra_identity_user_permission_grant" +
                "(id,user_id,permission_id,effect,scope_type,scope_reference_id,grant_reason,emergency_access," +
                "valid_from,valid_to,status,created_at)" +
                " VALUES (?,?,?,'GRANT',?,?,'synthetic GZ2 scope',false," +
                "now()-interval '1 hour',now()+interval '2 hours','ACTIVE',now())",
                id(), user, permission, scopeType, ref);
    }

    private String raise(Actor actor, String facility, String number) throws Exception {
        String request = """
                {"alarmNumber":"%s","alarmTypeId":"%s","severityId":"%s",
                 "titleFr":"Synthetic GZ2 gas alarm","sourceType":"MANUAL",
                 "topologyAssetTypeCode":"FACILITY","topologyAssetId":"%s","topologyAssetCode":"%s"}
                """.formatted(number, alarmType, severity, facility, facility);
        var result = http.perform(post(ROOT).header("Authorization", "Bearer " + actor.bearer())
                .contentType(MediaType.APPLICATION_JSON).content(request))
                .andDo(mvcResult -> {
                    if (mvcResult.getResponse().getStatus() != 200) {
                        var decoded = nativeDecoder.decode(actor.bearer());
                        var issuer = decoded.getIssuer();
                        var jti = decoded.getId();
                        java.util.Optional<dz.sh.hidra.modules.identity.domain.model.OperationAuthenticationBinding>
                                persisted = issuer == null || jti == null
                                        ? java.util.Optional.empty()
                                        : operationBindings.findByNativeIssuerAndJti(issuer.toString(), jti);
                        boolean bindingCurrent = persisted
                                .map(binding -> authenticationStates.current(binding, Instant.now()))
                                .orElse(false);
                        System.err.println("HAPI-930 native alarm-raise diagnostics: status="
                                + mvcResult.getResponse().getStatus()
                                + ", matchedRoute=" + mvcResult.getRequest().getAttribute(
                                        org.springframework.web.servlet.HandlerMapping.BEST_MATCHING_PATTERN_ATTRIBUTE)
                                + ", handler=" + mvcResult.getHandler()
                                + ", resolvedException=" + mvcResult.getResolvedException()
                                + ", responseBody=" + mvcResult.getResponse().getContentAsString()
                                + ", subjectMatchesActor=" + actor.id().equals(decoded.getSubject())
                                + ", issuerPresent=" + (issuer != null)
                                + ", jtiPresent=" + (jti != null)
                                + ", bindingPresent=" + persisted.isPresent()
                                + ", bindingActorMatches=" + persisted
                                        .map(binding -> actor.id().equals(binding.userId())).orElse(false)
                                + ", bindingCurrent=" + bindingCurrent);
                    }
                })
                .andExpect(status().isOk()).andReturn();
        String created = JSON.readTree(result.getResponse().getContentAsString()).path("id").asText();
        assertFalse(created.isBlank());
        return created;
    }

    private String alarmType, severity;

    private void catalogs() {
        alarmType = id(); severity = id();
        db.update("INSERT INTO hidra_alarm_catalog_entry" +
                "(id,catalog_name,code,active,sort_order,system_defined,created_at,updated_at)" +
                " VALUES (?,'ALARM_TYPE',?,true,0,false,now(),now())",
                alarmType, alarmType);
        db.update("INSERT INTO hidra_alarm_catalog_entry" +
                "(id,catalog_name,code,active,sort_order,system_defined,created_at,updated_at)" +
                " VALUES (?,'ALARM_SEVERITY',?,true,0,false,now(),now())",
                severity, severity);
    }

    private long count(String table, String alarm) {
        return db.queryForObject("SELECT count(*) FROM " + table + " WHERE alarm_id=?",
                Long.class, alarm);
    }

    @Test
    void pairedCurrentCompanyAndAssetScopesProtectNativeReadsCountsAndAcknowledgementHistory()
            throws Exception {
        assertEquals(3L, db.queryForObject("SELECT count(*) FROM hidra_alarm WHERE id IN (?,?,?)",
                Long.class, alarmA, alarmB, alarmForeign));

        for (Actor actor : List.of(inScope, sameCompanyOtherAsset, otherCompany)) {
            String own = actor == inScope ? alarmA : actor == sameCompanyOtherAsset ? alarmB : alarmForeign;
            http.perform(get(ROOT).header("Authorization", "Bearer " + actor.bearer())
                    .param("view", "all").param("page", "0").param("size", "2"))
                    .andExpect(status().isOk()).andExpect(jsonPath("$.totalElements").value(1))
                    .andExpect(jsonPath("$.content.length()").value(1))
                    .andExpect(jsonPath("$.content[0].id").value(own));
            http.perform(get(ROOT + "/" + own).header("Authorization", "Bearer " + actor.bearer()))
                    .andExpect(status().isOk()).andExpect(jsonPath("$.id").value(own));
        }
        for (Actor denied : List.of(sameCompanyOtherAsset, otherCompany)) {
            http.perform(get(ROOT + "/" + alarmA).header("Authorization", "Bearer " + denied.bearer()))
                    .andExpect(status().isNotFound());
            http.perform(get(ROOT + "/" + alarmA + "/shelvings")
                    .header("Authorization", "Bearer " + denied.bearer()))
                    .andExpect(status().isNotFound());
            http.perform(get(ROOT).header("Authorization", "Bearer " + denied.bearer())
                    .param("view", "all").param("topologyAssetId", facilityA))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.totalElements").value(0))
                    .andExpect(jsonPath("$.content.length()").value(0));
        }

        long ackBefore = count("hidra_alarm_acknowledgement", alarmA);
        long eventBefore = count("hidra_alarm_lifecycle_event", alarmA);
        for (Actor denied : List.of(sameCompanyOtherAsset, otherCompany)) {
            http.perform(post(ROOT + "/acknowledgements").header("Authorization", "Bearer " + denied.bearer())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content("{\"alarmId\":\"" + alarmA + "\",\"organizationUnitId\":\"forged\"}"))
                    .andExpect(status().isNotFound());
        }
        assertEquals(ackBefore, count("hidra_alarm_acknowledgement", alarmA));
        assertEquals(eventBefore, count("hidra_alarm_lifecycle_event", alarmA));
        assertEquals("RAISED", db.queryForObject("SELECT current_state FROM hidra_alarm WHERE id=?",
                String.class, alarmA));

        for (int action = 0; action < 2; action++) {
            http.perform(post(ROOT + "/acknowledgements")
                    .header("Authorization", "Bearer " + inScope.bearer())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content("{\"alarmId\":\"" + alarmA + "\",\"comment\":\"synthetic-operator-action\"}"))
                    .andExpect(status().isOk());
        }
        assertEquals(ackBefore + 2, count("hidra_alarm_acknowledgement", alarmA));
        assertEquals(eventBefore + 2, count("hidra_alarm_lifecycle_event", alarmA));
        assertEquals(2L, db.queryForObject("SELECT count(*) FROM hidra_alarm_acknowledgement" +
                " WHERE alarm_id=? AND acknowledged_by_actor_id=?", Long.class, alarmA, inScope.id()));
        http.perform(get(ROOT + "/" + alarmA).header("Authorization", "Bearer " + inScope.bearer()))
                .andExpect(status().isOk()).andExpect(jsonPath("$.acknowledgedByActorId").value(inScope.id()));

        // Revocation is observed by the native API without accepting stale JWT grants.
        db.update("UPDATE hidra_identity_user_permission_grant SET status='REVOKED',revoked_at=now()" +
                " WHERE user_id=? AND scope_type='FACILITY' AND scope_reference_id=?",
                inScope.id(), facilityA);
        http.perform(get(ROOT + "/" + alarmA).header("Authorization", "Bearer " + inScope.bearer()))
                .andExpect(status().isNotFound());
        http.perform(get(ROOT).header("Authorization", "Bearer " + inScope.bearer()).param("view", "all"))
                .andExpect(status().isOk()).andExpect(jsonPath("$.totalElements").value(0));
        http.perform(post(ROOT + "/acknowledgements")
                .header("Authorization", "Bearer " + inScope.bearer())
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"alarmId\":\"" + alarmA + "\"}"))
                .andExpect(status().isNotFound());
        assertEquals(ackBefore + 2, count("hidra_alarm_acknowledgement", alarmA));

        // Changing actual ownership does not resurrect authority from an Alarm snapshot.
        db.update("UPDATE hidra_topology_facility SET owner_party_id=?,updated_at=now() WHERE id=?",
                companyB, facilityA);
        http.perform(get(ROOT + "/" + alarmA).header("Authorization", "Bearer " + otherCompany.bearer()))
                .andExpect(status().isNotFound());
        // Pipeline-system assets remain unsupported until their current owner is proven.
        http.perform(post(ROOT).header("Authorization", "Bearer " + otherCompany.bearer())
                .contentType(MediaType.APPLICATION_JSON).content(
                "{\"alarmNumber\":\"synthetic-network\",\"alarmTypeId\":\"" + alarmType +
                "\",\"severityId\":\"" + severity + "\",\"titleFr\":\"Synthetic network alarm\"," +
                "\"topologyAssetTypeCode\":\"PIPELINE_SYSTEM\",\"topologyAssetId\":\"GZ2\"," +
                "\"topologyAssetCode\":\"GZ2\"}"))
                .andExpect(status().isNotFound());
    }
}
