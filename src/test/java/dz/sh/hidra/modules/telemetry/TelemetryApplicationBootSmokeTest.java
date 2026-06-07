/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : TelemetryApplicationBootSmokeTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Test
 * @Layer       : Bootstrap Test
 * @Module      : telemetry
 * @Package     : dz.sh.hidra.modules.telemetry
 *
 * @Description : Spring Boot smoke test that verifies telemetry module wiring loads inside HidraApplication.
 *
 */
package dz.sh.hidra.modules.telemetry;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import javax.sql.DataSource;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import dz.sh.hidra.HidraApplication;
import dz.sh.hidra.modules.telemetry.api.rest.controller.TelemetryCatalogController;
import dz.sh.hidra.modules.telemetry.api.rest.controller.TelemetryDeviceController;
import dz.sh.hidra.modules.telemetry.api.rest.controller.TelemetryIngestionBatchController;
import dz.sh.hidra.modules.telemetry.api.rest.controller.TelemetryPointBindingController;
import dz.sh.hidra.modules.telemetry.api.rest.controller.TelemetryPointController;
import dz.sh.hidra.modules.telemetry.api.rest.controller.TelemetryReadingController;
import dz.sh.hidra.modules.telemetry.api.rest.controller.TelemetrySourceController;
import dz.sh.hidra.modules.telemetry.api.rest.mapper.TelemetryRestMapper;
import dz.sh.hidra.modules.telemetry.application.port.in.AcceptTelemetryReadingUseCase;
import dz.sh.hidra.modules.telemetry.application.port.in.ActivateTelemetryDeviceUseCase;
import dz.sh.hidra.modules.telemetry.application.port.in.ActivateTelemetryPointUseCase;
import dz.sh.hidra.modules.telemetry.application.port.in.ActivateTelemetrySourceUseCase;
import dz.sh.hidra.modules.telemetry.application.port.in.BindTelemetryPointUseCase;
import dz.sh.hidra.modules.telemetry.application.port.in.CloseTelemetryPointBindingUseCase;
import dz.sh.hidra.modules.telemetry.application.port.in.CompleteTelemetryIngestionBatchUseCase;
import dz.sh.hidra.modules.telemetry.application.port.in.DeactivateTelemetryDeviceUseCase;
import dz.sh.hidra.modules.telemetry.application.port.in.DeactivateTelemetrySourceUseCase;
import dz.sh.hidra.modules.telemetry.application.port.in.FailTelemetryIngestionBatchUseCase;
import dz.sh.hidra.modules.telemetry.application.port.in.GetLatestTelemetryReadingUseCase;
import dz.sh.hidra.modules.telemetry.application.port.in.GetTelemetryCatalogTypeUseCase;
import dz.sh.hidra.modules.telemetry.application.port.in.GetTelemetryDeviceUseCase;
import dz.sh.hidra.modules.telemetry.application.port.in.GetTelemetryIngestionBatchUseCase;
import dz.sh.hidra.modules.telemetry.application.port.in.GetTelemetryPointBindingUseCase;
import dz.sh.hidra.modules.telemetry.application.port.in.GetTelemetryPointUseCase;
import dz.sh.hidra.modules.telemetry.application.port.in.GetTelemetryReadingUseCase;
import dz.sh.hidra.modules.telemetry.application.port.in.GetTelemetrySourceUseCase;
import dz.sh.hidra.modules.telemetry.application.port.in.ListTelemetryCatalogTypesUseCase;
import dz.sh.hidra.modules.telemetry.application.port.in.ListTelemetryDevicesUseCase;
import dz.sh.hidra.modules.telemetry.application.port.in.ListTelemetryIngestionBatchesUseCase;
import dz.sh.hidra.modules.telemetry.application.port.in.ListTelemetryPointBindingsUseCase;
import dz.sh.hidra.modules.telemetry.application.port.in.ListTelemetryPointsUseCase;
import dz.sh.hidra.modules.telemetry.application.port.in.ListTelemetryReadingsUseCase;
import dz.sh.hidra.modules.telemetry.application.port.in.ListTelemetrySourcesUseCase;
import dz.sh.hidra.modules.telemetry.application.port.in.MarkTelemetryIngestionBatchProcessingUseCase;
import dz.sh.hidra.modules.telemetry.application.port.in.MarkTelemetryReadingDuplicateUseCase;
import dz.sh.hidra.modules.telemetry.application.port.in.QuarantineTelemetryReadingUseCase;
import dz.sh.hidra.modules.telemetry.application.port.in.ReceiveTelemetryReadingUseCase;
import dz.sh.hidra.modules.telemetry.application.port.in.RegisterTelemetryDeviceUseCase;
import dz.sh.hidra.modules.telemetry.application.port.in.RegisterTelemetryPointUseCase;
import dz.sh.hidra.modules.telemetry.application.port.in.RegisterTelemetrySourceUseCase;
import dz.sh.hidra.modules.telemetry.application.port.in.RejectTelemetryReadingUseCase;
import dz.sh.hidra.modules.telemetry.application.port.in.ResolveTelemetryCatalogTypeUseCase;
import dz.sh.hidra.modules.telemetry.application.port.in.RetireTelemetryDeviceUseCase;
import dz.sh.hidra.modules.telemetry.application.port.in.RetireTelemetryPointUseCase;
import dz.sh.hidra.modules.telemetry.application.port.in.RetireTelemetrySourceUseCase;
import dz.sh.hidra.modules.telemetry.application.port.in.StartTelemetryIngestionBatchUseCase;
import dz.sh.hidra.modules.telemetry.application.port.in.SuspendTelemetryPointUseCase;
import dz.sh.hidra.modules.telemetry.application.port.out.TelemetryCatalogRepositoryPort;
import dz.sh.hidra.modules.telemetry.application.port.out.TelemetryDeviceRepositoryPort;
import dz.sh.hidra.modules.telemetry.application.port.out.TelemetryIngestionBatchRepositoryPort;
import dz.sh.hidra.modules.telemetry.application.port.out.TelemetryPointBindingRepositoryPort;
import dz.sh.hidra.modules.telemetry.application.port.out.TelemetryPointRepositoryPort;
import dz.sh.hidra.modules.telemetry.application.port.out.TelemetryReadingRepositoryPort;
import dz.sh.hidra.modules.telemetry.application.port.out.TelemetrySourceRepositoryPort;
import dz.sh.hidra.modules.telemetry.application.port.out.TelemetryTopologyAssetLookupPort;
import dz.sh.hidra.modules.telemetry.application.service.TelemetryBindingApplicationService;
import dz.sh.hidra.modules.telemetry.application.service.TelemetryCatalogApplicationService;
import dz.sh.hidra.modules.telemetry.application.service.TelemetryDeviceApplicationService;
import dz.sh.hidra.modules.telemetry.application.service.TelemetryIngestionApplicationService;
import dz.sh.hidra.modules.telemetry.application.service.TelemetryPointApplicationService;
import dz.sh.hidra.modules.telemetry.application.service.TelemetryReadingApplicationService;
import dz.sh.hidra.modules.telemetry.application.service.TelemetrySourceApplicationService;
import dz.sh.hidra.modules.telemetry.domain.policy.TelemetryBindingPolicy;
import dz.sh.hidra.modules.telemetry.domain.policy.TelemetryCatalogPolicy;
import dz.sh.hidra.modules.telemetry.domain.policy.TelemetryIngestionPolicy;
import dz.sh.hidra.modules.telemetry.domain.policy.TelemetryPointPolicy;
import dz.sh.hidra.modules.telemetry.domain.policy.TelemetryReadingPolicy;
import dz.sh.hidra.modules.telemetry.domain.policy.TelemetrySourcePolicy;
import dz.sh.hidra.modules.telemetry.domain.service.TelemetryBindingDomainService;
import dz.sh.hidra.modules.telemetry.domain.service.TelemetryCatalogDomainService;
import dz.sh.hidra.modules.telemetry.domain.service.TelemetryIngestionDomainService;
import dz.sh.hidra.modules.telemetry.domain.service.TelemetryReadingDomainService;
import dz.sh.hidra.modules.telemetry.domain.service.TelemetryRegistrationDomainService;
import dz.sh.hidra.modules.telemetry.infrastructure.configuration.TelemetryConfiguration;
import dz.sh.hidra.modules.telemetry.infrastructure.persistence.mapper.TelemetryPersistenceMapper;

