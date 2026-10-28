/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : ProductEngineeringOperatorQualificationTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-28
 *
 * @Type        : Class
 * @Layer       : Test
 * @Module      : custody
 * @Package     : dz.sh.hidra.modules.custody.qualification
 *
 * @Description : Qualifies configured multi-product ownership, publication exports and legacy GAS compatibility.
 *
 */
package dz.sh.hidra.modules.custody.qualification;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import dz.sh.hidra.HidraApplication;
import dz.sh.hidra.modules.custody.application.contract.publication.ProductPublicationSourceContract;
import dz.sh.hidra.modules.custody.application.contract.publication.ProductPublicationSourceContract.Mode;
import dz.sh.hidra.modules.custody.application.contract.publication.ProductPublicationSourceContract.Query;
import dz.sh.hidra.modules.custody.domain.model.CustodyGasFluidRevision;
import dz.sh.hidra.modules.custody.application.contract.simulation.SimulationGasFluidRevisionContract;

import dz.sh.hidra.modules.custody.application.port.in.ProductRevisionPublicationUseCase;
import dz.sh.hidra.modules.custody.application.port.in.ProductRevisionPublicationUseCase.Kind;
import dz.sh.hidra.modules.custody.application.port.in.ProductRevisionPublicationUseCase.Selection;
import dz.sh.hidra.modules.workflow.application.service.WorkflowApplicationService;
import dz.sh.hidra.modules.workflow.application.port.in.ExecuteWorkflowTransitionUseCase;
import dz.sh.hidra.modules.workflow.application.port.out.WorkflowTaskRepositoryPort;
import dz.sh.hidra.modules.workflow.application.command.*;
import dz.sh.hidra.modules.custody.domain.model.ProductRevisionPublication.State;
import dz.sh.hidra.modules.custody.infrastructure.persistence.adapter.ProductRevisionPublicationCodec;
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
public class ProductEngineeringOperatorQualificationTest {
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
        http=org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup(webContext)
                .apply(org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity()).build();
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
        publicationRoles();
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

