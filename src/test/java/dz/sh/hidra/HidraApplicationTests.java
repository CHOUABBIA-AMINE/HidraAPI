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
 * @Layer       : Bootstrap Test
 * @Module      : bootstrap
 * @Package     : dz.sh.hidra
 *
 * @Description : Bootstrap smoke test that verifies the Spring application context starts.
 *
 */
package dz.sh.hidra;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import javax.sql.DataSource;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

/**
 * Verifies that the current HidraAPI foundation can load a real Spring Boot context.
 *
 * <p>Business role:
 * Protects the bootstrap baseline before new modules such as topology, measurement, operations,
 * flow, risk, analytics, workflow, reporting, or notification are started.
 *
 * <p>Architecture role:
 * This is a bootstrap smoke test. It does not disable JPA scanning, repositories, Flyway,
 * security, identity, organization, or platform infrastructure just to make the test pass.
 *
 * <p>Validation:
 * The test uses PostgreSQL Testcontainers because the project already declares Testcontainers
 * dependencies. A Docker-compatible container runtime is required to execute this test.
 *
 * <p>Usage:
 * Run with <code>mvn -q test -Dtest=HidraApplicationTests</code> from the repository root.
 */
@Testcontainers
@SpringBootTest(classes = HidraApplication.class)
@ActiveProfiles("test")
class HidraApplicationTests {

    @Container
    private static final PostgreSQLContainer<?> POSTGRESQL = new PostgreSQLContainer<>("postgres:16-alpine")
            .withDatabaseName("hidra_test")
            .withUsername("hidra")
            .withPassword("hidra");

    @Autowired
    private org.springframework.context.ApplicationContext applicationContext;

    @Autowired
    private DataSource dataSource;

    @DynamicPropertySource
    static void registerPostgresqlProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", POSTGRESQL::getJdbcUrl);
        registry.add("spring.datasource.username", POSTGRESQL::getUsername);
        registry.add("spring.datasource.password", POSTGRESQL::getPassword);
        registry.add("spring.datasource.driver-class-name", POSTGRESQL::getDriverClassName);
    }

    @Test
    void contextLoadsWithPostgresqlTestContainer() {
        assertNotNull(applicationContext);
        assertNotNull(dataSource);
    }
}
