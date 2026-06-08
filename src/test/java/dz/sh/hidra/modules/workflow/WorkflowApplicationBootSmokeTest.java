/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : WorkflowApplicationBootSmokeTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Test
 * @Layer       : Bootstrap Test
 * @Module      : workflow
 * @Package     : dz.sh.hidra.modules.workflow
 *
 * @Description : Spring Boot smoke test that verifies workflow module wiring loads inside HidraApplication.
 *
 */
package dz.sh.hidra.modules.workflow;

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
import dz.sh.hidra.modules.workflow.api.rest.controller.WorkflowCatalogController;
import dz.sh.hidra.modules.workflow.api.rest.controller.WorkflowDefinitionController;
import dz.sh.hidra.modules.workflow.api.rest.controller.WorkflowInstanceController;
import dz.sh.hidra.modules.workflow.api.rest.controller.WorkflowTaskController;
import dz.sh.hidra.modules.workflow.api.rest.mapper.WorkflowRestMapper;
import dz.sh.hidra.modules.workflow.application.port.in.ActivateWorkflowDefinitionUseCase;
import dz.sh.hidra.modules.workflow.application.port.in.ApproveWorkflowTaskUseCase;
import dz.sh.hidra.modules.workflow.application.port.in.AssignWorkflowTaskUseCase;
import dz.sh.hidra.modules.workflow.application.port.in.CancelWorkflowInstanceUseCase;
import dz.sh.hidra.modules.workflow.application.port.in.ClaimWorkflowTaskUseCase;
import dz.sh.hidra.modules.workflow.application.port.in.CommentWorkflowTaskUseCase;
import dz.sh.hidra.modules.workflow.application.port.in.CreateWorkflowDefinitionUseCase;
import dz.sh.hidra.modules.workflow.application.port.in.CreateWorkflowStepUseCase;
import dz.sh.hidra.modules.workflow.application.port.in.CreateWorkflowTransitionUseCase;
import dz.sh.hidra.modules.workflow.application.port.in.DeactivateWorkflowDefinitionUseCase;
import dz.sh.hidra.modules.workflow.application.port.in.DelegateWorkflowTaskUseCase;
import dz.sh.hidra.modules.workflow.application.port.in.EscalateWorkflowTaskUseCase;
import dz.sh.hidra.modules.workflow.application.port.in.GetWorkflowCatalogTypeUseCase;
import dz.sh.hidra.modules.workflow.application.port.in.GetWorkflowDefinitionUseCase;
import dz.sh.hidra.modules.workflow.application.port.in.GetWorkflowInstanceUseCase;
import dz.sh.hidra.modules.workflow.application.port.in.GetWorkflowTaskUseCase;
import dz.sh.hidra.modules.workflow.application.port.in.GetWorkflowTimelineUseCase;
import dz.sh.hidra.modules.workflow.application.port.in.ListMyWorkflowTasksUseCase;
import dz.sh.hidra.modules.workflow.application.port.in.ListWorkflowCatalogTypesUseCase;
import dz.sh.hidra.modules.workflow.application.port.in.ListWorkflowDefinitionsUseCase;
import dz.sh.hidra.modules.workflow.application.port.in.ListWorkflowInstancesUseCase;
import dz.sh.hidra.modules.workflow.application.port.in.ListWorkflowTasksUseCase;
import dz.sh.hidra.modules.workflow.application.port.in.RejectWorkflowTaskUseCase;
import dz.sh.hidra.modules.workflow.application.port.in.RequestWorkflowCorrectionUseCase;
import dz.sh.hidra.modules.workflow.application.port.in.ResolveWorkflowCatalogTypeUseCase;
import dz.sh.hidra.modules.workflow.application.port.in.StartWorkflowInstanceUseCase;
import dz.sh.hidra.modules.workflow.application.port.out.WorkflowAuditEventPort;
import dz.sh.hidra.modules.workflow.application.port.out.WorkflowCatalogRepositoryPort;
import dz.sh.hidra.modules.workflow.application.port.out.WorkflowDefinitionRepositoryPort;
import dz.sh.hidra.modules.workflow.application.port.out.WorkflowInstanceRepositoryPort;
import dz.sh.hidra.modules.workflow.application.port.out.WorkflowTargetLookupPort;
import dz.sh.hidra.modules.workflow.application.port.out.WorkflowTaskRepositoryPort;
import dz.sh.hidra.modules.workflow.application.service.WorkflowCatalogApplicationService;
import dz.sh.hidra.modules.workflow.application.service.WorkflowDefinitionApplicationService;
import dz.sh.hidra.modules.workflow.application.service.WorkflowInstanceApplicationService;
import dz.sh.hidra.modules.workflow.application.service.WorkflowTaskApplicationService;
import dz.sh.hidra.modules.workflow.domain.policy.WorkflowAssignmentPolicy;
import dz.sh.hidra.modules.workflow.domain.policy.WorkflowDecisionPolicy;
import dz.sh.hidra.modules.workflow.domain.policy.WorkflowDefinitionPolicy;
import dz.sh.hidra.modules.workflow.domain.policy.WorkflowDelegationPolicy;
import dz.sh.hidra.modules.workflow.domain.policy.WorkflowEscalationPolicy;
import dz.sh.hidra.modules.workflow.domain.policy.WorkflowTargetPolicy;
import dz.sh.hidra.modules.workflow.domain.policy.WorkflowTransitionPolicy;
import dz.sh.hidra.modules.workflow.domain.service.WorkflowAssignmentDomainService;
import dz.sh.hidra.modules.workflow.domain.service.WorkflowDecisionDomainService;
import dz.sh.hidra.modules.workflow.domain.service.WorkflowDefinitionDomainService;
import dz.sh.hidra.modules.workflow.domain.service.WorkflowEscalationDomainService;
import dz.sh.hidra.modules.workflow.domain.service.WorkflowInstanceDomainService;
import dz.sh.hidra.modules.workflow.domain.service.WorkflowTaskDomainService;
import dz.sh.hidra.modules.workflow.infrastructure.configuration.WorkflowConfiguration;
import dz.sh.hidra.modules.workflow.infrastructure.configuration.WorkflowMapperConfiguration;
import dz.sh.hidra.modules.workflow.infrastructure.persistence.mapper.WorkflowPersistenceMapper;

