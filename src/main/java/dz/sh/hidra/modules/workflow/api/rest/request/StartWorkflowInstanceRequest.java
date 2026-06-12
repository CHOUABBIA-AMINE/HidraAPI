/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : StartWorkflowInstanceRequest
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : API
 * @Module      : workflow
 * @Package     : dz.sh.hidra.modules.workflow.api.rest.request
 *
 * @Description : REST request to start workflow instance.
 *
 */
package dz.sh.hidra.modules.workflow.api.rest.request;

/**
 * REST request to start workflow instance.
 */
public record StartWorkflowInstanceRequest(
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
