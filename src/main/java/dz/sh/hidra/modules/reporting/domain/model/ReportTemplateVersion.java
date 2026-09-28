/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : ReportTemplateVersion
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-28
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : reporting
 * @Package     : dz.sh.hidra.modules.reporting.domain.model
 *
 * @Description : Versioned report layout.
 *
 */
package dz.sh.hidra.modules.reporting.domain.model;

import dz.sh.hidra.modules.reporting.domain.exception.InvalidReportingValueException;
import dz.sh.hidra.modules.reporting.domain.value.*;
import java.time.Instant;

    /**
     * Versioned report layout.
     *
         * @param id id
     * @param reportTemplateId reportTemplateId
     * @param versionNumber versionNumber
     * @param status status
     * @param layoutContentReference layoutContentReference
     * @param styleReference styleReference
     * @param checksum checksum
     * @param createdByActorId createdByActorId
     * @param createdByDisplayNameSnapshot createdByDisplayNameSnapshot
     * @param createdAt createdAt
     * @param activatedAt activatedAt
     * @param retiredAt retiredAt
     */
    public record ReportTemplateVersion(
            String id,
        String reportTemplateId,
        int versionNumber,
        ReportTemplateVersionStatus status,
        String layoutContentReference,
        String styleReference,
        String checksum,
        String createdByActorId,
        String createdByDisplayNameSnapshot,
        Instant createdAt,
        Instant activatedAt,
        Instant retiredAt
    ) {

        public ReportTemplateVersion {
        // HRA-051 required: id
        if (id == null || id.isBlank()) {
            throw new InvalidReportingValueException("ReportTemplateVersion id must not be blank.");
        }
        // HRA-051 required: reportTemplateId
        if (reportTemplateId == null || reportTemplateId.isBlank()) {
            throw new InvalidReportingValueException("ReportTemplateVersion report template id must not be blank.");
        }
        // HRA-051 required: status
        if (status == null) {
            throw new InvalidReportingValueException("ReportTemplateVersion status must not be null.");
        }
        // HRA-051 required: checksum
        if (checksum == null || checksum.isBlank()) {
            throw new InvalidReportingValueException("ReportTemplateVersion checksum must not be blank.");
        }

        id = normalize(id);
        reportTemplateId = normalize(reportTemplateId);
        layoutContentReference = normalize(layoutContentReference);
        styleReference = normalize(styleReference);
        checksum = normalize(checksum);
        createdByActorId = normalize(createdByActorId);
        createdByDisplayNameSnapshot = normalize(createdByDisplayNameSnapshot);
        }

        private static String normalize(String value) {
            if (value == null || value.isBlank()) {
                return null;
            }
            return value.trim();
        }
    }
