/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : TelemetryCommissioningAuthorizationPort
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-11-12
 *
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : telemetry
 * @Package     : dz.sh.hidra.modules.telemetry.application.port.out
 *
 * @Description : Requires Identity grants and current authoritative asset ownership.
 *
 */
package dz.sh.hidra.modules.telemetry.application.port.out;

public interface TelemetryCommissioningAuthorizationPort {
    void requireCompanyManage(String companyPartyId);
    void requireCompanyRead(String companyPartyId);
    void requireScopedManage(String companyPartyId, String sourceId, String pointId);
    void requireScopedRead(String companyPartyId, String sourceId, String pointId);
    void requireAsset(String companyPartyId, String assetType, String assetId);
}
