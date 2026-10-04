/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : TopologyPartyReferenceContract
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-04
 *
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : party
 * @Package     : dz.sh.hidra.modules.party.application.contract.topology
 *
 * @Description : Party-owned public existence contract exported to Topology.
 *
 */
package dz.sh.hidra.modules.party.application.contract.topology;

/**
 * Validates Party identities for Topology without exposing Party domain or persistence types.
 */
public interface TopologyPartyReferenceContract {

    boolean exists(String partyId);
}
