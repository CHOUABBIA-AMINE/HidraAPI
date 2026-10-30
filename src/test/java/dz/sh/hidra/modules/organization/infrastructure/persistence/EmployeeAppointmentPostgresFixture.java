/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : EmployeeAppointmentPostgresFixture
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-30
 *
 * @Type        : Class
 * @Layer       : Test
 * @Module      : organization
 * @Package     : dz.sh.hidra.modules.organization.infrastructure.persistence
 *
 * @Description : Provides the disposable migrated database and native appointment qualification fixture.
 *
 */
package dz.sh.hidra.modules.organization.infrastructure.persistence;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import dz.sh.hidra.modules.identity.application.contract.organization.OrganizationProtectedAppointmentAuthorizationContract;
import dz.sh.hidra.modules.identity.application.contract.organization.OrganizationProtectedAppointmentAuthorizationContract.*;
import dz.sh.hidra.modules.identity.infrastructure.persistence.adapter.JdbcIdentityAuthorityFence;
import dz.sh.hidra.modules.organization.application.command.EmployeeAppointmentCommand;
import dz.sh.hidra.modules.organization.application.port.out.ProtectedEmployeeAppointmentRepositoryPort;
import dz.sh.hidra.modules.organization.application.port.out.ProtectedEmployeeAppointmentRepositoryPort.ReferenceUse;
import dz.sh.hidra.modules.organization.application.service.ProtectedEmployeeAppointmentAdmissionCoordinator;
import dz.sh.hidra.modules.organization.domain.value.AssignmentStatus;
import dz.sh.hidra.modules.organization.domain.value.AssignmentType;
import dz.sh.hidra.modules.organization.infrastructure.persistence.adapter.JdbcProtectedEmployeeAppointmentRepositoryAdapter;
import dz.sh.hidra.modules.party.application.contract.organization.OrganizationProtectedPartyEligibilityContract;
import dz.sh.hidra.modules.party.infrastructure.persistence.adapter.JdbcOrganizationProtectedPartyEligibilityAdapter;
import dz.sh.hidra.platform.persistence.ProtectedRelationFence;
import dz.sh.hidra.platform.persistence.ProtectedTransactionExecutor;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.flywaydb.core.Flyway;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.ConnectionHolder;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.support.TransactionSynchronizationManager;
import org.springframework.transaction.support.TransactionTemplate;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import tools.jackson.databind.json.JsonMapper;

/**
 * Migrated PostgreSQL, separated logins and actual atomic SQL proof/receipt checks.
 * Existing structural cases mock native Identity/executor and independently encode HMAC frames.
 * The genuineLocal/genuineNative/genuineExecution cases instead compose real LOCAL authentication,
 * native issuer, separate publisher login, protected executor, JDBC/JPA flush and observed commit.
 * EntityManager spies call the actual managed flush before fault injection or bounded coordination.
 * Non-LOCAL cases also execute real source verification/completion; only the external
 * directory transport and external decoder bean delegate are synthetic test boundaries.
 * No HTTP, deployment or industrial acceptance is established.
 */
@Testcontainers(disabledWithoutDocker = true)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@org.springframework.boot.test.context.SpringBootTest(classes=dz.sh.hidra.HidraApplication.class,
        webEnvironment=org.springframework.boot.test.context.SpringBootTest.WebEnvironment.MOCK,
        properties={"spring.flyway.enabled=true","spring.jpa.hibernate.ddl-auto=validate",
            // Bounded synthetic qualification ceiling; not an accepted operational SLA.
            "hidra.platform.persistence.execution.appointment-work-budget-ms=10000",
            "hidra.platform.security.jwt.hmac-secret=synthetic-appointment-runtime-key-32-bytes-only",
            "hidra.platform.security.ldap.enabled=true",
            "hidra.platform.security.ldap.url=ldap://synthetic.invalid:389",
            "hidra.platform.security.ldap.base-dn=dc=synthetic,dc=invalid",
            "hidra.platform.security.ldap.user-search-base=ou=people",
            "hidra.platform.security.ldap.user-search-filter=(uid={0})",
            "hidra.identity.ldap-verified-claim-receipt.enabled=true",
            "hidra.identity.oidc-verified-claim-receipt.enabled=true",
            "hidra.platform.security.jwt.token-issuer=hidra-api","hidra.platform.security.jwt.audience=hidra-api"})
@org.springframework.test.context.ActiveProfiles("test")
@org.springframework.test.annotation.DirtiesContext(classMode=org.springframework.test.annotation.DirtiesContext.ClassMode.AFTER_CLASS)
abstract class EmployeeAppointmentPostgresFixture {
    @org.springframework.beans.factory.annotation.Autowired org.springframework.web.context.WebApplicationContext runtimeWeb;


    // Isolate explicit-writer tests from ordinary background maintenance in this disposable database.
    @Container static final PostgreSQLContainer<?> POSTGRES = new PostgreSQLContainer<>("postgres:16")
            .withCommand("postgres","-c","autovacuum=off");
    static final String PASSWORD="disposable-classification-fixture", CONFIG="classification_config";
    static final String RUNTIME_APP="appointment_runtime_app", RUNTIME_PUBLISHER="appointment_runtime_publisher";
    @org.springframework.beans.factory.annotation.Autowired JdbcTemplate runtimeJdbc;
    @org.springframework.beans.factory.annotation.Autowired jakarta.persistence.EntityManager runtimeEntityManager;
    @org.springframework.beans.factory.annotation.Autowired ProtectedTransactionExecutor runtimeExecutor;
    @org.springframework.beans.factory.annotation.Autowired dz.sh.hidra.platform.persistence.ProtectedJpaTransactionManager runtimeTransactionManager;
    @org.springframework.beans.factory.annotation.Autowired OrganizationProtectedAppointmentAuthorizationContract runtimeIdentity;
    @org.springframework.beans.factory.annotation.Autowired OrganizationProtectedPartyEligibilityContract runtimeParty;
    @org.springframework.beans.factory.annotation.Autowired dz.sh.hidra.modules.identity.application.port.in.AuthenticateDirectUserUseCase runtimeLogin;
    @org.springframework.beans.factory.annotation.Autowired org.springframework.security.crypto.password.PasswordEncoder runtimePasswords;
    @org.springframework.beans.factory.annotation.Autowired dz.sh.hidra.modules.identity.infrastructure.configuration.OrganizationAppointmentCommitProofProperties runtimeKeys;
    @org.springframework.beans.factory.annotation.Autowired dz.sh.hidra.modules.identity.infrastructure.security.LdapCredentialVerificationAdapter runtimeDirectory;
    @org.springframework.beans.factory.annotation.Autowired dz.sh.hidra.modules.identity.infrastructure.security.IdentityOidcJwtAuthenticationConverter runtimeOidc;
    @org.springframework.beans.factory.annotation.Autowired dz.sh.hidra.modules.identity.application.port.in.CompleteAuthenticatedPrincipalUseCase runtimeCompletion;
    @org.springframework.beans.factory.annotation.Autowired dz.sh.hidra.modules.identity.application.port.out.OperationAuthenticationBindingRepositoryPort runtimeBindings;
    @org.springframework.beans.factory.annotation.Autowired dz.sh.hidra.modules.identity.application.port.out.BoundSourceClaimEvidenceRepositoryPort runtimeSourceClaims;
    @org.springframework.beans.factory.annotation.Autowired dz.sh.hidra.modules.identity.infrastructure.security.TrustedAuthenticationVerificationRegistry runtimeVerification;
    @org.springframework.test.context.bean.override.mockito.MockitoBean(name="externalOidcJwtDecoder")
    org.springframework.security.oauth2.jwt.JwtDecoder runtimeExternalDecoder;
    static final String RUNTIME_SOURCE="https://oidc.synthetic.invalid";
    static final javax.crypto.SecretKey RUNTIME_SOURCE_KEY=new javax.crypto.spec.SecretKeySpec(
            "synthetic-appointment-source-key-32-bytes-only".getBytes(java.nio.charset.StandardCharsets.UTF_8),"HmacSHA256");
    final org.springframework.security.oauth2.jwt.JwtEncoder runtimeSourceEncoder=
            org.springframework.security.oauth2.jwt.NimbusJwtEncoder.withSecretKey(RUNTIME_SOURCE_KEY)
                .algorithm(org.springframework.security.oauth2.jose.jws.MacAlgorithm.HS256).build();
    final org.springframework.security.oauth2.jwt.JwtDecoder runtimeSourceDecoder=
            org.springframework.security.oauth2.jwt.NimbusJwtDecoder.withSecretKey(RUNTIME_SOURCE_KEY)
                .macAlgorithm(org.springframework.security.oauth2.jose.jws.MacAlgorithm.HS256).build();
    record RuntimeOrigin(String actor,String provider,String link,String subject,String type) { }

    /** Bootstrap only: migrate as owner, then run the application and publisher as separate real logins. */
    @org.springframework.test.context.DynamicPropertySource
    static void runtimeProperties(org.springframework.test.context.DynamicPropertyRegistry r) throws Exception {
        // PER_CLASS Spring injection precedes the JUnit container beforeAll callback.
        // Start this same managed container before resolving its mapped JDBC endpoint.
        if(!POSTGRES.isRunning())POSTGRES.start();
        Flyway.configure().dataSource(POSTGRES.getJdbcUrl(),POSTGRES.getUsername(),POSTGRES.getPassword()).load().migrate();
        QualificationDriver.install();
        var admin=new JdbcTemplate(new DriverManagerDataSource(POSTGRES.getJdbcUrl(),POSTGRES.getUsername(),POSTGRES.getPassword()));
        assertEquals("off",admin.queryForObject("SHOW autovacuum",String.class));
        admin.execute("REVOKE CREATE ON SCHEMA public FROM PUBLIC");
        for(var role:List.of(RUNTIME_APP,RUNTIME_PUBLISHER)) {
            admin.execute("CREATE ROLE "+role+" LOGIN PASSWORD '"+PASSWORD+"'");
            admin.execute("GRANT USAGE ON SCHEMA public TO "+role);
            admin.execute("GRANT SELECT ON ALL TABLES IN SCHEMA public TO "+role);
        }
        for(var table:admin.queryForList("SELECT tablename FROM pg_catalog.pg_tables WHERE schemaname='public' AND tablename LIKE 'hidra_identity_%'",String.class)) {
            if(!table.matches("hidra_identity_[a-z0-9_]+"))throw new IllegalStateException("Unexpected fixture relation");
            admin.execute("GRANT INSERT,UPDATE,DELETE ON public."+table+" TO "+RUNTIME_APP);
        }
        for(var fence:JdbcIdentityAuthorityFence.requiredRelationFences())
            if(fence.mode()!=ProtectedRelationFence.Mode.ACCESS_SHARE)
                admin.execute("GRANT UPDATE ON "+fence.relation()+" TO "+RUNTIME_PUBLISHER);
        for(var role:List.of(RUNTIME_APP,RUNTIME_PUBLISHER)) {
            admin.execute("REVOKE ALL ON public.hidra_identity_org_commit_key_event,public.hidra_identity_org_commit_key_head,public.hidra_identity_org_commit_proof_use FROM "+role);
            admin.execute("REVOKE INSERT,UPDATE,DELETE ON public.hidra_identity_external_mapping_generation FROM "+role);
        }
        admin.execute("GRANT UPDATE ON public.hidra_party_party TO "+RUNTIME_PUBLISHER);
        for(var table:List.of("employee","unit","position","appointment_binding_head","appointment_policy_head","appointment_lock_key","employee_assignment"))
            admin.execute("GRANT UPDATE ON public.hidra_org_"+table+" TO "+RUNTIME_PUBLISHER);
        admin.execute("GRANT INSERT ON public.hidra_org_appointment_lock_key,public.hidra_org_employee_assignment,public.hidra_org_appointment_event TO "+RUNTIME_PUBLISHER);
        admin.execute("GRANT EXECUTE ON FUNCTION public.hidra_org_appointment_exact_id(text),public.hidra_identity_org_commit_key_context(boolean),"
                +"public.hidra_org_appointment_final_guard(uuid,uuid,uuid,text,text,text,text,timestamptz,timestamptz,integer,bigint,bytea,bytea,timestamptz),"
                +"public.hidra_org_appointment_read_guard(jsonb,timestamptz,timestamptz,integer,bigint,timestamptz) TO "+RUNTIME_PUBLISHER);
        r.add("spring.datasource.url",POSTGRES::getJdbcUrl);r.add("spring.datasource.username",()->RUNTIME_APP);
        r.add("spring.datasource.password",()->PASSWORD);
        r.add("spring.datasource.driver-class-name",()->QualificationDriver.class.getName());
        r.add("spring.flyway.url",POSTGRES::getJdbcUrl);
        r.add("spring.flyway.user",POSTGRES::getUsername);r.add("spring.flyway.password",POSTGRES::getPassword);
        r.add("hidra.platform.persistence.publication.enabled",()->true);
        r.add("hidra.platform.persistence.publication.username",()->RUNTIME_PUBLISHER);
        r.add("hidra.platform.persistence.publication.password",()->PASSWORD);
    }

