package dz.sh.hidra.modules.identity.infrastructure.persistence;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import dz.sh.hidra.HidraApplication;
import dz.sh.hidra.modules.identity.application.model.DirectAuthenticationCommand;
import dz.sh.hidra.modules.identity.application.port.in.AuthenticateDirectUserUseCase;
import dz.sh.hidra.modules.identity.domain.value.ProviderType;
import java.sql.Timestamp;
import java.time.Clock;
import java.time.Instant;
import java.time.ZoneOffset;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

/**
 * Real migrated PostgreSQL and genuine LOCAL login. Simulate the server's future
 * instant only; the row-lock database clock always remains an independent lower bound.
 */
@Testcontainers(disabledWithoutDocker = true)
@SpringBootTest(classes = HidraApplication.class,
        webEnvironment = SpringBootTest.WebEnvironment.MOCK,
        properties = {
            "hidra.identity.refresh.enabled=true",
            "hidra.identity.refresh.family-ttl-seconds=300",
            "hidra.platform.security.jwt.hmac-secret=synthetic-refresh-expiry-test-key-32-bytes-minimum",
            "hidra.platform.security.jwt.token-issuer=https://hidra.synthetic.invalid/local",
            "hidra.platform.security.jwt.audience=hidra-api"
        })
@ActiveProfiles("test")
class RefreshTokenFamilyHardExpiryPostgresIntegrationTest {
    @Container static final PostgreSQLContainer<?> POSTGRES =
            new PostgreSQLContainer<>("postgres:16").withDatabaseName("hidra_refresh_expiry");
    @DynamicPropertySource static void datasource(DynamicPropertyRegistry r) {
        r.add("spring.datasource.url", POSTGRES::getJdbcUrl);
        r.add("spring.datasource.username", POSTGRES::getUsername);
        r.add("spring.datasource.password", POSTGRES::getPassword);
        r.add("spring.flyway.enabled", () -> "true");
        r.add("spring.jpa.hibernate.ddl-auto", () -> "validate");
    }

    @Autowired JdbcTemplate jdbc;
    @Autowired PasswordEncoder passwords;
    @Autowired AuthenticateDirectUserUseCase login;
    @Autowired WebApplicationContext context;
    @MockitoBean(name = "externalOidcJwtDecoder")
    org.springframework.security.oauth2.jwt.JwtDecoder externalDecoder;
    @MockitoBean(name = "identityAuthenticationClock") Clock clock;
    private String user;

    @BeforeEach void fixture() {
        when(clock.instant()).thenAnswer(invocation -> Instant.now());
        when(clock.millis()).thenAnswer(invocation -> System.currentTimeMillis());
        when(clock.getZone()).thenReturn(ZoneOffset.UTC);
        user = UUID.randomUUID().toString();
        String provider = UUID.randomUUID().toString();
        String credential = UUID.randomUUID().toString();
        Timestamp now = Timestamp.from(Instant.now());
        jdbc.update("UPDATE public.hidra_identity_provider SET status='INACTIVE' WHERE status='ACTIVE'");
        jdbc.update("INSERT INTO public.hidra_identity_user "
                + "(id,username,user_type,status,failed_login_count,created_at,updated_at) "
                + "VALUES (?,?,'HUMAN','ACTIVE',0,?,?)", user,user,now,now);
        jdbc.update("INSERT INTO public.hidra_identity_provider "
                + "(id,code,name,provider_type,issuer_uri,directory_base_dn,user_search_base,status,"
                + "sync_enabled,just_in_time_provisioning_enabled,created_at,updated_at) "
                + "VALUES (?,?,'Synthetic expiring LOCAL','LOCAL',NULL,NULL,NULL,'ACTIVE',false,false,?,?)",
                provider,provider,now,now);
        jdbc.update("INSERT INTO public.hidra_identity_local_credential "
                + "(id,user_id,password_hash,credential_status,created_at,updated_at) "
                + "VALUES (?,?,?,'ACTIVE',?,?)",credential,user,
                passwords.encode("synthetic-expiry-password"),now,now);
    }

    @Test void actualPostgresFamilyDeadlineRevokesSessionWithoutConsumingProof() throws Exception {
        var first = login.authenticate(new DirectAuthenticationCommand(
                ProviderType.LOCAL, user, "synthetic-expiry-password", null,null,null));
        String opaque = first.refreshToken();
        String family = opaque.substring(0,36);
        Instant deadline = first.refreshExpiresAt();
        assertThat(deadline).isEqualTo(first.issuedAt().plusSeconds(300));
        assertThat(jdbc.queryForObject("SELECT deadline FROM public.hidra_identity_refresh_family "
                + "WHERE id=?",Timestamp.class,family).toInstant()).isEqualTo(deadline);
        // The owner observes a future expiry while real DB clock_timestamp() is
        // still consulted AFTER obtaining the family row lock.
        when(clock.instant()).thenReturn(deadline.plusSeconds(1));
        var http = MockMvcBuilders.webAppContextSetup(context)
                .apply(org.springframework.security.test.web.servlet.setup
                        .SecurityMockMvcConfigurers.springSecurity()).build();
        var response = http.perform(post("/api/v1/identity/authentication/refresh")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"refreshToken\":\"" + opaque + "\"}"))
                .andExpect(status().isUnauthorized()).andReturn();
        assertThat(response.getResponse().getContentAsString()).doesNotContain(opaque);
        assertThat(jdbc.queryForObject("SELECT count(*) FROM public.hidra_identity_refresh_family "
                + "WHERE id=? AND revoked_at IS NOT NULL",Integer.class,family)).isEqualTo(1);
        assertThat(jdbc.queryForObject("SELECT status FROM public.hidra_identity_login_session "
                + "WHERE id=?",String.class,first.sessionId())).isEqualTo("REVOKED");
        assertThat(jdbc.queryForObject("SELECT count(*) FROM public.hidra_identity_refresh_token "
                + "WHERE family_id=? AND generation>0",Integer.class,family)).isZero();
        assertThat(jdbc.queryForObject("SELECT count(*) FROM public.hidra_identity_refresh_token "
                + "WHERE family_id=? AND generation=0 AND consumed_at IS NULL",
                Integer.class,family)).isEqualTo(1);
        // A clock rollback must not bring back a revoked family or old bearer.
        when(clock.instant()).thenAnswer(invocation -> Instant.now());
        http.perform(post("/api/v1/identity/authentication/refresh")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"refreshToken\":\"" + opaque + "\"}"))
                .andExpect(status().isUnauthorized());
        int old = http.perform(get("/api/v1/identity/me")
                .header("Authorization","Bearer " + first.accessToken()))
                .andReturn().getResponse().getStatus();
        assertThat(old).isIn(401,403);
    }
}
