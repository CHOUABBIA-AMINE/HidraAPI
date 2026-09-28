/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : SimulationInputDataset
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-28
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : simulation
 * @Package     : dz.sh.hidra.modules.simulation.domain.model
 *
 * @Description : Concrete dataset attached to a simulation input snapshot.
 *
 */
package dz.sh.hidra.modules.simulation.domain.model;

import dz.sh.hidra.modules.simulation.domain.exception.InvalidSimulationValueException;
import java.time.Instant;

    /**
     * Concrete dataset attached to a simulation input snapshot.
     *
         * @param id id
     * @param inputSnapshotId inputSnapshotId
     * @param datasetTypeId datasetTypeId
     * @param sourceModule sourceModule
     * @param sourceReference sourceReference
     * @param recordCount recordCount
     * @param checksum checksum
     * @param createdAt createdAt
     */
    public record SimulationInputDataset(
            String id,
        String inputSnapshotId,
        String datasetTypeId,
        String sourceModule,
        String sourceReference,
        Long recordCount,
        String checksum,
        Instant createdAt
    ) {

        public SimulationInputDataset {
        // HRA-051 required: id
        if (id == null || id.isBlank()) {
            throw new InvalidSimulationValueException("SimulationInputDataset id must not be blank.");
        }
        // HRA-051 required: inputSnapshotId
        if (inputSnapshotId == null || inputSnapshotId.isBlank()) {
            throw new InvalidSimulationValueException("SimulationInputDataset input snapshot id must not be blank.");
        }
        // HRA-051 required: datasetTypeId
        if (datasetTypeId == null || datasetTypeId.isBlank()) {
            throw new InvalidSimulationValueException("SimulationInputDataset dataset type id must not be blank.");
        }

        id = normalize(id);
        inputSnapshotId = normalize(inputSnapshotId);
        datasetTypeId = normalize(datasetTypeId);
        sourceModule = normalize(sourceModule);
        sourceReference = normalize(sourceReference);
        checksum = normalize(checksum);
        }

        private static String normalize(String value) {
            if (value == null || value.isBlank()) {
                return null;
            }
            return value.trim();
        }
    }
