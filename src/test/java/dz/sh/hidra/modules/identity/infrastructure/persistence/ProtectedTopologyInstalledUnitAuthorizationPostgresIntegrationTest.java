/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : ProtectedTopologyInstalledUnitAuthorizationPostgresIntegrationTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-29
 *
 * @Type        : Class
 * @Layer       : Identity Test
 * @Module      : identity
 * @Package     : dz.sh.hidra.modules.identity.infrastructure.persistence
 *
 * @Description : Verifies configured installed-unit authorization with real native Identity and PostgreSQL fences.
 *
 */
package dz.sh.hidra.modules.identity.infrastructure.persistence;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import dz.sh.hidra.HidraApplication;
import dz.sh.hidra.modules.identity.application.contract.topology.TopologyProtectedAuthorizationContract;

import dz.sh.hidra.modules.identity.application.contract.topology.TopologyInstalledUnitAuthorizationContract;
import dz.sh.hidra.modules.identity.application.contract.topology.TopologyInstalledUnitAuthorizationContract.*;

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
public class ProtectedTopologyInstalledUnitAuthorizationPostgresIntegrationTest {
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
                    .withDatabaseName("hidra_installed_identity_test")
                    .withCommand(
                            "postgres",
                            "-c",
                            "log_error_verbosity=terse",
                            "-c",
                            "track_commit_timestamp=on",
                            "-c", "max_locks_per_transaction=256",
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

    @Autowired TopologyInstalledUnitAuthorizationContract installed;
    private record Fixture(String user,String provider,String link,String credential,String subject,String type) {}
    private String schema;
    private static final String T="public.hidra_identity_";
    private static final String COMPANY="synthetic-company";
    @BeforeEach void initialize() {
        SecurityContextHolder.clearContext(); cleanEvidence(); flyway.validate();
        assertThat(flyway.info().pending()).isEmpty();
        when(externalDecoder.decode(anyString())).thenAnswer(i->realExternal.decode(i.getArgument(0)));
        schema="installed_"+id().replace("-","");
        jdbc.execute("CREATE SCHEMA "+schema); jdbc.execute("CREATE TABLE "+receipt()+"(id integer PRIMARY KEY)");
        c3NonownerCapture();
    }
    @AfterEach void clear() {
        ReflectionTestUtils.setField(oidc,"verifiedClaimReceiptEnabled",false);
        ReflectionTestUtils.setField(ldap,"verifiedClaimReceiptEnabled",false);
        directPort.finishVerification(); SecurityContextHolder.clearContext(); cleanEvidence();
        if(schema!=null)jdbc.execute("DROP SCHEMA "+schema+" CASCADE");
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

    private String receipt() {
        return schema + ".receipt";
    }

    private TransactionTemplate tx() {
        var t = new TransactionTemplate(manager);
        t.setIsolationLevel(TransactionDefinition.ISOLATION_READ_COMMITTED);
        return t;
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

    private Request request(Operation op) {
        return new Request(op,COMPANY,"physical-unit",
            op==Operation.REGISTER_UNIT || op==Operation.READ_UNIT ? null : "installation",
            op==Operation.RELOCATE ? "replacement" : null,
            switch(op) { case CLOSE_INSTALLATION, RELOCATE, READ_INSTALLATION, READ_HISTORY -> "source"; default -> null; },
            op==Operation.RECORD_INSTALLATION || op==Operation.RELOCATE ? "destination" : null);
    }
    private String permission(Request request) { return permission(request,request.requirements().getFirst().resourceType()); }
    private String permission(Request request,String resourceType) {
        String code=TopologyInstalledUnitAuthorizationContract.permissionCode(request.operation(),resourceType);
        var found=jdbc.queryForList("SELECT id FROM "+T+"permission WHERE code=?",String.class,code);
        if(!found.isEmpty())return found.getFirst();
        String id=id();
        jdbc.update("INSERT INTO "+T+"permission(id,code,permission_domain,resource_type,action,sensitive,status,created_at,updated_at) VALUES (?,?,'topology',?,?,false,'ACTIVE',clock_timestamp(),clock_timestamp())",
            id,code,resourceType,code.substring("topology:installed-unit:".length()));
        return id;
    }
    private String grant(Fixture f,Request request,Scope scope,String reference,String effect) {
        return grant(f,request,request.requirements().getFirst().resourceType(),scope,reference,effect);
    }
    private String grant(Fixture f,Request request,String resourceType,Scope scope,String reference,String effect) {
        String id=id();
        jdbc.update("INSERT INTO "+T+"user_permission_grant(id,user_id,permission_id,effect,scope_type,scope_reference_id,grant_reason,emergency_access,valid_from,valid_to,status,created_at) VALUES (?,?,?,?,?,?,'synthetic installed-unit',false,clock_timestamp()-interval '1 hour',clock_timestamp()+interval '1 hour','ACTIVE',clock_timestamp())",
            id,f.user(),permission(request,resourceType),effect,scope==Scope.COMPANY?"CUSTOM":"FACILITY",scope==Scope.COMPANY?"topology-company:"+reference:reference);
        return id;
    }
    private void grants(Fixture f,Request r) {
        var seen=new HashSet<String>();
        for(var q:r.requirements())if(seen.add(q.resourceType()+":"+q.scope()+":"+q.scopeReference()))
            grant(f,r,q.resourceType(),q.scope(),q.scopeReference(),"GRANT");
    }
    private TopologyInstalledUnitAuthorizationContract.Prepared prepared(Fixture f) {
        if(f.type().equals("LDAP") || f.type().equals("ACTIVE_DIRECTORY"))return directoryPrepared(f);
        ambient(authenticate(f).accessToken()); return installed.prepare();
    }
    private TopologyInstalledUnitAuthorizationContract.Prepared directoryPrepared(
            Fixture f) {
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
        attrs.put("memberOf", " private-original-directory-group ");
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
        ambient(authenticate(f).accessToken());return installed.prepare();
    }

    private TopologyInstalledUnitAuthorizationContract.Admission contribute(TopologyInstalledUnitAuthorizationContract.Prepared p,Request r) {
        var ticket=installed.acquire(p,r); var result=installed.contribute(ticket);
        assertThat(result.overallDecision()).isEqualTo(Decision.PERMIT);
        assertThat(result.request()).isEqualTo(r);
        assertThat(result.backendPid()).isEqualTo(jdbc.queryForObject("SELECT pg_backend_pid()",Integer.class));
        assertThat(result.transactionId()).isEqualTo(jdbc.queryForObject("SELECT txid_current()",Long.class));
        installed.requireCurrent(ticket); return ticket;
    }
    @Test void allSevenOperationsUseRealNativeIdentityAndExactPhysicalScopes() {
        var f=fixture("LOCAL");
        for(var op:List.of(Operation.REGISTER_UNIT,Operation.READ_UNIT,Operation.RECORD_INSTALLATION,Operation.CLOSE_INSTALLATION,Operation.RELOCATE,Operation.READ_INSTALLATION,Operation.READ_HISTORY)) {
            var r=request(op);grants(f,r);var p=prepared(f);
            tx().executeWithoutResult(s->{c3SelectRestrictedRole();var ticket=contribute(p,r);
                assertThat(installed.finalFences(ticket)).isEqualTo(JdbcIdentityAuthorityFence.requiredRelationFences());});
        }
    }
    @Test void externalSourceBindingUsesTheSameInstalledUnitFamily() {
        var f=fixture("OIDC");var r=request(Operation.READ_UNIT);grants(f,r);var p=prepared(f);
        tx().executeWithoutResult(s->{c3SelectRestrictedRole();contribute(p,r);});
    }
    @Test void missingCompanyOrEitherFacilityAndExplicitDenialReject() {
        var f=fixture("LOCAL"); var r=request(Operation.RELOCATE);
        for(String missing:List.of(COMPANY,"source","destination")) {
            cleanEvidence();
            if(!missing.equals(COMPANY))grant(f,r,Scope.COMPANY,COMPANY,"GRANT");
            if(!missing.equals("source"))grant(f,r,Scope.FACILITY,"source","GRANT");
            if(!missing.equals("destination"))grant(f,r,Scope.FACILITY,"destination","GRANT");
            var p=prepared(f);assertThatThrownBy(()->tx().executeWithoutResult(s->{c3SelectRestrictedRole();contribute(p,r);})).isInstanceOf(RuntimeException.class);
        }
        cleanEvidence();grants(f,r);grant(f,r,Scope.FACILITY,"destination","DENY");var p=prepared(f);
        assertThatThrownBy(()->tx().executeWithoutResult(s->{c3SelectRestrictedRole();contribute(p,r);})).isInstanceOf(RuntimeException.class);
    }
    @Test void sameFacilityRelocationDeduplicatesButBothResourceIdentitiesRemainBound() {
        var f=fixture("LOCAL");var r=new Request(Operation.RELOCATE,COMPANY,"unit","old","new","same","same");grants(f,r);
        var p=prepared(f);tx().executeWithoutResult(s->{c3SelectRestrictedRole();var a=installed.acquire(p,r);var c=installed.contribute(a);
            assertThat(c.decisions()).hasSize(4);assertThat(c.decisions().stream().map(d->d.requirement().resourceId()).distinct()).containsExactly("new","old");installed.requireCurrent(a);});
    }
    @Test void stationPermissionCannotSubstituteForInstalledUnitPermission() {
        var f=fixture("LOCAL");var r=request(Operation.READ_UNIT);
        jdbc.update("INSERT INTO "+T+"permission(id,code,permission_domain,resource_type,action,sensitive,status,created_at,updated_at) VALUES ('station-only','topology:station-revision:read-current','topology','STATION_GRAPH_REVISION','read-current',false,'ACTIVE',clock_timestamp(),clock_timestamp())");
        jdbc.update("INSERT INTO "+T+"user_permission_grant(id,user_id,permission_id,effect,scope_type,scope_reference_id,grant_reason,emergency_access,valid_from,valid_to,status,created_at) VALUES (?,?,'station-only','GRANT','CUSTOM',?,'synthetic',false,clock_timestamp()-interval '1 hour',clock_timestamp()+interval '1 hour','ACTIVE',clock_timestamp())",id(),f.user(),"topology-company:"+COMPANY);
        assertThat(jdbc.queryForObject("SELECT count(*) FROM "+T+"user_permission_grant g JOIN "+T+"permission p ON p.id=g.permission_id WHERE g.user_id=? AND p.code='topology:station-revision:read-current' AND g.valid_from<clock_timestamp() AND g.valid_to>clock_timestamp()",Integer.class,f.user())).isEqualTo(1);
        assertThat(jdbc.queryForObject("SELECT count(*) FROM "+T+"user_permission_grant g JOIN "+T+"permission p ON p.id=g.permission_id WHERE g.user_id=? AND p.code LIKE 'topology:installed-unit:%'",Integer.class,f.user())).isZero();
        var p=prepared(f);assertThatThrownBy(()->tx().executeWithoutResult(s->{c3SelectRestrictedRole();contribute(p,r);})).isInstanceOf(RuntimeException.class);
    }
    @Test void ownTransactionGrantRevocationFailsFinalRecheckAndRollsBackSentinel() {
        var f=fixture("LOCAL");var r=request(Operation.READ_UNIT);grants(f,r);var p=prepared(f);
        assertThatThrownBy(()->tx().executeWithoutResult(s->{c3SelectRestrictedRole();var a=contribute(p,r);
            jdbc.update("INSERT INTO "+receipt()+" VALUES (1)");
            jdbc.update("UPDATE "+T+"user_permission_grant SET status='REVOKED' WHERE user_id=?",f.user());
            installed.requireCurrent(a);
        })).isInstanceOf(RuntimeException.class);
        assertThat(jdbc.queryForObject("SELECT count(*) FROM "+receipt(),Integer.class)).isZero();
    }
    @Test void shortenedGrantValidityRejectsFinalRecheck() {
        var f=fixture("LOCAL");var r=request(Operation.READ_UNIT);grants(f,r);var p=prepared(f);
        assertThatThrownBy(()->tx().executeWithoutResult(s->{c3SelectRestrictedRole();var a=contribute(p,r);
            jdbc.update("UPDATE "+T+"user_permission_grant SET valid_to=clock_timestamp() WHERE user_id=?",f.user());installed.requireCurrent(a);
        })).isInstanceOf(RuntimeException.class);
    }
    @Test void replayForgedAndCrossTransactionTicketsAreRejected() {
        var f=fixture("LOCAL");var r=request(Operation.READ_UNIT);grants(f,r);var p=prepared(f);
        var held=new AtomicReference<TopologyInstalledUnitAuthorizationContract.Admission>();
        tx().executeWithoutResult(s->{c3SelectRestrictedRole();held.set(contribute(p,r));});
        assertThatThrownBy(()->tx().executeWithoutResult(s->{c3SelectRestrictedRole();installed.requireCurrent(held.get());})).isInstanceOf(RuntimeException.class);
        assertThatThrownBy(()->tx().executeWithoutResult(s->{c3SelectRestrictedRole();installed.acquire(p,r);})).isInstanceOf(RuntimeException.class);
        assertThatThrownBy(()->tx().executeWithoutResult(s->installed.contribute(new TopologyInstalledUnitAuthorizationContract.Admission(){}))).isInstanceOf(RuntimeException.class);
    }
    @Test void duplicateContributionAndSwallowedFailureVetoCommit() {
        var f=fixture("LOCAL");var r=request(Operation.READ_UNIT);grants(f,r);var p=prepared(f);
        assertThatThrownBy(()->tx().executeWithoutResult(s->{c3SelectRestrictedRole();var a=contribute(p,r);jdbc.update("INSERT INTO "+receipt()+" VALUES (2)");
            assertThatThrownBy(()->installed.contribute(a)).isInstanceOf(RuntimeException.class);
        })).isInstanceOf(RuntimeException.class);
        assertThat(jdbc.queryForObject("SELECT count(*) FROM "+receipt(),Integer.class)).isZero();
    }
    @Test void changedActorAndDatabaseRoleCannotKeepAdmissionCurrent() {
        var f=fixture("LOCAL");var r=request(Operation.READ_UNIT);grants(f,r);var p=prepared(f);
        assertThatThrownBy(()->tx().executeWithoutResult(s->{c3SelectRestrictedRole();var a=contribute(p,r);jdbc.queryForObject("SELECT set_config('role','none',true)",String.class);installed.requireCurrent(a);})).isInstanceOf(RuntimeException.class);
        var second=prepared(f);
        assertThatThrownBy(()->tx().executeWithoutResult(s->{c3SelectRestrictedRole();var a=contribute(second,r);SecurityContextHolder.clearContext();installed.requireCurrent(a);})).isInstanceOf(RuntimeException.class);
    }
    @Test void crossThreadMisusePoisonsTheOriginalTransaction() {
        var f=fixture("LOCAL");var r=request(Operation.READ_UNIT);grants(f,r);var p=prepared(f);
        assertThatThrownBy(()->tx().executeWithoutResult(s->{c3SelectRestrictedRole();var a=contribute(p,r);jdbc.update("INSERT INTO "+receipt()+" VALUES (3)");
            var thread=new Thread(()->assertThatThrownBy(()->installed.requireCurrent(a)).isInstanceOf(RuntimeException.class));
            thread.start();try {thread.join(2000);}catch(InterruptedException e){throw new IllegalStateException(e);}assertThat(thread.isAlive()).isFalse();
        })).isInstanceOf(RuntimeException.class);
        assertThat(jdbc.queryForObject("SELECT count(*) FROM "+receipt(),Integer.class)).isZero();
    }
    @Test void suspendedTransactionAndReadOnlyContextFailClosed() {
        var f=fixture("LOCAL");var r=request(Operation.READ_UNIT);grants(f,r);var p=prepared(f);
        assertThatThrownBy(()->tx().executeWithoutResult(s->{c3SelectRestrictedRole();var a=contribute(p,r);
            var other=tx();other.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);
            other.executeWithoutResult(t->assertThatThrownBy(()->installed.requireCurrent(a)).isInstanceOf(RuntimeException.class));
        })).isInstanceOf(RuntimeException.class);
        var second=prepared(f);var readOnly=tx();readOnly.setReadOnly(true);
        assertThatThrownBy(()->readOnly.executeWithoutResult(s->installed.acquire(second,r))).isInstanceOf(RuntimeException.class);
    }
    @Test void revokedNativeSessionCannotAdmit() {
        var f=fixture("LOCAL");var r=request(Operation.READ_UNIT);grants(f,r);var p=prepared(f);
        assertThat(jdbc.update("UPDATE "+S+" SET status='REVOKED', ended_at=clock_timestamp() WHERE user_id=? AND status='ACTIVE'",f.user())).isEqualTo(1);
        assertThat(jdbc.queryForObject("SELECT count(*) FROM "+S+" WHERE user_id=? AND status='REVOKED' AND ended_at>=started_at",Integer.class,f.user())).isEqualTo(1);
        assertThatThrownBy(()->tx().executeWithoutResult(s->{c3SelectRestrictedRole();contribute(p,r);})).isInstanceOf(RuntimeException.class);
    }
    @Test void retainedIdentityLocksRejectConflictingWriterNowait() {
        var f=fixture("LOCAL");var r=request(Operation.READ_UNIT);grants(f,r);var p=prepared(f);
        tx().executeWithoutResult(s->{c3SelectRestrictedRole();var a=contribute(p,r);
            try(var connection=DriverManager.getConnection(POSTGRES.getJdbcUrl(),POSTGRES.getUsername(),POSTGRES.getPassword())) {
                connection.setAutoCommit(false);
                try(var statement=connection.createStatement()) {
                    assertThatThrownBy(()->statement.execute("LOCK TABLE public.hidra_identity_user_permission_grant IN ROW EXCLUSIVE MODE NOWAIT"))
                        .isInstanceOf(SQLException.class).satisfies(error->assertThat(((SQLException)error).getSQLState()).isEqualTo("55P03"));
                } finally {connection.rollback();}
            } catch(SQLException e){throw new IllegalStateException(e);}installed.requireCurrent(a);
        });
    }
    @Test void finalPolicyGraphChangesAndMalformedDenyFailClosed() {
        var f=fixture("LOCAL");var r=request(Operation.READ_UNIT);grants(f,r);var p=prepared(f);
        assertThatThrownBy(()->tx().executeWithoutResult(status->{c3SelectRestrictedRole();var a=contribute(p,r);
            policy("DENY","{\"unknown\":[]}");installed.requireCurrent(a);
        })).isInstanceOf(RuntimeException.class);
        policy("DENY","{\"unknown\":[]}");var second=prepared(f);
        assertThatThrownBy(()->tx().executeWithoutResult(status->{c3SelectRestrictedRole();contribute(second,r);})).isInstanceOf(RuntimeException.class);
    }
    @Test void futureDenyBoundaryIsRetained() {
        var f=fixture("LOCAL");var r=request(Operation.READ_UNIT);grants(f,r);
        String deny=grant(f,r,Scope.COMPANY,COMPANY,"DENY");var boundary=dbNow().plusSeconds(30);
        jdbc.update("UPDATE "+T+"user_permission_grant SET status='REVOKED',valid_from=?,valid_to=? WHERE id=?",
            Timestamp.from(boundary),Timestamp.from(boundary.plusSeconds(30)),deny);
        var p=prepared(f);tx().executeWithoutResult(status->{c3SelectRestrictedRole();var a=installed.acquire(p,r);var c=installed.contribute(a);
            assertThat(c.validUntil()).isBeforeOrEqualTo(boundary);installed.requireCurrent(a);});

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


    @Test void actualSourceExpiryCannotBeExtendedByNativeInstalledUnitAdmission() throws Exception {
        var f=fixture("OIDC");var r=request(Operation.READ_UNIT);grants(f,r);
        var deadline=Instant.now().plusSeconds(5).truncatedTo(ChronoUnit.SECONDS);
        ambient(completeOidc(f,deadline).accessToken());var p=installed.prepare();
        Thread.sleep(Math.max(1,Duration.between(Instant.now(),deadline).toMillis()+100));
        assertThatThrownBy(()->tx().executeWithoutResult(status->{c3SelectRestrictedRole();contribute(p,r);})).isInstanceOf(RuntimeException.class);
    }
    @Test void nativeGroupRejectsRequestSwapBeforeDisclosure() {
        var f=fixture("LOCAL");var r=request(Operation.READ_UNIT);grants(f,r);ambient(authenticate(f).accessToken());
        var nativePrepared=participant.prepare();
        assertThatThrownBy(()->tx().executeWithoutResult(status->{c3SelectRestrictedRole();
            var group=participant.qualifyInstalledUnitGroup(participant.admit(nativePrepared),r);
            var swapped=new Request(Operation.READ_UNIT,COMPANY,"other-unit",null,null,null,null);
            reader.loadQualifiedInstalledUnitGroup(group,swapped);
        })).isInstanceOf(RuntimeException.class);
    }

    @Test void relocationRequiresPermitForBothInstallationResourceIdentities() {
        var f=fixture("LOCAL");var r=request(Operation.RELOCATE);
        for(String denied:List.of("installation","replacement")) {
            cleanEvidence();grants(f,r);
            policy("DENY","{\"op\":\"eq\",\"attribute\":\"resource.referenceId\",\"type\":\"STRING\",\"value\":\""+denied+"\"}");
            var p=prepared(f);
            assertThatThrownBy(()->tx().executeWithoutResult(status->{c3SelectRestrictedRole();contribute(p,r);})).isInstanceOf(RuntimeException.class);
        }
        cleanEvidence();grants(f,r);
        policy("DENY","{\"op\":\"eq\",\"attribute\":\"resource.referenceId\",\"type\":\"STRING\",\"value\":\"unrelated\"}");
        var p=prepared(f);tx().executeWithoutResult(status->{c3SelectRestrictedRole();contribute(p,r);});
    }

    private Request replacement() { return new Request(Operation.REPLACE,COMPANY,"old-unit","old-install","new-install","facility","facility","new-unit"); }
    @Test void replacementCarriesBothPermissionTypesThroughEveryNativeProducer() {
        var r=replacement();
        var observed=new AtomicReference<JdbcProtectedAuthorizationEvidenceReader.InstalledUnitAuthorizationEvidence>();
        doAnswer(inv->{var actual=(JdbcProtectedAuthorizationEvidenceReader.InstalledUnitAuthorizationEvidence)inv.callRealMethod();observed.set(actual);return actual;})
            .when(reader).loadQualifiedInstalledUnitGroup(any(),eq(r));
        try {
            for(String type:List.of("LOCAL","LDAP","ACTIVE_DIRECTORY","OIDC")) {
                cleanEvidence();var f=fixture(type);grants(f,r);var p=prepared(f);observed.set(null);
                tx().executeWithoutResult(status->{c3SelectRestrictedRole();var a=contribute(p,r);assertThat(installed.finalFences(a)).isEqualTo(JdbcIdentityAuthorityFence.requiredRelationFences());});
                var actual=observed.get();assertThat(actual).isNotNull();assertThat(actual.requests()).hasSize(6);
                for(int n=0;n<r.requirements().size();n++) {
                    var expected=r.requirements().get(n);var request=actual.requests().get(n);
                    assertThat(request.resourceReferenceId()).isEqualTo(expected.resourceId());
                    assertThat(request.resourceType()).isEqualTo(expected.resourceType());
                    assertThat(request.permissionCode()).isEqualTo(TopologyInstalledUnitAuthorizationContract.permissionCode(Operation.REPLACE,expected.resourceType()));
                }
                assertThat(actual.contribution().evidence().authenticationType()).isEqualTo(type);
            }
        }finally{reset(reader);}
    }
    @Test void replacementCannotOmitEitherPermissionComponentOrInstallationFacility() {
        var f=fixture("LOCAL");var r=replacement();
        for(String missing:List.of("INSTALLED_UNIT","INSTALLED_UNIT_INSTALLATION","FACILITY")) {
            cleanEvidence();grants(f,r);
            if(missing.equals("FACILITY"))jdbc.update("DELETE FROM "+T+"user_permission_grant WHERE user_id=? AND scope_type='FACILITY'",f.user());
            else jdbc.update("DELETE FROM "+T+"user_permission_grant WHERE user_id=? AND permission_id=?",f.user(),permission(r,missing));
            var p=prepared(f);assertThatThrownBy(()->tx().executeWithoutResult(status->{c3SelectRestrictedRole();contribute(p,r);})).isInstanceOf(RuntimeException.class);
        }
        cleanEvidence();grants(f,r);var p=prepared(f);tx().executeWithoutResult(status->{c3SelectRestrictedRole();contribute(p,r);});
    }
    @Test void replacementDenialOfEitherUnitOrInstallationRejectsTheWholeGroup() {
        var f=fixture("LOCAL");var r=replacement();
        for(String denied:List.of(r.unitId(),r.replacementUnitId(),r.installationId(),r.replacementInstallationId())) {
            cleanEvidence();grants(f,r);policy("DENY","{\"op\":\"eq\",\"attribute\":\"resource.referenceId\",\"type\":\"STRING\",\"value\":\""+denied+"\"}");
            var p=prepared(f);assertThatThrownBy(()->tx().executeWithoutResult(status->{c3SelectRestrictedRole();contribute(p,r);})).isInstanceOf(RuntimeException.class);
        }
        cleanEvidence();grants(f,r);var p=prepared(f);tx().executeWithoutResult(status->{c3SelectRestrictedRole();contribute(p,r);});
    }
    @Test void replacementNeverWaivesPermissionRowResourceTypeEligibility() {
        var f=fixture("LOCAL");var r=replacement();
        for(String type:List.of("INSTALLED_UNIT","INSTALLED_UNIT_INSTALLATION")) {
            cleanEvidence();grants(f,r);
            jdbc.update("UPDATE "+T+"permission SET resource_type=? WHERE id=?",type.equals("INSTALLED_UNIT")?"INSTALLED_UNIT_INSTALLATION":"INSTALLED_UNIT",permission(r,type));
            var p=prepared(f);assertThatThrownBy(()->tx().executeWithoutResult(status->{c3SelectRestrictedRole();contribute(p,r);})).isInstanceOf(RuntimeException.class);
        }
        cleanEvidence();grants(f,r);var p=prepared(f);tx().executeWithoutResult(status->{c3SelectRestrictedRole();contribute(p,r);});
    }
    @Test void replacementRequestSubstitutionPoisonsTheOriginalNativeGroup() {
        var f=fixture("LOCAL");var r=replacement();
        var swapped=List.of(
            new Request(Operation.REPLACE,COMPANY,"other-unit",r.installationId(),r.replacementInstallationId(),"facility","facility",r.replacementUnitId()),
            new Request(Operation.REPLACE,COMPANY,r.unitId(),r.installationId(),r.replacementInstallationId(),"facility","facility","other-unit"),
            new Request(Operation.REPLACE,COMPANY,r.unitId(),"other-install",r.replacementInstallationId(),"facility","facility",r.replacementUnitId()),
            new Request(Operation.REPLACE,COMPANY,r.unitId(),r.installationId(),"other-install","facility","facility",r.replacementUnitId()),
            new Request(Operation.REPLACE,COMPANY,r.unitId(),r.installationId(),r.replacementInstallationId(),"other-facility","other-facility",r.replacementUnitId()));
        for(var changed:swapped) {
            cleanEvidence();grants(f,r);ambient(authenticate(f).accessToken());var p=participant.prepare();
            assertThatThrownBy(()->tx().executeWithoutResult(status->{c3SelectRestrictedRole();jdbc.update("INSERT INTO "+receipt()+" VALUES (9)");
                var group=participant.qualifyInstalledUnitGroup(participant.admit(p),r);
                try{reader.loadQualifiedInstalledUnitGroup(group,changed);}catch(RuntimeException expected){/* Swallowing must not save the original transaction. */}
            })).isInstanceOf(RuntimeException.class);
            assertThat(jdbc.queryForObject("SELECT count(*) FROM "+receipt(),Integer.class)).isZero();
        }
    }
    @Test void replacementRechecksBothPermissionComponentsAndTemporalBounds() {
        var f=fixture("LOCAL");var r=replacement();
        for(String type:List.of("INSTALLED_UNIT","INSTALLED_UNIT_INSTALLATION"))for(boolean expire:List.of(false,true)) {
            cleanEvidence();grants(f,r);String permission=permission(r,type);var p=prepared(f);
            assertThatThrownBy(()->tx().executeWithoutResult(status->{c3SelectRestrictedRole();var a=contribute(p,r);jdbc.update("INSERT INTO "+receipt()+" VALUES (10)");
                if(expire)jdbc.update("UPDATE "+T+"user_permission_grant SET valid_to=clock_timestamp() WHERE user_id=? AND permission_id=?",f.user(),permission);
                else jdbc.update("UPDATE "+T+"user_permission_grant SET status='REVOKED' WHERE user_id=? AND permission_id=?",f.user(),permission);
                installed.requireCurrent(a);
            })).isInstanceOf(RuntimeException.class);
            assertThat(jdbc.queryForObject("SELECT count(*) FROM "+receipt(),Integer.class)).isZero();
        }
    }

}
