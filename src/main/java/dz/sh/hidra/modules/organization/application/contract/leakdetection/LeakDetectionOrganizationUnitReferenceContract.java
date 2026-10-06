/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : LeakDetectionOrganizationUnitReferenceContract
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-06
 *
 * @Type        : Interface
 * @Layer       : Application Contract
 * @Module      : organization
 * @Package     : dz.sh.hidra.modules.organization.application.contract.leakdetection
 *
 * @Description : Exports Organization unit existence to Leak Detection.
 *
 */
package dz.sh.hidra.modules.organization.application.contract.leakdetection;

public interface LeakDetectionOrganizationUnitReferenceContract {

    boolean exists(String organizationUnitId);
}
