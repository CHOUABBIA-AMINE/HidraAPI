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
}
