/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : CustodyMeteringSystem
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-28
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : custody
 * @Package     : dz.sh.hidra.modules.custody.domain.model
 *
 * @Description : Metering system used for custody.
 *
 */
package dz.sh.hidra.modules.custody.domain.model;

import dz.sh.hidra.modules.custody.domain.exception.InvalidCustodyValueException;
import java.time.Instant;

    /**
     * Metering system used for custody.
     *
         * @param id id
     * @param meteringSystemCode meteringSystemCode
     * @param name name
     * @param transferPointId transferPointId
     * @param topologyAssetTypeCode topologyAssetTypeCode
     * @param topologyAssetId topologyAssetId
     * @param measurementStandardId measurementStandardId
     * @param calibrationCertificateId calibrationCertificateId
     * @param active active
     * @param effectiveFrom effectiveFrom
     * @param effectiveTo effectiveTo
     * @param createdAt createdAt
     * @param updatedAt updatedAt
     */
    public record CustodyMeteringSystem(
            String id,
        String meteringSystemCode,
        String name,
        String transferPointId,
        String topologyAssetTypeCode,
        String topologyAssetId,
        String measurementStandardId,
        String calibrationCertificateId,
        boolean active,
        Instant effectiveFrom,
        Instant effectiveTo,
        Instant createdAt,
        Instant updatedAt
    ) {

        public CustodyMeteringSystem {
        // HRA-051 required: id
        if (id == null || id.isBlank()) {
            throw new InvalidCustodyValueException("CustodyMeteringSystem id must not be blank.");
        }
        // HRA-051 required: meteringSystemCode
        if (meteringSystemCode == null || meteringSystemCode.isBlank()) {
            throw new InvalidCustodyValueException("CustodyMeteringSystem metering system code must not be blank.");
        }
        // HRA-051 required: transferPointId
        if (transferPointId == null || transferPointId.isBlank()) {
            throw new InvalidCustodyValueException("CustodyMeteringSystem transfer point id must not be blank.");
        }
        // HRA-051 order: effectiveFrom <= effectiveTo
        if (effectiveFrom != null && effectiveTo != null && effectiveTo.isBefore(effectiveFrom)) {
            throw new InvalidCustodyValueException("CustodyMeteringSystem effective to must not be before effective from.");
        }

        id = normalize(id);
        meteringSystemCode = normalize(meteringSystemCode);
        name = normalize(name);
        transferPointId = normalize(transferPointId);
        topologyAssetTypeCode = normalize(topologyAssetTypeCode);
        topologyAssetId = normalize(topologyAssetId);
        measurementStandardId = normalize(measurementStandardId);
        calibrationCertificateId = normalize(calibrationCertificateId);
        }

        private static String normalize(String value) {
            if (value == null || value.isBlank()) {
                return null;
            }
            return value.trim();
        }
    }
