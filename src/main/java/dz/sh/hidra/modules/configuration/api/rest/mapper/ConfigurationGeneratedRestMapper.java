/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : ConfigurationGeneratedRestMapper
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-28
 *
 * @Type        : Interface
 * @Layer       : API
 * @Module      : configuration
 * @Package     : dz.sh.hidra.modules.configuration.api.rest.mapper
 *
 * @Description : Generates exact configuration API/application boundary mappings at compile time.
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
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

/**
 * Generates only the HRA-070-approved exact configuration boundary mappings.
 */
@Mapper(
        unmappedSourcePolicy = ReportingPolicy.ERROR,
        unmappedTargetPolicy = ReportingPolicy.ERROR
)
public interface ConfigurationGeneratedRestMapper {

    ConfigurationGeneratedRestMapper INSTANCE = Mappers.getMapper(ConfigurationGeneratedRestMapper.class);

    CreateConfigurationDefinitionCommand toCommand(CreateConfigurationDefinitionRequest request);

    CreateFeatureFlagCommand toCommand(CreateFeatureFlagRequest request);

    SetConfigurationValueCommand toCommand(SetConfigurationValueRequest request);

    ConfigurationDefinitionResponse toResponse(ConfigurationDefinitionSummaryDto dto);

    ConfigurationValueResponse toResponse(ConfigurationValueSummaryDto dto);

    FeatureFlagResponse toResponse(FeatureFlagSummaryDto dto);
}
