/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : EngineeringBaselineDraftNativeHttpPostgresIntegrationTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-11-03
 *
 * @Type        : Class
 * @Layer       : Test
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.qualification
 *
 * @Description : Qualifies native baseline draft authority and HTTP transaction semantics on fresh PostgreSQL.
 *
 */
package dz.sh.hidra.modules.topology.qualification;

import dz.sh.hidra.HidraApplication;
import dz.sh.hidra.modules.topology.domain.model.*;
import dz.sh.hidra.modules.topology.domain.model.StationInternalGraph.*;
import dz.sh.hidra.modules.topology.domain.model.StationInternalGraphRevision.*;
import dz.sh.hidra.modules.topology.domain.model.StationInstalledConfiguration.*;
import dz.sh.hidra.modules.topology.infrastructure.persistence.adapter.StationInternalGraphRevisionCodec;
import dz.sh.hidra.modules.topology.application.port.in.StationInstalledConfigurationUseCase;

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
class EngineeringBaselineDraftNativeHttpPostgresIntegrationTest {
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

        // Genuine Workflow start/task/decision runs on the disposable APP datasource.
        // The producer's companion PostgreSQL test provisions these same DML boundaries.
        // Limit mutable rows to instance/task; action/history are append-only.
        // Testcontainers-only roles: no production grants, schema migration or bypass.
        for(String relation:List.of("hidra_workflow_instance","hidra_workflow_task"))
            admin.execute("GRANT INSERT,UPDATE ON public."+relation+" TO "+APP);
        for(String relation:List.of("hidra_workflow_action","hidra_workflow_state_history"))
            admin.execute("GRANT INSERT ON public."+relation+" TO "+APP);

        for(String relation:List.of("hidra_simulation_manufacturer_characteristic_revision",
                "hidra_simulation_manufacturer_characteristic_qualification"))
            admin.execute("GRANT INSERT ON public."+relation+" TO "+PUBLISHER);
        for(String relation:List.of("revision","member","request"))
            admin.execute("GRANT INSERT ON public.hidra_topology_station_configuration_"+relation+" TO "+PUBLISHER);
        admin.execute("GRANT INSERT,UPDATE ON public.hidra_topology_station_configuration_head TO "+PUBLISHER);
        admin.execute("GRANT EXECUTE ON FUNCTION public.hidra_station_configuration_lock_sources() TO "+PUBLISHER);
        admin.execute("REVOKE SELECT ON public.hidra_topology_station_configuration_revision FROM "+APP);
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
        // Synthetic-only baseline credentials: no runtime permission is conferred by DDL privileges.
        for(String suffix:List.of("draft","revision","request")){
            admin.execute("REVOKE SELECT ON public.hidra_topology_engineering_baseline_"+suffix+" FROM "+APP);
            admin.execute("GRANT INSERT ON public.hidra_topology_engineering_baseline_"+suffix+" TO "+PUBLISHER);
        }
        admin.execute("GRANT UPDATE ON public.hidra_topology_engineering_baseline_draft TO "+PUBLISHER);
        for(String function:List.of("hidra_topology_baseline_lock_storage","hidra_topology_baseline_lock_sources","hidra_custody_baseline_lock_product_source"))
            admin.execute("GRANT EXECUTE ON FUNCTION public."+function+"() TO "+PUBLISHER);
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
    @Autowired dz.sh.hidra.modules.workflow.application.service.WorkflowApplicationService workflowApplication;
    @Autowired dz.sh.hidra.modules.workflow.application.port.in.ExecuteWorkflowTransitionUseCase workflowTransitions;
    @Autowired dz.sh.hidra.modules.workflow.application.port.out.WorkflowTaskRepositoryPort workflowTasks;
    @Autowired dz.sh.hidra.modules.topology.application.port.in.InstalledCharacteristicLinkUseCase nativeLinkOwner;
    @Autowired dz.sh.hidra.modules.topology.infrastructure.integration.TopologyInstalledCharacteristicLinkWorkflowTargetLookup targetLookup;
    @org.springframework.test.context.bean.override.mockito.MockitoSpyBean
    org.springframework.transaction.PlatformTransactionManager transactionManager;
    @org.springframework.test.context.bean.override.mockito.MockitoSpyBean
    dz.sh.hidra.modules.topology.application.service.ProtectedInstalledCharacteristicLinkAdmissionCoordinator linkCoordinator;
    @org.springframework.test.context.bean.override.mockito.MockitoSpyBean
    dz.sh.hidra.modules.topology.application.service.ProtectedEngineeringBaselineAdmissionCoordinator baselineCoordinator;
    @Autowired dz.sh.hidra.modules.identity.infrastructure.security.ProtectedOperationIdentityParticipant nativeIdentity;
    @Autowired dz.sh.hidra.modules.identity.application.contract.topology.TopologyEngineeringBaselineAuthorizationContract baselineAuthority;
    @Autowired @org.springframework.beans.factory.annotation.Qualifier("hidraJwtDecoder")
    org.springframework.security.oauth2.jwt.JwtDecoder nativeJwtDecoder;
    private JdbcTemplate admin;
    private MockMvc http;
    private String company,manufacturer,source,unit,link,user,token;
    private static String id(){return UUID.randomUUID().toString();}
    
