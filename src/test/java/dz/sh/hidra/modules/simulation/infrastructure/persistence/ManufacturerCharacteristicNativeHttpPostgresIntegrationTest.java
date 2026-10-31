/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : ManufacturerCharacteristicNativeHttpPostgresIntegrationTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-31
 *
 * @Type        : Class
 * @Layer       : Test
 * @Module      : simulation
 * @Package     : dz.sh.hidra.modules.simulation.infrastructure.persistence
 *
 * @Description : Verifies configured native manufacturer HTTP admission against a disposable migrated PostgreSQL catalog.
 *
 */
package dz.sh.hidra.modules.simulation.infrastructure.persistence;

import static org.assertj.core.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import dz.sh.hidra.HidraApplication;
import dz.sh.hidra.modules.identity.application.contract.simulation.SimulationManufacturerCharacteristicAuthorizationContract;
import dz.sh.hidra.modules.identity.application.contract.simulation.SimulationManufacturerCharacteristicAuthorizationContract.Operation;
import dz.sh.hidra.modules.identity.application.model.DirectAuthenticationCommand;
import dz.sh.hidra.modules.identity.application.port.in.AuthenticateDirectUserUseCase;
import dz.sh.hidra.modules.identity.domain.value.ProviderType;
import dz.sh.hidra.modules.identity.infrastructure.persistence.adapter.JdbcIdentityAuthorityFence;
import dz.sh.hidra.platform.persistence.ProtectedRelationFence;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.HexFormat;
import java.util.List;
import java.util.UUID;
import org.flywaydb.core.Flyway;
import org.junit.jupiter.api.*;
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

/**
 * Only the password, identities and engineering facts are synthetic. Authentication, native
 * Identity decisions, Party evidence, publication fences, MVC filters and SQL are real.
 * No Mockito replacement of Identity, Party, Workflow or the Simulation use case is permitted.
 * This fixture does not establish commissioning, engine calibration or SITE qualification.
 */
@Testcontainers(disabledWithoutDocker = true)
@SpringBootTest(classes=HidraApplication.class,webEnvironment=SpringBootTest.WebEnvironment.MOCK,
    properties={
        "hidra.platform.security.jwt.hmac-secret=synthetic-completion-test-key-32-bytes-only",
        "hidra.platform.security.jwt.token-issuer=hidra-api",
        "hidra.platform.security.jwt.audience=hidra-api",
        "hidra.simulation.manufacturer-history.cursor-current-key-id=synthetic",
        "hidra.simulation.manufacturer-history.cursor-current-key-base64=AQIDBAUGBwgJCgsMDQ4PEBESExQVFhcYGRobHB0eHyA"
    })
@ActiveProfiles("test")
@org.springframework.test.annotation.DirtiesContext(
    classMode=org.springframework.test.annotation.DirtiesContext.ClassMode.AFTER_CLASS)
public class ManufacturerCharacteristicNativeHttpPostgresIntegrationTest {
    private static final String APP="manufacturer_native_app";
    private static final String PUBLISHER="manufacturer_native_publisher";
    private static final String PASSWORD="synthetic-manufacturer-password";
    private static final String ROOT="/api/v1/simulation/manufacturer-characteristics/";
    private static final String REV="public.hidra_simulation_manufacturer_characteristic_revision";
    private static final String QUAL="public.hidra_simulation_manufacturer_characteristic_qualification";
    private static final String G="public.hidra_identity_user_permission_grant";
    private static final String PERMISSION="public.hidra_identity_permission";
    private static final String SOURCE="public.hidra_party_manufacturer_source_authorization";
    private static final String MODEL="synthetic-model";

    @Container static final PostgreSQLContainer<?> PG=new PostgreSQLContainer<>("postgres:16")
        .withDatabaseName("hidra_manufacturer_native_http_test")
        .withCommand("postgres","-c","autovacuum=off","-c","log_min_error_statement=panic");

