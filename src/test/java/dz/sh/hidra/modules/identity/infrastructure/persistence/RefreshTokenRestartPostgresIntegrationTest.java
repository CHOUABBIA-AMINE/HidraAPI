package dz.sh.hidra.modules.identity.infrastructure.persistence;

import static org.assertj.core.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import dz.sh.hidra.HidraApplication;
import java.sql.Timestamp;
import java.time.Duration;
import java.time.Instant;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

/**
 * Two genuinely closed and freshly started Spring applications backed by one
 * disposable PostgreSQL 16 database. No mocked LOCAL signer or refresh owner.
 * Context restart is NOT a separate operating-system JVM restart or SITE proof.
 */
@Testcontainers(disabledWithoutDocker = true)
class RefreshTokenRestartPostgresIntegrationTest {
    @Container static final PostgreSQLContainer<?> POSTGRES =
            new PostgreSQLContainer<>("postgres:16").withDatabaseName("hidra_refresh_restart");
    private static final ObjectMapper JSON = new ObjectMapper();
    private static final String AUTH = "/api/v1/identity/authentication";
    private static final String FIRST_KEY = "synthetic-refresh-restart-signing-key-32-bytes-minimum";
    private static final String SECOND_KEY = "synthetic-refresh-restart-rotated-key-32-bytes-minimum";

    private ConfigurableApplicationContext start(String key) {
        return new SpringApplicationBuilder(HidraApplication.class)
                .profiles("test")
                .web(WebApplicationType.SERVLET)
                .run(
                        "--spring.datasource.url=" + POSTGRES.getJdbcUrl(),
                        "--spring.datasource.username=" + POSTGRES.getUsername(),
                        "--spring.datasource.password=" + POSTGRES.getPassword(),
                        "--spring.flyway.enabled=true",
                        "--spring.jpa.hibernate.ddl-auto=validate",
                        "--springdoc.api-docs.enabled=false",
                        "--springdoc.swagger-ui.enabled=false",
                        "--server.port=0",
                        "--hidra.identity.refresh.enabled=true",
                        "--hidra.identity.refresh.family-ttl-seconds=300",
                        "--hidra.platform.security.jwt.access-token-ttl-seconds=10",
                        "--hidra.platform.security.jwt.hmac-secret=" + key,
                        "--hidra.platform.security.jwt.token-issuer=https://hidra.synthetic.invalid/local",
                        "--hidra.platform.security.jwt.audience=hidra-api");
    }

    private static MockMvc http(ConfigurableApplicationContext app) {
        return MockMvcBuilders.webAppContextSetup((WebApplicationContext)app)
                .apply(org.springframework.security.test.web.servlet.setup
                        .SecurityMockMvcConfigurers.springSecurity()).build();
    }
    private record Actor(String id,String password) {}
    private static Actor fixture(ConfigurableApplicationContext app) {
        var jdbc=app.getBean(JdbcTemplate.class);
        var passwordEncoder=app.getBean(PasswordEncoder.class);
        String user=UUID.randomUUID().toString();
        String provider=UUID.randomUUID().toString();
        String credential=UUID.randomUUID().toString();
        String rawPassword="synthetic-restart-password";
        Timestamp now=Timestamp.from(Instant.now());
        jdbc.update("UPDATE public.hidra_identity_provider SET status='INACTIVE' WHERE status='ACTIVE'");
        jdbc.update("INSERT INTO public.hidra_identity_user "
                + "(id,username,user_type,status,failed_login_count,created_at,updated_at) "
                + "VALUES (?,?,'HUMAN','ACTIVE',0,?,?)",user,user,now,now);
        jdbc.update("INSERT INTO public.hidra_identity_provider "
                + "(id,code,name,provider_type,issuer_uri,directory_base_dn,user_search_base,status,"
                + "sync_enabled,just_in_time_provisioning_enabled,created_at,updated_at) "
                + "VALUES (?,?,'Synthetic restarted LOCAL','LOCAL',NULL,NULL,NULL,'ACTIVE',false,false,?,?)",
                provider,provider,now,now);
        jdbc.update("INSERT INTO public.hidra_identity_local_credential "
                + "(id,user_id,password_hash,credential_status,created_at,updated_at) "
                + "VALUES (?,?,?,'ACTIVE',?,?)",credential,user,
                passwordEncoder.encode(rawPassword),now,now);
        return new Actor(user,rawPassword);
    }
    private static JsonNode login(MockMvc http,Actor a) throws Exception {
        var response=http.perform(post(AUTH + "/login").contentType(MediaType.APPLICATION_JSON)
                .content(JSON.writeValueAsString(java.util.Map.of(
                        "providerType","LOCAL","principal",a.id(),"credentials",a.password()))))
                .andExpect(status().isOk()).andReturn();
        return JSON.readTree(response.getResponse().getContentAsString());
    }
    private static String refreshBody(String raw) throws Exception {
        return JSON.writeValueAsString(java.util.Map.of("refreshToken",raw));
    }

