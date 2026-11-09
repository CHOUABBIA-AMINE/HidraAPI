package dz.sh.hidra.modules.identity.infrastructure.persistence;

import static org.assertj.core.api.Assertions.*;
import dz.sh.hidra.HidraApplication;
import dz.sh.hidra.modules.identity.application.model.DirectAuthenticationCommand;
import dz.sh.hidra.modules.identity.application.model.AuthenticationResult;
import dz.sh.hidra.modules.identity.application.port.in.AuthenticateDirectUserUseCase;
import dz.sh.hidra.modules.identity.application.port.in.RotateLocalRefreshTokenUseCase;
import dz.sh.hidra.modules.identity.application.port.out.OperationAuthenticationBindingRepositoryPort;
import dz.sh.hidra.modules.identity.domain.service.RefreshTokenRotationPolicy;
import dz.sh.hidra.modules.identity.domain.value.ProviderType;
import dz.sh.hidra.modules.identity.infrastructure.security.TrustedAuthenticationVerificationRegistry;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;
import java.util.UUID;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

/**
 * Fresh-schema, synthetic LOCAL authority only. Docker absence skips rather than pretending
 * database proof; exact SHA hosted CI is required to qualify real transactional semantics.
 */
@Testcontainers(disabledWithoutDocker=true)
@SpringBootTest(classes=HidraApplication.class,
        webEnvironment=SpringBootTest.WebEnvironment.MOCK,
        properties={
            "hidra.identity.refresh.enabled=true",
            "hidra.identity.refresh.family-ttl-seconds=604800",
            "hidra.platform.security.jwt.hmac-secret=synthetic-refresh-signing-key-for-integration-only",
            "hidra.platform.security.jwt.token-issuer=https://hidra.synthetic.invalid/local",
            "hidra.platform.security.jwt.audience=hidra-api"
        })
@ActiveProfiles("test")
class RefreshTokenRotationPostgresIntegrationTest {
    @Container static final PostgreSQLContainer<?> POSTGRES =
            new PostgreSQLContainer<>("postgres:16").withDatabaseName("hidra_refresh_test")
                    .withCommand("postgres","-c","log_error_verbosity=terse",
                        "-c","log_min_error_statement=panic","-c","log_parameter_max_length_on_error=0");
    @DynamicPropertySource static void properties(DynamicPropertyRegistry r) {
        r.add("spring.datasource.url",POSTGRES::getJdbcUrl);
        r.add("spring.datasource.username",POSTGRES::getUsername);
        r.add("spring.datasource.password",POSTGRES::getPassword);
        r.add("spring.flyway.enabled",()->"true");
        r.add("spring.jpa.hibernate.ddl-auto",()->"validate");
    }
    private static final ObjectMapper JSON = new ObjectMapper();
    private static final String AUTH = "/api/v1/identity/authentication";
    @Autowired WebApplicationContext webContext;
    @Autowired JdbcTemplate jdbc;
    @Autowired PasswordEncoder passwords;
    @Autowired AuthenticateDirectUserUseCase login;
    @Autowired RotateLocalRefreshTokenUseCase rotate;
    @Autowired OperationAuthenticationBindingRepositoryPort bindings;
    @Autowired TrustedAuthenticationVerificationRegistry registry;
    @Qualifier("hidraJwtDecoder") @Autowired JwtDecoder jwt;
    @MockitoBean(name="externalOidcJwtDecoder") JwtDecoder externalDecoder;
    private String user,provider;

