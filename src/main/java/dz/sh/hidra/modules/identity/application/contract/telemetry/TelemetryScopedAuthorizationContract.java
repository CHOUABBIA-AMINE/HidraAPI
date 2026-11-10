/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : TelemetryScopedAuthorizationContract
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-11-10
 *
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : identity
 * @Package     : dz.sh.hidra.modules.identity.application.contract.telemetry
 *
 * @Description : Native scoped LOCAL producer read and ingest authorization without a global wildcard.
 *
 */
package dz.sh.hidra.modules.identity.application.contract.telemetry;


public interface TelemetryScopedAuthorizationContract {
    enum Operation { INGEST, READ }
    void require(Operation operation, String companyPartyId, String sourceId, String pointId);
}
