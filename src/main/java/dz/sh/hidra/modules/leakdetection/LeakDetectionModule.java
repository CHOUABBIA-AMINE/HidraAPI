/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : LeakDetectionModule
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Utility
 * @Layer       : Domain
 * @Module      : leakdetection
 * @Package     : dz.sh.hidra.modules.leakdetection
 *
 * @Description : Defines leakdetection module constants.
 *
 */
package dz.sh.hidra.modules.leakdetection;

/**
 * Leak detection module constants.
 */
public final class LeakDetectionModule {

    public static final String MODULE_NAME = "leakdetection";
    public static final String TABLE_PREFIX = "hidra_leak_detection_";

    private LeakDetectionModule() {
        throw new UnsupportedOperationException("Utility class must not be instantiated.");
    }
}
