/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : ConfigurationScope
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : configuration
 * @Package     : dz.sh.hidra.modules.configuration.domain.value
 *
 * @Description : Configuration scope value.
 *
 */
package dz.sh.hidra.modules.configuration.domain.value;

/**
 * Configuration scope value.
 *
 * @param scopeType scope type
 * @param scopeId scope identifier
 * @param moduleName target module name
 * @param organizationUnitId organization-unit reference
 */
public record ConfigurationScope(
        String scopeType,
        String scopeId,
        String moduleName,
        String organizationUnitId
) {
}
