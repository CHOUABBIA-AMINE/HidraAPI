/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : TelemetryCommissioningNativeScopeAdapter
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-11-12
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : telemetry
 * @Package     : dz.sh.hidra.modules.telemetry.infrastructure.security
 *
 * @Description : Confirms current native company/point grants and authoritative asset ownership.
 *
 */
package dz.sh.hidra.modules.telemetry.infrastructure.security;

import dz.sh.hidra.modules.telemetry.application.port.out.TelemetryCommissioningAuthorizationPort;
import dz.sh.hidra.modules.identity.application.contract.telemetry.TelemetryScopedAuthorizationContract;
import dz.sh.hidra.modules.topology.application.contract.telemetry.TelemetryTopologyOwnershipContract;
import java.util.Objects;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Component;

@Component
public final class TelemetryCommissioningNativeScopeAdapter implements TelemetryCommissioningAuthorizationPort {
    private final TelemetryScopedAuthorizationContract identity;
    private final TelemetryTopologyOwnershipContract topology;
    public TelemetryCommissioningNativeScopeAdapter(TelemetryScopedAuthorizationContract identity,
                                                     TelemetryTopologyOwnershipContract topology) {
        this.identity=Objects.requireNonNull(identity);
        this.topology=Objects.requireNonNull(topology);
    }
    @Override public void requireCompanyManage(String company) {
        identity.require(TelemetryScopedAuthorizationContract.Operation.COMMISSION,company,null,null);
    }
    @Override public void requireCompanyRead(String company) {
        identity.require(TelemetryScopedAuthorizationContract.Operation.READ,company,null,null);
    }
    @Override public void requireScopedManage(String company,String source,String point) {
        identity.require(TelemetryScopedAuthorizationContract.Operation.COMMISSION,company,source,point);
    }
    @Override public void requireScopedRead(String company,String source,String point) {
        identity.require(TelemetryScopedAuthorizationContract.Operation.READ,company,source,point);
    }
    @Override public void requireAsset(String company,String type,String asset) {
        var owned=topology.currentOwner(type,asset).orElseThrow(TelemetryCommissioningNativeScopeAdapter::denied);
        if(!owned.companyPartyId().equals(company)
                || !owned.typeCode().equals(type) || !owned.assetId().equals(asset))
            throw denied();
    }
    private static AccessDeniedException denied() {
        return new AccessDeniedException("Protected telemetry resource unavailable.");
    }
}