    @BeforeEach void setup() {
        jdbc.update("UPDATE public.hidra_identity_provider SET status='INACTIVE' WHERE status='ACTIVE'");
        user=UUID.randomUUID().toString();
        provider=UUID.randomUUID().toString();
        String credential=UUID.randomUUID().toString();
        Timestamp now=Timestamp.from(Instant.now());
        jdbc.update("INSERT INTO public.hidra_identity_user "
                +"(id,username,user_type,status,failed_login_count,created_at,updated_at)"
                +" VALUES (?,?,'HUMAN','ACTIVE',0,?,?)",user,user,now,now);
        jdbc.update("INSERT INTO public.hidra_identity_provider "
                +"(id,code,name,provider_type,issuer_uri,directory_base_dn,user_search_base,status,"
                +"sync_enabled,just_in_time_provisioning_enabled,created_at,updated_at)"
                +" VALUES (?,?,'Synthetic LOCAL refresh','LOCAL',NULL,NULL,NULL,'ACTIVE',false,false,?,?)",
                provider,provider,now,now);
        jdbc.update("INSERT INTO public.hidra_identity_local_credential"
                +" (id,user_id,password_hash,credential_status,created_at,updated_at)"
                +" VALUES (?,?,?,'ACTIVE',?,?)",credential,user,
                passwords.encode("synthetic-refresh-password"),now,now);
    }
    private AuthenticationResult initial() {
        return login.authenticate(new DirectAuthenticationCommand(ProviderType.LOCAL,user,
                "synthetic-refresh-password",null,null,null));
    }
    private int count(String sql) { return jdbc.queryForObject(sql,Integer.class,user); }
    @Test void localLoginRefreshCreatesNewGenuineBoundSessionAndRetiresOldBearer() {
        var first=initial();
        assertThat(first.refreshToken()).hasSize(80);
        assertThat(first.refreshExpiresAt()).isAfter(first.expiresAt());
        var second=rotate.rotate(first.refreshToken(),null,null,null);
        assertThat(second.refreshToken()).hasSize(80).isNotEqualTo(first.refreshToken());
        assertThat(second.refreshExpiresAt()).isEqualTo(first.refreshExpiresAt());
        assertThat(second.jti()).isNotEqualTo(first.jti());
        assertThat(second.sessionId()).isNotEqualTo(first.sessionId());
        assertThat(jwt.decode(second.accessToken()).getId()).isEqualTo(second.jti());
        var b=bindings.findByNativeIssuerAndJti("https://hidra.synthetic.invalid/local",
                second.jti()).orElseThrow();
        registry.validatePersistentOrigin(b);
        assertThat(b.authenticationType()).isEqualTo("LOCAL");
        assertThat(b.sessionId()).isEqualTo(second.sessionId());
        assertThat(jdbc.queryForObject("SELECT status FROM public.hidra_identity_login_session WHERE id=?",
                String.class,first.sessionId())).isEqualTo("REVOKED");
        assertThat(jdbc.queryForObject("SELECT status FROM public.hidra_identity_login_session WHERE id=?",
                String.class,second.sessionId())).isEqualTo("ACTIVE");
        assertThat(count("SELECT count(*) FROM public.hidra_identity_authentication_event"
                +" WHERE user_id=? AND event_type='TOKEN_REFRESH'")).isEqualTo(1);
        assertThat(count("SELECT count(*) FROM public.hidra_identity_refresh_token t"
                +" JOIN public.hidra_identity_refresh_family f ON f.id=t.family_id"
                +" WHERE f.user_id=?")).isEqualTo(2);
    }
    @Test void consumedTokenReplayIrrevocablyRevokesDescendants() {
        var first=initial();
        var second=rotate.rotate(first.refreshToken(),null,null,null);
        assertThatThrownBy(()->rotate.rotate(first.refreshToken(),null,null,null))
                .isInstanceOf(RefreshTokenRotationPolicy.Replay.class);
        assertThat(count("SELECT count(*) FROM public.hidra_identity_refresh_family"
                +" WHERE user_id=? AND revoked_at IS NOT NULL")).isEqualTo(1);
        assertThat(jdbc.queryForObject("SELECT status FROM public.hidra_identity_login_session"
                +" WHERE id=?",String.class,second.sessionId())).isEqualTo("REVOKED");
        assertThatThrownBy(()->rotate.rotate(second.refreshToken(),null,null,null))
                .isInstanceOf(BadCredentialsException.class);
    }
    @Test void unknownProofCannotRevokeOrMintAnyNativeSession() {
        var first=initial();
        String forged=first.refreshToken().substring(0,37)+"A".repeat(43);
        assertThatThrownBy(()->rotate.rotate(forged,null,null,null))
                .isInstanceOf(BadCredentialsException.class);
        assertThat(count("SELECT count(*) FROM public.hidra_identity_login_session"
                +" WHERE user_id=?")).isEqualTo(1);
        assertThat(rotate.rotate(first.refreshToken(),null,null,null).accessToken()).isNotBlank();
    }
    @Test void disabledAccountOrCredentialGenerationChangeInvalidatesFamily() {
        var first=initial();
        jdbc.update("UPDATE public.hidra_identity_user SET status='DISABLED' WHERE id=?",user);
        assertThatThrownBy(()->rotate.rotate(first.refreshToken(),null,null,null))
                .isInstanceOf(RefreshTokenRotationPolicy.Rejected.class);
        assertThat(count("SELECT count(*) FROM public.hidra_identity_refresh_family"
                +" WHERE user_id=? AND revoked_at IS NOT NULL")).isEqualTo(1);
    }
    @Test void passwordResetBumpsDatabaseOwnedGenerationAndDeniesRefresh() {
        var first=initial();
        jdbc.update("UPDATE public.hidra_identity_local_credential SET password_hash=?,"
                +" password_changed_at=clock_timestamp() WHERE user_id=?",
                passwords.encode("synthetic-replacement-password"),user);
        assertThatThrownBy(()->rotate.rotate(first.refreshToken(),null,null,null))
                .isInstanceOf(RefreshTokenRotationPolicy.Rejected.class);
        assertThat(count("SELECT count(*) FROM public.hidra_identity_refresh_family"
                +" WHERE user_id=? AND revoked_at IS NOT NULL")).isEqualTo(1);
    }
    @Test void providerDeactivationAndUserLockBothRequireFreshLogin() {
        var first=initial();
        jdbc.update("UPDATE public.hidra_identity_provider SET status='INACTIVE' WHERE id=?",provider);
        assertThatThrownBy(()->rotate.rotate(first.refreshToken(),null,null,null))
                .isInstanceOf(RefreshTokenRotationPolicy.Rejected.class);
        assertThat(count("SELECT count(*) FROM public.hidra_identity_refresh_family"
                +" WHERE user_id=? AND revoked_at IS NOT NULL")).isEqualTo(1);
    }
    @Test void refreshProofKeyRotationChangesOnlyCryptographicDigest() {
        var first=initial();
        var before=new dz.sh.hidra.modules.identity.infrastructure.security.TrustedRefreshTokenEvidenceAdapter(
                true,604800,"synthetic-refresh-signing-key-for-integration-only",
                registry,bindings,
                org.mockito.Mockito.mock(dz.sh.hidra.modules.identity.application.port.out.AuthenticationSecurityStateQueryPort.class),
                org.mockito.Mockito.mock(dz.sh.hidra.modules.identity.infrastructure.persistence.repository.UserJpaRepository.class),
                org.mockito.Mockito.mock(dz.sh.hidra.modules.identity.application.port.in.IdentityAdministrationQueryUseCase.class),
                org.mockito.Mockito.mock(dz.sh.hidra.modules.identity.infrastructure.security.IdentityAdministratorGrantService.class));
        var after=new dz.sh.hidra.modules.identity.infrastructure.security.TrustedRefreshTokenEvidenceAdapter(
                true,604800,"synthetic-different-signing-key-for-rotation-test",
                registry,bindings,
                org.mockito.Mockito.mock(dz.sh.hidra.modules.identity.application.port.out.AuthenticationSecurityStateQueryPort.class),
                org.mockito.Mockito.mock(dz.sh.hidra.modules.identity.infrastructure.persistence.repository.UserJpaRepository.class),
                org.mockito.Mockito.mock(dz.sh.hidra.modules.identity.application.port.in.IdentityAdministrationQueryUseCase.class),
                org.mockito.Mockito.mock(dz.sh.hidra.modules.identity.infrastructure.security.IdentityAdministratorGrantService.class));
        var old=before.proof(first.refreshToken()).digest();
        var rotated=after.proof(first.refreshToken()).digest();
        assertThat(java.security.MessageDigest.isEqual(old,rotated)).isFalse();
    }

