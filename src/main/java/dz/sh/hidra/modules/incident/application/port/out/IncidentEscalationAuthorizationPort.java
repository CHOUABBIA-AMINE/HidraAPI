/**
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 * @Name        : IncidentEscalationAuthorizationPort
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-11-14
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : incident
 * @Package     : dz.sh.hidra.modules.incident.application.port.out
 * @Description : Current paired company and asset authorization for Incident.
 */
package dz.sh.hidra.modules.incident.application.port.out;

import java.util.List;

public interface IncidentEscalationAuthorizationPort {
    record AssetRef(String type, String id) {}
    List<AssetRef> permittedAssets(String permission);
    void requireAsset(String permission, String company, String assetType, String assetId);
    void requireIncident(String permission, String id);
}
