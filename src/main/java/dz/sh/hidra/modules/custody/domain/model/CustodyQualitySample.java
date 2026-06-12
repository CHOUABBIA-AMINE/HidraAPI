/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : CustodyQualitySample
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : custody
 * @Package     : dz.sh.hidra.modules.custody.domain.model
 *
 * @Description : Quality sample.
 *
 */
package dz.sh.hidra.modules.custody.domain.model;

import java.time.Instant;

    /**
     * Quality sample.
     *
         * @param id id
     * @param sampleNumber sampleNumber
     * @param measurementPeriodId measurementPeriodId
     * @param batchId batchId
     * @param sampleTypeId sampleTypeId
     * @param productTypeId productTypeId
     * @param sampledAt sampledAt
     * @param sampledByActorId sampledByActorId
     * @param laboratoryPartyId laboratoryPartyId
     * @param laboratoryNameSnapshot laboratoryNameSnapshot
     * @param resultSummary resultSummary
     * @param certificateId certificateId
     * @param createdAt createdAt
     */
    public record CustodyQualitySample(
            String id,
        String sampleNumber,
        String measurementPeriodId,
        String batchId,
        String sampleTypeId,
        String productTypeId,
        Instant sampledAt,
        String sampledByActorId,
        String laboratoryPartyId,
        String laboratoryNameSnapshot,
        String resultSummary,
        String certificateId,
        Instant createdAt
    ) {

        public CustodyQualitySample {
        id = normalize(id);
        sampleNumber = normalize(sampleNumber);
        measurementPeriodId = normalize(measurementPeriodId);
        batchId = normalize(batchId);
        sampleTypeId = normalize(sampleTypeId);
        productTypeId = normalize(productTypeId);
        sampledByActorId = normalize(sampledByActorId);
        laboratoryPartyId = normalize(laboratoryPartyId);
        laboratoryNameSnapshot = normalize(laboratoryNameSnapshot);
        resultSummary = normalize(resultSummary);
        certificateId = normalize(certificateId);
        }

        private static String normalize(String value) {
            if (value == null || value.isBlank()) {
                return null;
            }
            return value.trim();
        }
    }