    /** Real persisted LOCAL credential, native token and exact resource grants; no native issuer mock. */
    String runtimeActor(Fixture f) throws Exception {
        return runtimeActor(f,"LOCAL").actor();
    }

    RuntimeOrigin runtimeActor(Fixture f,String type) throws Exception {
        assertTrue(List.of("LOCAL","LDAP","ACTIVE_DIRECTORY","OIDC").contains(type));
        String actor=id(),provider=id();
        admin("UPDATE public.hidra_identity_provider SET status='INACTIVE' WHERE status='ACTIVE'");
        admin("INSERT INTO public.hidra_identity_user(id,username,user_type,status,failed_login_count,created_at,updated_at) VALUES (?,?,'HUMAN','ACTIVE',0,now(),now())",actor,actor);
        admin("INSERT INTO public.hidra_identity_provider(id,code,name,provider_type,issuer_uri,directory_base_dn,user_search_base,status,sync_enabled,just_in_time_provisioning_enabled,created_at,updated_at) VALUES (?,?,'Runtime fixture',?,?,?,?,'ACTIVE',false,false,now(),now())",provider,provider,type,
                type.equals("OIDC")?RUNTIME_SOURCE:null,type.equals("LDAP")||type.equals("ACTIVE_DIRECTORY")?"dc=synthetic,dc=invalid":null,
                type.equals("LDAP")||type.equals("ACTIVE_DIRECTORY")?"ou=people":null);
        String link=null,subject=null;
        if(type.equals("LOCAL")) {
            admin("INSERT INTO public.hidra_identity_local_credential(id,user_id,password_hash,credential_status,created_at,updated_at) VALUES (?,?,?,'ACTIVE',now(),now())",id(),actor,runtimePasswords.encode("runtime-synthetic-password"));
        } else {
            link=id();subject=" exact-runtime-é-"+link+" ";
            admin("INSERT INTO public.hidra_identity_external_identity(id,user_id,identity_provider_id,external_subject,status,created_at,updated_at) VALUES (?,?,?,?,'LINKED',now(),now())",link,actor,provider,subject);
            String role=id();
            admin("INSERT INTO public.hidra_identity_role(id,code,role_type,status,created_at,updated_at) VALUES (?,?,'EXTERNAL_MAPPED','ACTIVE',now(),now())",role,role);
            admin("INSERT INTO public.hidra_identity_external_role_mapping(id,identity_provider_id,role_id,external_role_code,claim_name,mapping_mode,status,created_at,updated_at) VALUES (?,?,?,?,?,'DIRECT_GRANT','ACTIVE',now(),now())",
                    id(),provider,role,"runtime-observed-group",type.equals("OIDC")?"groups":"memberOf");
        }
        for(var action:Action.values()) {
            // Grants are fixture facts, not one oversized operation/admission.
            // Union valid one-pair requests; company/employee grants remain unique.
            var requirements=new java.util.LinkedHashSet<Requirement>();
            for(var unit:f.units())requirements.addAll(new Request(action,
                    List.of(new CompanyUnit(f.company(),unit)),action==Action.READ_POLICY?null:f.employee()).requirements());
            for(var requirement:requirements) {
                String code=OrganizationProtectedAppointmentAuthorizationContract.permissionCode(action,requirement.kind());
                // Permission definitions are not grants and are not seeded by this fixture's migrations.
                // Match the genuine Identity integration fixture before inserting a scoped grant.
                admin("INSERT INTO public.hidra_identity_permission(id,code,permission_domain,resource_type,action,sensitive,status,created_at,updated_at) VALUES (?,?,'organization',?,?,false,'ACTIVE',now(),now()) ON CONFLICT (code) DO NOTHING",id(),code,requirement.kind().resourceType(),action.name().toLowerCase(java.util.Locale.ROOT).replace('_','-'));
                try(var c=owner()) {
                    assertEquals("1",scalar(c,"SELECT count(*) FROM public.hidra_identity_permission WHERE code=? AND permission_domain='organization' AND resource_type=? AND status='ACTIVE'",code,requirement.kind().resourceType()));
                }
                admin("INSERT INTO public.hidra_identity_user_permission_grant(id,user_id,permission_id,effect,scope_type,scope_reference_id,grant_reason,emergency_access,valid_from,valid_to,status,created_at) SELECT ?,?,id,'GRANT',?,?,'runtime fixture',false,now()-interval '1 hour',now()+interval '1 hour','ACTIVE',now() FROM public.hidra_identity_permission WHERE code=?",id(),actor,requirement.kind()==ResourceKind.UNIT?"ORGANIZATION_UNIT":"CUSTOM",requirement.reference(),code);
                try(var c=owner()) {
                    assertEquals("1",scalar(c,"SELECT count(*) FROM public.hidra_identity_user_permission_grant g JOIN public.hidra_identity_permission p ON p.id=g.permission_id WHERE g.user_id=? AND p.code=? AND g.scope_type=? AND g.scope_reference_id=? AND g.effect='GRANT' AND g.status='ACTIVE'",actor,code,requirement.kind()==ResourceKind.UNIT?"ORGANIZATION_UNIT":"CUSTOM",requirement.reference()));
                }
            }
        }
        long prior;try(var c=owner()){prior=Long.parseLong(scalar(c,"SELECT coalesce(max(revision),0) FROM public.hidra_identity_org_commit_key_event"));}
        long revision;try(var c=DriverManager.getConnection(POSTGRES.getJdbcUrl(),"final_proof_admin",PASSWORD)) {
            revision=Long.parseLong(scalar(c,"SELECT public.hidra_identity_org_commit_admin(?,'runtime-key',?::uuid,?::name,?)",prior,SQL_AUDIENCE.toString(),RUNTIME_PUBLISHER,sqlKey()));
        }
        runtimeKeys.setEnabled(true);runtimeKeys.setKeyId("runtime-key");runtimeKeys.setKeyRevision(revision);
        runtimeKeys.setSigningKeyBase64(java.util.Base64.getEncoder().encodeToString(sqlKey()));runtimeKeys.initialize();
        assertEquals(RUNTIME_APP,runtimeJdbc.queryForObject("SELECT session_user",String.class));
        assertTrue(runtimeJdbc.queryForObject("SELECT NOT pg_catalog.has_table_privilege(current_user,'public.hidra_org_employee_assignment','INSERT,UPDATE,DELETE,TRUNCATE') AND NOT pg_catalog.pg_has_role(current_user,?::name,'MEMBER')",Boolean.class,RUNTIME_PUBLISHER));
        try(var publisher=DriverManager.getConnection(POSTGRES.getJdbcUrl(),RUNTIME_PUBLISHER,PASSWORD)) {
            assertEquals(RUNTIME_PUBLISHER,scalar(publisher,"SELECT session_user"));
            assertEquals("safe",scalar(publisher,"SELECT CASE WHEN current_user=session_user AND NOT pg_catalog.has_schema_privilege(current_user,'public','CREATE') AND NOT pg_catalog.has_table_privilege(current_user,'public.hidra_org_employee_assignment','DELETE,TRUNCATE,TRIGGER') AND NOT pg_catalog.has_function_privilege(current_user,'public.hidra_org_activate_appointment_binding(text,text,text,bigint)','EXECUTE') THEN 'safe' ELSE 'unsafe' END"));
        }
        var origin=new RuntimeOrigin(actor,provider,link,subject,type);
        runtimeAuthenticate(origin);return origin;
    }

    void runtimeAuthenticate(String actor) {
        var result=runtimeLogin.authenticate(new dz.sh.hidra.modules.identity.application.model.DirectAuthenticationCommand(
                dz.sh.hidra.modules.identity.domain.value.ProviderType.LOCAL,actor,"runtime-synthetic-password","synthetic-ip","synthetic-agent","runtime-correlation"));
        runtimeBearer(result);
    }

