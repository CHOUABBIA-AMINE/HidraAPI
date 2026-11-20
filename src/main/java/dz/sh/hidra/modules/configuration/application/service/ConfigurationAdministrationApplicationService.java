/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : ConfigurationAdministrationApplicationService
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-11-20
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
import java.util.HexFormat;
import java.util.NoSuchElementException;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import dz.sh.hidra.modules.configuration.domain.model.ConfigurationValue;
import dz.sh.hidra.modules.configuration.domain.value.ConfigurationValueStatus;
import dz.sh.hidra.modules.configuration.application.port.out.ConfigurationAdministrationPersistencePort.RetirementReceipt;
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

    private record Target(ConfigurationValue value,DefinitionState definition) { }
    private static void request(RetirementCommand c) {
        if(c==null)throw new IllegalArgumentException("Retirement request required.");
        TypedConfigurationPolicy.exact(c.definitionId(),80);
        TypedConfigurationPolicy.exact(c.definitionVersionId(),80);
        TypedConfigurationPolicy.environment(c.environment());
        TypedConfigurationPolicy.microseconds(c.cutoff());
    }
    private Target target(String valueId,RetirementCommand command,boolean lock) {
        TypedConfigurationPolicy.exact(valueId,80);
        var v=store.retirementTarget(valueId);
        // A known value with the wrong requested identity is indistinguishable from an unknown value.
        if(v==null||!v.definitionId().equals(command.definitionId())
                ||!java.util.Objects.equals(v.definitionVersionId(),command.definitionVersionId())
                ||!v.environment().equals(command.environment()))
            throw new NoSuchElementException("Configuration resource unavailable.");
        var state=store.definition(v.definitionId(),lock);
        TypedConfigurationPolicy.active(state.definition());
        store.requireNamespace(state.definition().namespaceId());
        store.requireSupportedRules(v.definitionId());
        if(!command.definitionVersionId().equals(state.activeVersionId())
                ||v.status()!=ConfigurationValueStatus.ACTIVE
                ||v.jsonValue()!=null||v.secretReference()!=null||v.rawValue()==null)
            throw new IllegalStateException("Active typed B5 override required.");
        TypedConfigurationPolicy.typed(state.definition().valueType(),v.rawValue());
        return new Target(v,state);
    }
    private static String digest(String... values) {
        try {
            var m=MessageDigest.getInstance("SHA-256");
            for(String v:values) {
                byte[] b=v==null?new byte[0]:v.getBytes(StandardCharsets.UTF_8);
                m.update((byte)(v==null?0:1));
                m.update(ByteBuffer.allocate(4).putInt(b.length).array());
                m.update(b);
            }
            return HexFormat.of().formatHex(m.digest());
        } catch(NoSuchAlgorithmException e) {throw new IllegalStateException("SHA-256 unavailable.",e);}
    }
    private RetirementPreview preview(Target target,RetirementCommand c,Instant now) {
        ConfigurationValue v=target.value();
        TypedConfigurationPolicy.retirement(v.effectiveFrom(),v.effectiveTo(),c.cutoff(),now);
        var before=store.effectiveValue(c.definitionId(),c.definitionVersionId(),c.environment(),c.cutoff().minusNanos(1000));
        if(before==null||!v.id().equals(before.id()))
            throw new IllegalStateException("Override no longer resolves immediately before cutoff.");
        var after=store.effectiveValueExcluding(c.definitionId(),c.definitionVersionId(),c.environment(),c.cutoff(),v.id());
        var d=target.definition().definition();
        // The default is validated by the same typed contract as effective-value resolution.
        if(after==null)TypedConfigurationPolicy.typed(d.valueType(),d.defaultValue());
        String afterSource=after==null?"DEFAULT":"ENVIRONMENT_OVERRIDE";
        String afterId=after==null?null:after.id();
        String token=digest("T32_RETIREMENT_V1",v.id(),v.definitionId(),v.definitionVersionId(),
                v.environment(),v.effectiveFrom().toString(),
                v.effectiveTo()==null?null:v.effectiveTo().toString(),
                c.cutoff().toString(),"ENVIRONMENT_OVERRIDE",v.id(),afterSource,afterId,
                c.definitionVersionId(),d.defaultValue());
        return new RetirementPreview(v.id(),v.definitionId(),v.definitionVersionId(),v.environment(),c.cutoff(),
                "ENVIRONMENT_OVERRIDE",v.id(),afterSource,afterId,token);
    }
    @Override public RetirementPreview previewRetirement(String valueId,RetirementCommand command) {
        authority.administrator();request(command);
        var target=target(valueId,command,false);
        if(store.retirementReceipt(valueId)!=null)throw new IllegalStateException("Override already scheduled for retirement.");
        var result=preview(target,command,Instant.now());
        authority.administrator();return result;
    }
    private static RetirementPreview saved(RetirementReceipt r) {
        return new RetirementPreview(r.valueId(),r.definitionId(),r.definitionVersionId(),r.environment(),
                r.cutoff(),r.beforeSource(),r.beforeValueId(),r.afterSource(),r.afterValueId(),r.stateToken());
    }
    @Override @Transactional(rollbackFor=Exception.class)
    public RetirementScheduled scheduleRetirement(String valueId,RetirementCommand command,String stateToken) {
        String actor=authority.administrator();request(command);
        if(stateToken==null||!stateToken.matches("[0-9a-f]{64}"))
            throw new IllegalArgumentException("Valid preview state token required.");
        var target=target(valueId,command,true); // Same definition lock as B5 override writes.
        var existing=store.retirementReceipt(valueId);
        if(existing!=null) {
            if(!actor.equals(existing.actorId())||!command.definitionId().equals(existing.definitionId())
                    ||!command.definitionVersionId().equals(existing.definitionVersionId())
                    ||!command.environment().equals(existing.environment())
                    ||!command.cutoff().equals(existing.cutoff())||!stateToken.equals(existing.stateToken()))
                throw new IllegalStateException("Competing retirement or stale request.");
            authority.administrator();
            return new RetirementScheduled(saved(existing),existing.id(),existing.actorId(),existing.createdAt(),true);
        }
        var calculated=preview(target,command,Instant.now());
        if(!calculated.stateToken().equals(stateToken))throw new IllegalStateException("Stale retirement preview.");
        var receipt=store.appendRetirement(new RetirementReceipt(java.util.UUID.randomUUID().toString(),
                valueId,command.definitionId(),command.definitionVersionId(),command.environment(),command.cutoff(),
                calculated.beforeSource(),calculated.beforeValueId(),calculated.afterSource(),calculated.afterValueId(),
                calculated.stateToken(),actor,null));
        authority.administrator(); // Denial rolls back the provisional receipt.
        return new RetirementScheduled(saved(receipt),receipt.id(),receipt.actorId(),receipt.createdAt(),false);
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
