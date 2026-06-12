/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : HseCorrectivePreventiveAction
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : hse
 * @Package     : dz.sh.hidra.modules.hse.domain.model
 *
 * @Description : Corrective and preventive HSE action.
 *
 */
package dz.sh.hidra.modules.hse.domain.model;

import dz.sh.hidra.modules.hse.domain.value.*;
import java.time.Instant;

    /**
     * Corrective and preventive HSE action.
     *
         * @param id id
     * @param hseCaseId hseCaseId
     * @param actionNumber actionNumber
     * @param actionTypeId actionTypeId
     * @param title title
     * @param description description
     * @param ownerActorId ownerActorId
     * @param ownerDisplayNameSnapshot ownerDisplayNameSnapshot
     * @param ownerOrganizationUnitId ownerOrganizationUnitId
     * @param ownerOrganizationUnitNameSnapshot ownerOrganizationUnitNameSnapshot
     * @param targetDate targetDate
     * @param completedAt completedAt
     * @param verificationRequired verificationRequired
     * @param verifiedByActorId verifiedByActorId
     * @param verifiedAt verifiedAt
     * @param status status
     * @param linkedWorkOrderId linkedWorkOrderId
     * @param workflowTaskId workflowTaskId
     * @param createdAt createdAt
     * @param updatedAt updatedAt
     */
    public record HseCorrectivePreventiveAction(
            String id,
        String hseCaseId,
        String actionNumber,
        String actionTypeId,
        String title,
        String description,
        String ownerActorId,
        String ownerDisplayNameSnapshot,
        String ownerOrganizationUnitId,
        String ownerOrganizationUnitNameSnapshot,
        Instant targetDate,
        Instant completedAt,
        boolean verificationRequired,
        String verifiedByActorId,
        Instant verifiedAt,
        CapaStatus status,
        String linkedWorkOrderId,
        String workflowTaskId,
        Instant createdAt,
        Instant updatedAt
    ) {

        public HseCorrectivePreventiveAction {
        id = normalize(id);
        hseCaseId = normalize(hseCaseId);
        actionNumber = normalize(actionNumber);
        actionTypeId = normalize(actionTypeId);
        title = normalize(title);
        description = normalize(description);
        ownerActorId = normalize(ownerActorId);
        ownerDisplayNameSnapshot = normalize(ownerDisplayNameSnapshot);
        ownerOrganizationUnitId = normalize(ownerOrganizationUnitId);
        ownerOrganizationUnitNameSnapshot = normalize(ownerOrganizationUnitNameSnapshot);
        verifiedByActorId = normalize(verifiedByActorId);
        linkedWorkOrderId = normalize(linkedWorkOrderId);
        workflowTaskId = normalize(workflowTaskId);
        }

        private static String normalize(String value) {
            if (value == null || value.isBlank()) {
                return null;
            }
            return value.trim();
        }
    }
