/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : InstalledEquipmentOperatorQualificationTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-11-01
 *
 * @Type        : Class
 * @Layer       : Test
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.qualification
 *
 * @Description : Cross-owner installed equipment consumer readiness and immutable historical contract qualification.
 *
 */
package dz.sh.hidra.modules.topology.qualification;

import dz.sh.hidra.HidraApplication;
import dz.sh.hidra.modules.topology.domain.model.InstalledCharacteristicLink;
import dz.sh.hidra.modules.topology.domain.service.InstalledCharacteristicLinkApprovalTargetDigester;
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
class InstalledEquipmentOperatorQualificationTest {
    private static final String APP="consumer_native_app",PUBLISHER="consumer_native_publisher";
    private static final String PASSWORD="synthetic-consumer-native-password";
    private static final String MODEL="synthetic-model",REV="v1";
    private static final String MS="/api/v1/simulation/manufacturer-characteristics/";
    private static final String LINK="/api/v1/topology/installed-units/";
    @Container static final PostgreSQLContainer<?> PG=new PostgreSQLContainer<>("postgres:16")
        .withDatabaseName("hidra_equipment_consumer_test")
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
        // This test-only publisher exercises Workflow's real SHARE NOWAIT table fences.
        // PostgreSQL requires UPDATE (or a stronger DML privilege) to take SHARE locks.
        // Schema owners configure these synthetic role grants; no migration grants them.
        // This is NOT a deployment privilege policy or proof of least-privilege production roles.
        for(String relation:List.of("hidra_workflow_action","hidra_workflow_definition",
                "hidra_workflow_definition_target_binding","hidra_workflow_instance",
                "hidra_workflow_state_history","hidra_workflow_step",
                "hidra_workflow_task","hidra_workflow_transition",
                "hidra_workflow_type_catalog"))
            admin.execute("GRANT UPDATE ON public."+relation+" TO "+PUBLISHER);

