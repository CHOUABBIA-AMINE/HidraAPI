/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : ProtectedStationAdmissionPostgresIntegrationTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-18
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
@org.springframework.context.annotation.Import(ProtectedStationAdmissionPostgresIntegrationTest.Config.class)
public class ProtectedStationAdmissionPostgresIntegrationTest {
    @org.springframework.boot.test.context.TestConfiguration(proxyBeanMethods=false)
    static class Config {
        @org.springframework.context.annotation.Bean @org.springframework.context.annotation.Primary
        PlatformTransactionManager admissionFixtureManager(jakarta.persistence.EntityManagerFactory factory,javax.sql.DataSource ds) {
            return new dz.sh.hidra.platform.persistence.ProtectedJpaTransactionManager(factory,ds) {
                @Override protected void doBegin(Object transaction,TransactionDefinition definition) {
                    super.doBegin(transaction,definition);
                    var j=new JdbcTemplate(ds);
                    if(Boolean.TRUE.equals(j.queryForObject("SELECT EXISTS(SELECT 1 FROM pg_catalog.pg_roles WHERE rolname='c3_source_claim_app')",Boolean.class)))
                        j.queryForObject("SELECT pg_catalog.set_config('role','c3_source_claim_app',true)",String.class);
                }
            };
        }
    }
    @Autowired dz.sh.hidra.modules.topology.application.service.ProtectedStationAdmissionCoordinator coordinator;
    @Autowired dz.sh.hidra.modules.topology.application.port.in.ProtectedStationGraphOperationUseCase graphOperations;
    @MockitoSpyBean dz.sh.hidra.modules.topology.infrastructure.persistence.adapter.JdbcStationGraphProtectedPublicationAdapter graphPublication;

