/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : HseOrganizationReferenceContract
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-08
 *
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : organization
 * @Package     : dz.sh.hidra.modules.organization.application.contract.hse
 *
 * @Description : Enforces HSE-owned lifecycle integrity through owner-controlled references.
 *
 */
package dz.sh.hidra.modules.organization.application.contract.hse;

import java.util.Optional;
public interface HseOrganizationReferenceContract {
    record Unit(String id,String code,String label) {}
    Optional<Unit> resolve(String id);
}
