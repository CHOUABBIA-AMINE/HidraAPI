/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : OpenLeakCaseCommand
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : Application
 * @Module      : leakdetection
 * @Package     : dz.sh.hidra.modules.leakdetection.application.command
 *
 * @Description : Command to open a leak detection case.
 *
 */
package dz.sh.hidra.modules.leakdetection.application.command;

import java.math.BigDecimal;

/**
 * Command to open a leak detection case.
 */
public record OpenLeakCaseCommand(
        String caseNumber,
        String primaryCandidateId,
        String topologyAssetType,
        String topologyAssetId,
        String topologyAssetCode,
        String owningOrganizationUnitId,
        BigDecimal confidenceScore,
        String openedByActorId,
        String correlationId
) {
}
