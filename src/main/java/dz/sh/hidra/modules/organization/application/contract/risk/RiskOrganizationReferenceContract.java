/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : RiskOrganizationReferenceContract
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-05
 *
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : organization
 * @Package     : dz.sh.hidra.modules.organization.application.contract.risk
 *
 * @Description : Organization-owned references exported to Risk.
 *
 */
package dz.sh.hidra.modules.organization.application.contract.risk;

import java.util.Optional;

public interface RiskOrganizationReferenceContract {

    Optional<ScopeView> resolveOrganizationUnit(String organizationUnitId);

    record ScopeView(String id, String code, String label) { }
}
