/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : LeakDetectionProfile
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-28
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : leakdetection
 * @Package     : dz.sh.hidra.modules.leakdetection.domain.model
 *
 * @Description : Detection configuration for a topology scope.
 *
 */
package dz.sh.hidra.modules.leakdetection.domain.model;

import dz.sh.hidra.modules.leakdetection.domain.exception.InvalidLeakDetectionValueException;
import dz.sh.hidra.modules.leakdetection.domain.value.*;
import java.time.Instant;

    /**
     * Detection configuration for a topology scope.
     *
         * @param id id
     * @param code code
     * @param nameAr nameAr
     * @param nameFr nameFr
     * @param nameEn nameEn
     * @param topologyAssetType topologyAssetType
     * @param topologyAssetId topologyAssetId
     * @param topologyAssetCode topologyAssetCode
     * @param topologyAssetNameSnapshot topologyAssetNameSnapshot
     * @param methodId methodId
     * @param configurationJson configurationJson
     * @param status status
     * @param createdByActorId createdByActorId
     * @param createdAt createdAt
     * @param updatedAt updatedAt
     */
    public record LeakDetectionProfile(
            String id,
        String code,
        String nameAr,
        String nameFr,
        String nameEn,
        String topologyAssetType,
        String topologyAssetId,
        String topologyAssetCode,
        String topologyAssetNameSnapshot,
        String methodId,
        String configurationJson,
        LeakDetectionProfileStatus status,
        String createdByActorId,
        Instant createdAt,
        Instant updatedAt
    ) {

        public LeakDetectionProfile {
        // HRA-051 required: id
        if (id == null || id.isBlank()) {
            throw new InvalidLeakDetectionValueException("LeakDetectionProfile id must not be blank.");
        }
        // HRA-051 required: code
        if (code == null || code.isBlank()) {
            throw new InvalidLeakDetectionValueException("LeakDetectionProfile code must not be blank.");
        }
        // HRA-051 required: topologyAssetId
        if (topologyAssetId == null || topologyAssetId.isBlank()) {
            throw new InvalidLeakDetectionValueException("LeakDetectionProfile topology asset id must not be blank.");
        }
        // HRA-051 required: topologyAssetCode
        if (topologyAssetCode == null || topologyAssetCode.isBlank()) {
            throw new InvalidLeakDetectionValueException("LeakDetectionProfile topology asset code must not be blank.");
        }
        // HRA-051 required: methodId
        if (methodId == null || methodId.isBlank()) {
            throw new InvalidLeakDetectionValueException("LeakDetectionProfile method id must not be blank.");
        }
        // HRA-051 required: status
        if (status == null) {
            throw new InvalidLeakDetectionValueException("LeakDetectionProfile status must not be null.");
        }

        id = normalize(id);
        code = normalize(code);
        nameAr = normalize(nameAr);
        nameFr = normalize(nameFr);
        nameEn = normalize(nameEn);
        topologyAssetType = normalize(topologyAssetType);
        topologyAssetId = normalize(topologyAssetId);
        topologyAssetCode = normalize(topologyAssetCode);
        topologyAssetNameSnapshot = normalize(topologyAssetNameSnapshot);
        methodId = normalize(methodId);
        configurationJson = normalize(configurationJson);
        createdByActorId = normalize(createdByActorId);
        }

        private static String normalize(String value) {
            if (value == null || value.isBlank()) {
                return null;
            }
            return value.trim();
        }
    }
