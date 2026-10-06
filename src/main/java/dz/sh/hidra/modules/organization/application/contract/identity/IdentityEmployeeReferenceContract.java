/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : IdentityEmployeeReferenceContract
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-06
 *
 * @Type        : Interface
 * @Layer       : Application Contract
 * @Module      : organization
 * @Package     : dz.sh.hidra.modules.organization.application.contract.identity
 *
 * @Description : Enforces the admitted Batch 5 IdentityEmployeeReferenceContract contract.
 *
 */
package dz.sh.hidra.modules.organization.application.contract.identity;

public interface IdentityEmployeeReferenceContract {
    boolean exists(String employeeId);
}
