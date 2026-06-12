/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : WorkflowAction
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : workflow
 * @Package     : dz.sh.hidra.modules.workflow.domain.model
 *
 * @Description : Append-only workflow action and decision evidence.
 *
 */
package dz.sh.hidra.modules.workflow.domain.model;

import dz.sh.hidra.modules.workflow.domain.value.*;
import java.time.Instant;

    /**
     * Append-only workflow action and decision evidence.
     *
         * @param id id
     * @param instanceId instanceId
     * @param taskId taskId
     * @param actionType actionType
     * @param decision decision
     * @param reasonId reasonId
     * @param decisionNote decisionNote
     * @param commentText commentText
     * @param actorId actorId
     * @param actorUsernameSnapshot actorUsernameSnapshot
     * @param actorDisplayNameSnapshot actorDisplayNameSnapshot
     * @param actorRoleCodeSnapshot actorRoleCodeSnapshot
     * @param organizationUnitId organizationUnitId
     * @param organizationUnitNameSnapshot organizationUnitNameSnapshot
     * @param organizationRoleCodeSnapshot organizationRoleCodeSnapshot
     * @param correlationId correlationId
     * @param actionSequence actionSequence
     * @param sourceSystem sourceSystem
     * @param ipAddressHash ipAddressHash
     * @param userAgentHash userAgentHash
     * @param actedAt actedAt
     */
    public record WorkflowAction(
            String id,
        String instanceId,
        String taskId,
        WorkflowActionType actionType,
        WorkflowDecision decision,
        String reasonId,
        String decisionNote,
        String commentText,
        String actorId,
        String actorUsernameSnapshot,
        String actorDisplayNameSnapshot,
        String actorRoleCodeSnapshot,
        String organizationUnitId,
        String organizationUnitNameSnapshot,
        String organizationRoleCodeSnapshot,
        String correlationId,
        long actionSequence,
        String sourceSystem,
        String ipAddressHash,
        String userAgentHash,
        Instant actedAt
    ) {

        public WorkflowAction {
        id = normalize(id);
        instanceId = normalize(instanceId);
        taskId = normalize(taskId);
        reasonId = normalize(reasonId);
        decisionNote = normalize(decisionNote);
        commentText = normalize(commentText);
        actorId = normalize(actorId);
        actorUsernameSnapshot = normalize(actorUsernameSnapshot);
        actorDisplayNameSnapshot = normalize(actorDisplayNameSnapshot);
        actorRoleCodeSnapshot = normalize(actorRoleCodeSnapshot);
        organizationUnitId = normalize(organizationUnitId);
        organizationUnitNameSnapshot = normalize(organizationUnitNameSnapshot);
        organizationRoleCodeSnapshot = normalize(organizationRoleCodeSnapshot);
        correlationId = normalize(correlationId);
        sourceSystem = normalize(sourceSystem);
        ipAddressHash = normalize(ipAddressHash);
        userAgentHash = normalize(userAgentHash);
        }

        private static String normalize(String value) {
            if (value == null || value.isBlank()) {
                return null;
            }
            return value.trim();
        }
    }
