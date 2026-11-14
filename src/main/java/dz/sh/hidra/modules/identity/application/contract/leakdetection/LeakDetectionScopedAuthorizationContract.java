/**
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 * @Name        : LeakDetectionScopedAuthorizationContract
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-11-14
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : identity
 * @Package     : dz.sh.hidra.modules.identity.application.contract.leakdetection
 * @Description : T13 exact owner contract and fail-closed source/asset authority.
 */
package dz.sh.hidra.modules.identity.application.contract.leakdetection;

public interface LeakDetectionScopedAuthorizationContract {
    /** Rejects unmatched/expired/denied company or exact asset grants. */
    boolean permitted(String permissionCode, String companyId, String assetType, String assetId);
    String authenticatedActor();
}
