/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : ProtectedProductDraftAuthorizationPostgresIntegrationTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-26
 *
 * @Type        : Class
 * @Layer       : Identity Test
 * @Module      : identity
 * @Package     : dz.sh.hidra.modules.identity.infrastructure.persistence
 *
 * @Description : Verifies actual qualified paired owner decisions in one physical fenced PostgreSQL transaction.
 *
 */
package dz.sh.hidra.modules.identity.infrastructure.persistence;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import dz.sh.hidra.HidraApplication;
import dz.sh.hidra.modules.identity.application.contract.custody.CustodyProtectedProductDraftAuthorizationContract;
import dz.sh.hidra.modules.identity.application.contract.custody.CustodyProtectedProductDraftAuthorizationContract.Decision;
import dz.sh.hidra.modules.identity.application.contract.custody.CustodyProtectedProductDraftAuthorizationContract.Action;
import dz.sh.hidra.modules.identity.application.contract.custody.CustodyProtectedProductDraftAuthorizationContract.Request;
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
@org.springframework.test.annotation.DirtiesContext(classMode=org.springframework.test.annotation.DirtiesContext.ClassMode.AFTER_CLASS)
public class ProtectedProductDraftAuthorizationPostgresIntegrationTest {
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
                    .withDatabaseName("hidra_product_authority_test")
                    .withCommand(
                            "postgres",
                            "-c",
                            "log_error_verbosity=terse",
                            "-c",
                            "track_commit_timestamp=on",
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
    dz.sh.hidra.modules.identity.application.contract.custody
                    .CustodyProtectedProductDraftAuthorizationContract
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
        schema = "hapi594a_" + id().replace("-", "");
        jdbc.execute("CREATE SCHEMA " + schema);
        jdbc.execute("CREATE TABLE " + receipt() + "(id integer PRIMARY KEY)");
        c3NonownerCapture();
        // Warm the full qualified pair/evaluator path outside the protected executor budget.
        var f = fixture("LOCAL");
        both(f, "create");
        var p = prepare(f);
        tx().executeWithoutResult(
                        s -> {
                            c3SelectRestrictedRole();
                            // The guard may insert a sentinel, but must not require row disclosure.
                            assertThat(jdbc.queryForObject(
                                    "SELECT has_table_privilege(current_user,?,'INSERT') AND NOT "
                                            + "has_column_privilege(current_user,?,'id','SELECT')",
                                    Boolean.class, receipt(), receipt())).isTrue();
                            paired.contribute(paired.acquire(p, request()));
                        });
        cleanEvidence();
        clearInvocations(readerTarget(), identityTarget());
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
        return new Request(Action.CREATE, COMPANY, PRODUCT);
    }

    private CustodyProtectedProductDraftAuthorizationContract.Prepared prepare(Fixture f) {
        ambient(authenticate(f).accessToken());
        return paired.prepare();
    }

    private CustodyProtectedProductDraftAuthorizationContract.Prepared originalSource(Fixture f) {
        if (f.type().equals("LDAP") || f.type().equals("ACTIVE_DIRECTORY"))
            return c3DirectoryPrepared(f, "memberOf");
        return prepare(f);
    }

    private CustodyProtectedProductDraftAuthorizationContract.Contribution run(
            CustodyProtectedProductDraftAuthorizationContract.Prepared p, Request request) {
        return run(p, request, () -> {}, () -> {});
    }

