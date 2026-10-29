/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : ProtectedInstalledUnitLifecyclePostgresIntegrationTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-29
 *
 * @Type        : Class
 * @Layer       : Topology Test
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.infrastructure.persistence
 *
 * @Description : Verifies the protected installed-unit lifecycle with genuine native producers and separate database roles.
 *
 */
package dz.sh.hidra.modules.topology.infrastructure.persistence;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import dz.sh.hidra.HidraApplication;
import dz.sh.hidra.modules.topology.application.port.in.InstalledUnitLifecycleUseCase;
import dz.sh.hidra.modules.topology.domain.model.InstalledUnit;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import dz.sh.hidra.modules.identity.application.contract.topology.TopologyProtectedEngineeringDraftAuthorizationContract;
import dz.sh.hidra.modules.identity.application.contract.topology.TopologyProtectedEngineeringDraftAuthorizationContract.Decision;
import dz.sh.hidra.modules.identity.application.contract.topology.TopologyProtectedEngineeringDraftAuthorizationContract.Action;
import dz.sh.hidra.modules.identity.application.contract.topology.TopologyProtectedEngineeringDraftAuthorizationContract.Request;
import dz.sh.hidra.modules.identity.application.model.*;
import dz.sh.hidra.modules.identity.application.port.in.*;
import dz.sh.hidra.modules.identity.application.port.out.*;
import dz.sh.hidra.modules.identity.domain.model.*;
import dz.sh.hidra.modules.identity.domain.policy.*;
import dz.sh.hidra.modules.identity.domain.value.*;
import dz.sh.hidra.modules.identity.infrastructure.persistence.adapter.JdbcIdentityAuthorityFence;
import dz.sh.hidra.modules.identity.infrastructure.persistence.adapter.JdbcProtectedAuthorizationEvidenceReader;
import dz.sh.hidra.modules.identity.infrastructure.security.*;
import dz.sh.hidra.modules.identity.infrastructure.security.ProtectedOperationIdentityParticipant.*;
import dz.sh.hidra.platform.persistence.ProtectedTransactionExecutor;

import org.flywaydb.core.Flyway;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.*;
import org.springframework.test.context.*;
import org.springframework.test.context.bean.override.mockito.*;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.transaction.*;
import org.springframework.transaction.support.*;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.*;

import java.nio.charset.StandardCharsets;
import java.sql.*;
import java.time.*;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.*;

import javax.crypto.spec.SecretKeySpec;

/**
 * Only directory/source transport is synthetic. Native issuers, private credential routing,
 * owner fences, provisional storage and final commit checks are real. This disposable fixture
 * isolates automatic maintenance. Schema-owner connections seed data and inject deliberate faults.
 */
@Testcontainers(disabledWithoutDocker = true)
@SpringBootTest(
        classes = HidraApplication.class,
        webEnvironment = SpringBootTest.WebEnvironment.MOCK,
        properties = {
            "hidra.platform.security.ldap.enabled=true",
            "hidra.platform.security.ldap.url=ldap://synthetic.invalid:389",
            "hidra.platform.security.ldap.base-dn=dc=synthetic,dc=invalid",
            "hidra.platform.security.ldap.user-search-base=ou=people",
            "hidra.platform.security.ldap.user-search-filter=(uid={0})",
            "hidra.platform.security.jwt.hmac-secret=synthetic-completion-test-key-32-bytes-only",
            "hidra.platform.security.jwt.token-issuer=hidra-api",
            "hidra.platform.security.jwt.audience=hidra-api"
        })
@ActiveProfiles("test")
@org.springframework.test.annotation.DirtiesContext(classMode=org.springframework.test.annotation.DirtiesContext.ClassMode.AFTER_CLASS)
public class ProtectedInstalledUnitLifecyclePostgresIntegrationTest {
    private static final String KEY = "synthetic-completion-test-key-32-bytes-only";
    private static final String SOURCE = "https://oidc.synthetic.invalid";
    private static final String P = "public.hidra_identity_provider",
            U = "public.hidra_identity_user",
            E = "public.hidra_identity_external_identity",
            C = "public.hidra_identity_local_credential",
            S = "public.hidra_identity_login_session",
            B = "public.hidra_identity_operation_binding";

    @Container
    static final PostgreSQLContainer<?> POSTGRES =
            new PostgreSQLContainer<>("postgres:16")
                    .withDatabaseName("hidra_installed_writer_test")
                    .withCommand(
                            "postgres",
                            "-c",
                            "log_error_verbosity=terse",
                            "-c",
                            "track_commit_timestamp=on",
                            // Test-only isolation: fixture cleanup churn must not start an unrelated
                            // maintenance transaction during native NOWAIT admission.
                            "-c",
                            "autovacuum=off",
                            "-c","max_locks_per_transaction=256",
                            "-c",
                            "log_min_error_statement=panic",
                            "-c",
                            "log_parameter_max_length_on_error=0");

    private static final String APP="installed_writer_app",PUBLISHER="installed_writer_publisher",PASSWORD="synthetic-writer-password";
    @DynamicPropertySource static void properties(DynamicPropertyRegistry r) {
        if(!POSTGRES.isRunning())POSTGRES.start();
        Flyway.configure().dataSource(POSTGRES.getJdbcUrl(),POSTGRES.getUsername(),POSTGRES.getPassword()).load().migrate();
        var admin=new JdbcTemplate(new DriverManagerDataSource(POSTGRES.getJdbcUrl(),POSTGRES.getUsername(),POSTGRES.getPassword()));
        admin.execute("REVOKE CREATE ON SCHEMA public FROM PUBLIC");
        for(String role:List.of(APP,PUBLISHER)) {
            admin.execute("CREATE ROLE "+role+" LOGIN PASSWORD '"+PASSWORD+"'");admin.execute("GRANT USAGE ON SCHEMA public TO "+role);
            admin.execute("GRANT SELECT ON ALL TABLES IN SCHEMA public TO "+role);
        }
        admin.execute("GRANT CREATE ON DATABASE "+POSTGRES.getDatabaseName()+" TO "+APP);
        for(String table:admin.queryForList("SELECT tablename FROM pg_catalog.pg_tables WHERE schemaname='public' AND tablename LIKE 'hidra_identity_%'",String.class)) {
            if(!table.matches("hidra_identity_[a-z0-9_]+"))throw new IllegalStateException("Unexpected fixture relation");
            admin.execute("GRANT INSERT,UPDATE,DELETE ON public."+table+" TO "+APP);
        }
        admin.execute("REVOKE INSERT,UPDATE,DELETE ON public.hidra_identity_external_mapping_generation FROM "+APP);
        for(var fence:JdbcIdentityAuthorityFence.requiredRelationFences())if(fence.mode()!=dz.sh.hidra.platform.persistence.ProtectedRelationFence.Mode.ACCESS_SHARE)
            admin.execute("GRANT UPDATE ON "+fence.relation()+" TO "+PUBLISHER);
        for(String suffix:List.of("installed_unit","installed_unit_installation","installed_unit_installation_event","installed_unit_operation_request"))
            admin.execute("GRANT INSERT,UPDATE ON public.hidra_topology_"+suffix+" TO "+PUBLISHER);
        // UPDATE on these immutable owned relations authorizes only their strong relation locks;
        // native immutable triggers and the executor's independent SQL pins still prohibit rewrites.
        provisionHelper(admin,"installed_company_lock_owner","public.hidra_party_lock_installed_unit_manufacturer_evidence()",
            List.of("hidra_party_party","hidra_party_role","hidra_party_role_assignment","hidra_party_manufacturer_profile"));
        provisionHelper(admin,"installed_position_lock_owner","public.hidra_topology_lock_installed_unit_position_ownership()",
            List.of("hidra_topology_equipment","hidra_topology_facility"));
        r.add("spring.datasource.url",POSTGRES::getJdbcUrl);r.add("spring.datasource.username",()->APP);r.add("spring.datasource.password",()->PASSWORD);
        r.add("spring.flyway.url",POSTGRES::getJdbcUrl);r.add("spring.flyway.user",POSTGRES::getUsername);r.add("spring.flyway.password",POSTGRES::getPassword);
        r.add("spring.jpa.hibernate.ddl-auto",()->"validate");r.add("spring.flyway.enabled",()->true);
        r.add("hidra.platform.persistence.publication.enabled",()->true);r.add("hidra.platform.persistence.publication.username",()->PUBLISHER);r.add("hidra.platform.persistence.publication.password",()->PASSWORD);
    }
    private static void provisionHelper(JdbcTemplate admin,String role,String function,List<String> tables) {
        admin.execute("CREATE ROLE "+role+" NOLOGIN NOINHERIT NOSUPERUSER NOBYPASSRLS NOCREATEROLE NOCREATEDB NOREPLICATION");
        admin.execute("GRANT USAGE,CREATE ON SCHEMA public TO "+role);
        for(String table:tables)admin.execute("GRANT UPDATE ON public."+table+" TO "+role);
        admin.execute("ALTER FUNCTION "+function+" OWNER TO "+role);admin.execute("REVOKE CREATE ON SCHEMA public FROM "+role);
        admin.execute("GRANT EXECUTE ON FUNCTION "+function+" TO "+PUBLISHER);
    }


