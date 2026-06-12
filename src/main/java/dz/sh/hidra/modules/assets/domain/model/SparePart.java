/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : SparePart
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : assets
 * @Package     : dz.sh.hidra.modules.assets.domain.model
 *
 * @Description : Spare part catalogue.
 *
 */
package dz.sh.hidra.modules.assets.domain.model;

import java.time.Instant;

    /**
     * Spare part catalogue.
     *
         * @param id id
     * @param partNumber partNumber
     * @param name name
     * @param description description
     * @param manufacturerPartyId manufacturerPartyId
     * @param manufacturerNameSnapshot manufacturerNameSnapshot
     * @param unitId unitId
     * @param categoryId categoryId
     * @param active active
     * @param createdAt createdAt
     * @param updatedAt updatedAt
     */
    public record SparePart(
            String id,
        String partNumber,
        String name,
        String description,
        String manufacturerPartyId,
        String manufacturerNameSnapshot,
        String unitId,
        String categoryId,
        boolean active,
        Instant createdAt,
        Instant updatedAt
    ) {

        public SparePart {
        id = normalize(id);
        partNumber = normalize(partNumber);
        name = normalize(name);
        description = normalize(description);
        manufacturerPartyId = normalize(manufacturerPartyId);
        manufacturerNameSnapshot = normalize(manufacturerNameSnapshot);
        unitId = normalize(unitId);
        categoryId = normalize(categoryId);
        }

        private static String normalize(String value) {
            if (value == null || value.isBlank()) {
                return null;
            }
            return value.trim();
        }
    }
