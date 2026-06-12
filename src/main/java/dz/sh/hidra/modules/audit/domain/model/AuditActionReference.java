/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : AuditActionReference
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : audit
 * @Package     : dz.sh.hidra.modules.audit.domain.model
 *
 * @Description : Normalized action semantics.
 *
 */
package dz.sh.hidra.modules.audit.domain.model;

import dz.sh.hidra.modules.audit.domain.value.*;
import java.time.Instant;

    /**
     * Normalized action semantics.
     *
         * @param id id
     * @param auditEventId auditEventId
     * @param actionCode actionCode
     * @param actionTypeId actionTypeId
     * @param operation operation
     * @param commandName commandName
     * @param resultStatus resultStatus
     * @param failureReasonCode failureReasonCode
     * @param capturedAt capturedAt
     */
    public record AuditActionReference(
            String id,
        String auditEventId,
        String actionCode,
        String actionTypeId,
        AuditOperation operation,
        String commandName,
        String resultStatus,
        String failureReasonCode,
        Instant capturedAt
    ) {

        public AuditActionReference {
        id = normalize(id);
        auditEventId = normalize(auditEventId);
        actionCode = normalize(actionCode);
        actionTypeId = normalize(actionTypeId);
        commandName = normalize(commandName);
        resultStatus = normalize(resultStatus);
        failureReasonCode = normalize(failureReasonCode);
        }

        private static String normalize(String value) {
            if (value == null || value.isBlank()) {
                return null;
            }
            return value.trim();
        }
    }
