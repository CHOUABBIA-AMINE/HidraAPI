/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : TelemetryTopologyAssetLookupPort
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : telemetry
 * @Package     : dz.sh.hidra.modules.telemetry.application.port.out
 *
 * @Description : Outbound port for validating neutral topology asset references.
 *
 */
package dz.sh.hidra.modules.telemetry.application.port.out;

import dz.sh.hidra.modules.telemetry.application.dto.TopologyAssetReferenceDto;
import dz.sh.hidra.modules.telemetry.domain.value.TelemetryCode;
import java.util.Optional;

/**
 * Outbound port for validating neutral topology asset references.
 *
 * <p>Architecture role:
 * Outbound application port for telemetry. Infrastructure adapters implement this interface.
 * Application and domain layers must not depend on JPA, REST, SQL, Spring Data, or external module
 * implementation classes.
 */
public interface TelemetryTopologyAssetLookupPort {

    Optional<TopologyAssetReferenceDto> findTopologyAssetReference(
            TelemetryCode assetTypeCode,
            String assetId);

    boolean existsTopologyAsset(TelemetryCode assetTypeCode, String assetId);

}
