/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : AlarmInfrastructure
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Utility
 * @Layer       : Infrastructure
 * @Module      : alarm
 * @Package     : dz.sh.hidra.modules.alarm.infrastructure
 *
 * @Description : Alarm infrastructure constants.
 *
 */
package dz.sh.hidra.modules.alarm.infrastructure;

/**
 * Alarm infrastructure constants.
 */
public final class AlarmInfrastructure {

    public static final String TABLE_PREFIX = "hidra_alarm";

    private AlarmInfrastructure() {
        throw new UnsupportedOperationException("Utility class must not be instantiated.");
    }
}