        for(String relation:List.of("hidra_simulation_manufacturer_characteristic_revision",
                "hidra_simulation_manufacturer_characteristic_qualification"))
            admin.execute("GRANT INSERT ON public."+relation+" TO "+PUBLISHER);
        for(String relation:List.of("revision","request"))
            admin.execute("GRANT INSERT ON public.hidra_topology_installed_characteristic_"+relation+" TO "+PUBLISHER);
        admin.execute("GRANT INSERT,UPDATE ON public.hidra_topology_installed_characteristic_head TO "+PUBLISHER);
        for(String relation:List.of("approval","approval_request"))
            admin.execute("GRANT INSERT ON public.hidra_topology_installed_characteristic_"+relation+" TO "+PUBLISHER);
        provisionLock(admin,"consumer_party_lock_owner","public.hidra_party_manufacturer_authorization_lock()",
            List.of("hidra_party_party","hidra_party_eligibility_generation","hidra_party_role",
                "hidra_party_role_assignment","hidra_party_manufacturer_profile",
                "hidra_party_manufacturer_source_authorization"));
        provisionLock(admin,"consumer_manufacturer_lock_owner","public.hidra_simulation_manufacturer_lock_sources()",
            List.of("hidra_simulation_manufacturer_characteristic_revision",
                "hidra_simulation_manufacturer_characteristic_qualification"));
        provisionLock(admin,"consumer_topology_lock_owner","public.hidra_topology_characteristic_lock_sources()",
            List.of("hidra_simulation_manufacturer_characteristic_revision","hidra_topology_installed_unit",
                "hidra_topology_installed_characteristic_revision",
                "hidra_topology_installed_characteristic_head","hidra_topology_installed_characteristic_request"));
        provisionLock(admin,"consumer_topology_approval_lock_owner",
            "public.hidra_topology_characteristic_lock_approval_sources()",
            List.of("hidra_simulation_manufacturer_characteristic_revision",
                "hidra_topology_installed_unit","hidra_topology_installed_unit_installation",
                "hidra_topology_installed_characteristic_revision",
                "hidra_topology_installed_characteristic_head",
                "hidra_topology_installed_characteristic_request",
                "hidra_topology_installed_characteristic_approval",
                "hidra_topology_installed_characteristic_approval_request"));
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
    private String protectedApprovalPath(){return linkPath()+"/revisions/1/approval";}
    /** The publisher remains untrusted; all fixture entries are synthetic schema-owner test data. */
    private String distinctReviewer() {
        String reviewer=id();
        Timestamp now=Timestamp.from(Instant.now());
        admin.update("INSERT INTO public.hidra_identity_user"+
            "(id,username,user_type,status,failed_login_count,created_at,updated_at)"+
            " VALUES (?,?,'HUMAN','ACTIVE',0,?,?)",reviewer,reviewer,now,now);
        admin.update("INSERT INTO public.hidra_identity_local_credential"+
            "(id,user_id,password_hash,credential_status,created_at,updated_at)"+
            " VALUES (?,?,?,'ACTIVE',?,?)",id(),reviewer,passwords.encode(PASSWORD),now,now);
        return reviewer;
    }
    private String reviewerToken(String reviewer) {
        return authentication.authenticate(new DirectAuthenticationCommand(
            ProviderType.LOCAL,reviewer,PASSWORD,"synthetic-ip","synthetic-agent",
            "synthetic-approval-correlation")).accessToken();
    }
    private void reviewerGrants(String reviewer, boolean approve) {
        String original=user;
        try {
            user=reviewer;
            for(String code:List.of("topology:installed-characteristic:read",
                    "topology:installed-characteristic:history"))
                grant(code,"INSTALLED_CHARACTERISTIC_LINK","topology-company:"+company);
            if(approve)
                grant("topology:installed-characteristic:approve",
                    "INSTALLED_CHARACTERISTIC_LINK","topology-company:"+company);
            grant("topology:installed-unit:read","INSTALLED_UNIT","topology-company:"+company);
            grant("simulation:manufacturer-characteristic:read",
                "MANUFACTURER_CHARACTERISTIC_REVISION","simulation-company:"+company);
            grant("simulation:manufacturer-characteristic:read",
                "MANUFACTURER_CHARACTERISTIC_REVISION",
                "simulation-source:"+sourceScope(company,source,REV));
        } finally {user=original;}
    }
    private String canonicalDraftDigest(){
        String sourceSha=admin.queryForObject(
            "SELECT payload_sha256 FROM public.hidra_simulation_manufacturer_characteristic_revision"+
            " WHERE company_party_id=? AND source_id=? AND revision_id=?",
            String.class,company,source,REV);
        var draft=new InstalledCharacteristicLink(link,1L,company,unit,source,REV,sourceSha,
            InstalledCharacteristicLink.Kind.OPERATING_LIMITS,
            Instant.parse("2026-10-01T10:00:00Z"),null,
            Instant.parse("2026-10-01T10:00:00Z"),"synthetic:link",null);
        return InstalledCharacteristicLinkApprovalTargetDigester.digest(draft);
    }
    private record ActualWorkflow(String definition,String type,String purpose,
            String instance,String task,String action,String digest){}
    /** Real Workflow owner relations: configured target, active definition, approved terminal action. */
    private ActualWorkflow terminalWorkflow(String reviewer,String digest){
        String definition=id(),start=id(),end=id(),instance=id(),task=id(),action=id();
        String type=admin.queryForObject(
            "SELECT id FROM public.hidra_workflow_type_catalog"+
            " WHERE catalog_name='WORKFLOW_TARGET_TYPE' AND code='INSTALLED_CHARACTERISTIC_LINK_REVISION'",
            String.class);
        String purpose=admin.queryForObject(
            "SELECT id FROM public.hidra_workflow_type_catalog"+
            " WHERE catalog_name='WORKFLOW_PURPOSE' AND code='INSTALLED_CHARACTERISTIC_LINK_APPROVAL'",
            String.class);
        admin.update("INSERT INTO public.hidra_workflow_definition"+
            "(id,code,name_fr,type_id,status,version,created_at,updated_at)"+
            " VALUES (?,?,'Synthetic installed characteristic approval',?,'ACTIVE',1,now(),now())",
            definition,definition,type);
        for(Object[] row:List.of(new Object[]{start,1},new Object[]{end,2}))
            admin.update("INSERT INTO public.hidra_workflow_step"+
                "(id,definition_id,code,name_fr,step_order,mandatory,allow_claim,"+
                "allow_delegation,allow_escalation,created_at,updated_at)"+
                " VALUES (?,?,?,'Synthetic terminal review',?,true,true,false,false,now(),now())",
                row[0],definition,row[0],row[1]);
        admin.update("INSERT INTO public.hidra_workflow_definition_target_binding"+
            "(id,definition_id,target_module,target_type_id,workflow_purpose_id,"+
            "active,created_at,updated_at)"+
            " VALUES (?,?,'topology',?,?,true,now(),now())",
            id(),definition,type,purpose);
        Instant terminal=Instant.now().minusSeconds(1).truncatedTo(java.time.temporal.ChronoUnit.MICROS);
        Timestamp done=Timestamp.from(terminal);
        admin.update("INSERT INTO public.hidra_workflow_instance"+
            "(id,definition_id,definition_version,workflow_purpose_id,target_module,target_type_id,"+
            "target_id,status,current_step_id,started_by_actor_id,started_by_display_name_snapshot,"+
            "started_at,completed_at,created_at,updated_at)"+
            " VALUES (?,?,1,?,'topology',?,?,'COMPLETED',?,?,'Synthetic Reviewer',?,?,?,now())",
            instance,definition,purpose,type,digest,end,reviewer,
            Timestamp.from(terminal.minusSeconds(10)),done,
            Timestamp.from(terminal.minusSeconds(10)));
        admin.update("INSERT INTO public.hidra_workflow_task"+
            "(id,instance_id,step_id,status,assigned_actor_id,completed_by_actor_id,"+
            "completed_at,created_at,updated_at)"+
            " VALUES (?,?,?,'APPROVED',?,?,?, ?,now())",
            task,instance,start,reviewer,reviewer,done,
            Timestamp.from(terminal.minusSeconds(10)));
        admin.update("INSERT INTO public.hidra_workflow_action"+
            "(id,instance_id,task_id,action_type,decision,actor_id,"+
            "actor_display_name_snapshot,action_sequence,source_system,acted_at)"+
            " VALUES (?,?,?,'APPROVE','APPROVE',?,'Synthetic Reviewer',1,'HIDRA_API',?)",
            action,instance,task,reviewer,done);
        return new ActualWorkflow(definition,type,purpose,instance,task,action,digest);
    }
    private String approvalBody(ActualWorkflow w){
        return """
            {"companyPartyId":"%s","requestId":"approved-once","expectedEpoch":1,
             "workflow":{"definitionId":"%s","definitionVersion":1,
                 "targetTypeId":"%s","purposeId":"%s","instanceId":"%s",
                 "taskId":"%s","actionId":"%s"}}
            """.formatted(company,w.definition(),w.type(),w.purpose(),
                w.instance(),w.task(),w.action());
    }
    private void savedDraft() throws Exception {
        grants();saveManufacturer();
        requireStatus(http.perform(put(linkPath()).header("Authorization","Bearer "+token)
            .contentType(MediaType.APPLICATION_JSON).content(draftBody())),200);
    }
    private long approvalCount(){
        return admin.queryForObject(
            "SELECT count(*) FROM public.hidra_topology_installed_characteristic_approval"+
            " WHERE company_party_id=? AND installed_unit_id=? AND link_id=?",
            Long.class,company,unit,link);
    }

