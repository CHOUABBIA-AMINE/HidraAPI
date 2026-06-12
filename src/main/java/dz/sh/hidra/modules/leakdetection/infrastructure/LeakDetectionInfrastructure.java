/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : LeakDetectionInfrastructure
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : leakdetection
 * @Package     : dz.sh.hidra.modules.leakdetection.infrastructure
 *
 * @Description : Leak detection infrastructure constants.
 *
 */
package dz.sh.hidra.modules.leakdetection.infrastructure;

/**
 * Leak detection infrastructure constants.
 */
public final class LeakDetectionInfrastructure {

    public static final String TABLE_PREFIX = "hidra_leak_detection_";

    private LeakDetectionInfrastructure() {
        throw new UnsupportedOperationException("Utility class must not be instantiated.");
    }
}
