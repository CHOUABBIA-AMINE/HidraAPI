/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : OutboxEventStatus
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Enum
 * @Layer       : Platform
 * @Module      : platform
 * @Package     : dz.sh.hidra.platform.outbox
 *
 * @Description : Defines technical outbox publication statuses.
 *
 */
package dz.sh.hidra.platform.outbox;

/**
 * Technical publication state for an outbox event.
 */
public enum OutboxEventStatus {
    PENDING,
    PUBLISHED,
    FAILED
}
