/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : TopologyEngineeringOperatorQualificationTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-26
 *
 * @Type        : Class
 * @Layer       : Topology Test
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.qualification
 *
 * @Description : Qualifies native operator HTTP workflows, isolated scopes and immutable PostgreSQL history.
 *
 */
package dz.sh.hidra.modules.topology.qualification;

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
@Testcontainers
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
@org.springframework.context.annotation.Import(TopologyEngineeringOperatorQualificationTest.Config.class)
public class TopologyEngineeringOperatorQualificationTest {
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

    private void ambient(String raw) {
        var actual = new org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken(nativeDecoder.decode(raw), List.of());
        actual.setAuthenticated(true);
        SecurityContextHolder.getContext().setAuthentication(actual);
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
    private String COMPANY = "synthetic-company", FACILITY = "synthetic-facility";

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
    private long events(UUID context) {
        return jdbc.queryForObject("SELECT count(*) FROM public.hidra_topology_engineering_publication_event WHERE context_id=?",Long.class,context);
    }

    private static final String ROOT="/api/v1/topology/engineering-networks";
    private tools.jackson.databind.JsonNode send(String method,String url,Object body,int status) throws Exception {
        return sendAs(rawToken,method,url,body,status);
    }
    private tools.jackson.databind.JsonNode sendAs(String token,String method,String url,Object body,int status) throws Exception {
        SecurityContextHolder.clearContext();
        var request=org.springframework.test.web.servlet.request.MockMvcRequestBuilders.request(org.springframework.http.HttpMethod.valueOf(method),url);
        if(token!=null)request.header("Authorization","Bearer "+token);
        if(body!=null)request.contentType("application/json").content(body instanceof String text?text:JSON.writeValueAsString(body));
        try {
            var response=http.perform(request).andReturn().getResponse();
            assertThat(response.getStatus()).as("%s %s: %s",method,url,response.getContentAsString()).isEqualTo(status);
            return response.getContentAsString().isBlank()?JSON.createObjectNode():JSON.readTree(response.getContentAsString());
        } finally {SecurityContextHolder.clearContext();}
    }
    private String scoped(UUID draft,String suffix) {
        return ROOT+"/"+draft+suffix+"?companyPartyId="+COMPANY+"&pipelineSystemId="+network;
    }
    private String exchange(UUID draft,String editor,String suffix) {
        return scoped(draft,"/exchanges/"+editor+suffix)+(editor.equals("SURVEY")?"&branchId=parallel-a":"");
    }
    private Map<String,Object> createBody(String title) {
        return Map.of("companyPartyId",COMPANY,"pipelineSystemId",network,"creationRequestId",UUID.randomUUID().toString(),"title",title);
    }
    private UUID createHttp(String title) throws Exception {
        var draft=send("POST",ROOT,createBody(title),201);
        assertThat(draft.path("engineeringComplete").asBoolean()).isFalse();
        assertThat(draft.path("metadata").path("title").asText()).isEqualTo(title);
        assertThat(draft.path("metadata").has("description")).isFalse(); // configured NON_NULL wire representation
        return UUID.fromString(draft.path("draftId").asText());
    }
    private Map<String,Object> graphBody(long version) {
        var graph=surveyStationGraph();
        return Map.of("expectedVersion",version,"nodes",graph.nodes(),"ports",graph.ports(),"branches",graph.branches());
    }
    private String surveyBody(long version) {
        return """
            {"expectedVersion":%d,"profile":{"branchId":"parallel-a",
            "horizontalCrs":{"authority":"SYNTHETIC","code":"test","axes":[{"name":"north","unitIdentifier":"metre"},{"name":"east","unitIdentifier":"metre"}]},
            "verticalReference":null,"chainageOriginReference":"origin","direction":"FROM_TO","evidenceReference":"evidence",
            "points":[{"id":"a","firstCoordinate":"1.00","secondCoordinate":"2.000","chainageMetres":"0","elevation":null,"coordinatePrecision":"1","chainagePrecisionMetres":"1","elevationPrecision":null},
            {"id":"b","firstCoordinate":"3.000","secondCoordinate":"4.00","chainageMetres":"10","elevation":null,"coordinatePrecision":"1","chainagePrecisionMetres":"1","elevationPrecision":null}],"sections":[]}}
            """.formatted(version);
    }
    private UUID authoredHttp(String title) throws Exception {
        UUID draft=createHttp(title);
        send("PUT",scoped(draft,"/graph"),graphBody(1),200);
        var survey=send("PUT",scoped(draft,"/survey/parallel-a"),surveyBody(2),200);
        assertThat(survey.path("profile").path("points").get(0).path("firstCoordinate").asText()).isEqualTo("1.00");
        assertThat(survey.path("profile").path("points").get(0).has("elevation")).isFalse();
        return draft;
    }
    private Map<String,Object> publishBody(long version,tools.jackson.databind.JsonNode head,UUID request) {
        var body=new LinkedHashMap<String,Object>();body.put("requestId",request.toString());body.put("draftVersion",version);
        body.put("expectedEpoch",head==null?0:head.path("epoch").asLong());
        if(head!=null)body.put("expectedRevisionId",head.path("revisionId").asText());return body;
    }
    private tools.jackson.databind.JsonNode publishHttp(UUID draft,long version,tools.jackson.databind.JsonNode head) throws Exception {
        return send("POST",scoped(draft,"/publications"),publishBody(version,head,UUID.randomUUID()),200);
    }
    private Map<String,Object> transitionBody(tools.jackson.databind.JsonNode head) {
        return Map.of("requestId",UUID.randomUUID().toString(),"expectedEpoch",head.path("epoch").asLong(),"expectedRevisionId",head.path("revisionId").asText());
    }
    private Map<String,String> storageImage() {
        var image=new TreeMap<String,String>();
        for(String table:List.of("context","draft","draft_version","graph_snapshot","survey_snapshot","publication_snapshot","publication_event","publication_head"))
            image.put(table,jdbc.queryForObject("SELECT md5(coalesce(jsonb_agg(to_jsonb(t) ORDER BY to_jsonb(t)::text)::text,'[]')) FROM public.hidra_topology_engineering_"+table+" t",String.class));
        return image;
    }
    private void noLeak(tools.jackson.databind.JsonNode error,String... selectors) {
        String text=error.toString();for(String selector:selectors)assertThat(text).doesNotContain(selector);
    }
    private void addScopedGrants(Fixture actor,String company,String system) {
        for(String family:List.of("draft","publication"))for(String op:family.equals("draft")?List.of("create","read","update","validate"):List.of("publish","withdraw","reactivate","read"))
            for(String scope:List.of("topology-company:"+company,"topology-network:"+system))
                jdbc.update("INSERT INTO public.hidra_identity_user_permission_grant(id,user_id,permission_id,effect,scope_type,scope_reference_id,grant_reason,emergency_access,valid_from,valid_to,status,created_at) VALUES(?,?,?,'GRANT','CUSTOM',?,'synthetic independent operator',false,now()-interval '1 hour',now()+interval '1 hour','ACTIVE',now())",
                    id(),actor.user(),(family.equals("draft")?"draft-permission-":"publication-permission-")+op,scope);
    }
    private Fixture independentLocalActor(Fixture first) {
        // Reuse the active LOCAL provider. The historical fixture(type) helper rotates providers,
        // which would revoke the first operator and invalidate a two-active-principal assertion.
        String user=id(),credential=id();
        jdbc.update("INSERT INTO public.hidra_identity_user(id,username,user_type,status,failed_login_count,created_at,updated_at) VALUES(?,?,'HUMAN','ACTIVE',0,now(),now())",user,user);
        jdbc.update("INSERT INTO public.hidra_identity_local_credential(id,user_id,password_hash,credential_status,created_at,updated_at) VALUES(?,?,?,'ACTIVE',now(),now())",credential,user,passwords.encode("synthetic-password"));
        return new Fixture(user,first.provider(),null,credential,null,"LOCAL");
    }

    @Test void nativeOperatorsAuthorExchangeAndPublishExactSnapshotsAcrossFourProviders() throws Exception {
        tools.jackson.databind.JsonNode predecessor=null;
        for(String provider:List.of("LOCAL","OIDC","LDAP","ACTIVE_DIRECTORY")) {
            publicationActor(provider);UUID draft=authoredHttp("operator "+provider);
            send("POST",scoped(draft,"/validate"),Map.of("expectedVersion",3),200);
            var packages=new LinkedHashMap<String,tools.jackson.databind.JsonNode>();
            var before=storageImage();
            for(String editor:List.of("METADATA","GRAPH","SURVEY")) {
                var exported=send("GET",exchange(draft,editor,"/export"),null,200);
                assertThat(exported.path("engineeringQualified").asBoolean()).isFalse();packages.put(editor,exported.path("package"));
                var preview=send("POST",exchange(draft,editor,"/dry-runs"),Map.of("expectedVersion",3,"package",packages.get(editor)),200);
                assertThat(preview.path("observedVersion").asLong()).isEqualTo(3L);
                assertThat(preview.has("targetVersion")).isFalse();
                assertThat(preview.path("writeAuthorization").asText()).isEqualTo("NOT_EVALUATED");
                var capabilities=send("GET",exchange(draft,editor,"/capabilities"),null,200);
                assertThat(capabilities.path("maximumDecodedBytes").asInt()).isEqualTo(dz.sh.hidra.modules.topology.domain.model.EngineeringLocalExchange.Editor.valueOf(editor).maximumBytes());
            }
            assertThat(storageImage()).isEqualTo(before);
            long version=3;
            for(String editor:packages.keySet()) {
                var imported=send("POST",exchange(draft,editor,"/imports"),Map.of("expectedVersion",version,"package",packages.get(editor)),200);
                assertThat(imported.path("targetVersion").asLong()).isEqualTo(++version);
                assertThat(imported.path("engineeringQualified").asBoolean()).isFalse();
                var roundtrip=send("GET",exchange(draft,editor,"/export"),null,200).path("package");
                assertThat(roundtrip.path("payload")).isEqualTo(packages.get(editor).path("payload"));
                assertThat(roundtrip.path("sha256")).isEqualTo(packages.get(editor).path("sha256"));
                if(editor.equals("SURVEY")) {
                    assertThat(packages.get(editor).path("boundGraphVersion").asLong()).isEqualTo(2);
                    assertThat(roundtrip.path("boundGraphVersion").asLong()).isEqualTo(5);
                    var original=new SurveyRouteProfileCodec().decode(Base64.getDecoder().decode(roundtrip.path("payload").asText()));
                    assertThat(original.points().getFirst().firstCoordinate()).isEqualTo(new BigDecimal("1.00"));
                    assertThat(original.points().getFirst().secondCoordinate()).isEqualTo(new BigDecimal("2.000"));
                    assertThat(original.horizontalCrs().axes().getFirst().unitIdentifier()).isEqualTo("metre");
                    assertThat(original.points().getFirst().elevation()).isNull();
                }
            }
            var publication=publishHttp(draft,version,predecessor);predecessor=publication.path("head");
            var snapshot=publication.path("snapshot");assertThat(snapshot.path("engineeringQualified").asBoolean()).isFalse();
            assertThat(snapshot.path("graph").path("sha256")).isEqualTo(packages.get("GRAPH").path("sha256"));
            var exact=send("GET",scoped(draft,"/publications/revisions/"+snapshot.path("revisionId").asText()),null,200);
            assertThat(exact.path("snapshot")).isEqualTo(snapshot);
            assertThat(jdbc.queryForObject("SELECT max(version) FROM public.hidra_topology_engineering_draft_version WHERE draft_id=?",Long.class,draft)).isEqualTo(6L);
        }
    }

    private record Attempt(String method,String suffix,Object body) {}
    private List<Attempt> attempts(UUID draft,tools.jackson.databind.JsonNode publication,tools.jackson.databind.JsonNode pack) {
        var head=publication.path("head");return List.of(
            new Attempt("GET","",null),new Attempt("PUT","",Map.of("expectedVersion",3,"title","denied")),
            new Attempt("POST","/validate",Map.of("expectedVersion",3)),new Attempt("GET","/graph",null),new Attempt("PUT","/graph",graphBody(3)),
            new Attempt("GET","/survey/parallel-a",null),new Attempt("PUT","/survey/parallel-a",surveyBody(3)),
            new Attempt("GET","/exchanges/METADATA/export",null),new Attempt("GET","/exchanges/METADATA/capabilities",null),
            new Attempt("POST","/exchanges/METADATA/dry-runs",Map.of("expectedVersion",3,"package",pack)),
            new Attempt("POST","/exchanges/METADATA/imports",Map.of("expectedVersion",3,"package",pack)),
            new Attempt("POST","/publications",publishBody(3,null,UUID.fromString(publication.path("receipt").path("requestId").asText()))),
            new Attempt("POST","/publications/withdraw",transitionBody(head)),new Attempt("POST","/publications/reactivate",transitionBody(head)),
            new Attempt("GET","/publications/current",null),new Attempt("GET","/publications/revisions/"+head.path("revisionId").asText(),null),
            new Attempt("GET","/publications/effective",null),new Attempt("GET","/publications/history",null),
            new Attempt("GET","/publications/requests/"+publication.path("receipt").path("requestId").asText(),null));
    }
    @Test void twoCompaniesAndNetworksCannotReadWriteOrReplayEachOthersSelectors() throws Exception {
        var actorA=publicationActor("LOCAL");String companyA=COMPANY,networkA=network,facilityA=FACILITY,tokenA=rawToken;
        UUID a=authoredHttp("company A");var publishedA=publishHttp(a,3,null);
        var packA=send("GET",exchange(a,"METADATA","/export"),null,200).path("package");
        COMPANY="qualification-company-"+id();FACILITY="qualification-station-"+id();network="qualification-network-"+id();owners();
        jdbc.update("INSERT INTO public.hidra_topology_pipeline_system(id,code,system_type_id,status,created_at,updated_at) VALUES(?,?,'draft-type','DRAFT',now(),now())",network,network);
        String companyB=COMPANY,networkB=network;var actorB=independentLocalActor(actorA);addScopedGrants(actorB,COMPANY,network);rawToken=authenticate(actorB).accessToken();String tokenB=rawToken;
        UUID b=authoredHttp("company B");var publishedB=publishHttp(b,3,null);
        var packB=send("GET",exchange(b,"METADATA","/export"),null,200).path("package");var image=storageImage();
        // Each real actor lacks exactly one of the two required scopes in each substitution.
        for(int side=0;side<2;side++) {
            rawToken=side==0?tokenA:tokenB;UUID target=side==0?a:b;
            var publication=side==0?publishedA:publishedB;var pack=side==0?packA:packB;
            String ownCompany=side==0?companyA:companyB,ownNetwork=side==0?networkA:networkB;
            for(boolean companySwap:List.of(true,false)) {
                COMPANY=companySwap?(side==0?companyB:companyA):ownCompany;
                network=companySwap?ownNetwork:(side==0?networkB:networkA);
                var error=send("POST",ROOT,createBody("denied"),403);noLeak(error,companyA,companyB,networkA,networkB);
                for(var attempt:attempts(target,publication,pack)) {
                    String url=scoped(target,attempt.suffix());
                    if(attempt.suffix().endsWith("/effective"))url+="&asOf="+publication.path("head").path("effectiveAt").asText();
                    error=send(attempt.method(),url,attempt.body(),403);noLeak(error,companyA,companyB,networkA,networkB,target.toString());
                }
                assertThat(storageImage()).isEqualTo(image);
            }
        }
        COMPANY=companyA;network=networkA;FACILITY=facilityA;rawToken=tokenA;
        var unavailable=send("GET",exchange(a,"METADATA","/export").replace("companyPartyId="+companyA,"companyPartyId=absent-qualification-owner"),null,503);
        noLeak(unavailable,"absent-qualification-owner",a.toString());assertThat(storageImage()).isEqualTo(image);
    }

    @Test void currentDenyAndRevocationOverridePriorPreviewsAndUnrelatedRoutes() throws Exception {
        var actor=publicationActor("LOCAL");UUID draft=authoredHttp("current authority");
        var pack=send("GET",exchange(draft,"METADATA","/export"),null,200).path("package");
        var submission=Map.of("expectedVersion",3,"package",pack);var image=storageImage();
        send("POST",exchange(draft,"METADATA","/dry-runs"),submission,200);
        jdbc.update("UPDATE public.hidra_identity_user_permission_grant SET effect='DENY' WHERE user_id=? AND permission_id='draft-permission-update' AND scope_reference_id=?",actor.user(),"topology-network:"+network);
        send("POST",exchange(draft,"METADATA","/imports"),submission,403);
        jdbc.update("UPDATE public.hidra_identity_user_permission_grant SET effect='GRANT',status='REVOKED',revoked_at=now() WHERE user_id=? AND permission_id='draft-permission-update'",actor.user());
        send("POST",exchange(draft,"METADATA","/imports"),submission,403);
        jdbc.update("UPDATE public.hidra_identity_user_permission_grant SET status='REVOKED',revoked_at=now() WHERE user_id=? AND permission_id='publication-permission-publish'",actor.user());
        send("POST",scoped(draft,"/publications"),publishBody(3,null,UUID.randomUUID()),403);
        var ordinary=independentLocalActor(actor);String ordinaryToken=authenticate(ordinary).accessToken();
        sendAs(ordinaryToken,"POST",scoped(draft,"/publications"),publishBody(3,null,UUID.randomUUID()),403);
        sendAs(null,"GET",exchange(draft,"METADATA","/export"),null,401);
        sendAs(null,"GET",scoped(draft,"/publications/current"),null,401);
        // Exercise the actual interceptor bean for both a sibling path and wrong method tuple.
        var interceptor=webContext.getBean(dz.sh.hidra.platform.permissions.HidraRouteAuthorizationInterceptor.class);
        var controller=webContext.getBean(dz.sh.hidra.modules.topology.api.rest.controller.EngineeringLocalExchangeController.class);
        var method=controller.getClass().getMethod("export",String.class,String.class,String.class,String.class,String.class,Long.class);
        for(String route:List.of(ROOT+"/{draftId}/exchanges/{editor}/export/sibling",ROOT+"/{draftId}/exchanges/{editor}/export")) {
            ambient(rawToken);var request=new org.springframework.mock.web.MockHttpServletRequest();request.setMethod("POST");
            request.setAttribute(org.springframework.web.servlet.HandlerMapping.BEST_MATCHING_PATTERN_ATTRIBUTE,route);
            assertThatThrownBy(()->interceptor.preHandle(request,new org.springframework.mock.web.MockHttpServletResponse(),new org.springframework.web.method.HandlerMethod(controller,method)))
                .isInstanceOf(org.springframework.security.access.AccessDeniedException.class);
        }
        SecurityContextHolder.clearContext();assertThat(storageImage()).isEqualTo(image);
        try(var connection=DriverManager.getConnection(POSTGRES.getJdbcUrl(),"c3_source_claim_app","synthetic-c3-test-only")) {
            for(String table:List.of("snapshot","event","head"))
                assertThatThrownBy(()->connection.createStatement().executeQuery("SELECT * FROM public.hidra_topology_engineering_publication_"+table))
                    .isInstanceOfSatisfying(SQLException.class,e->assertThat(e.getSQLState()).isEqualTo("42501"));
        }
    }

    @Test void publishedHistoryRemainsExactAfterSourceEditsAndLifecycleTransitions() throws Exception {
        publicationActor("LOCAL");UUID draft=authoredHttp("immutable history");UUID request=UUID.randomUUID();
        var body=publishBody(3,null,request);var first=send("POST",scoped(draft,"/publications"),body,200);
        var snapshot=first.path("snapshot");var head=first.path("head");String revision=snapshot.path("revisionId").asText();
        send("PUT",scoped(draft,""),Map.of("expectedVersion",3,"title","later"),200);
        send("PUT",scoped(draft,"/graph"),graphBody(4),200);send("PUT",scoped(draft,"/survey/parallel-a"),surveyBody(5),200);
        assertThat(send("GET",scoped(draft,"/publications/revisions/"+revision),null,200).path("snapshot")).isEqualTo(snapshot);
        var replay=send("POST",scoped(draft,"/publications"),body,200);assertThat(replay.path("replayed").asBoolean()).isTrue();
        assertThat(replay.path("receipt")).isEqualTo(first.path("receipt"));
        send("POST",scoped(draft,"/publications"),publishBody(6,head,request),409);
        assertThat(send("GET",scoped(draft,"/publications/requests/"+request),null,200).path("receipt")).isEqualTo(first.path("receipt"));
        var withdrawn=send("POST",scoped(draft,"/publications/withdraw"),transitionBody(head),200);
        var currentWithdrawn=send("GET",scoped(draft,"/publications/current"),null,200);
        assertThat(currentWithdrawn.path("head").path("state").asText()).isEqualTo("WITHDRAWN");
        assertThat(currentWithdrawn.has("snapshot")).isFalse();
        var effectiveWithdrawn=send("GET",scoped(draft,"/publications/effective")+"&asOf="+withdrawn.path("head").path("effectiveAt").asText(),null,200);
        assertThat(effectiveWithdrawn.path("events").get(0).path("state").asText()).isEqualTo("WITHDRAWN");
        assertThat(effectiveWithdrawn.has("snapshot")).isFalse();
        assertThat(send("GET",scoped(draft,"/publications/effective")+"&asOf="+head.path("effectiveAt").asText(),null,200).path("snapshot")).isEqualTo(snapshot);
        head=send("POST",scoped(draft,"/publications/reactivate"),transitionBody(withdrawn.path("head")),200).path("head");
        assertThat(send("GET",scoped(draft,"/publications/current"),null,200).path("snapshot")).isEqualTo(snapshot);
        for(int n=0;n<100;n++)head=publishHttp(draft,6,head).path("head");
        var page=send("GET",scoped(draft,"/publications/history")+"&limit=10",null,200);
        var epochs=new ArrayList<Long>();String cursor;
        // A concurrent later append cannot contaminate a cursor's pinned initial census.
        publishHttp(draft,6,head);
        do {
            for(var event:page.path("events"))epochs.add(event.path("epoch").asLong());
            cursor=page.has("nextCursor")?page.path("nextCursor").asText():null;
            if(cursor!=null){
                assertThat(page.path("nextCursor").isString()).isTrue();assertThat(cursor).isNotBlank();
                page=send("GET",scoped(draft,"/publications/history")+"&limit=10&cursor="+java.net.URLEncoder.encode(cursor,StandardCharsets.UTF_8),null,200);
            }
        } while(cursor!=null);
        assertThat(epochs).hasSize(103).doesNotHaveDuplicates().doesNotContain(104L);
        for(int n=0;n<epochs.size();n++)assertThat(epochs.get(n)).isEqualTo(103L-n);
        assertThat(send("GET",scoped(draft,"/publications/revisions/"+revision),null,200).path("snapshot")).isEqualTo(snapshot);
    }

    private List<Integer> race(String method,List<String> urls,List<Object> bodies) throws Exception {
        String token=rawToken;var start=new CountDownLatch(1);
        try(var pool=Executors.newFixedThreadPool(2)) {
            var jobs=new ArrayList<Future<Integer>>();
            for(int n=0;n<2;n++){final int index=n;jobs.add(pool.submit(()->{
                start.await();SecurityContextHolder.clearContext();
                try {return http.perform(org.springframework.test.web.servlet.request.MockMvcRequestBuilders.request(org.springframework.http.HttpMethod.valueOf(method),urls.get(index))
                    .header("Authorization","Bearer "+token).contentType("application/json").content(JSON.writeValueAsString(bodies.get(index)))).andReturn().getResponse().getStatus();}
                finally{SecurityContextHolder.clearContext();}
            }));}
            start.countDown();var result=new ArrayList<Integer>();for(var job:jobs)result.add(job.get(20,TimeUnit.SECONDS));return result;
        }
    }
    @Test void concurrentHttpWritersAndPublishersHaveOneCasWinner() throws Exception {
        publicationActor("LOCAL");UUID draft=createHttp("CAS");
        assertThat(race("PUT",List.of(scoped(draft,""),scoped(draft,"")),List.of(Map.of("expectedVersion",1,"title","one"),Map.of("expectedVersion",1,"title","two"))))
            .containsExactlyInAnyOrder(200,409);
        assertThat(jdbc.queryForObject("SELECT count(*) FROM public.hidra_topology_engineering_draft_version WHERE draft_id=?",Long.class,draft)).isEqualTo(2L);
        assertThat(jdbc.queryForObject("SELECT max(version) FROM public.hidra_topology_engineering_draft_version WHERE draft_id=?",Long.class,draft)).isEqualTo(2L);
        UUID other=createHttp("other candidate");
        assertThat(race("POST",List.of(scoped(draft,"/publications"),scoped(other,"/publications")),List.of(publishBody(2,null,UUID.randomUUID()),publishBody(1,null,UUID.randomUUID()))))
            .containsExactlyInAnyOrder(200,409);
        var first=send("GET",scoped(draft,"/publications/current"),null,200).path("head");
        assertThat(first).isEqualTo(send("GET",scoped(other,"/publications/current"),null,200).path("head"));
        assertThat(first.path("epoch").asLong()).isEqualTo(1);
        UUID context=UUID.fromString(send("GET",scoped(draft,""),null,200).path("contextId").asText());
        assertThat(events(context)).isEqualTo(1L);
        assertThat(jdbc.queryForObject("SELECT count(*) FROM public.hidra_topology_engineering_publication_snapshot WHERE context_id=?",Long.class,context)).isEqualTo(1L);
        assertThat(jdbc.queryForObject("SELECT count(*) FROM public.hidra_topology_engineering_publication_head WHERE context_id=?",Long.class,context)).isEqualTo(1L);
        assertThat(jdbc.queryForObject("SELECT count(*) FROM public.hidra_topology_engineering_graph_snapshot WHERE draft_id IN (?,?)",Long.class,draft,other)).isZero();
        assertThat(jdbc.queryForObject("SELECT count(*) FROM public.hidra_topology_engineering_survey_snapshot WHERE draft_id IN (?,?)",Long.class,draft,other)).isZero();
    }

    @Test void finalGuardAndManagedFlushFaultsRollBackEveryOperatorEffect() throws Exception {
        publicationActor("LOCAL");UUID draft=authoredHttp("rollback");var image=storageImage();
        var pack=send("GET",exchange(draft,"METADATA","/export"),null,200).path("package");
        doAnswer(invocation->{
            var sample=(dz.sh.hidra.modules.topology.application.port.out.EngineeringRevisionPublicationRepositoryPort.Observation)invocation.getArgument(1);
            var expired=new dz.sh.hidra.modules.topology.application.port.out.EngineeringRevisionPublicationRepositoryPort.Observation(
                sample.scope(),sample.actorUserId(),sample.nativeBindingId(),sample.sessionId(),sample.partyGeneration(),sample.sampledAt().minusSeconds(2),sample.sampledAt().minusSeconds(1),sample.backendPid(),sample.transactionId());
            ReflectionTestUtils.setField(invocation.getArgument(0),"observation",expired);
            doCallRealMethod().when(publicationTarget()).finalGuard(any(),any(),anyList());
            return publicationTarget().finalGuard(invocation.getArgument(0),expired,invocation.getArgument(2));
        }).when(publicationTarget()).finalGuard(any(),any(),anyList());
        var failed=send("POST",scoped(draft,"/publications"),publishBody(3,null,UUID.randomUUID()),503);
        assertThat(failed.path("code").asText()).isEqualTo("UNAVAILABLE");assertThat(storageImage()).isEqualTo(image);
        doAnswer(invocation->{
            invocation.callRealMethod();var managed=em.find(dz.sh.hidra.modules.topology.infrastructure.persistence.entity.PipelineSystemJpaEntity.class,network);
            ReflectionTestUtils.setField(managed,"code","dirty-"+network);return null;
        }).when(publicationTarget()).resolve(any());
        send("POST",scoped(draft,"/publications"),publishBody(3,null,UUID.randomUUID()),503);assertThat(storageImage()).isEqualTo(image);
        assertThat(jdbc.queryForObject("SELECT code FROM public.hidra_topology_pipeline_system WHERE id=?",String.class,network)).isEqualTo(network);
        doCallRealMethod().when(publicationTarget()).resolve(any());
        var calls=new AtomicInteger();doAnswer(i->{if(calls.incrementAndGet()==2)throw new IllegalStateException("injected managed flush failure");return i.callRealMethod();}).when(draftTarget()).flush();
        send("POST",exchange(draft,"METADATA","/imports"),Map.of("expectedVersion",3,"package",pack),503);
        assertThat(calls.get()).isEqualTo(2);assertThat(storageImage()).isEqualTo(image);
        doCallRealMethod().when(draftTarget()).flush();
    }

    @Test void stationOwnershipAndCatalogCorruptionFailClosedWithoutHistoryMutation() throws Exception {
        publicationActor("LOCAL");UUID draft=authoredHttp("station ownership");var control=publishHttp(draft,3,null);
        String company=COMPANY;var image=storageImage();String foreign="qualification-transfer-"+id();
        jdbc.update("INSERT INTO public.hidra_party_party(id,code,party_type_id,legal_name,country_code,status,created_at,updated_at) VALUES(?,?,'k-party-type','Synthetic foreign','DZ','ACTIVE',now(),now())",foreign,foreign);
        long generation=jdbc.queryForObject("SELECT generation FROM public.hidra_topology_facility_owner_generation WHERE facility_id=?",Long.class,FACILITY);
        jdbc.update("UPDATE public.hidra_topology_facility SET owner_party_id=? WHERE id=?",foreign,FACILITY);
        try {send("GET",scoped(draft,"/publications/revisions/"+control.path("head").path("revisionId").asText()),null,409);}
        finally {jdbc.update("UPDATE public.hidra_topology_facility SET owner_party_id=? WHERE id=?",company,FACILITY);}
        assertThat(jdbc.queryForObject("SELECT generation FROM public.hidra_topology_facility_owner_generation WHERE facility_id=?",Long.class,FACILITY)).isGreaterThan(generation);
        send("GET",scoped(draft,"/publications/revisions/"+control.path("head").path("revisionId").asText()),null,409);
        assertThat(storageImage()).isEqualTo(image);
        // A replacement may capture current ownership, but retained history is never rebound.
        var replacement=publishHttp(draft,3,control.path("head"));image=storageImage();
        var brokenGraph=JSON.readTree(JSON.writeValueAsString(graphBody(3)));
        ((tools.jackson.databind.node.ObjectNode)brokenGraph.path("ports").get(0)).put("nodeId","absent-incidence-node");
        send("PUT",scoped(draft,"/graph"),brokenGraph,400);assertThat(storageImage()).isEqualTo(image);
        jdbc.update("UPDATE public.hidra_topology_facility SET facility_kind='DEPOT' WHERE id=?",FACILITY);
        try {send("POST",scoped(draft,"/publications"),publishBody(3,replacement.path("head"),UUID.randomUUID()),409);}
        finally {jdbc.update("UPDATE public.hidra_topology_facility SET facility_kind='STATION' WHERE id=?",FACILITY);}
        assertThat(storageImage()).isEqualTo(image);
        jdbc.execute("ALTER TABLE public.hidra_topology_engineering_publication_snapshot DISABLE TRIGGER p25_publication_snapshot_insert");
        try {send("POST",scoped(draft,"/publications"),publishBody(3,replacement.path("head"),UUID.randomUUID()),503);}
        finally {jdbc.execute("ALTER TABLE public.hidra_topology_engineering_publication_snapshot ENABLE TRIGGER p25_publication_snapshot_insert");}
        assertThat(storageImage()).isEqualTo(image);
        String function=jdbc.queryForObject("SELECT pg_get_functiondef('public.hidra_p25_publication_event_guard()'::regprocedure)",String.class);
        jdbc.execute("CREATE OR REPLACE FUNCTION public.hidra_p25_publication_event_guard() RETURNS trigger LANGUAGE plpgsql SECURITY DEFINER SET search_path=pg_catalog,pg_temp AS $x$ BEGIN RETURN NEW; END; $x$");
        try {send("POST",scoped(draft,"/publications"),publishBody(3,replacement.path("head"),UUID.randomUUID()),503);}
        finally {jdbc.execute(function);}
        assertThat(storageImage()).isEqualTo(image);
        jdbc.execute("GRANT DELETE ON public.hidra_topology_engineering_publication_event TO c3_publication_app");
        try {send("POST",scoped(draft,"/publications"),publishBody(3,replacement.path("head"),UUID.randomUUID()),503);}
        finally {jdbc.execute("REVOKE DELETE ON public.hidra_topology_engineering_publication_event FROM c3_publication_app");}
        assertThat(storageImage()).isEqualTo(image);
    }

    @Test void runtimeConsumerContractMatchesTypedRoutesErrorsAndBounds() throws Exception {
        publicationActor("LOCAL");UUID draft=authoredHttp("consumer contract");
        var docs=send("GET","/v3/api-docs",null,200);int operations=0;
        for(String path:docs.path("paths").propertyNames())if(path.startsWith(ROOT)) {
            var item=docs.path("paths").path(path);
            for(String verb:List.of("get","post","put"))if(item.has(verb)) {
                var operation=item.path(verb);operations++;
                assertThat(operation.path("operationId").asText()).isNotBlank();
                var responses=operation.path("responses");
                for(String code:List.of("200","400","401","403","404","409","503"))assertThat(responses.has(code)).as("%s %s %s",verb,path,code).isTrue();
                assertThat(responses.path("200").path("content").toString()).contains("#/components/schemas/");
                assertThat(responses.path("401").path("content").isMissingNode() || responses.path("401").path("content").isEmpty()).isTrue();
                assertThat(operation.has("security")?operation.path("security").toString():docs.path("security").toString()).contains("hidraBearerJwt");
                if(verb.equals("put") || verb.equals("post"))assertThat(operation.path("requestBody").path("content").toString()).contains("#/components/schemas/");
            }
        }
        assertThat(operations).isEqualTo(20);
        assertThat(docs.path("paths").path(ROOT).path("post").path("responses").has("201")).isTrue();
        var image=storageImage();
        send("GET",scoped(UUID.randomUUID(),""),null,404);
        var malformed=send("POST",scoped(draft,"/publications"),"{\"requestId\":false}",400);noLeak(malformed,COMPANY,network,draft.toString());
        send("PUT",scoped(draft,""),Map.of("expectedVersion",1,"title","stale"),409);
        for(String bad:List.of("0","101"))send("GET",scoped(draft,"/publications/history")+"&limit="+bad,null,400);
        send("GET",scoped(draft,"/publications/history")+"&cursor=invalid",null,400);
        for(String at:List.of("2020-01-01T00:00:00%2B00:00","2020-01-01T00:00:00.000000001Z"))
            send("GET",scoped(draft,"/publications/effective")+"&asOf="+at,null,400);
        send("GET",scoped(draft,"/publications/effective")+"&asOf=2020-01-01T00:00:00Z",null,200);
        send("GET",scoped(draft,"/exchanges/SURVEY/export"),null,400);
        send("GET",exchange(draft,"BATCH","/export"),null,400);
        send("GET",exchange(draft,"METADATA","/export")+"&version=0",null,400);
        var pack=send("GET",exchange(draft,"METADATA","/export"),null,200).path("package");
        send("POST",exchange(draft,"METADATA","/imports"),Map.of("package",pack),400);
        send("POST",exchange(draft,"METADATA","/imports"),"{\"expectedVersion\":3,\"expectedVersion\":3,\"package\":"+pack+"}",400);
        var oversized=send("POST",exchange(draft,"METADATA","/imports"),"{\"padding\":\""+"x".repeat(25165824)+"\"}",400);
        assertThat(oversized.path("detail").asText()).isEqualTo("Engineering local exchange unavailable");noLeak(oversized,COMPANY,network,draft.toString());
        assertThat(storageImage()).isEqualTo(image);
    }

}
