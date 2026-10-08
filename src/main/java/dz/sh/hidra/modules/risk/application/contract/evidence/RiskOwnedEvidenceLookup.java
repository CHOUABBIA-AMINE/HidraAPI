/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : RiskOwnedEvidenceLookup
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-08
 *
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : risk
 * @Package     : dz.sh.hidra.modules.risk.application.contract.evidence
 *
 * @Description : Enforces governed Risk owner boundaries.
 *
 */
package dz.sh.hidra.modules.risk.application.contract.evidence;

import java.time.Instant;
import java.util.Optional;
import java.util.Set;

/** Owner-local, historical evidence resolution. No source state is transferred to Risk. */
public interface RiskOwnedEvidenceLookup {
    record Evidence(String id, String code, String label, Instant timestamp, String hash) {}
    String module();
    Set<String> evidenceTypes();
    Optional<Evidence> resolve(String type, String id);
}
