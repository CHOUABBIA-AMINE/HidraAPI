/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : EmployeeAppointmentConcurrencyPostgresIntegrationTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-25
 *
 * @Type        : Class
 * @Layer       : Test
 * @Module      : organization
 * @Package     : dz.sh.hidra.modules.organization.infrastructure.persistence
 *
 * @Description : Proves locked command classification and committed receipt replay before current source CAS.
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
 * No HTTP, non-LOCAL runtime, deployment or industrial acceptance is established.
 */
@Testcontainers(disabledWithoutDocker = true)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@org.springframework.boot.test.context.SpringBootTest(classes=dz.sh.hidra.HidraApplication.class,
        webEnvironment=org.springframework.boot.test.context.SpringBootTest.WebEnvironment.MOCK,
        properties={"spring.flyway.enabled=true","spring.jpa.hibernate.ddl-auto=validate",
            // Bounded synthetic qualification ceiling; not an accepted operational SLA.
            "hidra.platform.persistence.execution.appointment-work-budget-ms=10000",
            "hidra.platform.security.jwt.hmac-secret=synthetic-appointment-runtime-key-32-bytes-only",
            "hidra.platform.security.jwt.token-issuer=hidra-api","hidra.platform.security.jwt.audience=hidra-api"})
@org.springframework.test.context.ActiveProfiles("test")
@org.springframework.test.annotation.DirtiesContext(classMode=org.springframework.test.annotation.DirtiesContext.ClassMode.AFTER_CLASS)
class EmployeeAppointmentConcurrencyPostgresIntegrationTest {
    // Isolate explicit-writer tests from ordinary background maintenance in this disposable database.
    @Container static final PostgreSQLContainer<?> POSTGRES = new PostgreSQLContainer<>("postgres:16")
            .withCommand("postgres","-c","autovacuum=off");
    private static final String PASSWORD="disposable-classification-fixture", CONFIG="classification_config";
    private static final String RUNTIME_APP="appointment_runtime_app", RUNTIME_PUBLISHER="appointment_runtime_publisher";
    @org.springframework.beans.factory.annotation.Autowired JdbcTemplate runtimeJdbc;
    @org.springframework.beans.factory.annotation.Autowired jakarta.persistence.EntityManager runtimeEntityManager;
    @org.springframework.beans.factory.annotation.Autowired ProtectedTransactionExecutor runtimeExecutor;
    @org.springframework.beans.factory.annotation.Autowired OrganizationProtectedAppointmentAuthorizationContract runtimeIdentity;
    @org.springframework.beans.factory.annotation.Autowired OrganizationProtectedPartyEligibilityContract runtimeParty;
    @org.springframework.beans.factory.annotation.Autowired dz.sh.hidra.modules.identity.application.port.in.AuthenticateDirectUserUseCase runtimeLogin;
    @org.springframework.beans.factory.annotation.Autowired org.springframework.security.crypto.password.PasswordEncoder runtimePasswords;
    @org.springframework.beans.factory.annotation.Autowired dz.sh.hidra.modules.identity.infrastructure.configuration.OrganizationAppointmentCommitProofProperties runtimeKeys;

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
    private String runtimeActor(Fixture f) throws Exception {
        String actor=id(),provider=id();
        admin("UPDATE public.hidra_identity_provider SET status='INACTIVE' WHERE status='ACTIVE'");
        admin("INSERT INTO public.hidra_identity_user(id,username,user_type,status,failed_login_count,created_at,updated_at) VALUES (?,?,'HUMAN','ACTIVE',0,now(),now())",actor,actor);
        admin("INSERT INTO public.hidra_identity_provider(id,code,name,provider_type,status,sync_enabled,just_in_time_provisioning_enabled,created_at,updated_at) VALUES (?,?,'Runtime fixture','LOCAL','ACTIVE',false,false,now(),now())",provider,provider);
        admin("INSERT INTO public.hidra_identity_local_credential(id,user_id,password_hash,credential_status,created_at,updated_at) VALUES (?,?,?,'ACTIVE',now(),now())",id(),actor,runtimePasswords.encode("runtime-synthetic-password"));
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
        runtimeAuthenticate(actor);return actor;
    }

    private void runtimeAuthenticate(String actor) {
        var result=runtimeLogin.authenticate(new dz.sh.hidra.modules.identity.application.model.DirectAuthenticationCommand(
                dz.sh.hidra.modules.identity.domain.value.ProviderType.LOCAL,actor,"runtime-synthetic-password","synthetic-ip","synthetic-agent","runtime-correlation"));
        var jwt=org.springframework.security.oauth2.jwt.Jwt.withTokenValue(result.accessToken()).header("alg","HS256")
                .subject("untrusted-wrapper").issuedAt(Instant.now().minusSeconds(1)).expiresAt(Instant.now().plusSeconds(300)).build();
        SecurityContextHolder.getContext().setAuthentication(new org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken(jwt,List.of()));
    }

    private dz.sh.hidra.modules.organization.application.service.EmployeeAppointmentLifecycleApplicationService runtimeService(jakarta.persistence.EntityManager em) {
        var repository=new JdbcProtectedEmployeeAppointmentRepositoryAdapter(runtimeJdbc,runtimeIdentity,runtimeParty,em);
        var coordinator=new ProtectedEmployeeAppointmentAdmissionCoordinator(runtimeIdentity,runtimeParty,repository,runtimeExecutor);
        return new dz.sh.hidra.modules.organization.application.service.EmployeeAppointmentLifecycleApplicationService(coordinator,runtimeExecutor,repository);
    }

    /** Complete synthetic service call, including preparation and observed commit, never HTTP latency. */
    private static <T> T measuredRuntime(String stage,java.util.function.Supplier<T> action) {
        long began=System.nanoTime();boolean success=false;
        try {T result=tracedRuntime("UNCOORDINATED_"+stage,new RuntimeTrace(),action);success=true;return result;}
        finally {System.out.println("HAPI_778_TIMING_QUALIFICATION stage="+stage
                +" success="+success+" callElapsedNs="+(System.nanoTime()-began)
                +" workCeilingMs=10000 operationallyAccepted=false");}
    }

    @Test void genuineLocalLifecycleReplayAndScopedReadsUsePrivatePublisherAndObservedCommit() throws Exception {
        var f=fixture(false);String actor=runtimeActor(f);var service=runtimeService(runtimeEntityManager);var command=create(f,id());
        var created=measuredRuntime("CREATE",()->service.create(command));String assignment=created.after().assignmentId();
        assertFalse(created.historicalReplay());assertEquals(1,created.after().appointmentVersion());
        var corrected=measuredRuntime("CORRECT",()->service.correct(new EmployeeAppointmentCommand.Correct(id(),f.company(),assignment,1,f.units().get(0),f.position(),AssignmentType.PRIMARY,AssignmentStatus.ACTIVE,START,null,1,1,REASON)));
        assertEquals(2,corrected.after().appointmentVersion());
        var transferred=measuredRuntime("TRANSFER",()->service.transfer(new EmployeeAppointmentCommand.Transfer(id(),f.company(),assignment,2,f.units().get(1),f.position(),AssignmentType.PRIMARY,START.plusSeconds(3600),null,1,1,REASON)));
        assertEquals(3,transferred.after().appointmentVersion());assertNotNull(transferred.successor());
        measuredRuntime("END",()->service.end(new EmployeeAppointmentCommand.End(id(),f.company(),transferred.successor().assignmentId(),1,START.plusSeconds(7200),REASON)));
        admin("UPDATE public.hidra_party_party SET status='SUSPENDED' WHERE id=?",f.company());
        runtimeAuthenticate(actor);var replay=measuredRuntime("REPLAY",()->service.create(command));
        assertTrue(replay.historicalReplay());assertEquals(created.eventId(),replay.eventId());assertEquals(created.after(),replay.after());
        assertEquals(4,service.employeeHistory(dz.sh.hidra.modules.organization.application.query.EmployeeAppointmentQuery.History.employee(f.company(),f.employee(),100,null)).entries().size());
        assertEquals(1,service.exactVersion(new dz.sh.hidra.modules.organization.application.query.EmployeeAppointmentQuery.ExactVersion(f.company(),assignment,1)).appointmentVersion());
        assertEquals(f.company(),service.readPolicy(new dz.sh.hidra.modules.organization.application.query.EmployeeAppointmentQuery.Policy(f.company(),f.units().get(0))).companyPartyId());
        assertFalse(service.positionHistory(dz.sh.hidra.modules.organization.application.query.EmployeeAppointmentQuery.History.position(f.company(),f.units().get(0),f.position(),100,null)).entries().isEmpty());
        try(var c=owner()) {
            assertEquals("4",scalar(c,"SELECT count(*) FROM public.hidra_org_appointment_event WHERE employee_id=?",f.employee()));
            assertEquals("4",scalar(c,"SELECT count(*) FROM public.hidra_org_appointment_event WHERE employee_id=? AND actor_user_id=?",f.employee(),actor));
            assertEquals("4",scalar(c,"SELECT count(*) FROM public.hidra_identity_org_commit_proof_use u JOIN public.hidra_org_appointment_event e USING(event_id) WHERE e.employee_id=?",f.employee()));
            assertEquals("4",scalar(c,"SELECT count(*) FROM public.hidra_org_appointment_receipt r JOIN public.hidra_org_appointment_event e USING(event_id) WHERE e.employee_id=?",f.employee()));
        } finally {SecurityContextHolder.clearContext();}
        assertEquals(RUNTIME_APP,runtimeJdbc.queryForObject("SELECT session_user",String.class));
    }

    @Test void genuineNativeDenialDoesNotCreateProjectionEventReceiptOrKeys() throws Exception {
        var f=fixture(false);String actor=runtimeActor(f);
        admin("DELETE FROM public.hidra_identity_user_permission_grant WHERE user_id=?",actor);runtimeAuthenticate(actor);
        assertThrows(RuntimeException.class,()->runtimeService(runtimeEntityManager).create(create(f,id())));
        try(var c=owner()) {
            assertEquals("0",scalar(c,"SELECT count(*) FROM public.hidra_org_employee_assignment WHERE employee_id=?",f.employee()));
            assertEquals("0",scalar(c,"SELECT count(*) FROM public.hidra_org_appointment_event WHERE employee_id=?",f.employee()));
            assertEquals("0",scalar(c,"SELECT count(*) FROM public.hidra_org_appointment_lock_key WHERE kind='EMPLOYEE' AND key_one=?",f.employee()));
        } finally {SecurityContextHolder.clearContext();}
    }

