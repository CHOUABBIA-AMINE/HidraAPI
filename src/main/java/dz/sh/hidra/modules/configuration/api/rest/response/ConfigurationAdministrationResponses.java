/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : ConfigurationAdministrationResponses
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-11-04
 *
 * @Type        : Class
 * @Layer       : API
 * @Module      : configuration
 * @Package     : dz.sh.hidra.modules.configuration.api.rest.response
 *
 * @Description : Implements the bounded typed configuration administration contract.
 *
 */
package dz.sh.hidra.modules.configuration.api.rest.response;

import dz.sh.hidra.modules.configuration.application.port.in.ConfigurationAdministrationUseCase;
import java.time.Instant;

public final class ConfigurationAdministrationResponses {
    private ConfigurationAdministrationResponses() { }
    @io.swagger.v3.oas.annotations.media.Schema(name="FoundationConfigurationChoiceResponse")
    public record Choice(String id,String code,String label) {
        public static Choice from(ConfigurationAdministrationUseCase.Choice v){return new Choice(v.id(),v.code(),v.label());}
    }
    @io.swagger.v3.oas.annotations.media.Schema(name="FoundationConfigurationDefinitionResponse")
    public record Definition(String id,String namespaceId,String key,String valueType,String sensitivity,String status,String activeVersionId,String defaultValue,boolean redacted,Instant updatedAt) {
        public static Definition from(ConfigurationAdministrationUseCase.DefinitionView v){return new Definition(v.id(),v.namespaceId(),v.key(),v.valueType(),v.sensitivity(),v.status(),v.activeVersionId(),v.defaultValue(),v.redacted(),v.updatedAt());}
    }
    @io.swagger.v3.oas.annotations.media.Schema(name="FoundationConfigurationValueResponse")
    public record Value(String id,String definitionId,String definitionVersionId,String environment,String rawValue,boolean redacted,Instant effectiveFrom,Instant effectiveTo,String actorId) {
        public static Value from(ConfigurationAdministrationUseCase.ValueView v){return new Value(v.id(),v.definitionId(),v.definitionVersionId(),v.environment(),v.rawValue(),v.redacted(),v.effectiveFrom(),v.effectiveTo(),v.actorId());}
    }
    @io.swagger.v3.oas.annotations.media.Schema(name="FoundationConfigurationFlagResponse")
    public record Flag(String id,String code,String status,String strategy,boolean defaultEnabled,Instant updatedAt) {
        public static Flag from(ConfigurationAdministrationUseCase.FlagView v){return new Flag(v.id(),v.code(),v.status(),v.strategy(),v.defaultEnabled(),v.updatedAt());}
    }
    @io.swagger.v3.oas.annotations.media.Schema(name="FoundationConfigurationEffectiveResponse")
    public record Effective(String definitionId,String definitionVersionId,String environment,Instant instant,String value,String provenance,String sourceValueId,boolean redacted) {
        public static Effective from(ConfigurationAdministrationUseCase.Effective v){return new Effective(v.definitionId(),v.definitionVersionId(),v.environment(),v.instant(),v.value(),v.provenance(),v.sourceValueId(),v.redacted());}
    }
    @io.swagger.v3.oas.annotations.media.Schema(name="FoundationConfigurationFlagStateResponse")
    public record FlagState(String flagId,String environment,boolean enabled,String provenance,Instant updatedAt) {
        public static FlagState from(ConfigurationAdministrationUseCase.FlagState v){return new FlagState(v.flagId(),v.environment(),v.enabled(),v.provenance(),v.updatedAt());}
    }
}
