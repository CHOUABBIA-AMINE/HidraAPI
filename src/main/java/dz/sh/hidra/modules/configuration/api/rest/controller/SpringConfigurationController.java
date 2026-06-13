/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : SpringConfigurationController
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-13
 *
 * @Type        : Class
 * @Layer       : API
 * @Module      : configuration
 * @Package     : dz.sh.hidra.modules.configuration.api.rest.controller
 *
 * @Description : Spring MVC adapter exposing configuration REST endpoints.
 *
 */
package dz.sh.hidra.modules.configuration.api.rest.controller;

import dz.sh.hidra.modules.configuration.api.rest.mapper.ConfigurationRestMapper;
import dz.sh.hidra.modules.configuration.api.rest.request.CreateConfigurationDefinitionRequest;
import dz.sh.hidra.modules.configuration.api.rest.request.SetConfigurationValueRequest;
import dz.sh.hidra.modules.configuration.api.rest.response.ConfigurationDefinitionResponse;
import dz.sh.hidra.modules.configuration.api.rest.response.ConfigurationValueResponse;
import dz.sh.hidra.modules.configuration.application.port.in.CreateConfigurationDefinitionUseCase;
import dz.sh.hidra.modules.configuration.application.port.in.SetConfigurationValueUseCase;
import jakarta.validation.Valid;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.Objects;

/**
 * Spring MVC adapter exposing configuration REST endpoints.
 */
@RestController
@Validated
@RequestMapping("/api/v1/configuration")
public final class SpringConfigurationController implements ConfigurationController {

    private final CreateConfigurationDefinitionUseCase createConfigurationDefinitionUseCase;
    private final SetConfigurationValueUseCase setConfigurationValueUseCase;

    public SpringConfigurationController(
            CreateConfigurationDefinitionUseCase createConfigurationDefinitionUseCase,
            SetConfigurationValueUseCase setConfigurationValueUseCase
    ) {
        this.createConfigurationDefinitionUseCase = Objects.requireNonNull(createConfigurationDefinitionUseCase, "CreateConfigurationDefinitionUseCase must not be null.");
        this.setConfigurationValueUseCase = Objects.requireNonNull(setConfigurationValueUseCase, "SetConfigurationValueUseCase must not be null.");
    }


    @Override
    @PostMapping("/create-configuration-definition")
    public ConfigurationDefinitionResponse createConfigurationDefinition(@Valid @RequestBody CreateConfigurationDefinitionRequest request) {
        Objects.requireNonNull(request, "CreateConfigurationDefinitionRequest must not be null.");
        return ConfigurationRestMapper.toResponse(createConfigurationDefinitionUseCase.createConfigurationDefinition(ConfigurationRestMapper.toCommand(request)));
    }

    @Override
    @PostMapping("/set-configuration-value")
    public ConfigurationValueResponse setConfigurationValue(@Valid @RequestBody SetConfigurationValueRequest request) {
        Objects.requireNonNull(request, "SetConfigurationValueRequest must not be null.");
        return ConfigurationRestMapper.toResponse(setConfigurationValueUseCase.setConfigurationValue(ConfigurationRestMapper.toCommand(request)));
    }

}