    @Autowired JdbcTemplate jdbc;
    @Autowired InstalledUnitLifecycleUseCase lifecycle;
    @MockitoSpyBean dz.sh.hidra.modules.topology.application.service.ProtectedInstalledUnitAdmissionCoordinator admissionCoordinator;
    @Autowired dz.sh.hidra.modules.identity.application.contract.topology.TopologyInstalledUnitAuthorizationContract installedWriterIdentity;
    @MockitoSpyBean dz.sh.hidra.modules.topology.infrastructure.persistence.adapter.JdbcProtectedInstalledUnitRepositoryAdapter protectedStore;
    private JdbcTemplate admin(){return new JdbcTemplate(new DriverManagerDataSource(POSTGRES.getJdbcUrl(),POSTGRES.getUsername(),POSTGRES.getPassword()));}
    private static final Instant START=Instant.parse("2026-01-01T00:00:00Z");
    private record Place(String position,String facility,String kind){}
    private void ensureCompany() {
        var a=admin();String type=id();a.update("INSERT INTO public.hidra_party_type(id,code,status,created_at,updated_at) VALUES(?,?,'ACTIVE',now(),now())",type,type);
        a.update("INSERT INTO public.hidra_party_party(id,code,party_type_id,legal_name,country_code,status,created_at,updated_at) VALUES(?,?,?,'Synthetic company','DZ','ACTIVE',now(),now()) ON CONFLICT(id) DO NOTHING",COMPANY,COMPANY,type);
    }
    private Place place() {
        var a=admin();String type=id(),kind=id(),facility=id(),position=id();
        a.update("INSERT INTO public.hidra_topology_facility_type(id,code,name,facility_kind,status,created_at,updated_at) VALUES(?,?,'Synthetic','STATION','ACTIVE',now(),now())",type,type);
        a.update("INSERT INTO public.hidra_topology_equipment_type(id,code,name,status,created_at,updated_at) VALUES(?,?,'Synthetic kind','ACTIVE',now(),now())",kind,kind);
        a.update("INSERT INTO public.hidra_topology_facility(id,code,facility_type_id,facility_kind,owner_party_id,status,created_at,updated_at) VALUES(?,?,?,'STATION',?,'ACTIVE',now(),now())",facility,facility,type,COMPANY);
        a.update("INSERT INTO public.hidra_topology_equipment(id,code,facility_id,equipment_type_id,status,created_at,updated_at) VALUES(?,?,?,?,'PLANNED',now(),now())",position,position,facility,kind);
        return new Place(position,facility,kind);
    }
    private Place other(Place first) {
        var a=admin();String facilityType=id(),facility=id(),position=id();
        a.update("INSERT INTO public.hidra_topology_facility_type(id,code,name,facility_kind,status,created_at,updated_at) VALUES(?,?,'Synthetic','STATION','ACTIVE',now(),now())",facilityType,facilityType);
        a.update("INSERT INTO public.hidra_topology_facility(id,code,facility_type_id,facility_kind,owner_party_id,status,created_at,updated_at) VALUES(?,?,?,'STATION',?,'ACTIVE',now(),now())",facility,facility,facilityType,COMPANY);
        a.update("INSERT INTO public.hidra_topology_equipment(id,code,facility_id,equipment_type_id,status,created_at,updated_at) VALUES(?,?,?,?,'OUT_OF_SERVICE',now(),now())",position,position,facility,first.kind());return new Place(position,facility,first.kind());
    }
    private InstalledUnit unit(Place p){return new InstalledUnit(id(),COMPANY,p.kind(),InstalledUnit.Evidence.unknown(),InstalledUnit.Evidence.unknown(),InstalledUnit.Evidence.unknown(),null);}
    private InstalledUnitLifecycleUseCase.Command register(InstalledUnit u){return new InstalledUnitLifecycleUseCase.Command(InstalledUnitLifecycleUseCase.Operation.REGISTER_UNIT,id(),COMPANY,u.id(),null,null,null,null,u,null,null,null,0);}
    private InstalledUnitLifecycleUseCase.Command read(InstalledUnit u){return new InstalledUnitLifecycleUseCase.Command(InstalledUnitLifecycleUseCase.Operation.READ_UNIT,null,COMPANY,u.id(),null,null,null,null,null,null,null,null,0);}
    private InstalledUnitLifecycleUseCase.Position pos(Place p){return new InstalledUnitLifecycleUseCase.Position(p.position(),p.facility());}
    private InstalledUnitLifecycleUseCase.Command record(InstalledUnit u,Place p,String installation){return new InstalledUnitLifecycleUseCase.Command(InstalledUnitLifecycleUseCase.Operation.RECORD_INSTALLATION,id(),COMPANY,u.id(),installation,null,null,pos(p),null,START,null,"synthetic occupancy",0);}
    private dz.sh.hidra.modules.identity.application.contract.topology.TopologyInstalledUnitAuthorizationContract.Request nativeRequest(InstalledUnitLifecycleUseCase.Command c) {
        return new dz.sh.hidra.modules.identity.application.contract.topology.TopologyInstalledUnitAuthorizationContract.Request(
            dz.sh.hidra.modules.identity.application.contract.topology.TopologyInstalledUnitAuthorizationContract.Operation.valueOf(c.operation().name()),c.companyPartyId(),c.unitId(),c.installationId(),c.replacementInstallationId(),c.source()==null?null:c.source().facilityId(),c.destination()==null?null:c.destination().facilityId());
    }
    private void authorize(Fixture f,InstalledUnitLifecycleUseCase.Command c) {
        var request=nativeRequest(c);String code=dz.sh.hidra.modules.identity.application.contract.topology.TopologyInstalledUnitAuthorizationContract.permissionCode(request.operation());
        String permission=jdbc.queryForList("SELECT id FROM public.hidra_identity_permission WHERE code=?",String.class,code).stream().findFirst().orElse(null);
        if(permission==null){permission=id();jdbc.update("INSERT INTO public.hidra_identity_permission(id,code,permission_domain,resource_type,action,sensitive,status,created_at,updated_at) VALUES(?,?,'topology',?,?,false,'ACTIVE',now(),now())",permission,code,request.requirements().getFirst().resourceType(),code.substring("topology:installed-unit:".length()));}
        var seen=new HashSet<String>();
        for(var requirement:request.requirements()) {
            String scope=requirement.scope().name(),ref=scope.equals("COMPANY")?"topology-company:"+requirement.scopeReference():requirement.scopeReference();
            if(!seen.add(scope+":"+ref))continue;
            jdbc.update("INSERT INTO public.hidra_identity_user_permission_grant(id,user_id,permission_id,effect,scope_type,scope_reference_id,grant_reason,emergency_access,valid_from,valid_to,status,created_at) VALUES(?,?,?,'GRANT',?,?,'synthetic installed writer',false,now()-interval '1 hour',now()+interval '1 hour','ACTIVE',now())",id(),f.user(),permission,scope.equals("COMPANY")?"CUSTOM":"FACILITY",ref);
        }
    }
    private void login(Fixture f) {
        if(f.type().equals("LDAP") || f.type().equals("ACTIVE_DIRECTORY"))c3DirectoryPrepared(f,"memberOf");
        else ambient(authenticate(f).accessToken());
    }
    private InstalledUnitLifecycleUseCase.Result execute(Fixture f,InstalledUnitLifecycleUseCase.Command c){authorize(f,c);login(f);return lifecycle.execute(c);}
    private long count(String table){return admin().queryForObject("SELECT count(*) FROM public.hidra_topology_"+table,Long.class);}
    private String effects(){return List.of(count("installed_unit"),count("installed_unit_installation"),count("installed_unit_installation_event"),count("installed_unit_operation_request")).toString();}

    @MockitoSpyBean JdbcProtectedAuthorizationEvidenceReader reader;
    @Autowired jakarta.persistence.EntityManager em;
    @Autowired Flyway flyway;
    @MockitoSpyBean ProtectedOperationIdentityParticipant participant;

    @Autowired
    dz.sh.hidra.modules.identity.application.contract.topology
                    .TopologyProtectedEngineeringDraftAuthorizationContract
            paired;

    @Autowired JdbcIdentityAuthorityFence fence;
    // Failure injection only: real event persistence and real owner transaction still execute.

    @Autowired ProtectedTransactionExecutor executor;
    @Autowired PlatformTransactionManager manager;
    @Autowired AuthenticateDirectUserUseCase direct;
    @Autowired CompleteAuthenticatedPrincipalUseCase completion;
    @Autowired DirectAuthenticationPort directPort;
    @Autowired IdentityOidcJwtAuthenticationConverter oidc;
    @Autowired LdapAuthenticationProvider ldap;
    @Autowired TrustedAuthenticationVerificationRegistry verificationRegistry;
    @MockitoSpyBean OperationAuthenticationBindingRepositoryPort bindings;
    @Autowired PasswordEncoder passwords;
    @Autowired LoginSessionRepositoryPort sessions;
    @MockitoSpyBean ClaimNameCatalogPort claimCatalog;
    @MockitoSpyBean BoundSourceClaimIssuanceFencePort claimIssuanceFence;
    @MockitoSpyBean BoundSourceClaimEvidenceRepositoryPort claimCompanions;