    @Test void logoutIsIdempotentAndInvalidatesOutstandingBearers() {
        var first=initial();
        rotate.logout(first.refreshToken());
        rotate.logout(first.refreshToken());
        assertThatThrownBy(()->rotate.rotate(first.refreshToken(),null,null,null))
                .isInstanceOf(BadCredentialsException.class);
        assertThat(jdbc.queryForObject("SELECT status FROM public.hidra_identity_login_session"
                +" WHERE id=?",String.class,first.sessionId())).isEqualTo("REVOKED");
    }
    @Test void concurrentUseCannotCreateTwoLiveDescendants() throws Exception {
        var first=initial();
        try(var pool=Executors.newFixedThreadPool(2)) {
            var go=new CountDownLatch(1);
            var f1=pool.submit(()->{go.await();return outcome(first.refreshToken());});
            var f2=pool.submit(()->{go.await();return outcome(first.refreshToken());});
            go.countDown();
            var results=List.of(f1.get(30,TimeUnit.SECONDS),f2.get(30,TimeUnit.SECONDS));
            assertThat(results).containsExactlyInAnyOrder("OK","REPLAY");
            assertThat(count("SELECT count(*) FROM public.hidra_identity_refresh_family"
                +" WHERE user_id=? AND revoked_at IS NOT NULL")).isEqualTo(1);
        }
    }
    private String outcome(String raw) {
        try { rotate.rotate(raw,null,null,null); return "OK"; }
        catch(RefreshTokenRotationPolicy.Replay reused) {return "REPLAY";}
    }