    @Test void genuineExecutionRollsBackProvisionalWritesWhenActualManagedFlushFails() throws Exception {
        var f=fixture(false);runtimeActor(f);var em=spy(runtimeEntityManager);
        doAnswer(call->{runtimeEntityManager.flush();throw new IllegalStateException("synthetic post-flush failure");}).when(em).flush();
        assertThrows(RuntimeException.class,()->runtimeService(em).create(create(f,id())));
        verify(em).flush();
        try(var c=owner()) {
            assertEquals("0",scalar(c,"SELECT count(*) FROM public.hidra_org_employee_assignment WHERE employee_id=?",f.employee()));
            assertEquals("0",scalar(c,"SELECT count(*) FROM public.hidra_org_appointment_event WHERE employee_id=?",f.employee()));
            assertEquals("0",scalar(c,"SELECT count(*) FROM public.hidra_org_appointment_lock_key WHERE kind='EMPLOYEE' AND key_one=?",f.employee()));
        } finally {SecurityContextHolder.clearContext();}
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

    private static final ThreadLocal<RuntimeTrace> RUNTIME_TRACE=new ThreadLocal<>();
    private static final ThreadLocal<NativeExpiryFault> NATIVE_EXPIRY_FAULT=new ThreadLocal<>();

    /** Active fault injection, never part of the passive contention measurements. */
    private static final class NativeExpiryFault {
        final Instant expectedUntil;
        Instant nativeUntil,observedExpiry;int pid,guardAttempts,clockReads,postGuardStatements;
        long xid,began,dispatchedAt;String guardSqlState;
        NativeExpiryFault(Instant expectedUntil){this.expectedUntil=expectedUntil;}
        void binding(Object[] args) {
            if(args==null||args.length<2||!(args[0] instanceof Integer index))return;
            if(index==9&&args[1] instanceof java.sql.Timestamp value)nativeUntil=value.toInstant();
            if(index==10&&args[1] instanceof Number value)pid=value.intValue();
            if(index==11&&args[1] instanceof Number value)xid=value.longValue();
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
    private static final class RuntimeTrace {
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
                    +" workCeilingMs=10000 includesHarnessWork=true operationallyAccepted=false");
        }
        private static long interval(long start,long end){return start==0||end==0?-1:end-start;}
    }

    private static <T> T tracedRuntime(String stage,RuntimeTrace trace,java.util.function.Supplier<T> action) {
        assertNull(RUNTIME_TRACE.get());trace.began=System.nanoTime();RUNTIME_TRACE.set(trace);
        try {T result=action.get();trace.success=true;return result;}
        finally {trace.ended=System.nanoTime();RUNTIME_TRACE.remove();trace.print(stage);}
    }

    private static Object delegated(Object target,java.lang.reflect.Method method,Object[] args) throws Throwable {
        try{return method.invoke(target,args);}
        catch(java.lang.reflect.InvocationTargetException failure){throw failure.getCause();}
    }
    @SuppressWarnings("unchecked")
    private static <T> T jdbcObserver(Class<T> type,T target,java.lang.reflect.InvocationHandler handler) {
        return (T)java.lang.reflect.Proxy.newProxyInstance(type.getClassLoader(),new Class<?>[]{type},(proxy,method,args)->{
            if(method.getDeclaringClass()==Object.class)return switch(method.getName()) {
                case "equals"->proxy==args[0];case "hashCode"->System.identityHashCode(proxy);
                case "toString"->"QualificationJdbcHandle";default->throw new AssertionError();
            };
            return handler.invoke(target,method,args);
        });
    }
    private static Connection observedConnection(Connection physical) {
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
    private static java.sql.Statement observedStatement(Connection physical,java.sql.Statement statement,String preparedSql) {
        Class<? extends java.sql.Statement> type=statement instanceof java.sql.CallableStatement?java.sql.CallableStatement.class:
                statement instanceof java.sql.PreparedStatement?java.sql.PreparedStatement.class:java.sql.Statement.class;
        return observedStatementOfType(type,physical,statement,preparedSql);
    }
    private static <T extends java.sql.Statement> T observedStatementOfType(Class<T> type,Connection physical,java.sql.Statement statement,String preparedSql) {
        return jdbcObserver(type,type.cast(statement),(target,method,args)->{
            var trace=RUNTIME_TRACE.get();String name=method.getName();
            String sql=preparedSql!=null?preparedSql:args!=null&&args.length>0&&args[0] instanceof String text?text:null;
            boolean executing=name.startsWith("execute"),guard=sql!=null&&sql.equals(FINAL_SQL);
            if(trace!=null&&executing) {
                trace.interceptedPublisherExecutions++;
                if(sql!=null&&sql.startsWith("WITH identity_claim_locks AS MATERIALIZED"))trace.qualifiedFenceChecks++;
            }
            var expiry=NATIVE_EXPIRY_FAULT.get();
            if(expiry!=null) {
                if(guard&&name.startsWith("set"))expiry.binding(args); // Observe only; delegate identical parameters.
                if(executing&&expiry.dispatchedAt!=0)expiry.postGuardStatements++;
                if(executing&&guard)expiry.beforeGuard(physical);
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

    private static <T> T authenticated(org.springframework.security.core.Authentication authentication,java.util.concurrent.Callable<T> action) throws Exception {
        var context=SecurityContextHolder.createEmptyContext();context.setAuthentication(authentication);
        SecurityContextHolder.setContext(context);
        try{return action.call();}finally{SecurityContextHolder.clearContext();}
    }
    private static void stopWorker(java.util.concurrent.ExecutorService worker) throws InterruptedException {
        worker.shutdownNow();assertTrue(worker.awaitTermination(10,java.util.concurrent.TimeUnit.SECONDS),"Qualification worker did not terminate");
    }
    private static boolean sqlState(Throwable failure,String expected) {
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
    private String runtimeEffects(Fixture f,String actor) throws SQLException {
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

    @Test void genuineCompetingAppointmentRejectsWithoutPartialEffectsWhileWinnerCommits() throws Exception {
        var f=fixture(false);String actor=runtimeActor(f);var authentication=SecurityContextHolder.getContext().getAuthentication();
        var winner=create(f,id());var loser=create(f,id());var winnerTrace=new RuntimeTrace();var loserTrace=new RuntimeTrace();
        var go=new java.util.concurrent.CountDownLatch(1);var ready=new java.util.concurrent.CountDownLatch(1);
        var worker=java.util.concurrent.Executors.newSingleThreadExecutor();
        try {
            var competitor=worker.submit(()->authenticated(authentication,()->{
                ready.countDown();assertTrue(go.await(30,java.util.concurrent.TimeUnit.SECONDS));
                var failure=assertThrows(ProtectedTransactionExecutor.ExecutionFailure.class,()->
                        tracedRuntime("COMPETING_CREATE",loserTrace,()->runtimeService(runtimeEntityManager).create(loser)));
                assertEquals(ProtectedTransactionExecutor.Outcome.REJECTED_BEFORE_COMMIT,failure.outcome());
                assertTrue(sqlState(failure,"55P03"));return failure.outcome();
            }));
            assertTrue(ready.await(5,java.util.concurrent.TimeUnit.SECONDS));
            var em=spy(runtimeEntityManager);
            doAnswer(call->{runtimeEntityManager.flush();go.countDown();
                assertEquals(ProtectedTransactionExecutor.Outcome.REJECTED_BEFORE_COMMIT,
                        competitor.get(650,java.util.concurrent.TimeUnit.MILLISECONDS));return null;}).when(em).flush();
            var receipt=tracedRuntime("WINNING_CREATE",winnerTrace,()->runtimeService(em).create(winner));
            assertEquals(winner.requestId(),receipt.requestId());winnerTrace.committed();loserTrace.rejected();verify(em).flush();
            assertEquals("1,1,1,1,3",runtimeEffects(f,actor));
            try(var c=owner()) {
                assertEquals("0",scalar(c,"SELECT count(*) FROM public.hidra_org_appointment_lock_key WHERE kind='REQUEST' AND key_one=? AND key_three=?",actor,loser.requestId()));
                assertEquals("0",scalar(c,"SELECT count(*) FROM public.hidra_org_appointment_receipt WHERE actor_user_id=? AND request_id=?",actor,loser.requestId()));
            }
        } finally {go.countDown();stopWorker(worker);}
    }

    private record WriterObservation(String state,long elapsedNs) { }
    @Test void genuineAuthorityFenceBoundsRevocationAttemptThenCommittedRevocationDeniesOldToken() throws Exception {
        var f=fixture(false);String actor=runtimeActor(f);var trace=new RuntimeTrace();
        var go=new java.util.concurrent.CountDownLatch(1);var ready=new java.util.concurrent.CountDownLatch(1);
        var worker=java.util.concurrent.Executors.newSingleThreadExecutor();
        try {
            var writer=worker.submit(()->{
                try(var c=owner()) {
                    ready.countDown();assertTrue(go.await(30,java.util.concurrent.TimeUnit.SECONDS));c.setAutoCommit(false);
                    execute(c,"SET LOCAL lock_timeout='150ms'");execute(c,"SET LOCAL statement_timeout='500ms'");
                    long began=System.nanoTime();String state;
                    try {execute(c,"DELETE FROM public.hidra_identity_user_permission_grant WHERE user_id=?",actor);state="UNEXPECTED_SUCCESS";}
                    catch(SQLException failure){state=failure.getSQLState();if(!"55P03".equals(state))throw failure;}
                    finally {c.rollback();}
                    return new WriterObservation(state,System.nanoTime()-began);
                }
            });
            assertTrue(ready.await(5,java.util.concurrent.TimeUnit.SECONDS));
            var em=spy(runtimeEntityManager);
            doAnswer(call->{runtimeEntityManager.flush();go.countDown();var result=writer.get(650,java.util.concurrent.TimeUnit.MILLISECONDS);
                assertEquals("55P03",result.state());
                System.out.println("HAPI_778_CONTENTION_QUALIFICATION stage=REVOCATION_ATTEMPT sqlState=55P03 writerElapsedNs="+result.elapsedNs()+" operationallyAccepted=false");
                return null;}).when(em).flush();
            var receipt=tracedRuntime("CREATE_BEFORE_REVOCATION",trace,()->runtimeService(em).create(create(f,id())));
            trace.committed();verify(em).flush();assertEquals("1,1,1,1,3",runtimeEffects(f,actor));
            // Separate explicit action after known completion, never retry inside either operation.
            admin("DELETE FROM public.hidra_identity_user_permission_grant WHERE user_id=?",actor);
            try(var c=owner()){assertEquals("0",scalar(c,"SELECT count(*) FROM public.hidra_identity_user_permission_grant WHERE user_id=?",actor));}
            var deniedTrace=new RuntimeTrace();String before=runtimeEffects(f,actor);var deniedEm=spy(runtimeEntityManager);
            // END is otherwise valid: a second CREATE could be rejected by overlap even with stale authority.
            var endCommand=new EmployeeAppointmentCommand.End(id(),f.company(),receipt.after().assignmentId(),1,START.plusSeconds(3600),REASON);
            String projection;
            try(var c=owner()){projection=scalar(c,"SELECT encode(sha256(convert_to(to_jsonb(a)::text,'UTF8')),'hex') FROM public.hidra_org_employee_assignment a WHERE id=?",receipt.after().assignmentId());}
            var failure=assertThrows(ProtectedTransactionExecutor.ExecutionFailure.class,()->tracedRuntime("OLD_TOKEN_AFTER_REVOCATION",deniedTrace,
                    ()->runtimeService(deniedEm).end(endCommand)));
            assertEquals(ProtectedTransactionExecutor.Outcome.REJECTED_BEFORE_COMMIT,failure.outcome());deniedTrace.rejected();
            verify(deniedEm,never()).flush();assertEquals(0,deniedTrace.guardBegan);assertFalse(sqlState(failure,"55P03"));
            assertTrue(deniedTrace.ended-deniedTrace.began<10_000_000_000L,"Denial must precede the work ceiling");
            assertEquals(before,runtimeEffects(f,actor));
            try(var c=owner()){assertEquals(projection,scalar(c,"SELECT encode(sha256(convert_to(to_jsonb(a)::text,'UTF8')),'hex') FROM public.hidra_org_employee_assignment a WHERE id=?",receipt.after().assignmentId()));}
        } finally {go.countDown();stopWorker(worker);}
    }

    @Test void genuineCommittedRevocationBeforeAdmissionRejectsWithoutAnyEffects() throws Exception {
        var f=fixture(false);String actor=runtimeActor(f);var original=SecurityContextHolder.getContext().getAuthentication();
        admin("DELETE FROM public.hidra_identity_user_permission_grant WHERE user_id=?",actor);
        try(var c=owner()){assertEquals("0",scalar(c,"SELECT count(*) FROM public.hidra_identity_user_permission_grant WHERE user_id=?",actor));}
        var trace=new RuntimeTrace();var em=spy(runtimeEntityManager);assertEquals("0,0,0,0,0",runtimeEffects(f,actor));
        var failure=assertThrows(ProtectedTransactionExecutor.ExecutionFailure.class,()->tracedRuntime("REVOCATION_BEFORE_ADMISSION",trace,
                ()->runtimeService(em).create(create(f,id()))));
        assertSame(original,SecurityContextHolder.getContext().getAuthentication());
        assertEquals(ProtectedTransactionExecutor.Outcome.REJECTED_BEFORE_COMMIT,failure.outcome());trace.rejected();
        verify(em,never()).flush();assertEquals(0,trace.guardBegan);assertFalse(sqlState(failure,"55P03"));
        assertTrue(trace.ended-trace.began<10_000_000_000L,"Denial must precede the work ceiling");
        assertEquals("0,0,0,0,0",runtimeEffects(f,actor));
    }

    @Test void genuineNativeExpiryAtFinalDatabaseGuardRollsBackWhileWorkBudgetRemains() throws Exception {
        var f=fixture(false);String actor=runtimeActor(f);var command=create(f,id());var trace=new RuntimeTrace();
        Instant until=null;int grants=0;
        // One original persisted deadline for every actual scoped grant, before service preparation.
        try(var c=owner();var statement=c.prepareStatement("""
                WITH boundary AS MATERIALIZED (SELECT pg_catalog.clock_timestamp()+interval '8 seconds' AS u)
                UPDATE public.hidra_identity_user_permission_grant g SET valid_to=boundary.u
                  FROM boundary WHERE g.user_id=? RETURNING g.valid_to
                """)) {
            statement.setString(1,actor);
            try(var rows=statement.executeQuery()) {
                while(rows.next()){Instant value=rows.getTimestamp(1).toInstant();if(until==null)until=value;else assertEquals(until,value);grants++;}
            }
        }
        assertTrue(grants>0);assertNotNull(until);var fault=new NativeExpiryFault(until);var em=spy(runtimeEntityManager);
        assertNull(NATIVE_EXPIRY_FAULT.get());NATIVE_EXPIRY_FAULT.set(fault);
        try {
            var failure=assertThrows(ProtectedTransactionExecutor.ExecutionFailure.class,()->tracedRuntime("NATIVE_EXPIRY_FAULT",trace,
                    ()->runtimeService(em).create(command)));
            assertEquals(ProtectedTransactionExecutor.Outcome.REJECTED_BEFORE_COMMIT,failure.outcome());trace.rejected();verify(em).flush();
            assertEquals(1,fault.guardAttempts);assertNotNull(fault.observedExpiry);assertEquals(until,fault.nativeUntil);
            assertFalse(fault.observedExpiry.isBefore(until));assertEquals("P0001",fault.guardSqlState);
            assertTrue(trace.guardBegan>=fault.dispatchedAt);assertEquals(0,trace.guardClosed);assertEquals(0,fault.postGuardStatements);
            assertTrue(trace.ended-trace.began<10_000_000_000L,"Complete failed call must leave work budget remaining");
            assertFalse(sqlState(failure,"57014"));assertFalse(sqlState(failure,"25P03"));
            System.out.println("HAPI_778_EXPIRY_QUALIFICATION nativeExpired=true guardSqlState=P0001 driverClockReads="+fault.clockReads
                    +" faultElapsedNs="+(fault.dispatchedAt-fault.began)
                    +" remainingWorkBudgetLowerBoundNs="+(10_000_000_000L-(fault.dispatchedAt-trace.began))
                    +" activeFault=true operationallyAccepted=false");
        } finally {NATIVE_EXPIRY_FAULT.remove();}
        assertEquals("0,0,0,0,0",runtimeEffects(f,actor));
    }

    private static final Instant START=Instant.parse("2026-10-20T10:00:00Z");
    private static final String REASON="Change é\t\n retained";
    private static final JsonMapper JSON=JsonMapper.builder().build();
    private static final List<ProtectedRelationFence> KEYS=List.of(
            new ProtectedRelationFence("public.hidra_identity_org_commit_key_event",ProtectedRelationFence.Mode.ACCESS_SHARE),
            new ProtectedRelationFence("public.hidra_identity_org_commit_key_head",ProtectedRelationFence.Mode.SHARE),
            new ProtectedRelationFence("public.hidra_identity_org_commit_proof_use",ProtectedRelationFence.Mode.SHARE_ROW_EXCLUSIVE));
    private String partyType,unitType;
    private Fixture baseline,historical;
    private EmployeeAppointmentCommand.Correct originalCorrection;

    private record Fixture(String company,String employee,String assignment,String position,List<String> units,
                           String policy,String createRequest) { }

    private Connection owner() throws SQLException {
        return DriverManager.getConnection(POSTGRES.getJdbcUrl(),POSTGRES.getUsername(),POSTGRES.getPassword());
    }
    private void admin(String sql,Object... args) throws SQLException {
        try(var c=owner()){execute(c,sql,args);}
    }
    private static void execute(Connection c,String sql,Object... args) throws SQLException {
        try(var p=c.prepareStatement(sql)){for(int i=0;i<args.length;i++)p.setObject(i+1,args[i]);p.execute();}
    }
    private static String scalar(Connection c,String sql,Object... args) throws SQLException {
        try(var p=c.prepareStatement(sql)){
            for(int i=0;i<args.length;i++)p.setObject(i+1,args[i]);
            try(var r=p.executeQuery()){assertTrue(r.next());String value=r.getString(1);assertFalse(r.next());return value;}
        }
    }
    private static String id(){return UUID.randomUUID().toString();}
    private static String policy(){
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

    private Fixture fixture() throws Exception { return fixture(true); }
    private Fixture fixture(boolean seeded) throws Exception {
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
    private void describeFixtureConfigurationLocks(SQLException original) {
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

    private void seedCreate(Fixture f,Instant end) throws Exception {
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

    private static EmployeeAppointmentCommand.Correct correction(Fixture f,String request,long version,String unit){
        return new EmployeeAppointmentCommand.Correct(request,f.company(),f.assignment(),version,unit,f.position(),
                AssignmentType.PRIMARY,AssignmentStatus.ACTIVE,START,null,1,1,REASON);
    }
    private void commitCorrection(Fixture f,EmployeeAppointmentCommand.Correct command) throws Exception {
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
    private static void ledger(Connection c,Fixture f,String event,String kind,String command,String before) throws Exception {
        String body=scalar(c,"SELECT jsonb_build_object('command',?::jsonb,'before',?::jsonb,'after',public.hidra_org_appointment_snapshot(a),'successor',NULL)::text FROM public.hidra_org_employee_assignment a WHERE id=?",command,before,f.assignment());
        execute(c,"INSERT INTO public.hidra_org_appointment_event(event_id,operation,assignment_id,employee_id,company_party_id,policy_id,policy_revision,canonical_payload,command_sha256,actor_binding_id,actor_session_id,actor_user_id,sampled_at,valid_until,backend_pid,transaction_id) SELECT ?::uuid,?,?,?,?,?,1,convert_to(p::text,'UTF8'),encode(sha256(convert_to((p->'command')::text,'UTF8')),'hex'),gen_random_uuid(),gen_random_uuid(),'synthetic-actor',transaction_timestamp(),transaction_timestamp()+interval '1 hour',pg_backend_pid(),txid_current() FROM (SELECT ?::jsonb p) v",event,kind,f.assignment(),f.employee(),f.company(),f.policy(),body);
        execute(c,"INSERT INTO public.hidra_org_appointment_receipt(event_id,actor_user_id,scope_key,request_id,command_sha256,canonical_result,guarded_at,backend_pid,transaction_id) SELECT event_id,actor_user_id,encode(sha256(convert_to(jsonb_build_object('family','organization-appointment-v1','companyPartyId',company_party_id)::text,'UTF8')),'hex'),convert_from(canonical_payload,'UTF8')::jsonb->'command'->>'requestId',command_sha256,canonical_payload,sampled_at,backend_pid,transaction_id FROM public.hidra_org_appointment_event WHERE event_id=?::uuid",event);
        execute(c,"SET CONSTRAINTS ALL IMMEDIATE");
    }

    private final class Session implements AutoCloseable {
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
    private final class ProvisionalSession implements AutoCloseable {
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

    @Test void actualProvisionalCreateEndCorrectionAndTransferMatchFrozenCanonicalEventsAndRollBack() throws Exception {
        for(int kind=0;kind<5;kind++){
            var f=fixture(kind!=0);
            EmployeeAppointmentCommand command=switch(kind){
                case 0 -> create(f,id());
                case 1 -> end(f,1);
                case 2 -> correction(f,id(),1,f.units().get(0));
                case 3 -> correction(f,id(),1,f.units().get(1));
                default -> new EmployeeAppointmentCommand.Transfer(id(),f.company(),f.assignment(),1,f.units().get(1),f.position(),
                        AssignmentType.PRIMARY,START.plusSeconds(3600),null,1,1,REASON);
            };
            try(var p=new ProvisionalSession(command)){
                p.s.tx.execute(status -> {
                    p.persist();
                    assertDoesNotThrow(() -> p.invoke("requireCurrentWrite",p.write));
                    assertEquals(command instanceof EmployeeAppointmentCommand.Transfer?2:1,p.s.jdbc.queryForObject("SELECT count(*) FROM public.hidra_org_employee_assignment WHERE employee_id=?",Integer.class,f.employee()));
                    assertEquals(1,p.s.jdbc.queryForObject("SELECT count(*) FROM public.hidra_org_appointment_event WHERE employee_id=? AND actor_user_id=?",Integer.class,f.employee(),p.s.actor));
                    assertEquals(0,p.s.jdbc.queryForObject("SELECT count(*) FROM public.hidra_org_appointment_receipt WHERE event_id IN (SELECT event_id FROM public.hidra_org_appointment_event WHERE employee_id=? AND actor_user_id=?)",Integer.class,f.employee(),p.s.actor));
                    verify(p.executor,times(1)).beginProvisional(any(),argThat(sql -> sql.size()==6));
                    verify(p.executor,times(1)).endProvisional(any());
                    status.setRollbackOnly();return null;
                });
            }
            try(var c=owner()){
                assertEquals(kind==0?"0":"1",scalar(c,"SELECT count(*) FROM public.hidra_org_employee_assignment WHERE employee_id=?",f.employee()));
                assertEquals("0",scalar(c,"SELECT count(*) FROM public.hidra_org_appointment_lock_key WHERE kind='EMPLOYEE' AND key_one=?",f.employee()));
                assertEquals(kind==0?"0":"1",scalar(c,"SELECT count(*) FROM public.hidra_org_appointment_event WHERE employee_id=?",f.employee()));
            }
        }
    }

    @Test void swallowedProvisionalFailureAndMissingFinalReceiptVetoPhysicalCommit() throws Exception {
        for(boolean failWrite:List.of(false,true)){
            var f=fixture(false);var command=create(f,id());
            try(var p=new ProvisionalSession(command)){
                if(failWrite)admin("REVOKE INSERT ON public.hidra_org_appointment_event FROM "+p.s.role);
                var failure=assertThrows(RuntimeException.class,() -> p.s.tx.execute(status -> {
                    if(failWrite)assertThrows(AccessDeniedException.class,p::persist);else p.persist();
                    return null; // Deliberately swallow the write failure or omit final proof/receipt.
                }));
                assertTrue(failure instanceof AccessDeniedException || failure instanceof org.springframework.transaction.UnexpectedRollbackException);
            }
            try(var c=owner()){
                assertEquals("0",scalar(c,"SELECT count(*) FROM public.hidra_org_employee_assignment WHERE employee_id=?",f.employee()));
                assertEquals("0",scalar(c,"SELECT count(*) FROM public.hidra_org_appointment_event WHERE employee_id=?",f.employee()));
                assertEquals("0",scalar(c,"SELECT count(*) FROM public.hidra_org_appointment_lock_key WHERE kind='EMPLOYEE' AND key_one=?",f.employee()));
            }
        }
    }

    @Test void sameTransactionPostWriteProjectionAndPeerSubstitutionCannotPassFrozenEvidence() throws Exception {
        for(boolean peer:List.of(false,true)){
            var f=fixture(false);var command=create(f,id());
            try(var p=new ProvisionalSession(command)){p.s.tx.execute(status -> {
                p.persist();
                if(peer){
                    // A legacy ACTIVE row in the same physical transaction must not evade the complete post-write conflict probe.
                    p.s.jdbc.update("INSERT INTO public.hidra_org_employee_assignment(id,employee_id,organization_unit_id,position_id,assignment_type,valid_from,status,created_at,updated_at) VALUES (?,?,?,?,'PRIMARY',?,'ACTIVE',now(),now())",
                            id(),f.employee(),f.units().get(2),f.position(),java.sql.Timestamp.from(START));
                }else{
                    // The old plan remains a value, never permission to accept another source version/event.
                    p.s.jdbc.update("UPDATE public.hidra_org_employee_assignment SET appointment_version=appointment_version+1,last_event_id=?::uuid WHERE employee_id=?",id(),f.employee());
                }
                assertThrows(AccessDeniedException.class,() -> p.invoke("requireCurrentWrite",p.write));
                assertTrue(p.s.poisoned());status.setRollbackOnly();return null;
            });}
        }
    }

    @Test void directStorageCallCannotWriteAndReplayCannotCreateKeysOrEvents() throws Exception {
        var f=fixture(false);var command=create(f,id());
        try(var s=new Session(command)){s.tx.execute(status -> {
            var ticket=qualified(s);s.storage.contributeParty(ticket);s.storage.prepareMutation(ticket,command);
            var statements=s.storage.prepareProvisional(ticket,command);
            var hashes=statements.stream().map(sql -> {
                try{return java.util.HexFormat.of().formatHex(java.security.MessageDigest.getInstance("SHA-256").digest(sql.getBytes(java.nio.charset.StandardCharsets.UTF_8)));}
                catch(java.security.NoSuchAlgorithmException failure){throw new AssertionError(failure);}
            }).collect(java.util.stream.Collectors.toSet());
            assertEquals(java.util.Set.of("516725eb3d2d0fa98f75e73c62186d730c22b44b15d648ed03b1f4e88cba1af4","0991e6595ce13ba5b556da137e0211bc72ecc982a10a01feac5bcb2f9294c896","9d331551beaa09d534325126e04d71625f123dd606e5ca99f79b5e37edbec4d5","e1e6cbcbc2281f495441c7d63ead7d54dda6babfa4c8b1ad1fadbafb250454ee","5a891a894c55c6ebd50cf78641cf19a3c6ad748b4e8b4365eeab6b446424f424","db6d602e42ead6338d10af03c3086056a4c5d50e4b1b978b355c749d4dc57bda"),hashes);
            assertThrows(AccessDeniedException.class,() -> s.storage.persistProvisional(ticket));
            assertEquals(0,s.jdbc.queryForObject("SELECT count(*) FROM public.hidra_org_employee_assignment WHERE employee_id=?",Integer.class,f.employee()));
            status.setRollbackOnly();return null;
        });}
        try(var p=new ProvisionalSession(create(baseline,baseline.createRequest()))){p.s.actor="synthetic-actor";p.s.tx.execute(status -> {
            p.write=p.invoke("acquireWrite",p.prepared,p.s.command);
            assertThrows(AccessDeniedException.class,() -> p.invoke("persistProvisional",p.write));
            verify(p.executor,never()).beginProvisional(any(),any());status.setRollbackOnly();return null;
        });}
    }

    @Test void transferSuccessorOrEventFailureRollsBackTheSourceCloseAndEveryProvisionalKey() throws Exception {
        for(boolean successorFailure:List.of(true,false)){
            var f=fixture();
            var command=new EmployeeAppointmentCommand.Transfer(id(),f.company(),f.assignment(),1,f.units().get(1),f.position(),
                    AssignmentType.PRIMARY,START.plusSeconds(3600),null,1,1,REASON);
            try(var p=new ProvisionalSession(command)){
                admin("REVOKE INSERT ON public."+(successorFailure?"hidra_org_employee_assignment":"hidra_org_appointment_event")+" FROM "+p.s.role);
                var failure=assertThrows(RuntimeException.class,() -> p.s.tx.execute(status -> {
                    assertThrows(AccessDeniedException.class,p::persist);return null;
                }));
                assertTrue(failure instanceof AccessDeniedException || failure instanceof org.springframework.transaction.UnexpectedRollbackException);
            }
            try(var c=owner()){
                assertEquals("1",scalar(c,"SELECT count(*) FROM public.hidra_org_employee_assignment WHERE employee_id=?",f.employee()));
                assertEquals("1",scalar(c,"SELECT appointment_version FROM public.hidra_org_employee_assignment WHERE id=? AND valid_to IS NULL",f.assignment()));
                assertEquals("1",scalar(c,"SELECT count(*) FROM public.hidra_org_appointment_event WHERE employee_id=?",f.employee()));
                assertEquals("0",scalar(c,"SELECT count(*) FROM public.hidra_org_appointment_lock_key WHERE kind='EMPLOYEE' AND key_one=?",f.employee()));
            }
        }
    }

    // Intentionally unauthenticated transport: only the later SQL verifier can authenticate it.
    private static CommitProof syntheticProof(){return new CommitProof(new byte[]{1,2,3},new byte[32]);}

    /** Independent fixture encoder for the real SQL verifier, using synthetic native fields.
     * Actual Identity issuance and the real protected executor remain separate acceptance. */
    private void enableJavaFinalGuard(ProvisionalSession p,boolean invalidMac) throws Exception {
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

    @Test void combinedWriteCallbacksReturnDetachedReceiptsOnlyAfterActualCommitForEveryLifecycleShape() throws Exception {
        for(int kind=0;kind<5;kind++) {
            var f=fixture(kind!=0);
            EmployeeAppointmentCommand command=switch(kind) {
                case 0 -> create(f,id());case 1 -> end(f,1);
                case 2 -> correction(f,id(),1,f.units().get(0));case 3 -> correction(f,id(),1,f.units().get(1));
                default -> new EmployeeAppointmentCommand.Transfer(id(),f.company(),f.assignment(),1,f.units().get(1),f.position(),
                        AssignmentType.PRIMARY,START.plusSeconds(3600),null,1,1,REASON);
            };
            try(var p=new ProvisionalSession(command)) {
                enableJavaFinalGuard(p,false);
                var result=p.s.tx.execute(status->{
                    p.write=p.invoke("acquireWrite",p.prepared,command);
                    var detached=p.invoke("apply",p.write);
                    p.invoke("prepareFinal",p.write);
                    clearInvocations(p.s.identity,p.s.party);
                    p.invoke("finalizeOperation",p.write);verifyNoInteractions(p.s.identity,p.s.party);
                    return (dz.sh.hidra.modules.organization.application.dto.EmployeeAppointmentResult.MutationReceipt)detached;
                });
                assertNotNull(result);assertEquals(command.kind(),result.kind());assertEquals(command.requestId(),result.requestId());
                assertFalse(result.historicalReplay());assertEquals(f.employee(),result.after().snapshot().appointment().assignment().employeeId());
                assertEquals(kind==0?1:2,result.after().appointmentVersion());
                assertEquals(kind==4,result.successor()!=null);
                try(var c=owner()) {
                    assertEquals("1",scalar(c,"SELECT count(*) FROM public.hidra_org_appointment_receipt WHERE event_id=?::uuid",result.eventId()));
                }
            }
        }
    }

    @Test void combinedWriteResultRemainsInternalWhenFinalAuthenticationFails() throws Exception {
        var f=fixture(false);
        try(var p=new ProvisionalSession(create(f,id()))) {
            enableJavaFinalGuard(p,true);
            assertThrows(RuntimeException.class,()->p.s.tx.execute(status->{
                p.write=p.invoke("acquireWrite",p.prepared,p.s.command);
                assertNotNull(p.invoke("apply",p.write));p.invoke("prepareFinal",p.write);
                p.invoke("finalizeOperation",p.write);return null;
            }));
        }
        try(var c=owner()) {
            assertEquals("0",scalar(c,"SELECT count(*) FROM public.hidra_org_employee_assignment WHERE employee_id=?",f.employee()));
            assertEquals("0",scalar(c,"SELECT count(*) FROM public.hidra_org_appointment_event WHERE employee_id=?",f.employee()));
            assertEquals("0",scalar(c,"SELECT count(*) FROM public.hidra_org_appointment_lock_key WHERE kind='EMPLOYEE' AND key_one=?",f.employee()));
        }
    }

    @Test void combinedReplayUsesActualReadGuardAndCommitWithoutCreatingAnotherEventOrReceipt() throws Exception {
        var command=create(baseline,baseline.createRequest());
        try(var p=new ProvisionalSession(command)) {
            p.s.actor="synthetic-actor";
            admin("GRANT EXECUTE ON FUNCTION public.hidra_org_appointment_read_guard(jsonb,timestamptz,timestamptz,integer,bigint,timestamptz) TO "+p.s.role);
            var result=p.s.tx.execute(status->{
                p.write=p.invoke("acquireWrite",p.prepared,command);var detached=p.invoke("apply",p.write);
                p.invoke("prepareFinal",p.write);clearInvocations(p.s.identity,p.s.party);
                p.invoke("finalizeOperation",p.write);verifyNoInteractions(p.s.identity,p.s.party);
                return (dz.sh.hidra.modules.organization.application.dto.EmployeeAppointmentResult.MutationReceipt)detached;
            });
            assertNotNull(result);assertTrue(result.historicalReplay());assertEquals(baseline.assignment(),result.after().assignmentId());
            verify(p.executor,never()).beginProvisional(any(),any());verify(p.s.identity,never()).issueCommitProof(any(),any());
        }
        try(var c=owner()) {
            assertEquals("1",scalar(c,"SELECT count(*) FROM public.hidra_org_appointment_event WHERE employee_id=?",baseline.employee()));
            assertEquals("1",scalar(c,"SELECT count(*) FROM public.hidra_org_appointment_receipt r JOIN public.hidra_org_appointment_event e USING(event_id) WHERE e.employee_id=?",baseline.employee()));
        }
    }

    @Test void javaFinalGuardCommitsExactCreateEndCorrectionAndTransferWithReceiptAndNonce() throws Exception {
        for(int kind=0;kind<5;kind++){
            var f=fixture(kind!=0);
            EmployeeAppointmentCommand command=switch(kind){
                case 0 -> create(f,id());case 1 -> end(f,1);
                case 2 -> correction(f,id(),1,f.units().get(0));case 3 -> correction(f,id(),1,f.units().get(1));
                default -> new EmployeeAppointmentCommand.Transfer(id(),f.company(),f.assignment(),1,f.units().get(1),f.position(),
                        AssignmentType.PRIMARY,START.plusSeconds(3600),null,1,1,REASON);
            };
            try(var p=new ProvisionalSession(command)){
                enableJavaFinalGuard(p,false);
                p.s.tx.execute(status -> {
                    p.persist();p.invoke("prepareCommitProof",p.write);p.invoke("prepareFinalWrite",p.write);
                    clearInvocations(p.s.identity,p.s.party);
                    p.invoke("finalizeWrite",p.write);verifyNoInteractions(p.s.identity,p.s.party);
                    return null; // No owner SQL after GUARD; actual transaction commit runs both lifetime vetoes.
                });
            }
            assertEquals("1",finalCount("hidra_org_appointment_receipt",f.employee()));
            assertEquals("1",finalCount("hidra_identity_org_commit_proof_use",f.employee()));
            try(var c=owner()){
                assertEquals(kind==4?"2":"1",scalar(c,"SELECT count(*) FROM public.hidra_org_employee_assignment WHERE employee_id=?",f.employee()));
                assertEquals(kind==0?"1":"2",scalar(c,"SELECT count(*) FROM public.hidra_org_appointment_event WHERE employee_id=?",f.employee()));
            }
        }
    }

    @Test void javaFinalGuardRollbackRemovesReceiptNonceEventProjectionAndKeys() throws Exception {
        var f=fixture(false);
        try(var p=new ProvisionalSession(create(f,id()))){
            enableJavaFinalGuard(p,false);p.s.tx.execute(status -> {
                p.persist();p.invoke("prepareCommitProof",p.write);p.invoke("prepareFinalWrite",p.write);
                p.invoke("finalizeWrite",p.write);status.setRollbackOnly();return null;
            });
        }
        assertEquals("0",finalCount("hidra_org_appointment_receipt",f.employee()));
        assertEquals("0",finalCount("hidra_identity_org_commit_proof_use",f.employee()));
        try(var c=owner()){
            assertEquals("0",scalar(c,"SELECT count(*) FROM public.hidra_org_employee_assignment WHERE employee_id=?",f.employee()));
            assertEquals("0",scalar(c,"SELECT count(*) FROM public.hidra_org_appointment_event WHERE employee_id=?",f.employee()));
            assertEquals("0",scalar(c,"SELECT count(*) FROM public.hidra_org_appointment_lock_key WHERE kind='EMPLOYEE' AND key_one=?",f.employee()));
        }
    }

    @Test void javaFinalGuardRejectsForgedMacAndSwallowedFailureCannotCommitOrRetry() throws Exception {
        var f=fixture(false);
        try(var p=new ProvisionalSession(create(f,id()))){
            enableJavaFinalGuard(p,true);
            assertThrows(RuntimeException.class,() -> p.s.tx.execute(status -> {
                p.persist();p.invoke("prepareCommitProof",p.write);p.invoke("prepareFinalWrite",p.write);
                assertThrows(AccessDeniedException.class,() -> p.invoke("finalizeWrite",p.write));
                assertTrue(p.s.poisoned());assertThrows(AccessDeniedException.class,() -> p.invoke("finalizeWrite",p.write));
                return null;
            }));
        }
        assertEquals("0",finalCount("hidra_org_appointment_receipt",f.employee()));
        assertEquals("0",finalCount("hidra_identity_org_commit_proof_use",f.employee()));
        try(var c=owner()){assertEquals("0",scalar(c,"SELECT count(*) FROM public.hidra_org_appointment_event WHERE employee_id=?",f.employee()));}
    }

    @Test void preparedFinalWriteWithoutGuardStillVetoesPhysicalCommit() throws Exception {
        var f=fixture(false);
        try(var p=new ProvisionalSession(create(f,id()))){
            enableJavaFinalGuard(p,false);
            assertThrows(RuntimeException.class,() -> p.s.tx.execute(status -> {
                p.persist();p.invoke("prepareCommitProof",p.write);p.invoke("prepareFinalWrite",p.write);
                return null; // A prepared argument image is never a successful database guard.
            }));
        }
        assertEquals("0",finalCount("hidra_org_appointment_receipt",f.employee()));
        assertEquals("0",finalCount("hidra_identity_org_commit_proof_use",f.employee()));
        try(var c=owner()){assertEquals("0",scalar(c,"SELECT count(*) FROM public.hidra_org_appointment_event WHERE employee_id=?",f.employee()));}
    }

    @Test void javaFinalGuardRejectsProjectionOrLegacyPeerMutationAfterFinalPreparation() throws Exception {
        for(boolean peer:List.of(false,true)){
            var f=fixture(false);
            try(var p=new ProvisionalSession(create(f,id()))){
                enableJavaFinalGuard(p,false);p.s.tx.execute(status -> {
                    p.persist();p.invoke("prepareCommitProof",p.write);p.invoke("prepareFinalWrite",p.write);
                    if(peer)p.s.jdbc.update("INSERT INTO public.hidra_org_employee_assignment(id,employee_id,organization_unit_id,position_id,assignment_type,valid_from,status,created_at,updated_at) VALUES (?,?,?,?,'PRIMARY',?,'ACTIVE',now(),now())",
                            id(),f.employee(),f.units().get(2),f.position(),java.sql.Timestamp.from(START));
                    else p.s.jdbc.update("UPDATE public.hidra_org_employee_assignment SET appointment_version=2,last_event_id=?::uuid WHERE employee_id=?",id(),f.employee());
                    assertThrows(AccessDeniedException.class,() -> p.invoke("finalizeWrite",p.write));
                    assertTrue(p.s.poisoned());status.setRollbackOnly();return null;
                });
            }
            assertEquals("0",finalCount("hidra_org_appointment_receipt",f.employee()));
            assertEquals("0",finalCount("hidra_identity_org_commit_proof_use",f.employee()));
        }
    }

    @Test void javaFinalPreparationRejectsRevokedAuthorityAndPostGuardAuthenticationSwapVetoesCommit() throws Exception {
        for(boolean revoke:List.of(false,true)){
            var f=fixture(false);
            try(var p=new ProvisionalSession(create(f,id()))){
                enableJavaFinalGuard(p,false);
                assertThrows(RuntimeException.class,() -> p.s.tx.execute(status -> {
                    p.persist();p.invoke("prepareCommitProof",p.write);
                    if(revoke){
                        doThrow(new AccessDeniedException("revoked")).when(p.s.identity).requireCurrent(p.s.nativeTicket);
                        assertThrows(AccessDeniedException.class,() -> p.invoke("prepareFinalWrite",p.write));
                    }else{
                        p.invoke("prepareFinalWrite",p.write);p.invoke("finalizeWrite",p.write);
                        SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken("replacement",null,List.of()));
                    }
                    return null;
                }));
            }
            assertEquals("0",finalCount("hidra_org_appointment_receipt",f.employee()));
            assertEquals("0",finalCount("hidra_identity_org_commit_proof_use",f.employee()));
        }
    }

    @Test void finalWriteStorageRejectsDirectCallsAndPinsTheExactScalarStatement() throws Exception {
        for(boolean guard:List.of(false,true)){
            var f=fixture(false);
            try(var s=new Session(create(f,id()))){s.tx.execute(status -> {
                var admission=qualified(s);
                assertThrows(AccessDeniedException.class,() -> {if(guard)s.storage.finalizeWrite(admission);else s.storage.prepareFinalWrite(admission);});
                assertTrue(s.poisoned());status.setRollbackOnly();return null;
            });}
        }
        var field=JdbcProtectedEmployeeAppointmentRepositoryAdapter.class.getDeclaredField("WRITE_GUARD");field.setAccessible(true);
        assertEquals(FINAL_SQL,field.get(null));
    }

    @Test void storageDerivesCommitIntentFromExactPersistedUtf8EventForEveryFreshCommand() throws Exception {
        for(int kind=0;kind<5;kind++){
            var f=fixture(kind!=0);
            EmployeeAppointmentCommand command=switch(kind){
                case 0 -> create(f,id());
                case 1 -> end(f,1);
                case 2 -> correction(f,id(),1,f.units().get(0));
                case 3 -> correction(f,id(),1,f.units().get(1));
                default -> new EmployeeAppointmentCommand.Transfer(id(),f.company(),f.assignment(),1,f.units().get(1),f.position(),
                        AssignmentType.PRIMARY,START.plusSeconds(3600),null,1,1,REASON);
            };
            try(var p=new ProvisionalSession(command)){
                var captured=new java.util.concurrent.atomic.AtomicReference<CommitIntent>();
                when(p.s.identity.issueCommitProof(eq(p.s.nativeTicket),any())).thenAnswer(call -> {
                    var intent=call.getArgument(1,CommitIntent.class);captured.set(intent);
                    var expected=p.s.jdbc.queryForObject("SELECT event_id::text,command_sha256,encode(sha256(canonical_payload),'hex'),valid_until FROM public.hidra_org_appointment_event WHERE employee_id=? AND actor_user_id=?",
                            (r,i) -> new CommitIntent(UUID.fromString(r.getString(1)),r.getString(2),r.getString(3),r.getTimestamp(4).toInstant()),f.employee(),p.s.actor);
                    assertEquals(expected,intent);return syntheticProof();
                });
                p.s.tx.execute(status -> {
                    p.persist();assertNull(p.invoke("prepareCommitProof",p.write));assertNotNull(captured.get());
                    verify(p.s.identity,times(1)).issueCommitProof(eq(p.s.nativeTicket),any());
                    verify(p.s.identity,times(1)).contribute(p.s.nativeTicket);
                    var order=inOrder(p.executor,p.s.identity);
                    order.verify(p.executor).endProvisional(any());
                    order.verify(p.s.identity).issueCommitProof(eq(p.s.nativeTicket),eq(captured.get()));
                    assertEquals(0,p.s.jdbc.queryForObject("SELECT count(*) FROM public.hidra_org_appointment_receipt WHERE event_id=?::uuid",Integer.class,captured.get().eventId().toString()));
                    status.setRollbackOnly();return null;
                });
            }
            try(var c=owner()){
                assertEquals(kind==0?"0":"1",scalar(c,"SELECT count(*) FROM public.hidra_org_employee_assignment WHERE employee_id=?",f.employee()));
                assertEquals(kind==0?"0":"1",scalar(c,"SELECT count(*) FROM public.hidra_org_appointment_event WHERE employee_id=?",f.employee()));
            }
        }
    }

    @Test void boundedProofTransportAloneNeverAuthorizesReceiptOrPhysicalCommit() throws Exception {
        var f=fixture(false);
        try(var p=new ProvisionalSession(create(f,id()))){
            when(p.s.identity.issueCommitProof(eq(p.s.nativeTicket),any())).thenReturn(syntheticProof());
            assertThrows(RuntimeException.class,() -> p.s.tx.execute(status -> {
                p.persist();p.invoke("prepareCommitProof",p.write);return null;
            }));
        }
        try(var c=owner()){
            assertEquals("0",scalar(c,"SELECT count(*) FROM public.hidra_org_employee_assignment WHERE employee_id=?",f.employee()));
            assertEquals("0",scalar(c,"SELECT count(*) FROM public.hidra_org_appointment_event WHERE employee_id=?",f.employee()));
            assertEquals("0",scalar(c,"SELECT count(*) FROM public.hidra_org_appointment_lock_key WHERE kind='EMPLOYEE' AND key_one=?",f.employee()));
        }
    }

    @Test void missingOrFailedNativeProofPoisonsOriginalTransactionWithoutAnotherIssuerAttempt() throws Exception {
        for(boolean throwsFailure:List.of(false,true)){
            var f=fixture(false);
            try(var p=new ProvisionalSession(create(f,id()))){
                if(throwsFailure)when(p.s.identity.issueCommitProof(any(),any())).thenThrow(new IllegalStateException("native proof unavailable"));
                assertThrows(RuntimeException.class,() -> p.s.tx.execute(status -> {
                    p.persist();
                    assertThrows(AccessDeniedException.class,() -> p.invoke("prepareCommitProof",p.write));
                    assertTrue(p.s.poisoned());
                    assertThrows(AccessDeniedException.class,() -> p.invoke("prepareCommitProof",p.write));
                    verify(p.s.identity,times(1)).issueCommitProof(eq(p.s.nativeTicket),any());
                    return null; // Deliberately swallow; beforeCommit must still veto.
                }));
            }
            try(var c=owner()){
                assertEquals("0",scalar(c,"SELECT count(*) FROM public.hidra_org_appointment_event WHERE employee_id=?",f.employee()));
            }
        }
    }

    @Test void nativeProofCallbackCannotSubstitutePostWriteStateAuthenticationOrAuthority() throws Exception {
        for(int variant=0;variant<3;variant++){
            var f=fixture(false);final int changed=variant;
            try(var p=new ProvisionalSession(create(f,id()))){
                when(p.s.identity.issueCommitProof(eq(p.s.nativeTicket),any())).thenAnswer(call -> {
                    if(changed==0)p.s.jdbc.update("UPDATE public.hidra_org_employee_assignment SET appointment_version=appointment_version+1,last_event_id=?::uuid WHERE employee_id=?",id(),f.employee());
                    if(changed==1)SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken("replacement",null,List.of()));
                    if(changed==2)doThrow(new AccessDeniedException("revoked")).when(p.s.identity).requireCurrent(p.s.nativeTicket);
                    return syntheticProof();
                });
                p.s.tx.execute(status -> {
                    p.persist();
                    assertThrows(AccessDeniedException.class,() -> p.invoke("prepareCommitProof",p.write));
                    assertTrue(p.s.poisoned());
                    verify(p.s.identity,times(1)).issueCommitProof(eq(p.s.nativeTicket),any());
                    status.setRollbackOnly();return null;
                });
            }
            try(var c=owner()){
                assertEquals("0",scalar(c,"SELECT count(*) FROM public.hidra_org_employee_assignment WHERE employee_id=?",f.employee()));
            }
        }
    }

    @Test void proofPreparationRejectsDirectEarlyAndRepeatedStorageUse() throws Exception {
        var f=fixture(false);
        try(var s=new Session(create(f,id()))){s.tx.execute(status -> {
            var ticket=qualified(s);
            assertThrows(AccessDeniedException.class,() -> s.storage.prepareCommitProof(ticket));
            verify(s.identity,never()).issueCommitProof(any(),any());assertTrue(s.poisoned());
            status.setRollbackOnly();return null;
        });}
        try(var p=new ProvisionalSession(create(f,id()))){p.s.tx.execute(status -> {
            p.write=p.invoke("acquireWrite",p.prepared,p.s.command);
            assertThrows(AccessDeniedException.class,() -> p.invoke("prepareCommitProof",p.write));
            verify(p.s.identity,never()).issueCommitProof(any(),any());
            status.setRollbackOnly();return null;
        });}
        try(var p=new ProvisionalSession(create(f,id()))){
            when(p.s.identity.issueCommitProof(eq(p.s.nativeTicket),any())).thenReturn(syntheticProof());
            p.s.tx.execute(status -> {
                p.persist();p.invoke("prepareCommitProof",p.write);
                assertThrows(AccessDeniedException.class,() -> p.invoke("prepareCommitProof",p.write));
                verify(p.s.identity,times(1)).issueCommitProof(eq(p.s.nativeTicket),any());assertTrue(p.s.poisoned());
                status.setRollbackOnly();return null;
            });
        }
    }

    private final Map<String,UUID> finalEvents=new java.util.HashMap<>();
    private static final String FINAL_GUARD="public.hidra_org_appointment_final_guard(uuid,uuid,uuid,text,text,text,text,timestamptz,timestamptz,integer,bigint,bytea,bytea,timestamptz)";
    private static final String FINAL_SQL="WITH observed AS MATERIALIZED (SELECT pg_catalog.clock_timestamp() AS t) SELECT public.hidra_org_appointment_final_guard(?::uuid,?::uuid,?::uuid,?,?,?,?,?::timestamptz,?::timestamptz,?,?,?::bytea,?::bytea,t) FROM observed";
    private static final UUID SQL_AUDIENCE=UUID.fromString("11111111-1111-1111-1111-111111111111");
    private static byte[] sqlKey(){var key=new byte[32];for(int i=0;i<key.length;i++)key[i]=(byte)(i+1);return key;}
    private static void proofText(java.io.DataOutputStream out,String value) throws java.io.IOException {
        var bytes=value.getBytes(java.nio.charset.StandardCharsets.UTF_8);out.writeInt(bytes.length);out.write(bytes);
    }
    private static void proofUuid(java.io.DataOutputStream out,UUID value) throws java.io.IOException {
        out.writeLong(value.getMostSignificantBits());out.writeLong(value.getLeastSignificantBits());
    }
    private static long micros(Instant value){return Math.addExact(Math.multiplyExact(value.getEpochSecond(),1000000),value.getNano()/1000);}
    private record SqlProof(byte[] frame,byte[] mac) { }

    /** Independent raw JDBC/HMAC test harness. It never removes or changes production callbacks.
     * Manually encoded native fields are synthetic; genuine native Java/executor acceptance remains separate. */
    private final class FinalSqlSession implements AutoCloseable {
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
    private String finalCount(String table,String employee) throws Exception {
        try(var c=owner()){
            if(table.equals("hidra_identity_org_commit_proof_use") || table.equals("hidra_org_appointment_receipt"))
                return scalar(c,"SELECT count(*) FROM public."+table+" WHERE event_id=?::uuid",finalEvents.get(employee).toString());
            return scalar(c,"SELECT count(*) FROM public."+table+" WHERE employee_id=?",employee);
        }
    }

    @Test void realFinalSqlGuardAuthenticatesAndCommitsReceiptAndNonceAtomically() throws Exception {
        var f=fixture(false);
        try(var g=new FinalSqlSession(f,false,false)){
            g.guard(g.proof());
            assertEquals("0",finalCount("hidra_org_appointment_receipt",f.employee()));
            assertEquals("0",finalCount("hidra_identity_org_commit_proof_use",f.employee()));
            g.c.commit();
        }
        assertEquals("1",finalCount("hidra_org_appointment_receipt",f.employee()));
        assertEquals("1",finalCount("hidra_identity_org_commit_proof_use",f.employee()));
        assertEquals("1",finalCount("hidra_org_employee_assignment",f.employee()));
    }

    @Test void realFinalSqlGuardRollbackRemovesProjectionKeysEventReceiptAndNonce() throws Exception {
        var f=fixture(false);
        try(var g=new FinalSqlSession(f,false,false)){g.guard(g.proof());g.c.rollback();}
        for(String table:List.of("hidra_org_employee_assignment","hidra_org_appointment_event","hidra_org_appointment_receipt","hidra_identity_org_commit_proof_use"))assertEquals("0",finalCount(table,f.employee()));
        try(var c=owner()){assertEquals("0",scalar(c,"SELECT count(*) FROM public.hidra_org_appointment_lock_key WHERE kind='EMPLOYEE' AND key_one=?",f.employee()));}
    }

    @Test void realFinalSqlGuardRejectsForgedMacAndValidMacForForeignScopeOrPayload() throws Exception {
        for(int variant=0;variant<3;variant++){
            var f=fixture(false);
            try(var g=new FinalSqlSession(f,false,false)){
                var proof=variant==1?g.proof(f.units().get(1),g.payloadDigest):variant==2?g.proof(f.units().get(0),"00".repeat(32)):g.proof();
                if(variant==0)proof.mac()[0]^=1;
                assertThrows(SQLException.class,() -> g.guard(proof));g.c.rollback();
            }
            assertEquals("0",finalCount("hidra_org_appointment_receipt",f.employee()));
        }
    }

    @Test void realFinalSqlGuardRejectsChangedRequestAndExpiredNativeWindow() throws Exception {
        for(boolean expired:List.of(false,true)){
            var f=fixture(false);
            try(var g=new FinalSqlSession(f,expired,false)){
                var proof=g.proof();assertThrows(SQLException.class,() -> g.guard(proof,expired?g.request:"other-request"));g.c.rollback();
            }
            assertEquals("0",finalCount("hidra_org_employee_assignment",f.employee()));
        }
    }

    @Test void realFinalSqlGuardDeferredTypedFailureRollsBackProofUseAndReceipt() throws Exception {
        var f=fixture(false);
        try(var g=new FinalSqlSession(f,false,true)){
            var proof=g.proof();assertThrows(SQLException.class,() -> g.guard(proof));g.c.rollback();
        }
        assertEquals("0",finalCount("hidra_org_appointment_receipt",f.employee()));
        assertEquals("0",finalCount("hidra_identity_org_commit_proof_use",f.employee()));
    }

    @Test void realFinalSqlGuardCannotRedeemEventTwiceEvenInSameTransaction() throws Exception {
        var f=fixture(false);
        try(var g=new FinalSqlSession(f,false,false)){
            var proof=g.proof();g.guard(proof);var save=g.c.setSavepoint();
            assertThrows(SQLException.class,() -> g.guard(proof));g.c.rollback(save);g.c.releaseSavepoint(save);g.c.commit();
        }
        assertEquals("1",finalCount("hidra_org_appointment_receipt",f.employee()));
        assertEquals("1",finalCount("hidra_identity_org_commit_proof_use",f.employee()));
    }

    @Test void realFinalSqlGuardRejectsSameTransactionProjectionAndLegacyPeerSubstitution() throws Exception {
        for(boolean peer:List.of(false,true)){
            var f=fixture(false);
            try(var g=new FinalSqlSession(f,false,false)){
                var proof=g.proof();
                if(peer)execute(g.c,"INSERT INTO public.hidra_org_employee_assignment(id,employee_id,organization_unit_id,position_id,assignment_type,valid_from,status,created_at,updated_at) VALUES (?,?,?,?,'PRIMARY',?::timestamptz,'ACTIVE',now(),now())",id(),f.employee(),f.units().get(2),f.position(),START.toString());
                else execute(g.c,"UPDATE public.hidra_org_employee_assignment SET appointment_version=2,last_event_id=?::uuid WHERE id=?",id(),g.assignment);
                assertThrows(SQLException.class,() -> g.guard(proof));g.c.rollback();
            }
            assertEquals("0",finalCount("hidra_org_employee_assignment",f.employee()));
        }
    }

    @Test void finalSqlFunctionsHaveClosedAclAndNoOrdinaryReceiptOrPrivateVerifierAccess() throws Exception {
        var f=fixture(false);
        try(var s=new Session(create(f,id()));var c=s.source.getConnection()){
            for(String signature:List.of(FINAL_GUARD,"public.hidra_org_appointment_final_context()","public.hidra_org_appointment_final_fingerprint(regclass)","public.hidra_identity_org_commit_verify_consume(bytea,bytea,jsonb)"))
                assertEquals("f",scalar(c,"SELECT has_function_privilege(current_user,?,'EXECUTE')",signature));
            assertEquals("f",scalar(c,"SELECT has_table_privilege(current_user,'public.hidra_org_appointment_receipt','INSERT')"));
        }
        try(var c=owner()){
            assertEquals("3",scalar(c,"SELECT count(*) FROM pg_proc p JOIN pg_namespace n ON n.oid=p.pronamespace WHERE n.nspname='public' AND p.proname IN ('hidra_org_appointment_final_guard','hidra_org_appointment_final_context','hidra_org_appointment_final_fingerprint') AND NOT EXISTS(SELECT 1 FROM aclexplode(coalesce(p.proacl,acldefault('f',p.proowner))) a WHERE a.grantee=0 AND a.privilege_type='EXECUTE')"));
        }
        assertEquals("4691e7803cd1b3619472ddc29872a9f893adb5decbadbfe05e94779196e4a01b",java.util.HexFormat.of().formatHex(java.security.MessageDigest.getInstance("SHA-256").digest(FINAL_SQL.getBytes(java.nio.charset.StandardCharsets.UTF_8))));
    }

    @Test void realFinalSqlGuardRejectsReceiptWritePrivilegeAndMissingOwnerFence() throws Exception {
        for(int attack:List.of(1,2)){
            var f=fixture(false);
            try(var g=new FinalSqlSession(f,false,false,attack,START)){
                var proof=g.proof();assertThrows(SQLException.class,() -> g.guard(proof));g.c.rollback();
            }
            assertEquals("0",finalCount("hidra_org_appointment_receipt",f.employee()));
            assertEquals("0",finalCount("hidra_identity_org_commit_proof_use",f.employee()));
        }
    }

    @Test void realFinalSqlGuardIndependentlyRejectsEmployeeOverlapAndPositionCapacityConflict() throws Exception {
        for(boolean otherEmployee:List.of(false,true)){
            var seeded=fixture();var target=seeded;
            if(otherEmployee){
                String employee=id();
                admin("INSERT INTO public.hidra_org_employee(id,employee_number,employee_type,status,created_at,updated_at) VALUES (?,?,'PERMANENT','ACTIVE',now(),now())",employee,employee);
                target=new Fixture(seeded.company(),employee,id(),seeded.position(),seeded.units(),seeded.policy(),id());
            }
            try(var g=new FinalSqlSession(target,false,false)){
                var proof=g.proof();assertThrows(SQLException.class,() -> g.guard(proof));g.c.rollback();
            }
            assertEquals("0",finalCount("hidra_org_appointment_receipt",target.employee()));
            assertEquals("0",finalCount("hidra_identity_org_commit_proof_use",target.employee()));
        }
    }

    @Test void realFinalSqlGuardAllowsExactHalfOpenAdjacencyWithoutFalsePrimaryPeak() throws Exception {
        var f=fixture(false);var boundary=START.plusSeconds(3600);seedCreate(f,boundary);
        try(var g=new FinalSqlSession(f,false,false,0,boundary)){g.guard(g.proof());g.c.commit();}
        assertEquals("1",finalCount("hidra_org_appointment_receipt",f.employee()));
        assertEquals("1",finalCount("hidra_identity_org_commit_proof_use",f.employee()));
        assertEquals("2",finalCount("hidra_org_employee_assignment",f.employee()));
    }

    /** Only static identifiers enter assertion diagnostics; never print SQL payloads or proof bytes. */
    private static String markFixtureRejections(String source,String family) {
        String token="RAISE EXCEPTION 'Appointment finalization unavailable'";
        var out=new StringBuilder();int offset=0,index=0,found;
        while((found=source.indexOf(token,offset))>=0){
            out.append(source,offset,found).append("RAISE EXCEPTION 'Fixture invariant ")
                    .append(family).append(String.format(java.util.Locale.ROOT,"%02d",++index)).append("'");
            offset=found+token.length();
        }
        assertTrue(index>0);return out.append(source.substring(offset)).toString();
    }
    private static String safeFixtureMarker(SQLException failure) {
        var matcher=java.util.regex.Pattern.compile("Fixture invariant [CG][0-9]{2}").matcher(failure.getMessage());
        return matcher.find()?matcher.group():"no fixture marker; SQLSTATE="+failure.getSQLState();
    }

    @Test void archivedFinalGuardReproducesAmbiguousSweepColumnWhileForwardGuardCommits() throws Exception {
        // Test-only diagnostic clone of immutable001, with no redacting handler: expose only SQLSTATE.
        // The installed guard keeps generic diagnostics; no production function/ACL is weakened.
        String archived;
        try(var in=getClass().getResourceAsStream("/db/migration/V20261023_001__organization_appointment_final_write_guard.sql")){
            assertNotNull(in);archived=new String(in.readAllBytes(),java.nio.charset.StandardCharsets.UTF_8);
        }
        String currentContext;
        try(var in=getClass().getResourceAsStream("/db/migration/V20261023_003__organization_appointment_fingerprint_visibility.sql")){
            assertNotNull(in);currentContext=new String(in.readAllBytes(),java.nio.charset.StandardCharsets.UTF_8);
        }
        int contextStart=currentContext.indexOf("CREATE OR REPLACE FUNCTION public.hidra_org_appointment_final_context()");
        int contextEnd=currentContext.indexOf("$context$;",contextStart)+"$context$;".length();
        String context=markFixtureRejections(currentContext.substring(contextStart,contextEnd),"C")
                .replace("CREATE OR REPLACE FUNCTION public.hidra_org_appointment_final_context()","CREATE FUNCTION public.fixture_final_context()");
        context=context.replace("PERFORM public.hidra_identity_org_commit_caller();",
                "BEGIN PERFORM public.hidra_identity_org_commit_caller(); EXCEPTION WHEN OTHERS THEN RAISE EXCEPTION 'Fixture invariant C08'; END;")
                .replace("PERFORM public.hidra_identity_org_commit_locks();",
                "BEGIN PERFORM public.hidra_identity_org_commit_locks(); EXCEPTION WHEN OTHERS THEN RAISE EXCEPTION 'Fixture invariant C09'; END;");
        admin(context);
        admin("REVOKE ALL ON FUNCTION public.fixture_final_context() FROM PUBLIC");
        int start=archived.indexOf("CREATE FUNCTION public.hidra_org_appointment_final_guard(");
        int end=archived.indexOf("$guard$;",start)+"$guard$;".length();
        String diagnostic=archived.substring(start,end)
                .replace("CREATE FUNCTION public.hidra_org_appointment_final_guard(","CREATE FUNCTION public.fixture_ambiguous_final_guard(")
                .replace("EXCEPTION WHEN OTHERS THEN\n RAISE EXCEPTION USING ERRCODE='P0001',MESSAGE='Appointment finalization unavailable';\n","");
        assertFalse(diagnostic.contains("EXCEPTION WHEN OTHERS"));
        diagnostic=markFixtureRejections(diagnostic,"G").replace(
                "PERFORM public.hidra_org_appointment_final_context();","PERFORM public.fixture_final_context();");
        admin(diagnostic);
        admin("REVOKE ALL ON FUNCTION public.fixture_ambiguous_final_guard(uuid,uuid,uuid,text,text,text,text,timestamptz,timestamptz,integer,bigint,bytea,bytea,timestamptz) FROM PUBLIC");
        var f=fixture(false);
        try(var g=new FinalSqlSession(f,false,false)){
            admin("GRANT EXECUTE ON FUNCTION public.fixture_ambiguous_final_guard(uuid,uuid,uuid,text,text,text,text,timestamptz,timestamptz,integer,bigint,bytea,bytea,timestamptz) TO "+g.login.role);
            var proof=g.proof();var save=g.c.setSavepoint();
            var failure=assertThrows(SQLException.class,() -> g.guardSql(proof,g.request,
                    FINAL_SQL.replace("public.hidra_org_appointment_final_guard(","public.fixture_ambiguous_final_guard(")));
            assertEquals("42702",failure.getSQLState(),safeFixtureMarker(failure)); // No protected values in assertion diagnostics.
            g.c.rollback(save);g.c.releaseSavepoint(save);
            g.guard(proof);g.c.commit();
        }
        assertEquals("1",finalCount("hidra_org_appointment_receipt",f.employee()));
        assertEquals("1",finalCount("hidra_identity_org_commit_proof_use",f.employee()));
    }

    @Test void finalFingerprintPreservesAcceptedPinsWithCanonicalPublicVisibilityAndClosedAcl() throws Exception {
        String archived;
        try(var in=getClass().getResourceAsStream("/db/migration/V20261023_001__organization_appointment_final_write_guard.sql")){
            assertNotNull(in);archived=new String(in.readAllBytes(),java.nio.charset.StandardCharsets.UTF_8);
        }
        int start=archived.indexOf("CREATE FUNCTION public.hidra_org_appointment_final_fingerprint(");
        int end=archived.indexOf("$fingerprint$;",start)+"$fingerprint$;".length();
        admin(archived.substring(start,end).replace("CREATE FUNCTION public.hidra_org_appointment_final_fingerprint(","CREATE FUNCTION public.fixture_old_fingerprint("));
        admin("REVOKE ALL ON FUNCTION public.fixture_old_fingerprint(regclass) FROM PUBLIC");
        String relation="public.hidra_org_employee_assignment";
        String accepted="32d8113ab0f7d677837cd66c471351efd25ca3b0c05f76aacaeda9aab4b8c406";
        try(var c=owner()){
            assertNotEquals(accepted,scalar(c,"SELECT public.fixture_old_fingerprint(?::regclass)",relation));
            assertEquals(accepted,scalar(c,"SELECT public.hidra_org_appointment_final_fingerprint(?::regclass)",relation));
            assertEquals("search_path=pg_catalog, public",scalar(c,"SELECT proconfig[1] FROM pg_proc WHERE oid='public.hidra_org_appointment_final_fingerprint(regclass)'::regprocedure"));
        }
        var f=fixture(false);
        try(var login=new Session(create(f,id()));var c=login.source.getConnection()){
            assertEquals("f",scalar(c,"SELECT has_schema_privilege(current_user,'public','CREATE')"));
            assertEquals("f",scalar(c,"SELECT has_function_privilege(current_user,'public.hidra_org_appointment_final_fingerprint(regclass)','EXECUTE')"));
        }
    }

    private EmployeeAppointmentCommand.Create create(Fixture f,String request){
        return new EmployeeAppointmentCommand.Create(request,f.company(),f.employee(),f.units().get(0),f.position(),AssignmentType.PRIMARY,START,null,1,1);
    }
    private EmployeeAppointmentCommand.End end(Fixture f,long version){
        return new EmployeeAppointmentCommand.End(id(),f.company(),f.assignment(),version,START.plusSeconds(3600),REASON);
    }
    private void classify(EmployeeAppointmentCommand command,ReferenceUse expected) throws Exception {
        try(var s=new Session(command)){s.tx.execute(status -> {
            var ticket=s.acquire();var sample=s.storage.contribute(ticket);
            assertEquals(expected,s.storage.referenceUse(ticket,command,sample));
            assertEquals(expected,s.storage.referenceUse(ticket,command,sample));
            verify(s.identity,times(1)).contribute(s.nativeTicket);status.setRollbackOnly();return null;
        });}
    }

    @Test void freshCommandsClassifyCurrentOrRetainedWithoutCreatingLedgerRows() throws Exception {
        classify(create(baseline,id()),ReferenceUse.CURRENT);classify(end(baseline,1),ReferenceUse.RETAINED);
        classify(correction(baseline,id(),1,baseline.units().get(0)),ReferenceUse.RETAINED);
        classify(correction(baseline,id(),1,baseline.units().get(1)),ReferenceUse.CURRENT);
        classify(new EmployeeAppointmentCommand.Transfer(id(),baseline.company(),baseline.assignment(),1,
                baseline.units().get(1),baseline.position(),AssignmentType.ACTING,START.plusSeconds(3600),null,1,1,REASON),ReferenceUse.CURRENT);
        try(var c=owner()){assertEquals("4",scalar(c,"SELECT count(*) FROM public.hidra_org_appointment_event WHERE assignment_id IN (?,?)",baseline.assignment(),historical.assignment()));}
    }

    @Test void committedCreateReceiptReplaysOnlyForItsOriginalActorAndExactCommand() throws Exception {
        var command=create(baseline,baseline.createRequest());classify(command,ReferenceUse.REPLAY);
        try(var s=new Session(command)){s.actor="another-actor";s.tx.execute(status -> {
            var ticket=s.acquire();var sample=s.storage.contribute(ticket);
            assertEquals(ReferenceUse.CURRENT,s.storage.referenceUse(ticket,command,sample));status.setRollbackOnly();return null;
        });}
    }

    @Test void originalCorrectionReplaysFromHistoricalPinsAfterLaterMoveRebindingAndPartySuspension() throws Exception {
        try(var s=new Session(originalCorrection)){s.tx.execute(status -> {
            var preview=s.storage.preview(originalCorrection);
            assertEquals(2,preview.companyUnits().size());
            assertTrue(preview.companyUnits().contains(new CompanyUnit(historical.company(),historical.units().get(0))));
            assertTrue(preview.companyUnits().contains(new CompanyUnit(historical.company(),historical.units().get(1))));
            assertFalse(preview.companyUnits().contains(new CompanyUnit(historical.company(),historical.units().get(2))));
            assertEquals(3L,s.jdbc.queryForObject("SELECT appointment_version FROM public.hidra_org_employee_assignment WHERE id=?",Long.class,historical.assignment()));
            assertNotEquals(historical.company(),s.jdbc.queryForObject("SELECT company_party_id FROM public.hidra_org_appointment_binding_head WHERE organization_unit_id=?",String.class,historical.units().get(0)));
            var ticket=s.acquire();var sample=s.storage.contribute(ticket);
            assertEquals(ReferenceUse.REPLAY,s.storage.referenceUse(ticket,originalCorrection,sample));
            status.setRollbackOnly();return null;
        });}
    }

    @Test void receiptKeyRejectsChangedReasonVersionNullableBoundaryAndPolicyRevision() throws Exception {
        var c=originalCorrection;
        for(var changed:List.of(
                new EmployeeAppointmentCommand.Correct(c.requestId(),c.companyPartyId(),c.assignmentId(),1,c.correctedOrganizationUnitId(),c.correctedPositionId(),c.correctedType(),c.correctedStatus(),START,null,1,1,"Changed reason"),
                new EmployeeAppointmentCommand.Correct(c.requestId(),c.companyPartyId(),c.assignmentId(),3,c.correctedOrganizationUnitId(),c.correctedPositionId(),c.correctedType(),c.correctedStatus(),START,null,1,1,REASON),
                new EmployeeAppointmentCommand.Correct(c.requestId(),c.companyPartyId(),c.assignmentId(),1,c.correctedOrganizationUnitId(),c.correctedPositionId(),c.correctedType(),c.correctedStatus(),START,START.plusSeconds(3600),1,1,REASON),
                new EmployeeAppointmentCommand.Correct(c.requestId(),c.companyPartyId(),c.assignmentId(),1,c.correctedOrganizationUnitId(),c.correctedPositionId(),c.correctedType(),c.correctedStatus(),START,null,1,2,REASON))) {
            try(var s=new Session(changed)){s.tx.execute(status -> {
                var ticket=s.acquire();var sample=s.storage.contribute(ticket);
                assertEquals("Protected appointment admission unavailable",assertThrows(AccessDeniedException.class,
                        () -> s.storage.referenceUse(ticket,changed,sample)).getMessage());
                assertTrue(s.poisoned());status.setRollbackOnly();return null;
            });}
        }
    }

    @Test void freshMutationRejectsStaleCasAndRetainedBindingRevisionSubstitution() throws Exception {
        for(var command:List.of(end(baseline,2),new EmployeeAppointmentCommand.Correct(id(),baseline.company(),baseline.assignment(),1,
                baseline.units().get(0),baseline.position(),AssignmentType.PRIMARY,AssignmentStatus.ACTIVE,START,null,2,1,REASON))) {
            try(var s=new Session(command)){s.tx.execute(status -> {
                var ticket=s.acquire();var sample=s.storage.contribute(ticket);
                assertThrows(AccessDeniedException.class,() -> s.storage.referenceUse(ticket,command,sample));
                assertTrue(s.poisoned());status.setRollbackOnly();return null;
            });}
        }
    }

    @Test void scalarContributionCopyCannotQualifyClassificationOrRestoreAPoisonedTicket() throws Exception {
        var command=end(baseline,1);
        try(var s=new Session(command)){s.tx.execute(status -> {
            var ticket=s.acquire();var sample=s.storage.contribute(ticket);
            var copy=new Contribution(sample.request(),sample.decisions(),sample.bindingId(),sample.sessionId(),sample.userId(),
                    sample.sampledAt(),sample.validUntil(),sample.backendPid(),sample.transactionId());
            assertEquals(sample,copy);assertNotSame(sample,copy);
            assertThrows(AccessDeniedException.class,() -> s.storage.referenceUse(ticket,command,copy));
            assertTrue(s.poisoned());assertThrows(AccessDeniedException.class,() -> s.storage.referenceUse(ticket,command,sample));
            status.setRollbackOnly();return null;
        });}
    }

    @Test void replayRefusesAnIncompleteHistoricalOwnershipClosure() throws Exception {
        try(var s=new Session(originalCorrection)){
            s.previewOverride=new ProtectedEmployeeAppointmentRepositoryPort.DescriptorPreview(
                    List.of(new CompanyUnit(historical.company(),historical.units().get(1))),historical.employee(),historical.assignment(),false);
            s.tx.execute(status -> {
                var ticket=s.acquire();var sample=s.storage.contribute(ticket);
                assertThrows(AccessDeniedException.class,() -> s.storage.referenceUse(ticket,originalCorrection,sample));
                assertTrue(s.poisoned());status.setRollbackOnly();return null;
            });
        }
    }

    @Test void classificationIsFrozenToTheExactCommandAndRechecksNativeRevocation() throws Exception {
        var command=end(baseline,1);
        try(var s=new Session(command)){s.tx.execute(status -> {
            var ticket=s.acquire();var sample=s.storage.contribute(ticket);
            assertEquals(ReferenceUse.RETAINED,s.storage.referenceUse(ticket,command,sample));
            assertThrows(AccessDeniedException.class,() -> s.storage.referenceUse(ticket,end(baseline,1),sample));
            assertTrue(s.poisoned());status.setRollbackOnly();return null;
        });}
        try(var s=new Session(originalCorrection)){s.tx.execute(status -> {
            var ticket=s.acquire();var sample=s.storage.contribute(ticket);
            assertEquals(ReferenceUse.REPLAY,s.storage.referenceUse(ticket,originalCorrection,sample));
            doThrow(new IllegalStateException("issuer revoked")).when(s.identity).requireCurrent(s.nativeTicket);
            assertThrows(AccessDeniedException.class,() -> s.storage.referenceUse(ticket,originalCorrection,sample));
            assertTrue(s.poisoned());status.setRollbackOnly();return null;
        });}
    }

    private void prepare(EmployeeAppointmentCommand command,boolean allowed) throws Exception {
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
    private void activatePolicy(Fixture f,String value) throws Exception {
        try(var c=DriverManager.getConnection(POSTGRES.getJdbcUrl(),CONFIG,PASSWORD)){
            scalar(c,"SELECT public.hidra_org_activate_appointment_policy(?,?,1,?::jsonb)",f.policy(),f.company(),value);
        }
    }

    @Test void everyFreshKindBuildsStablePrivatePlansWithoutMutatingTheDatabase() throws Exception {
        var empty=fixture(false);prepare(create(empty,id()),true);
        var f=fixture();prepare(end(f,1),true);
        prepare(correction(f,id(),1,f.units().get(0)),true);prepare(correction(f,id(),1,f.units().get(1)),true);
        prepare(new EmployeeAppointmentCommand.Transfer(id(),f.company(),f.assignment(),1,f.units().get(1),f.position(),
                AssignmentType.ACTING,START.plusSeconds(3600),null,1,1,REASON),true);
        try(var c=owner()){assertEquals("1",scalar(c,"SELECT appointment_version FROM public.hidra_org_employee_assignment WHERE id=?",f.assignment()));}
    }

    @Test void planningRequiresExactCurrentPolicyAndDestinationBindingRevisions() throws Exception {
        var f=fixture(false);activatePolicy(f,policy());prepare(create(f,id()),false);
        try(var c=DriverManager.getConnection(POSTGRES.getJdbcUrl(),CONFIG,PASSWORD)){
            scalar(c,"SELECT public.hidra_org_activate_appointment_binding(?,?,?,1)",f.units().get(0),f.units().get(0),f.company());
        }
        prepare(new EmployeeAppointmentCommand.Create(id(),f.company(),f.employee(),f.units().get(0),f.position(),AssignmentType.PRIMARY,START,null,1,2),false);
        prepare(new EmployeeAppointmentCommand.Create(id(),f.company(),f.employee(),f.units().get(0),f.position(),AssignmentType.PRIMARY,START,null,2,2),true);
    }

    @Test void retainedPlanningKeepsTheOriginalCompanyPinAfterRebindAndPartySuspension() throws Exception {
        var f=fixture();var other=fixture(false);
        try(var c=DriverManager.getConnection(POSTGRES.getJdbcUrl(),CONFIG,PASSWORD)){
            scalar(c,"SELECT public.hidra_org_activate_appointment_binding(?,?,?,1)",f.units().get(0),f.units().get(0),other.company());
        }
        admin("UPDATE public.hidra_party_party SET status='SUSPENDED' WHERE id=?",f.company());
        prepare(end(f,1),true);prepare(correction(f,id(),1,f.units().get(0)),true);
    }

    @Test void completeEmployeeConflictsIncludeProtectedPeersFromAnotherCompany() throws Exception {
        var selected=fixture(false);var other=fixture(false);
        seedCreate(new Fixture(other.company(),selected.employee(),id(),other.position(),other.units(),other.policy(),id()),null);
        prepare(create(selected,id()),false);
    }

    @Test void explicitPositionCapacityControlsOtherEmployeesWithoutInferringADefault() throws Exception {
        var f=fixture();var other=fixture(false);
        var occupant=new Fixture(f.company(),other.employee(),id(),f.position(),f.units(),f.policy(),id());
        prepare(create(occupant,id()),false);
        activatePolicy(f,policy().replace("SINGLE_HOLDER","UNRESTRICTED"));
        prepare(new EmployeeAppointmentCommand.Create(id(),f.company(),other.employee(),f.units().get(0),f.position(),
                AssignmentType.PRIMARY,START,null,1,2),true);
    }

    @Test void completeTypeMatrixAndPrimaryLimitAreIndependentlyEnforced() throws Exception {
        var f=fixture();
        prepare(new EmployeeAppointmentCommand.Create(id(),f.company(),f.employee(),f.units().get(0),f.position(),
                AssignmentType.SECONDARY,START,null,1,1),false);
        activatePolicy(f,policy().replace("false","true").replace("SINGLE_HOLDER","UNRESTRICTED"));
        prepare(new EmployeeAppointmentCommand.Create(id(),f.company(),f.employee(),f.units().get(0),f.position(),
                AssignmentType.SECONDARY,START,null,1,2),true);
        prepare(new EmployeeAppointmentCommand.Create(id(),f.company(),f.employee(),f.units().get(0),f.position(),
                AssignmentType.PRIMARY,START,null,1,2),false);
    }

    @Test void unboundLegacyConflictCannotDisappearThroughAnInnerJoinOrInventedPin() throws Exception {
        var f=fixture(false);
        admin("INSERT INTO public.hidra_org_employee_assignment(id,employee_id,organization_unit_id,position_id,assignment_type,valid_from,status,created_at,updated_at) VALUES (?,?,?,?,'SECONDARY',?::timestamptz,'ACTIVE',now(),now())",id(),f.employee(),f.units().get(0),f.position(),START.toString());
        prepare(create(f,id()),false);
    }

    @Test void activeHalfOpenIntervalsPermitAnExactlyAdjacentAppointment() throws Exception {
        var f=fixture(false);Instant boundary=Instant.now().plusSeconds(3600).truncatedTo(java.time.temporal.ChronoUnit.MICROS);
        seedCreate(f,boundary);
        prepare(new EmployeeAppointmentCommand.Create(id(),f.company(),f.employee(),f.units().get(0),f.position(),
                AssignmentType.PRIMARY,boundary,null,1,1),true);
    }

    private void seedMany(Fixture f,int count) throws Exception {
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

    @Test void completeConflictBoundAccepts1024PeersAndRejectsThe1025thWithoutTruncation() throws Exception {
        var f=fixture(false);
        activatePolicy(f,policy().replace("false","true").replace("SINGLE_HOLDER","UNRESTRICTED")
                .replace("\"maxConcurrentPrimary\":1","\"maxConcurrentPrimary\":10000"));
        seedMany(f,1024);
        var command=new EmployeeAppointmentCommand.Create(id(),f.company(),f.employee(),f.units().get(0),f.position(),AssignmentType.PRIMARY,START,null,1,2);
        prepare(command,true);seedMany(f,1);prepare(command,false);
    }

    @Test void currentEmployeeUnitAndPositionEligibilityMustHoldAtTheNativeSample() throws Exception {
        for(int variant=0;variant<6;variant++){
            var f=fixture(false);
            switch(variant){
                case 0 -> admin("UPDATE public.hidra_org_employee SET status='SUSPENDED' WHERE id=?",f.employee());
                case 1 -> admin("UPDATE public.hidra_org_employee SET hired_at=clock_timestamp()+interval '1 hour' WHERE id=?",f.employee());
                case 2 -> admin("UPDATE public.hidra_org_employee SET terminated_at=clock_timestamp()-interval '1 minute' WHERE id=?",f.employee());
                case 3 -> admin("UPDATE public.hidra_org_unit SET status='INACTIVE' WHERE id=?",f.units().get(0));
                case 4 -> admin("UPDATE public.hidra_org_unit SET valid_from=clock_timestamp()+interval '1 hour' WHERE id=?",f.units().get(0));
                case 5 -> admin("UPDATE public.hidra_org_position SET status='INACTIVE' WHERE id=?",f.position());
                default -> throw new AssertionError();
            }
            prepare(create(f,id()),false);
        }
    }

    @Test void scheduledEmployeeExpiryNarrowsThePhysicalWindowWithoutWaitingOrAnotherWriter() throws Exception {
        var f=fixture(false);var command=create(f,id());
        admin("UPDATE public.hidra_org_employee SET terminated_at=clock_timestamp()-interval '30 seconds' WHERE id=?",f.employee());
        try(var s=new Session(command)){
            s.backdateSeconds=60; // Mocked native sample only: deterministic physical deadline mechanics.
            s.tx.execute(status -> {
                var ticket=s.acquire();var sample=s.storage.contribute(ticket);
                assertEquals(ReferenceUse.CURRENT,s.storage.referenceUse(ticket,command,sample));
                s.storage.contributeParty(ticket);
                assertThrows(AccessDeniedException.class,() -> s.storage.prepareMutation(ticket,command));
                assertTrue(s.poisoned());assertThrows(AccessDeniedException.class,() -> s.storage.requireCurrent(ticket));
                status.setRollbackOnly();return null;
            });
        }
    }

    @Test void committedReplayDoesNotReevaluatePresentMutationPolicyOrOrganizationEligibility() throws Exception {
        var f=fixture();var command=correction(f,id(),1,f.units().get(1));commitCorrection(f,command);
        activatePolicy(f,policy());
        admin("UPDATE public.hidra_org_employee SET status='SUSPENDED' WHERE id=?",f.employee());
        admin("UPDATE public.hidra_org_position SET status='INACTIVE' WHERE id=?",f.position());
        prepare(command,true);
    }

    private ProtectedEmployeeAppointmentRepositoryPort.Admission qualified(Session s) {
        var ticket=s.acquire();var sample=s.storage.contribute(ticket);
        s.storage.referenceUse(ticket,s.command,sample);return ticket;
    }

    @Test void genuinePartyContributionIsConsumedOnceAndPrivatelyQualifiesCurrentPlanning() throws Exception {
        var f=fixture(false);var command=create(f,id());
        try(var s=new Session(command)){
            try(var reader=DriverManager.getConnection(POSTGRES.getJdbcUrl(),s.role,PASSWORD)){
                assertEquals("42501",assertThrows(SQLException.class,() -> scalar(reader,
                        "SELECT count(*) FROM public.hidra_identity_org_commit_key_head")).getSQLState());
            }
            s.tx.execute(status -> {
            var ticket=qualified(s);var eligibility=s.storage.contributeParty(ticket);
            assertEquals(f.company(),eligibility.partyId());
            assertEquals(s.jdbc.queryForObject("SELECT generation FROM public.hidra_party_eligibility_generation WHERE party_id=?",Long.class,f.company()),Long.valueOf(eligibility.generation()));
            assertEquals(s.jdbc.queryForObject("SELECT pg_backend_pid()",Integer.class),Integer.valueOf(eligibility.backendPid()));
            assertEquals(s.jdbc.queryForObject("SELECT txid_current()",Long.class),Long.valueOf(eligibility.transactionId()));
            assertDoesNotThrow(() -> s.storage.prepareMutation(ticket,command));
            assertDoesNotThrow(() -> s.storage.requireCurrent(ticket));
            verify(s.party,times(1)).read(s.partyTicket);
            assertThrows(AccessDeniedException.class,() -> s.storage.contributeParty(ticket));
            assertTrue(s.poisoned());assertThrows(AccessDeniedException.class,() -> s.storage.prepareMutation(ticket,command));
            status.setRollbackOnly();return null;
        });}
    }

    @Test void partyConsumptionRequiresFrozenCurrentClassificationAndPlanningRequiresItsPrivateSample() throws Exception {
        var f=fixture(false);var command=create(f,id());
        for(boolean classify:List.of(false,true))try(var s=new Session(command)){s.tx.execute(status -> {
            var ticket=s.acquire();var sample=s.storage.contribute(ticket);
            if(classify){s.storage.referenceUse(ticket,command,sample);
                assertThrows(AccessDeniedException.class,() -> s.storage.prepareMutation(ticket,command));
            }else assertThrows(AccessDeniedException.class,() -> s.storage.contributeParty(ticket));
            verify(s.party,never()).read(any());assertTrue(s.poisoned());status.setRollbackOnly();return null;
        });}
    }

    @Test void fabricatedForeignIssuerAndWrongCompanyPartyTicketsCannotQualifyStorage() throws Exception {
        var f=fixture(false);var command=create(f,id());
        for(int variant=0;variant<3;variant++)try(var s=new Session(command)){
            if(variant==0)s.partyTicketOverride=new OrganizationProtectedPartyEligibilityContract.Admission(){};
            if(variant==1)s.ticketOwnerOverride=new JdbcOrganizationProtectedPartyEligibilityAdapter(s.jdbc);
            if(variant==2)s.partyIdOverride=baseline.company();
            s.tx.execute(status -> {
                var ticket=qualified(s);
                assertEquals("Protected appointment admission unavailable",assertThrows(AccessDeniedException.class,
                        () -> s.storage.contributeParty(ticket)).getMessage());
                assertTrue(s.poisoned());status.setRollbackOnly();return null;
            });
        }
    }

    @Test void actualSameTransactionPartyGenerationChangeInvalidatesTheRetainedCurrentSample() throws Exception {
        var f=fixture(false);var command=create(f,id());
        try(var s=new Session(command)){s.tx.execute(status -> {
            var ticket=qualified(s);var sample=s.storage.contributeParty(ticket);
            s.storage.prepareMutation(ticket,command);
            assertEquals(1,s.jdbc.update("UPDATE public.hidra_party_party SET status='SUSPENDED' WHERE id=?",f.company()));
            assertTrue(s.jdbc.queryForObject("SELECT generation FROM public.hidra_party_eligibility_generation WHERE party_id=?",Long.class,f.company())>sample.generation());
            assertThrows(AccessDeniedException.class,() -> s.storage.requireCurrent(ticket));
            assertTrue(s.poisoned());status.setRollbackOnly();return null;
        });}
    }

    @Test void retainedAndReplayPlanningNeverConsumesCurrentPartySelectability() throws Exception {
        for(var command:List.of(end(historical,3),originalCorrection))try(var s=new Session(command)){s.tx.execute(status -> {
            var ticket=qualified(s);assertDoesNotThrow(() -> s.storage.prepareMutation(ticket,command));
            verify(s.party,never()).read(any());verify(s.party,never()).requireCurrent(any());
            assertThrows(AccessDeniedException.class,() -> s.storage.contributeParty(ticket));
            verify(s.party,never()).read(any());assertTrue(s.poisoned());status.setRollbackOnly();return null;
        });}
    }

    @Test void foreignThreadPartyConsumptionPoisonsTheOriginalPhysicalHolder() throws Exception {
        var f=fixture(false);var command=create(f,id());
        try(var s=new Session(command)){s.tx.execute(status -> {
            var ticket=qualified(s);var worker=java.util.concurrent.Executors.newSingleThreadExecutor();
            try {
                var failed=worker.submit(() -> assertThrows(AccessDeniedException.class,() -> s.storage.contributeParty(ticket)));
                assertNotNull(failed.get(5,java.util.concurrent.TimeUnit.SECONDS));
            }catch(Exception unexpected){throw new AssertionError(unexpected);}finally{worker.shutdownNow();}
            assertTrue(s.poisoned());verify(s.party,never()).read(any());status.setRollbackOnly();return null;
        });}
    }

    @Test void callerConsumedPartyScalarCannotReplaceTheStorageOwnedOneUseRead() throws Exception {
        var f=fixture(false);var command=create(f,id());
        try(var s=new Session(command)){s.tx.execute(status -> {
            var ticket=qualified(s);var scalar=s.party.read(s.partyTicket);
            assertEquals(f.company(),scalar.partyId());
            assertThrows(AccessDeniedException.class,() -> s.storage.contributeParty(ticket));
            assertTrue(s.poisoned());status.setRollbackOnly();return null;
        });}
    }

    @Test void independentSourceWriterCannotPassTheRetainedClassificationFence() throws Exception {
        var command=end(baseline,1);
        try(var s=new Session(command)){s.tx.execute(status -> {
            var ticket=s.acquire();var sample=s.storage.contribute(ticket);
            assertEquals(ReferenceUse.RETAINED,s.storage.referenceUse(ticket,command,sample));
            try(var writer=owner()) {
                writer.setAutoCommit(false);
                assertEquals("55P03",assertThrows(SQLException.class,() -> execute(writer,
                        "LOCK TABLE public.hidra_org_employee_assignment IN ROW EXCLUSIVE MODE NOWAIT")).getSQLState());
                writer.rollback();
            } catch(SQLException failure){throw new AssertionError(failure);}
            assertEquals(ReferenceUse.RETAINED,s.storage.referenceUse(ticket,command,sample));
            status.setRollbackOnly();return null;
        });}
    }
}
