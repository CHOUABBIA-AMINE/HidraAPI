/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : LeakDetectionMethodTranslation
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : leakdetection
 * @Package     : dz.sh.hidra.modules.leakdetection.domain.model
 *
 * @Description : Multilingual method labels.
 *
 */
package dz.sh.hidra.modules.leakdetection.domain.model;

import java.time.Instant;

    /**
     * Multilingual method labels.
     *
         * @param id id
     * @param methodId methodId
     * @param locale locale
     * @param name name
     * @param description description
     * @param createdAt createdAt
     * @param updatedAt updatedAt
     */
    public record LeakDetectionMethodTranslation(
            String id,
        String methodId,
        String locale,
        String name,
        String description,
        Instant createdAt,
        Instant updatedAt
    ) {

        public LeakDetectionMethodTranslation {
        id = normalize(id);
        methodId = normalize(methodId);
        locale = normalize(locale);
        name = normalize(name);
        description = normalize(description);
        }

        private static String normalize(String value) {
            if (value == null || value.isBlank()) {
                return null;
            }
            return value.trim();
        }
    }
