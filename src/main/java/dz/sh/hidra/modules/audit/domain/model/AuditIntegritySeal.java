/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : AuditIntegritySeal
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-28
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : audit
 * @Package     : dz.sh.hidra.modules.audit.domain.model
 *
 * @Description : Tamper-evidence integrity seal.
 *
 */
package dz.sh.hidra.modules.audit.domain.model;

import dz.sh.hidra.modules.audit.domain.exception.InvalidAuditValueException;
import dz.sh.hidra.modules.audit.domain.value.*;
import java.time.Instant;

    /**
     * Tamper-evidence integrity seal.
     *
         * @param id id
     * @param sealTypeId sealTypeId
     * @param auditEventId auditEventId
     * @param fromRecordedAt fromRecordedAt
     * @param toRecordedAt toRecordedAt
     * @param eventCount eventCount
     * @param hashAlgorithm hashAlgorithm
     * @param rootHash rootHash
     * @param previousSealHash previousSealHash
     * @param sealedByActorId sealedByActorId
     * @param sealedAt sealedAt
     * @param verificationStatus verificationStatus
     * @param verifiedAt verifiedAt
     */
    public record AuditIntegritySeal(
            String id,
        String sealTypeId,
        String auditEventId,
        Instant fromRecordedAt,
        Instant toRecordedAt,
        Integer eventCount,
        String hashAlgorithm,
        String rootHash,
        String previousSealHash,
        String sealedByActorId,
        Instant sealedAt,
        AuditSealVerificationStatus verificationStatus,
        Instant verifiedAt
    ) {

        public AuditIntegritySeal {
        // HRA-051 required: id
        if (id == null || id.isBlank()) {
            throw new InvalidAuditValueException("AuditIntegritySeal id must not be blank.");
        }
        // HRA-051 required: sealTypeId
        if (sealTypeId == null || sealTypeId.isBlank()) {
            throw new InvalidAuditValueException("AuditIntegritySeal seal type id must not be blank.");
        }
        // HRA-051 required: eventCount
        if (eventCount == null) {
            throw new InvalidAuditValueException("AuditIntegritySeal event count must not be null.");
        }
        // HRA-051 required: rootHash
        if (rootHash == null || rootHash.isBlank()) {
            throw new InvalidAuditValueException("AuditIntegritySeal root hash must not be blank.");
        }
        // HRA-051 required: sealedAt
        if (sealedAt == null) {
            throw new InvalidAuditValueException("AuditIntegritySeal sealed at must not be null.");
        }
        // HRA-051 required: verificationStatus
        if (verificationStatus == null) {
            throw new InvalidAuditValueException("AuditIntegritySeal verification status must not be null.");
        }

        id = normalize(id);
        sealTypeId = normalize(sealTypeId);
        auditEventId = normalize(auditEventId);
        hashAlgorithm = normalize(hashAlgorithm);
        rootHash = normalize(rootHash);
        previousSealHash = normalize(previousSealHash);
        sealedByActorId = normalize(sealedByActorId);
        }

        private static String normalize(String value) {
            if (value == null || value.isBlank()) {
                return null;
            }
            return value.trim();
        }
    }
