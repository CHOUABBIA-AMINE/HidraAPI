/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : ConfigurationAdministrationUseCase
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-11-04
 *
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : configuration
 * @Package     : dz.sh.hidra.modules.configuration.application.port.in
 *
 * @Description : Implements the bounded typed configuration administration contract.
 *
 */
package dz.sh.hidra.modules.configuration.application.port.in;

import java.time.Instant;
import java.util.List;

public interface ConfigurationAdministrationUseCase {
    record Choice(String id,String code,String label) { }
    record DefinitionView(String id,String namespaceId,String key,String valueType,String sensitivity,String status,
            String activeVersionId,String defaultValue,boolean redacted,Instant updatedAt) { }
    record ValueView(String id,String definitionId,String definitionVersionId,String environment,String rawValue,
            boolean redacted,Instant effectiveFrom,Instant effectiveTo,String actorId) { }
    record FlagView(String id,String code,String status,String strategy,boolean defaultEnabled,Instant updatedAt) { }
    record Effective(String definitionId,String definitionVersionId,String environment,Instant instant,String value,
            String provenance,String sourceValueId,boolean redacted) { }
    record FlagState(String flagId,String environment,boolean enabled,String provenance,Instant updatedAt) { }
    List<Choice> namespaces(int limit,int offset);
    List<DefinitionView> definitions(String namespaceId,int limit,int offset);
    DefinitionView definition(String id);
    DefinitionView activateDefinition(String id,Instant expectedUpdatedAt);
    List<ValueView> values(String definitionId,String environment,int limit,int offset);
    Effective effective(String definitionId,String environment,Instant instant);
    List<FlagView> flags(int limit,int offset);
    FlagView activateFlag(String id,Instant expectedUpdatedAt);
    FlagState setFlagState(String id,String environment,boolean enabled,Instant expectedUpdatedAt);
    FlagState flagState(String id,String environment);
}
