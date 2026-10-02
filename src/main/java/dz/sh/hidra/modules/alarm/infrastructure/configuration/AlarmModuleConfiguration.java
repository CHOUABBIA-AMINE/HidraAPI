/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : AlarmModuleConfiguration
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : Infrastructure
 * @Module      : alarm
 * @Package     : dz.sh.hidra.modules.alarm.infrastructure.configuration
 *
 * @Description : Alarm infrastructure configuration.
 *
 */
package dz.sh.hidra.modules.alarm.infrastructure.configuration;

/**
 * Alarm infrastructure configuration.
 */
public record AlarmModuleConfiguration(
        boolean autoRaiseEnabled,
        boolean shelvingEnabled,
        boolean suppressionEnabled,
        boolean escalationEnabled
) {

    public static AlarmModuleConfiguration defaults() {
        return new AlarmModuleConfiguration(true, true, true, true);
    }
}
