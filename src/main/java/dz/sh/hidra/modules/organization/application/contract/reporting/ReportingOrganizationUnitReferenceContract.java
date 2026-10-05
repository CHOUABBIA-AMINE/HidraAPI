/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : ReportingOrganizationUnitReferenceContract
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-05
 *
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : organization
 * @Package     : dz.sh.hidra.modules.organization.application.contract.reporting
 *
 * @Description : Organization-owned OrganizationUnit existence contract exported to Reporting.
 *
 */
package dz.sh.hidra.modules.organization.application.contract.reporting;

public interface ReportingOrganizationUnitReferenceContract {

    boolean exists(String organizationUnitId);
}