    @Autowired
    @Qualifier("hidraJwtDecoder")
    JwtDecoder nativeDecoder;

    // Transport fixture only: no external directory service in this synthetic database.
    @MockitoBean LdapCredentialVerificationPort directory;

    // Transport fixture delegates genuine signed source verification below; native decoder is real.
    @MockitoBean(name = "externalOidcJwtDecoder")
    JwtDecoder externalDecoder;

    // Capture only: every call delegates the real LOCAL producer including its private receipt.

    private static final tools.jackson.databind.json.JsonMapper JSON =
            tools.jackson.databind.json.JsonMapper.builder().build();
    private final JwtEncoder sourceEncoder =
            NimbusJwtEncoder.withSecretKey(
                            new SecretKeySpec(KEY.getBytes(StandardCharsets.UTF_8), "HmacSHA256"))
                    .algorithm(MacAlgorithm.HS256)
                    .build();
    private final JwtDecoder realExternal =
            NimbusJwtDecoder.withSecretKey(
                            new SecretKeySpec(KEY.getBytes(StandardCharsets.UTF_8), "HmacSHA256"))
                    .macAlgorithm(MacAlgorithm.HS256)
                    .build();

    private record Fixture(
            String user,
            String provider,
            String link,
            String credential,
            String subject,
            String type) {}

    private String schema;
    private final AtomicInteger receipts = new AtomicInteger();

    @BeforeEach
    void initialize() {
        SecurityContextHolder.clearContext();
        cleanEvidence();
        flyway.validate();
        assertThat(flyway.info().pending()).isEmpty();
        when(externalDecoder.decode(anyString()))
                .thenAnswer(i -> realExternal.decode(i.getArgument(0)));
        schema = "hapi581_" + id().replace("-", "");
        jdbc.execute("CREATE SCHEMA " + schema);
        jdbc.execute("CREATE TABLE " + receipt() + "(id integer PRIMARY KEY)");
        c3NonownerCapture();ensureCompany();
        // Warm only genuine native qualification, without business storage or a protected writer bypass.
        // Every lifecycle operation below still runs under the unchanged protected500ms budget.
        var f=fixture("LOCAL");var unit=new InstalledUnit("native-warm-unit",COMPANY,"native-warm-kind",
            InstalledUnit.Evidence.unknown(),InstalledUnit.Evidence.unknown(),InstalledUnit.Evidence.unknown(),null);
        var cmd=register(unit);authorize(f,cmd);login(f);
        var prepared=installedWriterIdentity.prepare();
        tx().executeWithoutResult(status->{
            var ticket=installedWriterIdentity.acquire(prepared,nativeRequest(cmd));
            installedWriterIdentity.contribute(ticket);installedWriterIdentity.requireCurrent(ticket);
        });
        cleanEvidence();clearInvocations(readerTarget(),identityTarget());
    }

    @AfterEach
    void clear() {
        ReflectionTestUtils.setField(oidc, "verifiedClaimReceiptEnabled", false);
        ReflectionTestUtils.setField(ldap, "verifiedClaimReceiptEnabled", false);
        directPort.finishVerification();
        SecurityContextHolder.clearContext();
        cleanEvidence();
        if (schema != null) jdbc.execute("DROP SCHEMA " + schema + " CASCADE");
    }

    private JdbcProtectedAuthorizationEvidenceReader readerTarget() {
        return org.springframework.test.util.AopTestUtils.getUltimateTargetObject(reader);
    }

    private ProtectedOperationIdentityParticipant identityTarget() {
        return org.springframework.test.util.AopTestUtils.getUltimateTargetObject(participant);
    }

    private String receipt() {
        return schema + ".receipt";
    }

    private TransactionTemplate tx() {
        var t = new TransactionTemplate(manager);
        t.setIsolationLevel(TransactionDefinition.ISOLATION_READ_COMMITTED);
        return t;
    }

    private Request request() {
        return new Request(Action.CREATE, COMPANY, FACILITY);
    }

    private TopologyProtectedEngineeringDraftAuthorizationContract.Prepared prepare(Fixture f) {
        ambient(authenticate(f).accessToken());
        return paired.prepare();
    }

    private TopologyProtectedEngineeringDraftAuthorizationContract.Prepared originalSource(Fixture f) {
        if (f.type().equals("LDAP") || f.type().equals("ACTIVE_DIRECTORY"))
            return c3DirectoryPrepared(f, "memberOf");
        return prepare(f);
    }

    private TopologyProtectedEngineeringDraftAuthorizationContract.Contribution run(
            TopologyProtectedEngineeringDraftAuthorizationContract.Prepared p, Request request) {
        return run(p, request, () -> {}, () -> {});
    }

    private TopologyProtectedEngineeringDraftAuthorizationContract.Contribution run(
            TopologyProtectedEngineeringDraftAuthorizationContract.Prepared p,
            Request request,
            Runnable inside,
            Runnable guardAction) {
        var saved = new AtomicReference<TopologyProtectedEngineeringDraftAuthorizationContract.Contribution>();
        try {
            return executor.execute(
                () -> {
                    c3SelectRestrictedRole();
                    var result = paired.contribute(paired.acquire(p, request));
                    saved.set(result);
                    assertThat(jdbc.queryForObject("SELECT pg_backend_pid()", Integer.class))
                            .isEqualTo(result.backendPid());
                    assertThat(jdbc.queryForObject("SELECT txid_current()", Long.class))
                            .isEqualTo(result.transactionId());
                    inside.run();
                    return result;
                },
                em::flush,
                () -> {
                    guardAction.run();
                    var c = saved.get();
                    Integer count =
                            jdbc.queryForObject(
                                    "WITH observed AS MATERIALIZED (SELECT clock_timestamp() AS t),"
                                        + " made AS (INSERT INTO "
                                            + receipt()
                                            + " SELECT ? FROM observed WHERE t>=? AND t<? RETURNING"
                                            + " 1) SELECT count(*) FROM made",
                                    Integer.class,
                                    receipts.incrementAndGet(),
                                    Timestamp.from(c.sampledAt()),
                                    Timestamp.from(c.validUntil()));
                    if (!Integer.valueOf(1).equals(count))
                        throw new IllegalStateException("Synthetic guard expired");
                });
        } catch (ProtectedTransactionExecutor.ExecutionFailure failure) {
            // The executor has already rolled back/closed its scope. This independent diagnostic
            // read grants no authority and cannot turn rejection into success. A vanished blocker
            // yields an empty snapshot; never label it as a proven maintenance conflict.
            try {
                var locks=jdbc.queryForList("""
                    SELECT l.pid,l.mode,l.granted,a.backend_type,a.state,a.wait_event_type,a.wait_event
                    FROM pg_catalog.pg_locks l
                    LEFT JOIN pg_catalog.pg_stat_activity a ON a.pid=l.pid
                    WHERE l.locktype='relation'
                      AND l.relation=pg_catalog.to_regclass('public.hidra_identity_permission')
                    ORDER BY l.pid,l.mode
                    """);
                failure.addSuppressed(new IllegalStateException("Post-rollback permission lock snapshot: "+locks));
            } catch (RuntimeException diagnosticFailure) {
                failure.addSuppressed(diagnosticFailure);
            }
            throw failure;
        }
    }

    private void permit(TopologyProtectedEngineeringDraftAuthorizationContract.Contribution c) {
        assertThat(c.companyDecision()).isEqualTo(Decision.PERMIT);
        assertThat(c.networkDecision()).isEqualTo(Decision.PERMIT);
    }

    private void rejected(Runnable work) {
        assertThatThrownBy(work::run).isInstanceOf(RuntimeException.class);
    }

    private static org.springframework.security.oauth2.server.resource.authentication
                    .JwtAuthenticationToken
            ambient(String raw) {
        var jwt =
                Jwt.withTokenValue(raw)
                        .header("alg", "HS256")
                        .issuer("forged-wrapper")
                        .subject("forged-wrapper")
                        .issuedAt(Instant.now().minusSeconds(1))
                        .expiresAt(Instant.now().plusSeconds(3600))
                        .build();
        var actual =
                new org.springframework.security.oauth2.server.resource.authentication
                        .JwtAuthenticationToken(jwt, List.of());
        actual.setAuthenticated(true);
        SecurityContextHolder.getContext().setAuthentication(actual);
        return actual;
    }

    @SuppressWarnings("unchecked")
    private AuthenticationResult authenticate(Fixture f) {
        if (f.type().equals("OIDC"))
            return completeOidc(f, Instant.now().plusSeconds(300).truncatedTo(ChronoUnit.SECONDS));
        return direct.authenticate(
                new DirectAuthenticationCommand(
                        ProviderType.valueOf(f.type()),
                        f.user(),
                        "synthetic-password",
                        "synthetic-ip",
                        "synthetic-agent",
                        "synthetic-correlation"));
    }

