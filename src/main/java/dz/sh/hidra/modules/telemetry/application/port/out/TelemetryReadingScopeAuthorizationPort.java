/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : TelemetryReadingScopeAuthorizationPort
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-11-10
 *
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : telemetry
 * @Package     : dz.sh.hidra.modules.telemetry.application.port.out
 *
 * @Description : Requires current native Identity grants on authoritative Topology-owned scope.
 *
 */
package dz.sh.hidra.modules.telemetry.application.port.out;


import dz.sh.hidra.modules.telemetry.application.port.out.TelemetryReadingIngestionReferencePort.Scope;

public interface TelemetryReadingScopeAuthorizationPort {
    enum Operation { INGEST, READ, QUALIFY, TRUSTED_READ }
    void require(Scope scope, Operation operation);
    String requireActor(Scope scope, Operation operation, String companyPartyId);
}