    private static final String PREVIOUS_STATION_GRAPH_WIRE_FORMAT =
            "HIDRA_TOPOLOGY_STATION_GRAPH_REVISION_V1";

    private static String tableCountSql(String suffix) {
        return "SELECT count(*) FROM public.hidra_topology_installed_characteristic_"
                +suffix+" WHERE company_party_id=? AND installed_unit_id=? AND link_id=?";
    }

    private long countFor(String relation) {
        return admin.queryForObject(tableCountSql(relation),Long.class,company,unit,link);
    }

    /** Each source is published through actual Simulation HTTP; each link through native Topology. */
    @Test void nineIndependentUnitSourceBindingsRemainSeparateAcrossThreeStations() throws Exception {
        grants();
        String kind=admin.queryForObject(
            "SELECT equipment_kind_id FROM public.hidra_topology_installed_unit WHERE id=?",
            String.class,unit);
        String facilityType=id();
        admin.update("INSERT INTO public.hidra_topology_facility_type"+
            "(id,code,name,facility_kind,status,created_at,updated_at)"+
            " VALUES (?,?,'Synthetic consumer station','STATION','ACTIVE',now(),now())",
            facilityType,facilityType);
        var seenUnits=new java.util.HashSet<String>();
        var seenSources=new java.util.HashSet<String>();
        var seenLinks=new java.util.HashSet<String>();
        var stations=new java.util.ArrayList<String>();
        final String originalUnit=unit;
        final String originalSource=source;
        final String originalLink=link;
        for(int station=0;station<3;station++){
            String facility=id();stations.add(facility);
            admin.update("INSERT INTO public.hidra_topology_facility"+
                "(id,code,facility_type_id,facility_kind,owner_party_id,status,created_at,updated_at)"+
                " VALUES(?,?,?,'STATION',?,'ACTIVE',now(),now())",
                facility,facility,facilityType,company);
            for(int at=0;at<3;at++){
                int ordinal=station*3+at;
                unit=ordinal==0?originalUnit:id();
                source=ordinal==0?originalSource:id();
                link=ordinal==0?originalLink:id();
                assertTrue(seenUnits.add(unit));assertTrue(seenSources.add(source));
                assertTrue(seenLinks.add(link));
                if(ordinal>0){
                    admin.update("INSERT INTO public.hidra_topology_installed_unit"+
                        "(id,company_party_id,equipment_kind_id,manufacturer_party_verification,"+
                        "manufacturer_model_verification,serial_verification)"+
                        " VALUES (?,?,?,'UNKNOWN','UNKNOWN','UNKNOWN')",unit,company,kind);
                    grant("simulation:manufacturer-characteristic:save",
                        "MANUFACTURER_CHARACTERISTIC_REVISION",
                        "simulation-source:"+sourceScope(company,source,REV));
                    grant("simulation:manufacturer-characteristic:read",
                        "MANUFACTURER_CHARACTERISTIC_REVISION",
                        "simulation-source:"+sourceScope(company,source,REV));
                }
                String position=id();
                admin.update("INSERT INTO public.hidra_topology_equipment"+
                    "(id,code,facility_id,equipment_type_id,status,created_at,updated_at)"+
                    " VALUES(?,?,?,?,'PLANNED',now(),now())",
                    position,position,facility,kind);
                admin.update("INSERT INTO public.hidra_topology_installed_unit_installation"+
                    "(installation_id,unit_id,position_id,company_party_id,effective_from,"+
                    "recorded_at,evidence_reference,revision)"+
                    " VALUES (?,?,?,?,? ,?,'synthetic:station-binding',1)",
                    id(),unit,position,company,
                    Timestamp.from(Instant.parse("2026-10-01T10:00:00Z")),
                    Timestamp.from(Instant.parse("2026-10-01T10:00:00Z")));
                saveManufacturer();
                String sourceDigest=admin.queryForObject(
                    "SELECT payload_sha256 FROM public.hidra_simulation_manufacturer_characteristic_revision"+
                    " WHERE company_party_id=? AND source_id=? AND revision_id=?",
                    String.class,company,source,REV);
                requireStatus(http.perform(put(linkPath())
                    .header("Authorization","Bearer "+token)
                    .contentType(MediaType.APPLICATION_JSON).content(draftBody())),200)
                    .andExpect(jsonPath("$.link.state").value("DRAFT"));
                requireStatus(http.perform(get(linkPath()).header("Authorization","Bearer "+token)
                    .param("companyPartyId",company)),200)
                    .andExpect(jsonPath("$.link.sourceId").value(source))
                    .andExpect(jsonPath("$.link.sourceSha256").value(sourceDigest))
                    .andExpect(jsonPath("$.link.installedUnitId").value(unit));
                requireStatus(http.perform(get(linkPath()+"/revisions/1")
                    .header("Authorization","Bearer "+token).param("companyPartyId",company)),200)
                    .andExpect(jsonPath("$.link.sourceId").value(source));
                requireStatus(http.perform(get(linkPath()+"/history")
                    .header("Authorization","Bearer "+token).param("companyPartyId",company)
                    .param("limit","2")),200)
                    .andExpect(jsonPath("$.history.length()").value(1));
                assertEquals(1L,countFor("revision"));
                assertEquals(1L,countFor("request"));
                assertEquals(0L,countFor("approval"));
            }
        }
        assertEquals(9,seenUnits.size());
        assertEquals(9,seenSources.size());
        assertEquals(9,seenLinks.size());
        for(String facility:stations)assertEquals(3,admin.queryForObject(
            "SELECT count(*) FROM public.hidra_topology_installed_unit_installation i"+
            " JOIN public.hidra_topology_equipment e ON e.id=i.position_id"+
            " WHERE e.facility_id=?",Integer.class,facility));
        assertEquals(9L,admin.queryForObject(
            "SELECT count(*) FROM public.hidra_topology_installed_characteristic_revision"+
            " WHERE company_party_id=?",Long.class,company));
    }

