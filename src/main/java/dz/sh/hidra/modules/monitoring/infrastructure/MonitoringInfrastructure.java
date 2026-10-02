/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : MonitoringInfrastructure
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : monitoring
 * @Package     : dz.sh.hidra.modules.monitoring.infrastructure
 *
 * @Description : Monitoring infrastructure constants.
 *
 */
package dz.sh.hidra.modules.monitoring.infrastructure;

/**
 * Monitoring infrastructure constants.
 */
public final class MonitoringInfrastructure {

    public static final String TABLE_PREFIX = "hidra_monitoring_";

    private MonitoringInfrastructure() {
        throw new UnsupportedOperationException("Utility class must not be instantiated.");
    }
}
