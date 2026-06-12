/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : Incident
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : incident
 * @Package     : dz.sh.hidra.modules.incident.domain.model
 *
 * @Description : Main incident aggregate root and lifecycle owner.
 *
 */
package dz.sh.hidra.modules.incident.domain.model;

import dz.sh.hidra.modules.incident.domain.value.*;
import java.time.Instant;
import java.math.BigDecimal;

    /**
     * Main incident aggregate root and lifecycle owner.
     *
         * @param id id
     * @param incidentNumber incidentNumber
     * @param title title
     * @param description description
     * @param classificationId classificationId
     * @param severityId severityId
     * @param priorityId priorityId
     * @param status status
     * @param sourceType sourceType
     * @param sourceReferenceId sourceReferenceId
     * @param sourceReferenceCode sourceReferenceCode
     * @param detectedAt detectedAt
     * @param reportedAt reportedAt
     * @param occurredAt occurredAt
     * @param topologyAssetTypeCode topologyAssetTypeCode
     * @param topologyAssetId topologyAssetId
     * @param topologyAssetCode topologyAssetCode
     * @param topologyAssetNameSnapshot topologyAssetNameSnapshot
     * @param locationDescriptionAr locationDescriptionAr
     * @param locationDescriptionLt locationDescriptionLt
     * @param latitude latitude
     * @param longitude longitude
     * @param responsibleOrganizationUnitId responsibleOrganizationUnitId
     * @param responsibleOrganizationUnitCode responsibleOrganizationUnitCode
     * @param responsibleOrganizationUnitNameSnapshot responsibleOrganizationUnitNameSnapshot
     * @param responsibleActorId responsibleActorId
     * @param responsibleActorNameSnapshot responsibleActorNameSnapshot
     * @param workflowInstanceId workflowInstanceId
     * @param currentEscalationLevel currentEscalationLevel
     * @param containedAt containedAt
     * @param resolvedAt resolvedAt
     * @param closedAt closedAt
     * @param cancelledAt cancelledAt
     * @param createdByActorId createdByActorId
     * @param createdByActorNameSnapshot createdByActorNameSnapshot
     * @param createdAt createdAt
     * @param updatedAt updatedAt
     */
    public record Incident(
            String id,
        String incidentNumber,
        String title,
        String description,
        String classificationId,
        String severityId,
        String priorityId,
        IncidentStatus status,
        IncidentSourceType sourceType,
        String sourceReferenceId,
        String sourceReferenceCode,
        Instant detectedAt,
        Instant reportedAt,
        Instant occurredAt,
        String topologyAssetTypeCode,
        String topologyAssetId,
        String topologyAssetCode,
        String topologyAssetNameSnapshot,
        String locationDescriptionAr,
        String locationDescriptionLt,
        BigDecimal latitude,
        BigDecimal longitude,
        String responsibleOrganizationUnitId,
        String responsibleOrganizationUnitCode,
        String responsibleOrganizationUnitNameSnapshot,
        String responsibleActorId,
        String responsibleActorNameSnapshot,
        String workflowInstanceId,
        int currentEscalationLevel,
        Instant containedAt,
        Instant resolvedAt,
        Instant closedAt,
        Instant cancelledAt,
        String createdByActorId,
        String createdByActorNameSnapshot,
        Instant createdAt,
        Instant updatedAt
    ) {

        public Incident {
        id = normalize(id);
        incidentNumber = normalize(incidentNumber);
        title = normalize(title);
        description = normalize(description);
        classificationId = normalize(classificationId);
        severityId = normalize(severityId);
        priorityId = normalize(priorityId);
        sourceReferenceId = normalize(sourceReferenceId);
        sourceReferenceCode = normalize(sourceReferenceCode);
        topologyAssetTypeCode = normalize(topologyAssetTypeCode);
        topologyAssetId = normalize(topologyAssetId);
        topologyAssetCode = normalize(topologyAssetCode);
        topologyAssetNameSnapshot = normalize(topologyAssetNameSnapshot);
        locationDescriptionAr = normalize(locationDescriptionAr);
        locationDescriptionLt = normalize(locationDescriptionLt);
        responsibleOrganizationUnitId = normalize(responsibleOrganizationUnitId);
        responsibleOrganizationUnitCode = normalize(responsibleOrganizationUnitCode);
        responsibleOrganizationUnitNameSnapshot = normalize(responsibleOrganizationUnitNameSnapshot);
        responsibleActorId = normalize(responsibleActorId);
        responsibleActorNameSnapshot = normalize(responsibleActorNameSnapshot);
        workflowInstanceId = normalize(workflowInstanceId);
        createdByActorId = normalize(createdByActorId);
        createdByActorNameSnapshot = normalize(createdByActorNameSnapshot);
        }
        public boolean closedLifecycle() {
            return status == IncidentStatus.CLOSED
                    || status == IncidentStatus.CANCELLED
                    || status == IncidentStatus.MERGED;
        }

        public boolean canReceiveResponseAction() {
            return status != IncidentStatus.DRAFT
                    && !closedLifecycle();
        }
        private static String normalize(String value) {
            if (value == null || value.isBlank()) {
                return null;
            }
            return value.trim();
        }
    }