    /** Real capture/completion; the directory fixture supplies only the authenticated network callback. */
    void runtimeAuthenticate(RuntimeOrigin origin) throws Exception {
        if(origin.type().equals("LOCAL")){runtimeAuthenticate(origin.actor());return;}
        dz.sh.hidra.modules.identity.application.model.AuthenticationResult result;
        if(origin.type().equals("OIDC")) {
            doAnswer(call->runtimeSourceDecoder.decode(call.getArgument(0)))
                    .when(runtimeExternalDecoder).decode(anyString());
            var source=runtimeSourceEncoder.encode(org.springframework.security.oauth2.jwt.JwtEncoderParameters.from(
                    org.springframework.security.oauth2.jwt.JwtClaimsSet.builder().issuer(RUNTIME_SOURCE)
                        .subject(origin.subject()).audience(List.of("hidra-api"))
                        .issuedAt(Instant.now().minusSeconds(1))
                        .expiresAt(Instant.now().plusSeconds(300).truncatedTo(java.time.temporal.ChronoUnit.SECONDS))
                        .claim("groups",List.of("runtime-observed-group")).build()));
            var verified=runtimeOidc.convert(runtimeSourceDecoder.decode(source.getTokenValue()));
            SecurityContextHolder.getContext().setAuthentication(verified);
            var p=(dz.sh.hidra.modules.identity.domain.model.HidraPrincipal)verified.getPrincipal();
            result=runtimeCompletion.complete(new dz.sh.hidra.modules.identity.application.model.AuthenticatedPrincipalInput(
                    p.userId(),p.username(),p.displayName(),p.authenticationType(),p.identityProviderId(),
                    p.roles(),p.permissions(),p.externalIdentityId(),null),"synthetic-ip","synthetic-agent","runtime-correlation");
        } else {
            var original=org.springframework.test.util.ReflectionTestUtils.getField(runtimeDirectory,"ldapTemplate");
            var template=mock(org.springframework.ldap.core.LdapTemplate.class);
            var context=mock(javax.naming.directory.DirContext.class);
            var attrs=new javax.naming.directory.BasicAttributes(true);
            attrs.put("entryUUID",origin.subject());attrs.put("uid",origin.actor());
            attrs.put("memberOf","runtime-observed-group");
            when(context.getAttributes(any(javax.naming.Name.class),any(String[].class))).thenReturn(attrs);
            when(template.authenticate(anyString(),anyString(),anyString(),
                    any(org.springframework.ldap.core.AuthenticatedLdapEntryContextCallback.class))).thenAnswer(call->{
                ((org.springframework.ldap.core.AuthenticatedLdapEntryContextCallback)call.getArgument(3)).executeWithContext(
                        context,new org.springframework.ldap.core.LdapEntryIdentification(
                            new javax.naming.ldap.LdapName("uid=synthetic,ou=people,dc=synthetic,dc=invalid"),
                            new javax.naming.ldap.LdapName("uid=synthetic,ou=people")));
                return true;
            });
            try {
                org.springframework.test.util.ReflectionTestUtils.setField(runtimeDirectory,"ldapTemplate",template);
                result=runtimeLogin.authenticate(new dz.sh.hidra.modules.identity.application.model.DirectAuthenticationCommand(
                        dz.sh.hidra.modules.identity.domain.value.ProviderType.valueOf(origin.type()),origin.actor(),
                        "runtime-synthetic-password","synthetic-ip","synthetic-agent","runtime-correlation"));
                verify(template).authenticate(eq("ou=people"),eq("(uid="+origin.actor()+")"),eq("runtime-synthetic-password"),
                        any(org.springframework.ldap.core.AuthenticatedLdapEntryContextCallback.class));
            } finally {org.springframework.test.util.ReflectionTestUtils.setField(runtimeDirectory,"ldapTemplate",original);}
        }
        var binding=runtimeBindings.findByNativeIssuerAndJti("hidra-api",result.jti()).orElseThrow();
        runtimeVerification.validatePersistentOrigin(binding);
        var claims=runtimeSourceClaims.findByBindingId(binding.bindingId()).orElseThrow();
        runtimeVerification.validatePersistentSourceClaimOrigin(binding,claims);
        assertEquals(origin.type(),binding.authenticationType());assertEquals(origin.actor(),binding.userId());
        assertEquals(origin.provider(),binding.providerId());
        assertEquals(origin.link(),binding.externalIdentityId());assertEquals(origin.subject(),binding.externalSubject());
        assertEquals(origin.type().equals("ACTIVE_DIRECTORY")?"LDAP":origin.type(),binding.sessionProtocol());
        assertEquals(java.util.Set.of("runtime-observed-group"),claims.coverage().values(origin.type().equals("OIDC")?"groups":"memberOf"));
        if(origin.type().equals("OIDC")){assertEquals(RUNTIME_SOURCE,binding.sourceIssuer());assertNotNull(binding.sourceExpiresAt());}
        else {assertNull(binding.sourceIssuer());assertNull(binding.sourceExpiresAt());}
        runtimeBearer(result);
    }

    void runtimeBearer(dz.sh.hidra.modules.identity.application.model.AuthenticationResult result) {
        var jwt=org.springframework.security.oauth2.jwt.Jwt.withTokenValue(result.accessToken()).header("alg","HS256")
                .subject("untrusted-wrapper").issuedAt(Instant.now().minusSeconds(1)).expiresAt(Instant.now().plusSeconds(300)).build();
        SecurityContextHolder.getContext().setAuthentication(new org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken(jwt,List.of()));
    }

    dz.sh.hidra.modules.organization.application.service.EmployeeAppointmentLifecycleApplicationService runtimeService(jakarta.persistence.EntityManager em) {
        // Genuine ordinary calls use the development default; the injected 10s executor is reserved for native-expiry qualification.
        var developmentExecutor=new ProtectedTransactionExecutor(runtimeTransactionManager,runtimeJdbc.getDataSource(),
                dz.sh.hidra.platform.configuration.ProtectedExecutionBudgetProperties.defaults());
        return runtimeService(em,developmentExecutor);
    }

    dz.sh.hidra.modules.organization.application.service.EmployeeAppointmentLifecycleApplicationService runtimeService(
            jakarta.persistence.EntityManager em,ProtectedTransactionExecutor executor) {
        var repository=new JdbcProtectedEmployeeAppointmentRepositoryAdapter(runtimeJdbc,runtimeIdentity,runtimeParty,em);
        var coordinator=new ProtectedEmployeeAppointmentAdmissionCoordinator(runtimeIdentity,runtimeParty,repository,executor);
        return new dz.sh.hidra.modules.organization.application.service.EmployeeAppointmentLifecycleApplicationService(coordinator,executor,repository);
    }

    /** Complete synthetic service call, including preparation and observed commit, never HTTP latency. */
    static <T> T measuredRuntime(String stage,java.util.function.Supplier<T> action) {
        long began=System.nanoTime();boolean success=false;
        try {T result=tracedRuntime("UNCOORDINATED_"+stage,new RuntimeTrace(),action);success=true;return result;}
        finally {System.out.println("HAPI_778_TIMING_QUALIFICATION stage="+stage
                +" success="+success+" callElapsedNs="+(System.nanoTime()-began)
                +" workCeilingMs="+dz.sh.hidra.platform.configuration.ProtectedExecutionBudgetProperties.DEFAULT_APPOINTMENT_WORK_BUDGET_MILLIS
                +" operationallyAccepted=false");}
    }















    void runtimeExternalLifecycle(String type) throws Exception {
        var f=fixture(false);var origin=runtimeActor(f,type);var service=runtimeService(runtimeEntityManager);
        var command=create(f,id());var created=runtimeExternalCommit(type+"_CREATE",()->service.create(command));
        String assignment=created.after().assignmentId();
        assertFalse(created.historicalReplay());assertEquals(1,created.after().appointmentVersion());
        var corrected=runtimeExternalCommit(type+"_CORRECT",()->service.correct(new EmployeeAppointmentCommand.Correct(
                id(),f.company(),assignment,1,f.units().get(0),f.position(),AssignmentType.PRIMARY,
                AssignmentStatus.ACTIVE,START,null,1,1,REASON)));
        assertEquals(created.after(),corrected.before());assertEquals(2,corrected.after().appointmentVersion());
        var transferred=runtimeExternalCommit(type+"_TRANSFER",()->service.transfer(new EmployeeAppointmentCommand.Transfer(
                id(),f.company(),assignment,2,f.units().get(1),f.position(),AssignmentType.PRIMARY,
                START.plusSeconds(3600),null,1,1,REASON)));
        assertEquals(corrected.after(),transferred.before());assertEquals(3,transferred.after().appointmentVersion());
        assertNotEquals(assignment,transferred.successor().assignmentId());
        var ended=runtimeExternalCommit(type+"_END",()->service.end(new EmployeeAppointmentCommand.End(
                id(),f.company(),transferred.successor().assignmentId(),1,START.plusSeconds(7200),REASON)));
        assertEquals(transferred.successor(),ended.before());assertEquals(2,ended.after().appointmentVersion());
        assertEquals(START.plusSeconds(7200),ended.after().snapshot().appointment().assignment().validTo());
        runtimeAuthenticate(origin);
        var replay=service.create(command);
        assertTrue(replay.historicalReplay());assertEquals(created.eventId(),replay.eventId());assertEquals(created.after(),replay.after());
        assertEquals(4,service.employeeHistory(dz.sh.hidra.modules.organization.application.query.EmployeeAppointmentQuery.History.employee(
                f.company(),f.employee(),100,null)).entries().size());
        assertEquals(3,service.assignmentHistory(dz.sh.hidra.modules.organization.application.query.EmployeeAppointmentQuery.History.assignment(
                f.company(),assignment,100,null)).entries().size());
        assertEquals(created.after(),service.exactVersion(new dz.sh.hidra.modules.organization.application.query.EmployeeAppointmentQuery.ExactVersion(
                f.company(),assignment,1)));
        assertFalse(service.positionHistory(dz.sh.hidra.modules.organization.application.query.EmployeeAppointmentQuery.History.position(
                f.company(),f.units().get(0),f.position(),100,null)).entries().isEmpty());
        assertEquals(f.company(),service.readPolicy(new dz.sh.hidra.modules.organization.application.query.EmployeeAppointmentQuery.Policy(
                f.company(),f.units().get(0))).companyPartyId());
        assertEquals(2,service.currentAssignments(runtimeCurrent(f.employee(),null,0,10)).totalElements());
        try(var c=owner()) {
            assertEquals("4",scalar(c,"SELECT count(*) FROM public.hidra_org_appointment_event e JOIN public.hidra_identity_operation_binding b ON b.binding_id=e.actor_binding_id::text JOIN public.hidra_identity_bound_source_claim_evidence s ON s.binding_id=b.binding_id WHERE e.employee_id=? AND e.actor_user_id=? AND b.authentication_type=? AND b.external_identity_id=?",
                    f.employee(),origin.actor(),type,origin.link()));
        }
        String before=runtimeEffects(f,origin.actor());
        assertTrue(before.startsWith("2,4,4,4,"),"Four native proofs/receipts must accompany the two retained projections");
        // A new nonoverlapping appointment reaches real managed flush, then must roll back every provisional effect.
        var next=new EmployeeAppointmentCommand.Create(id(),f.company(),f.employee(),f.units().get(0),f.position(),
                AssignmentType.PRIMARY,START.plusSeconds(10800),null,1,1);
        var em=spy(runtimeEntityManager);var flushTrace=new RuntimeTrace();
        doAnswer(call->{runtimeEntityManager.flush();throw new IllegalStateException("synthetic external-origin post-flush fault");}).when(em).flush();
        assertThrows(RuntimeException.class,()->tracedRuntime(type+"_FLUSH_ROLLBACK",flushTrace,()->runtimeService(em).create(next)));
        verify(em).flush();flushTrace.rejected();assertEquals(0,flushTrace.guardBegan);
        assertEquals(before,runtimeEffects(f,origin.actor()));
        var oldAuthentication=SecurityContextHolder.getContext().getAuthentication();
        admin("UPDATE public.hidra_identity_external_identity SET status='UNLINKED' WHERE id=?",origin.link());
        SecurityContextHolder.getContext().setAuthentication(oldAuthentication);
        var deniedEm=spy(runtimeEntityManager);var deniedService=runtimeService(deniedEm);
        var deniedTrace=new RuntimeTrace();
        assertThrows(RuntimeException.class,()->tracedRuntime(type+"_REVOKED_LINK_REPLAY",deniedTrace,()->deniedService.create(command)));
        deniedTrace.rejected();assertEquals(0,deniedTrace.guardBegan);verify(deniedEm,never()).flush();
        assertThrows(RuntimeException.class,()->deniedService.create(next));
        assertThrows(RuntimeException.class,()->deniedService.employeeHistory(
                dz.sh.hidra.modules.organization.application.query.EmployeeAppointmentQuery.History.employee(f.company(),f.employee(),100,null)));
        assertThrows(RuntimeException.class,()->runtimeAuthenticate(origin));
        assertEquals(before,runtimeEffects(f,origin.actor()));
        assertEquals(RUNTIME_APP,runtimeJdbc.queryForObject("SELECT session_user",String.class));
    }

    static dz.sh.hidra.modules.organization.application.dto.EmployeeAppointmentResult.MutationReceipt runtimeExternalCommit(
            String stage,java.util.function.Supplier<dz.sh.hidra.modules.organization.application.dto.EmployeeAppointmentResult.MutationReceipt> action) {
        var trace=new RuntimeTrace();var result=tracedRuntime(stage,trace,action);trace.committed();return result;
    }




