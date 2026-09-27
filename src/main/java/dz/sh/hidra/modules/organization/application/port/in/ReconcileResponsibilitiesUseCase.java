/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : ReconcileResponsibilitiesUseCase
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-27
 *
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : organization
 * @Package     : dz.sh.hidra.modules.organization.application.port.in
 *
 * @Description : Read-only use case for responsibility integrity reconciliation.
 *
 */
package dz.sh.hidra.modules.organization.application.port.in;

import dz.sh.hidra.modules.organization.application.dto.ResponsibilityReconciliationResult;

/**
 * Rechecks persisted responsibility assignments against current Organization and
 * owner-resolution integrity rules without mutating historical data.
 */
public interface ReconcileResponsibilitiesUseCase {

    ResponsibilityReconciliationResult reconcileResponsibilities();
}
