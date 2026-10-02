/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : AlarmModule
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Domain
 * @Module      : alarm
 * @Package     : dz.sh.hidra.modules.alarm
 *
 * @Description : Defines alarm module constants.
 *
 */
package dz.sh.hidra.modules.alarm;

/**
 * Alarm module constants.
 */
public final class AlarmModule {

    public static final String MODULE_NAME = "alarm";
    public static final String TABLE_PREFIX = "hidra_alarm";

    private AlarmModule() {
        throw new UnsupportedOperationException("Utility class must not be instantiated.");
    }
}
