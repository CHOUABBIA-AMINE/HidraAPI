/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : ApproveRiskAssessmentCommand
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-08
 *
 * @Type        : Record
 * @Layer       : Application
 * @Module      : risk
 * @Package     : dz.sh.hidra.modules.risk.application.command
 *
 * @Description : Enforces governed Risk owner boundaries.
 *
 */
package dz.sh.hidra.modules.risk.application.command;

import java.time.Instant;
public record ApproveRiskAssessmentCommand(String assessmentId, String workflowInstanceId, String taskId,
        String transitionId, String reviewActionId, Instant expectedTaskUpdatedAt, String reasonId,
        String decisionNote, String commentText, String correlationId) {}
