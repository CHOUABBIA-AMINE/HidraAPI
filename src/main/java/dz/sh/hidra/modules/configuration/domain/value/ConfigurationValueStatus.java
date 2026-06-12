/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : ConfigurationValueStatus
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Enum
 * @Layer       : Domain
 * @Module      : configuration
 * @Package     : dz.sh.hidra.modules.configuration.domain.value
 *
 * @Description : Defines ConfigurationValueStatus values.
 *
 */
package dz.sh.hidra.modules.configuration.domain.value;

/**
 * Defines ConfigurationValueStatus values.
 */
public enum ConfigurationValueStatus {
    DRAFT, ACTIVE, SUPERSEDED, EXPIRED, REVOKED
}
