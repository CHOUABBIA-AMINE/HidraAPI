/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : OrganizationHierarchySnapshot
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-28
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : organization
 * @Package     : dz.sh.hidra.modules.organization.domain.model
 *
 * @Description : Snapshot of organization hierarchy for historical display/use.
 *
 */
package dz.sh.hidra.modules.organization.domain.model;

import dz.sh.hidra.modules.organization.domain.exception.InvalidOrganizationValueException;
import dz.sh.hidra.modules.organization.domain.value.*;
import java.time.Instant;

    /**
     * Snapshot of organization hierarchy for historical display/use.
     *
         * @param id id
     * @param snapshotCode snapshotCode
     * @param capturedAt capturedAt
     * @param capturedByEmployeeId capturedByEmployeeId
     * @param status status
     * @param snapshotPayload snapshotPayload
     * @param description description
     * @param createdAt createdAt
     */
    public record OrganizationHierarchySnapshot(
            String id,
        String snapshotCode,
        Instant capturedAt,
        String capturedByEmployeeId,
        HierarchySnapshotStatus status,
        String snapshotPayload,
        String description,
        Instant createdAt
    ) {

        public OrganizationHierarchySnapshot {
        id = requireText(id, "Hierarchy snapshot ID is required.");
        snapshotCode = requireText(snapshotCode, "Hierarchy snapshot code is required.");
        capturedByEmployeeId = requireText(
                capturedByEmployeeId,
                "Hierarchy snapshot captured-by employee ID is required."
        );
        if (capturedAt == null) {
            throw new InvalidOrganizationValueException("Hierarchy snapshot capturedAt is required.");
        }
        if (status == null) {
            throw new InvalidOrganizationValueException("Hierarchy snapshot status is required.");
        }
        snapshotPayload = normalize(snapshotPayload);
        description = normalize(description);
        }
    private static String requireText(String value, String message) {
        String normalized = normalize(value);
        if (normalized == null) {
            throw new InvalidOrganizationValueException(message);
        }
        return normalized;
    }


        private static String normalize(String value) {
            if (value == null || value.isBlank()) {
                return null;
            }
            return value.trim();
        }
    }
