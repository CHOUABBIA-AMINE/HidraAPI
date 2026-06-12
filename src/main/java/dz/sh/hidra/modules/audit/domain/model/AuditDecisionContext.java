/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : AuditDecisionContext
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : audit
 * @Package     : dz.sh.hidra.modules.audit.domain.model
 *
 * @Description : Decision-specific evidence context.
 *
 */
package dz.sh.hidra.modules.audit.domain.model;

import java.time.Instant;

    /**
     * Decision-specific evidence context.
     *
         * @param id id
     * @param auditEventId auditEventId
     * @param decisionCode decisionCode
     * @param decisionTypeId decisionTypeId
     * @param reasonId reasonId
     * @param reasonText reasonText
     * @param commentText commentText
     * @param policyCode policyCode
     * @param workflowInstanceId workflowInstanceId
     * @param workflowTaskId workflowTaskId
     * @param workflowActionId workflowActionId
     * @param fromState fromState
     * @param toState toState
     * @param decidedAt decidedAt
     */
    public record AuditDecisionContext(
            String id,
        String auditEventId,
        String decisionCode,
        String decisionTypeId,
        String reasonId,
        String reasonText,
        String commentText,
        String policyCode,
        String workflowInstanceId,
        String workflowTaskId,
        String workflowActionId,
        String fromState,
        String toState,
        Instant decidedAt
    ) {

        public AuditDecisionContext {
        id = normalize(id);
        auditEventId = normalize(auditEventId);
        decisionCode = normalize(decisionCode);
        decisionTypeId = normalize(decisionTypeId);
        reasonId = normalize(reasonId);
        reasonText = normalize(reasonText);
        commentText = normalize(commentText);
        policyCode = normalize(policyCode);
        workflowInstanceId = normalize(workflowInstanceId);
        workflowTaskId = normalize(workflowTaskId);
        workflowActionId = normalize(workflowActionId);
        fromState = normalize(fromState);
        toState = normalize(toState);
        }

        private static String normalize(String value) {
            if (value == null || value.isBlank()) {
                return null;
            }
            return value.trim();
        }
    }
