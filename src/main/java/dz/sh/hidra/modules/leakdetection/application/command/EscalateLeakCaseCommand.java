/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : EscalateLeakCaseCommand
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : Application
 * @Module      : leakdetection
 * @Package     : dz.sh.hidra.modules.leakdetection.application.command
 *
 * @Description : Command to escalate a leak case by neutral reference.
 *
 */
package dz.sh.hidra.modules.leakdetection.application.command;

import dz.sh.hidra.modules.leakdetection.domain.value.LeakEscalationTargetType;

/**
 * Command to escalate a leak case by neutral reference.
 */
public record EscalateLeakCaseCommand(
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
