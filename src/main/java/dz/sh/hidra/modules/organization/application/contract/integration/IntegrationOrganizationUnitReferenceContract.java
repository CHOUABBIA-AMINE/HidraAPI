/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : IntegrationOrganizationUnitReferenceContract
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-05
 *
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : organization
 * @Package     : dz.sh.hidra.modules.organization.application.contract.integration
 *
 * @Description : Organization-owned OrganizationUnit existence contract exported to Integration.
 *
 */
package dz.sh.hidra.modules.organization.application.contract.integration;

public interface IntegrationOrganizationUnitReferenceContract {

    boolean exists(String organizationUnitId);
}
