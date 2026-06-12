/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : WorkflowComment
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : workflow
 * @Package     : dz.sh.hidra.modules.workflow.domain.model
 *
 * @Description : Append-only workflow process comment.
 *
 */
package dz.sh.hidra.modules.workflow.domain.model;

import dz.sh.hidra.modules.workflow.domain.value.*;
import java.time.Instant;

    /**
     * Append-only workflow process comment.
     *
         * @param id id
     * @param instanceId instanceId
     * @param taskId taskId
     * @param actorId actorId
     * @param actorUsernameSnapshot actorUsernameSnapshot
     * @param actorDisplayNameSnapshot actorDisplayNameSnapshot
     * @param actorRoleCodeSnapshot actorRoleCodeSnapshot
     * @param commentText commentText
     * @param visibility visibility
     * @param parentCommentId parentCommentId
     * @param commentedAt commentedAt
     * @param editedAt editedAt
     */
    public record WorkflowComment(
            String id,
        String instanceId,
        String taskId,
        String actorId,
        String actorUsernameSnapshot,
        String actorDisplayNameSnapshot,
        String actorRoleCodeSnapshot,
        String commentText,
        WorkflowCommentVisibility visibility,
        String parentCommentId,
        Instant commentedAt,
        Instant editedAt
    ) {

        public WorkflowComment {
        id = normalize(id);
        instanceId = normalize(instanceId);
        taskId = normalize(taskId);
        actorId = normalize(actorId);
        actorUsernameSnapshot = normalize(actorUsernameSnapshot);
        actorDisplayNameSnapshot = normalize(actorDisplayNameSnapshot);
        actorRoleCodeSnapshot = normalize(actorRoleCodeSnapshot);
        commentText = normalize(commentText);
        parentCommentId = normalize(parentCommentId);
        }

        private static String normalize(String value) {
            if (value == null || value.isBlank()) {
                return null;
            }
            return value.trim();
        }
    }
