/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : TypedConfigurationPolicy
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-11-04
 *
 * @Type        : Class
 * @Layer       : Domain
 * @Module      : configuration
 * @Package     : dz.sh.hidra.modules.configuration.domain.service
 *
 * @Description : Implements the bounded typed configuration administration contract.
 *
 */
package dz.sh.hidra.modules.configuration.domain.service;

import dz.sh.hidra.modules.configuration.domain.model.*;
import dz.sh.hidra.modules.configuration.domain.value.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.Objects;

public final class TypedConfigurationPolicy {
    private static final ConfigurationValueGuard GUARD=new ConfigurationValueGuard();
    private TypedConfigurationPolicy() { }
    public static String exact(String value,int limit) {
        if(value==null||value.isBlank()||value.length()>limit||!value.equals(value.trim())
                ||value.codePoints().anyMatch(Character::isISOControl))throw new IllegalArgumentException("Bounded exact value required.");
        return value;
    }
    public static String environment(String value) {
        exact(value,40);
        if(!value.matches("[A-Za-z0-9][A-Za-z0-9_.-]{0,39}"))throw new IllegalArgumentException("Invalid environment.");
        return value;
    }
    public static void noSecrets(String... values) {for(String v:values)GUARD.ensureNoSecretMaterial(v);}
    public static String typed(ConfigurationValueType type,String value) {
        Objects.requireNonNull(type,"Value type required.");
        noSecrets(value);
        if(type!=ConfigurationValueType.STRING&&type!=ConfigurationValueType.BOOLEAN&&type!=ConfigurationValueType.NUMBER)
            throw new IllegalArgumentException("Unsupported configuration type.");
        if(value==null)return null;
        exact(value,2000);
        return switch(type) {
            case STRING -> value;
            case BOOLEAN -> {
                if(!value.equals("true")&&!value.equals("false"))throw new IllegalArgumentException("Strict boolean required.");
                yield value;
            }
            case NUMBER -> {
                if(!value.matches("-?(0|[1-9][0-9]*)(\\.[0-9]+)?"))throw new IllegalArgumentException("Plain decimal required.");
                BigDecimal number=new BigDecimal(value);
                if(number.precision()>38||number.scale()>18)throw new IllegalArgumentException("Decimal precision/scale exceeds supported bounds.");
                yield number.stripTrailingZeros().toPlainString();
            }
            default -> throw new IllegalArgumentException("Unsupported configuration type.");
        };
    }
    public static void supported(ConfigurationDefinition d) {
        GUARD.ensureAllowedDefinition(d.key());
        if(d.requiresApproval()||d.scoped())throw new IllegalStateException("Approval or resource-scoped configuration is not supported.");
        if(d.sensitivity()!=ConfigurationSensitivity.PUBLIC&&d.sensitivity()!=ConfigurationSensitivity.INTERNAL)
            throw new IllegalStateException("Sensitive configuration is metadata only.");
        if(d.valueType()!=ConfigurationValueType.STRING&&d.valueType()!=ConfigurationValueType.BOOLEAN&&d.valueType()!=ConfigurationValueType.NUMBER)
            throw new IllegalArgumentException("Unsupported configuration type.");
        typed(d.valueType(),d.defaultValue());
        noSecrets(d.key(),d.displayNameFr(),d.displayNameAr(),d.displayNameEn(),d.description());
    }
    public static void active(ConfigurationDefinition d) {
        supported(d);
        if(d.status()!=ConfigurationDefinitionStatus.ACTIVE)throw new IllegalStateException("Active definition required.");
    }
    public static void booleanFlag(FeatureFlag f) {
        if(f.evaluationStrategy()!=FeatureFlagEvaluationStrategy.BOOLEAN)throw new IllegalArgumentException("Only BOOLEAN flags are supported.");
        noSecrets(f.code(),f.nameFr(),f.nameAr(),f.nameEn(),f.description());
    }
    public static void window(Instant from,Instant to) {
        if(from==null||to!=null&&!to.isAfter(from))throw new IllegalArgumentException("Nonempty half-open interval required.");
        microseconds(from);if(to!=null)microseconds(to);
    }
    public static void microseconds(Instant instant) {
        if(instant==null||instant.getNano()%1000!=0)throw new IllegalArgumentException("Exact PostgreSQL microsecond instant required.");
    }
    public static void version(Instant actual,Instant expected) {
        microseconds(expected);
        if(!expected.equals(actual))throw new IllegalStateException("Stale configuration version.");
    }
}
