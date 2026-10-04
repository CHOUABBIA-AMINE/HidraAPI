/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : SimulationScenario
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-04
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : simulation
 * @Package     : dz.sh.hidra.modules.simulation.domain.model
 *
 * @Description : What-if or optimization case before execution.
 *
 */
package dz.sh.hidra.modules.simulation.domain.model;

import dz.sh.hidra.modules.simulation.domain.exception.InvalidSimulationValueException;
import dz.sh.hidra.modules.simulation.domain.value.*;
import java.time.Instant;

    /**
     * What-if or optimization case before execution.
     *
         * @param id id
     * @param code code
     * @param nameAr nameAr
     * @param nameFr nameFr
     * @param nameEn nameEn
     * @param scenarioTypeId scenarioTypeId
     * @param modelId modelId
     * @param modelVersionId modelVersionId
     * @param topologySnapshotId topologySnapshotId
     * @param planningReferenceId planningReferenceId
     * @param monitoringContextId monitoringContextId
     * @param status status
     * @param createdByActorId createdByActorId
     * @param createdByDisplayNameSnapshot createdByDisplayNameSnapshot
     * @param createdAt createdAt
     * @param updatedAt updatedAt
     */
    public record SimulationScenario(
            String id,
        String code,
        String nameAr,
        String nameFr,
        String nameEn,
        String scenarioTypeId,
        String modelId,
        String modelVersionId,
        String topologySnapshotId,
        String planningReferenceId,
        String monitoringContextId,
        SimulationScenarioStatus status,
        String createdByActorId,
        String createdByDisplayNameSnapshot,
        Instant createdAt,
        Instant updatedAt
    ) {

        public SimulationScenario {
        // HRA-051 required: id
        if (id == null || id.isBlank()) {
            throw new InvalidSimulationValueException("SimulationScenario id must not be blank.");
        }
        // HRA-051 required: code
        if (code == null || code.isBlank()) {
            throw new InvalidSimulationValueException("SimulationScenario code must not be blank.");
        }
        // HMR-034 required: nameFr
        if (nameFr == null || nameFr.isBlank()) {
            throw new InvalidSimulationValueException("SimulationScenario French name must not be blank.");
        }
        // HRA-051 required: scenarioTypeId
        if (scenarioTypeId == null || scenarioTypeId.isBlank()) {
            throw new InvalidSimulationValueException("SimulationScenario scenario type id must not be blank.");
        }
        // HRA-051 required: modelId
        if (modelId == null || modelId.isBlank()) {
            throw new InvalidSimulationValueException("SimulationScenario model id must not be blank.");
        }
        // HRA-051 required: modelVersionId
        if (modelVersionId == null || modelVersionId.isBlank()) {
            throw new InvalidSimulationValueException("SimulationScenario model version id must not be blank.");
        }
        // HRA-051 required: topologySnapshotId
        if (topologySnapshotId == null || topologySnapshotId.isBlank()) {
            throw new InvalidSimulationValueException("SimulationScenario topology snapshot id must not be blank.");
        }
        // HRA-051 required: status
        if (status == null) {
            throw new InvalidSimulationValueException("SimulationScenario status must not be null.");
        }
        // HRA-051 required: createdByActorId
        if (createdByActorId == null || createdByActorId.isBlank()) {
            throw new InvalidSimulationValueException("SimulationScenario created by actor id must not be blank.");
        }
        // HMR-034 required: createdByDisplayNameSnapshot
        if (createdByDisplayNameSnapshot == null || createdByDisplayNameSnapshot.isBlank()) {
            throw new InvalidSimulationValueException(
                    "SimulationScenario created by display name snapshot must not be blank."
            );
        }

        id = normalize(id);
        code = normalize(code);
        nameAr = normalize(nameAr);
        nameFr = normalize(nameFr);
        nameEn = normalize(nameEn);
        scenarioTypeId = normalize(scenarioTypeId);
        modelId = normalize(modelId);
        modelVersionId = normalize(modelVersionId);
        topologySnapshotId = normalize(topologySnapshotId);
        planningReferenceId = normalize(planningReferenceId);
        monitoringContextId = normalize(monitoringContextId);
        createdByActorId = normalize(createdByActorId);
        createdByDisplayNameSnapshot = normalize(createdByDisplayNameSnapshot);
        }
        public boolean executable() {
            return status == SimulationScenarioStatus.LOCKED;
        }
        private static String normalize(String value) {
            if (value == null || value.isBlank()) {
                return null;
            }
            return value.trim();
        }
    }
