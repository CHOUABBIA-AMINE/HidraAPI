/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : AlarmCatalogEntry
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-28
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : alarm
 * @Package     : dz.sh.hidra.modules.alarm.domain.model
 *
 * @Description : Controlled vocabulary entry for alarm taxonomy.
 *
 */
package dz.sh.hidra.modules.alarm.domain.model;

import dz.sh.hidra.modules.alarm.domain.exception.InvalidAlarmValueException;
import java.time.Instant;

    /**
     * Controlled vocabulary entry for alarm taxonomy.
     *
         * @param id id
     * @param catalogName catalogName
     * @param code code
     * @param active active
     * @param sortOrder sortOrder
     * @param systemDefined systemDefined
     * @param severityRank severityRank
     * @param colorCode colorCode
     * @param createdAt createdAt
     * @param updatedAt updatedAt
     */
    public record AlarmCatalogEntry(
            String id,
        String catalogName,
        String code,
        boolean active,
        int sortOrder,
        boolean systemDefined,
        Integer severityRank,
        String colorCode,
        Instant createdAt,
        Instant updatedAt
    ) {

        public AlarmCatalogEntry {
        // HRA-051 required: id
        if (id == null || id.isBlank()) {
            throw new InvalidAlarmValueException("AlarmCatalogEntry id must not be blank.");
        }
        // HRA-051 required: code
        if (code == null || code.isBlank()) {
            throw new InvalidAlarmValueException("AlarmCatalogEntry code must not be blank.");
        }

        id = normalize(id);
        catalogName = normalize(catalogName);
        code = normalize(code);
        colorCode = normalize(colorCode);
        }

        private static String normalize(String value) {
            if (value == null || value.isBlank()) {
                return null;
            }
            return value.trim();
        }
    }