    private AuthenticationResult completeOidc(Fixture f, Instant deadline) {
        return completeOidc(f, deadline, List.of("private-original-group"));
    }

    private AuthenticationResult completeOidc(Fixture f, Instant deadline, Object groups) {
        Jwt signed =
                sourceEncoder.encode(
                        JwtEncoderParameters.from(
                                JwtClaimsSet.builder()
                                        .issuer(SOURCE)
                                        .subject(f.subject())
                                        .audience(List.of("hidra-api"))
                                        .issuedAt(Instant.now().minusSeconds(1))
                                        .expiresAt(deadline)
                                        .claim("groups", groups)
                                        .build()));
        var actual = oidc.convert(realExternal.decode(signed.getTokenValue()));
        SecurityContextHolder.getContext().setAuthentication(actual);
        return completion.complete(
                input((HidraPrincipal) actual.getPrincipal(), null),
                "synthetic-ip",
                "synthetic-agent",
                "synthetic-correlation");
    }

    private static AuthenticatedPrincipalInput input(HidraPrincipal p, String handle) {
        return new AuthenticatedPrincipalInput(
                p.userId(),
                p.username(),
                p.displayName(),
                p.authenticationType(),
                p.identityProviderId(),
                p.roles(),
                p.permissions(),
                p.externalIdentityId(),
                handle);
    }

    private int backend() {
        return jdbc.queryForObject("SELECT pg_backend_pid()", Integer.class);
    }

    private Fixture fixture(String type) {
        jdbc.update("UPDATE " + P + " SET status='INACTIVE' WHERE status='ACTIVE'");
        String user = UUID.randomUUID().toString(),
                provider = UUID.randomUUID().toString(),
                link = type.equals("LOCAL") ? null : UUID.randomUUID().toString(),
                credential = type.equals("LOCAL") ? UUID.randomUUID().toString() : null,
                subject = link == null ? null : "  exact-synthetic-é-" + link + "  ";
        Timestamp now = Timestamp.from(Instant.now());
        jdbc.update(
                "INSERT INTO "
                        + U
                        + "(id,username,user_type,status,failed_login_count,created_at,updated_at)"
                        + " VALUES (?,?,'HUMAN','ACTIVE',0,?,?)",
                user,
                user,
                now,
                now);
        jdbc.update(
                "INSERT INTO "
                        + P
                        + "(id,code,name,provider_type,issuer_uri,directory_base_dn,user_search_base,status,sync_enabled,just_in_time_provisioning_enabled,created_at,updated_at)"
                        + " VALUES (?,?,'Synthetic completion"
                        + " provider',?,?,?,?,'ACTIVE',false,false,?,?)",
                provider,
                provider,
                type,
                type.equals("OIDC") ? SOURCE : null,
                type.equals("LDAP") || type.equals("ACTIVE_DIRECTORY")
                        ? "dc=synthetic,dc=invalid"
                        : null,
                type.equals("LDAP") || type.equals("ACTIVE_DIRECTORY") ? "ou=people" : null,
                now,
                now);
        if (credential != null)
            jdbc.update(
                    "INSERT INTO "
                            + C
                            + "(id,user_id,password_hash,credential_status,created_at,updated_at)"
                            + " VALUES (?, ?,?,'ACTIVE',?,?)",
                    credential,
                    user,
                    passwords.encode("synthetic-password"),
                    now,
                    now);
        else {
            jdbc.update(
                    "INSERT INTO "
                            + E
                            + "(id,user_id,identity_provider_id,external_subject,status,created_at,updated_at)"
                            + " VALUES (?,?,?,?,'LINKED',?,?)",
                    link,
                    user,
                    provider,
                    subject,
                    now,
                    now);
            if (!type.equals("OIDC"))
                when(directory.verify(user, "synthetic-password"))
                        .thenReturn(
                                Optional.of(
                                        new VerifiedDirectoryIdentity(
                                                subject, user, "Synthetic operator", null, null)));
        }
        return new Fixture(user, provider, link, credential, subject, type);
    }

    private static final String T = "public.hidra_identity_";
    private static final List<String> OPS=List.of("create","read","update","validate");
    private static final String COMPANY = "synthetic-company", FACILITY = "synthetic-facility";

    private static String id() {
        return UUID.randomUUID().toString();
    }

    private void cleanEvidence() {
        // Only ordinary fixtures; guarded security generations and provenance are retained.
        for (String name :
                List.of(
                        "authorization_policy_rule",
                        "authorization_policy_version",
                        "authorization_policy",
                        "subject_security_attribute",
                        "attribute_definition",
                        "authorization_delegation_grant",
                        "user_permission_grant",
                        "role_permission_grant",
                        "group_role_grant",
                        "user_role_grant",
                        "user_group_membership",
                        "external_permission_mapping",
                        "external_role_mapping",
                        "external_group_mapping",
                        "group",
                        "role",
                        "permission")) jdbc.update("DELETE FROM " + T + name);
    }

    private Instant dbNow() {
        return jdbc.queryForObject("SELECT clock_timestamp()", Timestamp.class).toInstant();
    }

    private String permission(String op) {
        String code = "topology:engineering-draft:" + op;
        List<String> found =
                jdbc.queryForList(
                        "SELECT id FROM " + T + "permission WHERE code=?", String.class, code);
        if (!found.isEmpty()) return found.getFirst();
        String id = id();
        jdbc.update(
                "INSERT INTO "
                        + T
                        + "permission(id,code,permission_domain,resource_type,action,sensitive,status,created_at,updated_at)"
                        + " VALUES"
                        + " (?,?,'topology','ENGINEERING_NETWORK_DRAFT',?,false,'ACTIVE',clock_timestamp(),clock_timestamp())",
                id,
                code,
                op);
        return id;
    }

    private String grant(Fixture f, String op, String effect, String scope, String ref) {
        String id = id();
        jdbc.update(
                "INSERT INTO "
                        + T
                        + "user_permission_grant(id,user_id,permission_id,effect,scope_type,scope_reference_id,grant_reason,emergency_access,valid_from,valid_to,status,created_at)"
                        + " VALUES (?,?,?,?,?,?,'synthetic"
                        + " evidence',false,clock_timestamp()-interval '1"
                        + " hour',clock_timestamp()+interval '1 hour','ACTIVE',clock_timestamp())",
                id,
                f.user(),
                permission(op),
                effect,
                scope,
                ref);
        return id;
    }

    private void both(Fixture f, String op) {
        grant(f, op, "GRANT", "CUSTOM", "topology-company:" + COMPANY);
        grant(f, op, "GRANT", "CUSTOM", "topology-network:"+FACILITY);
    }

    private AuthorizationEvaluationRequest request(Fixture f, String op, boolean company) {
        return new AuthorizationEvaluationRequest(
                f.user(),
                "topology:engineering-draft:" + op,
                "ENGINEERING_NETWORK_DRAFT",
                FACILITY,
                new AuthorizationScope(
                        ScopeType.CUSTOM,
                        company ? "topology-company:" + COMPANY : "topology-network:"+FACILITY,
                        null));
    }

    private String role(Fixture f, String op, String effect, boolean grouped) {
        String role = id(), rp = id();
        jdbc.update(
                "INSERT INTO "
                        + T
                        + "role(id,code,role_type,status,created_at,updated_at) VALUES"
                        + " (?,?,'BUSINESS','ACTIVE',clock_timestamp(),clock_timestamp())",
                role,
                role);
        jdbc.update(
                "INSERT INTO "
                        + T
                        + "role_permission_grant(id,role_id,permission_id,effect,valid_from,status,created_at)"
                        + " VALUES (?,?,?,?,clock_timestamp()-interval '1"
                        + " hour','ACTIVE',clock_timestamp())",
                rp,
                role,
                permission(op),
                effect);
        if (!grouped)
            jdbc.update(
                    "INSERT INTO "
                            + T
                            + "user_role_grant(id,user_id,role_id,valid_from,status,created_at)"
                            + " VALUES (?,?,?,clock_timestamp()-interval '1"
                            + " hour','ACTIVE',clock_timestamp())",
                    id(),
                    f.user(),
                    role);
        else {
            String group = id();
            jdbc.update(
                    "INSERT INTO "
                            + T
                            + "group(id,code,group_type,status,created_at,updated_at) VALUES"
                            + " (?,?,'LOCAL','ACTIVE',clock_timestamp(),clock_timestamp())",
                    group,
                    group);
            jdbc.update(
                    "INSERT INTO "
                            + T
                            + "user_group_membership(id,user_id,group_id,membership_type,valid_from,status,created_at,updated_at)"
                            + " VALUES (?,?,?,'DIRECT',clock_timestamp()-interval '1"
                            + " hour','ACTIVE',clock_timestamp(),clock_timestamp())",
                    id(),
                    f.user(),
                    group);
            jdbc.update(
                    "INSERT INTO "
                            + T
                            + "group_role_grant(id,group_id,role_id,valid_from,status,created_at)"
                            + " VALUES (?,?,?,clock_timestamp()-interval '1"
                            + " hour','ACTIVE',clock_timestamp())",
                    id(),
                    group,
                    role);
        }
        return role;
    }

