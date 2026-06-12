/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : ReadingState
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Enum
 * @Layer       : Domain
 * @Module      : telemetry
 * @Package     : dz.sh.hidra.modules.telemetry.domain.value
 *
 * @Description : Defines ReadingState values.
 *
 */
package dz.sh.hidra.modules.telemetry.domain.value;

/**
 * Defines ReadingState values.
 */
public enum ReadingState {
    RECEIVED, ACCEPTED, REJECTED, DUPLICATE, QUARANTINED, TRUSTED
}
