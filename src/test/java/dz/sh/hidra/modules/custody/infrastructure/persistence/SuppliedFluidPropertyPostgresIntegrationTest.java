/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : SuppliedFluidPropertyPostgresIntegrationTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-27
 *
 * @Type        : Class
 * @Layer       : Test
 * @Module      : custody
 * @Package     : dz.sh.hidra.modules.custody.infrastructure.persistence
 *
 * @Description : Preserves exact authored fluid facts independently of numerical qualification.
 *
 */
package dz.sh.hidra.modules.custody.infrastructure.persistence;

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
import dz.sh.hidra.modules.custody.domain.model.SuppliedFluidPropertyRevision.*;

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
public class SuppliedFluidPropertyPostgresIntegrationTest {
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
                    .withDatabaseName("hidra_supplied_property_test")
                    .withCommand(
                            "postgres",
                            "-c",
                            "log_error_verbosity=terse",
                            "-c",
                            "track_commit_timestamp=on",
                            // Explicit writer tests must not race background vacuum relation locks.
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


    @Autowired dz.sh.hidra.modules.custody.application.port.in.SuppliedFluidPropertyRevisionUseCase supplied;
    @MockitoSpyBean dz.sh.hidra.modules.custody.infrastructure.persistence.adapter.JdbcSuppliedFluidPropertyRevisionRepositoryAdapter fluidStorage;
    @MockitoSpyBean dz.sh.hidra.modules.custody.application.service.ProtectedSuppliedFluidPropertyAdmissionCoordinator fluidCoordinator;
    @Autowired JdbcTemplate runtimeJdbc;
    private JdbcTemplate jdbc;
    private javax.sql.DataSource administrator;
    @Autowired dz.sh.hidra.modules.custody.application.port.in.ProductEngineeringDraftUseCase drafts;
    @MockitoSpyBean dz.sh.hidra.modules.custody.infrastructure.persistence.adapter.JdbcProductEngineeringDraftRepositoryAdapter storage;
    @Autowired dz.sh.hidra.modules.party.application.contract.custody.CustodyProtectedPartyEligibilityContract partyOwner;
    @MockitoSpyBean dz.sh.hidra.modules.custody.application.service.ProtectedProductDraftAdmissionCoordinator productCoordinator;
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
        administrator=new org.springframework.jdbc.datasource.DriverManagerDataSource(POSTGRES.getJdbcUrl(),POSTGRES.getUsername(),POSTGRES.getPassword());
        jdbc=new JdbcTemplate(administrator) {
            @Override public javax.sql.DataSource getDataSource(){return TransactionSynchronizationManager.isActualTransactionActive()?runtimeJdbc.getDataSource():administrator;}
        };
        assertThat(jdbc.queryForObject("SHOW autovacuum",String.class)).isEqualTo("off");
        SecurityContextHolder.clearContext();cleanEvidence();flyway.validate();assertThat(flyway.info().pending()).isEmpty();
        for(String role:List.of("c3_source_claim_app","c3_publication_app")) {
            jdbc.execute("REVOKE ALL ON public.hidra_identity_external_mapping_generation,public.hidra_party_eligibility_generation FROM "+role);
            jdbc.execute("GRANT SELECT ON public.hidra_identity_external_mapping_generation,public.hidra_party_eligibility_generation TO "+role);
        }
        String tables="public.hidra_custody_product_engineering_draft,public.hidra_custody_product_engineering_draft_version";
        jdbc.execute("REVOKE ALL ON "+tables+" FROM c3_source_claim_app,c3_publication_app");
        jdbc.execute("GRANT SELECT ON "+tables+" TO c3_source_claim_app");
        jdbc.execute("GRANT SELECT,INSERT,UPDATE ON "+tables+" TO c3_publication_app");
        jdbc.execute("REVOKE ALL ON public.hidra_custody_engineering_product_policy FROM c3_source_claim_app");
        jdbc.execute("GRANT SELECT ON public.hidra_custody_engineering_product_policy TO c3_source_claim_app");
        jdbc.execute("REVOKE ALL ON public.hidra_custody_catalog_entry,public.hidra_custody_engineering_product_policy FROM c3_publication_app");
        jdbc.execute("GRANT SELECT,UPDATE ON public.hidra_custody_catalog_entry,public.hidra_custody_engineering_product_policy TO c3_publication_app");
        when(externalDecoder.decode(anyString())).thenAnswer(i->realExternal.decode(i.getArgument(0)));
        schema="hapi594b_"+id().replace("-","");jdbc.execute("CREATE SCHEMA "+schema);jdbc.execute("CREATE TABLE "+receipt()+"(id integer PRIMARY KEY)");
        c3NonownerCapture();
        jdbc.execute("INSERT INTO public.hidra_party_type(id,code,status,created_at,updated_at) VALUES('product-party-type','product-party-type','ACTIVE',now(),now()) ON CONFLICT DO NOTHING");
        for(String company:List.of(COMPANY,"other-company"))jdbc.update("INSERT INTO public.hidra_party_party(id,code,party_type_id,legal_name,country_code,status,created_at,updated_at) VALUES(?,?,'product-party-type','Synthetic','DZ','ACTIVE',now(),now()) ON CONFLICT(id) DO UPDATE SET status='ACTIVE'",company,company);
        jdbc.update("INSERT INTO public.hidra_custody_catalog_entry(id,catalog_name,code,active,sort_order,system_defined,created_at,updated_at) VALUES(?,'PRODUCT',?,true,0,false,now(),now()) ON CONFLICT(id) DO UPDATE SET active=true",PRODUCT,PRODUCT);
        jdbc.update("INSERT INTO public.hidra_custody_engineering_product_policy(catalog_product_id,active) VALUES(?,true) ON CONFLICT(catalog_product_id) DO UPDATE SET active=true",PRODUCT);
        jdbc.execute("REVOKE ALL ON public.hidra_custody_supplied_fluid_property_draft,public.hidra_custody_supplied_fluid_property_version FROM c3_source_claim_app,c3_publication_app");
        jdbc.execute("GRANT SELECT ON public.hidra_custody_supplied_fluid_property_draft,public.hidra_custody_supplied_fluid_property_version TO c3_source_claim_app");
        jdbc.execute("GRANT SELECT,INSERT,UPDATE ON public.hidra_custody_supplied_fluid_property_draft,public.hidra_custody_supplied_fluid_property_version TO c3_publication_app");
        jdbc.execute("REVOKE ALL ON public.hidra_telemetry_unit,public.hidra_telemetry_fluid_unit_policy FROM c3_source_claim_app,c3_publication_app");
        jdbc.execute("GRANT SELECT ON public.hidra_telemetry_unit,public.hidra_telemetry_fluid_unit_policy TO c3_source_claim_app");
        jdbc.execute("GRANT SELECT,UPDATE ON public.hidra_telemetry_unit,public.hidra_telemetry_fluid_unit_policy TO c3_publication_app");
        var f=actor("LOCAL");var prepared=paired.prepare();
        tx().executeWithoutResult(status->{var i=paired.acquire(prepared,request());var p=partyOwner.acquire(COMPANY);permit(paired.contribute(i));assertThat(partyOwner.read(p).partyId()).isEqualTo(COMPANY);paired.requireCurrent(i);});
        cleanEvidence();clearInvocations(storage);
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

    private dz.sh.hidra.modules.custody.infrastructure.persistence.adapter.JdbcProductEngineeringDraftRepositoryAdapter storageTarget() {
        return org.springframework.test.util.AopTestUtils.getUltimateTargetObject(storage);
    }
    private dz.sh.hidra.modules.custody.application.service.ProtectedProductDraftAdmissionCoordinator coordinatorTarget() {
        return org.springframework.test.util.AopTestUtils.getUltimateTargetObject(productCoordinator);
    }
    private static final String D="public.hidra_custody_product_engineering_draft", V="public.hidra_custody_product_engineering_draft_version", POLICY="public.hidra_custody_engineering_product_policy";
    private Fixture actor(String type) {
        cleanEvidence();var f=fixture(type);for(String action:OPS)both(f,action);
        if(type.equals("LDAP") || type.equals("ACTIVE_DIRECTORY"))c3DirectoryPrepared(f,"memberOf");
        else ambient(authenticate(f).accessToken());
        return f;
    }
    private dz.sh.hidra.modules.custody.domain.model.ProductEngineeringDraft.Metadata metadata(String name) {
        return new dz.sh.hidra.modules.custody.domain.model.ProductEngineeringDraft.Metadata(name,null,null,null);
    }
    private dz.sh.hidra.modules.custody.application.port.in.ProductEngineeringDraftUseCase.Result create(UUID request,String code,String name) {
        return drafts.execute(new dz.sh.hidra.modules.custody.application.port.in.ProductEngineeringDraftUseCase.Command(
            dz.sh.hidra.modules.custody.application.port.in.ProductEngineeringDraftUseCase.Kind.CREATE,COMPANY,null,code,PRODUCT,request,null,metadata(name)));
    }
    private dz.sh.hidra.modules.custody.application.port.in.ProductEngineeringDraftUseCase.Result read(String company,UUID draft) {
        return drafts.execute(new dz.sh.hidra.modules.custody.application.port.in.ProductEngineeringDraftUseCase.Command(
            dz.sh.hidra.modules.custody.application.port.in.ProductEngineeringDraftUseCase.Kind.READ,company,draft,null,null,null,null,null));
    }
    private dz.sh.hidra.modules.custody.application.port.in.ProductEngineeringDraftUseCase.Result update(UUID draft,long version,String name) {
        return drafts.execute(new dz.sh.hidra.modules.custody.application.port.in.ProductEngineeringDraftUseCase.Command(
            dz.sh.hidra.modules.custody.application.port.in.ProductEngineeringDraftUseCase.Kind.UPDATE,COMPANY,draft,null,null,null,version,metadata(name)));
    }
    private long count(UUID id) {return jdbc.queryForObject("SELECT count(*) FROM "+V+" WHERE draft_id=?",Long.class,id);}
    private long draftsCount(){return jdbc.queryForObject("SELECT count(*) FROM "+D,Long.class);}
    private Connection publisher() throws Exception{return DriverManager.getConnection(POSTGRES.getJdbcUrl(),"c3_publication_app","synthetic-publication-test-only");}


    private String fluidPermission(String op) {
        String code = "custody:engineering-product-properties:" + op;
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
                        + " (?,?,'custody','PRODUCT_ENGINEERING_PROPERTIES',?,false,'ACTIVE',clock_timestamp(),clock_timestamp())",
                id,
                code,
                op);
        return id;
    }

