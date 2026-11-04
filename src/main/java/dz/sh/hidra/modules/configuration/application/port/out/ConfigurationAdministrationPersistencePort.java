/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : ConfigurationAdministrationPersistencePort
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-11-04
 *
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : configuration
 * @Package     : dz.sh.hidra.modules.configuration.application.port.out
 *
 * @Description : Implements the bounded typed configuration administration contract.
 *
 */
package dz.sh.hidra.modules.configuration.application.port.out;

import dz.sh.hidra.modules.configuration.application.port.in.ConfigurationAdministrationUseCase.*;
import dz.sh.hidra.modules.configuration.domain.model.*;
import java.time.Instant;
import java.util.List;

public interface ConfigurationAdministrationPersistencePort {
    record DefinitionState(ConfigurationDefinition definition,String activeVersionId) { }
    void requireNamespace(String id);
    List<Choice> namespaces(int limit,int offset);
    List<DefinitionState> definitions(String namespaceId,int limit,int offset);
    DefinitionState definition(String id,boolean lock);
    DefinitionState activate(DefinitionState before,String actor);
    List<ConfigurationValue> values(String definitionId,String environment,int limit,int offset);
    ConfigurationValue effectiveValue(String definitionId,String version,String environment,Instant instant);
    void requireSupportedRules(String definitionId);
    void requireNoOverlap(String definitionId,String environment,Instant from,Instant to);
    List<FeatureFlag> flags(int limit,int offset);
    FeatureFlag flag(String id,boolean lock);
    FeatureFlag activateFlag(FeatureFlag before,String actor);
    FlagState flagState(FeatureFlag flag,String environment);
    FlagState setFlagState(FeatureFlag flag,String environment,boolean enabled,String actor);
}
