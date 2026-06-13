/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : AddRiskEvidenceRequest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-13
 *
 * @Type        : Record
 * @Layer       : API
 * @Module      : risk
 * @Package     : dz.sh.hidra.modules.risk.api.rest.request
 *
 * @Description : REST request for add risk evidence.
 *
 */
package dz.sh.hidra.modules.risk.api.rest.request;

import java.time.Instant;

/**
 * REST request for add risk evidence.
 */
public record AddRiskEvidenceRequest(
        String riskAssessmentId,
        String evidenceModule,
        String evidenceType,
        String evidenceId,
        String evidenceCodeSnapshot,
        String evidenceLabelSnapshot,
        Instant evidenceTimestamp,
        String evidenceHash,
        String evidenceSummary
) {
}