    @Autowired ProductRevisionPublicationUseCase publications;
    @Autowired WorkflowApplicationService workflows;
    @Autowired ExecuteWorkflowTransitionUseCase workflowTransitions;
    @Autowired WorkflowTaskRepositoryPort workflowTasks;
    @Autowired dz.sh.hidra.modules.custody.infrastructure.integration.CustodyProductPublicationWorkflowTargetLookup productionLookup;
    @MockitoSpyBean dz.sh.hidra.modules.custody.infrastructure.persistence.adapter.JdbcProductRevisionPublicationRepositoryAdapter publicationStorage;
    @MockitoSpyBean dz.sh.hidra.modules.custody.application.service.ProtectedProductPublicationAdmissionCoordinator publicationCoordinator;
    private static final String PP="public.hidra_custody_product_publication";
    private String definition,targetType,purpose;private Fixture currentActor;
    private void publicationRoles(){
        String tables=String.join(",",List.of(PP,PP+"_candidate",PP+"_head",PP+"_event",PP+"_request"));
        jdbc.execute("REVOKE ALL ON "+tables+" FROM c3_source_claim_app,c3_publication_app");
        jdbc.execute("GRANT SELECT ON "+tables+" TO c3_source_claim_app");jdbc.execute("GRANT SELECT,INSERT,UPDATE ON "+tables+" TO c3_publication_app");
        String policy="public.hidra_workflow_custody_publication_policy";jdbc.execute("REVOKE ALL ON "+policy+" FROM c3_source_claim_app,c3_publication_app");
        jdbc.execute("GRANT SELECT ON "+policy+" TO c3_source_claim_app");jdbc.execute("GRANT SELECT,UPDATE ON "+policy+" TO c3_publication_app");
    }
    private Fixture publicationActor(String type){var f=fluidActor(type);currentActor=f;
        var raw=((org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken)
                SecurityContextHolder.getContext().getAuthentication()).getToken().getTokenValue();
        var verified=nativeDecoder.decode(raw);assertThat(verified.getSubject()).isEqualTo(f.user());
        SecurityContextHolder.getContext().setAuthentication(
                new org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken(verified,List.of()));
        jdbc.update("UPDATE hidra_identity_user SET display_name='Synthetic Publication Reviewer' WHERE id=?",f.user());
        for(String action:List.of("read","prepare","publish","withdraw","reactivate"))for(String scope:List.of("custody-company:"+COMPANY,"custody-product:"+PRODUCT))publicationGrant(f,action,"GRANT",scope);
        return f;
    }
    private void publicationGrant(Fixture f,String action,String effect,String scope){
        String code="custody:engineering-product-publication:"+action;
        var ids=jdbc.queryForList("SELECT id FROM hidra_identity_permission WHERE code=?",String.class,code);String permission=ids.isEmpty()?id():ids.getFirst();
        if(ids.isEmpty())jdbc.update("INSERT INTO hidra_identity_permission(id,code,permission_domain,resource_type,action,sensitive,status,created_at,updated_at) VALUES (?,?,'custody','PRODUCT_ENGINEERING_PUBLICATION',?,false,'ACTIVE',now(),now())",permission,code,action);
        jdbc.update("INSERT INTO hidra_identity_user_permission_grant(id,user_id,permission_id,effect,scope_type,scope_reference_id,grant_reason,emergency_access,valid_from,valid_to,status,created_at) VALUES (?,?,?,?,'CUSTOM',?,'synthetic',false,now()-interval '1 hour',now()+interval '1 hour','ACTIVE',now())",id(),f.user(),permission,effect,scope);
    }
    private void configureWorkflow(){definition=id();targetType=id();purpose=id();
        for(var c:List.of(new String[]{targetType,"WORKFLOW_TARGET_TYPE","PRODUCT_SOURCE_PUBLICATION"},new String[]{purpose,"WORKFLOW_PURPOSE","PRODUCT_SOURCE_PUBLICATION_APPROVAL"}))jdbc.update("INSERT INTO hidra_workflow_type_catalog(id,catalog_name,code,active,sort_order,system_defined,created_at,updated_at) VALUES (?,?,?,true,0,false,now(),now())",c[0],c[1],c[2]);
        jdbc.update("INSERT INTO hidra_workflow_definition(id,code,name_fr,type_id,status,version,created_at,updated_at) VALUES (?,?,'Synthetic publication review',?,'ACTIVE',1,now(),now())",definition,definition,targetType);
        for(int n=1;n<=2;n++)jdbc.update("INSERT INTO hidra_workflow_step(id,definition_id,code,name_fr,step_order,mandatory,allow_claim,allow_delegation,allow_escalation,created_at,updated_at) VALUES (?,?,?,'Synthetic review',?,true,false,false,false,now(),now())",definition+n,definition,"STEP"+n,n);
        jdbc.update("INSERT INTO hidra_workflow_definition_target_binding(id,definition_id,target_module,target_type_id,workflow_purpose_id,active,created_at,updated_at) VALUES (?,?,'custody',?,?,true,now(),now())",id(),definition,targetType,purpose);
        jdbc.update("INSERT INTO hidra_workflow_transition(id,definition_id,from_step_id,to_step_id,decision,reason_required,comment_required,created_at,updated_at) VALUES (?,?,?,?,'APPROVE',false,false,now(),now())",definition+"approve",definition,definition+1,definition+2);
        jdbc.update("INSERT INTO hidra_workflow_custody_publication_policy VALUES (?,?,1,?,?,true,now(),now()) ON CONFLICT(company_party_id) DO UPDATE SET definition_id=excluded.definition_id,definition_version=1,target_type_id=excluded.target_type_id,purpose_id=excluded.purpose_id,active=true,updated_at=clock_timestamp()",COMPANY,definition,targetType,purpose);
    }
    private UUID preparedProduct(Facts facts){UUID p=product();put(p,UUID.randomUUID(),0,1,facts);configureWorkflow();return p;}
    private ProductRevisionPublicationUseCase.Command command(Kind k,String company,UUID p,UUID request,long mv,long fv,String digest,UUID publication,long epoch,UUID expected,Selection approval,Instant asOf,int limit,String cursor){return new ProductRevisionPublicationUseCase.Command(k,p,company,request,mv,fv,digest,publication,epoch,expected,approval,asOf,limit,cursor);}
    private ProductRevisionPublicationUseCase.Result preparePublication(UUID p,UUID request,long metadata,long fluid){return publications.execute(command(Kind.PREPARE,COMPANY,p,request,metadata,fluid,null,null,0,null,null,null,0,null));}
    private ProductRevisionPublicationUseCase.Result readPublication(Kind kind,UUID p,UUID publication){return publications.execute(command(kind,COMPANY,p,null,0,0,null,publication,0,null,null,null,0,null));}
    private Selection approve(String digest){
        var instance=workflows.startWorkflowInstance(new StartWorkflowInstanceCommand(definition,1,purpose,"custody",targetType,digest,null,null,definition+1,currentActor.user(),null,"ignored caller",null,"synthetic"));
        var task=workflows.createWorkflowTask(new CreateWorkflowTaskCommand(instance.id(),definition+1,currentActor.user(),null,null,null,null,null,null,null,null,"Synthetic publication review"));
        var actual=workflowTasks.findById(task.id()).orElseThrow();var done=workflowTransitions.execute(new ExecuteWorkflowTransitionCommand(task.id(),definition+"approve",actual.updatedAt(),null,null,null,"synthetic",currentActor.user(),null,"ignored caller",Set.of()));
        return new Selection(instance.id(),task.id(),done.actionId());
    }
    private ProductRevisionPublicationUseCase.Result publish(UUID p,String digest,Selection approval,long epoch,UUID expected,UUID request){return publications.execute(command(Kind.PUBLISH,COMPANY,p,request,0,0,digest,null,epoch,expected,approval,null,0,null));}
    private ProductRevisionPublicationUseCase.Result transition(Kind k,UUID p,UUID publication,long epoch,Selection approval,UUID request){return publications.execute(command(k,COMPANY,p,request,0,0,null,publication,epoch,publication,approval,null,0,null));}
    private ProductRevisionPublicationUseCase.Result published(UUID p){var candidate=preparePublication(p,UUID.randomUUID(),1,1);return publish(p,candidate.candidateDigest(),approve(candidate.candidateDigest()),0,null,UUID.randomUUID());}
    private long events(UUID p){return jdbc.queryForObject("SELECT count(*) FROM "+PP+"_event WHERE product_draft_id=?",Long.class,p);}
    private long candidates(UUID p){return jdbc.queryForObject("SELECT count(*) FROM "+PP+"_candidate WHERE product_draft_id=?",Long.class,p);}
    private dz.sh.hidra.modules.custody.infrastructure.persistence.adapter.JdbcProductRevisionPublicationRepositoryAdapter publicationTarget(){return org.springframework.test.util.AopTestUtils.getUltimateTargetObject(publicationStorage);}
    @Autowired org.springframework.web.context.WebApplicationContext webContext;
    private org.springframework.test.web.servlet.MockMvc http;
    @Autowired dz.sh.hidra.modules.custody.application.contract.publication.ProductPublicationSourceContract sources;
    private record HttpActor(Fixture fixture,String token) {}
    private HttpActor httpActor(String type){var f=publicationActor(type);var a=(org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken)SecurityContextHolder.getContext().getAuthentication();return new HttpActor(f,a.getToken().getTokenValue());}
    private String url(UUID p){return "/api/v1/custody/engineering-products/"+p+"/publications";}
    private String prep(UUID request){return "{\"requestId\":\""+request+"\",\"metadataVersion\":1,\"suppliedVersion\":1}";}
    private org.springframework.test.web.servlet.ResultActions send(HttpActor a,UUID p,String method,String suffix,String body,String... query)throws Exception{SecurityContextHolder.clearContext();var b=org.springframework.test.web.servlet.request.MockMvcRequestBuilders.request(org.springframework.http.HttpMethod.valueOf(method),url(p)+suffix).param("companyPartyId",COMPANY);if(a!=null)b.header("Authorization","Bearer "+a.token());for(int i=0;i<query.length;i+=2)b.param(query[i],query[i+1]);if(body!=null)b.contentType(org.springframework.http.MediaType.APPLICATION_JSON).content(body);return http.perform(b).andDo(result->{
        // Synthetic test diagnostics only: retain the real failure without weakening HTTP assertions.
        if(result.getResponse().getStatus()>=500){
            System.err.println("Publication HTTP failure: "+method+" "+suffix);
            var failure=result.getResolvedException();
            if(failure!=null)failure.printStackTrace(System.err);
        }
    });}
    private tools.jackson.databind.JsonNode json(org.springframework.test.web.servlet.ResultActions a)throws Exception{return JSON.readTree(a.andReturn().getResponse().getContentAsString());}
    private Selection humanApproval(HttpActor a,String digest){SecurityContextHolder.getContext().setAuthentication(new org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken(nativeDecoder.decode(a.token()),List.of()));currentActor=a.fixture();try{return approve(digest);}finally{SecurityContextHolder.clearContext();}}
    private String approvalJson(Selection a){return "{\"instanceId\":\""+a.instanceId()+"\",\"taskId\":\""+a.taskId()+"\",\"actionId\":\""+a.actionId()+"\"}";}
    private String publishJson(String digest,Selection a,UUID request,long epoch,UUID expected){return "{\"requestId\":\""+request+"\",\"candidateDigest\":\""+digest+"\",\"expectedEpoch\":"+epoch+",\"expectedPublicationId\":"+(expected==null?"null":"\""+expected+"\"")+",\"approval\":"+approvalJson(a)+"}";}
    private static final String BASE="/api/v1/custody/engineering-products";
    private record Flow(HttpActor actor,UUID product,String company,String digest,Selection approval,
                        UUID publication,UUID publishRequest,String publishBody) {}

