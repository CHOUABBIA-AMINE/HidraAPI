/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : AlarmComment
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : alarm
 * @Package     : dz.sh.hidra.modules.alarm.domain.model
 *
 * @Description : Human comment or note attached to an alarm timeline.
 *
 */
package dz.sh.hidra.modules.alarm.domain.model;

import dz.sh.hidra.modules.alarm.domain.value.*;
import java.time.Instant;

    /**
     * Human comment or note attached to an alarm timeline.
     *
         * @param id id
     * @param alarmId alarmId
     * @param commentText commentText
     * @param visibility visibility
     * @param createdByActorId createdByActorId
     * @param createdByDisplayName createdByDisplayName
     * @param createdAt createdAt
     * @param updatedAt updatedAt
     */
    public record AlarmComment(
            String id,
        String alarmId,
        String commentText,
        AlarmCommentVisibility visibility,
        String createdByActorId,
        String createdByDisplayName,
        Instant createdAt,
        Instant updatedAt
    ) {

        public AlarmComment {
        id = normalize(id);
        alarmId = normalize(alarmId);
        commentText = normalize(commentText);
        createdByActorId = normalize(createdByActorId);
        createdByDisplayName = normalize(createdByDisplayName);
        }

        private static String normalize(String value) {
            if (value == null || value.isBlank()) {
                return null;
            }
            return value.trim();
        }
    }
