/**
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 * @Name        : IncidentExplicitGrantEvidencePort
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-11-14
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : identity
 * @Package     : dz.sh.hidra.modules.identity.application.port.out
 * @Description : Direct current Incident permission evidence with PostgreSQL grant fence.
 */
package dz.sh.hidra.modules.identity.application.port.out;

import dz.sh.hidra.modules.identity.domain.value.ScopeType;
import java.time.Instant;

public interface IncidentExplicitGrantEvidencePort {
    boolean currentDirectPermit(String actor, String permission, ScopeType scopeType,
                               String reference, Instant at);
}