    @org.junit.jupiter.api.AfterEach void clearRuntimeAuthentication() {SecurityContextHolder.clearContext();}

    /** Test-only driver: passive observation, plus one separately armed expiry fault below the wrappers. */
    public static final class QualificationDriver implements java.sql.Driver {
        private static final QualificationDriver INSTANCE=new QualificationDriver();
        private final org.postgresql.Driver delegate=new org.postgresql.Driver();
        private static boolean installed;
        private static volatile String observedUrl;
        public QualificationDriver() { }
        static synchronized void install() throws SQLException {
            if(installed)return;
            observedUrl=POSTGRES.getJdbcUrl();
            // Publisher Hikari discovers its driver from the original metadata URL.
            // Prefer this URL-limited observer, then restore every existing PG driver.
            var drivers=java.util.Collections.list(DriverManager.getDrivers()).stream()
                    .filter(d->d instanceof org.postgresql.Driver).toList();
            try {
                for(var driver:drivers)DriverManager.deregisterDriver(driver);
                DriverManager.registerDriver(INSTANCE);installed=true;
            } finally {
                for(var driver:drivers)DriverManager.registerDriver(driver);
            }
            assertSame(INSTANCE,DriverManager.getDriver(POSTGRES.getJdbcUrl()));
        }
        static synchronized void uninstall() throws SQLException {
            if(installed){DriverManager.deregisterDriver(INSTANCE);installed=false;}
        }
        @Override public boolean acceptsURL(String url){return observedUrl!=null&&observedUrl.equals(url);}
        @Override public Connection connect(String url,java.util.Properties properties) throws SQLException {
            if(!acceptsURL(url))return null;
            var connection=delegate.connect(url,properties);
            return RUNTIME_PUBLISHER.equals(properties.getProperty("user"))?observedConnection(connection):connection;
        }
        @Override public java.sql.DriverPropertyInfo[] getPropertyInfo(String url,java.util.Properties p) throws SQLException {return delegate.getPropertyInfo(url,p);}
        @Override public int getMajorVersion(){return delegate.getMajorVersion();}
        @Override public int getMinorVersion(){return delegate.getMinorVersion();}
        @Override public boolean jdbcCompliant(){return delegate.jdbcCompliant();}
        @Override public java.util.logging.Logger getParentLogger() throws java.sql.SQLFeatureNotSupportedException {return delegate.getParentLogger();}
    }

    @org.junit.jupiter.api.AfterAll void removeQualificationDriver() throws SQLException {QualificationDriver.uninstall();}

    static final ThreadLocal<RuntimeTrace> RUNTIME_TRACE=new ThreadLocal<>();
    static final ThreadLocal<NativeExpiryFault> NATIVE_EXPIRY_FAULT=new ThreadLocal<>();

    /** Active fault injection, never part of the passive contention measurements. */
    static final class NativeExpiryFault {
        final Instant expectedUntil;
        final boolean read;
        Instant nativeUntil,observedExpiry;int pid,guardAttempts,clockReads,postGuardStatements;
        long xid,began,dispatchedAt;String guardSqlState;
        NativeExpiryFault(Instant expectedUntil){this(expectedUntil,false);}
        NativeExpiryFault(Instant expectedUntil,boolean read){this.expectedUntil=expectedUntil;this.read=read;}
        void binding(Object[] args) {
            if(args==null||args.length<2||!(args[0] instanceof Integer index))return;
            if(index==(read?3:9)&&args[1] instanceof java.sql.Timestamp value)nativeUntil=value.toInstant();
            if(index==(read?4:10)&&args[1] instanceof Number value)pid=value.intValue();
            if(index==(read?5:11)&&args[1] instanceof Number value)xid=value.longValue();
        }
        void beforeGuard(Connection physical) throws SQLException {
            assertEquals(0,guardAttempts++);assertEquals(expectedUntil,nativeUntil);
            began=System.nanoTime();
            // Same physical connection/XID, unchanged local bounds, and a still-live real window.
            try(var statement=physical.createStatement();var rows=statement.executeQuery("""
                    SELECT pg_catalog.clock_timestamp(),pg_catalog.pg_backend_pid(),pg_catalog.txid_current(),
                      current_setting('lock_timeout'),current_setting('statement_timeout'),
                      current_setting('idle_in_transaction_session_timeout')
                    """)) {
                clockReads++;assertTrue(rows.next());assertTrue(rows.getTimestamp(1).toInstant().isBefore(expectedUntil));
                assertEquals(pid,rows.getInt(2));assertEquals(xid,rows.getLong(3));
                assertEquals("250ms",rows.getString(4));assertEquals("750ms",rows.getString(5));assertEquals("1s",rows.getString(6));
                assertFalse(rows.next());
            }
            // Observe expiry using bounded real DB reads. Short active statements avoid an idle timeout.
            // The sleep paces a clock observation; it never guesses another transaction's ordering.
            for(int attempt=0;attempt<400;attempt++) {
                assertTrue(System.nanoTime()-began<8_500_000_000L,"Bounded expiry fault exceeded its observation limit");
                try(var statement=physical.createStatement();var rows=statement.executeQuery(
                        "SELECT pg_catalog.clock_timestamp(),pg_catalog.pg_sleep(0.025)")) {
                    clockReads++;assertTrue(rows.next());Instant observed=rows.getTimestamp(1).toInstant();assertFalse(rows.next());
                    if(!observed.isBefore(expectedUntil)){observedExpiry=observed;dispatchedAt=System.nanoTime();return;}
                }
            }
            fail("Bounded expiry fault did not observe the original deadline");
        }
    }
    static final class RuntimeTrace {
        final long workCeilingMillis;
        RuntimeTrace(){this(dz.sh.hidra.platform.configuration.ProtectedExecutionBudgetProperties.DEFAULT_APPOINTMENT_WORK_BUDGET_MILLIS);}
        RuntimeTrace(long workCeilingMillis){this.workCeilingMillis=workCeilingMillis;}
        long began,ended,firstFence,guardBegan,guardClosed,commitBegan,commitEnded,rollbackEnded;
        long interceptedPublisherExecutions,qualifiedFenceChecks;
        Object connection;boolean foreignConnection,success;
        void fence(Object physical){
            if(firstFence==0){firstFence=System.nanoTime();connection=physical;}
            else if(connection!=physical)foreignConnection=true;
        }
        void committed() {
            assertTrue(success);assertFalse(foreignConnection);
            assertTrue(firstFence>began);assertTrue(guardBegan>=firstFence);
            assertTrue(guardClosed>=guardBegan);assertTrue(commitBegan>=guardClosed);
            assertTrue(commitEnded>=commitBegan);assertTrue(ended>=commitEnded);assertEquals(0,rollbackEnded);
        }
        void rejected() {assertFalse(success);assertFalse(foreignConnection);assertEquals(0,commitBegan);assertEquals(0,commitEnded);assertTrue(rollbackEnded>began);}
        void print(String stage) {
            long completion=commitEnded!=0?commitEnded:rollbackEnded;
            System.out.println("HAPI_778_CONTENTION_QUALIFICATION stage="+stage+" success="+success
                    +" callElapsedNs="+(ended-began)+" observedFenceHoldLowerBoundNs="+interval(firstFence,completion)
                    +" guardToCompletionNs="+interval(guardBegan,completion)
                    +" postGuardTailNs="+interval(guardClosed,completion)
                    +" commitCallNs="+interval(commitBegan,commitEnded)
                    +" interceptedPublisherExecutions="+interceptedPublisherExecutions+" qualifiedFenceChecks="+qualifiedFenceChecks
                    +" workCeilingMs="+workCeilingMillis+" includesHarnessWork=true operationallyAccepted=false");
        }
        private static long interval(long start,long end){return start==0||end==0?-1:end-start;}
    }

    static <T> T tracedRuntime(String stage,RuntimeTrace trace,java.util.function.Supplier<T> action) {
        assertNull(RUNTIME_TRACE.get());trace.began=System.nanoTime();RUNTIME_TRACE.set(trace);
        try {T result=action.get();trace.success=true;return result;}
        finally {trace.ended=System.nanoTime();RUNTIME_TRACE.remove();trace.print(stage);}
    }

    static Object delegated(Object target,java.lang.reflect.Method method,Object[] args) throws Throwable {
        try{return method.invoke(target,args);}
        catch(java.lang.reflect.InvocationTargetException failure){throw failure.getCause();}
    }
    @SuppressWarnings("unchecked")
    static <T> T jdbcObserver(Class<T> type,T target,java.lang.reflect.InvocationHandler handler) {
        return (T)java.lang.reflect.Proxy.newProxyInstance(type.getClassLoader(),new Class<?>[]{type},(proxy,method,args)->{
            if(method.getDeclaringClass()==Object.class)return switch(method.getName()) {
                case "equals"->proxy==args[0];case "hashCode"->System.identityHashCode(proxy);
                case "toString"->"QualificationJdbcHandle";default->throw new AssertionError();
            };
            return handler.invoke(target,method,args);
        });
    }
    static Connection observedConnection(Connection physical) {
        return jdbcObserver(Connection.class,physical,(target,method,args)->{
            var trace=RUNTIME_TRACE.get();String name=method.getName();
            if(trace!=null&&name.equals("setAutoCommit")&&Boolean.FALSE.equals(args[0])) {
                if(trace.connection==null)trace.connection=physical;
                else if(trace.connection!=physical)trace.foreignConnection=true;
            }
            boolean tracked=trace!=null&&trace.connection==physical;
            if(tracked&&name.equals("commit"))trace.commitBegan=System.nanoTime();
            Object value=delegated(target,method,args);
            if(tracked&&name.equals("commit"))trace.commitEnded=System.nanoTime();
            if(tracked&&name.equals("rollback"))trace.rollbackEnded=System.nanoTime();
            if(value instanceof java.sql.Statement statement&&(name.equals("prepareStatement")||name.equals("prepareCall")||name.equals("createStatement"))) {
                String sql=args!=null&&args.length>0&&args[0] instanceof String text?text:null;
                return observedStatement(physical,statement,sql);
            }
            return value; // Metadata, unwrap, settings and all other calls retain actual driver semantics.
        });
    }
    static java.sql.Statement observedStatement(Connection physical,java.sql.Statement statement,String preparedSql) {
        Class<? extends java.sql.Statement> type=statement instanceof java.sql.CallableStatement?java.sql.CallableStatement.class:
                statement instanceof java.sql.PreparedStatement?java.sql.PreparedStatement.class:java.sql.Statement.class;
        return observedStatementOfType(type,physical,statement,preparedSql);
    }
    static <T extends java.sql.Statement> T observedStatementOfType(Class<T> type,Connection physical,java.sql.Statement statement,String preparedSql) {
        return jdbcObserver(type,type.cast(statement),(target,method,args)->{
            var trace=RUNTIME_TRACE.get();String name=method.getName();
            String sql=preparedSql!=null?preparedSql:args!=null&&args.length>0&&args[0] instanceof String text?text:null;
            boolean executing=name.startsWith("execute"),writeGuard=sql!=null&&sql.equals(FINAL_SQL),
                    readGuard=sql!=null&&sql.equals(CURRENT_FINAL_SQL),guard=writeGuard||readGuard;
            if(trace!=null&&executing) {
                trace.interceptedPublisherExecutions++;
                if(sql!=null&&sql.startsWith("WITH identity_claim_locks AS MATERIALIZED"))trace.qualifiedFenceChecks++;
            }
            var expiry=NATIVE_EXPIRY_FAULT.get();
            if(expiry!=null) {
                if((expiry.read?readGuard:writeGuard)&&name.startsWith("set"))expiry.binding(args); // Observe only; delegate identical parameters.
                if(executing&&expiry.dispatchedAt!=0)expiry.postGuardStatements++;
                if(executing&&(expiry.read?readGuard:writeGuard))expiry.beforeGuard(physical);
            }
            if(trace!=null&&executing&&guard)trace.guardBegan=System.nanoTime();
            Object value;
            try {value=delegated(target,method,args);}
            catch(SQLException failure){if(expiry!=null&&executing&&guard)expiry.guardSqlState=failure.getSQLState();throw failure;}
            if(trace!=null&&executing&&sql!=null&&sql.stripLeading().startsWith("LOCK TABLE "))trace.fence(physical);
            if(trace!=null&&guard&&value instanceof java.sql.ResultSet rows) {
                return jdbcObserver(java.sql.ResultSet.class,rows,(rowTarget,rowMethod,rowArgs)->{
                    Object result=delegated(rowTarget,rowMethod,rowArgs);
                    if(rowMethod.getName().equals("close"))trace.guardClosed=System.nanoTime();
                    return result;
                });
            }
            return value;
        });
    }

