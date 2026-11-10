/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : TelemetryReadingNativeScopeAdapter
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-11-10
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : telemetry
 * @Package     : dz.sh.hidra.modules.telemetry.infrastructure.security
 *
 * @Description : Joins locked authoritative Telemetry references with current Topology and Identity grants.
 *
 */
package dz.sh.hidra.modules.telemetry.infrastructure.security;


import dz.sh.hidra.modules.telemetry.application.port.out.TelemetryReadingScopeAuthorizationPort;
import dz.sh.hidra.modules.telemetry.application.port.out.TelemetryReadingIngestionReferencePort.Scope;
import dz.sh.hidra.modules.identity.application.contract.telemetry.TelemetryScopedAuthorizationContract;
import dz.sh.hidra.modules.topology.application.contract.telemetry.TelemetryTopologyOwnershipContract;
import java.util.Objects;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Component;

@Component
public final class TelemetryReadingNativeScopeAdapter implements TelemetryReadingScopeAuthorizationPort {
    private final TelemetryTopologyOwnershipContract topology;
    private final TelemetryScopedAuthorizationContract identity;
    public TelemetryReadingNativeScopeAdapter(TelemetryTopologyOwnershipContract topology,
            TelemetryScopedAuthorizationContract identity) {
        this.topology=Objects.requireNonNull(topology);this.identity=Objects.requireNonNull(identity);
    }
    @Override public void require(Scope scope,Operation operation) {
        if(scope==null||operation==null)
            throw new AccessDeniedException("Protected telemetry resource unavailable.");
        var owner=topology.currentOwner(scope.assetType(),scope.assetId())
                .orElseThrow(()->new AccessDeniedException("Protected telemetry resource unavailable."));
        if(!owner.typeCode().equals(scope.assetType()) || !owner.assetId().equals(scope.assetId()))
            throw new AccessDeniedException("Protected telemetry resource unavailable.");
        identity.require(operation==Operation.INGEST
                    ? TelemetryScopedAuthorizationContract.Operation.INGEST
                    : TelemetryScopedAuthorizationContract.Operation.READ,
                owner.companyPartyId(),scope.sourceId(),scope.pointId());
    }
}
