/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : PartyCatalogEntry
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : party
 * @Package     : dz.sh.hidra.modules.party.domain.model
 *
 * @Description : Party-owned catalog entry.
 *
 */
package dz.sh.hidra.modules.party.domain.model;

import dz.sh.hidra.modules.party.domain.value.*;
import java.time.Instant;

    /**
     * Party-owned catalog entry.
     *
         * @param id id
     * @param catalogCode catalogCode
     * @param entryCode entryCode
     * @param parentEntryId parentEntryId
     * @param status status
     * @param createdAt createdAt
     * @param updatedAt updatedAt
     */
    public record PartyCatalogEntry(
            String id,
        String catalogCode,
        String entryCode,
        String parentEntryId,
        PartyCatalogStatus status,
        Instant createdAt,
        Instant updatedAt
    ) {

        public PartyCatalogEntry {
        id = normalize(id);
        catalogCode = normalize(catalogCode);
        entryCode = normalize(entryCode);
        parentEntryId = normalize(parentEntryId);
        }

        private static String normalize(String value) {
            if (value == null || value.isBlank()) {
                return null;
            }
            return value.trim();
        }
    }
