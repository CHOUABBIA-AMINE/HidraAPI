/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : EngineeringRevisionPublicationPostgresIntegrationTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-20
 *
 * @Type        : Class
 * @Layer       : Topology Test
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.infrastructure.persistence
 *
 * @Description : Verifies governed publication with genuine native producers and separate actual PostgreSQL principals.
 *
 */
package dz.sh.hidra.modules.topology.infrastructure.persistence;

import dz.sh.hidra.modules.topology.application.port.in.EngineeringRevisionPublicationUseCase;
import dz.sh.hidra.modules.topology.application.port.in.EngineeringRevisionPublicationUseCase.Command;
import dz.sh.hidra.modules.topology.application.port.in.EngineeringRevisionPublicationUseCase.Kind;
import dz.sh.hidra.modules.topology.application.port.in.EngineeringRevisionPublicationUseCase.Result;
import dz.sh.hidra.modules.topology.application.model.EngineeringRevisionPublicationFailure;
import dz.sh.hidra.modules.topology.application.model.EngineeringRevisionPublicationFailure.Reason;
import dz.sh.hidra.modules.topology.domain.model.EngineeringRevisionPublication;
import dz.sh.hidra.modules.topology.domain.model.EngineeringRevisionPublication.*;
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
@org.springframework.context.annotation.Import(EngineeringRevisionPublicationPostgresIntegrationTest.Config.class)
public class EngineeringRevisionPublicationPostgresIntegrationTest {
    @Autowired dz.sh.hidra.modules.topology.application.port.in.EngineeringRevisionPublicationUseCase publications;
    @MockitoSpyBean dz.sh.hidra.modules.topology.infrastructure.persistence.adapter.JdbcEngineeringRevisionPublicationRepositoryAdapter publicationStorage;
    private dz.sh.hidra.modules.topology.infrastructure.persistence.adapter.JdbcEngineeringRevisionPublicationRepositoryAdapter publicationTarget() {
        return org.springframework.test.util.AopTestUtils.getUltimateTargetObject(publicationStorage);
    }
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
        String publicationTables="public.hidra_topology_engineering_publication_snapshot,public.hidra_topology_engineering_publication_event,public.hidra_topology_engineering_publication_head";
        jdbc.execute("REVOKE ALL ON "+publicationTables+" FROM c3_source_claim_app,c3_publication_app");
        jdbc.execute("GRANT SELECT,INSERT,UPDATE ON "+publicationTables+" TO c3_publication_app");
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





    private void surveyRace(boolean graph) throws Exception {
        draftActor("LOCAL");var id=surveyDraft();var auth=SecurityContextHolder.getContext().getAuthentication();var latch=new CountDownLatch(1);var pool=Executors.newFixedThreadPool(2);var count=new AtomicInteger();
        try{var jobs=new ArrayList<Future<?>>();for(int n=0;n<2;n++){final int kind=n;jobs.add(pool.submit(()->{SecurityContextHolder.getContext().setAuthentication(auth);
            try{latch.await();if(kind==0)surveyOwner().executeSurvey(surveyWrite(id,2));else if(graph)graphOwner().executeGraph(graphUpdate(id,2,stationlessSerialParallelGraph()));else drafts.execute(command(dz.sh.hidra.modules.topology.application.port.in.EngineeringNetworkDraftUseCase.Kind.UPDATE,id,null,2L,"race"));count.incrementAndGet();}
            catch(dz.sh.hidra.modules.topology.application.model.EngineeringNetworkDraftFailure expected){}catch(InterruptedException e){Thread.currentThread().interrupt();throw new AssertionError(e);}finally{SecurityContextHolder.clearContext();}}));}latch.countDown();for(var job:jobs)job.get(10,TimeUnit.SECONDS);
        }finally{pool.shutdownNow();}assertThat(count.get()).isEqualTo(1);assertThat(jdbc.queryForObject("SELECT max(version) FROM public.hidra_topology_engineering_draft_version WHERE draft_id=?",Long.class,id)).isEqualTo(3L);
    }


    private dz.sh.hidra.modules.topology.domain.model.EngineeringGraphDraft surveyStationGraph(){
        var g=stationlessSerialParallelGraph();var nodes=new ArrayList<>(g.nodes());nodes.addAll(stationGraph().nodes());return new dz.sh.hidra.modules.topology.domain.model.EngineeringGraphDraft(nodes,g.ports(),g.branches());
    }