    @BeforeEach void setup(){
        admin=new JdbcTemplate(new DriverManagerDataSource(PG.getJdbcUrl(),PG.getUsername(),PG.getPassword()));
        // Fixture-only owner reads must fail rather than wait on a lock held by this test.
        admin.setQueryTimeout(5);
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

    /** HAPI-605: independent native admissions may coexist; no credential is reused or weakened. */
    @Test void independentNativeAdmissionsRetainOneOriginalPhysicalTransaction() {
        useWorkflowActor(token,user);
        var firstPrepared=nativeIdentity.prepare();
        var secondPrepared=nativeIdentity.prepare();
        assertNotSame(firstPrepared,secondPrepared);
        var transaction=new org.springframework.transaction.support.TransactionTemplate(transactionManager);
        var observations=transaction.execute(status->{
            var first=nativeIdentity.admit(firstPrepared);
            var second=nativeIdentity.admit(secondPrepared);
            assertNotSame(first,second);
            var a=nativeIdentity.contribute(first);
            var b=nativeIdentity.contribute(second);
            assertEquals(a.backendPid(),b.backendPid());
            assertEquals(a.transactionId(),b.transactionId());
            assertEquals(a.evidence().bindingId(),b.evidence().bindingId());
            assertEquals(a.evidence().sessionId(),b.evidence().sessionId());
            assertEquals(user,a.evidence().userId());
            assertEquals(user,b.evidence().userId());
            nativeIdentity.requireAdmitted(first);
            nativeIdentity.requireAdmitted(second);
            return java.util.List.of(a,b);
        });
        assertNotNull(observations);
        assertEquals(2,observations.size());
    }

    /** A second independent admission does not make the first admission reusable. */
    @Test void composedNativeAdmissionsDoNotWaiveOneUseConsumption() {
        useWorkflowActor(token,user);
        var firstPrepared=nativeIdentity.prepare();
        var secondPrepared=nativeIdentity.prepare();
        var transaction=new org.springframework.transaction.support.TransactionTemplate(transactionManager);
        transaction.executeWithoutResult(status->{
            var first=nativeIdentity.admit(firstPrepared);
            var second=nativeIdentity.admit(secondPrepared);
            nativeIdentity.contribute(first);
            nativeIdentity.contribute(second);
            assertThrows(org.springframework.security.access.AccessDeniedException.class,
                ()->nativeIdentity.contribute(first));
            status.setRollbackOnly();
        });
    }
    private void baselineReadGrant(String scope) {
        grant("topology:engineering-baseline:read","ENGINEERING_BASELINE_DRAFT",scope);
    }
    private dz.sh.hidra.modules.identity.application.contract.topology.TopologyEngineeringBaselineAuthorizationContract.Request baselineRead(String pipeline) {
        return new dz.sh.hidra.modules.identity.application.contract.topology.TopologyEngineeringBaselineAuthorizationContract.Request(
            dz.sh.hidra.modules.identity.application.contract.topology.TopologyEngineeringBaselineAuthorizationContract.Action.READ,company,pipeline);
    }
    @Test void baselineCompanyGrantAloneCannotAuthorizePipelineScope() {
        String pipeline=id(); baselineReadGrant("topology-company:"+company);
        useWorkflowActor(token,user);
        var prepared=baselineAuthority.prepare();
        var transaction=new org.springframework.transaction.support.TransactionTemplate(transactionManager);
        assertThrows(org.springframework.security.access.AccessDeniedException.class,
            ()->transaction.execute(status->baselineAuthority.acquire(prepared,baselineRead(pipeline))));
    }
    @Test void baselinePipelineGrantAloneCannotAuthorizeCompanyScope() {
        String pipeline=id(); baselineReadGrant("topology-network:"+pipeline);
        useWorkflowActor(token,user);
        var prepared=baselineAuthority.prepare();
        var transaction=new org.springframework.transaction.support.TransactionTemplate(transactionManager);
        assertThrows(org.springframework.security.access.AccessDeniedException.class,
            ()->transaction.execute(status->baselineAuthority.acquire(prepared,baselineRead(pipeline))));
    }
    @Test void baselineRootGrantDoesNotConferExistingUnitSourceRead() {
        String pipeline=id(); baselineReadGrant("topology-company:"+company); baselineReadGrant("topology-network:"+pipeline);
        useWorkflowActor(token,user);
        var prepared=baselineAuthority.prepare();
        var transaction=new org.springframework.transaction.support.TransactionTemplate(transactionManager);
        assertThrows(org.springframework.security.access.AccessDeniedException.class,()->transaction.execute(status->{
            var admission=baselineAuthority.acquire(prepared,baselineRead(pipeline));
            assertEquals(user,baselineAuthority.contribute(admission).userId());
            baselineAuthority.requireSourceRead(admission,new dz.sh.hidra.modules.identity.application.contract.topology
                .TopologyEngineeringBaselineAuthorizationContract.Source(
                    dz.sh.hidra.modules.identity.application.contract.topology.TopologyEngineeringBaselineAuthorizationContract.SourceKind.INSTALLED_UNIT,unit));
            return null;
        }));
    }
    @Test void baselineAndIndependentSourcePermissionKeepOriginalNativeProvenance() {
        String pipeline=id(); baselineReadGrant("topology-company:"+company); baselineReadGrant("topology-network:"+pipeline);
        grant("topology:installed-unit:read","INSTALLED_UNIT","topology-company:"+company);
        useWorkflowActor(token,user);
        var prepared=baselineAuthority.prepare();
        var original=new java.util.concurrent.atomic.AtomicReference<
            dz.sh.hidra.modules.identity.application.contract.topology.TopologyEngineeringBaselineAuthorizationContract.Admission>();
        var transaction=new org.springframework.transaction.support.TransactionTemplate(transactionManager);
        transaction.executeWithoutResult(status->{
            var admission=baselineAuthority.acquire(prepared,baselineRead(pipeline)); original.set(admission);
            var origin=baselineAuthority.contribute(admission);
            assertEquals(user,origin.userId());
            var appJdbc=context.getBean(JdbcTemplate.class);
            assertEquals(appJdbc.queryForObject("SELECT pg_backend_pid()",Integer.class).intValue(),origin.backendPid());
            assertEquals(appJdbc.queryForObject("SELECT txid_current()",Long.class).longValue(),origin.transactionId());
            baselineAuthority.requireSourceRead(admission,new dz.sh.hidra.modules.identity.application.contract.topology
                .TopologyEngineeringBaselineAuthorizationContract.Source(
                    dz.sh.hidra.modules.identity.application.contract.topology.TopologyEngineeringBaselineAuthorizationContract.SourceKind.INSTALLED_UNIT,unit));
            baselineAuthority.requireCurrent(admission);
            assertFalse(baselineAuthority.finalFences(admission).isEmpty());
            // This proves authority participation only; no fake final-guard or baseline commit is asserted.
            status.setRollbackOnly();
        });
        assertThrows(org.springframework.security.access.AccessDeniedException.class,
            ()->baselineAuthority.requireCurrent(original.get()));
    }
    @Test void baselineAdmissionRejectsAuthenticatedActorReplacement() {
        String pipeline=id(); baselineReadGrant("topology-company:"+company); baselineReadGrant("topology-network:"+pipeline);
        useWorkflowActor(token,user);
        var prepared=baselineAuthority.prepare();
        var transaction=new org.springframework.transaction.support.TransactionTemplate(transactionManager);
        assertThrows(org.springframework.security.access.AccessDeniedException.class,()->transaction.execute(status->{
            var admission=baselineAuthority.acquire(prepared,baselineRead(pipeline));
            baselineAuthority.contribute(admission);
            SecurityContextHolder.getContext().setAuthentication(
                org.springframework.security.authentication.UsernamePasswordAuthenticationToken.authenticated(
                    "synthetic-replacement",null,java.util.List.of()));
            baselineAuthority.requireCurrent(admission);
            return null;
        }));
    }
    @Test void baselineRejectsStructuralPreparedAdmissionAndReadHandleCounterfeits() {
        useWorkflowActor(token,user);
        var transaction=new org.springframework.transaction.support.TransactionTemplate(transactionManager);
        assertThrows(org.springframework.security.access.AccessDeniedException.class,()->transaction.execute(status->
            baselineAuthority.acquire(new dz.sh.hidra.modules.identity.application.contract.topology
                .TopologyEngineeringBaselineAuthorizationContract.Prepared(){},baselineRead(id()))));
        assertThrows(org.springframework.security.access.AccessDeniedException.class,()->baselineAuthority.contribute(
            new dz.sh.hidra.modules.identity.application.contract.topology.TopologyEngineeringBaselineAuthorizationContract.Admission(){}));
        assertEquals(0,dz.sh.hidra.modules.identity.infrastructure.security
            .ProtectedEngineeringBaselineAuthorizationParticipant.ReadHandle.class.getConstructors().length);
    }

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
    
    /** The publisher remains untrusted; all fixture entries are synthetic schema-owner test data. */
    
    private record ActualWorkflow(String definition,String type,String purpose,
            String instance,String task,String action,String digest){}

    /** Actual Workflow owner start/task/transition; only configuration is synthetic SQL. */
    private void useWorkflowActor(String bearer,String expectedActor) {
        var jwt=nativeJwtDecoder.decode(bearer);
        assertEquals(expectedActor,jwt.getSubject());
        var authentication=new org.springframework.security.oauth2.server.resource.authentication
            .JwtAuthenticationToken(jwt,java.util.List.of());
        authentication.setAuthenticated(true);
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }
    
    private static final String PREVIOUS_STATION_GRAPH_WIRE_FORMAT =
            "HIDRA_TOPOLOGY_STATION_GRAPH_REVISION_V1";

    private static final Instant STATION_FROM=Instant.parse("2026-10-01T10:00:00Z");
    
    /** Each source is published through actual Simulation HTTP; each link through native Topology. */
    
private static final String BASELINE="/api/v1/topology/engineering-baselines";
    private String baselinePipeline(){
        String pipeline=id();
        String type=id();
        admin.update("INSERT INTO public.hidra_topology_pipeline_system_type(id,code,active,created_at,updated_at) VALUES(?,?,true,now(),now())",type,type);
        admin.update("INSERT INTO public.hidra_topology_pipeline_system(id,code,system_type_id,status,created_at,updated_at)"
            +" VALUES (?,?,?,'DRAFT',now(),now())",pipeline,pipeline,type);
        return pipeline;
    }
    private void baselineGrants(String pipeline){
        for(String action:List.of("create","read","update","validate")){
            grant("topology:engineering-baseline:"+action,"ENGINEERING_BASELINE_DRAFT","topology-company:"+company);
            grant("topology:engineering-baseline:"+action,"ENGINEERING_BASELINE_DRAFT","topology-network:"+pipeline);
        }
    }
    private String baselineCreateBody(String manifest,String pipeline,String request,String pins){
        return """
            {"manifestId":"%s","companyPartyId":"%s","pipelineSystemId":"%s","requestId":"%s",
             "effectiveFrom":"2026-10-01T00:00:00Z","effectiveUntil":null,"pins":%s}
            """.formatted(manifest,company,pipeline,request,pins);
    }
    private String baselineUpdateBody(String pipeline,String request,long expected){
        return """
            {"companyPartyId":"%s","pipelineSystemId":"%s","requestId":"%s","expectedVersion":%d,
             "effectiveFrom":"2026-10-02T00:00:00Z","effectiveUntil":null,"pins":[]}
            """.formatted(company,pipeline,request,expected);
    }
    private ResultActions createBaseline(String body)throws Exception{
        return http.perform(post(BASELINE).header("Authorization","Bearer "+token).contentType(MediaType.APPLICATION_JSON).content(body));
    }
    private long baselineRows(String suffix,String manifest){
        return admin.queryForObject("SELECT count(*) FROM public.hidra_topology_engineering_baseline_"+suffix
            +" WHERE manifest_id=?::uuid",Long.class,manifest);
    }
    @Test void baselineHttpCreateReadAndValidateKeepDraftIncompleteAndUnqualified()throws Exception{
        String pipeline=baselinePipeline(),manifest=id();baselineGrants(pipeline);
        requireStatus(createBaseline(baselineCreateBody(manifest,pipeline,id(),"[]")),200)
            .andExpect(jsonPath("$.version").value(1)).andExpect(jsonPath("$.capturedCompleteness.complete").value(false))
            .andExpect(jsonPath("$.engineeringQualified").value(false)).andExpect(jsonPath("$.authoritative").value(false))
            .andExpect(jsonPath("$.frozen").value(false));
        requireStatus(http.perform(get(BASELINE+"/"+manifest).header("Authorization","Bearer "+token)
            .param("companyPartyId",company).param("pipelineSystemId",pipeline)),200)
            .andExpect(jsonPath("$.pins").isEmpty()).andExpect(jsonPath("$.version").value(1));
        requireStatus(http.perform(post(BASELINE+"/"+manifest+"/validation").header("Authorization","Bearer "+token)
            .param("companyPartyId",company).param("pipelineSystemId",pipeline)),200)
            .andExpect(jsonPath("$.currentCompleteness.complete").value(false));
        assertEquals(1,baselineRows("draft",manifest));assertEquals(1,baselineRows("revision",manifest));assertEquals(1,baselineRows("request",manifest));
    }
    @Test void baselineExactRequestReplayDoesNotAppendAnotherRevision()throws Exception{
        String pipeline=baselinePipeline(),manifest=id();baselineGrants(pipeline);
        String body=baselineCreateBody(manifest,pipeline,id(),"[]");
        requireStatus(createBaseline(body),200).andExpect(jsonPath("$.replayed").value(false));
        requireStatus(createBaseline(body),200).andExpect(jsonPath("$.replayed").value(true));
        assertEquals(1,baselineRows("revision",manifest));assertEquals(1,baselineRows("request",manifest));
    }
    @Test void baselineChangedReplayAndStaleExpectedVersionDoNotMutateHistory()throws Exception{
        String pipeline=baselinePipeline(),manifest=id();baselineGrants(pipeline);
        String body=baselineCreateBody(manifest,pipeline,id(),"[]");
        requireStatus(createBaseline(body),200);
        requireStatus(createBaseline(body.replace("2026-10-01","2026-10-02")),409);
        String updated=baselineUpdateBody(pipeline,id(),1);
        requireStatus(http.perform(put(BASELINE+"/"+manifest).header("Authorization","Bearer "+token)
            .contentType(MediaType.APPLICATION_JSON).content(updated)),200).andExpect(jsonPath("$.version").value(2));
        requireStatus(http.perform(put(BASELINE+"/"+manifest).header("Authorization","Bearer "+token)
            .contentType(MediaType.APPLICATION_JSON).content(baselineUpdateBody(pipeline,id(),1))),409);
        requireStatus(http.perform(get(BASELINE+"/"+manifest+"/revisions/1").header("Authorization","Bearer "+token)
            .param("companyPartyId",company).param("pipelineSystemId",pipeline)),200).andExpect(jsonPath("$.version").value(1));
        assertEquals(2,baselineRows("revision",manifest));assertEquals(2,baselineRows("request",manifest));
    }
    @Test void baselineMissingRootAuthorityHidesExistingAndMissingDrafts()throws Exception{
        String pipeline=baselinePipeline(),manifest=id();
        requireStatus(createBaseline(baselineCreateBody(manifest,pipeline,id(),"[]")),403);
        for(String requested:List.of(manifest,id()))
            requireStatus(http.perform(get(BASELINE+"/"+requested).header("Authorization","Bearer "+token)
                .param("companyPartyId",company).param("pipelineSystemId",pipeline)),403);
        assertEquals(0,baselineRows("draft",manifest));
    }
    @Test void baselineWriteGrantCannotReadOrValidateTheCreatedDraft()throws Exception{
        String pipeline=baselinePipeline(),manifest=id();
        grant("topology:engineering-baseline:create","ENGINEERING_BASELINE_DRAFT","topology-company:"+company);
        grant("topology:engineering-baseline:create","ENGINEERING_BASELINE_DRAFT","topology-network:"+pipeline);
        requireStatus(createBaseline(baselineCreateBody(manifest,pipeline,id(),"[]")),200);
        requireStatus(http.perform(get(BASELINE+"/"+manifest).header("Authorization","Bearer "+token)
            .param("companyPartyId",company).param("pipelineSystemId",pipeline)),403);
        requireStatus(http.perform(post(BASELINE+"/"+manifest+"/validation").header("Authorization","Bearer "+token)
            .param("companyPartyId",company).param("pipelineSystemId",pipeline)),403);
    }
    @Test void baselineRootRightsCannotReadAnUnpermittedNetworkSource()throws Exception{
        String pipeline=baselinePipeline(),manifest=id();baselineGrants(pipeline);
        String context=id(),revision=id();
        String pins="[{\"kind\":\"NETWORK_PUBLICATION\",\"ownerId\":\""+context+"\",\"sourceId\":\""+context
            +"\",\"revisionId\":\""+revision+"\",\"sha256\":\""+"a".repeat(64)+"\"}]";
        requireStatus(createBaseline(baselineCreateBody(manifest,pipeline,id(),pins)),403);
        assertEquals(0,baselineRows("draft",manifest));assertEquals(0,baselineRows("revision",manifest));
    }
    @Test void baselineRevokedReadAndForeignCompanyCannotDiscloseRetainedDraft()throws Exception{
        String pipeline=baselinePipeline(),manifest=id();baselineGrants(pipeline);
        requireStatus(createBaseline(baselineCreateBody(manifest,pipeline,id(),"[]")),200);
        admin.update("UPDATE public.hidra_identity_user_permission_grant SET status='REVOKED',revoked_at=now() WHERE user_id=?"
            +" AND permission_id IN (SELECT id FROM public.hidra_identity_permission WHERE code='topology:engineering-baseline:read')",user);
        requireStatus(http.perform(get(BASELINE+"/"+manifest).header("Authorization","Bearer "+token)
            .param("companyPartyId",company).param("pipelineSystemId",pipeline)),403);
        requireStatus(http.perform(get(BASELINE+"/"+manifest).header("Authorization","Bearer "+token)
            .param("companyPartyId",id()).param("pipelineSystemId",pipeline)),403);
        assertEquals(1,baselineRows("revision",manifest));
    }
    @Test void baselineUnauthenticatedRequestsCannotReadOrCreateDrafts()throws Exception{
        String pipeline=baselinePipeline(),manifest=id();baselineGrants(pipeline);
        requireStatus(http.perform(post(BASELINE).contentType(MediaType.APPLICATION_JSON)
            .content(baselineCreateBody(manifest,pipeline,id(),"[]"))),401);
        requireStatus(http.perform(get(BASELINE+"/"+manifest).param("companyPartyId",company)
            .param("pipelineSystemId",pipeline)),401);
        assertEquals(0,baselineRows("draft",manifest));
    }
    @Test void baselineFailureAfterProvisionalWriteRollsBackDraftRevisionAndReceipt()throws Exception{
        String pipeline=baselinePipeline(),manifest=id();baselineGrants(pipeline);
        org.mockito.Mockito.doThrow(new IllegalStateException("synthetic baseline final preparation failure"))
            .when(baselineCoordinator).flushAndPrepare(org.mockito.ArgumentMatchers.any());
        try{requireStatus(createBaseline(baselineCreateBody(manifest,pipeline,id(),"[]")),503);}
        finally{org.mockito.Mockito.reset(baselineCoordinator);}
        for(String table:List.of("draft","revision","request"))assertEquals(0,baselineRows(table,manifest));
    }
    @Test void baselineLostPhysicalCommitAcknowledgementIsIndeterminateAndReplayIsStable()throws Exception{
        String pipeline=baselinePipeline(),manifest=id();baselineGrants(pipeline);
        String body=baselineCreateBody(manifest,pipeline,id(),"[]");
        var armed=new java.util.concurrent.atomic.AtomicReference<Thread>();var observed=new java.util.concurrent.atomic.AtomicBoolean();
        org.mockito.Mockito.doAnswer(invocation->{invocation.callRealMethod();armed.set(Thread.currentThread());return null;})
            .when(baselineCoordinator).finalGuard(org.mockito.ArgumentMatchers.any());
        org.mockito.Mockito.doAnswer(invocation->{
            if(armed.get()==Thread.currentThread()&&!observed.get()){
                invocation.callRealMethod();observed.set(true);armed.set(null);
                throw new org.springframework.transaction.TransactionSystemException("synthetic baseline acknowledgement lost");
            }
            return invocation.callRealMethod();
        }).when(transactionManager).commit(org.mockito.ArgumentMatchers.any(org.springframework.transaction.TransactionStatus.class));
        try{requireStatus(createBaseline(body),503);assertTrue(observed.get());}
        finally{org.mockito.Mockito.reset(baselineCoordinator,transactionManager);}
        assertEquals(1,baselineRows("revision",manifest));assertEquals(1,baselineRows("request",manifest));
        requireStatus(createBaseline(body),200).andExpect(jsonPath("$.replayed").value(true));
        assertEquals(1,baselineRows("revision",manifest));
    }
    @Test void baselineImmutableRevisionAndReceiptRejectDatabaseMutation()throws Exception{
        String pipeline=baselinePipeline(),manifest=id();baselineGrants(pipeline);
        requireStatus(createBaseline(baselineCreateBody(manifest,pipeline,id(),"[]")),200);
        assertThrows(org.springframework.dao.DataAccessException.class,()->admin.update(
            "UPDATE public.hidra_topology_engineering_baseline_revision SET findings='[]'::jsonb WHERE manifest_id=?::uuid",manifest));
        assertThrows(org.springframework.dao.DataAccessException.class,()->admin.update(
            "DELETE FROM public.hidra_topology_engineering_baseline_request WHERE manifest_id=?::uuid",manifest));
        var ordinary=new JdbcTemplate(new DriverManagerDataSource(PG.getJdbcUrl(),APP,PASSWORD));
        assertThrows(org.springframework.dao.DataAccessException.class,()->ordinary.queryForObject(
            "SELECT count(*) FROM public.hidra_topology_engineering_baseline_revision",Long.class));
    }
    @Test void baselineSourceLockContentionCannotPublishPartialDraft()throws Exception{
        String pipeline=baselinePipeline(),manifest=id();baselineGrants(pipeline);
        try(var writer=java.sql.DriverManager.getConnection(PG.getJdbcUrl(),PG.getUsername(),PG.getPassword())){
            writer.setAutoCommit(false);
            try(var statement=writer.createStatement()){
                statement.execute("LOCK TABLE public.hidra_topology_engineering_publication_snapshot IN ACCESS EXCLUSIVE MODE NOWAIT");
                requireStatus(createBaseline(baselineCreateBody(manifest,pipeline,id(),"[]")),503);
            }finally{writer.rollback();}
        }
        for(String table:List.of("draft","revision","request"))assertEquals(0,baselineRows(table,manifest));
    }
    /** Same submitted request is either applied once, replayed, or safely rejected under contention. */
    @Test void baselineConcurrentIdenticalCreateRequestsCannotDuplicateRevisionOrReceipt() throws Exception {
        String pipeline=baselinePipeline(),manifest=id();baselineGrants(pipeline);
        String body=baselineCreateBody(manifest,pipeline,id(),"[]");
        var statuses=race(() -> createBaseline(body).andReturn().getResponse().getStatus(),
            () -> createBaseline(body).andReturn().getResponse().getStatus());
        for(int code:statuses)assertTrue(code==200||code==409||code==503,
            "Concurrent duplicate draft writes must have a governed outcome, not "+code);
        if(!statuses.contains(200))requireStatus(createBaseline(body),200);
        requireStatus(createBaseline(body),200).andExpect(jsonPath("$.replayed").value(true));
        assertEquals(1,baselineRows("draft",manifest));
        assertEquals(1,baselineRows("revision",manifest));
        assertEquals(1,baselineRows("request",manifest));
    }
    /** Two distinct first writers never append two competing version-one revisions. */
    @Test void baselineConcurrentDifferentCreateRequestsRetainOneCommittedHead() throws Exception {
        String pipeline=baselinePipeline(),manifest=id();baselineGrants(pipeline);
        String first=baselineCreateBody(manifest,pipeline,id(),"[]");
        String second=baselineCreateBody(manifest,pipeline,id(),"[]");
        var statuses=race(() -> createBaseline(first).andReturn().getResponse().getStatus(),
            () -> createBaseline(second).andReturn().getResponse().getStatus());
        for(int code:statuses)assertTrue(code==200||code==409||code==503,
            "Concurrent changed drafts must fail closed, not "+code);
        assertTrue(statuses.stream().filter(x->x==200).count()<=1,
            "Two distinct create requests cannot both win the first revision");
        if(!statuses.contains(200))requireStatus(createBaseline(first),200);
        assertEquals(1,baselineRows("draft",manifest));
        assertEquals(1,baselineRows("revision",manifest));
        assertEquals(1,baselineRows("request",manifest));
        requireStatus(http.perform(get(BASELINE+"/"+manifest).header("Authorization","Bearer "+token)
            .param("companyPartyId",company).param("pipelineSystemId",pipeline)),200)
            .andExpect(jsonPath("$.version").value(1));
    }
    /** Two requests with the same expected version cannot both append version two. */
    @Test void baselineConcurrentExpectedVersionUpdatesKeepOneImmutableSuccessor() throws Exception {
        String pipeline=baselinePipeline(),manifest=id();baselineGrants(pipeline);
        requireStatus(createBaseline(baselineCreateBody(manifest,pipeline,id(),"[]")),200);
        var first=baselineUpdateBody(pipeline,id(),1);
        var second=baselineUpdateBody(pipeline,id(),1);
        var statuses=race(
            () -> http.perform(put(BASELINE+"/"+manifest).header("Authorization","Bearer "+token)
                .contentType(MediaType.APPLICATION_JSON).content(first)).andReturn().getResponse().getStatus(),
            () -> http.perform(put(BASELINE+"/"+manifest).header("Authorization","Bearer "+token)
                .contentType(MediaType.APPLICATION_JSON).content(second)).andReturn().getResponse().getStatus());
        for(int code:statuses)assertTrue(code==200||code==409||code==503,
            "Concurrent expected-version writes must fail closed, not "+code);
        assertTrue(statuses.stream().filter(x->x==200).count()<=1,
            "Two distinct expected-version requests cannot both commit");
        if(!statuses.contains(200))
            requireStatus(http.perform(put(BASELINE+"/"+manifest).header("Authorization","Bearer "+token)
                .contentType(MediaType.APPLICATION_JSON).content(first)),200);
        assertEquals(2,baselineRows("revision",manifest));
        assertEquals(2,baselineRows("request",manifest));
        requireStatus(http.perform(get(BASELINE+"/"+manifest+"/revisions/1")
            .header("Authorization","Bearer "+token).param("companyPartyId",company)
            .param("pipelineSystemId",pipeline)),200).andExpect(jsonPath("$.version").value(1));
    }
    private java.util.List<Integer> race(java.util.concurrent.Callable<Integer> one,
                                          java.util.concurrent.Callable<Integer> two) throws Exception {
        var pool=java.util.concurrent.Executors.newFixedThreadPool(2);
        var gate=new java.util.concurrent.CountDownLatch(1);
        try {
            var first=pool.submit(()->{gate.await();return one.call();});
            var second=pool.submit(()->{gate.await();return two.call();});
            gate.countDown();
            return java.util.List.of(first.get(20,java.util.concurrent.TimeUnit.SECONDS),
                                     second.get(20,java.util.concurrent.TimeUnit.SECONDS));
        }finally{gate.countDown();pool.shutdownNow();}
    }
    /** Fresh Flyway schema includes real composite keys and validated owner relationships. */
    @Test void baselinePhysicalOwnerTablesRetainValidatedKeysAndReferences() {
        for(String suffix:List.of("draft","revision","request")){
            String table="public.hidra_topology_engineering_baseline_"+suffix;
            assertEquals(1L,admin.queryForObject(
                "SELECT count(*) FROM pg_catalog.pg_constraint WHERE conrelid=?::regclass"
                +" AND contype='p' AND convalidated",Long.class,table));
            assertTrue(admin.queryForObject(
                "SELECT count(*) FROM pg_catalog.pg_constraint WHERE conrelid=?::regclass"
                +" AND contype='c' AND convalidated",Long.class,table)>=2L);
        }
        assertEquals(2L,admin.queryForObject(
            "SELECT count(*) FROM pg_catalog.pg_constraint"
            +" WHERE conrelid='public.hidra_topology_engineering_baseline_draft'::regclass"
            +" AND contype='f' AND convalidated",Long.class));
        for(String suffix:List.of("revision","request"))
            assertEquals(1L,admin.queryForObject(
                "SELECT count(*) FROM pg_catalog.pg_constraint WHERE conrelid=?::regclass"
                +" AND contype='f' AND convalidated",Long.class,
                "public.hidra_topology_engineering_baseline_"+suffix));
    }

    /** Dropping a validated owner relationship must fail the final physical guard. */
    @Test void baselineMissingRequiredForeignKeyCannotCommitAndRestoresFixture() throws Exception {
        String pipeline=baselinePipeline(),manifest=id();baselineGrants(pipeline);
        String table="public.hidra_topology_engineering_baseline_revision";
        String constraint=admin.queryForObject(
            "SELECT conname FROM pg_catalog.pg_constraint WHERE conrelid=?::regclass AND contype='f'",
            String.class,table);
        assertNotNull(constraint);
        assertTrue(constraint.matches("[a-z0-9_]+"));
        admin.execute("ALTER TABLE "+table+" DROP CONSTRAINT "+constraint);
        try{
            requireStatus(createBaseline(baselineCreateBody(manifest,pipeline,id(),"[]")),503);
        }finally{
            admin.execute("ALTER TABLE "+table+" ADD CONSTRAINT "+constraint
                +" FOREIGN KEY (company_party_id,pipeline_system_id,manifest_id)"
                +" REFERENCES public.hidra_topology_engineering_baseline_draft"
                +" (company_party_id,pipeline_system_id,manifest_id)"
                +" ON DELETE RESTRICT DEFERRABLE INITIALLY DEFERRED");
        }
        assertEquals(0,baselineRows("draft",manifest));
        assertEquals(0,baselineRows("revision",manifest));
        assertEquals(0,baselineRows("request",manifest));
    }

    /** Catalog shape, not a coincidental FK count, is an original-transaction safety fact. */
    @Test void baselineForeignKeySubstitutionWithEqualConstraintCountStillFailsClosed() throws Exception {
        String pipeline=baselinePipeline(),manifest=id();baselineGrants(pipeline);
        String table="public.hidra_topology_engineering_baseline_draft";
        String fk=admin.queryForObject(
            "SELECT conname FROM pg_catalog.pg_constraint"
            +" WHERE conrelid=?::regclass AND confrelid='public.hidra_topology_pipeline_system'::regclass"
            +" AND contype='f'",String.class,table);
        assertNotNull(fk);
        assertTrue(fk.matches("[a-z0-9_]+"));
        admin.execute("ALTER TABLE "+table+" DROP CONSTRAINT "+fk);
        try {
            admin.execute("ALTER TABLE "+table+" ADD CONSTRAINT "+fk
                +" FOREIGN KEY (company_party_id) REFERENCES public.hidra_party_party(id)"
                +" ON DELETE RESTRICT");
            assertEquals(2L,admin.queryForObject(
                "SELECT count(*) FROM pg_catalog.pg_constraint WHERE conrelid=?::regclass"
                +" AND contype='f' AND convalidated",Long.class,table));
            requireStatus(createBaseline(baselineCreateBody(manifest,pipeline,id(),"[]")),503);
        } finally {
            admin.execute("ALTER TABLE "+table+" DROP CONSTRAINT "+fk);
            admin.execute("ALTER TABLE "+table+" ADD CONSTRAINT "+fk
                +" FOREIGN KEY (pipeline_system_id)"
                +" REFERENCES public.hidra_topology_pipeline_system(id) ON DELETE RESTRICT");
        }
        assertEquals(0,baselineRows("draft",manifest));
        assertEquals(0,baselineRows("revision",manifest));
        assertEquals(0,baselineRows("request",manifest));
    }
    @Test void baselineFinalGuardCatalogDriftCannotCommitARevision()throws Exception{
        String pipeline=baselinePipeline(),manifest=id();baselineGrants(pipeline);
        admin.execute("ALTER TABLE public.hidra_topology_engineering_baseline_revision DISABLE TRIGGER trg_topology_baseline_revision_immutable");
        try{requireStatus(createBaseline(baselineCreateBody(manifest,pipeline,id(),"[]")),503);}
        finally{admin.execute("ALTER TABLE public.hidra_topology_engineering_baseline_revision ENABLE TRIGGER trg_topology_baseline_revision_immutable");}
        for(String table:List.of("draft","revision","request"))assertEquals(0,baselineRows(table,manifest));
    }

}
