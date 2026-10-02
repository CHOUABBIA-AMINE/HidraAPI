/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : ConfigurationRestMapper
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-28
 *
 * @Type        : Class
 * @Layer       : API
 * @Module      : configuration
 * @Package     : dz.sh.hidra.modules.configuration.api.rest.mapper
 *
 * @Description : Maps configuration REST models to application models.
 *
 */
package dz.sh.hidra.modules.configuration.api.rest.mapper;
import dz.sh.hidra.modules.configuration.api.rest.request.CreateConfigurationDefinitionRequest;
import dz.sh.hidra.modules.configuration.api.rest.request.CreateFeatureFlagRequest;
import dz.sh.hidra.modules.configuration.api.rest.request.SetConfigurationValueRequest;
import dz.sh.hidra.modules.configuration.api.rest.response.ConfigurationDefinitionResponse;
import dz.sh.hidra.modules.configuration.api.rest.response.ConfigurationValueResponse;
import dz.sh.hidra.modules.configuration.api.rest.response.FeatureFlagResponse;
import dz.sh.hidra.modules.configuration.application.command.CreateConfigurationDefinitionCommand;
import dz.sh.hidra.modules.configuration.application.command.CreateFeatureFlagCommand;
import dz.sh.hidra.modules.configuration.application.command.SetConfigurationValueCommand;
import dz.sh.hidra.modules.configuration.application.dto.ConfigurationDefinitionSummaryDto;
import dz.sh.hidra.modules.configuration.application.dto.ConfigurationValueSummaryDto;
import dz.sh.hidra.modules.configuration.application.dto.FeatureFlagSummaryDto;
import java.util.Objects;

/**
 * Maps configuration REST models to application models.
 */
public final class ConfigurationRestMapper {

    private static final ConfigurationGeneratedRestMapper GENERATED = ConfigurationGeneratedRestMapper.INSTANCE;

    private ConfigurationRestMapper() {
        throw new UnsupportedOperationException("Utility class must not be instantiated.");
    }

    public static CreateConfigurationDefinitionCommand toCommand(CreateConfigurationDefinitionRequest request) {
        return GENERATED.toCommand(Objects.requireNonNull(request, "CreateConfigurationDefinitionRequest must not be null."));
    }

    public static CreateFeatureFlagCommand toCommand(CreateFeatureFlagRequest request) {
        return GENERATED.toCommand(Objects.requireNonNull(request, "CreateFeatureFlagRequest must not be null."));
    }

    public static SetConfigurationValueCommand toCommand(SetConfigurationValueRequest request) {
        return GENERATED.toCommand(Objects.requireNonNull(request, "SetConfigurationValueRequest must not be null."));
    }

    public static ConfigurationDefinitionResponse toResponse(ConfigurationDefinitionSummaryDto dto) {
        return GENERATED.toResponse(Objects.requireNonNull(dto, "ConfigurationDefinitionSummaryDto must not be null."));
    }

    public static FeatureFlagResponse toResponse(FeatureFlagSummaryDto dto) {
        return GENERATED.toResponse(Objects.requireNonNull(dto, "FeatureFlagSummaryDto must not be null."));
    }

    public static ConfigurationValueResponse toResponse(ConfigurationValueSummaryDto dto) {
        return GENERATED.toResponse(Objects.requireNonNull(dto, "ConfigurationValueSummaryDto must not be null."));
    }
}
