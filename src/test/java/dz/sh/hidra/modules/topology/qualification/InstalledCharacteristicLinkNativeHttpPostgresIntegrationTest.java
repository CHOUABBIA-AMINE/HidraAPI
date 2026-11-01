/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : InstalledCharacteristicLinkNativeHttpPostgresIntegrationTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-11-01
 *
 * @Type        : Class
 * @Layer       : Test
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.qualification
 *
 * @Description : Real synthetic PostgreSQL, native owner grants and configured JWT characteristic-link workflow.
 *
 */
package dz.sh.hidra.modules.topology.qualification;

import dz.sh.hidra.HidraApplication;
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
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/** Source, Identity, Party and physical guards use real backend code; only fixture data are synthetic. */
@Testcontainers(disabledWithoutDocker=true)
@SpringBootTest(classes=HidraApplication.class,webEnvironment=SpringBootTest.WebEnvironment.MOCK,
    properties={
        "hidra.platform.persistence.execution.installed-unit-work-budget-ms=10000",
        "hidra.platform.persistence.execution.manufacturer-work-budget-ms=10000",
        "hidra.platform.security.jwt.hmac-secret=synthetic-completion-test-key-32-bytes-only",
        "hidra.platform.security.jwt.token-issuer=hidra-api",
        "hidra.platform.security.jwt.audience=hidra-api",
        "hidra.simulation.manufacturer-history.cursor-current-key-id=synthetic",
        "hidra.simulation.manufacturer-history.cursor-current-key-base64=AQIDBAUGBwgJCgsMDQ4PEBESExQVFhcYGRobHB0eHyA"
    })
@ActiveProfiles("test")
@org.springframework.test.annotation.DirtiesContext(
    classMode=org.springframework.test.annotation.DirtiesContext.ClassMode.AFTER_CLASS)