    @Test void applicationContextRestartPreservesOneTimeRefreshAfterRealBearerExpiry() throws Exception {
        Actor actor;
        String originalSecret, oldBearer;
        Instant accessExpiry;
        try(var original=start(FIRST_KEY)) {
            actor=fixture(original);
            var first=login(http(original),actor);
            originalSecret=first.path("refreshToken").asText();
            oldBearer=first.path("accessToken").asText();
            accessExpiry=Instant.parse(first.path("expiresAt").asText());
            assertThat(originalSecret).hasSize(80);
        }
        // No running original application context and no JWT/password held
        // in a Spring singleton; a second application independently discovers
        // the persisted family and verifies its one-use keyed digest.
        try(var restarted=start(FIRST_KEY)) {
            var web=http(restarted);
            // Spring JWT timestamp validation normally permits a 60-second
            // clock skew. Prove real expiry beyond that tolerance, not only
            // an altered or mocked exp claim.
            long remaining=Duration.between(Instant.now(),accessExpiry.plusSeconds(65)).toMillis();
            if(remaining>0) Thread.sleep(remaining);
            web.perform(get("/api/v1/identity/me")
                    .header("Authorization","Bearer " + oldBearer))
                    .andExpect(status().isUnauthorized());
            var renewed=JSON.readTree(web.perform(post(AUTH+"/refresh")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(refreshBody(originalSecret))).andExpect(status().isOk())
                    .andReturn().getResponse().getContentAsString());
            assertThat(renewed.path("refreshToken").asText()).hasSize(80)
                    .isNotEqualTo(originalSecret);
            assertThat(renewed.path("accessToken").asText()).isNotEqualTo(oldBearer);
            web.perform(get("/api/v1/identity/me")
                    .header("Authorization","Bearer " + renewed.path("accessToken").asText()))
                    .andExpect(status().isOk());
            web.perform(post(AUTH+"/refresh").contentType(MediaType.APPLICATION_JSON)
                    .content(refreshBody(originalSecret)))
                    .andExpect(status().isUnauthorized());
            int descendantStatus=web.perform(get("/api/v1/identity/me")
                    .header("Authorization","Bearer " + renewed.path("accessToken").asText()))
                    .andReturn().getResponse().getStatus();
            assertThat(descendantStatus).isIn(401,403);
            var jdbc=restarted.getBean(JdbcTemplate.class);
            assertThat(jdbc.queryForObject(
                    "SELECT count(*) FROM public.hidra_identity_refresh_family "
                    + "WHERE id=? AND revoked_at IS NOT NULL",Integer.class,
                    originalSecret.substring(0,36))).isEqualTo(1);
        }
    }

    @Test void newSigningKeyFailsClosedForOldProofAndBearerAcrossRestart() throws Exception {
        Actor actor;
        String oldBearer,oldSecret;
        try(var first=start(FIRST_KEY)) {
            actor=fixture(first);
            var login=login(http(first),actor);
            oldBearer=login.path("accessToken").asText();
            oldSecret=login.path("refreshToken").asText();
            assertThat(oldSecret).hasSize(80);
        }
        try(var rotated=start(SECOND_KEY)) {
            var web=http(rotated);
            web.perform(get("/api/v1/identity/me")
                    .header("Authorization","Bearer " + oldBearer))
                    .andExpect(status().isUnauthorized());
            var denied=web.perform(post(AUTH+"/refresh")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(refreshBody(oldSecret)))
                    .andExpect(status().isUnauthorized()).andReturn();
            assertThat(denied.getResponse().getContentAsString()).doesNotContain(oldSecret);
            // The old digest is not proof under the new key; a mismatch must
            // not grant revocation authority over the prior family.
            var jdbc=rotated.getBean(JdbcTemplate.class);
            assertThat(jdbc.queryForObject(
                    "SELECT count(*) FROM public.hidra_identity_refresh_family "
                    + "WHERE id=? AND revoked_at IS NOT NULL",Integer.class,
                    oldSecret.substring(0,36))).isZero();
            var fresh=login(web,actor);
            assertThat(fresh.path("accessToken").asText()).isNotEqualTo(oldBearer);
            var renewed=JSON.readTree(web.perform(post(AUTH+"/refresh")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(refreshBody(fresh.path("refreshToken").asText())))
                    .andExpect(status().isOk()).andReturn()
                    .getResponse().getContentAsString());
            assertThat(renewed.path("jti").asText()).isNotEqualTo(fresh.path("jti").asText());
            web.perform(get("/api/v1/identity/me")
                    .header("Authorization","Bearer " + renewed.path("accessToken").asText()))
                    .andExpect(status().isOk());
        }
    }
}
