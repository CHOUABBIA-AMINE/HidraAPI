/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : SimulationModelVersion
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-28
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : simulation
 * @Package     : dz.sh.hidra.modules.simulation.domain.model
 *
 * @Description : Immutable version of a simulation model configuration.
 *
 */
package dz.sh.hidra.modules.simulation.domain.model;

import dz.sh.hidra.modules.simulation.domain.exception.InvalidSimulationValueException;
import dz.sh.hidra.modules.simulation.domain.value.*;
import java.time.Instant;

    /**
     * Immutable version of a simulation model configuration.
     *
         * @param id id
     * @param modelId modelId
     * @param versionNumber versionNumber
     * @param solverProfileId solverProfileId
     * @param modelDefinitionHash modelDefinitionHash
     * @param compatibleTopologyVersion compatibleTopologyVersion
     * @param status status
     * @param activatedAt activatedAt
     * @param retiredAt retiredAt
     * @param createdAt createdAt
     */
    public record SimulationModelVersion(
            String id,
        String modelId,
        int versionNumber,
        String solverProfileId,
        String modelDefinitionHash,
        String compatibleTopologyVersion,
        SimulationModelStatus status,
        Instant activatedAt,
        Instant retiredAt,
        Instant createdAt
    ) {

        public SimulationModelVersion {
        // HRA-051 required: id
        if (id == null || id.isBlank()) {
            throw new InvalidSimulationValueException("SimulationModelVersion id must not be blank.");
        }
        // HRA-051 required: modelId
        if (modelId == null || modelId.isBlank()) {
            throw new InvalidSimulationValueException("SimulationModelVersion model id must not be blank.");
        }
        // HRA-051 required: solverProfileId
        if (solverProfileId == null || solverProfileId.isBlank()) {
            throw new InvalidSimulationValueException("SimulationModelVersion solver profile id must not be blank.");
        }
        // HRA-051 required: modelDefinitionHash
        if (modelDefinitionHash == null || modelDefinitionHash.isBlank()) {
            throw new InvalidSimulationValueException("SimulationModelVersion model definition hash must not be blank.");
        }
        // HRA-051 required: status
        if (status == null) {
            throw new InvalidSimulationValueException("SimulationModelVersion status must not be null.");
        }

        id = normalize(id);
        modelId = normalize(modelId);
        solverProfileId = normalize(solverProfileId);
        modelDefinitionHash = normalize(modelDefinitionHash);
        compatibleTopologyVersion = normalize(compatibleTopologyVersion);
        }

        private static String normalize(String value) {
            if (value == null || value.isBlank()) {
                return null;
            }
            return value.trim();
        }
    }