class InstalledCharacteristicLinkNativeHttpPostgresIntegrationTest {
    private static final String APP="link_native_app",PUBLISHER="link_native_publisher";
    private static final String PASSWORD="synthetic-link-native-password";
    private static final String MODEL="synthetic-model",REV="v1";
    private static final String MS="/api/v1/simulation/manufacturer-characteristics/";
    private static final String LINK="/api/v1/topology/installed-units/";
    @Container static final PostgreSQLContainer<?> PG=new PostgreSQLContainer<>("postgres:16")
        .withDatabaseName("hidra_characteristic_link_test")
        .withCommand("postgres","-c","max_locks_per_transaction=256");
    @DynamicPropertySource static void datasource(DynamicPropertyRegistry registry){
        if(!PG.isRunning())PG.start();
        Flyway.configure().dataSource(PG.getJdbcUrl(),PG.getUsername(),PG.getPassword()).load().migrate();
        var admin=new JdbcTemplate(new DriverManagerDataSource(PG.getJdbcUrl(),PG.getUsername(),PG.getPassword()));
        admin.execute("REVOKE CREATE ON SCHEMA public FROM PUBLIC");
        for(String role:List.of(APP,PUBLISHER)){
            admin.execute("CREATE ROLE "+role+" LOGIN PASSWORD '"+PASSWORD+"'");
            admin.execute("GRANT USAGE ON SCHEMA public TO "+role);
            admin.execute("GRANT SELECT ON ALL TABLES IN SCHEMA public TO "+role);
        }
        admin.execute("GRANT CREATE ON DATABASE "+PG.getDatabaseName()+" TO "+APP);
        for(String table:admin.queryForList(
                "SELECT tablename FROM pg_catalog.pg_tables WHERE schemaname='public' AND tablename LIKE 'hidra_identity_%'",
                String.class)){
            if(!table.matches("hidra_identity_[a-z0-9_]+"))throw new IllegalStateException();
            admin.execute("GRANT INSERT,UPDATE,DELETE ON public."+table+" TO "+APP);
        }
        admin.execute("REVOKE INSERT,UPDATE,DELETE ON public.hidra_identity_external_mapping_generation FROM "+APP);
        admin.execute("REVOKE SELECT ON public.hidra_topology_installed_characteristic_revision FROM "+APP);
        for(ProtectedRelationFence fence:JdbcIdentityAuthorityFence.requiredRelationFences())
            if(fence.mode()!=ProtectedRelationFence.Mode.ACCESS_SHARE)
                admin.execute("GRANT UPDATE ON "+fence.relation()+" TO "+PUBLISHER);
        for(String relation:List.of("hidra_party_party","hidra_party_eligibility_generation",
                "hidra_party_role","hidra_party_role_assignment",
                "hidra_party_manufacturer_profile","hidra_party_manufacturer_source_authorization"))
            admin.execute("GRANT UPDATE ON public."+relation+" TO "+PUBLISHER);
        for(String relation:List.of("hidra_simulation_manufacturer_characteristic_revision",
                "hidra_simulation_manufacturer_characteristic_qualification"))
            admin.execute("GRANT INSERT ON public."+relation+" TO "+PUBLISHER);
        for(String relation:List.of("revision","request"))
            admin.execute("GRANT INSERT ON public.hidra_topology_installed_characteristic_"+relation+" TO "+PUBLISHER);
        admin.execute("GRANT INSERT,UPDATE ON public.hidra_topology_installed_characteristic_head TO "+PUBLISHER);
        provisionLock(admin,"link_party_lock_owner","public.hidra_party_manufacturer_authorization_lock()",
            List.of("hidra_party_party","hidra_party_eligibility_generation","hidra_party_role",
                "hidra_party_role_assignment","hidra_party_manufacturer_profile",
                "hidra_party_manufacturer_source_authorization"));
        provisionLock(admin,"link_manufacturer_lock_owner","public.hidra_simulation_manufacturer_lock_sources()",
            List.of("hidra_simulation_manufacturer_characteristic_revision",
                "hidra_simulation_manufacturer_characteristic_qualification"));
        provisionLock(admin,"link_topology_lock_owner","public.hidra_topology_characteristic_lock_sources()",
            List.of("hidra_simulation_manufacturer_characteristic_revision","hidra_topology_installed_unit",
                "hidra_topology_installed_characteristic_revision",
                "hidra_topology_installed_characteristic_head","hidra_topology_installed_characteristic_request"));
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
        for(String relation:relations)admin.execute("GRANT UPDATE ON public."+relation+" TO "+role);
        admin.execute("ALTER FUNCTION "+function+" OWNER TO "+role);
        admin.execute("REVOKE CREATE ON SCHEMA public FROM "+role);
        admin.execute("GRANT EXECUTE ON FUNCTION "+function+" TO "+PUBLISHER);
    }
    @Autowired WebApplicationContext context;
    @Autowired AuthenticateDirectUserUseCase authentication;
    @Autowired PasswordEncoder passwords;
    private JdbcTemplate admin;
    private MockMvc http;
    private String company,manufacturer,source,unit,link,user,token;
    private static String id(){return UUID.randomUUID().toString();}
    private static String hash(String text){
        try{return HexFormat.of().formatHex(MessageDigest.getInstance("SHA-256")
                .digest(text.getBytes(StandardCharsets.UTF_8)));}
        catch(java.security.NoSuchAlgorithmException e){throw new AssertionError(e);}
    }
    private static String sourceScope(String company,String source,String revision){
        return hash("HIDRA_MANUFACTURER_NATIVE_SCOPE_V1|"+company.length()+":"+company
            +"|"+source.length()+":"+source+"|"+revision.length()+":"+revision);
    }
    @BeforeEach void setup(){
        admin=new JdbcTemplate(new DriverManagerDataSource(PG.getJdbcUrl(),PG.getUsername(),PG.getPassword()));
        SecurityContextHolder.clearContext();
        http=MockMvcBuilders.webAppContextSetup(context)
            .apply(org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity())
            .build();
        user=id();company=id();manufacturer=id();source=id();unit=id();link=id();
        String partyType=id(),equipmentType=id();
        var previous=admin.queryForList("SELECT id FROM public.hidra_party_role WHERE code='MANUFACTURER'",String.class);
        String partyRole=previous.isEmpty()?id():previous.getFirst();
        admin.update("INSERT INTO public.hidra_party_type(id,code,status,created_at,updated_at) VALUES (?,?,'ACTIVE',now(),now())",
            partyType,partyType);
        for(String party:List.of(company,manufacturer)){
            admin.update("INSERT INTO public.hidra_party_party(id,code,party_type_id,legal_name,country_code,status,created_at,updated_at)"
                +" VALUES (?,?,?,'Synthetic owner','DZ','ACTIVE',now(),now())",party,party,partyType);
        }
        if(previous.isEmpty())admin.update("INSERT INTO public.hidra_party_role"
            +"(id,code,qualification_required_by_default,status,created_at,updated_at)"
            +" VALUES (?,'MANUFACTURER',true,'ACTIVE',now(),now())",partyRole);
        admin.update("INSERT INTO public.hidra_party_role_assignment"
            +"(id,party_id,role_id,valid_from,status,qualification_required,created_at,updated_at)"
            +" VALUES (?,?,?,now()-interval '1 hour','ACTIVE',true,now(),now())",id(),manufacturer,partyRole);
        admin.update("INSERT INTO public.hidra_party_manufacturer_profile"
            +"(id,party_id,capability_type,qualification_status,created_at,updated_at)"
            +" VALUES (?,?,'EQUIPMENT','QUALIFIED',now(),now())",id(),manufacturer);
        admin.update("INSERT INTO public.hidra_party_manufacturer_source_authorization"
            +"(authorization_id,company_party_id,manufacturer_party_id,manufacturer_model_reference,"
            +"authorization_status,valid_from,generation,created_at,updated_at)"
            +" VALUES (?,?,?,?,'ACTIVE',now()-interval '1 hour',1,now(),now())",
            id(),company,manufacturer,MODEL);
        admin.update("INSERT INTO public.hidra_topology_equipment_type"
            +"(id,code,name,status,created_at,updated_at) VALUES (?,?,'Synthetic unit','ACTIVE',now(),now())",
            equipmentType,equipmentType);
        admin.update("INSERT INTO public.hidra_topology_installed_unit"
            +"(id,company_party_id,equipment_kind_id,manufacturer_party_verification,"
            +"manufacturer_model_verification,serial_verification)"
            +" VALUES (?,?,?,'UNKNOWN','UNKNOWN','UNKNOWN')",unit,company,equipmentType);
        admin.update("UPDATE public.hidra_identity_provider SET status='INACTIVE' WHERE status='ACTIVE'");
        String provider=id();
        Timestamp now=Timestamp.from(Instant.now());
        admin.update("INSERT INTO public.hidra_identity_user"
            +"(id,username,user_type,status,failed_login_count,created_at,updated_at)"
            +" VALUES (?,?,'HUMAN','ACTIVE',0,?,?)",user,user,now,now);
        admin.update("INSERT INTO public.hidra_identity_provider"
            +"(id,code,name,provider_type,status,sync_enabled,just_in_time_provisioning_enabled,created_at,updated_at)"
            +" VALUES (?,?,'Synthetic characteristic','LOCAL','ACTIVE',false,false,?,?)",
            provider,provider,now,now);
        admin.update("INSERT INTO public.hidra_identity_local_credential"
            +"(id,user_id,password_hash,credential_status,created_at,updated_at)"
            +" VALUES (?,?,?,'ACTIVE',?,?)",id(),user,passwords.encode(PASSWORD),now,now);
        token=authentication.authenticate(new DirectAuthenticationCommand(
            ProviderType.LOCAL,user,PASSWORD,"synthetic-ip","synthetic-agent","synthetic-correlation")).accessToken();
        SecurityContextHolder.clearContext();
    }
    @AfterEach void cleanup(){SecurityContextHolder.clearContext();}
    private void grant(String code,String resourceType,String scope){
        var existing=admin.queryForList("SELECT id FROM public.hidra_identity_permission WHERE code=?",String.class,code);
        String permission=existing.isEmpty()?id():existing.getFirst();
        if(existing.isEmpty())admin.update("INSERT INTO public.hidra_identity_permission"
            +"(id,code,permission_domain,resource_type,action,sensitive,status,created_at,updated_at)"
            +" VALUES (?,?,?, ?, ?,false,'ACTIVE',now(),now())",
            permission,code,code.substring(0,code.indexOf(':')),resourceType,
            code.substring(code.lastIndexOf(':')+1));
        admin.update("INSERT INTO public.hidra_identity_user_permission_grant"
            +"(id,user_id,permission_id,effect,scope_type,scope_reference_id,grant_reason,"
            +"emergency_access,valid_from,valid_to,status,created_at)"
            +" VALUES (?,?,?,'GRANT','CUSTOM',?,'synthetic characteristic',false,"
            +"now()-interval '1 hour',now()+interval '1 hour','ACTIVE',now())",
            id(),user,permission,scope);
    }
    private void grants(){
        for(String code:List.of("simulation:manufacturer-characteristic:save",
                "simulation:manufacturer-characteristic:read")){
            grant(code,"MANUFACTURER_CHARACTERISTIC_REVISION","simulation-company:"+company);
            grant(code,"MANUFACTURER_CHARACTERISTIC_REVISION","simulation-source:"+sourceScope(company,source,REV));
        }
        for(String code:List.of("topology:installed-characteristic:save",
                "topology:installed-characteristic:read",
                "topology:installed-characteristic:history"))
            grant(code,"INSTALLED_CHARACTERISTIC_LINK","topology-company:"+company);
        grant("topology:installed-unit:read","INSTALLED_UNIT","topology-company:"+company);
    }
    private String manufacturerBody(){
        return """
          {"companyPartyId":"%s","revision":{
            "sourceId":"%s","revisionId":"v1","recordedAt":"2026-10-01T10:00:00Z",
            "effectiveFrom":"2026-10-01T10:00:00Z","origin":"SYNTHETIC",
            "manufacturerPartyId":"%s","manufacturerModelReference":"synthetic-model",
            "documentReference":"synthetic-OEM-bounds","documentSha256":"%s",
            "referenceConditions":{"pressurePascalsAbsolute":180000.00,
              "temperatureKelvin":298.15,"methodReference":"synthetic conditions"},
            "family":{"kind":"OPERATING_LIMITS","limits":[{
              "axis":{"quantity":"MASS_FLOW","unit":"KILOGRAM_PER_SECOND"},
              "minimumInclusive":0.00,"maximumInclusive":2.00,
              "evidenceReference":"synthetic bounds"}]}}}
          """.formatted(company,source,manufacturer,"a".repeat(64));
    }
    /** CI-only failure classification: no SQL, IDs, request content or protected values. */
    private static ResultActions requireStatus(ResultActions action,int expected) throws Exception {
        return action.andDo(result -> {
            if(result.getResponse().getStatus()==expected)return;
            var trace=new java.util.ArrayList<String>();
            Throwable fault=result.getResolvedException();
            for(int i=0;i<8 && fault!=null;i++,fault=fault.getCause()) {
                String location="no-application-frame";
                for(var frame:fault.getStackTrace())
                    if(frame.getClassName().startsWith("dz.sh.hidra.")) {
                        location=frame.getClassName()+"#"+frame.getMethodName()+":"+frame.getLineNumber();
                        break;
                    }
                trace.add(fault.getClass().getSimpleName()+"@"+location);
            }
            throw new AssertionError("Expected protected HTTP status "+expected+" but got "+
                    result.getResponse().getStatus()+"; redacted cause chain "+trace);
        }).andExpect(org.springframework.test.web.servlet.result.MockMvcResultMatchers.status().is(expected));
    }
    private String linkPath(){return LINK+unit+"/characteristic-links/"+link;}
    private void saveManufacturer()throws Exception{
        // The synthetic manufacturer publisher capability exists only during that earlier operation.
        for(String relation:List.of("hidra_simulation_manufacturer_characteristic_revision",
                "hidra_simulation_manufacturer_characteristic_qualification"))
            admin.execute("GRANT INSERT ON public."+relation+" TO "+PUBLISHER);
        try{
            http.perform(put(MS+source+"/revisions/"+REV).header("Authorization","Bearer "+token)
                .contentType(MediaType.APPLICATION_JSON).content(manufacturerBody()))
                .andExpect(status().isOk());
        }finally{
            for(String relation:List.of("hidra_simulation_manufacturer_characteristic_revision",
                    "hidra_simulation_manufacturer_characteristic_qualification"))
                admin.execute("REVOKE INSERT ON public."+relation+" FROM "+PUBLISHER);
        }
        assertFalse(Boolean.TRUE.equals(admin.queryForObject(
            "SELECT pg_catalog.has_table_privilege(?::name,"
               +"'public.hidra_simulation_manufacturer_characteristic_revision','INSERT')",
            Boolean.class,PUBLISHER)));
    }
    private String draftBody(){
        String digest=admin.queryForObject("SELECT payload_sha256 FROM public.hidra_simulation_manufacturer_characteristic_revision"
            +" WHERE company_party_id=? AND source_id=? AND revision_id=?",
            String.class,company,source,REV);
        return """
           {"companyPartyId":"%s","requestId":"req-one","expectedEpoch":0,"linkRevision":1,
            "sourceId":"%s","sourceRevisionId":"v1","sourceSha256":"%s",
            "kind":"OPERATING_LIMITS","effectiveFrom":"2026-10-01T10:00:00Z",
            "recordedAt":"2026-10-01T10:00:00Z","evidenceReference":"synthetic:link"}
           """.formatted(company,source,digest);
    }
    @Test void authenticatedDraftSaveReplayCurrentRevisionHistoryUseOriginalNativeOwner()throws Exception{
        grants();saveManufacturer();String body=draftBody();
        requireStatus(http.perform(put(linkPath()).header("Authorization","Bearer "+token)
            .contentType(MediaType.APPLICATION_JSON).content(body)),200)
            .andExpect(jsonPath("$.link.state").value("DRAFT"));
        http.perform(put(linkPath()).header("Authorization","Bearer "+token)
            .contentType(MediaType.APPLICATION_JSON).content(body))
            .andExpect(status().isOk()).andExpect(jsonPath("$.replayed").value(true));
        for(String url:List.of(linkPath(),linkPath()+"/revisions/1")){
            http.perform(get(url).header("Authorization","Bearer "+token).param("companyPartyId",company))
                .andExpect(status().isOk()).andExpect(jsonPath("$.link.sourceId").value(source));
        }
        http.perform(get(linkPath()+"/history").header("Authorization","Bearer "+token)
            .param("companyPartyId",company).param("limit","5"))
            .andExpect(status().isOk()).andExpect(jsonPath("$.history.length()").value(1));
        assertEquals(1L,admin.queryForObject("SELECT count(*) FROM public.hidra_topology_installed_characteristic_request"
            +" WHERE company_party_id=? AND installed_unit_id=? AND link_id=?",
            Long.class,company,unit,link));
    }
    @Test void missingSourcePermissionDeniesBeforeAnyDraftMutation()throws Exception{
        grants();saveManufacturer();String body=draftBody();
        String permission=admin.queryForObject("SELECT id FROM public.hidra_identity_permission"
            +" WHERE code='simulation:manufacturer-characteristic:read'",String.class);
        admin.update("INSERT INTO public.hidra_identity_user_permission_grant"
            +"(id,user_id,permission_id,effect,scope_type,scope_reference_id,grant_reason,"
            +"emergency_access,valid_from,valid_to,status,created_at)"
            +" VALUES (?,?,?,'DENY','CUSTOM',?,'synthetic source DENY',false,"
            +"now()-interval '1 hour',now()+interval '1 hour','ACTIVE',now())",
            id(),user,permission,"simulation-source:"+sourceScope(company,source,REV));
        requireStatus(http.perform(put(linkPath()).header("Authorization","Bearer "+token)
            .contentType(MediaType.APPLICATION_JSON).content(body)),403);
        assertEquals(0L,admin.queryForObject("SELECT count(*) FROM public.hidra_topology_installed_characteristic_revision"
            +" WHERE company_party_id=? AND installed_unit_id=? AND link_id=?",
            Long.class,company,unit,link));
    }
    @Test void revokedManufacturerSourceDeniesExistingLinkCurrentRevisionAndHistory()throws Exception{
        grants();saveManufacturer();String body=draftBody();
        requireStatus(http.perform(put(linkPath()).header("Authorization","Bearer "+token)
            .contentType(MediaType.APPLICATION_JSON).content(body)),200);
        // A valid, already-issued JWT cannot override a newly effective source-specific DENY.
        String permission=admin.queryForObject("SELECT id FROM public.hidra_identity_permission"
            +" WHERE code='simulation:manufacturer-characteristic:read'",String.class);
        admin.update("INSERT INTO public.hidra_identity_user_permission_grant"
            +"(id,user_id,permission_id,effect,scope_type,scope_reference_id,grant_reason,"
            +"emergency_access,valid_from,valid_to,status,created_at)"
            +" VALUES (?,?,?,'DENY','CUSTOM',?,'synthetic revoked source read',false,"
            +"now()-interval '1 hour',now()+interval '1 hour','ACTIVE',now())",
            id(),user,permission,"simulation-source:"+sourceScope(company,source,REV));
        for(String url:List.of(linkPath(),linkPath()+"/revisions/1",linkPath()+"/history")){
            requireStatus(http.perform(get(url).header("Authorization","Bearer "+token)
                .param("companyPartyId",company)),403);
        }
        assertEquals(1L,admin.queryForObject("SELECT count(*)"
            +" FROM public.hidra_topology_installed_characteristic_revision"
            +" WHERE company_party_id=? AND installed_unit_id=? AND link_id=?",
            Long.class,company,unit,link));
    }
    @Test void absentBearerCannotReadCurrentOrHistoricalExistence()throws Exception{
        http.perform(get(linkPath()).param("companyPartyId",company))
            .andExpect(status().isUnauthorized());
        http.perform(get(linkPath()+"/history").param("companyPartyId",company))
            .andExpect(status().isUnauthorized());
        http.perform(put(linkPath()).contentType(MediaType.APPLICATION_JSON)
            .content("{}")).andExpect(status().isUnauthorized());
    }
}