    private Fixture publicationActor(String type) {
        var actor=draftActor(type);
        for(String action:List.of("publish","withdraw","reactivate","read")) {
            String permission="publication-permission-"+action;
            jdbc.update("INSERT INTO public.hidra_identity_permission(id,code,permission_domain,resource_type,action,sensitive,status,created_at,updated_at) VALUES(?,?,'topology','ENGINEERING_NETWORK_PUBLICATION',?,false,'ACTIVE',now(),now())",
                permission,"topology:engineering-publication:"+action,action);
            for(String scope:List.of("topology-company:"+COMPANY,"topology-network:"+network))
                jdbc.update("INSERT INTO public.hidra_identity_user_permission_grant(id,user_id,permission_id,effect,scope_type,scope_reference_id,grant_reason,emergency_access,valid_from,valid_to,status,created_at) VALUES(?,?,?,'GRANT','CUSTOM',?,'synthetic publication evidence',false,now()-interval '1 hour',now()+interval '1 hour','ACTIVE',now())",
                    id(),actor.user(),permission,scope);
        }
        return actor;
    }
    private Command publication(UUID draft,long version,Head expected,UUID request) {
        return new Command(Kind.PUBLISH,COMPANY,network,draft,request,version,expected==null?0L:expected.epoch(),
            expected==null?null:expected.revisionId(),null,null,null,null);
    }
    private Command query(Kind kind,UUID draft,UUID target,Instant asOf,Integer limit,String cursor) {
        return new Command(kind,COMPANY,network,draft,kind==Kind.READ_REQUEST?target:null,null,null,null,
            kind==Kind.READ_EXACT?target:null,asOf,limit,cursor);
    }
    private Result publish(UUID draft,long version,Head head) {
        return publications.executePublication(publication(draft,version,head,UUID.randomUUID()));
    }
    private Result current(UUID draft) {return publications.executePublication(query(Kind.READ_CURRENT,draft,null,null,null,null));}
    private Result exact(UUID draft,UUID revision) {return publications.executePublication(query(Kind.READ_EXACT,draft,revision,null,null,null));}
    private Result transition(Kind kind,UUID draft,Head head) {
        return publications.executePublication(new Command(kind,COMPANY,network,draft,UUID.randomUUID(),null,
            head.epoch(),head.revisionId(),null,null,null,null));
    }
    private long events(UUID context) {
        return jdbc.queryForObject("SELECT count(*) FROM public.hidra_topology_engineering_publication_event WHERE context_id=?",Long.class,context);
    }
    private void noPublication(UUID draft) {
        assertThat(jdbc.queryForObject("SELECT count(*) FROM public.hidra_topology_engineering_publication_snapshot WHERE draft_id=?",Long.class,draft)).isZero();
        assertThat(jdbc.queryForObject("SELECT count(*) FROM public.hidra_topology_engineering_publication_event e JOIN public.hidra_topology_engineering_draft d ON d.context_id=e.context_id WHERE d.id=?",Long.class,draft)).isZero();
    }
    private String publicationUrl(UUID draft,String suffix) {
        return "/api/v1/topology/engineering-networks/"+draft+"/publications"+suffix+"?companyPartyId="+COMPANY+"&pipelineSystemId="+network;
    }