/**
 * Spring Boot smoke test for workflow module wiring inside HidraApplication.
 *
 * <p>Business role:
 * Verifies that the workflow module can participate in the real HidraAPI application context after
 * workflow domain, application, persistence, REST mapper, REST controllers, and telemetry target
 * lookup adapter have been added.
 *
 * <p>Architecture role:
 * This is a workflow bootstrap smoke test. It intentionally does not disable JPA scanning,
 * repositories, Flyway, security, identity, organization, platform infrastructure, topology,
 * telemetry, workflow REST wiring, or workflow persistence wiring just to make the test pass.
 *
 * <p>Validation:
 * The test uses PostgreSQL Testcontainers, matching the telemetry and topology boot-smoke baseline.
 * A Docker-compatible container runtime is required. Workflow Flyway migrations must be present so
 * Hibernate/JPA can validate the workflow schema during context startup.
 *
 * <p>Usage:
 * Run with <code>mvn -q test -Dtest=WorkflowApplicationBootSmokeTest</code> from the repository root.
 */
@Testcontainers
@SpringBootTest(classes = HidraApplication.class)
@ActiveProfiles("test")
class WorkflowApplicationBootSmokeTest {

    @Container
    private static final PostgreSQLContainer<?> POSTGRESQL = new PostgreSQLContainer<>("postgres:16-alpine")
            .withDatabaseName("hidra_workflow_test")
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
    void contextLoadsWithWorkflowModuleAndPostgresqlTestContainer() {
        assertNotNull(applicationContext);
        assertNotNull(dataSource);
        assertNotNull(applicationContext.getBean(HidraApplication.class));
        assertBean("workflowConfiguration", WorkflowConfiguration.class);
        assertBean("workflowMapperConfiguration", WorkflowMapperConfiguration.class);
        assertBean("workflowRestMapper", WorkflowRestMapper.class);
        assertBean("workflowPersistenceMapper", WorkflowPersistenceMapper.class);
    }

    @Test
    void workflowRestControllersAreRegistered() {
        assertTypeBean(WorkflowCatalogController.class);
        assertTypeBean(WorkflowDefinitionController.class);
        assertTypeBean(WorkflowInstanceController.class);
        assertTypeBean(WorkflowTaskController.class);
    }

