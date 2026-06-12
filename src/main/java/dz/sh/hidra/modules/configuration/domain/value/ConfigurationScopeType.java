/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : ConfigurationScopeType
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Enum
 * @Layer       : Domain
 * @Module      : configuration
 * @Package     : dz.sh.hidra.modules.configuration.domain.value
 *
 * @Description : Defines ConfigurationScopeType values.
 *
 */
package dz.sh.hidra.modules.configuration.domain.value;

/**
 * Defines ConfigurationScopeType values.
 */
public enum ConfigurationScopeType {
    GLOBAL, MODULE, ORGANIZATION_UNIT, ENVIRONMENT, TENANT, ACTOR, TARGET_CONTEXT
}
