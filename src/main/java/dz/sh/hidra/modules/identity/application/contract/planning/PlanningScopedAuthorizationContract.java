/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : PlanningScopedAuthorizationContract
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-11-10
 *
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : identity
 * @Package     : dz.sh.hidra.modules.identity.application.contract.planning
 *
 * @Description : Current Identity grants for Planning operations.
 *
 */
package dz.sh.hidra.modules.identity.application.contract.planning;

import java.util.Set;

public interface PlanningScopedAuthorizationContract {

    enum Operation { PERIOD_READ, PERIOD_CREATE, PLAN_READ, PLAN_CREATE, PLAN_AUTHOR }

    record Asset(String type, String id) { }

    record Access(String actorId, Set<String> companies, Set<Asset> assets) {
        public Access {
            companies = Set.copyOf(companies);
            assets = Set.copyOf(assets);
        }
    }

    Access current(Operation operation);
}