    private CustodyProtectedProductDraftAuthorizationContract.Contribution run(
            CustodyProtectedProductDraftAuthorizationContract.Prepared p,
            Request request,
            Runnable inside,
            Runnable guardAction) {
        var saved = new AtomicReference<CustodyProtectedProductDraftAuthorizationContract.Contribution>();
        return executor.execute(
                () -> {
                    c3SelectRestrictedRole();
                    var ticket=paired.acquire(p, request);
                    var result = paired.contribute(ticket);
                    saved.set(result);
                    assertThat(jdbc.queryForObject("SELECT pg_backend_pid()", Integer.class))
                            .isEqualTo(result.backendPid());
                    assertThat(jdbc.queryForObject("SELECT txid_current()", Long.class))
                            .isEqualTo(result.transactionId());
                    inside.run();
                    paired.requireCurrent(ticket);
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

    private void permit(CustodyProtectedProductDraftAuthorizationContract.Contribution c) {
        assertThat(c.companyDecision()).isEqualTo(Decision.PERMIT);
        assertThat(c.productDecision()).isEqualTo(Decision.PERMIT);
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
    private static final List<String> OPS=List.of("create","read","update");
    private static final String COMPANY = "synthetic-company", PRODUCT = "synthetic-product";

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



    private String permission(String op) {
        String code = "custody:engineering-product:" + op;
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
                        + " (?,?,'custody','PRODUCT_ENGINEERING_DRAFT',?,false,'ACTIVE',clock_timestamp(),clock_timestamp())",
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
        grant(f, op, "GRANT", "CUSTOM", "custody-company:" + COMPANY);
        grant(f, op, "GRANT", "CUSTOM", "custody-product:"+PRODUCT);
    }







    private String policy(String effect, String context) {
        String p = id(), v = id();
        jdbc.update(
                "INSERT INTO "
                        + T
                        + "authorization_policy(id,code,name,policy_domain,status,created_at,updated_at)"
                        + " VALUES (?,?,?,'custody','ACTIVE',clock_timestamp(),clock_timestamp())",
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



    private CustodyProtectedProductDraftAuthorizationContract.Prepared c3DirectoryPrepared(
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


    @Test void fourNativeProducersRequireBothExactScopesForThreeActions() {
        for(String type:List.of("LOCAL","OIDC","LDAP","ACTIVE_DIRECTORY")) {
            cleanEvidence();var f=fixture(type);
            for(String action:OPS) {
                both(f,action);
                var request=new Request(Action.valueOf(action.toUpperCase(Locale.ROOT)),COMPANY,PRODUCT);
                var c=run(originalSource(f),request);permit(c);assertThat(c.request()).isEqualTo(request);
            }
        }
    }
    @Test void eitherScopeDenyAndMissingAuthorityCannotProducePartialPermit() {
        for(boolean company:List.of(true,false)) {
            cleanEvidence();var f=fixture("LOCAL");both(f,"create");
            grant(f,"create","DENY","CUSTOM",company?"custody-company:"+COMPANY:"custody-product:"+PRODUCT);
            var c=run(prepare(f),request());
            assertThat(company?c.companyDecision():c.productDecision()).isEqualTo(Decision.DENY);
        }
        cleanEvidence();var f=fixture("LOCAL");grant(f,"create","GRANT","CUSTOM","custody-company:"+COMPANY);
        assertThat(run(prepare(f),request()).productDecision()).isNotEqualTo(Decision.PERMIT);
    }
    @Test void companyProductAndActionCannotBeSubstituted() {
        var f=fixture("LOCAL");both(f,"create");
        assertThat(run(prepare(f),new Request(Action.CREATE,"another-company",PRODUCT)).companyDecision()).isNotEqualTo(Decision.PERMIT);
        assertThat(run(prepare(f),new Request(Action.CREATE,COMPANY,"another-product")).productDecision()).isNotEqualTo(Decision.PERMIT);
        var read=run(prepare(f),new Request(Action.READ,COMPANY,PRODUCT));
        assertThat(read.companyDecision()).isNotEqualTo(Decision.PERMIT);
        assertThat(read.productDecision()).isNotEqualTo(Decision.PERMIT);
    }
    @Test void topologyScopedGrantsNeverAuthorizeProductDrafts() {
        var f=fixture("LOCAL");
        grant(f,"create","GRANT","CUSTOM","topology-company:"+COMPANY);
        grant(f,"create","GRANT","CUSTOM","topology-network:"+PRODUCT);
        var c=run(prepare(f),request());
        assertThat(c.companyDecision()).isNotEqualTo(Decision.PERMIT);
        assertThat(c.productDecision()).isNotEqualTo(Decision.PERMIT);
    }
    @Test void preparedEntryCannotReplayOrMoveToDifferentAuthentication() {
        var f=fixture("LOCAL");both(f,"create");var p=prepare(f);permit(run(p,request()));
        rejected(()->run(p,request()));var other=prepare(f);
        SecurityContextHolder.clearContext();rejected(()->run(other,request()));
    }
    @Test void fabricatedPreparationAdmissionAndNativePairFailClosed() {
        var f=fixture("LOCAL");both(f,"create");prepare(f);
        rejected(()->tx().executeWithoutResult(s->paired.acquire(new CustodyProtectedProductDraftAuthorizationContract.Prepared(){},request())));
        rejected(()->tx().executeWithoutResult(s->paired.contribute(new CustodyProtectedProductDraftAuthorizationContract.Admission(){})));
        rejected(()->tx().executeWithoutResult(s->reader.loadQualifiedProductPair(null,request())));
    }
    @Test void consumedOrCompletedAdmissionCannotBeUsedAgain() {
        var f=fixture("LOCAL");both(f,"create");var p=prepare(f);
        var ticket=tx().execute(s->{c3SelectRestrictedRole();var a=paired.acquire(p,request());paired.contribute(a);return a;});
        rejected(()->tx().executeWithoutResult(s->paired.requireCurrent(ticket)));
        var next=prepare(f);
        rejected(()->tx().executeWithoutResult(s->{c3SelectRestrictedRole();var a=paired.acquire(next,request());paired.contribute(a);paired.contribute(a);}));
    }
    @Test void userProviderAndSessionRevocationsRejectPreparedNativeEvidence() {
        for(String target:List.of("user","provider","session")) {
            cleanEvidence();var f=fixture("LOCAL");both(f,"create");var p=prepare(f);
            switch(target) {
                case "user" -> jdbc.update("UPDATE "+U+" SET status='INACTIVE' WHERE id=?",f.user());
                case "provider" -> jdbc.update("UPDATE "+P+" SET status='INACTIVE' WHERE id=?",f.provider());
                case "session" -> jdbc.update("UPDATE "+S+" SET status='REVOKED',ended_at=clock_timestamp() WHERE user_id=? AND status='ACTIVE'",f.user());
                default -> throw new AssertionError();
            }
            rejected(()->run(p,request()));
        }
    }
    @Test void sameTransactionGrantRevocationIsDetectedAndRolledBack() {
        var f=fixture("LOCAL");both(f,"create");var p=prepare(f);
        rejected(()->tx().executeWithoutResult(s->{
            c3SelectRestrictedRole();var a=paired.acquire(p,request());paired.contribute(a);
            jdbc.update("UPDATE "+T+"user_permission_grant SET status='INACTIVE' WHERE user_id=?",f.user());
            paired.requireCurrent(a);
        }));
        assertThat(jdbc.queryForObject("SELECT count(*) FROM "+T+"user_permission_grant WHERE user_id=? AND status='INACTIVE'",Integer.class,f.user())).isZero();
    }
    @Test void grantExpiryUsesFreshDatabaseTimeForBothScopes() {
        var f=fixture("LOCAL");both(f,"create");var p=prepare(f);
        rejected(()->tx().executeWithoutResult(s->{
            c3SelectRestrictedRole();var a=paired.acquire(p,request());paired.contribute(a);
            // Same-transaction ordinary writer makes the actual grant expire at database time.
            jdbc.update("UPDATE "+T+"user_permission_grant SET valid_to=clock_timestamp() WHERE user_id=?",f.user());
            paired.requireCurrent(a);
        }));
    }
    @Test void expiredNativeDeadlineCannotSurviveFinalRecheck() {
        var f=fixture("OIDC");both(f,"create");
        ambient(completeOidc(f,Instant.now().plusSeconds(4).truncatedTo(ChronoUnit.SECONDS)).accessToken());
        var p=paired.prepare();var reachedDeadline=new AtomicBoolean();
        rejected(()->tx().executeWithoutResult(s->{
            c3SelectRestrictedRole();var a=paired.acquire(p,request());var c=paired.contribute(a);
            // Wait for the genuine native/source deadline; immutable binding data is never edited.
            jdbc.queryForObject("SELECT pg_sleep(LEAST(5,GREATEST(0,EXTRACT(EPOCH FROM (CAST(? AS timestamptz)-clock_timestamp())))+0.01))",Object.class,Timestamp.from(c.validUntil()));
            reachedDeadline.set(true);
            paired.requireCurrent(a);
        }));
        assertThat(reachedDeadline.get()).isTrue();
    }
    @Test void crossThreadRejectionPoisonsOriginalTransactionEvenWhenSwallowed() throws Exception {
        var f=fixture("LOCAL");both(f,"create");var p=prepare(f);
        var worker=Executors.newSingleThreadExecutor();
        try {
            rejected(()->tx().executeWithoutResult(s->{
                c3SelectRestrictedRole();var a=paired.acquire(p,request());paired.contribute(a);
                try {assertThat(worker.submit(()->{rejected(()->paired.requireCurrent(a));return true;}).get(5,TimeUnit.SECONDS)).isTrue();}
                catch(Exception failure){throw new AssertionError(failure);}
            }));
        } finally {worker.shutdownNow();assertThat(worker.awaitTermination(5,TimeUnit.SECONDS)).isTrue();}
    }
    @Test void suspendedTransactionCannotResumeOldAdmission() {
        var f=fixture("LOCAL");both(f,"create");var p=prepare(f);
        rejected(()->tx().executeWithoutResult(s->{
            c3SelectRestrictedRole();var a=paired.acquire(p,request());paired.contribute(a);
            var nested=tx();nested.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);
            nested.executeWithoutResult(n->{});paired.requireCurrent(a);
        }));
    }
    @Test void missingPermissionDefinitionCannotBeGrantedByCallerClaims() {
        var f=fixture("LOCAL");both(f,"create");var p=prepare(f);
        jdbc.update("UPDATE "+T+"permission SET status='INACTIVE' WHERE code='custody:engineering-product:create'");
        var c=run(p,request());assertThat(c.companyDecision()).isNotEqualTo(Decision.PERMIT);
        assertThat(c.productDecision()).isNotEqualTo(Decision.PERMIT);
    }

    @Test void unresolvedPolicyEvidenceRemainsIndeterminateDespiteBothGrants() {
        var f=fixture("LOCAL");both(f,"create");
        policy("PERMIT","{\"op\":\"eq\",\"attribute\":\"subject.type\",\"type\":\"NUMBER\",\"value\":1}");
        var c=run(prepare(f),request());
        assertThat(c.companyDecision()).isEqualTo(Decision.INDETERMINATE);
        assertThat(c.productDecision()).isEqualTo(Decision.INDETERMINATE);
    }

    @Test void permissionMigrationIsIdempotentRejectsMismatchAndAssignsNoGrants() throws Exception {
        String sql=java.nio.file.Files.readString(java.nio.file.Path.of(
                "src/main/resources/db/migration/V20261026_001__custody_product_draft_authority.sql"));
        Integer before=jdbc.queryForObject("SELECT count(*) FROM "+T+"user_permission_grant",Integer.class);
        jdbc.execute(sql);jdbc.execute(sql);
        assertThat(jdbc.queryForObject("SELECT count(*) FROM "+T+"permission WHERE code LIKE 'custody:engineering-product:%'",Integer.class)).isEqualTo(3);
        assertThat(jdbc.queryForObject("SELECT count(*) FROM "+T+"user_permission_grant",Integer.class)).isEqualTo(before);
        jdbc.update("UPDATE "+T+"permission SET resource_type='UNSUPPORTED' WHERE code='custody:engineering-product:read'");
        assertThatThrownBy(()->jdbc.execute(sql)).isInstanceOf(org.springframework.dao.DataAccessException.class);
        assertThat(jdbc.queryForObject("SELECT resource_type FROM "+T+"permission WHERE code='custody:engineering-product:read'",String.class)).isEqualTo("UNSUPPORTED");
    }
}
