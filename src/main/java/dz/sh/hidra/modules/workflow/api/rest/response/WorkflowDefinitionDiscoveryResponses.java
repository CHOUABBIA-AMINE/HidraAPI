/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : WorkflowDefinitionDiscoveryResponses
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-11-04
 *
 * @Type        : Class
 * @Layer       : API
 * @Module      : workflow
 * @Package     : dz.sh.hidra.modules.workflow.api.rest.response
 *
 * @Description : Explicit REST view of active workflow bindings and safe assignment metadata.
 *
 */
package dz.sh.hidra.modules.workflow.api.rest.response;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;

public final class WorkflowDefinitionDiscoveryResponses {
    private WorkflowDefinitionDiscoveryResponses() {}

    @Schema(description = "One currently supported, owner-authorized Workflow process binding.")
    public record Definition(String id, String code, String nameFr, int version,
            String targetModule, String targetTypeId, String workflowPurposeId,
            String firstStepId, List<Assignment> assignmentChoices, List<Decision> availableDecisions) {}

    @Schema(description = "Current eligible independent human reviewer, not an authorization grant.")
    public record Assignment(String actorId, String username, String displayName) {}

    @Schema(description = "Supported transition metadata, not a statement of execution permission.")
    public record Decision(String transitionId, String decision, String fromStepId,
            String toStepId, boolean reasonRequired, boolean commentRequired,
            String requiredPermissionCode) {}
}
