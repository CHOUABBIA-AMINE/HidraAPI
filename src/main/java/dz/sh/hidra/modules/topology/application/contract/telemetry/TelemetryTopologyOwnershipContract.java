/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : TelemetryTopologyOwnershipContract
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-11-10
 *
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.application.contract.telemetry
 *
 * @Description : Resolves currently active Topology-owned Telemetry binding asset and company.
 *
 */
package dz.sh.hidra.modules.topology.application.contract.telemetry;


import java.util.Optional;
public interface TelemetryTopologyOwnershipContract {
    record OwnedAsset(String typeCode,String assetId,String companyPartyId) {
        public OwnedAsset {
            if(typeCode==null || typeCode.isBlank() || assetId==null || assetId.isBlank()
                    || companyPartyId==null || companyPartyId.isBlank())
                throw new IllegalArgumentException("Authoritative telemetry asset owner required.");
        }
    }
    Optional<OwnedAsset> currentOwner(String assetType,String assetId);
}
