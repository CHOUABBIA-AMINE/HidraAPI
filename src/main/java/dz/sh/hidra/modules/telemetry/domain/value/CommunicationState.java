/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : CommunicationState
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Enum
 * @Layer       : Domain
 * @Module      : telemetry
 * @Package     : dz.sh.hidra.modules.telemetry.domain.value
 *
 * @Description : Defines CommunicationState values.
 *
 */
package dz.sh.hidra.modules.telemetry.domain.value;

/**
 * Defines CommunicationState values.
 */
public enum CommunicationState {
    UNKNOWN, ONLINE, STALE, OFFLINE, BAD_QUALITY
}
