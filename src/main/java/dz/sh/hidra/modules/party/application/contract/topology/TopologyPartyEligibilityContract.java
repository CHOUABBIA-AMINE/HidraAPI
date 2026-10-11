/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : TopologyPartyEligibilityContract
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-11
 *
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : party
 * @Package     : dz.sh.hidra.modules.party.application.contract.topology
 *
 * @Description : Exports Party-owned active-reference eligibility to Topology.
 *
 */
package dz.sh.hidra.modules.party.application.contract.topology;

/**
 * Eligibility for a new Party reference, not company membership or access permission.
 * Blank, missing, non-active or mismatched identities are ineligible; repository failures propagate.
 */
public interface TopologyPartyEligibilityContract {
    boolean eligibleForNewReference(String partyId);
}
