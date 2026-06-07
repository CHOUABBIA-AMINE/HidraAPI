/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : TelemetryReadingState
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Enum
 * @Layer       : Domain
 * @Module      : telemetry
 * @Package     : dz.sh.hidra.modules.telemetry.domain.value
 *
 * @Description : Technical telemetry TelemetryReadingState lifecycle/state enumeration.
 *
 */
package dz.sh.hidra.modules.telemetry.domain.value;

/**
 * Technical telemetry lifecycle/state enumeration.
 *
 * <p>Architecture rule:
 * This enum is allowed because it represents an internal technical lifecycle or processing state,
 * not a user-configurable multilingual business taxonomy.
 */
public enum TelemetryReadingState {

    RECEIVED, ACCEPTED, REJECTED, DUPLICATE, QUARANTINED
}
