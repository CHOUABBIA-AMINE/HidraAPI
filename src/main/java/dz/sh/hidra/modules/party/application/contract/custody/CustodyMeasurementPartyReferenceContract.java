/**
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 * @Name        : CustodyMeasurementPartyReferenceContract
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-11-15
 * @Type        : Interface
 * @Layer       : Application Contract
 * @Module      : party
 * @Package     : dz.sh.hidra.modules.party.application.contract.custody
 * @Description : Reports current active exact Party eligibility for a governed Custody capture.
 */
package dz.sh.hidra.modules.party.application.contract.custody;

public interface CustodyMeasurementPartyReferenceContract {
    boolean active(String partyId,String roleId);
}
