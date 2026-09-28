/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : AssetType
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-28
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : assets
 * @Package     : dz.sh.hidra.modules.assets.domain.model
 *
 * @Description : Catalog of maintainable asset types.
 *
 */
package dz.sh.hidra.modules.assets.domain.model;

import dz.sh.hidra.modules.assets.domain.exception.InvalidAssetsValueException;
import java.time.Instant;

    /**
     * Catalog of maintainable asset types.
     *
         * @param id id
     * @param code code
     * @param parentTypeId parentTypeId
     * @param active active
     * @param sortOrder sortOrder
     * @param systemDefined systemDefined
     * @param createdAt createdAt
     * @param updatedAt updatedAt
     */
    public record AssetType(
            String id,
        String code,
        String parentTypeId,
        boolean active,
        int sortOrder,
        boolean systemDefined,
        Instant createdAt,
        Instant updatedAt
    ) {

        public AssetType {
        // HRA-051 required: id
        if (id == null || id.isBlank()) {
            throw new InvalidAssetsValueException("AssetType id must not be blank.");
        }
        // HRA-051 required: code
        if (code == null || code.isBlank()) {
            throw new InvalidAssetsValueException("AssetType code must not be blank.");
        }
        // HRA-051 self-reference: parentTypeId != id
        if (id != null && parentTypeId != null && parentTypeId.equals(id)) {
            throw new InvalidAssetsValueException("AssetType parent type id must not reference itself.");
        }

        id = normalize(id);
        code = normalize(code);
        parentTypeId = normalize(parentTypeId);
        }

        private static String normalize(String value) {
            if (value == null || value.isBlank()) {
                return null;
            }
            return value.trim();
        }
    }
