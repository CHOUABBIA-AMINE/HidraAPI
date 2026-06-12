/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : ConfigurationExternalReference
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : configuration
 * @Package     : dz.sh.hidra.modules.configuration.domain.model
 *
 * @Description : Neutral external reference for configuration governance.
 *
 */
package dz.sh.hidra.modules.configuration.domain.model;

import java.time.Instant;

    /**
     * Neutral external reference for configuration governance.
     *
         * @param id id
     * @param targetType targetType
     * @param targetId targetId
     * @param targetCodeSnapshot targetCodeSnapshot
     * @param targetLabelSnapshot targetLabelSnapshot
     * @param referenceModule referenceModule
     * @param referenceType referenceType
     * @param referenceId referenceId
     * @param referenceCodeSnapshot referenceCodeSnapshot
     * @param referenceLabelSnapshot referenceLabelSnapshot
     * @param active active
     * @param createdAt createdAt
     * @param updatedAt updatedAt
     */
    public record ConfigurationExternalReference(
            String id,
        String targetType,
        String targetId,
        String targetCodeSnapshot,
        String targetLabelSnapshot,
        String referenceModule,
        String referenceType,
        String referenceId,
        String referenceCodeSnapshot,
        String referenceLabelSnapshot,
        boolean active,
        Instant createdAt,
        Instant updatedAt
    ) {

        public ConfigurationExternalReference {
        id = normalize(id);
        targetType = normalize(targetType);
        targetId = normalize(targetId);
        targetCodeSnapshot = normalize(targetCodeSnapshot);
        targetLabelSnapshot = normalize(targetLabelSnapshot);
        referenceModule = normalize(referenceModule);
        referenceType = normalize(referenceType);
        referenceId = normalize(referenceId);
        referenceCodeSnapshot = normalize(referenceCodeSnapshot);
        referenceLabelSnapshot = normalize(referenceLabelSnapshot);
        }

        private static String normalize(String value) {
            if (value == null || value.isBlank()) {
                return null;
            }
            return value.trim();
        }
    }