    private MockMvc nativeHttp() {
        org.springframework.security.core.context.SecurityContextHolder.clearContext();
        return MockMvcBuilders.webAppContextSetup(webContext)
                .apply(org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity())
                .build();
    }

    private JsonNode loginOverHttp(MockMvc http) throws Exception {
        var request = "{\"providerType\":\"LOCAL\",\"principal\":\"" + user
                + "\",\"credentials\":\"synthetic-refresh-password\"}";
        var result = http.perform(post(AUTH + "/login")
                .contentType(MediaType.APPLICATION_JSON).content(request))
                .andExpect(status().isOk()).andReturn();
        return JSON.readTree(result.getResponse().getContentAsString());
    }

    private static String refreshBody(String opaque) {
        return "{\"refreshToken\":\"" + opaque + "\"}";
    }

    @Test void nativeHttpLoginRefreshAndMeRequireNewCurrentBearer() throws Exception {
        var http = nativeHttp();
        var first = loginOverHttp(http);
        var oldBearer = first.path("accessToken").asText();
        var originalRefresh = first.path("refreshToken").asText();
        assertThat(originalRefresh).hasSize(80);
        assertThat(first.path("authenticationType").asText()).isEqualTo("LOCAL");

        http.perform(get("/api/v1/identity/me")
                .header("Authorization", "Bearer " + oldBearer)).andExpect(status().isOk());
        var nextResult = http.perform(post(AUTH + "/refresh")
                .contentType(MediaType.APPLICATION_JSON).content(refreshBody(originalRefresh)))
                .andExpect(status().isOk()).andReturn();
        var second = JSON.readTree(nextResult.getResponse().getContentAsString());
        var replacement = second.path("accessToken").asText();
        assertThat(replacement).isNotBlank().isNotEqualTo(oldBearer);
        assertThat(second.path("jti").asText()).isNotEqualTo(first.path("jti").asText());
        assertThat(second.path("sessionId").asText()).isNotEqualTo(first.path("sessionId").asText());
        assertThat(second.path("refreshExpiresAt").asText())
                .isEqualTo(first.path("refreshExpiresAt").asText());
        assertThat(second.path("refreshToken").asText()).hasSize(80)
                .isNotEqualTo(originalRefresh);
        http.perform(get("/api/v1/identity/me")
                .header("Authorization", "Bearer " + replacement))
                .andExpect(status().isOk());
        http.perform(get("/api/v1/identity/me/permissions")
                .header("Authorization", "Bearer " + replacement))
                .andExpect(status().isOk());
        int oldStatus = http.perform(get("/api/v1/identity/me")
                .header("Authorization", "Bearer " + oldBearer))
                .andReturn().getResponse().getStatus();
        assertThat(oldStatus).isIn(401, 403);
        // The refresh endpoint did not need the old access bearer at all.
        assertThat(count("SELECT count(*) FROM public.hidra_identity_authentication_event"
                + " WHERE user_id=? AND event_type='TOKEN_REFRESH'")).isEqualTo(1);
    }

