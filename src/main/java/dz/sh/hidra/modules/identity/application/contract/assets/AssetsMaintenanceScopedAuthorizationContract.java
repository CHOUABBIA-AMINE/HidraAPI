/**
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 * @Name        : AssetsMaintenanceScopedAuthorizationContract
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-11-15
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : identity
 * @Package     : dz.sh.hidra.modules.identity.application.contract.assets
 * @Description : T18 governed Integrity and Assets maintenance handoff boundary.
 */
package dz.sh.hidra.modules.identity.application.contract.assets;

public interface AssetsMaintenanceScopedAuthorizationContract {
    String currentActor();
    void require(String permission,String companyId,String assetType,String assetId);
    boolean assigneeEligible(String actorId,String companyId,String assetType,String assetId);
}
