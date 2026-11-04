/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : ConfigurationAdministrationRequests
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-11-04
 *
 * @Type        : Class
 * @Layer       : API
 * @Module      : configuration
 * @Package     : dz.sh.hidra.modules.configuration.api.rest.request
 *
 * @Description : Implements the bounded typed configuration administration contract.
 *
 */
package dz.sh.hidra.modules.configuration.api.rest.request;

import jakarta.validation.constraints.*;
import java.time.Instant;

public final class ConfigurationAdministrationRequests {
    private ConfigurationAdministrationRequests() { }
    @io.swagger.v3.oas.annotations.media.Schema(name="FoundationConfigurationActivationRequest")
    public record Activation(@NotNull Instant expectedUpdatedAt) { }
    @io.swagger.v3.oas.annotations.media.Schema(name="FoundationConfigurationFlagStateRequest")
    public record FlagState(@NotBlank @Size(max=40) String environment,@NotNull Boolean enabled,@NotNull Instant expectedUpdatedAt) { }
}
