/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : FeatureFlag
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-28
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : configuration
 * @Package     : dz.sh.hidra.modules.configuration.domain.model
 *
 * @Description : Governed feature flag.
 *
 */
package dz.sh.hidra.modules.configuration.domain.model;

import dz.sh.hidra.modules.configuration.domain.exception.InvalidConfigurationValueException;
import dz.sh.hidra.modules.configuration.domain.value.*;
import java.time.Instant;

    /**
     * Governed feature flag.
     *
         * @param id id
     * @param code code
     * @param nameFr nameFr
     * @param nameAr nameAr
     * @param nameEn nameEn
     * @param owningModule owningModule
     * @param status status
     * @param evaluationStrategy evaluationStrategy
     * @param defaultEnabled defaultEnabled
     * @param description description
     * @param createdAt createdAt
     * @param updatedAt updatedAt
     */
    public record FeatureFlag(
            String id,
        String code,
        String nameFr,
        String nameAr,
        String nameEn,
        String owningModule,
        FeatureFlagStatus status,
        FeatureFlagEvaluationStrategy evaluationStrategy,
        boolean defaultEnabled,
        String description,
        Instant createdAt,
        Instant updatedAt
    ) {

        public FeatureFlag {
        // HRA-051 required: id
        if (id == null || id.isBlank()) {
            throw new InvalidConfigurationValueException("FeatureFlag id must not be blank.");
        }
        // HRA-051 required: code
        if (code == null || code.isBlank()) {
            throw new InvalidConfigurationValueException("FeatureFlag code must not be blank.");
        }
        // HRA-051 required: status
        if (status == null) {
            throw new InvalidConfigurationValueException("FeatureFlag status must not be null.");
        }
        // HRA-051 required: evaluationStrategy
        if (evaluationStrategy == null) {
            throw new InvalidConfigurationValueException("FeatureFlag evaluation strategy must not be null.");
        }

        id = normalize(id);
        code = normalize(code);
        nameFr = normalize(nameFr);
        nameAr = normalize(nameAr);
        nameEn = normalize(nameEn);
        owningModule = normalize(owningModule);
        description = normalize(description);
        }
        public boolean canEvaluate() {
            return status == FeatureFlagStatus.ACTIVE;
        }
        private static String normalize(String value) {
            if (value == null || value.isBlank()) {
                return null;
            }
            return value.trim();
        }
    }
