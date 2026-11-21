/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : AssetScopedTrustedObservationAuthorizationPort
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-11-21
 *
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : telemetry
 * @Package     : dz.sh.hidra.modules.telemetry.application.port.out
 *
 * @Description : Evaluates current regional and trusted observation read authority.
 *
 */
package dz.sh.hidra.modules.telemetry.application.port.out;

import dz.sh.hidra.modules.telemetry.application.port.in.AssetScopedTrustedObservationReadUseCase.Asset;

public interface AssetScopedTrustedObservationAuthorizationPort {
    boolean permitsAsset(Asset asset);
    boolean permitsPoint(Asset asset, String sourceId, String pointId);
}
