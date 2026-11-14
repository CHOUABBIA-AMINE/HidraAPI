/**
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 * @Name        : LeakDetectionExplicitGrantEvidencePort
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-11-14
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : identity
 * @Package     : dz.sh.hidra.modules.identity.application.port.out
 * @Description : T13 exact owner contract and fail-closed source/asset authority.
 */
package dz.sh.hidra.modules.identity.application.port.out;

import dz.sh.hidra.modules.identity.domain.value.ScopeType;
import java.time.Instant;

public interface LeakDetectionExplicitGrantEvidencePort {
    boolean currentDirectPermit(String actorId, String permissionCode, ScopeType scopeType,
            String scopeReferenceId, Instant at);
}
