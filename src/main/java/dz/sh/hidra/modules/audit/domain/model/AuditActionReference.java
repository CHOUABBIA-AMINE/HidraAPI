/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : AuditActionReference
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-28
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

import dz.sh.hidra.modules.audit.domain.exception.InvalidAuditValueException;
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
        // HRA-051 required: id
        if (id == null || id.isBlank()) {
            throw new InvalidAuditValueException("AuditActionReference id must not be blank.");
        }
        // HRA-051 required: auditEventId
        if (auditEventId == null || auditEventId.isBlank()) {
            throw new InvalidAuditValueException("AuditActionReference audit event id must not be blank.");
        }
        // HRA-051 required: actionCode
        if (actionCode == null || actionCode.isBlank()) {
            throw new InvalidAuditValueException("AuditActionReference action code must not be blank.");
        }
        // HRA-051 required: actionTypeId
        if (actionTypeId == null || actionTypeId.isBlank()) {
            throw new InvalidAuditValueException("AuditActionReference action type id must not be blank.");
        }
        // HRA-051 required: operation
        if (operation == null) {
            throw new InvalidAuditValueException("AuditActionReference operation must not be null.");
        }
        // HRA-051 required: capturedAt
        if (capturedAt == null) {
            throw new InvalidAuditValueException("AuditActionReference captured at must not be null.");
        }

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
