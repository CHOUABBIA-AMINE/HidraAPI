/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : HidraApplicationTests
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Class
 * @Layer       : Test
 * @Module      : bootstrap
 * @Package     : dz.sh.hidra
 *
 * @Description : Verifies the complete Hidra Spring application context against PostgreSQL.
 *
 */
package dz.sh.hidra;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

@Testcontainers(disabledWithoutDocker = true)
@SpringBootTest(
        classes = HidraApplication.class,
        webEnvironment = SpringBootTest.WebEnvironment.MOCK
)
@ActiveProfiles("test")
class HidraApplicationTests {

    @Container
    static final PostgreSQLContainer<?> POSTGRESQL =
            new PostgreSQLContainer<>(
                    DockerImageName.parse("postgres:16-alpine")
            )
                    .withDatabaseName("hidra_test")
                    .withUsername("hidra")
                    .withPassword("hidra");

    @DynamicPropertySource
    static void databaseProperties(DynamicPropertyRegistry registry) {
        registry.add(
                "spring.datasource.url",
                POSTGRESQL::getJdbcUrl
        );
        registry.add(
                "spring.datasource.username",
                POSTGRESQL::getUsername
        );
        registry.add(
                "spring.datasource.password",
                POSTGRESQL::getPassword
        );

        registry.add(
                "spring.jpa.hibernate.ddl-auto",
                () -> "validate"
        );

        registry.add(
                "spring.flyway.enabled",
                () -> "true"
        );

        registry.add(
                "spring.flyway.validate-on-migrate",
                () -> "true"
        );
    }

    @Test
    void contextLoads() {
        // Successful complete application context creation is the assertion.
    }
}