    @DynamicPropertySource static void datasource(DynamicPropertyRegistry registry) {
        if(!PG.isRunning())PG.start();
        Flyway.configure().dataSource(PG.getJdbcUrl(),PG.getUsername(),PG.getPassword()).load().migrate();
        var admin=new JdbcTemplate(new DriverManagerDataSource(
            PG.getJdbcUrl(),PG.getUsername(),PG.getPassword()));
        admin.execute("REVOKE CREATE ON SCHEMA public FROM PUBLIC");
        for(String role:List.of(APP,PUBLISHER)){
            admin.execute("CREATE ROLE "+role+" LOGIN PASSWORD '"+PASSWORD+"'");
            admin.execute("GRANT USAGE ON SCHEMA public TO "+role);
            admin.execute("GRANT SELECT ON ALL TABLES IN SCHEMA public TO "+role);
        }
        for(String table:admin.queryForList(
            "SELECT tablename FROM pg_catalog.pg_tables WHERE schemaname='public' AND tablename LIKE 'hidra_identity_%'",
            String.class)){
            if(!table.matches("hidra_identity_[a-z0-9_]+"))
                throw new IllegalStateException("Unexpected native test relation");
            admin.execute("GRANT INSERT,UPDATE,DELETE ON public."+table+" TO "+APP);
        }
        admin.execute("REVOKE INSERT,UPDATE,DELETE ON public.hidra_identity_external_mapping_generation FROM "+APP);
        for(ProtectedRelationFence fence:JdbcIdentityAuthorityFence.requiredRelationFences())
            if(fence.mode()!=ProtectedRelationFence.Mode.ACCESS_SHARE)
                admin.execute("GRANT UPDATE ON "+fence.relation()+" TO "+PUBLISHER);
        for(String table:List.of(REV,QUAL))
            admin.execute("GRANT SELECT,INSERT,UPDATE ON "+table+" TO "+PUBLISHER);
        provisionLock(admin,"manufacturer_party_lock_owner",
            "public.hidra_party_manufacturer_authorization_lock()",
            List.of("hidra_party_party","hidra_party_eligibility_generation","hidra_party_role",
                "hidra_party_role_assignment","hidra_party_manufacturer_profile",
                "hidra_party_manufacturer_source_authorization"));
        provisionLock(admin,"manufacturer_source_lock_owner",
            "public.hidra_simulation_manufacturer_lock_sources()",
            List.of("hidra_simulation_manufacturer_characteristic_revision",
                "hidra_simulation_manufacturer_characteristic_qualification"));
        registry.add("spring.datasource.url",PG::getJdbcUrl);
        registry.add("spring.datasource.username",()->APP);
        registry.add("spring.datasource.password",()->PASSWORD);
        registry.add("spring.flyway.url",PG::getJdbcUrl);
        registry.add("spring.flyway.user",PG::getUsername);
        registry.add("spring.flyway.password",PG::getPassword);
        registry.add("spring.jpa.hibernate.ddl-auto",()->"validate");
        registry.add("spring.flyway.enabled",()->true);
        registry.add("hidra.platform.persistence.publication.enabled",()->true);
        registry.add("hidra.platform.persistence.publication.username",()->PUBLISHER);
        registry.add("hidra.platform.persistence.publication.password",()->PASSWORD);
    }
    private static void provisionLock(JdbcTemplate admin,String role,String function,List<String> relations){
        admin.execute("CREATE ROLE "+role+" NOLOGIN NOINHERIT NOSUPERUSER NOBYPASSRLS NOCREATEROLE NOCREATEDB NOREPLICATION");
        admin.execute("GRANT USAGE,CREATE ON SCHEMA public TO "+role);
        for(String relation:relations)
            admin.execute("GRANT UPDATE ON public."+relation+" TO "+role);
        admin.execute("ALTER FUNCTION "+function+" OWNER TO "+role);
        admin.execute("REVOKE CREATE ON SCHEMA public FROM "+role);
        admin.execute("GRANT EXECUTE ON FUNCTION "+function+" TO "+PUBLISHER);
    }

    @Autowired WebApplicationContext context;
    @Autowired AuthenticateDirectUserUseCase authentication;
    @Autowired PasswordEncoder passwords;
    @Autowired Flyway flyway;
    private JdbcTemplate admin;
    private MockMvc http;
    private String company, manufacturer, source, user, token;

