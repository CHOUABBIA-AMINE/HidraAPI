/**
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Name        : AlarmExplicitGrantEvidencePort
 * @UpdatedOn   : 2026-11-08
 * @Type        : Interface
 * @Layer       : Interface
 * @Module      : identity
 * @Package     : dz.sh.hidra.modules.identity.application.port.out
 * @Description : Requires a direct active permission grant on one exact scope.
 */
package dz.sh.hidra.modules.identity.application.port.out;

import dz.sh.hidra.modules.identity.domain.value.ScopeType;
import java.time.Instant;
public interface AlarmExplicitGrantEvidencePort {
    boolean currentDirectPermit(String userId,String permissionCode,ScopeType type,String reference,Instant at);
}
