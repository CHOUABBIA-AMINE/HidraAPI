/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : OpenIntegrityCaseCommand
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : Application
 * @Module      : integrity
 * @Package     : dz.sh.hidra.modules.integrity.application.command
 *
 * @Description : Command to open integrity case.
 *
 */
package dz.sh.hidra.modules.integrity.application.command;

/**
 * Command to open integrity case.
 */
public record OpenIntegrityCaseCommand(
        String caseNumber,
        String title,
        String description,
        String caseTypeId,
        String severityId,
        String topologyAssetTypeCode,
        String topologyAssetId,
        String topologyAssetCodeSnapshot,
        String primaryDefectId,
        String sourceIncidentId,
        String sourceHseCaseId,
        String responsibleOrganizationUnitId,
        String workflowInstanceId,
        String openedByActorId
) {
}