    @Test void nativeHttpReplayRevokesDescendantsWithoutCredentialDisclosure() throws Exception {
        var http = nativeHttp();
        var first = loginOverHttp(http);
        var raw = first.path("refreshToken").asText();
        var secondResponse = http.perform(post(AUTH + "/refresh")
                .contentType(MediaType.APPLICATION_JSON).content(refreshBody(raw)))
                .andExpect(status().isOk()).andReturn();
        var second = JSON.readTree(secondResponse.getResponse().getContentAsString());
        var denied = http.perform(post(AUTH + "/refresh")
                .contentType(MediaType.APPLICATION_JSON).content(refreshBody(raw)))
                .andExpect(status().isUnauthorized()).andReturn();
        assertThat(denied.getResponse().getContentAsString()).doesNotContain(raw);
        int descendantStatus = http.perform(get("/api/v1/identity/me")
                .header("Authorization", "Bearer " + second.path("accessToken").asText()))
                .andReturn().getResponse().getStatus();
        assertThat(descendantStatus).isIn(401, 403);
        http.perform(post(AUTH + "/refresh").contentType(MediaType.APPLICATION_JSON)
                .content(refreshBody(second.path("refreshToken").asText())))
                .andExpect(status().isUnauthorized());
    }

    @Test void nativeHttpLogoutAndMalformedRefreshRemainNonDisclosing() throws Exception {
        var http = nativeHttp();
        var first = loginOverHttp(http);
        var raw = first.path("refreshToken").asText();
        var unknown = "A".repeat(80);
        var denied = http.perform(post(AUTH + "/refresh")
                .contentType(MediaType.APPLICATION_JSON).content(refreshBody(unknown)))
                .andExpect(status().isUnauthorized()).andReturn();
        assertThat(denied.getResponse().getContentAsString()).doesNotContain(unknown);
        http.perform(post(AUTH + "/refresh")
                .contentType(MediaType.APPLICATION_JSON).content(refreshBody("B".repeat(81))))
                .andExpect(status().isBadRequest());
        http.perform(post(AUTH + "/logout")
                .contentType(MediaType.APPLICATION_JSON).content(refreshBody(raw)))
                .andExpect(status().isNoContent());
        http.perform(post(AUTH + "/logout")
                .contentType(MediaType.APPLICATION_JSON).content(refreshBody(raw)))
                .andExpect(status().isNoContent());
        http.perform(post(AUTH + "/refresh")
                .contentType(MediaType.APPLICATION_JSON).content(refreshBody(raw)))
                .andExpect(status().isUnauthorized());
        int ended = http.perform(get("/api/v1/identity/me")
                .header("Authorization", "Bearer " + first.path("accessToken").asText()))
                .andReturn().getResponse().getStatus();
        assertThat(ended).isIn(401, 403);
    }

    @Test void currentGrantChangeRejectsAndRevokesExistingFamily() {
        var first=initial();
        String permissionId=UUID.randomUUID().toString();
        String permissionCode="identity:refresh:synthetic-"+UUID.randomUUID();
        jdbc.update("INSERT INTO public.hidra_identity_permission "
                +"(id,code,permission_domain,resource_type,action,sensitive,status,created_at,updated_at) "
                +"VALUES (?,?,'identity','USER','read',false,'ACTIVE',now(),now())",
                permissionId,permissionCode);
        jdbc.update("INSERT INTO public.hidra_identity_user_permission_grant "
                +"(id,user_id,permission_id,effect,scope_type,scope_reference_id,grant_reason,"
                +"emergency_access,valid_from,valid_to,status,created_at) "
                +"VALUES (?,?,?,'GRANT','GLOBAL',NULL,'synthetic refresh authority change',false,"
                +"now()-interval '1 hour',now()+interval '1 hour','ACTIVE',now())",
                UUID.randomUUID().toString(),user,permissionId);
        assertThatThrownBy(()->rotate.rotate(first.refreshToken(),null,null,null))
                .isInstanceOf(RefreshTokenRotationPolicy.Rejected.class);
        assertThat(count("SELECT count(*) FROM public.hidra_identity_refresh_family "
                +"WHERE user_id=? AND revoked_at IS NOT NULL")).isEqualTo(1);
        assertThat(count("SELECT count(*) FROM public.hidra_identity_refresh_token t "
                +"JOIN public.hidra_identity_refresh_family f ON f.id=t.family_id "
                +"WHERE f.user_id=? AND t.generation=1")).isZero();
        assertThat(jdbc.queryForObject("SELECT status FROM public.hidra_identity_login_session "
                +"WHERE id=?",String.class,first.sessionId())).isEqualTo("REVOKED");
    }