    @Test void malformedManufacturerAxisAndLinkDigestCannotCreateConsumerRevision() throws Exception {
        grants();
        requireStatus(http.perform(put(MS+source+"/revisions/"+REV)
            .header("Authorization","Bearer "+token).contentType(MediaType.APPLICATION_JSON)
            .content(manufacturerBody().replace("MASS_FLOW","NOT_AN_AXIS"))),400);
        assertEquals(0L,admin.queryForObject(
            "SELECT count(*) FROM public.hidra_simulation_manufacturer_characteristic_revision"+
            " WHERE company_party_id=? AND source_id=?",Long.class,company,source));
        saveManufacturer();
        requireStatus(http.perform(put(linkPath()).header("Authorization","Bearer "+token)
            .contentType(MediaType.APPLICATION_JSON)
            .content(draftBody().replaceFirst("[0-9a-f]{64}", "invalid-sha"))),400);
        assertEquals(0L,countFor("revision"));
    }

    @Test void foreignCompanyAndUnauthenticatedConsumerCannotDiscoverExistingSource() throws Exception {
        grants();saveManufacturer();
        String payload=draftBody();
        requireStatus(http.perform(put(linkPath()).contentType(MediaType.APPLICATION_JSON)
            .content(payload)),401);
        requireStatus(http.perform(get(linkPath()).param("companyPartyId",company)),401);
        requireStatus(http.perform(get(linkPath()).header("Authorization","Bearer "+token)
            .param("companyPartyId",id())),403);
        requireStatus(http.perform(put(linkPath()).header("Authorization","Bearer "+token)
            .contentType(MediaType.APPLICATION_JSON)
            .content(payload.replace(company,id()))),403);
        assertEquals(0L,countFor("revision"));
    }

