/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : ExternalObjectReference
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-28
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : integration
 * @Package     : dz.sh.hidra.modules.integration.domain.model
 *
 * @Description : Mapping from external object identifier to Hidra target reference.
 *
 */
package dz.sh.hidra.modules.integration.domain.model;

import dz.sh.hidra.modules.integration.domain.exception.InvalidIntegrationValueException;
import dz.sh.hidra.modules.integration.domain.value.*;
import java.time.Instant;

    /**
     * Mapping from external object identifier to Hidra target reference.
     *
         * @param id id
     * @param externalSystemId externalSystemId
     * @param externalObjectType externalObjectType
     * @param externalObjectId externalObjectId
     * @param externalObjectCode externalObjectCode
     * @param targetModule targetModule
     * @param targetTypeCode targetTypeCode
     * @param targetId targetId
     * @param targetCodeSnapshot targetCodeSnapshot
     * @param targetLabelSnapshot targetLabelSnapshot
     * @param confidenceLevel confidenceLevel
     * @param status status
     * @param validFrom validFrom
     * @param validTo validTo
     * @param createdAt createdAt
     * @param updatedAt updatedAt
     */
    public record ExternalObjectReference(
            String id,
        String externalSystemId,
        String externalObjectType,
        String externalObjectId,
        String externalObjectCode,
        String targetModule,
        String targetTypeCode,
        String targetId,
        String targetCodeSnapshot,
        String targetLabelSnapshot,
        ExternalObjectConfidence confidenceLevel,
        ExternalObjectReferenceStatus status,
        Instant validFrom,
        Instant validTo,
        Instant createdAt,
        Instant updatedAt
    ) {

        public ExternalObjectReference {
        // HRA-051 required: id
        if (id == null || id.isBlank()) {
            throw new InvalidIntegrationValueException("ExternalObjectReference id must not be blank.");
        }
        // HRA-051 required: externalSystemId
        if (externalSystemId == null || externalSystemId.isBlank()) {
            throw new InvalidIntegrationValueException("ExternalObjectReference external system id must not be blank.");
        }
        // HRA-051 required: externalObjectId
        if (externalObjectId == null || externalObjectId.isBlank()) {
            throw new InvalidIntegrationValueException("ExternalObjectReference external object id must not be blank.");
        }
        // HRA-051 required: targetTypeCode
        if (targetTypeCode == null || targetTypeCode.isBlank()) {
            throw new InvalidIntegrationValueException("ExternalObjectReference target type code must not be blank.");
        }
        // HRA-051 required: targetId
        if (targetId == null || targetId.isBlank()) {
            throw new InvalidIntegrationValueException("ExternalObjectReference target id must not be blank.");
        }
        // HRA-051 required: confidenceLevel
        if (confidenceLevel == null) {
            throw new InvalidIntegrationValueException("ExternalObjectReference confidence level must not be null.");
        }
        // HRA-051 required: status
        if (status == null) {
            throw new InvalidIntegrationValueException("ExternalObjectReference status must not be null.");
        }
        // HRA-051 order: validFrom <= validTo
        if (validFrom != null && validTo != null && validTo.isBefore(validFrom)) {
            throw new InvalidIntegrationValueException("ExternalObjectReference valid to must not be before valid from.");
        }

        id = normalize(id);
        externalSystemId = normalize(externalSystemId);
        externalObjectType = normalize(externalObjectType);
        externalObjectId = normalize(externalObjectId);
        externalObjectCode = normalize(externalObjectCode);
        targetModule = normalize(targetModule);
        targetTypeCode = normalize(targetTypeCode);
        targetId = normalize(targetId);
        targetCodeSnapshot = normalize(targetCodeSnapshot);
        targetLabelSnapshot = normalize(targetLabelSnapshot);
        }

        private static String normalize(String value) {
            if (value == null || value.isBlank()) {
                return null;
            }
            return value.trim();
        }
    }
