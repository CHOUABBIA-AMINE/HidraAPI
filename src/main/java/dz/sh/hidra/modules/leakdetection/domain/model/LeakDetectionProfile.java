/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : LeakDetectionProfile
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
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
