/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : ProtectedOrganizationAppointmentAuthorizationPostgresIntegrationTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-21
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
import dz.sh.hidra.modules.identity.application.contract.organization.OrganizationProtectedAppointmentAuthorizationContract;
import dz.sh.hidra.modules.identity.application.contract.organization.OrganizationProtectedAppointmentAuthorizationContract.Decision;
import dz.sh.hidra.modules.identity.application.contract.organization.OrganizationProtectedAppointmentAuthorizationContract.Action;
import dz.sh.hidra.modules.identity.application.contract.organization.OrganizationProtectedAppointmentAuthorizationContract.*;
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
public class ProtectedOrganizationAppointmentAuthorizationPostgresIntegrationTest {
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
                    .withDatabaseName("hidra_appointment_test")
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
    dz.sh.hidra.modules.identity.application.contract.organization
                    .OrganizationProtectedAppointmentAuthorizationContract
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
        return new Request(Action.CREATE, List.of(new CompanyUnit(COMPANY, FACILITY)), EMPLOYEE);
    }

    private OrganizationProtectedAppointmentAuthorizationContract.Prepared prepare(Fixture f) {
        ambient(authenticate(f).accessToken());
        return paired.prepare();
    }

    private OrganizationProtectedAppointmentAuthorizationContract.Prepared originalSource(Fixture f) {
        if (f.type().equals("LDAP") || f.type().equals("ACTIVE_DIRECTORY"))
            return c3DirectoryPrepared(f, "memberOf");
        return prepare(f);
    }

    private OrganizationProtectedAppointmentAuthorizationContract.Contribution run(
            OrganizationProtectedAppointmentAuthorizationContract.Prepared p, Request request) {
        return run(p, request, () -> {}, () -> {});
    }

    private OrganizationProtectedAppointmentAuthorizationContract.Contribution run(
            OrganizationProtectedAppointmentAuthorizationContract.Prepared p,
            Request request,
            Runnable inside,
            Runnable guardAction) {
        var saved = new AtomicReference<OrganizationProtectedAppointmentAuthorizationContract.Contribution>();
        var ticket = new AtomicReference<OrganizationProtectedAppointmentAuthorizationContract.Admission>();
        return executor.execute(
                () -> {
                    c3SelectRestrictedRole();
                    var admission = paired.acquire(p, request);
                    ticket.set(admission);
                    var result = paired.contribute(admission);
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
                    assertThat(paired.finalFences(ticket.get())).isNotEmpty();
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

    private void permit(OrganizationProtectedAppointmentAuthorizationContract.Contribution c) {
        assertThat(c.overallDecision()).isEqualTo(Decision.PERMIT);
        assertThat(c.decisions()).allMatch(d -> d.decision() == Decision.PERMIT);
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
    private static final List<String> OPS=List.of("create","end","transfer","correct","read-current","read-history","read-policy");
    private static final String COMPANY = "synthetic-company", FACILITY = "synthetic-facility", EMPLOYEE = "synthetic-employee";

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

    private Action action(String verb) {
        return Action.valueOf(verb.toUpperCase(Locale.ROOT).replace('-', '_'));
    }

    private String permission(String op, ResourceKind kind) {
        String code = OrganizationProtectedAppointmentAuthorizationContract.permissionCode(action(op), kind);
        var found = jdbc.queryForList("SELECT id FROM " + T + "permission WHERE code=?", String.class, code);
        if (!found.isEmpty()) return found.getFirst();
        String id = id();
        jdbc.update("INSERT INTO " + T + "permission(id,code,permission_domain,resource_type,action,sensitive,status,created_at,updated_at)"
                + " VALUES (?,?,'organization',?,?,false,'ACTIVE',clock_timestamp(),clock_timestamp())",
                id, code, kind.resourceType(), op);
        return id;
    }

    private String grant(Fixture f, String op, ResourceKind kind, String effect, String ref) {
        String id = id();
        jdbc.update("INSERT INTO " + T + "user_permission_grant(id,user_id,permission_id,effect,scope_type,scope_reference_id,grant_reason,emergency_access,valid_from,valid_to,status,created_at)"
                + " VALUES (?,?,?,?,?,?,'synthetic appointment',false,clock_timestamp()-interval '1 hour',clock_timestamp()+interval '1 hour','ACTIVE',clock_timestamp())",
                id, f.user(), permission(op, kind), effect,
                kind == ResourceKind.UNIT ? "ORGANIZATION_UNIT" : "CUSTOM", ref);
        return id;
    }

    private void all(Fixture f, Request request) {
        String verb = request.action().name().toLowerCase(Locale.ROOT).replace('_', '-');
        for (var required : request.requirements()) grant(f, verb, required.kind(), "GRANT", required.reference());
    }

    private void both(Fixture f, String op) {
        var action = action(op);
        all(f, new Request(action, List.of(new CompanyUnit(COMPANY, FACILITY)), action == Action.READ_POLICY ? null : EMPLOYEE));
    }

    private String policy(String effect, String context) {
        String p = id(), v = id();
        jdbc.update(
                "INSERT INTO "
                        + T
                        + "authorization_policy(id,code,name,policy_domain,status,created_at,updated_at)"
                        + " VALUES (?,?,?,'organization','ACTIVE',clock_timestamp(),clock_timestamp())",
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

    private void c3PermissionMapping(Fixture f, String op, ResourceKind kind, String effect) {
        jdbc.update(
                "INSERT INTO public.hidra_identity_external_permission_mapping"
                    + "(id,identity_provider_id,permission_id,external_permission_code,claim_name,mapping_mode,effect,status,created_at,updated_at)"
                    + " VALUES"
                    + " (?,?,?,'private-original-group','groups','DIRECT_GRANT',?,'ACTIVE',now(),now())",
                id(),
                f.provider(),
                permission(op, kind),
                effect);
    }

    private OrganizationProtectedAppointmentAuthorizationContract.Prepared c3DirectoryPrepared(
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

    @Test void fourGenuineOriginsAndAllSevenActionsUseExactAppointmentResources() {
        for (String type : List.of("LOCAL", "OIDC", "LDAP", "ACTIVE_DIRECTORY")) {
            var f = fixture(type);
            for (var action : Action.values()) {
                var request = new Request(action, List.of(new CompanyUnit(COMPANY, FACILITY)),
                        action == Action.READ_POLICY ? null : EMPLOYEE);
                all(f, request);
                var result = run(originalSource(f), request);
                permit(result);
                assertThat(result.request()).isEqualTo(request);
                assertThat(result.userId()).isEqualTo(f.user());
                assertThat(result.decisions()).extracting(ResourceDecision::requirement).containsExactlyElementsOf(request.requirements());
            }
        }
    }

    @Test void eachCompanyEmployeeSourceAndDestinationDenialRollsBack() {
        var request = new Request(Action.TRANSFER, List.of(new CompanyUnit(COMPANY, FACILITY),
                new CompanyUnit(COMPANY, FACILITY + "-z")), EMPLOYEE);
        for (var required : request.requirements()) {
            cleanEvidence();
            var f = fixture("LOCAL"); all(f, request);
            grant(f, "transfer", required.kind(), "DENY", required.reference());
            rejected(() -> run(prepare(f), request));
            assertThat(jdbc.queryForObject("SELECT count(*) FROM " + receipt(), Integer.class)).isZero();
        }
    }

    @Test void multiCompanyMultiUnitHistoryHasOneNativeSampleAndAllDecisions() {
        var f = fixture("OIDC");
        var request = new Request(Action.READ_HISTORY, List.of(new CompanyUnit("company-a", "unit-1"),
                new CompanyUnit("company-a", "unit-2"), new CompanyUnit("company-b", "unit-3")), EMPLOYEE);
        all(f, request);
        clearInvocations(identityTarget());
        var result = run(prepare(f), request); permit(result);
        assertThat(result.decisions()).hasSize(6);
        verify(identityTarget(), times(1)).contribute(any(ProtectedOperationIdentityParticipant.Admitted.class));
    }

    @Test void missingCatalogAndEveryMissingResourceGrantFailClosed() {
        var f = fixture("LOCAL");
        rejected(() -> run(prepare(f), request()));
        for (var missing : request().requirements()) {
            cleanEvidence();
            for (var required : request().requirements()) if (!required.equals(missing))
                grant(f, "create", required.kind(), "GRANT", required.reference());
            rejected(() -> run(prepare(f), request()));
        }
    }

    @Test void wrongResourceCatalogAndUnknownPolicyRemainFailClosed() {
        var f = fixture("LOCAL"); both(f, "create");
        jdbc.update("UPDATE " + T + "permission SET resource_type='STATION_GRAPH_REVISION' WHERE id=?", permission("create", ResourceKind.UNIT));
        rejected(() -> run(prepare(f), request()));
        jdbc.update("UPDATE " + T + "permission SET resource_type='ORGANIZATION_UNIT' WHERE id=?", permission("create", ResourceKind.UNIT));
        policy("PERMIT", "{\"unsupported\":true}");
        rejected(() -> run(prepare(f), request()));
    }

    @Test void differentActionAndExactReferenceCannotBorrowGrants() {
        var f = fixture("LOCAL"); both(f, "create");
        rejected(() -> run(prepare(f), new Request(Action.END, request().companyUnits(), EMPLOYEE)));
        rejected(() -> run(prepare(f), new Request(Action.CREATE,
                List.of(new CompanyUnit(COMPANY, FACILITY + "-foreign")), EMPLOYEE)));
        rejected(() -> run(prepare(f), new Request(Action.CREATE, request().companyUnits(), "foreign-employee")));
    }

    @Test void fabricatedPreparationAndAdmissionNeverReachAuthority() {
        assertThatThrownBy(() -> tx().executeWithoutResult(s -> { c3SelectRestrictedRole(); rejected(() -> paired.acquire(new OrganizationProtectedAppointmentAuthorizationContract.Prepared(){}, request())); }))
                .isInstanceOf(org.springframework.transaction.UnexpectedRollbackException.class);
        assertThatThrownBy(() -> tx().executeWithoutResult(s -> { c3SelectRestrictedRole(); rejected(() -> paired.contribute(new OrganizationProtectedAppointmentAuthorizationContract.Admission(){})); }))
                .isInstanceOf(org.springframework.transaction.UnexpectedRollbackException.class);
    }

    @Test void preparationAndAdmissionCannotBeReplayed() {
        var f = fixture("LOCAL"); both(f, "create"); var p = prepare(f);
        permit(run(p, request())); rejected(() -> run(p, request()));
        var p2 = prepare(f);
        assertThatThrownBy(() -> tx().executeWithoutResult(s -> {
            c3SelectRestrictedRole(); var a = paired.acquire(p2, request()); paired.contribute(a);
            rejected(() -> paired.contribute(a));
        })).isInstanceOf(org.springframework.transaction.UnexpectedRollbackException.class);
    }

    @Test void anotherIssuerRejectsTicketAndPoisonsItsOriginalHolder() {
        var f = fixture("LOCAL"); both(f, "create"); var p = prepare(f);
        var other = new ProtectedOrganizationAppointmentAuthorizationParticipant(participant, reader, jdbc);
        assertThatThrownBy(() -> tx().executeWithoutResult(s -> {
            c3SelectRestrictedRole(); var a = paired.acquire(p, request());
            rejected(() -> other.contribute(a));
        })).isInstanceOf(org.springframework.transaction.UnexpectedRollbackException.class);
    }

    @Test void replacedAuthenticationFailsFinalOwnerCheckAndRollsBack() {
        var f = fixture("LOCAL"); both(f, "create"); var p = prepare(f);
        assertThatThrownBy(() -> tx().executeWithoutResult(s -> {
            c3SelectRestrictedRole(); var a = paired.acquire(p, request()); paired.contribute(a);
            SecurityContextHolder.clearContext(); rejected(() -> paired.requireCurrent(a));
        })).isInstanceOf(org.springframework.transaction.UnexpectedRollbackException.class);
    }

    @Test void wrongThreadCallPoisonsOriginalPhysicalTransaction() {
        var f = fixture("LOCAL"); both(f, "create"); var p = prepare(f);
        assertThatThrownBy(() -> tx().executeWithoutResult(s -> {
            c3SelectRestrictedRole(); var a = paired.acquire(p, request()); paired.contribute(a);
            var worker = Executors.newSingleThreadExecutor();
            try { worker.submit(() -> rejected(() -> paired.requireCurrent(a))).get(10, TimeUnit.SECONDS); }
            catch (Exception failure) { throw new IllegalStateException(failure); }
            finally { worker.shutdownNow(); }
        })).isInstanceOf(org.springframework.transaction.UnexpectedRollbackException.class);
    }

    @Test void completedAndSuspendedTicketsCannotBeReused() {
        var f = fixture("LOCAL"); both(f, "create"); var p = prepare(f);
        var saved = new AtomicReference<OrganizationProtectedAppointmentAuthorizationContract.Admission>();
        tx().executeWithoutResult(s -> { c3SelectRestrictedRole(); var a = paired.acquire(p, request()); paired.contribute(a); saved.set(a); });
        rejected(() -> paired.requireCurrent(saved.get()));
        var next = prepare(f);
        assertThatThrownBy(() -> tx().executeWithoutResult(s -> {
            c3SelectRestrictedRole(); var a = paired.acquire(next, request()); paired.contribute(a);
            var nested = new TransactionTemplate(manager); nested.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);
            nested.executeWithoutResult(n -> {});
            rejected(() -> paired.requireCurrent(a));
        })).isInstanceOf(org.springframework.transaction.UnexpectedRollbackException.class);
    }

    @Test void holderReplacementAndRollbackOnlyStateInvalidateAdmission() {
        var f = fixture("LOCAL"); both(f, "create"); var p = prepare(f);
        assertThatThrownBy(() -> tx().executeWithoutResult(s -> {
            c3SelectRestrictedRole(); var a = paired.acquire(p, request()); paired.contribute(a);
            var ds = jdbc.getDataSource(); Object original = TransactionSynchronizationManager.unbindResource(ds);
            TransactionSynchronizationManager.bindResource(ds, new org.springframework.jdbc.datasource.ConnectionHolder(mock(Connection.class)));
            try { rejected(() -> paired.requireCurrent(a)); }
            finally { TransactionSynchronizationManager.unbindResource(ds); TransactionSynchronizationManager.bindResource(ds, original); }
        })).isInstanceOf(org.springframework.transaction.UnexpectedRollbackException.class);
        var next = prepare(f);
        assertThatThrownBy(() -> tx().executeWithoutResult(s -> {
            c3SelectRestrictedRole(); var a = paired.acquire(next, request()); paired.contribute(a);
            ((org.springframework.jdbc.datasource.ConnectionHolder)TransactionSynchronizationManager.getResource(jdbc.getDataSource())).setRollbackOnly();
            rejected(() -> paired.requireCurrent(a));
        })).isInstanceOf(org.springframework.transaction.UnexpectedRollbackException.class);
    }

    @Test void physicalCommitChangesXidAndLosesRetainedFences() {
        var f = fixture("LOCAL"); both(f, "create"); var p = prepare(f);
        assertThatThrownBy(() -> tx().executeWithoutResult(s -> {
            c3SelectRestrictedRole(); var a = paired.acquire(p, request()); paired.contribute(a);
            try { ((org.springframework.jdbc.datasource.ConnectionHolder)TransactionSynchronizationManager.getResource(jdbc.getDataSource())).getConnection().commit(); }
            catch (SQLException failure) { throw new IllegalStateException(failure); }
            rejected(() -> paired.requireCurrent(a));
        })).isInstanceOf(org.springframework.transaction.UnexpectedRollbackException.class);
    }

    @Test void changedDatabaseRoleFailsFinalNativeFenceCheck() {
        var f = fixture("LOCAL"); both(f, "create"); var p = prepare(f);
        assertThatThrownBy(() -> tx().executeWithoutResult(s -> {
            c3SelectRestrictedRole(); var a = paired.acquire(p, request()); paired.contribute(a);
            jdbc.queryForObject("SELECT set_config('role','none',true)", String.class);
            rejected(() -> paired.requireCurrent(a));
        })).isInstanceOf(org.springframework.transaction.UnexpectedRollbackException.class);
    }

    @Test void committedProviderAndCredentialAbaCannotRestorePreparedOrigin() {
        for (boolean credential : List.of(false, true)) {
            var f = fixture("LOCAL"); both(f, "create"); var p = prepare(f);
            String table = credential ? C : P, column = credential ? "credential_status" : "status";
            String id = credential ? f.credential() : f.provider();
            jdbc.update("UPDATE " + table + " SET " + column + "=? WHERE id=?", credential ? "DISABLED" : "INACTIVE", id);
            jdbc.update("UPDATE " + table + " SET " + column + "='ACTIVE' WHERE id=?", id);
            rejected(() -> run(p, request()));
        }
    }

    @Test void unitHistoryViewRequiresNoUnrelatedEmployeePermission() {
        var f = fixture("LOCAL");
        var request = new Request(Action.READ_HISTORY, List.of(new CompanyUnit(COMPANY, FACILITY)), null);
        all(f, request);
        var result = run(prepare(f), request); permit(result);
        assertThat(result.decisions()).hasSize(2).noneMatch(d -> d.requirement().kind() == ResourceKind.EMPLOYEE);
    }

    @Test void exactIdentifiersWithWrongScopeTypesCannotBorrowAuthority() {
        for (var kind : ResourceKind.values()) {
            cleanEvidence(); var f = fixture("LOCAL"); both(f, "create");
            jdbc.update("UPDATE " + T + "user_permission_grant SET scope_type=? WHERE permission_id=?",
                    kind == ResourceKind.UNIT ? "CUSTOM" : "ORGANIZATION_UNIT", permission("create", kind));
            rejected(() -> run(prepare(f), request()));
        }
    }

    @Test void finalOwnerCheckRejectsInTransactionGrantAndProviderRevocation() {
        for (String table : List.of("user_permission_grant", "provider")) {
            var f = fixture("LOCAL"); both(f, "create"); var p = prepare(f);
            assertThatThrownBy(() -> tx().executeWithoutResult(s -> {
                c3SelectRestrictedRole(); var a = paired.acquire(p, request()); paired.contribute(a);
                jdbc.update("UPDATE " + T + table + " SET status=?", table.equals("provider") ? "INACTIVE" : "REVOKED");
                rejected(() -> paired.requireCurrent(a));
            })).isInstanceOf(org.springframework.transaction.UnexpectedRollbackException.class);
        }
    }

    @Test void savepointWriteCannotShortenOriginalTemporalBound() {
        var f = fixture("LOCAL"); both(f, "create"); var p = prepare(f);
        assertThatThrownBy(() -> tx().executeWithoutResult(s -> {
            c3SelectRestrictedRole(); var a = paired.acquire(p, request()); var observed = paired.contribute(a);
            try {
                var connection = ((org.springframework.jdbc.datasource.ConnectionHolder)TransactionSynchronizationManager.getResource(jdbc.getDataSource())).getConnection();
                var savepoint = connection.setSavepoint();
                var shortened = observed.sampledAt().plusNanos(Duration.between(observed.sampledAt(), observed.validUntil()).toNanos() / 2);
                jdbc.update("UPDATE " + T + "user_permission_grant SET valid_to=? WHERE user_id=?", Timestamp.from(shortened), f.user());
                connection.releaseSavepoint(savepoint);
            } catch (SQLException failure) { throw new IllegalStateException(failure); }
            rejected(() -> paired.requireCurrent(a));
        })).isInstanceOf(org.springframework.transaction.UnexpectedRollbackException.class);
    }

    @Test void committedNativeSessionRevocationBeforeConsumptionDenies() {
        var f = fixture("LOCAL"); both(f, "create"); var p = prepare(f);
        jdbc.update("UPDATE " + S + " SET status='REVOKED', ended_at=clock_timestamp() WHERE user_id=?", f.user());
        rejected(() -> run(p, request()));
    }

    @Test void claimMappingGenerationChangeRequiresFreshNativeAuthentication() {
        var f = fixture("OIDC"); both(f, "create");
        c3PermissionMapping(f, "create", ResourceKind.UNIT, "GRANT");
        var p = prepare(f);
        jdbc.update("UPDATE " + T + "external_permission_mapping SET status='INACTIVE' WHERE identity_provider_id=?", f.provider());
        rejected(() -> run(p, request()));
    }

    @Test void finalExpiryUsesActualDatabaseTimeAndRollsBack() {
        var f = fixture("LOCAL"); both(f, "create");
        jdbc.update("UPDATE " + T + "user_permission_grant SET valid_to=clock_timestamp()+interval '8 seconds' WHERE user_id=?", f.user());
        var p = prepare(f);
        assertThatThrownBy(() -> tx().executeWithoutResult(s -> {
            c3SelectRestrictedRole(); var a = paired.acquire(p, request()); var c = paired.contribute(a);
            jdbc.queryForList("SELECT pg_sleep(GREATEST(0, EXTRACT(EPOCH FROM ?::timestamptz-clock_timestamp()))+0.05)", Timestamp.from(c.validUntil()));
            rejected(() -> paired.requireCurrent(a));
        })).isInstanceOf(org.springframework.transaction.UnexpectedRollbackException.class);
    }

    @Test void explicitDenyWinsOverSupportedButUnknownPolicyEvidence() {
        var f = fixture("LOCAL"); both(f, "create");
        grant(f, "create", ResourceKind.UNIT, "DENY", FACILITY);
        policy("PERMIT", "{\"op\":\"eq\",\"attribute\":\"subject.attributes.missing\",\"type\":\"STRING\",\"value\":\"x\"}");
        var captured = new AtomicReference<JdbcProtectedAuthorizationEvidenceReader.OrganizationAuthorizationEvidence>();
        doAnswer(i -> { var graph = (JdbcProtectedAuthorizationEvidenceReader.OrganizationAuthorizationEvidence)i.callRealMethod(); captured.set(graph); return graph; })
                .when(readerTarget()).loadQualifiedOrganizationGroup(any(), any());
        try {
            rejected(() -> run(prepare(f), request()));
            var graph = captured.get(); assertThat(graph).isNotNull();
            assertThat(new dz.sh.hidra.modules.identity.domain.service.AuthorizationPolicyEvaluator()
                    .evaluate(graph.requests().get(1), graph.evidence().get(1), graph.contribution().evidence().observedAt()).decision())
                    .isEqualTo(AuthorizationDecisionValue.DENY);
        } finally { doCallRealMethod().when(readerTarget()).loadQualifiedOrganizationGroup(any(), any()); }
    }

    @Test void retainedNativeGroupBlocksCompetingAuthorityWriter() throws Exception {
        var f = fixture("LOCAL"); both(f, "create"); var p = prepare(f);
        tx().executeWithoutResult(s -> {
            c3SelectRestrictedRole(); var a = paired.acquire(p, request()); paired.contribute(a);
            try (var connection = DriverManager.getConnection(POSTGRES.getJdbcUrl(), POSTGRES.getUsername(), POSTGRES.getPassword());
                    var statement = connection.createStatement()) {
                statement.execute("SET lock_timeout='100ms'");
                assertThatThrownBy(() -> statement.executeUpdate("UPDATE " + T + "user_permission_grant SET status='REVOKED'"))
                        .isInstanceOf(SQLException.class).satisfies(failure -> assertThat(((SQLException)failure).getSQLState()).isEqualTo("55P03"));
            } catch (SQLException failure) { throw new IllegalStateException(failure); }
            paired.requireCurrent(a);
        });
    }

    @Test void genuineDiagnosticCredentialCannotSelectAppointmentOwnerFamily() {
        var f = fixture("LOCAL"); both(f, "create"); ambient(authenticate(f).accessToken());
        var nativePreparation = participant.prepare();
        var request = new AuthorizationEvaluationRequest(f.user(), "organization:appointment-unit:create",
                "ORGANIZATION_UNIT", FACILITY, new AuthorizationScope(ScopeType.ORGANIZATION_UNIT, FACILITY, null));
        assertThatThrownBy(() -> tx().executeWithoutResult(s -> {
            c3SelectRestrictedRole(); var diagnostic = participant.qualifyClaims(nativePreparation, request);
            assertThat(participant.organizationCredential(diagnostic)).isFalse();
            rejected(() -> reader.loadQualified(diagnostic, request));
        })).isInstanceOf(org.springframework.transaction.UnexpectedRollbackException.class);
    }

    @Test void scalarDiagnosticCannotEnterOrganizationQualifiedReader() {
        var f = fixture("LOCAL"); both(f, "create"); prepare(f);
        assertThatThrownBy(() -> tx().executeWithoutResult(s -> {
            c3SelectRestrictedRole();
            assertThatThrownBy(() -> reader.loadQualifiedOrganizationGroup(null, request())).isInstanceOf(RuntimeException.class);
        })).isInstanceOf(org.springframework.transaction.UnexpectedRollbackException.class);
    }

}
