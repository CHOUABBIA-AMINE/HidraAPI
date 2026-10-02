/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : EscalateLeakCaseRequest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-13
 *
 * @Type        : Record
 * @Layer       : API
 * @Module      : leakdetection
 * @Package     : dz.sh.hidra.modules.leakdetection.api.rest.request
 *
 * @Description : REST request for escalate leak case.
 *
 */
package dz.sh.hidra.modules.leakdetection.api.rest.request;

import dz.sh.hidra.modules.leakdetection.domain.value.LeakEscalationTargetType;

/**
 * REST request for escalate leak case.
 */
public record EscalateLeakCaseRequest(
        String caseId,
        String candidateId,
        LeakEscalationTargetType targetType,
        String targetReferenceId,
        String targetCodeSnapshot,
        String targetNameSnapshot,
        String escalatedByActorId,
        String reasonText,
        String correlationId
) {
}
