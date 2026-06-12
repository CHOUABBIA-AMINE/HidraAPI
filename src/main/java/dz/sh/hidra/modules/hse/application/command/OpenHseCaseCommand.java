/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : OpenHseCaseCommand
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : Application
 * @Module      : hse
 * @Package     : dz.sh.hidra.modules.hse.application.command
 *
 * @Description : Command to open HSE case.
 *
 */
package dz.sh.hidra.modules.hse.application.command;

import dz.sh.hidra.modules.hse.domain.value.HseCaseSourceType;

import java.time.Instant;

/**
 * Command to open HSE case.
 */
public record OpenHseCaseCommand(
        String caseNumber,
        String title,
        String description,
        String caseTypeId,
        String severityId,
        String priorityId,
        HseCaseSourceType sourceType,
        String incidentReferenceId,
        String incidentCodeSnapshot,
        String incidentTitleSnapshot,
        String targetModule,
        String targetTypeCode,
        String targetId,
        String targetCodeSnapshot,
        String targetLabelSnapshot,
        Instant occurredAt,
        String reportedByActorId,
        String reportedByDisplayNameSnapshot,
        String responsibleOrganizationUnitId,
        String responsibleOrganizationUnitNameSnapshot,
        String workflowInstanceId
) {
}
