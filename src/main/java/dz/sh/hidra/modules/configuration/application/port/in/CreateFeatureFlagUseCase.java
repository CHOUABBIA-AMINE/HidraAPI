/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : CreateFeatureFlagUseCase
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : configuration
 * @Package     : dz.sh.hidra.modules.configuration.application.port.in
 *
 * @Description : Use case for creating feature flags.
 *
 */
package dz.sh.hidra.modules.configuration.application.port.in;

import dz.sh.hidra.modules.configuration.application.command.CreateFeatureFlagCommand;
import dz.sh.hidra.modules.configuration.application.dto.FeatureFlagSummaryDto;

/**
 * Use case for creating feature flags.
 */
public interface CreateFeatureFlagUseCase {

    FeatureFlagSummaryDto createFeatureFlag(CreateFeatureFlagCommand command);
}
