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
import java.util.Objects;
import org.springframework.stereotype.Component;

@Component
public final class TelemetryCommissioningNativeScopeAdapter implements TelemetryCommissioningAuthorizationPort {
    private final TelemetryReadingNativeScopeAdapter nativeScope;
    public TelemetryCommissioningNativeScopeAdapter(TelemetryReadingNativeScopeAdapter nativeScope) {
        this.nativeScope=Objects.requireNonNull(nativeScope);
    }
    @Override public void requireCompanyManage(String company) {
        nativeScope.requireCompanyManage(company);
    }
    @Override public void requireCompanyRead(String company) {
        nativeScope.requireCompanyRead(company);
    }
    @Override public void requireScopedManage(String company,String source,String point) {
        nativeScope.requireScopedManage(company,source,point);
    }
    @Override public void requireScopedRead(String company,String source,String point) {
        nativeScope.requireScopedRead(company,source,point);
    }
    @Override public void requireAsset(String company,String type,String asset) {
        nativeScope.requireAsset(company,type,asset);
    }
}
