/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : SimulationEvidenceLink
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : simulation
 * @Package     : dz.sh.hidra.modules.simulation.domain.model
 *
 * @Description : Evidence link to documents, audit, workflow, topology, telemetry, planning, monitoring, or external files.
 *
 */
package dz.sh.hidra.modules.simulation.domain.model;

import dz.sh.hidra.modules.simulation.domain.value.*;
import java.time.Instant;

    /**
     * Evidence link to documents, audit, workflow, topology, telemetry, planning, monitoring, or external files.
     *
         * @param id id
     * @param ownerType ownerType
     * @param ownerId ownerId
     * @param evidenceType evidenceType
     * @param evidenceReference evidenceReference
     * @param label label
     * @param createdAt createdAt
     */
    public record SimulationEvidenceLink(
            String id,
        SimulationOwnerType ownerType,
        String ownerId,
        SimulationEvidenceType evidenceType,
        String evidenceReference,
        String label,
        Instant createdAt
    ) {

        public SimulationEvidenceLink {
        id = normalize(id);
        ownerId = normalize(ownerId);
        evidenceReference = normalize(evidenceReference);
        label = normalize(label);
        }

        private static String normalize(String value) {
            if (value == null || value.isBlank()) {
                return null;
            }
            return value.trim();
        }
    }