    @Test
    void workflowApplicationUseCasesAndServicesAreRegistered() {
        assertBean("workflowCatalogApplicationService", WorkflowCatalogApplicationService.class);
        assertTypeBean(GetWorkflowCatalogTypeUseCase.class);
        assertTypeBean(ListWorkflowCatalogTypesUseCase.class);
        assertTypeBean(ResolveWorkflowCatalogTypeUseCase.class);

        assertBean("workflowDefinitionApplicationService", WorkflowDefinitionApplicationService.class);
        assertTypeBean(CreateWorkflowDefinitionUseCase.class);
        assertTypeBean(ActivateWorkflowDefinitionUseCase.class);
        assertTypeBean(DeactivateWorkflowDefinitionUseCase.class);
        assertTypeBean(GetWorkflowDefinitionUseCase.class);
        assertTypeBean(ListWorkflowDefinitionsUseCase.class);
        assertTypeBean(CreateWorkflowStepUseCase.class);
        assertTypeBean(CreateWorkflowTransitionUseCase.class);

        assertBean("workflowInstanceApplicationService", WorkflowInstanceApplicationService.class);
        assertTypeBean(StartWorkflowInstanceUseCase.class);
        assertTypeBean(CancelWorkflowInstanceUseCase.class);
        assertTypeBean(GetWorkflowInstanceUseCase.class);
        assertTypeBean(ListWorkflowInstancesUseCase.class);
        assertTypeBean(GetWorkflowTimelineUseCase.class);

        assertBean("workflowTaskApplicationService", WorkflowTaskApplicationService.class);
        assertTypeBean(AssignWorkflowTaskUseCase.class);
        assertTypeBean(ClaimWorkflowTaskUseCase.class);
        assertTypeBean(ApproveWorkflowTaskUseCase.class);
        assertTypeBean(RejectWorkflowTaskUseCase.class);
        assertTypeBean(RequestWorkflowCorrectionUseCase.class);
        assertTypeBean(DelegateWorkflowTaskUseCase.class);
        assertTypeBean(EscalateWorkflowTaskUseCase.class);
        assertTypeBean(CommentWorkflowTaskUseCase.class);
        assertTypeBean(GetWorkflowTaskUseCase.class);
        assertTypeBean(ListWorkflowTasksUseCase.class);
        assertTypeBean(ListMyWorkflowTasksUseCase.class);
    }

    @Test
    void workflowDomainPoliciesServicesAndPortsAreRegistered() {
        assertBean("workflowDefinitionPolicy", WorkflowDefinitionPolicy.class);
        assertBean("workflowTransitionPolicy", WorkflowTransitionPolicy.class);
        assertBean("workflowAssignmentPolicy", WorkflowAssignmentPolicy.class);
        assertBean("workflowDecisionPolicy", WorkflowDecisionPolicy.class);
        assertBean("workflowDelegationPolicy", WorkflowDelegationPolicy.class);
        assertBean("workflowEscalationPolicy", WorkflowEscalationPolicy.class);
        assertBean("workflowTargetPolicy", WorkflowTargetPolicy.class);

        assertBean("workflowDefinitionDomainService", WorkflowDefinitionDomainService.class);
        assertBean("workflowInstanceDomainService", WorkflowInstanceDomainService.class);
        assertBean("workflowTaskDomainService", WorkflowTaskDomainService.class);
        assertBean("workflowDecisionDomainService", WorkflowDecisionDomainService.class);
        assertBean("workflowAssignmentDomainService", WorkflowAssignmentDomainService.class);
        assertBean("workflowEscalationDomainService", WorkflowEscalationDomainService.class);

        assertTypeBean(WorkflowCatalogRepositoryPort.class);
        assertTypeBean(WorkflowDefinitionRepositoryPort.class);
        assertTypeBean(WorkflowInstanceRepositoryPort.class);
        assertTypeBean(WorkflowTaskRepositoryPort.class);
        assertTypeBean(WorkflowTargetLookupPort.class);
        assertTypeBean(WorkflowAuditEventPort.class);
    }

    private <T> void assertTypeBean(Class<T> beanType) {
        assertNotNull(applicationContext.getBean(beanType));
    }

    private <T> void assertBean(String beanName, Class<T> beanType) {
        assertTrue(applicationContext.containsBean(beanName), "Missing Spring bean: " + beanName);
        assertNotNull(applicationContext.getBean(beanName, beanType));
    }
}
