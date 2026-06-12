/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : AuditBeforeAfterValue
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : audit
 * @Package     : dz.sh.hidra.modules.audit.domain.model
 *
 * @Description : Field-level before and after evidence.
 *
 */
package dz.sh.hidra.modules.audit.domain.model;

import dz.sh.hidra.modules.audit.domain.value.*;
import java.time.Instant;

    /**
     * Field-level before and after evidence.
     *
         * @param id id
     * @param auditEventId auditEventId
     * @param fieldPath fieldPath
     * @param fieldLabelSnapshot fieldLabelSnapshot
     * @param valueType valueType
     * @param beforeValueText beforeValueText
     * @param afterValueText afterValueText
     * @param beforeValueHash beforeValueHash
     * @param afterValueHash afterValueHash
     * @param masked masked
     * @param maskReasonId maskReasonId
     * @param changed changed
     * @param recordedAt recordedAt
     */
    public record AuditBeforeAfterValue(
            String id,
        String auditEventId,
        String fieldPath,
        String fieldLabelSnapshot,
        AuditValueType valueType,
        String beforeValueText,
        String afterValueText,
        String beforeValueHash,
        String afterValueHash,
        boolean masked,
        String maskReasonId,
        boolean changed,
        Instant recordedAt
    ) {

        public AuditBeforeAfterValue {
        id = normalize(id);
        auditEventId = normalize(auditEventId);
        fieldPath = normalize(fieldPath);
        fieldLabelSnapshot = normalize(fieldLabelSnapshot);
        beforeValueText = normalize(beforeValueText);
        afterValueText = normalize(afterValueText);
        beforeValueHash = normalize(beforeValueHash);
        afterValueHash = normalize(afterValueHash);
        maskReasonId = normalize(maskReasonId);
        }
        public boolean storesMaskedEvidence() {
            return masked || valueType == AuditValueType.MASKED;
        }
        private static String normalize(String value) {
            if (value == null || value.isBlank()) {
                return null;
            }
            return value.trim();
        }
    }
