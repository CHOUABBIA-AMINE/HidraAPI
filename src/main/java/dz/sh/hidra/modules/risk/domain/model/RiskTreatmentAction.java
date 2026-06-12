/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : RiskTreatmentAction
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : risk
 * @Package     : dz.sh.hidra.modules.risk.domain.model
 *
 * @Description : Concrete action in a treatment plan.
 *
 */
package dz.sh.hidra.modules.risk.domain.model;

import dz.sh.hidra.modules.risk.domain.value.*;
import java.time.Instant;

    /**
     * Concrete action in a treatment plan.
     *
         * @param id id
     * @param riskTreatmentPlanId riskTreatmentPlanId
     * @param actionCode actionCode
     * @param title title
     * @param description description
     * @param actionTypeId actionTypeId
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
     * @param linkedWorkflowTaskId linkedWorkflowTaskId
     * @param createdAt createdAt
     * @param updatedAt updatedAt
     */
    public record RiskTreatmentAction(
            String id,
        String riskTreatmentPlanId,
        String actionCode,
        String title,
        String description,
        String actionTypeId,
        String ownerActorId,
        String ownerDisplayNameSnapshot,
        String ownerOrganizationUnitId,
        String ownerOrganizationUnitNameSnapshot,
        Instant targetDate,
        Instant completedAt,
        boolean verificationRequired,
        String verifiedByActorId,
        Instant verifiedAt,
        RiskTreatmentStatus status,
        String linkedWorkOrderId,
        String linkedWorkflowTaskId,
        Instant createdAt,
        Instant updatedAt
    ) {

        public RiskTreatmentAction {
        id = normalize(id);
        riskTreatmentPlanId = normalize(riskTreatmentPlanId);
        actionCode = normalize(actionCode);
        title = normalize(title);
        description = normalize(description);
        actionTypeId = normalize(actionTypeId);
        ownerActorId = normalize(ownerActorId);
        ownerDisplayNameSnapshot = normalize(ownerDisplayNameSnapshot);
        ownerOrganizationUnitId = normalize(ownerOrganizationUnitId);
        ownerOrganizationUnitNameSnapshot = normalize(ownerOrganizationUnitNameSnapshot);
        verifiedByActorId = normalize(verifiedByActorId);
        linkedWorkOrderId = normalize(linkedWorkOrderId);
        linkedWorkflowTaskId = normalize(linkedWorkflowTaskId);
        }

        private static String normalize(String value) {
            if (value == null || value.isBlank()) {
                return null;
            }
            return value.trim();
        }
    }
