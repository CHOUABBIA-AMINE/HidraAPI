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
    /**
     * Non-native implementations must not implicitly authorize sensitive qualification.
     * The reviewed Telemetry native adapter overrides this method with a live
     * company/source-point/Topology check and returns the authenticated LOCAL actor.
     */
    default String requireActor(Scope scope, Operation operation, String companyPartyId) {
        throw new org.springframework.security.access.AccessDeniedException(
                "Protected telemetry resource unavailable.");
    }
}
