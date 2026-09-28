/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : AuditEvidenceLink
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-28
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : audit
 * @Package     : dz.sh.hidra.modules.audit.domain.model
 *
 * @Description : Supporting evidence link.
 *
 */
package dz.sh.hidra.modules.audit.domain.model;

import dz.sh.hidra.modules.audit.domain.exception.InvalidAuditValueException;
import java.time.Instant;

    /**
     * Supporting evidence link.
     *
         * @param id id
     * @param auditEventId auditEventId
     * @param evidenceTypeId evidenceTypeId
     * @param referenceModule referenceModule
     * @param referenceType referenceType
     * @param referenceId referenceId
     * @param referenceCodeSnapshot referenceCodeSnapshot
     * @param referenceLabelSnapshot referenceLabelSnapshot
     * @param externalUriMasked externalUriMasked
     * @param checksum checksum
     * @param linkedAt linkedAt
     */
    public record AuditEvidenceLink(
            String id,
        String auditEventId,
        String evidenceTypeId,
        String referenceModule,
        String referenceType,
        String referenceId,
        String referenceCodeSnapshot,
        String referenceLabelSnapshot,
        String externalUriMasked,
        String checksum,
        Instant linkedAt
    ) {

        public AuditEvidenceLink {
        // HRA-051 required: id
        if (id == null || id.isBlank()) {
            throw new InvalidAuditValueException("AuditEvidenceLink id must not be blank.");
        }
        // HRA-051 required: auditEventId
        if (auditEventId == null || auditEventId.isBlank()) {
            throw new InvalidAuditValueException("AuditEvidenceLink audit event id must not be blank.");
        }
        // HRA-051 required: evidenceTypeId
        if (evidenceTypeId == null || evidenceTypeId.isBlank()) {
            throw new InvalidAuditValueException("AuditEvidenceLink evidence type id must not be blank.");
        }
        // HRA-051 required: referenceId
        if (referenceId == null || referenceId.isBlank()) {
            throw new InvalidAuditValueException("AuditEvidenceLink reference id must not be blank.");
        }
        // HRA-051 required: linkedAt
        if (linkedAt == null) {
            throw new InvalidAuditValueException("AuditEvidenceLink linked at must not be null.");
        }

        id = normalize(id);
        auditEventId = normalize(auditEventId);
        evidenceTypeId = normalize(evidenceTypeId);
        referenceModule = normalize(referenceModule);
        referenceType = normalize(referenceType);
        referenceId = normalize(referenceId);
        referenceCodeSnapshot = normalize(referenceCodeSnapshot);
        referenceLabelSnapshot = normalize(referenceLabelSnapshot);
        externalUriMasked = normalize(externalUriMasked);
        checksum = normalize(checksum);
        }

        private static String normalize(String value) {
            if (value == null || value.isBlank()) {
                return null;
            }
            return value.trim();
        }
    }
