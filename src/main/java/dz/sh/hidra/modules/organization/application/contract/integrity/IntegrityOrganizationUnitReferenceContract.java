/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : IntegrityOrganizationUnitReferenceContract
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-06
 *
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : organization
 * @Package     : dz.sh.hidra.modules.organization.application.contract.integrity
 *
 * @Description : Organization-owned unit existence contract exported to Integrity.
 *
 */
package dz.sh.hidra.modules.organization.application.contract.integrity;

public interface IntegrityOrganizationUnitReferenceContract {

    boolean exists(String organizationUnitId);
}
