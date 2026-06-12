/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : ExchangeMessageStatus
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Enum
 * @Layer       : Domain
 * @Module      : integration
 * @Package     : dz.sh.hidra.modules.integration.domain.value
 *
 * @Description : Defines ExchangeMessageStatus values.
 *
 */
package dz.sh.hidra.modules.integration.domain.value;

/**
 * Defines ExchangeMessageStatus values.
 */
public enum ExchangeMessageStatus {
    RECEIVED, MAPPED, SUBMITTED, ACCEPTED, REJECTED, SENT, ACKNOWLEDGED, DEAD_LETTERED
}
