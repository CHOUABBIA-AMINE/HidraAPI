/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : TopologyApplicationBootSmokeTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Test
 * @Layer       : Bootstrap Test
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology
 *
 * @Description : Spring Boot smoke test that verifies topology module wiring loads inside HidraApplication.
 *
 */
package dz.sh.hidra.modules.topology;

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
import dz.sh.hidra.modules.topology.api.rest.configuration.TopologyApiRestConfiguration;
import dz.sh.hidra.modules.topology.api.rest.controller.EquipmentController;
import dz.sh.hidra.modules.topology.api.rest.controller.FacilityController;
import dz.sh.hidra.modules.topology.api.rest.controller.PipelineAppurtenanceController;
import dz.sh.hidra.modules.topology.api.rest.controller.PipelineController;
import dz.sh.hidra.modules.topology.api.rest.controller.PipelineSegmentController;
import dz.sh.hidra.modules.topology.api.rest.controller.PipelineSystemController;
import dz.sh.hidra.modules.topology.api.rest.controller.TopologyConnectionController;
import dz.sh.hidra.modules.topology.api.rest.controller.TopologyNodeController;
import dz.sh.hidra.modules.topology.api.rest.mapper.TopologyRestMapper;
import dz.sh.hidra.modules.topology.application.port.in.CreateFacilityUseCase;
import dz.sh.hidra.modules.topology.application.port.in.CreatePipelineAppurtenanceUseCase;
import dz.sh.hidra.modules.topology.application.port.in.CreatePipelineSegmentUseCase;
import dz.sh.hidra.modules.topology.application.port.in.CreatePipelineSystemUseCase;
import dz.sh.hidra.modules.topology.application.port.in.CreatePipelineUseCase;
import dz.sh.hidra.modules.topology.application.port.in.CreateTopologyConnectionUseCase;
import dz.sh.hidra.modules.topology.application.port.in.CreateTopologyNodeUseCase;
import dz.sh.hidra.modules.topology.application.port.in.GetFacilityUseCase;
import dz.sh.hidra.modules.topology.application.port.in.GetPipelineAppurtenanceUseCase;
import dz.sh.hidra.modules.topology.application.port.in.GetPipelineSystemUseCase;
import dz.sh.hidra.modules.topology.application.port.in.GetPipelineUseCase;
import dz.sh.hidra.modules.topology.application.port.in.GetTopologyNodeUseCase;
import dz.sh.hidra.modules.topology.application.port.in.ListFacilitiesUseCase;
import dz.sh.hidra.modules.topology.application.port.in.ListPipelineAppurtenancesUseCase;
import dz.sh.hidra.modules.topology.application.port.in.ListPipelineSegmentsUseCase;
import dz.sh.hidra.modules.topology.application.port.in.ListPipelineSystemsUseCase;
import dz.sh.hidra.modules.topology.application.port.in.ListPipelinesUseCase;
import dz.sh.hidra.modules.topology.application.port.in.ListTopologyConnectionsUseCase;
import dz.sh.hidra.modules.topology.application.port.in.ListTopologyNodesUseCase;
import dz.sh.hidra.modules.topology.application.port.in.RegisterEquipmentUseCase;
import dz.sh.hidra.modules.topology.application.port.out.EquipmentRepositoryPort;
import dz.sh.hidra.modules.topology.application.port.out.FacilityRepositoryPort;
import dz.sh.hidra.modules.topology.application.port.out.PipelineAppurtenanceRepositoryPort;
import dz.sh.hidra.modules.topology.application.port.out.PipelineRepositoryPort;
import dz.sh.hidra.modules.topology.application.port.out.PipelineSegmentRepositoryPort;
import dz.sh.hidra.modules.topology.application.port.out.PipelineSystemRepositoryPort;
import dz.sh.hidra.modules.topology.application.port.out.TopologyConnectionRepositoryPort;
import dz.sh.hidra.modules.topology.application.port.out.TopologyNodeRepositoryPort;
import dz.sh.hidra.modules.topology.application.service.EquipmentApplicationService;
import dz.sh.hidra.modules.topology.application.service.FacilityApplicationService;
import dz.sh.hidra.modules.topology.application.service.PipelineAppurtenanceApplicationService;
import dz.sh.hidra.modules.topology.application.service.PipelineApplicationService;
import dz.sh.hidra.modules.topology.application.service.PipelineSegmentApplicationService;
import dz.sh.hidra.modules.topology.application.service.PipelineSystemApplicationService;
import dz.sh.hidra.modules.topology.application.service.TopologyConnectionApplicationService;
import dz.sh.hidra.modules.topology.application.service.TopologyNodeApplicationService;
import dz.sh.hidra.modules.topology.domain.policy.FacilityTopologyPolicy;
import dz.sh.hidra.modules.topology.domain.policy.PipelineAppurtenancePolicy;
import dz.sh.hidra.modules.topology.domain.policy.TopologyAssetStatusPolicy;
import dz.sh.hidra.modules.topology.domain.policy.TopologyConnectivityPolicy;
import dz.sh.hidra.modules.topology.domain.service.PipelineAppurtenanceDomainService;
import dz.sh.hidra.modules.topology.domain.service.TopologyConnectivityDomainService;
import dz.sh.hidra.modules.topology.domain.service.TopologyRegistrationDomainService;
import dz.sh.hidra.modules.topology.infrastructure.configuration.TopologyConfiguration;
import dz.sh.hidra.modules.topology.infrastructure.persistence.mapper.TopologyPersistenceMapper;

