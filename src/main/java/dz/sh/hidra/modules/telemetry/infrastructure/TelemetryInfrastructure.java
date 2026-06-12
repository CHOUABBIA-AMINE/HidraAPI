/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : TelemetryInfrastructure
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : telemetry
 * @Package     : dz.sh.hidra.modules.telemetry.infrastructure
 *
 * @Description : Telemetry infrastructure constants.
 *
 */
package dz.sh.hidra.modules.telemetry.infrastructure;

/**
 * Telemetry infrastructure constants.
 */
public final class TelemetryInfrastructure {

    public static final String TABLE_PREFIX = "hidra_telemetry_";

    private TelemetryInfrastructure() {
        throw new UnsupportedOperationException("Utility class must not be instantiated.");
    }
}