    static <T> T authenticated(org.springframework.security.core.Authentication authentication,java.util.concurrent.Callable<T> action) throws Exception {
        var context=SecurityContextHolder.createEmptyContext();context.setAuthentication(authentication);
        SecurityContextHolder.setContext(context);
        try{return action.call();}finally{SecurityContextHolder.clearContext();}
    }
    static void stopWorker(java.util.concurrent.ExecutorService worker) throws InterruptedException {
        worker.shutdownNow();assertTrue(worker.awaitTermination(10,java.util.concurrent.TimeUnit.SECONDS),"Qualification worker did not terminate");
    }
    static boolean sqlState(Throwable failure,String expected) {
        var pending=new java.util.ArrayDeque<Throwable>();pending.add(failure);
        var seen=java.util.Collections.newSetFromMap(new java.util.IdentityHashMap<Throwable,Boolean>());
        while(!pending.isEmpty()) {
            var current=pending.remove();if(!seen.add(current))continue;
            if(current instanceof SQLException sql&&expected.equals(sql.getSQLState()))return true;
            if(current.getCause()!=null)pending.add(current.getCause());
            java.util.Collections.addAll(pending,current.getSuppressed());
        }
        return false;
    }
    String runtimeEffects(Fixture f,String actor) throws SQLException {
        try(var c=owner()) {
            return scalar(c,"""
                SELECT concat_ws(',',
                  (SELECT count(*) FROM public.hidra_org_employee_assignment WHERE employee_id=?),
                  (SELECT count(*) FROM public.hidra_org_appointment_event WHERE employee_id=?),
                  (SELECT count(*) FROM public.hidra_org_appointment_receipt r JOIN public.hidra_org_appointment_event e USING(event_id) WHERE e.employee_id=?),
                  (SELECT count(*) FROM public.hidra_identity_org_commit_proof_use u JOIN public.hidra_org_appointment_event e USING(event_id) WHERE e.employee_id=?),
                  (SELECT count(*) FROM public.hidra_org_appointment_lock_key WHERE
                    (kind='EMPLOYEE' AND key_one=?) OR (kind='REQUEST' AND key_one=?) OR (kind='OCCUPANCY' AND key_one=?)))
                """,f.employee(),f.employee(),f.employee(),f.employee(),f.employee(),actor,f.units().get(0));
        }
    }



    record WriterObservation(String state,long elapsedNs) { }








    static final Instant START=Instant.parse("2026-10-20T10:00:00Z");
    static final String REASON="Change é\t\n retained";
    static final JsonMapper JSON=JsonMapper.builder().build();
    static final List<ProtectedRelationFence> KEYS=List.of(
            new ProtectedRelationFence("public.hidra_identity_org_commit_key_event",ProtectedRelationFence.Mode.ACCESS_SHARE),
            new ProtectedRelationFence("public.hidra_identity_org_commit_key_head",ProtectedRelationFence.Mode.SHARE),
            new ProtectedRelationFence("public.hidra_identity_org_commit_proof_use",ProtectedRelationFence.Mode.SHARE_ROW_EXCLUSIVE));
    String partyType,unitType;
    Fixture baseline,historical;
    EmployeeAppointmentCommand.Correct originalCorrection;

    record Fixture(String company,String employee,String assignment,String position,List<String> units,
                           String policy,String createRequest) { }

    Connection owner() throws SQLException {
        return DriverManager.getConnection(POSTGRES.getJdbcUrl(),POSTGRES.getUsername(),POSTGRES.getPassword());
    }
    void admin(String sql,Object... args) throws SQLException {
        try(var c=owner()){execute(c,sql,args);}
    }
    static void execute(Connection c,String sql,Object... args) throws SQLException {
        try(var p=c.prepareStatement(sql)){for(int i=0;i<args.length;i++)p.setObject(i+1,args[i]);p.execute();}
    }
    static String scalar(Connection c,String sql,Object... args) throws SQLException {
        try(var p=c.prepareStatement(sql)){
            for(int i=0;i<args.length;i++)p.setObject(i+1,args[i]);
            try(var r=p.executeQuery()){assertTrue(r.next());String value=r.getString(1);assertFalse(r.next());return value;}
        }
    }
    static String id(){return UUID.randomUUID().toString();}
    static String policy(){
        var rows=new java.util.LinkedHashMap<String,Object>();
        for(var a:AssignmentType.values()){
            var row=new java.util.LinkedHashMap<String,Boolean>();
            for(var b:AssignmentType.values())row.put(b.name(),false);
            rows.put(a.name(),row);
        }
        return JSON.writeValueAsString(Map.of("maxConcurrentPrimary",1,"positionCapacity","SINGLE_HOLDER","overlaps",rows));
    }

    @BeforeAll void migrateAndCommitHistoricalFixtures() throws Exception {
        Flyway.configure().dataSource(POSTGRES.getJdbcUrl(),POSTGRES.getUsername(),POSTGRES.getPassword()).load().migrate();
        admin("CREATE ROLE "+CONFIG+" LOGIN PASSWORD '"+PASSWORD+"'");
        admin("GRANT USAGE ON SCHEMA public TO "+CONFIG);
        admin("GRANT EXECUTE ON FUNCTION public.hidra_org_activate_appointment_binding(text,text,text,bigint),public.hidra_org_activate_appointment_policy(text,text,bigint,jsonb),public.hidra_party_require_organization_configuration_reference(text) TO "+CONFIG);
        admin("CREATE ROLE final_proof_admin LOGIN PASSWORD '"+PASSWORD+"'");
        admin("GRANT USAGE ON SCHEMA public TO final_proof_admin");
        admin("GRANT EXECUTE ON FUNCTION public.hidra_identity_org_commit_admin(bigint,text,uuid,name,bytea) TO final_proof_admin");
        partyType=id();unitType=id();
        admin("INSERT INTO public.hidra_party_type(id,code,status,created_at,updated_at) VALUES (?,?,'ACTIVE',now(),now())",partyType,partyType);
        admin("INSERT INTO public.hidra_org_unit_type(id,code,kind,active,created_at,updated_at) VALUES (?,?,'STATION',true,now(),now())",unitType,unitType);
        baseline=fixture();historical=fixture();
        originalCorrection=correction(historical,"original-correction",1,historical.units().get(1));
        commitCorrection(historical,originalCorrection);
        commitCorrection(historical,correction(historical,"later-correction",2,historical.units().get(2)));
        String reboundCompany=id();
        admin("INSERT INTO public.hidra_party_party(id,code,party_type_id,legal_name,country_code,status,created_at,updated_at) VALUES (?,?,?,'Synthetic rebound company','DZ','ACTIVE',now(),now())",reboundCompany,reboundCompany,partyType);
        try(var config=DriverManager.getConnection(POSTGRES.getJdbcUrl(),CONFIG,PASSWORD)){
            scalar(config,"SELECT public.hidra_org_activate_appointment_binding(?,?,?,1)",
                    historical.units().get(0),historical.units().get(0),reboundCompany);
        }
        admin("UPDATE public.hidra_party_party SET status='SUSPENDED' WHERE id=?",historical.company());
    }

    Fixture fixture() throws Exception { return fixture(true); }
    Fixture fixture(boolean seeded) throws Exception {
        var f=new Fixture(id(),id(),id(),id(),List.of(id(),id(),id()),id(),id());
        admin("INSERT INTO public.hidra_party_party(id,code,party_type_id,legal_name,country_code,status,created_at,updated_at) VALUES (?,?,?,'Synthetic company','DZ','ACTIVE',now(),now())",f.company(),f.company(),partyType);
        admin("INSERT INTO public.hidra_org_employee(id,employee_number,employee_type,status,created_at,updated_at) VALUES (?,?,'PERMANENT','ACTIVE',now(),now())",f.employee(),f.employee());
        admin("INSERT INTO public.hidra_org_position(id,code,level,status,created_at,updated_at) VALUES (?,?,'ENGINEER','ACTIVE',now(),now())",f.position(),f.position());
        for(var unit:f.units())admin("INSERT INTO public.hidra_org_unit(id,code,unit_type_id,status,valid_from,created_at,updated_at) VALUES (?,?,?,'ACTIVE',now()-interval '1 day',now(),now())",unit,unit,unitType);
        try(var config=DriverManager.getConnection(POSTGRES.getJdbcUrl(),CONFIG,PASSWORD)){
            config.setAutoCommit(false);
            for(var unit:f.units())scalar(config,"SELECT public.hidra_org_activate_appointment_binding(?,?,?,0)",unit,unit,f.company());
            scalar(config,"SELECT public.hidra_org_activate_appointment_policy(?,?,0,?::jsonb)",f.policy(),f.company(),policy());config.commit();
        } catch(SQLException failure) {
            if("55P03".equals(failure.getSQLState()))describeFixtureConfigurationLocks(failure);
            throw failure; // Diagnostic only: never retry, continue or relabel failed fixture setup.
        }
        if(seeded)seedCreate(f,null);
        return f;
    }

    /** Bounded post-failure observation after the fixture connection closed/rolled back. */
    void describeFixtureConfigurationLocks(SQLException original) {
        try(var connection=owner();var statement=connection.prepareStatement("""
                SELECT l.pid,COALESCE(a.backend_type,'unavailable'),l.mode,l.granted
                  FROM pg_catalog.pg_locks l
                  LEFT JOIN pg_catalog.pg_stat_activity a ON a.pid=l.pid
                 WHERE l.locktype='relation'
                   AND l.relation='public.hidra_org_appointment_configuration_event'::regclass
                 ORDER BY l.pid,l.mode LIMIT 32
                """)) {
            statement.setQueryTimeout(2);
            var observed=new java.util.ArrayList<String>();
            try(var rows=statement.executeQuery()) {
                while(rows.next())observed.add(rows.getInt(1)+":"+rows.getString(2)+":"+rows.getString(3)+":"+rows.getBoolean(4));
            }
            original.addSuppressed(new IllegalStateException("Fixture configuration locks after rollback: "+observed));
        }catch(Exception diagnosticFailure){if(diagnosticFailure!=original)original.addSuppressed(diagnosticFailure);}
    }

