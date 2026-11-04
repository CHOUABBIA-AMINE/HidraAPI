/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : OrganizationIdentityUserEligibility
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-11-04
 *
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : identity
 * @Package     : dz.sh.hidra.modules.identity.application.contract.organization
 *
 * @Description : Expose minimal Identity-owned active-user eligibility to protected Organization use cases.
 *
 */
package dz.sh.hidra.modules.identity.application.contract.organization;

/**
 * A bounded scalar eligibility contract. The caller must independently
 * authorize the protected Organization operation before resolving a reference.
 * This does not grant Identity or Organization administration rights.
 */
public interface OrganizationIdentityUserEligibility {
    enum Eligibility { ACTIVE, INACTIVE, NOT_FOUND }

    Eligibility resolve(String userId);
}
