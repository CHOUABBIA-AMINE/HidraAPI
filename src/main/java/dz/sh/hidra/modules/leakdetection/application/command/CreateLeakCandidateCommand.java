/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : CreateLeakCandidateCommand
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : Application
 * @Module      : leakdetection
 * @Package     : dz.sh.hidra.modules.leakdetection.application.command
 *
 * @Description : Command to create a leak candidate.
 *
 */
package dz.sh.hidra.modules.leakdetection.application.command;

import java.math.BigDecimal;
import java.time.Instant;

/**
 * Command to create a leak candidate.
 */
public record CreateLeakCandidateCommand(
        String runId,
        String profileId,
        String candidateNumber,
        String topologyAssetType,
        String topologyAssetId,
        String topologyAssetCode,
        String topologyAssetNameSnapshot,
        Instant suspectedAt,
        Instant firstEvidenceAt,
        BigDecimal confidenceScore,
        String summary,
        String correlationId
) {
}
