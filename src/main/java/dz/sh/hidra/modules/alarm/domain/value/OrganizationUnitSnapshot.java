/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : OrganizationUnitSnapshot
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : alarm
 * @Package     : dz.sh.hidra.modules.alarm.domain.value
 *
 * @Description : Organization unit snapshot captured by alarms.
 *
 */
package dz.sh.hidra.modules.alarm.domain.value;

/**
 * Organization unit snapshot captured by alarms.
 *
 * @param organizationUnitId organization unit reference
 * @param organizationUnitCode organization unit code snapshot
 * @param organizationUnitNameSnapshot organization unit display name snapshot
 */
public record OrganizationUnitSnapshot(
        String organizationUnitId,
        String organizationUnitCode,
        String organizationUnitNameSnapshot
) {
}
