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
import dz.sh.hidra.modules.configuration.api.rest.request.CreateFeatureFlagRequest;
import dz.sh.hidra.modules.configuration.api.rest.request.SetConfigurationValueRequest;
import dz.sh.hidra.modules.configuration.api.rest.response.ConfigurationDefinitionResponse;
import dz.sh.hidra.modules.configuration.api.rest.response.ConfigurationValueResponse;
import dz.sh.hidra.modules.configuration.api.rest.response.FeatureFlagResponse;
import dz.sh.hidra.modules.configuration.application.port.in.CreateConfigurationDefinitionUseCase;
import dz.sh.hidra.modules.configuration.application.port.in.CreateFeatureFlagUseCase;
import dz.sh.hidra.modules.configuration.application.port.in.SetConfigurationValueUseCase;
import jakarta.validation.Valid;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Spring MVC adapter exposing configuration REST endpoints.
 */
@RestController
@Validated
@RequestMapping("/api/v1/configuration")
public class SpringConfigurationController implements ConfigurationController {

    private final CreateConfigurationDefinitionUseCase createConfigurationDefinitionUseCase;
    private final CreateFeatureFlagUseCase createFeatureFlagUseCase;
    private final SetConfigurationValueUseCase setConfigurationValueUseCase;

    public SpringConfigurationController(
            CreateConfigurationDefinitionUseCase createConfigurationDefinitionUseCase,
            CreateFeatureFlagUseCase createFeatureFlagUseCase,
            SetConfigurationValueUseCase setConfigurationValueUseCase
    ) {
        this.createConfigurationDefinitionUseCase = Objects.requireNonNull(createConfigurationDefinitionUseCase, "CreateConfigurationDefinitionUseCase must not be null.");
        this.createFeatureFlagUseCase = Objects.requireNonNull(createFeatureFlagUseCase, "CreateFeatureFlagUseCase must not be null.");
        this.setConfigurationValueUseCase = Objects.requireNonNull(setConfigurationValueUseCase, "SetConfigurationValueUseCase must not be null.");
    }

    @GetMapping("/capabilities")
    public Map<String, Object> capabilities() {
        return Map.of(
                "module", "configuration",
                "mission", "Govern runtime definitions, feature flags, and operational configuration values safely.",
                "objectives", List.of(
                "Create configuration definitions.",
                "Set configuration values with controlled context.",
                "Create feature flags for controlled operational capability rollout."
        ),
                "operations", List.of(
                "createConfigurationDefinition",
                "createFeatureFlag",
                "setConfigurationValue"
        ),
                "resourceEndpoints", List.of(
                "POST /api/v1/configuration/definitions",
                "POST /api/v1/configuration/feature-flags",
                "POST /api/v1/configuration/values"
        )
        );
    }

    @Override
    @PostMapping({"/create-configuration-definition", "/definitions"})
    public ConfigurationDefinitionResponse createConfigurationDefinition(@Valid @RequestBody CreateConfigurationDefinitionRequest request) {
        Objects.requireNonNull(request, "CreateConfigurationDefinitionRequest must not be null.");
        return ConfigurationRestMapper.toResponse(createConfigurationDefinitionUseCase.createConfigurationDefinition(ConfigurationRestMapper.toCommand(request)));
    }

    @Override
    @PostMapping({"/create-feature-flag", "/feature-flags"})
    public FeatureFlagResponse createFeatureFlag(@Valid @RequestBody CreateFeatureFlagRequest request) {
        Objects.requireNonNull(request, "CreateFeatureFlagRequest must not be null.");
        return ConfigurationRestMapper.toResponse(createFeatureFlagUseCase.createFeatureFlag(ConfigurationRestMapper.toCommand(request)));
    }

    @Override
    @PostMapping({"/set-configuration-value", "/values"})
    public ConfigurationValueResponse setConfigurationValue(@Valid @RequestBody SetConfigurationValueRequest request) {
        Objects.requireNonNull(request, "SetConfigurationValueRequest must not be null.");
        return ConfigurationRestMapper.toResponse(setConfigurationValueUseCase.setConfigurationValue(ConfigurationRestMapper.toCommand(request)));
    }

}
