/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : AssetsOrganizationUnitReferenceContract
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-05
 *
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : organization
 * @Package     : dz.sh.hidra.modules.organization.application.contract.assets
 *
 * @Description : Organization-owned unit existence contract exported to Assets.
 *
 */
package dz.sh.hidra.modules.organization.application.contract.assets;

public interface AssetsOrganizationUnitReferenceContract {

    boolean exists(String organizationUnitId);
}
