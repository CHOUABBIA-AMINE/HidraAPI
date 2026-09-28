/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : TelemetryCatalogTranslation
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-28
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : telemetry
 * @Package     : dz.sh.hidra.modules.telemetry.domain.model
 *
 * @Description : Localized telemetry catalog labels and descriptions.
 *
 */
package dz.sh.hidra.modules.telemetry.domain.model;

import dz.sh.hidra.modules.telemetry.domain.exception.InvalidTelemetryValueException;
import java.time.Instant;

    /**
     * Localized telemetry catalog labels and descriptions.
     *
         * @param id id
     * @param typeId typeId
     * @param locale locale
     * @param name name
     * @param description description
     * @param createdAt createdAt
     * @param updatedAt updatedAt
     */
    public record TelemetryCatalogTranslation(
            String id,
        String typeId,
        String locale,
        String name,
        String description,
        Instant createdAt,
        Instant updatedAt
    ) {

        public TelemetryCatalogTranslation {
        // HRA-051 required: id
        if (id == null || id.isBlank()) {
            throw new InvalidTelemetryValueException("TelemetryCatalogTranslation id must not be blank.");
        }
        // HRA-051 required: typeId
        if (typeId == null || typeId.isBlank()) {
            throw new InvalidTelemetryValueException("TelemetryCatalogTranslation type id must not be blank.");
        }
        // HRA-051 required: locale
        if (locale == null || locale.isBlank()) {
            throw new InvalidTelemetryValueException("TelemetryCatalogTranslation locale must not be blank.");
        }

        id = normalize(id);
        typeId = normalize(typeId);
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
