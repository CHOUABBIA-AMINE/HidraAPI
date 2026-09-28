/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : AssetWarranty
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-28
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

import dz.sh.hidra.modules.assets.domain.exception.InvalidAssetsValueException;
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
        // HRA-051 required: id
        if (id == null || id.isBlank()) {
            throw new InvalidAssetsValueException("AssetWarranty id must not be blank.");
        }
        // HRA-051 required: maintainableAssetId
        if (maintainableAssetId == null || maintainableAssetId.isBlank()) {
            throw new InvalidAssetsValueException("AssetWarranty maintainable asset id must not be blank.");
        }
        // HRA-051 required: warrantyNumber
        if (warrantyNumber == null || warrantyNumber.isBlank()) {
            throw new InvalidAssetsValueException("AssetWarranty warranty number must not be blank.");
        }
        // HRA-051 required: validFrom
        if (validFrom == null) {
            throw new InvalidAssetsValueException("AssetWarranty valid from must not be null.");
        }
        // HRA-051 required: validTo
        if (validTo == null) {
            throw new InvalidAssetsValueException("AssetWarranty valid to must not be null.");
        }
        // HRA-051 required: status
        if (status == null) {
            throw new InvalidAssetsValueException("AssetWarranty status must not be null.");
        }
        // HRA-051 order: validFrom <= validTo
        if (validFrom != null && validTo != null && validTo.isBefore(validFrom)) {
            throw new InvalidAssetsValueException("AssetWarranty valid to must not be before valid from.");
        }

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