/**
 * Spring Boot smoke test for telemetry module wiring inside HidraApplication.
 *
 * <p>Business role:
 * Verifies that the telemetry module can participate in the real HidraAPI application context after
 * telemetry domain, application, persistence, Flyway migration, REST mapper, REST controllers, and
 * topology lookup adapter have been added.
 *
 * <p>Architecture role:
 * This is a telemetry bootstrap smoke test. It intentionally does not disable JPA scanning,
 * repositories, Flyway, security, identity, organization, platform infrastructure, topology, or
 * telemetry REST wiring just to make the test pass.
 *
 * <p>Validation:
 * The test uses PostgreSQL Testcontainers, matching the topology boot-smoke baseline. A
 * Docker-compatible container runtime is required. Telemetry Flyway migrations must be present so
 * Hibernate/JPA can validate the telemetry schema during context startup.
 *
 * <p>Usage:
 * Run with <code>mvn -q test -Dtest=TelemetryApplicationBootSmokeTest</code> from the repository root.
 */
@Testcontainers
@SpringBootTest(classes = HidraApplication.class)
@ActiveProfiles("test")
class TelemetryApplicationBootSmokeTest {

    @Container
    private static final PostgreSQLContainer<?> POSTGRESQL = new PostgreSQLContainer<>("postgres:16-alpine")
            .withDatabaseName("hidra_telemetry_test")
            .withUsername("hidra")
            .withPassword("hidra");

