/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : EngineeringGraphStationWitnessPort
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-19
 *
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.application.port.out
 *
 * @Description : Requires current Topology-owned facility eligibility and monotonic generation witnesses inside protected draft transaction.
 *
 */
package dz.sh.hidra.modules.topology.application.port.out;

import dz.sh.hidra.modules.topology.domain.model.EngineeringGraphDraft;
import java.util.List;
import java.util.Objects;

public interface EngineeringGraphStationWitnessPort {
    record Witness(String facilityId, long ownerGeneration) {
        public Witness {
            if (facilityId == null || facilityId.isBlank() || ownerGeneration < 1)
                throw new IllegalArgumentException("Positive current facility witness required");
        }
    }

    /** Requires transaction-held facility and generation relation fences before any lookup. */
    List<Witness> requireCurrent(String companyPartyId, EngineeringGraphDraft graph);

    /** Must be evaluated within the one final materialized physical database guard. */
    boolean verifyCurrent(String companyPartyId, List<Witness> witnesses);
}