    void seedCreate(Fixture f,Instant end) throws Exception {
        try(var c=owner()){
            c.setAutoCommit(false);String event=id();
            execute(c,"INSERT INTO public.hidra_org_employee_assignment(id,employee_id,organization_unit_id,position_id,assignment_type,valid_from,valid_to,status,created_at,updated_at,appointment_version,binding_id,binding_revision,policy_id,policy_revision,last_event_id) VALUES (?,?,?,?,'PRIMARY',?::timestamptz,?::timestamptz,'ACTIVE',transaction_timestamp(),transaction_timestamp(),1,?,1,?,1,?::uuid)",f.assignment(),f.employee(),f.units().get(0),f.position(),START.toString(),end==null?null:end.toString(),f.units().get(0),f.policy(),event);
            String command=JSON.writeValueAsString(Map.ofEntries(
                    Map.entry("kind","CREATE"),Map.entry("companyPartyId",f.company()),Map.entry("requestId",f.createRequest()),
                    Map.entry("employeeId",f.employee()),Map.entry("organizationUnitId",f.units().get(0)),Map.entry("positionId",f.position()),
                    Map.entry("assignmentType","PRIMARY"),Map.entry("validFrom",START.toString()),
                    Map.entry("expectedBindingRevision",1),Map.entry("expectedPolicyRevision",1)));
            command=scalar(c,"SELECT ?::jsonb || jsonb_build_object('validTo',?::text)",command,end==null?null:end.toString());
            ledger(c,f,event,"CREATE",command,"null");c.commit();
        }
    }

    static EmployeeAppointmentCommand.Correct correction(Fixture f,String request,long version,String unit){
        return new EmployeeAppointmentCommand.Correct(request,f.company(),f.assignment(),version,unit,f.position(),
                AssignmentType.PRIMARY,AssignmentStatus.ACTIVE,START,null,1,1,REASON);
    }
    void commitCorrection(Fixture f,EmployeeAppointmentCommand.Correct command) throws Exception {
        try(var c=owner()){
            c.setAutoCommit(false);
            String before=scalar(c,"SELECT public.hidra_org_appointment_snapshot(a)::text FROM public.hidra_org_employee_assignment a WHERE id=?",f.assignment());
            String event=id();
            execute(c,"UPDATE public.hidra_org_employee_assignment SET organization_unit_id=?,binding_id=?,appointment_version=appointment_version+1,last_event_id=?::uuid,updated_at=transaction_timestamp() WHERE id=?",command.correctedOrganizationUnitId(),command.correctedOrganizationUnitId(),event,f.assignment());
            String json=JSON.writeValueAsString(Map.ofEntries(Map.entry("kind","CORRECT"),Map.entry("requestId",command.requestId()),
                    Map.entry("companyPartyId",f.company()),Map.entry("assignmentId",f.assignment()),Map.entry("expectedVersion",command.expectedVersion()),
                    Map.entry("correctedOrganizationUnitId",command.correctedOrganizationUnitId()),Map.entry("correctedPositionId",f.position()),
                    Map.entry("correctedType","PRIMARY"),Map.entry("correctedStatus","ACTIVE"),Map.entry("correctedValidFrom",START.toString()),
                    Map.entry("expectedDestinationBindingRevision",1),Map.entry("expectedDestinationPolicyRevision",1),Map.entry("reason",REASON)));
            json=scalar(c,"SELECT ?::jsonb || jsonb_build_object('correctedValidTo',NULL)",json);
            ledger(c,f,event,"CORRECT",json,before);c.commit();
        }
    }
    static void ledger(Connection c,Fixture f,String event,String kind,String command,String before) throws Exception {
        String body=scalar(c,"SELECT jsonb_build_object('command',?::jsonb,'before',?::jsonb,'after',public.hidra_org_appointment_snapshot(a),'successor',NULL)::text FROM public.hidra_org_employee_assignment a WHERE id=?",command,before,f.assignment());
        execute(c,"INSERT INTO public.hidra_org_appointment_event(event_id,operation,assignment_id,employee_id,company_party_id,policy_id,policy_revision,canonical_payload,command_sha256,actor_binding_id,actor_session_id,actor_user_id,sampled_at,valid_until,backend_pid,transaction_id) SELECT ?::uuid,?,?,?,?,?,1,convert_to(p::text,'UTF8'),encode(sha256(convert_to((p->'command')::text,'UTF8')),'hex'),gen_random_uuid(),gen_random_uuid(),'synthetic-actor',transaction_timestamp(),transaction_timestamp()+interval '1 hour',pg_backend_pid(),txid_current() FROM (SELECT ?::jsonb p) v",event,kind,f.assignment(),f.employee(),f.company(),f.policy(),body);
        execute(c,"INSERT INTO public.hidra_org_appointment_receipt(event_id,actor_user_id,scope_key,request_id,command_sha256,canonical_result,guarded_at,backend_pid,transaction_id) SELECT event_id,actor_user_id,encode(sha256(convert_to(jsonb_build_object('family','organization-appointment-v1','companyPartyId',company_party_id)::text,'UTF8')),'hex'),convert_from(canonical_payload,'UTF8')::jsonb->'command'->>'requestId',command_sha256,canonical_payload,sampled_at,backend_pid,transaction_id FROM public.hidra_org_appointment_event WHERE event_id=?::uuid",event);
        execute(c,"SET CONSTRAINTS ALL IMMEDIATE");
    }