    @Test void replayConflictAndStaleEpochPreserveHistoricalSourceAndReceipt() throws Exception {
        savedDraft();
        String first=draftBody();
        requireStatus(http.perform(put(linkPath()).header("Authorization","Bearer "+token)
            .contentType(MediaType.APPLICATION_JSON).content(first)),200)
            .andExpect(jsonPath("$.replayed").value(true));
        requireStatus(http.perform(put(linkPath()).header("Authorization","Bearer "+token)
            .contentType(MediaType.APPLICATION_JSON)
            .content(first.replace("synthetic:link","synthetic:tampered"))),409);
        requireStatus(http.perform(put(linkPath()).header("Authorization","Bearer "+token)
            .contentType(MediaType.APPLICATION_JSON)
            .content(first.replace("req-one","new-request"))),409);
        assertEquals(1L,countFor("revision"));
        assertEquals(1L,countFor("request"));
        requireStatus(http.perform(get(linkPath()+"/revisions/1")
            .header("Authorization","Bearer "+token).param("companyPartyId",company)),200)
            .andExpect(jsonPath("$.link.evidenceReference").value("synthetic:link"));
    }

    @Test void sourcePermissionRevocationFencesCurrentRevisionAndHistory() throws Exception {
        savedDraft();
        String permission=admin.queryForObject(
            "SELECT id FROM public.hidra_identity_permission"+
            " WHERE code='simulation:manufacturer-characteristic:read'",String.class);
        admin.update("INSERT INTO public.hidra_identity_user_permission_grant"+
            "(id,user_id,permission_id,effect,scope_type,scope_reference_id,grant_reason,"+
            "emergency_access,valid_from,valid_to,status,created_at)"+
            " VALUES (?,?,?,'DENY','CUSTOM',?,'synthetic revoked source',false,"+
            "now()-interval '1 hour',now()+interval '1 hour','ACTIVE',now())",
            id(),user,permission,"simulation-source:"+sourceScope(company,source,REV));
        for(String path:List.of(linkPath(),linkPath()+"/revisions/1",linkPath()+"/history")){
            requireStatus(http.perform(get(path).header("Authorization","Bearer "+token)
                .param("companyPartyId",company)),403);
        }
        assertEquals(1L,countFor("revision"));
    }

