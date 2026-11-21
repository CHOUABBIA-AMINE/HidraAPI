/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : TopologyPressureRestrictionAuthorizationContract
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-11-21
 *
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : identity
 * @Package     : dz.sh.hidra.modules.identity.application.contract.topology
 *
 * @Description : Provides scoped authorization for pressure restriction lifecycle operations.
 *
 */
package dz.sh.hidra.modules.identity.application.contract.topology;

import java.util.Optional;

public interface TopologyPressureRestrictionAuthorizationContract {
    enum Operation {
        SUBMIT("submit"), REVIEW("review"), PUBLISH("publish"), EVIDENCE_READ("evidence-read");
        private final String action;

        Operation(String action) { this.action = action; }

        public String permission() { return "topology:pressure-restriction:" + action; }
    }

    record Target(String company, String pipeline, String section) { }

    Optional<String> current(Target target, Operation operation);
    boolean reviewerEligible(Target target, String actor);
}
