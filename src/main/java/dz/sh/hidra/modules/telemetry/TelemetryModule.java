/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : TelemetryModule
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Domain
 * @Module      : telemetry
 * @Package     : dz.sh.hidra.modules.telemetry
 *
 * @Description : Defines telemetry module constants.
 *
 */
package dz.sh.hidra.modules.telemetry;

/**
 * Telemetry module constants.
 */
public final class TelemetryModule {

    public static final String MODULE_NAME = "telemetry";
    public static final String TABLE_PREFIX = "hidra_telemetry_";

    private TelemetryModule() {
        throw new UnsupportedOperationException("Utility class must not be instantiated.");
    }
}