    private org.springframework.test.web.servlet.ResultActions wire(HttpActor actor,String method,String path,
            String company,String body)throws Exception {
        SecurityContextHolder.clearContext();
        var request=org.springframework.test.web.servlet.request.MockMvcRequestBuilders.request(
                org.springframework.http.HttpMethod.valueOf(method),path);
        if(company!=null)request.param("companyPartyId",company);
        if(actor!=null)request.header("Authorization","Bearer "+actor.token());
        if(body!=null)request.contentType(org.springframework.http.MediaType.APPLICATION_JSON).content(body);
        return http.perform(request).andDo(result->{
            if(result.getResponse().getStatus()>=500 && result.getResolvedException()!=null)
                result.getResolvedException().printStackTrace(System.err);
        });
    }
    private String encode(Object value){return JSON.writeValueAsString(value);}
    private void extraCompany(HttpActor a,String company){
        for(String action:OPS)grant(a.fixture(),action,"GRANT","CUSTOM","custody-company:"+company);
        for(String action:List.of("read","write"))fluidGrant(a.fixture(),action,"GRANT","CUSTOM","custody-company:"+company);
        for(String action:List.of("read","prepare","publish","withdraw","reactivate"))
            publicationGrant(a.fixture(),action,"GRANT","custody-company:"+company);
    }
    private UUID createHttp(HttpActor a,String company,String code,String classification)throws Exception {
        var out=json(wire(a,"POST",BASE,null,encode(Map.of("companyPartyId",company,"localCode",code,
                "catalogProductId",PRODUCT,"creationRequestId",UUID.randomUUID().toString(),
                "name","Synthetic é","classification",classification))).andExpect(status().isCreated()));
        UUID p=UUID.fromString(out.get("draftId").asText());
        wire(a,"PUT",BASE+"/"+p,company,encode(Map.of("expectedVersion",1,"name","Revised é",
                "classification",classification))).andExpect(status().isOk()).andExpect(jsonPath("$.version").value(2));
        return p;
    }
    private void allUnits(){
        units();
        jdbc.update("INSERT INTO public.hidra_telemetry_unit(id,code,symbol,dimension,active,system_defined,created_at,updated_at) VALUES('fluid-viscosity','fluid-viscosity','mu','declared-dimension',true,false,now(),now()) ON CONFLICT(id) DO UPDATE SET active=true");
        jdbc.update("INSERT INTO public.hidra_telemetry_fluid_unit_policy(unit_id,usage_role,expected_dimension,active,created_at,updated_at) VALUES('fluid-viscosity','DYNAMIC_VISCOSITY','declared-dimension',true,now(),now()) ON CONFLICT(unit_id,usage_role) DO UPDATE SET active=true");
    }
    private Map<String,Object> sourceFact(){return Map.of("origin","SYNTHETIC","reference","synthetic-source","evidenceReference","synthetic-evidence");}
    private Map<String,Object> propertyFact(String property,String unit,String name){return Map.of(
            "observationId",name,"property",property,"value","1.200","unitId",unit,
            "absolutePressure",Map.of("value","100.00","unitId","fluid-pressure"),
            "absoluteTemperature",Map.of("value","300.00","unitId","fluid-temperature"),
            "method",Map.of("reference","synthetic-method","revision","1"),"source",sourceFact());}
    private Map<String,Object> factsBody(String basis,long version,long metadata){
        var body=new LinkedHashMap<String,Object>();body.put("requestId",UUID.randomUUID().toString());
        body.put("expectedVersion",version);body.put("expectedProductMetadataVersion",metadata);
        body.put("composition",Map.of("basis",basis,"components",List.of(Map.of("reference","A","fraction","0.8000"),Map.of("reference","B","fraction","0.2000")),
                "method",Map.of("reference","synthetic-method","revision","1"),"source",sourceFact()));
        body.put("properties",List.of(propertyFact("DENSITY","fluid-density","rho"),propertyFact("DYNAMIC_VISCOSITY","fluid-viscosity","mu")));
        body.put("methods",List.of(Map.of("key",Map.of("reference","synthetic-method","revision","1"),"origin","SYNTHETIC","basis",basis,
                "components",List.of("A","B"),"properties",List.of("DENSITY","DYNAMIC_VISCOSITY"),
                "absolutePressure",Map.of("minimum","1.00","maximum","1000","unitId","fluid-pressure"),
                "absoluteTemperature",Map.of("minimum","1.00","maximum","1000","unitId","fluid-temperature"),"evidenceReference","synthetic-method-evidence")));
        body.put("source",sourceFact());return body;
    }
    private UUID authored(HttpActor a,String classification,String basis,String company,String code)throws Exception {
        allUnits();UUID p=createHttp(a,company,code,classification);
        wire(a,"PUT",BASE+"/"+p+"/properties",company,encode(factsBody(basis,0,2)))
                .andExpect(status().isCreated()).andExpect(jsonPath("$.facts.composition.basis").value(basis));
        configureWorkflow();
        if(!company.equals(COMPANY))jdbc.update("INSERT INTO hidra_workflow_custody_publication_policy(company_party_id,definition_id,definition_version,target_type_id,purpose_id,active,created_at,updated_at) SELECT ?,definition_id,definition_version,target_type_id,purpose_id,active,created_at,updated_at FROM hidra_workflow_custody_publication_policy WHERE company_party_id=? ON CONFLICT(company_party_id) DO UPDATE SET definition_id=excluded.definition_id,target_type_id=excluded.target_type_id,purpose_id=excluded.purpose_id,active=true",company,COMPANY);
        return p;
    }
    private Flow finish(HttpActor a,UUID p,String company)throws Exception {
        var candidate=json(wire(a,"POST",url(p)+"/candidates",company,
                encode(Map.of("requestId",UUID.randomUUID().toString(),"metadataVersion",2,"suppliedVersion",1))).andExpect(status().isCreated()));
        String digest=candidate.get("candidateDigest").asText();var approval=humanApproval(a,digest);
        UUID request=UUID.randomUUID();String body=publishJson(digest,approval,request,0,null);
        var published=json(wire(a,"POST",url(p),company,body).andExpect(status().isCreated()));
        return new Flow(a,p,company,digest,approval,UUID.fromString(published.get("publication").get("publicationId").asText()),request,body);
    }
    private Flow full(String classification,String basis)throws Exception {
        var a=httpActor("LOCAL");return finish(a,authored(a,classification,basis,COMPANY,id()),COMPANY);
    }
    private ProductPublicationSourceContract.Source exported(Flow f,Mode mode,Instant at){
        ambient(f.actor().token());try{return sources.read(new Query(mode,f.company(),f.product(),mode==Mode.EXACT_HISTORICAL?f.publication():null,at)).orElseThrow();}
        finally{SecurityContextHolder.clearContext();}
    }
    private void exportDenied(Flow f,String company){
        ambient(f.actor().token());try{assertThatThrownBy(()->sources.read(new Query(Mode.EXACT_HISTORICAL,company,f.product(),f.publication(),null)))
                .isInstanceOf(ProductRevisionPublicationUseCase.Failure.class).hasMessage("Product publication DENIED");}
        finally{SecurityContextHolder.clearContext();}
    }
    private long[] rows(UUID p){return new long[]{candidates(p),row(PP,p),row(PP+"_head",p),events(p),row(PP+"_request",p)};}
    private long row(String table,UUID p){return jdbc.queryForObject("SELECT count(*) FROM "+table+" WHERE product_draft_id=?",Long.class,p);}
    private void withdraw(Flow f)throws Exception {
        wire(f.actor(),"POST",url(f.product())+"/"+f.publication()+"/withdraw",f.company(),encode(Map.of(
                "requestId",UUID.randomUUID().toString(),"expectedEpoch",1,"expectedPublicationId",f.publication().toString())))
                .andExpect(status().isOk()).andExpect(jsonPath("$.observedHead.state").value("WITHDRAWN"));
    }

