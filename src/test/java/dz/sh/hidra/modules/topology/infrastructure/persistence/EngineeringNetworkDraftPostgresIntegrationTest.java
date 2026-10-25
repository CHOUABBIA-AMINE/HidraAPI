/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : EngineeringNetworkDraftPostgresIntegrationTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-25
 *
 * @Type        : Class
 * @Layer       : Topology Test
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.infrastructure.persistence
 *
 * @Description : Verifies ordered real Identity and Party/Facility admission with one physical PostgreSQL sample.
 *
 */
package dz.sh.hidra.modules.topology.infrastructure.persistence;

import dz.sh.hidra.modules.topology.domain.model.EngineeringLocalExchange;
import dz.sh.hidra.modules.topology.domain.model.EngineeringLocalExchange.Editor;
import dz.sh.hidra.modules.topology.application.port.in.EngineeringLocalExchangeUseCase;
import dz.sh.hidra.modules.topology.application.model.EngineeringLocalExchangeFailure;


import dz.sh.hidra.modules.topology.domain.model.SurveyRouteProfile;
import dz.sh.hidra.modules.topology.application.port.in.SurveyRouteProfileUseCase;
import dz.sh.hidra.modules.topology.infrastructure.persistence.adapter.SurveyRouteProfileCodec;
import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import dz.sh.hidra.HidraApplication;
import dz.sh.hidra.modules.identity.application.contract.topology.TopologyProtectedAuthorizationContract;
import dz.sh.hidra.modules.identity.application.contract.topology.TopologyProtectedAuthorizationContract.Decision;
import dz.sh.hidra.modules.identity.application.contract.topology.TopologyScopedAccessContract.Operation;
import dz.sh.hidra.modules.identity.application.contract.topology.TopologyScopedAccessContract.Request;
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
import dz.sh.hidra.modules.topology.application.port.in.ProtectedStationGraphOperationUseCase;
import dz.sh.hidra.modules.topology.application.service.ProtectedStationAdmissionCoordinator;
import dz.sh.hidra.modules.topology.domain.model.StationInternalGraphRevision;

import org.flywaydb.core.Flyway;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.*;
import org.springframework.test.annotation.DirtiesContext;
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
import static dz.sh.hidra.modules.topology.application.port.in.ProtectedStationGraphOperationUseCase.Kind.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.*;

import javax.crypto.spec.SecretKeySpec;