    @Test void failedRefreshAuditInsertRollsBackTokenSessionAndBinding() {
        var first=initial();
        // Disposable fresh-schema test fixture only; no production migration or
        // previously installed security trigger is modified.
        jdbc.execute("""
                CREATE FUNCTION public.hapi_929_synthetic_refresh_audit_failure()
                RETURNS trigger LANGUAGE plpgsql AS $$
                BEGIN
                    RAISE EXCEPTION 'Synthetic TOKEN_REFRESH rollback exercise'
                        USING ERRCODE='P0001';
                END;
                $$
                """);
        jdbc.execute("""
                CREATE TRIGGER hapi_929_synthetic_refresh_audit_failure
                BEFORE INSERT ON public.hidra_identity_authentication_event
                FOR EACH ROW EXECUTE FUNCTION public.hapi_929_synthetic_refresh_audit_failure()
                """);
        try {
            assertThatThrownBy(()->rotate.rotate(first.refreshToken(),null,null,null))
                    .isInstanceOf(RuntimeException.class);
            assertThat(jdbc.queryForObject("SELECT status FROM public.hidra_identity_login_session "
                    +"WHERE id=?",String.class,first.sessionId())).isEqualTo("ACTIVE");
            assertThat(count("SELECT count(*) FROM public.hidra_identity_refresh_token t "
                    +"JOIN public.hidra_identity_refresh_family f ON f.id=t.family_id "
                    +"WHERE f.user_id=? AND t.generation=0 AND t.consumed_at IS NULL")).isEqualTo(1);
            assertThat(count("SELECT count(*) FROM public.hidra_identity_refresh_token t "
                    +"JOIN public.hidra_identity_refresh_family f ON f.id=t.family_id "
                    +"WHERE f.user_id=? AND t.generation>0")).isZero();
            assertThat(count("SELECT count(*) FROM public.hidra_identity_authentication_event "
                    +"WHERE user_id=? AND event_type='TOKEN_REFRESH'")).isZero();
        } finally {
            jdbc.execute("DROP TRIGGER IF EXISTS hapi_929_synthetic_refresh_audit_failure "
                    +"ON public.hidra_identity_authentication_event");
            jdbc.execute("DROP FUNCTION IF EXISTS public.hapi_929_synthetic_refresh_audit_failure()");
        }
        // A commit failure must not turn the only good secret into a consumed token.
        var retry=rotate.rotate(first.refreshToken(),null,null,null);
        assertThat(retry.refreshToken()).hasSize(80).isNotEqualTo(first.refreshToken());
        assertThat(retry.jti()).isNotEqualTo(first.jti());
    }

    @Test void persistedRefreshCredentialSurvivesIndependentDatabaseConnection() {
        var first=initial();
        String family=first.refreshToken().substring(0,36);
        var independent=new JdbcTemplate(new org.springframework.jdbc.datasource.DriverManagerDataSource(
                POSTGRES.getJdbcUrl(),POSTGRES.getUsername(),POSTGRES.getPassword()));
        assertThat(independent.queryForObject(
                "SELECT count(*) FROM public.hidra_identity_refresh_family WHERE id=?",
                Integer.class,family)).isEqualTo(1);
        assertThat(independent.queryForObject(
                "SELECT count(*) FROM public.hidra_identity_refresh_token WHERE family_id=? "
                +"AND generation=0 AND consumed_at IS NULL",Integer.class,family)).isEqualTo(1);
        var renewed=rotate.rotate(first.refreshToken(),null,null,null);
        assertThat(renewed.refreshToken()).hasSize(80);
        assertThat(independent.queryForObject(
                "SELECT count(*) FROM public.hidra_identity_refresh_token WHERE family_id=? "
                +"AND generation=1 AND consumed_at IS NULL",Integer.class,family)).isEqualTo(1);
    }

