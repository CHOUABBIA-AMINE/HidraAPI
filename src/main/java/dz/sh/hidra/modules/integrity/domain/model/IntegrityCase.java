/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : IntegrityCase
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : integrity
 * @Package     : dz.sh.hidra.modules.integrity.domain.model
 *
 * @Description : Integrity lifecycle case.
 *
 */
package dz.sh.hidra.modules.integrity.domain.model;

import dz.sh.hidra.modules.integrity.domain.value.*;
import java.time.Instant;

    /**
     * Integrity lifecycle case.
     *
         * @param id id
     * @param caseNumber caseNumber
     * @param title title
     * @param description description
     * @param caseTypeId caseTypeId
     * @param status status
     * @param severityId severityId
     * @param topologyAssetTypeCode topologyAssetTypeCode
     * @param topologyAssetId topologyAssetId
     * @param topologyAssetCodeSnapshot topologyAssetCodeSnapshot
     * @param primaryDefectId primaryDefectId
     * @param sourceIncidentId sourceIncidentId
     * @param sourceHseCaseId sourceHseCaseId
     * @param responsibleOrganizationUnitId responsibleOrganizationUnitId
     * @param workflowInstanceId workflowInstanceId
     * @param openedAt openedAt
     * @param closedAt closedAt
     * @param openedByActorId openedByActorId
     * @param createdAt createdAt
     * @param updatedAt updatedAt
     */
    public record IntegrityCase(
            String id,
        String caseNumber,
        String title,
        String description,
        String caseTypeId,
        IntegrityCaseStatus status,
        String severityId,
        String topologyAssetTypeCode,
        String topologyAssetId,
        String topologyAssetCodeSnapshot,
        String primaryDefectId,
        String sourceIncidentId,
        String sourceHseCaseId,
        String responsibleOrganizationUnitId,
        String workflowInstanceId,
        Instant openedAt,
        Instant closedAt,
        String openedByActorId,
        Instant createdAt,
        Instant updatedAt
    ) {

        public IntegrityCase {
        id = normalize(id);
        caseNumber = normalize(caseNumber);
        title = normalize(title);
        description = normalize(description);
        caseTypeId = normalize(caseTypeId);
        severityId = normalize(severityId);
        topologyAssetTypeCode = normalize(topologyAssetTypeCode);
        topologyAssetId = normalize(topologyAssetId);
        topologyAssetCodeSnapshot = normalize(topologyAssetCodeSnapshot);
        primaryDefectId = normalize(primaryDefectId);
        sourceIncidentId = normalize(sourceIncidentId);
        sourceHseCaseId = normalize(sourceHseCaseId);
        responsibleOrganizationUnitId = normalize(responsibleOrganizationUnitId);
        workflowInstanceId = normalize(workflowInstanceId);
        openedByActorId = normalize(openedByActorId);
        }
        public boolean closedLifecycle() {
            return status == IntegrityCaseStatus.CLOSED
                    || status == IntegrityCaseStatus.CANCELLED;
        }
        private static String normalize(String value) {
            if (value == null || value.isBlank()) {
                return null;
            }
            return value.trim();
        }
    }
