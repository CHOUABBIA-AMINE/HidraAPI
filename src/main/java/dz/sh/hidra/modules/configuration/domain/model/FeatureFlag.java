/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : FeatureFlag
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
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
