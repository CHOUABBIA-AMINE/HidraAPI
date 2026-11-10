/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : PlanningExplicitGrantEvidencePort
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-11-10
 *
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : identity
 * @Package     : dz.sh.hidra.modules.identity.application.port.out
 *
 * @Description : Locates explicit current grant candidates under the active transaction.
 *
 */
package dz.sh.hidra.modules.identity.application.port.out;

import dz.sh.hidra.modules.identity.domain.value.ScopeType;
import java.time.Instant;
import java.util.List;

public interface PlanningExplicitGrantEvidencePort {

    record GrantScope(ScopeType type, String reference) { }

    void fenceCurrentAuthorization();

    List<GrantScope> candidates(String actor, String permission, Instant at);
}