    private String attribute(
            String subjectType, String subject, String code, String type, String value) {
        String d = id(), a = id();
        jdbc.update(
                "INSERT INTO "
                        + T
                        + "attribute_definition(id,code,name,attribute_target,data_type,multi_valued,sensitive,status,created_at,updated_at)"
                        + " VALUES (?,?,?,"
                        + " ?,?,false,false,'ACTIVE',clock_timestamp(),clock_timestamp())",
                d,
                code,
                code,
                subjectType,
                type);
        jdbc.update(
                "INSERT INTO "
                        + T
                        + "subject_security_attribute(id,subject_type,subject_id,attribute_definition_id,attribute_value,status,created_at,updated_at)"
                        + " VALUES (?,?,?,?,?,'ACTIVE',clock_timestamp(),clock_timestamp())",
                a,
                subjectType,
                subject,
                d,
                value);
        return d;
    }

    private String policy(String effect, String context) {
        String p = id(), v = id();
        jdbc.update(
                "INSERT INTO "
                        + T
                        + "authorization_policy(id,code,name,policy_domain,status,created_at,updated_at)"
                        + " VALUES (?,?,?,'topology','ACTIVE',clock_timestamp(),clock_timestamp())",
                p,
                p,
                p);
        jdbc.update(
                "INSERT INTO "
                        + T
                        + "authorization_policy_version(id,policy_id,version_number,status,effective_from,created_at)"
                        + " VALUES (?,?,1,'ACTIVE',clock_timestamp()-interval '1"
                        + " hour',clock_timestamp())",
                v,
                p);
        jdbc.update(
                "INSERT INTO "
                        + T
                        + "authorization_policy_rule(id,policy_version_id,rule_code,effect,priority,context_expression,status,created_at)"
                        + " VALUES (?,?,?, ?,0,?::jsonb,'ACTIVE',clock_timestamp())",
                id(),
                v,
                id(),
                effect,
                context);
        return p;
    }

    private void c3NonownerCapture() {
        ReflectionTestUtils.setField(oidc,"verifiedClaimReceiptEnabled",true);
    }


    /**
     * Only this synthetic fixture switches the PostgreSQL current role. The protected transaction
     * still rejects SET/RESET SQL statements; no production escape hatch or alternate authorization
     * implementation is installed. The setting is LOCAL to the physical transaction and
     * automatically resets on completion.
     */
    private void c3SelectRestrictedRole() {
        assertThat(
                        jdbc.queryForObject(
                                "SELECT set_config('role',?,true)",
                                String.class,
                                "c3_source_claim_app"))
                .isEqualTo("c3_source_claim_app");
        assertThat(
                        jdbc.queryForObject(
                                "SELECT current_user='c3_source_claim_app' AND NOT"
                                    + " has_schema_privilege(current_user,'public','CREATE') AND"
                                    + " NOT has_table_privilege(current_user,"
                                    + "'public.hidra_identity_external_mapping_generation','UPDATE')",
                                Boolean.class))
                .isTrue();
    }

    private void c3PermissionMapping(Fixture f, String op, String effect) {
        jdbc.update(
                "INSERT INTO public.hidra_identity_external_permission_mapping"
                    + "(id,identity_provider_id,permission_id,external_permission_code,claim_name,mapping_mode,effect,status,created_at,updated_at)"
                    + " VALUES"
                    + " (?,?,?,'private-original-group','groups','DIRECT_GRANT',?,'ACTIVE',now(),now())",
                id(),
                f.provider(),
                permission(op),
                effect);
    }

    private TopologyProtectedEngineeringDraftAuthorizationContract.Prepared c3DirectoryPrepared(
            Fixture f, String attributeName) {
        var properties =
                new dz.sh.hidra.platform.configuration.HidraLdapSecurityProperties(
                        true,
                        "ldap://synthetic.invalid:389",
                        "dc=synthetic,dc=invalid",
                        "ou=people",
                        "(uid={0})",
                        null,
                        null,
                        null,
                        null);
        var adapter =
                new LdapCredentialVerificationAdapter(
                        mock(org.springframework.ldap.core.support.LdapContextSource.class),
                        properties,
                        verificationRegistry);
        var template = mock(org.springframework.ldap.core.LdapTemplate.class);
        ReflectionTestUtils.setField(adapter, "ldapTemplate", template);
        var context = mock(javax.naming.directory.DirContext.class);
        var attrs = new javax.naming.directory.BasicAttributes(true);
        attrs.put("entryUUID", f.subject());
        attrs.put("uid", f.user());
        if ("binary".equals(attributeName)) attrs.put("memberOf", new byte[] {1, 2});
        else if (attributeName != null)
            attrs.put(attributeName, " private-original-directory-group ");
        try {
            when(context.getAttributes(any(javax.naming.Name.class), any(String[].class)))
                    .thenReturn(attrs);
            when(template.authenticate(
                            anyString(),
                            anyString(),
                            anyString(),
                            any(
                                    org.springframework.ldap.core
                                            .AuthenticatedLdapEntryContextCallback.class)))
                    .thenAnswer(
                            i -> {
                                ((org.springframework.ldap.core
                                                        .AuthenticatedLdapEntryContextCallback)
                                                i.getArgument(3))
                                        .executeWithContext(
                                                context,
                                                new org.springframework.ldap.core
                                                        .LdapEntryIdentification(
                                                        new javax.naming.ldap.LdapName(
                                                                "uid=synthetic,ou=people,dc=synthetic,dc=invalid"),
                                                        new javax.naming.ldap.LdapName(
                                                                "uid=synthetic,ou=people")));
                                return true;
                            });
        } catch (javax.naming.NamingException impossible) {
            throw new IllegalStateException(impossible);
        }
        doAnswer(i -> adapter.verify(i.getArgument(0), i.getArgument(1)))
                .when(directory)
                .verify(f.user(), "synthetic-password");
        ReflectionTestUtils.setField(ldap, "verifiedClaimReceiptEnabled", true);
        return prepare(f);
    }









    private void publicationGrant(Fixture f,String action,String effect,String scope) {
        String code="topology:engineering-publication:"+action;
        var found=jdbc.queryForList("SELECT id FROM public.hidra_identity_permission WHERE code=?",String.class,code);
        String permission=found.isEmpty()?id():found.getFirst();
        if(found.isEmpty())jdbc.update("INSERT INTO public.hidra_identity_permission(id,code,permission_domain,resource_type,action,sensitive,status,created_at,updated_at) VALUES(?,?,'topology','ENGINEERING_NETWORK_PUBLICATION',?,false,'ACTIVE',now(),now())",permission,code,action);
        jdbc.update("INSERT INTO public.hidra_identity_user_permission_grant(id,user_id,permission_id,effect,scope_type,scope_reference_id,grant_reason,emergency_access,valid_from,valid_to,status,created_at) VALUES(?,?,?,?, 'CUSTOM',?,'synthetic publication authority',false,now()-interval '1 hour',now()+interval '1 hour','ACTIVE',now())",
            id(),f.user(),permission,effect,scope);
    }



