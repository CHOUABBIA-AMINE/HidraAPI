/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : AnalyticsSubjectArea
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : analytics
 * @Package     : dz.sh.hidra.modules.analytics.domain.model
 *
 * @Description : High-level analytical domain.
 *
 */
package dz.sh.hidra.modules.analytics.domain.model;

import java.time.Instant;

    /**
     * High-level analytical domain.
     *
         * @param id id
     * @param code code
     * @param nameAr nameAr
     * @param nameFr nameFr
     * @param nameEn nameEn
     * @param description description
     * @param ownerModule ownerModule
     * @param active active
     * @param createdAt createdAt
     * @param updatedAt updatedAt
     */
    public record AnalyticsSubjectArea(
            String id,
        String code,
        String nameAr,
        String nameFr,
        String nameEn,
        String description,
        String ownerModule,
        boolean active,
        Instant createdAt,
        Instant updatedAt
    ) {

        public AnalyticsSubjectArea {
        id = normalize(id);
        code = normalize(code);
        nameAr = normalize(nameAr);
        nameFr = normalize(nameFr);
        nameEn = normalize(nameEn);
        description = normalize(description);
        ownerModule = normalize(ownerModule);
        }

        private static String normalize(String value) {
            if (value == null || value.isBlank()) {
                return null;
            }
            return value.trim();
        }
    }
