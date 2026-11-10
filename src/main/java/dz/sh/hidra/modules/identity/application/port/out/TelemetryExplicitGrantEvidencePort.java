/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : TelemetryExplicitGrantEvidencePort
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-11-10
 *
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : identity
 * @Package     : dz.sh.hidra.modules.identity.application.port.out
 *
 * @Description : Locks an exact current native Identity direct grant.
 *
 */
package dz.sh.hidra.modules.identity.application.port.out;


import dz.sh.hidra.modules.identity.domain.value.ScopeType;
import java.time.Instant;
public interface TelemetryExplicitGrantEvidencePort {
    boolean currentDirectPermit(String actor,String permissionCode,ScopeType scopeType,String scopeReference,Instant at);
}
