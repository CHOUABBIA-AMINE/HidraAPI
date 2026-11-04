/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : JpaConfigurationAdministrationQueryAdapter
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-11-04
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : configuration
 * @Package     : dz.sh.hidra.modules.configuration.infrastructure.query
 *
 * @Description : Implements the bounded typed configuration administration contract.
 *
 */
package dz.sh.hidra.modules.configuration.infrastructure.query;

import dz.sh.hidra.modules.configuration.application.port.in.ConfigurationAdministrationUseCase.*;
import dz.sh.hidra.modules.configuration.application.port.out.ConfigurationAdministrationPersistencePort;
import dz.sh.hidra.modules.configuration.domain.model.*;
import dz.sh.hidra.modules.configuration.domain.value.*;
import java.time.Instant;
import java.sql.*;
import java.util.*;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

/** Uses the transaction-bound JDBC connection managed by the application's JPA transaction manager. */
@Repository
public class JpaConfigurationAdministrationQueryAdapter implements ConfigurationAdministrationPersistencePort {
    private final JdbcTemplate jdbc;
    public JpaConfigurationAdministrationQueryAdapter(JdbcTemplate jdbc) {this.jdbc=Objects.requireNonNull(jdbc);}
    private static Instant time(ResultSet r,String c) throws SQLException {var t=r.getTimestamp(c);return t==null?null:t.toInstant();}
    private static Timestamp stamp(Instant v) {return v==null?null:Timestamp.from(v);}
    private static ConfigurationDefinition definition(ResultSet r) throws SQLException {
        return new ConfigurationDefinition(r.getString("id"),r.getString("namespace_id"),r.getString("key"),r.getString("display_name_fr"),r.getString("display_name_ar"),r.getString("display_name_en"),ConfigurationValueType.valueOf(r.getString("value_type")),ConfigurationSensitivity.valueOf(r.getString("sensitivity")),ConfigurationDefinitionStatus.valueOf(r.getString("status")),r.getBoolean("scoped"),r.getBoolean("requires_approval"),r.getString("default_value"),r.getString("description"),time(r,"created_at"),time(r,"updated_at"));
    }
    private static ConfigurationValue value(ResultSet r) throws SQLException {
        return new ConfigurationValue(r.getString("id"),r.getString("definition_id"),r.getString("definition_version_id"),r.getString("environment"),r.getString("raw_value"),r.getString("json_value"),r.getString("secret_reference"),ConfigurationValueStatus.valueOf(r.getString("status")),time(r,"effective_from"),time(r,"effective_to"),r.getString("created_by_actor_id"),time(r,"created_at"),time(r,"updated_at"));
    }
    private static FeatureFlag flag(ResultSet r) throws SQLException {
        return new FeatureFlag(r.getString("id"),r.getString("code"),r.getString("name_fr"),r.getString("name_ar"),r.getString("name_en"),r.getString("owning_module"),FeatureFlagStatus.valueOf(r.getString("status")),FeatureFlagEvaluationStrategy.valueOf(r.getString("evaluation_strategy")),r.getBoolean("default_enabled"),r.getString("description"),time(r,"created_at"),time(r,"updated_at"));
    }
    private static <T> T one(List<T> rows) {if(rows.size()!=1)throw new IllegalArgumentException("Configuration resource unavailable.");return rows.getFirst();}
    @Override public void requireNamespace(String id) {
        var rows=jdbc.queryForList("SELECT id FROM public.hidra_configuration_namespace WHERE id=? AND status='ACTIVE' FOR SHARE",String.class,id);
        one(rows);
    }
    @Override public List<Choice> namespaces(int limit,int offset) {
        return jdbc.query("SELECT id,code,name_fr FROM public.hidra_configuration_namespace WHERE status='ACTIVE' ORDER BY code,id LIMIT ? OFFSET ?",(r,n)->new Choice(r.getString(1),r.getString(2),r.getString(3)),limit,offset);
    }
    private DefinitionState state(ConfigurationDefinition d) {
        var versions=jdbc.queryForList("SELECT id FROM public.hidra_configuration_definition_version WHERE definition_id=? AND active AND validation_summary='HAPI-927' AND schema_json IS NULL",String.class,d.id());
        if(versions.size()>1)throw new IllegalStateException("Ambiguous definition version.");
        return new DefinitionState(d,versions.isEmpty()?null:versions.getFirst());
    }
    @Override public List<DefinitionState> definitions(String namespace,int limit,int offset) {
        return jdbc.query("SELECT * FROM public.hidra_configuration_definition WHERE namespace_id=? ORDER BY key,id LIMIT ? OFFSET ?",(r,n)->definition(r),namespace,limit,offset).stream().map(this::state).toList();
    }
    @Override public DefinitionState definition(String id,boolean lock) {
        return state(one(jdbc.query("SELECT * FROM public.hidra_configuration_definition WHERE id=?"+(lock?" FOR UPDATE":""),(r,n)->definition(r),id)));
    }
    @Override public void requireSupportedRules(String id) {
        Integer count=jdbc.queryForObject("SELECT count(*) FROM public.hidra_configuration_validation_rule WHERE definition_id=? AND status='ACTIVE'",Integer.class,id);
        if(count==null||count!=0)throw new IllegalStateException("Custom validation rules are not admitted.");
    }
    @Override public DefinitionState activate(DefinitionState before,String actor) {
        String id=before.definition().id(),version=UUID.randomUUID().toString();
        jdbc.update("INSERT INTO public.hidra_configuration_definition_version(id,definition_id,version_number,default_value,validation_summary,created_by_actor_id,created_at,active) VALUES (?,?,1,?,'HAPI-927',?,clock_timestamp(),true)",version,id,before.definition().defaultValue(),actor);
        if(jdbc.update("UPDATE public.hidra_configuration_definition SET status='ACTIVE',updated_at=greatest(clock_timestamp(),updated_at+interval '1 microsecond') WHERE id=? AND status='DRAFT' AND updated_at=?",id,stamp(before.definition().updatedAt()))!=1)throw new IllegalStateException("Concurrent definition activation.");
        return definition(id,false);
    }
    @Override public List<ConfigurationValue> values(String id,String environment,int limit,int offset) {
        return jdbc.query("SELECT * FROM public.hidra_configuration_value WHERE definition_id=? AND environment=? ORDER BY effective_from,id LIMIT ? OFFSET ?",(r,n)->value(r),id,environment,limit,offset);
    }
    @Override public ConfigurationValue effectiveValue(String id,String version,String environment,Instant instant) {
        var rows=jdbc.query("SELECT * FROM public.hidra_configuration_value WHERE definition_id=? AND definition_version_id=? AND environment=? AND status='ACTIVE' AND effective_from<=? AND (effective_to IS NULL OR ?<effective_to) ORDER BY id LIMIT 2",(r,n)->value(r),id,version,environment,stamp(instant),stamp(instant));
        if(rows.size()>1)throw new IllegalStateException("Ambiguous effective configuration.");
        return rows.isEmpty()?null:rows.getFirst();
    }
    @Override public void requireNoOverlap(String id,String environment,Instant from,Instant to) {
        var count=jdbc.queryForObject("SELECT count(*) FROM public.hidra_configuration_value WHERE definition_id=? AND environment=? AND status='ACTIVE' AND (effective_to IS NULL OR effective_to>?) AND (?::timestamptz IS NULL OR effective_from IS NULL OR effective_from<?)",Integer.class,id,environment,stamp(from),stamp(to),stamp(to));
        if(count==null||count!=0)throw new IllegalStateException("Overlapping active environment override.");
    }
    @Override public List<FeatureFlag> flags(int limit,int offset) {
        return jdbc.query("SELECT * FROM public.hidra_configuration_feature_flag ORDER BY code,id LIMIT ? OFFSET ?",(r,n)->flag(r),limit,offset);
    }
    @Override public FeatureFlag flag(String id,boolean lock) {
        return one(jdbc.query("SELECT * FROM public.hidra_configuration_feature_flag WHERE id=?"+(lock?" FOR UPDATE":""),(r,n)->flag(r),id));
    }
    private void supportedRules(String id) {
        var count=jdbc.queryForObject("SELECT count(*) FROM public.hidra_configuration_feature_flag_rule WHERE feature_flag_id=? AND active AND (scope_type<>'ENVIRONMENT' OR scope_id IS NULL OR condition_expression IS NOT NULL OR percentage IS NOT NULL OR rule_name NOT LIKE 'HAPI-927:%')",Integer.class,id);
        if(count==null||count!=0)throw new IllegalStateException("Unsupported feature flag rules.");
    }
    @Override public FeatureFlag activateFlag(FeatureFlag before,String actor) {
        supportedRules(before.id());
        if(jdbc.update("UPDATE public.hidra_configuration_feature_flag SET status='ACTIVE',updated_at=greatest(clock_timestamp(),updated_at+interval '1 microsecond') WHERE id=? AND status='DRAFT' AND updated_at=?",before.id(),stamp(before.updatedAt()))!=1)throw new IllegalStateException("Concurrent flag activation.");
        jdbc.update("INSERT INTO public.hidra_configuration_feature_flag_rule(id,feature_flag_id,rule_name,scope_type,enabled,priority_order,active,created_at,updated_at) VALUES (?,?,?,'GLOBAL',false,0,false,clock_timestamp(),clock_timestamp())",UUID.randomUUID().toString(),before.id(),"HAPI-927:ACTIVATE:"+actor);
        return flag(before.id(),false);
    }
    @Override public FlagState flagState(FeatureFlag f,String environment) {
        supportedRules(f.id());
        var rows=jdbc.queryForList("SELECT enabled FROM public.hidra_configuration_feature_flag_rule WHERE feature_flag_id=? AND scope_type='ENVIRONMENT' AND scope_id=? AND active",Boolean.class,f.id(),environment);
        if(rows.size()>1)throw new IllegalStateException("Ambiguous flag state.");
        return new FlagState(f.id(),environment,rows.isEmpty()?f.defaultEnabled():rows.getFirst(),rows.isEmpty()?"DEFAULT":"ENVIRONMENT_OVERRIDE",f.updatedAt());
    }
    @Override public FlagState setFlagState(FeatureFlag f,String environment,boolean enabled,String actor) {
        supportedRules(f.id());
        jdbc.update("UPDATE public.hidra_configuration_feature_flag_rule SET active=false,updated_at=clock_timestamp() WHERE feature_flag_id=? AND scope_type='ENVIRONMENT' AND scope_id=? AND active",f.id(),environment);
        jdbc.update("INSERT INTO public.hidra_configuration_feature_flag_rule(id,feature_flag_id,rule_name,scope_type,scope_id,enabled,priority_order,active,created_at,updated_at) VALUES (?,?,?,'ENVIRONMENT',?,?,0,true,clock_timestamp(),clock_timestamp())",UUID.randomUUID().toString(),f.id(),"HAPI-927:STATE:"+actor,environment,enabled);
        if(jdbc.update("UPDATE public.hidra_configuration_feature_flag SET updated_at=greatest(clock_timestamp(),updated_at+interval '1 microsecond') WHERE id=? AND status='ACTIVE' AND updated_at=?",f.id(),stamp(f.updatedAt()))!=1)throw new IllegalStateException("Concurrent flag state.");
        return flagState(flag(f.id(),false),environment);
    }
}
