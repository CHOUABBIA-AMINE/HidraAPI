/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : WorkflowDefinitionDiscoveryUseCase
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-11-06
 *
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : workflow
 * @Package     : dz.sh.hidra.modules.workflow.application.port.in
 *
 * @Description : Discovers currently supported active process bindings for one owner-authorized target.
 *
 */
package dz.sh.hidra.modules.workflow.application.port.in;

import java.util.List;
import java.util.Optional;

public interface WorkflowDefinitionDiscoveryUseCase {
    List<AvailableDefinition> discover(String targetModule, String targetTypeId,
            String workflowPurposeId, String targetId, String targetLocator);

    Optional<TargetHandoff> installedCharacteristicLinkTarget(String companyPartyId,
            String installedUnitId, String linkId, long revisionId);

    record TargetHandoff(String targetModule, String targetTypeId, String workflowPurposeId,
            String targetId, String targetLocator, long revisionId, String digestFormat) {}

    record AssignmentChoice(String actorId, String username, String displayName) {}
    record AvailableDecision(String transitionId, String decision, String fromStepId,
                             String toStepId, boolean reasonRequired, boolean commentRequired,
                             String requiredPermissionCode) {}
    record AvailableDefinition(String id, String code, String nameFr, int version,
            String targetModule, String targetTypeId, String workflowPurposeId,
            String firstStepId, List<AssignmentChoice> assignmentChoices,
            List<AvailableDecision> availableDecisions) {}
}
