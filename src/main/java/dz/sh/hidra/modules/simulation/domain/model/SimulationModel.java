/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : SimulationModel
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-28
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : simulation
 * @Package     : dz.sh.hidra.modules.simulation.domain.model
 *
 * @Description : Reusable simulation model definition.
 *
 */
package dz.sh.hidra.modules.simulation.domain.model;

import dz.sh.hidra.modules.simulation.domain.exception.InvalidSimulationValueException;
import dz.sh.hidra.modules.simulation.domain.value.*;
import java.time.Instant;

    /**
     * Reusable simulation model definition.
     *
         * @param id id
     * @param code code
     * @param nameAr nameAr
     * @param nameFr nameFr
     * @param nameEn nameEn
     * @param modelTypeId modelTypeId
     * @param topologyScopeType topologyScopeType
     * @param topologyScopeId topologyScopeId
     * @param status status
     * @param description description
     * @param createdAt createdAt
     * @param updatedAt updatedAt
     */
    public record SimulationModel(
            String id,
        String code,
        String nameAr,
        String nameFr,
        String nameEn,
        String modelTypeId,
        String topologyScopeType,
        String topologyScopeId,
        SimulationModelStatus status,
        String description,
        Instant createdAt,
        Instant updatedAt
    ) {

        public SimulationModel {
        // HRA-051 required: id
        if (id == null || id.isBlank()) {
            throw new InvalidSimulationValueException("SimulationModel id must not be blank.");
        }
        // HRA-051 required: code
        if (code == null || code.isBlank()) {
            throw new InvalidSimulationValueException("SimulationModel code must not be blank.");
        }
        // HMR-009 required: nameFr
        if (nameFr == null || nameFr.isBlank()) {
            throw new InvalidSimulationValueException("SimulationModel French name must not be blank.");
        }
        // HRA-051 required: modelTypeId
        if (modelTypeId == null || modelTypeId.isBlank()) {
            throw new InvalidSimulationValueException("SimulationModel model type id must not be blank.");
        }
        if (topologyScopeType == null || topologyScopeType.isBlank()) {
            throw new InvalidSimulationValueException("SimulationModel topology scope type must not be blank.");
        }
        String normalizedScopeType = topologyScopeType.trim();
        if (!java.util.Set.of(
                "PIPELINE_SYSTEM",
                "PIPELINE",
                "SEGMENT_GROUP",
                "FACILITY_NETWORK"
        ).contains(normalizedScopeType)) {
            throw new InvalidSimulationValueException(
                    "Unsupported SimulationModel topology scope type: " + topologyScopeType
            );
        }
        if (createdAt == null || updatedAt == null) {
            throw new InvalidSimulationValueException("SimulationModel createdAt and updatedAt must not be null.");
        }
        // HRA-051 required: status
        if (status == null) {
            throw new InvalidSimulationValueException("SimulationModel status must not be null.");
        }

        id = normalize(id);
        code = normalize(code);
        nameAr = normalize(nameAr);
        nameFr = normalize(nameFr);
        nameEn = normalize(nameEn);
        modelTypeId = normalize(modelTypeId);
        topologyScopeType = normalizedScopeType;
        topologyScopeId = normalize(topologyScopeId);
        description = normalize(description);
        }

        private static String normalize(String value) {
            if (value == null || value.isBlank()) {
                return null;
            }
            return value.trim();
        }
    }