    final class Session implements AutoCloseable {
        final String role="classification_"+id().replace("-","");
        final OrganizationProtectedAppointmentAuthorizationContract identity=mock(OrganizationProtectedAppointmentAuthorizationContract.class);
        final Admission nativeTicket=mock(Admission.class);
        final DriverManagerDataSource source;
        final JdbcTemplate jdbc;
        final TransactionTemplate tx;
        final JdbcProtectedEmployeeAppointmentRepositoryAdapter storage;
        final JdbcOrganizationProtectedPartyEligibilityAdapter party;
        OrganizationProtectedPartyEligibilityContract.Admission partyTicket;
        OrganizationProtectedPartyEligibilityContract.Admission partyTicketOverride;
        OrganizationProtectedPartyEligibilityContract ticketOwnerOverride;
        String partyIdOverride;
        final EmployeeAppointmentCommand command;
        String actor="synthetic-actor";
        Contribution lastSample;
        long backdateSeconds;
        ProtectedEmployeeAppointmentRepositoryPort.DescriptorPreview previewOverride;
        Session(EmployeeAppointmentCommand command) throws Exception {
            this.command=command;
            admin("CREATE ROLE "+role+" LOGIN PASSWORD '"+PASSWORD+"'");admin("GRANT USAGE ON SCHEMA public TO "+role);
            for(var table:List.of("employee","unit","position","appointment_binding_revision","appointment_binding_head",
                    "appointment_policy_revision","appointment_policy_head","appointment_configuration_event",
                    "appointment_lock_key","employee_assignment","appointment_event","appointment_receipt"))
                admin("GRANT SELECT ON public.hidra_org_"+table+" TO "+role);
            admin("GRANT SELECT ON public.hidra_party_party,public.hidra_party_eligibility_generation TO "+role);
            admin("GRANT UPDATE ON public.hidra_party_party TO "+role);
            for(var table:List.of("employee","unit","position","appointment_binding_head","appointment_policy_head","appointment_lock_key","employee_assignment"))
                admin("GRANT UPDATE ON public.hidra_org_"+table+" TO "+role);
            var fences=new ArrayList<>(JdbcIdentityAuthorityFence.requiredRelationFences());fences.addAll(KEYS);
            fences.add(new ProtectedRelationFence("public.hidra_party_party",ProtectedRelationFence.Mode.SHARE_ROW_EXCLUSIVE));
            fences.add(new ProtectedRelationFence("public.hidra_party_eligibility_generation",ProtectedRelationFence.Mode.ACCESS_SHARE));
            StringBuilder locks=new StringBuilder("BEGIN ");
            for(var fence:fences)locks.append("LOCK TABLE ").append(fence.relation()).append(" IN ")
                    .append(switch(fence.mode()){case SHARE_ROW_EXCLUSIVE -> "SHARE ROW EXCLUSIVE";case ACCESS_SHARE -> "ACCESS SHARE";case SHARE -> "SHARE";})
                    .append(" MODE NOWAIT; ");
            admin("CREATE FUNCTION public.fixture_"+role+"() RETURNS void LANGUAGE plpgsql SECURITY DEFINER SET search_path=pg_catalog AS $fixture$ "+locks+" END; $fixture$");
            admin("REVOKE ALL ON FUNCTION public.fixture_"+role+"() FROM PUBLIC");admin("GRANT EXECUTE ON FUNCTION public.fixture_"+role+"() TO "+role);
            source=new DriverManagerDataSource(POSTGRES.getJdbcUrl(),role,PASSWORD);jdbc=new JdbcTemplate(source);
            tx=new TransactionTemplate(new DataSourceTransactionManager(source));tx.setIsolationLevel(Connection.TRANSACTION_READ_COMMITTED);
            party=spy(new JdbcOrganizationProtectedPartyEligibilityAdapter(jdbc));
            storage=new JdbcProtectedEmployeeAppointmentRepositoryAdapter(jdbc,identity,party);
            SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken("synthetic",null,List.of()));
            when(identity.finalFences(nativeTicket)).thenReturn(JdbcIdentityAuthorityFence.requiredRelationFences());
            when(identity.commitProofFences(nativeTicket)).thenReturn(KEYS);
            when(identity.contribute(nativeTicket)).thenAnswer(call -> {
                var preview=previewOverride==null?storage.preview(command):previewOverride;
                var request=new Request(Action.valueOf(command.kind().name()),preview.companyUnits(),preview.employeeId());
                lastSample=jdbc.queryForObject("SELECT pg_backend_pid(),txid_current(),clock_timestamp()",(r,i) ->
                        new Contribution(request,request.requirements().stream().map(q -> new ResourceDecision(q,Decision.PERMIT)).toList(),
                                "00000000-0000-0000-0000-000000000001","00000000-0000-0000-0000-000000000002",actor,r.getTimestamp(3).toInstant().minusSeconds(backdateSeconds),r.getTimestamp(3).toInstant().plusSeconds(60),r.getInt(1),r.getLong(2)));
                return lastSample;
            });
        }
        ProtectedEmployeeAppointmentRepositoryPort.Admission acquire(){
            jdbc.execute("SELECT public.fixture_"+role+"()");
            partyTicket=partyTicketOverride!=null?partyTicketOverride
                    :(ticketOwnerOverride==null?party:ticketOwnerOverride).acquire(
                        partyIdOverride==null?command.companyPartyId():partyIdOverride);
            return storage.acquire(nativeTicket,List.of(partyTicket),
                    previewOverride==null?storage.preview(command):previewOverride);
        }
        boolean poisoned(){return ((ConnectionHolder)TransactionSynchronizationManager.getResource(source)).isRollbackOnly();}
        @Override public void close(){SecurityContextHolder.clearContext();}
    }

    /** Test-only port composition, no production bean. Real storage/Party, mocked Identity and executor. */
    final class ProvisionalSession implements AutoCloseable {
        final Session s;
        final ProtectedTransactionExecutor executor=mock(ProtectedTransactionExecutor.class);
        final ProtectedEmployeeAppointmentAdmissionCoordinator coordinator;
        final ProtectedEmployeeAppointmentAdmissionCoordinator.Prepared prepared;
        Object write;
        ProvisionalSession(EmployeeAppointmentCommand command) throws Exception {
            s=new Session(command);
            s.actor="provisional-actor";
            admin("GRANT INSERT ON public.hidra_org_appointment_lock_key,public.hidra_org_employee_assignment,public.hidra_org_appointment_event TO "+s.role);
            admin("GRANT EXECUTE ON FUNCTION public.hidra_org_appointment_exact_id(text) TO "+s.role);
            when(s.identity.prepare()).thenReturn(mock(OrganizationProtectedAppointmentAuthorizationContract.Prepared.class));
            when(s.identity.acquire(any(),any())).thenAnswer(call -> {s.jdbc.execute("SELECT public.fixture_"+s.role+"()");return s.nativeTicket;});
            when(executor.sealAdmission()).thenReturn(mock(ProtectedTransactionExecutor.ProvisionalPermit.class));
            coordinator=new ProtectedEmployeeAppointmentAdmissionCoordinator(s.identity,s.party,s.storage,executor);
            prepared=coordinator.prepare();
        }
        Object invoke(String name,Object... args) {
            try {
                var method=java.util.Arrays.stream(coordinator.getClass().getDeclaredMethods()).filter(m -> m.getName().equals(name)).findFirst().orElseThrow();
                method.setAccessible(true);return method.invoke(coordinator,args);
            }catch(java.lang.reflect.InvocationTargetException failure){
                if(failure.getCause() instanceof RuntimeException runtime)throw runtime;
                throw new AssertionError(failure.getCause());
            }catch(ReflectiveOperationException failure){throw new AssertionError(failure);}
        }
        void persist(){write=invoke("acquireWrite",prepared,s.command);invoke("persistProvisional",write);}
        @Override public void close(){s.close();}
    }











    // Intentionally unauthenticated transport: only the later SQL verifier can authenticate it.
    static CommitProof syntheticProof(){return new CommitProof(new byte[]{1,2,3},new byte[32]);}

    /** Independent fixture encoder for the real SQL verifier, using synthetic native fields.
     * Actual Identity issuance and the real protected executor remain separate acceptance. */
    void enableJavaFinalGuard(ProvisionalSession p,boolean invalidMac) throws Exception {
        admin("GRANT EXECUTE ON FUNCTION "+FINAL_GUARD+" TO "+p.s.role);
        long prior;try(var c=owner()){prior=Long.parseLong(scalar(c,"SELECT coalesce(max(revision),0) FROM public.hidra_identity_org_commit_key_event"));}
        final long revision;
        try(var keyAdmin=DriverManager.getConnection(POSTGRES.getJdbcUrl(),"final_proof_admin",PASSWORD)){
            revision=Long.parseLong(scalar(keyAdmin,"SELECT public.hidra_identity_org_commit_admin(?,'final-test-key',?::uuid,?::name,?)",
                    prior,SQL_AUDIENCE.toString(),p.s.role,sqlKey()));
        }
        when(p.s.identity.issueCommitProof(eq(p.s.nativeTicket),any())).thenAnswer(call -> {
            var intent=call.getArgument(1,CommitIntent.class);var sample=p.s.lastSample;
            finalEvents.put(sample.request().employeeId(),intent.eventId());
            var bytes=new java.io.ByteArrayOutputStream();var out=new java.io.DataOutputStream(bytes);
            proofText(out,"HIDRA/IDENTITY/ORG-APPOINTMENT-COMMIT/1");out.writeInt(1);proofUuid(out,SQL_AUDIENCE);
            proofText(out,p.s.role);proofText(out,"final-test-key");out.writeLong(revision);
            var nonce=new byte[32];new java.security.SecureRandom().nextBytes(nonce);out.write(nonce);
            proofUuid(out,UUID.fromString(sample.bindingId()));proofUuid(out,UUID.fromString(sample.sessionId()));
            proofText(out,sample.userId());proofText(out,sample.request().action().name());out.writeInt(sample.request().companyUnits().size());
            for(var pair:sample.request().companyUnits()){proofText(out,pair.companyPartyId());proofText(out,pair.organizationUnitId());}
            proofText(out,sample.request().employeeId());out.writeLong(micros(sample.sampledAt()));
            out.writeLong(micros(sample.validUntil()));out.writeLong(micros(intent.operationValidUntil()));
            out.writeInt(sample.backendPid());out.writeLong(sample.transactionId());proofUuid(out,intent.eventId());
            out.write(java.util.HexFormat.of().parseHex(intent.commandSha256()));out.write(java.util.HexFormat.of().parseHex(intent.payloadSha256()));
            var frame=bytes.toByteArray();var encoder=javax.crypto.Mac.getInstance("HmacSHA256");
            encoder.init(new javax.crypto.spec.SecretKeySpec(sqlKey(),"HmacSHA256"));var mac=encoder.doFinal(frame);
            if(invalidMac)mac[0]^=1;
            return new CommitProof(frame,mac);
        });
    }































    final Map<String,UUID> finalEvents=new java.util.HashMap<>();
    static final String FINAL_GUARD="public.hidra_org_appointment_final_guard(uuid,uuid,uuid,text,text,text,text,timestamptz,timestamptz,integer,bigint,bytea,bytea,timestamptz)";
    static final String FINAL_SQL="WITH observed AS MATERIALIZED (SELECT pg_catalog.clock_timestamp() AS t) SELECT public.hidra_org_appointment_final_guard(?::uuid,?::uuid,?::uuid,?,?,?,?,?::timestamptz,?::timestamptz,?,?,?::bytea,?::bytea,t) FROM observed";
    static final String CURRENT_FINAL_SQL="WITH observed AS MATERIALIZED (SELECT pg_catalog.clock_timestamp() AS t) SELECT public.hidra_org_appointment_read_guard(?::jsonb,?::timestamptz,?::timestamptz,?,?,t) FROM observed";
    static final UUID SQL_AUDIENCE=UUID.fromString("11111111-1111-1111-1111-111111111111");
    static byte[] sqlKey(){var key=new byte[32];for(int i=0;i<key.length;i++)key[i]=(byte)(i+1);return key;}
    static void proofText(java.io.DataOutputStream out,String value) throws java.io.IOException {
        var bytes=value.getBytes(java.nio.charset.StandardCharsets.UTF_8);out.writeInt(bytes.length);out.write(bytes);
    }
    static void proofUuid(java.io.DataOutputStream out,UUID value) throws java.io.IOException {
        out.writeLong(value.getMostSignificantBits());out.writeLong(value.getLeastSignificantBits());
    }
    static long micros(Instant value){return Math.addExact(Math.multiplyExact(value.getEpochSecond(),1000000),value.getNano()/1000);}
    record SqlProof(byte[] frame,byte[] mac) { }

    /** Independent raw JDBC/HMAC test harness. It never removes or changes production callbacks.
     * Manually encoded native fields are synthetic; genuine native Java/executor acceptance remains separate. */
    final class FinalSqlSession implements AutoCloseable {
        final Fixture f;final Session login;final Connection c;final long revision;
        final UUID event=UUID.randomUUID(),binding=UUID.randomUUID(),session=UUID.randomUUID();
        final String actor="final-sql-actor",request=id(),assignment=id(),scope,commandDigest,payloadDigest;
        final Instant sampled,until;final int pid;final long xid;
        FinalSqlSession(Fixture f,boolean expired,boolean invalidTypedCommand) throws Exception {
            this(f,expired,invalidTypedCommand,0,START);
        }
        FinalSqlSession(Fixture f,boolean expired,boolean invalidTypedCommand,int attack,Instant from) throws Exception {
            this.f=f;finalEvents.put(f.employee(),event);login=new Session(create(f,request));
            admin("GRANT INSERT ON public.hidra_org_employee_assignment,public.hidra_org_appointment_lock_key,public.hidra_org_appointment_event TO "+login.role);
            admin("GRANT EXECUTE ON FUNCTION public.hidra_org_appointment_exact_id(text),"+FINAL_GUARD+" TO "+login.role);
            if(attack==1)admin("GRANT INSERT ON public.hidra_org_appointment_receipt TO "+login.role);
            long prior;try(var owner=owner()){prior=Long.parseLong(scalar(owner,"SELECT coalesce(max(revision),0) FROM public.hidra_identity_org_commit_key_event"));}
            try(var keyAdmin=DriverManager.getConnection(POSTGRES.getJdbcUrl(),"final_proof_admin",PASSWORD)){
                revision=Long.parseLong(scalar(keyAdmin,"SELECT public.hidra_identity_org_commit_admin(?,'final-test-key',?::uuid,?::name,?)",prior,SQL_AUDIENCE.toString(),login.role,sqlKey()));
            }
            c=login.source.getConnection();c.setAutoCommit(false);
            execute(c,"SELECT public.fixture_"+login.role+"()");
            // Exact existing Organization order; the native fixture already holds Identity27 then Party2.
            for(String table:List.of("employee","unit","position","appointment_binding_revision","appointment_binding_head",
                    "appointment_policy_revision","appointment_policy_head","appointment_configuration_event","appointment_lock_key",
                    "employee_assignment","appointment_event","appointment_receipt")){
                if(attack==2 && table.equals("appointment_receipt"))continue;
                boolean read=List.of("appointment_binding_revision","appointment_policy_revision","appointment_configuration_event","appointment_event","appointment_receipt").contains(table);
                execute(c,"LOCK TABLE public.hidra_org_"+table+" IN "+(read?"ACCESS SHARE":"SHARE ROW EXCLUSIVE")+" MODE NOWAIT");
            }
            try(var st=c.createStatement();var r=st.executeQuery("SELECT pg_backend_pid(),txid_current(),clock_timestamp()")){
                assertTrue(r.next());pid=r.getInt(1);xid=r.getLong(2);sampled=r.getTimestamp(3).toInstant().minusSeconds(expired?120:0);until=sampled.plusSeconds(60);
            }
            String cmd=JSON.writeValueAsString(Map.ofEntries(Map.entry("kind","CREATE"),Map.entry("companyPartyId",f.company()),
                    Map.entry("requestId",request),Map.entry("employeeId",f.employee()),Map.entry("organizationUnitId",f.units().get(0)),
                    Map.entry("positionId",f.position()),Map.entry("assignmentType","PRIMARY"),Map.entry("validFrom",from.toString()),
                    Map.entry("expectedBindingRevision",invalidTypedCommand?2:1),Map.entry("expectedPolicyRevision",1)));
            cmd=scalar(c,"SELECT (?::jsonb || jsonb_build_object('validTo',NULL))::text",cmd);
            scope=scalar(c,"SELECT encode(sha256(convert_to(jsonb_build_object('family','organization-appointment-v1','companyPartyId',?::text)::text,'UTF8')),'hex')",f.company());
            commandDigest=scalar(c,"SELECT encode(sha256(convert_to(?::jsonb::text,'UTF8')),'hex')",cmd);
            execute(c,"INSERT INTO public.hidra_org_appointment_lock_key VALUES ('EMPLOYEE',?,'',''),('OCCUPANCY',?,? ,''),('REQUEST',?,?,?)",f.employee(),f.units().get(0),f.position(),actor,scope,request);
            execute(c,"INSERT INTO public.hidra_org_employee_assignment(id,employee_id,organization_unit_id,position_id,assignment_type,valid_from,status,created_at,updated_at,appointment_version,binding_id,binding_revision,policy_id,policy_revision,last_event_id) VALUES (?,?,?,?,'PRIMARY',?::timestamptz,'ACTIVE',?::timestamptz,?::timestamptz,1,?,1,?,1,?::uuid)",assignment,f.employee(),f.units().get(0),f.position(),from.toString(),sampled.toString(),sampled.toString(),f.units().get(0),f.policy(),event.toString());
            String payload=scalar(c,"SELECT jsonb_build_object('command',?::jsonb,'before',NULL,'after',jsonb_build_object('assignment',to_jsonb(a),'binding',to_jsonb(b)),'successor',NULL)::text FROM public.hidra_org_employee_assignment a JOIN public.hidra_org_appointment_binding_revision b ON b.binding_id=a.binding_id AND b.revision=a.binding_revision WHERE a.id=?",cmd,assignment);
            payloadDigest=scalar(c,"SELECT encode(sha256(convert_to(?::text,'UTF8')),'hex')",payload);
            execute(c,"INSERT INTO public.hidra_org_appointment_event(event_id,operation,assignment_id,employee_id,company_party_id,policy_id,policy_revision,canonical_payload,command_sha256,actor_binding_id,actor_session_id,actor_user_id,sampled_at,valid_until,backend_pid,transaction_id) VALUES (?::uuid,'CREATE',?,?,?,?,1,?,?,?::uuid,?::uuid,?,?::timestamptz,?::timestamptz,?,?)",event.toString(),assignment,f.employee(),f.company(),f.policy(),payload.getBytes(java.nio.charset.StandardCharsets.UTF_8),commandDigest,binding.toString(),session.toString(),actor,sampled.toString(),until.toString(),pid,xid);
        }
        SqlProof proof(String unit,String payloadHash) throws Exception {
            var bytes=new java.io.ByteArrayOutputStream();var out=new java.io.DataOutputStream(bytes);
            proofText(out,"HIDRA/IDENTITY/ORG-APPOINTMENT-COMMIT/1");out.writeInt(1);proofUuid(out,SQL_AUDIENCE);
            proofText(out,login.role);proofText(out,"final-test-key");out.writeLong(revision);
            byte[] nonce=new byte[32];new java.security.SecureRandom().nextBytes(nonce);out.write(nonce);
            proofUuid(out,binding);proofUuid(out,session);proofText(out,actor);proofText(out,"CREATE");out.writeInt(1);
            proofText(out,f.company());proofText(out,unit);proofText(out,f.employee());
            out.writeLong(micros(sampled));out.writeLong(micros(until));out.writeLong(micros(until));out.writeInt(pid);out.writeLong(xid);
            proofUuid(out,event);out.write(java.util.HexFormat.of().parseHex(commandDigest));out.write(java.util.HexFormat.of().parseHex(payloadHash));
            var frame=bytes.toByteArray();var mac=javax.crypto.Mac.getInstance("HmacSHA256");mac.init(new javax.crypto.spec.SecretKeySpec(sqlKey(),"HmacSHA256"));
            return new SqlProof(frame,mac.doFinal(frame));
        }
        SqlProof proof() throws Exception {return proof(f.units().get(0),payloadDigest);}
        void guard(SqlProof proof) throws SQLException {guard(proof,request);}
        void guard(SqlProof proof,String requested) throws SQLException {guardSql(proof,requested,FINAL_SQL);}
        void guardSql(SqlProof proof,String requested,String statement) throws SQLException {
            assertEquals("t",scalar(c,statement,event.toString(),binding.toString(),session.toString(),actor,scope,requested,
                    commandDigest,sampled.toString(),until.toString(),pid,xid,proof.frame(),proof.mac()));
        }
        @Override public void close() throws Exception {try{c.rollback();}finally{c.close();login.close();}}
    }
    String finalCount(String table,String employee) throws Exception {
        try(var c=owner()){
            if(table.equals("hidra_identity_org_commit_proof_use") || table.equals("hidra_org_appointment_receipt"))
                return scalar(c,"SELECT count(*) FROM public."+table+" WHERE event_id=?::uuid",finalEvents.get(employee).toString());
            return scalar(c,"SELECT count(*) FROM public."+table+" WHERE employee_id=?",employee);
        }
    }























    /** Only static identifiers enter assertion diagnostics; never print SQL payloads or proof bytes. */
    static String markFixtureRejections(String source,String family) {
        String token="RAISE EXCEPTION 'Appointment finalization unavailable'";
        var out=new StringBuilder();int offset=0,index=0,found;
        while((found=source.indexOf(token,offset))>=0){
            out.append(source,offset,found).append("RAISE EXCEPTION 'Fixture invariant ")
                    .append(family).append(String.format(java.util.Locale.ROOT,"%02d",++index)).append("'");
            offset=found+token.length();
        }
        assertTrue(index>0);return out.append(source.substring(offset)).toString();
    }
    static String safeFixtureMarker(SQLException failure) {
        var matcher=java.util.regex.Pattern.compile("Fixture invariant [CG][0-9]{2}").matcher(failure.getMessage());
        return matcher.find()?matcher.group():"no fixture marker; SQLSTATE="+failure.getSQLState();
    }





    EmployeeAppointmentCommand.Create create(Fixture f,String request){
        return new EmployeeAppointmentCommand.Create(request,f.company(),f.employee(),f.units().get(0),f.position(),AssignmentType.PRIMARY,START,null,1,1);
    }
    EmployeeAppointmentCommand.End end(Fixture f,long version){
        return new EmployeeAppointmentCommand.End(id(),f.company(),f.assignment(),version,START.plusSeconds(3600),REASON);
    }
    void classify(EmployeeAppointmentCommand command,ReferenceUse expected) throws Exception {
        try(var s=new Session(command)){s.tx.execute(status -> {
            var ticket=s.acquire();var sample=s.storage.contribute(ticket);
            assertEquals(expected,s.storage.referenceUse(ticket,command,sample));
            assertEquals(expected,s.storage.referenceUse(ticket,command,sample));
            verify(s.identity,times(1)).contribute(s.nativeTicket);status.setRollbackOnly();return null;
        });}
    }

















    void prepare(EmployeeAppointmentCommand command,boolean allowed) throws Exception {
        try(var s=new Session(command)){s.tx.execute(status -> {
            int events=s.jdbc.queryForObject("SELECT count(*) FROM public.hidra_org_appointment_event",Integer.class);
            int assignments=s.jdbc.queryForObject("SELECT count(*) FROM public.hidra_org_employee_assignment",Integer.class);
            var ticket=s.acquire();var sample=s.storage.contribute(ticket);
            var use=s.storage.referenceUse(ticket,command,sample);
            if(use==ReferenceUse.CURRENT)s.storage.contributeParty(ticket);
            if(allowed){
                assertDoesNotThrow(() -> s.storage.prepareMutation(ticket,command));
                assertDoesNotThrow(() -> s.storage.prepareMutation(ticket,command));
            }else{
                assertEquals("Protected appointment admission unavailable",assertThrows(AccessDeniedException.class,
                        () -> s.storage.prepareMutation(ticket,command)).getMessage());assertTrue(s.poisoned());
            }
            assertEquals(events,s.jdbc.queryForObject("SELECT count(*) FROM public.hidra_org_appointment_event",Integer.class));
            assertEquals(assignments,s.jdbc.queryForObject("SELECT count(*) FROM public.hidra_org_employee_assignment",Integer.class));
            verify(s.party,times(use==ReferenceUse.CURRENT?1:0)).read(s.partyTicket);
            status.setRollbackOnly();return null;
        });}
    }
    void activatePolicy(Fixture f,String value) throws Exception {
        try(var c=DriverManager.getConnection(POSTGRES.getJdbcUrl(),CONFIG,PASSWORD)){
            scalar(c,"SELECT public.hidra_org_activate_appointment_policy(?,?,1,?::jsonb)",f.policy(),f.company(),value);
        }
    }

















    void seedMany(Fixture f,int count) throws Exception {
        String prefix=id()+"-";
        try(var c=owner()){
            c.setAutoCommit(false);
            execute(c,"INSERT INTO public.hidra_org_employee_assignment(id,employee_id,organization_unit_id,position_id,assignment_type,valid_from,status,created_at,updated_at,appointment_version,binding_id,binding_revision,policy_id,policy_revision,last_event_id) SELECT ?::text||n,?,?,?,'PRIMARY',?::timestamptz,'ACTIVE',transaction_timestamp(),transaction_timestamp(),1,?,1,?,2,gen_random_uuid() FROM generate_series(1,?) n",prefix,f.employee(),f.units().get(0),f.position(),START.toString(),f.units().get(0),f.policy(),count);
            execute(c,"""
                    INSERT INTO public.hidra_org_appointment_event(event_id,operation,assignment_id,employee_id,
                        company_party_id,policy_id,policy_revision,canonical_payload,command_sha256,actor_binding_id,
                        actor_session_id,actor_user_id,sampled_at,valid_until,backend_pid,transaction_id)
                    SELECT a.last_event_id,'CREATE',a.id,a.employee_id,?::text,a.policy_id,a.policy_revision,
                        convert_to(p.body::text,'UTF8'),encode(sha256(convert_to((p.body->'command')::text,'UTF8')),'hex'),
                        gen_random_uuid(),gen_random_uuid(),'synthetic-actor',transaction_timestamp(),
                        transaction_timestamp()+interval '1 hour',pg_backend_pid(),txid_current()
                      FROM public.hidra_org_employee_assignment a
                     CROSS JOIN LATERAL (SELECT jsonb_build_object('command',jsonb_build_object(
                         'kind','CREATE','requestId',a.id,'companyPartyId',?::text,'employeeId',a.employee_id,
                         'organizationUnitId',a.organization_unit_id,'positionId',a.position_id,'assignmentType',a.assignment_type,
                         'validFrom',a.valid_from,'validTo',a.valid_to,'expectedBindingRevision',a.binding_revision,
                         'expectedPolicyRevision',a.policy_revision),'before',NULL,
                         'after',public.hidra_org_appointment_snapshot(a),'successor',NULL) body) p
                     WHERE a.employee_id=? AND a.id LIKE ?
                    """,f.company(),f.company(),f.employee(),prefix+"%");
            execute(c,"INSERT INTO public.hidra_org_appointment_receipt(event_id,actor_user_id,scope_key,request_id,command_sha256,canonical_result,guarded_at,backend_pid,transaction_id) SELECT event_id,actor_user_id,encode(sha256(convert_to(jsonb_build_object('family','organization-appointment-v1','companyPartyId',company_party_id)::text,'UTF8')),'hex'),convert_from(canonical_payload,'UTF8')::jsonb->'command'->>'requestId',command_sha256,canonical_payload,sampled_at,backend_pid,transaction_id FROM public.hidra_org_appointment_event WHERE employee_id=? AND assignment_id LIKE ?",f.employee(),prefix+"%");
            execute(c,"SET CONSTRAINTS ALL IMMEDIATE");c.commit();
        }
    }









    ProtectedEmployeeAppointmentRepositoryPort.Admission qualified(Session s) {
        var ticket=s.acquire();var sample=s.storage.contribute(ticket);
        s.storage.referenceUse(ticket,s.command,sample);return ticket;
    }
















    static dz.sh.hidra.modules.organization.application.query.EmployeeAppointmentQuery.Current runtimeCurrent(
            String employee,String unit,int page,int size) {
        return new dz.sh.hidra.modules.organization.application.query.EmployeeAppointmentQuery.Current(null,employee,unit,null,page,size,false);
    }














}
