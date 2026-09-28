/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : AlarmEvidenceLink
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-28
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : alarm
 * @Package     : dz.sh.hidra.modules.alarm.domain.model
 *
 * @Description : Links an alarm to supporting evidence.
 *
 */
package dz.sh.hidra.modules.alarm.domain.model;

import dz.sh.hidra.modules.alarm.domain.exception.InvalidAlarmValueException;
import dz.sh.hidra.modules.alarm.domain.value.*;
import java.time.Instant;

    /**
     * Links an alarm to supporting evidence.
     *
         * @param id id
     * @param alarmId alarmId
     * @param evidenceType evidenceType
     * @param evidenceReferenceId evidenceReferenceId
     * @param evidenceCodeSnapshot evidenceCodeSnapshot
     * @param evidenceNameSnapshot evidenceNameSnapshot
     * @param description description
     * @param createdAt createdAt
     */
    public record AlarmEvidenceLink(
            String id,
        String alarmId,
        AlarmEvidenceType evidenceType,
        String evidenceReferenceId,
        String evidenceCodeSnapshot,
        String evidenceNameSnapshot,
        String description,
        Instant createdAt
    ) {

        public AlarmEvidenceLink {
        // HRA-051 required: id
        if (id == null || id.isBlank()) {
            throw new InvalidAlarmValueException("AlarmEvidenceLink id must not be blank.");
        }
        // HRA-051 required: alarmId
        if (alarmId == null || alarmId.isBlank()) {
            throw new InvalidAlarmValueException("AlarmEvidenceLink alarm id must not be blank.");
        }
        // HRA-051 required: evidenceType
        if (evidenceType == null) {
            throw new InvalidAlarmValueException("AlarmEvidenceLink evidence type must not be null.");
        }
        // HRA-051 required: evidenceReferenceId
        if (evidenceReferenceId == null || evidenceReferenceId.isBlank()) {
            throw new InvalidAlarmValueException("AlarmEvidenceLink evidence reference id must not be blank.");
        }

        id = normalize(id);
        alarmId = normalize(alarmId);
        evidenceReferenceId = normalize(evidenceReferenceId);
        evidenceCodeSnapshot = normalize(evidenceCodeSnapshot);
        evidenceNameSnapshot = normalize(evidenceNameSnapshot);
        description = normalize(description);
        }

        private static String normalize(String value) {
            if (value == null || value.isBlank()) {
                return null;
            }
            return value.trim();
        }
    }