    private static String id(){return UUID.randomUUID().toString();}
    private static String sha(String raw){
        try{return HexFormat.of().formatHex(MessageDigest.getInstance("SHA-256")
            .digest(raw.getBytes(StandardCharsets.UTF_8)));}
        catch(java.security.NoSuchAlgorithmException impossible){throw new AssertionError(impossible);}
    }
    private static String resource(String company,String source,String revision){
        return sha("HIDRA_MANUFACTURER_NATIVE_SCOPE_V1|"+company.length()+":"+company
            +"|"+source.length()+":"+source+"|"
            +(revision==null?"HISTORY":revision.length()+":"+revision));
    }
    private static void insertParty(JdbcTemplate jdbc,String type,String id){
        jdbc.update("INSERT INTO public.hidra_party_party"
            +"(id,code,party_type_id,legal_name,country_code,status,created_at,updated_at)"
            +" VALUES (?,?,?,'Synthetic qualification fixture','DZ','ACTIVE',now(),now())",
            id,id,type);
    }
    @BeforeEach void setup() {
        admin=new JdbcTemplate(new DriverManagerDataSource(PG.getJdbcUrl(),PG.getUsername(),PG.getPassword()));
        flyway.validate();
        assertThat(flyway.info().pending()).isEmpty();
        SecurityContextHolder.clearContext();
        http=MockMvcBuilders.webAppContextSetup(context)
            .apply(org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity())
            .build();
        user=id();company=id();manufacturer=id();source=id();
        String type=id();
        admin.update("INSERT INTO public.hidra_party_type(id,code,status,created_at,updated_at)"
            +" VALUES (?,?,'ACTIVE',now(),now())",type,type);
        insertParty(admin,type,company);insertParty(admin,type,manufacturer);
        List<String> roles=admin.queryForList(
            "SELECT id FROM public.hidra_party_role WHERE code='MANUFACTURER'",String.class);
        String role=roles.isEmpty()?id():roles.getFirst();
        if(roles.isEmpty())admin.update("INSERT INTO public.hidra_party_role"
            +"(id,code,qualification_required_by_default,status,created_at,updated_at)"
            +" VALUES (?,'MANUFACTURER',true,'ACTIVE',now(),now())",role);
        admin.update("INSERT INTO public.hidra_party_role_assignment"
            +"(id,party_id,role_id,valid_from,status,qualification_required,created_at,updated_at)"
            +" VALUES (?,?,?,now()-interval '1 hour','ACTIVE',true,now(),now())",
            id(),manufacturer,role);
        admin.update("INSERT INTO public.hidra_party_manufacturer_profile"
            +"(id,party_id,capability_type,qualification_status,created_at,updated_at)"
            +" VALUES (?,?,'EQUIPMENT','QUALIFIED',now(),now())",id(),manufacturer);
        admin.update("INSERT INTO "+SOURCE
            +"(authorization_id,company_party_id,manufacturer_party_id,manufacturer_model_reference,"
            +"authorization_status,valid_from,generation,created_at,updated_at)"
            +" VALUES (?,?,?,?,'ACTIVE',now()-interval '1 hour',1,now(),now())",
            id(),company,manufacturer,MODEL);
        admin.update("UPDATE public.hidra_identity_provider SET status='INACTIVE' WHERE status='ACTIVE'");
        String provider=id();
        Timestamp now=Timestamp.from(Instant.now());
        admin.update("INSERT INTO public.hidra_identity_user"
            +"(id,username,user_type,status,failed_login_count,created_at,updated_at)"
            +" VALUES (?,?,'HUMAN','ACTIVE',0,?,?)",user,user,now,now);
        admin.update("INSERT INTO public.hidra_identity_provider"
            +"(id,code,name,provider_type,status,sync_enabled,just_in_time_provisioning_enabled,created_at,updated_at)"
            +" VALUES (?,?,'Synthetic local manufacturer','LOCAL','ACTIVE',false,false,?,?)",
            provider,provider,now,now);
        admin.update("INSERT INTO public.hidra_identity_local_credential"
            +"(id,user_id,password_hash,credential_status,created_at,updated_at)"
            +" VALUES (?,?,?,'ACTIVE',?,?)",
            id(),user,passwords.encode("synthetic-password"),now,now);
        token=authentication.authenticate(new DirectAuthenticationCommand(
            ProviderType.LOCAL,user,"synthetic-password","synthetic-ip",
            "synthetic-agent","synthetic-correlation")).accessToken();
        SecurityContextHolder.clearContext();
    }
    @AfterEach void teardown(){SecurityContextHolder.clearContext();}

