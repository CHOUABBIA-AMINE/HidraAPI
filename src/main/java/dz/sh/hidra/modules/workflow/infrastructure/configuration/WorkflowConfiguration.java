/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : WorkflowConfiguration
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Configuration
 * @Layer       : Infrastructure
 * @Module      : workflow
 * @Package     : dz.sh.hidra.modules.workflow.infrastructure.configuration
 *
 * @Description : Spring configuration for workflow domain and application services.
 *
 */
package dz.sh.hidra.modules.workflow.infrastructure.configuration;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

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
import dz.sh.hidra.modules.workflow.domain.model.WorkflowAction;
import dz.sh.hidra.modules.workflow.domain.model.WorkflowInstance;
import dz.sh.hidra.modules.workflow.domain.model.WorkflowTask;
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

/**
 * Spring configuration for workflow domain and application services.
 *
 * <p>Business role:
 * Wires the workflow module's policies, domain services, application services, and temporary
 * audit-ready event port.
 *
 * <p>Architecture role:
 * This is infrastructure-layer composition only. It does not contain persistence mapping, REST
 * mapping, migrations, tests, telemetry implementation imports, topology implementation imports,
 * planning, monitoring, incidents, analytics, reporting, or notification behavior.
 *
 * <p>Dependency rule:
 * Outbound repository and lookup ports are supplied by later infrastructure adapters. This
 * configuration depends on outbound port interfaces, not JPA repositories or external clients.
 */
@Configuration
public class WorkflowConfiguration {

    @Bean
    public WorkflowDefinitionPolicy workflowDefinitionPolicy() {
        return new WorkflowDefinitionPolicy();
    }

    @Bean
    public WorkflowTransitionPolicy workflowTransitionPolicy() {
        return new WorkflowTransitionPolicy();
    }

    @Bean
    public WorkflowAssignmentPolicy workflowAssignmentPolicy() {
        return new WorkflowAssignmentPolicy();
    }

    @Bean
    public WorkflowDecisionPolicy workflowDecisionPolicy() {
        return new WorkflowDecisionPolicy();
    }

    @Bean
    public WorkflowDelegationPolicy workflowDelegationPolicy() {
        return new WorkflowDelegationPolicy();
    }

    @Bean
    public WorkflowEscalationPolicy workflowEscalationPolicy() {
        return new WorkflowEscalationPolicy();
    }

    @Bean
    public WorkflowTargetPolicy workflowTargetPolicy() {
        return new WorkflowTargetPolicy();
    }

    @Bean
    public WorkflowDefinitionDomainService workflowDefinitionDomainService(
            WorkflowDefinitionPolicy workflowDefinitionPolicy,
            WorkflowTransitionPolicy workflowTransitionPolicy) {

        return new WorkflowDefinitionDomainService(
                workflowDefinitionPolicy,
                workflowTransitionPolicy);
    }

    @Bean
    public WorkflowInstanceDomainService workflowInstanceDomainService(
            WorkflowDefinitionPolicy workflowDefinitionPolicy,
            WorkflowTargetPolicy workflowTargetPolicy) {

        return new WorkflowInstanceDomainService(
                workflowDefinitionPolicy,
                workflowTargetPolicy);
    }

    @Bean
    public WorkflowTaskDomainService workflowTaskDomainService(
            WorkflowAssignmentPolicy workflowAssignmentPolicy) {

        return new WorkflowTaskDomainService(workflowAssignmentPolicy);
    }

    @Bean
    public WorkflowDecisionDomainService workflowDecisionDomainService(
            WorkflowDecisionPolicy workflowDecisionPolicy,
            WorkflowTransitionPolicy workflowTransitionPolicy) {

        return new WorkflowDecisionDomainService(
                workflowDecisionPolicy,
                workflowTransitionPolicy);
    }

    @Bean
    public WorkflowAssignmentDomainService workflowAssignmentDomainService(
            WorkflowAssignmentPolicy workflowAssignmentPolicy) {

        return new WorkflowAssignmentDomainService(workflowAssignmentPolicy);
    }

    @Bean
    public WorkflowEscalationDomainService workflowEscalationDomainService(
            WorkflowEscalationPolicy workflowEscalationPolicy) {

        return new WorkflowEscalationDomainService(workflowEscalationPolicy);
    }

    @Bean
    @ConditionalOnMissingBean(WorkflowAuditEventPort.class)
    public WorkflowAuditEventPort workflowAuditEventPort() {
        return new WorkflowAuditEventPort() {

            @Override
            public void recordWorkflowStarted(WorkflowInstance instance) {
                // Intentionally no-op until audit hardening is implemented.
            }

            @Override
            public void recordWorkflowAction(WorkflowAction action) {
                // Intentionally no-op until audit hardening is implemented.
            }

            @Override
            public void recordWorkflowTaskChanged(WorkflowTask task) {
                // Intentionally no-op until audit hardening is implemented.
            }
        };
    }

    @Bean
    public WorkflowCatalogApplicationService workflowCatalogApplicationService(
            WorkflowCatalogRepositoryPort workflowCatalogRepositoryPort) {

        return new WorkflowCatalogApplicationService(workflowCatalogRepositoryPort);
    }

    @Bean
    public WorkflowDefinitionApplicationService workflowDefinitionApplicationService(
            WorkflowDefinitionRepositoryPort workflowDefinitionRepositoryPort,
            WorkflowDefinitionDomainService workflowDefinitionDomainService) {

        return new WorkflowDefinitionApplicationService(
                workflowDefinitionRepositoryPort,
                workflowDefinitionDomainService);
    }

    @Bean
    public WorkflowInstanceApplicationService workflowInstanceApplicationService(
            WorkflowDefinitionRepositoryPort workflowDefinitionRepositoryPort,
            WorkflowInstanceRepositoryPort workflowInstanceRepositoryPort,
            WorkflowTargetLookupPort workflowTargetLookupPort,
            WorkflowAuditEventPort workflowAuditEventPort,
            WorkflowInstanceDomainService workflowInstanceDomainService) {

        return new WorkflowInstanceApplicationService(
                workflowDefinitionRepositoryPort,
                workflowInstanceRepositoryPort,
                workflowTargetLookupPort,
                workflowAuditEventPort,
                workflowInstanceDomainService);
    }

    @Bean
    public WorkflowTaskApplicationService workflowTaskApplicationService(
            WorkflowInstanceRepositoryPort workflowInstanceRepositoryPort,
            WorkflowTaskRepositoryPort workflowTaskRepositoryPort,
            WorkflowAuditEventPort workflowAuditEventPort,
            WorkflowTaskDomainService workflowTaskDomainService,
            WorkflowDecisionDomainService workflowDecisionDomainService) {

        return new WorkflowTaskApplicationService(
                workflowInstanceRepositoryPort,
                workflowTaskRepositoryPort,
                workflowAuditEventPort,
                workflowTaskDomainService,
                workflowDecisionDomainService);
    }
}
