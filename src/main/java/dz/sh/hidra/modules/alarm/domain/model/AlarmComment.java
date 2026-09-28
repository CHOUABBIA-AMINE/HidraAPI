/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : AlarmComment
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-28
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

import dz.sh.hidra.modules.alarm.domain.exception.InvalidAlarmValueException;
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
        // HRA-051 required: id
        if (id == null || id.isBlank()) {
            throw new InvalidAlarmValueException("AlarmComment id must not be blank.");
        }
        // HRA-051 required: alarmId
        if (alarmId == null || alarmId.isBlank()) {
            throw new InvalidAlarmValueException("AlarmComment alarm id must not be blank.");
        }
        // HRA-051 required: visibility
        if (visibility == null) {
            throw new InvalidAlarmValueException("AlarmComment visibility must not be null.");
        }
        // HRA-051 required: createdByActorId
        if (createdByActorId == null || createdByActorId.isBlank()) {
            throw new InvalidAlarmValueException("AlarmComment created by actor id must not be blank.");
        }

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
