/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : PartyCatalogEntry
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-28
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

import dz.sh.hidra.modules.party.domain.exception.InvalidPartyValueException;
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
        // HRA-051 required: id
        if (id == null || id.isBlank()) {
            throw new InvalidPartyValueException("PartyCatalogEntry id must not be blank.");
        }
        // HRA-051 required: catalogCode
        if (catalogCode == null || catalogCode.isBlank()) {
            throw new InvalidPartyValueException("PartyCatalogEntry catalog code must not be blank.");
        }
        // HRA-051 required: entryCode
        if (entryCode == null || entryCode.isBlank()) {
            throw new InvalidPartyValueException("PartyCatalogEntry entry code must not be blank.");
        }
        // HRA-051 required: status
        if (status == null) {
            throw new InvalidPartyValueException("PartyCatalogEntry status must not be null.");
        }
        // HRA-051 self-reference: parentEntryId != id
        if (id != null && parentEntryId != null && parentEntryId.equals(id)) {
            throw new InvalidPartyValueException("PartyCatalogEntry parent entry id must not reference itself.");
        }

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