/**
 * Spring Boot smoke test for topology module wiring inside HidraApplication.
 *
 * <p>Business role:
 * Verifies that the topology module can participate in the real HidraAPI application context after
 * topology domain, application, persistence, Flyway migration, REST mapper, and REST controllers have
 * been added.
 *
 * <p>Architecture role:
 * This is a topology bootstrap smoke test. It intentionally does not disable JPA scanning,
 * repositories, Flyway, security, identity, organization, platform infrastructure, or topology REST
 * wiring just to make the test pass.
 *
 * <p>Validation:
 * The test uses PostgreSQL Testcontainers, matching the existing HidraApplicationTests baseline. A
 * Docker-compatible container runtime is required. The topology Flyway migration must be present so
 * Hibernate/JPA can validate the topology schema during context startup.
 *
 * <p>Usage:
 * Run with <code>mvn -q test -Dtest=TopologyApplicationBootSmokeTest</code> from the repository root.
 */
@Testcontainers
@SpringBootTest(classes = HidraApplication.class)
@ActiveProfiles("test")
class TopologyApplicationBootSmokeTest {

    @Container
    private static final PostgreSQLContainer<?> POSTGRESQL = new PostgreSQLContainer<>("postgres:16-alpine")
            .withDatabaseName("hidra_topology_test")
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
    void contextLoadsWithTopologyModuleAndPostgresqlTestContainer() {
        assertNotNull(applicationContext);
        assertNotNull(dataSource);
        assertNotNull(applicationContext.getBean(HidraApplication.class));
        assertBean("topologyConfiguration", TopologyConfiguration.class);
        assertBean("topologyApiRestConfiguration", TopologyApiRestConfiguration.class);
        assertBean("topologyRestMapper", TopologyRestMapper.class);
        assertBean("topologyPersistenceMapper", TopologyPersistenceMapper.class);
    }

    @Test
    void topologyRestControllersAreRegistered() {
        assertTypeBean(PipelineSystemController.class);
        assertTypeBean(PipelineController.class);
        assertTypeBean(FacilityController.class);
        assertTypeBean(TopologyNodeController.class);
        assertTypeBean(PipelineSegmentController.class);
        assertTypeBean(PipelineAppurtenanceController.class);
        assertTypeBean(TopologyConnectionController.class);
        assertTypeBean(EquipmentController.class);
    }

