/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : CustodyMeteringSystem
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
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