    @Test void gasMoleSourceRoundTripsThroughOwnersAndExport()throws Exception {
        var f=full("GAS","MOLE_FRACTION");var source=exported(f,Mode.EXACT_HISTORICAL,null);
        assertThat(source.engineeringQualified()).isFalse();assertThat(source.currentlyEligible()).isFalse();
        assertThat(source.authoredMetadata().name()).isEqualTo("Revised é");assertThat(source.envelope().classification()).isEqualTo("GAS");
        assertThat(source.authoredFacts()).contains(new ProductPublicationSourceContract.ScalarFact("supplied.composition.components[0].fraction","0.8000"));
        var exact=json(wire(f.actor(),"GET",url(f.product())+"/"+f.publication(),COMPANY,null).andExpect(status().isOk()));
        assertThat(exact.get("publication").get("envelope").get("supplied").get("base64").asText()).isEqualTo(source.envelope().supplied().base64());
        assertThat(exported(f,Mode.ELIGIBLE_CURRENT,null).currentlyEligible()).isTrue();
        assertThat(source.envelope().metadata().version()).isEqualTo(2);assertThat(source.envelope().supplied().version()).isEqualTo(1);
    }
    @Test void liquidMassSourceRoundTripsWithoutNumericalQualification()throws Exception {
        var f=full("LIQUID","MASS_FRACTION");var s=exported(f,Mode.EXACT_HISTORICAL,null);
        assertThat(s.envelope().classification()).isEqualTo("LIQUID");assertThat(s.engineeringQualified()).isFalse();
        assertThat(s.authoredFacts()).contains(new ProductPublicationSourceContract.ScalarFact("supplied.composition.basis","MASS_FRACTION"),
                new ProductPublicationSourceContract.ScalarFact("supplied.properties[1].unitId","fluid-viscosity"),
                new ProductPublicationSourceContract.ScalarFact("supplied.methods[0].absolutePressure.minimum","1.00"));
        assertThat(s.envelope().units()).extracting(ProductPublicationSourceContract.Unit::role).contains("DENSITY","DYNAMIC_VISCOSITY","ABSOLUTE_PRESSURE","ABSOLUTE_TEMPERATURE");
        assertThat(legacyQuery.resolve(f.product().toString(),f.digest(),f.publication().toString(),Instant.now(),SimulationGasFluidRevisionContract.SupportedUse.STEADY_STATE)).isEmpty();
    }
    @Test void twoCompaniesShareCatalogWithoutSharingOwnedHistory()throws Exception {
        var a=httpActor("LOCAL");extraCompany(a,"other-company");String code=id();
        var left=finish(a,authored(a,"GAS","MOLE_FRACTION",COMPANY,code),COMPANY);
        var right=finish(a,authored(a,"LIQUID","MASS_FRACTION","other-company",code),"other-company");
        assertThat(left.product()).isNotEqualTo(right.product());
        assertThat(exported(left,Mode.EXACT_HISTORICAL,null).envelope().scope().catalogProductId()).isEqualTo(PRODUCT);
        assertThat(exported(right,Mode.EXACT_HISTORICAL,null).envelope().scope().companyPartyId()).isEqualTo("other-company");
        for(String suffix:List.of("/current","/"+left.publication(),"/history")) {
            if(suffix.equals("/history"))httpForeignHistory(left,"other-company");
            else wire(a,"GET",url(left.product())+suffix,"other-company",null).andExpect(status().isForbidden());
        }
        exportDenied(left,"other-company");exportDenied(right,COMPANY);
        assertThat(events(left.product())).isEqualTo(1);assertThat(events(right.product())).isEqualTo(1);
    }
    private void httpForeignHistory(Flow f,String company)throws Exception {
        SecurityContextHolder.clearContext();http.perform(org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get(url(f.product())+"/history")
            .param("companyPartyId",company).param("asOf",databaseNow().toString()).param("limit","10").header("Authorization","Bearer "+f.actor().token())).andExpect(status().isForbidden());
    }
    private Instant databaseNow(){return jdbc.queryForObject("SELECT clock_timestamp()",Timestamp.class).toInstant();}
    @Test void exactHistoricalExportSurvivesSourceAndHeadChanges()throws Exception {
        var f=full("GAS","MOLE_FRACTION");var original=exported(f,Mode.EXACT_HISTORICAL,null);
        wire(f.actor(),"PUT",BASE+"/"+f.product(),COMPANY,encode(Map.of("expectedVersion",2,"name","Newer","classification","GAS"))).andExpect(status().isOk());
        wire(f.actor(),"PUT",BASE+"/"+f.product()+"/properties",COMPANY,encode(factsBody("MOLE_FRACTION",1,3))).andExpect(status().isOk());
        withdraw(f);var later=exported(f,Mode.EXACT_HISTORICAL,null);
        assertThat(later.envelope()).isEqualTo(original.envelope());assertThat(later.approval()).isEqualTo(original.approval());
        assertThat(later.authoredFacts()).isEqualTo(original.authoredFacts());assertThat(later.observedHead().state()).isEqualTo("WITHDRAWN");
        for(Mode mode:List.of(Mode.ELIGIBLE_CURRENT,Mode.ELIGIBLE_EFFECTIVE)){
            ambient(f.actor().token());try{assertThat(sources.read(new Query(mode,COMPANY,f.product(),null,mode==Mode.ELIGIBLE_EFFECTIVE?databaseNow():null))).isEmpty();}finally{SecurityContextHolder.clearContext();}
        }
    }
    @Test void companyAndProductDenialsBlockReadPublishAndExport()throws Exception {
        var f=full("GAS","MOLE_FRACTION");long[] before=rows(f.product());
        for(String scope:List.of("custody-company:"+COMPANY,"custody-product:"+PRODUCT))for(String action:List.of("read","prepare","publish","withdraw","reactivate")){
            publicationGrant(f.actor().fixture(),action,"DENY",scope);
            if(action.equals("read")){
                wire(f.actor(),"GET",url(f.product())+"/"+f.publication(),COMPANY,null).andExpect(status().isForbidden());exportDenied(f,COMPANY);
            }else if(action.equals("publish"))wire(f.actor(),"POST",url(f.product()),COMPANY,f.publishBody()).andExpect(status().isForbidden());
            else if(action.equals("prepare"))wire(f.actor(),"POST",url(f.product())+"/candidates",COMPANY,encode(Map.of("requestId",UUID.randomUUID().toString(),"metadataVersion",2,"suppliedVersion",1))).andExpect(status().isForbidden());
            else {var body=new LinkedHashMap<String,Object>();body.put("requestId",UUID.randomUUID().toString());body.put("expectedEpoch",1);body.put("expectedPublicationId",f.publication().toString());if(action.equals("reactivate"))body.put("approval",Map.of("instanceId",f.approval().instanceId(),"taskId",f.approval().taskId(),"actionId",f.approval().actionId()));wire(f.actor(),"POST",url(f.product())+"/"+f.publication()+"/"+action,COMPANY,encode(body)).andExpect(status().isForbidden());}
            assertThat(rows(f.product())).containsExactly(before);
            jdbc.update("DELETE FROM hidra_identity_user_permission_grant WHERE user_id=? AND effect='DENY'",f.actor().fixture().user());
        }
    }
    @Test void revokedNativeSessionBlocksReplayAndExport()throws Exception {
        var f=full("GAS","MOLE_FRACTION");long[] before=rows(f.product());
        jdbc.update("UPDATE hidra_identity_login_session SET status='REVOKED',ended_at=clock_timestamp() WHERE user_id=? AND status='ACTIVE'",f.actor().fixture().user());
        wire(f.actor(),"POST",url(f.product()),COMPANY,f.publishBody()).andExpect(status().isForbidden());
        wire(f.actor(),"GET",url(f.product())+"/"+f.publication(),COMPANY,null).andExpect(status().isForbidden());exportDenied(f,COMPANY);
        assertThat(rows(f.product())).containsExactly(before);
    }
    @Test void staleAndMixedSourceVersionsCannotPublish()throws Exception {
        var f=full("GAS","MOLE_FRACTION");long[] before=rows(f.product());
        wire(f.actor(),"PUT",BASE+"/"+f.product(),COMPANY,encode(Map.of("expectedVersion",2,"name","New","classification","GAS"))).andExpect(status().isOk());
        wire(f.actor(),"POST",url(f.product())+"/candidates",COMPANY,encode(Map.of("requestId",UUID.randomUUID().toString(),"metadataVersion",3,"suppliedVersion",1))).andExpect(status().isConflict());
        wire(f.actor(),"PUT",BASE+"/"+f.product()+"/properties",COMPANY,encode(factsBody("MOLE_FRACTION",1,3))).andExpect(status().isOk());
        wire(f.actor(),"POST",url(f.product()),COMPANY,publishJson(f.digest(),f.approval(),UUID.randomUUID(),1,f.publication())).andExpect(status().isConflict());
        assertThat(rows(f.product())).containsExactly(before);
    }
    @Test void unsupportedLiquidCompressibilityNeverReachesStorage()throws Exception {
        var a=httpActor("LOCAL");allUnits();UUID p=createHttp(a,COMPANY,id(),"LIQUID");
        var body=factsBody("MASS_FRACTION",0,2);body.put("properties",List.of(propertyFact("COMPRESSIBILITY_FACTOR","fluid-density","z")));
        clearInvocations(fluidTarget());wire(a,"PUT",BASE+"/"+p+"/properties",COMPANY,encode(body)).andExpect(status().isBadRequest());
        verify(fluidTarget(),never()).apply(any(),any());assertThat(versions(p)).isZero();assertThat(row(FR,p)).isZero();assertThat(rows(p)).containsOnly(0);
    }
    @Test void malformedCompositionAndSelectorsNeverReachOwners()throws Exception {
        var a=httpActor("LOCAL");UUID p=createHttp(a,COMPANY,id(),"GAS");allUnits();
        String valid=encode(factsBody("MOLE_FRACTION",0,2));
        for(String bad:List.of(valid.replace("0.8000","0.7000"),valid.replace("\"B\"","\"A\""),valid.replace("\"expectedVersion\":0","\"expectedVersion\":\"0\""),valid.substring(0,valid.length()-1)+",\"actor\":\"forged\"}"))
            wire(a,"PUT",BASE+"/"+p+"/properties",COMPANY,bad).andExpect(status().isBadRequest());
        assertThatThrownBy(()->new Query(Mode.ELIGIBLE_CURRENT,COMPANY,p,UUID.randomUUID(),null)).isInstanceOf(IllegalArgumentException.class);
        assertThat(versions(p)).isZero();assertThat(rows(p)).containsOnly(0);
    }
    @Test void policyAndUnitRevocationPreventEligibleSelection()throws Exception {
        var f=full("GAS","MOLE_FRACTION");var exact=exported(f,Mode.EXACT_HISTORICAL,null);long[] before=rows(f.product());
        for(String sql:List.of("UPDATE hidra_custody_engineering_product_policy SET active=false WHERE catalog_product_id='"+PRODUCT+"'",
                "UPDATE hidra_telemetry_fluid_unit_policy SET active=false WHERE unit_id='fluid-density'",
                "UPDATE hidra_workflow_custody_publication_policy SET active=false WHERE company_party_id='"+COMPANY+"'")){
            jdbc.update(sql);ambient(f.actor().token());
            try{
                assertThatThrownBy(()->sources.read(new Query(Mode.ELIGIBLE_CURRENT,COMPANY,f.product(),null,null)))
                        .isInstanceOf(ProductRevisionPublicationUseCase.Failure.class)
                        .hasMessage("Product publication "+(sql.contains("engineering_product_policy")?"DENIED":"UNAVAILABLE"));
                assertThat(exported(f,Mode.EXACT_HISTORICAL,null).envelope()).isEqualTo(exact.envelope());
                assertThat(rows(f.product())).containsExactly(before);
            }finally{SecurityContextHolder.clearContext();jdbc.update(sql.replace("active=false","active=true"));}
        }
    }
    @Test void corruptedStoredBytesNeverBecomeFallbackSelection()throws Exception {
        var f=full("GAS","MOLE_FRACTION");var original=exported(f,Mode.EXACT_HISTORICAL,null);
        byte[] bytes=jdbc.queryForObject("SELECT canonical_payload FROM "+PP+" WHERE publication_id=?",byte[].class,f.publication());
        jdbc.execute("ALTER TABLE "+PP+" DISABLE TRIGGER USER");
        try{
            jdbc.update("UPDATE "+PP+" SET canonical_payload=canonical_payload||decode('00','hex'),sha256=encode(sha256(canonical_payload||decode('00','hex')),'hex') WHERE publication_id=?",f.publication());
            jdbc.execute("ALTER TABLE "+PP+" ENABLE TRIGGER USER");
            for(Mode mode:List.of(Mode.EXACT_HISTORICAL,Mode.ELIGIBLE_CURRENT)){
                ambient(f.actor().token());try{assertThatThrownBy(()->sources.read(new Query(mode,COMPANY,f.product(),mode==Mode.EXACT_HISTORICAL?f.publication():null,null))).isInstanceOf(ProductRevisionPublicationUseCase.Failure.class);}
                finally{SecurityContextHolder.clearContext();}
            }
            wire(f.actor(),"GET",url(f.product())+"/"+f.publication(),COMPANY,null).andExpect(status().isServiceUnavailable());
        }finally{
            jdbc.execute("ALTER TABLE "+PP+" DISABLE TRIGGER USER");
            jdbc.update("UPDATE "+PP+" SET canonical_payload=?,sha256=encode(sha256(?::bytea),'hex') WHERE publication_id=?",bytes,bytes,f.publication());
            jdbc.execute("ALTER TABLE "+PP+" ENABLE TRIGGER USER");
        }
        assertThat(exported(f,Mode.EXACT_HISTORICAL,null).envelope()).isEqualTo(original.envelope());
    }
    @Test void failedFinalGuardRollsBackPublicationAndReceipt()throws Exception {
        var a=httpActor("LOCAL");UUID p=authored(a,"GAS","MOLE_FRACTION",COMPANY,id());
        String digest=json(wire(a,"POST",url(p)+"/candidates",COMPANY,encode(Map.of("requestId",UUID.randomUUID().toString(),"metadataVersion",2,"suppliedVersion",1))).andExpect(status().isCreated())).get("candidateDigest").asText();
        var approval=humanApproval(a,digest);long[] before=rows(p);var reached=new AtomicBoolean();
        doAnswer(invocation->{
            var c=(dz.sh.hidra.modules.custody.application.service.ProtectedProductPublicationAdmissionCoordinator.Contribution)invocation.getArgument(1);
            invocation.getArguments()[1]=new dz.sh.hidra.modules.custody.application.service.ProtectedProductPublicationAdmissionCoordinator.Contribution(c.scope(),c.partyGeneration(),c.bindingId(),c.sessionId(),c.userId(),c.sampledAt(),c.sampledAt(),c.backendPid(),c.transactionId(),c.units(),c.policy(),c.approval());
            reached.set(true);return invocation.callRealMethod();
        }).when(publicationTarget()).finalGuard(any(),any(),anyList());
        var response=wire(a,"POST",url(p),COMPANY,publishJson(digest,approval,UUID.randomUUID(),0,null)).andExpect(status().isServiceUnavailable()).andReturn().getResponse();
        assertThat(reached).isTrue();verify(publicationTarget(),atLeastOnce()).apply(any(),any());assertThat(rows(p)).containsExactly(before);
        assertThat(response.getHeader("Retry-After")).isNull();assertThat(response.getContentAsString()).doesNotContain("SELECT","stackTrace","backend_pid");
    }

