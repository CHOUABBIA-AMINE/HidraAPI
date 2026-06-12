/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : WorkflowModuleConfiguration
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : Infrastructure
 * @Module      : workflow
 * @Package     : dz.sh.hidra.modules.workflow.infrastructure.configuration
 *
 * @Description : Workflow infrastructure configuration.
 *
 */
package dz.sh.hidra.modules.workflow.infrastructure.configuration;

/**
 * Workflow infrastructure configuration.
 */
public record WorkflowModuleConfiguration(
        boolean targetBindingRequired,
        boolean auditOutboxReferenceEnabled,
        boolean slaPoliciesEnabled,
        boolean notificationsDelegatedToNotificationModule
) {

    public static WorkflowModuleConfiguration defaults() {
        return new WorkflowModuleConfiguration(true, true, true, true);
    }
}
