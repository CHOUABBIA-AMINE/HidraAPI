/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : SimulationScenario
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
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