    private String permission(Operation op){
        String code=SimulationManufacturerCharacteristicAuthorizationContract.permissionCode(op);
        var existing=admin.queryForList("SELECT id FROM "+PERMISSION+" WHERE code=?",String.class,code);
        if(!existing.isEmpty())return existing.getFirst();
        String id=id();
        admin.update("INSERT INTO "+PERMISSION
            +"(id,code,permission_domain,resource_type,action,sensitive,status,created_at,updated_at)"
            +" VALUES (?,?,'simulation',?,?,false,'ACTIVE',now(),now())",
            id,code,SimulationManufacturerCharacteristicAuthorizationContract.RESOURCE_TYPE,
            op.name().toLowerCase(java.util.Locale.ROOT));
        return id;
    }
    private void grant(Operation op,String revision,String effect,String scoped){
        String pid=permission(op);
        String ref=scoped.equals("company")?"simulation-company:"+company
            :"simulation-source:"+resource(company,source,revision);
        admin.update("INSERT INTO "+G
            +"(id,user_id,permission_id,effect,scope_type,scope_reference_id,grant_reason,"
            +"emergency_access,valid_from,valid_to,status,created_at)"
            +" VALUES (?,?,?,?,'CUSTOM',?,'synthetic native qualification',false,"
            +"now()-interval '1 hour',now()+interval '1 hour','ACTIVE',now())",
            id(),user,pid,effect,ref);
    }
    private void allow(Operation op,String revision){
        grant(op,revision,"GRANT","company");grant(op,revision,"GRANT","source");
    }
    private String body(String revision){
        return """
          {"companyPartyId":"%s","revision":{
            "sourceId":"%s","revisionId":"%s","recordedAt":"2026-10-01T10:00:00Z",
            "effectiveFrom":"2026-10-01T10:00:00Z","origin":"SYNTHETIC",
            "manufacturerPartyId":"%s","manufacturerModelReference":"synthetic-model",
            "documentReference":"synthetic-OEM-bounds","documentSha256":"%s",
            "referenceConditions":{"pressurePascalsAbsolute":180000.00,
              "temperatureKelvin":298.15,"methodReference":"synthetic conditions"},
            "family":{"kind":"OPERATING_LIMITS","limits":[{
              "axis":{"quantity":"MASS_FLOW","unit":"KILOGRAM_PER_SECOND"},
              "minimumInclusive":0.00,"maximumInclusive":2.00,
              "evidenceReference":"synthetic bounds"}]}}}
          """.formatted(company,source,revision,manufacturer,"a".repeat(64));
    }
    private org.springframework.test.web.servlet.ResultActions http(
            String method,String path,String payload,String bearer)throws Exception{
        SecurityContextHolder.clearContext();
        var request=switch(method){
            case "PUT"->put(ROOT+path);
            case "POST"->post(ROOT+path);
            default->get(ROOT+path);
        };
        if(bearer!=null)request.header("Authorization","Bearer "+bearer);
        if(payload!=null)request.contentType(MediaType.APPLICATION_JSON).content(payload);
        else request.param("companyPartyId",company);
        return http.perform(request);
    }
    private String path(String revision){return source+"/revisions/"+revision;}
    private long count(String table){return admin.queryForObject("SELECT count(*) FROM "+table
        +" WHERE company_party_id=? AND source_id=?",Long.class,company,source);}
    private void save(String revision)throws Exception{
        allow(Operation.SAVE,revision);
        http("PUT",path(revision),body(revision),token).andExpect(status().isOk())
            .andExpect(jsonPath("$.sourceId").value(source))
            .andExpect(jsonPath("$.revisionId").value(revision));
    }
    @Test void realLocalBearerPersistsCanonicalSourceAndReadsThroughConfiguredFilters()throws Exception{
        String revision="v1";save(revision);
        var row=admin.queryForMap("SELECT creator_user_id,operation_binding_id,session_id,"
            +"backend_pid,transaction_id,database_role,payload_sha256 FROM "+REV
            +" WHERE company_party_id=? AND source_id=? AND revision_id=?",company,source,revision);
        assertThat(row.get("creator_user_id")).isEqualTo(user);
        assertThat((String)row.get("operation_binding_id")).isNotBlank();
        assertThat((String)row.get("session_id")).isNotBlank();
        assertThat(((Number)row.get("backend_pid")).intValue()).isPositive();
        assertThat(((Number)row.get("transaction_id")).longValue()).isPositive();
        assertThat(row.get("database_role")).isEqualTo(PUBLISHER);
        assertThat((String)row.get("payload_sha256")).matches("[0-9a-f]{64}");
        allow(Operation.READ_REVISION,revision);
        http("GET",path(revision),null,token).andExpect(status().isOk())
            .andExpect(jsonPath("$.source.certificate").doesNotExist());
        assertThat(count(REV)).isEqualTo(1);
        assertThat(count(QUAL)).isZero();
    }
    @Test void absentAndMalformedBearerCannotReadOrWriteSource()throws Exception{
        http("PUT",path("v1"),body("v1"),null).andExpect(status().isUnauthorized());
        http("GET",path("v1"),null,"invalid.bearer.value").andExpect(status().isUnauthorized());
        http("GET",source+"/history",null,null).andExpect(status().isUnauthorized());
        http("GET",path("v1")+"/certificate",null,null).andExpect(status().isUnauthorized());
        http("POST",path("v1")+"/qualification","{}",null).andExpect(status().isUnauthorized());
        assertThat(count(REV)).isZero();assertThat(count(QUAL)).isZero();
    }
    @Test void missingSourceScopeAndExplicitDenyPrecedeExistenceAndReplay()throws Exception{
        grant(Operation.SAVE,"v1","GRANT","company");
        http("PUT",path("v1"),body("v1"),token).andExpect(status().isForbidden())
            .andExpect(jsonPath("$.code").value("DENIED"));
        assertThat(count(REV)).isZero();
        grant(Operation.SAVE,"v1","GRANT","source");
        grant(Operation.SAVE,"v1","DENY","source");
        http("PUT",path("v1"),body("v1"),token).andExpect(status().isForbidden());
        assertThat(count(REV)).isZero();
    }
    @Test void replayAfterSessionRevocationCannotMutatePreviouslyCommittedSource()throws Exception{
        save("v1");
        admin.update("UPDATE public.hidra_identity_login_session"
            +" SET status='REVOKED',ended_at=clock_timestamp() WHERE user_id=? AND status='ACTIVE'",user);
        http("PUT",path("v1"),body("v1"),token).andExpect(status().isForbidden());
        assertThat(count(REV)).isEqualTo(1);assertThat(count(QUAL)).isZero();
    }
    @Test void historyCursorRejectsTamperingAndQualificationCannotInventWorkflow()throws Exception{
        save("v1");save("v2");
        allow(Operation.LIST_HISTORY,null);
        var page=http("GET",source+"/history",null,token).andExpect(status().isOk())
            .andExpect(jsonPath("$.history").isArray())
            .andReturn().getResponse().getContentAsString();
        assertThat(page).doesNotContain("totalCount","issuerReference");
        http.perform(get(ROOT+source+"/history").param("companyPartyId",company)
            .param("cursor","invalid").header("Authorization","Bearer "+token))
            .andExpect(status().isForbidden());
        allow(Operation.QUALIFY,"v1");
        http("POST",path("v1")+"/qualification",
            "{\"companyPartyId\":\""+company+"\",\"approval\":{"
            +"\"definitionId\":\"unrelated\",\"definitionVersion\":1,"
            +"\"targetTypeId\":\"manufacturer-revision\",\"purposeId\":\"QUALIFY\","
            +"\"instanceId\":\"nonexistent\",\"taskId\":\"nonexistent\","
            +"\"actionId\":\"nonexistent\"}}",token)
            .andExpect(status().isForbidden());
        assertThat(count(REV)).isEqualTo(2);assertThat(count(QUAL)).isZero();
    }

