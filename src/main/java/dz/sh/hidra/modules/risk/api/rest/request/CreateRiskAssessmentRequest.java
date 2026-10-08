/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : CreateRiskAssessmentRequest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-08
 *
 * @Type        : Record
 * @Layer       : API
 * @Module      : risk
 * @Package     : dz.sh.hidra.modules.risk.api.rest.request
 *
 * @Description : REST request to create risk assessment.
 *
 */
package dz.sh.hidra.modules.risk.api.rest.request;

import java.time.Instant;

/**
 * REST request to create risk assessment.
 */
public record CreateRiskAssessmentRequest(
        String riskRegisterId,
        String assessmentNumber,
        String title,
        String description,
        String assessmentTypeId,
        String methodologyId,
        String scopeId,
        String riskScenarioId,
        Instant assessmentDate,
        Instant validFrom,
        Instant validTo,
        String assessedByActorId,
        String assessedByDisplayNameSnapshot,
        java.util.List<dz.sh.hidra.modules.risk.application.command.RiskAssessmentScopeInput> scopes
) {
    public CreateRiskAssessmentRequest { scopes = scopes == null ? java.util.List.of() : java.util.List.copyOf(scopes); }
    public CreateRiskAssessmentRequest(String riskRegisterId, String assessmentNumber, String title, String description,
            String assessmentTypeId, String methodologyId, String scopeId, String riskScenarioId,
            Instant assessmentDate, Instant validFrom, Instant validTo, String assessedByActorId,
            String assessedByDisplayNameSnapshot) {
        this(riskRegisterId, assessmentNumber, title, description, assessmentTypeId, methodologyId,
                scopeId, riskScenarioId, assessmentDate, validFrom, validTo, assessedByActorId,
                assessedByDisplayNameSnapshot, java.util.List.of());
    }
}