    @Test void allSevenOperationsCommitThroughEachGenuineNativeProducer() {
        for(String type:List.of("LOCAL","LDAP","ACTIVE_DIRECTORY","OIDC")) {
            cleanEvidence();var f=fixture(type);var p=place();var q=other(p);var u=unit(p);
            var registration=register(u);assertThat(execute(f,registration).unit()).isEqualTo(u);
            assertThat(execute(f,registration).replayed()).isTrue();assertThat(execute(f,read(u)).unit()).isEqualTo(u);
            String first=id(),second=id();assertThat(execute(f,record(u,p,first)).installation().revision()).isEqualTo(1);
            var move=new InstalledUnitLifecycleUseCase.Command(InstalledUnitLifecycleUseCase.Operation.RELOCATE,id(),COMPANY,u.id(),first,second,pos(p),pos(q),null,START.plusSeconds(10),START.plusSeconds(10),"synthetic move",1);
            var moved=execute(f,move);assertThat(moved.installation().effectiveUntil()).isEqualTo(moved.replacement().effectiveFrom());
            assertThat(execute(f,move).replayed()).isTrue();
            var close=new InstalledUnitLifecycleUseCase.Command(InstalledUnitLifecycleUseCase.Operation.CLOSE_INSTALLATION,id(),COMPANY,u.id(),second,null,pos(q),null,null,null,START.plusSeconds(20),null,1);
            assertThat(execute(f,close).installation().revision()).isEqualTo(2);
            for(var operation:List.of(InstalledUnitLifecycleUseCase.Operation.READ_INSTALLATION,InstalledUnitLifecycleUseCase.Operation.READ_HISTORY)) {
                var command=new InstalledUnitLifecycleUseCase.Command(operation,null,COMPANY,u.id(),second,null,pos(q),null,null,null,null,null,0);
                var result=execute(f,command);assertThat(result.installation().effectiveUntil()).isEqualTo(START.plusSeconds(20));
                if(operation==InstalledUnitLifecycleUseCase.Operation.READ_HISTORY)assertThat(result.history()).hasSize(2);
            }
        }
    }
    @Test void scopedRevocationDeniesFreshReadsAndExactReplayWithoutEffects() {
        var f=fixture("LOCAL");var p=place();var u=unit(p);var c=register(u);execute(f,c);var read=read(u);authorize(f,read);login(f);String before=effects();
        jdbc.update("UPDATE public.hidra_identity_user_permission_grant SET status='REVOKED' WHERE user_id=?",f.user());
        assertThatThrownBy(()->lifecycle.execute(read)).isInstanceOf(RuntimeException.class);
        assertThatThrownBy(()->lifecycle.execute(c)).isInstanceOf(RuntimeException.class);assertThat(effects()).isEqualTo(before);
    }
    @Test void missingDestinationScopeAndChangedCompanyCannotPartiallyMoveUnit() {
        var f=fixture("LOCAL");var p=place();var q=other(p);var u=unit(p);execute(f,register(u));String first=id();execute(f,record(u,p,first));
        var move=new InstalledUnitLifecycleUseCase.Command(InstalledUnitLifecycleUseCase.Operation.RELOCATE,id(),COMPANY,u.id(),first,id(),pos(p),pos(q),null,START.plusSeconds(1),START.plusSeconds(1),"move",1);
        authorize(f,move);jdbc.update("UPDATE public.hidra_identity_user_permission_grant SET status='REVOKED' WHERE user_id=? AND scope_reference_id=?",f.user(),q.facility());login(f);String before=effects();
        assertThatThrownBy(()->lifecycle.execute(move)).isInstanceOf(RuntimeException.class);assertThat(effects()).isEqualTo(before);
        admin().update("UPDATE public.hidra_topology_facility SET owner_party_id=? WHERE id=?",id(),p.facility());
        assertThatThrownBy(()->lifecycle.execute(move)).isInstanceOf(RuntimeException.class);assertThat(effects()).isEqualTo(before);
    }
    @Test void overlapDuringRelocationRollsBackClosureEventAndReceipt() {
        var f=fixture("LOCAL");var p=place();var q=other(p);var u=unit(p);var occupied=unit(q);
        execute(f,register(u));execute(f,register(occupied));String first=id();execute(f,record(u,p,first));execute(f,record(occupied,q,id()));
        var move=new InstalledUnitLifecycleUseCase.Command(InstalledUnitLifecycleUseCase.Operation.RELOCATE,id(),COMPANY,u.id(),first,id(),pos(p),pos(q),null,START.plusSeconds(1),START.plusSeconds(1),"conflicting move",1);
        authorize(f,move);login(f);String before=effects();assertThatThrownBy(()->lifecycle.execute(move)).isInstanceOf(RuntimeException.class);assertThat(effects()).isEqualTo(before);
        assertThat(admin().queryForObject("SELECT effective_until IS NULL AND revision=1 FROM public.hidra_topology_installed_unit_installation WHERE installation_id=?",Boolean.class,first)).isTrue();
    }
    @Test void staleRevisionAndConflictingRequestIdentityLeaveNoExtraEvidence() {
        var f=fixture("LOCAL");var p=place();var u=unit(p);var c=register(u);execute(f,c);
        var changed=new InstalledUnit(u.id(),COMPANY,u.equipmentKindId(),u.manufacturerParty(),u.manufacturerModel(),new InstalledUnit.Evidence("changed",InstalledUnit.Verification.UNVERIFIED,"synthetic source"),null);
        var conflicting=new InstalledUnitLifecycleUseCase.Command(c.operation(),c.requestId(),COMPANY,u.id(),null,null,null,null,changed,null,null,null,0);authorize(f,conflicting);login(f);String before=effects();
        assertThatThrownBy(()->lifecycle.execute(conflicting)).isInstanceOf(RuntimeException.class);assertThat(effects()).isEqualTo(before);
        String installation=id();execute(f,record(u,p,installation));
        var stale=new InstalledUnitLifecycleUseCase.Command(InstalledUnitLifecycleUseCase.Operation.CLOSE_INSTALLATION,id(),COMPANY,u.id(),installation,null,pos(p),null,null,null,START.plusSeconds(1),null,2);
        authorize(f,stale);login(f);before=effects();assertThatThrownBy(()->lifecycle.execute(stale)).isInstanceOf(RuntimeException.class);assertThat(effects()).isEqualTo(before);
    }
    @Test void databaseKindReferenceIsValidatedAndRestrictsDeletionWithoutCatalogDmlGrant() throws Exception {
        var f=fixture("LOCAL");var p=place();var u=unit(p);execute(f,register(u));
        assertThat(admin().queryForObject("SELECT convalidated AND NOT condeferrable AND confdeltype='r' AND confupdtype='r' FROM pg_catalog.pg_constraint WHERE conname='fk_installed_unit_equipment_type' AND conrelid='public.hidra_topology_installed_unit'::regclass",Boolean.class)).isTrue();
        assertThatThrownBy(()->admin().update("DELETE FROM public.hidra_topology_equipment_type WHERE id=?",p.kind())).isInstanceOf(RuntimeException.class);
        assertThat(jdbc.queryForObject("SELECT NOT has_table_privilege(?::name,'public.hidra_topology_equipment_type','UPDATE,DELETE')",Boolean.class,PUBLISHER)).isTrue();
        var absent=new InstalledUnit(id(),COMPANY,id(),u.manufacturerParty(),u.manufacturerModel(),u.serialNumber(),null);var c=register(absent);authorize(f,c);login(f);String before=effects();
        assertThatThrownBy(()->lifecycle.execute(c)).isInstanceOf(RuntimeException.class);assertThat(effects()).isEqualTo(before);
    }
    @Test void ordinaryRoleCannotMutatePrivateStorageAndAdmissionRejectsAmbientTransaction() {
        var f=fixture("LOCAL");var p=place();var u=unit(p);var c=register(u);authorize(f,c);login(f);String before=effects();
        assertThatThrownBy(()->jdbc.update("DELETE FROM public.hidra_topology_installed_unit WHERE false")).isInstanceOf(RuntimeException.class);
        assertThatThrownBy(()->new TransactionTemplate(manager).execute(s->lifecycle.execute(c))).isInstanceOf(RuntimeException.class);
        assertThat(effects()).isEqualTo(before);
    }
    @Test void nativeWriterFirstConflictRejectsNowaitWithoutUnitOrReceipt() throws Exception {
        var f=fixture("LOCAL");var p=place();var u=unit(p);var c=register(u);authorize(f,c);login(f);String before=effects();
        try(var connection=DriverManager.getConnection(POSTGRES.getJdbcUrl(),POSTGRES.getUsername(),POSTGRES.getPassword())) {
            connection.setAutoCommit(false);try(var statement=connection.createStatement()){statement.execute("LOCK TABLE public.hidra_topology_installed_unit IN ROW EXCLUSIVE MODE");}
            assertThatThrownBy(()->lifecycle.execute(c)).isInstanceOf(RuntimeException.class);connection.rollback();
        }
        assertThat(effects()).isEqualTo(before);
    }
    @Test void knownManufacturerRequiresCurrentRealPartyEvidence() {
        var f=fixture("LOCAL");var p=place();var unknown=unit(p);String type=id(),manufacturer=id(),role=id(),assignment=id(),profile=id();var a=admin();
        a.update("INSERT INTO public.hidra_party_type(id,code,status,created_at,updated_at) VALUES(?,?,'ACTIVE',now(),now())",type,type);
        a.update("INSERT INTO public.hidra_party_party(id,code,party_type_id,legal_name,country_code,status,created_at,updated_at) VALUES(?,?,?,'Manufacturer','DZ','ACTIVE',now(),now())",manufacturer,manufacturer,type);
        a.update("INSERT INTO public.hidra_party_role(id,code,qualification_required_by_default,status,created_at,updated_at) VALUES(?,'MANUFACTURER',true,'ACTIVE',now(),now())",role);
        a.update("INSERT INTO public.hidra_party_role_assignment(id,party_id,role_id,valid_from,status,qualification_required,created_at,updated_at) VALUES(?,?,?,now()-interval '1 hour','ACTIVE',true,now(),now())",assignment,manufacturer,role);
        a.update("INSERT INTO public.hidra_party_manufacturer_profile(id,party_id,capability_type,qualification_status,created_at,updated_at) VALUES(?,?,'EQUIPMENT','QUALIFIED',now(),now())",profile,manufacturer);
        var u=new InstalledUnit(unknown.id(),COMPANY,p.kind(),new InstalledUnit.Evidence(manufacturer,InstalledUnit.Verification.UNVERIFIED,"synthetic provenance"),InstalledUnit.Evidence.unknown(),InstalledUnit.Evidence.unknown(),null);
        execute(f,register(u));var read=read(u);authorize(f,read);login(f);String before=effects();a.update("UPDATE public.hidra_party_manufacturer_profile SET qualification_status='SUSPENDED' WHERE id=?",profile);
        assertThatThrownBy(()->lifecycle.execute(read)).isInstanceOf(RuntimeException.class);assertThat(effects()).isEqualTo(before);
    }
    @Test void disabledReceiptTriggerAndWrongPositionKindFailBeforeMutation() {
        var f=fixture("LOCAL");var p=place();var u=unit(p);var c=register(u);authorize(f,c);login(f);String before=effects();
        admin().execute("ALTER TABLE public.hidra_topology_installed_unit_operation_request DISABLE TRIGGER topology_installed_unit_request_guard");
        try{assertThatThrownBy(()->lifecycle.execute(c)).isInstanceOf(RuntimeException.class);}finally{admin().execute("ALTER TABLE public.hidra_topology_installed_unit_operation_request ENABLE TRIGGER topology_installed_unit_request_guard");}
        assertThat(effects()).isEqualTo(before);execute(f,c);var wrong=place();var record=record(u,wrong,id());authorize(f,record);login(f);before=effects();
        assertThatThrownBy(()->lifecycle.execute(record)).isInstanceOf(RuntimeException.class);assertThat(effects()).isEqualTo(before);
    }