/**
 * Only directory/source transport is synthetic; native crypto, origin, fences and owner state are
 * real.
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
@DirtiesContext(classMode=DirtiesContext.ClassMode.AFTER_CLASS)
@org.springframework.context.annotation.Import(EngineeringNetworkDraftPostgresIntegrationTest.Config.class)
public class EngineeringNetworkDraftPostgresIntegrationTest {
    @org.springframework.boot.test.context.TestConfiguration(proxyBeanMethods=false)
    static class Config {
        @org.springframework.context.annotation.Bean @org.springframework.context.annotation.Primary
        PlatformTransactionManager admissionFixtureManager(jakarta.persistence.EntityManagerFactory factory,javax.sql.DataSource ds) {
            return new dz.sh.hidra.platform.persistence.ProtectedJpaTransactionManager(factory,ds) {
                @Override protected void doBegin(Object transaction,TransactionDefinition definition) {
                    super.doBegin(transaction,definition);
                    var j=new JdbcTemplate(ds);
                    if(Boolean.TRUE.equals(j.queryForObject("SELECT session_user='c3_source_claim_app'",Boolean.class)))
                        j.queryForObject("SELECT pg_catalog.set_config('role','c3_source_claim_app',true)",String.class);
                }
            };
        }
    }
    @MockitoSpyBean dz.sh.hidra.modules.topology.application.service.ProtectedStationAdmissionCoordinator coordinator;
    @Autowired dz.sh.hidra.modules.topology.application.port.in.ProtectedStationGraphOperationUseCase graphOperations;
    @MockitoSpyBean dz.sh.hidra.modules.topology.infrastructure.persistence.adapter.JdbcStationGraphProtectedPublicationAdapter graphPublication;

    // Mockito setup/verification must not enter the mandatory-transaction interceptor.
    // Runtime operations still call graphPublication through its secured Spring proxy.
    private dz.sh.hidra.modules.topology.infrastructure.persistence.adapter.JdbcStationGraphProtectedPublicationAdapter graphTarget() {
        return org.springframework.test.util.AopTestUtils.getUltimateTargetObject(graphPublication);
    }

    @Autowired dz.sh.hidra.modules.party.application.contract.topology.TopologyProtectedPartyEligibilityContract partyOwner;
    @Autowired dz.sh.hidra.modules.topology.application.contract.station.StationProtectedFacilityOwnershipContract facilityOwner;
    @MockitoSpyBean dz.sh.hidra.modules.identity.infrastructure.persistence.adapter.JdbcIdentityTemporalEventCollector collector;

    @Autowired dz.sh.hidra.modules.topology.application.port.in.EngineeringNetworkDraftUseCase drafts;
    @MockitoSpyBean dz.sh.hidra.modules.topology.infrastructure.persistence.adapter.JdbcEngineeringNetworkDraftRepositoryAdapter draftStorage;
    @Autowired org.springframework.web.context.WebApplicationContext webContext;
    private org.springframework.test.web.servlet.MockMvc http;
    private String network;
    private String rawToken;
    private dz.sh.hidra.modules.topology.infrastructure.persistence.adapter.JdbcEngineeringNetworkDraftRepositoryAdapter draftTarget(){
        return org.springframework.test.util.AopTestUtils.getUltimateTargetObject(draftStorage);
    }
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
                    .withDatabaseName("hidra_paired_test")
                    .withCommand(
                            "postgres",
                            "-c",
                            "log_error_verbosity=terse",
                            "-c",
                            "track_commit_timestamp=on",
                            // This private disposable database tests explicit writers, not autovacuum races.
                            "-c",
                            "autovacuum=off",
                            "-c",
                            "log_min_error_statement=panic",
                            "-c",
                            "log_parameter_max_length_on_error=0");

    @DynamicPropertySource
    static void properties(DynamicPropertyRegistry r) {
        r.add("spring.datasource.url", POSTGRES::getJdbcUrl);
        POSTGRES.start();
        var admin=new JdbcTemplate(new org.springframework.jdbc.datasource.DriverManagerDataSource(
            POSTGRES.getJdbcUrl(),POSTGRES.getUsername(),POSTGRES.getPassword()));
        for(String role:List.of("c3_source_claim_app","c3_publication_app")) {
            if(!Boolean.TRUE.equals(admin.queryForObject("SELECT EXISTS(SELECT 1 FROM pg_catalog.pg_roles WHERE rolname=?)",Boolean.class,role)))
                admin.execute("CREATE ROLE "+role+" LOGIN PASSWORD '"+(role.equals("c3_publication_app")?"synthetic-publication-test-only":"synthetic-c3-test-only")+"'");
            admin.execute("GRANT USAGE ON SCHEMA public TO "+role);
            admin.execute("ALTER DEFAULT PRIVILEGES IN SCHEMA public GRANT SELECT,INSERT,UPDATE,DELETE ON TABLES TO "+role);
        }
        admin.execute("REVOKE CREATE ON SCHEMA public FROM PUBLIC");
        r.add("spring.datasource.username", ()->"c3_source_claim_app");
        r.add("spring.datasource.password", ()->"synthetic-c3-test-only");
        // A separate migration login needs an explicit URL; the runtime DataSource is guarded.
        r.add("spring.flyway.url",POSTGRES::getJdbcUrl);
        r.add("spring.flyway.user",POSTGRES::getUsername);
        r.add("spring.flyway.password",POSTGRES::getPassword);
        r.add("hidra.platform.persistence.publication.enabled",()->"true");
        r.add("hidra.platform.persistence.publication.username",()->"c3_publication_app");
        r.add("hidra.platform.persistence.publication.password",()->"synthetic-publication-test-only");
        r.add("spring.jpa.hibernate.ddl-auto", () -> "validate");
        r.add("spring.flyway.enabled", () -> "true");
    }

    @Autowired JdbcTemplate runtimeJdbc;
    @Autowired dz.sh.hidra.modules.topology.application.port.out.StationInternalGraphRevisionRepositoryPort legacyRevisions;
    private JdbcTemplate jdbc;
    private javax.sql.DataSource administrator;

    @MockitoSpyBean JdbcProtectedAuthorizationEvidenceReader reader;
    @Autowired jakarta.persistence.EntityManager em;
    @Autowired Flyway flyway;
    @MockitoSpyBean ProtectedOperationIdentityParticipant participant;

    @Autowired
    dz.sh.hidra.modules.identity.application.contract.topology
                    .TopologyProtectedAuthorizationContract
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
    // Keep immutable issuance history intact without growing it merely to repeat JVM warmup.
    // Every test still performs its own genuine paired warmup and all asserted operations below.
    private static boolean ownerPathsWarmed;

    @BeforeEach
    void initialize() {
        administrator=new org.springframework.jdbc.datasource.DriverManagerDataSource(
            POSTGRES.getJdbcUrl(),POSTGRES.getUsername(),POSTGRES.getPassword());
        // Fixture DDL/seeding uses the administrator explicitly. Transaction callbacks
        // obtain the genuine runtime resource key so denied SQL still poisons the executor.
        jdbc=new JdbcTemplate(administrator) {
            @Override public javax.sql.DataSource getDataSource() {
                return TransactionSynchronizationManager.isActualTransactionActive()?runtimeJdbc.getDataSource():administrator;
            }
        };
        for(String role:List.of("c3_source_claim_app","c3_publication_app")) {
            jdbc.execute("REVOKE ALL ON public.hidra_identity_external_mapping_generation,public.hidra_party_eligibility_generation,public.hidra_topology_facility_owner_generation FROM "+role);
            jdbc.execute("GRANT SELECT ON public.hidra_identity_external_mapping_generation,public.hidra_party_eligibility_generation,public.hidra_topology_facility_owner_generation TO "+role);
        }
        jdbc.execute("REVOKE ALL ON public.hidra_topology_station_graph_head,public.hidra_topology_station_graph_operation_event FROM c3_source_claim_app");
        jdbc.execute("GRANT SELECT ON public.hidra_topology_station_graph_head,public.hidra_topology_station_graph_operation_event TO c3_source_claim_app");
        assertThat(runtimeJdbc.queryForObject("SELECT session_user=current_user AND session_user='c3_source_claim_app'",Boolean.class)).isTrue();
        assertThat(org.springframework.aop.support.AopUtils.isAopProxy(graphPublication)).isTrue();
        assertThat(mockingDetails(graphTarget()).isSpy()).isTrue();
        assertThat(jdbc.queryForObject("SHOW autovacuum",String.class)).isEqualTo("off");
        String tables="public.hidra_topology_engineering_context,public.hidra_topology_engineering_draft,public.hidra_topology_engineering_draft_version";
        jdbc.execute("REVOKE ALL ON "+tables+" FROM c3_source_claim_app,c3_publication_app");
        jdbc.execute("GRANT SELECT ON "+tables+" TO c3_source_claim_app");
        jdbc.execute("GRANT SELECT,INSERT,UPDATE ON "+tables+" TO c3_publication_app");
        jdbc.execute("REVOKE ALL ON public.hidra_topology_engineering_survey_snapshot FROM c3_source_claim_app,c3_publication_app");
        jdbc.execute("GRANT SELECT,INSERT,UPDATE ON public.hidra_topology_engineering_survey_snapshot TO c3_publication_app");
        // Graph-only privilege: metadata retains its original 31-fence ownership path.
        jdbc.execute("REVOKE ALL ON public.hidra_topology_engineering_graph_snapshot FROM c3_source_claim_app,c3_publication_app");
        jdbc.execute("GRANT SELECT,INSERT,UPDATE ON public.hidra_topology_engineering_graph_snapshot TO c3_publication_app");
        SecurityContextHolder.clearContext();
        cleanEvidence();
        flyway.validate();
        assertThat(flyway.info().pending()).isEmpty();
        when(externalDecoder.decode(anyString()))
                .thenAnswer(i -> realExternal.decode(i.getArgument(0)));
        schema = "hapi581_" + id().replace("-", "");
        jdbc.execute("CREATE SCHEMA " + schema);
        jdbc.execute("CREATE TABLE " + receipt() + "(id integer PRIMARY KEY)");
        c3NonownerCapture();
        // Warm the full qualified pair/evaluator path outside the protected executor budget.
        var f = fixture("LOCAL");
        both(f, "author");
        var p = prepare(f);
        tx().executeWithoutResult(
                        s -> {
                            c3SelectRestrictedRole();
                            // The guard may insert a sentinel, but must not require row disclosure.
                            assertThat(jdbc.queryForObject(
                                    "SELECT has_table_privilege(current_user,?,'INSERT') AND NOT "
                                            + "has_column_privilege(current_user,?,'id','SELECT')",
                                    Boolean.class, receipt(), receipt())).isTrue();
                            paired.contribute(p, request());
                        });
        cleanEvidence();
        clearInvocations(readerTarget(), identityTarget());
        owners();
        // Warm the additional owner/provider paths once in this serial class, outside measured work.
        // Repeating setup per method unnecessarily consumes the real1024-row census.
        if(!ownerPathsWarmed)for(String type:List.of("LOCAL","OIDC")) {
            cleanEvidence();var warm=fixture(type);both(warm,"author");var prepared=originalSource(warm);
            tx().executeWithoutResult(status->{
                var identityAdmission=paired.acquire(prepared,request());
                var partyAdmission=partyOwner.acquire(COMPANY);var facilityAdmission=facilityOwner.acquire(FACILITY);
                assertThat(paired.contribute(identityAdmission).combinedDecision()).isEqualTo(Decision.PERMIT);
                assertThat(partyOwner.read(partyAdmission).partyId()).isEqualTo(COMPANY);
                assertThat(facilityOwner.read(facilityAdmission).ownerPartyId()).isEqualTo(COMPANY);
                paired.requireCurrent(identityAdmission);
            });
        }
        ownerPathsWarmed=true;
        cleanEvidence();clearInvocations(readerTarget(),identityTarget());
        network="network-"+id();
        jdbc.execute("INSERT INTO public.hidra_topology_pipeline_system_type(id,code,active,created_at,updated_at) VALUES('draft-type','draft-type',true,now(),now()) ON CONFLICT(id) DO UPDATE SET active=true");
        jdbc.update("INSERT INTO public.hidra_topology_pipeline_system(id,code,system_type_id,status,created_at,updated_at) VALUES(?,?,'draft-type','DRAFT',now(),now())",network,network);
        http=org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup(webContext)
            .apply(org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity()).build();

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
        return new Request(Operation.AUTHOR, COMPANY, FACILITY);
    }

    private TopologyProtectedAuthorizationContract.Prepared prepare(Fixture f) {
        ambient(authenticate(f).accessToken());
        return paired.prepare();
    }

    private TopologyProtectedAuthorizationContract.Prepared originalSource(Fixture f) {
        if (f.type().equals("LDAP") || f.type().equals("ACTIVE_DIRECTORY"))
            return c3DirectoryPrepared(f, "memberOf");
        return prepare(f);
    }

    private TopologyProtectedAuthorizationContract.Contribution run(
            TopologyProtectedAuthorizationContract.Prepared p, Request request) {
        return run(p, request, () -> {}, () -> {});
    }

    private TopologyProtectedAuthorizationContract.Contribution run(
            TopologyProtectedAuthorizationContract.Prepared p,
            Request request,
            Runnable inside,
            Runnable guardAction) {
        var saved = new AtomicReference<TopologyProtectedAuthorizationContract.Contribution>();
        return executor.execute(
                () -> {
                    c3SelectRestrictedRole();
                    var result = paired.contribute(p, request);
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
    }

    private void permit(TopologyProtectedAuthorizationContract.Contribution c) {
        assertThat(c.companyDecision()).isEqualTo(Decision.PERMIT);
        assertThat(c.facilityDecision()).isEqualTo(Decision.PERMIT);
        assertThat(c.combinedDecision()).isEqualTo(Decision.PERMIT);
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
    private static final List<String> OPS =
            List.of(
                    "author",
                    "publish",
                    "read-current",
                    "read-history",
                    "list-history",
                    "withdraw",
                    "reactivate",
                    "transfer");
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
        String code = "topology:station-revision:" + op;
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
                        + " (?,?,'topology','STATION_GRAPH_REVISION',?,false,'ACTIVE',clock_timestamp(),clock_timestamp())",
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
        grant(f, op, "GRANT", "FACILITY", FACILITY);
    }

    private AuthorizationEvaluationRequest request(Fixture f, String op, boolean company) {
        return new AuthorizationEvaluationRequest(
                f.user(),
                "topology:station-revision:" + op,
                "STATION_GRAPH_REVISION",
                FACILITY,
                new AuthorizationScope(
                        company ? ScopeType.CUSTOM : ScopeType.FACILITY,
                        company ? "topology-company:" + COMPANY : FACILITY,
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
        String role = "c3_source_claim_app";
        if (!Boolean.TRUE.equals(
                jdbc.queryForObject(
                        "SELECT EXISTS(SELECT 1 FROM pg_roles WHERE rolname=?)",
                        Boolean.class,
                        role))) {
            jdbc.execute("CREATE ROLE " + role + " LOGIN PASSWORD 'synthetic-c3-test-only'");
            jdbc.execute("REVOKE CREATE ON SCHEMA public FROM PUBLIC");
            jdbc.execute("GRANT USAGE ON SCHEMA public TO " + role);
            jdbc.execute(
                    "GRANT SELECT,INSERT,UPDATE,DELETE ON ALL TABLES IN SCHEMA public TO " + role);
            jdbc.execute(
                    "REVOKE ALL ON public.hidra_identity_external_mapping_generation FROM " + role);
            jdbc.execute(
                    "GRANT SELECT ON public.hidra_identity_external_mapping_generation TO " + role);
        }
        var appJdbc =
                new JdbcTemplate(
                        new org.springframework.jdbc.datasource.DriverManagerDataSource(
                                POSTGRES.getJdbcUrl(), role, "synthetic-c3-test-only"));
        var appCatalog =
                new dz.sh.hidra.modules.identity.infrastructure.persistence.adapter
                        .JdbcClaimNameCatalogAdapter(appJdbc);
        // Authentication's source-name hint is selected on a real non-owner connection.
        // During the issuance fence the identical selected generation must be rechecked
        // on the native physical transaction, after taking the non-owner identity.
        doAnswer(
                        i -> {
                            if (TransactionSynchronizationManager.isActualTransactionActive()
                                    && Boolean.TRUE.equals(
                                            jdbc.queryForObject(
                                                    "SELECT current_user=?", Boolean.class, role)))
                                return i.callRealMethod();
                            return appCatalog.selectVersioned(i.getArgument(0));
                        })
                .when(claimCatalog)
                .selectVersioned(anyString());
        new TransactionTemplate(manager)
                .executeWithoutResult(
                        status ->
                                doAnswer(
                                                i -> {
                                                    c3SelectRestrictedRole();
                                                    return i.callRealMethod();
                                                })
                                        .when(claimIssuanceFence)
                                        .prepare(anyString(), any()));
        // The final guarded fixture insert must also be performed without owner rights.
        jdbc.execute("GRANT USAGE ON SCHEMA " + schema + " TO " + role);
        jdbc.execute("GRANT INSERT ON " + receipt() + " TO " + role);
        ReflectionTestUtils.setField(oidc, "verifiedClaimReceiptEnabled", true);
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

    private TopologyProtectedAuthorizationContract.Prepared c3DirectoryPrepared(
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

    private void owners() {
        jdbc.execute("REVOKE ALL ON public.hidra_party_eligibility_generation,public.hidra_topology_facility_owner_generation FROM c3_source_claim_app");
        jdbc.execute("GRANT SELECT ON public.hidra_party_eligibility_generation,public.hidra_topology_facility_owner_generation TO c3_source_claim_app");
        jdbc.execute("INSERT INTO public.hidra_party_type(id,code,status,created_at,updated_at) VALUES('k-party-type','k-party-type','ACTIVE',now(),now()) ON CONFLICT DO NOTHING");
        jdbc.execute("INSERT INTO public.hidra_topology_facility_type(id,code,name,facility_kind,status,created_at,updated_at) VALUES('k-facility-type','k-facility-type','Synthetic','STATION','ACTIVE',now(),now()) ON CONFLICT DO NOTHING");
        jdbc.update("INSERT INTO public.hidra_party_party(id,code,party_type_id,legal_name,country_code,status,created_at,updated_at) VALUES(?,?,'k-party-type','Synthetic','DZ','ACTIVE',now(),now()) ON CONFLICT(id) DO UPDATE SET status='ACTIVE'",COMPANY,COMPANY);
        jdbc.update("INSERT INTO public.hidra_topology_facility(id,code,facility_type_id,facility_kind,owner_party_id,status,created_at,updated_at) VALUES(?,?,'k-facility-type','STATION',?,'ACTIVE',now(),now()) ON CONFLICT(id) DO UPDATE SET owner_party_id=EXCLUDED.owner_party_id",FACILITY,FACILITY,COMPANY);
    }

    private Fixture draftActor(String type){
        cleanEvidence();var f=fixture(type);
        for(String op:List.of("create","read","update","validate")){
            String code="topology:engineering-draft:"+op;String permission="draft-permission-"+op;
            jdbc.update("INSERT INTO public.hidra_identity_permission(id,code,permission_domain,resource_type,action,sensitive,status,created_at,updated_at) VALUES(?,?,'topology','ENGINEERING_NETWORK_DRAFT',?,false,'ACTIVE',now(),now())",permission,code,op);
            for(String scope:List.of("topology-company:"+COMPANY,"topology-network:"+network))jdbc.update(
                "INSERT INTO public.hidra_identity_user_permission_grant(id,user_id,permission_id,effect,scope_type,scope_reference_id,grant_reason,emergency_access,valid_from,valid_to,status,created_at) VALUES(?,?,?,'GRANT','CUSTOM',?,'synthetic draft evidence',false,now()-interval '1 hour',now()+interval '1 hour','ACTIVE',now())",id(),f.user(),permission,scope);
        }
        rawToken=authenticate(f).accessToken();ambient(rawToken);return f;
    }
    private dz.sh.hidra.modules.topology.application.port.in.EngineeringNetworkDraftUseCase.Command command(
            dz.sh.hidra.modules.topology.application.port.in.EngineeringNetworkDraftUseCase.Kind kind,UUID draft,UUID request,Long version,String title){
        return new dz.sh.hidra.modules.topology.application.port.in.EngineeringNetworkDraftUseCase.Command(kind,COMPANY,network,draft,request,version,
            kind==dz.sh.hidra.modules.topology.application.port.in.EngineeringNetworkDraftUseCase.Kind.CREATE || kind==dz.sh.hidra.modules.topology.application.port.in.EngineeringNetworkDraftUseCase.Kind.UPDATE
                ?new dz.sh.hidra.modules.topology.domain.model.EngineeringNetworkDraft.Metadata(title,null,null):null);
    }
    private dz.sh.hidra.modules.topology.application.port.in.EngineeringNetworkDraftUseCase.Result create(UUID request,String title){
        return drafts.execute(command(dz.sh.hidra.modules.topology.application.port.in.EngineeringNetworkDraftUseCase.Kind.CREATE,null,request,null,title));
    }
    @Test void realNativeProvidersCreateReadUpdateAndValidateWithoutPublishing(){
        for(String type:List.of("LOCAL","OIDC","LDAP","ACTIVE_DIRECTORY")){
            var actor=draftActor(type);var first=create(UUID.randomUUID(),"initial");var id=first.draft().draftId();
            var next=drafts.execute(command(dz.sh.hidra.modules.topology.application.port.in.EngineeringNetworkDraftUseCase.Kind.UPDATE,id,null,1L,"next"));assertThat(next.draft().version()).isEqualTo(2);
            var current=drafts.execute(command(dz.sh.hidra.modules.topology.application.port.in.EngineeringNetworkDraftUseCase.Kind.READ,id,null,null,null));assertThat(current.draft()).isEqualTo(next.draft());
            var validated=drafts.execute(command(dz.sh.hidra.modules.topology.application.port.in.EngineeringNetworkDraftUseCase.Kind.VALIDATE,id,null,2L,null));assertThat(validated.diagnostics()).contains("GRAPH_NOT_AUTHORED");
            var authored=graphOwner().executeGraph(graphUpdate(id,2L,stationlessSerialParallelGraph()));
            assertThat(authored.version()).isEqualTo(3L);
            assertThat(graphOwner().executeGraph(graphRead(id)).graph()).isEqualTo(stationlessSerialParallelGraph());
            jdbc.update("UPDATE public.hidra_identity_user_permission_grant SET effect='DENY' WHERE user_id=? AND permission_id='draft-permission-update' AND scope_reference_id=?",
                actor.user(),"topology-company:"+COMPANY);
            assertThatThrownBy(()->graphOwner().executeGraph(graphUpdate(id,3L,stationlessSerialParallelGraph())))
                .isInstanceOf(dz.sh.hidra.modules.topology.application.model.EngineeringNetworkDraftFailure.class);
            assertThat(jdbc.queryForObject("SELECT max(version) FROM public.hidra_topology_engineering_draft_version WHERE draft_id=?",Long.class,id)).isEqualTo(3L);

        }
    }
    @Test void originalCreationReplayDoesNotRevertLaterMetadata(){
        draftActor("LOCAL");var request=UUID.randomUUID();var first=create(request,"initial");
        drafts.execute(command(dz.sh.hidra.modules.topology.application.port.in.EngineeringNetworkDraftUseCase.Kind.UPDATE,first.draft().draftId(),null,1L,"edited"));
        var replay=create(request,"initial");assertThat(replay.replayed()).isTrue();assertThat(replay.draft()).isEqualTo(first.draft());
        assertThat(jdbc.queryForObject("SELECT max(version) FROM public.hidra_topology_engineering_draft_version WHERE draft_id=?",Long.class,first.draft().draftId())).isEqualTo(2);
        assertThatThrownBy(()->create(request,"different")).isInstanceOf(dz.sh.hidra.modules.topology.application.model.EngineeringNetworkDraftFailure.class);
    }
    @Test void samePairReusesOneContextForIndependentCandidates(){
        draftActor("LOCAL");var a=create(UUID.randomUUID(),null);var b=create(UUID.randomUUID(),null);
        assertThat(a.draft().contextId()).isEqualTo(b.draft().contextId());assertThat(a.draft().draftId()).isNotEqualTo(b.draft().draftId());
    }
    @Test void staleVersionNeverAppendsOrOverwrites(){
        draftActor("LOCAL");var a=create(UUID.randomUUID(),"first");var update=command(dz.sh.hidra.modules.topology.application.port.in.EngineeringNetworkDraftUseCase.Kind.UPDATE,a.draft().draftId(),null,1L,"next");
        drafts.execute(update);assertThatThrownBy(()->drafts.execute(update)).isInstanceOf(dz.sh.hidra.modules.topology.application.model.EngineeringNetworkDraftFailure.class);
        assertThat(jdbc.queryForObject("SELECT count(*) FROM public.hidra_topology_engineering_draft_version WHERE draft_id=?",Long.class,a.draft().draftId())).isEqualTo(2);
    }
    @Test void actualPublisherFenceWorksWhileStatementTriggersRejectUpdates() throws Exception {
        draftActor("LOCAL");var a=create(UUID.randomUUID(),"immutable");
        try(var c=DriverManager.getConnection(POSTGRES.getJdbcUrl(),"c3_publication_app","synthetic-publication-test-only")){
            c.setAutoCommit(false);try(var st=c.createStatement()){
                st.execute("LOCK TABLE public.hidra_topology_engineering_context,public.hidra_topology_engineering_draft,public.hidra_topology_engineering_draft_version IN SHARE ROW EXCLUSIVE MODE NOWAIT");c.rollback();
                SQLException failure=org.junit.jupiter.api.Assertions.assertThrows(SQLException.class,()->st.execute("UPDATE public.hidra_topology_engineering_context SET company_party_id='forged'"));
                assertThat(failure.getSQLState()).isEqualTo("23514");c.rollback();
            }
        }
        assertThat(jdbc.queryForObject("SELECT company_party_id FROM public.hidra_topology_engineering_context WHERE id=?",String.class,a.draft().contextId())).isEqualTo(COMPANY);
    }
    @Test void ordinaryDirectDmlAndDdlRemainDenied() throws Exception {
        draftActor("LOCAL");create(UUID.randomUUID(),null);
        for(String sql:List.of("UPDATE public.hidra_topology_engineering_context SET company_party_id='x'","TRUNCATE public.hidra_topology_engineering_context","ALTER TABLE public.hidra_topology_engineering_context DISABLE TRIGGER ALL")){
            try(var c=DriverManager.getConnection(POSTGRES.getJdbcUrl(),"c3_source_claim_app","synthetic-c3-test-only");var st=c.createStatement()){
                SQLException e=org.junit.jupiter.api.Assertions.assertThrows(SQLException.class,()->st.execute(sql));assertThat(e.getSQLState()).isEqualTo("42501");
            }
        }
    }
    @Test void realHttpBearerRoundTripAndSafeUnknownFieldRejection() throws Exception {
        draftActor("LOCAL");String body="{\"companyPartyId\":\""+COMPANY+"\",\"pipelineSystemId\":\""+network+"\",\"creationRequestId\":\""+UUID.randomUUID()+"\"}";
        // Do not use the original synthetic ambient wrapper as an HTTP credential.
        // The real Spring Security filter must decode and authenticate the native JWT.
        assertThat(nativeDecoder.decode(rawToken).getTokenValue()).isEqualTo(rawToken);
        SecurityContextHolder.clearContext();
        var actualHttp=http.perform(org.springframework.test.web.servlet.request.MockMvcRequestBuilders
            .post("/api/v1/topology/engineering-networks").header("Authorization","Bearer "+rawToken)
            .contentType(org.springframework.http.MediaType.APPLICATION_JSON).content(body)).andReturn();
        assertThat(actualHttp.getResponse().getStatus())
            .as("Actual bearer status; response=%s, resolvedException=%s",
                actualHttp.getResponse().getContentAsString(),
                actualHttp.getResolvedException()==null?"none":actualHttp.getResolvedException().getClass().getName())
            .isEqualTo(201);
        http.perform(org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post("/api/v1/topology/engineering-networks").header("Authorization","Bearer "+rawToken).contentType(org.springframework.http.MediaType.APPLICATION_JSON).content(body.substring(0,body.length()-1)+",\"graph\":{}}"))
            .andExpect(org.springframework.test.web.servlet.result.MockMvcResultMatchers.status().isBadRequest());
    }
    @Test void signedBearerGraphVersionAppendAndHistoricalAsOfAreDraftOnly() throws Exception {
        draftActor("LOCAL");
        var original=create(UUID.randomUUID(),"graph-round-trip");
        var id=original.draft().draftId();
        String route="/api/v1/topology/engineering-networks/"+id+"/graph?companyPartyId="+COMPANY+"&pipelineSystemId="+network;
        String graph="{\"expectedVersion\":1,\"nodes\":["
            +"{\"id\":\"a\",\"kind\":\"JUNCTION\"},"
            +"{\"id\":\"b\",\"kind\":\"JUNCTION\"}],"
            +"\"ports\":[{\"id\":\"out\",\"nodeId\":\"a\",\"direction\":\"OUTLET\"},"
            +"{\"id\":\"in\",\"nodeId\":\"b\",\"direction\":\"INLET\"}],"
            +"\"branches\":[{\"id\":\"pipe\",\"kind\":\"PIPE\","
            +"\"fromPortId\":\"out\",\"toPortId\":\"in\"}]}";
        assertThat(nativeDecoder.decode(rawToken).getTokenValue()).isEqualTo(rawToken);
        SecurityContextHolder.clearContext();
        http.perform(org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put(route)
            .header("Authorization","Bearer "+rawToken)
            .contentType(org.springframework.http.MediaType.APPLICATION_JSON).content(graph))
            .andExpect(org.springframework.test.web.servlet.result.MockMvcResultMatchers.status().isOk())
            .andExpect(org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath("$.version").value(2))
            .andExpect(org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath("$.graphVersion").value(2))
            .andExpect(org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath("$.branches[0].id").value("pipe"));
        http.perform(org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get(route+"&version=1")
            .header("Authorization","Bearer "+rawToken))
            .andExpect(org.springframework.test.web.servlet.result.MockMvcResultMatchers.status().isOk())
            .andExpect(org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath("$.version").value(1))
            .andExpect(org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath("$.diagnostics[0]").value("GRAPH_NOT_AUTHORED"));
        http.perform(org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get(route+"&version=2")
            .header("Authorization","Bearer "+rawToken))
            .andExpect(org.springframework.test.web.servlet.result.MockMvcResultMatchers.status().isOk())
            .andExpect(org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath("$.graphVersion").value(2));
        assertThat(jdbc.queryForObject("SELECT count(*) FROM public.hidra_topology_engineering_graph_snapshot WHERE draft_id=?",Long.class,id)).isEqualTo(1);
        assertThat(jdbc.queryForObject("SELECT max(version) FROM public.hidra_topology_engineering_draft_version WHERE draft_id=?",Long.class,id)).isEqualTo(2);
    }
    @Test void ordinaryGraphSnapshotPrivilegesRemainDenied() throws Exception {
        draftActor("LOCAL");
        try(var c=DriverManager.getConnection(POSTGRES.getJdbcUrl(),"c3_source_claim_app","synthetic-c3-test-only");
            var stmt=c.createStatement()) {
            for(String query : java.util.List.of(
                    "SELECT * FROM public.hidra_topology_engineering_graph_snapshot LIMIT 1",
                    "TRUNCATE public.hidra_topology_engineering_graph_snapshot",
                    "DELETE FROM public.hidra_topology_engineering_graph_snapshot WHERE false")) {
                var forbidden=org.junit.jupiter.api.Assertions.assertThrows(SQLException.class,
                    ()->stmt.execute(query));
                assertThat(forbidden.getSQLState()).isEqualTo("42501");
            }
        }
    }

    @Test void graphSurvivesMetadataOnlySuccessorWithHistoricalSha() {
        draftActor("LOCAL");var first=create(UUID.randomUUID(),"before");
        var id=first.draft().draftId();
        var g=new dz.sh.hidra.modules.topology.domain.model.EngineeringGraphDraft(List.of(),List.of(),List.of());
        var owner=(dz.sh.hidra.modules.topology.application.port.in.EngineeringGraphDraftUseCase)drafts;
        var write=new dz.sh.hidra.modules.topology.application.port.in.EngineeringGraphDraftUseCase.Command(
            dz.sh.hidra.modules.topology.application.port.in.EngineeringGraphDraftUseCase.Kind.UPDATE,
            COMPANY,network,id,1L,null,g);
        var snap=owner.executeGraph(write);
        drafts.execute(command(dz.sh.hidra.modules.topology.application.port.in.EngineeringNetworkDraftUseCase.Kind.UPDATE,
            id,null,2L,"after"));
        var query=new dz.sh.hidra.modules.topology.application.port.in.EngineeringGraphDraftUseCase.Command(
            dz.sh.hidra.modules.topology.application.port.in.EngineeringGraphDraftUseCase.Kind.READ,
            COMPANY,network,id,null,null,null);
        var current=owner.executeGraph(query);
        assertThat(current.version()).isEqualTo(3L);
        assertThat(current.graphVersion()).isEqualTo(2L);
        assertThat(current.graphSha256()).isEqualTo(snap.graphSha256());
        assertThat(jdbc.queryForObject("SELECT count(*) FROM public.hidra_topology_engineering_graph_snapshot WHERE draft_id=?",
            Long.class,id)).isEqualTo(1L);
    }
    @Test void graphCurrentStationWrongKindAndStatusFailClosed() {
        draftActor("LOCAL");owners();var first=create(UUID.randomUUID(),"station");
        var g=new dz.sh.hidra.modules.topology.domain.model.EngineeringGraphDraft(
            List.of(new dz.sh.hidra.modules.topology.domain.model.EngineeringGraphDraft.Node(
                "station",dz.sh.hidra.modules.topology.domain.model.EngineeringGraphDraft.NodeKind.STATION,FACILITY)),
            List.of(),List.of());
        var owner=(dz.sh.hidra.modules.topology.application.port.in.EngineeringGraphDraftUseCase)drafts;
        var write=new dz.sh.hidra.modules.topology.application.port.in.EngineeringGraphDraftUseCase.Command(
            dz.sh.hidra.modules.topology.application.port.in.EngineeringGraphDraftUseCase.Kind.UPDATE,
            COMPANY,network,first.draft().draftId(),1L,null,g);
        jdbc.update("UPDATE public.hidra_topology_facility SET facility_kind='DEPOT' WHERE id=?",FACILITY);
        try {assertThatThrownBy(()->owner.executeGraph(write))
            .isInstanceOf(dz.sh.hidra.modules.topology.application.model.EngineeringNetworkDraftFailure.class);}
        finally {jdbc.update("UPDATE public.hidra_topology_facility SET facility_kind='STATION' WHERE id=?",FACILITY);}
        jdbc.update("UPDATE public.hidra_topology_facility SET status='INACTIVE' WHERE id=?",FACILITY);
        try {assertThatThrownBy(()->owner.executeGraph(write))
            .isInstanceOf(dz.sh.hidra.modules.topology.application.model.EngineeringNetworkDraftFailure.class);}
        finally {jdbc.update("UPDATE public.hidra_topology_facility SET status='ACTIVE' WHERE id=?",FACILITY);}
        assertThat(jdbc.queryForObject("SELECT count(*) FROM public.hidra_topology_engineering_graph_snapshot WHERE draft_id=?",
            Long.class,first.draft().draftId())).isZero();
    }
    @Test void revokedGraphWriteDoesNotCreateASecondMetadataVersion() {
        var actor=draftActor("LOCAL");var first=create(UUID.randomUUID(),"revoked");
        jdbc.update("UPDATE public.hidra_identity_user_permission_grant SET status='REVOKED',revoked_at=now() WHERE user_id=? AND permission_id='draft-permission-update'",actor.user());
        var owner=(dz.sh.hidra.modules.topology.application.port.in.EngineeringGraphDraftUseCase)drafts;
        var g=new dz.sh.hidra.modules.topology.domain.model.EngineeringGraphDraft(List.of(),List.of(),List.of());
        var cmd=new dz.sh.hidra.modules.topology.application.port.in.EngineeringGraphDraftUseCase.Command(
            dz.sh.hidra.modules.topology.application.port.in.EngineeringGraphDraftUseCase.Kind.UPDATE,
            COMPANY,network,first.draft().draftId(),1L,null,g);
        assertThatThrownBy(()->owner.executeGraph(cmd))
            .isInstanceOf(dz.sh.hidra.modules.topology.application.model.EngineeringNetworkDraftFailure.class);
        assertThat(jdbc.queryForObject("SELECT count(*) FROM public.hidra_topology_engineering_draft_version WHERE draft_id=?",
            Long.class,first.draft().draftId())).isEqualTo(1L);
    }
    @Test void twoConcurrentSameVersionGraphWritersCommitExactlyOneSuccessor() throws Exception {
        draftActor("LOCAL");var first=create(UUID.randomUUID(),"concurrent");
        var auth=SecurityContextHolder.getContext().getAuthentication();
        var owner=(dz.sh.hidra.modules.topology.application.port.in.EngineeringGraphDraftUseCase)drafts;
        var g=new dz.sh.hidra.modules.topology.domain.model.EngineeringGraphDraft(List.of(),List.of(),List.of());
        var cmd=new dz.sh.hidra.modules.topology.application.port.in.EngineeringGraphDraftUseCase.Command(
            dz.sh.hidra.modules.topology.application.port.in.EngineeringGraphDraftUseCase.Kind.UPDATE,
            COMPANY,network,first.draft().draftId(),1L,null,g);
        var latch=new CountDownLatch(1);var pool=Executors.newFixedThreadPool(2);var count=new AtomicInteger();
        try {
            var jobs=new ArrayList<Future<?>>();
            for(int i=0;i<2;i++)jobs.add(pool.submit(()->{
                SecurityContextHolder.getContext().setAuthentication(auth);
                try {latch.await();owner.executeGraph(cmd);count.incrementAndGet();}
                catch(dz.sh.hidra.modules.topology.application.model.EngineeringNetworkDraftFailure expected){}
                catch(InterruptedException e){Thread.currentThread().interrupt();throw new AssertionError(e);}
                finally {SecurityContextHolder.clearContext();}
            }));
            latch.countDown();for(var j:jobs)j.get(10,TimeUnit.SECONDS);
        }finally{pool.shutdownNow();}
        assertThat(count.get()).isEqualTo(1);
        assertThat(jdbc.queryForObject("SELECT count(*) FROM public.hidra_topology_engineering_graph_snapshot WHERE draft_id=?",
            Long.class,first.draft().draftId())).isEqualTo(1L);
    }
    @Test void graphFinalGuardContainsThirtyFourRelationFences() {
        draftActor("LOCAL");var first=create(UUID.randomUUID(),"guard");
        var owner=(dz.sh.hidra.modules.topology.application.port.in.EngineeringGraphDraftUseCase)drafts;
        var observed=new AtomicBoolean();
        doAnswer(i->{java.util.List<dz.sh.hidra.platform.persistence.ProtectedRelationFence> f=i.getArgument(2);
            assertThat(f).hasSize(34);
            assertThat(f.stream().filter(x->x.mode()==dz.sh.hidra.platform.persistence.ProtectedRelationFence.Mode.SHARE_ROW_EXCLUSIVE).count()).isEqualTo(30L);
            assertThat(f.stream().filter(x->x.mode()==dz.sh.hidra.platform.persistence.ProtectedRelationFence.Mode.ACCESS_SHARE).count()).isEqualTo(3L);
            assertThat(f).contains(
                new dz.sh.hidra.platform.persistence.ProtectedRelationFence("public.hidra_topology_facility",
                    dz.sh.hidra.platform.persistence.ProtectedRelationFence.Mode.SHARE_ROW_EXCLUSIVE),
                new dz.sh.hidra.platform.persistence.ProtectedRelationFence("public.hidra_topology_facility_owner_generation",
                    dz.sh.hidra.platform.persistence.ProtectedRelationFence.Mode.ACCESS_SHARE));
            observed.set(true);return i.callRealMethod();})
            .when(draftTarget()).finalGuard(any(),any(),anyList());
        owner.executeGraph(new dz.sh.hidra.modules.topology.application.port.in.EngineeringGraphDraftUseCase.Command(
            dz.sh.hidra.modules.topology.application.port.in.EngineeringGraphDraftUseCase.Kind.UPDATE,
            COMPANY,network,first.draft().draftId(),1L,null,
            new dz.sh.hidra.modules.topology.domain.model.EngineeringGraphDraft(List.of(),List.of(),List.of())));
        assertThat(observed).isTrue();
    }
    @Test void graphStationRoundTripRequiresReadOnlyGenerationAuthority() {
        draftActor("LOCAL");owners();var first=create(UUID.randomUUID(),"read-only station witness");
        var graph=new dz.sh.hidra.modules.topology.domain.model.EngineeringGraphDraft(
            List.of(new dz.sh.hidra.modules.topology.domain.model.EngineeringGraphDraft.Node(
                "station",dz.sh.hidra.modules.topology.domain.model.EngineeringGraphDraft.NodeKind.STATION,FACILITY)),
            List.of(),List.of());
        var owner=(dz.sh.hidra.modules.topology.application.port.in.EngineeringGraphDraftUseCase)drafts;
        var before=jdbc.queryForObject("SELECT generation FROM public.hidra_topology_facility_owner_generation WHERE facility_id=?",
            Long.class,FACILITY);
        assertThat(jdbc.queryForObject("SELECT has_table_privilege('c3_publication_app',"
            +"'public.hidra_topology_facility_owner_generation','INSERT,UPDATE,DELETE,TRUNCATE,REFERENCES,TRIGGER')",
            Boolean.class)).isFalse();
        var written=owner.executeGraph(new dz.sh.hidra.modules.topology.application.port.in.EngineeringGraphDraftUseCase.Command(
            dz.sh.hidra.modules.topology.application.port.in.EngineeringGraphDraftUseCase.Kind.UPDATE,
            COMPANY,network,first.draft().draftId(),1L,null,graph));
        var read=new dz.sh.hidra.modules.topology.application.port.in.EngineeringGraphDraftUseCase.Command(
            dz.sh.hidra.modules.topology.application.port.in.EngineeringGraphDraftUseCase.Kind.READ,
            COMPANY,network,first.draft().draftId(),null,null,null);
        assertThat(written.graph()).isEqualTo(graph);
        assertThat(owner.executeGraph(read).graphSha256()).isEqualTo(written.graphSha256());
        assertThat(jdbc.queryForObject("SELECT generation FROM public.hidra_topology_facility_owner_generation WHERE facility_id=?",
            Long.class,FACILITY)).isEqualTo(before);
        // A wider grant invalidates the read-only owner witness contract, rather than fixing LOCK.
        jdbc.execute("GRANT UPDATE ON public.hidra_topology_facility_owner_generation TO c3_publication_app");
        try {assertThatThrownBy(()->owner.executeGraph(read))
            .isInstanceOf(dz.sh.hidra.modules.topology.application.model.EngineeringNetworkDraftFailure.class);}
        finally {jdbc.execute("REVOKE UPDATE ON public.hidra_topology_facility_owner_generation FROM c3_publication_app");}
        assertThat(owner.executeGraph(read).graphSha256()).isEqualTo(written.graphSha256());
    }

    @Test void disabledActualCatalogTriggerFailsBeforeAnyDraftInsert(){
        draftActor("LOCAL");UUID request=UUID.randomUUID();
        jdbc.execute("ALTER TABLE public.hidra_topology_engineering_context DISABLE TRIGGER p25_engineering_context_immutable");
        try {assertThatThrownBy(()->create(request,"rollback")).isInstanceOf(dz.sh.hidra.modules.topology.application.model.EngineeringNetworkDraftFailure.class);
            assertThat(jdbc.queryForObject("SELECT count(*) FROM public.hidra_topology_engineering_draft WHERE creation_request_id=?",Long.class,request)).isZero();}
        finally {jdbc.execute("ALTER TABLE public.hidra_topology_engineering_context ENABLE TRIGGER p25_engineering_context_immutable");}
    }
    @Test void twoCompaniesHaveIsolatedContextsOnTheSameLogicalNetwork(){
        var actor=draftActor("LOCAL");var first=create(UUID.randomUUID(),"company one");String other="other-"+id();
        jdbc.update("INSERT INTO public.hidra_party_party(id,code,party_type_id,legal_name,country_code,status,created_at,updated_at) VALUES(?,?,'k-party-type','Synthetic','DZ','ACTIVE',now(),now())",other,other);
        for(String action:List.of("create","read"))jdbc.update("INSERT INTO public.hidra_identity_user_permission_grant(id,user_id,permission_id,effect,scope_type,scope_reference_id,grant_reason,emergency_access,valid_from,valid_to,status,created_at) VALUES(?,?,?,'GRANT','CUSTOM',?,'synthetic draft evidence',false,now()-interval '1 hour',now()+interval '1 hour','ACTIVE',now())",id(),actor.user(),"draft-permission-"+action,"topology-company:"+other);
        var cmd=new dz.sh.hidra.modules.topology.application.port.in.EngineeringNetworkDraftUseCase.Command(
            dz.sh.hidra.modules.topology.application.port.in.EngineeringNetworkDraftUseCase.Kind.CREATE,other,network,null,UUID.randomUUID(),null,
            new dz.sh.hidra.modules.topology.domain.model.EngineeringNetworkDraft.Metadata("company two",null,null));
        var second=drafts.execute(cmd);assertThat(second.draft().contextId()).isNotEqualTo(first.draft().contextId());
        var wrong=new dz.sh.hidra.modules.topology.application.port.in.EngineeringNetworkDraftUseCase.Command(
            dz.sh.hidra.modules.topology.application.port.in.EngineeringNetworkDraftUseCase.Kind.READ,other,network,first.draft().draftId(),null,null,null);
        assertThatThrownBy(()->drafts.execute(wrong)).isInstanceOf(dz.sh.hidra.modules.topology.application.model.EngineeringNetworkDraftFailure.class)
            .extracting("reason").isEqualTo(dz.sh.hidra.modules.topology.application.model.EngineeringNetworkDraftFailure.Reason.NOT_FOUND);
    }
    @Test void committedNetworkRevocationAndPartyIneligibilityPreventReplay(){
        var actor=draftActor("LOCAL");var request=UUID.randomUUID();create(request,"first");
        jdbc.update("UPDATE public.hidra_identity_user_permission_grant SET status='REVOKED',revoked_at=now() WHERE user_id=? AND scope_reference_id=?",actor.user(),"topology-network:"+network);
        assertThatThrownBy(()->create(request,"first")).isInstanceOf(dz.sh.hidra.modules.topology.application.model.EngineeringNetworkDraftFailure.class);
        jdbc.update("UPDATE public.hidra_identity_user_permission_grant SET status='ACTIVE',revoked_at=NULL WHERE user_id=?",actor.user());
        jdbc.update("UPDATE public.hidra_party_party SET status='INACTIVE' WHERE id=?",COMPANY);
        try {assertThatThrownBy(()->create(request,"first")).isInstanceOf(dz.sh.hidra.modules.topology.application.model.EngineeringNetworkDraftFailure.class);}
        finally {jdbc.update("UPDATE public.hidra_party_party SET status='ACTIVE' WHERE id=?",COMPANY);}
    }
    @Test void actualFinalGuardRejectsExpiredProvisionalVersionAndRetainsNoRows(){
        draftActor("LOCAL");UUID request=UUID.randomUUID();var reached=new AtomicBoolean();
        doAnswer(i->{var c=(dz.sh.hidra.modules.topology.application.service.ProtectedEngineeringDraftAdmissionCoordinator.Contribution)i.getArgument(1);
            i.getArguments()[1]=new dz.sh.hidra.modules.topology.application.service.ProtectedEngineeringDraftAdmissionCoordinator.Contribution(
                c.request(),c.partyGeneration(),c.bindingId(),c.sessionId(),c.userId(),c.sampledAt(),c.sampledAt(),c.backendPid(),c.transactionId());
            reached.set(true);return i.callRealMethod();}).when(draftTarget()).finalGuard(any(),any(),anyList());
        assertThatThrownBy(()->create(request,"rollback")).isInstanceOf(dz.sh.hidra.modules.topology.application.model.EngineeringNetworkDraftFailure.class);
        assertThat(reached).isTrue();verify(draftTarget()).apply(any(),any());
        assertThat(jdbc.queryForObject("SELECT count(*) FROM public.hidra_topology_engineering_draft WHERE creation_request_id=?",Long.class,request)).isZero();
    }
    @Test void writerFirstLockPreventsProvisionalCreate() throws Exception {
        draftActor("LOCAL");UUID request=UUID.randomUUID();
        try(var writer=administrator.getConnection();var statement=writer.createStatement()){
            writer.setAutoCommit(false);statement.execute("LOCK TABLE public.hidra_topology_engineering_context IN ROW EXCLUSIVE MODE");
            assertThatThrownBy(()->create(request,"blocked")).isInstanceOf(dz.sh.hidra.modules.topology.application.model.EngineeringNetworkDraftFailure.class);writer.rollback();
        }
        assertThat(jdbc.queryForObject("SELECT count(*) FROM public.hidra_topology_engineering_draft WHERE creation_request_id=?",Long.class,request)).isZero();
    }
    @Test void concurrentSameExpectedVersionCommitsAtMostOneSuccessor() throws Exception {
        draftActor("LOCAL");var first=create(UUID.randomUUID(),"initial");var auth=SecurityContextHolder.getContext().getAuthentication();
        var start=new java.util.concurrent.CountDownLatch(1);var pool=Executors.newFixedThreadPool(2);var successes=new AtomicInteger();
        try {
            var jobs=new ArrayList<Future<?>>();for(int n=0;n<2;n++)jobs.add(pool.submit(()->{
                SecurityContextHolder.getContext().setAuthentication(auth);
                try {start.await();drafts.execute(command(dz.sh.hidra.modules.topology.application.port.in.EngineeringNetworkDraftUseCase.Kind.UPDATE,first.draft().draftId(),null,1L,"next"));successes.incrementAndGet();}
                catch(dz.sh.hidra.modules.topology.application.model.EngineeringNetworkDraftFailure expected){
                    assertThat(expected.reason()).isIn(dz.sh.hidra.modules.topology.application.model.EngineeringNetworkDraftFailure.Reason.CONFLICT,dz.sh.hidra.modules.topology.application.model.EngineeringNetworkDraftFailure.Reason.UNAVAILABLE);
                }catch(InterruptedException e){Thread.currentThread().interrupt();throw new AssertionError(e);}finally{SecurityContextHolder.clearContext();}
            }));start.countDown();for(var job:jobs)job.get(10,TimeUnit.SECONDS);
        }finally{pool.shutdownNow();}
        assertThat(successes.get()).isEqualTo(1);
        assertThat(jdbc.queryForObject("SELECT count(*) FROM public.hidra_topology_engineering_draft_version WHERE draft_id=?",Long.class,first.draft().draftId())).isEqualTo(1L+successes.get());
    }
    @Test void actualFinalGuardVerifiesCompleteOwnerFenceSubset(){
        draftActor("LOCAL");UUID request=UUID.randomUUID();var observed=new AtomicBoolean();
        doAnswer(i->{java.util.List<dz.sh.hidra.platform.persistence.ProtectedRelationFence> fences=i.getArgument(2);
            assertThat(fences).hasSize(31);assertThat(fences.stream().filter(f->f.mode()==dz.sh.hidra.platform.persistence.ProtectedRelationFence.Mode.SHARE_ROW_EXCLUSIVE).count()).isEqualTo(28);
            observed.set(true);return i.callRealMethod();}).when(draftTarget()).finalGuard(any(),any(),anyList());
        create(request,"fenced");assertThat(observed).isTrue();
    }

    private dz.sh.hidra.modules.topology.application.port.in.EngineeringGraphDraftUseCase graphOwner() {
        return (dz.sh.hidra.modules.topology.application.port.in.EngineeringGraphDraftUseCase)drafts;
    }
    private dz.sh.hidra.modules.topology.application.port.in.EngineeringGraphDraftUseCase.Command graphUpdate(
            UUID id,long version,dz.sh.hidra.modules.topology.domain.model.EngineeringGraphDraft graph) {
        return new dz.sh.hidra.modules.topology.application.port.in.EngineeringGraphDraftUseCase.Command(
            dz.sh.hidra.modules.topology.application.port.in.EngineeringGraphDraftUseCase.Kind.UPDATE,
            COMPANY,network,id,version,null,graph);
    }
    private dz.sh.hidra.modules.topology.application.port.in.EngineeringGraphDraftUseCase.Command graphRead(UUID id) {
        return new dz.sh.hidra.modules.topology.application.port.in.EngineeringGraphDraftUseCase.Command(
            dz.sh.hidra.modules.topology.application.port.in.EngineeringGraphDraftUseCase.Kind.READ,
            COMPANY,network,id,null,null,null);
    }
    private dz.sh.hidra.modules.topology.domain.model.EngineeringGraphDraft stationGraph() {
        return new dz.sh.hidra.modules.topology.domain.model.EngineeringGraphDraft(List.of(
            new dz.sh.hidra.modules.topology.domain.model.EngineeringGraphDraft.Node("station",
                dz.sh.hidra.modules.topology.domain.model.EngineeringGraphDraft.NodeKind.STATION,FACILITY)),List.of(),List.of());
    }
    private dz.sh.hidra.modules.topology.domain.model.EngineeringGraphDraft stationlessSerialParallelGraph() {
        return new dz.sh.hidra.modules.topology.domain.model.EngineeringGraphDraft(
            List.of(new dz.sh.hidra.modules.topology.domain.model.EngineeringGraphDraft.Node("a",dz.sh.hidra.modules.topology.domain.model.EngineeringGraphDraft.NodeKind.JUNCTION,null),
                new dz.sh.hidra.modules.topology.domain.model.EngineeringGraphDraft.Node("b",dz.sh.hidra.modules.topology.domain.model.EngineeringGraphDraft.NodeKind.JUNCTION,null),
                new dz.sh.hidra.modules.topology.domain.model.EngineeringGraphDraft.Node("c",dz.sh.hidra.modules.topology.domain.model.EngineeringGraphDraft.NodeKind.JUNCTION,null)),
            List.of(new dz.sh.hidra.modules.topology.domain.model.EngineeringGraphDraft.Port("a-out","a",dz.sh.hidra.modules.topology.domain.model.EngineeringGraphDraft.PortDirection.OUTLET),
                new dz.sh.hidra.modules.topology.domain.model.EngineeringGraphDraft.Port("b-in","b",dz.sh.hidra.modules.topology.domain.model.EngineeringGraphDraft.PortDirection.INLET),
                new dz.sh.hidra.modules.topology.domain.model.EngineeringGraphDraft.Port("b-out","b",dz.sh.hidra.modules.topology.domain.model.EngineeringGraphDraft.PortDirection.OUTLET),
                new dz.sh.hidra.modules.topology.domain.model.EngineeringGraphDraft.Port("c-in","c",dz.sh.hidra.modules.topology.domain.model.EngineeringGraphDraft.PortDirection.INLET)),
            List.of(new dz.sh.hidra.modules.topology.domain.model.EngineeringGraphDraft.Branch("parallel-a",dz.sh.hidra.modules.topology.domain.model.EngineeringGraphDraft.BranchKind.PIPE,"a-out","b-in"),
                new dz.sh.hidra.modules.topology.domain.model.EngineeringGraphDraft.Branch("parallel-b",dz.sh.hidra.modules.topology.domain.model.EngineeringGraphDraft.BranchKind.PIPE,"a-out","b-in"),
                new dz.sh.hidra.modules.topology.domain.model.EngineeringGraphDraft.Branch("serial",dz.sh.hidra.modules.topology.domain.model.EngineeringGraphDraft.BranchKind.CONNECTOR,"b-out","c-in")));
    }

    @Test void graphForeignOwnerAndOwnerAbaNeverReuseAStaleStationWitness() {
        draftActor("LOCAL");owners();var first=create(UUID.randomUUID(),"owner ABA");var id=first.draft().draftId();
        var stored=graphOwner().executeGraph(graphUpdate(id,1L,stationGraph()));
        long before=jdbc.queryForObject("SELECT generation FROM public.hidra_topology_facility_owner_generation WHERE facility_id=?",Long.class,FACILITY);
        String foreign="foreign-"+id();
        jdbc.update("INSERT INTO public.hidra_party_party(id,code,party_type_id,legal_name,country_code,status,created_at,updated_at) VALUES(?,?,'k-party-type','Synthetic','DZ','ACTIVE',now(),now())",foreign,foreign);
        jdbc.update("UPDATE public.hidra_topology_facility SET owner_party_id=? WHERE id=?",foreign,FACILITY);
        try {
            assertThatThrownBy(()->graphOwner().executeGraph(graphRead(id)))
                .isInstanceOf(dz.sh.hidra.modules.topology.application.model.EngineeringNetworkDraftFailure.class);
            assertThatThrownBy(()->graphOwner().executeGraph(graphUpdate(id,2L,stationGraph())))
                .isInstanceOf(dz.sh.hidra.modules.topology.application.model.EngineeringNetworkDraftFailure.class);
            assertThat(jdbc.queryForObject("SELECT count(*) FROM public.hidra_topology_engineering_draft_version WHERE draft_id=?",Long.class,id)).isEqualTo(2L);
            assertThat(jdbc.queryForObject("SELECT count(*) FROM public.hidra_topology_engineering_graph_snapshot WHERE draft_id=?",Long.class,id)).isEqualTo(1L);
        } finally {jdbc.update("UPDATE public.hidra_topology_facility SET owner_party_id=? WHERE id=?",COMPANY,FACILITY);}
        assertThat(jdbc.queryForObject("SELECT generation FROM public.hidra_topology_facility_owner_generation WHERE facility_id=?",Long.class,FACILITY)).isGreaterThan(before);
        assertThat(graphOwner().executeGraph(graphRead(id)).graphSha256()).isEqualTo(stored.graphSha256());
    }

    @Test void facilityWriterFirstPreventsBothGraphAndMetadataAppends() throws Exception {
        draftActor("LOCAL");owners();var first=create(UUID.randomUUID(),"writer first");var id=first.draft().draftId();
        try(var writer=administrator.getConnection();var statement=writer.createStatement()) {
            writer.setAutoCommit(false);statement.execute("LOCK TABLE public.hidra_topology_facility IN ROW EXCLUSIVE MODE");
            assertThatThrownBy(()->graphOwner().executeGraph(graphUpdate(id,1L,stationGraph())))
                .isInstanceOf(dz.sh.hidra.modules.topology.application.model.EngineeringNetworkDraftFailure.class);
            writer.rollback();
        }
        assertThat(jdbc.queryForObject("SELECT count(*) FROM public.hidra_topology_engineering_draft_version WHERE draft_id=?",Long.class,id)).isEqualTo(1L);
        assertThat(jdbc.queryForObject("SELECT count(*) FROM public.hidra_topology_engineering_graph_snapshot WHERE draft_id=?",Long.class,id)).isZero();
    }

    @Test void graphOwnerFirstBlocksAConcurrentFacilityGenerationWriter() {
        draftActor("LOCAL");owners();var first=create(UUID.randomUUID(),"owner first");var id=first.draft().draftId();
        long before=jdbc.queryForObject("SELECT generation FROM public.hidra_topology_facility_owner_generation WHERE facility_id=?",Long.class,FACILITY);
        var reached=new AtomicBoolean();
        doAnswer(i->{
            try(var writer=administrator.getConnection();var statement=writer.createStatement()) {
                writer.setAutoCommit(false);statement.execute("SET LOCAL lock_timeout='50ms'");
                try(var update=writer.prepareStatement("UPDATE public.hidra_topology_facility SET owner_party_id=owner_party_id WHERE id=?")) {
                    update.setString(1,FACILITY);
                    var blocked=org.junit.jupiter.api.Assertions.assertThrows(SQLException.class,update::executeUpdate);
                    assertThat(blocked.getSQLState()).isEqualTo("55P03");
                } finally {writer.rollback();}
            }
            reached.set(true);return i.callRealMethod();
        }).when(draftTarget()).finalGuard(any(),any(),anyList());
        assertThat(graphOwner().executeGraph(graphUpdate(id,1L,stationGraph())).version()).isEqualTo(2L);
        assertThat(reached).isTrue();
        assertThat(jdbc.queryForObject("SELECT generation FROM public.hidra_topology_facility_owner_generation WHERE facility_id=?",Long.class,FACILITY)).isEqualTo(before);
    }

    @Test void expiredGraphFinalGuardRollsBackBothVersionRows() {
        draftActor("LOCAL");var first=create(UUID.randomUUID(),"graph rollback");var id=first.draft().draftId();var reached=new AtomicBoolean();
        doAnswer(i->{var c=(dz.sh.hidra.modules.topology.application.service.ProtectedEngineeringDraftAdmissionCoordinator.Contribution)i.getArgument(1);
            i.getArguments()[1]=new dz.sh.hidra.modules.topology.application.service.ProtectedEngineeringDraftAdmissionCoordinator.Contribution(
                c.request(),c.partyGeneration(),c.bindingId(),c.sessionId(),c.userId(),c.sampledAt(),c.sampledAt(),c.backendPid(),c.transactionId());
            reached.set(true);return i.callRealMethod();
        }).when(draftTarget()).finalGuard(any(),any(),anyList());
        assertThatThrownBy(()->graphOwner().executeGraph(graphUpdate(id,1L,stationlessSerialParallelGraph())))
            .isInstanceOf(dz.sh.hidra.modules.topology.application.model.EngineeringNetworkDraftFailure.class);
        assertThat(reached).isTrue();
        assertThat(jdbc.queryForObject("SELECT count(*) FROM public.hidra_topology_engineering_draft_version WHERE draft_id=?",Long.class,id)).isEqualTo(1L);
        assertThat(jdbc.queryForObject("SELECT count(*) FROM public.hidra_topology_engineering_graph_snapshot WHERE draft_id=?",Long.class,id)).isZero();
    }

    @Test void actualGraphTriggerAndPrivilegeTamperingRejectBeforeMutation() {
        draftActor("LOCAL");var first=create(UUID.randomUUID(),"catalog");var id=first.draft().draftId();
        jdbc.execute("ALTER TABLE public.hidra_topology_engineering_graph_snapshot DISABLE TRIGGER trg_p25_engineering_graph_same_transaction");
        try {assertThatThrownBy(()->graphOwner().executeGraph(graphUpdate(id,1L,stationlessSerialParallelGraph())))
            .isInstanceOf(dz.sh.hidra.modules.topology.application.model.EngineeringNetworkDraftFailure.class);}
        finally {jdbc.execute("ALTER TABLE public.hidra_topology_engineering_graph_snapshot ENABLE TRIGGER trg_p25_engineering_graph_same_transaction");}
        jdbc.execute("GRANT DELETE ON public.hidra_topology_engineering_graph_snapshot TO c3_publication_app");
        try {assertThatThrownBy(()->graphOwner().executeGraph(graphUpdate(id,1L,stationlessSerialParallelGraph())))
            .isInstanceOf(dz.sh.hidra.modules.topology.application.model.EngineeringNetworkDraftFailure.class);}
        finally {jdbc.execute("REVOKE DELETE ON public.hidra_topology_engineering_graph_snapshot FROM c3_publication_app");}
        assertThat(jdbc.queryForObject("SELECT count(*) FROM public.hidra_topology_engineering_draft_version WHERE draft_id=?",Long.class,id)).isEqualTo(1L);
        assertThat(jdbc.queryForObject("SELECT count(*) FROM public.hidra_topology_engineering_graph_snapshot WHERE draft_id=?",Long.class,id)).isZero();
        assertThat(graphOwner().executeGraph(graphUpdate(id,1L,stationlessSerialParallelGraph())).version()).isEqualTo(2L);
    }


    private SurveyRouteProfileUseCase surveyOwner(){return (SurveyRouteProfileUseCase)drafts;}
    private SurveyRouteProfile surveyProfile(String branch){return new SurveyRouteProfile(branch,
        new SurveyRouteProfile.Crs("SYNTHETIC","test",List.of(new SurveyRouteProfile.Axis("north","metre"),new SurveyRouteProfile.Axis("east","metre"))),null,"origin",SurveyRouteProfile.Direction.FROM_TO,"evidence",
        List.of(new SurveyRouteProfile.Point("a",new BigDecimal("1.00"),new BigDecimal("2.000"),BigDecimal.ZERO,null,BigDecimal.ONE,BigDecimal.ONE,null),
            new SurveyRouteProfile.Point("b",new BigDecimal("3.000"),new BigDecimal("4.00"),BigDecimal.TEN,null,BigDecimal.ONE,BigDecimal.ONE,null)),List.of());}
    private SurveyRouteProfileUseCase.Command surveyWrite(UUID id,long version){return new SurveyRouteProfileUseCase.Command(SurveyRouteProfileUseCase.Kind.UPDATE,COMPANY,network,id,"parallel-a",version,null,surveyProfile("parallel-a"));}
    private SurveyRouteProfileUseCase.Command surveyRead(UUID id,Long version){return new SurveyRouteProfileUseCase.Command(SurveyRouteProfileUseCase.Kind.READ,COMPANY,network,id,"parallel-a",null,version,null);}
    private UUID surveyDraft(){var id=create(UUID.randomUUID(),"survey").draft().draftId();graphOwner().executeGraph(graphUpdate(id,1,stationlessSerialParallelGraph()));return id;}
    private void surveyRows(UUID id,long version,long count){
        assertThat(jdbc.queryForObject("SELECT max(version) FROM public.hidra_topology_engineering_draft_version WHERE draft_id=?",Long.class,id)).isEqualTo(version);
        assertThat(jdbc.queryForObject("SELECT count(*) FROM public.hidra_topology_engineering_survey_snapshot WHERE draft_id=?",Long.class,id)).isEqualTo(count);
    }
    @Test void realNativeProvidersRoundTripSurveyWithCurrentDeny() throws Exception {
        for(String type:List.of("LOCAL","OIDC","LDAP","ACTIVE_DIRECTORY")){
            var actor=draftActor(type);var id=surveyDraft();assertThat(surveyOwner().executeSurvey(surveyRead(id,null)).profile()).isNull();
            var result=surveyOwner().executeSurvey(surveyWrite(id,2));assertThat(result.profile()).isEqualTo(surveyProfile("parallel-a"));
            assertThat(surveyOwner().executeSurvey(surveyRead(id,null)).surveySha256()).isEqualTo(result.surveySha256());
            SecurityContextHolder.clearContext();
            try{http.perform(org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get("/api/v1/topology/engineering-networks/"+id+"/survey/parallel-a?companyPartyId="+COMPANY+"&pipelineSystemId="+network).header("Authorization","Bearer "+rawToken))
                .andExpect(org.springframework.test.web.servlet.result.MockMvcResultMatchers.status().isOk())
                .andExpect(org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath("$.surveyVersion").value(3))
                .andExpect(org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath("$.profile.points[0].firstCoordinate").value("1.00"));}
            finally{ambient(rawToken);}

            jdbc.update("UPDATE public.hidra_identity_user_permission_grant SET effect='DENY' WHERE user_id=? AND permission_id='draft-permission-update' AND scope_reference_id=?",actor.user(),"topology-company:"+COMPANY);
            assertThatThrownBy(()->surveyOwner().executeSurvey(surveyWrite(id,3))).isInstanceOf(dz.sh.hidra.modules.topology.application.model.EngineeringNetworkDraftFailure.class);
            surveyRows(id,3,1);
        }
    }
    @Test void surveyMetadataGraphInterleavingPreservesSharedVersions(){
        draftActor("LOCAL");var id=surveyDraft();var original=surveyOwner().executeSurvey(surveyWrite(id,2));
        drafts.execute(command(dz.sh.hidra.modules.topology.application.port.in.EngineeringNetworkDraftUseCase.Kind.UPDATE,id,null,3L,"metadata"));
        var current=surveyOwner().executeSurvey(surveyRead(id,null));assertThat(current.version()).isEqualTo(4);assertThat(current.surveyVersion()).isEqualTo(3L);assertThat(current.surveySha256()).isEqualTo(original.surveySha256());
        surveyOwner().executeSurvey(new SurveyRouteProfileUseCase.Command(SurveyRouteProfileUseCase.Kind.UPDATE,COMPANY,network,id,"parallel-b",4L,null,surveyProfile("parallel-b")));
        surveyRows(id,5,2);assertThat(graphOwner().executeGraph(graphRead(id)).graphVersion()).isEqualTo(2L);
        assertThatThrownBy(()->surveyOwner().executeSurvey(surveyWrite(id,4))).isInstanceOf(dz.sh.hidra.modules.topology.application.model.EngineeringNetworkDraftFailure.class);
    }
    @Test void surveyRejectsMissingForeignAndConnectorBranches(){
        draftActor("LOCAL");var id=surveyDraft();for(String branch:List.of("missing","serial")){
            assertThatThrownBy(()->surveyOwner().executeSurvey(new SurveyRouteProfileUseCase.Command(SurveyRouteProfileUseCase.Kind.UPDATE,COMPANY,network,id,branch,2L,null,surveyProfile(branch))))
                .isInstanceOf(dz.sh.hidra.modules.topology.application.model.EngineeringNetworkDraftFailure.class).extracting("reason").isEqualTo(dz.sh.hidra.modules.topology.application.model.EngineeringNetworkDraftFailure.Reason.NOT_FOUND);
        }
        assertThatThrownBy(()->surveyOwner().executeSurvey(new SurveyRouteProfileUseCase.Command(SurveyRouteProfileUseCase.Kind.READ,"foreign",network,id,"parallel-a",null,null,null))).isInstanceOf(dz.sh.hidra.modules.topology.application.model.EngineeringNetworkDraftFailure.class);
        surveyRows(id,2,0);
    }
    @Test void surveyGraphReauthoringConflictsUntilRebound(){
        draftActor("LOCAL");var id=surveyDraft();surveyOwner().executeSurvey(surveyWrite(id,2));graphOwner().executeGraph(graphUpdate(id,3,stationlessSerialParallelGraph()));
        assertThatThrownBy(()->surveyOwner().executeSurvey(surveyRead(id,null))).isInstanceOf(dz.sh.hidra.modules.topology.application.model.EngineeringNetworkDraftFailure.class).extracting("reason").isEqualTo(dz.sh.hidra.modules.topology.application.model.EngineeringNetworkDraftFailure.Reason.CONFLICT);
        assertThat(surveyOwner().executeSurvey(surveyRead(id,3L)).boundGraphVersion()).isEqualTo(2L);
        assertThat(surveyOwner().executeSurvey(surveyWrite(id,4)).boundGraphVersion()).isEqualTo(4L);surveyRows(id,5,2);
    }
    @Test void surveyExactHistoricalReadsRetainOriginalBytes(){
        draftActor("LOCAL");var id=surveyDraft();var first=surveyOwner().executeSurvey(surveyWrite(id,2));byte[] bytes=jdbc.queryForObject("SELECT canonical_payload FROM public.hidra_topology_engineering_survey_snapshot WHERE draft_id=? AND version=3",byte[].class,id);
        surveyOwner().executeSurvey(surveyWrite(id,3));var historical=surveyOwner().executeSurvey(surveyRead(id,3L));
        assertThat(historical.surveySha256()).isEqualTo(first.surveySha256());assertThat(new SurveyRouteProfileCodec().encode(historical.profile())).isEqualTo(bytes);
        assertThatThrownBy(()->surveyOwner().executeSurvey(surveyRead(id,99L))).isInstanceOf(dz.sh.hidra.modules.topology.application.model.EngineeringNetworkDraftFailure.class);
    }
    private void surveyRace(boolean graph) throws Exception {
        draftActor("LOCAL");var id=surveyDraft();var auth=SecurityContextHolder.getContext().getAuthentication();var latch=new CountDownLatch(1);var pool=Executors.newFixedThreadPool(2);var count=new AtomicInteger();
        try{var jobs=new ArrayList<Future<?>>();for(int n=0;n<2;n++){final int kind=n;jobs.add(pool.submit(()->{SecurityContextHolder.getContext().setAuthentication(auth);
            try{latch.await();if(kind==0)surveyOwner().executeSurvey(surveyWrite(id,2));else if(graph)graphOwner().executeGraph(graphUpdate(id,2,stationlessSerialParallelGraph()));else drafts.execute(command(dz.sh.hidra.modules.topology.application.port.in.EngineeringNetworkDraftUseCase.Kind.UPDATE,id,null,2L,"race"));count.incrementAndGet();}
            catch(dz.sh.hidra.modules.topology.application.model.EngineeringNetworkDraftFailure expected){}catch(InterruptedException e){Thread.currentThread().interrupt();throw new AssertionError(e);}finally{SecurityContextHolder.clearContext();}}));}latch.countDown();for(var job:jobs)job.get(10,TimeUnit.SECONDS);
        }finally{pool.shutdownNow();}assertThat(count.get()).isEqualTo(1);assertThat(jdbc.queryForObject("SELECT max(version) FROM public.hidra_topology_engineering_draft_version WHERE draft_id=?",Long.class,id)).isEqualTo(3L);
    }
    @Test void concurrentSurveyAndMetadataWritersHaveOneSuccessor() throws Exception {surveyRace(false);}
    @Test void concurrentSurveyAndGraphWritersHaveOneSuccessor() throws Exception {surveyRace(true);}
    private dz.sh.hidra.modules.topology.domain.model.EngineeringGraphDraft surveyStationGraph(){
        var g=stationlessSerialParallelGraph();var nodes=new ArrayList<>(g.nodes());nodes.addAll(stationGraph().nodes());return new dz.sh.hidra.modules.topology.domain.model.EngineeringGraphDraft(nodes,g.ports(),g.branches());
    }
    @Test void surveyForeignOwnerAndOwnerAbaNeverReuseWitness(){
        draftActor("LOCAL");owners();var id=create(UUID.randomUUID(),"station survey").draft().draftId();graphOwner().executeGraph(graphUpdate(id,1,surveyStationGraph()));var first=surveyOwner().executeSurvey(surveyWrite(id,2));
        long before=jdbc.queryForObject("SELECT generation FROM public.hidra_topology_facility_owner_generation WHERE facility_id=?",Long.class,FACILITY);
        String foreign="survey-foreign-"+id();jdbc.update("INSERT INTO public.hidra_party_party(id,code,party_type_id,legal_name,country_code,status,created_at,updated_at) VALUES(?,?,'k-party-type','Synthetic','DZ','ACTIVE',now(),now())",foreign,foreign);
        jdbc.update("UPDATE public.hidra_topology_facility SET owner_party_id=? WHERE id=?",foreign,FACILITY);
        try{assertThatThrownBy(()->surveyOwner().executeSurvey(surveyRead(id,null))).isInstanceOf(dz.sh.hidra.modules.topology.application.model.EngineeringNetworkDraftFailure.class);assertThatThrownBy(()->surveyOwner().executeSurvey(surveyWrite(id,3))).isInstanceOf(dz.sh.hidra.modules.topology.application.model.EngineeringNetworkDraftFailure.class);}
        finally{jdbc.update("UPDATE public.hidra_topology_facility SET owner_party_id=? WHERE id=?",COMPANY,FACILITY);}
        assertThat(jdbc.queryForObject("SELECT generation FROM public.hidra_topology_facility_owner_generation WHERE facility_id=?",Long.class,FACILITY)).isGreaterThan(before);
        assertThat(surveyOwner().executeSurvey(surveyRead(id,null)).surveySha256()).isEqualTo(first.surveySha256());surveyRows(id,3,1);
    }
    @Test void surveyWriterFirstAndOwnerFirstPreserveFacilityFences() throws Exception {
        draftActor("LOCAL");owners();var id=create(UUID.randomUUID(),"survey locks").draft().draftId();graphOwner().executeGraph(graphUpdate(id,1,surveyStationGraph()));
        try(var writer=administrator.getConnection();var st=writer.createStatement()){writer.setAutoCommit(false);st.execute("LOCK TABLE public.hidra_topology_facility IN ROW EXCLUSIVE MODE");assertThatThrownBy(()->surveyOwner().executeSurvey(surveyWrite(id,2))).isInstanceOf(dz.sh.hidra.modules.topology.application.model.EngineeringNetworkDraftFailure.class);writer.rollback();}surveyRows(id,2,0);
        var reached=new AtomicBoolean();doAnswer(i->{List<dz.sh.hidra.platform.persistence.ProtectedRelationFence> fences=i.getArgument(2);assertThat(fences).hasSize(35);
            assertThat(fences.stream().filter(f->f.mode()==dz.sh.hidra.platform.persistence.ProtectedRelationFence.Mode.SHARE_ROW_EXCLUSIVE).count()).isEqualTo(31L);
            try(var writer=administrator.getConnection();var st=writer.createStatement()){writer.setAutoCommit(false);st.execute("SET LOCAL lock_timeout='50ms'");try(var update=writer.prepareStatement("UPDATE public.hidra_topology_facility SET owner_party_id=owner_party_id WHERE id=?")){update.setString(1,FACILITY);assertThat(org.junit.jupiter.api.Assertions.assertThrows(SQLException.class,update::executeUpdate).getSQLState()).isEqualTo("55P03");}finally{writer.rollback();}}
            reached.set(true);return i.callRealMethod();}).when(draftTarget()).finalGuard(any(),any(),anyList());surveyOwner().executeSurvey(surveyWrite(id,2));assertThat(reached).isTrue();surveyRows(id,3,1);
    }
    @Test void expiredSurveyGuardRollsBackBothRows(){
        draftActor("LOCAL");var id=surveyDraft();var reached=new AtomicBoolean();doAnswer(i->{var c=(dz.sh.hidra.modules.topology.application.service.ProtectedEngineeringDraftAdmissionCoordinator.Contribution)i.getArgument(1);i.getArguments()[1]=new dz.sh.hidra.modules.topology.application.service.ProtectedEngineeringDraftAdmissionCoordinator.Contribution(c.request(),c.partyGeneration(),c.bindingId(),c.sessionId(),c.userId(),c.sampledAt(),c.sampledAt(),c.backendPid(),c.transactionId());reached.set(true);return i.callRealMethod();}).when(draftTarget()).finalGuard(any(),any(),anyList());
        assertThatThrownBy(()->surveyOwner().executeSurvey(surveyWrite(id,2))).isInstanceOf(dz.sh.hidra.modules.topology.application.model.EngineeringNetworkDraftFailure.class);assertThat(reached).isTrue();surveyRows(id,2,0);
    }
    @Test void surveyCatalogAndPrivilegeTamperingRejects(){
        draftActor("LOCAL");var id=surveyDraft();jdbc.execute("ALTER TABLE public.hidra_topology_engineering_survey_snapshot DISABLE TRIGGER trg_p25_engineering_survey_same_transaction");
        try{assertThatThrownBy(()->surveyOwner().executeSurvey(surveyWrite(id,2))).isInstanceOf(dz.sh.hidra.modules.topology.application.model.EngineeringNetworkDraftFailure.class);}finally{jdbc.execute("ALTER TABLE public.hidra_topology_engineering_survey_snapshot ENABLE TRIGGER trg_p25_engineering_survey_same_transaction");}
        jdbc.execute("GRANT DELETE ON public.hidra_topology_engineering_survey_snapshot TO c3_publication_app");try{assertThatThrownBy(()->surveyOwner().executeSurvey(surveyWrite(id,2))).isInstanceOf(dz.sh.hidra.modules.topology.application.model.EngineeringNetworkDraftFailure.class);}finally{jdbc.execute("REVOKE DELETE ON public.hidra_topology_engineering_survey_snapshot FROM c3_publication_app");}surveyRows(id,2,0);surveyOwner().executeSurvey(surveyWrite(id,2));
    }
    @Test void surveyDatabaseRejectsWrongTransactionDigestAndGraphBinding(){
        draftActor("LOCAL");var id=surveyDraft();surveyOwner().executeSurvey(surveyWrite(id,2));
        String insert="INSERT INTO public.hidra_topology_engineering_survey_snapshot (draft_id,branch_id,version,graph_version,graph_sha256,payload_format,canonical_payload,sha256,transaction_id) SELECT draft_id,branch_id,version+1,graph_version,graph_sha256,payload_format,canonical_payload,sha256,transaction_id FROM public.hidra_topology_engineering_survey_snapshot WHERE draft_id=?";
        assertThatThrownBy(()->jdbc.update(insert,id)).isInstanceOf(org.springframework.dao.DataAccessException.class);
        // Checks still reject a forged digest/graph when only the transaction trigger is deliberately disabled by admin.
        jdbc.execute("ALTER TABLE public.hidra_topology_engineering_survey_snapshot DISABLE TRIGGER trg_p25_engineering_survey_same_transaction");
        try{
            for(String alteration:List.of("sha256=repeat('0',64)","graph_version=999")){
                String sql=alteration.startsWith("sha256")?insert.replace("canonical_payload,sha256,transaction_id FROM","canonical_payload,repeat('0',64),transaction_id FROM"):insert.replace("version+1,graph_version,graph_sha256","version+1,999,graph_sha256");
                assertThatThrownBy(()->jdbc.update(sql,id)).isInstanceOf(org.springframework.dao.DataAccessException.class);
            }
        }finally{jdbc.execute("ALTER TABLE public.hidra_topology_engineering_survey_snapshot ENABLE TRIGGER trg_p25_engineering_survey_same_transaction");}surveyRows(id,3,1);
        for(String sql:List.of("SELECT * FROM public.hidra_topology_engineering_survey_snapshot","DELETE FROM public.hidra_topology_engineering_survey_snapshot WHERE false")){
            try(var ordinary=DriverManager.getConnection(POSTGRES.getJdbcUrl(),"c3_source_claim_app","synthetic-c3-test-only");var st=ordinary.createStatement()){
                assertThat(org.junit.jupiter.api.Assertions.assertThrows(SQLException.class,()->st.execute(sql)).getSQLState()).isEqualTo("42501");
            }catch(SQLException e){throw new AssertionError(e);}
        }
    }

    private EngineeringLocalExchangeUseCase exchanges(){return webContext.getBean(EngineeringLocalExchangeUseCase.class);}
    private EngineeringLocalExchangeUseCase.Command exchangeCommand(EngineeringLocalExchangeUseCase.Operation op,Editor editor,UUID draft,Long expected,EngineeringLocalExchange pack){
        return new EngineeringLocalExchangeUseCase.Command(op,editor,COMPANY,network,draft,editor==Editor.SURVEY?"parallel-a":null,null,expected,pack);
    }
    private String exchangeUrl(UUID draft,Editor editor,String suffix){return "/api/v1/topology/engineering-networks/"+draft+"/exchanges/"+editor+suffix
        +"?companyPartyId="+COMPANY+"&pipelineSystemId="+network+(editor==Editor.SURVEY?"&branchId=parallel-a":"");}
    private String exchangeBody(long version,EngineeringLocalExchange pack){return JSON.writeValueAsString(Map.of("expectedVersion",version,"package",pack));}
    private Map<String,String> exchangeStorageState(){
        var state=new TreeMap<String,String>();
        for(String table:List.of("hidra_topology_engineering_context","hidra_topology_engineering_draft","hidra_topology_engineering_draft_version",
                "hidra_topology_engineering_graph_snapshot","hidra_topology_engineering_survey_snapshot","hidra_topology_engineering_publication_snapshot",
                "hidra_topology_engineering_publication_event","hidra_topology_engineering_publication_head"))
            state.put(table,jdbc.queryForObject("SELECT md5(coalesce(jsonb_agg(to_jsonb(t) ORDER BY to_jsonb(t)::text)::text,'[]')) FROM public."+table+" t",String.class));
        return state;
    }
    @Test void genuineNativeEditorsExportDryRunAndImportUseExistingProtectedOwners() throws Exception {
        for(String provider:List.of("LOCAL","OIDC","LDAP","ACTIVE_DIRECTORY")) {
            draftActor(provider);UUID draft=surveyDraft();surveyOwner().executeSurvey(surveyWrite(draft,2));
            var originals=new EnumMap<Editor,EngineeringLocalExchange>(Editor.class);
            for(var editor:Editor.values())originals.put(editor,exchanges().execute(exchangeCommand(EngineeringLocalExchangeUseCase.Operation.EXPORT,editor,draft,null,null)).bundle());
            long version=3;
            for(var editor:Editor.values()) {
                String token=rawToken;SecurityContextHolder.clearContext();
                http.perform(org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post(exchangeUrl(draft,editor,"/imports"))
                    .header("Authorization","Bearer "+token).contentType("application/json").content(exchangeBody(version,originals.get(editor))))
                    .andExpect(org.springframework.test.web.servlet.result.MockMvcResultMatchers.status().isOk())
                    .andExpect(org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath("$.targetVersion").value((int)(version+1)))
                    .andExpect(org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath("$.engineeringQualified").value(false));
                ambient(token);version++;
                var exported=exchanges().execute(exchangeCommand(EngineeringLocalExchangeUseCase.Operation.EXPORT,editor,draft,null,null));
                assertThat(exported.bundle().bytes()).isEqualTo(originals.get(editor).bytes());
                if(editor==Editor.SURVEY){assertThat(exported.bundle().boundGraphVersion()).isEqualTo(5L);assertThat(originals.get(editor).boundGraphVersion()).isEqualTo(2L);}
            }
            assertThat(jdbc.queryForObject("SELECT max(version) FROM public.hidra_topology_engineering_draft_version WHERE draft_id=?",Long.class,draft)).isEqualTo(6L);
        }
    }
    @Test void dryRunAndCapabilityReadsLeaveAllDraftAndPublicationStorageUnchanged() {
        draftActor("LOCAL");UUID draft=surveyDraft();surveyOwner().executeSurvey(surveyWrite(draft,2));var before=exchangeStorageState();
        for(var editor:Editor.values()) {
            var pack=exchanges().execute(exchangeCommand(EngineeringLocalExchangeUseCase.Operation.EXPORT,editor,draft,null,null)).bundle();
            var dry=exchanges().execute(exchangeCommand(EngineeringLocalExchangeUseCase.Operation.DRY_RUN,editor,draft,3L,pack));
            assertThat(dry.targetVersion()).isNull();assertThat(dry.diagnostics()).contains("DRY_RUN_ADVISORY","WRITE_AUTHORIZATION_NOT_EVALUATED");
            var capabilities=exchanges().execute(exchangeCommand(EngineeringLocalExchangeUseCase.Operation.CAPABILITIES,editor,draft,null,null));
            assertThat(capabilities.targetVersion()).isNull();assertThat(capabilities.state()).isEqualTo("AUTHORED");
        }
        assertThat(exchangeStorageState()).isEqualTo(before);
    }
    @Test void staleConcurrentAndRevokedImportsCannotAppendPartialSuccessors() throws Exception {
        var actor=draftActor("LOCAL");UUID draft=create(UUID.randomUUID(),"exchange race").draft().draftId();
        var pack=exchanges().execute(exchangeCommand(EngineeringLocalExchangeUseCase.Operation.EXPORT,Editor.METADATA,draft,null,null)).bundle();
        drafts.execute(command(dz.sh.hidra.modules.topology.application.port.in.EngineeringNetworkDraftUseCase.Kind.UPDATE,draft,null,1L,"changed"));
        var before=exchangeStorageState();assertThatThrownBy(()->exchanges().execute(exchangeCommand(EngineeringLocalExchangeUseCase.Operation.IMPORT,Editor.METADATA,draft,1L,pack)))
            .isInstanceOf(EngineeringLocalExchangeFailure.class);assertThat(exchangeStorageState()).isEqualTo(before);
        var auth=SecurityContextHolder.getContext().getAuthentication();var latch=new CountDownLatch(1);var pool=Executors.newFixedThreadPool(2);var winners=new AtomicInteger();
        try {
            var jobs=new ArrayList<Future<?>>();for(int n=0;n<2;n++)jobs.add(pool.submit(()->{
                SecurityContextHolder.getContext().setAuthentication(auth);
                try{latch.await();exchanges().execute(exchangeCommand(EngineeringLocalExchangeUseCase.Operation.IMPORT,Editor.METADATA,draft,2L,pack));winners.incrementAndGet();}
                catch(EngineeringLocalExchangeFailure|dz.sh.hidra.modules.topology.application.model.EngineeringNetworkDraftFailure rejected){}
                catch(InterruptedException interrupted){Thread.currentThread().interrupt();throw new AssertionError(interrupted);}
                finally{SecurityContextHolder.clearContext();}
            }));latch.countDown();for(var job:jobs)job.get(10,TimeUnit.SECONDS);
        }finally{pool.shutdownNow();}
        assertThat(winners.get()).isEqualTo(1);assertThat(jdbc.queryForObject("SELECT max(version) FROM public.hidra_topology_engineering_draft_version WHERE draft_id=?",Long.class,draft)).isEqualTo(3L);
        before=exchangeStorageState();var guards=new AtomicInteger();
        doAnswer(i->{if(guards.incrementAndGet()==2){var c=(dz.sh.hidra.modules.topology.application.service.ProtectedEngineeringDraftAdmissionCoordinator.Contribution)i.getArgument(1);
            i.getArguments()[1]=new dz.sh.hidra.modules.topology.application.service.ProtectedEngineeringDraftAdmissionCoordinator.Contribution(
                c.request(),c.partyGeneration(),c.bindingId(),c.sessionId(),c.userId(),c.sampledAt(),c.sampledAt(),c.backendPid(),c.transactionId());}
            return i.callRealMethod();}).when(draftTarget()).finalGuard(any(),any(),anyList());
        assertThatThrownBy(()->exchanges().execute(exchangeCommand(EngineeringLocalExchangeUseCase.Operation.IMPORT,Editor.METADATA,draft,3L,pack)))
            .isInstanceOf(dz.sh.hidra.modules.topology.application.model.EngineeringNetworkDraftFailure.class);
        assertThat(guards.get()).isEqualTo(2);assertThat(exchangeStorageState()).isEqualTo(before);
        doCallRealMethod().when(draftTarget()).finalGuard(any(),any(),anyList());
        var flushes=new AtomicInteger();doAnswer(i->{i.callRealMethod();if(flushes.incrementAndGet()==2)throw new IllegalStateException("synthetic post-managed-flush exchange failure");return null;}).when(draftTarget()).flush();
        assertThatThrownBy(()->exchanges().execute(exchangeCommand(EngineeringLocalExchangeUseCase.Operation.IMPORT,Editor.METADATA,draft,3L,pack)))
            .isInstanceOf(dz.sh.hidra.modules.topology.application.model.EngineeringNetworkDraftFailure.class);
        assertThat(flushes.get()).isEqualTo(2);assertThat(exchangeStorageState()).isEqualTo(before);doCallRealMethod().when(draftTarget()).flush();
        var attempts=new AtomicInteger();
        dz.sh.hidra.modules.topology.application.port.in.EngineeringNetworkDraftUseCase revoking=command->{
            if(command.kind()==dz.sh.hidra.modules.topology.application.port.in.EngineeringNetworkDraftUseCase.Kind.UPDATE){attempts.incrementAndGet();
                jdbc.update("UPDATE public.hidra_identity_user_permission_grant SET status='REVOKED',revoked_at=now() WHERE user_id=? AND permission_id='draft-permission-update'",actor.user());}
            return drafts.execute(command);
        };
        var facade=new dz.sh.hidra.modules.topology.application.service.EngineeringLocalExchangeApplicationService(revoking,graphOwner(),surveyOwner(),new dz.sh.hidra.modules.topology.infrastructure.persistence.adapter.EngineeringLocalExchangeCodec());
        assertThatThrownBy(()->facade.execute(exchangeCommand(EngineeringLocalExchangeUseCase.Operation.IMPORT,Editor.METADATA,draft,3L,pack)))
            .isInstanceOf(dz.sh.hidra.modules.topology.application.model.EngineeringNetworkDraftFailure.class);
        assertThat(attempts.get()).isEqualTo(1);assertThat(exchangeStorageState()).isEqualTo(before);
    }
    @Test void malformedForeignAndUnsupportedPackagesRevealNoOwnerDataAndRetainExactBytes() throws Exception {
        draftActor("LOCAL");UUID draft=surveyDraft();surveyOwner().executeSurvey(surveyWrite(draft,2));
        var pack=exchanges().execute(exchangeCommand(EngineeringLocalExchangeUseCase.Operation.EXPORT,Editor.METADATA,draft,null,null)).bundle();var before=exchangeStorageState();
        String body=exchangeBody(3,pack),token=rawToken;
        String foreignCompany="exchange-foreign-"+id();
        jdbc.update("INSERT INTO public.hidra_party_party(id,code,party_type_id,legal_name,country_code,status,created_at,updated_at) VALUES(?,?,'k-party-type','Synthetic','DZ','ACTIVE',now(),now())",foreignCompany,foreignCompany);
        SecurityContextHolder.clearContext();
        http.perform(org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get(exchangeUrl(draft,Editor.METADATA,"/export"))).andExpect(org.springframework.test.web.servlet.result.MockMvcResultMatchers.status().isUnauthorized());
        http.perform(org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get(exchangeUrl(draft,Editor.METADATA,"/export").replace("companyPartyId="+COMPANY,"companyPartyId="+foreignCompany))
            .header("Authorization","Bearer "+token)).andExpect(org.springframework.test.web.servlet.result.MockMvcResultMatchers.status().isForbidden())
            .andExpect(org.springframework.test.web.servlet.result.MockMvcResultMatchers.content().string(org.hamcrest.Matchers.not(org.hamcrest.Matchers.containsString(foreignCompany))));
        http.perform(org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get(exchangeUrl(draft,Editor.METADATA,"/export").replace("companyPartyId="+COMPANY,"companyPartyId=unavailable-company"))
            .header("Authorization","Bearer "+token)).andExpect(org.springframework.test.web.servlet.result.MockMvcResultMatchers.status().isServiceUnavailable())
            .andExpect(org.springframework.test.web.servlet.result.MockMvcResultMatchers.content().string(org.hamcrest.Matchers.not(org.hamcrest.Matchers.containsString("unavailable-company"))));
        for(String invalid:List.of(body.replace("\"expectedVersion\":3","\"expectedVersion\":3,\"expectedVersion\":3"),body.replace("\"package\":","\"actor\":\"forged\",\"package\":")))
            http.perform(org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post(exchangeUrl(draft,Editor.METADATA,"/imports"))
                .header("Authorization","Bearer "+token).contentType("application/json").content(invalid)).andExpect(org.springframework.test.web.servlet.result.MockMvcResultMatchers.status().isBadRequest());
        http.perform(org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post(exchangeUrl(draft,Editor.METADATA,"/imports").replace("/METADATA/","/BATCH/"))
            .header("Authorization","Bearer "+token).contentType("application/json").content(body)).andExpect(org.springframework.test.web.servlet.result.MockMvcResultMatchers.status().isBadRequest());
        UUID foreign=UUID.randomUUID();String substituted=body.replace(pack.contextId().toString(),foreign.toString());
        http.perform(org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post(exchangeUrl(draft,Editor.METADATA,"/imports"))
            .header("Authorization","Bearer "+token).contentType("application/json").content(substituted)).andExpect(org.springframework.test.web.servlet.result.MockMvcResultMatchers.status().isConflict())
            .andExpect(org.springframework.test.web.servlet.result.MockMvcResultMatchers.content().string(org.hamcrest.Matchers.not(org.hamcrest.Matchers.containsString(foreign.toString()))));
        ambient(token);var survey=exchanges().execute(exchangeCommand(EngineeringLocalExchangeUseCase.Operation.EXPORT,Editor.SURVEY,draft,null,null)).bundle();
        var bad=new EngineeringLocalExchange(survey.format(),survey.editor(),survey.companyPartyId(),survey.pipelineSystemId(),survey.contextId(),survey.draftId(),survey.sourceVersion(),survey.componentVersion(),survey.componentFormat(),survey.sha256(),survey.payload(),survey.branchId(),survey.boundGraphVersion(),"0".repeat(64));
        assertThatThrownBy(()->exchanges().execute(exchangeCommand(EngineeringLocalExchangeUseCase.Operation.IMPORT,Editor.SURVEY,draft,3L,bad))).isInstanceOf(EngineeringLocalExchangeFailure.class);
        assertThat(exchangeStorageState()).isEqualTo(before);assertThat(exchanges().execute(exchangeCommand(EngineeringLocalExchangeUseCase.Operation.EXPORT,Editor.METADATA,draft,null,null)).bundle().bytes()).isEqualTo(pack.bytes());
    }
}
