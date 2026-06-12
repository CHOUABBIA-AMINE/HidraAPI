/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : SimulationResultSeriesReference
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : simulation
 * @Package     : dz.sh.hidra.modules.simulation.domain.model
 *
 * @Description : Reference to external or large result series storage.
 *
 */
package dz.sh.hidra.modules.simulation.domain.model;

import java.time.Instant;

    /**
     * Reference to external or large result series storage.
     *
         * @param id id
     * @param runId runId
     * @param seriesTypeId seriesTypeId
     * @param targetType targetType
     * @param targetId targetId
     * @param storageLocation storageLocation
     * @param checksum checksum
     * @param createdAt createdAt
     */
    public record SimulationResultSeriesReference(
            String id,
        String runId,
        String seriesTypeId,
        String targetType,
        String targetId,
        String storageLocation,
        String checksum,
        Instant createdAt
    ) {

        public SimulationResultSeriesReference {
        id = normalize(id);
        runId = normalize(runId);
        seriesTypeId = normalize(seriesTypeId);
        targetType = normalize(targetType);
        targetId = normalize(targetId);
        storageLocation = normalize(storageLocation);
        checksum = normalize(checksum);
        }

        private static String normalize(String value) {
            if (value == null || value.isBlank()) {
                return null;
            }
            return value.trim();
        }
    }
