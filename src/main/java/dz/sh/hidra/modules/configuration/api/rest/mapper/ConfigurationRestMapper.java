/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : ConfigurationRestMapper
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Utility
 * @Layer       : API
 * @Module      : configuration
 * @Package     : dz.sh.hidra.modules.configuration.api.rest.mapper
 *
 * @Description : Maps configuration REST models to application models.
 *
 */
package dz.sh.hidra.modules.configuration.api.rest.mapper;

import dz.sh.hidra.modules.configuration.api.rest.request.CreateConfigurationDefinitionRequest;
import dz.sh.hidra.modules.configuration.api.rest.request.SetConfigurationValueRequest;
import dz.sh.hidra.modules.configuration.api.rest.response.ConfigurationDefinitionResponse;
import dz.sh.hidra.modules.configuration.api.rest.response.ConfigurationValueResponse;
import dz.sh.hidra.modules.configuration.application.command.CreateConfigurationDefinitionCommand;
import dz.sh.hidra.modules.configuration.application.command.SetConfigurationValueCommand;
import dz.sh.hidra.modules.configuration.application.dto.ConfigurationDefinitionSummaryDto;
import dz.sh.hidra.modules.configuration.application.dto.ConfigurationValueSummaryDto;

/**
 * Maps configuration REST models to application models.
 */
public final class ConfigurationRestMapper {

    private ConfigurationRestMapper() {
        throw new UnsupportedOperationException("Utility class must not be instantiated.");
    }

    public static CreateConfigurationDefinitionCommand toCommand(CreateConfigurationDefinitionRequest request) {
        return new CreateConfigurationDefinitionCommand(request.namespaceId(), request.key(), request.displayNameFr(), request.displayNameAr(), request.displayNameEn(), request.valueType(), request.sensitivity(), request.scoped(), request.requiresApproval(), request.defaultValue(), request.description());
    }

    public static SetConfigurationValueCommand toCommand(SetConfigurationValueRequest request) {
        return new SetConfigurationValueCommand(request.definitionId(), request.definitionVersionId(), request.environment(), request.rawValue(), request.jsonValue(), request.secretReference(), request.effectiveFrom(), request.effectiveTo(), request.createdByActorId());
    }

    public static ConfigurationDefinitionResponse toResponse(ConfigurationDefinitionSummaryDto dto) {
        return new ConfigurationDefinitionResponse(dto.id(), dto.namespaceId(), dto.key(), dto.displayNameFr(), dto.valueType(), dto.sensitivity(), dto.status(), dto.scoped(), dto.requiresApproval());
    }

    public static ConfigurationValueResponse toResponse(ConfigurationValueSummaryDto dto) {
        return new ConfigurationValueResponse(dto.id(), dto.definitionId(), dto.definitionVersionId(), dto.environment(), dto.status(), dto.effectiveFrom(), dto.effectiveTo());
    }
}