    @Test void terminalApprovalRequiresIndependentHumanAndNeverReclassifiesDraft() throws Exception {
        savedDraft();
        String reviewer=distinctReviewer();
        reviewerGrants(reviewer,true);
        String reviewerJwt=reviewerToken(reviewer);
        var workflow=terminalWorkflow(reviewer,canonicalDraftDigest());
        requireStatus(http.perform(post(protectedApprovalPath())
            .header("Authorization","Bearer "+reviewerJwt)
            .contentType(MediaType.APPLICATION_JSON).content(approvalBody(workflow))),200)
            .andExpect(jsonPath("$.approval.approvingActorUserId").value(reviewer));
        requireStatus(http.perform(get(protectedApprovalPath())
            .header("Authorization","Bearer "+reviewerJwt).param("companyPartyId",company)),200)
            .andExpect(jsonPath("$.approval.targetSha256").value(workflow.digest()));
        requireStatus(http.perform(get(linkPath())
            .header("Authorization","Bearer "+token).param("companyPartyId",company)),200)
            .andExpect(jsonPath("$.link.state").value("DRAFT"));
        assertEquals(1L,countFor("approval"));
    }

    @Test void previousV1StationGraphDecoderKeepsExactPayloadAndRejectsCorruption() {
        assertEquals(PREVIOUS_STATION_GRAPH_WIRE_FORMAT,
            dz.sh.hidra.modules.topology.infrastructure.persistence.adapter
                .StationInternalGraphRevisionCodec.FORMAT);
        var graph=new dz.sh.hidra.modules.topology.domain.model.StationInternalGraph(
            "station-v1","graph-v1",
            dz.sh.hidra.modules.topology.domain.model.StationInternalGraph.Status.COMPLETE,
            List.of(
                new dz.sh.hidra.modules.topology.domain.model.StationInternalGraph.Header(
                    "in",dz.sh.hidra.modules.topology.domain.model.StationInternalGraph.HeaderKind.SUCTION,"s"),
                new dz.sh.hidra.modules.topology.domain.model.StationInternalGraph.Header(
                    "out",dz.sh.hidra.modules.topology.domain.model.StationInternalGraph.HeaderKind.DISCHARGE,"d")
            ),
            List.of(new dz.sh.hidra.modules.topology.domain.model.StationInternalGraph.Unit(
                "position-v1","station-v1","i","o")),
            List.of(
                new dz.sh.hidra.modules.topology.domain.model.StationInternalGraph.Edge("e1","s","i"),
                new dz.sh.hidra.modules.topology.domain.model.StationInternalGraph.Edge("e2","o","d")));
        var time=Instant.parse("2026-10-01T10:00:00Z");
        var revision=new dz.sh.hidra.modules.topology.domain.model.StationInternalGraphRevision(
            "station-v1","graph-v1",graph,
            dz.sh.hidra.modules.topology.domain.model.StationInternalGraphRevision.SourceClassification.SYNTHETIC,
            time,time,null,"synthetic:old-format",null,
            dz.sh.hidra.modules.topology.domain.model.StationInternalGraphRevision.Qualification.STRUCTURALLY_QUALIFIED);
        var codec=new dz.sh.hidra.modules.topology.infrastructure.persistence.adapter
            .StationInternalGraphRevisionCodec();
        byte[] prior=codec.encode(revision);
        assertEquals(revision,codec.decode(prior));
        assertEquals(codec.sha256(prior),codec.sha256(codec.encode(codec.decode(prior))));
        byte[] damaged=prior.clone();damaged[0]=(byte)0xFF;
        assertThrows(RuntimeException.class,()->codec.decode(damaged));
    }

}
