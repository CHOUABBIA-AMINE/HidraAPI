/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : PlanningCompanyEligibilityContract
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-11-10
 *
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : party
 * @Package     : dz.sh.hidra.modules.party.application.contract.planning
 *
 * @Description : Exposes Party-owned current company eligibility to Planning.
 *
 */
package dz.sh.hidra.modules.party.application.contract.planning;

public interface PlanningCompanyEligibilityContract {

    boolean isActiveCompany(String companyPartyId);
}