    @Test void setBasedLockObservationRequiresEveryExactRelationAndMode() throws Exception {
        // Isolated native SQL proof; this administrative connection grants no lifecycle authority.
        var method=dz.sh.hidra.modules.topology.infrastructure.persistence.adapter.JdbcProtectedInstalledUnitRepositoryAdapter.class
            .getDeclaredMethod("held",List.class);method.setAccessible(true);
        var unit=new dz.sh.hidra.platform.persistence.ProtectedRelationFence("public.hidra_topology_installed_unit",dz.sh.hidra.platform.persistence.ProtectedRelationFence.Mode.ACCESS_SHARE);
        var installation=new dz.sh.hidra.platform.persistence.ProtectedRelationFence("public.hidra_topology_installed_unit_installation",dz.sh.hidra.platform.persistence.ProtectedRelationFence.Mode.ACCESS_SHARE);
        try(var connection=DriverManager.getConnection(POSTGRES.getJdbcUrl(),POSTGRES.getUsername(),POSTGRES.getPassword());var statement=connection.createStatement()) {
            connection.setAutoCommit(false);statement.execute("LOCK TABLE public.hidra_topology_installed_unit,public.hidra_topology_installed_unit_installation IN ACCESS SHARE MODE NOWAIT");
            try(var result=statement.executeQuery("SELECT "+method.invoke(null,List.of(unit,installation)))){assertThat(result.next()).isTrue();assertThat(result.getBoolean(1)).isTrue();}
            var wrongMode=new dz.sh.hidra.platform.persistence.ProtectedRelationFence(unit.relation(),dz.sh.hidra.platform.persistence.ProtectedRelationFence.Mode.SHARE_ROW_EXCLUSIVE);
            try(var result=statement.executeQuery("SELECT "+method.invoke(null,List.of(wrongMode,installation)))){assertThat(result.next()).isTrue();assertThat(result.getBoolean(1)).isFalse();}
            var missing=new dz.sh.hidra.platform.persistence.ProtectedRelationFence("public.hidra_topology_installed_unit_operation_request",dz.sh.hidra.platform.persistence.ProtectedRelationFence.Mode.SHARE_ROW_EXCLUSIVE);
            try(var result=statement.executeQuery("SELECT "+method.invoke(null,List.of(unit,installation,missing)))){assertThat(result.next()).isTrue();assertThat(result.getBoolean(1)).isFalse();}
            var duplicate=org.junit.jupiter.api.Assertions.assertThrows(java.lang.reflect.InvocationTargetException.class,()->method.invoke(null,List.of(unit,unit)));
            assertThat(duplicate.getCause()).isInstanceOf(IllegalStateException.class);connection.rollback();
        }
    }

    @Test void expiredFinalBoundRollsBackProvisionalRootAndReceipt() {
        var f=fixture("LOCAL");var p=place();var u=unit(p);var c=register(u);authorize(f,c);login(f);String before=effects();
        doAnswer(inv->{
            inv.callRealMethod();Object ticket=inv.getArgument(0);
            var origin=(dz.sh.hidra.modules.topology.application.port.out.ProtectedInstalledUnitRepositoryPort.Origin)ReflectionTestUtils.getField(ticket,"origin");
            ReflectionTestUtils.setField(ticket,"origin",new dz.sh.hidra.modules.topology.application.port.out.ProtectedInstalledUnitRepositoryPort.Origin(origin.actorId(),origin.bindingId(),origin.sessionId(),origin.sampledAt(),origin.sampledAt(),origin.backendPid(),origin.transactionId()));
            return null;
        }).when(protectedStore).freeze(any(),any(),anyList());
        try{assertThatThrownBy(()->lifecycle.execute(c)).isInstanceOf(RuntimeException.class);verify(protectedStore).finalGuard(any());}finally{reset(protectedStore);}
        assertThat(effects()).isEqualTo(before);
    }
    @Test void sqlAfterRealFinalGuardPoisonsCommitInsteadOfReturningSuccess() {
        var f=fixture("LOCAL");var p=place();var u=unit(p);var c=register(u);authorize(f,c);login(f);String before=effects();
        doAnswer(inv->{Object result=inv.callRealMethod();assertThat(result).isEqualTo(true);jdbc.queryForObject("SELECT 1",Integer.class);return result;}).when(protectedStore).finalGuard(any());
        try{assertThatThrownBy(()->lifecycle.execute(c)).isInstanceOf(RuntimeException.class);verify(protectedStore).finalGuard(any());}finally{reset(protectedStore);}
        assertThat(effects()).isEqualTo(before);
    }
    @Test void rolePrivilegeChangeAfterFreezeIsRejectedByFinalCatalogImage() {
        var f=fixture("LOCAL");var p=place();var u=unit(p);var c=register(u);authorize(f,c);login(f);String before=effects();var observedRejection=new AtomicBoolean();
        doAnswer(inv->{
            inv.callRealMethod();var resources=Map.copyOf(TransactionSynchronizationManager.getResourceMap());
            // External committed fault: raw JDBC must not enlist another resource in the protected transaction.
            try(var connection=DriverManager.getConnection(POSTGRES.getJdbcUrl(),POSTGRES.getUsername(),POSTGRES.getPassword());var statement=connection.createStatement()) {
                assertThat(connection.getAutoCommit()).isTrue();statement.execute("ALTER ROLE "+PUBLISHER+" BYPASSRLS");
            }
            assertThat(TransactionSynchronizationManager.getResourceMap()).isEqualTo(resources);return null;
        }).when(protectedStore).freeze(any(),any(),anyList());
        doAnswer(inv->{Object result=inv.callRealMethod();observedRejection.set(Boolean.FALSE.equals(result));return result;}).when(protectedStore).finalGuard(any());
        try {
            var rejection=org.junit.jupiter.api.Assertions.assertThrows(RuntimeException.class,()->lifecycle.execute(c));
            if(!observedRejection.get())throw new AssertionError("Expected rejection by the real final catalog guard; retaining earlier failure diagnostics",rejection);
            verify(protectedStore).finalGuard(any());
        }finally{reset(protectedStore);admin().execute("ALTER ROLE "+PUBLISHER+" NOBYPASSRLS");}
        assertThat(observedRejection.get()).isTrue();assertThat(effects()).isEqualTo(before);
    }
    private String unmountedKind() {
        String kind=id();admin().update("INSERT INTO public.hidra_topology_equipment_type(id,code,name,status,created_at,updated_at) VALUES(?,?,'Unmounted synthetic','ACTIVE',now(),now())",kind,kind);return kind;
    }
    private String rawUnitSql(){return "INSERT INTO public.hidra_topology_installed_unit(id,company_party_id,equipment_kind_id,manufacturer_party_verification,manufacturer_model_verification,serial_verification) VALUES(?,?,?,'UNKNOWN','UNKNOWN','UNKNOWN')";}
    @Test void nativeKindConstraintRejectsUnknownReferenceWith23503() throws Exception {
        try(var connection=DriverManager.getConnection(POSTGRES.getJdbcUrl(),POSTGRES.getUsername(),POSTGRES.getPassword());var statement=connection.prepareStatement(rawUnitSql())) {
            statement.setString(1,id());statement.setString(2,COMPANY);statement.setString(3,id());
            var failed=org.junit.jupiter.api.Assertions.assertThrows(SQLException.class,statement::executeUpdate);assertThat(failed.getSQLState()).isEqualTo("23503");
        }
    }
    @Test void unitFirstRetainsUnmountedKindUntilRollbackAndDeletionThenSucceeds() throws Exception {
        String kind=unmountedKind();
        try(var unit=DriverManager.getConnection(POSTGRES.getJdbcUrl(),POSTGRES.getUsername(),POSTGRES.getPassword());
            var deletion=DriverManager.getConnection(POSTGRES.getJdbcUrl(),POSTGRES.getUsername(),POSTGRES.getPassword())) {
            unit.setAutoCommit(false);deletion.setAutoCommit(false);
            try(var statement=unit.prepareStatement(rawUnitSql())){statement.setString(1,id());statement.setString(2,COMPANY);statement.setString(3,kind);statement.executeUpdate();}
            try(var timeout=deletion.createStatement()){timeout.execute("SET LOCAL lock_timeout='100ms'");}
            try(var statement=deletion.prepareStatement("DELETE FROM public.hidra_topology_equipment_type WHERE id=?")) {
                statement.setString(1,kind);var failed=org.junit.jupiter.api.Assertions.assertThrows(SQLException.class,statement::executeUpdate);assertThat(failed.getSQLState()).isEqualTo("55P03");
            }
            deletion.rollback();unit.rollback();
            try(var statement=deletion.prepareStatement("DELETE FROM public.hidra_topology_equipment_type WHERE id=?")){statement.setString(1,kind);assertThat(statement.executeUpdate()).isEqualTo(1);}deletion.rollback();
        }
    }
    @Test void deletionFirstCannotRaceProtectedRegistrationIntoDanglingKind() throws Exception {
        String kind=unmountedKind();var f=fixture("LOCAL");var u=new InstalledUnit(id(),COMPANY,kind,InstalledUnit.Evidence.unknown(),InstalledUnit.Evidence.unknown(),InstalledUnit.Evidence.unknown(),null);
        var c=register(u);authorize(f,c);login(f);String before=effects();
        try(var deletion=DriverManager.getConnection(POSTGRES.getJdbcUrl(),POSTGRES.getUsername(),POSTGRES.getPassword())) {
            deletion.setAutoCommit(false);try(var statement=deletion.prepareStatement("DELETE FROM public.hidra_topology_equipment_type WHERE id=?")){statement.setString(1,kind);statement.executeUpdate();}
            assertThatThrownBy(()->lifecycle.execute(c)).isInstanceOf(RuntimeException.class);deletion.rollback();
        }
        assertThat(effects()).isEqualTo(before);assertThat(execute(f,c).unit()).isEqualTo(u);
        assertThatThrownBy(()->admin().update("DELETE FROM public.hidra_topology_equipment_type WHERE id=?",kind)).isInstanceOf(RuntimeException.class);
    }