    @Autowired dz.sh.hidra.modules.party.application.contract.topology.TopologyProtectedPartyEligibilityContract partyOwner;
    @Autowired dz.sh.hidra.modules.topology.application.contract.station.StationProtectedFacilityOwnershipContract facilityOwner;
    @MockitoSpyBean dz.sh.hidra.modules.identity.infrastructure.persistence.adapter.JdbcIdentityTemporalEventCollector collector;

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
        r.add("spring.datasource.username", POSTGRES::getUsername);
        r.add("spring.datasource.password", POSTGRES::getPassword);
        r.add("spring.jpa.hibernate.ddl-auto", () -> "validate");
        r.add("spring.flyway.enabled", () -> "true");
    }

    @Autowired JdbcTemplate jdbc;
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

    @BeforeEach
    void initialize() {
        assertThat(jdbc.queryForObject("SHOW autovacuum",String.class)).isEqualTo("off");
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
        // Exercise genuine configured I/J paths before the executor's strict measured attempt.
        for(String type:List.of("LOCAL","OIDC")) {
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
    private dz.sh.hidra.modules.topology.application.service.ProtectedStationAdmissionCoordinator.Prepared kp(Fixture f) {
        originalSource(f);return coordinator.prepare();
    }
    private dz.sh.hidra.modules.topology.application.service.ProtectedStationAdmissionCoordinator.Contribution kr(
        dz.sh.hidra.modules.topology.application.service.ProtectedStationAdmissionCoordinator.Prepared pre,Request request,
        Runnable admitted,Runnable collected,Runnable beforeGuard) {
        var value=new AtomicReference<dz.sh.hidra.modules.topology.application.service.ProtectedStationAdmissionCoordinator.Contribution>();
        return executor.execute(()->{
            var a=coordinator.acquire(pre,request);admitted.run();var c=coordinator.collect(a);value.set(c);collected.run();coordinator.requireCurrent(a);return c;
        },em::flush,()->{
            beforeGuard.run();var c=value.get();
            Boolean ok=jdbc.queryForObject("SELECT t>=? AND t<? AND pg_catalog.pg_backend_pid()=? AND pg_catalog.txid_current()=? FROM (SELECT pg_catalog.clock_timestamp() AS t) observed",Boolean.class,Timestamp.from(c.sampledAt()),Timestamp.from(c.validUntil()),c.backendPid(),c.transactionId());
            if(!Boolean.TRUE.equals(ok))throw new IllegalStateException("Synthetic composition time/context guard rejected: operation="
                +c.operation()+", sampledAt="+c.sampledAt()+", validUntil="+c.validUntil()
                +", expectedPid="+c.backendPid()+", expectedXid="+c.transactionId()
                +", fixtureObservedAfterGuard="+Instant.now());
        });
    }
    private dz.sh.hidra.modules.topology.application.service.ProtectedStationAdmissionCoordinator.Contribution kr(Fixture f) {return kr(kp(f),request(),()->{},()->{},()->{});}
    private Fixture localPair() {var f=fixture("LOCAL");both(f,"author");return f;}
    private Connection writer() throws SQLException {return DriverManager.getConnection(POSTGRES.getJdbcUrl(),"c3_source_claim_app","synthetic-c3-test-only");}
    private String blocked(String sql) {
        var worker=Executors.newSingleThreadExecutor();var began=new CountDownLatch(1);
        try {var future=worker.submit(()->{try(var c=writer();var st=c.createStatement()) {st.execute("SET lock_timeout='1ms'");began.countDown();try{st.executeUpdate(sql);return "WROTE";}catch(SQLException expected){return expected.getSQLState();}}});
            assertThat(began.await(3,TimeUnit.SECONDS)).isTrue();return future.get(3,TimeUnit.SECONDS);
        }catch(Exception failure){throw new AssertionError(failure);}finally{worker.shutdownNow();try{assertThat(worker.awaitTermination(3,TimeUnit.SECONDS)).isTrue();}catch(InterruptedException failure){Thread.currentThread().interrupt();throw new AssertionError(failure);}}
    }
    private void heldWriterRejects(Fixture f,String sql) throws Exception {
        var pre=kp(f);
        try(var w=writer();var st=w.createStatement()) {w.setAutoCommit(false);st.executeUpdate(sql);assertThatThrownBy(()->kr(pre,request(),()->{},()->{},()->{})).isInstanceOf(ProtectedTransactionExecutor.ExecutionFailure.class);w.rollback();}
    }
    private void jpaWriterRejects(Fixture f,boolean partyRoot) throws Exception {
        var pre=kp(f);var ready=new CountDownLatch(1);var release=new CountDownLatch(1);var worker=Executors.newSingleThreadExecutor();
        try {
            var future=worker.submit(()->tx().execute(status->{
                Object entity=partyRoot ? em.find(dz.sh.hidra.modules.party.infrastructure.persistence.entity.PartyJpaEntity.class,COMPANY)
                    : em.find(dz.sh.hidra.modules.identity.infrastructure.persistence.entity.UserJpaEntity.class,f.user());
                ReflectionTestUtils.setField(entity,partyRoot?"legalName":"displayName","Synthetic JPA writer "+id());
                em.flush();ready.countDown();
                try {if(!release.await(5,TimeUnit.SECONDS))throw new IllegalStateException("Writer barrier timeout");}
                catch(InterruptedException failure){Thread.currentThread().interrupt();throw new IllegalStateException(failure);}
                status.setRollbackOnly();return true;
            }));
            assertThat(ready.await(5,TimeUnit.SECONDS)).isTrue();
            assertThatThrownBy(()->kr(pre,request(),()->{},()->{},()->{})).isInstanceOf(ProtectedTransactionExecutor.ExecutionFailure.class);
            release.countDown();assertThat(future.get(5,TimeUnit.SECONDS)).isTrue();
        } finally {release.countDown();worker.shutdownNow();assertThat(worker.awaitTermination(5,TimeUnit.SECONDS)).isTrue();}
    }
    private long partyGeneration() {return jdbc.queryForObject("SELECT generation FROM public.hidra_party_eligibility_generation WHERE party_id=?",Long.class,COMPANY);}

    /** Positive matrix setup only: retain immutable evidence while old expiry fixtures settle. */
    private void awaitStableTemporalCensus() {
        String census=(String)ReflectionTestUtils.getField(
            dz.sh.hidra.modules.identity.infrastructure.persistence.adapter.JdbcIdentityTemporalEventCollector.class,
            "TEMPORAL_SQL");
        assertThat(census).isNotBlank();
        long deadline=System.nanoTime()+TimeUnit.SECONDS.toNanos(15);
        while(true) {
            // Use the actual collector census, including retained native/source/session endpoints.
            // One read outside the protected executor; never change or delete provenance or dates.
            Timestamp edge=jdbc.queryForObject("WITH census AS MATERIALIZED ("+census+
                "), observed AS MATERIALIZED (SELECT clock_timestamp() AS t) "
                +"SELECT max(v.boundary) FROM census c CROSS JOIN observed o "
                +"CROSS JOIN LATERAL (VALUES(c.from_at),(c.to_at)) v(boundary) "
                +"WHERE v.boundary>o.t AND v.boundary<=o.t+interval '2 seconds'",Timestamp.class);
            if(edge==null)return;
            if(System.nanoTime()>=deadline)throw new AssertionError("Positive fixture has no stable temporal horizon; next boundary="+edge);
            long pause=Math.max(1,Math.min(2000,java.time.Duration.between(dbNow(),edge.toInstant()).toMillis()+5));
            try {Thread.sleep(pause);}
            catch(InterruptedException interrupted){Thread.currentThread().interrupt();throw new AssertionError(interrupted);}
        }
    }

    @Test void realProducerPairsAndOwnerWitnessesUseOnePhysicalSample() {
        for(String type:List.of("LOCAL","OIDC","LDAP","ACTIVE_DIRECTORY")) {
            cleanEvidence();var f=fixture(type);for(String op:OPS)both(f,op);
            // Warm this matrix's genuine provider/operation paths outside the executor budget.
            // No protected-attempt retry, deadline change or measured combination is removed.
            for(var operation:Operation.values()) {
                var prepared=originalSource(f);var warmRequest=new Request(operation,COMPANY,FACILITY);
                tx().executeWithoutResult(status->{
                    var i=paired.acquire(prepared,warmRequest);
                    var p=partyOwner.acquire(COMPANY);var owner=facilityOwner.acquire(FACILITY);
                    assertThat(paired.contribute(i).combinedDecision()).isEqualTo(Decision.PERMIT);
                    assertThat(partyOwner.read(p).partyId()).isEqualTo(COMPANY);
                    assertThat(facilityOwner.read(owner).ownerPartyId()).isEqualTo(COMPANY);
                    paired.requireCurrent(i);
                });
            }
            for(var operation:Operation.values()) {
                var prepared=kp(f);
                awaitStableTemporalCensus(); // Setup only; the following protected attempt runs once.
                var c=kr(prepared,new Request(operation,COMPANY,FACILITY),()->{},()->{},()->{});
                assertThat(c.userId()).isEqualTo(f.user());assertThat(c.partyGeneration()).isPositive();assertThat(c.facilityGeneration()).isPositive();assertThat(c.sampledAt()).isBefore(c.validUntil());
            }
        }
    }
    @Test void allRelationFencesPrecedeTheSingleEligibilitySample() {
        var f=localPair();var samples=new AtomicInteger();
        doAnswer(i->{
            samples.incrementAndGet();
            @SuppressWarnings("unchecked")
            var prefix=(List<String>)ReflectionTestUtils.getField(JdbcIdentityAuthorityFence.class,"RELATIONS");
            assertThat(prefix).hasSize(22);
            var held=new HashSet<String>();
            jdbc.query("SELECT n.nspname||'.'||c.relname AS relation,l.mode FROM pg_catalog.pg_locks l JOIN pg_catalog.pg_class c ON c.oid=l.relation JOIN pg_catalog.pg_namespace n ON n.oid=c.relnamespace WHERE l.pid=pg_catalog.pg_backend_pid() AND l.granted AND l.locktype='relation'",
                (org.springframework.jdbc.core.RowCallbackHandler)row->held.add(row.getString(1)+":"+row.getString(2)));
            for(String relation:prefix)assertThat(held).contains(relation+":ShareRowExclusiveLock");
            assertThat(held).contains("public.hidra_identity_bound_source_claim_evidence:ShareLock");
            for(String relation:List.of("hidra_party_party","hidra_topology_facility"))assertThat(held).contains("public."+relation+":ShareRowExclusiveLock");
            for(String relation:List.of("hidra_identity_external_mapping_generation","hidra_party_eligibility_generation","hidra_topology_facility_owner_generation"))assertThat(held).contains("public."+relation+":AccessShareLock");
            return i.callRealMethod();
        }).when(org.springframework.test.util.AopTestUtils.<dz.sh.hidra.modules.identity.infrastructure.persistence.adapter.JdbcIdentityTemporalEventCollector>getUltimateTargetObject(collector)).collectFenced(anyString());
        var observed=new ArrayList<Instant>();
        doAnswer(i->{var result=i.callRealMethod();observed.add(((ProtectedOperationIdentityParticipant.QualifiedClaimRead)i.getArgument(0))==null?null:
            ((ProtectedOperationIdentityParticipant.Contribution)ReflectionTestUtils.getField((Object)i.getArgument(0),"contribution")).evidence().observedAt());return result;})
            .when(readerTarget()).loadQualified(any(),any());
        var result=kr(kp(f),request(),()->assertThat(samples).hasValue(0),()->assertThat(samples).hasValue(1),()->{});
        assertThat(samples).hasValue(1);assertThat(observed).containsExactly(result.sampledAt(),result.sampledAt());
    }
    @Test void bothScopesDenyOrUnknownNeverExportQualifiedOwnerAuthority() {
        var f=localPair();grant(f,"author","DENY","FACILITY",FACILITY);assertThatThrownBy(()->kr(f)).isInstanceOf(RuntimeException.class);
        cleanEvidence();both(f,"author");grant(f,"author","DENY","CUSTOM","topology-company:"+COMPANY);assertThatThrownBy(()->kr(f)).isInstanceOf(RuntimeException.class);
        cleanEvidence();assertThatThrownBy(()->kr(f)).isInstanceOf(RuntimeException.class);
    }
    @Test void partyStatusAndFacilityOwnershipMismatchFailClosed() {
        var f=localPair();jdbc.update("UPDATE public.hidra_party_party SET status='SUSPENDED' WHERE id=?",COMPANY);assertThatThrownBy(()->kr(f)).isInstanceOf(RuntimeException.class);
        jdbc.update("UPDATE public.hidra_party_party SET status='ACTIVE' WHERE id=?",COMPANY);jdbc.update("UPDATE public.hidra_topology_facility SET owner_party_id='other-company' WHERE id=?",FACILITY);assertThatThrownBy(()->kr(f)).isInstanceOf(RuntimeException.class);
    }
    @Test void identityNativeAndJpaWritersConflictBeforeAdmission() throws Exception {
        var f=localPair();heldWriterRejects(f,"UPDATE public.hidra_identity_user SET status=status WHERE id='"+f.user()+"'");jpaWriterRejects(f,false);
        var pre=kp(f);assertThatThrownBy(()->executor.execute(()->{em.createNativeQuery("UPDATE public.hidra_identity_user SET status=status WHERE id=:id").setParameter("id",f.user()).executeUpdate();return coordinator.acquire(pre,request());},em::flush,()->jdbc.queryForObject("SELECT 1",Integer.class))).isInstanceOf(RuntimeException.class);
    }
    @Test void partyAndFacilityNativeBulkAndJpaWritersConflictBeforeAdmission() throws Exception {
        var f=localPair();heldWriterRejects(f,"UPDATE public.hidra_party_party SET status=status");heldWriterRejects(f,"UPDATE public.hidra_topology_facility SET owner_party_id=owner_party_id");jpaWriterRejects(f,true);
        var pre=kp(f);assertThatThrownBy(()->executor.execute(()->{em.createNativeQuery("UPDATE public.hidra_party_party SET legal_name=legal_name WHERE id=:id").setParameter("id",COMPANY).executeUpdate();return coordinator.acquire(pre,request());},em::flush,()->jdbc.queryForObject("SELECT 1",Integer.class))).isInstanceOf(RuntimeException.class);
    }
    @Test void admissionFirstExcludesAuthorityWritersThroughCommitAndRollback() {
        var f=localPair();kr(kp(f),request(),()->{},()->{
            assertThat(blocked("UPDATE public.hidra_party_party SET status='SUSPENDED' WHERE id='"+COMPANY+"'")).isEqualTo("55P03");
            assertThat(blocked("UPDATE public.hidra_topology_facility SET owner_party_id='other-company' WHERE id='"+FACILITY+"'")).isEqualTo("55P03");
        },()->{});
        long before=partyGeneration();jdbc.update("UPDATE public.hidra_party_party SET status=status WHERE id=?",COMPANY);assertThat(partyGeneration()).isEqualTo(before+1);
        assertThatThrownBy(()->kr(kp(f),request(),()->{},()->{assertThat(blocked("UPDATE public.hidra_identity_user SET status=status WHERE id='"+f.user()+"'")).isEqualTo("55P03");throw new IllegalStateException("force rollback");},()->{})).isInstanceOf(RuntimeException.class);
    }
    @Test void missingIdentityAndOwnerRowsCannotRacePhantomAuthority() throws Exception {
        var f=localPair();var pre=kp(f);var req=new Request(Operation.AUTHOR,"missing-party",FACILITY);
        try(var w=writer();var st=w.createStatement()) {w.setAutoCommit(false);st.executeUpdate("INSERT INTO public.hidra_party_party(id,code,party_type_id,legal_name,country_code,status,created_at,updated_at) VALUES('missing-party','missing-party','k-party-type','Synthetic','DZ','ACTIVE',now(),now())");assertThatThrownBy(()->kr(pre,req,()->{},()->{},()->{})).isInstanceOf(RuntimeException.class);w.rollback();}
    }
    @Test void committedRevocationAndOwnerAbaAreObservedBeforeAdmission() {
        var f=localPair();var c=kr(f);jdbc.update("UPDATE public.hidra_topology_facility SET owner_party_id='other-company' WHERE id=?",FACILITY);jdbc.update("UPDATE public.hidra_topology_facility SET owner_party_id=? WHERE id=?",COMPANY,FACILITY);assertThat(kr(f).facilityGeneration()).isEqualTo(c.facilityGeneration()+2);
        var pre=kp(f);jdbc.update("UPDATE public.hidra_identity_user SET status='INACTIVE' WHERE id=?",f.user());assertThatThrownBy(()->kr(pre,request(),()->{},()->{},()->{})).isInstanceOf(RuntimeException.class);
    }
    @Test void futureDenyAndSourceExpiryConstrainTheCommonUpperBound() {
        var f=localPair();String deny=grant(f,"author","DENY","FACILITY",FACILITY);Instant start=dbNow().plusSeconds(10);jdbc.update("UPDATE public.hidra_identity_user_permission_grant SET valid_from=?,valid_to=? WHERE id=?",Timestamp.from(start),Timestamp.from(start.plusSeconds(30)),deny);assertThat(kr(f).validUntil()).isEqualTo(start);
        cleanEvidence();var external=fixture("OIDC");both(external,"author");Instant deadline=Instant.now().plusSeconds(20).truncatedTo(ChronoUnit.SECONDS);ambient(completeOidc(external,deadline).accessToken());var c=kr(coordinator.prepare(),request(),()->{},()->{},()->{});assertThat(c.validUntil()).isBeforeOrEqualTo(deadline);
    }

    @Test void expiryBetweenAdmissionCollectionAndFinalGuardRollsBack() {
        var f=localPair();var pre=kp(f);String deny=grant(f,"author","DENY","FACILITY",FACILITY);
        Instant start=dbNow().plusMillis(350);jdbc.update("UPDATE public.hidra_identity_user_permission_grant SET valid_from=?,valid_to=? WHERE id=?",Timestamp.from(start),Timestamp.from(start.plusSeconds(60)),deny);
        var reachedGuard=new AtomicBoolean();
        var captured=new AtomicReference<dz.sh.hidra.modules.topology.application.service.ProtectedStationAdmissionCoordinator.Contribution>();
        assertThatThrownBy(()->executor.execute(()->{
            var a=coordinator.acquire(pre,request());var result=coordinator.collect(a);captured.set(result);return result;
        },em::flush,()->{
            reachedGuard.set(true);var c=captured.get();
            Boolean permitted=jdbc.queryForObject("WITH waited AS MATERIALIZED (SELECT pg_catalog.pg_sleep(GREATEST(0,EXTRACT(EPOCH FROM (?::timestamptz-pg_catalog.clock_timestamp()))))), observed AS MATERIALIZED (SELECT pg_catalog.clock_timestamp() AS t FROM waited) SELECT t>=? AND t<? FROM observed",
                Boolean.class,Timestamp.from(c.validUntil()),Timestamp.from(c.sampledAt()),Timestamp.from(c.validUntil()));
            assertThat(permitted).isFalse();throw new IllegalStateException("Actual common deadline reached");
        })).isInstanceOf(ProtectedTransactionExecutor.ExecutionFailure.class);
        assertThat(reachedGuard).isTrue();assertThat(captured.get().validUntil()).isEqualTo(start);
    }
    @Test void forgedReplayedSuspendedAndChangedAuthenticationTicketsFail() {
        var f=localPair();var pre=kp(f);kr(pre,request(),()->{},()->{},()->{});assertThatThrownBy(()->kr(pre,request(),()->{},()->{},()->{})).isInstanceOf(RuntimeException.class);
        var next=kp(f);assertThatThrownBy(()->kr(next,request(),()->SecurityContextHolder.clearContext(),()->{},()->{})).isInstanceOf(RuntimeException.class);
        assertThatThrownBy(()->coordinator.collect(null)).isInstanceOf(RuntimeException.class);
        assertThatThrownBy(()->kr(kp(f),request(),()->{var tx=new TransactionTemplate(manager);tx.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);tx.execute(s->null);},()->{},()->{})).isInstanceOf(RuntimeException.class);
    }
    @Test void changedPhysicalContextAndWrongIsolationFail() {
        var f=localPair();var pre=kp(f);var t=tx();t.setIsolationLevel(TransactionDefinition.ISOLATION_REPEATABLE_READ);assertThatThrownBy(()->t.execute(s->coordinator.acquire(pre,request()))).isInstanceOf(RuntimeException.class);
        assertThatThrownBy(()->kr(kp(f),request(),()->{try(var c=jdbc.getDataSource().getConnection()){c.getMetaData();}catch(SQLException failure){throw new IllegalStateException(failure);}},()->{},()->{})).isInstanceOf(RuntimeException.class);
    }
    @Test void priorSqlAndDirtyFlushCannotBeAdopted() {
        var f=localPair();var pre=kp(f);assertThatThrownBy(()->executor.execute(()->{jdbc.queryForObject("SELECT 1",Integer.class);return coordinator.acquire(pre,request());},em::flush,()->jdbc.queryForObject("SELECT 1",Integer.class))).isInstanceOf(RuntimeException.class);
        var next=kp(f);assertThatThrownBy(()->executor.execute(()->{var a=coordinator.acquire(next,request());var result=coordinator.collect(a);
            var entity=em.find(dz.sh.hidra.modules.party.infrastructure.persistence.entity.PartyJpaEntity.class,COMPANY);ReflectionTestUtils.setField(entity,"legalName","dirty before flush");return result;
        },em::flush,()->jdbc.queryForObject("SELECT 1",Integer.class))).isInstanceOf(RuntimeException.class);
    }
    @Test void caughtOwnerOrSqlFailureCannotCommitPartialWork() {
        var f=localPair();var pre=kp(f);long before=partyGeneration();assertThatThrownBy(()->executor.execute(()->{var a=coordinator.acquire(pre,request());try{jdbc.update("UPDATE public.hidra_party_party SET status='SUSPENDED' WHERE id=?",COMPANY);}catch(RuntimeException ignored){}return coordinator.collect(a);},em::flush,()->jdbc.queryForObject("SELECT 1",Integer.class))).isInstanceOf(RuntimeException.class);assertThat(partyGeneration()).isEqualTo(before);
    }
    @Test void noResultEscapesBeforePhysicalCommitAndUnknownCommitDoesNotRetry() {
        var f=localPair();var pre=kp(f);var committed=new AtomicBoolean();var invocations=new AtomicInteger();
        var c=kr(pre,request(),()->{invocations.incrementAndGet();TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronization(){@Override public void afterCommit(){committed.set(true);}});},()->assertThat(committed).isFalse(),()->{});assertThat(committed).isTrue();assertThat(c).isNotNull();assertThat(invocations).hasValue(1);
        var post=kp(f);var calls=new AtomicInteger();assertThatThrownBy(()->kr(post,request(),()->{calls.incrementAndGet();TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronization(){@Override public void afterCommit(){throw new IllegalStateException("unknown acknowledgement");}});},()->{},()->{})).isInstanceOf(ProtectedTransactionExecutor.ExecutionFailure.class).satisfies(x->assertThat(((ProtectedTransactionExecutor.ExecutionFailure)x).outcome()).isEqualTo(ProtectedTransactionExecutor.Outcome.INDETERMINATE));assertThat(calls).hasValue(1);
    }
    @Test void legacyIdentityAndClaimIssuancePathsRetainTheirSemantics() {
        var f=fixture("OIDC");both(f,"author");permit(run(originalSource(f),request()));assertThat(kr(f).userId()).isEqualTo(f.user());
    }
    @Test void incomingFkAndPlanningRowShareRemainCompatible() {
        var f=localPair();kr(kp(f),request(),()->{},()->{
            var worker=Executors.newSingleThreadExecutor();try{assertThat(worker.submit(()->{try(var w=writer();var st=w.createStatement()){w.setAutoCommit(false);st.execute("SET LOCAL lock_timeout='50ms'");st.executeQuery("SELECT id FROM public.hidra_party_party WHERE id='"+COMPANY+"' FOR SHARE").close();st.executeQuery("SELECT id FROM public.hidra_topology_facility WHERE id='"+FACILITY+"' FOR KEY SHARE").close();
                st.executeUpdate("INSERT INTO public.hidra_party_external_reference(id,party_id,external_system_type,external_system_code,external_reference,status,created_at,updated_at) VALUES('kchild-"+id()+"','"+COMPANY+"','OTHER','synthetic','synthetic','ACTIVE',now(),now())");w.rollback();return true;}}).get(3,TimeUnit.SECONDS)).isTrue();}catch(Exception failure){throw new AssertionError(failure);}finally{worker.shutdownNow();try{assertThat(worker.awaitTermination(3,TimeUnit.SECONDS)).isTrue();}catch(InterruptedException failure){Thread.currentThread().interrupt();throw new AssertionError(failure);}}
        },()->{});
    }
    @Test void allFencesReleaseOnlyAfterPhysicalCompletion() {
        var f=localPair();var a=new AtomicReference<dz.sh.hidra.modules.topology.application.service.ProtectedStationAdmissionCoordinator.Admission>();var pre=kp(f);
        executor.execute(()->{a.set(coordinator.acquire(pre,request()));return coordinator.collect(a.get());},em::flush,()->{assertThat(blocked("UPDATE public.hidra_party_party SET status=status WHERE id='"+COMPANY+"'")).isEqualTo("55P03");jdbc.queryForObject("SELECT 1",Integer.class);});
        assertThatThrownBy(()->coordinator.requireCurrent(a.get())).isInstanceOf(RuntimeException.class);assertThat(blocked("UPDATE public.hidra_party_party SET status=status WHERE id='"+COMPANY+"'")).isEqualTo("WROTE");
    }
    private dz.sh.hidra.modules.topology.domain.model.StationInternalGraphRevision graphRevision(String revision,String predecessor) {
        var g=new dz.sh.hidra.modules.topology.domain.model.StationInternalGraph(FACILITY,revision,
            dz.sh.hidra.modules.topology.domain.model.StationInternalGraph.Status.COMPLETE,
            List.of(new dz.sh.hidra.modules.topology.domain.model.StationInternalGraph.Header("s",
                        dz.sh.hidra.modules.topology.domain.model.StationInternalGraph.HeaderKind.SUCTION,"sp"),
                    new dz.sh.hidra.modules.topology.domain.model.StationInternalGraph.Header("d",
                        dz.sh.hidra.modules.topology.domain.model.StationInternalGraph.HeaderKind.DISCHARGE,"dp")),
            List.of(new dz.sh.hidra.modules.topology.domain.model.StationInternalGraph.Unit("u",FACILITY,"in","out")),
            List.of(new dz.sh.hidra.modules.topology.domain.model.StationInternalGraph.Edge("a","sp","in"),
                    new dz.sh.hidra.modules.topology.domain.model.StationInternalGraph.Edge("b","out","dp")));
        var at=Instant.parse("2026-10-18T00:00:00Z");
        return new dz.sh.hidra.modules.topology.domain.model.StationInternalGraphRevision(FACILITY,revision,g,
            dz.sh.hidra.modules.topology.domain.model.StationInternalGraphRevision.SourceClassification.SYNTHETIC,
            at,at,null,"synthetic-source",predecessor,
            dz.sh.hidra.modules.topology.domain.model.StationInternalGraphRevision.Qualification.STRUCTURALLY_QUALIFIED);
    }
    private Fixture graphActor(String type) {
        cleanEvidence();var f=fixture(type);for(String op:OPS)both(f,op);return f;
    }
    private dz.sh.hidra.modules.topology.application.port.in.ProtectedStationGraphOperationUseCase.Result graphRun(
        Fixture actor,dz.sh.hidra.modules.topology.application.port.in.ProtectedStationGraphOperationUseCase.Kind kind,
        String revision,Long expected,String prior,
        dz.sh.hidra.modules.topology.domain.model.StationInternalGraphRevision authored) {
        originalSource(actor);awaitStableTemporalCensus();
        return graphOperations.execute(new dz.sh.hidra.modules.topology.application.port.in.ProtectedStationGraphOperationUseCase.Command(
            kind,COMPANY,FACILITY,revision,expected,prior,null,authored,100));
    }
    private long graphRows(String table,String revision) {
        return jdbc.queryForObject("SELECT count(*) FROM public.hidra_topology_station_graph_"+table+
            " WHERE facility_id=?"+(revision==null?"":" AND revision_id=?"),Long.class,
            revision==null?new Object[]{FACILITY}:new Object[]{FACILITY,revision});
    }
    @Test void genuinePublicationLifecycleUsesEveryProviderAndSameOwnerSample() {
        var types=List.of("LOCAL","OIDC","LDAP","ACTIVE_DIRECTORY");
        for(String type:types) {
            var actor=graphActor(type);String rev="live-"+id();
            var existing=jdbc.queryForList("SELECT revision_id,head_epoch FROM public.hidra_topology_station_graph_head WHERE facility_id=?",FACILITY);
            String predecessor=existing.isEmpty()?null:(String)existing.getFirst().get("revision_id");
            long epoch=existing.isEmpty()?0:((Number)existing.getFirst().get("head_epoch")).longValue();
            var authored=graphRun(actor,dz.sh.hidra.modules.topology.application.port.in.ProtectedStationGraphOperationUseCase.Kind.AUTHOR,
                rev,null,null,graphRevision(rev,predecessor));
            assertThat(authored.observedCommit()).isTrue();assertThat(authored.revision().revisionId()).isEqualTo(rev);
            var pub=graphRun(actor,dz.sh.hidra.modules.topology.application.port.in.ProtectedStationGraphOperationUseCase.Kind.PUBLISH,
                rev,epoch,predecessor,null);
            assertThat(pub.observedCommit()).isTrue();assertThat(pub.head().epoch()).isEqualTo(epoch+1);
            var history=graphRun(actor,dz.sh.hidra.modules.topology.application.port.in.ProtectedStationGraphOperationUseCase.Kind.LIST_HISTORY,
                null,null,null,null).history();
            assertThat(history).hasSizeGreaterThanOrEqualTo(2);
            for(var e:history.stream().filter(e->e.resultingRevisionId().equals(rev)).toList()) {
                assertThat(e.actorUserId()).isEqualTo(actor.user());
                assertThat(e.companyPartyId()).isEqualTo(COMPANY);
                assertThat(e.partyGeneration()).isEqualTo(partyGeneration());
                assertThat(e.facilityGeneration()).isEqualTo(jdbc.queryForObject("SELECT generation FROM public.hidra_topology_facility_owner_generation WHERE facility_id=?",Long.class,FACILITY));
                assertThat(jdbc.queryForObject("SELECT user_id=? AND session_id=? FROM public.hidra_identity_operation_binding WHERE binding_id=?",Boolean.class,e.actorUserId(),e.actorSessionId(),e.actorBindingId())).isTrue();
                assertThat(e.sampledAt()).isBefore(e.validUntil());
            }
            assertThat(graphRun(actor,dz.sh.hidra.modules.topology.application.port.in.ProtectedStationGraphOperationUseCase.Kind.READ_CURRENT,
                null,null,null,null).revision().revisionId()).isEqualTo(rev);
            assertThat(graphRun(actor,dz.sh.hidra.modules.topology.application.port.in.ProtectedStationGraphOperationUseCase.Kind.READ_HISTORY,
                rev,null,null,null).revision().revisionId()).isEqualTo(rev);
            var withdrawn=graphRun(actor,dz.sh.hidra.modules.topology.application.port.in.ProtectedStationGraphOperationUseCase.Kind.WITHDRAW,
                null,epoch+1,rev,null);
            assertThat(withdrawn.head().epoch()).isEqualTo(epoch+2);
            assertThat(graphRun(actor,dz.sh.hidra.modules.topology.application.port.in.ProtectedStationGraphOperationUseCase.Kind.READ_CURRENT,
                null,null,null,null).revision()).isNull();
            assertThat(graphRun(actor,dz.sh.hidra.modules.topology.application.port.in.ProtectedStationGraphOperationUseCase.Kind.REACTIVATE,
                null,epoch+2,rev,null).head().epoch()).isEqualTo(epoch+3);
        }
    }

    @Test void realPublicationRejectsUnauthorizedReadsBeforeHeadOrHistory() {
        var actor=graphActor("LOCAL");cleanEvidence();clearInvocations(graphPublication);
        for(var kind:List.of(READ_CURRENT,READ_HISTORY,LIST_HISTORY)) {
            assertThatThrownBy(()->graphRun(actor,kind,kind==READ_HISTORY?"unknown":null,null,null,null))
                .isInstanceOf(RuntimeException.class);
        }
        verify(graphPublication,never()).head(any());
        verify(graphPublication,never()).history(any(),anyInt());
        verify(graphPublication,never()).revision(any(),anyString());
    }
    @Test void realPublicationRefusesDirectPortAdmissionAndSqlRoleEscapes() throws Exception {
        assertThatThrownBy(()->tx().execute(status->graphPublication.acquire(FACILITY)))
            .isInstanceOf(RuntimeException.class);
        try(var c=writer();var st=c.createStatement()) {
            assertThatThrownBy(()->st.execute("ALTER TABLE public.hidra_topology_station_graph_head DISABLE TRIGGER ALL"))
                .isInstanceOf(SQLException.class);
            assertThatThrownBy(()->st.execute("TRUNCATE public.hidra_topology_station_graph_operation_event"))
                .isInstanceOf(SQLException.class);
            assertThatThrownBy(()->st.execute("CREATE OR REPLACE FUNCTION public.hidra_p25_graph_immutable_event() RETURNS trigger LANGUAGE plpgsql AS $$ BEGIN RETURN NEW; END; $$"))
                .isInstanceOf(SQLException.class);
        }
    }
    @Test void realPublicationRejectsDisabledCatalogGuardBeforeWriting() {
        var actor=graphActor("LOCAL");String revision="tamper-"+id();
        jdbc.execute("ALTER TABLE public.hidra_topology_station_graph_head DISABLE TRIGGER trg_p25_graph_head_transition");
        try {assertThatThrownBy(()->graphRun(actor,AUTHOR,revision,null,null,graphRevision(revision,null)))
                .isInstanceOf(RuntimeException.class);}
        finally {jdbc.execute("ALTER TABLE public.hidra_topology_station_graph_head ENABLE TRIGGER trg_p25_graph_head_transition");}
        assertThat(graphRows("revision",revision)).isZero();
    }
    @Test void realPublicationRollsBackRevisionAndEventOnActualFinalGuardExpiry() {
        var actor=graphActor("LOCAL");String revision="rollback-"+id();
        long before=graphRows("operation_event",null);
        doAnswer(i->{i.getArguments()[2]=i.getArgument(1);return i.callRealMethod();})
            .when(graphPublication).finalGuard(any(),any(),any(),anyString(),anyLong(),anyLong(),any(),any());
        assertThatThrownBy(()->graphRun(actor,AUTHOR,revision,null,null,graphRevision(revision,null)))
            .isInstanceOf(ProtectedTransactionExecutor.ExecutionFailure.class);
        verify(graphPublication).append(any(),any(),any());
        verify(graphPublication).finalGuard(any(),any(),any(),anyString(),anyLong(),anyLong(),any(),any());
        assertThat(graphRows("revision",revision)).isZero();
        assertThat(graphRows("operation_event",null)).isEqualTo(before);
    }
    @Test void realPublicationPoisonsCaughtUnreviewedProvisionalSql() {
        var actor=graphActor("LOCAL");String revision="poison-"+id();long before=graphRows("operation_event",null);
        doAnswer(i->{Object result=i.callRealMethod();
            try {jdbc.update("DELETE FROM public.hidra_topology_station_graph_head WHERE facility_id=?",FACILITY);}
            catch(RuntimeException expected){/* The real executor must remember this denial. */}
            return result;
        }).when(graphPublication).append(any(),any(),any());
        assertThatThrownBy(()->graphRun(actor,AUTHOR,revision,null,null,graphRevision(revision,null)))
            .isInstanceOf(ProtectedTransactionExecutor.ExecutionFailure.class);
        assertThat(graphRows("revision",revision)).isZero();
        assertThat(graphRows("operation_event",null)).isEqualTo(before);
    }
    @Test void realPublicationRetainsPhysicalCommitAndIndeterminateNoRetry() {
        var actor=graphActor("LOCAL");var committed=new AtomicBoolean();var acquired=new AtomicInteger();
        doAnswer(i->{var result=i.callRealMethod();acquired.incrementAndGet();
            TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronization(){
                @Override public void afterCommit(){committed.set(true);}
            });return result;
        }).when(graphPublication).acquire(anyString());
        String revision="committed-"+id();
        var result=graphRun(actor,AUTHOR,revision,null,null,graphRevision(revision,null));
        assertThat(committed).isTrue();assertThat(result.observedCommit()).isTrue();assertThat(acquired).hasValue(1);
        acquired.set(0);
        doAnswer(i->{var value=i.callRealMethod();acquired.incrementAndGet();
            TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronization(){
                @Override public void afterCommit(){throw new IllegalStateException("Synthetic lost acknowledgement");}
            });return value;
        }).when(graphPublication).acquire(anyString());
        String uncertain="uncertain-"+id();
        assertThatThrownBy(()->graphRun(actor,AUTHOR,uncertain,null,null,graphRevision(uncertain,null)))
            .isInstanceOf(ProtectedTransactionExecutor.ExecutionFailure.class)
            .satisfies(e->assertThat(((ProtectedTransactionExecutor.ExecutionFailure)e).outcome())
                .isEqualTo(ProtectedTransactionExecutor.Outcome.INDETERMINATE));
        assertThat(acquired).hasValue(1);assertThat(graphRows("revision",uncertain)).isEqualTo(1);
    }
    @Test void realPublicationUsesActualSingleIdentitySampleAndRejectsStaleEpoch() {
        var actor=graphActor("OIDC");String revision="sample-"+id();
        var observed=new AtomicReference<dz.sh.hidra.modules.identity.infrastructure.persistence.adapter.JdbcIdentityTemporalEventCollector.Candidate>();
        var count=new AtomicInteger();
        var target=org.springframework.test.util.AopTestUtils.<dz.sh.hidra.modules.identity.infrastructure.persistence.adapter.JdbcIdentityTemporalEventCollector>getUltimateTargetObject(collector);
        doAnswer(i->{var value=(dz.sh.hidra.modules.identity.infrastructure.persistence.adapter.JdbcIdentityTemporalEventCollector.Candidate)i.callRealMethod();observed.set(value);count.incrementAndGet();return value;})
            .when(target).collectFenced(anyString());
        assertThat(TransactionSynchronizationManager.isSynchronizationActive()).isFalse();
        graphRun(actor,AUTHOR,revision,null,null,graphRevision(revision,null));
        assertThat(TransactionSynchronizationManager.isSynchronizationActive()).isFalse();
        assertThat(TransactionSynchronizationManager.getResourceMap()).isEmpty();
        assertThat(jdbc.queryForObject("SELECT 1",Integer.class)).isEqualTo(1);
        assertThat(count).hasValue(1);
        var row=jdbc.queryForMap("SELECT sampled_at,backend_pid,transaction_id FROM public.hidra_topology_station_graph_operation_event WHERE facility_id=? AND resulting_revision_id=?",FACILITY,revision);
        assertThat(((Timestamp)row.get("sampled_at")).toInstant()).isEqualTo(observed.get().sampledAt());
        assertThat(((Number)row.get("backend_pid")).intValue()).isEqualTo(observed.get().backendPid());
        assertThat(((Number)row.get("transaction_id")).longValue()).isEqualTo(observed.get().transactionId());
        long before=graphRows("operation_event",null);
        assertThatThrownBy(()->graphRun(actor,PUBLISH,revision,Long.MAX_VALUE,"stale",null))
            .isInstanceOf(RuntimeException.class);
        assertThat(graphRows("operation_event",null)).isEqualTo(before);
    }
    @Test void realPublicationRejectsWriterFirstAndLiveOwnerTransfer() throws Exception {
        var actor=graphActor("LDAP");String revision="writer-"+id();
        try(var c=writer();var st=c.createStatement()) {
            c.setAutoCommit(false);
            st.execute("LOCK TABLE public.hidra_topology_station_graph_head IN SHARE ROW EXCLUSIVE MODE");
            assertThatThrownBy(()->graphRun(actor,AUTHOR,revision,null,null,graphRevision(revision,null)))
                .isInstanceOf(RuntimeException.class);
            c.rollback();
        }
        jdbc.update("UPDATE public.hidra_topology_facility SET owner_party_id='other-company' WHERE id=?",FACILITY);
        try {assertThatThrownBy(()->graphRun(actor,AUTHOR,revision,null,null,graphRevision(revision,null)))
                .isInstanceOf(RuntimeException.class);}
        finally {jdbc.update("UPDATE public.hidra_topology_facility SET owner_party_id=? WHERE id=?",COMPANY,FACILITY);}
        assertThat(graphRows("revision",revision)).isZero();
    }

    @Test void realPublicationRejectsCaughtSqlAfterActualFinalGuard() {
        var actor=graphActor("LOCAL");String revision="sealed-"+id();long before=graphRows("operation_event",null);
        doAnswer(i->{var result=i.callRealMethod();
            try {jdbc.queryForObject("SELECT 1",Integer.class);}
            catch(RuntimeException expected){/* Poisoning must survive a caught post-guard denial. */}
            return result;
        }).when(graphPublication).finalGuard(any(),any(),any(),anyString(),anyLong(),anyLong(),any(),any());
        assertThatThrownBy(()->graphRun(actor,AUTHOR,revision,null,null,graphRevision(revision,null)))
            .isInstanceOf(ProtectedTransactionExecutor.ExecutionFailure.class);
        verify(graphPublication).append(any(),any(),any());
        verify(graphPublication).finalGuard(any(),any(),any(),anyString(),anyLong(),anyLong(),any(),any());
        assertThat(graphRows("revision",revision)).isZero();assertThat(graphRows("operation_event",null)).isEqualTo(before);
    }

}