    @Test
    void topologyApplicationUseCasesAndServicesAreRegistered() {
        assertBean("pipelineSystemApplicationService", PipelineSystemApplicationService.class);
        assertBean("createPipelineSystemUseCase", CreatePipelineSystemUseCase.class);
        assertBean("getPipelineSystemUseCase", GetPipelineSystemUseCase.class);
        assertBean("listPipelineSystemsUseCase", ListPipelineSystemsUseCase.class);

        assertBean("pipelineApplicationService", PipelineApplicationService.class);
        assertBean("createPipelineUseCase", CreatePipelineUseCase.class);
        assertBean("getPipelineUseCase", GetPipelineUseCase.class);
        assertBean("listPipelinesUseCase", ListPipelinesUseCase.class);

        assertBean("facilityApplicationService", FacilityApplicationService.class);
        assertBean("createFacilityUseCase", CreateFacilityUseCase.class);
        assertBean("getFacilityUseCase", GetFacilityUseCase.class);
        assertBean("listFacilitiesUseCase", ListFacilitiesUseCase.class);

        assertBean("topologyNodeApplicationService", TopologyNodeApplicationService.class);
        assertBean("createTopologyNodeUseCase", CreateTopologyNodeUseCase.class);
        assertBean("getTopologyNodeUseCase", GetTopologyNodeUseCase.class);
        assertBean("listTopologyNodesUseCase", ListTopologyNodesUseCase.class);

        assertBean("pipelineSegmentApplicationService", PipelineSegmentApplicationService.class);
        assertBean("createPipelineSegmentUseCase", CreatePipelineSegmentUseCase.class);
        assertBean("listPipelineSegmentsUseCase", ListPipelineSegmentsUseCase.class);

        assertBean("pipelineAppurtenanceApplicationService", PipelineAppurtenanceApplicationService.class);
        assertBean("createPipelineAppurtenanceUseCase", CreatePipelineAppurtenanceUseCase.class);
        assertBean("getPipelineAppurtenanceUseCase", GetPipelineAppurtenanceUseCase.class);
        assertBean("listPipelineAppurtenancesUseCase", ListPipelineAppurtenancesUseCase.class);

        assertBean("topologyConnectionApplicationService", TopologyConnectionApplicationService.class);
        assertBean("createTopologyConnectionUseCase", CreateTopologyConnectionUseCase.class);
        assertBean("listTopologyConnectionsUseCase", ListTopologyConnectionsUseCase.class);

        assertBean("equipmentApplicationService", EquipmentApplicationService.class);
        assertBean("registerEquipmentUseCase", RegisterEquipmentUseCase.class);
    }

    @Test
    void topologyDomainPoliciesServicesAndPersistencePortsAreRegistered() {
        assertBean("topologyAssetStatusPolicy", TopologyAssetStatusPolicy.class);
        assertBean("topologyConnectivityPolicy", TopologyConnectivityPolicy.class);
        assertBean("facilityTopologyPolicy", FacilityTopologyPolicy.class);
        assertBean("pipelineAppurtenancePolicy", PipelineAppurtenancePolicy.class);
        assertBean("topologyRegistrationDomainService", TopologyRegistrationDomainService.class);
        assertBean("topologyConnectivityDomainService", TopologyConnectivityDomainService.class);
        assertBean("pipelineAppurtenanceDomainService", PipelineAppurtenanceDomainService.class);

        assertBean("pipelineSystemRepositoryPort", PipelineSystemRepositoryPort.class);
        assertBean("pipelineRepositoryPort", PipelineRepositoryPort.class);
        assertBean("facilityRepositoryPort", FacilityRepositoryPort.class);
        assertBean("topologyNodeRepositoryPort", TopologyNodeRepositoryPort.class);
        assertBean("pipelineSegmentRepositoryPort", PipelineSegmentRepositoryPort.class);
        assertBean("pipelineAppurtenanceRepositoryPort", PipelineAppurtenanceRepositoryPort.class);
        assertBean("topologyConnectionRepositoryPort", TopologyConnectionRepositoryPort.class);
        assertBean("equipmentRepositoryPort", EquipmentRepositoryPort.class);
    }

    private <T> void assertTypeBean(Class<T> beanType) {
        assertNotNull(applicationContext.getBean(beanType));
    }

    private <T> void assertBean(String beanName, Class<T> beanType) {
        assertTrue(applicationContext.containsBean(beanName), "Missing Spring bean: " + beanName);
        assertNotNull(applicationContext.getBean(beanName, beanType));
    }
}
