/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : LeakDetectionMethodCatalog
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-28
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : leakdetection
 * @Package     : dz.sh.hidra.modules.leakdetection.domain.model
 *
 * @Description : Catalog of leak detection methods.
 *
 */
package dz.sh.hidra.modules.leakdetection.domain.model;

import dz.sh.hidra.modules.leakdetection.domain.exception.InvalidLeakDetectionValueException;
import dz.sh.hidra.modules.leakdetection.domain.value.*;
import java.time.Instant;

    /**
     * Catalog of leak detection methods.
     *
         * @param id id
     * @param code code
     * @param methodFamily methodFamily
     * @param description description
     * @param status status
     * @param systemDefined systemDefined
     * @param createdAt createdAt
     * @param updatedAt updatedAt
     */
    public record LeakDetectionMethodCatalog(
            String id,
        String code,
        String methodFamily,
        String description,
        LeakDetectionMethodStatus status,
        boolean systemDefined,
        Instant createdAt,
        Instant updatedAt
    ) {

        public LeakDetectionMethodCatalog {
        // HRA-051 required: id
        if (id == null || id.isBlank()) {
            throw new InvalidLeakDetectionValueException("LeakDetectionMethodCatalog id must not be blank.");
        }
        // HRA-051 required: code
        if (code == null || code.isBlank()) {
            throw new InvalidLeakDetectionValueException("LeakDetectionMethodCatalog code must not be blank.");
        }
        // HRA-051 required: status
        if (status == null) {
            throw new InvalidLeakDetectionValueException("LeakDetectionMethodCatalog status must not be null.");
        }

        id = normalize(id);
        code = normalize(code);
        methodFamily = normalize(methodFamily);
        description = normalize(description);
        }

        private static String normalize(String value) {
            if (value == null || value.isBlank()) {
                return null;
            }
            return value.trim();
        }
    }
