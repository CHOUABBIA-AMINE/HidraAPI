/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : MonitoringModule
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Domain
 * @Module      : monitoring
 * @Package     : dz.sh.hidra.modules.monitoring
 *
 * @Description : Defines monitoring module constants.
 *
 */
package dz.sh.hidra.modules.monitoring;

/**
 * Monitoring module constants.
 */
public final class MonitoringModule {

    public static final String MODULE_NAME = "monitoring";
    public static final String TABLE_PREFIX = "hidra_monitoring_";

    private MonitoringModule() {
        throw new UnsupportedOperationException("Utility class must not be instantiated.");
    }
}
