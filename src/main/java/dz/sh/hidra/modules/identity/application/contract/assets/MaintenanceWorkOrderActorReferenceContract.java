/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : MaintenanceWorkOrderActorReferenceContract
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-08
 *
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : identity
 * @Package     : dz.sh.hidra.modules.identity.application.contract.assets
 *
 * @Description : Validates owner-controlled MaintenanceWorkOrder references and preserves historical evidence.
 *
 */
package dz.sh.hidra.modules.identity.application.contract.assets;

import java.time.Instant;
/** Real actor eligibility only; caller names and snapshots are not credentials. */
public interface MaintenanceWorkOrderActorReferenceContract { boolean eligible(String actorId, Instant at); }
