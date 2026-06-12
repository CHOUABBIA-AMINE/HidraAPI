/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : AssetWarranty
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : assets
 * @Package     : dz.sh.hidra.modules.assets.domain.model
 *
 * @Description : Warranty record.
 *
 */
package dz.sh.hidra.modules.assets.domain.model;

import dz.sh.hidra.modules.assets.domain.value.*;
import java.time.Instant;

    /**
     * Warranty record.
     *
         * @param id id
     * @param maintainableAssetId maintainableAssetId
     * @param warrantyNumber warrantyNumber
     * @param providerPartyId providerPartyId
     * @param providerNameSnapshot providerNameSnapshot
     * @param validFrom validFrom
     * @param validTo validTo
     * @param status status
     * @param termsSummary termsSummary
     * @param documentReferenceId documentReferenceId
     * @param createdAt createdAt
     * @param updatedAt updatedAt
     */
    public record AssetWarranty(
            String id,
        String maintainableAssetId,
        String warrantyNumber,
        String providerPartyId,
        String providerNameSnapshot,
        Instant validFrom,
        Instant validTo,
        WarrantyStatus status,
        String termsSummary,
        String documentReferenceId,
        Instant createdAt,
        Instant updatedAt
    ) {

        public AssetWarranty {
        id = normalize(id);
        maintainableAssetId = normalize(maintainableAssetId);
        warrantyNumber = normalize(warrantyNumber);
        providerPartyId = normalize(providerPartyId);
        providerNameSnapshot = normalize(providerNameSnapshot);
        termsSummary = normalize(termsSummary);
        documentReferenceId = normalize(documentReferenceId);
        }

        private static String normalize(String value) {
            if (value == null || value.isBlank()) {
                return null;
            }
            return value.trim();
        }
    }
