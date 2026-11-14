/**
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 * @Name        : LeakCandidateResourceAuthorizationPort
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-11-14
 * @Type        : Class
 * @Layer       : Application
 * @Module      : leakdetection
 * @Package     : dz.sh.hidra.modules.leakdetection.application.port.out
 * @Description : T13 governed source-linked suspected leak candidate delivery.
 */
package dz.sh.hidra.modules.leakdetection.application.port.out;

import java.util.List;

/** Current company+asset authorization, never a caller-chosen scope. */
public interface LeakCandidateResourceAuthorizationPort {
    record AssetRef(String type, String id) { }
    List<AssetRef> permittedAssets(String permissionCode);
    String requireAsset(String permissionCode, String type, String id);
    /** Source company must still be the current asset company at creation. */
    String requireSource(String permissionCode, String company, String type, String id);
    void requireCandidate(String permissionCode, String candidateId);
}
