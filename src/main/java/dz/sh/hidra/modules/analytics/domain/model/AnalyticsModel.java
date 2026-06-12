/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : AnalyticsModel
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : analytics
 * @Package     : dz.sh.hidra.modules.analytics.domain.model
 *
 * @Description : Analytical model definition.
 *
 */
package dz.sh.hidra.modules.analytics.domain.model;

import dz.sh.hidra.modules.analytics.domain.value.*;
import java.time.Instant;

    /**
     * Analytical model definition.
     *
         * @param id id
     * @param code code
     * @param nameAr nameAr
     * @param nameFr nameFr
     * @param nameEn nameEn
     * @param modelType modelType
     * @param subjectAreaId subjectAreaId
     * @param ownerModule ownerModule
     * @param status status
     * @param createdAt createdAt
     * @param updatedAt updatedAt
     */
    public record AnalyticsModel(
            String id,
        String code,
        String nameAr,
        String nameFr,
        String nameEn,
        String modelType,
        String subjectAreaId,
        String ownerModule,
        AnalyticsModelStatus status,
        Instant createdAt,
        Instant updatedAt
    ) {

        public AnalyticsModel {
        id = normalize(id);
        code = normalize(code);
        nameAr = normalize(nameAr);
        nameFr = normalize(nameFr);
        nameEn = normalize(nameEn);
        modelType = normalize(modelType);
        subjectAreaId = normalize(subjectAreaId);
        ownerModule = normalize(ownerModule);
        }

        private static String normalize(String value) {
            if (value == null || value.isBlank()) {
                return null;
            }
            return value.trim();
        }
    }