    @Test void nativeHttpInjectionShapedRefreshCannotMutateValidFamily() throws Exception {
        var http=nativeHttp();
        var first=loginOverHttp(http);
        String genuine=first.path("refreshToken").asText();
        String malformed=genuine.substring(0,37)
                +("' OR 1=1 --".repeat(4)).substring(0,43);
        String json=JSON.writeValueAsString(java.util.Map.of("refreshToken",malformed));
        var denial=http.perform(post(AUTH+"/refresh")
                .contentType(MediaType.APPLICATION_JSON).content(json))
                .andExpect(status().isUnauthorized()).andReturn();
        assertThat(denial.getResponse().getContentAsString()).doesNotContain(malformed);
        assertThat(count("SELECT count(*) FROM public.hidra_identity_refresh_family "
                +"WHERE user_id=? AND revoked_at IS NULL")).isEqualTo(1);
        assertThat(count("SELECT count(*) FROM public.hidra_identity_login_session "
                +"WHERE user_id=? AND status='ACTIVE'")).isEqualTo(1);
        http.perform(post(AUTH+"/refresh").contentType(MediaType.APPLICATION_JSON)
                .content(refreshBody(genuine))).andExpect(status().isOk());
    }

    @Test void lockedHumanActorCannotRenewAndFamilyRevokes() {
        var first=initial();
        jdbc.update("UPDATE public.hidra_identity_user SET locked_until=now()+interval '1 hour' "
                +"WHERE id=?",user);
        assertThatThrownBy(()->rotate.rotate(first.refreshToken(),null,null,null))
                .isInstanceOf(RefreshTokenRotationPolicy.Rejected.class);
        assertThat(count("SELECT count(*) FROM public.hidra_identity_refresh_family "
                +"WHERE user_id=? AND revoked_at IS NOT NULL")).isEqualTo(1);
        assertThat(jdbc.queryForObject("SELECT status FROM public.hidra_identity_login_session "
                +"WHERE id=?",String.class,first.sessionId())).isEqualTo("REVOKED");
        assertThat(count("SELECT count(*) FROM public.hidra_identity_refresh_token t "
                +"JOIN public.hidra_identity_refresh_family f ON f.id=t.family_id "
                +"WHERE f.user_id=? AND t.generation>0")).isZero();
    }

    @Test void rotatedProofKeyCannotResolveOrRevokeExistingFamily() {
        var first=initial();
        var rotatedKeyEvidence=new dz.sh.hidra.modules.identity.infrastructure.security.TrustedRefreshTokenEvidenceAdapter(
                true,604800,"synthetic-independent-rotated-refresh-key-only",
                registry,bindings,
                org.mockito.Mockito.mock(dz.sh.hidra.modules.identity.application.port.out.AuthenticationSecurityStateQueryPort.class),
                org.mockito.Mockito.mock(dz.sh.hidra.modules.identity.infrastructure.persistence.repository.UserJpaRepository.class),
                org.mockito.Mockito.mock(dz.sh.hidra.modules.identity.application.port.in.IdentityAdministrationQueryUseCase.class),
                org.mockito.Mockito.mock(dz.sh.hidra.modules.identity.infrastructure.security.IdentityAdministratorGrantService.class));
        var rotatedProof=rotatedKeyEvidence.proof(first.refreshToken());
        assertThat(jdbc.queryForObject(
                "SELECT count(*) FROM public.hidra_identity_refresh_token t "
                +"JOIN public.hidra_identity_refresh_family f ON f.id=t.family_id "
                +"WHERE f.user_id=? AND t.digest=?",Integer.class,user,rotatedProof.digest()))
                .isZero();
        assertThat(count("SELECT count(*) FROM public.hidra_identity_refresh_family "
                +"WHERE user_id=? AND revoked_at IS NOT NULL")).isZero();
        // A mismatched digest is not a revocation authority. The genuine key and
        // original one-time LOCAL secret remain usable by the trusted owner.
        var valid=rotate.rotate(first.refreshToken(),null,null,null);
        assertThat(valid.refreshToken()).hasSize(80).isNotEqualTo(first.refreshToken());
        assertThat(valid.jti()).isNotEqualTo(first.jti());
    }
}