    @Test void fourGenuineProvidersPublishAndReadExactSnapshots() throws Exception {
        for(String type:List.of("LOCAL","OIDC","LDAP","ACTIVE_DIRECTORY")) {
            publicationActor(type);var draft=create(UUID.randomUUID(),"published "+type).draft().draftId();
            var request=UUID.randomUUID();
            String body="{\"requestId\":\""+request+"\",\"draftVersion\":1,\"expectedEpoch\":0}";
            assertThat(nativeDecoder.decode(rawToken).getTokenValue()).isEqualTo(rawToken);
            SecurityContextHolder.clearContext();
            var response=http.perform(org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post(publicationUrl(draft,""))
                    .header("Authorization","Bearer "+rawToken).contentType("application/json").content(body))
                .andExpect(org.springframework.test.web.servlet.result.MockMvcResultMatchers.status().isOk()).andReturn();
            var root=JSON.readTree(response.getResponse().getContentAsString());
            UUID revision=UUID.fromString(root.path("snapshot").path("revisionId").asText());
            ambient(rawToken);
            assertThat(root.path("snapshot").path("engineeringQualified").asBoolean()).isFalse();
            assertThat(exact(draft,revision).publication().revisionId()).isEqualTo(revision);
            SecurityContextHolder.clearContext();
            http.perform(org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get(publicationUrl(draft,"/revisions/"+revision))
                    .header("Authorization","Bearer "+rawToken))
                .andExpect(org.springframework.test.web.servlet.result.MockMvcResultMatchers.status().isOk());
            SecurityContextHolder.clearContext();
            http.perform(org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get(publicationUrl(draft,"/current")))
                .andExpect(org.springframework.test.web.servlet.result.MockMvcResultMatchers.status().isUnauthorized());
            http.perform(org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post(publicationUrl(draft,""))
                    .header("Authorization","Bearer "+rawToken).contentType("application/json")
                    .content(body.substring(0,body.length()-1)+",\"engineeringQualified\":true}"))
                .andExpect(org.springframework.test.web.servlet.result.MockMvcResultMatchers.status().isBadRequest());
        }
    }
    @Test void ordinaryLoginCannotReadOrMutatePublicationStorage() throws Exception {
        publicationActor("LOCAL");UUID draft=create(UUID.randomUUID(),"privilege control").draft().draftId();
        var published=publish(draft,1,null);var snapshot=published.publication().snapshot();
        assertThat(events(snapshot.scope().contextId())).isEqualTo(1);
        try(var connection=DriverManager.getConnection(POSTGRES.getJdbcUrl(),"c3_source_claim_app","synthetic-c3-test-only")) {
            for(String suffix:List.of("snapshot","event","head")) {
                String table="public.hidra_topology_engineering_publication_"+suffix;
                for(String sql:List.of("SELECT * FROM "+table,"UPDATE "+table+" SET context_id=context_id WHERE false","DELETE FROM "+table+" WHERE false"))
                    assertThatThrownBy(()->connection.createStatement().execute(sql)).isInstanceOfSatisfying(SQLException.class,e->assertThat(e.getSQLState()).isEqualTo("42501"));
            }
            byte[] bytes=new dz.sh.hidra.modules.topology.infrastructure.persistence.adapter.EngineeringRevisionPublicationCodec().encode(snapshot);
            try(var insert=connection.prepareStatement("INSERT INTO public.hidra_topology_engineering_publication_snapshot(revision_id,context_id,draft_id,draft_version,payload_format,canonical_payload,sha256,engineering_qualified,transaction_id) VALUES(?,?,?,1,'HIDRA_ENGINEERING_PUBLICATION_V1',?,?,false,txid_current())")) {
                insert.setObject(1,UUID.randomUUID());insert.setObject(2,snapshot.scope().contextId());insert.setObject(3,draft);
                insert.setBytes(4,bytes);insert.setString(5,EngineeringRevisionPublication.hash(bytes));
                assertThatThrownBy(insert::executeUpdate).isInstanceOfSatisfying(SQLException.class,e->assertThat(e.getSQLState()).isEqualTo("42501"));
            }
        }
        assertThat(jdbc.queryForObject("SELECT count(*) FROM public.hidra_topology_engineering_publication_snapshot WHERE draft_id=?",Long.class,draft)).isEqualTo(1);
    }
    @Test void currentDenyRevocationAndForeignScopeRevealNoPublicationData() {
        var actor=publicationActor("LOCAL");UUID draft=create(UUID.randomUUID(),"denial").draft().draftId();
        var published=publish(draft,1,null);UUID revision=published.publication().revisionId();
        jdbc.update("UPDATE public.hidra_identity_user_permission_grant SET effect='DENY' WHERE user_id=? AND permission_id='publication-permission-read' AND scope_reference_id=?",
            actor.user(),"topology-network:"+network);
        clearInvocations(publicationTarget());
        assertThatThrownBy(()->exact(draft,revision)).isInstanceOf(EngineeringRevisionPublicationFailure.class);
        verify(publicationTarget(),never()).eligible(any());
        jdbc.update("UPDATE public.hidra_identity_user_permission_grant SET effect='GRANT' WHERE user_id=? AND permission_id='publication-permission-read'",actor.user());
        assertThat(exact(draft,revision).publication().revisionId()).isEqualTo(revision);
        assertThatThrownBy(()->publications.executePublication(new Command(Kind.READ_EXACT,"foreign",network,draft,null,null,null,null,revision,null,null,null)))
            .isInstanceOf(EngineeringRevisionPublicationFailure.class);
        jdbc.update("UPDATE public.hidra_identity_user_permission_grant SET status='REVOKED',revoked_at=now() WHERE user_id=? AND permission_id='publication-permission-read'",actor.user());
        assertThatThrownBy(()->exact(draft,revision)).isInstanceOf(EngineeringRevisionPublicationFailure.class);
        assertThat(events(published.publication().snapshot().scope().contextId())).isEqualTo(1);
    }
    @Test void sourceGraphSurveyDigestsAndIncompleteDiagnosticsRemainExact() {
        publicationActor("LOCAL");UUID draft=surveyDraft();surveyOwner().executeSurvey(surveyWrite(draft,2));
        var first=publish(draft,3,null);var snapshot=first.publication().snapshot();
        assertThat(snapshot.engineeringQualified()).isFalse();
        assertThat(snapshot.graph().sha256()).isEqualTo(jdbc.queryForObject("SELECT sha256 FROM public.hidra_topology_engineering_graph_snapshot WHERE draft_id=? AND version=2",String.class,draft));
        var supplied=snapshot.surveys().stream().filter(v->v.branchId().equals("parallel-a")).findFirst().orElseThrow();
        assertThat(supplied.source().sha256()).isEqualTo(jdbc.queryForObject("SELECT sha256 FROM public.hidra_topology_engineering_survey_snapshot WHERE draft_id=? AND version=3",String.class,draft));
        var decoded=new SurveyRouteProfileCodec().decode(Base64.getDecoder().decode(supplied.source().base64()));
        assertThat(decoded.points().getFirst().firstCoordinate()).isEqualTo(new BigDecimal("1.00"));
        assertThat(decoded.points().getFirst().elevation()).isNull();
        assertThat(snapshot.surveys().stream().filter(v->v.branchId().equals("parallel-b")).findFirst().orElseThrow().diagnostics()).contains("SURVEY_NOT_AUTHORED");
        graphOwner().executeGraph(graphUpdate(draft,3,stationlessSerialParallelGraph()));
        var second=publish(draft,4,first.observedHead());
        assertThat(second.publication().snapshot().surveys().stream().filter(v->v.branchId().equals("parallel-a")).findFirst().orElseThrow().diagnostics()).contains("SURVEY_GRAPH_STALE");
        assertThat(exact(draft,first.publication().revisionId()).publication()).isEqualTo(first.publication());
    }
    @Test void draftEditsNeverRewritePublishedSnapshots() {
        publicationActor("LOCAL");UUID draft=create(UUID.randomUUID(),"original").draft().draftId();
        var original=publish(draft,1,null);
        drafts.execute(command(dz.sh.hidra.modules.topology.application.port.in.EngineeringNetworkDraftUseCase.Kind.UPDATE,draft,null,1L,"edited"));
        assertThat(exact(draft,original.publication().revisionId()).publication()).isEqualTo(original.publication());
        assertThat(current(draft).observedHead()).isEqualTo(original.observedHead());
        assertThatThrownBy(()->publish(draft,1,original.observedHead())).isInstanceOfSatisfying(EngineeringRevisionPublicationFailure.class,e->assertThat(e.reason()).isEqualTo(Reason.CONFLICT));
        assertThat(events(original.publication().snapshot().scope().contextId())).isEqualTo(1);
    }
    @Test void concurrentDraftCandidatesHaveOneContextHeadCasWinner() throws Exception {
        publicationActor("LOCAL");UUID first=create(UUID.randomUUID(),"first").draft().draftId(),second=create(UUID.randomUUID(),"second").draft().draftId();
        var auth=SecurityContextHolder.getContext().getAuthentication();var latch=new CountDownLatch(1);var count=new AtomicInteger();
        try(var pool=Executors.newFixedThreadPool(2)) {
            var jobs=new ArrayList<Future<?>>();
            for(UUID draft:List.of(first,second))jobs.add(pool.submit(()->{
                SecurityContextHolder.getContext().setAuthentication(auth);
                try{latch.await();publish(draft,1,null);count.incrementAndGet();}
                catch(EngineeringRevisionPublicationFailure expected){}catch(InterruptedException e){Thread.currentThread().interrupt();throw new AssertionError(e);}
                finally{SecurityContextHolder.clearContext();}
            }));
            latch.countDown();for(var job:jobs)job.get(10,TimeUnit.SECONDS);
        }
        assertThat(count.get()).isEqualTo(1);
        assertThat(current(first).observedHead()).isEqualTo(current(second).observedHead());
        assertThat(current(first).observedHead().epoch()).isEqualTo(1);
    }
    @Test void identicalReplayIsStableAndConflictingReplayIsRejected() {
        var actor=publicationActor("LOCAL");UUID draft=create(UUID.randomUUID(),"replay").draft().draftId();
        var command=publication(draft,1,null,UUID.randomUUID());var first=publications.executePublication(command);
        var second=publish(draft,1,first.observedHead());ambient(authenticate(actor).accessToken());
        var replay=publications.executePublication(command);
        assertThat(replay.replayed()).isTrue();assertThat(replay.receipt()).isEqualTo(first.receipt());
        assertThat(replay.publication()).isEqualTo(first.publication());assertThat(replay.observedHead()).isEqualTo(second.observedHead());
        assertThat(events(first.publication().snapshot().scope().contextId())).isEqualTo(2);
        var conflicting=publication(draft,1,first.observedHead(),command.requestId());
        assertThatThrownBy(()->publications.executePublication(conflicting)).isInstanceOfSatisfying(EngineeringRevisionPublicationFailure.class,e->assertThat(e.reason()).isEqualTo(Reason.CONFLICT));
        var request=publications.executePublication(query(Kind.READ_REQUEST,draft,command.requestId(),null,null,null));
        assertThat(request.receipt()).isEqualTo(first.receipt());
    }