    @Autowired
    private ApplicationContext applicationContext;

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
    void contextLoadsWithTelemetryModuleAndPostgresqlTestContainer() {
        assertNotNull(applicationContext);
        assertNotNull(dataSource);
        assertNotNull(applicationContext.getBean(HidraApplication.class));
        assertBean("telemetryConfiguration", TelemetryConfiguration.class);
        assertBean("telemetryRestMapper", TelemetryRestMapper.class);
        assertBean("telemetryPersistenceMapper", TelemetryPersistenceMapper.class);
    }

    @Test
    void telemetryRestControllersAreRegistered() {
        assertTypeBean(TelemetryCatalogController.class);
        assertTypeBean(TelemetrySourceController.class);
        assertTypeBean(TelemetryDeviceController.class);
        assertTypeBean(TelemetryPointController.class);
        assertTypeBean(TelemetryPointBindingController.class);
        assertTypeBean(TelemetryReadingController.class);
        assertTypeBean(TelemetryIngestionBatchController.class);
    }

    @Test
    void telemetryApplicationUseCasesAndServicesAreRegistered() {
        assertBean("telemetryCatalogApplicationService", TelemetryCatalogApplicationService.class);
        assertTypeBean(GetTelemetryCatalogTypeUseCase.class);
        assertTypeBean(ListTelemetryCatalogTypesUseCase.class);
        assertTypeBean(ResolveTelemetryCatalogTypeUseCase.class);

        assertBean("telemetrySourceApplicationService", TelemetrySourceApplicationService.class);
        assertTypeBean(RegisterTelemetrySourceUseCase.class);
        assertTypeBean(ActivateTelemetrySourceUseCase.class);
        assertTypeBean(DeactivateTelemetrySourceUseCase.class);
        assertTypeBean(RetireTelemetrySourceUseCase.class);
        assertTypeBean(GetTelemetrySourceUseCase.class);
        assertTypeBean(ListTelemetrySourcesUseCase.class);

        assertBean("telemetryDeviceApplicationService", TelemetryDeviceApplicationService.class);
        assertTypeBean(RegisterTelemetryDeviceUseCase.class);
        assertTypeBean(ActivateTelemetryDeviceUseCase.class);
        assertTypeBean(DeactivateTelemetryDeviceUseCase.class);
        assertTypeBean(RetireTelemetryDeviceUseCase.class);
        assertTypeBean(GetTelemetryDeviceUseCase.class);
        assertTypeBean(ListTelemetryDevicesUseCase.class);

        assertBean("telemetryPointApplicationService", TelemetryPointApplicationService.class);
        assertTypeBean(RegisterTelemetryPointUseCase.class);
        assertTypeBean(ActivateTelemetryPointUseCase.class);
        assertTypeBean(SuspendTelemetryPointUseCase.class);
        assertTypeBean(RetireTelemetryPointUseCase.class);
        assertTypeBean(GetTelemetryPointUseCase.class);
        assertTypeBean(ListTelemetryPointsUseCase.class);

        assertBean("telemetryBindingApplicationService", TelemetryBindingApplicationService.class);
        assertTypeBean(BindTelemetryPointUseCase.class);
        assertTypeBean(CloseTelemetryPointBindingUseCase.class);
        assertTypeBean(GetTelemetryPointBindingUseCase.class);
        assertTypeBean(ListTelemetryPointBindingsUseCase.class);

        assertBean("telemetryReadingApplicationService", TelemetryReadingApplicationService.class);
        assertTypeBean(ReceiveTelemetryReadingUseCase.class);
        assertTypeBean(AcceptTelemetryReadingUseCase.class);
        assertTypeBean(RejectTelemetryReadingUseCase.class);
        assertTypeBean(QuarantineTelemetryReadingUseCase.class);
        assertTypeBean(MarkTelemetryReadingDuplicateUseCase.class);
        assertTypeBean(GetTelemetryReadingUseCase.class);
        assertTypeBean(GetLatestTelemetryReadingUseCase.class);
        assertTypeBean(ListTelemetryReadingsUseCase.class);

        assertBean("telemetryIngestionApplicationService", TelemetryIngestionApplicationService.class);
        assertTypeBean(StartTelemetryIngestionBatchUseCase.class);
        assertTypeBean(MarkTelemetryIngestionBatchProcessingUseCase.class);
        assertTypeBean(CompleteTelemetryIngestionBatchUseCase.class);
        assertTypeBean(FailTelemetryIngestionBatchUseCase.class);
        assertTypeBean(GetTelemetryIngestionBatchUseCase.class);
        assertTypeBean(ListTelemetryIngestionBatchesUseCase.class);
    }

