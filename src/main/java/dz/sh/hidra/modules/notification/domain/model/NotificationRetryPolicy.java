/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : NotificationRetryPolicy
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : notification
 * @Package     : dz.sh.hidra.modules.notification.domain.model
 *
 * @Description : Retry behavior configuration.
 *
 */
package dz.sh.hidra.modules.notification.domain.model;

import dz.sh.hidra.modules.notification.domain.value.*;
import java.time.Instant;

    /**
     * Retry behavior configuration.
     *
         * @param id id
     * @param code code
     * @param nameAr nameAr
     * @param nameFr nameFr
     * @param nameEn nameEn
     * @param maxAttempts maxAttempts
     * @param initialDelaySeconds initialDelaySeconds
     * @param maxDelaySeconds maxDelaySeconds
     * @param backoffStrategy backoffStrategy
     * @param retryOnTemporaryFailure retryOnTemporaryFailure
     * @param active active
     * @param createdAt createdAt
     * @param updatedAt updatedAt
     */
    public record NotificationRetryPolicy(
            String id,
        String code,
        String nameAr,
        String nameFr,
        String nameEn,
        int maxAttempts,
        int initialDelaySeconds,
        int maxDelaySeconds,
        NotificationBackoffStrategy backoffStrategy,
        boolean retryOnTemporaryFailure,
        boolean active,
        Instant createdAt,
        Instant updatedAt
    ) {

        public NotificationRetryPolicy {
        id = normalize(id);
        code = normalize(code);
        nameAr = normalize(nameAr);
        nameFr = normalize(nameFr);
        nameEn = normalize(nameEn);
        }

        private static String normalize(String value) {
            if (value == null || value.isBlank()) {
                return null;
            }
            return value.trim();
        }
    }
