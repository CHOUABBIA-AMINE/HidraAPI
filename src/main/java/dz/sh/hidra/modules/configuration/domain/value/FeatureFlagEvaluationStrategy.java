/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : FeatureFlagEvaluationStrategy
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Enum
 * @Layer       : Domain
 * @Module      : configuration
 * @Package     : dz.sh.hidra.modules.configuration.domain.value
 *
 * @Description : Defines FeatureFlagEvaluationStrategy values.
 *
 */
package dz.sh.hidra.modules.configuration.domain.value;

/**
 * Defines FeatureFlagEvaluationStrategy values.
 */
public enum FeatureFlagEvaluationStrategy {
    BOOLEAN, PERCENTAGE, RULE_BASED, ALLOW_LIST, DENY_LIST
}
