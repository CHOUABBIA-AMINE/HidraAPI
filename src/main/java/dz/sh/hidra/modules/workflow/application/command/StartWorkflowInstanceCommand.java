/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : StartWorkflowInstanceCommand
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : Application
 * @Module      : workflow
 * @Package     : dz.sh.hidra.modules.workflow.application.command
 *
 * @Description : Command to start a workflow instance.
 *
 */
package dz.sh.hidra.modules.workflow.application.command;

/**
 * Command to start a workflow instance.
 */
public record StartWorkflowInstanceCommand(
        String definitionId,
        int definitionVersion,
        String workflowPurposeId,
        String targetModule,
        String targetTypeId,
        String targetId,
        String targetCodeSnapshot,
        String targetLabelSnapshot,
        String currentStepId,
        String startedByActorId,
        String startedByUsernameSnapshot,
        String startedByDisplayNameSnapshot,
        String startedByRoleCodeSnapshot,
        String correlationId
) {
}
