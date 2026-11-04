/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : ConfigurationAdministrationApplicationService
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-11-04
 *
 * @Type        : Class
 * @Layer       : Application
 * @Module      : configuration
 * @Package     : dz.sh.hidra.modules.configuration.application.service
 *
 * @Description : Implements the bounded typed configuration administration contract.
 *
 */
package dz.sh.hidra.modules.configuration.application.service;

import dz.sh.hidra.modules.configuration.application.port.in.ConfigurationAdministrationUseCase;
import dz.sh.hidra.modules.configuration.application.port.out.*;
import dz.sh.hidra.modules.configuration.application.port.out.ConfigurationAdministrationPersistencePort.DefinitionState;
import dz.sh.hidra.modules.configuration.domain.service.TypedConfigurationPolicy;
import dz.sh.hidra.modules.configuration.domain.value.*;
import java.time.Instant;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly=true)
public class ConfigurationAdministrationApplicationService implements ConfigurationAdministrationUseCase {
    private final ConfigurationAdministrationPersistencePort store;
    private final ConfigurationAdministrationAuthorizationPort authority;
    public ConfigurationAdministrationApplicationService(ConfigurationAdministrationPersistencePort store,ConfigurationAdministrationAuthorizationPort authority) {
        this.store=java.util.Objects.requireNonNull(store);this.authority=java.util.Objects.requireNonNull(authority);
    }
    private static void page(int limit,int offset) {
        if(limit<1||limit>200||offset<0||offset>100000)throw new IllegalArgumentException("Bounded page required.");
    }
    private static boolean sensitive(DefinitionState s) {
        return s.definition().sensitivity()!=ConfigurationSensitivity.PUBLIC&&s.definition().sensitivity()!=ConfigurationSensitivity.INTERNAL;
    }
    private static boolean safe(String value) {
        try { TypedConfigurationPolicy.noSecrets(value);return true; }
        catch(dz.sh.hidra.modules.configuration.domain.exception.ConfigurationBoundaryViolationException denied) {return false;}
    }
    private static DefinitionView view(DefinitionState s) {
        var d=s.definition();return new DefinitionView(d.id(),d.namespaceId(),d.key(),d.valueType().name(),d.sensitivity().name(),d.status().name(),s.activeVersionId(),sensitive(s)||!safe(d.defaultValue())?null:d.defaultValue(),sensitive(s)||!safe(d.defaultValue()),d.updatedAt());
    }
    private static FlagView view(dz.sh.hidra.modules.configuration.domain.model.FeatureFlag f) {
        return new FlagView(f.id(),f.code(),f.status().name(),f.evaluationStrategy().name(),f.defaultEnabled(),f.updatedAt());
    }
    @Override public List<Choice> namespaces(int limit,int offset) {
        authority.administrator();page(limit,offset);var result=List.copyOf(store.namespaces(limit,offset));authority.administrator();return result;
    }
    @Override public List<DefinitionView> definitions(String namespace,int limit,int offset) {
        authority.administrator();page(limit,offset);TypedConfigurationPolicy.exact(namespace,80);
        var result=store.definitions(namespace,limit,offset).stream().map(ConfigurationAdministrationApplicationService::view).toList();authority.administrator();return result;
    }
    @Override public DefinitionView definition(String id) {
        authority.administrator();var result=view(store.definition(TypedConfigurationPolicy.exact(id,80),false));authority.administrator();return result;
    }
    @Override @Transactional(rollbackFor=Exception.class)
    public DefinitionView activateDefinition(String id,Instant expected) {
        String actor=authority.administrator();var state=store.definition(TypedConfigurationPolicy.exact(id,80),true);var d=state.definition();
        TypedConfigurationPolicy.version(d.updatedAt(),expected);TypedConfigurationPolicy.supported(d);store.requireNamespace(d.namespaceId());store.requireSupportedRules(id);
        if(d.status()!=ConfigurationDefinitionStatus.DRAFT)throw new IllegalStateException("Only DRAFT definitions may activate.");
        var result=view(store.activate(state,actor));authority.administrator();return result;
    }
    @Override public List<ValueView> values(String id,String environment,int limit,int offset) {
        authority.administrator();page(limit,offset);TypedConfigurationPolicy.environment(environment);var s=store.definition(TypedConfigurationPolicy.exact(id,80),false);
        var result=store.values(id,environment,limit,offset).stream().map(v->new ValueView(v.id(),v.definitionId(),v.definitionVersionId(),v.environment(),sensitive(s)||!safe(v.rawValue())||!safe(v.jsonValue())?null:v.rawValue(),sensitive(s)||!safe(v.rawValue())||!safe(v.jsonValue()),v.effectiveFrom(),v.effectiveTo(),v.createdByActorId())).toList();authority.administrator();return result;
    }
    @Override public Effective effective(String id,String environment,Instant instant) {
        authority.administrator();TypedConfigurationPolicy.environment(environment);TypedConfigurationPolicy.microseconds(instant);
        var state=store.definition(TypedConfigurationPolicy.exact(id,80),false);var d=state.definition();
        if(sensitive(state)) {authority.administrator();return new Effective(id,state.activeVersionId(),environment,instant,null,"REDACTED",null,true);}
        TypedConfigurationPolicy.active(d);store.requireSupportedRules(id);
        if(state.activeVersionId()==null)throw new IllegalStateException("Admitted definition version required.");
        var override=store.effectiveValue(id,state.activeVersionId(),environment,instant);
        String value=TypedConfigurationPolicy.typed(d.valueType(),override==null?d.defaultValue():override.rawValue());
        var result=new Effective(id,state.activeVersionId(),environment,instant,value,override==null?"DEFAULT":"ENVIRONMENT_OVERRIDE",override==null?null:override.id(),false);
        authority.administrator();return result;
    }
    @Override public List<FlagView> flags(int limit,int offset) {
        authority.administrator();page(limit,offset);var result=store.flags(limit,offset).stream().map(ConfigurationAdministrationApplicationService::view).toList();authority.administrator();return result;
    }
    @Override @Transactional(rollbackFor=Exception.class)
    public FlagView activateFlag(String id,Instant expected) {
        String actor=authority.administrator();var f=store.flag(TypedConfigurationPolicy.exact(id,80),true);TypedConfigurationPolicy.version(f.updatedAt(),expected);TypedConfigurationPolicy.booleanFlag(f);
        if(f.status()!=FeatureFlagStatus.DRAFT)throw new IllegalStateException("Only DRAFT flags may activate.");
        var result=view(store.activateFlag(f,actor));authority.administrator();return result;
    }
    @Override public FlagState flagState(String id,String environment) {
        authority.administrator();TypedConfigurationPolicy.environment(environment);var f=store.flag(TypedConfigurationPolicy.exact(id,80),false);TypedConfigurationPolicy.booleanFlag(f);
        if(f.status()!=FeatureFlagStatus.ACTIVE)throw new IllegalStateException("Active BOOLEAN flag required.");
        var result=store.flagState(f,environment);authority.administrator();return result;
    }
    @Override @Transactional(rollbackFor=Exception.class)
    public FlagState setFlagState(String id,String environment,boolean enabled,Instant expected) {
        String actor=authority.administrator();TypedConfigurationPolicy.environment(environment);var f=store.flag(TypedConfigurationPolicy.exact(id,80),true);TypedConfigurationPolicy.version(f.updatedAt(),expected);TypedConfigurationPolicy.booleanFlag(f);
        if(f.status()!=FeatureFlagStatus.ACTIVE)throw new IllegalStateException("Active BOOLEAN flag required.");
        var result=store.setFlagState(f,environment,enabled,actor);authority.administrator();return result;
    }
}