    @Test
    void telemetryDomainPoliciesServicesAndPersistencePortsAreRegistered() {
        assertBean("telemetryCatalogPolicy", TelemetryCatalogPolicy.class);
        assertBean("telemetrySourcePolicy", TelemetrySourcePolicy.class);
        assertBean("telemetryPointPolicy", TelemetryPointPolicy.class);
        assertBean("telemetryBindingPolicy", TelemetryBindingPolicy.class);
        assertBean("telemetryReadingPolicy", TelemetryReadingPolicy.class);
        assertBean("telemetryIngestionPolicy", TelemetryIngestionPolicy.class);

        assertBean("telemetryCatalogDomainService", TelemetryCatalogDomainService.class);
        assertBean("telemetryRegistrationDomainService", TelemetryRegistrationDomainService.class);
        assertBean("telemetryBindingDomainService", TelemetryBindingDomainService.class);
        assertBean("telemetryReadingDomainService", TelemetryReadingDomainService.class);
        assertBean("telemetryIngestionDomainService", TelemetryIngestionDomainService.class);

        assertTypeBean(TelemetryCatalogRepositoryPort.class);
        assertTypeBean(TelemetrySourceRepositoryPort.class);
        assertTypeBean(TelemetryDeviceRepositoryPort.class);
        assertTypeBean(TelemetryPointRepositoryPort.class);
        assertTypeBean(TelemetryPointBindingRepositoryPort.class);
        assertTypeBean(TelemetryReadingRepositoryPort.class);
        assertTypeBean(TelemetryIngestionBatchRepositoryPort.class);
        assertTypeBean(TelemetryTopologyAssetLookupPort.class);
    }

    private <T> void assertTypeBean(Class<T> beanType) {
        assertNotNull(applicationContext.getBean(beanType));
    }

    private <T> void assertBean(String beanName, Class<T> beanType) {
        assertTrue(applicationContext.containsBean(beanName), "Missing Spring bean: " + beanName);
        assertNotNull(applicationContext.getBean(beanName, beanType));
    }
}