    private String fluidGrant(Fixture f, String op, String effect, String scope, String ref) {
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
                fluidPermission(op),
                effect,
                scope,
                ref);
        return id;
    }


    private static final String FV="public.hidra_custody_supplied_fluid_property_version",FR="public.hidra_custody_supplied_fluid_property_draft";
    private Fixture fluidActor(String type){var f=actor(type);for(String action:List.of("read","write")){fluidGrant(f,action,"GRANT","CUSTOM","custody-company:"+COMPANY);fluidGrant(f,action,"GRANT","CUSTOM","custody-product:"+PRODUCT);}return f;}
    private UUID product(){return drafts.execute(new dz.sh.hidra.modules.custody.application.port.in.ProductEngineeringDraftUseCase.Command(
        dz.sh.hidra.modules.custody.application.port.in.ProductEngineeringDraftUseCase.Kind.CREATE,COMPANY,null,id(),PRODUCT,UUID.randomUUID(),null,
        new dz.sh.hidra.modules.custody.domain.model.ProductEngineeringDraft.Metadata("Synthetic",null,null,dz.sh.hidra.modules.custody.domain.model.ProductEngineeringDraft.Classification.GAS))).draft().id();}
    private Facts absent(){return new Facts(null,null,null,null,null);}
    private dz.sh.hidra.modules.custody.application.port.in.SuppliedFluidPropertyRevisionUseCase.Result put(UUID id,UUID request,long version,long metadata,Facts facts){return supplied.execute(new dz.sh.hidra.modules.custody.application.port.in.SuppliedFluidPropertyRevisionUseCase.Command(dz.sh.hidra.modules.custody.application.port.in.SuppliedFluidPropertyRevisionUseCase.Kind.WRITE,COMPANY,id,request,version,metadata,facts));}
    private dz.sh.hidra.modules.custody.application.port.in.SuppliedFluidPropertyRevisionUseCase.Result get(String company,UUID id){return supplied.execute(new dz.sh.hidra.modules.custody.application.port.in.SuppliedFluidPropertyRevisionUseCase.Command(dz.sh.hidra.modules.custody.application.port.in.SuppliedFluidPropertyRevisionUseCase.Kind.READ,company,id,null,null,null,null));}
    private long versions(UUID id){return jdbc.queryForObject("SELECT count(*) FROM "+FV+" WHERE product_draft_id=?",Long.class,id);}
    private dz.sh.hidra.modules.custody.infrastructure.persistence.adapter.JdbcSuppliedFluidPropertyRevisionRepositoryAdapter fluidTarget(){return org.springframework.test.util.AopTestUtils.getUltimateTargetObject(fluidStorage);}
    private Facts observation(){return new Facts(null,List.of(new Observation("density",null,Property.DENSITY,new Decimal("1.200"),"fluid-density",new Condition(new Decimal("100"),"fluid-pressure"),new Condition(new Decimal("300"),"fluid-temperature"),null,null)),null,null,null);}
    private void units(){for(String role:List.of("DENSITY","ABSOLUTE_PRESSURE","ABSOLUTE_TEMPERATURE")){
        String id=role.equals("DENSITY")?"fluid-density":role.equals("ABSOLUTE_PRESSURE")?"fluid-pressure":"fluid-temperature";
        jdbc.update("INSERT INTO public.hidra_telemetry_unit(id,code,symbol,dimension,active,system_defined,created_at,updated_at) VALUES(?,?,?,'declared-dimension',true,false,now(),now()) ON CONFLICT(id) DO UPDATE SET active=true",id,id,id);
        jdbc.update("INSERT INTO public.hidra_telemetry_fluid_unit_policy(unit_id,usage_role,expected_dimension,active,created_at,updated_at) VALUES(?,?,'declared-dimension',true,now(),now()) ON CONFLICT(unit_id,usage_role) DO UPDATE SET active=true,updated_at=clock_timestamp()",id,role);
    }}
    @Test void fourNativeProducersCreateReadAndAppendExactSuppliedFacts(){
        for(String type:List.of("LOCAL","OIDC","LDAP","ACTIVE_DIRECTORY")){fluidActor(type);UUID p=product();var one=put(p,UUID.randomUUID(),0,1,absent());assertThat(one.draft().version()).isEqualTo(1);assertThat(get(COMPANY,p).draft()).isEqualTo(one.draft());assertThat(put(p,UUID.randomUUID(),1,1,absent()).draft().version()).isEqualTo(2);assertThat(versions(p)).isEqualTo(2);}
    }
    @Test void exactReplayReturnsCurrentWithoutRevertingAndConflictsRollback(){fluidActor("LOCAL");UUID p=product(),r=UUID.randomUUID();put(p,r,0,1,absent());put(p,UUID.randomUUID(),1,1,new Facts(null,List.of(),List.of(),null,"later"));var replay=put(p,r,0,1,absent());assertThat(replay.replayed()).isTrue();assertThat(replay.draft().version()).isEqualTo(2);rejected(()->put(p,r,0,1,new Facts(null,null,null,null,"different")));assertThat(versions(p)).isEqualTo(2);}
    @Test void stalePropertyAndMetadataVersionsNeverAppend(){fluidActor("LOCAL");UUID p=product();put(p,UUID.randomUUID(),0,1,absent());rejected(()->put(p,UUID.randomUUID(),0,1,absent()));rejected(()->put(p,UUID.randomUUID(),1,2,absent()));assertThat(versions(p)).isEqualTo(1);}
    @Test void metadataRevisionRetainsAuthoredClassificationAndReplayBytes(){fluidActor("LOCAL");UUID p=product(),request=UUID.randomUUID();put(p,request,0,1,absent());drafts.execute(new dz.sh.hidra.modules.custody.application.port.in.ProductEngineeringDraftUseCase.Command(dz.sh.hidra.modules.custody.application.port.in.ProductEngineeringDraftUseCase.Kind.UPDATE,COMPANY,p,null,null,null,1L,new dz.sh.hidra.modules.custody.domain.model.ProductEngineeringDraft.Metadata("liquid",null,null,dz.sh.hidra.modules.custody.domain.model.ProductEngineeringDraft.Classification.LIQUID)));var stale=get(COMPANY,p);assertThat(stale.metadataBindingCurrent()).isFalse();assertThat(stale.draft().classification()).isEqualTo(Classification.GAS);assertThat(stale.diagnostics()).contains("METADATA_BINDING_STALE");assertThat(put(p,request,0,1,absent()).draft()).isEqualTo(stale.draft());assertThat(put(p,UUID.randomUUID(),1,2,absent()).draft().classification()).isEqualTo(Classification.LIQUID);}
    @Test void unspecifiedMetadataRejectsInitialPropertyRevision(){fluidActor("LOCAL");UUID p=create(UUID.randomUUID(),id(),"no classification").draft().id();rejected(()->put(p,UUID.randomUUID(),0,1,absent()));assertThat(versions(p)).isZero();}
    @Test void absentAndForeignProductShareNonDisclosingDenial(){fluidActor("LOCAL");UUID p=product();put(p,UUID.randomUUID(),0,1,absent());for(UUID id:List.of(p,UUID.randomUUID()))assertThatThrownBy(()->get("other-company",id)).hasMessage("Supplied fluid draft DENIED");}
    @Test void metadataPermissionsAloneCannotWriteProperties(){actor("LOCAL");UUID p=product();assertThatThrownBy(()->put(p,UUID.randomUUID(),0,1,absent())).hasMessage("Supplied fluid draft DENIED");assertThat(versions(p)).isZero();}
    @Test void nativeDenyOverridesPropertyGrant(){var f=fluidActor("LOCAL");UUID p=product();fluidGrant(f,"write","DENY","CUSTOM","custody-company:"+COMPANY);rejected(()->put(p,UUID.randomUUID(),0,1,absent()));assertThat(versions(p)).isZero();}
    @Test void unitAdmissionAndRevocationAreRequiredForReadAndWrite(){fluidActor("LOCAL");UUID p=product();units();var facts=observation();assertThat(put(p,UUID.randomUUID(),0,1,facts).draft().facts()).isEqualTo(facts);jdbc.update("UPDATE public.hidra_telemetry_fluid_unit_policy SET active=false WHERE unit_id='fluid-density'");rejected(()->get(COMPANY,p));rejected(()->put(p,UUID.randomUUID(),1,1,facts));assertThat(versions(p)).isEqualTo(1);}
    @Test void unavailableUnitCannotCreatePartialRoot(){fluidActor("LOCAL");UUID p=product();jdbc.update("DELETE FROM public.hidra_telemetry_fluid_unit_policy WHERE unit_id='fluid-density'");rejected(()->put(p,UUID.randomUUID(),0,1,observation()));assertThat(versions(p)).isZero();assertThat(jdbc.queryForObject("SELECT count(*) FROM "+FR+" WHERE product_draft_id=?",Long.class,p)).isZero();}
    @Test void finalExpiredGuardRollsBackRootAndRevision(){fluidActor("LOCAL");UUID p=product();var reached=new AtomicBoolean();doAnswer(i->{reached.set(true);var c=(dz.sh.hidra.modules.custody.application.service.ProtectedSuppliedFluidPropertyAdmissionCoordinator.Contribution)i.getArgument(1);i.getArguments()[1]=new dz.sh.hidra.modules.custody.application.service.ProtectedSuppliedFluidPropertyAdmissionCoordinator.Contribution(c.request(),c.partyGeneration(),c.bindingId(),c.sessionId(),c.userId(),c.sampledAt().minusSeconds(3600),Instant.EPOCH,c.backendPid(),c.transactionId(),c.units());return i.callRealMethod();}).when(fluidTarget()).finalGuard(any(),any(),anyList());rejected(()->put(p,UUID.randomUUID(),0,1,absent()));assertThat(reached).isTrue();assertThat(versions(p)).isZero();}
    @Test void finalFenceSubstitutionRollsBackProvisionalRevision(){fluidActor("LOCAL");UUID p=product();var reached=new AtomicBoolean();doAnswer(i->{reached.set(true);var fs=new ArrayList<dz.sh.hidra.platform.persistence.ProtectedRelationFence>((List<dz.sh.hidra.platform.persistence.ProtectedRelationFence>)i.getArgument(2));fs.set(0,new dz.sh.hidra.platform.persistence.ProtectedRelationFence("public.hidra_party_type",dz.sh.hidra.platform.persistence.ProtectedRelationFence.Mode.SHARE_ROW_EXCLUSIVE));i.getArguments()[2]=fs;return i.callRealMethod();}).when(fluidTarget()).finalGuard(any(),any(),anyList());rejected(()->put(p,UUID.randomUUID(),0,1,absent()));assertThat(reached).isTrue();assertThat(versions(p)).isZero();}
    @Test void ordinaryAndPublisherCannotMutateHistory()throws Exception{fluidActor("LOCAL");UUID p=product();put(p,UUID.randomUUID(),0,1,absent());assertThatThrownBy(()->runtimeJdbc.update("DELETE FROM "+FV+" WHERE product_draft_id=?",p)).isInstanceOf(RuntimeException.class);try(var c=publisher();var st=c.createStatement()){for(String sql:List.of("UPDATE "+FR+" SET created_at=clock_timestamp()","UPDATE "+FV+" SET classification='LIQUID'","DELETE FROM "+FV,"TRUNCATE "+FV))assertThatThrownBy(()->st.execute(sql)).isInstanceOf(SQLException.class);}assertThat(versions(p)).isEqualTo(1);}
    @Test void disabledAppendTriggerOrExcessPrivilegesReject(){fluidActor("LOCAL");UUID p=product();put(p,UUID.randomUUID(),0,1,absent());jdbc.execute("ALTER TABLE "+FV+" DISABLE TRIGGER custody_supplied_fluid_next_version");try{rejected(()->get(COMPANY,p));}finally{jdbc.execute("ALTER TABLE "+FV+" ENABLE TRIGGER custody_supplied_fluid_next_version");}jdbc.execute("GRANT DELETE ON "+FV+" TO c3_publication_app");try{rejected(()->get(COMPANY,p));}finally{jdbc.execute("REVOKE DELETE ON "+FV+" FROM c3_publication_app");}}
    @Test void concurrentExpectedVersionWritersCommitOneSuccessor()throws Exception{fluidActor("LOCAL");UUID p=product();put(p,UUID.randomUUID(),0,1,absent());var auth=SecurityContextHolder.getContext().getAuthentication();var pool=Executors.newFixedThreadPool(2);var start=new CountDownLatch(1);var successes=new AtomicInteger();try{var jobs=new ArrayList<Future<?>>();for(int j=0;j<2;j++)jobs.add(pool.submit(()->{SecurityContextHolder.getContext().setAuthentication(auth);try{start.await();put(p,UUID.randomUUID(),1,1,absent());successes.incrementAndGet();}catch(dz.sh.hidra.modules.custody.application.port.in.SuppliedFluidPropertyRevisionUseCase.Failure expected){}catch(InterruptedException e){Thread.currentThread().interrupt();throw new IllegalStateException(e);}finally{SecurityContextHolder.clearContext();}}));start.countDown();for(var job:jobs)job.get(10,TimeUnit.SECONDS);}finally{pool.shutdownNow();}assertThat(successes).hasValue(1);assertThat(versions(p)).isEqualTo(2);}
    @Test void completedAndForgedAdmissionsCannotDisclose(){fluidActor("LOCAL");UUID p=product();var saved=new AtomicReference<dz.sh.hidra.modules.custody.application.port.out.SuppliedFluidPropertyRevisionRepositoryPort.Admission>();doAnswer(i->{saved.set(i.getArgument(0));return i.callRealMethod();}).when(fluidTarget()).apply(any(),any());put(p,UUID.randomUUID(),0,1,absent());rejected(()->fluidStorage.catalogProductId(saved.get()));rejected(()->fluidStorage.catalogProductId(new dz.sh.hidra.modules.custody.application.port.out.SuppliedFluidPropertyRevisionRepositoryPort.Admission(){}));}
    @Test void swallowedWorkerReusePoisonsOriginalTransaction(){fluidActor("LOCAL");UUID p=product();doAnswer(i->{Object result=i.callRealMethod();var a=(dz.sh.hidra.modules.custody.application.service.ProtectedSuppliedFluidPropertyAdmissionCoordinator.Admission)i.getArgument(0);var pool=Executors.newSingleThreadExecutor();try{pool.submit(()->rejected(()->fluidCoordinator.requireCurrent(a))).get(5,TimeUnit.SECONDS);}catch(Exception e){throw new AssertionError("Worker diagnostic did not complete",e);}finally{pool.shutdownNow();}return result;}).when((dz.sh.hidra.modules.custody.application.service.ProtectedSuppliedFluidPropertyAdmissionCoordinator)org.springframework.test.util.AopTestUtils.getUltimateTargetObject(fluidCoordinator)).collect(any());rejected(()->put(p,UUID.randomUUID(),0,1,absent()));assertThat(versions(p)).isZero();}

    @Test void exactCompositionBytesAndBothBasesPersistForBothClassifications() {
        fluidActor("LOCAL");
        for(var phase:Classification.values())for(var basis:Basis.values()) {
            UUID p=product();long metadata=1;
            if(phase==Classification.LIQUID){
                drafts.execute(new dz.sh.hidra.modules.custody.application.port.in.ProductEngineeringDraftUseCase.Command(
                    dz.sh.hidra.modules.custody.application.port.in.ProductEngineeringDraftUseCase.Kind.UPDATE,COMPANY,p,null,null,null,1L,
                    new dz.sh.hidra.modules.custody.domain.model.ProductEngineeringDraft.Metadata("liquid",null,null,dz.sh.hidra.modules.custody.domain.model.ProductEngineeringDraft.Classification.LIQUID)));
                metadata=2;
            }
            var facts=new Facts(new Composition(basis,List.of(new Component("declared-A",new Decimal("0.2500")),new Component("declared-B",new Decimal("0.750"))),null,null),null,null,null,null);
            put(p,UUID.randomUUID(),0,metadata,facts);
            assertThat(get(COMPANY,p).draft().facts()).isEqualTo(facts);
            assertThat(jdbc.queryForObject("SELECT canonical_payload FROM "+FV+" WHERE product_draft_id=?",byte[].class,p))
                .isEqualTo(new dz.sh.hidra.modules.custody.infrastructure.persistence.adapter.SuppliedFluidPropertyRevisionCodec().encode(facts));
        }
    }
    @Test void revokedSessionsDenyFreshReadAndExactReplayWithoutAppend() {
        var f=fluidActor("LOCAL");UUID p=product(),request=UUID.randomUUID();put(p,request,0,1,absent());
        jdbc.update("UPDATE "+S+" SET status='REVOKED',ended_at=clock_timestamp() WHERE user_id=? AND status='ACTIVE'",f.user());
        rejected(()->get(COMPANY,p));rejected(()->put(p,request,0,1,absent()));assertThat(versions(p)).isEqualTo(1);
    }
    @Test void inactiveCompanyOrCatalogDeniesReadsWithoutChangingHistory() {
        fluidActor("LOCAL");UUID p=product();put(p,UUID.randomUUID(),0,1,absent());
        jdbc.update("UPDATE public.hidra_party_party SET status='INACTIVE' WHERE id=?",COMPANY);
        rejected(()->get(COMPANY,p));
        jdbc.update("UPDATE public.hidra_party_party SET status='ACTIVE' WHERE id=?",COMPANY);
        jdbc.update("UPDATE "+POLICY+" SET active=false WHERE catalog_product_id=?",PRODUCT);
        rejected(()->get(COMPANY,p));assertThat(versions(p)).isEqualTo(1);
    }
    @Test void writerFirstUnitPolicyContentionLeavesNoProvisionalRoot() throws Exception {
        fluidActor("LOCAL");UUID p=product();units();
        try(var connection=administrator.getConnection()) {
            connection.setAutoCommit(false);
            try(var st=connection.createStatement()){st.executeUpdate("UPDATE public.hidra_telemetry_fluid_unit_policy SET active=false WHERE unit_id='fluid-density'");}
            rejected(()->put(p,UUID.randomUUID(),0,1,observation()));connection.rollback();
        }
        assertThat(versions(p)).isZero();assertThat(jdbc.queryForObject("SELECT count(*) FROM "+FR+" WHERE product_draft_id=?",Long.class,p)).isZero();
    }
    @Test void ownerFirstUnitFencesBlockRevocationUntilCommit() {
        fluidActor("LOCAL");UUID p=product();units();var reached=new AtomicBoolean();
        doAnswer(i->{
            reached.set(true);
            try(var connection=administrator.getConnection();var st=connection.createStatement()) {
                st.execute("SET lock_timeout='25ms'");
                assertThatThrownBy(()->st.executeUpdate("UPDATE public.hidra_telemetry_fluid_unit_policy SET active=false WHERE unit_id='fluid-density'"))
                    .isInstanceOf(SQLException.class).extracting(e->((SQLException)e).getSQLState()).isEqualTo("55P03");
            }
            return i.callRealMethod();
        }).when(fluidTarget()).apply(any(),any());
        assertThat(put(p,UUID.randomUUID(),0,1,observation()).draft().version()).isEqualTo(1);assertThat(reached).isTrue();
    }
    @Test void separatelyAuthorizedCompaniesKeepIndependentSuppliedSequences() {
        var f=fluidActor("LOCAL");UUID first=product();put(first,UUID.randomUUID(),0,1,absent());
        for(String action:OPS)grant(f,action,"GRANT","CUSTOM","custody-company:other-company");
        for(String action:List.of("read","write"))fluidGrant(f,action,"GRANT","CUSTOM","custody-company:other-company");
        UUID second=drafts.execute(new dz.sh.hidra.modules.custody.application.port.in.ProductEngineeringDraftUseCase.Command(
            dz.sh.hidra.modules.custody.application.port.in.ProductEngineeringDraftUseCase.Kind.CREATE,"other-company",null,id(),PRODUCT,UUID.randomUUID(),null,
            new dz.sh.hidra.modules.custody.domain.model.ProductEngineeringDraft.Metadata("Synthetic",null,null,dz.sh.hidra.modules.custody.domain.model.ProductEngineeringDraft.Classification.GAS))).draft().id();
        supplied.execute(new dz.sh.hidra.modules.custody.application.port.in.SuppliedFluidPropertyRevisionUseCase.Command(
            dz.sh.hidra.modules.custody.application.port.in.SuppliedFluidPropertyRevisionUseCase.Kind.WRITE,"other-company",second,UUID.randomUUID(),0L,1L,absent()));
        assertThat(get("other-company",second).draft().companyPartyId()).isEqualTo("other-company");
        assertThatThrownBy(()->get(COMPANY,second)).hasMessage("Supplied fluid draft DENIED");
        assertThatThrownBy(()->get("other-company",first)).hasMessage("Supplied fluid draft DENIED");
        assertThat(versions(first)).isEqualTo(1);assertThat(versions(second)).isEqualTo(1);
    }

    @Test void publisherCannotForgeSequenceMetadataOrTransactionOrigin() throws Exception {
        fluidActor("LOCAL");UUID p=product();put(p,UUID.randomUUID(),0,1,absent());
        for(int mode=0;mode<3;mode++)try(var connection=publisher();var st=connection.prepareStatement(
            "INSERT INTO "+FV+" (product_draft_id,version,request_id,request_sha256,expected_version,product_metadata_version,classification,payload_format,canonical_payload,sha256,predecessor_sha256,actor_binding_id,actor_session_id,actor_user_id,party_generation,sampled_at,valid_until,backend_pid,transaction_id) "
            +"SELECT product_draft_id,?,?::uuid,request_sha256,?,product_metadata_version,?,payload_format,canonical_payload,sha256,sha256,actor_binding_id,actor_session_id,actor_user_id,party_generation,clock_timestamp(),clock_timestamp()+interval '1 hour',?,txid_current() FROM "+FV+" WHERE product_draft_id=? AND version=1")) {
            st.setLong(1,mode==0?3:2);st.setString(2,id());st.setLong(3,mode==0?2:1);st.setString(4,mode==1?"LIQUID":"GAS");st.setInt(5,0);st.setObject(6,p);
            assertThatThrownBy(st::executeUpdate).isInstanceOf(SQLException.class);
        }
        assertThat(versions(p)).isEqualTo(1);
    }

    @Test void liquidCompressibilityRejectsAsTypedInvalidInputBeforeProvisionalWrites() {
        fluidActor("LOCAL");UUID p=product();
        drafts.execute(new dz.sh.hidra.modules.custody.application.port.in.ProductEngineeringDraftUseCase.Command(
            dz.sh.hidra.modules.custody.application.port.in.ProductEngineeringDraftUseCase.Kind.UPDATE,COMPANY,p,null,null,null,1L,
            new dz.sh.hidra.modules.custody.domain.model.ProductEngineeringDraft.Metadata("liquid",null,null,dz.sh.hidra.modules.custody.domain.model.ProductEngineeringDraft.Classification.LIQUID)));
        var z=new Facts(null,List.of(new Observation("Z",null,Property.COMPRESSIBILITY_FACTOR,new Decimal("1.20"),"fluid-Z",new Condition(new Decimal("100"),"fluid-pressure"),new Condition(new Decimal("300"),"fluid-temperature"),null,null)),null,null,null);
        clearInvocations(fluidTarget());
        assertThatThrownBy(()->put(p,UUID.randomUUID(),0,2,z)).isInstanceOf(dz.sh.hidra.modules.custody.application.port.in.SuppliedFluidPropertyRevisionUseCase.Failure.class)
            .extracting(e->((dz.sh.hidra.modules.custody.application.port.in.SuppliedFluidPropertyRevisionUseCase.Failure)e).reason())
            .isEqualTo(dz.sh.hidra.modules.custody.application.port.in.SuppliedFluidPropertyRevisionUseCase.Reason.INVALID_REQUEST);
        verify(fluidTarget(),never()).apply(any(),any());assertThat(versions(p)).isZero();assertThat(jdbc.queryForObject("SELECT count(*) FROM "+FR+" WHERE product_draft_id=?",Long.class,p)).isZero();
    }

    @Test void corruptStoredCodecRemainsUnavailableRatherThanInvalidUserInput() {
        fluidActor("LOCAL");UUID p=product();put(p,UUID.randomUUID(),0,1,absent());
        byte[] original=jdbc.queryForObject("SELECT canonical_payload FROM "+FV+" WHERE product_draft_id=? AND version=1",byte[].class,p);
        jdbc.execute("ALTER TABLE "+FV+" DISABLE TRIGGER custody_supplied_fluid_version_immutable");
        try {jdbc.update("UPDATE "+FV+" SET canonical_payload=int4send(1)||decode(repeat('00',14),'hex'),sha256=encode(sha256(int4send(1)||decode(repeat('00',14),'hex')),'hex') WHERE product_draft_id=? AND version=1",p);}
        finally {jdbc.execute("ALTER TABLE "+FV+" ENABLE TRIGGER custody_supplied_fluid_version_immutable");}
        try {
            assertThatThrownBy(()->get(COMPANY,p)).isInstanceOf(dz.sh.hidra.modules.custody.application.port.in.SuppliedFluidPropertyRevisionUseCase.Failure.class)
                .extracting(e->((dz.sh.hidra.modules.custody.application.port.in.SuppliedFluidPropertyRevisionUseCase.Failure)e).reason())
                .isEqualTo(dz.sh.hidra.modules.custody.application.port.in.SuppliedFluidPropertyRevisionUseCase.Reason.UNAVAILABLE);
        } finally {
            jdbc.execute("ALTER TABLE "+FV+" DISABLE TRIGGER custody_supplied_fluid_version_immutable");
            try {jdbc.update("UPDATE "+FV+" SET canonical_payload=?,sha256=encode(sha256(?),'hex') WHERE product_draft_id=? AND version=1",original,original,p);}
            finally {jdbc.execute("ALTER TABLE "+FV+" ENABLE TRIGGER custody_supplied_fluid_version_immutable");}
        }
        assertThat(versions(p)).isEqualTo(1);
    }
}