    @Test void certificateRequiresItsOwnCurrentPermissionEvenAfterRevisionRead()throws Exception {
        save("v1");allow(Operation.READ_REVISION,"v1");
        http("GET",path("v1"),null,token).andExpect(status().isOk())
            .andExpect(jsonPath("$.source.certificate").doesNotExist());
        http("GET",path("v1")+"/certificate",null,token)
            .andExpect(status().isForbidden()).andExpect(jsonPath("$.code").value("DENIED"));
        assertThat(count(REV)).isEqualTo(1);assertThat(count(QUAL)).isZero();
    }

    @Test void currentPartySuspensionBlocksPreviouslyAuthorizedRevision()throws Exception {
        save("v1");allow(Operation.READ_REVISION,"v1");
        http("GET",path("v1"),null,token).andExpect(status().isOk());
        admin.update("UPDATE public.hidra_party_manufacturer_profile"
            +" SET qualification_status='SUSPENDED',updated_at=clock_timestamp() WHERE party_id=?",
            manufacturer);
        http("GET",path("v1"),null,token).andExpect(status().isForbidden())
            .andExpect(jsonPath("$.code").value("DENIED"));
        assertThat(count(REV)).isEqualTo(1);
    }

    @Test void immutableReplayOfSameIssuerTokenDoesNotAppendAnotherRevision()throws Exception {
        save("v1");
        http("PUT",path("v1"),body("v1"),token).andExpect(status().isOk());
        assertThat(count(REV)).isEqualTo(1);assertThat(count(QUAL)).isZero();
    }

    @Test void foreignSourceAndWrongHttpVerbDoNotInheritNativeAuthority()throws Exception {
        allow(Operation.READ_REVISION,"v1");
        http("GET",path("v1"),null,token).andExpect(status().isForbidden());
        assertThat(count(REV)).isZero();
        var wrong=http.perform(post(ROOT+path("v1")).header("Authorization","Bearer "+token)
            .contentType(MediaType.APPLICATION_JSON).content(body("v1")))
            .andReturn().getResponse();
        assertThat(wrong.getStatus()).isNotEqualTo(200);
        assertThat(count(REV)).isZero();
    }
}
