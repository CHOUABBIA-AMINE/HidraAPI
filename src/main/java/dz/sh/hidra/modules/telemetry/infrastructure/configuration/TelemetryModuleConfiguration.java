/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : TelemetryModuleConfiguration
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : Infrastructure
 * @Module      : telemetry
 * @Package     : dz.sh.hidra.modules.telemetry.infrastructure.configuration
 *
 * @Description : Telemetry infrastructure configuration.
 *
 */
package dz.sh.hidra.modules.telemetry.infrastructure.configuration;

/**
 * Telemetry infrastructure configuration.
 */
public record TelemetryModuleConfiguration(
        boolean ingestionEnabled,
        boolean quarantineEnabled,
        boolean trustedReadingEnabled,
        boolean pointStateSnapshotEnabled
) {

    public static TelemetryModuleConfiguration defaults() {
        return new TelemetryModuleConfiguration(true, true, true, true);
    }
}