    @Autowired dz.sh.hidra.modules.custody.application.port.out.CustodyGasFluidRevisionRepositoryPort legacyRepository;
    @Autowired dz.sh.hidra.modules.custody.application.contract.simulation.SimulationProductCandidateContract legacyProducts;
    @Autowired SimulationGasFluidRevisionContract legacyQuery;
    @Autowired dz.sh.hidra.modules.custody.application.service.CustodyGasFluidQualificationService legacyQualifications;
    private record Legacy(CustodyGasFluidRevision revision,String qualification,SimulationGasFluidRevisionContract.QualifiedRevision exported) {}
    private Legacy legacy(HttpActor a){
        String suffix=id(),product="legacy-"+id(),definition="def-"+suffix,type="type-"+suffix,purpose="purpose-"+suffix;
        Instant at=Instant.parse("2020-01-01T00:00:00.123456789Z");
        jdbc.update("INSERT INTO hidra_custody_catalog_entry(id,catalog_name,code,active,sort_order,system_defined,created_at,updated_at) VALUES (?,'PRODUCT',?,true,0,false,?,?)",product,product,Timestamp.from(at),Timestamp.from(at));
        var catalog=legacyProducts.resolve(product).orElseThrow();
        var method=new CustodyGasFluidRevision.Method("method-"+suffix,"m1",at,at,null,CustodyGasFluidRevision.Origin.SYNTHETIC,"synthetic evidence",
                CustodyGasFluidRevision.InputRepresentation.GAS_MOLE_FRACTION,List.of("A","B"),new java.math.BigDecimal("100000.00"),new java.math.BigDecimal("900000.00"),new java.math.BigDecimal("250.00"),new java.math.BigDecimal("350.00"),List.of(CustodyGasFluidRevision.SupportedUse.STEADY_STATE));
        var revision=new CustodyGasFluidRevision("source-"+suffix,"r1",at,at,null,CustodyGasFluidRevision.Origin.SYNTHETIC,"synthetic source",
                new CustodyGasFluidRevision.ProductSnapshot(catalog.id(),catalog.catalogName(),catalog.code(),catalog.active(),catalog.createdAt(),catalog.updatedAt()),CustodyGasFluidRevision.ProductKind.GAS,method,
                List.of(new CustodyGasFluidRevision.Component("A",new java.math.BigDecimal("0.8000")),new CustodyGasFluidRevision.Component("B",new java.math.BigDecimal("0.2000"))),new CustodyGasFluidRevision.GovernanceBinding(definition,1,type,purpose));
        legacyRepository.append(revision);String digest=legacyRepository.findStored(revision.sourceId(),revision.revisionId()).orElseThrow().sha256();
        var approval=legacyApprove(a,definition,type,purpose,digest);String q="q-"+suffix;
        legacyQualifications.qualify(q,revision.sourceId(),revision.revisionId(),approval.instanceId(),approval.taskId(),approval.actionId());
        return new Legacy(revision,q,legacyQuery.resolve(revision.sourceId(),revision.revisionId(),q,Instant.now(),SimulationGasFluidRevisionContract.SupportedUse.STEADY_STATE).orElseThrow());
    }
    private dz.sh.hidra.modules.workflow.application.dto.WorkflowTransitionExecutionDto legacyApprove(HttpActor a,String definition,String type,String purpose,String digest){
        for(var c:List.of(new String[]{type,"WORKFLOW_TARGET_TYPE","GAS_FLUID_REVISION"},new String[]{purpose,"WORKFLOW_PURPOSE","GAS_FLUID_INPUT_QUALIFICATION"}))
            jdbc.update("INSERT INTO hidra_workflow_type_catalog(id,catalog_name,code,active,sort_order,system_defined,created_at,updated_at) VALUES (?,?,?,true,0,false,now(),now())",c[0],c[1],c[2]);
        jdbc.update("INSERT INTO hidra_workflow_definition(id,code,name_fr,type_id,status,version,created_at,updated_at) VALUES (?,?,'Synthetic GAS review',?,'ACTIVE',1,now(),now())",definition,definition,type);
        String from=definition+"-from",to=definition+"-to";
        for(var step:List.of(new Object[]{from,1},new Object[]{to,2}))jdbc.update("INSERT INTO hidra_workflow_step(id,definition_id,code,name_fr,step_order,mandatory,allow_claim,allow_delegation,allow_escalation,created_at,updated_at) VALUES (?,?,?,'Synthetic review',?,true,false,false,false,now(),now())",step[0],definition,step[0],step[1]);
        jdbc.update("INSERT INTO hidra_workflow_definition_target_binding(id,definition_id,target_module,target_type_id,workflow_purpose_id,active,created_at,updated_at) VALUES (?,?,'custody',?,?,true,now(),now())",definition+"-binding",definition,type,purpose);
        jdbc.update("INSERT INTO hidra_workflow_transition(id,definition_id,from_step_id,to_step_id,decision,reason_required,comment_required,created_at,updated_at) VALUES (?,?,?,?,'APPROVE',false,false,now(),now())",definition+"-approve",definition,from,to);
        var verified=nativeDecoder.decode(a.token());
        assertThat(verified.getSubject()).isEqualTo(a.fixture().user());
        SecurityContextHolder.getContext().setAuthentication(
                new org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken(verified,List.of()));
        try{
            var i=workflows.startWorkflowInstance(new StartWorkflowInstanceCommand(definition,1,purpose,"custody",type,digest,null,null,from,a.fixture().user(),null,"ignored caller display",null,"synthetic"));
            var task=workflows.createWorkflowTask(new CreateWorkflowTaskCommand(i.id(),from,a.fixture().user(),null,null,null,null,null,null,null,null,"Synthetic review"));
            var actual=workflowTasks.findById(task.id()).orElseThrow();return workflowTransitions.execute(new ExecuteWorkflowTransitionCommand(task.id(),definition+"-approve",actual.updatedAt(),null,null,null,"synthetic",a.fixture().user(),null,"ignored caller display",Set.of()));
        }finally{SecurityContextHolder.clearContext();}
    }
    @Test void legacyQualifiedGasReplayKeepsExactIdentity(){
        var a=httpActor("LOCAL");var l=legacy(a);var v=l.revision();var original=l.exported();
        assertThat(legacyRepository.append(v)).isEqualTo(v);
        var approval=original.qualification();assertThat(legacyQualifications.qualify(l.qualification(),v.sourceId(),v.revisionId(),approval.workflowInstanceId(),approval.workflowTaskId(),approval.workflowActionId()).qualificationId()).isEqualTo(l.qualification());
        var again=legacyQuery.resolve(v.sourceId(),v.revisionId(),l.qualification(),Instant.now(),SimulationGasFluidRevisionContract.SupportedUse.STEADY_STATE).orElseThrow();
        var codec=new dz.sh.hidra.modules.custody.infrastructure.persistence.adapter.CustodyGasFluidRevisionCodec();
        assertThat(again.stored().sha256()).isEqualTo(codec.sha256(codec.encode(v)));
        assertThat(jdbc.queryForObject("SELECT canonical_payload FROM hidra_custody_gas_fluid_revision WHERE source_id=? AND revision_id=?",byte[].class,v.sourceId(),v.revisionId())).containsExactly(codec.encode(v));
        assertThat(again).isEqualTo(original);assertThat(again.stored().payloadFormat()).isEqualTo(dz.sh.hidra.modules.custody.infrastructure.persistence.adapter.CustodyGasFluidRevisionCodec.FORMAT);
        assertThat(again.stored().revision().components().getFirst().moleFraction().toPlainString()).isEqualTo("0.8000");
        assertThat(legacyQuery.resolve(v.sourceId(),v.revisionId(),l.qualification(),Instant.now(),SimulationGasFluidRevisionContract.SupportedUse.TRANSIENT)).isEmpty();
        jdbc.update("UPDATE hidra_workflow_definition_target_binding SET active=false WHERE definition_id=?",v.governanceBinding().definitionId());
        assertThat(legacyQuery.resolve(v.sourceId(),v.revisionId(),l.qualification(),Instant.now(),SimulationGasFluidRevisionContract.SupportedUse.STEADY_STATE)).isEmpty();
    }
    @Test void publicationCannotSubstituteLegacyGasQualification()throws Exception {
        var f=full("GAS","MOLE_FRACTION");var l=legacy(f.actor());var v=l.revision();
        assertThat(legacyQuery.resolve(v.sourceId(),v.revisionId(),f.publication().toString(),Instant.now(),SimulationGasFluidRevisionContract.SupportedUse.STEADY_STATE)).isEmpty();
        assertThat(legacyQuery.resolve(f.product().toString(),f.digest(),l.qualification(),Instant.now(),SimulationGasFluidRevisionContract.SupportedUse.STEADY_STATE)).isEmpty();
        assertThat(legacyQuery.resolve(v.sourceId(),"missing",l.qualification(),Instant.now(),SimulationGasFluidRevisionContract.SupportedUse.STEADY_STATE)).isEmpty();
        exportDenied(f,"other-company");assertThat(exported(f,Mode.EXACT_HISTORICAL,null).engineeringQualified()).isFalse();
    }

}
