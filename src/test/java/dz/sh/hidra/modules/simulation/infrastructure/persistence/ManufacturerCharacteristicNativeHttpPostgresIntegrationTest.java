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
import org.springframework.test.context.bean.override.mockito.MockitoSpyBean;
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
        "hidra.simulation.manufacturer-history.cursor-current-key-base64=AQIDBAUGBwgJCgsMDQ4PEBESExQVFhcYGRobHB0eHyA",
        "hidra.simulation.manufacturer-history.cursor-previous-key-id=synthetic-former",
        "hidra.simulation.manufacturer-history.cursor-previous-key-base64=BwcHBwcHBwcHBwcHBwcHBwcHBwcHBwcHBwcHBwcHBwc",
        "hidra.platform.security.ldap.enabled=true",
        "hidra.platform.security.ldap.url=ldap://synthetic.invalid:389",
        "hidra.platform.security.ldap.base-dn=dc=synthetic,dc=invalid",
        "hidra.platform.security.ldap.user-search-base=ou=people",
        "hidra.platform.security.ldap.user-search-filter=(uid={0})"
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
        // Genuine Workflow SHARE NOWAIT fences require table UPDATE privilege for the
        // disposable publisher role. These test-only grants are never deployment policy.
        for(String table:List.of("hidra_workflow_action","hidra_workflow_definition",
                "hidra_workflow_definition_target_binding","hidra_workflow_instance",
                "hidra_workflow_state_history","hidra_workflow_step",
                "hidra_workflow_task","hidra_workflow_transition","hidra_workflow_type_catalog"))
            admin.execute("GRANT UPDATE ON public."+table+" TO "+PUBLISHER);
        // The real Party owner publishes SHARE_ROW_EXCLUSIVE final relation fences.
        // PostgreSQL requires a table-level UPDATE privilege for that lock strength;
        // the test-only publication role previously had SELECT and could never
        // complete an otherwise valid manufacturer save. This grant is confined to
        // this disposable Testcontainers catalog; production grants are unchanged.
        for(String table:List.of("hidra_party_party","hidra_party_eligibility_generation",
                "hidra_party_role","hidra_party_role_assignment",
                "hidra_party_manufacturer_profile",
                "hidra_party_manufacturer_source_authorization"))
            admin.execute("GRANT UPDATE ON public."+table+" TO "+PUBLISHER);
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
    @Autowired @org.springframework.beans.factory.annotation.Qualifier("hidraJwtDecoder")
    org.springframework.security.oauth2.jwt.JwtDecoder nativeJwtDecoder;
    @Autowired dz.sh.hidra.modules.identity.application.port.in.CompleteAuthenticatedPrincipalUseCase completion;
    @Autowired dz.sh.hidra.modules.identity.infrastructure.security.IdentityOidcJwtAuthenticationConverter oidc;
    @MockitoSpyBean dz.sh.hidra.modules.identity.infrastructure.security.LdapCredentialVerificationAdapter ldapVerification;
    @org.springframework.test.context.bean.override.mockito.MockitoBean(name="externalOidcJwtDecoder")
    org.springframework.security.oauth2.jwt.JwtDecoder externalOidcDecoder;

    // Real owner methods execute as usual. Spies observe stage *names* only;
    // they do not stub native Identity, Party, Workflow or persistence.
    @MockitoSpyBean dz.sh.hidra.modules.simulation.application.service.ManufacturerCharacteristicRevisionApplicationService apiOwner;
    @MockitoSpyBean dz.sh.hidra.modules.simulation.application.service.ProtectedManufacturerCharacteristicAdmissionCoordinator coordinator;
    @MockitoSpyBean dz.sh.hidra.modules.identity.infrastructure.security.ProtectedSimulationManufacturerCharacteristicAuthorizationParticipant nativeIdentity;
    @MockitoSpyBean dz.sh.hidra.modules.party.infrastructure.persistence.adapter.JdbcSimulationManufacturerEvidenceAdapter nativeParty;
    @MockitoSpyBean dz.sh.hidra.modules.simulation.infrastructure.persistence.adapter.JdbcManufacturerCharacteristicRevisionRepositoryAdapter nativeSource;
    @MockitoSpyBean org.springframework.transaction.PlatformTransactionManager transactionManager;
    @Autowired dz.sh.hidra.platform.configuration.ProtectedExecutionBudgetProperties workBudgets;
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
        var result=http.perform(request);
        // Only failed private CI requests: report class/method provenance, never
        // token, resource identity, SQL details, claim values or exception message.
        if(result.andReturn().getResponse().getStatus()==503) {
            Throwable observed=result.andReturn().getResolvedException();
            Throwable cause=observed==null?null:observed.getCause();
            String outcome=observed instanceof
                dz.sh.hidra.platform.persistence.ProtectedTransactionExecutor.ExecutionFailure failure
                    ?failure.outcome().name():"not-execution-failure";
            String origin=cause==null||cause.getStackTrace().length==0?"none":
                cause.getStackTrace()[0].getClassName()+"#"
                    +cause.getStackTrace()[0].getMethodName();
            System.err.println("HAPI601 PRIVATE CI 503 method="+method+" outcome="+outcome
                +" resolved="+(observed==null?"none":observed.getClass().getSimpleName())
                +" cause="+(cause==null?"none":cause.getClass().getSimpleName())
                +" sourceMethod="+origin);
            System.err.println("HAPI601 PRIVATE CI 503 service="+methodNames(apiOwner)
                +" coordinator="+methodNames(coordinator)
                +" identity="+methodNames(nativeIdentity));
            System.err.println("HAPI601 PRIVATE CI 503 party="+methodNames(nativeParty)
                +" persistence="+methodNames(nativeSource));
        }
        return result;
    }
    private String path(String revision){return source+"/revisions/"+revision;}
    private long count(String table){return admin.queryForObject("SELECT count(*) FROM "+table
        +" WHERE company_party_id=? AND source_id=?",Long.class,company,source);}
    private static String methodNames(Object spy) {
        // Diagnostic whitelist: never log private parties, IDs, source payloads,
        // native grants, credentials, request bodies or Java exception messages.
        return org.mockito.Mockito.mockingDetails(spy).getInvocations().stream()
            .map(i->i.getMethod().getName()).distinct().sorted().toList().toString();
    }

    private void save(String revision)throws Exception{
        allow(Operation.SAVE,revision);
        var performed=http("PUT",path(revision),body(revision),token);
        var actual=performed.andReturn();
        if(actual.getResponse().getStatus()!=200) {
            String resolved=actual.getResolvedException()==null?"none":
                actual.getResolvedException().getClass().getSimpleName();
            String response=actual.getResponse().getContentAsString();
            String code=response.contains("\"code\":\"DENIED\"")?"DENIED":"not-DENIED";
            System.err.println("HAPI601 PRIVATE CI STAGES status="+actual.getResponse().getStatus()
                +" code="+code+" resolvedClass="+resolved);
            System.err.println("HAPI601 PRIVATE CI STAGES service="+methodNames(apiOwner)
                +" coordinator="+methodNames(coordinator));
            System.err.println("HAPI601 PRIVATE CI STAGES identity="+methodNames(nativeIdentity)
                +" party="+methodNames(nativeParty)+" persistence="+methodNames(nativeSource));
        }
        performed.andExpect(status().isOk())
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

    /**
     * A genuine persisted terminal Workflow is required by the configured manufacturer
     * QUALIFY route. The synthetic schema-owner fixture cannot manufacture operator authority.
     */
    private record ManufacturerTerminalWorkflow(String definition,String type,String purpose,
                                                String instance,String task,String action) {}
    private ManufacturerTerminalWorkflow terminalManufacturerWorkflow(String revision) {
        String suffix=id(),reviewer=id(),definition=id(),type=id(),purpose=id();
        String start=id(),end=id(),instance=id(),task=id(),action=id();
        Timestamp at=Timestamp.from(Instant.now().minusSeconds(1)
            .truncatedTo(java.time.temporal.ChronoUnit.MICROS));
        String sha=admin.queryForObject("SELECT payload_sha256 FROM "+REV
                +" WHERE company_party_id=? AND source_id=? AND revision_id=?",
                String.class,company,source,revision);
        String target=dz.sh.hidra.modules.simulation.application.service
            .ProtectedManufacturerCharacteristicAdmissionCoordinator.targetDigestStatic(
                new dz.sh.hidra.modules.simulation.application.port.out
                    .ManufacturerCharacteristicRevisionRepositoryPort.Key(company,source,revision),sha);
        admin.update("INSERT INTO public.hidra_identity_user"
                +"(id,username,display_name,user_type,status,failed_login_count,created_at,activated_at,updated_at)"
                +" VALUES (?,?,'Synthetic Manufacturer Reviewer','HUMAN','ACTIVE',0,now(),now(),now())",
                reviewer,reviewer);
        for(String[] row:List.of(new String[]{type,"WORKFLOW_TARGET_TYPE","MANUFACTURER_CHARACTERISTIC_REVISION"},
                new String[]{purpose,"WORKFLOW_PURPOSE","MANUFACTURER_CHARACTERISTIC_QUALIFICATION"}))
            admin.update("INSERT INTO public.hidra_workflow_type_catalog"
                +"(id,catalog_name,code,active,sort_order,system_defined,created_at,updated_at)"
                +" VALUES (?,?,?,true,0,false,now(),now())",row[0],row[1],row[2]);
        admin.update("INSERT INTO public.hidra_workflow_definition"
            +"(id,code,name_fr,type_id,status,version,created_at,updated_at)"
            +" VALUES (?,?,'Synthetic manufacturer qualification',?,'ACTIVE',1,now(),now())",
            definition,definition,type);
        for(Object[] step:List.<Object[]>of(new Object[]{start,1},new Object[]{end,2}))
            admin.update("INSERT INTO public.hidra_workflow_step"
                +"(id,definition_id,code,name_fr,step_order,mandatory,allow_claim,"
                +"allow_delegation,allow_escalation,created_at,updated_at)"
                +" VALUES (?,?,?,'Synthetic review',?,true,true,false,false,now(),now())",
                step[0],definition,step[0],step[1]);
        String binding=id();
        admin.update("INSERT INTO public.hidra_workflow_definition_target_binding"
            +"(id,definition_id,target_module,target_type_id,workflow_purpose_id,"
            +"active,created_at,updated_at) VALUES (?,?,'simulation',?,?,true,now(),now())",
            binding,definition,type,purpose);
        admin.update("INSERT INTO public.hidra_workflow_instance"
            +"(id,definition_id,definition_version,workflow_purpose_id,target_module,target_type_id,"
            +"target_id,status,current_step_id,started_by_actor_id,started_by_display_name_snapshot,"
            +"started_at,completed_at,created_at,updated_at)"
            +" VALUES (?,?,1,?,'simulation',?,?,'COMPLETED',?,?,'Synthetic Reviewer',?,?,?,now())",
            instance,definition,purpose,type,target,end,reviewer,
            Timestamp.from(at.toInstant().minusSeconds(10)),at,
            Timestamp.from(at.toInstant().minusSeconds(10)));
        admin.update("INSERT INTO public.hidra_workflow_task"
            +"(id,instance_id,step_id,status,assigned_actor_id,completed_by_actor_id,"
            +"completed_at,created_at,updated_at)"
            +" VALUES (?,?,?,'APPROVED',?,?,?, ?,now())",
            task,instance,start,reviewer,reviewer,at,
            Timestamp.from(at.toInstant().minusSeconds(10)));
        admin.update("INSERT INTO public.hidra_workflow_action"
            +"(id,instance_id,task_id,action_type,decision,actor_id,"
            +"actor_display_name_snapshot,action_sequence,source_system,acted_at)"
            +" VALUES (?,?,?,'APPROVE','APPROVE',?,'Synthetic Reviewer',1,'HIDRA_API',?)",
            action,instance,task,reviewer,at);
        return new ManufacturerTerminalWorkflow(definition,type,purpose,instance,task,action);
    }

    private String qualificationBody(ManufacturerTerminalWorkflow w) {
        return "{\"companyPartyId\":\""+company+"\",\"approval\":{"
            +"\"definitionId\":\""+w.definition()+"\",\"definitionVersion\":1,"
            +"\"targetTypeId\":\""+w.type()+"\",\"purposeId\":\""+w.purpose()+"\","
            +"\"instanceId\":\""+w.instance()+"\",\"taskId\":\""+w.task()+"\","
            +"\"actionId\":\""+w.action()+"\"}}";
    }

    @Test void nativeManufacturerQualificationRequiresRealTerminalWorkflowAndReplaysOnce()throws Exception{
        save("v1");
        allow(Operation.QUALIFY,"v1");
        var w=terminalManufacturerWorkflow("v1");
        String payload=qualificationBody(w);
        http("POST",path("v1")+"/qualification",payload,token)
            .andExpect(status().isOk()).andExpect(jsonPath("$.workflowQualified").value(true));
        http("POST",path("v1")+"/qualification",payload,token)
            .andExpect(status().isOk()).andExpect(jsonPath("$.workflowQualified").value(true));
        assertThat(count(QUAL)).isEqualTo(1);
        admin.update("UPDATE public.hidra_workflow_definition_target_binding"
                +" SET active=false WHERE definition_id=?",w.definition());
        http("POST",path("v1")+"/qualification",payload,token)
            .andExpect(status().isForbidden()).andExpect(jsonPath("$.code").value("DENIED"));
        assertThat(count(REV)).isEqualTo(1);
        assertThat(count(QUAL)).isEqualTo(1);
    }

    @Test void supplierDeclaredCertificateRemainsDocumentaryAndNeedsSeparateCurrentGrant()throws Exception{
        String revision="certificate-v1";
        String certificate="\"certificate\":{\"issuerReference\":\"synthetic-issuer\","
            +"\"certificateReference\":\"unverified-supplier-document\","
            +"\"certificateSha256\":\""+ "b".repeat(64) +"\","
            +"\"sourceId\":\""+source+"\",\"revisionId\":\""+revision+"\","
            +"\"sourceDocumentSha256\":\""+ "a".repeat(64) +"\","
            +"\"manufacturerPartyId\":\""+manufacturer+"\","
            +"\"manufacturerModelReference\":\""+MODEL+"\","
            +"\"characteristicKind\":\"OPERATING_LIMITS\","
            +"\"validFrom\":\"2026-10-01T10:00:00Z\"},";
        String supplied=body(revision).replace("\"origin\":\"SYNTHETIC\"",
                "\"origin\":\"SUPPLIER_DECLARED\"").replace("\"family\":",certificate+"\"family\":");
        allow(Operation.SAVE,revision);
        http("PUT",path(revision),supplied,token).andExpect(status().isOk());
        allow(Operation.READ_REVISION,revision);
        http("GET",path(revision),null,token).andExpect(status().isOk())
            .andExpect(jsonPath("$.source.certificate").doesNotExist());
        http("GET",path(revision)+"/certificate",null,token)
            .andExpect(status().isForbidden());
        allow(Operation.READ_CERTIFICATE,revision);
        http("GET",path(revision)+"/certificate",null,token).andExpect(status().isOk())
            .andExpect(jsonPath("$.certificate.issuerReference").value("synthetic-issuer"))
            .andExpect(jsonPath("$.certificate.certificateReference")
                .value("unverified-supplier-document"));
        grant(Operation.READ_CERTIFICATE,revision,"DENY","source");
        http("GET",path(revision)+"/certificate",null,token)
            .andExpect(status().isForbidden());
        assertThat(count(REV)).isEqualTo(1);
        assertThat(count(QUAL)).isZero();
    }

    @Test void nativePagedManufacturerHistoryRechecksCurrentAuthorityForEveryContinuation()throws Exception{
        save("v1");save("v2");save("v3");allow(Operation.LIST_HISTORY,null);
        String first=http.perform(get(ROOT+source+"/history").param("companyPartyId",company)
            .param("size","1").header("Authorization","Bearer "+token))
            .andExpect(status().isOk()).andExpect(jsonPath("$.history.length()").value(1))
            .andReturn().getResponse().getContentAsString();
        var json=new com.fasterxml.jackson.databind.ObjectMapper().readTree(first);
        String cursor=json.path("nextCursor").asText();
        assertThat(cursor).isNotBlank();
        assertThat(first).doesNotContain("totalCount","manufacturerPartyId","issuerReference");
        http.perform(get(ROOT+source+"/history").param("companyPartyId",company)
            .param("size","1").param("cursor",cursor).header("Authorization","Bearer "+token))
            .andExpect(status().isOk()).andExpect(jsonPath("$.history.length()").value(1));
        http.perform(get(ROOT+source+"/history").param("companyPartyId",company)
            .param("size","2").param("cursor",cursor).header("Authorization","Bearer "+token))
            .andExpect(status().isForbidden());
        grant(Operation.LIST_HISTORY,null,"DENY","source");
        http.perform(get(ROOT+source+"/history").param("companyPartyId",company)
            .param("size","1").param("cursor",cursor).header("Authorization","Bearer "+token))
            .andExpect(status().isForbidden()).andExpect(jsonPath("$.code").value("DENIED"));
        assertThat(count(REV)).isEqualTo(3);
    }

    @Test void divergentImmutableReplayAndMalformedSupplierAxisCannotModifyStoredRevision()throws Exception{
        save("v1");
        String old=admin.queryForObject("SELECT payload_sha256 FROM "+REV
            +" WHERE company_party_id=? AND source_id=? AND revision_id=?",
            String.class,company,source,"v1");
        String changed=body("v1").replace("\"maximumInclusive\":2.00",
            "\"maximumInclusive\":3.00");
        assertThat(changed).isNotEqualTo(body("v1"));
        http("PUT",path("v1"),changed,token).andExpect(status().isForbidden());
        String malformed=body("v1").replace("\"unit\":\"KILOGRAM_PER_SECOND\"",
            "\"unit\":\"UNSUPPORTED_UNIT\"");
        assertThat(malformed).isNotEqualTo(body("v1"));
        http("PUT",path("v1"),malformed,token).andExpect(status().isBadRequest());
        assertThat(count(REV)).isEqualTo(1);
        assertThat(admin.queryForObject("SELECT payload_sha256 FROM "+REV
            +" WHERE company_party_id=? AND source_id=? AND revision_id=?",
            String.class,company,source,"v1")).isEqualTo(old);
        assertThat(count(QUAL)).isZero();
    }

    /** Schema-owner synthetic bulk fixture; the native HTTP route must authenticate all
     * source principals, including those beyond the page boundary. This helper is not a
     * bypass for a real publisher or a claim of authorized vendor data. */
    private void insertCanonicalHistory(int rows, boolean distinctModels) {
        var datasource=new DriverManagerDataSource(PG.getJdbcUrl(),PG.getUsername(),PG.getPassword());
        var jdbc=new JdbcTemplate(datasource);
        var manager=new org.springframework.jdbc.datasource.DataSourceTransactionManager(datasource);
        var tx=new org.springframework.transaction.support.TransactionTemplate(manager);
        tx.setIsolationLevel(org.springframework.transaction.TransactionDefinition.ISOLATION_READ_COMMITTED);
        tx.execute(status->{
            int pid=jdbc.queryForObject("SELECT pg_backend_pid()",Integer.class);
            long xid=jdbc.queryForObject("SELECT txid_current()",Long.class);
            String dbRole=jdbc.queryForObject("SELECT current_user",String.class);
            var codec=new dz.sh.hidra.modules.simulation.infrastructure.persistence.adapter
                .ManufacturerCharacteristicRevisionCodec();
            Instant recorded=Instant.parse("2026-10-01T10:00:00Z");
            for(int i=0;i<rows;i++){
                String revision="history-"+String.format(java.util.Locale.ROOT,"%04d",i);
                String model=distinctModels?"synthetic-model-"+i:MODEL;
                var sourceRevision=new dz.sh.hidra.modules.simulation.domain.model
                    .ManufacturerCharacteristicRevision(
                        source,revision,recorded,recorded,null,
                        dz.sh.hidra.modules.simulation.domain.model
                            .ManufacturerCharacteristicRevision.Origin.SYNTHETIC,
                        manufacturer,model,"synthetic-history-evidence","a".repeat(64),
                        new dz.sh.hidra.modules.simulation.domain.model
                            .ManufacturerCharacteristicRevision.ReferenceConditions(
                                new java.math.BigDecimal("180000.00"),
                                new java.math.BigDecimal("298.15"),"synthetic history conditions"),
                        null,new dz.sh.hidra.modules.simulation.domain.model
                            .ManufacturerCharacteristicRevision.OperatingLimits(List.of(
                                new dz.sh.hidra.modules.simulation.domain.model
                                    .ManufacturerCharacteristicRevision.Limit(
                                        new dz.sh.hidra.modules.simulation.domain.model
                                            .ManufacturerCharacteristicRevision.Axis(
                                                dz.sh.hidra.modules.simulation.domain.model
                                                    .ManufacturerCharacteristicRevision.Quantity.MASS_FLOW,
                                                dz.sh.hidra.modules.simulation.domain.model
                                                    .ManufacturerCharacteristicRevision.Unit.KILOGRAM_PER_SECOND),
                                        new java.math.BigDecimal("0.00"),
                                        new java.math.BigDecimal("2.00"),"synthetic history bounds"))));
                byte[] payload=codec.encode(sourceRevision);
                String payloadSha=codec.sha256(payload);
                String target=dz.sh.hidra.modules.simulation.application.service
                    .ProtectedManufacturerCharacteristicAdmissionCoordinator.targetDigestStatic(
                        new dz.sh.hidra.modules.simulation.application.port.out
                            .ManufacturerCharacteristicRevisionRepositoryPort.Key(company,source,revision),
                        payloadSha);
                jdbc.update("INSERT INTO "+REV
                    +" (company_party_id,source_id,revision_id,manufacturer_party_id,"
                    +"manufacturer_model_reference,payload_format,canonical_payload,payload_sha256,"
                    +"workflow_target_digest,document_sha256,source_recorded_at,effective_from,source_origin,"
                    +"creator_user_id,operation_binding_id,session_id,backend_pid,transaction_id,database_role,inserted_at)"
                    +" VALUES (?,?,?,?,?,?,?,?,?,?,?,?,'SYNTHETIC',?,?,?,?,?,?,?)",
                    company,source,revision,manufacturer,model,
                    "HIDRA_MANUFACTURER_CHARACTERISTIC_SOURCE_V1",payload,payloadSha,target,
                    "a".repeat(64),Timestamp.from(recorded),Timestamp.from(recorded),
                    user,"synthetic-history-binding","synthetic-history-session",pid,xid,dbRole,
                    Timestamp.from(Instant.now()));
            }
            return null;
        });
    }
    @Test void nativeHttpHistoryChecksAll265RowsAndFailsClosedAtSixtyFifthPrincipal()throws Exception{
        insertCanonicalHistory(265,false);
        allow(Operation.LIST_HISTORY,null);
        String current=http.perform(get(ROOT+source+"/history")
                .param("companyPartyId",company).param("size","5")
                .header("Authorization","Bearer "+token))
            .andExpect(status().isOk()).andExpect(jsonPath("$.history.length()").value(5))
            .andReturn().getResponse().getContentAsString();
        var json=new com.fasterxml.jackson.databind.ObjectMapper().readTree(current);
        assertThat(json.path("history").get(0).path("revisionId").asText())
            .isEqualTo("history-0000");
        assertThat(json.path("nextCursor").asText()).isNotBlank();
        assertThat(current).doesNotContain("totalCount","manufacturerPartyId");
        assertThat(count(REV)).isEqualTo(265);
        // This is a distinct source so a small page may not hide 65 forbidden principals.
        source=id();
        insertCanonicalHistory(65,true);
        allow(Operation.LIST_HISTORY,null);
        http.perform(get(ROOT+source+"/history")
            .param("companyPartyId",company).param("size","1")
            .header("Authorization","Bearer "+token))
            .andExpect(status().isForbidden()).andExpect(jsonPath("$.code").value("DENIED"));
        assertThat(count(REV)).isEqualTo(65);
    }

    /** Only an external transport is synthetic. Identity's verified external completion,
     * producer binding, native issuer, Party grants, original transaction and HTTP stack
     * retain their real configured implementations. No enterprise IdP is contacted. */
    private String externalProvider(String type,String subject) {
        admin.update("UPDATE public.hidra_identity_provider"
            +" SET status='INACTIVE' WHERE status='ACTIVE'");
        String provider=id(),link=id();
        Timestamp now=Timestamp.from(Instant.now());
        admin.update("INSERT INTO public.hidra_identity_provider"
            +"(id,code,name,provider_type,issuer_uri,directory_base_dn,user_search_base,"
            +"status,sync_enabled,just_in_time_provisioning_enabled,created_at,updated_at)"
            +" VALUES (?,?,'Synthetic original-source provider',?,?,?,?,'ACTIVE',false,false,?,?)",
            provider,provider,type,type.equals("OIDC")?"https://oidc.synthetic.invalid":null,
            type.equals("OIDC")?null:"dc=synthetic,dc=invalid",
            type.equals("OIDC")?null:"ou=people",now,now);
        admin.update("INSERT INTO public.hidra_identity_external_identity"
            +"(id,user_id,identity_provider_id,external_subject,status,created_at,updated_at)"
            +" VALUES (?,?,?,?,'LINKED',?,?)",link,user,provider,subject,now,now);
        return provider;
    }
    private static final String SYNTHETIC_SOURCE_SECRET =
        "synthetic-completion-test-key-32-bytes-only";
    private String signedOriginalOidcBearer(String subject) {
        var secret=new javax.crypto.spec.SecretKeySpec(
            SYNTHETIC_SOURCE_SECRET.getBytes(StandardCharsets.UTF_8),"HmacSHA256");
        var encoder=org.springframework.security.oauth2.jwt.NimbusJwtEncoder
            .withSecretKey(secret)
            .algorithm(org.springframework.security.oauth2.jose.jws.MacAlgorithm.HS256).build();
        var decoder=org.springframework.security.oauth2.jwt.NimbusJwtDecoder
            .withSecretKey(secret)
            .macAlgorithm(org.springframework.security.oauth2.jose.jws.MacAlgorithm.HS256).build();
        org.mockito.Mockito.when(externalOidcDecoder.decode(
            org.mockito.ArgumentMatchers.anyString())).thenAnswer(
                i->decoder.decode(i.getArgument(0)));
        var jwt=encoder.encode(org.springframework.security.oauth2.jwt.JwtEncoderParameters.from(
            org.springframework.security.oauth2.jwt.JwtClaimsSet.builder()
                .issuer("https://oidc.synthetic.invalid").subject(subject)
                .audience(List.of("hidra-api"))
                .issuedAt(Instant.now().minusSeconds(1))
                .expiresAt(Instant.now().plusSeconds(300)
                    .truncatedTo(java.time.temporal.ChronoUnit.SECONDS))
                .build()));
        var authentication=oidc.convert(decoder.decode(jwt.getTokenValue()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        try {
            var principal=(dz.sh.hidra.modules.identity.domain.model.HidraPrincipal)
                authentication.getPrincipal();
            return completion.complete(
                new dz.sh.hidra.modules.identity.application.model.AuthenticatedPrincipalInput(
                    principal.userId(),principal.username(),principal.displayName(),
                    principal.authenticationType(),principal.identityProviderId(),
                    principal.roles(),principal.permissions(),principal.externalIdentityId(),null),
                "synthetic-ip","synthetic-agent","synthetic-correlation").accessToken();
        } finally {SecurityContextHolder.clearContext();}
    }
    @Test void originalSignedOidcAuthenticationCompletesToNativeManufacturerHttpWithRevocation()throws Exception{
        String provider=externalProvider("OIDC","synthetic-oidc-subject-"+id());
        String subject=admin.queryForObject("SELECT external_subject FROM public.hidra_identity_external_identity"
            +" WHERE user_id=? AND identity_provider_id=?",String.class,user,provider);
        String externalToken=signedOriginalOidcBearer(subject);
        assertNativeBinding(externalToken,"OIDC");
        allow(Operation.SAVE,"oidc-v1");
        http("PUT",path("oidc-v1"),body("oidc-v1"),externalToken)
            .andExpect(status().isOk());
        allow(Operation.READ_REVISION,"oidc-v1");
        http("GET",path("oidc-v1"),null,externalToken)
            .andExpect(status().isOk()).andExpect(jsonPath("$.source.certificate").doesNotExist());
        admin.update("UPDATE public.hidra_identity_provider"
            +" SET status='INACTIVE',updated_at=clock_timestamp() WHERE id=?",provider);
        http("GET",path("oidc-v1"),null,externalToken)
            .andExpect(status().isForbidden());
        assertThat(count(REV)).isEqualTo(1);
    }

    private void assertNativeBinding(String nativeBearer,String expectedType) {
        String jti=nativeJwtDecoder.decode(nativeBearer).getClaimAsString("jti");
        assertThat(jti).isNotBlank();
        assertThat(admin.queryForObject(
            "SELECT authentication_type FROM public.hidra_identity_operation_binding"
                +" WHERE user_id=? AND jti=?",String.class,user,jti)).isEqualTo(expectedType);
    }

    private String simulatedRealDirectoryCallback(ProviderType type,String subject) throws Exception {
        var target=org.springframework.test.util.AopTestUtils
            .<dz.sh.hidra.modules.identity.infrastructure.security.LdapCredentialVerificationAdapter>
                getUltimateTargetObject(ldapVerification);
        var original=org.springframework.test.util.ReflectionTestUtils.getField(
            target,"ldapTemplate");
        var transport=org.mockito.Mockito.mock(org.springframework.ldap.core.LdapTemplate.class);
        var context=org.mockito.Mockito.mock(javax.naming.directory.DirContext.class);
        var attributes=new javax.naming.directory.BasicAttributes(true);
        attributes.put("entryUUID",subject);
        attributes.put("uid",user);
        attributes.put("displayName","Synthetic Manufacturer Operator");
        org.mockito.Mockito.when(context.getAttributes(
            org.mockito.ArgumentMatchers.any(javax.naming.Name.class),
            org.mockito.ArgumentMatchers.any(String[].class))).thenReturn(attributes);
        org.mockito.Mockito.when(transport.authenticate(
            org.mockito.ArgumentMatchers.anyString(),
            org.mockito.ArgumentMatchers.anyString(),
            org.mockito.ArgumentMatchers.anyString(),
            org.mockito.ArgumentMatchers.any(
                org.springframework.ldap.core.AuthenticatedLdapEntryContextCallback.class)))
            .thenAnswer(i->{
                ((org.springframework.ldap.core.AuthenticatedLdapEntryContextCallback)i.getArgument(3))
                    .executeWithContext(context,new org.springframework.ldap.core.LdapEntryIdentification(
                        new javax.naming.ldap.LdapName("uid=synthetic,ou=people,dc=synthetic,dc=invalid"),
                        new javax.naming.ldap.LdapName("uid=synthetic,ou=people")));
                return true;
            });
        org.springframework.test.util.ReflectionTestUtils.setField(target,"ldapTemplate",transport);
        try {
            // Actual LDAP verification adapter invokes the original authenticated callback;
            // only the directory network transport is simulated.
            return authentication.authenticate(new DirectAuthenticationCommand(
                type,user,PASSWORD,"synthetic-ip","synthetic-agent",
                "synthetic-directory-native")).accessToken();
        } finally {
            org.springframework.test.util.ReflectionTestUtils.setField(target,"ldapTemplate",original);
        }
    }
    @Test void originalLdapCallbackCompletesToNativeManufacturerHttpWithoutBorrowedLocalGrant()throws Exception{
        String subject="synthetic-ldap-subject-"+id();
        externalProvider("LDAP",subject);
        String directoryToken=simulatedRealDirectoryCallback(ProviderType.LDAP,subject);
        assertNativeBinding(directoryToken,"LDAP");
        allow(Operation.SAVE,"ldap-v1");
        http("PUT",path("ldap-v1"),body("ldap-v1"),directoryToken)
            .andExpect(status().isOk());
        allow(Operation.READ_REVISION,"ldap-v1");
        http("GET",path("ldap-v1"),null,directoryToken)
            .andExpect(status().isOk());
        admin.update("UPDATE public.hidra_identity_external_identity"
            +" SET status='UNLINKED',updated_at=clock_timestamp()"
            +" WHERE user_id=?",user);
        http("GET",path("ldap-v1"),null,directoryToken)
            .andExpect(status().isForbidden());
        assertThat(count(REV)).isEqualTo(1);
    }

    @Test void configuredHttpHistoryAcceptsPreviousHmacKeyOnlyDuringRotation()throws Exception{
        save("v1");save("v2");
        allow(Operation.LIST_HISTORY,null);
        var old=new dz.sh.hidra.modules.simulation.infrastructure.security
            .HmacManufacturerCharacteristicHistoryCursorAdapter(
                "synthetic-former","BwcHBwcHBwcHBwcHBwcHBwcHBwcHBwcHBwcHBwcHBwc",
                null,null,java.time.Clock.systemUTC());
        var position=new dz.sh.hidra.modules.simulation.application.port.out
            .ManufacturerCharacteristicHistoryCursorPort.Position(
                Instant.parse("2026-10-01T10:00:00Z"),"v1");
        String previous=old.encode(company,source,1,position);
        http.perform(get(ROOT+source+"/history").param("companyPartyId",company)
            .param("size","1").param("cursor",previous)
            .header("Authorization","Bearer "+token))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.history[0].revisionId").value("v2"));
        String poisoned=previous.substring(0,previous.length()-1)
            +(previous.endsWith("A")?"B":"A");
        http.perform(get(ROOT+source+"/history").param("companyPartyId",company)
            .param("size","1").param("cursor",poisoned)
            .header("Authorization","Bearer "+token))
            .andExpect(status().isForbidden());
        assertThat(count(REV)).isEqualTo(2);
    }

    @Test void originalActiveDirectoryCallbackCompletesToNativeManufacturerHistory()throws Exception{
        String subject="synthetic-ad-subject-"+id();
        externalProvider("ACTIVE_DIRECTORY",subject);
        String directoryToken=simulatedRealDirectoryCallback(ProviderType.ACTIVE_DIRECTORY,subject);
        assertNativeBinding(directoryToken,"ACTIVE_DIRECTORY");
        allow(Operation.SAVE,"ad-v1");
        http("PUT",path("ad-v1"),body("ad-v1"),directoryToken)
            .andExpect(status().isOk());
        allow(Operation.LIST_HISTORY,null);
        http("GET",source+"/history",null,directoryToken)
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.history[0].revisionId").value("ad-v1"));
        admin.update("UPDATE public.hidra_identity_provider SET status='INACTIVE'"
            +" WHERE provider_type='ACTIVE_DIRECTORY'");
        http("GET",source+"/history",null,directoryToken).andExpect(status().isForbidden());
        assertThat(count(REV)).isEqualTo(1);
    }

    @Test void manufacturerLostPhysicalCommitAcknowledgementRemainsIndeterminateWithoutBlindRetry()throws Exception{
        allow(Operation.SAVE,"lost-v1");
        var armed=new java.util.concurrent.atomic.AtomicReference<Thread>();
        var observed=new java.util.concurrent.atomic.AtomicBoolean();
        org.mockito.Mockito.doAnswer(inv->{
            Object result=inv.callRealMethod();
            assertThat(result).isEqualTo(true);
            armed.set(Thread.currentThread());
            return result;
        }).when(coordinator).finalGuard(org.mockito.ArgumentMatchers.any());
        org.mockito.Mockito.doAnswer(inv->{
            if(armed.get()==Thread.currentThread() && !observed.get()) {
                inv.callRealMethod(); // A genuine physical PostgreSQL commit succeeded.
                observed.set(true);
                armed.set(null);
                throw new org.springframework.transaction.TransactionSystemException(
                    "synthetic manufacturer physical commit acknowledgement lost");
            }
            return inv.callRealMethod();
        }).when(transactionManager).commit(org.mockito.ArgumentMatchers.any(
            org.springframework.transaction.TransactionStatus.class));
        try {
            http("PUT",path("lost-v1"),body("lost-v1"),token)
                .andExpect(status().isServiceUnavailable())
                .andExpect(jsonPath("$.code").value("INDETERMINATE"));
            assertThat(observed.get()).isTrue();
        } finally {
            org.mockito.Mockito.reset(transactionManager,coordinator);
        }
        assertThat(count(REV)).isEqualTo(1);
        http("PUT",path("lost-v1"),body("lost-v1"),token)
            .andExpect(status().isOk());
        assertThat(count(REV)).isEqualTo(1);
        assertThat(count(QUAL)).isZero();
    }

    @Test void manufacturerPublicationBudgetIsIndependentAndFiniteWithoutAnArbitraryHttpLatencySla()throws Exception{
        assertThat(workBudgets.manufacturerWorkBudgetMillis())
            .isEqualTo(dz.sh.hidra.platform.configuration.ProtectedExecutionBudgetProperties
                .DEFAULT_MANUFACTURER_WORK_BUDGET_MILLIS);
        assertThat(workBudgets.manufacturerWorkBudgetNanos()).isGreaterThan(
            dz.sh.hidra.platform.configuration.ProtectedExecutionBudgetProperties
                .STANDARD_WORK_BUDGET_MILLIS*1_000_000L);
        assertThat(workBudgets.manufacturerWorkBudgetMillis()).isLessThanOrEqualTo(
            dz.sh.hidra.platform.configuration.ProtectedExecutionBudgetProperties
                .MAX_WORK_BUDGET_MILLIS);
        allow(Operation.SAVE,"budget-v1");allow(Operation.READ_REVISION,"budget-v1");
        long start=System.nanoTime();
        http("PUT",path("budget-v1"),body("budget-v1"),token).andExpect(status().isOk());
        long saveMillis=java.util.concurrent.TimeUnit.NANOSECONDS
            .toMillis(System.nanoTime()-start);
        start=System.nanoTime();
        http("GET",path("budget-v1"),null,token).andExpect(status().isOk());
        long readMillis=java.util.concurrent.TimeUnit.NANOSECONDS
            .toMillis(System.nanoTime()-start);
        // Observational fixture metrics, not an arbitrary HTTP deadline assertion or an OT SLA.
        System.out.println("HAPI601 SYNTHETIC manufacturer budget sample saveMs="
            +saveMillis+" readMs="+readMillis+" configuredProtectedWorkMs="
            +workBudgets.manufacturerWorkBudgetMillis());
        assertThat(count(REV)).isEqualTo(1);
    }
}
