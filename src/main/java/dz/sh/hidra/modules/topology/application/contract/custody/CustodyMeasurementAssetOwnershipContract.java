/**
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 * @Name        : CustodyMeasurementAssetOwnershipContract
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-11-15
 * @Type        : Interface
 * @Layer       : Application Contract
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.application.contract.custody
 * @Description : Exports live facility owner scalar without sharing the Topology aggregate.
 */
package dz.sh.hidra.modules.topology.application.contract.custody;

import java.util.Optional;
public interface CustodyMeasurementAssetOwnershipContract {
    Optional<String> activeOwner(String type,String facilityId);
}