    @Test void withdrawReactivateAndEffectiveBoundariesPreserveHistory() {
        publicationActor("LOCAL");UUID draft=create(UUID.randomUUID(),"effective").draft().draftId();
        var first=publish(draft,1,null);var withdrawn=transition(Kind.WITHDRAW,draft,first.observedHead());
        assertThat(current(draft).publication()).isNull();
        assertThat(publications.executePublication(query(Kind.READ_EFFECTIVE,draft,null,withdrawn.observedHead().effectiveAt(),null,null)).publication()).isNull();
        assertThat(publications.executePublication(query(Kind.READ_EFFECTIVE,draft,null,first.observedHead().effectiveAt(),null,null)).publication()).isEqualTo(first.publication());
        var active=transition(Kind.REACTIVATE,draft,withdrawn.observedHead());
        assertThat(current(draft).publication()).isEqualTo(first.publication());
        assertThatThrownBy(()->publications.executePublication(query(Kind.READ_EFFECTIVE,draft,null,dbNow().plusSeconds(60),null,null)))
            .isInstanceOfSatisfying(EngineeringRevisionPublicationFailure.class,e->assertThat(e.reason()).isEqualTo(Reason.INVALID));
        // Administrator-only constraint control: two genuine committed ledger rows share one microsecond.
        // This proves SQL tie ordering, not native authorization for administrator credentials.
        var connectionSource=administrator;
        new org.springframework.transaction.support.TransactionTemplate(new org.springframework.jdbc.datasource.DataSourceTransactionManager(connectionSource)).executeWithoutResult(status->{
            var admin=new JdbcTemplate(connectionSource);Head predecessor=active.observedHead();
            for(String action:List.of("WITHDRAW","REACTIVATE")) {
                UUID event=UUID.randomUUID();String state=action.equals("WITHDRAW")?"WITHDRAWN":"ACTIVE";long epoch=predecessor.epoch()+1;
                admin.update("""
                    INSERT INTO public.hidra_topology_engineering_publication_event
                    (event_id,context_id,epoch,operation,prior_revision_id,revision_id,resulting_state,request_id,command_sha256,
                     actor_binding_id,actor_session_id,actor_user_id,party_generation,station_evidence,
                     sampled_at,valid_until,effective_at,backend_pid,transaction_id)
                    SELECT ?,context_id,?,?,revision_id,revision_id,?,?,command_sha256,
                     actor_binding_id,actor_session_id,actor_user_id,party_generation,station_evidence,
                     effective_at,clock_timestamp()+interval '1 minute',effective_at,pg_backend_pid(),txid_current()
                    FROM public.hidra_topology_engineering_publication_event WHERE event_id=?
                    """,event,epoch,action,state,UUID.randomUUID(),active.observedHead().eventId());
                assertThat(admin.update("UPDATE public.hidra_topology_engineering_publication_head SET epoch=?,state=?,last_event_id=? WHERE context_id=?",
                    epoch,state,event,first.publication().snapshot().scope().contextId())).isEqualTo(1);
                predecessor=new Head(first.publication().snapshot().scope(),first.publication().revisionId(),epoch,
                    State.valueOf(state),event,active.observedHead().effectiveAt());
            }
        });
        var effective=publications.executePublication(query(Kind.READ_EFFECTIVE,draft,null,active.observedHead().effectiveAt(),null,null));
        assertThat(effective.events().getFirst().epoch()).isEqualTo(5);assertThat(effective.publication()).isEqualTo(first.publication());
        assertThat(exact(draft,first.publication().revisionId()).publication()).isEqualTo(first.publication());
    }
    @Test void exactReadAndKeysetHistorySurviveMoreThanHundredEvents() {
        publicationActor("LOCAL");UUID draft=create(UUID.randomUUID(),"history").draft().draftId();
        var first=publish(draft,1,null);Head head=first.observedHead();
        for(int n=1;n<103;n++)head=publish(draft,1,head).observedHead();
        var page=publications.executePublication(query(Kind.LIST_HISTORY,draft,null,null,10,null));
        assertThat(page.events()).hasSize(10);assertThat(page.events().getFirst().epoch()).isEqualTo(103);
        String cursor=page.nextCursor();assertThat(cursor).isNotBlank();
        publish(draft,1,head);var epochs=new ArrayList<Long>(page.events().stream().map(Event::epoch).toList());
        while(cursor!=null) {
            page=publications.executePublication(query(Kind.LIST_HISTORY,draft,null,null,10,cursor));
            epochs.addAll(page.events().stream().map(Event::epoch).toList());cursor=page.nextCursor();
        }
        assertThat(epochs).hasSize(103).doesNotHaveDuplicates().doesNotContain(104L);
        for(int n=0;n<epochs.size();n++)assertThat(epochs.get(n)).isEqualTo(103L-n);
        assertThat(exact(draft,first.publication().revisionId()).publication()).isEqualTo(first.publication());
        var foreign=new Scope(UUID.randomUUID(),COMPANY,network);
        String substitution=new dz.sh.hidra.modules.topology.infrastructure.persistence.adapter.EngineeringRevisionPublicationCursorCodec().encode(
            new Cursor(foreign,null,103,94));
        assertThatThrownBy(()->publications.executePublication(query(Kind.LIST_HISTORY,draft,null,null,10,substitution)))
            .isInstanceOfSatisfying(EngineeringRevisionPublicationFailure.class,e->assertThat(e.reason()).isEqualTo(Reason.INVALID));
    }
    @Test void stationOwnerAbaAndTransferMismatchHoldWithoutRebinding() {
        publicationActor("LOCAL");UUID draft=create(UUID.randomUUID(),"station").draft().draftId();
        graphOwner().executeGraph(graphUpdate(draft,1,stationGraph()));
        var control=publish(draft,2,null);
        jdbc.update("UPDATE public.hidra_topology_facility SET facility_kind='DEPOT' WHERE id=?",FACILITY);
        try{assertThatThrownBy(()->publish(draft,2,control.observedHead())).isInstanceOfSatisfying(EngineeringRevisionPublicationFailure.class,e->assertThat(e.reason()).isEqualTo(Reason.CONFLICT));}
        finally{jdbc.update("UPDATE public.hidra_topology_facility SET facility_kind='STATION' WHERE id=?",FACILITY);}
        jdbc.update("UPDATE public.hidra_topology_facility SET status='INACTIVE' WHERE id=?",FACILITY);
        try{assertThatThrownBy(()->publish(draft,2,control.observedHead())).isInstanceOfSatisfying(EngineeringRevisionPublicationFailure.class,e->assertThat(e.reason()).isEqualTo(Reason.CONFLICT));}
        finally{jdbc.update("UPDATE public.hidra_topology_facility SET status='ACTIVE' WHERE id=?",FACILITY);}
        var first=publish(draft,2,control.observedHead());long generation=jdbc.queryForObject("SELECT generation FROM public.hidra_topology_facility_owner_generation WHERE facility_id=?",Long.class,FACILITY);
        String other="publication-other-"+id();
        jdbc.update("INSERT INTO public.hidra_party_party(id,code,party_type_id,legal_name,country_code,status,created_at,updated_at) VALUES(?,?,'k-party-type','Synthetic foreign','DZ','ACTIVE',now(),now())",other,other);
        jdbc.update("UPDATE public.hidra_topology_facility SET owner_party_id=? WHERE id=?",other,FACILITY);
        assertThatThrownBy(()->exact(draft,first.publication().revisionId())).isInstanceOfSatisfying(EngineeringRevisionPublicationFailure.class,e->assertThat(e.reason()).isEqualTo(Reason.CONFLICT));
        jdbc.update("UPDATE public.hidra_topology_facility SET owner_party_id=? WHERE id=?",COMPANY,FACILITY);
        assertThat(jdbc.queryForObject("SELECT generation FROM public.hidra_topology_facility_owner_generation WHERE facility_id=?",Long.class,FACILITY)).isGreaterThan(generation);
        assertThatThrownBy(()->exact(draft,first.publication().revisionId())).isInstanceOfSatisfying(EngineeringRevisionPublicationFailure.class,e->assertThat(e.reason()).isEqualTo(Reason.CONFLICT));
        assertThat(jdbc.queryForObject("SELECT company_party_id FROM public.hidra_topology_engineering_context WHERE id=?",String.class,first.publication().snapshot().scope().contextId())).isEqualTo(COMPANY);
        var replacement=publish(draft,2,first.observedHead());assertThat(replacement.observedHead().epoch()).isEqualTo(3);
    }
    @Test void writerFirstAndOwnerFirstRetainAllThirtyEightFences() throws Exception {
        publicationActor("LOCAL");UUID draft=create(UUID.randomUUID(),"fences").draft().draftId();
        try(var writer=administrator.getConnection()) {
            writer.setAutoCommit(false);writer.createStatement().execute("LOCK TABLE public.hidra_topology_facility IN ROW EXCLUSIVE MODE");
            assertThatThrownBy(()->publish(draft,1,null)).isInstanceOf(EngineeringRevisionPublicationFailure.class);writer.rollback();
        }
        noPublication(draft);var blocked=new AtomicBoolean();var proof=new AtomicBoolean();
        doAnswer(invocation->{
            invocation.callRealMethod();
            var locks=runtimeJdbc.queryForList("SELECT mode FROM pg_locks WHERE pid=pg_backend_pid() AND granted AND locktype='relation' AND relation IN (SELECT oid FROM pg_class WHERE relnamespace='public'::regnamespace)",String.class);
            assertThat(locks.stream().filter("ShareRowExclusiveLock"::equals).count()).isEqualTo(34);
            try(var writer=administrator.getConnection()){
                writer.setAutoCommit(false);writer.createStatement().execute("SET LOCAL lock_timeout='55ms'");
                assertThatThrownBy(()->writer.createStatement().execute("UPDATE public.hidra_topology_facility SET status=status WHERE id='"+FACILITY+"'"))
                    .isInstanceOfSatisfying(SQLException.class,e->assertThat(e.getSQLState()).isEqualTo("55P03"));
                blocked.set(true);writer.rollback();
            }
            return null;
        }).when(publicationTarget()).resolve(any());
        doAnswer(invocation->{
            List<dz.sh.hidra.platform.persistence.ProtectedRelationFence> locks=invocation.getArgument(2);
            assertThat(locks).hasSize(38);assertThat(locks.stream().map(dz.sh.hidra.platform.persistence.ProtectedRelationFence::relation).distinct().count()).isEqualTo(38);
            assertThat(locks.stream().filter(f->f.mode()==dz.sh.hidra.platform.persistence.ProtectedRelationFence.Mode.ACCESS_SHARE).count()).isEqualTo(3);
            proof.set(true);return invocation.callRealMethod();
        }).when(publicationTarget()).finalGuard(any(),any(),anyList());
        publish(draft,1,null);assertThat(blocked.get()).isTrue();assertThat(proof.get()).isTrue();
    }
    @Test void expiredFinalGuardAndDirtyFlushRollBackAllPublicationEffects() {
        publicationActor("LOCAL");UUID draft=create(UUID.randomUUID(),"rollback").draft().draftId();
        doAnswer(invocation->{
            var sample=(dz.sh.hidra.modules.topology.application.port.out.EngineeringRevisionPublicationRepositoryPort.Observation)invocation.getArgument(1);
            var expired=new dz.sh.hidra.modules.topology.application.port.out.EngineeringRevisionPublicationRepositoryPort.Observation(
                sample.scope(),sample.actorUserId(),sample.nativeBindingId(),sample.sessionId(),sample.partyGeneration(),
                sample.sampledAt().minusSeconds(2),sample.sampledAt().minusSeconds(1),sample.backendPid(),sample.transactionId());
            ReflectionTestUtils.setField(invocation.getArgument(0),"observation",expired);
            doCallRealMethod().when(publicationTarget()).finalGuard(any(),any(),anyList());
            return publicationTarget().finalGuard(invocation.getArgument(0),expired,invocation.getArgument(2));
        }).when(publicationTarget()).finalGuard(any(),any(),anyList());
        assertThatThrownBy(()->publish(draft,1,null)).isInstanceOf(EngineeringRevisionPublicationFailure.class);noPublication(draft);
        doAnswer(invocation->{
            invocation.callRealMethod();
            var managed=em.find(dz.sh.hidra.modules.topology.infrastructure.persistence.entity.PipelineSystemJpaEntity.class,network);
            ReflectionTestUtils.setField(managed,"code","dirty-"+network);return null;
        }).when(publicationTarget()).resolve(any());
        assertThatThrownBy(()->publish(draft,1,null)).isInstanceOf(EngineeringRevisionPublicationFailure.class);noPublication(draft);
        doCallRealMethod().when(publicationTarget()).resolve(any());
        doAnswer(invocation->{
            Object result=invocation.callRealMethod();
            try{runtimeJdbc.queryForObject("SELECT 1",Integer.class);}catch(RuntimeException poisoned){/* swallowed intentionally */}
            return result;
        }).when(publicationTarget()).finalGuard(any(),any(),anyList());
        assertThatThrownBy(()->publish(draft,1,null)).isInstanceOf(EngineeringRevisionPublicationFailure.class);noPublication(draft);
    }
    @Test void catalogRoleAndLedgerTamperingFailClosed() {
        publicationActor("LOCAL");UUID draft=create(UUID.randomUUID(),"catalog").draft().draftId();
        jdbc.execute("ALTER TABLE public.hidra_topology_engineering_publication_snapshot DISABLE TRIGGER p25_publication_snapshot_insert");
        try{assertThatThrownBy(()->publish(draft,1,null)).isInstanceOf(EngineeringRevisionPublicationFailure.class);noPublication(draft);}
        finally{jdbc.execute("ALTER TABLE public.hidra_topology_engineering_publication_snapshot ENABLE TRIGGER p25_publication_snapshot_insert");}
        jdbc.execute("GRANT SELECT(revision_id) ON public.hidra_topology_engineering_publication_snapshot TO c3_source_claim_app");
        try{assertThatThrownBy(()->publish(draft,1,null)).isInstanceOf(EngineeringRevisionPublicationFailure.class);}
        finally{jdbc.execute("REVOKE SELECT(revision_id) ON public.hidra_topology_engineering_publication_snapshot FROM c3_source_claim_app");}
        jdbc.execute("GRANT DELETE ON public.hidra_topology_engineering_publication_event TO c3_publication_app");
        try{assertThatThrownBy(()->publish(draft,1,null)).isInstanceOf(EngineeringRevisionPublicationFailure.class);}
        finally{jdbc.execute("REVOKE DELETE ON public.hidra_topology_engineering_publication_event FROM c3_publication_app");}
        String function=jdbc.queryForObject("SELECT pg_get_functiondef('public.hidra_p25_publication_event_guard()'::regprocedure)",String.class);
        jdbc.execute("CREATE OR REPLACE FUNCTION public.hidra_p25_publication_event_guard() RETURNS trigger LANGUAGE plpgsql SECURITY DEFINER SET search_path=pg_catalog,pg_temp AS $x$ BEGIN RETURN NEW; END; $x$");
        try{assertThatThrownBy(()->publish(draft,1,null)).isInstanceOf(EngineeringRevisionPublicationFailure.class);}
        finally{jdbc.execute(function);}
        var first=publish(draft,1,null);
        for(String table:List.of("snapshot","event","head")) {
            assertThatThrownBy(()->jdbc.update("DELETE FROM public.hidra_topology_engineering_publication_"+table+" WHERE false"))
                .isInstanceOf(org.springframework.dao.DataIntegrityViolationException.class);
        }
        for(String table:List.of("snapshot","event"))
            assertThatThrownBy(()->jdbc.update("UPDATE public.hidra_topology_engineering_publication_"+table+" SET context_id=context_id WHERE false"))
                .isInstanceOf(org.springframework.dao.DataIntegrityViolationException.class);
        assertThatThrownBy(()->jdbc.update("UPDATE public.hidra_topology_engineering_publication_head SET epoch=epoch+2 WHERE context_id=?",first.publication().snapshot().scope().contextId()))
            .isInstanceOf(org.springframework.dao.DataIntegrityViolationException.class);
        assertThat(current(draft).observedHead().epoch()).isEqualTo(1);
    }
    @Test void observedCommitAndIndeterminateOutcomeNeverAutoRetry() throws Exception {
        publicationActor("LOCAL");UUID draft=create(UUID.randomUUID(),"commit").draft().draftId();
        var first=publish(draft,1,null);
        assertThat(jdbc.queryForObject("SELECT pg_xact_commit_timestamp(transaction_id::text::xid) IS NOT NULL FROM public.hidra_topology_engineering_publication_event WHERE event_id=?",Boolean.class,first.observedHead().eventId())).isTrue();
        var constructor=ProtectedTransactionExecutor.ExecutionFailure.class.getDeclaredConstructor(ProtectedTransactionExecutor.Outcome.class,Throwable.class);
        constructor.setAccessible(true);
        var unknown=constructor.newInstance(ProtectedTransactionExecutor.Outcome.INDETERMINATE,new IllegalStateException("simulated acknowledgement outcome; no real unknown COMMIT induced"));
        var simulated=mock(ProtectedTransactionExecutor.class);doThrow(unknown).when(simulated).executePublication(any(),any(),any());
        var coordinator=webContext.getBean(dz.sh.hidra.modules.topology.application.service.ProtectedEngineeringPublicationAdmissionCoordinator.class);
        var service=new dz.sh.hidra.modules.topology.application.service.EngineeringRevisionPublicationApplicationService(coordinator,publicationStorage,simulated);
        assertThatThrownBy(()->service.executePublication(publication(draft,1,first.observedHead(),UUID.randomUUID())))
            .isInstanceOfSatisfying(EngineeringRevisionPublicationFailure.class,e->assertThat(e.reason()).isEqualTo(Reason.INDETERMINATE));
        verify(simulated,times(1)).executePublication(any(),any(),any());
        assertThat(events(first.publication().snapshot().scope().contextId())).isEqualTo(1);
    }
}
