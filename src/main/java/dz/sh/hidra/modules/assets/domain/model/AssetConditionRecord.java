/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : AssetConditionRecord
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-28
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : assets
 * @Package     : dz.sh.hidra.modules.assets.domain.model
 *
 * @Description : Operational condition record.
 *
 */
package dz.sh.hidra.modules.assets.domain.model;

import dz.sh.hidra.modules.assets.domain.exception.InvalidAssetsValueException;
import dz.sh.hidra.modules.assets.domain.value.*;
import java.time.Instant;
import java.math.BigDecimal;

    /**
     * Operational condition record.
     *
         * @param id id
     * @param maintainableAssetId maintainableAssetId
     * @param conditionStatus conditionStatus
     * @param conditionTypeId conditionTypeId
     * @param sourceModule sourceModule
     * @param sourceReferenceId sourceReferenceId
     * @param summary summary
     * @param conditionScore conditionScore
     * @param observedAt observedAt
     * @param observedByActorId observedByActorId
     * @param createdAt createdAt
     */
    public record AssetConditionRecord(
            String id,
        String maintainableAssetId,
        AssetConditionStatus conditionStatus,
        String conditionTypeId,
        String sourceModule,
        String sourceReferenceId,
        String summary,
        BigDecimal conditionScore,
        Instant observedAt,
        String observedByActorId,
        Instant createdAt
    ) {

        public AssetConditionRecord {
        // HRA-051 required: id
        if (id == null || id.isBlank()) {
            throw new InvalidAssetsValueException("AssetConditionRecord id must not be blank.");
        }
        // HRA-051 required: maintainableAssetId
        if (maintainableAssetId == null || maintainableAssetId.isBlank()) {
            throw new InvalidAssetsValueException("AssetConditionRecord maintainable asset id must not be blank.");
        }
        // HRA-051 required: conditionStatus
        if (conditionStatus == null) {
            throw new InvalidAssetsValueException("AssetConditionRecord condition status must not be null.");
        }
        // HRA-051 required: observedAt
        if (observedAt == null) {
            throw new InvalidAssetsValueException("AssetConditionRecord observed at must not be null.");
        }

        id = normalize(id);
        maintainableAssetId = normalize(maintainableAssetId);
        conditionTypeId = normalize(conditionTypeId);
        sourceModule = normalize(sourceModule);
        sourceReferenceId = normalize(sourceReferenceId);
        summary = normalize(summary);
        observedByActorId = normalize(observedByActorId);
        }

        private static String normalize(String value) {
            if (value == null || value.isBlank()) {
                return null;
            }
            return value.trim();
        }
    }
