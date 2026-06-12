/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : EndpointRole
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Enum
 * @Layer       : Domain
 * @Module      : telemetry
 * @Package     : dz.sh.hidra.modules.telemetry.domain.value
 *
 * @Description : Defines EndpointRole values.
 *
 */
package dz.sh.hidra.modules.telemetry.domain.value;

/**
 * Defines EndpointRole values.
 */
public enum EndpointRole {
    PRIMARY, SECONDARY, FAILOVER, HISTORIAN_API, SUBSCRIPTION, POLLING, MANUAL_IMPORT
}