    @Test void currentCompanySuspensionDeniesAuthorizedReadWithoutLeakingPayload() {
        var f=fixture("LOCAL");var p=place();var u=unit(p);execute(f,register(u));var c=read(u);authorize(f,c);login(f);String before=effects();
        admin().update("UPDATE public.hidra_party_party SET status='SUSPENDED' WHERE id=?",COMPANY);
        try{assertThatThrownBy(()->lifecycle.execute(c)).isInstanceOf(RuntimeException.class).hasMessageNotContaining(u.id());}
        finally{admin().update("UPDATE public.hidra_party_party SET status='ACTIVE' WHERE id=?",COMPANY);}
        assertThat(effects()).isEqualTo(before);
    }
    @Test void missingNativeKindConstraintCannotBeSubstitutedByApplicationExistenceCheck() {
        var f=fixture("LOCAL");var p=place();var u=unit(p);var c=register(u);authorize(f,c);login(f);String before=effects();
        admin().execute("ALTER TABLE public.hidra_topology_installed_unit DROP CONSTRAINT fk_installed_unit_equipment_type");
        try{assertThatThrownBy(()->lifecycle.execute(c)).isInstanceOf(RuntimeException.class);}
        finally{admin().execute("ALTER TABLE public.hidra_topology_installed_unit ADD CONSTRAINT fk_installed_unit_equipment_type FOREIGN KEY(equipment_kind_id) REFERENCES public.hidra_topology_equipment_type(id) ON UPDATE RESTRICT ON DELETE RESTRICT NOT DEFERRABLE");}
        assertThat(effects()).isEqualTo(before);assertThat(execute(f,c).unit()).isEqualTo(u);
    }

    @Test void sourceGenerationChangeAfterFreezeCannotPassFinalGuard() {
        var f=fixture("LOCAL");var p=place();var u=unit(p);var c=register(u);authorize(f,c);login(f);String before=effects();var observedRejection=new AtomicBoolean();
        doAnswer(inv->{
            inv.callRealMethod();var resources=Map.copyOf(TransactionSynchronizationManager.getResourceMap());
            // External committed fault: raw JDBC must not enlist another resource in the protected transaction.
            try(var connection=DriverManager.getConnection(POSTGRES.getJdbcUrl(),POSTGRES.getUsername(),POSTGRES.getPassword());
                var statement=connection.prepareStatement("UPDATE public.hidra_party_eligibility_generation SET generation=generation+1 WHERE party_id=?")) {
                assertThat(connection.getAutoCommit()).isTrue();statement.setString(1,COMPANY);assertThat(statement.executeUpdate()).isEqualTo(1);
            }
            assertThat(TransactionSynchronizationManager.getResourceMap()).isEqualTo(resources);return null;
        }).when(protectedStore).freeze(any(),any(),anyList());
        doAnswer(inv->{Object result=inv.callRealMethod();observedRejection.set(Boolean.FALSE.equals(result));return result;}).when(protectedStore).finalGuard(any());
        try {
            var rejection=org.junit.jupiter.api.Assertions.assertThrows(RuntimeException.class,()->lifecycle.execute(c));
            if(!observedRejection.get())throw new AssertionError("Expected rejection by the real final source-generation guard; retaining earlier failure diagnostics",rejection);
            verify(protectedStore).finalGuard(any());
        }finally{reset(protectedStore);}
        assertThat(observedRejection.get()).isTrue();assertThat(effects()).isEqualTo(before);
    }

    @Test void foreignThreadTicketMisusePoisonsOriginalWriterDespiteSwallowedFailure() throws Exception {
        var f=fixture("LOCAL");var p=place();var u=unit(p);var c=register(u);authorize(f,c);login(f);String before=effects();var worker=Executors.newSingleThreadExecutor();
        doAnswer(inv->{
            var ticket=(dz.sh.hidra.modules.topology.application.service.ProtectedInstalledUnitAdmissionCoordinator.Admission)inv.getArgument(0);
            try{assertThat(worker.submit(()->{assertThatThrownBy(()->admissionCoordinator.perform(ticket)).isInstanceOf(RuntimeException.class);return true;}).get(5,TimeUnit.SECONDS)).isTrue();}
            catch(Exception failure){throw new AssertionError(failure);}return inv.callRealMethod();
        }).when(admissionCoordinator).prepareFinal(any());
        try{assertThatThrownBy(()->lifecycle.execute(c)).isInstanceOf(RuntimeException.class);verify(admissionCoordinator).prepareFinal(any());}
        finally{reset(admissionCoordinator);worker.shutdownNow();assertThat(worker.awaitTermination(5,TimeUnit.SECONDS)).isTrue();}
        assertThat(effects()).isEqualTo(before);
    }
    @Test void suspendedWriterCannotResumeWithEarlierOwnerTickets() {
        var f=fixture("LOCAL");var p=place();var u=unit(p);var c=register(u);authorize(f,c);login(f);String before=effects();
        doAnswer(inv->{
            var nested=new TransactionTemplate(manager);nested.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);
            try{nested.executeWithoutResult(status->{});}catch(RuntimeException expected){/* Intentionally swallowed; original must still reject. */}
            return inv.callRealMethod();
        }).when(admissionCoordinator).prepareFinal(any());
        try{assertThatThrownBy(()->lifecycle.execute(c)).isInstanceOf(RuntimeException.class);verify(admissionCoordinator).prepareFinal(any());}finally{reset(admissionCoordinator);}
        assertThat(effects()).isEqualTo(before);
    }
    @Test void swallowedSavepointControlCannotCommitEarlierProvisionalWrites() {
        var f=fixture("LOCAL");var p=place();var u=unit(p);var c=register(u);authorize(f,c);login(f);String before=effects();
        doAnswer(inv->{
            var connection=org.springframework.jdbc.datasource.DataSourceUtils.getConnection(jdbc.getDataSource());
            var rejected=org.junit.jupiter.api.Assertions.assertThrows(SQLException.class,connection::setSavepoint);
            assertThat(rejected.getSQLState()).isEqualTo("25000");
            assertThat(rejected.getMessage()).contains("savepoint/abort prohibited");
            return inv.callRealMethod();
        }).when(admissionCoordinator).prepareFinal(any());
        try{assertThatThrownBy(()->lifecycle.execute(c)).isInstanceOf(RuntimeException.class);verify(admissionCoordinator).prepareFinal(any());}finally{reset(admissionCoordinator);}
        assertThat(effects()).isEqualTo(before);
    }
}
