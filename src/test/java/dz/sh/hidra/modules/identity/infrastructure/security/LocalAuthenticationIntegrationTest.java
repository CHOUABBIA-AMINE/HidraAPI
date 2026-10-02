/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : LocalAuthenticationIntegrationTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-15
 *
 * @Type        : Class
 * @Layer       : Test
 * @Module      : identity
 * @Package     : dz.sh.hidra.modules.identity.infrastructure.security
 *
 * @Description : Proves persisted PostgreSQL LOCAL credentials authenticate through the real Spring Identity infrastructure.
 *
 */
package dz.sh.hidra.modules.identity.infrastructure.security;

import static org.assertj.core.api.Assertions.assertThat;

import dz.sh.hidra.HidraApplication;
import dz.sh.hidra.modules.identity.application.port.out.LocalCredentialRepositoryPort;
import dz.sh.hidra.modules.identity.domain.model.HidraPrincipal;
import dz.sh.hidra.modules.identity.domain.model.LocalCredential;
import dz.sh.hidra.modules.identity.domain.value.ProviderType;
import dz.sh.hidra.modules.identity.domain.value.UserStatus;
import dz.sh.hidra.modules.identity.domain.value.UserType;
import dz.sh.hidra.modules.identity.infrastructure.persistence.entity.UserJpaEntity;
import dz.sh.hidra.modules.identity.infrastructure.persistence.repository.UserJpaRepository;
import dz.sh.hidra.platform.security.LocalAuthenticationToken;
import java.sql.Timestamp;
import java.time.Instant;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

@Testcontainers(disabledWithoutDocker = true)
@SpringBootTest(classes = HidraApplication.class, webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@ActiveProfiles("test")
class LocalAuthenticationIntegrationTest {

    private static final String PROVIDER_ID = "provider-local-integration";

    @Container
    static final PostgreSQLContainer<?> POSTGRESQL =
            new PostgreSQLContainer<>(DockerImageName.parse("postgres:18-alpine"))
                    .withDatabaseName("hidra_local_auth_test")
                    .withUsername("hidra")
                    .withPassword("hidra");

    @Autowired
    LocalAuthenticationProvider authenticationProvider;

    @Autowired
    UserJpaRepository userRepository;

    @Autowired
    LocalCredentialRepositoryPort localCredentialRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    JdbcTemplate jdbcTemplate;

    @DynamicPropertySource
    static void databaseProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", POSTGRESQL::getJdbcUrl);
        registry.add("spring.datasource.username", POSTGRESQL::getUsername);
        registry.add("spring.datasource.password", POSTGRESQL::getPassword);
        registry.add("spring.jpa.hibernate.ddl-auto", () -> "validate");
        registry.add("spring.flyway.enabled", () -> "true");
        registry.add("spring.flyway.validate-on-migrate", () -> "true");
    }

    @Test
    void authenticatesCredentialPersistedThroughIdentityRepositories() {
        Instant now = Instant.parse("2026-09-15T09:00:00Z");
        jdbcTemplate.update(
                """
                INSERT INTO hidra_identity_provider (
                    id, code, name, provider_type, sync_enabled,
                    just_in_time_provisioning_enabled, status, created_at, updated_at
                ) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)
                """,
                PROVIDER_ID,
                "LOCAL_INTEGRATION",
                "Local Integration",
                "LOCAL",
                false,
                false,
                "ACTIVE",
                Timestamp.from(now),
                Timestamp.from(now)
        );

        UserJpaEntity user = new UserJpaEntity(
                "user-local-integration",
                "local.integration",
                "local.integration@hidra.invalid",
                "Local Integration User",
                UserType.HUMAN,
                UserStatus.ACTIVE,
                null,
                null,
                0,
                null,
                now,
                now,
                null,
                null,
                now
        );
        userRepository.save(user);

        String password = "integration-secret";
        String passwordHash = passwordEncoder.encode(password);
        localCredentialRepository.save(new LocalCredential(
                "credential-local-integration",
                user.id(),
                passwordHash,
                "ACTIVE",
                now,
                now,
                now
        ));

        Authentication authentication = authenticationProvider.authenticate(
                LocalAuthenticationToken.unauthenticated(user.username(), password)
        );

        assertThat(authentication.isAuthenticated()).isTrue();
        assertThat(authentication.getPrincipal()).isInstanceOf(HidraPrincipal.class);
        HidraPrincipal principal = (HidraPrincipal) authentication.getPrincipal();
        assertThat(principal.userId()).isEqualTo(user.id());
        assertThat(principal.username()).isEqualTo(user.username());
        assertThat(principal.authenticationType()).isEqualTo(ProviderType.LOCAL);
        assertThat(principal.identityProviderId()).isEqualTo(PROVIDER_ID);

        LocalCredential persistedCredential = localCredentialRepository.findByUserId(user.id()).orElseThrow();
        assertThat(persistedCredential.passwordHash()).startsWith("$2");
        assertThat(passwordEncoder.matches(password, persistedCredential.passwordHash())).isTrue();
        assertThat(userRepository.findByUsername(user.username()).orElseThrow().lastAuthenticatedAt()).isNotNull();
    }
}
