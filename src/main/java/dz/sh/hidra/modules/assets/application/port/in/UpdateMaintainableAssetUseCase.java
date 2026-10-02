/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : UpdateMaintainableAssetUseCase
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-12
 *
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : assets
 * @Package     : dz.sh.hidra.modules.assets.application.port.in
 *
 * @Description : Concurrency-protected application contract for renaming a maintainable asset.
 *
 */
package dz.sh.hidra.modules.assets.application.port.in;

import dz.sh.hidra.modules.assets.application.dto.MaintainableAssetSummaryDto;

import java.time.Instant;

public interface UpdateMaintainableAssetUseCase {

    MaintainableAssetSummaryDto updateMaintainableAsset(String assetId, Command command);

    record Command(
            Instant expectedUpdatedAt,
            String assetName
    ) {
        public Command {
            if (expectedUpdatedAt == null) {
                throw new IllegalArgumentException("expectedUpdatedAt must not be null.");
            }
            if (assetName == null || assetName.isBlank()) {
                throw new IllegalArgumentException("assetName must not be null or blank.");
            }
            assetName = assetName.trim();
        }
    }
}
